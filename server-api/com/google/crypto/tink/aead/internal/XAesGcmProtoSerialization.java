/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.XAesGcmKey;
/*     */ import com.google.crypto.tink.aead.XAesGcmParameters;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.SerializationRegistry;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.proto.XAesGcmKey;
/*     */ import com.google.crypto.tink.proto.XAesGcmKeyFormat;
/*     */ import com.google.crypto.tink.proto.XAesGcmParams;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Objects;
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
/*     */ public final class XAesGcmProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.XAesGcmKey";
/*  52 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.XAesGcmKey");
/*     */ 
/*     */   
/*     */   private static final int KEY_SIZE_BYTES = 32;
/*     */   
/*  57 */   private static final ParametersSerializer<XAesGcmParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(XAesGcmProtoSerialization::serializeParameters, XAesGcmParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(XAesGcmProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final KeySerializer<XAesGcmKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(XAesGcmProtoSerialization::serializeKey, XAesGcmKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(XAesGcmProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(XAesGcmParameters.Variant variant) throws GeneralSecurityException {
/*  78 */     if (Objects.equals(variant, XAesGcmParameters.Variant.TINK)) {
/*  79 */       return OutputPrefixType.TINK;
/*     */     }
/*  81 */     if (Objects.equals(variant, XAesGcmParameters.Variant.NO_PREFIX)) {
/*  82 */       return OutputPrefixType.RAW;
/*     */     }
/*  84 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static XAesGcmParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  89 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  91 */         return XAesGcmParameters.Variant.TINK;
/*     */       case RAW:
/*  93 */         return XAesGcmParameters.Variant.NO_PREFIX;
/*     */     } 
/*  95 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/*  96 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(XAesGcmParameters parameters) throws GeneralSecurityException {
/* 102 */     return ProtoParametersSerialization.create(
/* 103 */         KeyTemplate.newBuilder()
/* 104 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.XAesGcmKey")
/* 105 */         .setValue(
/* 106 */           XAesGcmKeyFormat.newBuilder()
/* 107 */           .setParams(
/* 108 */             XAesGcmParams.newBuilder()
/* 109 */             .setSaltSize(parameters.getSaltSizeBytes())
/* 110 */             .build())
/* 111 */           .build()
/* 112 */           .toByteString())
/* 113 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 114 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(XAesGcmKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 119 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.XAesGcmKey", 
/*     */         
/* 121 */         XAesGcmKey.newBuilder()
/* 122 */         .setKeyValue(
/* 123 */           ByteString.copyFrom(key
/* 124 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 125 */         .setParams(
/* 126 */           XAesGcmParams.newBuilder()
/* 127 */           .setSaltSize(key.getParameters().getSaltSizeBytes())
/* 128 */           .build())
/* 129 */         .build()
/* 130 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, 
/*     */         
/* 132 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 133 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static XAesGcmParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     XAesGcmKeyFormat format;
/* 138 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.XAesGcmKey")) {
/* 139 */       throw new IllegalArgumentException("Wrong type URL in call to XAesGcmProtoSerialization.parseParameters: " + serialization
/*     */           
/* 141 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 146 */       format = XAesGcmKeyFormat.parseFrom(serialization
/* 147 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 148 */     } catch (InvalidProtocolBufferException e) {
/* 149 */       throw new GeneralSecurityException("Parsing XAesGcmParameters failed: ", e);
/*     */     } 
/* 151 */     if (format.getVersion() != 0) {
/* 152 */       throw new GeneralSecurityException("Only version 0 parameters are accepted");
/*     */     }
/* 154 */     return XAesGcmParameters.create(
/* 155 */         toVariant(serialization.getKeyTemplate().getOutputPrefixType()), format
/* 156 */         .getParams().getSaltSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static XAesGcmKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 163 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.XAesGcmKey")) {
/* 164 */       throw new IllegalArgumentException("Wrong type URL in call to XAesGcmProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 169 */       XAesGcmKey protoKey = XAesGcmKey.parseFrom(serialization
/* 170 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 171 */       if (protoKey.getVersion() != 0) {
/* 172 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 175 */       if (protoKey.getKeyValue().size() != 32) {
/* 176 */         throw new GeneralSecurityException("Only 32 byte key size is accepted");
/*     */       }
/* 178 */       return XAesGcmKey.create(
/* 179 */           XAesGcmParameters.create(
/* 180 */             toVariant(serialization.getOutputPrefixType()), protoKey.getParams().getSaltSize()), 
/* 181 */           SecretBytes.copyFrom(protoKey
/* 182 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)), serialization
/* 183 */           .getIdRequirementOrNull());
/* 184 */     } catch (InvalidProtocolBufferException e) {
/* 185 */       throw new GeneralSecurityException("Parsing XAesGcmKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 190 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 195 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 196 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 197 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 198 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(SerializationRegistry.Builder registryBuilder) throws GeneralSecurityException {
/* 203 */     registryBuilder.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 204 */     registryBuilder.registerParametersParser(PARAMETERS_PARSER);
/* 205 */     registryBuilder.registerKeySerializer(KEY_SERIALIZER);
/* 206 */     registryBuilder.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\XAesGcmProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */