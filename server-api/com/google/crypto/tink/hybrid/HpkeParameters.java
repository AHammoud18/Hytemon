/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Objects;
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
/*     */ public final class HpkeParameters
/*     */   extends HybridParameters
/*     */ {
/*     */   private final KemId kem;
/*     */   private final KdfId kdf;
/*     */   private final AeadId aead;
/*     */   private final Variant variant;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  30 */     public static final Variant TINK = new Variant("TINK");
/*     */     
/*  32 */     public static final Variant CRUNCHY = new Variant("CRUNCHY");
/*     */     
/*  34 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  39 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  44 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   private static class AlgorithmIdentifier
/*     */   {
/*     */     protected final String name;
/*     */     
/*     */     protected final int value;
/*     */ 
/*     */     
/*     */     private AlgorithmIdentifier(String name, int value) {
/*  59 */       this.name = name;
/*  60 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int getValue() {
/*  64 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  69 */       return String.format("%s(0x%04x)", new Object[] { this.name, Integer.valueOf(this.value) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   public static final class KemId
/*     */     extends AlgorithmIdentifier
/*     */   {
/*  80 */     public static final KemId DHKEM_P256_HKDF_SHA256 = new KemId("DHKEM_P256_HKDF_SHA256", 16);
/*  81 */     public static final KemId DHKEM_P384_HKDF_SHA384 = new KemId("DHKEM_P384_HKDF_SHA384", 17);
/*  82 */     public static final KemId DHKEM_P521_HKDF_SHA512 = new KemId("DHKEM_P521_HKDF_SHA512", 18);
/*  83 */     public static final KemId DHKEM_X25519_HKDF_SHA256 = new KemId("DHKEM_X25519_HKDF_SHA256", 32);
/*     */ 
/*     */     
/*     */     private KemId(String name, int value) {
/*  87 */       super(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   public static final class KdfId
/*     */     extends AlgorithmIdentifier
/*     */   {
/*  98 */     public static final KdfId HKDF_SHA256 = new KdfId("HKDF_SHA256", 1);
/*  99 */     public static final KdfId HKDF_SHA384 = new KdfId("HKDF_SHA384", 2);
/* 100 */     public static final KdfId HKDF_SHA512 = new KdfId("HKDF_SHA512", 3);
/*     */     
/*     */     private KdfId(String name, int value) {
/* 103 */       super(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   public static final class AeadId
/*     */     extends AlgorithmIdentifier
/*     */   {
/* 114 */     public static final AeadId AES_128_GCM = new AeadId("AES_128_GCM", 1);
/* 115 */     public static final AeadId AES_256_GCM = new AeadId("AES_256_GCM", 2);
/* 116 */     public static final AeadId CHACHA20_POLY1305 = new AeadId("CHACHA20_POLY1305", 3);
/*     */     
/*     */     private AeadId(String name, int value) {
/* 119 */       super(name, value);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/* 125 */     private HpkeParameters.KemId kem = null;
/* 126 */     private HpkeParameters.KdfId kdf = null;
/* 127 */     private HpkeParameters.AeadId aead = null;
/* 128 */     private HpkeParameters.Variant variant = HpkeParameters.Variant.NO_PREFIX;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKemId(HpkeParameters.KemId kem) {
/* 134 */       this.kem = kem;
/* 135 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKdfId(HpkeParameters.KdfId kdf) {
/* 140 */       this.kdf = kdf;
/* 141 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAeadId(HpkeParameters.AeadId aead) {
/* 146 */       this.aead = aead;
/* 147 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(HpkeParameters.Variant variant) {
/* 152 */       this.variant = variant;
/* 153 */       return this;
/*     */     }
/*     */     
/*     */     public HpkeParameters build() throws GeneralSecurityException {
/* 157 */       if (this.kem == null) {
/* 158 */         throw new GeneralSecurityException("HPKE KEM parameter is not set");
/*     */       }
/* 160 */       if (this.kdf == null) {
/* 161 */         throw new GeneralSecurityException("HPKE KDF parameter is not set");
/*     */       }
/* 163 */       if (this.aead == null) {
/* 164 */         throw new GeneralSecurityException("HPKE AEAD parameter is not set");
/*     */       }
/* 166 */       if (this.variant == null) {
/* 167 */         throw new GeneralSecurityException("HPKE variant is not set");
/*     */       }
/* 169 */       return new HpkeParameters(this.kem, this.kdf, this.aead, this.variant);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private HpkeParameters(KemId kem, KdfId kdf, AeadId aead, Variant variant) {
/* 179 */     this.kem = kem;
/* 180 */     this.kdf = kdf;
/* 181 */     this.aead = aead;
/* 182 */     this.variant = variant;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 186 */     return new Builder();
/*     */   }
/*     */   
/*     */   public KemId getKemId() {
/* 190 */     return this.kem;
/*     */   }
/*     */   
/*     */   public KdfId getKdfId() {
/* 194 */     return this.kdf;
/*     */   }
/*     */   
/*     */   public AeadId getAeadId() {
/* 198 */     return this.aead;
/*     */   }
/*     */   
/*     */   public Variant getVariant() {
/* 202 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 207 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 212 */     if (!(o instanceof HpkeParameters)) {
/* 213 */       return false;
/*     */     }
/* 215 */     HpkeParameters other = (HpkeParameters)o;
/* 216 */     return (this.kem == other.kem && this.kdf == other.kdf && this.aead == other.aead && this.variant == other.variant);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 221 */     return Objects.hash(new Object[] { HpkeParameters.class, this.kem, this.kdf, this.aead, this.variant });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 226 */     return "HPKE Parameters (Variant: " + this.variant + ", KemId: " + this.kem + ", KdfId: " + this.kdf + ", AeadId: " + this.aead + ")";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HpkeParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */