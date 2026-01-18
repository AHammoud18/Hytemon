/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PublicKey;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.interfaces.RSAPrivateCrtKey;
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
/*     */ @Immutable
/*     */ public final class RsaSsaPkcs1SignJce
/*     */   implements PublicKeySign
/*     */ {
/*  38 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*     */   private final PublicKeySign signer;
/*     */ 
/*     */   
/*     */   public static PublicKeySign create(RsaSsaPkcs1PrivateKey key) throws GeneralSecurityException {
/*  45 */     return com.google.crypto.tink.signature.internal.RsaSsaPkcs1SignJce.create(key);
/*     */   }
/*     */   
/*     */   private static RsaSsaPkcs1Parameters.HashType convertHashType(Enums.HashType hash) throws GeneralSecurityException {
/*  49 */     switch (hash) {
/*     */       case SHA256:
/*  51 */         return RsaSsaPkcs1Parameters.HashType.SHA256;
/*     */       case SHA384:
/*  53 */         return RsaSsaPkcs1Parameters.HashType.SHA384;
/*     */       case SHA512:
/*  55 */         return RsaSsaPkcs1Parameters.HashType.SHA512;
/*     */     } 
/*     */ 
/*     */     
/*  59 */     throw new GeneralSecurityException("Unsupported hash: " + hash.name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static PublicKeySign getSigner(RSAPrivateCrtKey privateKey, Enums.HashType hash) throws GeneralSecurityException {
/*  71 */     RsaSsaPkcs1Parameters parameters = RsaSsaPkcs1Parameters.builder().setModulusSizeBits(privateKey.getModulus().bitLength()).setPublicExponent(privateKey.getPublicExponent()).setHashType(convertHashType(hash)).setVariant(RsaSsaPkcs1Parameters.Variant.NO_PREFIX).build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     RsaSsaPkcs1PublicKey publicKey = RsaSsaPkcs1PublicKey.builder().setParameters(parameters).setModulus(privateKey.getModulus()).build();
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
/*  89 */     RsaSsaPkcs1PrivateKey key = RsaSsaPkcs1PrivateKey.builder().setPublicKey(publicKey).setPrimes(SecretBigInteger.fromBigInteger(privateKey.getPrimeP(), InsecureSecretKeyAccess.get()), SecretBigInteger.fromBigInteger(privateKey.getPrimeQ(), InsecureSecretKeyAccess.get())).setPrivateExponent(SecretBigInteger.fromBigInteger(privateKey.getPrivateExponent(), InsecureSecretKeyAccess.get())).setPrimeExponents(SecretBigInteger.fromBigInteger(privateKey.getPrimeExponentP(), InsecureSecretKeyAccess.get()), SecretBigInteger.fromBigInteger(privateKey.getPrimeExponentQ(), InsecureSecretKeyAccess.get())).setCrtCoefficient(SecretBigInteger.fromBigInteger(privateKey.getCrtCoefficient(), InsecureSecretKeyAccess.get())).build();
/*  90 */     return com.google.crypto.tink.signature.internal.RsaSsaPkcs1SignJce.create(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public RsaSsaPkcs1SignJce(RSAPrivateCrtKey privateKey, Enums.HashType hash) throws GeneralSecurityException {
/*  95 */     this.signer = getSigner(privateKey, hash);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(byte[] data) throws GeneralSecurityException {
/* 100 */     return this.signer.sign(data);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\RsaSsaPkcs1SignJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */