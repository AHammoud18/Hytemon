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
/*     */ public class AesCtrHmacStreamingParameters
/*     */   extends StreamingAeadParameters
/*     */ {
/*     */   private final Integer keySizeBytes;
/*     */   private final Integer derivedKeySizeBytes;
/*     */   private final HashType hkdfHashType;
/*     */   private final HashType hmacHashType;
/*     */   private final Integer hmacTagSizeBytes;
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
/*  59 */       this.derivedKeySizeBytes = null;
/*     */       
/*  61 */       this.hkdfHashType = null;
/*     */       
/*  63 */       this.hmacHashType = null;
/*  64 */       this.hmacTagSizeBytes = null;
/*     */       
/*  66 */       this.ciphertextSegmentSizeBytes = null;
/*     */     }
/*     */     @Nullable
/*     */     private Integer derivedKeySizeBytes; @Nullable
/*     */     private AesCtrHmacStreamingParameters.HashType hkdfHashType; @Nullable
/*     */     private AesCtrHmacStreamingParameters.HashType hmacHashType; @Nullable
/*     */     private Integer hmacTagSizeBytes; @Nullable
/*     */     private Integer ciphertextSegmentSizeBytes;
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) {
/*  76 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  77 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setDerivedKeySizeBytes(int derivedKeySizeBytes) {
/*  87 */       this.derivedKeySizeBytes = Integer.valueOf(derivedKeySizeBytes);
/*  88 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHkdfHashType(AesCtrHmacStreamingParameters.HashType hkdfHashType) {
/*  94 */       this.hkdfHashType = hkdfHashType;
/*  95 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHmacHashType(AesCtrHmacStreamingParameters.HashType hmacHashType) {
/* 101 */       this.hmacHashType = hmacHashType;
/* 102 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHmacTagSizeBytes(Integer hmacTagSizeBytes) {
/* 108 */       this.hmacTagSizeBytes = hmacTagSizeBytes;
/* 109 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setCiphertextSegmentSizeBytes(int ciphertextSegmentSizeBytes) {
/* 119 */       this.ciphertextSegmentSizeBytes = Integer.valueOf(ciphertextSegmentSizeBytes);
/* 120 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public AesCtrHmacStreamingParameters build() throws GeneralSecurityException {
/* 125 */       if (this.keySizeBytes == null) {
/* 126 */         throw new GeneralSecurityException("keySizeBytes needs to be set");
/*     */       }
/* 128 */       if (this.derivedKeySizeBytes == null) {
/* 129 */         throw new GeneralSecurityException("derivedKeySizeBytes needs to be set");
/*     */       }
/* 131 */       if (this.hkdfHashType == null) {
/* 132 */         throw new GeneralSecurityException("hkdfHashType needs to be set");
/*     */       }
/* 134 */       if (this.hmacHashType == null) {
/* 135 */         throw new GeneralSecurityException("hmacHashType needs to be set");
/*     */       }
/* 137 */       if (this.hmacTagSizeBytes == null) {
/* 138 */         throw new GeneralSecurityException("hmacTagSizeBytes needs to be set");
/*     */       }
/* 140 */       if (this.ciphertextSegmentSizeBytes == null) {
/* 141 */         throw new GeneralSecurityException("ciphertextSegmentSizeBytes needs to be set");
/*     */       }
/*     */       
/* 144 */       if (this.derivedKeySizeBytes.intValue() != 16 && this.derivedKeySizeBytes.intValue() != 32) {
/* 145 */         throw new GeneralSecurityException("derivedKeySizeBytes needs to be 16 or 32, not " + this.derivedKeySizeBytes);
/*     */       }
/*     */       
/* 148 */       if (this.keySizeBytes.intValue() < this.derivedKeySizeBytes.intValue()) {
/* 149 */         throw new GeneralSecurityException("keySizeBytes needs to be at least derivedKeySizeBytes, i.e., " + this.derivedKeySizeBytes);
/*     */       }
/*     */       
/* 152 */       if (this.ciphertextSegmentSizeBytes.intValue() <= this.derivedKeySizeBytes.intValue() + this.hmacTagSizeBytes.intValue() + 8) {
/* 153 */         throw new GeneralSecurityException("ciphertextSegmentSizeBytes needs to be at least derivedKeySizeBytes + hmacTagSizeBytes + 9, i.e., " + (this.derivedKeySizeBytes
/*     */ 
/*     */             
/* 156 */             .intValue() + this.hmacTagSizeBytes.intValue() + 9));
/*     */       }
/*     */       
/* 159 */       int hmacTagSizeLowerBound = 10;
/* 160 */       int hmacTagSizeUpperBound = 0;
/* 161 */       if (this.hmacHashType == AesCtrHmacStreamingParameters.HashType.SHA1) {
/* 162 */         hmacTagSizeUpperBound = 20;
/*     */       }
/* 164 */       if (this.hmacHashType == AesCtrHmacStreamingParameters.HashType.SHA256) {
/* 165 */         hmacTagSizeUpperBound = 32;
/*     */       }
/* 167 */       if (this.hmacHashType == AesCtrHmacStreamingParameters.HashType.SHA512) {
/* 168 */         hmacTagSizeUpperBound = 64;
/*     */       }
/* 170 */       if (this.hmacTagSizeBytes.intValue() < hmacTagSizeLowerBound || this.hmacTagSizeBytes.intValue() > hmacTagSizeUpperBound) {
/* 171 */         throw new GeneralSecurityException("hmacTagSize must be in range [" + hmacTagSizeLowerBound + ", " + hmacTagSizeUpperBound + "], but is " + this.hmacTagSizeBytes);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 179 */       return new AesCtrHmacStreamingParameters(this.keySizeBytes, this.derivedKeySizeBytes, this.hkdfHashType, this.hmacHashType, this.hmacTagSizeBytes, this.ciphertextSegmentSizeBytes);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AesCtrHmacStreamingParameters(Integer keySizeBytes, Integer derivedKeySizeBytes, HashType hkdfHashType, HashType hmacHashType, Integer hmacTagSizeBytes, Integer ciphertextSegmentSizeBytes) {
/* 203 */     this.keySizeBytes = keySizeBytes;
/* 204 */     this.derivedKeySizeBytes = derivedKeySizeBytes;
/* 205 */     this.hkdfHashType = hkdfHashType;
/* 206 */     this.hmacHashType = hmacHashType;
/* 207 */     this.hmacTagSizeBytes = hmacTagSizeBytes;
/* 208 */     this.ciphertextSegmentSizeBytes = ciphertextSegmentSizeBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKeySizeBytes() {
/* 213 */     return this.keySizeBytes.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDerivedKeySizeBytes() {
/* 218 */     return this.derivedKeySizeBytes.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public HashType getHkdfHashType() {
/* 223 */     return this.hkdfHashType;
/*     */   }
/*     */ 
/*     */   
/*     */   public HashType getHmacHashType() {
/* 228 */     return this.hmacHashType;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHmacTagSizeBytes() {
/* 233 */     return this.hmacTagSizeBytes.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCiphertextSegmentSizeBytes() {
/* 238 */     return this.ciphertextSegmentSizeBytes.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 243 */     if (!(o instanceof AesCtrHmacStreamingParameters)) {
/* 244 */       return false;
/*     */     }
/* 246 */     AesCtrHmacStreamingParameters that = (AesCtrHmacStreamingParameters)o;
/* 247 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that
/* 248 */       .getDerivedKeySizeBytes() == getDerivedKeySizeBytes() && that
/* 249 */       .getHkdfHashType() == getHkdfHashType() && that
/* 250 */       .getHmacHashType() == getHmacHashType() && that
/* 251 */       .getHmacTagSizeBytes() == getHmacTagSizeBytes() && that
/* 252 */       .getCiphertextSegmentSizeBytes() == getCiphertextSegmentSizeBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 257 */     return Objects.hash(new Object[] { AesCtrHmacStreamingParameters.class, this.keySizeBytes, this.derivedKeySizeBytes, this.hkdfHashType, this.hmacHashType, this.hmacTagSizeBytes, this.ciphertextSegmentSizeBytes });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 269 */     return "AesCtrHmacStreaming Parameters (IKM size: " + this.keySizeBytes + ", " + this.derivedKeySizeBytes + "-byte AES key, " + this.hkdfHashType + " for HKDF, " + this.hkdfHashType + " for HMAC, " + this.hmacTagSizeBytes + "-byte tags, " + this.ciphertextSegmentSizeBytes + "-byte ciphertexts)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\AesCtrHmacStreamingParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */