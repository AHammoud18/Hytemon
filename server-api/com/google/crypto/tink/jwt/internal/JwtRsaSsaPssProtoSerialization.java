/*     */ package com.google.crypto.tink.jwt.internal;
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
/*     */ import com.google.crypto.tink.jwt.JwtRsaSsaPssParameters;
/*     */ import com.google.crypto.tink.jwt.JwtRsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.jwt.JwtRsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPssAlgorithm;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPssKeyFormat;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPssPrivateKey;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
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
/*     */ 
/*     */ @AccessesPartialKey
/*     */ public final class JwtRsaSsaPssProtoSerialization
/*     */ {
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPrivateKey";
/*  57 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPublicKey";
/*  60 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPublicKey");
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final ParametersSerializer<JwtRsaSsaPssParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(JwtRsaSsaPssProtoSerialization::serializeParameters, JwtRsaSsaPssParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(JwtRsaSsaPssProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final KeySerializer<JwtRsaSsaPssPublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(JwtRsaSsaPssProtoSerialization::serializePublicKey, JwtRsaSsaPssPublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(JwtRsaSsaPssProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final KeySerializer<JwtRsaSsaPssPrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(JwtRsaSsaPssProtoSerialization::serializePrivateKey, JwtRsaSsaPssPrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(JwtRsaSsaPssProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   private static final EnumTypeProtoConverter<JwtRsaSsaPssAlgorithm, JwtRsaSsaPssParameters.Algorithm> ALGORITHM_CONVERTER = EnumTypeProtoConverter.builder()
/* 106 */     .add((Enum)JwtRsaSsaPssAlgorithm.PS256, JwtRsaSsaPssParameters.Algorithm.PS256)
/* 107 */     .add((Enum)JwtRsaSsaPssAlgorithm.PS384, JwtRsaSsaPssParameters.Algorithm.PS384)
/* 108 */     .add((Enum)JwtRsaSsaPssAlgorithm.PS512, JwtRsaSsaPssParameters.Algorithm.PS512)
/* 109 */     .build();
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(JwtRsaSsaPssParameters parameters) {
/* 112 */     if (parameters
/* 113 */       .getKidStrategy()
/* 114 */       .equals(JwtRsaSsaPssParameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/* 115 */       return OutputPrefixType.TINK;
/*     */     }
/* 117 */     return OutputPrefixType.RAW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ByteString encodeBigInteger(BigInteger i) {
/* 124 */     byte[] encoded = BigIntegerEncoding.toBigEndianBytes(i);
/* 125 */     return ByteString.copyFrom(encoded);
/*     */   }
/*     */ 
/*     */   
/*     */   private static JwtRsaSsaPssKeyFormat getProtoKeyFormat(JwtRsaSsaPssParameters parameters) throws GeneralSecurityException {
/* 130 */     if (!parameters.getKidStrategy().equals(JwtRsaSsaPssParameters.KidStrategy.IGNORED) && 
/*     */ 
/*     */       
/* 133 */       !parameters.getKidStrategy().equals(JwtRsaSsaPssParameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/* 134 */       throw new GeneralSecurityException("Unable to serialize Parameters object with KidStrategy " + parameters
/* 135 */           .getKidStrategy());
/*     */     }
/* 137 */     return JwtRsaSsaPssKeyFormat.newBuilder()
/* 138 */       .setVersion(0)
/* 139 */       .setAlgorithm((JwtRsaSsaPssAlgorithm)ALGORITHM_CONVERTER.toProtoEnum(parameters.getAlgorithm()))
/* 140 */       .setModulusSizeInBits(parameters.getModulusSizeBits())
/* 141 */       .setPublicExponent(encodeBigInteger(parameters.getPublicExponent()))
/* 142 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(JwtRsaSsaPssParameters parameters) throws GeneralSecurityException {
/* 147 */     OutputPrefixType outputPrefixType = toProtoOutputPrefixType(parameters);
/* 148 */     return ProtoParametersSerialization.create(
/* 149 */         KeyTemplate.newBuilder()
/* 150 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPrivateKey")
/* 151 */         .setValue(getProtoKeyFormat(parameters).toByteString())
/* 152 */         .setOutputPrefixType(outputPrefixType)
/* 153 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtRsaSsaPssPublicKey getProtoPublicKey(JwtRsaSsaPssPublicKey key) throws GeneralSecurityException {
/* 163 */     JwtRsaSsaPssPublicKey.Builder builder = JwtRsaSsaPssPublicKey.newBuilder().setVersion(0).setAlgorithm((JwtRsaSsaPssAlgorithm)ALGORITHM_CONVERTER.toProtoEnum(key.getParameters().getAlgorithm())).setN(encodeBigInteger(key.getModulus())).setE(encodeBigInteger(key.getParameters().getPublicExponent()));
/* 164 */     if (key.getParameters().getKidStrategy().equals(JwtRsaSsaPssParameters.KidStrategy.CUSTOM)) {
/* 165 */       builder.setCustomKid(
/* 166 */           JwtRsaSsaPssPublicKey.CustomKid.newBuilder()
/* 167 */           .setValue(key.getKid().get())
/* 168 */           .build());
/*     */     }
/* 170 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(JwtRsaSsaPssPublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 176 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPublicKey", 
/*     */         
/* 178 */         getProtoPublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */         
/* 180 */         toProtoOutputPrefixType(key.getParameters()), key
/* 181 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static ByteString encodeSecretBigInteger(SecretBigInteger i, SecretKeyAccess access) {
/* 185 */     return encodeBigInteger(i.getBigInteger(access));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(JwtRsaSsaPssPrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 191 */     SecretKeyAccess a = SecretKeyAccess.requireAccess(access);
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
/* 202 */     JwtRsaSsaPssPrivateKey protoPrivateKey = JwtRsaSsaPssPrivateKey.newBuilder().setVersion(0).setPublicKey(getProtoPublicKey(key.getPublicKey())).setD(encodeSecretBigInteger(key.getPrivateExponent(), a)).setP(encodeSecretBigInteger(key.getPrimeP(), a)).setQ(encodeSecretBigInteger(key.getPrimeQ(), a)).setDp(encodeSecretBigInteger(key.getPrimeExponentP(), a)).setDq(encodeSecretBigInteger(key.getPrimeExponentQ(), a)).setCrt(encodeSecretBigInteger(key.getCrtCoefficient(), a)).build();
/* 203 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPrivateKey", protoPrivateKey
/*     */         
/* 205 */         .toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, 
/*     */         
/* 207 */         toProtoOutputPrefixType(key.getParameters()), key
/* 208 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static BigInteger decodeBigInteger(ByteString data) {
/* 212 */     return BigIntegerEncoding.fromUnsignedBigEndianBytes(data.toByteArray());
/*     */   }
/*     */   
/*     */   private static void validateVersion(int version) throws GeneralSecurityException {
/* 216 */     if (version != 0) {
/* 217 */       throw new GeneralSecurityException("Parsing failed: unknown version " + version);
/*     */     }
/*     */   }
/*     */   
/*     */   private static JwtRsaSsaPssParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     JwtRsaSsaPssKeyFormat format;
/* 223 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPrivateKey")) {
/* 224 */       throw new IllegalArgumentException("Wrong type URL in call to JwtRsaSsaPssProtoSerialization.parseParameters: " + serialization
/*     */           
/* 226 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 231 */       format = JwtRsaSsaPssKeyFormat.parseFrom(serialization
/* 232 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 233 */     } catch (InvalidProtocolBufferException e) {
/* 234 */       throw new GeneralSecurityException("Parsing JwtRsaSsaPssParameters failed: ", e);
/*     */     } 
/* 236 */     validateVersion(format.getVersion());
/* 237 */     JwtRsaSsaPssParameters.KidStrategy kidStrategy = null;
/* 238 */     if (serialization.getKeyTemplate().getOutputPrefixType().equals(OutputPrefixType.TINK)) {
/* 239 */       kidStrategy = JwtRsaSsaPssParameters.KidStrategy.BASE64_ENCODED_KEY_ID;
/*     */     }
/* 241 */     if (serialization.getKeyTemplate().getOutputPrefixType().equals(OutputPrefixType.RAW)) {
/* 242 */       kidStrategy = JwtRsaSsaPssParameters.KidStrategy.IGNORED;
/*     */     }
/* 244 */     if (kidStrategy == null) {
/* 245 */       throw new GeneralSecurityException("Invalid OutputPrefixType for JwtHmacKeyFormat");
/*     */     }
/* 247 */     return JwtRsaSsaPssParameters.builder()
/* 248 */       .setKidStrategy(kidStrategy)
/* 249 */       .setAlgorithm((JwtRsaSsaPssParameters.Algorithm)ALGORITHM_CONVERTER.fromProtoEnum((Enum)format.getAlgorithm()))
/* 250 */       .setPublicExponent(decodeBigInteger(format.getPublicExponent()))
/* 251 */       .setModulusSizeBits(format.getModulusSizeInBits())
/* 252 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtRsaSsaPssPublicKey getPublicKeyFromProto(JwtRsaSsaPssPublicKey protoKey, OutputPrefixType outputPrefixType, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 260 */     validateVersion(protoKey.getVersion());
/*     */     
/* 262 */     JwtRsaSsaPssParameters.Builder parametersBuilder = JwtRsaSsaPssParameters.builder();
/* 263 */     JwtRsaSsaPssPublicKey.Builder keyBuilder = JwtRsaSsaPssPublicKey.builder();
/*     */     
/* 265 */     if (outputPrefixType.equals(OutputPrefixType.TINK)) {
/* 266 */       if (protoKey.hasCustomKid()) {
/* 267 */         throw new GeneralSecurityException("Keys serialized with OutputPrefixType TINK should not have a custom kid");
/*     */       }
/*     */       
/* 270 */       if (idRequirement == null) {
/* 271 */         throw new GeneralSecurityException("Keys serialized with OutputPrefixType TINK need an ID Requirement");
/*     */       }
/*     */       
/* 274 */       parametersBuilder.setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.BASE64_ENCODED_KEY_ID);
/* 275 */       keyBuilder.setIdRequirement(idRequirement);
/* 276 */     } else if (outputPrefixType.equals(OutputPrefixType.RAW)) {
/* 277 */       if (protoKey.hasCustomKid()) {
/* 278 */         parametersBuilder.setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.CUSTOM);
/* 279 */         keyBuilder.setCustomKid(protoKey.getCustomKid().getValue());
/*     */       } else {
/* 281 */         parametersBuilder.setKidStrategy(JwtRsaSsaPssParameters.KidStrategy.IGNORED);
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     BigInteger modulus = decodeBigInteger(protoKey.getN());
/* 286 */     int modulusSizeInBits = modulus.bitLength();
/* 287 */     parametersBuilder
/* 288 */       .setAlgorithm((JwtRsaSsaPssParameters.Algorithm)ALGORITHM_CONVERTER.fromProtoEnum((Enum)protoKey.getAlgorithm()))
/* 289 */       .setPublicExponent(decodeBigInteger(protoKey.getE()))
/* 290 */       .setModulusSizeBits(modulusSizeInBits);
/*     */     
/* 292 */     keyBuilder.setModulus(modulus).setParameters(parametersBuilder.build());
/*     */     
/* 294 */     return keyBuilder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtRsaSsaPssPublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 301 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPublicKey")) {
/* 302 */       throw new IllegalArgumentException("Wrong type URL in call to JwtRsaSsaPssProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 304 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 308 */       JwtRsaSsaPssPublicKey protoKey = JwtRsaSsaPssPublicKey.parseFrom(serialization
/* 309 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 310 */       return getPublicKeyFromProto(protoKey, serialization
/* 311 */           .getOutputPrefixType(), serialization.getIdRequirementOrNull());
/* 312 */     } catch (InvalidProtocolBufferException e) {
/* 313 */       throw new GeneralSecurityException("Parsing JwtRsaSsaPssPublicKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static SecretBigInteger decodeSecretBigInteger(ByteString data, SecretKeyAccess access) {
/* 318 */     return SecretBigInteger.fromBigInteger(
/* 319 */         BigIntegerEncoding.fromUnsignedBigEndianBytes(data.toByteArray()), access);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtRsaSsaPssPrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 326 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtRsaSsaPssPrivateKey")) {
/* 327 */       throw new IllegalArgumentException("Wrong type URL in call to JwtRsaSsaPssProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 329 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 333 */       JwtRsaSsaPssPrivateKey protoKey = JwtRsaSsaPssPrivateKey.parseFrom(serialization
/* 334 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 335 */       validateVersion(protoKey.getVersion());
/*     */ 
/*     */       
/* 338 */       JwtRsaSsaPssPublicKey publicKey = getPublicKeyFromProto(protoKey
/* 339 */           .getPublicKey(), serialization
/* 340 */           .getOutputPrefixType(), serialization
/* 341 */           .getIdRequirementOrNull());
/*     */       
/* 343 */       SecretKeyAccess a = SecretKeyAccess.requireAccess(access);
/* 344 */       return JwtRsaSsaPssPrivateKey.builder()
/* 345 */         .setPublicKey(publicKey)
/* 346 */         .setPrimes(
/* 347 */           decodeSecretBigInteger(protoKey.getP(), a), 
/* 348 */           decodeSecretBigInteger(protoKey.getQ(), a))
/* 349 */         .setPrivateExponent(decodeSecretBigInteger(protoKey.getD(), a))
/* 350 */         .setPrimeExponents(
/* 351 */           decodeSecretBigInteger(protoKey.getDp(), a), 
/* 352 */           decodeSecretBigInteger(protoKey.getDq(), a))
/* 353 */         .setCrtCoefficient(decodeSecretBigInteger(protoKey.getCrt(), a))
/* 354 */         .build();
/* 355 */     } catch (InvalidProtocolBufferException e) {
/* 356 */       throw new GeneralSecurityException("Parsing JwtRsaSsaPssPrivateKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 361 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 366 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 367 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 368 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 369 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 370 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 371 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\internal\JwtRsaSsaPssProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */