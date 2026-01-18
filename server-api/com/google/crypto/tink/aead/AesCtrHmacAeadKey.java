/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Objects;
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
/*     */ public final class AesCtrHmacAeadKey
/*     */   extends AeadKey
/*     */ {
/*     */   private final AesCtrHmacAeadParameters parameters;
/*     */   private final SecretBytes aesKeyBytes;
/*     */   private final SecretBytes hmacKeyBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*  40 */     private AesCtrHmacAeadParameters parameters = null; @Nullable
/*  41 */     private SecretBytes aesKeyBytes = null; @Nullable
/*  42 */     private SecretBytes hmacKeyBytes = null; @Nullable
/*  43 */     private Integer idRequirement = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(AesCtrHmacAeadParameters parameters) {
/*  49 */       this.parameters = parameters;
/*  50 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAesKeyBytes(SecretBytes aesKeyBytes) {
/*  55 */       this.aesKeyBytes = aesKeyBytes;
/*  56 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHmacKeyBytes(SecretBytes hmacKeyBytes) {
/*  61 */       this.hmacKeyBytes = hmacKeyBytes;
/*  62 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(@Nullable Integer idRequirement) {
/*  67 */       this.idRequirement = idRequirement;
/*  68 */       return this;
/*     */     }
/*     */     
/*     */     private Bytes getOutputPrefix() {
/*  72 */       if (this.parameters.getVariant() == AesCtrHmacAeadParameters.Variant.NO_PREFIX) {
/*  73 */         return OutputPrefixUtil.EMPTY_PREFIX;
/*     */       }
/*  75 */       if (this.parameters.getVariant() == AesCtrHmacAeadParameters.Variant.CRUNCHY) {
/*  76 */         return OutputPrefixUtil.getLegacyOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  78 */       if (this.parameters.getVariant() == AesCtrHmacAeadParameters.Variant.TINK) {
/*  79 */         return OutputPrefixUtil.getTinkOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  81 */       throw new IllegalStateException("Unknown AesCtrHmacAeadParameters.Variant: " + this.parameters
/*  82 */           .getVariant());
/*     */     }
/*     */     
/*     */     public AesCtrHmacAeadKey build() throws GeneralSecurityException {
/*  86 */       if (this.parameters == null) {
/*  87 */         throw new GeneralSecurityException("Cannot build without parameters");
/*     */       }
/*     */       
/*  90 */       if (this.aesKeyBytes == null || this.hmacKeyBytes == null) {
/*  91 */         throw new GeneralSecurityException("Cannot build without key material");
/*     */       }
/*     */       
/*  94 */       if (this.parameters.getAesKeySizeBytes() != this.aesKeyBytes.size()) {
/*  95 */         throw new GeneralSecurityException("AES key size mismatch");
/*     */       }
/*     */       
/*  98 */       if (this.parameters.getHmacKeySizeBytes() != this.hmacKeyBytes.size()) {
/*  99 */         throw new GeneralSecurityException("HMAC key size mismatch");
/*     */       }
/*     */       
/* 102 */       if (this.parameters.hasIdRequirement() && this.idRequirement == null) {
/* 103 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */ 
/*     */       
/* 107 */       if (!this.parameters.hasIdRequirement() && this.idRequirement != null) {
/* 108 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */       
/* 111 */       Bytes outputPrefix = getOutputPrefix();
/* 112 */       return new AesCtrHmacAeadKey(this.parameters, this.aesKeyBytes, this.hmacKeyBytes, outputPrefix, this.idRequirement);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AesCtrHmacAeadKey(AesCtrHmacAeadParameters parameters, SecretBytes aesKeyBytes, SecretBytes hmacKeyBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/* 123 */     this.parameters = parameters;
/* 124 */     this.aesKeyBytes = aesKeyBytes;
/* 125 */     this.hmacKeyBytes = hmacKeyBytes;
/* 126 */     this.outputPrefix = outputPrefix;
/* 127 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 136 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getAesKeyBytes() {
/* 146 */     return this.aesKeyBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getHmacKeyBytes() {
/* 156 */     return this.hmacKeyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 161 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCtrHmacAeadParameters getParameters() {
/* 166 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 172 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 177 */     if (!(o instanceof AesCtrHmacAeadKey)) {
/* 178 */       return false;
/*     */     }
/* 180 */     AesCtrHmacAeadKey that = (AesCtrHmacAeadKey)o;
/*     */     
/* 182 */     return (that.parameters.equals(this.parameters) && that.aesKeyBytes
/* 183 */       .equalsSecretBytes(this.aesKeyBytes) && that.hmacKeyBytes
/* 184 */       .equalsSecretBytes(this.hmacKeyBytes) && 
/* 185 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesCtrHmacAeadKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */