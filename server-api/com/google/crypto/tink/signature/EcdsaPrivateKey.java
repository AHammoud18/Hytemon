/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECPoint;
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
/*     */ public final class EcdsaPrivateKey
/*     */   extends SignaturePrivateKey
/*     */ {
/*     */   private final EcdsaPublicKey publicKey;
/*     */   private final SecretBigInteger privateValue;
/*     */   
/*     */   public static class Builder
/*     */   {
/*  43 */     private EcdsaPublicKey publicKey = null;
/*  44 */     private SecretBigInteger privateValue = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicKey(EcdsaPublicKey publicKey) {
/*  50 */       this.publicKey = publicKey;
/*  51 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPrivateValue(SecretBigInteger privateValue) {
/*  56 */       this.privateValue = privateValue;
/*  57 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static void validatePrivateValue(BigInteger privateValue, ECPoint publicPoint, EcdsaParameters.CurveType curveType) throws GeneralSecurityException {
/*  63 */       BigInteger order = curveType.toParameterSpec().getOrder();
/*  64 */       if (privateValue.signum() <= 0 || privateValue.compareTo(order) >= 0) {
/*  65 */         throw new GeneralSecurityException("Invalid private value");
/*     */       }
/*  67 */       ECPoint p = EllipticCurvesUtil.multiplyByGenerator(privateValue, curveType.toParameterSpec());
/*  68 */       if (!p.equals(publicPoint))
/*  69 */         throw new GeneralSecurityException("Invalid private value"); 
/*     */     }
/*     */     private Builder() {}
/*     */     
/*     */     @AccessesPartialKey
/*     */     public EcdsaPrivateKey build() throws GeneralSecurityException {
/*  75 */       if (this.publicKey == null) {
/*  76 */         throw new GeneralSecurityException("Cannot build without a ecdsa public key");
/*     */       }
/*  78 */       if (this.privateValue == null) {
/*  79 */         throw new GeneralSecurityException("Cannot build without a private value");
/*     */       }
/*  81 */       validatePrivateValue(this.privateValue
/*  82 */           .getBigInteger(InsecureSecretKeyAccess.get()), this.publicKey
/*  83 */           .getPublicPoint(), this.publicKey
/*  84 */           .getParameters().getCurveType());
/*  85 */       return new EcdsaPrivateKey(this.publicKey, this.privateValue);
/*     */     }
/*     */   }
/*     */   
/*     */   private EcdsaPrivateKey(EcdsaPublicKey publicKey, SecretBigInteger privateValue) {
/*  90 */     this.publicKey = publicKey;
/*  91 */     this.privateValue = privateValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 100 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   public EcdsaParameters getParameters() {
/* 105 */     return this.publicKey.getParameters();
/*     */   }
/*     */ 
/*     */   
/*     */   public EcdsaPublicKey getPublicKey() {
/* 110 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBigInteger getPrivateValue() {
/* 119 */     return this.privateValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 124 */     if (!(o instanceof EcdsaPrivateKey)) {
/* 125 */       return false;
/*     */     }
/* 127 */     EcdsaPrivateKey that = (EcdsaPrivateKey)o;
/* 128 */     return (that.publicKey.equalsKey(this.publicKey) && this.privateValue
/* 129 */       .equalsSecretBigInteger(that.privateValue));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\EcdsaPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */