/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.signature.EcdsaParameters;
/*     */ import com.google.crypto.tink.signature.EcdsaPublicKey;
/*     */ import com.google.crypto.tink.subtle.Base64;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECPoint;
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
/*     */ @Immutable
/*     */ public final class JwtEcdsaPublicKey
/*     */   extends JwtSignaturePublicKey
/*     */ {
/*     */   private final JwtEcdsaParameters parameters;
/*     */   private final EcdsaPublicKey ecdsaPublicKey;
/*     */   private final Optional<String> kid;
/*     */   private final Optional<Integer> idRequirement;
/*     */   
/*     */   public static class Builder
/*     */   {
/*  45 */     private Optional<JwtEcdsaParameters> parameters = Optional.empty();
/*  46 */     private Optional<ECPoint> publicPoint = Optional.empty();
/*  47 */     private Optional<Integer> idRequirement = Optional.empty();
/*  48 */     private Optional<String> customKid = Optional.empty();
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(JwtEcdsaParameters parameters) {
/*  54 */       this.parameters = Optional.of(parameters);
/*  55 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicPoint(ECPoint publicPoint) {
/*  60 */       this.publicPoint = Optional.of(publicPoint);
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
/*  77 */       if (((JwtEcdsaParameters)this.parameters
/*  78 */         .get())
/*  79 */         .getKidStrategy()
/*  80 */         .equals(JwtEcdsaParameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/*  81 */         if (this.customKid.isPresent()) {
/*  82 */           throw new GeneralSecurityException("customKid must not be set for KidStrategy BASE64_ENCODED_KEY_ID");
/*     */         }
/*     */         
/*  85 */         byte[] bigEndianKeyId = ByteBuffer.allocate(4).putInt(((Integer)this.idRequirement.get()).intValue()).array();
/*  86 */         return Optional.of(Base64.urlSafeEncode(bigEndianKeyId));
/*     */       } 
/*  88 */       if (((JwtEcdsaParameters)this.parameters.get()).getKidStrategy().equals(JwtEcdsaParameters.KidStrategy.CUSTOM)) {
/*  89 */         if (!this.customKid.isPresent()) {
/*  90 */           throw new GeneralSecurityException("customKid needs to be set for KidStrategy CUSTOM");
/*     */         }
/*  92 */         return this.customKid;
/*     */       } 
/*  94 */       if (((JwtEcdsaParameters)this.parameters.get()).getKidStrategy().equals(JwtEcdsaParameters.KidStrategy.IGNORED)) {
/*  95 */         if (this.customKid.isPresent()) {
/*  96 */           throw new GeneralSecurityException("customKid must not be set for KidStrategy IGNORED");
/*     */         }
/*  98 */         return Optional.empty();
/*     */       } 
/* 100 */       throw new IllegalStateException("Unknown kid strategy");
/*     */     }
/*     */ 
/*     */     
/*     */     private static EcdsaParameters.CurveType getCurveType(JwtEcdsaParameters parameters) throws GeneralSecurityException {
/* 105 */       if (parameters.getAlgorithm().equals(JwtEcdsaParameters.Algorithm.ES256)) {
/* 106 */         return EcdsaParameters.CurveType.NIST_P256;
/*     */       }
/* 108 */       if (parameters.getAlgorithm().equals(JwtEcdsaParameters.Algorithm.ES384)) {
/* 109 */         return EcdsaParameters.CurveType.NIST_P384;
/*     */       }
/* 111 */       if (parameters.getAlgorithm().equals(JwtEcdsaParameters.Algorithm.ES512)) {
/* 112 */         return EcdsaParameters.CurveType.NIST_P521;
/*     */       }
/* 114 */       throw new GeneralSecurityException("unknown algorithm in parameters: " + parameters);
/*     */     }
/*     */ 
/*     */     
/*     */     private static EcdsaParameters.HashType getHashType(JwtEcdsaParameters parameters) throws GeneralSecurityException {
/* 119 */       if (parameters.getAlgorithm().equals(JwtEcdsaParameters.Algorithm.ES256)) {
/* 120 */         return EcdsaParameters.HashType.SHA256;
/*     */       }
/* 122 */       if (parameters.getAlgorithm().equals(JwtEcdsaParameters.Algorithm.ES384)) {
/* 123 */         return EcdsaParameters.HashType.SHA384;
/*     */       }
/* 125 */       if (parameters.getAlgorithm().equals(JwtEcdsaParameters.Algorithm.ES512)) {
/* 126 */         return EcdsaParameters.HashType.SHA512;
/*     */       }
/* 128 */       throw new GeneralSecurityException("unknown algorithm in parameters: " + parameters);
/*     */     }
/*     */     
/*     */     @AccessesPartialKey
/*     */     public JwtEcdsaPublicKey build() throws GeneralSecurityException {
/* 133 */       if (!this.parameters.isPresent()) {
/* 134 */         throw new GeneralSecurityException("Cannot build without parameters");
/*     */       }
/* 136 */       if (!this.publicPoint.isPresent()) {
/* 137 */         throw new GeneralSecurityException("Cannot build without public point");
/*     */       }
/* 139 */       if (((JwtEcdsaParameters)this.parameters.get()).hasIdRequirement() && !this.idRequirement.isPresent()) {
/* 140 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */       
/* 143 */       if (!((JwtEcdsaParameters)this.parameters.get()).hasIdRequirement() && this.idRequirement.isPresent()) {
/* 144 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       EcdsaParameters ecdsaParameters = EcdsaParameters.builder().setSignatureEncoding(EcdsaParameters.SignatureEncoding.IEEE_P1363).setCurveType(getCurveType(this.parameters.get())).setHashType(getHashType(this.parameters.get())).build();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 158 */       EcdsaPublicKey ecdsaPublicKey = EcdsaPublicKey.builder().setParameters(ecdsaParameters).setPublicPoint(this.publicPoint.get()).build();
/* 159 */       return new JwtEcdsaPublicKey(this.parameters.get(), ecdsaPublicKey, computeKid(), this.idRequirement);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private JwtEcdsaPublicKey(JwtEcdsaParameters parameters, EcdsaPublicKey ecdsaPublicKey, Optional<String> kid, Optional<Integer> idRequirement) {
/* 168 */     this.parameters = parameters;
/* 169 */     this.ecdsaPublicKey = ecdsaPublicKey;
/* 170 */     this.kid = kid;
/* 171 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 180 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   @AccessesPartialKey
/*     */   public ECPoint getPublicPoint() {
/* 190 */     return this.ecdsaPublicKey.getPublicPoint();
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<String> getKid() {
/* 195 */     return this.kid;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 201 */     return this.idRequirement.orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public JwtEcdsaParameters getParameters() {
/* 206 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 211 */     if (!(o instanceof JwtEcdsaPublicKey)) {
/* 212 */       return false;
/*     */     }
/* 214 */     JwtEcdsaPublicKey that = (JwtEcdsaPublicKey)o;
/* 215 */     return (that.parameters.equals(this.parameters) && that.ecdsaPublicKey
/* 216 */       .equalsKey((Key)this.ecdsaPublicKey) && that.kid
/* 217 */       .equals(this.kid));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   EcdsaPublicKey getEcdsaPublicKey() {
/* 226 */     return this.ecdsaPublicKey;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtEcdsaPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */