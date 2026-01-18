/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
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
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.proto.RsaSsaPssKeyFormat;
/*     */ import com.google.crypto.tink.proto.RsaSsaPssParams;
/*     */ import com.google.crypto.tink.proto.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.proto.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssParameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ public final class RsaSsaPssProtoSerialization
/*     */ {
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.RsaSsaPssPrivateKey";
/*  57 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.RsaSsaPssPrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.RsaSsaPssPublicKey";
/*  60 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.RsaSsaPssPublicKey");
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final ParametersSerializer<RsaSsaPssParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(RsaSsaPssProtoSerialization::serializeParameters, RsaSsaPssParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(RsaSsaPssProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final KeySerializer<RsaSsaPssPublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(RsaSsaPssProtoSerialization::serializePublicKey, RsaSsaPssPublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(RsaSsaPssProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final KeySerializer<RsaSsaPssPrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(RsaSsaPssProtoSerialization::serializePrivateKey, RsaSsaPssPrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(RsaSsaPssProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private static final EnumTypeProtoConverter<OutputPrefixType, RsaSsaPssParameters.Variant> VARIANT_CONVERTER = EnumTypeProtoConverter.builder()
/* 104 */     .add((Enum)OutputPrefixType.RAW, RsaSsaPssParameters.Variant.NO_PREFIX)
/* 105 */     .add((Enum)OutputPrefixType.TINK, RsaSsaPssParameters.Variant.TINK)
/* 106 */     .add((Enum)OutputPrefixType.CRUNCHY, RsaSsaPssParameters.Variant.CRUNCHY)
/* 107 */     .add((Enum)OutputPrefixType.LEGACY, RsaSsaPssParameters.Variant.LEGACY)
/* 108 */     .build();
/*     */ 
/*     */ 
/*     */   
/* 112 */   private static final EnumTypeProtoConverter<HashType, RsaSsaPssParameters.HashType> HASH_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 113 */     .add((Enum)HashType.SHA256, RsaSsaPssParameters.HashType.SHA256)
/* 114 */     .add((Enum)HashType.SHA384, RsaSsaPssParameters.HashType.SHA384)
/* 115 */     .add((Enum)HashType.SHA512, RsaSsaPssParameters.HashType.SHA512)
/* 116 */     .build();
/*     */ 
/*     */   
/*     */   private static RsaSsaPssParams getProtoParams(RsaSsaPssParameters parameters) throws GeneralSecurityException {
/* 120 */     return RsaSsaPssParams.newBuilder()
/* 121 */       .setSigHash((HashType)HASH_TYPE_CONVERTER.toProtoEnum(parameters.getSigHashType()))
/* 122 */       .setMgf1Hash((HashType)HASH_TYPE_CONVERTER.toProtoEnum(parameters.getMgf1HashType()))
/* 123 */       .setSaltLength(parameters.getSaltLengthBytes())
/* 124 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ByteString encodeBigInteger(BigInteger i) {
/* 131 */     byte[] encoded = BigIntegerEncoding.toBigEndianBytes(i);
/* 132 */     return ByteString.copyFrom(encoded);
/*     */   }
/*     */ 
/*     */   
/*     */   private static RsaSsaPssPublicKey getProtoPublicKey(RsaSsaPssPublicKey key) throws GeneralSecurityException {
/* 137 */     return RsaSsaPssPublicKey.newBuilder()
/* 138 */       .setParams(getProtoParams(key.getParameters()))
/* 139 */       .setN(encodeBigInteger(key.getModulus()))
/* 140 */       .setE(encodeBigInteger(key.getParameters().getPublicExponent()))
/* 141 */       .setVersion(0)
/* 142 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(RsaSsaPssParameters parameters) throws GeneralSecurityException {
/* 147 */     return ProtoParametersSerialization.create(
/* 148 */         KeyTemplate.newBuilder()
/* 149 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.RsaSsaPssPrivateKey")
/* 150 */         .setValue(
/* 151 */           RsaSsaPssKeyFormat.newBuilder()
/* 152 */           .setParams(getProtoParams(parameters))
/* 153 */           .setModulusSizeInBits(parameters.getModulusSizeBits())
/* 154 */           .setPublicExponent(encodeBigInteger(parameters.getPublicExponent()))
/* 155 */           .build()
/* 156 */           .toByteString())
/* 157 */         .setOutputPrefixType((OutputPrefixType)VARIANT_CONVERTER.toProtoEnum(parameters.getVariant()))
/* 158 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(RsaSsaPssPublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 163 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.RsaSsaPssPublicKey", 
/*     */         
/* 165 */         getProtoPublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 167 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 168 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static ByteString encodeSecretBigInteger(SecretBigInteger i, SecretKeyAccess access) {
/* 172 */     return encodeBigInteger(i.getBigInteger(access));
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(RsaSsaPssPrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 177 */     SecretKeyAccess a = SecretKeyAccess.requireAccess(access);
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
/* 188 */     RsaSsaPssPrivateKey protoPrivateKey = RsaSsaPssPrivateKey.newBuilder().setVersion(0).setPublicKey(getProtoPublicKey(key.getPublicKey())).setD(encodeSecretBigInteger(key.getPrivateExponent(), a)).setP(encodeSecretBigInteger(key.getPrimeP(), a)).setQ(encodeSecretBigInteger(key.getPrimeQ(), a)).setDp(encodeSecretBigInteger(key.getPrimeExponentP(), a)).setDq(encodeSecretBigInteger(key.getPrimeExponentQ(), a)).setCrt(encodeSecretBigInteger(key.getCrtCoefficient(), a)).build();
/* 189 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.RsaSsaPssPrivateKey", protoPrivateKey
/*     */         
/* 191 */         .toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 193 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 194 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static BigInteger decodeBigInteger(ByteString data) {
/* 198 */     return BigIntegerEncoding.fromUnsignedBigEndianBytes(data.toByteArray());
/*     */   }
/*     */   
/*     */   private static RsaSsaPssParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     RsaSsaPssKeyFormat format;
/* 203 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.RsaSsaPssPrivateKey")) {
/* 204 */       throw new IllegalArgumentException("Wrong type URL in call to RsaSsaPssProtoSerialization.parseParameters: " + serialization
/*     */           
/* 206 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 211 */       format = RsaSsaPssKeyFormat.parseFrom(serialization
/* 212 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 213 */     } catch (InvalidProtocolBufferException e) {
/* 214 */       throw new GeneralSecurityException("Parsing RsaSsaPssParameters failed: ", e);
/*     */     } 
/* 216 */     return RsaSsaPssParameters.builder()
/* 217 */       .setSigHashType((RsaSsaPssParameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)format.getParams().getSigHash()))
/* 218 */       .setMgf1HashType((RsaSsaPssParameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)format.getParams().getMgf1Hash()))
/* 219 */       .setPublicExponent(decodeBigInteger(format.getPublicExponent()))
/* 220 */       .setModulusSizeBits(format.getModulusSizeInBits())
/* 221 */       .setSaltLengthBytes(format.getParams().getSaltLength())
/* 222 */       .setVariant((RsaSsaPssParameters.Variant)VARIANT_CONVERTER
/* 223 */         .fromProtoEnum((Enum)serialization.getKeyTemplate().getOutputPrefixType()))
/* 224 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RsaSsaPssPublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 231 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.RsaSsaPssPublicKey")) {
/* 232 */       throw new IllegalArgumentException("Wrong type URL in call to RsaSsaPssProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 234 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 238 */       RsaSsaPssPublicKey protoKey = RsaSsaPssPublicKey.parseFrom(serialization
/* 239 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 240 */       if (protoKey.getVersion() != 0) {
/* 241 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 243 */       BigInteger modulus = decodeBigInteger(protoKey.getN());
/* 244 */       int modulusSizeInBits = modulus.bitLength();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       RsaSsaPssParameters parameters = RsaSsaPssParameters.builder().setSigHashType((RsaSsaPssParameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)protoKey.getParams().getSigHash())).setMgf1HashType((RsaSsaPssParameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)protoKey.getParams().getMgf1Hash())).setPublicExponent(decodeBigInteger(protoKey.getE())).setModulusSizeBits(modulusSizeInBits).setSaltLengthBytes(protoKey.getParams().getSaltLength()).setVariant((RsaSsaPssParameters.Variant)VARIANT_CONVERTER.fromProtoEnum((Enum)serialization.getOutputPrefixType())).build();
/* 255 */       return RsaSsaPssPublicKey.builder()
/* 256 */         .setParameters(parameters)
/* 257 */         .setModulus(modulus)
/* 258 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 259 */         .build();
/* 260 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 261 */       throw new GeneralSecurityException("Parsing RsaSsaPssPublicKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static SecretBigInteger decodeSecretBigInteger(ByteString data, SecretKeyAccess access) {
/* 266 */     return SecretBigInteger.fromBigInteger(
/* 267 */         BigIntegerEncoding.fromUnsignedBigEndianBytes(data.toByteArray()), access);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RsaSsaPssPrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 274 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.RsaSsaPssPrivateKey")) {
/* 275 */       throw new IllegalArgumentException("Wrong type URL in call to RsaSsaPssProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 277 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 281 */       RsaSsaPssPrivateKey protoKey = RsaSsaPssPrivateKey.parseFrom(serialization
/* 282 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 283 */       if (protoKey.getVersion() != 0) {
/* 284 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 286 */       RsaSsaPssPublicKey protoPublicKey = protoKey.getPublicKey();
/* 287 */       if (protoPublicKey.getVersion() != 0) {
/* 288 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 291 */       BigInteger modulus = decodeBigInteger(protoPublicKey.getN());
/* 292 */       int modulusSizeInBits = modulus.bitLength();
/* 293 */       BigInteger publicExponent = decodeBigInteger(protoPublicKey.getE());
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
/* 304 */       RsaSsaPssParameters parameters = RsaSsaPssParameters.builder().setSigHashType((RsaSsaPssParameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)protoPublicKey.getParams().getSigHash())).setMgf1HashType((RsaSsaPssParameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)protoPublicKey.getParams().getMgf1Hash())).setPublicExponent(publicExponent).setModulusSizeBits(modulusSizeInBits).setSaltLengthBytes(protoPublicKey.getParams().getSaltLength()).setVariant((RsaSsaPssParameters.Variant)VARIANT_CONVERTER.fromProtoEnum((Enum)serialization.getOutputPrefixType())).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 310 */       RsaSsaPssPublicKey publicKey = RsaSsaPssPublicKey.builder().setParameters(parameters).setModulus(modulus).setIdRequirement(serialization.getIdRequirementOrNull()).build();
/*     */       
/* 312 */       SecretKeyAccess a = SecretKeyAccess.requireAccess(access);
/* 313 */       return RsaSsaPssPrivateKey.builder()
/* 314 */         .setPublicKey(publicKey)
/* 315 */         .setPrimes(
/* 316 */           decodeSecretBigInteger(protoKey.getP(), a), 
/* 317 */           decodeSecretBigInteger(protoKey.getQ(), a))
/* 318 */         .setPrivateExponent(decodeSecretBigInteger(protoKey.getD(), a))
/* 319 */         .setPrimeExponents(
/* 320 */           decodeSecretBigInteger(protoKey.getDp(), a), 
/* 321 */           decodeSecretBigInteger(protoKey.getDq(), a))
/* 322 */         .setCrtCoefficient(decodeSecretBigInteger(protoKey.getCrt(), a))
/* 323 */         .build();
/* 324 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 325 */       throw new GeneralSecurityException("Parsing RsaSsaPssPrivateKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 330 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 335 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 336 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 337 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 338 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 339 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 340 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\RsaSsaPssProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */