/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
/*     */ import com.google.crypto.tink.hybrid.internal.HpkeUtil;
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.internal.EnumTypeProtoConverter;
/*     */ import com.google.crypto.tink.internal.KeyParser;
/*     */ import com.google.crypto.tink.internal.KeySerializer;
/*     */ import com.google.crypto.tink.internal.MutableSerializationRegistry;
/*     */ import com.google.crypto.tink.internal.ParametersParser;
/*     */ import com.google.crypto.tink.internal.ParametersSerializer;
/*     */ import com.google.crypto.tink.internal.ProtoKeySerialization;
/*     */ import com.google.crypto.tink.internal.ProtoParametersSerialization;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.proto.HpkeAead;
/*     */ import com.google.crypto.tink.proto.HpkeKdf;
/*     */ import com.google.crypto.tink.proto.HpkeKem;
/*     */ import com.google.crypto.tink.proto.HpkeKeyFormat;
/*     */ import com.google.crypto.tink.proto.HpkeParams;
/*     */ import com.google.crypto.tink.proto.HpkePrivateKey;
/*     */ import com.google.crypto.tink.proto.HpkePublicKey;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.protobuf.ByteString;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.InvalidProtocolBufferException;
/*     */ import java.math.BigInteger;
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
/*     */ public final class HpkeProtoSerialization
/*     */ {
/*     */   private static final int VERSION = 0;
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.HpkePrivateKey";
/*  60 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.HpkePrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.HpkePublicKey";
/*  63 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.HpkePublicKey");
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final ParametersSerializer<HpkeParameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(HpkeProtoSerialization::serializeParameters, HpkeParameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(HpkeProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private static final KeySerializer<HpkePublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(HpkeProtoSerialization::serializePublicKey, HpkePublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(HpkeProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private static final KeySerializer<HpkePrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(HpkeProtoSerialization::serializePrivateKey, HpkePrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(HpkeProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private static final EnumTypeProtoConverter<OutputPrefixType, HpkeParameters.Variant> VARIANT_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 105 */     .add((Enum)OutputPrefixType.RAW, HpkeParameters.Variant.NO_PREFIX)
/* 106 */     .add((Enum)OutputPrefixType.TINK, HpkeParameters.Variant.TINK)
/* 107 */     .add((Enum)OutputPrefixType.LEGACY, HpkeParameters.Variant.CRUNCHY)
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     .add((Enum)OutputPrefixType.CRUNCHY, HpkeParameters.Variant.CRUNCHY)
/* 113 */     .build();
/*     */ 
/*     */   
/* 116 */   private static final EnumTypeProtoConverter<HpkeKem, HpkeParameters.KemId> KEM_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 117 */     .add((Enum)HpkeKem.DHKEM_P256_HKDF_SHA256, HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)
/* 118 */     .add((Enum)HpkeKem.DHKEM_P384_HKDF_SHA384, HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384)
/* 119 */     .add((Enum)HpkeKem.DHKEM_P521_HKDF_SHA512, HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512)
/* 120 */     .add((Enum)HpkeKem.DHKEM_X25519_HKDF_SHA256, HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)
/* 121 */     .build();
/*     */ 
/*     */   
/* 124 */   private static final EnumTypeProtoConverter<HpkeKdf, HpkeParameters.KdfId> KDF_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 125 */     .add((Enum)HpkeKdf.HKDF_SHA256, HpkeParameters.KdfId.HKDF_SHA256)
/* 126 */     .add((Enum)HpkeKdf.HKDF_SHA384, HpkeParameters.KdfId.HKDF_SHA384)
/* 127 */     .add((Enum)HpkeKdf.HKDF_SHA512, HpkeParameters.KdfId.HKDF_SHA512)
/* 128 */     .build();
/*     */ 
/*     */   
/* 131 */   private static final EnumTypeProtoConverter<HpkeAead, HpkeParameters.AeadId> AEAD_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 132 */     .add((Enum)HpkeAead.AES_128_GCM, HpkeParameters.AeadId.AES_128_GCM)
/* 133 */     .add((Enum)HpkeAead.AES_256_GCM, HpkeParameters.AeadId.AES_256_GCM)
/* 134 */     .add((Enum)HpkeAead.CHACHA20_POLY1305, HpkeParameters.AeadId.CHACHA20_POLY1305)
/* 135 */     .build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 142 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 148 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 149 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 150 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 151 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 152 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 153 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ 
/*     */   
/*     */   private static HpkeParams toProtoParameters(HpkeParameters params) throws GeneralSecurityException {
/* 158 */     return HpkeParams.newBuilder()
/* 159 */       .setKem((HpkeKem)KEM_TYPE_CONVERTER.toProtoEnum(params.getKemId()))
/* 160 */       .setKdf((HpkeKdf)KDF_TYPE_CONVERTER.toProtoEnum(params.getKdfId()))
/* 161 */       .setAead((HpkeAead)AEAD_TYPE_CONVERTER.toProtoEnum(params.getAeadId()))
/* 162 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static HpkePublicKey toProtoPublicKey(HpkePublicKey key) throws GeneralSecurityException {
/* 167 */     return HpkePublicKey.newBuilder()
/* 168 */       .setVersion(0)
/* 169 */       .setParams(toProtoParameters(key.getParameters()))
/* 170 */       .setPublicKey(ByteString.copyFrom(key.getPublicKeyBytes().toByteArray()))
/* 171 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static HpkePrivateKey toProtoPrivateKey(HpkePrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 176 */     return HpkePrivateKey.newBuilder()
/* 177 */       .setVersion(0)
/* 178 */       .setPublicKey(toProtoPublicKey(key.getPublicKey()))
/* 179 */       .setPrivateKey(
/* 180 */         ByteString.copyFrom(key
/* 181 */           .getPrivateKeyBytes().toByteArray(SecretKeyAccess.requireAccess(access))))
/* 182 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static HpkeParameters fromProtoParameters(OutputPrefixType outputPrefixType, HpkeParams protoParams) throws GeneralSecurityException {
/* 187 */     return HpkeParameters.builder()
/* 188 */       .setVariant((HpkeParameters.Variant)VARIANT_TYPE_CONVERTER.fromProtoEnum((Enum)outputPrefixType))
/* 189 */       .setKemId((HpkeParameters.KemId)KEM_TYPE_CONVERTER.fromProtoEnum((Enum)protoParams.getKem()))
/* 190 */       .setKdfId((HpkeParameters.KdfId)KDF_TYPE_CONVERTER.fromProtoEnum((Enum)protoParams.getKdf()))
/* 191 */       .setAeadId((HpkeParameters.AeadId)AEAD_TYPE_CONVERTER.fromProtoEnum((Enum)protoParams.getAead()))
/* 192 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(HpkeParameters parameters) throws GeneralSecurityException {
/* 197 */     return ProtoParametersSerialization.create(
/* 198 */         KeyTemplate.newBuilder()
/* 199 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.HpkePrivateKey")
/* 200 */         .setValue(
/* 201 */           HpkeKeyFormat.newBuilder()
/* 202 */           .setParams(toProtoParameters(parameters))
/* 203 */           .build()
/* 204 */           .toByteString())
/* 205 */         .setOutputPrefixType((OutputPrefixType)VARIANT_TYPE_CONVERTER.toProtoEnum(parameters.getVariant()))
/* 206 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(HpkePublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 217 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.HpkePublicKey", 
/*     */         
/* 219 */         toProtoPublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, (OutputPrefixType)VARIANT_TYPE_CONVERTER
/*     */         
/* 221 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 222 */         .getIdRequirementOrNull());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(HpkePrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 227 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.HpkePrivateKey", 
/*     */         
/* 229 */         toProtoPrivateKey(key, access).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, (OutputPrefixType)VARIANT_TYPE_CONVERTER
/*     */         
/* 231 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 232 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static HpkeParameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     HpkeKeyFormat format;
/* 237 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.HpkePrivateKey")) {
/* 238 */       throw new IllegalArgumentException("Wrong type URL in call to HpkeProtoSerialization.parseParameters: " + serialization
/*     */           
/* 240 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 245 */       format = HpkeKeyFormat.parseFrom(serialization
/* 246 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 247 */     } catch (InvalidProtocolBufferException e) {
/* 248 */       throw new GeneralSecurityException("Parsing HpkeParameters failed: ", e);
/*     */     } 
/* 250 */     return fromProtoParameters(serialization
/* 251 */         .getKeyTemplate().getOutputPrefixType(), format.getParams());
/*     */   }
/*     */ 
/*     */   
/*     */   private static Bytes encodePublicKeyBytes(HpkeParameters.KemId kemId, byte[] publicKeyBytes) throws GeneralSecurityException {
/* 256 */     BigInteger n = BigIntegerEncoding.fromUnsignedBigEndianBytes(publicKeyBytes);
/*     */     
/* 258 */     byte[] encodedPublicKeyBytes = BigIntegerEncoding.toBigEndianBytesOfFixedLength(n, 
/* 259 */         HpkeUtil.getEncodedPublicKeyLength(kemId));
/* 260 */     return Bytes.copyFrom(encodedPublicKeyBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HpkePublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 267 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.HpkePublicKey")) {
/* 268 */       throw new IllegalArgumentException("Wrong type URL in call to HpkeProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 270 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 274 */       HpkePublicKey protoKey = HpkePublicKey.parseFrom(serialization
/* 275 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 276 */       if (protoKey.getVersion() != 0) {
/* 277 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */ 
/*     */       
/* 281 */       HpkeParameters params = fromProtoParameters(serialization.getOutputPrefixType(), protoKey.getParams());
/* 282 */       return HpkePublicKey.create(params, 
/*     */           
/* 284 */           encodePublicKeyBytes(params.getKemId(), protoKey.getPublicKey().toByteArray()), serialization
/* 285 */           .getIdRequirementOrNull());
/* 286 */     } catch (InvalidProtocolBufferException e) {
/* 287 */       throw new GeneralSecurityException("Parsing HpkePublicKey failed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static SecretBytes encodePrivateKeyBytes(HpkeParameters.KemId kemId, byte[] privateKeyBytes, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 294 */     BigInteger n = BigIntegerEncoding.fromUnsignedBigEndianBytes(privateKeyBytes);
/*     */     
/* 296 */     byte[] encodedPrivateKeyBytes = BigIntegerEncoding.toBigEndianBytesOfFixedLength(n, 
/* 297 */         HpkeUtil.getEncodedPrivateKeyLength(kemId));
/* 298 */     return SecretBytes.copyFrom(encodedPrivateKeyBytes, SecretKeyAccess.requireAccess(access));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static HpkePrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 305 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.HpkePrivateKey")) {
/* 306 */       throw new IllegalArgumentException("Wrong type URL in call to HpkeProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 308 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 312 */       HpkePrivateKey protoKey = HpkePrivateKey.parseFrom(serialization
/* 313 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 314 */       if (protoKey.getVersion() != 0) {
/* 315 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 317 */       HpkePublicKey protoPublicKey = protoKey.getPublicKey();
/* 318 */       if (protoPublicKey.getVersion() != 0) {
/* 319 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 322 */       HpkeParameters params = fromProtoParameters(serialization.getOutputPrefixType(), protoPublicKey.getParams());
/*     */       
/* 324 */       HpkePublicKey publicKey = HpkePublicKey.create(params, 
/*     */           
/* 326 */           encodePublicKeyBytes(params.getKemId(), protoPublicKey.getPublicKey().toByteArray()), serialization
/* 327 */           .getIdRequirementOrNull());
/* 328 */       return HpkePrivateKey.create(publicKey, 
/*     */           
/* 330 */           encodePrivateKeyBytes(params.getKemId(), protoKey.getPrivateKey().toByteArray(), access));
/* 331 */     } catch (InvalidProtocolBufferException e) {
/* 332 */       throw new GeneralSecurityException("Parsing HpkePrivateKey failed");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HpkeProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */