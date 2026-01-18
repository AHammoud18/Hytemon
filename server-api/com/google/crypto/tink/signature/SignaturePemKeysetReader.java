/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.KeysetReader;
/*     */ import com.google.crypto.tink.PemKeyType;
/*     */ import com.google.crypto.tink.proto.EcdsaParams;
/*     */ import com.google.crypto.tink.proto.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.proto.EcdsaSignatureEncoding;
/*     */ import com.google.crypto.tink.proto.EllipticCurveType;
/*     */ import com.google.crypto.tink.proto.EncryptedKeyset;
/*     */ import com.google.crypto.tink.proto.HashType;
/*     */ import com.google.crypto.tink.proto.KeyData;
/*     */ import com.google.crypto.tink.proto.KeyStatusType;
/*     */ import com.google.crypto.tink.proto.Keyset;
/*     */ import com.google.crypto.tink.proto.OutputPrefixType;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1Params;
/*     */ import com.google.crypto.tink.proto.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.proto.RsaSsaPssParams;
/*     */ import com.google.crypto.tink.proto.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.signature.internal.SigUtil;
/*     */ import com.google.crypto.tink.subtle.Enums;
/*     */ import com.google.crypto.tink.subtle.Random;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.security.Key;
/*     */ import java.security.interfaces.ECPublicKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class SignaturePemKeysetReader
/*     */   implements KeysetReader
/*     */ {
/*     */   private List<PemKey> pemKeys;
/*     */   
/*     */   SignaturePemKeysetReader(List<PemKey> pemKeys) {
/*  70 */     this.pemKeys = pemKeys;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Builder newBuilder() {
/*  75 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/*  80 */     private List<SignaturePemKeysetReader.PemKey> pemKeys = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/*     */     public KeysetReader build() {
/*  85 */       return new SignaturePemKeysetReader(this.pemKeys);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder addPem(String pem, PemKeyType keyType) {
/*  98 */       SignaturePemKeysetReader.PemKey pemKey = new SignaturePemKeysetReader.PemKey();
/*  99 */       pemKey.reader = new BufferedReader(new StringReader(pem));
/* 100 */       pemKey.type = keyType;
/* 101 */       this.pemKeys.add(pemKey);
/* 102 */       return this;
/*     */     } }
/*     */   
/*     */   private static final class PemKey {
/*     */     BufferedReader reader;
/*     */     PemKeyType type;
/*     */     
/*     */     private PemKey() {}
/*     */   }
/*     */   
/*     */   public Keyset read() throws IOException {
/* 113 */     Keyset.Builder keyset = Keyset.newBuilder();
/* 114 */     for (PemKey pemKey : this.pemKeys) {
/* 115 */       Keyset.Key key = readKey(pemKey.reader, pemKey.type);
/* 116 */       for (; key != null; 
/* 117 */         key = readKey(pemKey.reader, pemKey.type)) {
/* 118 */         keyset.addKey(key);
/*     */       }
/*     */     } 
/*     */     
/* 122 */     if (keyset.getKeyCount() == 0) {
/* 123 */       throw new IOException("cannot find any key");
/*     */     }
/*     */     
/* 126 */     keyset.setPrimaryKeyId(keyset.getKey(0).getKeyId());
/* 127 */     return keyset.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public EncryptedKeyset readEncrypted() throws IOException {
/* 132 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Keyset.Key readKey(BufferedReader reader, PemKeyType pemKeyType) throws IOException {
/*     */     KeyData keyData;
/* 139 */     Key key = pemKeyType.readKey(reader);
/* 140 */     if (key == null) {
/* 141 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 145 */     if (key instanceof RSAPublicKey) {
/* 146 */       keyData = convertRsaPublicKey(pemKeyType, (RSAPublicKey)key);
/* 147 */     } else if (key instanceof ECPublicKey) {
/* 148 */       keyData = convertEcPublicKey(pemKeyType, (ECPublicKey)key);
/*     */     } else {
/*     */       
/* 151 */       return null;
/*     */     } 
/*     */     
/* 154 */     return Keyset.Key.newBuilder()
/* 155 */       .setKeyData(keyData)
/* 156 */       .setStatus(KeyStatusType.ENABLED)
/* 157 */       .setOutputPrefixType(OutputPrefixType.RAW)
/* 158 */       .setKeyId(Random.randInt())
/* 159 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static KeyData convertRsaPublicKey(PemKeyType pemKeyType, RSAPublicKey key) throws IOException {
/* 164 */     if (pemKeyType.algorithm.equals("RSASSA-PKCS1-v1_5")) {
/*     */       
/* 166 */       RsaSsaPkcs1Params params = RsaSsaPkcs1Params.newBuilder().setHashType(getHashType(pemKeyType)).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 173 */       RsaSsaPkcs1PublicKey pkcs1PubKey = RsaSsaPkcs1PublicKey.newBuilder().setVersion(0).setParams(params).setE(SigUtil.toUnsignedIntByteString(key.getPublicExponent())).setN(SigUtil.toUnsignedIntByteString(key.getModulus())).build();
/* 174 */       return KeyData.newBuilder()
/* 175 */         .setTypeUrl(RsaSsaPkcs1VerifyKeyManager.getKeyType())
/* 176 */         .setValue(pkcs1PubKey.toByteString())
/* 177 */         .setKeyMaterialType(KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC)
/* 178 */         .build();
/* 179 */     }  if (pemKeyType.algorithm.equals("RSASSA-PSS")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 185 */       RsaSsaPssParams params = RsaSsaPssParams.newBuilder().setSigHash(getHashType(pemKeyType)).setMgf1Hash(getHashType(pemKeyType)).setSaltLength(getDigestSizeInBytes(pemKeyType)).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       RsaSsaPssPublicKey pssPubKey = RsaSsaPssPublicKey.newBuilder().setVersion(0).setParams(params).setE(SigUtil.toUnsignedIntByteString(key.getPublicExponent())).setN(SigUtil.toUnsignedIntByteString(key.getModulus())).build();
/* 193 */       return KeyData.newBuilder()
/* 194 */         .setTypeUrl(RsaSsaPssVerifyKeyManager.getKeyType())
/* 195 */         .setValue(pssPubKey.toByteString())
/* 196 */         .setKeyMaterialType(KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC)
/* 197 */         .build();
/*     */     } 
/* 199 */     throw new IOException("unsupported RSA signature algorithm: " + pemKeyType.algorithm);
/*     */   }
/*     */ 
/*     */   
/*     */   private static KeyData convertEcPublicKey(PemKeyType pemKeyType, ECPublicKey key) throws IOException {
/* 204 */     if (pemKeyType.algorithm.equals("ECDSA")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 210 */       EcdsaParams params = EcdsaParams.newBuilder().setHashType(getHashType(pemKeyType)).setCurve(getCurveType(pemKeyType)).setEncoding(EcdsaSignatureEncoding.DER).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       EcdsaPublicKey ecdsaPubKey = EcdsaPublicKey.newBuilder().setVersion(0).setParams(params).setX(SigUtil.toUnsignedIntByteString(key.getW().getAffineX())).setY(SigUtil.toUnsignedIntByteString(key.getW().getAffineY())).build();
/*     */       
/* 219 */       return KeyData.newBuilder()
/* 220 */         .setTypeUrl(EcdsaVerifyKeyManager.getKeyType())
/* 221 */         .setValue(ecdsaPubKey.toByteString())
/* 222 */         .setKeyMaterialType(KeyData.KeyMaterialType.ASYMMETRIC_PUBLIC)
/* 223 */         .build();
/*     */     } 
/* 225 */     throw new IOException("unsupported EC signature algorithm: " + pemKeyType.algorithm);
/*     */   }
/*     */   
/*     */   private static HashType getHashType(PemKeyType pemKeyType) {
/* 229 */     switch (pemKeyType.hash) {
/*     */       case SHA256:
/* 231 */         return HashType.SHA256;
/*     */       case SHA384:
/* 233 */         return HashType.SHA384;
/*     */       case SHA512:
/* 235 */         return HashType.SHA512;
/*     */     } 
/*     */ 
/*     */     
/* 239 */     throw new IllegalArgumentException("unsupported hash type: " + pemKeyType.hash.name());
/*     */   }
/*     */   
/*     */   private static int getDigestSizeInBytes(PemKeyType pemKeyType) {
/* 243 */     switch (pemKeyType.hash) {
/*     */       case SHA256:
/* 245 */         return 32;
/*     */       case SHA384:
/* 247 */         return 48;
/*     */       case SHA512:
/* 249 */         return 64;
/*     */     } 
/*     */ 
/*     */     
/* 253 */     throw new IllegalArgumentException("unsupported hash type: " + pemKeyType.hash.name());
/*     */   }
/*     */   
/*     */   private static EllipticCurveType getCurveType(PemKeyType pemKeyType) {
/* 257 */     switch (pemKeyType.keySizeInBits) {
/*     */       case 256:
/* 259 */         return EllipticCurveType.NIST_P256;
/*     */       case 384:
/* 261 */         return EllipticCurveType.NIST_P384;
/*     */       case 521:
/* 263 */         return EllipticCurveType.NIST_P521;
/*     */     } 
/*     */ 
/*     */     
/* 267 */     throw new IllegalArgumentException("unsupported curve for key size: " + pemKeyType.keySizeInBits);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SignaturePemKeysetReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */