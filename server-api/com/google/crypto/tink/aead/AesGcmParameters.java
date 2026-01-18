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
/*     */ public final class AesGcmParameters
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     @Nullable
/*  59 */     private Integer keySizeBytes = null; @Nullable
/*  60 */     private Integer ivSizeBytes = null; @Nullable
/*  61 */     private Integer tagSizeBytes = null;
/*  62 */     private AesGcmParameters.Variant variant = AesGcmParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) throws GeneralSecurityException {
/*  69 */       if (keySizeBytes != 16 && keySizeBytes != 24 && keySizeBytes != 32)
/*  70 */         throw new InvalidAlgorithmParameterException(
/*  71 */             String.format("Invalid key size %d; only 16-byte, 24-byte and 32-byte AES keys are supported", new Object[] {
/*     */                 
/*  73 */                 Integer.valueOf(keySizeBytes)
/*     */               })); 
/*  75 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  76 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIvSizeBytes(int ivSizeBytes) throws GeneralSecurityException {
/*  82 */       if (ivSizeBytes <= 0) {
/*  83 */         throw new GeneralSecurityException(
/*  84 */             String.format("Invalid IV size in bytes %d; IV size must be positive", new Object[] { Integer.valueOf(ivSizeBytes) }));
/*     */       }
/*  86 */       this.ivSizeBytes = Integer.valueOf(ivSizeBytes);
/*  87 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setTagSizeBytes(int tagSizeBytes) throws GeneralSecurityException {
/*  92 */       if (tagSizeBytes < 12 || tagSizeBytes > 16)
/*  93 */         throw new GeneralSecurityException(
/*  94 */             String.format("Invalid tag size in bytes %d; value must be between 12 and 16 bytes", new Object[] {
/*     */                 
/*  96 */                 Integer.valueOf(tagSizeBytes)
/*     */               })); 
/*  98 */       this.tagSizeBytes = Integer.valueOf(tagSizeBytes);
/*  99 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(AesGcmParameters.Variant variant) {
/* 104 */       this.variant = variant;
/* 105 */       return this;
/*     */     }
/*     */     
/*     */     public AesGcmParameters build() throws GeneralSecurityException {
/* 109 */       if (this.keySizeBytes == null) {
/* 110 */         throw new GeneralSecurityException("Key size is not set");
/*     */       }
/* 112 */       if (this.variant == null) {
/* 113 */         throw new GeneralSecurityException("Variant is not set");
/*     */       }
/* 115 */       if (this.ivSizeBytes == null) {
/* 116 */         throw new GeneralSecurityException("IV size is not set");
/*     */       }
/*     */       
/* 119 */       if (this.tagSizeBytes == null) {
/* 120 */         throw new GeneralSecurityException("Tag size is not set");
/*     */       }
/*     */       
/* 123 */       return new AesGcmParameters(this.keySizeBytes.intValue(), this.ivSizeBytes.intValue(), this.tagSizeBytes.intValue(), this.variant);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private AesGcmParameters(int keySizeBytes, int ivSizeBytes, int tagSizeBytes, Variant variant) {
/* 133 */     this.keySizeBytes = keySizeBytes;
/* 134 */     this.ivSizeBytes = ivSizeBytes;
/* 135 */     this.tagSizeBytes = tagSizeBytes;
/* 136 */     this.variant = variant;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 140 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getKeySizeBytes() {
/* 144 */     return this.keySizeBytes;
/*     */   }
/*     */   
/*     */   public int getIvSizeBytes() {
/* 148 */     return this.ivSizeBytes;
/*     */   }
/*     */   
/*     */   public int getTagSizeBytes() {
/* 152 */     return this.tagSizeBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 157 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 162 */     if (!(o instanceof AesGcmParameters)) {
/* 163 */       return false;
/*     */     }
/* 165 */     AesGcmParameters that = (AesGcmParameters)o;
/* 166 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that
/* 167 */       .getIvSizeBytes() == getIvSizeBytes() && that
/* 168 */       .getTagSizeBytes() == getTagSizeBytes() && that
/* 169 */       .getVariant() == getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 174 */     return Objects.hash(new Object[] { AesGcmParameters.class, Integer.valueOf(this.keySizeBytes), Integer.valueOf(this.ivSizeBytes), Integer.valueOf(this.tagSizeBytes), this.variant });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 179 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 184 */     return "AesGcm Parameters (variant: " + this.variant + ", " + this.ivSizeBytes + "-byte IV, " + this.tagSizeBytes + "-byte tag, and " + this.keySizeBytes + "-byte key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesGcmParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */