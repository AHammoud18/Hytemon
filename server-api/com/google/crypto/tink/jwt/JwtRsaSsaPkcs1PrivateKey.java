/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PrivateKey;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Optional;
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
/*     */ public final class JwtRsaSsaPkcs1PrivateKey
/*     */   extends JwtSignaturePrivateKey
/*     */ {
/*     */   private final JwtRsaSsaPkcs1PublicKey publicKey;
/*     */   private final RsaSsaPkcs1PrivateKey rsaSsaPkcs1PrivateKey;
/*     */   
/*     */   public static class Builder
/*     */   {
/*  39 */     private Optional<JwtRsaSsaPkcs1PublicKey> publicKey = Optional.empty();
/*  40 */     private Optional<SecretBigInteger> d = Optional.empty();
/*  41 */     private Optional<SecretBigInteger> p = Optional.empty();
/*  42 */     private Optional<SecretBigInteger> q = Optional.empty();
/*  43 */     private Optional<SecretBigInteger> dP = Optional.empty();
/*  44 */     private Optional<SecretBigInteger> dQ = Optional.empty();
/*  45 */     private Optional<SecretBigInteger> qInv = Optional.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicKey(JwtRsaSsaPkcs1PublicKey publicKey) {
/*  56 */       this.publicKey = Optional.of(publicKey);
/*  57 */       return this;
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
/*     */     public Builder setPrimes(SecretBigInteger p, SecretBigInteger q) {
/*  69 */       this.p = Optional.of(p);
/*  70 */       this.q = Optional.of(q);
/*  71 */       return this;
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
/*     */     public Builder setPrivateExponent(SecretBigInteger d) {
/*  83 */       this.d = Optional.of(d);
/*  84 */       return this;
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
/*  96 */       this.dP = Optional.of(dP);
/*  97 */       this.dQ = Optional.of(dQ);
/*  98 */       return this;
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
/* 110 */       this.qInv = Optional.of(qInv);
/* 111 */       return this;
/*     */     }
/*     */     
/*     */     @AccessesPartialKey
/*     */     public JwtRsaSsaPkcs1PrivateKey build() throws GeneralSecurityException {
/* 116 */       if (!this.publicKey.isPresent()) {
/* 117 */         throw new GeneralSecurityException("Cannot build without a RSA SSA PKCS1 public key");
/*     */       }
/* 119 */       if (!this.p.isPresent() || !this.q.isPresent()) {
/* 120 */         throw new GeneralSecurityException("Cannot build without prime factors");
/*     */       }
/* 122 */       if (!this.d.isPresent()) {
/* 123 */         throw new GeneralSecurityException("Cannot build without private exponent");
/*     */       }
/* 125 */       if (!this.dP.isPresent() || !this.dQ.isPresent()) {
/* 126 */         throw new GeneralSecurityException("Cannot build without prime exponents");
/*     */       }
/* 128 */       if (!this.qInv.isPresent()) {
/* 129 */         throw new GeneralSecurityException("Cannot build without CRT coefficient");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       RsaSsaPkcs1PrivateKey rsaSsaPkcs1PrivateKey = RsaSsaPkcs1PrivateKey.builder().setPublicKey(((JwtRsaSsaPkcs1PublicKey)this.publicKey.get()).getRsaSsaPkcs1PublicKey()).setPrimes(this.p.get(), this.q.get()).setPrivateExponent(this.d.get()).setPrimeExponents(this.dP.get(), this.dQ.get()).setCrtCoefficient(this.qInv.get()).build();
/* 140 */       return new JwtRsaSsaPkcs1PrivateKey(this.publicKey.get(), rsaSsaPkcs1PrivateKey);
/*     */     }
/*     */     
/*     */     private Builder() {} }
/*     */   
/*     */   private JwtRsaSsaPkcs1PrivateKey(JwtRsaSsaPkcs1PublicKey publicKey, RsaSsaPkcs1PrivateKey rsaSsaPkcs1PrivateKey) {
/* 146 */     this.publicKey = publicKey;
/* 147 */     this.rsaSsaPkcs1PrivateKey = rsaSsaPkcs1PrivateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 156 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JwtRsaSsaPkcs1Parameters getParameters() {
/* 162 */     return this.publicKey.getParameters();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JwtRsaSsaPkcs1PublicKey getPublicKey() {
/* 168 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrimeP() {
/* 179 */     return this.rsaSsaPkcs1PrivateKey.getPrimeP();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrimeQ() {
/* 190 */     return this.rsaSsaPkcs1PrivateKey.getPrimeQ();
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrivateExponent() {
/* 196 */     return this.rsaSsaPkcs1PrivateKey.getPrivateExponent();
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrimeExponentP() {
/* 202 */     return this.rsaSsaPkcs1PrivateKey.getPrimeExponentP();
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getPrimeExponentQ() {
/* 208 */     return this.rsaSsaPkcs1PrivateKey.getPrimeExponentQ();
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public SecretBigInteger getCrtCoefficient() {
/* 214 */     return this.rsaSsaPkcs1PrivateKey.getCrtCoefficient();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 219 */     if (!(o instanceof JwtRsaSsaPkcs1PrivateKey)) {
/* 220 */       return false;
/*     */     }
/* 222 */     JwtRsaSsaPkcs1PrivateKey that = (JwtRsaSsaPkcs1PrivateKey)o;
/* 223 */     return (that.publicKey.equalsKey(this.publicKey) && that.rsaSsaPkcs1PrivateKey
/* 224 */       .equalsKey((Key)this.rsaSsaPkcs1PrivateKey));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   RsaSsaPkcs1PrivateKey getRsaSsaPkcs1PrivateKey() {
/* 233 */     return this.rsaSsaPkcs1PrivateKey;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPkcs1PrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */