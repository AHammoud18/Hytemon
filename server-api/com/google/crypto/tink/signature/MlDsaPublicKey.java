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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MlDsaPublicKey
/*     */   extends SignaturePublicKey
/*     */ {
/*     */   private static final int MLDSA65_PUBLIC_KEY_BYTES = 1952;
/*     */   private final MlDsaParameters parameters;
/*     */   private final Bytes serializedPublicKey;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private MlDsaPublicKey(MlDsaParameters parameters, Bytes serializedPublicKey, Bytes outputPrefix, @Nullable Integer idRequirement) {
/*  47 */     this.parameters = parameters;
/*  48 */     this.serializedPublicKey = serializedPublicKey;
/*  49 */     this.outputPrefix = outputPrefix;
/*  50 */     this.idRequirement = idRequirement;
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     @Nullable
/*  55 */     private MlDsaParameters parameters = null; @Nullable
/*  56 */     private Bytes serializedPublicKey = null; @Nullable
/*  57 */     private Integer idRequirement = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(MlDsaParameters parameters) {
/*  63 */       this.parameters = parameters;
/*  64 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setSerializedPublicKey(Bytes serializedPublicKey) {
/*  69 */       this.serializedPublicKey = serializedPublicKey;
/*  70 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(@Nullable Integer idRequirement) {
/*  75 */       this.idRequirement = idRequirement;
/*  76 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private Bytes getOutputPrefix() {
/*  81 */       if (this.parameters.getVariant() == MlDsaParameters.Variant.NO_PREFIX) {
/*  82 */         return OutputPrefixUtil.EMPTY_PREFIX;
/*     */       }
/*  84 */       if (this.parameters.getVariant() == MlDsaParameters.Variant.TINK) {
/*  85 */         return OutputPrefixUtil.getTinkOutputPrefix(this.idRequirement.intValue());
/*     */       }
/*  87 */       throw new IllegalStateException("Unknown MlDsaParameters.Variant: " + this.parameters
/*  88 */           .getVariant());
/*     */     }
/*     */     
/*     */     public MlDsaPublicKey build() throws GeneralSecurityException {
/*  92 */       if (this.parameters == null) {
/*  93 */         throw new GeneralSecurityException("Cannot build without parameters");
/*     */       }
/*  95 */       if (this.parameters.getVariant() == MlDsaParameters.Variant.NO_PREFIX && this.idRequirement != null) {
/*  96 */         throw new GeneralSecurityException("Id requirement present for parameters' variant NO_PREFIX");
/*     */       }
/*     */       
/*  99 */       if (this.parameters.getVariant() == MlDsaParameters.Variant.TINK && this.idRequirement == null) {
/* 100 */         throw new GeneralSecurityException("Id requirement missing for parameters' variant TINK");
/*     */       }
/*     */       
/* 103 */       if (this.serializedPublicKey == null) {
/* 104 */         throw new GeneralSecurityException("Cannot build without public key bytes");
/*     */       }
/* 106 */       if (this.parameters.getMlDsaInstance() != MlDsaParameters.MlDsaInstance.ML_DSA_65) {
/* 107 */         throw new GeneralSecurityException("Unknown ML-DSA instance; only ML-DSA-65 is currently supported");
/*     */       }
/*     */       
/* 110 */       if (this.serializedPublicKey.size() != 1952) {
/* 111 */         throw new GeneralSecurityException("Incorrect public key size for ML-DSA-65");
/*     */       }
/*     */       
/* 114 */       Bytes outputPrefix = getOutputPrefix();
/* 115 */       return new MlDsaPublicKey(this.parameters, this.serializedPublicKey, outputPrefix, this.idRequirement);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 125 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public Bytes getSerializedPublicKey() {
/* 134 */     return this.serializedPublicKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 139 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public MlDsaParameters getParameters() {
/* 144 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 150 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 155 */     if (!(o instanceof MlDsaPublicKey)) {
/* 156 */       return false;
/*     */     }
/* 158 */     MlDsaPublicKey that = (MlDsaPublicKey)o;
/* 159 */     return (that.parameters.equals(this.parameters) && that.serializedPublicKey
/* 160 */       .equals(this.serializedPublicKey) && 
/* 161 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\MlDsaPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */