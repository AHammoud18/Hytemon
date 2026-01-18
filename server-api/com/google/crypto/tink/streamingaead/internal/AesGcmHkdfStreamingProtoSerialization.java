/*     */ package com.google.crypto.tink.streamingaead.internal;
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
/*     */ import com.google.crypto.tink.proto.AesGcmHkdfStreamingKey;
/*     */ import com.google.crypto.tink.proto.AesGcmHkdfStreamingKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesGcmHkdfStreamingParams;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.streamingaead.AesGcmHkdfStreamingKey;
/*     */ import com.google.crypto.tink.streamingaead.AesGcmHkdfStreamingParameters;
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
/*     */ public final class AesGcmHkdfStreamingProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.AesGcmHkdfStreamingKey";
/*  53 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.AesGcmHkdfStreamingKey");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final ParametersSerializer<AesGcmHkdfStreamingParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(AesGcmHkdfStreamingProtoSerialization::serializeParameters, AesGcmHkdfStreamingParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(AesGcmHkdfStreamingProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final KeySerializer<AesGcmHkdfStreamingKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(AesGcmHkdfStreamingProtoSerialization::serializeKey, AesGcmHkdfStreamingKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(AesGcmHkdfStreamingProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HashType toProtoHashType(AesGcmHkdfStreamingParameters.HashType hashType) throws GeneralSecurityException {
/*  83 */     if (AesGcmHkdfStreamingParameters.HashType.SHA1.equals(hashType)) {
/*  84 */       return HashType.SHA1;
/*     */     }
/*  86 */     if (AesGcmHkdfStreamingParameters.HashType.SHA256.equals(hashType)) {
/*  87 */       return HashType.SHA256;
/*     */     }
/*  89 */     if (AesGcmHkdfStreamingParameters.HashType.SHA512.equals(hashType)) {
/*  90 */       return HashType.SHA512;
/*     */     }
/*  92 */     throw new GeneralSecurityException("Unable to serialize HashType " + hashType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AesGcmHkdfStreamingParameters.HashType toHashType(HashType hashType) throws GeneralSecurityException {
/*  97 */     switch (hashType) {
/*     */       case SHA1:
/*  99 */         return AesGcmHkdfStreamingParameters.HashType.SHA1;
/*     */       case SHA256:
/* 101 */         return AesGcmHkdfStreamingParameters.HashType.SHA256;
/*     */       case SHA512:
/* 103 */         return AesGcmHkdfStreamingParameters.HashType.SHA512;
/*     */     } 
/* 105 */     throw new GeneralSecurityException("Unable to parse HashType: " + hashType
/* 106 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesGcmHkdfStreamingParams toProtoParams(AesGcmHkdfStreamingParameters parameters) throws GeneralSecurityException {
/* 112 */     return AesGcmHkdfStreamingParams.newBuilder()
/* 113 */       .setCiphertextSegmentSize(parameters.getCiphertextSegmentSizeBytes())
/* 114 */       .setDerivedKeySize(parameters.getDerivedAesGcmKeySizeBytes())
/* 115 */       .setHkdfHashType(toProtoHashType(parameters.getHkdfHashType()))
/* 116 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(AesGcmHkdfStreamingParameters parameters) throws GeneralSecurityException {
/* 121 */     return ProtoParametersSerialization.create(
/* 122 */         KeyTemplate.newBuilder()
/* 123 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.AesGcmHkdfStreamingKey")
/* 124 */         .setValue(
/* 125 */           AesGcmHkdfStreamingKeyFormat.newBuilder()
/* 126 */           .setKeySize(parameters.getKeySizeBytes())
/* 127 */           .setParams(toProtoParams(parameters))
/* 128 */           .build()
/* 129 */           .toByteString())
/* 130 */         .setOutputPrefixType(OutputPrefixType.RAW)
/* 131 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(AesGcmHkdfStreamingKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 137 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.AesGcmHkdfStreamingKey", 
/*     */         
/* 139 */         AesGcmHkdfStreamingKey.newBuilder()
/* 140 */         .setKeyValue(
/* 141 */           ByteString.copyFrom(key
/* 142 */             .getInitialKeyMaterial().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 143 */         .setParams(toProtoParams(key.getParameters()))
/* 144 */         .build()
/* 145 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, OutputPrefixType.RAW, key
/*     */ 
/*     */         
/* 148 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesGcmHkdfStreamingParameters toParametersObject(AesGcmHkdfStreamingParams params, int keySize) throws GeneralSecurityException {
/* 154 */     return AesGcmHkdfStreamingParameters.builder()
/* 155 */       .setKeySizeBytes(keySize)
/* 156 */       .setDerivedAesGcmKeySizeBytes(params.getDerivedKeySize())
/* 157 */       .setCiphertextSegmentSizeBytes(params.getCiphertextSegmentSize())
/* 158 */       .setHkdfHashType(toHashType(params.getHkdfHashType()))
/* 159 */       .build();
/*     */   }
/*     */   
/*     */   private static AesGcmHkdfStreamingParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     AesGcmHkdfStreamingKeyFormat format;
/* 164 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesGcmHkdfStreamingKey")) {
/* 165 */       throw new IllegalArgumentException("Wrong type URL in call to AesGcmHkdfStreamingParameters.parseParameters: " + serialization
/*     */           
/* 167 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 172 */       format = AesGcmHkdfStreamingKeyFormat.parseFrom(serialization
/* 173 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 174 */     } catch (InvalidProtocolBufferException e) {
/* 175 */       throw new GeneralSecurityException("Parsing AesGcmHkdfStreamingParameters failed: ", e);
/*     */     } 
/* 177 */     if (format.getVersion() != 0) {
/* 178 */       throw new GeneralSecurityException("Only version 0 parameters are accepted");
/*     */     }
/* 180 */     return toParametersObject(format.getParams(), format.getKeySize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesGcmHkdfStreamingKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 187 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesGcmHkdfStreamingKey")) {
/* 188 */       throw new IllegalArgumentException("Wrong type URL in call to AesGcmHkdfStreamingParameters.parseParameters");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 193 */       AesGcmHkdfStreamingKey protoKey = AesGcmHkdfStreamingKey.parseFrom(serialization
/* 194 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 195 */       if (protoKey.getVersion() != 0) {
/* 196 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 199 */       AesGcmHkdfStreamingParameters parameters = toParametersObject(protoKey.getParams(), protoKey.getKeyValue().size());
/* 200 */       return AesGcmHkdfStreamingKey.create(parameters, 
/*     */           
/* 202 */           SecretBytes.copyFrom(protoKey
/* 203 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)));
/* 204 */     } catch (InvalidProtocolBufferException e) {
/* 205 */       throw new GeneralSecurityException("Parsing AesGcmHkdfStreamingKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 210 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 215 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 216 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 217 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 218 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\internal\AesGcmHkdfStreamingProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */