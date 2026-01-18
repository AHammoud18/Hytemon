/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlhDsaPublicKey
/*     */   extends SignaturePublicKey
/*     */ {
/*     */   private static final int SLH_DSA_SHA2_128S_PUBLIC_KEY_BYTES = 32;
/*     */   private final SlhDsaParameters parameters;
/*     */   private final Bytes serializedPublicKey;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private SlhDsaPublicKey(SlhDsaParameters parameters, Bytes serializedPublicKey, Bytes outputPrefix, @Nullable Integer idRequirement) {
/*  43 */     this.parameters = parameters;
/*  44 */     this.serializedPublicKey = serializedPublicKey;
/*  45 */     this.outputPrefix = outputPrefix;
/*  46 */     this.idRequirement = idRequirement;
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     @Nullable
/*  51 */     private SlhDsaParameters parameters = null; @Nullable
/*  52 */     private Bytes serializedPublicKey = null; @Nullable
/*  53 */     private Integer idRequirement = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(SlhDsaParameters parameters) {
/*  59 */       this.parameters = parameters;
/*  60 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setSerializedPublicKey(Bytes serializedPublicKey) {
/*  65 */       this.serializedPublicKey = serializedPublicKey;
/*  66 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(@Nullable Integer idRequirement) {
/*  71 */       this.idRequirement = idRequirement;
/*  72 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private Bytes getOutputPrefix() {
/*  77 */       if (this.parameters.getVariant() == SlhDsaParameters.Variant.NO_PREFIX) {
/*  78 */         return OutputPrefixUtil.EMPTY_PREFIX;
/*     */       }
/*  80 */       if (this.parameters.getVariant() == SlhDsaParameters.Variant.TINK) {
/*  81 */         return OutputPrefixUtil.getTinkOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  83 */       throw new IllegalStateException("Unknown SlhDsaParameters.Variant: " + this.parameters
/*  84 */           .getVariant());
/*     */     }
/*     */     
/*     */     public SlhDsaPublicKey build() throws GeneralSecurityException {
/*  88 */       if (this.parameters == null) {
/*  89 */         throw new GeneralSecurityException("Cannot build without parameters");
/*     */       }
/*  91 */       if (this.parameters.getVariant() == SlhDsaParameters.Variant.NO_PREFIX && this.idRequirement != null) {
/*  92 */         throw new GeneralSecurityException("IdRequirement must be null for variant NO_PREFIX");
/*     */       }
/*     */       
/*  95 */       if (this.parameters.getVariant() == SlhDsaParameters.Variant.TINK && this.idRequirement == null) {
/*  96 */         throw new GeneralSecurityException("Id requirement missing for parameters' variant TINK");
/*     */       }
/*     */       
/*  99 */       if (this.serializedPublicKey == null) {
/* 100 */         throw new GeneralSecurityException("Cannot build without public key bytes");
/*     */       }
/* 102 */       if (this.parameters.getHashType() != SlhDsaParameters.HashType.SHA2) {
/* 103 */         throw new GeneralSecurityException("Unknown SLH-DSA hash type option " + this.parameters
/*     */             
/* 105 */             .getHashType() + "; only SHA2 is currently supported");
/*     */       }
/*     */       
/* 108 */       if (this.parameters.getPrivateKeySize() != 64) {
/* 109 */         throw new GeneralSecurityException("Unknown SLH-DSA private key size " + this.parameters
/*     */             
/* 111 */             .getPrivateKeySize() + "; only security level 128 (private key size 64) is currently supported");
/*     */       }
/*     */       
/* 114 */       if (this.parameters.getSignatureType() != SlhDsaParameters.SignatureType.SMALL_SIGNATURE) {
/* 115 */         throw new GeneralSecurityException("Unknown SLH-DSA signature type " + this.parameters
/*     */             
/* 117 */             .getSignatureType() + "; only \"S\" (SMALL_SIGNATURE) is currently supported");
/*     */       }
/*     */       
/* 120 */       if (this.serializedPublicKey.size() != 32) {
/* 121 */         throw new GeneralSecurityException("Incorrect public key size for SLH-DSA-SHA2-128S: should be 32, but was " + this.serializedPublicKey
/*     */ 
/*     */ 
/*     */             
/* 125 */             .size());
/*     */       }
/*     */       
/* 128 */       Bytes outputPrefix = getOutputPrefix();
/* 129 */       return new SlhDsaPublicKey(this.parameters, this.serializedPublicKey, outputPrefix, this.idRequirement);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 139 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public Bytes getSerializedPublicKey() {
/* 148 */     return this.serializedPublicKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 153 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public SlhDsaParameters getParameters() {
/* 158 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 164 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 169 */     if (!(o instanceof SlhDsaPublicKey)) {
/* 170 */       return false;
/*     */     }
/* 172 */     SlhDsaPublicKey that = (SlhDsaPublicKey)o;
/* 173 */     return (that.parameters.equals(this.parameters) && that.serializedPublicKey
/* 174 */       .equals(this.serializedPublicKey) && 
/* 175 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SlhDsaPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */