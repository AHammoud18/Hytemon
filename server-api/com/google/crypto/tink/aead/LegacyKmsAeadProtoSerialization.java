/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.KmsAeadKey;
/*     */ import com.google.crypto.tink.proto.KmsAeadKeyFormat;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ final class LegacyKmsAeadProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.KmsAeadKey";
/*  42 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.KmsAeadKey");
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final ParametersSerializer<LegacyKmsAeadParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(LegacyKmsAeadProtoSerialization::serializeParameters, LegacyKmsAeadParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(LegacyKmsAeadProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final KeySerializer<LegacyKmsAeadKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(LegacyKmsAeadProtoSerialization::serializeKey, LegacyKmsAeadKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(LegacyKmsAeadProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(LegacyKmsAeadParameters.Variant variant) throws GeneralSecurityException {
/*  69 */     if (LegacyKmsAeadParameters.Variant.TINK.equals(variant)) {
/*  70 */       return OutputPrefixType.TINK;
/*     */     }
/*  72 */     if (LegacyKmsAeadParameters.Variant.NO_PREFIX.equals(variant)) {
/*  73 */       return OutputPrefixType.RAW;
/*     */     }
/*  75 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static LegacyKmsAeadParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  80 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  82 */         return LegacyKmsAeadParameters.Variant.TINK;
/*     */       case RAW:
/*  84 */         return LegacyKmsAeadParameters.Variant.NO_PREFIX;
/*     */     } 
/*  86 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/*  87 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(LegacyKmsAeadParameters parameters) throws GeneralSecurityException {
/*  93 */     return ProtoParametersSerialization.create(
/*  94 */         KeyTemplate.newBuilder()
/*  95 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.KmsAeadKey")
/*  96 */         .setValue(
/*  97 */           KmsAeadKeyFormat.newBuilder().setKeyUri(parameters.keyUri()).build().toByteString())
/*  98 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.variant()))
/*  99 */         .build());
/*     */   }
/*     */   
/*     */   private static LegacyKmsAeadParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     KmsAeadKeyFormat format;
/* 104 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.KmsAeadKey")) {
/* 105 */       throw new IllegalArgumentException("Wrong type URL in call to LegacyKmsAeadProtoSerialization.parseParameters: " + serialization
/*     */           
/* 107 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 112 */       format = KmsAeadKeyFormat.parseFrom(serialization
/* 113 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 114 */     } catch (InvalidProtocolBufferException e) {
/* 115 */       throw new GeneralSecurityException("Parsing KmsAeadKeyFormat failed: ", e);
/*     */     } 
/* 117 */     return LegacyKmsAeadParameters.create(format
/* 118 */         .getKeyUri(), toVariant(serialization.getKeyTemplate().getOutputPrefixType()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(LegacyKmsAeadKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 123 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.KmsAeadKey", 
/*     */         
/* 125 */         KmsAeadKey.newBuilder()
/* 126 */         .setParams(
/* 127 */           KmsAeadKeyFormat.newBuilder().setKeyUri(key.getParameters().keyUri()).build())
/* 128 */         .build()
/* 129 */         .toByteString(), KeyData.KeyMaterialType.REMOTE, 
/*     */         
/* 131 */         toProtoOutputPrefixType(key.getParameters().variant()), key
/* 132 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static LegacyKmsAeadKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 138 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.KmsAeadKey")) {
/* 139 */       throw new IllegalArgumentException("Wrong type URL in call to LegacyKmsAeadProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 144 */       KmsAeadKey protoKey = KmsAeadKey.parseFrom(serialization
/* 145 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 146 */       if (protoKey.getVersion() != 0) {
/* 147 */         throw new GeneralSecurityException("KmsAeadKey are only accepted with version 0, got " + protoKey);
/*     */       }
/*     */ 
/*     */       
/* 151 */       LegacyKmsAeadParameters parameters = LegacyKmsAeadParameters.create(protoKey
/* 152 */           .getParams().getKeyUri(), toVariant(serialization.getOutputPrefixType()));
/* 153 */       return LegacyKmsAeadKey.create(parameters, serialization.getIdRequirementOrNull());
/* 154 */     } catch (InvalidProtocolBufferException e) {
/* 155 */       throw new GeneralSecurityException("Parsing KmsAeadKey failed: ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 160 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 165 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 166 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 167 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 168 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\LegacyKmsAeadProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */