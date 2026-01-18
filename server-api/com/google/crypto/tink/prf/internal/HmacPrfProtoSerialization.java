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
/*     */ import com.google.crypto.tink.prf.HmacPrfKey;
/*     */ import com.google.crypto.tink.prf.HmacPrfParameters;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.HmacPrfKey;
/*     */ import com.google.crypto.tink.proto.HmacPrfKeyFormat;
/*     */ import com.google.crypto.tink.proto.HmacPrfParams;
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
/*     */ public final class HmacPrfProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.HmacPrfKey";
/*  51 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.HmacPrfKey");
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final ParametersSerializer<HmacPrfParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(HmacPrfProtoSerialization::serializeParameters, HmacPrfParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(HmacPrfProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final KeySerializer<HmacPrfKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(HmacPrfProtoSerialization::serializeKey, HmacPrfKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(HmacPrfProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static HashType toProtoHashType(HmacPrfParameters.HashType hashType) throws GeneralSecurityException {
/*  76 */     if (HmacPrfParameters.HashType.SHA1.equals(hashType)) {
/*  77 */       return HashType.SHA1;
/*     */     }
/*  79 */     if (HmacPrfParameters.HashType.SHA224.equals(hashType)) {
/*  80 */       return HashType.SHA224;
/*     */     }
/*  82 */     if (HmacPrfParameters.HashType.SHA256.equals(hashType)) {
/*  83 */       return HashType.SHA256;
/*     */     }
/*  85 */     if (HmacPrfParameters.HashType.SHA384.equals(hashType)) {
/*  86 */       return HashType.SHA384;
/*     */     }
/*  88 */     if (HmacPrfParameters.HashType.SHA512.equals(hashType)) {
/*  89 */       return HashType.SHA512;
/*     */     }
/*  91 */     throw new GeneralSecurityException("Unable to serialize HashType " + hashType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static HmacPrfParameters.HashType toHashType(HashType hashType) throws GeneralSecurityException {
/*  96 */     switch (hashType) {
/*     */       case SHA1:
/*  98 */         return HmacPrfParameters.HashType.SHA1;
/*     */       case SHA224:
/* 100 */         return HmacPrfParameters.HashType.SHA224;
/*     */       case SHA256:
/* 102 */         return HmacPrfParameters.HashType.SHA256;
/*     */       case SHA384:
/* 104 */         return HmacPrfParameters.HashType.SHA384;
/*     */       case SHA512:
/* 106 */         return HmacPrfParameters.HashType.SHA512;
/*     */     } 
/* 108 */     throw new GeneralSecurityException("Unable to parse HashType: " + hashType.getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static HmacPrfParams getProtoParams(HmacPrfParameters parameters) throws GeneralSecurityException {
/* 114 */     return HmacPrfParams.newBuilder()
/* 115 */       .setHash(toProtoHashType(parameters.getHashType()))
/* 116 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(HmacPrfParameters parameters) throws GeneralSecurityException {
/* 121 */     return ProtoParametersSerialization.create(
/* 122 */         KeyTemplate.newBuilder()
/* 123 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.HmacPrfKey")
/* 124 */         .setValue(
/* 125 */           HmacPrfKeyFormat.newBuilder()
/* 126 */           .setParams(getProtoParams(parameters))
/* 127 */           .setKeySize(parameters.getKeySizeBytes())
/* 128 */           .build()
/* 129 */           .toByteString())
/* 130 */         .setOutputPrefixType(OutputPrefixType.RAW)
/* 131 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(HmacPrfKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 136 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.HmacPrfKey", 
/*     */         
/* 138 */         HmacPrfKey.newBuilder()
/* 139 */         .setParams(getProtoParams(key.getParameters()))
/* 140 */         .setKeyValue(
/* 141 */           ByteString.copyFrom(key
/* 142 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 143 */         .build()
/* 144 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, OutputPrefixType.RAW, key
/*     */ 
/*     */         
/* 147 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static HmacPrfParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     HmacPrfKeyFormat format;
/* 152 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.HmacPrfKey")) {
/* 153 */       throw new IllegalArgumentException("Wrong type URL in call to HmacPrfProtoSerialization.parseParameters: " + serialization
/*     */           
/* 155 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 160 */       format = HmacPrfKeyFormat.parseFrom(serialization
/* 161 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 162 */     } catch (InvalidProtocolBufferException e) {
/* 163 */       throw new GeneralSecurityException("Parsing HmacPrfParameters failed: ", e);
/*     */     } 
/* 165 */     if (format.getVersion() != 0) {
/* 166 */       throw new GeneralSecurityException("Parsing HmacPrfParameters failed: unknown Version " + format
/* 167 */           .getVersion());
/*     */     }
/* 169 */     if (serialization.getKeyTemplate().getOutputPrefixType() != OutputPrefixType.RAW) {
/* 170 */       throw new GeneralSecurityException("Parsing HmacPrfParameters failed: only RAW output prefix type is accepted");
/*     */     }
/*     */     
/* 173 */     return HmacPrfParameters.builder()
/* 174 */       .setKeySizeBytes(format.getKeySize())
/* 175 */       .setHashType(toHashType(format.getParams().getHash()))
/* 176 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HmacPrfKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 183 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.HmacPrfKey")) {
/* 184 */       throw new IllegalArgumentException("Wrong type URL in call to HmacPrfProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 189 */       HmacPrfKey protoKey = HmacPrfKey.parseFrom(serialization
/* 190 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 191 */       if (protoKey.getVersion() != 0) {
/* 192 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 194 */       if (serialization.getIdRequirementOrNull() != null) {
/* 195 */         throw new GeneralSecurityException("ID requirement must be null.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       HmacPrfParameters parameters = HmacPrfParameters.builder().setKeySizeBytes(protoKey.getKeyValue().size()).setHashType(toHashType(protoKey.getParams().getHash())).build();
/* 202 */       return HmacPrfKey.builder()
/* 203 */         .setParameters(parameters)
/* 204 */         .setKeyBytes(
/* 205 */           SecretBytes.copyFrom(protoKey
/* 206 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)))
/* 207 */         .build();
/* 208 */     } catch (InvalidProtocolBufferException e) {
/* 209 */       throw new GeneralSecurityException("Parsing HmacPrfKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 214 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 219 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 220 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 221 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 222 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\internal\HmacPrfProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */