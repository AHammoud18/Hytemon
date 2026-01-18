/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.ChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.aead.ChaCha20Poly1305Parameters;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.ChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.proto.ChaCha20Poly1305KeyFormat;
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
/*     */ 
/*     */ @AccessesPartialKey
/*     */ public final class ChaCha20Poly1305ProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key";
/*  52 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private static final ParametersSerializer<ChaCha20Poly1305Parameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(ChaCha20Poly1305ProtoSerialization::serializeParameters, ChaCha20Poly1305Parameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(ChaCha20Poly1305ProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final KeySerializer<ChaCha20Poly1305Key, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(ChaCha20Poly1305ProtoSerialization::serializeKey, ChaCha20Poly1305Key.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(ChaCha20Poly1305ProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(ChaCha20Poly1305Parameters.Variant variant) throws GeneralSecurityException {
/*  82 */     if (ChaCha20Poly1305Parameters.Variant.TINK.equals(variant)) {
/*  83 */       return OutputPrefixType.TINK;
/*     */     }
/*  85 */     if (ChaCha20Poly1305Parameters.Variant.CRUNCHY.equals(variant)) {
/*  86 */       return OutputPrefixType.CRUNCHY;
/*     */     }
/*  88 */     if (ChaCha20Poly1305Parameters.Variant.NO_PREFIX.equals(variant)) {
/*  89 */       return OutputPrefixType.RAW;
/*     */     }
/*  91 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static ChaCha20Poly1305Parameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  96 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  98 */         return ChaCha20Poly1305Parameters.Variant.TINK;
/*     */       
/*     */       case CRUNCHY:
/*     */       case LEGACY:
/* 102 */         return ChaCha20Poly1305Parameters.Variant.CRUNCHY;
/*     */       case RAW:
/* 104 */         return ChaCha20Poly1305Parameters.Variant.NO_PREFIX;
/*     */     } 
/* 106 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/* 107 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(ChaCha20Poly1305Parameters parameters) throws GeneralSecurityException {
/* 113 */     return ProtoParametersSerialization.create(
/* 114 */         KeyTemplate.newBuilder()
/* 115 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key")
/* 116 */         .setValue(
/* 117 */           ChaCha20Poly1305KeyFormat.getDefaultInstance()
/* 118 */           .toByteString())
/* 119 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 120 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(ChaCha20Poly1305Key key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 125 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key", 
/*     */         
/* 127 */         ChaCha20Poly1305Key.newBuilder()
/* 128 */         .setKeyValue(
/* 129 */           ByteString.copyFrom(key
/* 130 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 131 */         .build()
/* 132 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, 
/*     */         
/* 134 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 135 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ChaCha20Poly1305Parameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/* 140 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key")) {
/* 141 */       throw new IllegalArgumentException("Wrong type URL in call to ChaCha20Poly1305ProtoSerialization.parseParameters: " + serialization
/*     */           
/* 143 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 150 */       ChaCha20Poly1305KeyFormat chaCha20Poly1305KeyFormat = ChaCha20Poly1305KeyFormat.parseFrom(serialization
/* 151 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 152 */     } catch (InvalidProtocolBufferException e) {
/* 153 */       throw new GeneralSecurityException("Parsing ChaCha20Poly1305Parameters failed: ", e);
/*     */     } 
/* 155 */     return ChaCha20Poly1305Parameters.create(
/* 156 */         toVariant(serialization.getKeyTemplate().getOutputPrefixType()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ChaCha20Poly1305Key parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 163 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key")) {
/* 164 */       throw new IllegalArgumentException("Wrong type URL in call to ChaCha20Poly1305ProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 169 */       ChaCha20Poly1305Key protoKey = ChaCha20Poly1305Key.parseFrom(serialization
/* 170 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 171 */       if (protoKey.getVersion() != 0) {
/* 172 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 174 */       return ChaCha20Poly1305Key.create(
/* 175 */           toVariant(serialization.getOutputPrefixType()), 
/* 176 */           SecretBytes.copyFrom(protoKey
/* 177 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)), serialization
/* 178 */           .getIdRequirementOrNull());
/* 179 */     } catch (InvalidProtocolBufferException e) {
/* 180 */       throw new GeneralSecurityException("Parsing ChaCha20Poly1305Key failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 185 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 190 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 191 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 192 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 193 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\ChaCha20Poly1305ProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */