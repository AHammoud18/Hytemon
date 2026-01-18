/*     */ package com.google.crypto.tink.daead;
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
/*     */ public final class AesSivParameters
/*     */   extends DeterministicAeadParameters
/*     */ {
/*     */   private final int keySizeBytes;
/*     */   private final Variant variant;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  31 */     public static final Variant TINK = new Variant("TINK");
/*  32 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/*  33 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  38 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  43 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Builder {
/*     */     @Nullable
/*  49 */     private Integer keySizeBytes = null;
/*  50 */     private AesSivParameters.Variant variant = AesSivParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) throws GeneralSecurityException {
/*  61 */       if (keySizeBytes != 32 && keySizeBytes != 48 && keySizeBytes != 64)
/*  62 */         throw new InvalidAlgorithmParameterException(
/*  63 */             String.format("Invalid key size %d; only 32-byte, 48-byte and 64-byte AES-SIV keys are supported", new Object[] {
/*     */                 
/*  65 */                 Integer.valueOf(keySizeBytes)
/*     */               })); 
/*  67 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  68 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(AesSivParameters.Variant variant) {
/*  73 */       this.variant = variant;
/*  74 */       return this;
/*     */     }
/*     */     
/*     */     public AesSivParameters build() throws GeneralSecurityException {
/*  78 */       if (this.keySizeBytes == null) {
/*  79 */         throw new GeneralSecurityException("Key size is not set");
/*     */       }
/*  81 */       if (this.variant == null) {
/*  82 */         throw new GeneralSecurityException("Variant is not set");
/*     */       }
/*  84 */       return new AesSivParameters(this.keySizeBytes.intValue(), this.variant);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */   
/*     */   private AesSivParameters(int keySizeBytes, Variant variant) {
/*  92 */     this.keySizeBytes = keySizeBytes;
/*  93 */     this.variant = variant;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/*  97 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getKeySizeBytes() {
/* 101 */     return this.keySizeBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 106 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 111 */     if (!(o instanceof AesSivParameters)) {
/* 112 */       return false;
/*     */     }
/* 114 */     AesSivParameters that = (AesSivParameters)o;
/* 115 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that.getVariant() == getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Objects.hash(new Object[] { AesSivParameters.class, Integer.valueOf(this.keySizeBytes), this.variant });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 125 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 130 */     return "AesSiv Parameters (variant: " + this.variant + ", " + this.keySizeBytes + "-byte key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\AesSivParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */