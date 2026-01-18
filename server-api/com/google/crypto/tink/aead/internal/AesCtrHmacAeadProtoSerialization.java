/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadKey;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadParameters;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.AesCtrHmacAeadKey;
/*     */ import com.google.crypto.tink.proto.AesCtrHmacAeadKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesCtrKey;
/*     */ import com.google.crypto.tink.proto.AesCtrKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesCtrParams;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.HmacKey;
/*     */ import com.google.crypto.tink.proto.HmacKeyFormat;
/*     */ import com.google.crypto.tink.proto.HmacParams;
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
/*     */ @AccessesPartialKey
/*     */ public final class AesCtrHmacAeadProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey";
/*  52 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey");
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final ParametersSerializer<AesCtrHmacAeadParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(AesCtrHmacAeadProtoSerialization::serializeParameters, AesCtrHmacAeadParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(AesCtrHmacAeadProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final KeySerializer<AesCtrHmacAeadKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(AesCtrHmacAeadProtoSerialization::serializeKey, AesCtrHmacAeadKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(AesCtrHmacAeadProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(AesCtrHmacAeadParameters.Variant variant) throws GeneralSecurityException {
/*  79 */     if (AesCtrHmacAeadParameters.Variant.TINK.equals(variant)) {
/*  80 */       return OutputPrefixType.TINK;
/*     */     }
/*  82 */     if (AesCtrHmacAeadParameters.Variant.CRUNCHY.equals(variant)) {
/*  83 */       return OutputPrefixType.CRUNCHY;
/*     */     }
/*  85 */     if (AesCtrHmacAeadParameters.Variant.NO_PREFIX.equals(variant)) {
/*  86 */       return OutputPrefixType.RAW;
/*     */     }
/*  88 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AesCtrHmacAeadParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  93 */     switch (outputPrefixType) {
/*     */       case SHA1:
/*  95 */         return AesCtrHmacAeadParameters.Variant.TINK;
/*     */       case SHA224:
/*     */       case SHA256:
/*  98 */         return AesCtrHmacAeadParameters.Variant.CRUNCHY;
/*     */       case SHA384:
/* 100 */         return AesCtrHmacAeadParameters.Variant.NO_PREFIX;
/*     */     } 
/* 102 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/* 103 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static HashType toProtoHashType(AesCtrHmacAeadParameters.HashType hashType) throws GeneralSecurityException {
/* 109 */     if (AesCtrHmacAeadParameters.HashType.SHA1.equals(hashType)) {
/* 110 */       return HashType.SHA1;
/*     */     }
/* 112 */     if (AesCtrHmacAeadParameters.HashType.SHA224.equals(hashType)) {
/* 113 */       return HashType.SHA224;
/*     */     }
/* 115 */     if (AesCtrHmacAeadParameters.HashType.SHA256.equals(hashType)) {
/* 116 */       return HashType.SHA256;
/*     */     }
/* 118 */     if (AesCtrHmacAeadParameters.HashType.SHA384.equals(hashType)) {
/* 119 */       return HashType.SHA384;
/*     */     }
/* 121 */     if (AesCtrHmacAeadParameters.HashType.SHA512.equals(hashType)) {
/* 122 */       return HashType.SHA512;
/*     */     }
/* 124 */     throw new GeneralSecurityException("Unable to serialize HashType " + hashType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AesCtrHmacAeadParameters.HashType toHashType(HashType hashType) throws GeneralSecurityException {
/* 129 */     switch (hashType) {
/*     */       case SHA1:
/* 131 */         return AesCtrHmacAeadParameters.HashType.SHA1;
/*     */       case SHA224:
/* 133 */         return AesCtrHmacAeadParameters.HashType.SHA224;
/*     */       case SHA256:
/* 135 */         return AesCtrHmacAeadParameters.HashType.SHA256;
/*     */       case SHA384:
/* 137 */         return AesCtrHmacAeadParameters.HashType.SHA384;
/*     */       case SHA512:
/* 139 */         return AesCtrHmacAeadParameters.HashType.SHA512;
/*     */     } 
/* 141 */     throw new GeneralSecurityException("Unable to parse HashType: " + hashType.getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static HmacParams getHmacProtoParams(AesCtrHmacAeadParameters parameters) throws GeneralSecurityException {
/* 147 */     return HmacParams.newBuilder()
/* 148 */       .setTagSize(parameters.getTagSizeBytes())
/* 149 */       .setHash(toProtoHashType(parameters.getHashType()))
/* 150 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(AesCtrHmacAeadParameters parameters) throws GeneralSecurityException {
/* 155 */     return ProtoParametersSerialization.create(
/* 156 */         KeyTemplate.newBuilder()
/* 157 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey")
/* 158 */         .setValue(
/* 159 */           AesCtrHmacAeadKeyFormat.newBuilder()
/* 160 */           .setAesCtrKeyFormat(
/* 161 */             AesCtrKeyFormat.newBuilder()
/* 162 */             .setParams(
/* 163 */               AesCtrParams.newBuilder()
/* 164 */               .setIvSize(parameters.getIvSizeBytes())
/* 165 */               .build())
/* 166 */             .setKeySize(parameters.getAesKeySizeBytes())
/* 167 */             .build())
/* 168 */           .setHmacKeyFormat(
/* 169 */             HmacKeyFormat.newBuilder()
/* 170 */             .setParams(getHmacProtoParams(parameters))
/* 171 */             .setKeySize(parameters.getHmacKeySizeBytes())
/* 172 */             .build())
/* 173 */           .build()
/* 174 */           .toByteString())
/* 175 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 176 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(AesCtrHmacAeadKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 181 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey", 
/*     */         
/* 183 */         AesCtrHmacAeadKey.newBuilder()
/* 184 */         .setAesCtrKey(
/* 185 */           AesCtrKey.newBuilder()
/* 186 */           .setParams(
/* 187 */             AesCtrParams.newBuilder()
/* 188 */             .setIvSize(key.getParameters().getIvSizeBytes())
/* 189 */             .build())
/* 190 */           .setKeyValue(
/* 191 */             ByteString.copyFrom(key
/* 192 */               .getAesKeyBytes()
/* 193 */               .toByteArray(SecretKeyAccess.requireAccess(access))))
/* 194 */           .build())
/* 195 */         .setHmacKey(
/* 196 */           HmacKey.newBuilder()
/* 197 */           .setParams(getHmacProtoParams(key.getParameters()))
/* 198 */           .setKeyValue(
/* 199 */             ByteString.copyFrom(key
/* 200 */               .getHmacKeyBytes()
/* 201 */               .toByteArray(SecretKeyAccess.requireAccess(access))))
/* 202 */           .build())
/* 203 */         .build()
/* 204 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, 
/*     */         
/* 206 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 207 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static AesCtrHmacAeadParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     AesCtrHmacAeadKeyFormat format;
/* 212 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey")) {
/* 213 */       throw new IllegalArgumentException("Wrong type URL in call to AesCtrHmacAeadProtoSerialization.parseParameters: " + serialization
/*     */           
/* 215 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 220 */       format = AesCtrHmacAeadKeyFormat.parseFrom(serialization
/* 221 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 222 */     } catch (InvalidProtocolBufferException e) {
/* 223 */       throw new GeneralSecurityException("Parsing AesCtrHmacAeadParameters failed: ", e);
/*     */     } 
/* 225 */     if (format.getHmacKeyFormat().getVersion() != 0) {
/* 226 */       throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */     }
/* 228 */     return AesCtrHmacAeadParameters.builder()
/* 229 */       .setAesKeySizeBytes(format.getAesCtrKeyFormat().getKeySize())
/* 230 */       .setHmacKeySizeBytes(format.getHmacKeyFormat().getKeySize())
/* 231 */       .setIvSizeBytes(format.getAesCtrKeyFormat().getParams().getIvSize())
/* 232 */       .setTagSizeBytes(format.getHmacKeyFormat().getParams().getTagSize())
/* 233 */       .setHashType(toHashType(format.getHmacKeyFormat().getParams().getHash()))
/* 234 */       .setVariant(toVariant(serialization.getKeyTemplate().getOutputPrefixType()))
/* 235 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesCtrHmacAeadKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 242 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey")) {
/* 243 */       throw new IllegalArgumentException("Wrong type URL in call to AesCtrHmacAeadProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 248 */       AesCtrHmacAeadKey protoKey = AesCtrHmacAeadKey.parseFrom(serialization
/* 249 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 250 */       if (protoKey.getVersion() != 0) {
/* 251 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 253 */       if (protoKey.getAesCtrKey().getVersion() != 0) {
/* 254 */         throw new GeneralSecurityException("Only version 0 keys inner AES CTR keys are accepted");
/*     */       }
/* 256 */       if (protoKey.getHmacKey().getVersion() != 0) {
/* 257 */         throw new GeneralSecurityException("Only version 0 keys inner HMAC keys are accepted");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 267 */       AesCtrHmacAeadParameters parameters = AesCtrHmacAeadParameters.builder().setAesKeySizeBytes(protoKey.getAesCtrKey().getKeyValue().size()).setHmacKeySizeBytes(protoKey.getHmacKey().getKeyValue().size()).setIvSizeBytes(protoKey.getAesCtrKey().getParams().getIvSize()).setTagSizeBytes(protoKey.getHmacKey().getParams().getTagSize()).setHashType(toHashType(protoKey.getHmacKey().getParams().getHash())).setVariant(toVariant(serialization.getOutputPrefixType())).build();
/* 268 */       return AesCtrHmacAeadKey.builder()
/* 269 */         .setParameters(parameters)
/* 270 */         .setAesKeyBytes(
/* 271 */           SecretBytes.copyFrom(protoKey
/* 272 */             .getAesCtrKey().getKeyValue().toByteArray(), 
/* 273 */             SecretKeyAccess.requireAccess(access)))
/* 274 */         .setHmacKeyBytes(
/* 275 */           SecretBytes.copyFrom(protoKey
/* 276 */             .getHmacKey().getKeyValue().toByteArray(), 
/* 277 */             SecretKeyAccess.requireAccess(access)))
/* 278 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 279 */         .build();
/* 280 */     } catch (InvalidProtocolBufferException e) {
/* 281 */       throw new GeneralSecurityException("Parsing AesCtrHmacAeadKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 286 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 291 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 292 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 293 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 294 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\AesCtrHmacAeadProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */