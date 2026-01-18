/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.AesGcmKey;
/*     */ import com.google.crypto.tink.aead.AesGcmParameters;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.SerializationRegistry;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.AesGcmKey;
/*     */ import com.google.crypto.tink.proto.AesGcmKeyFormat;
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
/*     */ @AccessesPartialKey
/*     */ public final class AesGcmProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.AesGcmKey";
/*  49 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.AesGcmKey");
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static final ParametersSerializer<AesGcmParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(AesGcmProtoSerialization::serializeParameters, AesGcmParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(AesGcmProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final KeySerializer<AesGcmKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(AesGcmProtoSerialization::serializeKey, AesGcmKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(AesGcmProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(AesGcmParameters.Variant variant) throws GeneralSecurityException {
/*  74 */     if (AesGcmParameters.Variant.TINK.equals(variant)) {
/*  75 */       return OutputPrefixType.TINK;
/*     */     }
/*  77 */     if (AesGcmParameters.Variant.CRUNCHY.equals(variant)) {
/*  78 */       return OutputPrefixType.CRUNCHY;
/*     */     }
/*  80 */     if (AesGcmParameters.Variant.NO_PREFIX.equals(variant)) {
/*  81 */       return OutputPrefixType.RAW;
/*     */     }
/*  83 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AesGcmParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  88 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  90 */         return AesGcmParameters.Variant.TINK;
/*     */       
/*     */       case CRUNCHY:
/*     */       case LEGACY:
/*  94 */         return AesGcmParameters.Variant.CRUNCHY;
/*     */       case RAW:
/*  96 */         return AesGcmParameters.Variant.NO_PREFIX;
/*     */     } 
/*  98 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/*  99 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateParameters(AesGcmParameters parameters) throws GeneralSecurityException {
/* 106 */     if (parameters.getTagSizeBytes() != 16) {
/* 107 */       throw new GeneralSecurityException(
/* 108 */           String.format("Invalid tag size in bytes %d. Currently Tink only supports serialization of AES GCM keys with tag size equal to 16 bytes.", new Object[] {
/*     */ 
/*     */               
/* 111 */               Integer.valueOf(parameters.getTagSizeBytes())
/*     */             }));
/*     */     }
/* 114 */     if (parameters.getIvSizeBytes() != 12) {
/* 115 */       throw new GeneralSecurityException(
/* 116 */           String.format("Invalid IV size in bytes %d. Currently Tink only supports serialization of AES GCM keys with IV size equal to 12 bytes.", new Object[] {
/*     */ 
/*     */               
/* 119 */               Integer.valueOf(parameters.getIvSizeBytes())
/*     */             }));
/*     */     }
/*     */   }
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(AesGcmParameters parameters) throws GeneralSecurityException {
/* 125 */     validateParameters(parameters);
/* 126 */     return ProtoParametersSerialization.create(
/* 127 */         KeyTemplate.newBuilder()
/* 128 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.AesGcmKey")
/* 129 */         .setValue(
/* 130 */           AesGcmKeyFormat.newBuilder()
/* 131 */           .setKeySize(parameters.getKeySizeBytes())
/* 132 */           .build()
/* 133 */           .toByteString())
/* 134 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 135 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(AesGcmKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 140 */     validateParameters(key.getParameters());
/* 141 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.AesGcmKey", 
/*     */         
/* 143 */         AesGcmKey.newBuilder()
/* 144 */         .setKeyValue(
/* 145 */           ByteString.copyFrom(key
/* 146 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 147 */         .build()
/* 148 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, 
/*     */         
/* 150 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 151 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static AesGcmParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     AesGcmKeyFormat format;
/* 156 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesGcmKey")) {
/* 157 */       throw new IllegalArgumentException("Wrong type URL in call to AesGcmProtoSerialization.parseParameters: " + serialization
/*     */           
/* 159 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 164 */       format = AesGcmKeyFormat.parseFrom(serialization
/* 165 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 166 */     } catch (InvalidProtocolBufferException e) {
/* 167 */       throw new GeneralSecurityException("Parsing AesGcmParameters failed: ", e);
/*     */     } 
/* 169 */     if (format.getVersion() != 0) {
/* 170 */       throw new GeneralSecurityException("Only version 0 parameters are accepted");
/*     */     }
/* 172 */     return AesGcmParameters.builder()
/* 173 */       .setKeySizeBytes(format.getKeySize())
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 179 */       .setIvSizeBytes(12)
/* 180 */       .setTagSizeBytes(16)
/* 181 */       .setVariant(toVariant(serialization.getKeyTemplate().getOutputPrefixType()))
/* 182 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesGcmKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 189 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesGcmKey")) {
/* 190 */       throw new IllegalArgumentException("Wrong type URL in call to AesGcmProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 195 */       AesGcmKey protoKey = AesGcmKey.parseFrom(serialization
/* 196 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 197 */       if (protoKey.getVersion() != 0) {
/* 198 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 206 */       AesGcmParameters parameters = AesGcmParameters.builder().setKeySizeBytes(protoKey.getKeyValue().size()).setIvSizeBytes(12).setTagSizeBytes(16).setVariant(toVariant(serialization.getOutputPrefixType())).build();
/* 207 */       return AesGcmKey.builder()
/* 208 */         .setParameters(parameters)
/* 209 */         .setKeyBytes(
/* 210 */           SecretBytes.copyFrom(protoKey
/* 211 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)))
/* 212 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 213 */         .build();
/* 214 */     } catch (InvalidProtocolBufferException e) {
/* 215 */       throw new GeneralSecurityException("Parsing AesGcmKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 220 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 225 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 226 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 227 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 228 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(SerializationRegistry.Builder registryBuilder) throws GeneralSecurityException {
/* 233 */     registryBuilder.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 234 */     registryBuilder.registerParametersParser(PARAMETERS_PARSER);
/* 235 */     registryBuilder.registerKeySerializer(KEY_SERIALIZER);
/* 236 */     registryBuilder.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\AesGcmProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */