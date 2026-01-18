/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
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
/*     */ 
/*     */ public final class JwtEcdsaParameters
/*     */   extends JwtSignatureParameters
/*     */ {
/*     */   private final KidStrategy kidStrategy;
/*     */   private final Algorithm algorithm;
/*     */   
/*     */   @Immutable
/*     */   public static final class KidStrategy
/*     */   {
/*  43 */     public static final KidStrategy BASE64_ENCODED_KEY_ID = new KidStrategy("BASE64_ENCODED_KEY_ID");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     public static final KidStrategy IGNORED = new KidStrategy("IGNORED");
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
/*  67 */     public static final KidStrategy CUSTOM = new KidStrategy("CUSTOM");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private KidStrategy(String name) {
/*  72 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  77 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   public static final class Algorithm
/*     */   {
/*  85 */     public static final Algorithm ES256 = new Algorithm("ES256", EllipticCurvesUtil.NIST_P256_PARAMS);
/*     */ 
/*     */     
/*  88 */     public static final Algorithm ES384 = new Algorithm("ES384", EllipticCurvesUtil.NIST_P384_PARAMS);
/*     */ 
/*     */     
/*  91 */     public static final Algorithm ES512 = new Algorithm("ES512", EllipticCurvesUtil.NIST_P521_PARAMS);
/*     */ 
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final ECParameterSpec ecParameterSpec;
/*     */ 
/*     */     
/*     */     private Algorithm(String name, ECParameterSpec ecParameterSpec) {
/* 100 */       this.name = name;
/* 101 */       this.ecParameterSpec = ecParameterSpec;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 106 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getStandardName() {
/* 110 */       return this.name;
/*     */     }
/*     */     
/*     */     public ECParameterSpec getEcParameterSpec() {
/* 114 */       return this.ecParameterSpec;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/* 120 */     Optional<JwtEcdsaParameters.KidStrategy> kidStrategy = Optional.empty();
/* 121 */     Optional<JwtEcdsaParameters.Algorithm> algorithm = Optional.empty();
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKidStrategy(JwtEcdsaParameters.KidStrategy kidStrategy) {
/* 125 */       this.kidStrategy = Optional.of(kidStrategy);
/* 126 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAlgorithm(JwtEcdsaParameters.Algorithm algorithm) {
/* 131 */       this.algorithm = Optional.of(algorithm);
/* 132 */       return this;
/*     */     }
/*     */     
/*     */     public JwtEcdsaParameters build() throws GeneralSecurityException {
/* 136 */       if (!this.algorithm.isPresent()) {
/* 137 */         throw new GeneralSecurityException("Algorithm must be set");
/*     */       }
/* 139 */       if (!this.kidStrategy.isPresent()) {
/* 140 */         throw new GeneralSecurityException("KidStrategy must be set");
/*     */       }
/* 142 */       return new JwtEcdsaParameters(this.kidStrategy.get(), this.algorithm.get());
/*     */     }
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 149 */     return new Builder();
/*     */   }
/*     */   
/*     */   private JwtEcdsaParameters(KidStrategy kidStrategy, Algorithm algorithm) {
/* 153 */     this.kidStrategy = kidStrategy;
/* 154 */     this.algorithm = algorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KidStrategy getKidStrategy() {
/* 161 */     return this.kidStrategy;
/*     */   }
/*     */   
/*     */   public Algorithm getAlgorithm() {
/* 165 */     return this.algorithm;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 170 */     return this.kidStrategy.equals(KidStrategy.BASE64_ENCODED_KEY_ID);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowKidAbsent() {
/* 175 */     return (this.kidStrategy.equals(KidStrategy.CUSTOM) || this.kidStrategy.equals(KidStrategy.IGNORED));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 180 */     if (!(o instanceof JwtEcdsaParameters)) {
/* 181 */       return false;
/*     */     }
/* 183 */     JwtEcdsaParameters that = (JwtEcdsaParameters)o;
/* 184 */     return (that.kidStrategy.equals(this.kidStrategy) && that.algorithm.equals(this.algorithm));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 189 */     return Objects.hash(new Object[] { JwtEcdsaParameters.class, this.kidStrategy, this.algorithm });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 194 */     return "JWT ECDSA Parameters (kidStrategy: " + this.kidStrategy + ", Algorithm " + this.algorithm + ")";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtEcdsaParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */