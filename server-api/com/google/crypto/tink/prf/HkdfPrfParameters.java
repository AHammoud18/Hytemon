/*     */ package com.google.crypto.tink.prf;
/*     */ 
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ public final class HkdfPrfParameters
/*     */   extends PrfParameters
/*     */ {
/*     */   private static final int MIN_KEY_SIZE = 16;
/*     */   private final int keySizeBytes;
/*     */   private final HashType hashType;
/*     */   @Nullable
/*     */   private final Bytes salt;
/*     */   
/*     */   @Immutable
/*     */   public static final class HashType
/*     */   {
/*  34 */     public static final HashType SHA1 = new HashType("SHA1");
/*  35 */     public static final HashType SHA224 = new HashType("SHA224");
/*  36 */     public static final HashType SHA256 = new HashType("SHA256");
/*  37 */     public static final HashType SHA384 = new HashType("SHA384");
/*  38 */     public static final HashType SHA512 = new HashType("SHA512");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private HashType(String name) {
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
/*  55 */     private HkdfPrfParameters.HashType hashType = null; @Nullable
/*  56 */     private Bytes salt = null;
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) throws GeneralSecurityException {
/*  62 */       if (keySizeBytes < 16)
/*  63 */         throw new InvalidAlgorithmParameterException(
/*  64 */             String.format("Invalid key size %d; only 128-bit or larger are supported", new Object[] {
/*  65 */                 Integer.valueOf(keySizeBytes * 8)
/*     */               })); 
/*  67 */       this.keySizeBytes = Integer.valueOf(keySizeBytes);
/*  68 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHashType(HkdfPrfParameters.HashType hashType) {
/*  73 */       this.hashType = hashType;
/*  74 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setSalt(Bytes salt) {
/*  79 */       if (salt.size() == 0) {
/*  80 */         this.salt = null;
/*  81 */         return this;
/*     */       } 
/*  83 */       this.salt = salt;
/*  84 */       return this;
/*     */     }
/*     */     
/*     */     public HkdfPrfParameters build() throws GeneralSecurityException {
/*  88 */       if (this.keySizeBytes == null) {
/*  89 */         throw new GeneralSecurityException("key size is not set");
/*     */       }
/*  91 */       if (this.hashType == null) {
/*  92 */         throw new GeneralSecurityException("hash type is not set");
/*     */       }
/*  94 */       return new HkdfPrfParameters(this.keySizeBytes.intValue(), this.hashType, this.salt);
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private HkdfPrfParameters(int keySizeBytes, HashType hashType, Bytes salt) {
/* 103 */     this.keySizeBytes = keySizeBytes;
/* 104 */     this.hashType = hashType;
/* 105 */     this.salt = salt;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 109 */     return new Builder();
/*     */   }
/*     */   
/*     */   public int getKeySizeBytes() {
/* 113 */     return this.keySizeBytes;
/*     */   }
/*     */   
/*     */   public HashType getHashType() {
/* 117 */     return this.hashType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Bytes getSalt() {
/* 127 */     return this.salt;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 132 */     if (!(o instanceof HkdfPrfParameters)) {
/* 133 */       return false;
/*     */     }
/* 135 */     HkdfPrfParameters that = (HkdfPrfParameters)o;
/* 136 */     return (that.getKeySizeBytes() == getKeySizeBytes() && that
/* 137 */       .getHashType() == getHashType() && 
/* 138 */       Objects.equals(that.getSalt(), getSalt()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 143 */     return Objects.hash(new Object[] { HkdfPrfParameters.class, Integer.valueOf(this.keySizeBytes), this.hashType, this.salt });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 153 */     return "HKDF PRF Parameters (hashType: " + this.hashType + ", salt: " + this.salt + ", and " + this.keySizeBytes + "-byte key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\HkdfPrfParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */