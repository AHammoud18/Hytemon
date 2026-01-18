/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.XChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.aead.XChaCha20Poly1305Parameters;
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
/*     */ import com.google.crypto.tink.proto.XChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.proto.XChaCha20Poly1305KeyFormat;
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
/*     */ 
/*     */ @AccessesPartialKey
/*     */ public final class XChaCha20Poly1305ProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.XChaCha20Poly1305Key";
/*  53 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.XChaCha20Poly1305Key");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final ParametersSerializer<XChaCha20Poly1305Parameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(XChaCha20Poly1305ProtoSerialization::serializeParameters, XChaCha20Poly1305Parameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(XChaCha20Poly1305ProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final KeySerializer<XChaCha20Poly1305Key, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(XChaCha20Poly1305ProtoSerialization::serializeKey, XChaCha20Poly1305Key.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(XChaCha20Poly1305ProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(XChaCha20Poly1305Parameters.Variant variant) throws GeneralSecurityException {
/*  83 */     if (XChaCha20Poly1305Parameters.Variant.TINK.equals(variant)) {
/*  84 */       return OutputPrefixType.TINK;
/*     */     }
/*  86 */     if (XChaCha20Poly1305Parameters.Variant.CRUNCHY.equals(variant)) {
/*  87 */       return OutputPrefixType.CRUNCHY;
/*     */     }
/*  89 */     if (XChaCha20Poly1305Parameters.Variant.NO_PREFIX.equals(variant)) {
/*  90 */       return OutputPrefixType.RAW;
/*     */     }
/*  92 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static XChaCha20Poly1305Parameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  97 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  99 */         return XChaCha20Poly1305Parameters.Variant.TINK;
/*     */       
/*     */       case CRUNCHY:
/*     */       case LEGACY:
/* 103 */         return XChaCha20Poly1305Parameters.Variant.CRUNCHY;
/*     */       case RAW:
/* 105 */         return XChaCha20Poly1305Parameters.Variant.NO_PREFIX;
/*     */     } 
/* 107 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/* 108 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(XChaCha20Poly1305Parameters parameters) throws GeneralSecurityException {
/* 114 */     return ProtoParametersSerialization.create(
/* 115 */         KeyTemplate.newBuilder()
/* 116 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.XChaCha20Poly1305Key")
/* 117 */         .setValue(
/* 118 */           XChaCha20Poly1305KeyFormat.getDefaultInstance()
/* 119 */           .toByteString())
/* 120 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 121 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(XChaCha20Poly1305Key key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 126 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.XChaCha20Poly1305Key", 
/*     */         
/* 128 */         XChaCha20Poly1305Key.newBuilder()
/* 129 */         .setKeyValue(
/* 130 */           ByteString.copyFrom(key
/* 131 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 132 */         .build()
/* 133 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, 
/*     */         
/* 135 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 136 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static XChaCha20Poly1305Parameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     XChaCha20Poly1305KeyFormat format;
/* 141 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.XChaCha20Poly1305Key")) {
/* 142 */       throw new IllegalArgumentException("Wrong type URL in call to XChaCha20Poly1305ProtoSerialization.parseParameters: " + serialization
/*     */           
/* 144 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 149 */       format = XChaCha20Poly1305KeyFormat.parseFrom(serialization
/* 150 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 151 */     } catch (InvalidProtocolBufferException e) {
/* 152 */       throw new GeneralSecurityException("Parsing XChaCha20Poly1305Parameters failed: ", e);
/*     */     } 
/* 154 */     if (format.getVersion() != 0) {
/* 155 */       throw new GeneralSecurityException("Only version 0 parameters are accepted");
/*     */     }
/* 157 */     return XChaCha20Poly1305Parameters.create(
/* 158 */         toVariant(serialization.getKeyTemplate().getOutputPrefixType()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static XChaCha20Poly1305Key parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 165 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.XChaCha20Poly1305Key")) {
/* 166 */       throw new IllegalArgumentException("Wrong type URL in call to XChaCha20Poly1305ProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 171 */       XChaCha20Poly1305Key protoKey = XChaCha20Poly1305Key.parseFrom(serialization
/* 172 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 173 */       if (protoKey.getVersion() != 0) {
/* 174 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 176 */       return XChaCha20Poly1305Key.create(
/* 177 */           toVariant(serialization.getOutputPrefixType()), 
/* 178 */           SecretBytes.copyFrom(protoKey
/* 179 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)), serialization
/* 180 */           .getIdRequirementOrNull());
/* 181 */     } catch (InvalidProtocolBufferException e) {
/* 182 */       throw new GeneralSecurityException("Parsing XChaCha20Poly1305Key failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 187 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 192 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 193 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 194 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 195 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(SerializationRegistry.Builder registryBuilder) throws GeneralSecurityException {
/* 200 */     registryBuilder.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 201 */     registryBuilder.registerParametersParser(PARAMETERS_PARSER);
/* 202 */     registryBuilder.registerKeySerializer(KEY_SERIALIZER);
/* 203 */     registryBuilder.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\XChaCha20Poly1305ProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */