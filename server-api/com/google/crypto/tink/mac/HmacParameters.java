/*     */ package com.google.crypto.tink.mac;
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
/*     */ 
/*     */ 
/*     */ public final class HmacParameters
/*     */   extends MacParameters
/*     */ {
/*     */   private final int keySizeBytes;
/*     */   private final int tagSizeBytes;
/*     */   private final Variant variant;
/*     */   private final HashType hashType;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  36 */     public static final Variant TINK = new Variant("TINK");
/*  37 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/*  38 */     public static final Variant LEGACY = new Variant("LEGACY");
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
/*  76 */     private Integer keySizeBytes = null; @Nullable
/*  77 */     private Integer tagSizeBytes = null;
/*  78 */     private HmacParameters.HashType hashType = null;
/*  79 */     private HmacParameters.Variant variant = HmacParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) throws GeneralSecurityException {
/*  85 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  86 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setTagSizeBytes(int tagSizeBytes) throws GeneralSecurityException {
/*  91 */       this.tagSizeBytes = Integer.valueOf(tagSizeBytes);
/*  92 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(HmacParameters.Variant variant) {
/*  97 */       this.variant = variant;
/*  98 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHashType(HmacParameters.HashType hashType) {
/* 103 */       this.hashType = hashType;
/* 104 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private static void validateTagSizeBytes(int tagSizeBytes, HmacParameters.HashType hashType) throws GeneralSecurityException {
/* 109 */       if (tagSizeBytes < 10) {
/* 110 */         throw new GeneralSecurityException(
/* 111 */             String.format("Invalid tag size in bytes %d; must be at least 10 bytes", new Object[] { Integer.valueOf(tagSizeBytes) }));
/*     */       }
/* 113 */       if (hashType == HmacParameters.HashType.SHA1) {
/* 114 */         if (tagSizeBytes > 20)
/* 115 */           throw new GeneralSecurityException(
/* 116 */               String.format("Invalid tag size in bytes %d; can be at most 20 bytes for SHA1", new Object[] {
/* 117 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 121 */       if (hashType == HmacParameters.HashType.SHA224) {
/* 122 */         if (tagSizeBytes > 28)
/* 123 */           throw new GeneralSecurityException(
/* 124 */               String.format("Invalid tag size in bytes %d; can be at most 28 bytes for SHA224", new Object[] {
/*     */                   
/* 126 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 130 */       if (hashType == HmacParameters.HashType.SHA256) {
/* 131 */         if (tagSizeBytes > 32)
/* 132 */           throw new GeneralSecurityException(
/* 133 */               String.format("Invalid tag size in bytes %d; can be at most 32 bytes for SHA256", new Object[] {
/*     */                   
/* 135 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 139 */       if (hashType == HmacParameters.HashType.SHA384) {
/* 140 */         if (tagSizeBytes > 48)
/* 141 */           throw new GeneralSecurityException(
/* 142 */               String.format("Invalid tag size in bytes %d; can be at most 48 bytes for SHA384", new Object[] {
/*     */                   
/* 144 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 148 */       if (hashType == HmacParameters.HashType.SHA512) {
/* 149 */         if (tagSizeBytes > 64)
/* 150 */           throw new GeneralSecurityException(
/* 151 */               String.format("Invalid tag size in bytes %d; can be at most 64 bytes for SHA512", new Object[] {
/*     */                   
/* 153 */                   Integer.valueOf(tagSizeBytes)
/*     */                 })); 
/*     */         return;
/*     */       } 
/* 157 */       throw new GeneralSecurityException("unknown hash type; must be SHA256, SHA384 or SHA512");
/*     */     }
/*     */     
/*     */     public HmacParameters build() throws GeneralSecurityException {
/* 161 */       if (this.keySizeBytes == null) {
/* 162 */         throw new GeneralSecurityException("key size is not set");
/*     */       }
/* 164 */       if (this.tagSizeBytes == null) {
/* 165 */         throw new GeneralSecurityException("tag size is not set");
/*     */       }
/* 167 */       if (this.hashType == null) {
/* 168 */         throw new GeneralSecurityException("hash type is not set");
/*     */       }
/* 170 */       if (this.variant == null) {
/* 171 */         throw new GeneralSecurityException("variant is not set");
/*     */       }
/* 173 */       if (this.keySizeBytes.intValue() < 16) {
/* 174 */         throw new InvalidAlgorithmParameterException(
/* 175 */             String.format("Invalid key size in bytes %d; must be at least 16 bytes", new Object[] { this.keySizeBytes }));
/*     */       }
/* 177 */       validateTagSizeBytes(this.tagSizeBytes.intValue(), this.hashType);
/* 178 */       return new HmacParameters(this.keySizeBytes.intValue(), this.tagSizeBytes.intValue(), this.variant, this.hashType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private HmacParameters(int keySizeBytes, int tagSizeBytes, Variant variant, HashType hashType) {
/* 188 */     this.keySizeBytes = keySizeBytes;
/* 189 */     this.tagSizeBytes = tagSizeBytes;
/* 190 */     this.variant = variant;
/* 191 */     this.hashType = hashType;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 195 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getKeySizeBytes() {
/* 199 */     return this.keySizeBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCryptographicTagSizeBytes() {
/* 209 */     return this.tagSizeBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalTagSizeBytes() {
/* 217 */     if (this.variant == Variant.NO_PREFIX) {
/* 218 */       return getCryptographicTagSizeBytes();
/*     */     }
/* 220 */     if (this.variant == Variant.TINK) {
/* 221 */       return getCryptographicTagSizeBytes() + 5;
/*     */     }
/* 223 */     if (this.variant == Variant.CRUNCHY) {
/* 224 */       return getCryptographicTagSizeBytes() + 5;
/*     */     }
/* 226 */     if (this.variant == Variant.LEGACY) {
/* 227 */       return getCryptographicTagSizeBytes() + 5;
/*     */     }
/* 229 */     throw new IllegalStateException("Unknown variant");
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 234 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public HashType getHashType() {
/* 239 */     return this.hashType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 244 */     if (!(o instanceof HmacParameters)) {
/* 245 */       return false;
/*     */     }
/* 247 */     HmacParameters that = (HmacParameters)o;
/* 248 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that
/* 249 */       .getTotalTagSizeBytes() == getTotalTagSizeBytes() && that
/* 250 */       .getVariant() == getVariant() && that
/* 251 */       .getHashType() == getHashType());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 256 */     return Objects.hash(new Object[] { HmacParameters.class, Integer.valueOf(this.keySizeBytes), Integer.valueOf(this.tagSizeBytes), this.variant, this.hashType });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 261 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 266 */     return "HMAC Parameters (variant: " + this.variant + ", hashType: " + this.hashType + ", " + this.tagSizeBytes + "-byte tags, and " + this.keySizeBytes + "-byte key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\HmacParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */