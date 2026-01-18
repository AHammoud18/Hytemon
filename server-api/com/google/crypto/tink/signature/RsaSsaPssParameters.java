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
/*     */ public final class RsaSsaPssParameters
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
/*  77 */   public static final BigInteger F4 = BigInteger.valueOf(65537L); private final HashType sigHashType; private final HashType mgf1HashType;
/*     */   private final int saltLengthBytes;
/*     */   
/*     */   public static final class Builder { @Nullable
/*  81 */     private Integer modulusSizeBits = null; @Nullable
/*  82 */     private BigInteger publicExponent = RsaSsaPssParameters.F4; @Nullable
/*  83 */     private RsaSsaPssParameters.HashType sigHashType = null; @Nullable
/*  84 */     private RsaSsaPssParameters.HashType mgf1HashType = null; @Nullable
/*  85 */     private Integer saltLengthBytes = null;
/*  86 */     private RsaSsaPssParameters.Variant variant = RsaSsaPssParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setModulusSizeBits(int modulusSizeBits) {
/*  92 */       this.modulusSizeBits = Integer.valueOf(modulusSizeBits);
/*  93 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPublicExponent(BigInteger e) {
/*  98 */       this.publicExponent = e;
/*  99 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(RsaSsaPssParameters.Variant variant) {
/* 104 */       this.variant = variant;
/* 105 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setSigHashType(RsaSsaPssParameters.HashType sigHashType) {
/* 110 */       this.sigHashType = sigHashType;
/* 111 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setMgf1HashType(RsaSsaPssParameters.HashType mgf1HashType) {
/* 116 */       this.mgf1HashType = mgf1HashType;
/* 117 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setSaltLengthBytes(int saltLengthBytes) throws GeneralSecurityException {
/* 122 */       if (saltLengthBytes < 0)
/* 123 */         throw new GeneralSecurityException(
/* 124 */             String.format("Invalid salt length in bytes %d; salt length must be positive", new Object[] {
/* 125 */                 Integer.valueOf(saltLengthBytes)
/*     */               })); 
/* 127 */       this.saltLengthBytes = Integer.valueOf(saltLengthBytes);
/* 128 */       return this;
/*     */     }
/*     */     
/* 131 */     private static final BigInteger TWO = BigInteger.valueOf(2L);
/* 132 */     private static final BigInteger PUBLIC_EXPONENT_UPPER_BOUND = TWO.pow(256);
/*     */ 
/*     */     
/*     */     private static final int MIN_RSA_MODULUS_SIZE = 2048;
/*     */ 
/*     */     
/*     */     private void validatePublicExponent(BigInteger publicExponent) throws InvalidAlgorithmParameterException {
/* 139 */       int c = publicExponent.compareTo(RsaSsaPssParameters.F4);
/* 140 */       if (c == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 144 */       if (c < 0)
/*     */       {
/* 146 */         throw new InvalidAlgorithmParameterException("Public exponent must be at least 65537.");
/*     */       }
/* 148 */       if (publicExponent.mod(TWO).equals(BigInteger.ZERO))
/*     */       {
/* 150 */         throw new InvalidAlgorithmParameterException("Invalid public exponent");
/*     */       }
/* 152 */       if (publicExponent.compareTo(PUBLIC_EXPONENT_UPPER_BOUND) > 0)
/*     */       {
/* 154 */         throw new InvalidAlgorithmParameterException("Public exponent cannot be larger than 2^256.");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public RsaSsaPssParameters build() throws GeneralSecurityException {
/* 160 */       if (this.modulusSizeBits == null) {
/* 161 */         throw new GeneralSecurityException("key size is not set");
/*     */       }
/* 163 */       if (this.publicExponent == null) {
/* 164 */         throw new GeneralSecurityException("publicExponent is not set");
/*     */       }
/* 166 */       if (this.sigHashType == null) {
/* 167 */         throw new GeneralSecurityException("signature hash type is not set");
/*     */       }
/* 169 */       if (this.mgf1HashType == null) {
/* 170 */         throw new GeneralSecurityException("mgf1 hash type is not set");
/*     */       }
/* 172 */       if (this.variant == null) {
/* 173 */         throw new GeneralSecurityException("variant is not set");
/*     */       }
/* 175 */       if (this.saltLengthBytes == null) {
/* 176 */         throw new GeneralSecurityException("salt length is not set");
/*     */       }
/* 178 */       if (this.modulusSizeBits.intValue() < 2048)
/* 179 */         throw new InvalidAlgorithmParameterException(
/* 180 */             String.format("Invalid key size in bytes %d; must be at least %d bits", new Object[] {
/*     */                 
/* 182 */                 this.modulusSizeBits, Integer.valueOf(2048)
/*     */               })); 
/* 184 */       if (this.sigHashType != this.mgf1HashType) {
/* 185 */         throw new GeneralSecurityException("MGF1 hash is different from signature hash");
/*     */       }
/* 187 */       validatePublicExponent(this.publicExponent);
/* 188 */       return new RsaSsaPssParameters(this.modulusSizeBits
/* 189 */           .intValue(), this.publicExponent, this.variant, this.sigHashType, this.mgf1HashType, this.saltLengthBytes.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {} }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RsaSsaPssParameters(int modulusSizeBits, BigInteger publicExponent, Variant variant, HashType sigHashType, HashType mgf1HashType, int saltLengthBytes) {
/* 207 */     this.modulusSizeBits = modulusSizeBits;
/* 208 */     this.publicExponent = publicExponent;
/* 209 */     this.variant = variant;
/* 210 */     this.sigHashType = sigHashType;
/* 211 */     this.mgf1HashType = mgf1HashType;
/* 212 */     this.saltLengthBytes = saltLengthBytes;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 216 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getModulusSizeBits() {
/* 220 */     return this.modulusSizeBits;
/*     */   }
/*     */   
/*     */   public BigInteger getPublicExponent() {
/* 224 */     return this.publicExponent;
/*     */   }
/*     */   
/*     */   public Variant getVariant() {
/* 228 */     return this.variant;
/*     */   }
/*     */   
/*     */   public HashType getSigHashType() {
/* 232 */     return this.sigHashType;
/*     */   }
/*     */   
/*     */   public HashType getMgf1HashType() {
/* 236 */     return this.mgf1HashType;
/*     */   }
/*     */   
/*     */   public int getSaltLengthBytes() {
/* 240 */     return this.saltLengthBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 245 */     if (!(o instanceof RsaSsaPssParameters)) {
/* 246 */       return false;
/*     */     }
/* 248 */     RsaSsaPssParameters that = (RsaSsaPssParameters)o;
/* 249 */     return (that.getModulusSizeBits() == getModulusSizeBits() && 
/* 250 */       Objects.equals(that.getPublicExponent(), getPublicExponent()) && 
/* 251 */       Objects.equals(that.getVariant(), getVariant()) && 
/* 252 */       Objects.equals(that.getSigHashType(), getSigHashType()) && 
/* 253 */       Objects.equals(that.getMgf1HashType(), getMgf1HashType()) && that
/* 254 */       .getSaltLengthBytes() == getSaltLengthBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 259 */     return Objects.hash(new Object[] { RsaSsaPssParameters.class, 
/*     */           
/* 261 */           Integer.valueOf(this.modulusSizeBits), this.publicExponent, this.variant, this.sigHashType, this.mgf1HashType, 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 266 */           Integer.valueOf(this.saltLengthBytes) });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 271 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 276 */     return "RSA SSA PSS Parameters (variant: " + this.variant + ", signature hashType: " + this.sigHashType + ", mgf1 hashType: " + this.mgf1HashType + ", saltLengthBytes: " + this.saltLengthBytes + ", publicExponent: " + this.publicExponent + ", and " + this.modulusSizeBits + "-bit modulus)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\RsaSsaPssParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */