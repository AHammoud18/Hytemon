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
/*     */ import com.google.crypto.tink.jwt.JwtRsaSsaPkcs1Parameters;
/*     */ import com.google.crypto.tink.jwt.JwtRsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.jwt.JwtRsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPkcs1Algorithm;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPkcs1KeyFormat;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.proto.JwtRsaSsaPkcs1PublicKey;
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
/*     */ public final class JwtRsaSsaPkcs1ProtoSerialization
/*     */ {
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PrivateKey";
/*  57 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PublicKey";
/*  60 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PublicKey");
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final ParametersSerializer<JwtRsaSsaPkcs1Parameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(JwtRsaSsaPkcs1ProtoSerialization::serializeParameters, JwtRsaSsaPkcs1Parameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(JwtRsaSsaPkcs1ProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final KeySerializer<JwtRsaSsaPkcs1PublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(JwtRsaSsaPkcs1ProtoSerialization::serializePublicKey, JwtRsaSsaPkcs1PublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(JwtRsaSsaPkcs1ProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final KeySerializer<JwtRsaSsaPkcs1PrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(JwtRsaSsaPkcs1ProtoSerialization::serializePrivateKey, JwtRsaSsaPkcs1PrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(JwtRsaSsaPkcs1ProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   private static final EnumTypeProtoConverter<JwtRsaSsaPkcs1Algorithm, JwtRsaSsaPkcs1Parameters.Algorithm> ALGORITHM_CONVERTER = EnumTypeProtoConverter.builder()
/* 106 */     .add((Enum)JwtRsaSsaPkcs1Algorithm.RS256, JwtRsaSsaPkcs1Parameters.Algorithm.RS256)
/* 107 */     .add((Enum)JwtRsaSsaPkcs1Algorithm.RS384, JwtRsaSsaPkcs1Parameters.Algorithm.RS384)
/* 108 */     .add((Enum)JwtRsaSsaPkcs1Algorithm.RS512, JwtRsaSsaPkcs1Parameters.Algorithm.RS512)
/* 109 */     .build();
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(JwtRsaSsaPkcs1Parameters parameters) {
/* 112 */     if (parameters
/* 113 */       .getKidStrategy()
/* 114 */       .equals(JwtRsaSsaPkcs1Parameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
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
/*     */   private static JwtRsaSsaPkcs1KeyFormat getProtoKeyFormat(JwtRsaSsaPkcs1Parameters parameters) throws GeneralSecurityException {
/* 130 */     if (!parameters.getKidStrategy().equals(JwtRsaSsaPkcs1Parameters.KidStrategy.IGNORED) && 
/*     */ 
/*     */       
/* 133 */       !parameters.getKidStrategy().equals(JwtRsaSsaPkcs1Parameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/* 134 */       throw new GeneralSecurityException("Unable to serialize Parameters object with KidStrategy " + parameters
/* 135 */           .getKidStrategy());
/*     */     }
/* 137 */     return JwtRsaSsaPkcs1KeyFormat.newBuilder()
/* 138 */       .setVersion(0)
/* 139 */       .setAlgorithm((JwtRsaSsaPkcs1Algorithm)ALGORITHM_CONVERTER.toProtoEnum(parameters.getAlgorithm()))
/* 140 */       .setModulusSizeInBits(parameters.getModulusSizeBits())
/* 141 */       .setPublicExponent(encodeBigInteger(parameters.getPublicExponent()))
/* 142 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(JwtRsaSsaPkcs1Parameters parameters) throws GeneralSecurityException {
/* 147 */     OutputPrefixType outputPrefixType = toProtoOutputPrefixType(parameters);
/* 148 */     return ProtoParametersSerialization.create(
/* 149 */         KeyTemplate.newBuilder()
/* 150 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PrivateKey")
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
/*     */   private static JwtRsaSsaPkcs1PublicKey getProtoPublicKey(JwtRsaSsaPkcs1PublicKey key) throws GeneralSecurityException {
/* 163 */     JwtRsaSsaPkcs1PublicKey.Builder builder = JwtRsaSsaPkcs1PublicKey.newBuilder().setVersion(0).setAlgorithm((JwtRsaSsaPkcs1Algorithm)ALGORITHM_CONVERTER.toProtoEnum(key.getParameters().getAlgorithm())).setN(encodeBigInteger(key.getModulus())).setE(encodeBigInteger(key.getParameters().getPublicExponent()));
/* 164 */     if (key.getParameters().getKidStrategy().equals(JwtRsaSsaPkcs1Parameters.KidStrategy.CUSTOM)) {
/* 165 */       builder.setCustomKid(
/* 166 */           JwtRsaSsaPkcs1PublicKey.CustomKid.newBuilder()
/* 167 */           .setValue(key.getKid().get())
/* 168 */           .build());
/*     */     }
/* 170 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(JwtRsaSsaPkcs1PublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 176 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PublicKey", 
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
/*     */   private static ProtoKeySerialization serializePrivateKey(JwtRsaSsaPkcs1PrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
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
/* 202 */     JwtRsaSsaPkcs1PrivateKey protoPrivateKey = JwtRsaSsaPkcs1PrivateKey.newBuilder().setVersion(0).setPublicKey(getProtoPublicKey(key.getPublicKey())).setD(encodeSecretBigInteger(key.getPrivateExponent(), a)).setP(encodeSecretBigInteger(key.getPrimeP(), a)).setQ(encodeSecretBigInteger(key.getPrimeQ(), a)).setDp(encodeSecretBigInteger(key.getPrimeExponentP(), a)).setDq(encodeSecretBigInteger(key.getPrimeExponentQ(), a)).setCrt(encodeSecretBigInteger(key.getCrtCoefficient(), a)).build();
/* 203 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PrivateKey", protoPrivateKey
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
/*     */   private static JwtRsaSsaPkcs1Parameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     JwtRsaSsaPkcs1KeyFormat format;
/* 223 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PrivateKey")) {
/* 224 */       throw new IllegalArgumentException("Wrong type URL in call to JwtRsaSsaPkcs1ProtoSerialization.parseParameters: " + serialization
/*     */           
/* 226 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 231 */       format = JwtRsaSsaPkcs1KeyFormat.parseFrom(serialization
/* 232 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 233 */     } catch (InvalidProtocolBufferException e) {
/* 234 */       throw new GeneralSecurityException("Parsing JwtRsaSsaPkcs1Parameters failed: ", e);
/*     */     } 
/* 236 */     validateVersion(format.getVersion());
/* 237 */     JwtRsaSsaPkcs1Parameters.KidStrategy kidStrategy = null;
/* 238 */     if (serialization.getKeyTemplate().getOutputPrefixType().equals(OutputPrefixType.TINK)) {
/* 239 */       kidStrategy = JwtRsaSsaPkcs1Parameters.KidStrategy.BASE64_ENCODED_KEY_ID;
/*     */     }
/* 241 */     if (serialization.getKeyTemplate().getOutputPrefixType().equals(OutputPrefixType.RAW)) {
/* 242 */       kidStrategy = JwtRsaSsaPkcs1Parameters.KidStrategy.IGNORED;
/*     */     }
/* 244 */     if (kidStrategy == null) {
/* 245 */       throw new GeneralSecurityException("Invalid OutputPrefixType for JwtHmacKeyFormat");
/*     */     }
/* 247 */     return JwtRsaSsaPkcs1Parameters.builder()
/* 248 */       .setKidStrategy(kidStrategy)
/* 249 */       .setAlgorithm((JwtRsaSsaPkcs1Parameters.Algorithm)ALGORITHM_CONVERTER.fromProtoEnum((Enum)format.getAlgorithm()))
/* 250 */       .setPublicExponent(decodeBigInteger(format.getPublicExponent()))
/* 251 */       .setModulusSizeBits(format.getModulusSizeInBits())
/* 252 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtRsaSsaPkcs1PublicKey getPublicKeyFromProto(JwtRsaSsaPkcs1PublicKey protoKey, OutputPrefixType outputPrefixType, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 260 */     validateVersion(protoKey.getVersion());
/*     */     
/* 262 */     JwtRsaSsaPkcs1Parameters.Builder parametersBuilder = JwtRsaSsaPkcs1Parameters.builder();
/* 263 */     JwtRsaSsaPkcs1PublicKey.Builder keyBuilder = JwtRsaSsaPkcs1PublicKey.builder();
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
/* 274 */       parametersBuilder.setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.BASE64_ENCODED_KEY_ID);
/* 275 */       keyBuilder.setIdRequirement(idRequirement);
/* 276 */     } else if (outputPrefixType.equals(OutputPrefixType.RAW)) {
/* 277 */       if (protoKey.hasCustomKid()) {
/* 278 */         parametersBuilder.setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.CUSTOM);
/* 279 */         keyBuilder.setCustomKid(protoKey.getCustomKid().getValue());
/*     */       } else {
/* 281 */         parametersBuilder.setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy.IGNORED);
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     BigInteger modulus = decodeBigInteger(protoKey.getN());
/* 286 */     int modulusSizeInBits = modulus.bitLength();
/* 287 */     parametersBuilder
/* 288 */       .setAlgorithm((JwtRsaSsaPkcs1Parameters.Algorithm)ALGORITHM_CONVERTER.fromProtoEnum((Enum)protoKey.getAlgorithm()))
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
/*     */   private static JwtRsaSsaPkcs1PublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 301 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PublicKey")) {
/* 302 */       throw new IllegalArgumentException("Wrong type URL in call to JwtRsaSsaPkcs1ProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 304 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 308 */       JwtRsaSsaPkcs1PublicKey protoKey = JwtRsaSsaPkcs1PublicKey.parseFrom(serialization
/* 309 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 310 */       return getPublicKeyFromProto(protoKey, serialization
/* 311 */           .getOutputPrefixType(), serialization.getIdRequirementOrNull());
/* 312 */     } catch (InvalidProtocolBufferException e) {
/* 313 */       throw new GeneralSecurityException("Parsing JwtRsaSsaPkcs1PublicKey failed");
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
/*     */   private static JwtRsaSsaPkcs1PrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 326 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtRsaSsaPkcs1PrivateKey")) {
/* 327 */       throw new IllegalArgumentException("Wrong type URL in call to JwtRsaSsaPkcs1ProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 329 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 333 */       JwtRsaSsaPkcs1PrivateKey protoKey = JwtRsaSsaPkcs1PrivateKey.parseFrom(serialization
/* 334 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 335 */       validateVersion(protoKey.getVersion());
/*     */ 
/*     */       
/* 338 */       JwtRsaSsaPkcs1PublicKey publicKey = getPublicKeyFromProto(protoKey
/* 339 */           .getPublicKey(), serialization
/* 340 */           .getOutputPrefixType(), serialization
/* 341 */           .getIdRequirementOrNull());
/*     */       
/* 343 */       SecretKeyAccess a = SecretKeyAccess.requireAccess(access);
/* 344 */       return JwtRsaSsaPkcs1PrivateKey.builder()
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
/* 356 */       throw new GeneralSecurityException("Parsing JwtRsaSsaPkcs1PrivateKey failed");
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\internal\JwtRsaSsaPkcs1ProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */