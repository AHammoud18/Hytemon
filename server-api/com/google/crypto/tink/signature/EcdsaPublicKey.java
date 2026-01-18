/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECPoint;
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
/*     */ @Immutable
/*     */ public final class EcdsaPublicKey
/*     */   extends SignaturePublicKey
/*     */ {
/*     */   private final EcdsaParameters parameters;
/*     */   private final ECPoint publicPoint;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*  43 */     private EcdsaParameters parameters = null; @Nullable
/*  44 */     private ECPoint publicPoint = null; @Nullable
/*  45 */     private Integer idRequirement = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(EcdsaParameters parameters) {
/*  51 */       this.parameters = parameters;
/*  52 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicPoint(ECPoint publicPoint) {
/*  57 */       this.publicPoint = publicPoint;
/*  58 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(@Nullable Integer idRequirement) {
/*  63 */       this.idRequirement = idRequirement;
/*  64 */       return this;
/*     */     }
/*     */     
/*     */     private Bytes getOutputPrefix() {
/*  68 */       if (this.parameters.getVariant() == EcdsaParameters.Variant.NO_PREFIX) {
/*  69 */         return OutputPrefixUtil.EMPTY_PREFIX;
/*     */       }
/*  71 */       if (this.parameters.getVariant() == EcdsaParameters.Variant.LEGACY || this.parameters
/*  72 */         .getVariant() == EcdsaParameters.Variant.CRUNCHY) {
/*  73 */         return OutputPrefixUtil.getLegacyOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  75 */       if (this.parameters.getVariant() == EcdsaParameters.Variant.TINK) {
/*  76 */         return OutputPrefixUtil.getTinkOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  78 */       throw new IllegalStateException("Unknown EcdsaParameters.Variant: " + this.parameters
/*  79 */           .getVariant());
/*     */     }
/*     */     
/*     */     public EcdsaPublicKey build() throws GeneralSecurityException {
/*  83 */       if (this.parameters == null) {
/*  84 */         throw new GeneralSecurityException("Cannot build without parameters");
/*     */       }
/*  86 */       if (this.publicPoint == null) {
/*  87 */         throw new GeneralSecurityException("Cannot build without public point");
/*     */       }
/*  89 */       EllipticCurvesUtil.checkPointOnCurve(this.publicPoint, this.parameters
/*  90 */           .getCurveType().toParameterSpec().getCurve());
/*  91 */       if (this.parameters.hasIdRequirement() && this.idRequirement == null) {
/*  92 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */       
/*  95 */       if (!this.parameters.hasIdRequirement() && this.idRequirement != null) {
/*  96 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */       
/*  99 */       Bytes outputPrefix = getOutputPrefix();
/* 100 */       return new EcdsaPublicKey(this.parameters, this.publicPoint, outputPrefix, this.idRequirement);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private EcdsaPublicKey(EcdsaParameters parameters, ECPoint publicPoint, Bytes outputPrefix, @Nullable Integer idRequirement) {
/* 109 */     this.parameters = parameters;
/* 110 */     this.publicPoint = publicPoint;
/* 111 */     this.outputPrefix = outputPrefix;
/* 112 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 121 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public ECPoint getPublicPoint() {
/* 130 */     return this.publicPoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 135 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public EcdsaParameters getParameters() {
/* 140 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 146 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 151 */     if (!(o instanceof EcdsaPublicKey)) {
/* 152 */       return false;
/*     */     }
/* 154 */     EcdsaPublicKey that = (EcdsaPublicKey)o;
/*     */     
/* 156 */     return (that.parameters.equals(this.parameters) && that.publicPoint
/* 157 */       .equals(this.publicPoint) && 
/* 158 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\EcdsaPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */