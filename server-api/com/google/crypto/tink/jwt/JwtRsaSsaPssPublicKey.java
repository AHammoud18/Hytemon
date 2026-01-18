/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssParameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPssPublicKey;
/*     */ import com.google.crypto.tink.subtle.Base64;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Optional;
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
/*     */ public final class JwtRsaSsaPssPublicKey
/*     */   extends JwtSignaturePublicKey
/*     */ {
/*     */   private final JwtRsaSsaPssParameters parameters;
/*     */   private final RsaSsaPssPublicKey rsaSsaPssPublicKey;
/*     */   private final Optional<Integer> idRequirement;
/*     */   private final Optional<String> kid;
/*     */   
/*     */   public static class Builder
/*     */   {
/*  45 */     private Optional<JwtRsaSsaPssParameters> parameters = Optional.empty();
/*  46 */     private Optional<BigInteger> modulus = Optional.empty();
/*  47 */     private Optional<Integer> idRequirement = Optional.empty();
/*  48 */     private Optional<String> customKid = Optional.empty();
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(JwtRsaSsaPssParameters parameters) {
/*  54 */       this.parameters = Optional.of(parameters);
/*  55 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setModulus(BigInteger modulus) {
/*  60 */       this.modulus = Optional.of(modulus);
/*  61 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(Integer idRequirement) {
/*  66 */       this.idRequirement = Optional.of(idRequirement);
/*  67 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setCustomKid(String customKid) {
/*  72 */       this.customKid = Optional.of(customKid);
/*  73 */       return this;
/*     */     }
/*     */     
/*     */     private Optional<String> computeKid() throws GeneralSecurityException {
/*  77 */       if (((JwtRsaSsaPssParameters)this.parameters
/*  78 */         .get())
/*  79 */         .getKidStrategy()
/*  80 */         .equals(JwtRsaSsaPssParameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/*  81 */         if (this.customKid.isPresent()) {
/*  82 */           throw new GeneralSecurityException("customKid must not be set for KidStrategy BASE64_ENCODED_KEY_ID");
/*     */         }
/*     */         
/*  85 */         byte[] bigEndianKeyId = ByteBuffer.allocate(4).putInt(((Integer)this.idRequirement.get()).intValue()).array();
/*  86 */         return Optional.of(Base64.urlSafeEncode(bigEndianKeyId));
/*     */       } 
/*  88 */       if (((JwtRsaSsaPssParameters)this.parameters.get()).getKidStrategy().equals(JwtRsaSsaPssParameters.KidStrategy.CUSTOM)) {
/*  89 */         if (!this.customKid.isPresent()) {
/*  90 */           throw new GeneralSecurityException("customKid needs to be set for KidStrategy CUSTOM");
/*     */         }
/*  92 */         return this.customKid;
/*     */       } 
/*  94 */       if (((JwtRsaSsaPssParameters)this.parameters.get()).getKidStrategy().equals(JwtRsaSsaPssParameters.KidStrategy.IGNORED)) {
/*  95 */         if (this.customKid.isPresent()) {
/*  96 */           throw new GeneralSecurityException("customKid must not be set for KidStrategy IGNORED");
/*     */         }
/*  98 */         return Optional.empty();
/*     */       } 
/* 100 */       throw new IllegalStateException("Unknown kid strategy");
/*     */     }
/*     */ 
/*     */     
/*     */     private static RsaSsaPssParameters.HashType getHashType(JwtRsaSsaPssParameters.Algorithm algorithm) throws GeneralSecurityException {
/* 105 */       if (algorithm.equals(JwtRsaSsaPssParameters.Algorithm.PS256)) {
/* 106 */         return RsaSsaPssParameters.HashType.SHA256;
/*     */       }
/* 108 */       if (algorithm.equals(JwtRsaSsaPssParameters.Algorithm.PS384)) {
/* 109 */         return RsaSsaPssParameters.HashType.SHA384;
/*     */       }
/* 111 */       if (algorithm.equals(JwtRsaSsaPssParameters.Algorithm.PS512)) {
/* 112 */         return RsaSsaPssParameters.HashType.SHA512;
/*     */       }
/* 114 */       throw new GeneralSecurityException("unknown algorithm " + algorithm);
/*     */     }
/*     */ 
/*     */     
/*     */     private static int getSaltLengthBytes(JwtRsaSsaPssParameters.Algorithm algorithm) throws GeneralSecurityException {
/* 119 */       if (algorithm.equals(JwtRsaSsaPssParameters.Algorithm.PS256)) {
/* 120 */         return 32;
/*     */       }
/* 122 */       if (algorithm.equals(JwtRsaSsaPssParameters.Algorithm.PS384)) {
/* 123 */         return 48;
/*     */       }
/* 125 */       if (algorithm.equals(JwtRsaSsaPssParameters.Algorithm.PS512)) {
/* 126 */         return 64;
/*     */       }
/* 128 */       throw new GeneralSecurityException("unknown algorithm " + algorithm);
/*     */     }
/*     */     
/*     */     @AccessesPartialKey
/*     */     public JwtRsaSsaPssPublicKey build() throws GeneralSecurityException {
/* 133 */       if (!this.parameters.isPresent()) {
/* 134 */         throw new GeneralSecurityException("Cannot build without parameters");
/*     */       }
/*     */       
/* 137 */       if (!this.modulus.isPresent()) {
/* 138 */         throw new GeneralSecurityException("Cannot build without modulus");
/*     */       }
/*     */       
/* 141 */       RsaSsaPssParameters.HashType hashType = getHashType(((JwtRsaSsaPssParameters)this.parameters.get()).getAlgorithm());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       RsaSsaPssParameters rsaSsaPssParameters = RsaSsaPssParameters.builder().setModulusSizeBits(((JwtRsaSsaPssParameters)this.parameters.get()).getModulusSizeBits()).setPublicExponent(((JwtRsaSsaPssParameters)this.parameters.get()).getPublicExponent()).setSigHashType(hashType).setMgf1HashType(hashType).setSaltLengthBytes(getSaltLengthBytes(((JwtRsaSsaPssParameters)this.parameters.get()).getAlgorithm())).setVariant(RsaSsaPssParameters.Variant.NO_PREFIX).build();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       RsaSsaPssPublicKey rsaSsaPssPublicKey = RsaSsaPssPublicKey.builder().setParameters(rsaSsaPssParameters).setModulus(this.modulus.get()).build();
/*     */       
/* 157 */       if (((JwtRsaSsaPssParameters)this.parameters.get()).hasIdRequirement() && !this.idRequirement.isPresent()) {
/* 158 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */ 
/*     */       
/* 162 */       if (!((JwtRsaSsaPssParameters)this.parameters.get()).hasIdRequirement() && this.idRequirement.isPresent()) {
/* 163 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */ 
/*     */       
/* 167 */       return new JwtRsaSsaPssPublicKey(this.parameters
/* 168 */           .get(), rsaSsaPssPublicKey, this.idRequirement, computeKid());
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private JwtRsaSsaPssPublicKey(JwtRsaSsaPssParameters parameters, RsaSsaPssPublicKey rsaSsaPssPublicKey, Optional<Integer> idRequirement, Optional<String> kid) {
/* 177 */     this.parameters = parameters;
/* 178 */     this.rsaSsaPssPublicKey = rsaSsaPssPublicKey;
/* 179 */     this.idRequirement = idRequirement;
/* 180 */     this.kid = kid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 189 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   @AccessesPartialKey
/*     */   public BigInteger getModulus() {
/* 200 */     return this.rsaSsaPssPublicKey.getModulus();
/*     */   }
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
/*     */   public Optional<String> getKid() {
/* 214 */     return this.kid;
/*     */   }
/*     */ 
/*     */   
/*     */   public JwtRsaSsaPssParameters getParameters() {
/* 219 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 225 */     return this.idRequirement.orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 230 */     if (!(o instanceof JwtRsaSsaPssPublicKey)) {
/* 231 */       return false;
/*     */     }
/* 233 */     JwtRsaSsaPssPublicKey that = (JwtRsaSsaPssPublicKey)o;
/* 234 */     return (that.parameters.equals(this.parameters) && that.rsaSsaPssPublicKey
/* 235 */       .equalsKey((Key)this.rsaSsaPssPublicKey) && that.kid
/* 236 */       .equals(this.kid) && that.idRequirement
/* 237 */       .equals(this.idRequirement));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   RsaSsaPssPublicKey getRsaSsaPssPublicKey() {
/* 246 */     return this.rsaSsaPssPublicKey;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPssPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */