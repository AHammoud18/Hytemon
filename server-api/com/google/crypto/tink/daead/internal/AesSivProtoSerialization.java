/*     */ package com.google.crypto.tink.daead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.daead.AesSivKey;
/*     */ import com.google.crypto.tink.daead.AesSivParameters;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.AesSivKey;
/*     */ import com.google.crypto.tink.proto.AesSivKeyFormat;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class AesSivProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.AesSivKey";
/*  52 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.AesSivKey");
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final ParametersSerializer<AesSivParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(AesSivProtoSerialization::serializeParameters, AesSivParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(AesSivProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final KeySerializer<AesSivKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(AesSivProtoSerialization::serializeKey, AesSivKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(AesSivProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */   
/*     */   private static Map<AesSivParameters.Variant, OutputPrefixType> createVariantToOutputPrefixMap() {
/*  76 */     Map<AesSivParameters.Variant, OutputPrefixType> result = new HashMap<>();
/*  77 */     result.put(AesSivParameters.Variant.NO_PREFIX, OutputPrefixType.RAW);
/*  78 */     result.put(AesSivParameters.Variant.TINK, OutputPrefixType.TINK);
/*  79 */     result.put(AesSivParameters.Variant.CRUNCHY, OutputPrefixType.CRUNCHY);
/*  80 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   private static Map<OutputPrefixType, AesSivParameters.Variant> createOutputPrefixToVariantMap() {
/*  84 */     Map<OutputPrefixType, AesSivParameters.Variant> result = new EnumMap<>(OutputPrefixType.class);
/*  85 */     result.put(OutputPrefixType.RAW, AesSivParameters.Variant.NO_PREFIX);
/*  86 */     result.put(OutputPrefixType.TINK, AesSivParameters.Variant.TINK);
/*  87 */     result.put(OutputPrefixType.CRUNCHY, AesSivParameters.Variant.CRUNCHY);
/*     */     
/*  89 */     result.put(OutputPrefixType.LEGACY, AesSivParameters.Variant.CRUNCHY);
/*  90 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final Map<AesSivParameters.Variant, OutputPrefixType> variantsToOutputPrefixMap = createVariantToOutputPrefixMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static final Map<OutputPrefixType, AesSivParameters.Variant> outputPrefixToVariantMap = createOutputPrefixToVariantMap();
/*     */ 
/*     */   
/*     */   private static OutputPrefixType toProtoOutputPrefixType(AesSivParameters.Variant variant) throws GeneralSecurityException {
/* 105 */     if (variantsToOutputPrefixMap.containsKey(variant)) {
/* 106 */       return variantsToOutputPrefixMap.get(variant);
/*     */     }
/* 108 */     throw new GeneralSecurityException("Unable to serialize variant: " + variant);
/*     */   }
/*     */ 
/*     */   
/*     */   private static AesSivParameters.Variant toVariant(OutputPrefixType outputPrefixType) throws GeneralSecurityException {
/* 113 */     if (outputPrefixToVariantMap.containsKey(outputPrefixType)) {
/* 114 */       return outputPrefixToVariantMap.get(outputPrefixType);
/*     */     }
/* 116 */     throw new GeneralSecurityException("Unable to parse OutputPrefixType: " + outputPrefixType
/* 117 */         .getNumber());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(AesSivParameters parameters) throws GeneralSecurityException {
/* 122 */     return ProtoParametersSerialization.create(
/* 123 */         KeyTemplate.newBuilder()
/* 124 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.AesSivKey")
/* 125 */         .setValue(
/* 126 */           AesSivKeyFormat.newBuilder()
/* 127 */           .setKeySize(parameters.getKeySizeBytes())
/* 128 */           .build()
/* 129 */           .toByteString())
/* 130 */         .setOutputPrefixType(toProtoOutputPrefixType(parameters.getVariant()))
/* 131 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(AesSivKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 136 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.AesSivKey", 
/*     */         
/* 138 */         AesSivKey.newBuilder()
/* 139 */         .setKeyValue(
/* 140 */           ByteString.copyFrom(key
/* 141 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 142 */         .build()
/* 143 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, 
/*     */         
/* 145 */         toProtoOutputPrefixType(key.getParameters().getVariant()), key
/* 146 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static AesSivParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     AesSivKeyFormat format;
/* 151 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesSivKey")) {
/* 152 */       throw new IllegalArgumentException("Wrong type URL in call to AesSivParameters.parseParameters: " + serialization
/*     */           
/* 154 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 159 */       format = AesSivKeyFormat.parseFrom(serialization
/* 160 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 161 */       if (format.getVersion() != 0) {
/* 162 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 164 */     } catch (InvalidProtocolBufferException e) {
/* 165 */       throw new GeneralSecurityException("Parsing AesSivParameters failed: ", e);
/*     */     } 
/* 167 */     return AesSivParameters.builder()
/* 168 */       .setKeySizeBytes(format.getKeySize())
/* 169 */       .setVariant(toVariant(serialization.getKeyTemplate().getOutputPrefixType()))
/* 170 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AesSivKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 177 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.AesSivKey")) {
/* 178 */       throw new IllegalArgumentException("Wrong type URL in call to AesSivParameters.parseParameters");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 183 */       AesSivKey protoKey = AesSivKey.parseFrom(serialization
/* 184 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 185 */       if (protoKey.getVersion() != 0) {
/* 186 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       AesSivParameters parameters = AesSivParameters.builder().setKeySizeBytes(protoKey.getKeyValue().size()).setVariant(toVariant(serialization.getOutputPrefixType())).build();
/* 193 */       return AesSivKey.builder()
/* 194 */         .setParameters(parameters)
/* 195 */         .setKeyBytes(
/* 196 */           SecretBytes.copyFrom(protoKey
/* 197 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)))
/* 198 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 199 */         .build();
/* 200 */     } catch (InvalidProtocolBufferException e) {
/* 201 */       throw new GeneralSecurityException("Parsing AesSivKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 206 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 211 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 212 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 213 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 214 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\internal\AesSivProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */