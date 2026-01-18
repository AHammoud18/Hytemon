/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
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
/*     */ public final class LegacyKmsEnvelopeAeadParameters
/*     */   extends AeadParameters
/*     */ {
/*     */   private final Variant variant;
/*     */   private final String kekUri;
/*     */   private final DekParsingStrategy dekParsingStrategy;
/*     */   private final AeadParameters dekParametersForNewKeys;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  74 */     public static final Variant TINK = new Variant("TINK");
/*  75 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  80 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  85 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   public static final class DekParsingStrategy
/*     */   {
/*  97 */     public static final DekParsingStrategy ASSUME_AES_GCM = new DekParsingStrategy("ASSUME_AES_GCM");
/*     */ 
/*     */ 
/*     */     
/* 101 */     public static final DekParsingStrategy ASSUME_XCHACHA20POLY1305 = new DekParsingStrategy("ASSUME_XCHACHA20POLY1305");
/*     */ 
/*     */ 
/*     */     
/* 105 */     public static final DekParsingStrategy ASSUME_CHACHA20POLY1305 = new DekParsingStrategy("ASSUME_CHACHA20POLY1305");
/*     */ 
/*     */ 
/*     */     
/* 109 */     public static final DekParsingStrategy ASSUME_AES_CTR_HMAC = new DekParsingStrategy("ASSUME_AES_CTR_HMAC");
/*     */ 
/*     */ 
/*     */     
/* 113 */     public static final DekParsingStrategy ASSUME_AES_EAX = new DekParsingStrategy("ASSUME_AES_EAX");
/*     */ 
/*     */ 
/*     */     
/* 117 */     public static final DekParsingStrategy ASSUME_AES_GCM_SIV = new DekParsingStrategy("ASSUME_AES_GCM_SIV");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     private DekParsingStrategy(String name) {
/* 123 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 128 */       return this.name;
/*     */     }
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
/*     */   private LegacyKmsEnvelopeAeadParameters(Variant variant, String kekUri, DekParsingStrategy dekParsingStrategy, AeadParameters dekParametersForNewKeys) {
/* 142 */     this.variant = variant;
/* 143 */     this.kekUri = kekUri;
/* 144 */     this.dekParsingStrategy = dekParsingStrategy;
/* 145 */     this.dekParametersForNewKeys = dekParametersForNewKeys;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*     */     private LegacyKmsEnvelopeAeadParameters.Variant variant;
/*     */     
/*     */     @Nullable
/*     */     private String kekUri;
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setVariant(LegacyKmsEnvelopeAeadParameters.Variant variant) {
/* 159 */       this.variant = variant;
/* 160 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private LegacyKmsEnvelopeAeadParameters.DekParsingStrategy dekParsingStrategy;
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setKekUri(String kekUri) {
/* 170 */       this.kekUri = kekUri;
/* 171 */       return this;
/*     */     } @Nullable
/*     */     private AeadParameters dekParametersForNewKeys; private Builder() {}
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setDekParsingStrategy(LegacyKmsEnvelopeAeadParameters.DekParsingStrategy dekParsingStrategy) {
/* 176 */       this.dekParsingStrategy = dekParsingStrategy;
/* 177 */       return this;
/*     */     }
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setDekParametersForNewKeys(AeadParameters aeadParameters) {
/* 182 */       this.dekParametersForNewKeys = aeadParameters;
/* 183 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean parsingStrategyAllowed(LegacyKmsEnvelopeAeadParameters.DekParsingStrategy parsingStrategy, AeadParameters aeadParameters) {
/* 188 */       if (parsingStrategy.equals(LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_GCM) && aeadParameters instanceof AesGcmParameters)
/*     */       {
/* 190 */         return true;
/*     */       }
/* 192 */       if (parsingStrategy.equals(LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_CHACHA20POLY1305) && aeadParameters instanceof ChaCha20Poly1305Parameters)
/*     */       {
/* 194 */         return true;
/*     */       }
/* 196 */       if (parsingStrategy.equals(LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_XCHACHA20POLY1305) && aeadParameters instanceof XChaCha20Poly1305Parameters)
/*     */       {
/* 198 */         return true;
/*     */       }
/* 200 */       if (parsingStrategy.equals(LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_CTR_HMAC) && aeadParameters instanceof AesCtrHmacAeadParameters)
/*     */       {
/* 202 */         return true;
/*     */       }
/* 204 */       if (parsingStrategy.equals(LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_EAX) && aeadParameters instanceof AesEaxParameters)
/*     */       {
/* 206 */         return true;
/*     */       }
/* 208 */       if (parsingStrategy.equals(LegacyKmsEnvelopeAeadParameters.DekParsingStrategy.ASSUME_AES_GCM_SIV) && aeadParameters instanceof AesGcmSivParameters)
/*     */       {
/* 210 */         return true;
/*     */       }
/* 212 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public LegacyKmsEnvelopeAeadParameters build() throws GeneralSecurityException {
/* 217 */       if (this.variant == null)
/*     */       {
/* 219 */         this.variant = LegacyKmsEnvelopeAeadParameters.Variant.NO_PREFIX;
/*     */       }
/* 221 */       if (this.kekUri == null) {
/* 222 */         throw new GeneralSecurityException("kekUri must be set");
/*     */       }
/* 224 */       if (this.dekParsingStrategy == null) {
/* 225 */         throw new GeneralSecurityException("dekParsingStrategy must be set");
/*     */       }
/* 227 */       if (this.dekParametersForNewKeys == null) {
/* 228 */         throw new GeneralSecurityException("dekParametersForNewKeys must be set");
/*     */       }
/* 230 */       if (this.dekParametersForNewKeys.hasIdRequirement()) {
/* 231 */         throw new GeneralSecurityException("dekParametersForNewKeys must not have ID Requirements");
/*     */       }
/* 233 */       if (!parsingStrategyAllowed(this.dekParsingStrategy, this.dekParametersForNewKeys)) {
/* 234 */         throw new GeneralSecurityException("Cannot use parsing strategy " + this.dekParsingStrategy
/*     */             
/* 236 */             .toString() + " when new keys are picked according to " + this.dekParametersForNewKeys + ".");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 242 */       return new LegacyKmsEnvelopeAeadParameters(this.variant, this.kekUri, this.dekParsingStrategy, this.dekParametersForNewKeys);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Builder builder() {
/* 253 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public String getKekUri() {
/* 263 */     return this.kekUri;
/*     */   }
/*     */   
/*     */   public Variant getVariant() {
/* 267 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 272 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DekParsingStrategy getDekParsingStrategy() {
/* 281 */     return this.dekParsingStrategy;
/*     */   }
/*     */ 
/*     */   
/*     */   public AeadParameters getDekParametersForNewKeys() {
/* 286 */     return this.dekParametersForNewKeys;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 291 */     if (!(o instanceof LegacyKmsEnvelopeAeadParameters)) {
/* 292 */       return false;
/*     */     }
/* 294 */     LegacyKmsEnvelopeAeadParameters that = (LegacyKmsEnvelopeAeadParameters)o;
/* 295 */     return (that.dekParsingStrategy.equals(this.dekParsingStrategy) && that.dekParametersForNewKeys
/* 296 */       .equals(this.dekParametersForNewKeys) && that.kekUri
/* 297 */       .equals(this.kekUri) && that.variant
/* 298 */       .equals(this.variant));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 303 */     return Objects.hash(new Object[] { LegacyKmsEnvelopeAeadParameters.class, this.kekUri, this.dekParsingStrategy, this.dekParametersForNewKeys, this.variant });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 313 */     return "LegacyKmsEnvelopeAead Parameters (kekUri: " + this.kekUri + ", dekParsingStrategy: " + this.dekParsingStrategy + ", dekParametersForNewKeys: " + this.dekParametersForNewKeys + ", variant: " + this.variant + ")";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\LegacyKmsEnvelopeAeadParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */