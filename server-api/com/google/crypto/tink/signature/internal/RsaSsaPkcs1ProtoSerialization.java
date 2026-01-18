/*     */ package com.google.crypto.tink.signature.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.SecretKeyAccess;
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
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyTemplate;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1KeyFormat;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1Params;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
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
/*     */ @AccessesPartialKey
/*     */ public final class RsaSsaPkcs1ProtoSerialization
/*     */ {
/*     */   private static final String PRIVATE_TYPE_URL = "type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PrivateKey";
/*  57 */   private static final Bytes PRIVATE_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PrivateKey");
/*     */   
/*     */   private static final String PUBLIC_TYPE_URL = "type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PublicKey";
/*  60 */   private static final Bytes PUBLIC_TYPE_URL_BYTES = Util.toBytesFromPrintableAscii("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PublicKey");
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final ParametersSerializer<RsaSsaPkcs1Parameters, ProtoParametersSerialization> PARAMETERS_SERIALIZER = ParametersSerializer.create(RsaSsaPkcs1ProtoSerialization::serializeParameters, RsaSsaPkcs1Parameters.class, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final ParametersParser<ProtoParametersSerialization> PARAMETERS_PARSER = ParametersParser.create(RsaSsaPkcs1ProtoSerialization::parseParameters, PRIVATE_TYPE_URL_BYTES, ProtoParametersSerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final KeySerializer<RsaSsaPkcs1PublicKey, ProtoKeySerialization> PUBLIC_KEY_SERIALIZER = KeySerializer.create(RsaSsaPkcs1ProtoSerialization::serializePublicKey, RsaSsaPkcs1PublicKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private static final KeyParser<ProtoKeySerialization> PUBLIC_KEY_PARSER = KeyParser.create(RsaSsaPkcs1ProtoSerialization::parsePublicKey, PUBLIC_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final KeySerializer<RsaSsaPkcs1PrivateKey, ProtoKeySerialization> PRIVATE_KEY_SERIALIZER = KeySerializer.create(RsaSsaPkcs1ProtoSerialization::serializePrivateKey, RsaSsaPkcs1PrivateKey.class, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final KeyParser<ProtoKeySerialization> PRIVATE_KEY_PARSER = KeyParser.create(RsaSsaPkcs1ProtoSerialization::parsePrivateKey, PRIVATE_TYPE_URL_BYTES, ProtoKeySerialization.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private static final EnumTypeProtoConverter<OutputPrefixType, RsaSsaPkcs1Parameters.Variant> VARIANT_CONVERTER = EnumTypeProtoConverter.builder()
/* 104 */     .add((Enum)OutputPrefixType.RAW, RsaSsaPkcs1Parameters.Variant.NO_PREFIX)
/* 105 */     .add((Enum)OutputPrefixType.TINK, RsaSsaPkcs1Parameters.Variant.TINK)
/* 106 */     .add((Enum)OutputPrefixType.CRUNCHY, RsaSsaPkcs1Parameters.Variant.CRUNCHY)
/* 107 */     .add((Enum)OutputPrefixType.LEGACY, RsaSsaPkcs1Parameters.Variant.LEGACY)
/* 108 */     .build();
/*     */ 
/*     */ 
/*     */   
/* 112 */   private static final EnumTypeProtoConverter<HashType, RsaSsaPkcs1Parameters.HashType> HASH_TYPE_CONVERTER = EnumTypeProtoConverter.builder()
/* 113 */     .add((Enum)HashType.SHA256, RsaSsaPkcs1Parameters.HashType.SHA256)
/* 114 */     .add((Enum)HashType.SHA384, RsaSsaPkcs1Parameters.HashType.SHA384)
/* 115 */     .add((Enum)HashType.SHA512, RsaSsaPkcs1Parameters.HashType.SHA512)
/* 116 */     .build();
/*     */ 
/*     */   
/*     */   private static RsaSsaPkcs1Params getProtoParams(RsaSsaPkcs1Parameters parameters) throws GeneralSecurityException {
/* 120 */     return RsaSsaPkcs1Params.newBuilder()
/* 121 */       .setHashType((HashType)HASH_TYPE_CONVERTER.toProtoEnum(parameters.getHashType()))
/* 122 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ByteString encodeBigInteger(BigInteger i) {
/* 129 */     byte[] encoded = BigIntegerEncoding.toBigEndianBytes(i);
/* 130 */     return ByteString.copyFrom(encoded);
/*     */   }
/*     */ 
/*     */   
/*     */   private static RsaSsaPkcs1PublicKey getProtoPublicKey(RsaSsaPkcs1PublicKey key) throws GeneralSecurityException {
/* 135 */     return RsaSsaPkcs1PublicKey.newBuilder()
/* 136 */       .setParams(getProtoParams(key.getParameters()))
/* 137 */       .setN(encodeBigInteger(key.getModulus()))
/* 138 */       .setE(encodeBigInteger(key.getParameters().getPublicExponent()))
/* 139 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoParametersSerialization serializeParameters(RsaSsaPkcs1Parameters parameters) throws GeneralSecurityException {
/* 144 */     return ProtoParametersSerialization.create(
/* 145 */         KeyTemplate.newBuilder()
/* 146 */         .setTypeUrl("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PrivateKey")
/* 147 */         .setValue(
/* 148 */           RsaSsaPkcs1KeyFormat.newBuilder()
/* 149 */           .setParams(getProtoParams(parameters))
/* 150 */           .setModulusSizeInBits(parameters.getModulusSizeBits())
/* 151 */           .setPublicExponent(encodeBigInteger(parameters.getPublicExponent()))
/* 152 */           .build()
/* 153 */           .toByteString())
/* 154 */         .setOutputPrefixType((OutputPrefixType)VARIANT_CONVERTER.toProtoEnum(parameters.getVariant()))
/* 155 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePublicKey(RsaSsaPkcs1PublicKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 160 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PublicKey", 
/*     */         
/* 162 */         getProtoPublicKey(key).toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 164 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 165 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static ByteString encodeSecretBigInteger(SecretBigInteger i, SecretKeyAccess access) {
/* 169 */     return encodeBigInteger(i.getBigInteger(access));
/*     */   }
/*     */ 
/*     */   
/*     */   private static ProtoKeySerialization serializePrivateKey(RsaSsaPkcs1PrivateKey key, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 174 */     SecretKeyAccess a = SecretKeyAccess.requireAccess(access);
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
/* 185 */     RsaSsaPkcs1PrivateKey protoPrivateKey = RsaSsaPkcs1PrivateKey.newBuilder().setVersion(0).setPublicKey(getProtoPublicKey(key.getPublicKey())).setD(encodeSecretBigInteger(key.getPrivateExponent(), a)).setP(encodeSecretBigInteger(key.getPrimeP(), a)).setQ(encodeSecretBigInteger(key.getPrimeQ(), a)).setDp(encodeSecretBigInteger(key.getPrimeExponentP(), a)).setDq(encodeSecretBigInteger(key.getPrimeExponentQ(), a)).setCrt(encodeSecretBigInteger(key.getCrtCoefficient(), a)).build();
/* 186 */     return ProtoKeySerialization.create("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PrivateKey", protoPrivateKey
/*     */         
/* 188 */         .toByteString(), KeyData.KeyMaterialType.ASYMMETRIC_PRIVATE, (OutputPrefixType)VARIANT_CONVERTER
/*     */         
/* 190 */         .toProtoEnum(key.getParameters().getVariant()), key
/* 191 */         .getIdRequirementOrNull());
/*     */   }
/*     */   
/*     */   private static BigInteger decodeBigInteger(ByteString data) {
/* 195 */     return BigIntegerEncoding.fromUnsignedBigEndianBytes(data.toByteArray());
/*     */   }
/*     */   
/*     */   private static RsaSsaPkcs1Parameters parseParameters(ProtoParametersSerialization serialization) throws GeneralSecurityException {
/*     */     RsaSsaPkcs1KeyFormat format;
/* 200 */     if (!serialization.getKeyTemplate().getTypeUrl().equals("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PrivateKey")) {
/* 201 */       throw new IllegalArgumentException("Wrong type URL in call to RsaSsaPkcs1ProtoSerialization.parseParameters: " + serialization
/*     */           
/* 203 */           .getKeyTemplate().getTypeUrl());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 208 */       format = RsaSsaPkcs1KeyFormat.parseFrom(serialization
/* 209 */           .getKeyTemplate().getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 210 */     } catch (InvalidProtocolBufferException e) {
/* 211 */       throw new GeneralSecurityException("Parsing RsaSsaPkcs1Parameters failed: ", e);
/*     */     } 
/* 213 */     return RsaSsaPkcs1Parameters.builder()
/* 214 */       .setHashType((RsaSsaPkcs1Parameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)format.getParams().getHashType()))
/* 215 */       .setPublicExponent(decodeBigInteger(format.getPublicExponent()))
/* 216 */       .setModulusSizeBits(format.getModulusSizeInBits())
/* 217 */       .setVariant((RsaSsaPkcs1Parameters.Variant)VARIANT_CONVERTER
/* 218 */         .fromProtoEnum((Enum)serialization.getKeyTemplate().getOutputPrefixType()))
/* 219 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RsaSsaPkcs1PublicKey parsePublicKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 226 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PublicKey")) {
/* 227 */       throw new IllegalArgumentException("Wrong type URL in call to RsaSsaPkcs1ProtoSerialization.parsePublicKey: " + serialization
/*     */           
/* 229 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 233 */       RsaSsaPkcs1PublicKey protoKey = RsaSsaPkcs1PublicKey.parseFrom(serialization
/* 234 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 235 */       if (protoKey.getVersion() != 0) {
/* 236 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 238 */       BigInteger modulus = decodeBigInteger(protoKey.getN());
/* 239 */       int modulusSizeInBits = modulus.bitLength();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 246 */       RsaSsaPkcs1Parameters parameters = RsaSsaPkcs1Parameters.builder().setHashType((RsaSsaPkcs1Parameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)protoKey.getParams().getHashType())).setPublicExponent(decodeBigInteger(protoKey.getE())).setModulusSizeBits(modulusSizeInBits).setVariant((RsaSsaPkcs1Parameters.Variant)VARIANT_CONVERTER.fromProtoEnum((Enum)serialization.getOutputPrefixType())).build();
/* 247 */       return RsaSsaPkcs1PublicKey.builder()
/* 248 */         .setParameters(parameters)
/* 249 */         .setModulus(modulus)
/* 250 */         .setIdRequirement(serialization.getIdRequirementOrNull())
/* 251 */         .build();
/* 252 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 253 */       throw new GeneralSecurityException("Parsing RsaSsaPkcs1PublicKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static SecretBigInteger decodeSecretBigInteger(ByteString data, SecretKeyAccess access) {
/* 258 */     return SecretBigInteger.fromBigInteger(
/* 259 */         BigIntegerEncoding.fromUnsignedBigEndianBytes(data.toByteArray()), access);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RsaSsaPkcs1PrivateKey parsePrivateKey(ProtoKeySerialization serialization, @Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 266 */     if (!serialization.getTypeUrl().equals("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PrivateKey")) {
/* 267 */       throw new IllegalArgumentException("Wrong type URL in call to RsaSsaPkcs1ProtoSerialization.parsePrivateKey: " + serialization
/*     */           
/* 269 */           .getTypeUrl());
/*     */     }
/*     */     
/*     */     try {
/* 273 */       RsaSsaPkcs1PrivateKey protoKey = RsaSsaPkcs1PrivateKey.parseFrom(serialization
/* 274 */           .getValue(), ExtensionRegistryLite.getEmptyRegistry());
/* 275 */       if (protoKey.getVersion() != 0) {
/* 276 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/* 278 */       RsaSsaPkcs1PublicKey protoPublicKey = protoKey.getPublicKey();
/* 279 */       if (protoPublicKey.getVersion() != 0) {
/* 280 */         throw new GeneralSecurityException("Only version 0 keys are accepted");
/*     */       }
/*     */       
/* 283 */       BigInteger modulus = decodeBigInteger(protoPublicKey.getN());
/* 284 */       int modulusSizeInBits = modulus.bitLength();
/* 285 */       BigInteger publicExponent = decodeBigInteger(protoPublicKey.getE());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 293 */       RsaSsaPkcs1Parameters parameters = RsaSsaPkcs1Parameters.builder().setHashType((RsaSsaPkcs1Parameters.HashType)HASH_TYPE_CONVERTER.fromProtoEnum((Enum)protoPublicKey.getParams().getHashType())).setPublicExponent(publicExponent).setModulusSizeBits(modulusSizeInBits).setVariant((RsaSsaPkcs1Parameters.Variant)VARIANT_CONVERTER.fromProtoEnum((Enum)serialization.getOutputPrefixType())).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       RsaSsaPkcs1PublicKey publicKey = RsaSsaPkcs1PublicKey.builder().setParameters(parameters).setModulus(modulus).setIdRequirement(serialization.getIdRequirementOrNull()).build();
/*     */       
/* 301 */       SecretKeyAccess a = SecretKeyAccess.requireAccess(access);
/* 302 */       return RsaSsaPkcs1PrivateKey.builder()
/* 303 */         .setPublicKey(publicKey)
/* 304 */         .setPrimes(
/* 305 */           decodeSecretBigInteger(protoKey.getP(), a), 
/* 306 */           decodeSecretBigInteger(protoKey.getQ(), a))
/* 307 */         .setPrivateExponent(decodeSecretBigInteger(protoKey.getD(), a))
/* 308 */         .setPrimeExponents(
/* 309 */           decodeSecretBigInteger(protoKey.getDp(), a), 
/* 310 */           decodeSecretBigInteger(protoKey.getDq(), a))
/* 311 */         .setCrtCoefficient(decodeSecretBigInteger(protoKey.getCrt(), a))
/* 312 */         .build();
/* 313 */     } catch (InvalidProtocolBufferException|IllegalArgumentException e) {
/* 314 */       throw new GeneralSecurityException("Parsing RsaSsaPkcs1PrivateKey failed");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 319 */     register(MutableSerializationRegistry.globalInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(MutableSerializationRegistry registry) throws GeneralSecurityException {
/* 324 */     registry.registerParametersSerializer(PARAMETERS_SERIALIZER);
/* 325 */     registry.registerParametersParser(PARAMETERS_PARSER);
/* 326 */     registry.registerKeySerializer(PUBLIC_KEY_SERIALIZER);
/* 327 */     registry.registerKeyParser(PUBLIC_KEY_PARSER);
/* 328 */     registry.registerKeySerializer(PRIVATE_KEY_SERIALIZER);
/* 329 */     registry.registerKeyParser(PRIVATE_KEY_PARSER);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\internal\RsaSsaPkcs1ProtoSerialization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */