/*     */ package com.google.crypto.tink.prf;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
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
/*     */ @Immutable
/*     */ public final class HmacPrfKey
/*     */   extends PrfKey
/*     */ {
/*     */   private final HmacPrfParameters parameters;
/*     */   private final SecretBytes keyBytes;
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     @Nullable
/*  36 */     private HmacPrfParameters parameters = null; @Nullable
/*  37 */     private SecretBytes keyBytes = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(HmacPrfParameters parameters) {
/*  43 */       this.parameters = parameters;
/*  44 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeyBytes(SecretBytes keyBytes) {
/*  49 */       this.keyBytes = keyBytes;
/*  50 */       return this;
/*     */     }
/*     */     private Builder() {}
/*     */     public HmacPrfKey build() throws GeneralSecurityException {
/*  54 */       if (this.parameters == null || this.keyBytes == null) {
/*  55 */         throw new GeneralSecurityException("Cannot build without parameters and/or key material");
/*     */       }
/*     */       
/*  58 */       if (this.parameters.getKeySizeBytes() != this.keyBytes.size()) {
/*  59 */         throw new GeneralSecurityException("Key size mismatch");
/*     */       }
/*     */       
/*  62 */       return new HmacPrfKey(this.parameters, this.keyBytes);
/*     */     }
/*     */   }
/*     */   
/*     */   private HmacPrfKey(HmacPrfParameters parameters, SecretBytes keyBytes) {
/*  67 */     this.parameters = parameters;
/*  68 */     this.keyBytes = keyBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/*  77 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getKeyBytes() {
/*  86 */     return this.keyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public HmacPrfParameters getParameters() {
/*  91 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 102 */     if (!(o instanceof HmacPrfKey)) {
/* 103 */       return false;
/*     */     }
/* 105 */     HmacPrfKey that = (HmacPrfKey)o;
/* 106 */     return (that.parameters.equals(this.parameters) && that.keyBytes.equalsSecretBytes(this.keyBytes));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\HmacPrfKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */