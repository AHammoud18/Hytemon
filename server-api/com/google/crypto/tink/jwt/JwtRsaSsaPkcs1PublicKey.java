/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1Parameters;
/*     */ import com.google.crypto.tink.signature.RsaSsaPkcs1PublicKey;
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
/*     */ 
/*     */ public final class JwtRsaSsaPkcs1PublicKey
/*     */   extends JwtSignaturePublicKey
/*     */ {
/*     */   private final JwtRsaSsaPkcs1Parameters parameters;
/*     */   private final RsaSsaPkcs1PublicKey rsaSsaPkcs1PublicKey;
/*     */   private final Optional<Integer> idRequirement;
/*     */   private final Optional<String> kid;
/*     */   
/*     */   private static RsaSsaPkcs1Parameters.HashType getHashType(JwtRsaSsaPkcs1Parameters.Algorithm algorithm) throws GeneralSecurityException {
/*  45 */     if (algorithm.equals(JwtRsaSsaPkcs1Parameters.Algorithm.RS256)) {
/*  46 */       return RsaSsaPkcs1Parameters.HashType.SHA256;
/*     */     }
/*  48 */     if (algorithm.equals(JwtRsaSsaPkcs1Parameters.Algorithm.RS384)) {
/*  49 */       return RsaSsaPkcs1Parameters.HashType.SHA384;
/*     */     }
/*  51 */     if (algorithm.equals(JwtRsaSsaPkcs1Parameters.Algorithm.RS512)) {
/*  52 */       return RsaSsaPkcs1Parameters.HashType.SHA512;
/*     */     }
/*  54 */     throw new GeneralSecurityException("unknown algorithm " + algorithm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static RsaSsaPkcs1PublicKey toRsaSsaPkcs1PublicKey(JwtRsaSsaPkcs1Parameters parameters, BigInteger modulus) throws GeneralSecurityException {
/*  66 */     RsaSsaPkcs1Parameters rsaSsaPkcs1Parameters = RsaSsaPkcs1Parameters.builder().setModulusSizeBits(parameters.getModulusSizeBits()).setPublicExponent(parameters.getPublicExponent()).setHashType(getHashType(parameters.getAlgorithm())).setVariant(RsaSsaPkcs1Parameters.Variant.NO_PREFIX).build();
/*  67 */     return RsaSsaPkcs1PublicKey.builder()
/*  68 */       .setParameters(rsaSsaPkcs1Parameters)
/*  69 */       .setModulus(modulus)
/*  70 */       .build();
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */   {
/*  75 */     private Optional<JwtRsaSsaPkcs1Parameters> parameters = Optional.empty();
/*  76 */     private Optional<BigInteger> modulus = Optional.empty();
/*  77 */     private Optional<Integer> idRequirement = Optional.empty();
/*  78 */     private Optional<String> customKid = Optional.empty();
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(JwtRsaSsaPkcs1Parameters parameters) {
/*  84 */       this.parameters = Optional.of(parameters);
/*  85 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setModulus(BigInteger modulus) {
/*  90 */       this.modulus = Optional.of(modulus);
/*  91 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(Integer idRequirement) {
/*  96 */       this.idRequirement = Optional.of(idRequirement);
/*  97 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setCustomKid(String customKid) {
/* 102 */       this.customKid = Optional.of(customKid);
/* 103 */       return this;
/*     */     }
/*     */     
/*     */     private Optional<String> computeKid() throws GeneralSecurityException {
/* 107 */       if (((JwtRsaSsaPkcs1Parameters)this.parameters
/* 108 */         .get())
/* 109 */         .getKidStrategy()
/* 110 */         .equals(JwtRsaSsaPkcs1Parameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/* 111 */         if (this.customKid.isPresent()) {
/* 112 */           throw new GeneralSecurityException("customKid must not be set for KidStrategy BASE64_ENCODED_KEY_ID");
/*     */         }
/*     */         
/* 115 */         byte[] bigEndianKeyId = ByteBuffer.allocate(4).putInt(((Integer)this.idRequirement.get()).intValue()).array();
/* 116 */         return Optional.of(Base64.urlSafeEncode(bigEndianKeyId));
/*     */       } 
/* 118 */       if (((JwtRsaSsaPkcs1Parameters)this.parameters.get()).getKidStrategy().equals(JwtRsaSsaPkcs1Parameters.KidStrategy.CUSTOM)) {
/* 119 */         if (!this.customKid.isPresent()) {
/* 120 */           throw new GeneralSecurityException("customKid needs to be set for KidStrategy CUSTOM");
/*     */         }
/* 122 */         return this.customKid;
/*     */       } 
/* 124 */       if (((JwtRsaSsaPkcs1Parameters)this.parameters.get()).getKidStrategy().equals(JwtRsaSsaPkcs1Parameters.KidStrategy.IGNORED)) {
/* 125 */         if (this.customKid.isPresent()) {
/* 126 */           throw new GeneralSecurityException("customKid must not be set for KidStrategy IGNORED");
/*     */         }
/* 128 */         return Optional.empty();
/*     */       } 
/* 130 */       throw new IllegalStateException("Unknown kid strategy");
/*     */     }
/*     */     
/*     */     public JwtRsaSsaPkcs1PublicKey build() throws GeneralSecurityException {
/* 134 */       if (!this.parameters.isPresent()) {
/* 135 */         throw new GeneralSecurityException("Cannot build without parameters");
/*     */       }
/*     */       
/* 138 */       if (!this.modulus.isPresent()) {
/* 139 */         throw new GeneralSecurityException("Cannot build without modulus");
/*     */       }
/*     */       
/* 142 */       RsaSsaPkcs1PublicKey rsaSsaPkcs1PublicKey = JwtRsaSsaPkcs1PublicKey.toRsaSsaPkcs1PublicKey(this.parameters.get(), this.modulus.get());
/*     */       
/* 144 */       if (((JwtRsaSsaPkcs1Parameters)this.parameters.get()).hasIdRequirement() && !this.idRequirement.isPresent()) {
/* 145 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */ 
/*     */       
/* 149 */       if (!((JwtRsaSsaPkcs1Parameters)this.parameters.get()).hasIdRequirement() && this.idRequirement.isPresent()) {
/* 150 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */ 
/*     */       
/* 154 */       return new JwtRsaSsaPkcs1PublicKey(this.parameters
/* 155 */           .get(), rsaSsaPkcs1PublicKey, this.idRequirement, computeKid());
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private JwtRsaSsaPkcs1PublicKey(JwtRsaSsaPkcs1Parameters parameters, RsaSsaPkcs1PublicKey rsaSsaPkcs1PublicKey, Optional<Integer> idRequirement, Optional<String> kid) {
/* 164 */     this.parameters = parameters;
/* 165 */     this.rsaSsaPkcs1PublicKey = rsaSsaPkcs1PublicKey;
/* 166 */     this.idRequirement = idRequirement;
/* 167 */     this.kid = kid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 176 */     return new Builder();
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
/* 187 */     return this.rsaSsaPkcs1PublicKey.getModulus();
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
/* 201 */     return this.kid;
/*     */   }
/*     */ 
/*     */   
/*     */   public JwtRsaSsaPkcs1Parameters getParameters() {
/* 206 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 212 */     return this.idRequirement.orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 217 */     if (!(o instanceof JwtRsaSsaPkcs1PublicKey)) {
/* 218 */       return false;
/*     */     }
/* 220 */     JwtRsaSsaPkcs1PublicKey that = (JwtRsaSsaPkcs1PublicKey)o;
/* 221 */     return (that.parameters.equals(this.parameters) && that.rsaSsaPkcs1PublicKey
/* 222 */       .equalsKey((Key)this.rsaSsaPkcs1PublicKey) && that.kid
/* 223 */       .equals(this.kid) && that.idRequirement
/* 224 */       .equals(this.idRequirement));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   RsaSsaPkcs1PublicKey getRsaSsaPkcs1PublicKey() {
/* 233 */     return this.rsaSsaPkcs1PublicKey;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPkcs1PublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */