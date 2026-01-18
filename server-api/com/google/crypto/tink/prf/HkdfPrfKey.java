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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class HkdfPrfKey
/*     */   extends PrfKey
/*     */ {
/*     */   private final HkdfPrfParameters parameters;
/*     */   private final SecretBytes keyBytes;
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     @Nullable
/*  40 */     private HkdfPrfParameters parameters = null; @Nullable
/*  41 */     private SecretBytes keyBytes = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(HkdfPrfParameters parameters) {
/*  47 */       this.parameters = parameters;
/*  48 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeyBytes(SecretBytes keyBytes) {
/*  53 */       this.keyBytes = keyBytes;
/*  54 */       return this;
/*     */     }
/*     */     private Builder() {}
/*     */     public HkdfPrfKey build() throws GeneralSecurityException {
/*  58 */       if (this.parameters == null || this.keyBytes == null) {
/*  59 */         throw new GeneralSecurityException("Cannot build without parameters and/or key material");
/*     */       }
/*     */       
/*  62 */       if (this.parameters.getKeySizeBytes() != this.keyBytes.size()) {
/*  63 */         throw new GeneralSecurityException("Key size mismatch");
/*     */       }
/*     */       
/*  66 */       return new HkdfPrfKey(this.parameters, this.keyBytes);
/*     */     }
/*     */   }
/*     */   
/*     */   private HkdfPrfKey(HkdfPrfParameters parameters, SecretBytes keyBytes) {
/*  71 */     this.parameters = parameters;
/*  72 */     this.keyBytes = keyBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/*  81 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getKeyBytes() {
/*  90 */     return this.keyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public HkdfPrfParameters getParameters() {
/*  95 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 106 */     if (!(o instanceof HkdfPrfKey)) {
/* 107 */       return false;
/*     */     }
/* 109 */     HkdfPrfKey that = (HkdfPrfKey)o;
/* 110 */     return (that.parameters.equals(this.parameters) && that.keyBytes.equalsSecretBytes(this.keyBytes));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\HkdfPrfKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */