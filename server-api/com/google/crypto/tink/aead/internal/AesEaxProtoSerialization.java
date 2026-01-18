/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.AesEaxKey;
/*     */ import com.google.crypto.tink.aead.AesEaxParameters;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.AesEaxKey;
/*     */ import com.google.crypto.tink.proto.AesEaxKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesEaxParams;
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
/*     */ @AccessesPartialKey
/*     */ public final class AesEaxProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.AesEaxKey";
/*  48 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.AesEaxKey");
/*     */ 
/*     */ 
/*     */   
/*  52 */   private static final ParametersSerializer<AesEaxParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(AesEaxProtoSerialization::serializeParameters, AesEaxParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(AesEaxProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final KeySerializer<AesEaxKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(AesEaxProtoSerialization::serializeKey, AesEaxKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(AesEaxProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(AesEaxParameters.Variant variant) throws GeneralSecurityException {
/*  73 */     if (AesEaxParameters.Variant.TINK.equals(variant)) {
/*  74 */       return OutputPrefixType.TINK;
/*     */     }
/*  76 */     if (AesEaxParameters.Variant.CRUNCHY.equals(variant)) {
/*  77 */       return OutputPrefixType.CRUNCHY;
/*     */     }
/*  79 */     if (AesEaxParameters.Variant.NO_PREFIX.equals(variant)) {
/*  80 */       return OutputPrefixType.RAW;
/*     */     }
/*  82 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AesEaxParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  87 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  89 */         return AesEaxParameters.Variant.TINK;
/*     */       
/*     */       case CRUNCHY:
/*     */       case LEGACY:
/*  93 */         return AesEaxParameters.Variant.CRUNCHY;
/*     */       case RAW:
/*  95 */         return AesEaxParameters.Variant.NO_PREFIX;
/*     */     } 
/*  97 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/*  98 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesEaxParams getProtoParams(AesEaxParameters parameters) throws GeneralSecurityException {
/* 105 */     if (parameters.getTagSizeBytes() != 16)
/* 106 */       throw new GeneralSecurityException(
/* 107 */           String.format("Invalid tag size in bytes %d. Currently Tink only supports aes eax keys with tag size equal to 16 bytes.", new Object[] {
/*     */ 
/*     */               
/* 110 */               Integer.valueOf(parameters.getTagSizeBytes())
/*     */             })); 
/* 112 */     return AesEaxParams.newBuilder()
/* 113 */       .setIvSize(parameters.getIvSizeBytes())
/* 114 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(AesEaxParameters parameters) throws GeneralSecurityException {
/* 119 */     return ProtoParametersSerialization.create(
/* 120 */         KeyTemplate.newBuilder()
/* 121 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.AesEaxKey")
/* 122 */         .setValue(
/* 123 */           AesEaxKeyFormat.newBuilder()
/* 124 */           .setParams(getProtoParams(parameters))
/* 125 */           .setKeySize(parameters.getKeySizeBytes())
/* 126 */           .build()
/* 127 */           .toByteString())
/* 128 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 129 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(AesEaxKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 134 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.AesEaxKey", 
/*     */         
/* 136 */         AesEaxKey.newBuilder()
/* 137 */         .setParams(getProtoParams(key.getParameters()))
/* 138 */         .setKeyValue(
/* 139 */           ByteString.copyFrom(key
/* 140 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 141 */         .build()
/* 142 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, 
/*     */         
/* 144 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 145 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static AesEaxParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     AesEaxKeyFormat format;
/* 150 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesEaxKey")) {
/* 151 */       throw new IllegalArgumentException("Wrong type URL in call to AesEaxProtoSerialization.parseParameters: " + serialization
/*     */           
/* 153 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 158 */       format = AesEaxKeyFormat.parseFrom(serialization
/* 159 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 160 */     } catch (InvalidProtocolBufferException e) {
/* 161 */       throw new GeneralSecurityException("Parsing AesEaxParameters failed: ", e);
/*     */     } 
/* 163 */     return AesEaxParameters.builder()
/* 164 */       .setKeySizeBytes(format.getKeySize())
/* 165 */       .setIvSizeBytes(format.getParams().getIvSize())
/*     */       
/* 167 */       .setTagSizeBytes(16)
/* 168 */       .setVariant(toVariant(serialization.getKeyTemplate().getOutputPrefixType()))
/* 169 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesEaxKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 176 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesEaxKey")) {
/* 177 */       throw new IllegalArgumentException("Wrong type URL in call to AesEaxProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 182 */       AesEaxKey protoKey = AesEaxKey.parseFrom(serialization
/* 183 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 184 */       if (protoKey.getVersion() != 0) {
/* 185 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       AesEaxParameters parameters = AesEaxParameters.builder().setKeySizeBytes(protoKey.getKeyValue().size()).setIvSizeBytes(protoKey.getParams().getIvSize()).setTagSizeBytes(16).setVariant(toVariant(serialization.getOutputPrefixType())).build();
/* 194 */       return AesEaxKey.builder()
/* 195 */         .setParameters(parameters)
/* 196 */         .setKeyBytes(
/* 197 */           SecretBytes.copyFrom(protoKey
/* 198 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)))
/* 199 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 200 */         .build();
/* 201 */     } catch (InvalidProtocolBufferException e) {
/* 202 */       throw new GeneralSecurityException("Parsing AesEaxcKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 207 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 212 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 213 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 214 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 215 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\AesEaxProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */