/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
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
/*     */ public final class AesCtrHmacAeadParameters
/*     */   extends AeadParameters
/*     */ {
/*     */   private static final int PREFIX_SIZE_IN_BYTES = 5;
/*     */   private final int aesKeySizeBytes;
/*     */   private final int hmacKeySizeBytes;
/*     */   private final int ivSizeBytes;
/*     */   private final int tagSizeBytes;
/*     */   private final Variant variant;
/*     */   private final HashType hashType;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  37 */     public static final Variant TINK = new Variant("TINK");
/*  38 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/*  39 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  44 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  49 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class HashType
/*     */   {
/*  56 */     public static final HashType SHA1 = new HashType("SHA1");
/*  57 */     public static final HashType SHA224 = new HashType("SHA224");
/*  58 */     public static final HashType SHA256 = new HashType("SHA256");
/*  59 */     public static final HashType SHA384 = new HashType("SHA384");
/*  60 */     public static final HashType SHA512 = new HashType("SHA512");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private HashType(String name) {
/*  65 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  70 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Builder {
/*     */     @Nullable
/*  76 */     private Integer aesKeySizeBytes = null; @Nullable
/*  77 */     private Integer hmacKeySizeBytes = null; @Nullable
/*  78 */     private Integer ivSizeBytes = null; @Nullable
/*  79 */     private Integer tagSizeBytes = null;
/*  80 */     private AesCtrHmacAeadParameters.HashType hashType = null;
/*  81 */     private AesCtrHmacAeadParameters.Variant variant = AesCtrHmacAeadParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAesKeySizeBytes(int aesKeySizeBytes) throws GeneralSecurityException {
/*  88 */       if (aesKeySizeBytes != 16 && aesKeySizeBytes != 24 && aesKeySizeBytes != 32)
/*  89 */         throw new InvalidAlgorithmParameterException(
/*  90 */             String.format("Invalid key size %d; only 16-byte, 24-byte and 32-byte AES keys are supported", new Object[] {
/*     */                 
/*  92 */                 Integer.valueOf(aesKeySizeBytes)
/*     */               })); 
/*  94 */       this.aesKeySizeBytes = Integer.valueOf(aesKeySizeBytes);
/*  95 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHmacKeySizeBytes(int hmacKeySizeBytes) throws GeneralSecurityException {
/* 101 */       if (hmacKeySizeBytes < 16)
/* 102 */         throw new InvalidAlgorithmParameterException(
/* 103 */             String.format("Invalid key size in bytes %d; HMAC key must be at least 16 bytes", new Object[] {
/*     */                 
/* 105 */                 Integer.valueOf(hmacKeySizeBytes)
/*     */               })); 
/* 107 */       this.hmacKeySizeBytes = Integer.valueOf(hmacKeySizeBytes);
/* 108 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIvSizeBytes(int ivSizeBytes) throws GeneralSecurityException {
/* 114 */       if (ivSizeBytes < 12 || ivSizeBytes > 16)
/* 115 */         throw new GeneralSecurityException(
/* 116 */             String.format("Invalid IV size in bytes %d; IV size must be between 12 and 16 bytes", new Object[] {
/*     */                 
/* 118 */                 Integer.valueOf(ivSizeBytes)
/*     */               })); 
/* 120 */       this.ivSizeBytes = Integer.valueOf(ivSizeBytes);
/* 121 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setTagSizeBytes(int tagSizeBytes) throws GeneralSecurityException {
/* 126 */       if (tagSizeBytes < 10) {
/* 127 */         throw new GeneralSecurityException(
/* 128 */             String.format("Invalid tag size in bytes %d; must be at least 10 bytes", new Object[] { Integer.valueOf(tagSizeBytes) }));
/*     */       }
/* 130 */       this.tagSizeBytes = Integer.valueOf(tagSizeBytes);
/* 131 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(AesCtrHmacAeadParameters.Variant variant) {
/* 136 */       this.variant = variant;
/* 137 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHashType(AesCtrHmacAeadParameters.HashType hashType) {
/* 142 */       this.hashType = hashType;
/* 143 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private static void validateTagSizeBytes(int tagSizeBytes, AesCtrHmacAeadParameters.HashType hashType) throws GeneralSecurityException {
/* 148 */       if (hashType == AesCtrHmacAeadParameters.HashType.SHA1) {
/* 149 */         if (tagSizeBytes > 20)
/* 150 */           throw new GeneralSecurityException(
/* 151 */               String.format("Invalid tag size in bytes %d; can be at most 20 bytes for SHA1", new Object[] {
/* 152 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 156 */       if (hashType == AesCtrHmacAeadParameters.HashType.SHA224) {
/* 157 */         if (tagSizeBytes > 28)
/* 158 */           throw new GeneralSecurityException(
/* 159 */               String.format("Invalid tag size in bytes %d; can be at most 28 bytes for SHA224", new Object[] {
/*     */                   
/* 161 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 165 */       if (hashType == AesCtrHmacAeadParameters.HashType.SHA256) {
/* 166 */         if (tagSizeBytes > 32)
/* 167 */           throw new GeneralSecurityException(
/* 168 */               String.format("Invalid tag size in bytes %d; can be at most 32 bytes for SHA256", new Object[] {
/*     */                   
/* 170 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 174 */       if (hashType == AesCtrHmacAeadParameters.HashType.SHA384) {
/* 175 */         if (tagSizeBytes > 48)
/* 176 */           throw new GeneralSecurityException(
/* 177 */               String.format("Invalid tag size in bytes %d; can be at most 48 bytes for SHA384", new Object[] {
/*     */                   
/* 179 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 183 */       if (hashType == AesCtrHmacAeadParameters.HashType.SHA512) {
/* 184 */         if (tagSizeBytes > 64)
/* 185 */           throw new GeneralSecurityException(
/* 186 */               String.format("Invalid tag size in bytes %d; can be at most 64 bytes for SHA512", new Object[] {
/*     */                   
/* 188 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 192 */       throw new GeneralSecurityException("unknown hash type; must be SHA1, SHA224, SHA256, SHA384 or SHA512");
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrHmacAeadParameters build() throws GeneralSecurityException {
/* 197 */       if (this.aesKeySizeBytes == null) {
/* 198 */         throw new GeneralSecurityException("AES key size is not set");
/*     */       }
/* 200 */       if (this.hmacKeySizeBytes == null) {
/* 201 */         throw new GeneralSecurityException("HMAC key size is not set");
/*     */       }
/* 203 */       if (this.ivSizeBytes == null) {
/* 204 */         throw new GeneralSecurityException("iv size is not set");
/*     */       }
/* 206 */       if (this.tagSizeBytes == null) {
/* 207 */         throw new GeneralSecurityException("tag size is not set");
/*     */       }
/* 209 */       if (this.hashType == null) {
/* 210 */         throw new GeneralSecurityException("hash type is not set");
/*     */       }
/* 212 */       if (this.variant == null) {
/* 213 */         throw new GeneralSecurityException("variant is not set");
/*     */       }
/* 215 */       validateTagSizeBytes(this.tagSizeBytes.intValue(), this.hashType);
/* 216 */       return new AesCtrHmacAeadParameters(this.aesKeySizeBytes
/* 217 */           .intValue(), this.hmacKeySizeBytes.intValue(), this.ivSizeBytes.intValue(), this.tagSizeBytes.intValue(), this.variant, this.hashType);
/*     */     }
/*     */ 
/*     */ 
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
/*     */ 
/*     */   
/*     */   private AesCtrHmacAeadParameters(int aesKeySizeBytes, int hmacKeySizeBytes, int ivSizeBytes, int tagSizeBytes, Variant variant, HashType hashType) {
/* 235 */     this.aesKeySizeBytes = aesKeySizeBytes;
/* 236 */     this.hmacKeySizeBytes = hmacKeySizeBytes;
/* 237 */     this.ivSizeBytes = ivSizeBytes;
/* 238 */     this.tagSizeBytes = tagSizeBytes;
/* 239 */     this.variant = variant;
/* 240 */     this.hashType = hashType;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 244 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getAesKeySizeBytes() {
/* 248 */     return this.aesKeySizeBytes;
/*     */   }
/*     */   
/*     */   public int getHmacKeySizeBytes() {
/* 252 */     return this.hmacKeySizeBytes;
/*     */   }
/*     */   
/*     */   public int getTagSizeBytes() {
/* 256 */     return this.tagSizeBytes;
/*     */   }
/*     */   
/*     */   public int getIvSizeBytes() {
/* 260 */     return this.ivSizeBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCiphertextOverheadSizeBytes() {
/* 269 */     if (this.variant == Variant.NO_PREFIX) {
/* 270 */       return getTagSizeBytes() + getIvSizeBytes();
/*     */     }
/* 272 */     if (this.variant == Variant.TINK || this.variant == Variant.CRUNCHY) {
/* 273 */       return getTagSizeBytes() + getIvSizeBytes() + 5;
/*     */     }
/* 275 */     throw new IllegalStateException("Unknown variant");
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 280 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public HashType getHashType() {
/* 285 */     return this.hashType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 290 */     if (!(o instanceof AesCtrHmacAeadParameters)) {
/* 291 */       return false;
/*     */     }
/* 293 */     AesCtrHmacAeadParameters that = (AesCtrHmacAeadParameters)o;
/* 294 */     return (that.getAesKeySizeBytes() == getAesKeySizeBytes() && that
/* 295 */       .getHmacKeySizeBytes() == getHmacKeySizeBytes() && that
/* 296 */       .getIvSizeBytes() == getIvSizeBytes() && that
/* 297 */       .getTagSizeBytes() == getTagSizeBytes() && that
/* 298 */       .getVariant() == getVariant() && that
/* 299 */       .getHashType() == getHashType());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 304 */     return Objects.hash(new Object[] { AesCtrHmacAeadParameters.class, 
/*     */           
/* 306 */           Integer.valueOf(this.aesKeySizeBytes), 
/* 307 */           Integer.valueOf(this.hmacKeySizeBytes), 
/* 308 */           Integer.valueOf(this.ivSizeBytes), 
/* 309 */           Integer.valueOf(this.tagSizeBytes), this.variant, this.hashType });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 316 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 321 */     return "AesCtrHmacAead Parameters (variant: " + this.variant + ", hashType: " + this.hashType + ", " + this.ivSizeBytes + "-byte IV, and " + this.tagSizeBytes + "-byte tags, and " + this.aesKeySizeBytes + "-byte AES key, and " + this.hmacKeySizeBytes + "-byte HMAC key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesCtrHmacAeadParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */