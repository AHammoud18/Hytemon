/*     */ package com.google.crypto.tink.mac;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class AesCmacKey
/*     */   extends MacKey
/*     */ {
/*     */   private final AesCmacParameters parameters;
/*     */   private final SecretBytes aesKeyBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*  48 */     private AesCmacParameters parameters = null; @Nullable
/*  49 */     private SecretBytes aesKeyBytes = null; @Nullable
/*  50 */     private Integer idRequirement = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(AesCmacParameters parameters) {
/*  56 */       this.parameters = parameters;
/*  57 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAesKeyBytes(SecretBytes aesKeyBytes) throws GeneralSecurityException {
/*  62 */       this.aesKeyBytes = aesKeyBytes;
/*  63 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(@Nullable Integer idRequirement) {
/*  68 */       this.idRequirement = idRequirement;
/*  69 */       return this;
/*     */     }
/*     */     
/*     */     private Bytes getOutputPrefix() {
/*  73 */       if (this.parameters.getVariant() == AesCmacParameters.Variant.NO_PREFIX) {
/*  74 */         return OutputPrefixUtil.EMPTY_PREFIX;
/*     */       }
/*  76 */       if (this.parameters.getVariant() == AesCmacParameters.Variant.LEGACY || this.parameters
/*  77 */         .getVariant() == AesCmacParameters.Variant.CRUNCHY) {
/*  78 */         return OutputPrefixUtil.getLegacyOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  80 */       if (this.parameters.getVariant() == AesCmacParameters.Variant.TINK) {
/*  81 */         return OutputPrefixUtil.getTinkOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  83 */       throw new IllegalStateException("Unknown AesCmacParametersParameters.Variant: " + this.parameters
/*  84 */           .getVariant());
/*     */     }
/*     */     
/*     */     public AesCmacKey build() throws GeneralSecurityException {
/*  88 */       if (this.parameters == null || this.aesKeyBytes == null) {
/*  89 */         throw new GeneralSecurityException("Cannot build without parameters and/or key material");
/*     */       }
/*     */       
/*  92 */       if (this.parameters.getKeySizeBytes() != this.aesKeyBytes.size()) {
/*  93 */         throw new GeneralSecurityException("Key size mismatch");
/*     */       }
/*     */       
/*  96 */       if (this.parameters.hasIdRequirement() && this.idRequirement == null) {
/*  97 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */ 
/*     */       
/* 101 */       if (!this.parameters.hasIdRequirement() && this.idRequirement != null) {
/* 102 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */       
/* 105 */       Bytes outputPrefix = getOutputPrefix();
/* 106 */       return new AesCmacKey(this.parameters, this.aesKeyBytes, outputPrefix, this.idRequirement);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private AesCmacKey(AesCmacParameters parameters, SecretBytes aesKeyBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/* 115 */     this.parameters = parameters;
/* 116 */     this.aesKeyBytes = aesKeyBytes;
/* 117 */     this.outputPrefix = outputPrefix;
/* 118 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 127 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getAesKey() {
/* 137 */     return this.aesKeyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 142 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesCmacParameters getParameters() {
/* 147 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 153 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 158 */     if (!(o instanceof AesCmacKey)) {
/* 159 */       return false;
/*     */     }
/* 161 */     AesCmacKey that = (AesCmacKey)o;
/* 162 */     return (that.parameters.equals(this.parameters) && that.aesKeyBytes
/* 163 */       .equalsSecretBytes(this.aesKeyBytes) && 
/* 164 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\AesCmacKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */