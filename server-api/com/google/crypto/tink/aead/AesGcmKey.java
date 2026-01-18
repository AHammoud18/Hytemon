/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
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
/*     */ @Immutable
/*     */ public final class AesGcmKey
/*     */   extends AeadKey
/*     */ {
/*     */   private final AesGcmParameters parameters;
/*     */   private final SecretBytes keyBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*  41 */     private AesGcmParameters parameters = null; @Nullable
/*  42 */     private SecretBytes keyBytes = null; @Nullable
/*  43 */     private Integer idRequirement = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(AesGcmParameters parameters) {
/*  49 */       this.parameters = parameters;
/*  50 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeyBytes(SecretBytes keyBytes) {
/*  55 */       this.keyBytes = keyBytes;
/*  56 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(@Nullable Integer idRequirement) {
/*  61 */       this.idRequirement = idRequirement;
/*  62 */       return this;
/*     */     }
/*     */     
/*     */     private Bytes getOutputPrefix() {
/*  66 */       if (this.parameters.getVariant() == AesGcmParameters.Variant.NO_PREFIX) {
/*  67 */         return OutputPrefixUtil.EMPTY_PREFIX;
/*     */       }
/*  69 */       if (this.parameters.getVariant() == AesGcmParameters.Variant.CRUNCHY) {
/*  70 */         return OutputPrefixUtil.getLegacyOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  72 */       if (this.parameters.getVariant() == AesGcmParameters.Variant.TINK) {
/*  73 */         return OutputPrefixUtil.getTinkOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  75 */       throw new IllegalStateException("Unknown AesGcmParameters.Variant: " + this.parameters
/*  76 */           .getVariant());
/*     */     }
/*     */     
/*     */     public AesGcmKey build() throws GeneralSecurityException {
/*  80 */       if (this.parameters == null || this.keyBytes == null) {
/*  81 */         throw new GeneralSecurityException("Cannot build without parameters and/or key material");
/*     */       }
/*     */       
/*  84 */       if (this.parameters.getKeySizeBytes() != this.keyBytes.size()) {
/*  85 */         throw new GeneralSecurityException("Key size mismatch");
/*     */       }
/*     */       
/*  88 */       if (this.parameters.hasIdRequirement() && this.idRequirement == null) {
/*  89 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */ 
/*     */       
/*  93 */       if (!this.parameters.hasIdRequirement() && this.idRequirement != null) {
/*  94 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */       
/*  97 */       Bytes outputPrefix = getOutputPrefix();
/*  98 */       return new AesGcmKey(this.parameters, this.keyBytes, outputPrefix, this.idRequirement);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private AesGcmKey(AesGcmParameters parameters, SecretBytes keyBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/* 107 */     this.parameters = parameters;
/* 108 */     this.keyBytes = keyBytes;
/* 109 */     this.outputPrefix = outputPrefix;
/* 110 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 119 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getKeyBytes() {
/* 129 */     return this.keyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 134 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesGcmParameters getParameters() {
/* 139 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 145 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 150 */     if (!(o instanceof AesGcmKey)) {
/* 151 */       return false;
/*     */     }
/* 153 */     AesGcmKey that = (AesGcmKey)o;
/*     */     
/* 155 */     return (that.parameters.equals(this.parameters) && that.keyBytes
/* 156 */       .equalsSecretBytes(this.keyBytes) && 
/* 157 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesGcmKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */