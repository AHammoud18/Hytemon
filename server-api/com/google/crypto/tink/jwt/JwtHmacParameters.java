/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
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
/*     */ 
/*     */ public class JwtHmacParameters
/*     */   extends JwtMacParameters
/*     */ {
/*     */   private final int keySizeBytes;
/*     */   private final KidStrategy kidStrategy;
/*     */   private final Algorithm algorithm;
/*     */   
/*     */   @Immutable
/*     */   public static final class KidStrategy
/*     */   {
/*  40 */     public static final KidStrategy BASE64_ENCODED_KEY_ID = new KidStrategy("BASE64_ENCODED_KEY_ID");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     public static final KidStrategy IGNORED = new KidStrategy("IGNORED");
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
/*  64 */     public static final KidStrategy CUSTOM = new KidStrategy("CUSTOM");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private KidStrategy(String name) {
/*  69 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  74 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   public static final class Algorithm
/*     */   {
/*  81 */     public static final Algorithm HS256 = new Algorithm("HS256");
/*  82 */     public static final Algorithm HS384 = new Algorithm("HS384");
/*  83 */     public static final Algorithm HS512 = new Algorithm("HS512");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Algorithm(String name) {
/*  88 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  93 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getStandardName() {
/*  97 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Builder
/*     */   {
/* 103 */     Optional<Integer> keySizeBytes = Optional.empty();
/* 104 */     Optional<JwtHmacParameters.KidStrategy> kidStrategy = Optional.empty();
/* 105 */     Optional<JwtHmacParameters.Algorithm> algorithm = Optional.empty();
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeySizeBytes(int keySizeBytes) {
/* 109 */       this.keySizeBytes = Optional.of(Integer.valueOf(keySizeBytes));
/* 110 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKidStrategy(JwtHmacParameters.KidStrategy kidStrategy) {
/* 115 */       this.kidStrategy = Optional.of(kidStrategy);
/* 116 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setAlgorithm(JwtHmacParameters.Algorithm algorithm) {
/* 121 */       this.algorithm = Optional.of(algorithm);
/* 122 */       return this;
/*     */     }
/*     */     
/*     */     public JwtHmacParameters build() throws GeneralSecurityException {
/* 126 */       if (!this.keySizeBytes.isPresent()) {
/* 127 */         throw new GeneralSecurityException("Key Size must be set");
/*     */       }
/* 129 */       if (!this.algorithm.isPresent()) {
/* 130 */         throw new GeneralSecurityException("Algorithm must be set");
/*     */       }
/* 132 */       if (!this.kidStrategy.isPresent()) {
/* 133 */         throw new GeneralSecurityException("KidStrategy must be set");
/*     */       }
/* 135 */       if (((Integer)this.keySizeBytes.get()).intValue() < 16) {
/* 136 */         throw new GeneralSecurityException("Key size must be at least 16 bytes");
/*     */       }
/* 138 */       return new JwtHmacParameters(((Integer)this.keySizeBytes.get()).intValue(), this.kidStrategy.get(), this.algorithm.get());
/*     */     }
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 145 */     return new Builder();
/*     */   }
/*     */   
/*     */   private JwtHmacParameters(int keySizeBytes, KidStrategy kidStrategy, Algorithm algorithm) {
/* 149 */     this.keySizeBytes = keySizeBytes;
/* 150 */     this.kidStrategy = kidStrategy;
/* 151 */     this.algorithm = algorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getKeySizeBytes() {
/* 159 */     return this.keySizeBytes;
/*     */   }
/*     */   
/*     */   public KidStrategy getKidStrategy() {
/* 163 */     return this.kidStrategy;
/*     */   }
/*     */   
/*     */   public Algorithm getAlgorithm() {
/* 167 */     return this.algorithm;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 172 */     return this.kidStrategy.equals(KidStrategy.BASE64_ENCODED_KEY_ID);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowKidAbsent() {
/* 177 */     return (this.kidStrategy.equals(KidStrategy.CUSTOM) || this.kidStrategy
/* 178 */       .equals(KidStrategy.IGNORED));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 183 */     if (!(o instanceof JwtHmacParameters)) {
/* 184 */       return false;
/*     */     }
/* 186 */     JwtHmacParameters that = (JwtHmacParameters)o;
/* 187 */     return (that.keySizeBytes == this.keySizeBytes && that.kidStrategy
/* 188 */       .equals(this.kidStrategy) && that.algorithm
/* 189 */       .equals(this.algorithm));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 194 */     return Objects.hash(new Object[] { JwtHmacParameters.class, Integer.valueOf(this.keySizeBytes), this.kidStrategy, this.algorithm });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 199 */     return "JWT HMAC Parameters (kidStrategy: " + this.kidStrategy + ", Algorithm " + this.algorithm + ", and " + this.keySizeBytes + "-byte key)";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtHmacParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */