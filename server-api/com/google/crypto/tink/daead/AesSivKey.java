/*     */ package com.google.crypto.tink.daead;
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
/*     */ @Immutable
/*     */ public final class AesSivKey
/*     */   extends DeterministicAeadKey
/*     */ {
/*     */   private final AesSivParameters parameters;
/*     */   private final SecretBytes keyBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*  44 */     private AesSivParameters parameters = null; @Nullable
/*  45 */     private SecretBytes keyBytes = null; @Nullable
/*  46 */     private Integer idRequirement = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(AesSivParameters parameters) {
/*  52 */       this.parameters = parameters;
/*  53 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeyBytes(SecretBytes keyBytes) {
/*  58 */       this.keyBytes = keyBytes;
/*  59 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(@Nullable Integer idRequirement) {
/*  64 */       this.idRequirement = idRequirement;
/*  65 */       return this;
/*     */     }
/*     */     
/*     */     public AesSivKey build() throws GeneralSecurityException {
/*  69 */       if (this.parameters == null || this.keyBytes == null) {
/*  70 */         throw new IllegalArgumentException("Cannot build without parameters and/or key material");
/*     */       }
/*     */       
/*  73 */       if (this.parameters.getKeySizeBytes() != this.keyBytes.size()) {
/*  74 */         throw new GeneralSecurityException("Key size mismatch");
/*     */       }
/*     */       
/*  77 */       if (this.parameters.hasIdRequirement() && this.idRequirement == null) {
/*  78 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */ 
/*     */       
/*  82 */       if (!this.parameters.hasIdRequirement() && this.idRequirement != null) {
/*  83 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */       
/*  86 */       Bytes outputPrefix = getOutputPrefix();
/*  87 */       return new AesSivKey(this.parameters, this.keyBytes, outputPrefix, this.idRequirement);
/*     */     }
/*     */     
/*     */     private Bytes getOutputPrefix() {
/*  91 */       if (this.parameters.getVariant() == AesSivParameters.Variant.NO_PREFIX) {
/*  92 */         return OutputPrefixUtil.EMPTY_PREFIX;
/*     */       }
/*  94 */       if (this.parameters.getVariant() == AesSivParameters.Variant.CRUNCHY) {
/*  95 */         return OutputPrefixUtil.getLegacyOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  97 */       if (this.parameters.getVariant() == AesSivParameters.Variant.TINK) {
/*  98 */         return OutputPrefixUtil.getTinkOutputPrefix(this.idRequirement.intValue());
/*     */       }
/* 100 */       throw new IllegalStateException("Unknown AesSivParameters.Variant: " + this.parameters
/* 101 */           .getVariant());
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private AesSivKey(AesSivParameters parameters, SecretBytes keyBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/* 110 */     this.parameters = parameters;
/* 111 */     this.keyBytes = keyBytes;
/* 112 */     this.outputPrefix = outputPrefix;
/* 113 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 122 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getKeyBytes() {
/* 132 */     return this.keyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 137 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public AesSivParameters getParameters() {
/* 142 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 148 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 153 */     if (!(o instanceof AesSivKey)) {
/* 154 */       return false;
/*     */     }
/* 156 */     AesSivKey that = (AesSivKey)o;
/*     */     
/* 158 */     return (that.parameters.equals(this.parameters) && that.keyBytes
/* 159 */       .equalsSecretBytes(this.keyBytes) && 
/* 160 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\AesSivKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */