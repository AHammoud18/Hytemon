/*     */ package com.google.crypto.tink.jwt;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.subtle.Base64;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Optional;
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
/*     */ public final class JwtHmacKey
/*     */   extends JwtMacKey
/*     */ {
/*     */   private final JwtHmacParameters parameters;
/*     */   private final SecretBytes key;
/*     */   private final Optional<Integer> idRequirement;
/*     */   private final Optional<String> kid;
/*     */   
/*     */   public static class Builder
/*     */   {
/*  39 */     private Optional<JwtHmacParameters> parameters = Optional.empty();
/*  40 */     private Optional<SecretBytes> keyBytes = Optional.empty();
/*  41 */     private Optional<Integer> idRequirement = Optional.empty();
/*  42 */     private Optional<String> customKid = Optional.empty();
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setParameters(JwtHmacParameters parameters) {
/*  48 */       this.parameters = Optional.of(parameters);
/*  49 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKeyBytes(SecretBytes keyBytes) {
/*  54 */       this.keyBytes = Optional.of(keyBytes);
/*  55 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setIdRequirement(int idRequirement) {
/*  60 */       this.idRequirement = Optional.of(Integer.valueOf(idRequirement));
/*  61 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setCustomKid(String customKid) {
/*  66 */       this.customKid = Optional.of(customKid);
/*  67 */       return this;
/*     */     }
/*     */     
/*     */     private Optional<String> computeKid() throws GeneralSecurityException {
/*  71 */       if (((JwtHmacParameters)this.parameters
/*  72 */         .get())
/*  73 */         .getKidStrategy()
/*  74 */         .equals(JwtHmacParameters.KidStrategy.BASE64_ENCODED_KEY_ID)) {
/*  75 */         byte[] bigEndianKeyId = ByteBuffer.allocate(4).putInt(((Integer)this.idRequirement.get()).intValue()).array();
/*  76 */         if (this.customKid.isPresent()) {
/*  77 */           throw new GeneralSecurityException("customKid must not be set for KidStrategy BASE64_ENCODED_KEY_ID");
/*     */         }
/*     */         
/*  80 */         return Optional.of(Base64.urlSafeEncode(bigEndianKeyId));
/*     */       } 
/*  82 */       if (((JwtHmacParameters)this.parameters.get()).getKidStrategy().equals(JwtHmacParameters.KidStrategy.CUSTOM)) {
/*  83 */         if (!this.customKid.isPresent()) {
/*  84 */           throw new GeneralSecurityException("customKid needs to be set for KidStrategy CUSTOM");
/*     */         }
/*  86 */         return this.customKid;
/*     */       } 
/*  88 */       if (((JwtHmacParameters)this.parameters.get()).getKidStrategy().equals(JwtHmacParameters.KidStrategy.IGNORED)) {
/*  89 */         if (this.customKid.isPresent()) {
/*  90 */           throw new GeneralSecurityException("customKid must not be set for KidStrategy IGNORED");
/*     */         }
/*  92 */         return Optional.empty();
/*     */       } 
/*  94 */       throw new IllegalStateException("Unknown kid strategy");
/*     */     }
/*     */     
/*     */     public JwtHmacKey build() throws GeneralSecurityException {
/*  98 */       if (!this.parameters.isPresent()) {
/*  99 */         throw new GeneralSecurityException("Parameters are required");
/*     */       }
/* 101 */       if (!this.keyBytes.isPresent()) {
/* 102 */         throw new GeneralSecurityException("KeyBytes are required");
/*     */       }
/*     */       
/* 105 */       if (((JwtHmacParameters)this.parameters.get()).getKeySizeBytes() != ((SecretBytes)this.keyBytes.get()).size()) {
/* 106 */         throw new GeneralSecurityException("Key size mismatch");
/*     */       }
/*     */       
/* 109 */       if (((JwtHmacParameters)this.parameters.get()).hasIdRequirement() && !this.idRequirement.isPresent()) {
/* 110 */         throw new GeneralSecurityException("Cannot create key without ID requirement with parameters with ID requirement");
/*     */       }
/*     */ 
/*     */       
/* 114 */       if (!((JwtHmacParameters)this.parameters.get()).hasIdRequirement() && this.idRequirement.isPresent()) {
/* 115 */         throw new GeneralSecurityException("Cannot create key with ID requirement with parameters without ID requirement");
/*     */       }
/*     */ 
/*     */       
/* 119 */       return new JwtHmacKey(this.parameters.get(), this.keyBytes.get(), this.idRequirement, computeKid());
/*     */     }
/*     */ 
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 129 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JwtHmacKey(JwtHmacParameters parameters, SecretBytes key, Optional<Integer> idRequirement, Optional<String> kid) {
/* 137 */     this.parameters = parameters;
/* 138 */     this.key = key;
/* 139 */     this.idRequirement = idRequirement;
/* 140 */     this.kid = kid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getKeyBytes() {
/* 149 */     return this.key;
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
/*     */ 
/*     */   
/*     */   public Optional<String> getKid() {
/* 163 */     return this.kid;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 169 */     return this.idRequirement.orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public JwtHmacParameters getParameters() {
/* 174 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 179 */     if (!(o instanceof JwtHmacKey)) {
/* 180 */       return false;
/*     */     }
/* 182 */     JwtHmacKey that = (JwtHmacKey)o;
/* 183 */     return (that.parameters.equals(this.parameters) && that.key
/* 184 */       .equalsSecretBytes(this.key) && that.kid
/* 185 */       .equals(this.kid) && that.idRequirement
/* 186 */       .equals(this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtHmacKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */