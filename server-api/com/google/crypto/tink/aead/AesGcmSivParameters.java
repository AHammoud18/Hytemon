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
/*     */ 
/*     */ 
/*     */ public final class AesGcmSivParameters
/*     */   extends AeadParameters
/*     */ {
/*     */   private final int keySizeBytes;
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
/*  54 */     private Integer keySizeBytes = null;
/*  55 */     private AesGcmSivParameters.Variant variant = AesGcmSivParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) throws GeneralSecurityException {
/*  62 */       if (keySizeBytes != 16 && keySizeBytes != 32)
/*  63 */         throw new InvalidAlgorithmParameterException(
/*  64 */             String.format("Invalid key size %d; only 16-byte and 32-byte AES keys are supported", new Object[] {
/*     */                 
/*  66 */                 Integer.valueOf(keySizeBytes)
/*     */               })); 
/*  68 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  69 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(AesGcmSivParameters.Variant variant) {
/*  74 */       this.variant = variant;
/*  75 */       return this;
/*     */     }
/*     */     
/*     */     public AesGcmSivParameters build() throws GeneralSecurityException {
/*  79 */       if (this.keySizeBytes == null) {
/*  80 */         throw new GeneralSecurityException("Key size is not set");
/*     */       }
/*  82 */       if (this.variant == null) {
/*  83 */         throw new GeneralSecurityException("Variant is not set");
/*     */       }
/*  85 */       return new AesGcmSivParameters(this.keySizeBytes.intValue(), this.variant);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */   
/*     */   private AesGcmSivParameters(int keySizeBytes, Variant variant) {
/*  93 */     this.keySizeBytes = keySizeBytes;
/*  94 */     this.variant = variant;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/*  98 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getKeySizeBytes() {
/* 102 */     return this.keySizeBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 107 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 112 */     if (!(o instanceof AesGcmSivParameters)) {
/* 113 */       return false;
/*     */     }
/* 115 */     AesGcmSivParameters that = (AesGcmSivParameters)o;
/* 116 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that.getVariant() == getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 121 */     return Objects.hash(new Object[] { AesGcmSivParameters.class, Integer.valueOf(this.keySizeBytes), this.variant });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 126 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     return "AesGcmSiv Parameters (variant: " + this.variant + ", " + this.keySizeBytes + "-byte key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AesGcmSivParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */