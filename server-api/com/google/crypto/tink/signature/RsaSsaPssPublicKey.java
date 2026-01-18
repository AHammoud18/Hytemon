/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.math.BigInteger;
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
/*     */ public final class RsaSsaPssPublicKey
/*     */   extends SignaturePublicKey
/*     */ {
/*     */   private final RsaSsaPssParameters parameters;
/*     */   private final BigInteger modulus;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*  43 */     private RsaSsaPssParameters parameters = null; @Nullable
/*  44 */     private BigInteger modulus = null; @Nullable
/*  45 */     private Integer idRequirement = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(RsaSsaPssParameters parameters) {
/*  51 */       this.parameters = parameters;
/*  52 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setModulus(BigInteger modulus) {
/*  57 */       this.modulus = modulus;
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
/*  68 */       if (this.parameters.getVariant() == RsaSsaPssParameters.Variant.NO_PREFIX) {
/*  69 */         return OutputPrefixUtil.EMPTY_PREFIX;
/*     */       }
/*  71 */       if (this.parameters.getVariant() == RsaSsaPssParameters.Variant.LEGACY || this.parameters
/*  72 */         .getVariant() == RsaSsaPssParameters.Variant.CRUNCHY) {
/*  73 */         return OutputPrefixUtil.getLegacyOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  75 */       if (this.parameters.getVariant() == RsaSsaPssParameters.Variant.TINK) {
/*  76 */         return OutputPrefixUtil.getTinkOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  78 */       throw new IllegalStateException("Unknown RsaSsaPssParameters.Variant: " + this.parameters
/*  79 */           .getVariant());
/*     */     }
/*     */     
/*     */     public RsaSsaPssPublicKey build() throws GeneralSecurityException {
/*  83 */       if (this.parameters == null) {
/*  84 */         throw new GeneralSecurityException("Cannot build without parameters");
/*     */       }
/*     */       
/*  87 */       if (this.modulus == null) {
/*  88 */         throw new GeneralSecurityException("Cannot build without modulus");
/*     */       }
/*     */       
/*  91 */       int modulusSize = this.modulus.bitLength();
/*  92 */       int paramModulusSize = this.parameters.getModulusSizeBits();
/*     */ 
/*     */       
/*  95 */       if (modulusSize != paramModulusSize) {
/*  96 */         throw new GeneralSecurityException("Got modulus size " + modulusSize + ", but parameters requires modulus size " + paramModulusSize);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       if (this.parameters.hasIdRequirement() && this.idRequirement == null) {
/* 104 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */ 
/*     */       
/* 108 */       if (!this.parameters.hasIdRequirement() && this.idRequirement != null) {
/* 109 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */       
/* 112 */       Bytes outputPrefix = getOutputPrefix();
/* 113 */       return new RsaSsaPssPublicKey(this.parameters, this.modulus, outputPrefix, this.idRequirement);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private RsaSsaPssPublicKey(RsaSsaPssParameters parameters, BigInteger modulus, Bytes outputPrefix, @Nullable Integer idRequirement) {
/* 122 */     this.parameters = parameters;
/* 123 */     this.modulus = modulus;
/* 124 */     this.outputPrefix = outputPrefix;
/* 125 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 134 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public BigInteger getModulus() {
/* 144 */     return this.modulus;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 149 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public RsaSsaPssParameters getParameters() {
/* 154 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 160 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 165 */     if (!(o instanceof RsaSsaPssPublicKey)) {
/* 166 */       return false;
/*     */     }
/* 168 */     RsaSsaPssPublicKey that = (RsaSsaPssPublicKey)o;
/*     */     
/* 170 */     return (that.parameters.equals(this.parameters) && that.modulus
/* 171 */       .equals(this.modulus) && 
/* 172 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\RsaSsaPssPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */