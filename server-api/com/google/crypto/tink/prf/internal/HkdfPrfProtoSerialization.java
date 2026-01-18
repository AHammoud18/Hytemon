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
/*     */ import com.google.crypto.tink.prf.HkdfPrfKey;
/*     */ import com.google.crypto.tink.prf.HkdfPrfParameters;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.HkdfPrfKey;
/*     */ import com.google.crypto.tink.proto.HkdfPrfKeyFormat;
/*     */ import com.google.crypto.tink.proto.HkdfPrfParams;
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
/*     */ public final class HkdfPrfProtoSerialization
/*     */ {
/*     */   private static final String TYPE_URL = "type.googleapis.com/google.crypto.tink.HkdfPrfKey";
/*  51 */   private static final Bytes TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.HkdfPrfKey");
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final ParametersSerializer<HkdfPrfParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(HkdfPrfProtoSerialization::serializeParameters, HkdfPrfParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(HkdfPrfProtoSerialization::parseParameters, TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final KeySerializer<HkdfPrfKey, ProtoKeySerialization> KEY_SERIALIZER = KeySerializer.create(HkdfPrfProtoSerialization::serializeKey, HkdfPrfKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static final KeyParser<ProtoKeySerialization> KEY_PARSER = KeyParser.create(HkdfPrfProtoSerialization::parseKey, TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static HashType toProtoHashType(HkdfPrfParameters.HashType hashType) throws GeneralSecurityException {
/*  76 */     if (HkdfPrfParameters.HashType.SHA1.equals(hashType)) {
/*  77 */       return HashType.SHA1;
/*     */     }
/*  79 */     if (HkdfPrfParameters.HashType.SHA224.equals(hashType)) {
/*  80 */       return HashType.SHA224;
/*     */     }
/*  82 */     if (HkdfPrfParameters.HashType.SHA256.equals(hashType)) {
/*  83 */       return HashType.SHA256;
/*     */     }
/*  85 */     if (HkdfPrfParameters.HashType.SHA384.equals(hashType)) {
/*  86 */       return HashType.SHA384;
/*     */     }
/*  88 */     if (HkdfPrfParameters.HashType.SHA512.equals(hashType)) {
/*  89 */       return HashType.SHA512;
/*     */     }
/*  91 */     throw new GeneralSecurityException("Unable to serialize HashType " + hashType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static HkdfPrfParameters.HashType toHashType(HashType hashType) throws GeneralSecurityException {
/*  96 */     switch (hashType) {
/*     */       case SHA1:
/*  98 */         return HkdfPrfParameters.HashType.SHA1;
/*     */       case SHA224:
/* 100 */         return HkdfPrfParameters.HashType.SHA224;
/*     */       case SHA256:
/* 102 */         return HkdfPrfParameters.HashType.SHA256;
/*     */       case SHA384:
/* 104 */         return HkdfPrfParameters.HashType.SHA384;
/*     */       case SHA512:
/* 106 */         return HkdfPrfParameters.HashType.SHA512;
/*     */     } 
/* 108 */     throw new GeneralSecurityException("Unable to parse HashType: " + hashType
/* 109 */         .getNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HkdfPrfParams getProtoParams(HkdfPrfParameters parameters) throws GeneralSecurityException {
/* 117 */     HkdfPrfParams.Builder builder = HkdfPrfParams.newBuilder().setHash(toProtoHashType(parameters.getHashType()));
/* 118 */     if (parameters.getSalt() != null && parameters.getSalt().size() > 0) {
/* 119 */       builder.setSalt(ByteString.copyFrom(parameters.getSalt().toByteArray()));
/*     */     }
/* 121 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(HkdfPrfParameters parameters) throws GeneralSecurityException {
/* 126 */     return ProtoParametersSerialization.create(
/* 127 */         KeyTemplate.newBuilder()
/* 128 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.HkdfPrfKey")
/* 129 */         .setValue(
/* 130 */           HkdfPrfKeyFormat.newBuilder()
/* 131 */           .setParams(getProtoParams(parameters))
/* 132 */           .setKeySize(parameters.getKeySizeBytes())
/* 133 */           .build()
/* 134 */           .toByteString())
/* 135 */         .setOutputPrefixType(OutputPrefixType.RAW)
/* 136 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializeKey(HkdfPrfKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 141 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.HkdfPrfKey", 
/*     */         
/* 143 */         HkdfPrfKey.newBuilder()
/* 144 */         .setParams(getProtoParams(key.getParameters()))
/* 145 */         .setKeyValue(
/* 146 */           ByteString.copyFrom(key
/* 147 */             .getKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 148 */         .build()
/* 149 */         .toByteString(), KeyData.KeyMaterialType.SYMMETRIC, OutputPrefixType.RAW, key
/*     */ 
/*     */         
/* 152 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static HkdfPrfParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     HkdfPrfKeyFormat format;
/* 157 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.HkdfPrfKey")) {
/* 158 */       throw new IllegalArgumentException("Wrong type URL in call to HkdfPrfProtoSerialization.parseParameters: " + serialization
/*     */           
/* 160 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 165 */       format = HkdfPrfKeyFormat.parseFrom(serialization
/* 166 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 167 */     } catch (InvalidProtocolBufferException e) {
/* 168 */       throw new GeneralSecurityException("Parsing HkdfPrfParameters failed: ", e);
/*     */     } 
/* 170 */     if (format.getVersion() != 0) {
/* 171 */       throw new GeneralSecurityException("Parsing HkdfPrfParameters failed: unknown Version " + format
/* 172 */           .getVersion());
/*     */     }
/* 174 */     if (serialization.getKeyTemplate().getOutputPrefixType() != OutputPrefixType.RAW) {
/* 175 */       throw new GeneralSecurityException("Parsing HkdfPrfParameters failed: only RAW output prefix type is accepted");
/*     */     }
/*     */     
/* 178 */     return HkdfPrfParameters.builder()
/* 179 */       .setKeySizeBytes(format.getKeySize())
/* 180 */       .setHashType(toHashType(format.getParams().getHash()))
/* 181 */       .setSalt(Bytes.copyFrom(format.getParams().getSalt().toByteArray()))
/* 182 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HkdfPrfKey parseKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 189 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.HkdfPrfKey")) {
/* 190 */       throw new IllegalArgumentException("Wrong type URL in call to HkdfPrfProtoSerialization.parseKey");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 195 */       HkdfPrfKey protoKey = HkdfPrfKey.parseFrom(serialization
/* 196 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 197 */       if (protoKey.getVersion() != 0) {
/* 198 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 200 */       if (serialization.getIdRequirementOrNull() != null) {
/* 201 */         throw new GeneralSecurityException("ID requirement must be null.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 208 */       HkdfPrfParameters parameters = HkdfPrfParameters.builder().setKeySizeBytes(protoKey.getKeyValue().size()).setHashType(toHashType(protoKey.getParams().getHash())).setSalt(Bytes.copyFrom(protoKey.getParams().getSalt().toByteArray())).build();
/* 209 */       return HkdfPrfKey.builder()
/* 210 */         .setParameters(parameters)
/* 211 */         .setKeyBytes(
/* 212 */           SecretBytes.copyFrom(protoKey
/* 213 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)))
/* 214 */         .build();
/* 215 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 216 */       throw new GeneralSecurityException("Parsing HkdfPrfKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 221 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 226 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 227 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 228 */     registry.registerKeySerializer(KEY_SERIALIZER);
/* 229 */     registry.registerKeyParser(KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\internal\HkdfPrfProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */