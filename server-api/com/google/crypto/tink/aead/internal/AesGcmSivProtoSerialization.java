/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.AesGcmSivKey;
/*     */ import com.google.crypto.tink.aead.AesGcmSivParameters;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.AesGcmSivKey;
/*     */ import com.google.crypto.tink.proto.AesGcmSivKeyFormat;
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
/*     */ public final class AesGcmSivProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.AesGcmSivKey";
/*  51 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.AesGcmSivKey");
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final ParametersSerializer<AesGcmSivParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(AesGcmSivProtoSerialization::serializeParameters, AesGcmSivParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(AesGcmSivProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final KeySerializer<AesGcmSivKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(AesGcmSivProtoSerialization::serializeKey, AesGcmSivKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(AesGcmSivProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(AesGcmSivParameters.Variant variant) throws GeneralSecurityException {
/*  78 */     if (AesGcmSivParameters.Variant.TINK.equals(variant)) {
/*  79 */       return OutputPrefixType.TINK;
/*     */     }
/*  81 */     if (AesGcmSivParameters.Variant.CRUNCHY.equals(variant)) {
/*  82 */       return OutputPrefixType.CRUNCHY;
/*     */     }
/*  84 */     if (AesGcmSivParameters.Variant.NO_PREFIX.equals(variant)) {
/*  85 */       return OutputPrefixType.RAW;
/*     */     }
/*  87 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AesGcmSivParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  92 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  94 */         return AesGcmSivParameters.Variant.TINK;
/*     */       
/*     */       case CRUNCHY:
/*     */       case LEGACY:
/*  98 */         return AesGcmSivParameters.Variant.CRUNCHY;
/*     */       case RAW:
/* 100 */         return AesGcmSivParameters.Variant.NO_PREFIX;
/*     */     } 
/* 102 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/* 103 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(AesGcmSivParameters parameters) throws GeneralSecurityException {
/* 109 */     return ProtoParametersSerialization.create(
/* 110 */         KeyTemplate.newBuilder()
/* 111 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.AesGcmSivKey")
/* 112 */         .setValue(
/* 113 */           AesGcmSivKeyFormat.newBuilder()
/* 114 */           .setKeySize(parameters.getKeySizeBytes())
/* 115 */           .build()
/* 116 */           .toByteString())
/* 117 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 118 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(AesGcmSivKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 123 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.AesGcmSivKey", 
/*     */         
/* 125 */         AesGcmSivKey.newBuilder()
/* 126 */         .setKeyValue(
/* 127 */           ByteString.copyFrom(key
/* 128 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 129 */         .build()
/* 130 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, 
/*     */         
/* 132 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 133 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static AesGcmSivParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     AesGcmSivKeyFormat format;
/* 138 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesGcmSivKey")) {
/* 139 */       throw new IllegalArgumentException("Wrong type URL in call to AesGcmSivProtoSerialization.parseParameters: " + serialization
/*     */           
/* 141 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 146 */       format = AesGcmSivKeyFormat.parseFrom(serialization
/* 147 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 148 */     } catch (InvalidProtocolBufferException e) {
/* 149 */       throw new GeneralSecurityException("Parsing AesGcmSivParameters failed: ", e);
/*     */     } 
/* 151 */     if (format.getVersion() != 0) {
/* 152 */       throw new GeneralSecurityException("Only version 0 parameters are accepted");
/*     */     }
/* 154 */     return AesGcmSivParameters.builder()
/* 155 */       .setKeySizeBytes(format.getKeySize())
/* 156 */       .setVariant(toVariant(serialization.getKeyTemplate().getOutputPrefixType()))
/* 157 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesGcmSivKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 164 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesGcmSivKey")) {
/* 165 */       throw new IllegalArgumentException("Wrong type URL in call to AesGcmSivProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 170 */       AesGcmSivKey protoKey = AesGcmSivKey.parseFrom(serialization
/* 171 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 172 */       if (protoKey.getVersion() != 0) {
/* 173 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 179 */       AesGcmSivParameters parameters = AesGcmSivParameters.builder().setKeySizeBytes(protoKey.getKeyValue().size()).setVariant(toVariant(serialization.getOutputPrefixType())).build();
/* 180 */       return AesGcmSivKey.builder()
/* 181 */         .setParameters(parameters)
/* 182 */         .setKeyBytes(
/* 183 */           SecretBytes.copyFrom(protoKey
/* 184 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)))
/* 185 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 186 */         .build();
/* 187 */     } catch (InvalidProtocolBufferException e) {
/* 188 */       throw new GeneralSecurityException("Parsing AesGcmSivKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 193 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 198 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 199 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 200 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 201 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\AesGcmSivProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */