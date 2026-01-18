/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
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
/*     */ 
/*     */ 
/*     */ public final class RsaSsaPkcs1Parameters
/*     */   extends SignatureParameters
/*     */ {
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  41 */     public static final Variant TINK = new Variant("TINK");
/*  42 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/*  43 */     public static final Variant LEGACY = new Variant("LEGACY");
/*  44 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  49 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  54 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class HashType
/*     */   {
/*  61 */     public static final HashType SHA256 = new HashType("SHA256");
/*  62 */     public static final HashType SHA384 = new HashType("SHA384");
/*  63 */     public static final HashType SHA512 = new HashType("SHA512");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private HashType(String name) {
/*  68 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  73 */       return this.name;
/*     */     } } private final int modulusSizeBits;
/*     */   private final BigInteger publicExponent;
/*     */   private final Variant variant;
/*  77 */   public static final BigInteger F4 = BigInteger.valueOf(65537L);
/*     */   private final HashType hashType;
/*     */   
/*     */   public static final class Builder { @Nullable
/*  81 */     private Integer modulusSizeBits = null; @Nullable
/*  82 */     private BigInteger publicExponent = RsaSsaPkcs1Parameters.F4; @Nullable
/*  83 */     private RsaSsaPkcs1Parameters.HashType hashType = null;
/*  84 */     private RsaSsaPkcs1Parameters.Variant variant = RsaSsaPkcs1Parameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setModulusSizeBits(int modulusSizeBits) {
/*  90 */       this.modulusSizeBits = Integer.valueOf(modulusSizeBits);
/*  91 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicExponent(BigInteger e) {
/*  96 */       this.publicExponent = e;
/*  97 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(RsaSsaPkcs1Parameters.Variant variant) {
/* 102 */       this.variant = variant;
/* 103 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHashType(RsaSsaPkcs1Parameters.HashType hashType) {
/* 108 */       this.hashType = hashType;
/* 109 */       return this;
/*     */     }
/*     */     
/* 112 */     private static final BigInteger TWO = BigInteger.valueOf(2L);
/* 113 */     private static final BigInteger PUBLIC_EXPONENT_UPPER_BOUND = TWO.pow(256);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void validatePublicExponent(BigInteger publicExponent) throws InvalidAlgorithmParameterException {
/* 119 */       int c = publicExponent.compareTo(RsaSsaPkcs1Parameters.F4);
/* 120 */       if (c == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 124 */       if (c < 0)
/*     */       {
/* 126 */         throw new InvalidAlgorithmParameterException("Public exponent must be at least 65537.");
/*     */       }
/* 128 */       if (publicExponent.mod(TWO).equals(BigInteger.ZERO))
/*     */       {
/* 130 */         throw new InvalidAlgorithmParameterException("Invalid public exponent");
/*     */       }
/* 132 */       if (publicExponent.compareTo(PUBLIC_EXPONENT_UPPER_BOUND) > 0)
/*     */       {
/* 134 */         throw new InvalidAlgorithmParameterException("Public exponent cannot be larger than 2^256.");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public RsaSsaPkcs1Parameters build() throws GeneralSecurityException {
/* 140 */       if (this.modulusSizeBits == null) {
/* 141 */         throw new GeneralSecurityException("key size is not set");
/*     */       }
/* 143 */       if (this.publicExponent == null) {
/* 144 */         throw new GeneralSecurityException("publicExponent is not set");
/*     */       }
/* 146 */       if (this.hashType == null) {
/* 147 */         throw new GeneralSecurityException("hash type is not set");
/*     */       }
/* 149 */       if (this.variant == null) {
/* 150 */         throw new GeneralSecurityException("variant is not set");
/*     */       }
/* 152 */       if (this.modulusSizeBits.intValue() < 2048) {
/* 153 */         throw new InvalidAlgorithmParameterException(
/* 154 */             String.format("Invalid key size in bytes %d; must be at least 2048 bits", new Object[] { this.modulusSizeBits }));
/*     */       }
/*     */       
/* 157 */       validatePublicExponent(this.publicExponent);
/* 158 */       return new RsaSsaPkcs1Parameters(this.modulusSizeBits.intValue(), this.publicExponent, this.variant, this.hashType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {} }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RsaSsaPkcs1Parameters(int modulusSizeBits, BigInteger publicExponent, Variant variant, HashType hashType) {
/* 169 */     this.modulusSizeBits = modulusSizeBits;
/* 170 */     this.publicExponent = publicExponent;
/* 171 */     this.variant = variant;
/* 172 */     this.hashType = hashType;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 176 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getModulusSizeBits() {
/* 180 */     return this.modulusSizeBits;
/*     */   }
/*     */   
/*     */   public BigInteger getPublicExponent() {
/* 184 */     return this.publicExponent;
/*     */   }
/*     */   
/*     */   public Variant getVariant() {
/* 188 */     return this.variant;
/*     */   }
/*     */   
/*     */   public HashType getHashType() {
/* 192 */     return this.hashType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 197 */     if (!(o instanceof RsaSsaPkcs1Parameters)) {
/* 198 */       return false;
/*     */     }
/* 200 */     RsaSsaPkcs1Parameters that = (RsaSsaPkcs1Parameters)o;
/* 201 */     return (that.getModulusSizeBits() == getModulusSizeBits() && 
/* 202 */       Objects.equals(that.getPublicExponent(), getPublicExponent()) && that
/* 203 */       .getVariant() == getVariant() && that
/* 204 */       .getHashType() == getHashType());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 209 */     return Objects.hash(new Object[] { RsaSsaPkcs1Parameters.class, 
/* 210 */           Integer.valueOf(this.modulusSizeBits), this.publicExponent, this.variant, this.hashType });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 215 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 220 */     return "RSA SSA PKCS1 Parameters (variant: " + this.variant + ", hashType: " + this.hashType + ", publicExponent: " + this.publicExponent + ", and " + this.modulusSizeBits + "-bit modulus)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\RsaSsaPkcs1Parameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */