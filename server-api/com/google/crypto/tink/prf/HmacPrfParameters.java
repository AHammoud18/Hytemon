/*     */ package com.google.crypto.tink.prf;
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
/*     */ public final class HmacPrfParameters
/*     */   extends PrfParameters
/*     */ {
/*     */   private static final int MIN_KEY_SIZE = 16;
/*     */   private final int keySizeBytes;
/*     */   private final HashType hashType;
/*     */   
/*     */   @Immutable
/*     */   public static final class HashType
/*     */   {
/*  33 */     public static final HashType SHA1 = new HashType("SHA1");
/*  34 */     public static final HashType SHA224 = new HashType("SHA224");
/*  35 */     public static final HashType SHA256 = new HashType("SHA256");
/*  36 */     public static final HashType SHA384 = new HashType("SHA384");
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
/*     */   public static final class Builder {
/*     */     @Nullable
/*  53 */     private Integer keySizeBytes = null; @Nullable
/*  54 */     private HmacPrfParameters.HashType hashType = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) throws GeneralSecurityException {
/*  60 */       if (keySizeBytes < 16)
/*  61 */         throw new InvalidAlgorithmParameterException(
/*  62 */             String.format("Invalid key size %d; only 128-bit or larger are supported", new Object[] {
/*  63 */                 Integer.valueOf(keySizeBytes * 8)
/*     */               })); 
/*  65 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  66 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHashType(HmacPrfParameters.HashType hashType) {
/*  71 */       this.hashType = hashType;
/*  72 */       return this;
/*     */     }
/*     */     
/*     */     public HmacPrfParameters build() throws GeneralSecurityException {
/*  76 */       if (this.keySizeBytes == null) {
/*  77 */         throw new GeneralSecurityException("key size is not set");
/*     */       }
/*  79 */       if (this.hashType == null) {
/*  80 */         throw new GeneralSecurityException("hash type is not set");
/*     */       }
/*  82 */       return new HmacPrfParameters(this.keySizeBytes.intValue(), this.hashType);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */   
/*     */   private HmacPrfParameters(int keySizeBytes, HashType hashType) {
/*  90 */     this.keySizeBytes = keySizeBytes;
/*  91 */     this.hashType = hashType;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/*  95 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getKeySizeBytes() {
/*  99 */     return this.keySizeBytes;
/*     */   }
/*     */   
/*     */   public HashType getHashType() {
/* 103 */     return this.hashType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 108 */     if (!(o instanceof HmacPrfParameters)) {
/* 109 */       return false;
/*     */     }
/* 111 */     HmacPrfParameters that = (HmacPrfParameters)o;
/* 112 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that.getHashType() == getHashType());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     return Objects.hash(new Object[] { HmacPrfParameters.class, Integer.valueOf(this.keySizeBytes), this.hashType });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 127 */     return "HMAC PRF Parameters (hashType: " + this.hashType + " and " + this.keySizeBytes + "-byte key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\HmacPrfParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */