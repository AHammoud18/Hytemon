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
/*     */ 
/*     */ 
/*     */ public final class AesEaxParameters
/*     */   extends AeadParameters
/*     */ {
/*     */   private final int keySizeBytes;
/*     */   private final int ivSizeBytes;
/*     */   private final int tagSizeBytes;
/*     */   private final Variant variant;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  36 */     public static final Variant TINK = new Variant("TINK");
/*  37 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/*  38 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  43 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  48 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Builder {
/*     */     @Nullable
/*  54 */     private Integer keySizeBytes = null; @Nullable
/*  55 */     private Integer ivSizeBytes = null; @Nullable
/*  56 */     private Integer tagSizeBytes = null;
/*  57 */     private AesEaxParameters.Variant variant = AesEaxParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) throws GeneralSecurityException {
/*  67 */       if (keySizeBytes != 16 && keySizeBytes != 24 && keySizeBytes != 32)
/*  68 */         throw new InvalidAlgorithmParameterException(
/*  69 */             String.format("Invalid key size %d; only 16-byte, 24-byte and 32-byte AES keys are supported", new Object[] {
/*     */                 
/*  71 */                 Integer.valueOf(keySizeBytes)
/*     */               })); 
/*  73 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  74 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIvSizeBytes(int ivSizeBytes) throws GeneralSecurityException {
/*  79 */       if (ivSizeBytes != 12 && ivSizeBytes != 16)
/*  80 */         throw new GeneralSecurityException(
/*  81 */             String.format("Invalid IV size in bytes %d; acceptable values have 12 or 16 bytes", new Object[] {
/*  82 */                 Integer.valueOf(ivSizeBytes)
/*     */               })); 
/*  84 */       this.ivSizeBytes = Integer.valueOf(ivSizeBytes);
/*  85 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setTagSizeBytes(int tagSizeBytes) throws GeneralSecurityException {
/*  94 */       if (tagSizeBytes < 0 || tagSizeBytes > 16)
/*  95 */         throw new GeneralSecurityException(
/*  96 */             String.format("Invalid tag size in bytes %d; value must be at most 16 bytes", new Object[] {
/*  97 */                 Integer.valueOf(tagSizeBytes)
/*     */               })); 
/*  99 */       this.tagSizeBytes = Integer.valueOf(tagSizeBytes);
/* 100 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(AesEaxParameters.Variant variant) {
/* 105 */       this.variant = variant;
/* 106 */       return this;
/*     */     }
/*     */     
/*     */     public AesEaxParameters build() throws GeneralSecurityException {
/* 110 */       if (this.keySizeBytes == null) {
/* 111 */         throw new GeneralSecurityException("Key size is not set");
/*     */       }
/* 113 */       if (this.ivSizeBytes == null) {
/* 114 */         throw new GeneralSecurityException("IV size is not set");
/*     */       }
/* 116 */       if (this.variant == null) {
/* 117 */         throw new GeneralSecurityException("Variant is not set");
/*     */       }
/*     */       
/* 120 */       if (this.tagSizeBytes == null) {
/* 121 */         throw new GeneralSecurityException("Tag size is not set");
/*     */       }
/*     */       
/* 124 */       return new AesEaxParameters(this.keySizeBytes.intValue(), this.ivSizeBytes.intValue(), this.tagSizeBytes.intValue(), this.variant);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private AesEaxParameters(int keySizeBytes, int ivSizeBytes, int tagSizeBytes, Variant variant) {
/* 134 */     this.keySizeBytes = keySizeBytes;
/* 135 */     this.ivSizeBytes = ivSizeBytes;
/* 136 */     this.tagSizeBytes = tagSizeBytes;
/* 137 */     this.variant = variant;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 141 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getKeySizeBytes() {
/* 145 */     return this.keySizeBytes;
/*     */   }
/*     */   
/*     */   public int getIvSizeBytes() {
/* 149 */     return this.ivSizeBytes;
/*     */   }
/*     */   
/*     */   public int getTagSizeBytes() {
/* 153 */     return this.tagSizeBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 158 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 163 */     if (!(o instanceof AesEaxParameters)) {
/* 164 */       return false;
/*     */     }
/* 166 */     AesEaxParameters that = (AesEaxParameters)o;
/* 167 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that
/* 168 */       .getIvSizeBytes() == getIvSizeBytes() && that
/* 169 */       .getTagSizeBytes() == getTagSizeBytes() && that
/* 170 */       .getVariant() == getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 175 */     return Objects.hash(new Object[] { AesEaxParameters.class, Integer.valueOf(this.keySizeBytes), Integer.valueOf(this.ivSizeBytes), Integer.valueOf(this.tagSizeBytes), this.variant });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 180 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 185 */     return "AesEax Parameters (variant: " + this.variant + ", " + this.ivSizeBytes + "-byte IV, " + this.tagSizeBytes + "-byte tag, and " + this.keySizeBytes + "-byte key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesEaxParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */