/*     */ package com.google.crypto.tink.mac.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.internal.EnumTypeProtoConverter;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.mac.HmacKey;
/*     */ import com.google.crypto.tink.mac.HmacParameters;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.HmacKey;
/*     */ import com.google.crypto.tink.proto.HmacKeyFormat;
/*     */ import com.google.crypto.tink.proto.HmacParams;
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
/*     */ public final class HmacProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.HmacKey";
/*  50 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.HmacKey");
/*     */ 
/*     */   
/*  53 */   private static final EnumTypeProtoConverter<OutputPrefixType, HmacParameters.Variant> OUTPUT_PREFIX_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/*  54 */     .add((Enum)OutputPrefixType.RAW, HmacParameters.Variant.NO_PREFIX)
/*  55 */     .add((Enum)OutputPrefixType.TINK, HmacParameters.Variant.TINK)
/*  56 */     .add((Enum)OutputPrefixType.LEGACY, HmacParameters.Variant.LEGACY)
/*  57 */     .add((Enum)OutputPrefixType.CRUNCHY, HmacParameters.Variant.CRUNCHY)
/*  58 */     .build();
/*     */ 
/*     */   
/*  61 */   private static final EnumTypeProtoConverter<HashType, HmacParameters.HashType> HASH_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/*  62 */     .add((Enum)HashType.SHA1, HmacParameters.HashType.SHA1)
/*  63 */     .add((Enum)HashType.SHA224, HmacParameters.HashType.SHA224)
/*  64 */     .add((Enum)HashType.SHA256, HmacParameters.HashType.SHA256)
/*  65 */     .add((Enum)HashType.SHA384, HmacParameters.HashType.SHA384)
/*  66 */     .add((Enum)HashType.SHA512, HmacParameters.HashType.SHA512)
/*  67 */     .build();
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static final ParametersSerializer<HmacParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(HmacProtoSerialization::serializeParameters, HmacParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(HmacProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final KeySerializer<HmacKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(HmacProtoSerialization::serializeKey, HmacKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*  87 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(HmacProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static HmacParams getProtoParams(HmacParameters parameters) throws GeneralSecurityException {
/*  92 */     return HmacParams.newBuilder()
/*  93 */       .setTagSize(parameters.getCryptographicTagSizeBytes())
/*  94 */       .setHash((HashType)HASH_TYPE_CONVERTER.toProtoEnum(parameters.getHashType()))
/*  95 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(HmacParameters parameters) throws GeneralSecurityException {
/* 100 */     return ProtoParametersSerialization.create(
/* 101 */         KeyTemplate.newBuilder()
/* 102 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.HmacKey")
/* 103 */         .setValue(
/* 104 */           HmacKeyFormat.newBuilder()
/* 105 */           .setParams(getProtoParams(parameters))
/* 106 */           .setKeySize(parameters.getKeySizeBytes())
/* 107 */           .build()
/* 108 */           .toByteString())
/* 109 */         .setOutputPrefixType((OutputPrefixType)OUTPUT_PREFIX_TYPE_CONVERTER.toProtoEnum(parameters.getVariant()))
/* 110 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(HmacKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 115 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.HmacKey", 
/*     */         
/* 117 */         HmacKey.newBuilder()
/* 118 */         .setParams(getProtoParams(key.getParameters()))
/* 119 */         .setKeyValue(
/* 120 */           ByteString.copyFrom(key
/* 121 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 122 */         .build()
/* 123 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, (OutputPrefixType)OUTPUT_PREFIX_TYPE_CONVERTER
/*     */         
/* 125 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 126 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static HmacParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     HmacKeyFormat format;
/* 131 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.HmacKey")) {
/* 132 */       throw new IllegalArgumentException("Wrong type URL in call to HmacProtoSerialization.parseParameters: " + serialization
/*     */           
/* 134 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 139 */       format = HmacKeyFormat.parseFrom(serialization
/* 140 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 141 */     } catch (InvalidProtocolBufferException e) {
/* 142 */       throw new GeneralSecurityException("Parsing HmacParameters failed: ", e);
/*     */     } 
/* 144 */     if (format.getVersion() != 0) {
/* 145 */       throw new GeneralSecurityException("Parsing HmacParameters failed: unknown Version " + format
/* 146 */           .getVersion());
/*     */     }
/* 148 */     return HmacParameters.builder()
/* 149 */       .setKeySizeBytes(format.getKeySize())
/* 150 */       .setTagSizeBytes(format.getParams().getTagSize())
/* 151 */       .setHashType((HmacParameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)format.getParams().getHash()))
/* 152 */       .setVariant((HmacParameters.Variant)OUTPUT_PREFIX_TYPE_CONVERTER
/* 153 */         .fromProtoEnum((Enum)serialization
/* 154 */           .getKeyTemplate().getOutputPrefixType()))
/* 155 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HmacKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 162 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.HmacKey")) {
/* 163 */       throw new IllegalArgumentException("Wrong type URL in call to HmacProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 168 */       HmacKey protoKey = HmacKey.parseFrom(serialization
/* 169 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 170 */       if (protoKey.getVersion() != 0) {
/* 171 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 180 */       HmacParameters parameters = HmacParameters.builder().setKeySizeBytes(protoKey.getKeyValue().size()).setTagSizeBytes(protoKey.getParams().getTagSize()).setHashType((HmacParameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)protoKey.getParams().getHash())).setVariant((HmacParameters.Variant)OUTPUT_PREFIX_TYPE_CONVERTER.fromProtoEnum((Enum)serialization.getOutputPrefixType())).build();
/* 181 */       return HmacKey.builder()
/* 182 */         .setParameters(parameters)
/* 183 */         .setKeyBytes(
/* 184 */           SecretBytes.copyFrom(protoKey
/* 185 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)))
/* 186 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 187 */         .build();
/* 188 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 189 */       throw new GeneralSecurityException("Parsing HmacKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 194 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 199 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 200 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 201 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 202 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\internal\HmacProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */