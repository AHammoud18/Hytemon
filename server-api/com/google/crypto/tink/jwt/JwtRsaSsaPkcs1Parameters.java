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
/*     */ 
/*     */ public final class JwtRsaSsaPkcs1Parameters
/*     */   extends JwtSignatureParameters
/*     */ {
/*     */   @Immutable
/*     */   public static final class KidStrategy
/*     */   {
/*  48 */     public static final KidStrategy BASE64_ENCODED_KEY_ID = new KidStrategy("BASE64_ENCODED_KEY_ID");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     public static final KidStrategy IGNORED = new KidStrategy("IGNORED");
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
/*  72 */     public static final KidStrategy CUSTOM = new KidStrategy("CUSTOM");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private KidStrategy(String name) {
/*  77 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  82 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   public static final class Algorithm
/*     */   {
/*  90 */     public static final Algorithm RS256 = new Algorithm("RS256");
/*     */ 
/*     */     
/*  93 */     public static final Algorithm RS384 = new Algorithm("RS384");
/*     */ 
/*     */     
/*  96 */     public static final Algorithm RS512 = new Algorithm("RS512");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Algorithm(String name) {
/* 101 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 106 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getStandardName() {
/* 110 */       return this.name;
/*     */     } } private final int modulusSizeBits;
/*     */   private final BigInteger publicExponent;
/*     */   private final KidStrategy kidStrategy;
/* 114 */   public static final BigInteger F4 = BigInteger.valueOf(65537L);
/*     */   private final Algorithm algorithm;
/*     */   
/*     */   public static final class Builder {
/* 118 */     Optional<Integer> modulusSizeBits = Optional.empty();
/* 119 */     Optional<BigInteger> publicExponent = Optional.of(JwtRsaSsaPkcs1Parameters.F4);
/* 120 */     Optional<JwtRsaSsaPkcs1Parameters.KidStrategy> kidStrategy = Optional.empty();
/* 121 */     Optional<JwtRsaSsaPkcs1Parameters.Algorithm> algorithm = Optional.empty();
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setModulusSizeBits(int modulusSizeBits) {
/* 127 */       this.modulusSizeBits = Optional.of(Integer.valueOf(modulusSizeBits));
/* 128 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicExponent(BigInteger e) {
/* 133 */       this.publicExponent = Optional.of(e);
/* 134 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKidStrategy(JwtRsaSsaPkcs1Parameters.KidStrategy kidStrategy) {
/* 139 */       this.kidStrategy = Optional.of(kidStrategy);
/* 140 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAlgorithm(JwtRsaSsaPkcs1Parameters.Algorithm algorithm) {
/* 145 */       this.algorithm = Optional.of(algorithm);
/* 146 */       return this;
/*     */     }
/*     */     
/* 149 */     private static final BigInteger TWO = BigInteger.valueOf(2L);
/* 150 */     private static final BigInteger PUBLIC_EXPONENT_UPPER_BOUND = TWO.pow(256);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void validatePublicExponent(BigInteger publicExponent) throws InvalidAlgorithmParameterException {
/* 156 */       int c = publicExponent.compareTo(JwtRsaSsaPkcs1Parameters.F4);
/* 157 */       if (c == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 161 */       if (c < 0)
/*     */       {
/* 163 */         throw new InvalidAlgorithmParameterException("Public exponent must be at least 65537.");
/*     */       }
/* 165 */       if (publicExponent.mod(TWO).equals(BigInteger.ZERO))
/*     */       {
/* 167 */         throw new InvalidAlgorithmParameterException("Invalid public exponent");
/*     */       }
/* 169 */       if (publicExponent.compareTo(PUBLIC_EXPONENT_UPPER_BOUND) > 0)
/*     */       {
/* 171 */         throw new InvalidAlgorithmParameterException("Public exponent cannot be larger than 2^256.");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public JwtRsaSsaPkcs1Parameters build() throws GeneralSecurityException {
/* 177 */       if (!this.modulusSizeBits.isPresent()) {
/* 178 */         throw new GeneralSecurityException("key size is not set");
/*     */       }
/* 180 */       if (!this.publicExponent.isPresent()) {
/* 181 */         throw new GeneralSecurityException("publicExponent is not set");
/*     */       }
/* 183 */       if (!this.algorithm.isPresent()) {
/* 184 */         throw new GeneralSecurityException("Algorithm must be set");
/*     */       }
/* 186 */       if (!this.kidStrategy.isPresent()) {
/* 187 */         throw new GeneralSecurityException("KidStrategy must be set");
/*     */       }
/* 189 */       if (((Integer)this.modulusSizeBits.get()).intValue() < 2048)
/* 190 */         throw new InvalidAlgorithmParameterException(
/* 191 */             String.format("Invalid modulus size in bits %d; must be at least 2048 bits", new Object[] {
/*     */                 
/* 193 */                 this.modulusSizeBits.get()
/*     */               })); 
/* 195 */       validatePublicExponent(this.publicExponent.get());
/* 196 */       return new JwtRsaSsaPkcs1Parameters(((Integer)this.modulusSizeBits
/* 197 */           .get()).intValue(), this.publicExponent.get(), this.kidStrategy.get(), this.algorithm.get());
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
/*     */   private JwtRsaSsaPkcs1Parameters(int modulusSizeBits, BigInteger publicExponent, KidStrategy kidStrategy, Algorithm algorithm) {
/* 211 */     this.modulusSizeBits = modulusSizeBits;
/* 212 */     this.publicExponent = publicExponent;
/* 213 */     this.kidStrategy = kidStrategy;
/* 214 */     this.algorithm = algorithm;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 218 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getModulusSizeBits() {
/* 222 */     return this.modulusSizeBits;
/*     */   }
/*     */   
/*     */   public BigInteger getPublicExponent() {
/* 226 */     return this.publicExponent;
/*     */   }
/*     */   
/*     */   public KidStrategy getKidStrategy() {
/* 230 */     return this.kidStrategy;
/*     */   }
/*     */   
/*     */   public Algorithm getAlgorithm() {
/* 234 */     return this.algorithm;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowKidAbsent() {
/* 239 */     return (this.kidStrategy.equals(KidStrategy.CUSTOM) || this.kidStrategy.equals(KidStrategy.IGNORED));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 244 */     if (!(o instanceof JwtRsaSsaPkcs1Parameters)) {
/* 245 */       return false;
/*     */     }
/* 247 */     JwtRsaSsaPkcs1Parameters that = (JwtRsaSsaPkcs1Parameters)o;
/* 248 */     return (that.getModulusSizeBits() == getModulusSizeBits() && 
/* 249 */       Objects.equals(that.getPublicExponent(), getPublicExponent()) && that.kidStrategy
/* 250 */       .equals(this.kidStrategy) && that.algorithm
/* 251 */       .equals(this.algorithm));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 256 */     return Objects.hash(new Object[] { JwtRsaSsaPkcs1Parameters.class, 
/* 257 */           Integer.valueOf(this.modulusSizeBits), this.publicExponent, this.kidStrategy, this.algorithm });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 262 */     return this.kidStrategy.equals(KidStrategy.BASE64_ENCODED_KEY_ID);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 267 */     return "JWT RSA SSA PKCS1 Parameters (kidStrategy: " + this.kidStrategy + ", algorithm " + this.algorithm + ", publicExponent: " + this.publicExponent + ", and " + this.modulusSizeBits + "-bit modulus)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtRsaSsaPkcs1Parameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */