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
/*     */ import com.google.crypto.tink.proto.AesCtrHmacStreamingKey;
/*     */ import com.google.crypto.tink.proto.AesCtrHmacStreamingKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesCtrHmacStreamingParams;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.HmacParams;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.streamingaead.AesCtrHmacStreamingKey;
/*     */ import com.google.crypto.tink.streamingaead.AesCtrHmacStreamingParameters;
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
/*     */ public final class AesCtrHmacStreamingProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.AesCtrHmacStreamingKey";
/*  54 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.AesCtrHmacStreamingKey");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final ParametersSerializer<AesCtrHmacStreamingParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(AesCtrHmacStreamingProtoSerialization::serializeParameters, AesCtrHmacStreamingParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(AesCtrHmacStreamingProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static final KeySerializer<AesCtrHmacStreamingKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(AesCtrHmacStreamingProtoSerialization::serializeKey, AesCtrHmacStreamingKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(AesCtrHmacStreamingProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HashType toProtoHashType(AesCtrHmacStreamingParameters.HashType hashType) throws GeneralSecurityException {
/*  84 */     if (AesCtrHmacStreamingParameters.HashType.SHA1.equals(hashType)) {
/*  85 */       return HashType.SHA1;
/*     */     }
/*  87 */     if (AesCtrHmacStreamingParameters.HashType.SHA256.equals(hashType)) {
/*  88 */       return HashType.SHA256;
/*     */     }
/*  90 */     if (AesCtrHmacStreamingParameters.HashType.SHA512.equals(hashType)) {
/*  91 */       return HashType.SHA512;
/*     */     }
/*  93 */     throw new GeneralSecurityException("Unable to serialize HashType " + hashType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AesCtrHmacStreamingParameters.HashType toHashType(HashType hashType) throws GeneralSecurityException {
/*  98 */     switch (hashType) {
/*     */       case SHA1:
/* 100 */         return AesCtrHmacStreamingParameters.HashType.SHA1;
/*     */       case SHA256:
/* 102 */         return AesCtrHmacStreamingParameters.HashType.SHA256;
/*     */       case SHA512:
/* 104 */         return AesCtrHmacStreamingParameters.HashType.SHA512;
/*     */     } 
/* 106 */     throw new GeneralSecurityException("Unable to parse HashType: " + hashType
/* 107 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesCtrHmacStreamingParams toProtoParams(AesCtrHmacStreamingParameters parameters) throws GeneralSecurityException {
/* 113 */     return AesCtrHmacStreamingParams.newBuilder()
/* 114 */       .setCiphertextSegmentSize(parameters.getCiphertextSegmentSizeBytes())
/* 115 */       .setDerivedKeySize(parameters.getDerivedKeySizeBytes())
/* 116 */       .setHkdfHashType(toProtoHashType(parameters.getHkdfHashType()))
/* 117 */       .setHmacParams(
/* 118 */         HmacParams.newBuilder()
/* 119 */         .setHash(toProtoHashType(parameters.getHmacHashType()))
/* 120 */         .setTagSize(parameters.getHmacTagSizeBytes()))
/* 121 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(AesCtrHmacStreamingParameters parameters) throws GeneralSecurityException {
/* 126 */     return ProtoParametersSerialization.create(
/* 127 */         KeyTemplate.newBuilder()
/* 128 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.AesCtrHmacStreamingKey")
/* 129 */         .setValue(
/* 130 */           AesCtrHmacStreamingKeyFormat.newBuilder()
/* 131 */           .setKeySize(parameters.getKeySizeBytes())
/* 132 */           .setParams(toProtoParams(parameters))
/* 133 */           .build()
/* 134 */           .toByteString())
/* 135 */         .setOutputPrefixType(OutputPrefixType.RAW)
/* 136 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(AesCtrHmacStreamingKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 142 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.AesCtrHmacStreamingKey", 
/*     */         
/* 144 */         AesCtrHmacStreamingKey.newBuilder()
/* 145 */         .setKeyValue(
/* 146 */           ByteString.copyFrom(key
/* 147 */             .getInitialKeyMaterial().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 148 */         .setParams(toProtoParams(key.getParameters()))
/* 149 */         .build()
/* 150 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, OutputPrefixType.RAW, key
/*     */ 
/*     */         
/* 153 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesCtrHmacStreamingParameters toParametersObject(AesCtrHmacStreamingParams params, int keySize) throws GeneralSecurityException {
/* 159 */     return AesCtrHmacStreamingParameters.builder()
/* 160 */       .setKeySizeBytes(keySize)
/* 161 */       .setDerivedKeySizeBytes(params.getDerivedKeySize())
/* 162 */       .setCiphertextSegmentSizeBytes(params.getCiphertextSegmentSize())
/* 163 */       .setHkdfHashType(toHashType(params.getHkdfHashType()))
/* 164 */       .setHmacHashType(toHashType(params.getHmacParams().getHash()))
/* 165 */       .setHmacTagSizeBytes(Integer.valueOf(params.getHmacParams().getTagSize()))
/* 166 */       .build();
/*     */   }
/*     */   
/*     */   private static AesCtrHmacStreamingParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     AesCtrHmacStreamingKeyFormat format;
/* 171 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesCtrHmacStreamingKey")) {
/* 172 */       throw new IllegalArgumentException("Wrong type URL in call to AesCtrHmacStreamingParameters.parseParameters: " + serialization
/*     */           
/* 174 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 179 */       format = AesCtrHmacStreamingKeyFormat.parseFrom(serialization
/* 180 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 181 */     } catch (InvalidProtocolBufferException e) {
/* 182 */       throw new GeneralSecurityException("Parsing AesCtrHmacStreamingParameters failed: ", e);
/*     */     } 
/* 184 */     return toParametersObject(format.getParams(), format.getKeySize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesCtrHmacStreamingKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 191 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesCtrHmacStreamingKey")) {
/* 192 */       throw new IllegalArgumentException("Wrong type URL in call to AesCtrHmacStreamingParameters.parseParameters");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 197 */       AesCtrHmacStreamingKey protoKey = AesCtrHmacStreamingKey.parseFrom(serialization
/* 198 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 199 */       if (protoKey.getVersion() != 0) {
/* 200 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 203 */       AesCtrHmacStreamingParameters parameters = toParametersObject(protoKey.getParams(), protoKey.getKeyValue().size());
/* 204 */       return AesCtrHmacStreamingKey.create(parameters, 
/*     */           
/* 206 */           SecretBytes.copyFrom(protoKey
/* 207 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)));
/* 208 */     } catch (InvalidProtocolBufferException e) {
/* 209 */       throw new GeneralSecurityException("Parsing AesCtrHmacStreamingKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 214 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 219 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 220 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 221 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 222 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\internal\AesCtrHmacStreamingProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */