/*     */ package com.google.crypto.tink.mac.internal;
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
/*     */ import com.google.crypto.tink.mac.AesCmacKey;
/*     */ import com.google.crypto.tink.mac.AesCmacParameters;
/*     */ import com.google.crypto.tink.proto.AesCmacKey;
/*     */ import com.google.crypto.tink.proto.AesCmacKeyFormat;
/*     */ import com.google.crypto.tink.proto.AesCmacParams;
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
/*     */ @AccessesPartialKey
/*     */ public final class AesCmacProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.AesCmacKey";
/*  50 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.AesCmacKey");
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static final ParametersSerializer<AesCmacParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(AesCmacProtoSerialization::serializeParameters, AesCmacParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(AesCmacProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static final KeySerializer<AesCmacKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(AesCmacProtoSerialization::serializeKey, AesCmacKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(AesCmacProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toOutputPrefixType(AesCmacParameters.Variant variant) throws GeneralSecurityException {
/*  75 */     if (AesCmacParameters.Variant.TINK.equals(variant)) {
/*  76 */       return OutputPrefixType.TINK;
/*     */     }
/*  78 */     if (AesCmacParameters.Variant.CRUNCHY.equals(variant)) {
/*  79 */       return OutputPrefixType.CRUNCHY;
/*     */     }
/*  81 */     if (AesCmacParameters.Variant.NO_PREFIX.equals(variant)) {
/*  82 */       return OutputPrefixType.RAW;
/*     */     }
/*  84 */     if (AesCmacParameters.Variant.LEGACY.equals(variant)) {
/*  85 */       return OutputPrefixType.LEGACY;
/*     */     }
/*  87 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AesCmacParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  92 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  94 */         return AesCmacParameters.Variant.TINK;
/*     */       case CRUNCHY:
/*  96 */         return AesCmacParameters.Variant.CRUNCHY;
/*     */       case LEGACY:
/*  98 */         return AesCmacParameters.Variant.LEGACY;
/*     */       case RAW:
/* 100 */         return AesCmacParameters.Variant.NO_PREFIX;
/*     */     } 
/* 102 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/* 103 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesCmacParams getProtoParams(AesCmacParameters parameters) {
/* 109 */     return AesCmacParams.newBuilder()
/* 110 */       .setTagSize(parameters.getCryptographicTagSizeBytes())
/* 111 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(AesCmacParameters parameters) throws GeneralSecurityException {
/* 116 */     return ProtoParametersSerialization.create(
/* 117 */         KeyTemplate.newBuilder()
/* 118 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.AesCmacKey")
/* 119 */         .setValue(
/* 120 */           AesCmacKeyFormat.newBuilder()
/* 121 */           .setParams(getProtoParams(parameters))
/* 122 */           .setKeySize(parameters.getKeySizeBytes())
/* 123 */           .build()
/* 124 */           .toByteString())
/* 125 */         .setOutputPrefixType(toOutputPrefixType(parameters.getVariant()))
/* 126 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(AesCmacKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 131 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.AesCmacKey", 
/*     */         
/* 133 */         AesCmacKey.newBuilder()
/* 134 */         .setParams(getProtoParams(key.getParameters()))
/* 135 */         .setKeyValue(
/* 136 */           ByteString.copyFrom(key
/* 137 */             .getAesKey().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 138 */         .build()
/* 139 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, 
/*     */         
/* 141 */         toOutputPrefixType(key.getParameters().getVariant()), key
/* 142 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static AesCmacParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     AesCmacKeyFormat format;
/* 147 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesCmacKey")) {
/* 148 */       throw new IllegalArgumentException("Wrong type URL in call to AesCmacProtoSerialization.parseParameters: " + serialization
/*     */           
/* 150 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 155 */       format = AesCmacKeyFormat.parseFrom(serialization
/* 156 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 157 */     } catch (InvalidProtocolBufferException e) {
/* 158 */       throw new GeneralSecurityException("Parsing AesCmacParameters failed: ", e);
/*     */     } 
/*     */     
/* 161 */     return AesCmacParameters.builder().setKeySizeBytes(format.getKeySize()).setTagSizeBytes(format
/* 162 */         .getParams().getTagSize()).setVariant(
/* 163 */         toVariant(serialization.getKeyTemplate().getOutputPrefixType())).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesCmacKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 170 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesCmacKey")) {
/* 171 */       throw new IllegalArgumentException("Wrong type URL in call to AesCmacProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 176 */       AesCmacKey protoKey = AesCmacKey.parseFrom(serialization
/* 177 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 178 */       if (protoKey.getVersion() != 0) {
/* 179 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 184 */       AesCmacParameters parameters = AesCmacParameters.builder().setKeySizeBytes(protoKey.getKeyValue().size()).setTagSizeBytes(protoKey.getParams().getTagSize()).setVariant(toVariant(serialization.getOutputPrefixType())).build();
/* 185 */       return AesCmacKey.builder()
/* 186 */         .setParameters(parameters)
/* 187 */         .setAesKeyBytes(SecretBytes.copyFrom(protoKey
/* 188 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)))
/* 189 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 190 */         .build();
/* 191 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 192 */       throw new GeneralSecurityException("Parsing AesCmacKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 197 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 202 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 203 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 204 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 205 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\AesCmacProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */