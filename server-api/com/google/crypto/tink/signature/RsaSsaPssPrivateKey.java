/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
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
/*     */ public final class RsaSsaPssPrivateKey
/*     */   extends SignaturePrivateKey
/*     */ {
/*     */   private final RsaSsaPssPublicKey publicKey;
/*     */   private final SecretBigInteger d;
/*     */   private final SecretBigInteger p;
/*     */   private final SecretBigInteger q;
/*     */   private final SecretBigInteger dP;
/*     */   private final SecretBigInteger dQ;
/*     */   private final SecretBigInteger qInv;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*  45 */     private RsaSsaPssPublicKey publicKey = null; @Nullable
/*  46 */     private SecretBigInteger d = null; @Nullable
/*  47 */     private SecretBigInteger p = null; @Nullable
/*  48 */     private SecretBigInteger q = null; @Nullable
/*  49 */     private SecretBigInteger dP = null; @Nullable
/*  50 */     private SecretBigInteger dQ = null; @Nullable
/*  51 */     private SecretBigInteger qInv = null;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int PRIME_CERTAINTY = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicKey(RsaSsaPssPublicKey publicKey) {
/*  62 */       this.publicKey = publicKey;
/*  63 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPrimes(SecretBigInteger p, SecretBigInteger q) {
/*  73 */       this.p = p;
/*  74 */       this.q = q;
/*  75 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPrivateExponent(SecretBigInteger d) {
/*  85 */       this.d = d;
/*  86 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPrimeExponents(SecretBigInteger dP, SecretBigInteger dQ) {
/*  98 */       this.dP = dP;
/*  99 */       this.dQ = dQ;
/* 100 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setCrtCoefficient(SecretBigInteger qInv) {
/* 112 */       this.qInv = qInv;
/* 113 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @AccessesPartialKey
/*     */     public RsaSsaPssPrivateKey build() throws GeneralSecurityException {
/* 120 */       if (this.publicKey == null) {
/* 121 */         throw new GeneralSecurityException("Cannot build without a RSA SSA PKCS1 public key");
/*     */       }
/* 123 */       if (this.p == null || this.q == null) {
/* 124 */         throw new GeneralSecurityException("Cannot build without prime factors");
/*     */       }
/* 126 */       if (this.d == null) {
/* 127 */         throw new GeneralSecurityException("Cannot build without private exponent");
/*     */       }
/* 129 */       if (this.dP == null || this.dQ == null) {
/* 130 */         throw new GeneralSecurityException("Cannot build without prime exponents");
/*     */       }
/* 132 */       if (this.qInv == null) {
/* 133 */         throw new GeneralSecurityException("Cannot build without CRT coefficient");
/*     */       }
/* 135 */       BigInteger e = this.publicKey.getParameters().getPublicExponent();
/* 136 */       BigInteger n = this.publicKey.getModulus();
/*     */       
/* 138 */       BigInteger ip = this.p.getBigInteger(InsecureSecretKeyAccess.get());
/* 139 */       BigInteger iq = this.q.getBigInteger(InsecureSecretKeyAccess.get());
/* 140 */       BigInteger id = this.d.getBigInteger(InsecureSecretKeyAccess.get());
/* 141 */       BigInteger idP = this.dP.getBigInteger(InsecureSecretKeyAccess.get());
/* 142 */       BigInteger idQ = this.dQ.getBigInteger(InsecureSecretKeyAccess.get());
/* 143 */       BigInteger iqInv = this.qInv.getBigInteger(InsecureSecretKeyAccess.get());
/*     */       
/* 145 */       if (!ip.isProbablePrime(10)) {
/* 146 */         throw new GeneralSecurityException("p is not a prime");
/*     */       }
/* 148 */       if (!iq.isProbablePrime(10)) {
/* 149 */         throw new GeneralSecurityException("q is not a prime");
/*     */       }
/* 151 */       if (!ip.multiply(iq).equals(n)) {
/* 152 */         throw new GeneralSecurityException("Prime p times prime q is not equal to the public key's modulus");
/*     */       }
/*     */ 
/*     */       
/* 156 */       BigInteger pMinusOne = ip.subtract(BigInteger.ONE);
/* 157 */       BigInteger qMinusOne = iq.subtract(BigInteger.ONE);
/* 158 */       BigInteger lambda = pMinusOne.divide(pMinusOne.gcd(qMinusOne)).multiply(qMinusOne);
/* 159 */       if (!e.multiply(id).mod(lambda).equals(BigInteger.ONE)) {
/* 160 */         throw new GeneralSecurityException("D is invalid.");
/*     */       }
/* 162 */       if (!e.multiply(idP).mod(pMinusOne).equals(BigInteger.ONE)) {
/* 163 */         throw new GeneralSecurityException("dP is invalid.");
/*     */       }
/* 165 */       if (!e.multiply(idQ).mod(qMinusOne).equals(BigInteger.ONE)) {
/* 166 */         throw new GeneralSecurityException("dQ is invalid.");
/*     */       }
/* 168 */       if (!iq.multiply(iqInv).mod(ip).equals(BigInteger.ONE)) {
/* 169 */         throw new GeneralSecurityException("qInv is invalid.");
/*     */       }
/* 171 */       return new RsaSsaPssPrivateKey(this.publicKey, this.p, this.q, this.d, this.dP, this.dQ, this.qInv);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RsaSsaPssPrivateKey(RsaSsaPssPublicKey publicKey, SecretBigInteger p, SecretBigInteger q, SecretBigInteger d, SecretBigInteger dP, SecretBigInteger dQ, SecretBigInteger qInv) {
/* 183 */     this.publicKey = publicKey;
/* 184 */     this.p = p;
/* 185 */     this.q = q;
/* 186 */     this.d = d;
/* 187 */     this.dP = dP;
/* 188 */     this.dQ = dQ;
/* 189 */     this.qInv = qInv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 198 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RsaSsaPssParameters getParameters() {
/* 204 */     return this.publicKey.getParameters();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RsaSsaPssPublicKey getPublicKey() {
/* 210 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBigInteger getPrimeP() {
/* 220 */     return this.p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBigInteger getPrimeQ() {
/* 230 */     return this.q;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecretBigInteger getPrivateExponent() {
/* 235 */     return this.d;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecretBigInteger getPrimeExponentP() {
/* 240 */     return this.dP;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecretBigInteger getPrimeExponentQ() {
/* 245 */     return this.dQ;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecretBigInteger getCrtCoefficient() {
/* 250 */     return this.qInv;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 255 */     if (!(o instanceof RsaSsaPssPrivateKey)) {
/* 256 */       return false;
/*     */     }
/* 258 */     RsaSsaPssPrivateKey that = (RsaSsaPssPrivateKey)o;
/* 259 */     return (that.publicKey.equalsKey(this.publicKey) && this.p
/* 260 */       .equalsSecretBigInteger(that.p) && this.q
/* 261 */       .equalsSecretBigInteger(that.q) && this.d
/* 262 */       .equalsSecretBigInteger(that.d) && this.dP
/* 263 */       .equalsSecretBigInteger(that.dP) && this.dQ
/* 264 */       .equalsSecretBigInteger(that.dQ) && this.qInv
/* 265 */       .equalsSecretBigInteger(that.qInv));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\RsaSsaPssPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */