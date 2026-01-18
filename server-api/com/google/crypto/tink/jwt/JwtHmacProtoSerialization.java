/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.JwtHmacAlgorithm;
/*     */ import com.google.crypto.tink.proto.JwtHmacKey;
/*     */ import com.google.crypto.tink.proto.JwtHmacKeyFormat;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
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
/*     */ final class JwtHmacProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.JwtHmacKey";
/*  50 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.JwtHmacKey");
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static final ParametersSerializer<JwtHmacParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(JwtHmacProtoSerialization::serializeParameters, JwtHmacParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(JwtHmacProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static final KeySerializer<JwtHmacKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(JwtHmacProtoSerialization::serializeKey, JwtHmacKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(JwtHmacProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtHmacAlgorithm toProtoAlgorithm(JwtHmacParameters.Algorithm hashType) throws GeneralSecurityException {
/*  75 */     if (JwtHmacParameters.Algorithm.HS256.equals(hashType)) {
/*  76 */       return JwtHmacAlgorithm.HS256;
/*     */     }
/*  78 */     if (JwtHmacParameters.Algorithm.HS384.equals(hashType)) {
/*  79 */       return JwtHmacAlgorithm.HS384;
/*     */     }
/*  81 */     if (JwtHmacParameters.Algorithm.HS512.equals(hashType)) {
/*  82 */       return JwtHmacAlgorithm.HS512;
/*     */     }
/*  84 */     throw new GeneralSecurityException("Unable to serialize HashType " + hashType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static JwtHmacParameters.Algorithm toAlgorithm(JwtHmacAlgorithm hashType) throws GeneralSecurityException {
/*  89 */     switch (hashType) {
/*     */       case HS256:
/*  91 */         return JwtHmacParameters.Algorithm.HS256;
/*     */       case HS384:
/*  93 */         return JwtHmacParameters.Algorithm.HS384;
/*     */       case HS512:
/*  95 */         return JwtHmacParameters.Algorithm.HS512;
/*     */     } 
/*  97 */     throw new GeneralSecurityException("Unable to parse HashType: " + hashType.getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtHmacKeyFormat serializeToJwtHmacKeyFormat(JwtHmacParameters parameters) throws GeneralSecurityException {
/* 103 */     if (parameters.getKidStrategy().equals(JwtHmacParameters.KidStrategy.CUSTOM)) {
/* 104 */       throw new GeneralSecurityException("Unable to serialize Parameters object with KidStrategy CUSTOM");
/*     */     }
/*     */     
/* 107 */     return JwtHmacKeyFormat.newBuilder()
/* 108 */       .setVersion(0)
/* 109 */       .setAlgorithm(toProtoAlgorithm(parameters.getAlgorithm()))
/* 110 */       .setKeySize(parameters.getKeySizeBytes())
/* 111 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(JwtHmacParameters parameters) throws GeneralSecurityException {
/* 116 */     OutputPrefixType outputPrefixType = OutputPrefixType.TINK;
/* 117 */     if (parameters.getKidStrategy().equals(JwtHmacParameters.KidStrategy.IGNORED)) {
/* 118 */       outputPrefixType = OutputPrefixType.RAW;
/*     */     }
/* 120 */     return ProtoParametersSerialization.create(
/* 121 */         KeyTemplate.newBuilder()
/* 122 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.JwtHmacKey")
/* 123 */         .setValue(serializeToJwtHmacKeyFormat(parameters).toByteString())
/* 124 */         .setOutputPrefixType(outputPrefixType)
/* 125 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(JwtHmacKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 131 */     JwtHmacKey.Builder protoKeyBuilder = JwtHmacKey.newBuilder();
/* 132 */     protoKeyBuilder
/* 133 */       .setVersion(0)
/* 134 */       .setAlgorithm(toProtoAlgorithm(key.getParameters().getAlgorithm()))
/* 135 */       .setKeyValue(
/* 136 */         ByteString.copyFrom(key
/* 137 */           .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))));
/* 138 */     OutputPrefixType outputPrefixType = null;
/* 139 */     if (key.getParameters().getKidStrategy().equals(JwtHmacParameters.KidStrategy.CUSTOM)) {
/* 140 */       protoKeyBuilder.setCustomKid(JwtHmacKey.CustomKid.newBuilder().setValue(key.getKid().get()));
/* 141 */       outputPrefixType = OutputPrefixType.RAW;
/*     */     } 
/* 143 */     if (key.getParameters().getKidStrategy().equals(JwtHmacParameters.KidStrategy.IGNORED)) {
/* 144 */       outputPrefixType = OutputPrefixType.RAW;
/*     */     }
/* 146 */     if (key.getParameters()
/* 147 */       .getKidStrategy()
/* 148 */       .equals(JwtHmacParameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/* 149 */       outputPrefixType = OutputPrefixType.TINK;
/*     */     }
/* 151 */     if (outputPrefixType == null) {
/* 152 */       throw new GeneralSecurityException("Unknown KID Strategy in " + key
/* 153 */           .getParameters().getKidStrategy());
/*     */     }
/*     */     
/* 156 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.JwtHmacKey", protoKeyBuilder
/*     */         
/* 158 */         .build().toByteString(), KeyData.KeyMaterialType.SYMMETRIC, outputPrefixType, key
/*     */ 
/*     */         
/* 161 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static JwtHmacParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     JwtHmacKeyFormat format;
/* 166 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtHmacKey")) {
/* 167 */       throw new IllegalArgumentException("Wrong type URL in call to JwtHmacProtoSerialization.parseParameters: " + serialization
/*     */           
/* 169 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 174 */       format = JwtHmacKeyFormat.parseFrom(serialization
/* 175 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 176 */     } catch (InvalidProtocolBufferException e) {
/* 177 */       throw new GeneralSecurityException("Parsing HmacParameters failed: ", e);
/*     */     } 
/* 179 */     if (format.getVersion() != 0) {
/* 180 */       throw new GeneralSecurityException("Parsing HmacParameters failed: unknown Version " + format
/* 181 */           .getVersion());
/*     */     }
/* 183 */     JwtHmacParameters.KidStrategy kidStrategy = null;
/* 184 */     if (serialization.getKeyTemplate().getOutputPrefixType().equals(OutputPrefixType.TINK)) {
/* 185 */       kidStrategy = JwtHmacParameters.KidStrategy.BASE64_ENCODED_KEY_ID;
/*     */     }
/* 187 */     if (serialization.getKeyTemplate().getOutputPrefixType().equals(OutputPrefixType.RAW)) {
/* 188 */       kidStrategy = JwtHmacParameters.KidStrategy.IGNORED;
/*     */     }
/* 190 */     if (kidStrategy == null) {
/* 191 */       throw new GeneralSecurityException("Invalid OutputPrefixType for JwtHmacKeyFormat");
/*     */     }
/* 193 */     return JwtHmacParameters.builder()
/* 194 */       .setAlgorithm(toAlgorithm(format.getAlgorithm()))
/* 195 */       .setKeySizeBytes(format.getKeySize())
/* 196 */       .setKidStrategy(kidStrategy)
/* 197 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JwtHmacKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 204 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.JwtHmacKey")) {
/* 205 */       throw new IllegalArgumentException("Wrong type URL in call to HmacProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 210 */       JwtHmacKey protoKey = JwtHmacKey.parseFrom(serialization
/* 211 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 212 */       if (protoKey.getVersion() != 0) {
/* 213 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 215 */       JwtHmacParameters.Builder parametersBuilder = JwtHmacParameters.builder();
/* 216 */       JwtHmacKey.Builder keyBuilder = JwtHmacKey.builder();
/* 217 */       if (serialization.getOutputPrefixType().equals(OutputPrefixType.TINK)) {
/* 218 */         if (protoKey.hasCustomKid()) {
/* 219 */           throw new GeneralSecurityException("Keys serialized with OutputPrefixType TINK should not have a custom kid");
/*     */         }
/*     */         
/* 222 */         Integer idRequirement = serialization.getIdRequirementOrNull();
/* 223 */         if (idRequirement == null) {
/* 224 */           throw new GeneralSecurityException("Keys serialized with OutputPrefixType TINK need an ID Requirement");
/*     */         }
/*     */         
/* 227 */         parametersBuilder.setKidStrategy(JwtHmacParameters.KidStrategy.BASE64_ENCODED_KEY_ID);
/* 228 */         keyBuilder.setIdRequirement(idRequirement.intValue());
/* 229 */       } else if (serialization.getOutputPrefixType().equals(OutputPrefixType.RAW)) {
/* 230 */         if (protoKey.hasCustomKid()) {
/* 231 */           parametersBuilder.setKidStrategy(JwtHmacParameters.KidStrategy.CUSTOM);
/* 232 */           keyBuilder.setCustomKid(protoKey.getCustomKid().getValue());
/*     */         } else {
/* 234 */           parametersBuilder.setKidStrategy(JwtHmacParameters.KidStrategy.IGNORED);
/*     */         } 
/*     */       } 
/* 237 */       parametersBuilder.setAlgorithm(toAlgorithm(protoKey.getAlgorithm()));
/* 238 */       parametersBuilder.setKeySizeBytes(protoKey.getKeyValue().size());
/* 239 */       return keyBuilder
/* 240 */         .setKeyBytes(
/* 241 */           SecretBytes.copyFrom(protoKey
/* 242 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)))
/* 243 */         .setParameters(parametersBuilder.build())
/* 244 */         .build();
/* 245 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 246 */       throw new GeneralSecurityException("Parsing HmacKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 251 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 256 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 257 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 258 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 259 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtHmacProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */