/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.EcdsaKeyFormat;
/*     */ import com.google.crypto.tink.proto.EcdsaParams;
/*     */ import com.google.crypto.tink.proto.EcdsaPrivateKey;
/*     */ import com.google.crypto.tink.proto.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.proto.EcdsaSignatureEncoding;
/*     */ import com.google.crypto.tink.proto.EllipticCurveType;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.signature.EcdsaParameters;
/*     */ import com.google.crypto.tink.signature.EcdsaPrivateKey;
/*     */ import com.google.crypto.tink.signature.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @AccessesPartialKey
/*     */ public final class EcdsaProtoSerialization
/*     */ {
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.EcdsaPrivateKey";
/*  58 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.EcdsaPrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.EcdsaPublicKey";
/*  61 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.EcdsaPublicKey");
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final ParametersSerializer<EcdsaParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(EcdsaProtoSerialization::serializeParameters, EcdsaParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(EcdsaProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final KeySerializer<EcdsaPublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(EcdsaProtoSerialization::serializePublicKey, EcdsaPublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(EcdsaProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final KeySerializer<EcdsaPrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(EcdsaProtoSerialization::serializePrivateKey, EcdsaPrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(EcdsaProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(EcdsaParameters.Variant variant) throws GeneralSecurityException {
/* 103 */     if (EcdsaParameters.Variant.TINK.equals(variant)) {
/* 104 */       return OutputPrefixType.TINK;
/*     */     }
/* 106 */     if (EcdsaParameters.Variant.CRUNCHY.equals(variant)) {
/* 107 */       return OutputPrefixType.CRUNCHY;
/*     */     }
/* 109 */     if (EcdsaParameters.Variant.NO_PREFIX.equals(variant)) {
/* 110 */       return OutputPrefixType.RAW;
/*     */     }
/* 112 */     if (EcdsaParameters.Variant.LEGACY.equals(variant)) {
/* 113 */       return OutputPrefixType.LEGACY;
/*     */     }
/* 115 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static HashType toProtoHashType(EcdsaParameters.HashType hashType) throws GeneralSecurityException {
/* 120 */     if (EcdsaParameters.HashType.SHA256.equals(hashType)) {
/* 121 */       return HashType.SHA256;
/*     */     }
/* 123 */     if (EcdsaParameters.HashType.SHA384.equals(hashType)) {
/* 124 */       return HashType.SHA384;
/*     */     }
/* 126 */     if (EcdsaParameters.HashType.SHA512.equals(hashType)) {
/* 127 */       return HashType.SHA512;
/*     */     }
/* 129 */     throw new GeneralSecurityException("Unable to serialize HashType " + hashType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EcdsaParameters.HashType toHashType(HashType hashType) throws GeneralSecurityException {
/* 134 */     switch (hashType) {
/*     */       case IEEE_P1363:
/* 136 */         return EcdsaParameters.HashType.SHA256;
/*     */       case DER:
/* 138 */         return EcdsaParameters.HashType.SHA384;
/*     */       case null:
/* 140 */         return EcdsaParameters.HashType.SHA512;
/*     */     } 
/* 142 */     throw new GeneralSecurityException("Unable to parse HashType: " + hashType
/* 143 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static EcdsaParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/* 149 */     switch (outputPrefixType) {
/*     */       case IEEE_P1363:
/* 151 */         return EcdsaParameters.Variant.TINK;
/*     */       case DER:
/* 153 */         return EcdsaParameters.Variant.CRUNCHY;
/*     */       case null:
/* 155 */         return EcdsaParameters.Variant.LEGACY;
/*     */       case null:
/* 157 */         return EcdsaParameters.Variant.NO_PREFIX;
/*     */     } 
/* 159 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/* 160 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static EllipticCurveType toProtoCurveType(EcdsaParameters.CurveType curveType) throws GeneralSecurityException {
/* 166 */     if (EcdsaParameters.CurveType.NIST_P256.equals(curveType)) {
/* 167 */       return EllipticCurveType.NIST_P256;
/*     */     }
/* 169 */     if (EcdsaParameters.CurveType.NIST_P384.equals(curveType)) {
/* 170 */       return EllipticCurveType.NIST_P384;
/*     */     }
/* 172 */     if (EcdsaParameters.CurveType.NIST_P521.equals(curveType)) {
/* 173 */       return EllipticCurveType.NIST_P521;
/*     */     }
/* 175 */     throw new GeneralSecurityException("Unable to serialize CurveType " + curveType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getEncodingLength(EcdsaParameters.CurveType curveType) throws GeneralSecurityException {
/* 183 */     if (EcdsaParameters.CurveType.NIST_P256.equals(curveType)) {
/* 184 */       return 33;
/*     */     }
/* 186 */     if (EcdsaParameters.CurveType.NIST_P384.equals(curveType)) {
/* 187 */       return 49;
/*     */     }
/* 189 */     if (EcdsaParameters.CurveType.NIST_P521.equals(curveType)) {
/* 190 */       return 67;
/*     */     }
/* 192 */     throw new GeneralSecurityException("Unable to serialize CurveType " + curveType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EcdsaParameters.CurveType toCurveType(EllipticCurveType protoCurveType) throws GeneralSecurityException {
/* 197 */     switch (protoCurveType) {
/*     */       case IEEE_P1363:
/* 199 */         return EcdsaParameters.CurveType.NIST_P256;
/*     */       case DER:
/* 201 */         return EcdsaParameters.CurveType.NIST_P384;
/*     */       case null:
/* 203 */         return EcdsaParameters.CurveType.NIST_P521;
/*     */     } 
/* 205 */     throw new GeneralSecurityException("Unable to parse EllipticCurveType: " + protoCurveType
/* 206 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static EcdsaSignatureEncoding toProtoSignatureEncoding(EcdsaParameters.SignatureEncoding encoding) throws GeneralSecurityException {
/* 212 */     if (EcdsaParameters.SignatureEncoding.IEEE_P1363.equals(encoding)) {
/* 213 */       return EcdsaSignatureEncoding.IEEE_P1363;
/*     */     }
/* 215 */     if (EcdsaParameters.SignatureEncoding.DER.equals(encoding)) {
/* 216 */       return EcdsaSignatureEncoding.DER;
/*     */     }
/* 218 */     throw new GeneralSecurityException("Unable to serialize SignatureEncoding " + encoding);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EcdsaParameters.SignatureEncoding toSignatureEncoding(EcdsaSignatureEncoding encoding) throws GeneralSecurityException {
/* 223 */     switch (encoding) {
/*     */       case IEEE_P1363:
/* 225 */         return EcdsaParameters.SignatureEncoding.IEEE_P1363;
/*     */       case DER:
/* 227 */         return EcdsaParameters.SignatureEncoding.DER;
/*     */     } 
/* 229 */     throw new GeneralSecurityException("Unable to parse EcdsaSignatureEncoding: " + encoding
/* 230 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static EcdsaParams getProtoParams(EcdsaParameters parameters) throws GeneralSecurityException {
/* 236 */     return EcdsaParams.newBuilder()
/* 237 */       .setHashType(toProtoHashType(parameters.getHashType()))
/* 238 */       .setCurve(toProtoCurveType(parameters.getCurveType()))
/* 239 */       .setEncoding(toProtoSignatureEncoding(parameters.getSignatureEncoding()))
/* 240 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static EcdsaPublicKey getProtoPublicKey(EcdsaPublicKey key) throws GeneralSecurityException {
/* 245 */     int encLength = getEncodingLength(key.getParameters().getCurveType());
/* 246 */     ECPoint publicPoint = key.getPublicPoint();
/* 247 */     return EcdsaPublicKey.newBuilder()
/* 248 */       .setParams(getProtoParams(key.getParameters()))
/* 249 */       .setX(
/* 250 */         ByteString.copyFrom(
/* 251 */           BigIntegerEncoding.toBigEndianBytesOfFixedLength(publicPoint
/* 252 */             .getAffineX(), encLength)))
/* 253 */       .setY(
/* 254 */         ByteString.copyFrom(
/* 255 */           BigIntegerEncoding.toBigEndianBytesOfFixedLength(publicPoint
/* 256 */             .getAffineY(), encLength)))
/* 257 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(EcdsaParameters parameters) throws GeneralSecurityException {
/* 262 */     return ProtoParametersSerialization.create(
/* 263 */         KeyTemplate.newBuilder()
/* 264 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.EcdsaPrivateKey")
/* 265 */         .setValue(
/* 266 */           EcdsaKeyFormat.newBuilder()
/* 267 */           .setParams(getProtoParams(parameters))
/* 268 */           .build()
/* 269 */           .toByteString())
/* 270 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 271 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(EcdsaPublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 276 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.EcdsaPublicKey", 
/*     */         
/* 278 */         getProtoPublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */         
/* 280 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 281 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(EcdsaPrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 286 */     int encLength = getEncodingLength(key.getParameters().getCurveType());
/* 287 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.EcdsaPrivateKey", 
/*     */         
/* 289 */         EcdsaPrivateKey.newBuilder()
/* 290 */         .setPublicKey(getProtoPublicKey(key.getPublicKey()))
/* 291 */         .setKeyValue(
/* 292 */           ByteString.copyFrom(
/* 293 */             BigIntegerEncoding.toBigEndianBytesOfFixedLength(key
/* 294 */               .getPrivateValue().getBigInteger(SecretKeyAccess.requireAccess(access)), encLength)))
/*     */         
/* 296 */         .build()
/* 297 */         .toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, 
/*     */         
/* 299 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 300 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static EcdsaParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     EcdsaKeyFormat format;
/* 305 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.EcdsaPrivateKey")) {
/* 306 */       throw new IllegalArgumentException("Wrong type URL in call to EcdsaProtoSerialization.parseParameters: " + serialization
/*     */           
/* 308 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 313 */       format = EcdsaKeyFormat.parseFrom(serialization
/* 314 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 315 */     } catch (InvalidProtocolBufferException e) {
/* 316 */       throw new GeneralSecurityException("Parsing EcdsaParameters failed: ", e);
/*     */     } 
/* 318 */     return EcdsaParameters.builder()
/* 319 */       .setHashType(toHashType(format.getParams().getHashType()))
/* 320 */       .setSignatureEncoding(toSignatureEncoding(format.getParams().getEncoding()))
/* 321 */       .setCurveType(toCurveType(format.getParams().getCurve()))
/* 322 */       .setVariant(toVariant(serialization.getKeyTemplate().getOutputPrefixType()))
/* 323 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EcdsaPublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 330 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.EcdsaPublicKey")) {
/* 331 */       throw new IllegalArgumentException("Wrong type URL in call to EcdsaProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 333 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 337 */       EcdsaPublicKey protoKey = EcdsaPublicKey.parseFrom(serialization
/* 338 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 339 */       if (protoKey.getVersion() != 0) {
/* 340 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 348 */       EcdsaParameters parameters = EcdsaParameters.builder().setHashType(toHashType(protoKey.getParams().getHashType())).setSignatureEncoding(toSignatureEncoding(protoKey.getParams().getEncoding())).setCurveType(toCurveType(protoKey.getParams().getCurve())).setVariant(toVariant(serialization.getOutputPrefixType())).build();
/* 349 */       return EcdsaPublicKey.builder()
/* 350 */         .setParameters(parameters)
/* 351 */         .setPublicPoint(new ECPoint(
/*     */             
/* 353 */             BigIntegerEncoding.fromUnsignedBigEndianBytes(protoKey.getX().toByteArray()), 
/* 354 */             BigIntegerEncoding.fromUnsignedBigEndianBytes(protoKey.getY().toByteArray())))
/* 355 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 356 */         .build();
/* 357 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 358 */       throw new GeneralSecurityException("Parsing EcdsaPublicKey failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EcdsaPrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 366 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.EcdsaPrivateKey")) {
/* 367 */       throw new IllegalArgumentException("Wrong type URL in call to EcdsaProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 369 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 373 */       EcdsaPrivateKey protoKey = EcdsaPrivateKey.parseFrom(serialization
/* 374 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 375 */       if (protoKey.getVersion() != 0) {
/* 376 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 378 */       EcdsaPublicKey protoPublicKey = protoKey.getPublicKey();
/* 379 */       if (protoPublicKey.getVersion() != 0) {
/* 380 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 388 */       EcdsaParameters parameters = EcdsaParameters.builder().setHashType(toHashType(protoPublicKey.getParams().getHashType())).setSignatureEncoding(toSignatureEncoding(protoPublicKey.getParams().getEncoding())).setCurveType(toCurveType(protoPublicKey.getParams().getCurve())).setVariant(toVariant(serialization.getOutputPrefixType())).build();
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
/* 399 */       EcdsaPublicKey publicKey = EcdsaPublicKey.builder().setParameters(parameters).setPublicPoint(new ECPoint(BigIntegerEncoding.fromUnsignedBigEndianBytes(protoPublicKey.getX().toByteArray()), BigIntegerEncoding.fromUnsignedBigEndianBytes(protoPublicKey.getY().toByteArray()))).setIdRequirement(serialization.getIdRequirementOrNull()).build();
/* 400 */       return EcdsaPrivateKey.builder()
/* 401 */         .setPublicKey(publicKey)
/* 402 */         .setPrivateValue(
/* 403 */           SecretBigInteger.fromBigInteger(
/* 404 */             BigIntegerEncoding.fromUnsignedBigEndianBytes(protoKey
/* 405 */               .getKeyValue().toByteArray()), 
/* 406 */             SecretKeyAccess.requireAccess(access)))
/* 407 */         .build();
/* 408 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 409 */       throw new GeneralSecurityException("Parsing EcdsaPrivateKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 414 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 419 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 420 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 421 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 422 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 423 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 424 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\EcdsaProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */