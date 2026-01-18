/*     */ package com.google.crypto.tink.streamingaead;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ public class AesGcmHkdfStreamingParameters
/*     */   extends StreamingAeadParameters
/*     */ {
/*     */   private final Integer keySizeBytes;
/*     */   private final Integer derivedAesGcmKeySizeBytes;
/*     */   private final HashType hkdfHashType;
/*     */   private final Integer ciphertextSegmentSizeBytes;
/*     */   
/*     */   @Immutable
/*     */   public static final class HashType
/*     */   {
/*  35 */     public static final HashType SHA1 = new HashType("SHA1");
/*  36 */     public static final HashType SHA256 = new HashType("SHA256");
/*  37 */     public static final HashType SHA512 = new HashType("SHA512");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private HashType(String name) {
/*  42 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  47 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/*  52 */     return new Builder();
/*     */   }
/*     */   public static final class Builder { @Nullable
/*     */     private Integer keySizeBytes;
/*     */     public Builder() {
/*  57 */       this.keySizeBytes = null;
/*     */       
/*  59 */       this.derivedAesGcmKeySizeBytes = null;
/*     */       
/*  61 */       this.hkdfHashType = null;
/*     */       
/*  63 */       this.ciphertextSegmentSizeBytes = null;
/*     */     }
/*     */     @Nullable
/*     */     private Integer derivedAesGcmKeySizeBytes; @Nullable
/*     */     private AesGcmHkdfStreamingParameters.HashType hkdfHashType;
/*     */     @Nullable
/*     */     private Integer ciphertextSegmentSizeBytes;
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) {
/*  73 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  74 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setDerivedAesGcmKeySizeBytes(int derivedAesGcmKeySizeBytes) {
/*  84 */       this.derivedAesGcmKeySizeBytes = Integer.valueOf(derivedAesGcmKeySizeBytes);
/*  85 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHkdfHashType(AesGcmHkdfStreamingParameters.HashType hkdfHashType) {
/*  91 */       this.hkdfHashType = hkdfHashType;
/*  92 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setCiphertextSegmentSizeBytes(int ciphertextSegmentSizeBytes) {
/* 103 */       this.ciphertextSegmentSizeBytes = Integer.valueOf(ciphertextSegmentSizeBytes);
/* 104 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesGcmHkdfStreamingParameters build() throws GeneralSecurityException {
/* 109 */       if (this.keySizeBytes == null) {
/* 110 */         throw new GeneralSecurityException("keySizeBytes needs to be set");
/*     */       }
/* 112 */       if (this.derivedAesGcmKeySizeBytes == null) {
/* 113 */         throw new GeneralSecurityException("derivedAesGcmKeySizeBytes needs to be set");
/*     */       }
/* 115 */       if (this.hkdfHashType == null) {
/* 116 */         throw new GeneralSecurityException("hkdfHashType needs to be set");
/*     */       }
/* 118 */       if (this.ciphertextSegmentSizeBytes == null) {
/* 119 */         throw new GeneralSecurityException("ciphertextSegmentSizeBytes needs to be set");
/*     */       }
/*     */       
/* 122 */       if (this.derivedAesGcmKeySizeBytes.intValue() != 16 && this.derivedAesGcmKeySizeBytes.intValue() != 32) {
/* 123 */         throw new GeneralSecurityException("derivedAesGcmKeySizeBytes needs to be 16 or 32, not " + this.derivedAesGcmKeySizeBytes);
/*     */       }
/*     */       
/* 126 */       if (this.keySizeBytes.intValue() < this.derivedAesGcmKeySizeBytes.intValue()) {
/* 127 */         throw new GeneralSecurityException("keySizeBytes needs to be at least derivedAesGcmKeySizeBytes, i.e., " + this.derivedAesGcmKeySizeBytes);
/*     */       }
/*     */ 
/*     */       
/* 131 */       if (this.ciphertextSegmentSizeBytes.intValue() <= this.derivedAesGcmKeySizeBytes.intValue() + 24) {
/* 132 */         throw new GeneralSecurityException("ciphertextSegmentSizeBytes needs to be at least derivedAesGcmKeySizeBytes + 25, i.e., " + (this.derivedAesGcmKeySizeBytes
/*     */             
/* 134 */             .intValue() + 25));
/*     */       }
/* 136 */       return new AesGcmHkdfStreamingParameters(this.keySizeBytes, this.derivedAesGcmKeySizeBytes, this.hkdfHashType, this.ciphertextSegmentSizeBytes);
/*     */     } }
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
/*     */   private AesGcmHkdfStreamingParameters(Integer keySizeBytes, Integer derivedAesGcmKeySizeBytes, HashType hkdfHashType, Integer ciphertextSegmentSizeBytes) {
/* 151 */     this.keySizeBytes = keySizeBytes;
/* 152 */     this.derivedAesGcmKeySizeBytes = derivedAesGcmKeySizeBytes;
/* 153 */     this.hkdfHashType = hkdfHashType;
/* 154 */     this.ciphertextSegmentSizeBytes = ciphertextSegmentSizeBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKeySizeBytes() {
/* 159 */     return this.keySizeBytes.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDerivedAesGcmKeySizeBytes() {
/* 164 */     return this.derivedAesGcmKeySizeBytes.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public HashType getHkdfHashType() {
/* 169 */     return this.hkdfHashType;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCiphertextSegmentSizeBytes() {
/* 174 */     return this.ciphertextSegmentSizeBytes.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 180 */     if (!(o instanceof AesGcmHkdfStreamingParameters)) {
/* 181 */       return false;
/*     */     }
/* 183 */     AesGcmHkdfStreamingParameters that = (AesGcmHkdfStreamingParameters)o;
/* 184 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that
/* 185 */       .getDerivedAesGcmKeySizeBytes() == getDerivedAesGcmKeySizeBytes() && that
/* 186 */       .getHkdfHashType() == getHkdfHashType() && that
/* 187 */       .getCiphertextSegmentSizeBytes() == getCiphertextSegmentSizeBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 192 */     return Objects.hash(new Object[] { AesGcmHkdfStreamingParameters.class, this.keySizeBytes, this.derivedAesGcmKeySizeBytes, this.hkdfHashType, this.ciphertextSegmentSizeBytes });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 202 */     return "AesGcmHkdfStreaming Parameters (IKM size: " + this.keySizeBytes + ", " + this.derivedAesGcmKeySizeBytes + "-byte AES GCM key, " + this.hkdfHashType + " for HKDF " + this.ciphertextSegmentSizeBytes + "-byte ciphertexts)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\AesGcmHkdfStreamingParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */