/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JwtRsaSsaPssParameters
/*     */   extends JwtSignatureParameters
/*     */ {
/*     */   @Immutable
/*     */   public static final class KidStrategy
/*     */   {
/*  47 */     public static final KidStrategy BASE64_ENCODED_KEY_ID = new KidStrategy("BASE64_ENCODED_KEY_ID");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     public static final KidStrategy IGNORED = new KidStrategy("IGNORED");
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
/*  71 */     public static final KidStrategy CUSTOM = new KidStrategy("CUSTOM");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private KidStrategy(String name) {
/*  76 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  81 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   public static final class Algorithm
/*     */   {
/*  89 */     public static final Algorithm PS256 = new Algorithm("PS256");
/*     */ 
/*     */     
/*  92 */     public static final Algorithm PS384 = new Algorithm("PS384");
/*     */ 
/*     */     
/*  95 */     public static final Algorithm PS512 = new Algorithm("PS512");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Algorithm(String name) {
/* 100 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 105 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getStandardName() {
/* 109 */       return this.name;
/*     */     } } private final int modulusSizeBits;
/*     */   private final BigInteger publicExponent;
/*     */   private final KidStrategy kidStrategy;
/* 113 */   public static final BigInteger F4 = BigInteger.valueOf(65537L);
/*     */   private final Algorithm algorithm;
/*     */   
/*     */   public static final class Builder {
/* 117 */     Optional<Integer> modulusSizeBits = Optional.empty();
/* 118 */     Optional<BigInteger> publicExponent = Optional.of(JwtRsaSsaPssParameters.F4);
/* 119 */     Optional<JwtRsaSsaPssParameters.KidStrategy> kidStrategy = Optional.empty();
/* 120 */     Optional<JwtRsaSsaPssParameters.Algorithm> algorithm = Optional.empty();
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setModulusSizeBits(int modulusSizeBits) {
/* 126 */       this.modulusSizeBits = Optional.of(Integer.valueOf(modulusSizeBits));
/* 127 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicExponent(BigInteger e) {
/* 132 */       this.publicExponent = Optional.of(e);
/* 133 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKidStrategy(JwtRsaSsaPssParameters.KidStrategy kidStrategy) {
/* 138 */       this.kidStrategy = Optional.of(kidStrategy);
/* 139 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAlgorithm(JwtRsaSsaPssParameters.Algorithm algorithm) {
/* 144 */       this.algorithm = Optional.of(algorithm);
/* 145 */       return this;
/*     */     }
/*     */     
/* 148 */     private static final BigInteger TWO = BigInteger.valueOf(2L);
/* 149 */     private static final BigInteger PUBLIC_EXPONENT_UPPER_BOUND = TWO.pow(256);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void validatePublicExponent(BigInteger publicExponent) throws InvalidAlgorithmParameterException {
/* 155 */       int c = publicExponent.compareTo(JwtRsaSsaPssParameters.F4);
/* 156 */       if (c == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 160 */       if (c < 0)
/*     */       {
/* 162 */         throw new InvalidAlgorithmParameterException("Public exponent must be at least 65537.");
/*     */       }
/* 164 */       if (publicExponent.mod(TWO).equals(BigInteger.ZERO))
/*     */       {
/* 166 */         throw new InvalidAlgorithmParameterException("Invalid public exponent");
/*     */       }
/* 168 */       if (publicExponent.compareTo(PUBLIC_EXPONENT_UPPER_BOUND) > 0)
/*     */       {
/* 170 */         throw new InvalidAlgorithmParameterException("Public exponent cannot be larger than 2^256.");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public JwtRsaSsaPssParameters build() throws GeneralSecurityException {
/* 176 */       if (!this.modulusSizeBits.isPresent()) {
/* 177 */         throw new GeneralSecurityException("key size is not set");
/*     */       }
/* 179 */       if (!this.publicExponent.isPresent()) {
/* 180 */         throw new GeneralSecurityException("publicExponent is not set");
/*     */       }
/* 182 */       if (!this.algorithm.isPresent()) {
/* 183 */         throw new GeneralSecurityException("Algorithm must be set");
/*     */       }
/* 185 */       if (!this.kidStrategy.isPresent()) {
/* 186 */         throw new GeneralSecurityException("KidStrategy must be set");
/*     */       }
/* 188 */       if (((Integer)this.modulusSizeBits.get()).intValue() < 2048)
/* 189 */         throw new InvalidAlgorithmParameterException(
/* 190 */             String.format("Invalid modulus size in bits %d; must be at least 2048 bits", new Object[] {
/*     */                 
/* 192 */                 this.modulusSizeBits.get()
/*     */               })); 
/* 194 */       validatePublicExponent(this.publicExponent.get());
/* 195 */       return new JwtRsaSsaPssParameters(((Integer)this.modulusSizeBits
/* 196 */           .get()).intValue(), this.publicExponent.get(), this.kidStrategy.get(), this.algorithm.get());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JwtRsaSsaPssParameters(int modulusSizeBits, BigInteger publicExponent, KidStrategy kidStrategy, Algorithm algorithm) {
/* 210 */     this.modulusSizeBits = modulusSizeBits;
/* 211 */     this.publicExponent = publicExponent;
/* 212 */     this.kidStrategy = kidStrategy;
/* 213 */     this.algorithm = algorithm;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 217 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getModulusSizeBits() {
/* 221 */     return this.modulusSizeBits;
/*     */   }
/*     */   
/*     */   public BigInteger getPublicExponent() {
/* 225 */     return this.publicExponent;
/*     */   }
/*     */   
/*     */   public KidStrategy getKidStrategy() {
/* 229 */     return this.kidStrategy;
/*     */   }
/*     */   
/*     */   public Algorithm getAlgorithm() {
/* 233 */     return this.algorithm;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowKidAbsent() {
/* 238 */     return (this.kidStrategy.equals(KidStrategy.CUSTOM) || this.kidStrategy.equals(KidStrategy.IGNORED));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 243 */     if (!(o instanceof JwtRsaSsaPssParameters)) {
/* 244 */       return false;
/*     */     }
/* 246 */     JwtRsaSsaPssParameters that = (JwtRsaSsaPssParameters)o;
/* 247 */     return (that.getModulusSizeBits() == getModulusSizeBits() && 
/* 248 */       Objects.equals(that.getPublicExponent(), getPublicExponent()) && that.kidStrategy
/* 249 */       .equals(this.kidStrategy) && that.algorithm
/* 250 */       .equals(this.algorithm));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 255 */     return Objects.hash(new Object[] { JwtRsaSsaPssParameters.class, 
/* 256 */           Integer.valueOf(this.modulusSizeBits), this.publicExponent, this.kidStrategy, this.algorithm });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 261 */     return this.kidStrategy.equals(KidStrategy.BASE64_ENCODED_KEY_ID);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 266 */     return "JWT RSA SSA PSS Parameters (kidStrategy: " + this.kidStrategy + ", algorithm " + this.algorithm + ", publicExponent: " + this.publicExponent + ", and " + this.modulusSizeBits + "-bit modulus)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPssParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */