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
/*     */ 
/*     */ public final class AesCmacParameters
/*     */   extends MacParameters
/*     */ {
/*     */   private final int keySizeBytes;
/*     */   private final int tagSizeBytes;
/*     */   private final Variant variant;
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
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     @Nullable
/*  57 */     private Integer keySizeBytes = null; @Nullable
/*  58 */     private Integer tagSizeBytes = null;
/*  59 */     private AesCmacParameters.Variant variant = AesCmacParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) throws GeneralSecurityException {
/*  65 */       if (keySizeBytes != 16 && keySizeBytes != 32)
/*  66 */         throw new InvalidAlgorithmParameterException(
/*  67 */             String.format("Invalid key size %d; only 128-bit and 256-bit AES keys are supported", new Object[] {
/*     */                 
/*  69 */                 Integer.valueOf(keySizeBytes * 8)
/*     */               })); 
/*  71 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  72 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setTagSizeBytes(int tagSizeBytes) throws GeneralSecurityException {
/*  77 */       if (tagSizeBytes < 10 || 16 < tagSizeBytes) {
/*  78 */         throw new GeneralSecurityException("Invalid tag size for AesCmacParameters: " + tagSizeBytes);
/*     */       }
/*     */       
/*  81 */       this.tagSizeBytes = Integer.valueOf(tagSizeBytes);
/*  82 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(AesCmacParameters.Variant variant) {
/*  87 */       this.variant = variant;
/*  88 */       return this;
/*     */     }
/*     */     
/*     */     public AesCmacParameters build() throws GeneralSecurityException {
/*  92 */       if (this.keySizeBytes == null) {
/*  93 */         throw new GeneralSecurityException("key size not set");
/*     */       }
/*  95 */       if (this.tagSizeBytes == null) {
/*  96 */         throw new GeneralSecurityException("tag size not set");
/*     */       }
/*  98 */       if (this.variant == null) {
/*  99 */         throw new GeneralSecurityException("variant not set");
/*     */       }
/* 101 */       return new AesCmacParameters(this.keySizeBytes.intValue(), this.tagSizeBytes.intValue(), this.variant);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private AesCmacParameters(int keySizeBytes, int tagSizeBytes, Variant variant) {
/* 110 */     this.keySizeBytes = keySizeBytes;
/* 111 */     this.tagSizeBytes = tagSizeBytes;
/* 112 */     this.variant = variant;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 116 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getKeySizeBytes() {
/* 120 */     return this.keySizeBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCryptographicTagSizeBytes() {
/* 130 */     return this.tagSizeBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalTagSizeBytes() {
/* 138 */     if (this.variant == Variant.NO_PREFIX) {
/* 139 */       return getCryptographicTagSizeBytes();
/*     */     }
/* 141 */     if (this.variant == Variant.TINK) {
/* 142 */       return getCryptographicTagSizeBytes() + 5;
/*     */     }
/* 144 */     if (this.variant == Variant.CRUNCHY) {
/* 145 */       return getCryptographicTagSizeBytes() + 5;
/*     */     }
/* 147 */     if (this.variant == Variant.LEGACY) {
/* 148 */       return getCryptographicTagSizeBytes() + 5;
/*     */     }
/* 150 */     throw new IllegalStateException("Unknown variant");
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 155 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 160 */     if (!(o instanceof AesCmacParameters)) {
/* 161 */       return false;
/*     */     }
/* 163 */     AesCmacParameters that = (AesCmacParameters)o;
/* 164 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that
/* 165 */       .getTotalTagSizeBytes() == getTotalTagSizeBytes() && that
/* 166 */       .getVariant() == getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 171 */     return Objects.hash(new Object[] { AesCmacParameters.class, Integer.valueOf(this.keySizeBytes), Integer.valueOf(this.tagSizeBytes), this.variant });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 176 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 181 */     return "AES-CMAC Parameters (variant: " + this.variant + ", " + this.tagSizeBytes + "-byte tags, and " + this.keySizeBytes + "-byte key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\AesCmacParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */