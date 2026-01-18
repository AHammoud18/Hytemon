/*     */ package com.google.crypto.tink.signature.internal;
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
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.proto.SlhDsaHashType;
/*     */ import com.google.crypto.tink.proto.SlhDsaKeyFormat;
/*     */ import com.google.crypto.tink.proto.SlhDsaParams;
/*     */ import com.google.crypto.tink.proto.SlhDsaPrivateKey;
/*     */ import com.google.crypto.tink.proto.SlhDsaPublicKey;
/*     */ import com.google.crypto.tink.proto.SlhDsaSignatureType;
/*     */ import com.google.crypto.tink.signature.SlhDsaParameters;
/*     */ import com.google.crypto.tink.signature.SlhDsaPrivateKey;
/*     */ import com.google.crypto.tink.signature.SlhDsaPublicKey;
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
/*     */ @AccessesPartialKey
/*     */ public final class SlhDsaProtoSerialization
/*     */ {
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.SlhDsaPrivateKey";
/*  54 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.SlhDsaPrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.SlhDsaPublicKey";
/*  57 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.SlhDsaPublicKey");
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final ParametersSerializer<SlhDsaParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(SlhDsaProtoSerialization::serializeParameters, SlhDsaParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(SlhDsaProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final KeySerializer<SlhDsaPublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(SlhDsaProtoSerialization::serializePublicKey, SlhDsaPublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(SlhDsaProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private static final KeySerializer<SlhDsaPrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(SlhDsaProtoSerialization::serializePrivateKey, SlhDsaPrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(SlhDsaProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private static final EnumTypeProtoConverter<OutputPrefixType, SlhDsaParameters.Variant> VARIANT_CONVERTER = EnumTypeProtoConverter.builder()
/* 100 */     .add((Enum)OutputPrefixType.RAW, SlhDsaParameters.Variant.NO_PREFIX)
/* 101 */     .add((Enum)OutputPrefixType.TINK, SlhDsaParameters.Variant.TINK)
/* 102 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private static final EnumTypeProtoConverter<SlhDsaHashType, SlhDsaParameters.HashType> HASH_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 109 */     .add((Enum)SlhDsaHashType.SHA2, SlhDsaParameters.HashType.SHA2)
/* 110 */     .add((Enum)SlhDsaHashType.SHAKE, SlhDsaParameters.HashType.SHAKE)
/*     */ 
/*     */     
/* 113 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private static final EnumTypeProtoConverter<SlhDsaSignatureType, SlhDsaParameters.SignatureType> SIGNATURE_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 121 */     .add((Enum)SlhDsaSignatureType.FAST_SIGNING, SlhDsaParameters.SignatureType.FAST_SIGNING)
/*     */ 
/*     */     
/* 124 */     .add((Enum)SlhDsaSignatureType.SMALL_SIGNATURE, SlhDsaParameters.SignatureType.SMALL_SIGNATURE)
/*     */ 
/*     */     
/* 127 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 134 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 140 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 141 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 142 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 143 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 144 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 145 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ 
/*     */   
/*     */   private static SlhDsaParams getProtoParams(SlhDsaParameters parameters) throws GeneralSecurityException {
/* 150 */     return SlhDsaParams.newBuilder()
/* 151 */       .setKeySize(parameters.getPrivateKeySize())
/* 152 */       .setHashType((SlhDsaHashType)HASH_TYPE_CONVERTER.toProtoEnum(parameters.getHashType()))
/* 153 */       .setSigType((SlhDsaSignatureType)SIGNATURE_TYPE_CONVERTER.toProtoEnum(parameters.getSignatureType()))
/* 154 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static SlhDsaPublicKey getProtoPublicKey(SlhDsaPublicKey key) throws GeneralSecurityException {
/* 159 */     return SlhDsaPublicKey.newBuilder()
/* 160 */       .setVersion(0)
/* 161 */       .setParams(getProtoParams(key.getParameters()))
/* 162 */       .setKeyValue(ByteString.copyFrom(key.getSerializedPublicKey().toByteArray()))
/* 163 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(SlhDsaParameters parameters) throws GeneralSecurityException {
/* 168 */     return ProtoParametersSerialization.create(
/* 169 */         KeyTemplate.newBuilder()
/* 170 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.SlhDsaPrivateKey")
/* 171 */         .setValue(
/* 172 */           SlhDsaKeyFormat.newBuilder()
/* 173 */           .setParams(getProtoParams(parameters))
/* 174 */           .setVersion(0)
/* 175 */           .build()
/* 176 */           .toByteString())
/* 177 */         .setOutputPrefixType((OutputPrefixType)VARIANT_CONVERTER.toProtoEnum(parameters.getVariant()))
/* 178 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(SlhDsaPublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 189 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.SlhDsaPublicKey", 
/*     */         
/* 191 */         getProtoPublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 193 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 194 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(SlhDsaPrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 199 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.SlhDsaPrivateKey", 
/*     */         
/* 201 */         SlhDsaPrivateKey.newBuilder()
/* 202 */         .setVersion(0)
/* 203 */         .setPublicKey(getProtoPublicKey(key.getPublicKey()))
/* 204 */         .setKeyValue(
/* 205 */           ByteString.copyFrom(key
/* 206 */             .getPrivateKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 207 */         .build()
/* 208 */         .toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 210 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 211 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static SlhDsaParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     SlhDsaKeyFormat format;
/* 216 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.SlhDsaPrivateKey")) {
/* 217 */       throw new IllegalArgumentException("Wrong type URL in call to SlhDsaProtoSerialization.parseParameters: " + serialization
/*     */           
/* 219 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 224 */       format = SlhDsaKeyFormat.parseFrom(serialization
/* 225 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 226 */     } catch (InvalidProtocolBufferException e) {
/* 227 */       throw new GeneralSecurityException("Parsing SlhDsaParameters failed: ", e);
/*     */     } 
/* 229 */     if (format.getVersion() != 0) {
/* 230 */       throw new GeneralSecurityException("Only version 0 keys are accepted for SLH-DSA.");
/*     */     }
/* 232 */     return validateAndConvertToSlhDsaParameters(format
/* 233 */         .getParams(), (SlhDsaParameters.Variant)VARIANT_CONVERTER
/* 234 */         .fromProtoEnum((Enum)serialization.getKeyTemplate().getOutputPrefixType()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static SlhDsaParameters validateAndConvertToSlhDsaParameters(SlhDsaParams params, SlhDsaParameters.Variant variant) throws GeneralSecurityException {
/* 240 */     if (params.getKeySize() != 64 || params
/* 241 */       .getHashType() != SlhDsaHashType.SHA2 || params
/* 242 */       .getSigType() != SlhDsaSignatureType.SMALL_SIGNATURE)
/*     */     {
/* 244 */       throw new GeneralSecurityException("Unsupported SLH-DSA parameters");
/*     */     }
/* 246 */     return SlhDsaParameters.createSlhDsaWithSha2And128S(variant);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SlhDsaPublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 253 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.SlhDsaPublicKey")) {
/* 254 */       throw new IllegalArgumentException("Wrong type URL in call to SlhDsaProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 256 */           .getTypeUrl());
/*     */     }
/* 258 */     if (serialization.getKeyMaterialType() != KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC) {
/* 259 */       throw new GeneralSecurityException("Wrong KeyMaterialType for SlhDsaPublicKey: " + serialization
/*     */           
/* 261 */           .getKeyMaterialType().name());
/*     */     }
/*     */     try {
/* 264 */       return convertToSlhDsaPublicKey(serialization, 
/*     */           
/* 266 */           SlhDsaPublicKey.parseFrom(serialization
/* 267 */             .getValue(), ExtensionRegistryLite.getEmptyRegistry()));
/* 268 */     } catch (InvalidProtocolBufferException e) {
/* 269 */       throw new GeneralSecurityException("Parsing SlhDsaPublicKey failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static SlhDsaPublicKey convertToSlhDsaPublicKey(ProtoKeySerialization serialization, SlhDsaPublicKey protoKey) throws GeneralSecurityException {
/* 276 */     if (protoKey.getVersion() != 0) {
/* 277 */       throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */     }
/*     */     
/* 280 */     SlhDsaParameters parameters = validateAndConvertToSlhDsaParameters(protoKey
/* 281 */         .getParams(), (SlhDsaParameters.Variant)VARIANT_CONVERTER
/* 282 */         .fromProtoEnum((Enum)serialization.getOutputPrefixType()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     SlhDsaPublicKey.Builder builder = SlhDsaPublicKey.builder().setParameters(parameters).setSerializedPublicKey(Bytes.copyFrom(protoKey.getKeyValue().toByteArray()));
/* 288 */     if (serialization.getIdRequirementOrNull() != null) {
/* 289 */       builder.setIdRequirement(serialization.getIdRequirementOrNull());
/*     */     }
/* 291 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SlhDsaPrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 298 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.SlhDsaPrivateKey")) {
/* 299 */       throw new IllegalArgumentException("Wrong type URL in call to SlhDsaProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 301 */           .getTypeUrl());
/*     */     }
/* 303 */     if (serialization.getKeyMaterialType() != KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE) {
/* 304 */       throw new GeneralSecurityException("Wrong KeyMaterialType for SlhDsaPrivateKey: " + serialization
/*     */           
/* 306 */           .getKeyMaterialType().name());
/*     */     }
/*     */     
/*     */     try {
/* 310 */       SlhDsaPrivateKey protoKey = SlhDsaPrivateKey.parseFrom(serialization
/* 311 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 312 */       if (protoKey.getVersion() != 0) {
/* 313 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 316 */       SlhDsaPublicKey publicKey = convertToSlhDsaPublicKey(serialization, protoKey.getPublicKey());
/*     */       
/* 318 */       return SlhDsaPrivateKey.createWithoutVerification(publicKey, 
/*     */           
/* 320 */           SecretBytes.copyFrom(protoKey
/* 321 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)));
/* 322 */     } catch (InvalidProtocolBufferException e) {
/* 323 */       throw new GeneralSecurityException("Parsing SlhDsaPrivateKey failed");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\SlhDsaProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */