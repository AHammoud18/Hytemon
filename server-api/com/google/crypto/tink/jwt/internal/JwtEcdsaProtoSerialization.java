/*     */ package com.google.crypto.tink.jwt.internal;
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
/*     */ import com.google.crypto.tink.jwt.JwtEcdsaParameters;
/*     */ import com.google.crypto.tink.jwt.JwtEcdsaPrivateKey;
/*     */ import com.google.crypto.tink.jwt.JwtEcdsaPublicKey;
/*     */ import com.google.crypto.tink.proto.JwtEcdsaAlgorithm;
/*     */ import com.google.crypto.tink.proto.JwtEcdsaKeyFormat;
/*     */ import com.google.crypto.tink.proto.JwtEcdsaPrivateKey;
/*     */ import com.google.crypto.tink.proto.JwtEcdsaPublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
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
/*     */ 
/*     */ @AccessesPartialKey
/*     */ public final class JwtEcdsaProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.JwtEcdsaPrivateKey";
/*  56 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.JwtEcdsaPrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.JwtEcdsaPublicKey";
/*     */   
/*  60 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.JwtEcdsaPublicKey");
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final ParametersSerializer<JwtEcdsaParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(JwtEcdsaProtoSerialization::serializeParameters, JwtEcdsaParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(JwtEcdsaProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final KeySerializer<JwtEcdsaPublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(JwtEcdsaProtoSerialization::serializePublicKey, JwtEcdsaPublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(JwtEcdsaProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final KeySerializer<JwtEcdsaPrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(JwtEcdsaProtoSerialization::serializePrivateKey, JwtEcdsaPrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(JwtEcdsaProtoSerialization::parsePrivateKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtEcdsaAlgorithm toProtoAlgorithm(JwtEcdsaParameters.Algorithm algorithm) throws GeneralSecurityException {
/* 101 */     if (JwtEcdsaParameters.Algorithm.ES256.equals(algorithm)) {
/* 102 */       return JwtEcdsaAlgorithm.ES256;
/*     */     }
/* 104 */     if (JwtEcdsaParameters.Algorithm.ES384.equals(algorithm)) {
/* 105 */       return JwtEcdsaAlgorithm.ES384;
/*     */     }
/* 107 */     if (JwtEcdsaParameters.Algorithm.ES512.equals(algorithm)) {
/* 108 */       return JwtEcdsaAlgorithm.ES512;
/*     */     }
/* 110 */     throw new GeneralSecurityException("Unable to serialize algorithm: " + algorithm);
/*     */   }
/*     */ 
/*     */   
/*     */   private static JwtEcdsaParameters.Algorithm toAlgorithm(JwtEcdsaAlgorithm algorithm) throws GeneralSecurityException {
/* 115 */     switch (algorithm) {
/*     */       case ES256:
/* 117 */         return JwtEcdsaParameters.Algorithm.ES256;
/*     */       case ES384:
/* 119 */         return JwtEcdsaParameters.Algorithm.ES384;
/*     */       case ES512:
/* 121 */         return JwtEcdsaParameters.Algorithm.ES512;
/*     */     } 
/* 123 */     throw new GeneralSecurityException("Unable to parse algorithm: " + algorithm.getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtEcdsaKeyFormat serializeToJwtEcdsaKeyFormat(JwtEcdsaParameters parameters) throws GeneralSecurityException {
/* 129 */     if (!parameters.getKidStrategy().equals(JwtEcdsaParameters.KidStrategy.IGNORED) && 
/*     */ 
/*     */       
/* 132 */       !parameters.getKidStrategy().equals(JwtEcdsaParameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/* 133 */       throw new GeneralSecurityException("Unable to serialize Parameters object with KidStrategy " + parameters
/* 134 */           .getKidStrategy());
/*     */     }
/* 136 */     return JwtEcdsaKeyFormat.newBuilder()
/* 137 */       .setVersion(0)
/* 138 */       .setAlgorithm(toProtoAlgorithm(parameters.getAlgorithm()))
/* 139 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(JwtEcdsaParameters parameters) throws GeneralSecurityException {
/* 144 */     OutputPrefixType outputPrefixType = OutputPrefixType.TINK;
/* 145 */     if (parameters.getKidStrategy().equals(JwtEcdsaParameters.KidStrategy.IGNORED)) {
/* 146 */       outputPrefixType = OutputPrefixType.RAW;
/*     */     }
/* 148 */     return ProtoParametersSerialization.create(
/* 149 */         KeyTemplate.newBuilder()
/* 150 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.JwtEcdsaPrivateKey")
/* 151 */         .setValue(serializeToJwtEcdsaKeyFormat(parameters).toByteString())
/* 152 */         .setOutputPrefixType(outputPrefixType)
/* 153 */         .build());
/*     */   }
/*     */   
/*     */   private static JwtEcdsaParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     JwtEcdsaKeyFormat format;
/* 158 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtEcdsaPrivateKey")) {
/* 159 */       throw new IllegalArgumentException("Wrong type URL in call to JwtEcdsaParameters.parseParameters: " + serialization
/*     */           
/* 161 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 166 */       format = JwtEcdsaKeyFormat.parseFrom(serialization
/* 167 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 168 */     } catch (InvalidProtocolBufferException e) {
/* 169 */       throw new GeneralSecurityException("Parsing JwtEcdsaKeyFormat failed: ", e);
/*     */     } 
/* 171 */     if (format.getVersion() != 0) {
/* 172 */       throw new GeneralSecurityException("Parsing HmacParameters failed: unknown Version " + format
/* 173 */           .getVersion());
/*     */     }
/* 175 */     JwtEcdsaParameters.KidStrategy kidStrategy = null;
/* 176 */     if (serialization.getKeyTemplate().getOutputPrefixType().equals(OutputPrefixType.TINK)) {
/* 177 */       kidStrategy = JwtEcdsaParameters.KidStrategy.BASE64_ENCODED_KEY_ID;
/*     */     }
/* 179 */     if (serialization.getKeyTemplate().getOutputPrefixType().equals(OutputPrefixType.RAW)) {
/* 180 */       kidStrategy = JwtEcdsaParameters.KidStrategy.IGNORED;
/*     */     }
/* 182 */     if (kidStrategy == null) {
/* 183 */       throw new GeneralSecurityException("Invalid OutputPrefixType for JwtHmacKeyFormat");
/*     */     }
/* 185 */     return JwtEcdsaParameters.builder()
/* 186 */       .setAlgorithm(toAlgorithm(format.getAlgorithm()))
/* 187 */       .setKidStrategy(kidStrategy)
/* 188 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getEncodingLength(JwtEcdsaParameters.Algorithm algorithm) throws GeneralSecurityException {
/* 196 */     if (algorithm.equals(JwtEcdsaParameters.Algorithm.ES256)) {
/* 197 */       return 33;
/*     */     }
/* 199 */     if (algorithm.equals(JwtEcdsaParameters.Algorithm.ES384)) {
/* 200 */       return 49;
/*     */     }
/* 202 */     if (algorithm.equals(JwtEcdsaParameters.Algorithm.ES512)) {
/* 203 */       return 67;
/*     */     }
/* 205 */     throw new GeneralSecurityException("Unknown algorithm: " + algorithm);
/*     */   }
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(JwtEcdsaParameters parameters) {
/* 209 */     if (parameters.getKidStrategy().equals(JwtEcdsaParameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/* 210 */       return OutputPrefixType.TINK;
/*     */     }
/* 212 */     return OutputPrefixType.RAW;
/*     */   }
/*     */ 
/*     */   
/*     */   private static JwtEcdsaPublicKey serializePublicKey(JwtEcdsaPublicKey key) throws GeneralSecurityException {
/* 217 */     int encLength = getEncodingLength(key.getParameters().getAlgorithm());
/* 218 */     ECPoint publicPoint = key.getPublicPoint();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     JwtEcdsaPublicKey.Builder builder = JwtEcdsaPublicKey.newBuilder().setVersion(0).setAlgorithm(toProtoAlgorithm(key.getParameters().getAlgorithm())).setX(ByteString.copyFrom(BigIntegerEncoding.toBigEndianBytesOfFixedLength(publicPoint.getAffineX(), encLength))).setY(
/* 228 */         ByteString.copyFrom(
/* 229 */           BigIntegerEncoding.toBigEndianBytesOfFixedLength(publicPoint
/* 230 */             .getAffineY(), encLength)));
/* 231 */     if (key.getParameters().getKidStrategy().equals(JwtEcdsaParameters.KidStrategy.CUSTOM)) {
/* 232 */       builder.setCustomKid(
/* 233 */           JwtEcdsaPublicKey.CustomKid.newBuilder()
/* 234 */           .setValue(key.getKid().get())
/* 235 */           .build());
/*     */     }
/* 237 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(JwtEcdsaPublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 242 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.JwtEcdsaPublicKey", 
/*     */         
/* 244 */         serializePublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, 
/*     */         
/* 246 */         toProtoOutputPrefixType(key.getParameters()), key
/* 247 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtEcdsaPublicKey parsePublicKeyFromProto(JwtEcdsaPublicKey protoKey, OutputPrefixType outputPrefixType, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 255 */     if (protoKey.getVersion() != 0) {
/* 256 */       throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */     }
/*     */     
/* 259 */     JwtEcdsaParameters.Builder parametersBuilder = JwtEcdsaParameters.builder();
/* 260 */     JwtEcdsaPublicKey.Builder keyBuilder = JwtEcdsaPublicKey.builder();
/*     */     
/* 262 */     if (outputPrefixType.equals(OutputPrefixType.TINK)) {
/* 263 */       if (protoKey.hasCustomKid()) {
/* 264 */         throw new GeneralSecurityException("Keys serialized with OutputPrefixType TINK should not have a custom kid");
/*     */       }
/*     */       
/* 267 */       if (idRequirement == null) {
/* 268 */         throw new GeneralSecurityException("Keys serialized with OutputPrefixType TINK need an ID Requirement");
/*     */       }
/*     */       
/* 271 */       parametersBuilder.setKidStrategy(JwtEcdsaParameters.KidStrategy.BASE64_ENCODED_KEY_ID);
/* 272 */       keyBuilder.setIdRequirement(idRequirement);
/* 273 */     } else if (outputPrefixType.equals(OutputPrefixType.RAW)) {
/* 274 */       if (protoKey.hasCustomKid()) {
/* 275 */         parametersBuilder.setKidStrategy(JwtEcdsaParameters.KidStrategy.CUSTOM);
/* 276 */         keyBuilder.setCustomKid(protoKey.getCustomKid().getValue());
/*     */       } else {
/* 278 */         parametersBuilder.setKidStrategy(JwtEcdsaParameters.KidStrategy.IGNORED);
/*     */       } 
/*     */     } 
/* 281 */     parametersBuilder.setAlgorithm(toAlgorithm(protoKey.getAlgorithm()));
/* 282 */     keyBuilder.setPublicPoint(new ECPoint(
/*     */           
/* 284 */           BigIntegerEncoding.fromUnsignedBigEndianBytes(protoKey.getX().toByteArray()), 
/* 285 */           BigIntegerEncoding.fromUnsignedBigEndianBytes(protoKey.getY().toByteArray())));
/* 286 */     return keyBuilder.setParameters(parametersBuilder.build()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtEcdsaPublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 293 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtEcdsaPublicKey")) {
/* 294 */       throw new IllegalArgumentException("Wrong type URL in call to EcdsaProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 296 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 300 */       JwtEcdsaPublicKey protoKey = JwtEcdsaPublicKey.parseFrom(serialization
/* 301 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 302 */       return parsePublicKeyFromProto(protoKey, serialization
/* 303 */           .getOutputPrefixType(), serialization.getIdRequirementOrNull());
/* 304 */     } catch (InvalidProtocolBufferException e) {
/* 305 */       throw new GeneralSecurityException("Parsing EcdsaPublicKey failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static JwtEcdsaPrivateKey serializePrivateKeyToProto(JwtEcdsaPrivateKey key, SecretKeyAccess access) throws GeneralSecurityException {
/* 311 */     int encLength = getEncodingLength(key.getParameters().getAlgorithm());
/* 312 */     return JwtEcdsaPrivateKey.newBuilder()
/* 313 */       .setPublicKey(serializePublicKey(key.getPublicKey()))
/* 314 */       .setKeyValue(
/* 315 */         ByteString.copyFrom(
/* 316 */           BigIntegerEncoding.toBigEndianBytesOfFixedLength(key
/* 317 */             .getPrivateValue().getBigInteger(access), encLength)))
/* 318 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(JwtEcdsaPrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 323 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.JwtEcdsaPrivateKey", 
/*     */         
/* 325 */         serializePrivateKeyToProto(key, SecretKeyAccess.requireAccess(access)).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, 
/*     */         
/* 327 */         toProtoOutputPrefixType(key.getParameters()), key
/* 328 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtEcdsaPrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 335 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtEcdsaPrivateKey")) {
/* 336 */       throw new IllegalArgumentException("Wrong type URL in call to EcdsaProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 338 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 342 */       JwtEcdsaPrivateKey protoKey = JwtEcdsaPrivateKey.parseFrom(serialization
/* 343 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 344 */       if (protoKey.getVersion() != 0) {
/* 345 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 348 */       JwtEcdsaPublicKey publicKey = parsePublicKeyFromProto(protoKey
/* 349 */           .getPublicKey(), serialization
/* 350 */           .getOutputPrefixType(), serialization
/* 351 */           .getIdRequirementOrNull());
/* 352 */       return JwtEcdsaPrivateKey.create(publicKey, 
/*     */           
/* 354 */           SecretBigInteger.fromBigInteger(
/* 355 */             BigIntegerEncoding.fromUnsignedBigEndianBytes(protoKey.getKeyValue().toByteArray()), 
/* 356 */             SecretKeyAccess.requireAccess(access)));
/* 357 */     } catch (InvalidProtocolBufferException e) {
/* 358 */       throw new GeneralSecurityException("Parsing EcdsaPrivateKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 363 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 368 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 369 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 370 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 371 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 372 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 373 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\internal\JwtEcdsaProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */