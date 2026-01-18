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
/*     */ public final class RsaSsaPkcs1PublicKey
/*     */   extends SignaturePublicKey
/*     */ {
/*     */   private final RsaSsaPkcs1Parameters parameters;
/*     */   private final BigInteger modulus;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*  43 */     private RsaSsaPkcs1Parameters parameters = null; @Nullable
/*  44 */     private BigInteger modulus = null; @Nullable
/*  45 */     private Integer idRequirement = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(RsaSsaPkcs1Parameters parameters) {
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
/*  68 */       if (this.parameters.getVariant() == RsaSsaPkcs1Parameters.Variant.NO_PREFIX) {
/*  69 */         return OutputPrefixUtil.EMPTY_PREFIX;
/*     */       }
/*  71 */       if (this.parameters.getVariant() == RsaSsaPkcs1Parameters.Variant.LEGACY || this.parameters
/*  72 */         .getVariant() == RsaSsaPkcs1Parameters.Variant.CRUNCHY) {
/*  73 */         return OutputPrefixUtil.getLegacyOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  75 */       if (this.parameters.getVariant() == RsaSsaPkcs1Parameters.Variant.TINK) {
/*  76 */         return OutputPrefixUtil.getTinkOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  78 */       throw new IllegalStateException("Unknown RsaSsaPkcs1Parameters.Variant: " + this.parameters
/*  79 */           .getVariant());
/*     */     }
/*     */     
/*     */     public RsaSsaPkcs1PublicKey build() throws GeneralSecurityException {
/*  83 */       if (this.parameters == null) {
/*  84 */         throw new GeneralSecurityException("Cannot build without parameters");
/*     */       }
/*     */       
/*  87 */       if (this.modulus == null) {
/*  88 */         throw new GeneralSecurityException("Cannot build without modulus");
/*     */       }
/*  90 */       int modulusSize = this.modulus.bitLength();
/*  91 */       int paramModulusSize = this.parameters.getModulusSizeBits();
/*     */ 
/*     */       
/*  94 */       if (modulusSize != paramModulusSize) {
/*  95 */         throw new GeneralSecurityException("Got modulus size " + modulusSize + ", but parameters requires modulus size " + paramModulusSize);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 112 */       return new RsaSsaPkcs1PublicKey(this.parameters, this.modulus, outputPrefix, this.idRequirement);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private RsaSsaPkcs1PublicKey(RsaSsaPkcs1Parameters parameters, BigInteger modulus, Bytes outputPrefix, @Nullable Integer idRequirement) {
/* 121 */     this.parameters = parameters;
/* 122 */     this.modulus = modulus;
/* 123 */     this.outputPrefix = outputPrefix;
/* 124 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 133 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public BigInteger getModulus() {
/* 143 */     return this.modulus;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 148 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public RsaSsaPkcs1Parameters getParameters() {
/* 153 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 159 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 164 */     if (!(o instanceof RsaSsaPkcs1PublicKey)) {
/* 165 */       return false;
/*     */     }
/* 167 */     RsaSsaPkcs1PublicKey that = (RsaSsaPkcs1PublicKey)o;
/*     */     
/* 169 */     return (that.parameters.equals(this.parameters) && that.modulus
/* 170 */       .equals(this.modulus) && 
/* 171 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\RsaSsaPkcs1PublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */