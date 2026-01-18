/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.TinkProtoParametersFormat;
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
/*     */ import com.google.crypto.tink.proto.KmsEnvelopeAeadKey;
/*     */ import com.google.crypto.tink.proto.KmsEnvelopeAeadKeyFormat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LegacyKmsEnvelopeAeadProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey";
/*  50 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final ParametersSerializer<LegacyKmsEnvelopeAeadParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(LegacyKmsEnvelopeAeadProtoSerialization::serializeParameters, LegacyKmsEnvelopeAeadParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(LegacyKmsEnvelopeAeadProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final KeySerializer<LegacyKmsEnvelopeAeadKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(LegacyKmsEnvelopeAeadProtoSerialization::serializeKey, LegacyKmsEnvelopeAeadKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(LegacyKmsEnvelopeAeadProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(LegacyKmsEnvelopeAeadParameters.Variant variant) throws GeneralSecurityException {
/*  81 */     if (LegacyKmsEnvelopeAeadParameters.Variant.TINK.equals(variant)) {
/*  82 */       return OutputPrefixType.TINK;
/*     */     }
/*  84 */     if (LegacyKmsEnvelopeAeadParameters.Variant.NO_PREFIX.equals(variant)) {
/*  85 */       return OutputPrefixType.RAW;
/*     */     }
/*  87 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static LegacyKmsEnvelopeAeadParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*  92 */     switch (outputPrefixType) {
/*     */       case TINK:
/*  94 */         return LegacyKmsEnvelopeAeadParameters.Variant.TINK;
/*     */       case RAW:
/*  96 */         return LegacyKmsEnvelopeAeadParameters.Variant.NO_PREFIX;
/*     */     } 
/*  98 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/*  99 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static ProtoParametersSerialization serializeParameters(LegacyKmsEnvelopeAeadParameters parameters) throws GeneralSecurityException {
/* 106 */     return ProtoParametersSerialization.create(
/* 107 */         KeyTemplate.newBuilder()
/* 108 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey")
/* 109 */         .setValue(serializeParametersToKmsEnvelopeAeadKeyFormat(parameters).toByteString())
/* 110 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 111 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static KmsEnvelopeAeadKeyFormat serializeParametersToKmsEnvelopeAeadKeyFormat(LegacyKmsEnvelopeAeadParameters parameters) throws GeneralSecurityException {
/* 118 */     byte[] serializedDekParameters = TinkProtoParametersFormat.serialize(parameters.getDekParametersForNewKeys());
/*     */     
/*     */     try {
/* 121 */       KeyTemplate dekKeyTemplate = KeyTemplate.parseFrom(serializedDekParameters, ExtensionRegistryLite.getEmptyRegistry());
/* 122 */       return KmsEnvelopeAeadKeyFormat.newBuilder()
/* 123 */         .setKekUri(parameters.getKekUri())
/* 124 */         .setDekTemplate(dekKeyTemplate)
/* 125 */         .build();
/* 126 */     } catch (InvalidProtocolBufferException e) {
/* 127 */       throw new GeneralSecurityException("Parsing KmsEnvelopeAeadKeyFormat failed: ", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static ProtoKeySerialization serializeKey(LegacyKmsEnvelopeAeadKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 135 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey", 
/*     */         
/* 137 */         KmsEnvelopeAeadKey.newBuilder()
/* 138 */         .setParams(serializeParametersToKmsEnvelopeAeadKeyFormat(key.getParameters()))
/* 139 */         .build()
/* 140 */         .toByteString(), KeyData.KeyMaterialType.REMOTE, 
/*     */         
/* 142 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 143 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static LegacyKmsEnvelopeAeadParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     KmsEnvelopeAeadKeyFormat format;
/* 149 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey")) {
/* 150 */       throw new IllegalArgumentException("Wrong type URL in call to LegacyKmsEnvelopeAeadProtoSerialization.parseParameters: " + serialization
/*     */           
/* 152 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 157 */       format = KmsEnvelopeAeadKeyFormat.parseFrom(serialization
/* 158 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 159 */     } catch (InvalidProtocolBufferException e) {
/* 160 */       throw new GeneralSecurityException("Parsing KmsEnvelopeAeadKeyFormat failed: ", e);
/*     */     } 
/* 162 */     return parseParameters(format, serialization.getKeyTemplate().getOutputPrefixType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static LegacyKmsEnvelopeAeadParameters parseParameters(KmsEnvelopeAeadKeyFormat format, OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/*     */     LegacyKmsEnvelopeAeadParameters.DekParsingStrategy strategy;
/* 170 */     Parameters aeadParameters = TinkProtoParametersFormat.parse(
/* 171 */         KeyTemplate.newBuilder()
/* 172 */         .setTypeUrl(format.getDekTemplate().getTypeUrl())
/* 173 */         .setValue(format.getDekTemplate().getValue())
/* 174 */         .setOutputPrefixType(OutputPrefixType.RAW)
/* 175 */         .build()
/* 176 */         .toByteArray());
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (aeadParameters instanceof AesGcmParameters) {
/* 181 */       strategy = LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_GCM;
/* 182 */     } else if (aeadParameters instanceof ChaCha20Poly1305Parameters) {
/* 183 */       strategy = LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_CHACHA20POLY1305;
/* 184 */     } else if (aeadParameters instanceof XChaCha20Poly1305Parameters) {
/* 185 */       strategy = LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_XCHACHA20POLY1305;
/* 186 */     } else if (aeadParameters instanceof AesCtrHmacAeadParameters) {
/* 187 */       strategy = LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_CTR_HMAC;
/* 188 */     } else if (aeadParameters instanceof AesEaxParameters) {
/* 189 */       strategy = LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_EAX;
/* 190 */     } else if (aeadParameters instanceof AesGcmSivParameters) {
/* 191 */       strategy = LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_GCM_SIV;
/*     */     } else {
/* 193 */       throw new GeneralSecurityException("Unsupported DEK parameters when parsing " + aeadParameters);
/*     */     } 
/*     */     
/* 196 */     return LegacyKmsEnvelopeAeadParameters.builder()
/* 197 */       .setVariant(toVariant(outputPrefixType))
/* 198 */       .setKekUri(format.getKekUri())
/* 199 */       .setDekParametersForNewKeys((AeadParameters)aeadParameters)
/* 200 */       .setDekParsingStrategy(strategy)
/* 201 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static LegacyKmsEnvelopeAeadKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 208 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey")) {
/* 209 */       throw new IllegalArgumentException("Wrong type URL in call to LegacyKmsEnvelopeAeadProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 214 */       KmsEnvelopeAeadKey protoKey = KmsEnvelopeAeadKey.parseFrom(serialization
/* 215 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 216 */       if (protoKey.getVersion() != 0) {
/* 217 */         throw new GeneralSecurityException("KmsEnvelopeAeadKeys are only accepted with version 0, got " + protoKey);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 222 */       LegacyKmsEnvelopeAeadParameters parameters = parseParameters(protoKey.getParams(), serialization.getOutputPrefixType());
/* 223 */       return LegacyKmsEnvelopeAeadKey.create(parameters, serialization.getIdRequirementOrNull());
/* 224 */     } catch (InvalidProtocolBufferException e) {
/* 225 */       throw new GeneralSecurityException("Parsing KmsEnvelopeAeadKey failed: ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 230 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 235 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 236 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 237 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 238 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\LegacyKmsEnvelopeAeadProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */