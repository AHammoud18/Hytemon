/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.TinkProtoParametersFormat;
/*     */ import com.google.crypto.tink.hybrid.EciesParameters;
/*     */ import com.google.crypto.tink.hybrid.EciesPrivateKey;
/*     */ import com.google.crypto.tink.hybrid.EciesPublicKey;
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.internal.EnumTypeProtoConverter;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.EcPointFormat;
/*     */ import com.google.crypto.tink.proto.EciesAeadDemParams;
/*     */ import com.google.crypto.tink.proto.EciesAeadHkdfKeyFormat;
/*     */ import com.google.crypto.tink.proto.EciesAeadHkdfParams;
/*     */ import com.google.crypto.tink.proto.EciesAeadHkdfPrivateKey;
/*     */ import com.google.crypto.tink.proto.EciesAeadHkdfPublicKey;
/*     */ import com.google.crypto.tink.proto.EciesHkdfKemParams;
/*     */ import com.google.crypto.tink.proto.EllipticCurveType;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECPoint;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @AccessesPartialKey
/*     */ public final class EciesProtoSerialization
/*     */ {
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey";
/*  60 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey";
/*     */   
/*  64 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey");
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final ParametersSerializer<EciesParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(EciesProtoSerialization::serializeParameters, EciesParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(EciesProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private static final KeySerializer<EciesPublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(EciesProtoSerialization::serializePublicKey, EciesPublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(EciesProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private static final KeySerializer<EciesPrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(EciesProtoSerialization::serializePrivateKey, EciesPrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(EciesProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private static final EnumTypeProtoConverter<OutputPrefixType, EciesParameters.Variant> VARIANT_CONVERTER = EnumTypeProtoConverter.builder()
/* 107 */     .add((Enum)OutputPrefixType.RAW, EciesParameters.Variant.NO_PREFIX)
/* 108 */     .add((Enum)OutputPrefixType.TINK, EciesParameters.Variant.TINK)
/* 109 */     .add((Enum)OutputPrefixType.LEGACY, EciesParameters.Variant.CRUNCHY)
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     .add((Enum)OutputPrefixType.CRUNCHY, EciesParameters.Variant.CRUNCHY)
/* 115 */     .build();
/*     */ 
/*     */ 
/*     */   
/* 119 */   private static final EnumTypeProtoConverter<HashType, EciesParameters.HashType> HASH_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 120 */     .add((Enum)HashType.SHA1, EciesParameters.HashType.SHA1)
/* 121 */     .add((Enum)HashType.SHA224, EciesParameters.HashType.SHA224)
/* 122 */     .add((Enum)HashType.SHA256, EciesParameters.HashType.SHA256)
/* 123 */     .add((Enum)HashType.SHA384, EciesParameters.HashType.SHA384)
/* 124 */     .add((Enum)HashType.SHA512, EciesParameters.HashType.SHA512)
/* 125 */     .build();
/*     */ 
/*     */ 
/*     */   
/* 129 */   private static final EnumTypeProtoConverter<EllipticCurveType, EciesParameters.CurveType> CURVE_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 130 */     .add((Enum)EllipticCurveType.NIST_P256, EciesParameters.CurveType.NIST_P256)
/* 131 */     .add((Enum)EllipticCurveType.NIST_P384, EciesParameters.CurveType.NIST_P384)
/* 132 */     .add((Enum)EllipticCurveType.NIST_P521, EciesParameters.CurveType.NIST_P521)
/* 133 */     .add((Enum)EllipticCurveType.CURVE25519, EciesParameters.CurveType.X25519)
/* 134 */     .build();
/*     */ 
/*     */ 
/*     */   
/* 138 */   private static final EnumTypeProtoConverter<EcPointFormat, EciesParameters.PointFormat> POINT_FORMAT_CONVERTER = EnumTypeProtoConverter.builder()
/* 139 */     .add((Enum)EcPointFormat.UNCOMPRESSED, EciesParameters.PointFormat.UNCOMPRESSED)
/* 140 */     .add((Enum)EcPointFormat.COMPRESSED, EciesParameters.PointFormat.COMPRESSED)
/* 141 */     .add((Enum)EcPointFormat.DO_NOT_USE_CRUNCHY_UNCOMPRESSED, EciesParameters.PointFormat.LEGACY_UNCOMPRESSED)
/*     */ 
/*     */     
/* 144 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 151 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 157 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 158 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 159 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 160 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 161 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 162 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EciesAeadHkdfParams toProtoParameters(EciesParameters parameters) throws GeneralSecurityException {
/*     */     EciesAeadDemParams demProtoParams;
/* 170 */     EciesHkdfKemParams.Builder kemProtoParamsBuilder = EciesHkdfKemParams.newBuilder().setCurveType((EllipticCurveType)CURVE_TYPE_CONVERTER.toProtoEnum(parameters.getCurveType())).setHkdfHashType((HashType)HASH_TYPE_CONVERTER.toProtoEnum(parameters.getHashType()));
/* 171 */     if (parameters.getSalt() != null && parameters.getSalt().size() > 0) {
/* 172 */       kemProtoParamsBuilder.setHkdfSalt(ByteString.copyFrom(parameters.getSalt().toByteArray()));
/*     */     }
/* 174 */     EciesHkdfKemParams kemProtoParams = kemProtoParamsBuilder.build();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 179 */       KeyTemplate demKeyTemplate = KeyTemplate.parseFrom(
/* 180 */           TinkProtoParametersFormat.serialize(parameters.getDemParameters()), 
/* 181 */           ExtensionRegistryLite.getEmptyRegistry());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       demProtoParams = EciesAeadDemParams.newBuilder().setAeadDem(KeyTemplate.newBuilder().setTypeUrl(demKeyTemplate.getTypeUrl()).setOutputPrefixType(OutputPrefixType.TINK).setValue(demKeyTemplate.getValue()).build()).build();
/* 193 */     } catch (InvalidProtocolBufferException e) {
/* 194 */       throw new GeneralSecurityException("Parsing EciesParameters failed: ", e);
/*     */     } 
/*     */     
/* 197 */     EciesParameters.PointFormat pointFormat = parameters.getNistCurvePointFormat();
/*     */     
/* 199 */     if (pointFormat == null) {
/* 200 */       pointFormat = EciesParameters.PointFormat.COMPRESSED;
/*     */     }
/* 202 */     return EciesAeadHkdfParams.newBuilder()
/* 203 */       .setKemParams(kemProtoParams)
/* 204 */       .setDemParams(demProtoParams)
/* 205 */       .setEcPointFormat((EcPointFormat)POINT_FORMAT_CONVERTER.toProtoEnum(pointFormat))
/* 206 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EciesParameters fromProtoParameters(OutputPrefixType outputPrefixType, EciesAeadHkdfParams protoParams) throws GeneralSecurityException {
/* 218 */     KeyTemplate aeadKeyTemplate = KeyTemplate.newBuilder().setTypeUrl(protoParams.getDemParams().getAeadDem().getTypeUrl()).setOutputPrefixType(OutputPrefixType.RAW).setValue(protoParams.getDemParams().getAeadDem().getValue()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 228 */     EciesParameters.Builder builder = EciesParameters.builder().setVariant((EciesParameters.Variant)VARIANT_CONVERTER.fromProtoEnum((Enum)outputPrefixType)).setCurveType((EciesParameters.CurveType)CURVE_TYPE_CONVERTER.fromProtoEnum((Enum)protoParams.getKemParams().getCurveType())).setHashType((EciesParameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)protoParams.getKemParams().getHkdfHashType())).setDemParameters(TinkProtoParametersFormat.parse(aeadKeyTemplate.toByteArray())).setSalt(Bytes.copyFrom(protoParams.getKemParams().getHkdfSalt().toByteArray()));
/* 229 */     if (!protoParams.getKemParams().getCurveType().equals(EllipticCurveType.CURVE25519)) {
/* 230 */       builder.setNistCurvePointFormat((EciesParameters.PointFormat)POINT_FORMAT_CONVERTER
/* 231 */           .fromProtoEnum((Enum)protoParams.getEcPointFormat()));
/*     */     }
/* 233 */     else if (!protoParams.getEcPointFormat().equals(EcPointFormat.COMPRESSED)) {
/* 234 */       throw new GeneralSecurityException("For CURVE25519 EcPointFormat must be compressed");
/*     */     } 
/*     */     
/* 237 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getEncodingLength(EciesParameters.CurveType curveType) throws GeneralSecurityException {
/* 245 */     if (EciesParameters.CurveType.NIST_P256.equals(curveType)) {
/* 246 */       return 33;
/*     */     }
/* 248 */     if (EciesParameters.CurveType.NIST_P384.equals(curveType)) {
/* 249 */       return 49;
/*     */     }
/* 251 */     if (EciesParameters.CurveType.NIST_P521.equals(curveType)) {
/* 252 */       return 67;
/*     */     }
/* 254 */     throw new GeneralSecurityException("Unable to serialize CurveType " + curveType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EciesAeadHkdfPublicKey toProtoPublicKey(EciesPublicKey key) throws GeneralSecurityException {
/* 259 */     if (key.getParameters().getCurveType().equals(EciesParameters.CurveType.X25519)) {
/* 260 */       return EciesAeadHkdfPublicKey.newBuilder()
/* 261 */         .setVersion(0)
/* 262 */         .setParams(toProtoParameters(key.getParameters()))
/* 263 */         .setX(ByteString.copyFrom(key.getX25519CurvePointBytes().toByteArray()))
/* 264 */         .setY(ByteString.EMPTY)
/* 265 */         .build();
/*     */     }
/*     */     
/* 268 */     int encLength = getEncodingLength(key.getParameters().getCurveType());
/* 269 */     ECPoint publicPoint = key.getNistCurvePoint();
/* 270 */     if (publicPoint == null) {
/* 271 */       throw new GeneralSecurityException("NistCurvePoint was null for NIST curve");
/*     */     }
/* 273 */     return EciesAeadHkdfPublicKey.newBuilder()
/* 274 */       .setVersion(0)
/* 275 */       .setParams(toProtoParameters(key.getParameters()))
/* 276 */       .setX(
/* 277 */         ByteString.copyFrom(
/* 278 */           BigIntegerEncoding.toBigEndianBytesOfFixedLength(publicPoint
/* 279 */             .getAffineX(), encLength)))
/* 280 */       .setY(
/* 281 */         ByteString.copyFrom(
/* 282 */           BigIntegerEncoding.toBigEndianBytesOfFixedLength(publicPoint
/* 283 */             .getAffineY(), encLength)))
/* 284 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EciesAeadHkdfPrivateKey toProtoPrivateKey(EciesPrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 292 */     EciesAeadHkdfPrivateKey.Builder builder = EciesAeadHkdfPrivateKey.newBuilder().setVersion(0).setPublicKey(toProtoPublicKey(key.getPublicKey()));
/* 293 */     if (key.getParameters().getCurveType().equals(EciesParameters.CurveType.X25519)) {
/* 294 */       builder.setKeyValue(
/* 295 */           ByteString.copyFrom(key
/* 296 */             .getX25519PrivateKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))));
/*     */     } else {
/* 298 */       int encLength = getEncodingLength(key.getParameters().getCurveType());
/* 299 */       builder.setKeyValue(
/* 300 */           ByteString.copyFrom(
/* 301 */             BigIntegerEncoding.toBigEndianBytesOfFixedLength(key
/* 302 */               .getNistPrivateKeyValue().getBigInteger(SecretKeyAccess.requireAccess(access)), encLength)));
/*     */     } 
/*     */     
/* 305 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(EciesParameters parameters) throws GeneralSecurityException {
/* 310 */     return ProtoParametersSerialization.create(
/* 311 */         KeyTemplate.newBuilder()
/* 312 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey")
/* 313 */         .setValue(
/* 314 */           EciesAeadHkdfKeyFormat.newBuilder()
/* 315 */           .setParams(toProtoParameters(parameters))
/* 316 */           .build()
/* 317 */           .toByteString())
/* 318 */         .setOutputPrefixType((OutputPrefixType)VARIANT_CONVERTER.toProtoEnum(parameters.getVariant()))
/* 319 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(EciesPublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 324 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey", 
/*     */         
/* 326 */         toProtoPublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 328 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 329 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(EciesPrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 334 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey", 
/*     */         
/* 336 */         toProtoPrivateKey(key, access).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 338 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 339 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static EciesParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     EciesAeadHkdfKeyFormat format;
/* 344 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey")) {
/* 345 */       throw new IllegalArgumentException("Wrong type URL in call to EciesProtoSerialization.parseParameters: " + serialization
/*     */           
/* 347 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 352 */       format = EciesAeadHkdfKeyFormat.parseFrom(serialization
/* 353 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 354 */     } catch (InvalidProtocolBufferException e) {
/* 355 */       throw new GeneralSecurityException("Parsing EciesParameters failed: ", e);
/*     */     } 
/* 357 */     return fromProtoParameters(serialization
/* 358 */         .getKeyTemplate().getOutputPrefixType(), format.getParams());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EciesPublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 365 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey")) {
/* 366 */       throw new IllegalArgumentException("Wrong type URL in call to EciesProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 368 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 372 */       EciesAeadHkdfPublicKey protoKey = EciesAeadHkdfPublicKey.parseFrom(serialization
/* 373 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 374 */       if (protoKey.getVersion() != 0) {
/* 375 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 378 */       EciesParameters parameters = fromProtoParameters(serialization.getOutputPrefixType(), protoKey.getParams());
/* 379 */       if (parameters.getCurveType().equals(EciesParameters.CurveType.X25519)) {
/* 380 */         if (!protoKey.getY().isEmpty()) {
/* 381 */           throw new GeneralSecurityException("Y must be empty for X25519 points");
/*     */         }
/* 383 */         return EciesPublicKey.createForCurveX25519(parameters, 
/*     */             
/* 385 */             Bytes.copyFrom(protoKey.getX().toByteArray()), serialization
/* 386 */             .getIdRequirementOrNull());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 391 */       ECPoint point = new ECPoint(BigIntegerEncoding.fromUnsignedBigEndianBytes(protoKey.getX().toByteArray()), BigIntegerEncoding.fromUnsignedBigEndianBytes(protoKey.getY().toByteArray()));
/*     */       
/* 393 */       return EciesPublicKey.createForNistCurve(parameters, point, serialization
/* 394 */           .getIdRequirementOrNull());
/* 395 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 396 */       throw new GeneralSecurityException("Parsing EcdsaPublicKey failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EciesPrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 404 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey")) {
/* 405 */       throw new IllegalArgumentException("Wrong type URL in call to EciesProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 407 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 411 */       EciesAeadHkdfPrivateKey protoKey = EciesAeadHkdfPrivateKey.parseFrom(serialization
/* 412 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 413 */       if (protoKey.getVersion() != 0) {
/* 414 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 416 */       EciesAeadHkdfPublicKey protoPublicKey = protoKey.getPublicKey();
/* 417 */       if (protoPublicKey.getVersion() != 0) {
/* 418 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 421 */       EciesParameters parameters = fromProtoParameters(serialization.getOutputPrefixType(), protoPublicKey.getParams());
/* 422 */       if (parameters.getCurveType().equals(EciesParameters.CurveType.X25519)) {
/*     */         
/* 424 */         EciesPublicKey eciesPublicKey = EciesPublicKey.createForCurveX25519(parameters, 
/*     */             
/* 426 */             Bytes.copyFrom(protoPublicKey.getX().toByteArray()), serialization
/* 427 */             .getIdRequirementOrNull());
/* 428 */         return EciesPrivateKey.createForCurveX25519(eciesPublicKey, 
/*     */             
/* 430 */             SecretBytes.copyFrom(protoKey
/* 431 */               .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 436 */       ECPoint point = new ECPoint(BigIntegerEncoding.fromUnsignedBigEndianBytes(protoPublicKey.getX().toByteArray()), BigIntegerEncoding.fromUnsignedBigEndianBytes(protoPublicKey.getY().toByteArray()));
/*     */ 
/*     */       
/* 439 */       EciesPublicKey publicKey = EciesPublicKey.createForNistCurve(parameters, point, serialization
/* 440 */           .getIdRequirementOrNull());
/* 441 */       return EciesPrivateKey.createForNistCurve(publicKey, 
/*     */           
/* 443 */           SecretBigInteger.fromBigInteger(
/* 444 */             BigIntegerEncoding.fromUnsignedBigEndianBytes(protoKey.getKeyValue().toByteArray()), 
/* 445 */             SecretKeyAccess.requireAccess(access)));
/* 446 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 447 */       throw new GeneralSecurityException("Parsing EcdsaPrivateKey failed");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\EciesProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */