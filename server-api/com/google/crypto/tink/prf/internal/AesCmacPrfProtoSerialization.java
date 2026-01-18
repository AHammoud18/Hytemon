/*     */ package com.google.crypto.tink.prf.internal;
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
/*     */ import com.google.crypto.tink.prf.AesCmacPrfKey;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfParameters;
/*     */ import com.google.crypto.tink.proto.AesCmacPrfKey;
/*     */ import com.google.crypto.tink.proto.AesCmacPrfKeyFormat;
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
/*     */ public final class AesCmacPrfProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.AesCmacPrfKey";
/*  51 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.AesCmacPrfKey");
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final ParametersSerializer<AesCmacPrfParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(AesCmacPrfProtoSerialization::serializeParameters, AesCmacPrfParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(AesCmacPrfProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final KeySerializer<AesCmacPrfKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(AesCmacPrfProtoSerialization::serializeKey, AesCmacPrfKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(AesCmacPrfProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(AesCmacPrfParameters parameters) {
/*  77 */     return ProtoParametersSerialization.create(
/*  78 */         KeyTemplate.newBuilder()
/*  79 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.AesCmacPrfKey")
/*  80 */         .setValue(
/*  81 */           AesCmacPrfKeyFormat.newBuilder()
/*  82 */           .setKeySize(parameters.getKeySizeBytes())
/*  83 */           .build()
/*  84 */           .toByteString())
/*  85 */         .setOutputPrefixType(OutputPrefixType.RAW)
/*  86 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(AesCmacPrfKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/*  91 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.AesCmacPrfKey", 
/*     */         
/*  93 */         AesCmacPrfKey.newBuilder()
/*  94 */         .setKeyValue(
/*  95 */           ByteString.copyFrom(key
/*  96 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/*  97 */         .build()
/*  98 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, OutputPrefixType.RAW, key
/*     */ 
/*     */         
/* 101 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static AesCmacPrfParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     AesCmacPrfKeyFormat format;
/* 106 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesCmacPrfKey")) {
/* 107 */       throw new IllegalArgumentException("Wrong type URL in call to AesCmacPrfProtoSerialization.parseParameters: " + serialization
/*     */           
/* 109 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 114 */       format = AesCmacPrfKeyFormat.parseFrom(serialization
/* 115 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 116 */     } catch (InvalidProtocolBufferException e) {
/* 117 */       throw new GeneralSecurityException("Parsing AesCmacPrfParameters failed: ", e);
/*     */     } 
/* 119 */     if (format.getVersion() != 0) {
/* 120 */       throw new GeneralSecurityException("Parsing AesCmacPrfParameters failed: unknown Version " + format
/* 121 */           .getVersion());
/*     */     }
/* 123 */     if (serialization.getKeyTemplate().getOutputPrefixType() != OutputPrefixType.RAW) {
/* 124 */       throw new GeneralSecurityException("Parsing AesCmacPrfParameters failed: only RAW output prefix type is accepted");
/*     */     }
/*     */     
/* 127 */     return AesCmacPrfParameters.create(format.getKeySize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesCmacPrfKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 134 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesCmacPrfKey")) {
/* 135 */       throw new IllegalArgumentException("Wrong type URL in call to AesCmacPrfProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 140 */       AesCmacPrfKey protoKey = AesCmacPrfKey.parseFrom(serialization
/* 141 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 142 */       if (protoKey.getVersion() != 0) {
/* 143 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 145 */       if (serialization.getIdRequirementOrNull() != null) {
/* 146 */         throw new GeneralSecurityException("ID requirement must be null.");
/*     */       }
/* 148 */       AesCmacPrfParameters parameters = AesCmacPrfParameters.create(protoKey.getKeyValue().size());
/* 149 */       return AesCmacPrfKey.create(parameters, 
/*     */           
/* 151 */           SecretBytes.copyFrom(protoKey
/* 152 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)));
/*     */     }
/* 154 */     catch (InvalidProtocolBufferException e) {
/* 155 */       throw new GeneralSecurityException("Parsing AesCmacPrfKey failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 164 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 170 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 171 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 172 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 173 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\internal\AesCmacPrfProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */