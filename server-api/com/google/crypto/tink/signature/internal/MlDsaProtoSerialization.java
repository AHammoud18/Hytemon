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
/*     */ import com.google.crypto.tink.proto.MlDsaInstance;
/*     */ import com.google.crypto.tink.proto.MlDsaKeyFormat;
/*     */ import com.google.crypto.tink.proto.MlDsaParams;
/*     */ import com.google.crypto.tink.proto.MlDsaPrivateKey;
/*     */ import com.google.crypto.tink.proto.MlDsaPublicKey;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.signature.MlDsaParameters;
/*     */ import com.google.crypto.tink.signature.MlDsaPrivateKey;
/*     */ import com.google.crypto.tink.signature.MlDsaPublicKey;
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
/*     */ public final class MlDsaProtoSerialization
/*     */ {
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.MlDsaPrivateKey";
/*  56 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.MlDsaPrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.MlDsaPublicKey";
/*  59 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.MlDsaPublicKey");
/*     */ 
/*     */ 
/*     */   
/*  63 */   private static final ParametersSerializer<MlDsaParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(MlDsaProtoSerialization::serializeParameters, MlDsaParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(MlDsaProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final KeySerializer<MlDsaPublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(MlDsaProtoSerialization::serializePublicKey, MlDsaPublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(MlDsaProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final KeySerializer<MlDsaPrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(MlDsaProtoSerialization::serializePrivateKey, MlDsaPrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(MlDsaProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static final EnumTypeProtoConverter<OutputPrefixType, MlDsaParameters.Variant> VARIANT_CONVERTER = EnumTypeProtoConverter.builder()
/* 102 */     .add((Enum)OutputPrefixType.RAW, MlDsaParameters.Variant.NO_PREFIX)
/* 103 */     .add((Enum)OutputPrefixType.TINK, MlDsaParameters.Variant.TINK)
/* 104 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   private static final EnumTypeProtoConverter<MlDsaInstance, MlDsaParameters.MlDsaInstance> INSTANCE_CONVERTER = EnumTypeProtoConverter.builder()
/* 111 */     .add((Enum)MlDsaInstance.ML_DSA_65, MlDsaParameters.MlDsaInstance.ML_DSA_65)
/*     */ 
/*     */     
/* 114 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 121 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 127 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 128 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 129 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 130 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 131 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 132 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ 
/*     */   
/*     */   private static MlDsaParams getProtoParams(MlDsaParameters parameters) throws GeneralSecurityException {
/* 137 */     return MlDsaParams.newBuilder()
/* 138 */       .setMlDsaInstance((MlDsaInstance)INSTANCE_CONVERTER.toProtoEnum(parameters.getMlDsaInstance()))
/* 139 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static MlDsaPublicKey getProtoPublicKey(MlDsaPublicKey key) throws GeneralSecurityException {
/* 144 */     return MlDsaPublicKey.newBuilder()
/* 145 */       .setVersion(0)
/* 146 */       .setParams(getProtoParams(key.getParameters()))
/* 147 */       .setKeyValue(ByteString.copyFrom(key.getSerializedPublicKey().toByteArray()))
/* 148 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(MlDsaParameters parameters) throws GeneralSecurityException {
/* 153 */     return ProtoParametersSerialization.create(
/* 154 */         KeyTemplate.newBuilder()
/* 155 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.MlDsaPrivateKey")
/* 156 */         .setValue(
/* 157 */           MlDsaKeyFormat.newBuilder()
/* 158 */           .setParams(getProtoParams(parameters))
/* 159 */           .setVersion(0)
/* 160 */           .build()
/* 161 */           .toByteString())
/* 162 */         .setOutputPrefixType((OutputPrefixType)VARIANT_CONVERTER.toProtoEnum(parameters.getVariant()))
/* 163 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(MlDsaPublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 174 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.MlDsaPublicKey", 
/*     */         
/* 176 */         getProtoPublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 178 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 179 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(MlDsaPrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 184 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.MlDsaPrivateKey", 
/*     */         
/* 186 */         MlDsaPrivateKey.newBuilder()
/* 187 */         .setVersion(0)
/* 188 */         .setPublicKey(getProtoPublicKey(key.getPublicKey()))
/* 189 */         .setKeyValue(
/* 190 */           ByteString.copyFrom(key
/* 191 */             .getPrivateSeed().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 192 */         .build()
/* 193 */         .toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 195 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 196 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static MlDsaParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     MlDsaKeyFormat format;
/* 201 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.MlDsaPrivateKey")) {
/* 202 */       throw new IllegalArgumentException("Wrong type URL in call to MlDsaProtoSerialization.parseParameters: " + serialization
/*     */           
/* 204 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 209 */       format = MlDsaKeyFormat.parseFrom(serialization
/* 210 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 211 */     } catch (InvalidProtocolBufferException e) {
/* 212 */       throw new GeneralSecurityException("Parsing MlDsaParameters failed: ", e);
/*     */     } 
/* 214 */     if (format.getVersion() != 0) {
/* 215 */       throw new GeneralSecurityException("Only version 0 keys are accepted for ML-DSA.");
/*     */     }
/* 217 */     return MlDsaParameters.create((MlDsaParameters.MlDsaInstance)INSTANCE_CONVERTER
/* 218 */         .fromProtoEnum((Enum)format.getParams().getMlDsaInstance()), (MlDsaParameters.Variant)VARIANT_CONVERTER
/* 219 */         .fromProtoEnum((Enum)serialization.getKeyTemplate().getOutputPrefixType()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MlDsaPublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 226 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.MlDsaPublicKey")) {
/* 227 */       throw new IllegalArgumentException("Wrong type URL in call to MlDsaProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 229 */           .getTypeUrl());
/*     */     }
/* 231 */     if (serialization.getKeyMaterialType() != KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC) {
/* 232 */       throw new GeneralSecurityException("Wrong KeyMaterialType for MlDsaPublicKey: " + serialization
/* 233 */           .getKeyMaterialType());
/*     */     }
/*     */     
/*     */     try {
/* 237 */       MlDsaPublicKey protoKey = MlDsaPublicKey.parseFrom(serialization
/* 238 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 239 */       if (protoKey.getVersion() != 0) {
/* 240 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 243 */       MlDsaParameters parameters = MlDsaParameters.create((MlDsaParameters.MlDsaInstance)INSTANCE_CONVERTER
/* 244 */           .fromProtoEnum((Enum)protoKey.getParams().getMlDsaInstance()), (MlDsaParameters.Variant)VARIANT_CONVERTER
/* 245 */           .fromProtoEnum((Enum)serialization.getOutputPrefixType()));
/*     */ 
/*     */ 
/*     */       
/* 249 */       MlDsaPublicKey.Builder builder = MlDsaPublicKey.builder().setParameters(parameters).setSerializedPublicKey(Bytes.copyFrom(protoKey.getKeyValue().toByteArray()));
/* 250 */       if (serialization.getIdRequirementOrNull() != null) {
/* 251 */         builder.setIdRequirement(serialization.getIdRequirementOrNull());
/*     */       }
/* 253 */       return builder.build();
/* 254 */     } catch (InvalidProtocolBufferException e) {
/* 255 */       throw new GeneralSecurityException("Parsing MlDsaPublicKey failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MlDsaPrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 263 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.MlDsaPrivateKey")) {
/* 264 */       throw new IllegalArgumentException("Wrong type URL in call to MlDsaProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 266 */           .getTypeUrl());
/*     */     }
/* 268 */     if (serialization.getKeyMaterialType() != KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE) {
/* 269 */       throw new GeneralSecurityException("Wrong KeyMaterialType for MlDsaPrivateKey: " + serialization
/* 270 */           .getKeyMaterialType());
/*     */     }
/*     */     
/*     */     try {
/* 274 */       MlDsaPrivateKey protoKey = MlDsaPrivateKey.parseFrom(serialization
/* 275 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 276 */       if (protoKey.getVersion() != 0) {
/* 277 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 279 */       MlDsaPublicKey protoPublicKey = protoKey.getPublicKey();
/* 280 */       if (protoPublicKey.getVersion() != 0) {
/* 281 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 284 */       MlDsaParameters parameters = MlDsaParameters.create((MlDsaParameters.MlDsaInstance)INSTANCE_CONVERTER
/* 285 */           .fromProtoEnum((Enum)protoPublicKey.getParams().getMlDsaInstance()), (MlDsaParameters.Variant)VARIANT_CONVERTER
/* 286 */           .fromProtoEnum((Enum)serialization.getOutputPrefixType()));
/*     */ 
/*     */ 
/*     */       
/* 290 */       MlDsaPublicKey.Builder builder = MlDsaPublicKey.builder().setParameters(parameters).setSerializedPublicKey(Bytes.copyFrom(protoPublicKey.getKeyValue().toByteArray()));
/* 291 */       if (serialization.getIdRequirementOrNull() != null) {
/* 292 */         builder.setIdRequirement(serialization.getIdRequirementOrNull());
/*     */       }
/* 294 */       MlDsaPublicKey publicKey = builder.build();
/*     */       
/* 296 */       return MlDsaPrivateKey.createWithoutVerification(publicKey, 
/*     */           
/* 298 */           SecretBytes.copyFrom(protoKey
/* 299 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)));
/* 300 */     } catch (InvalidProtocolBufferException e) {
/* 301 */       throw new GeneralSecurityException("Parsing MlDsaPrivateKey failed");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\MlDsaProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */