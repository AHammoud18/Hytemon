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
/*     */ import com.google.crypto.tink.proto.Ed25519KeyFormat;
/*     */ import com.google.crypto.tink.proto.Ed25519PrivateKey;
/*     */ import com.google.crypto.tink.proto.Ed25519PublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.signature.Ed25519Parameters;
/*     */ import com.google.crypto.tink.signature.Ed25519PrivateKey;
/*     */ import com.google.crypto.tink.signature.Ed25519PublicKey;
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
/*     */ public final class Ed25519ProtoSerialization
/*     */ {
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.Ed25519PrivateKey";
/*  55 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.Ed25519PrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.Ed25519PublicKey";
/*  58 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.Ed25519PublicKey");
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final ParametersSerializer<Ed25519Parameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(Ed25519ProtoSerialization::serializeParameters, Ed25519Parameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(Ed25519ProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final KeySerializer<Ed25519PublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(Ed25519ProtoSerialization::serializePublicKey, Ed25519PublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(Ed25519ProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final KeySerializer<Ed25519PrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(Ed25519ProtoSerialization::serializePrivateKey, Ed25519PrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(Ed25519ProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static final EnumTypeProtoConverter<OutputPrefixType, Ed25519Parameters.Variant> VARIANT_CONVERTER = EnumTypeProtoConverter.builder()
/* 102 */     .add((Enum)OutputPrefixType.RAW, Ed25519Parameters.Variant.NO_PREFIX)
/* 103 */     .add((Enum)OutputPrefixType.TINK, Ed25519Parameters.Variant.TINK)
/* 104 */     .add((Enum)OutputPrefixType.CRUNCHY, Ed25519Parameters.Variant.CRUNCHY)
/* 105 */     .add((Enum)OutputPrefixType.LEGACY, Ed25519Parameters.Variant.LEGACY)
/* 106 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 113 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 119 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 120 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 121 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 122 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 123 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 124 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Ed25519PublicKey getProtoPublicKey(Ed25519PublicKey key) {
/* 129 */     return Ed25519PublicKey.newBuilder()
/* 130 */       .setKeyValue(ByteString.copyFrom(key.getPublicKeyBytes().toByteArray()))
/* 131 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(Ed25519Parameters parameters) throws GeneralSecurityException {
/* 136 */     return ProtoParametersSerialization.create(
/* 137 */         KeyTemplate.newBuilder()
/* 138 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.Ed25519PrivateKey")
/* 139 */         .setValue(Ed25519KeyFormat.getDefaultInstance().toByteString())
/* 140 */         .setOutputPrefixType((OutputPrefixType)VARIANT_CONVERTER.toProtoEnum(parameters.getVariant()))
/* 141 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(Ed25519PublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 152 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.Ed25519PublicKey", 
/*     */         
/* 154 */         getProtoPublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 156 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 157 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(Ed25519PrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 162 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.Ed25519PrivateKey", 
/*     */         
/* 164 */         Ed25519PrivateKey.newBuilder()
/* 165 */         .setPublicKey(getProtoPublicKey(key.getPublicKey()))
/* 166 */         .setKeyValue(
/* 167 */           ByteString.copyFrom(key
/* 168 */             .getPrivateKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 169 */         .build()
/* 170 */         .toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 172 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 173 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */   
/*     */   private static Ed25519Parameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/* 178 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.Ed25519PrivateKey")) {
/* 179 */       throw new IllegalArgumentException("Wrong type URL in call to Ed25519ProtoSerialization.parseParameters: " + serialization
/*     */           
/* 181 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 186 */       Ed25519KeyFormat format = Ed25519KeyFormat.parseFrom(serialization
/* 187 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 188 */       if (format.getVersion() != 0) {
/* 189 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 191 */     } catch (InvalidProtocolBufferException e) {
/* 192 */       throw new GeneralSecurityException("Parsing Ed25519Parameters failed: ", e);
/*     */     } 
/* 194 */     return Ed25519Parameters.create((Ed25519Parameters.Variant)VARIANT_CONVERTER
/* 195 */         .fromProtoEnum((Enum)serialization.getKeyTemplate().getOutputPrefixType()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Ed25519PublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 202 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.Ed25519PublicKey")) {
/* 203 */       throw new IllegalArgumentException("Wrong type URL in call to Ed25519ProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 205 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 209 */       Ed25519PublicKey protoKey = Ed25519PublicKey.parseFrom(serialization
/* 210 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 211 */       if (protoKey.getVersion() != 0) {
/* 212 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 215 */       return Ed25519PublicKey.create((Ed25519Parameters.Variant)VARIANT_CONVERTER
/* 216 */           .fromProtoEnum((Enum)serialization.getOutputPrefixType()), 
/* 217 */           Bytes.copyFrom(protoKey.getKeyValue().toByteArray()), serialization
/* 218 */           .getIdRequirementOrNull());
/* 219 */     } catch (InvalidProtocolBufferException e) {
/* 220 */       throw new GeneralSecurityException("Parsing Ed25519PublicKey failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Ed25519PrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 228 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.Ed25519PrivateKey")) {
/* 229 */       throw new IllegalArgumentException("Wrong type URL in call to Ed25519ProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 231 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 235 */       Ed25519PrivateKey protoKey = Ed25519PrivateKey.parseFrom(serialization
/* 236 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 237 */       if (protoKey.getVersion() != 0) {
/* 238 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 240 */       Ed25519PublicKey protoPublicKey = protoKey.getPublicKey();
/* 241 */       if (protoPublicKey.getVersion() != 0) {
/* 242 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 245 */       Ed25519PublicKey publicKey = Ed25519PublicKey.create((Ed25519Parameters.Variant)VARIANT_CONVERTER
/* 246 */           .fromProtoEnum((Enum)serialization.getOutputPrefixType()), 
/* 247 */           Bytes.copyFrom(protoPublicKey.getKeyValue().toByteArray()), serialization
/* 248 */           .getIdRequirementOrNull());
/*     */       
/* 250 */       return Ed25519PrivateKey.create(publicKey, 
/*     */           
/* 252 */           SecretBytes.copyFrom(protoKey
/* 253 */             .getKeyValue().toByteArray(), SecretKeyAccess.requireAccess(access)));
/* 254 */     } catch (InvalidProtocolBufferException e) {
/* 255 */       throw new GeneralSecurityException("Parsing Ed25519PrivateKey failed");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\Ed25519ProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */