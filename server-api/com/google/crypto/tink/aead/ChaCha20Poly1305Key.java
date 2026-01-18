/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
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
/*     */ @Immutable
/*     */ public final class ChaCha20Poly1305Key
/*     */   extends AeadKey
/*     */ {
/*     */   private final ChaCha20Poly1305Parameters parameters;
/*     */   private final SecretBytes keyBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private ChaCha20Poly1305Key(ChaCha20Poly1305Parameters parameters, SecretBytes keyBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/*  49 */     this.parameters = parameters;
/*  50 */     this.keyBytes = keyBytes;
/*  51 */     this.outputPrefix = outputPrefix;
/*  52 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Bytes getOutputPrefix(ChaCha20Poly1305Parameters parameters, @Nullable Integer idRequirement) {
/*  57 */     if (parameters.getVariant() == ChaCha20Poly1305Parameters.Variant.NO_PREFIX) {
/*  58 */       return OutputPrefixUtil.EMPTY_PREFIX;
/*     */     }
/*  60 */     if (parameters.getVariant() == ChaCha20Poly1305Parameters.Variant.CRUNCHY) {
/*  61 */       return OutputPrefixUtil.getLegacyOutputPrefix(idRequirement.intValue());
/*     */     }
/*  63 */     if (parameters.getVariant() == ChaCha20Poly1305Parameters.Variant.TINK) {
/*  64 */       return OutputPrefixUtil.getTinkOutputPrefix(idRequirement.intValue());
/*     */     }
/*  66 */     throw new IllegalStateException("Unknown Variant: " + parameters.getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/*  71 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   @AccessesPartialKey
/*     */   public static ChaCha20Poly1305Key create(SecretBytes secretBytes) throws GeneralSecurityException {
/*  82 */     return create(ChaCha20Poly1305Parameters.Variant.NO_PREFIX, secretBytes, null);
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
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static ChaCha20Poly1305Key create(ChaCha20Poly1305Parameters.Variant variant, SecretBytes secretBytes, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  95 */     if (variant != ChaCha20Poly1305Parameters.Variant.NO_PREFIX && idRequirement == null) {
/*  96 */       throw new GeneralSecurityException("For given Variant " + variant + " the value of idRequirement must be non-null");
/*     */     }
/*     */     
/*  99 */     if (variant == ChaCha20Poly1305Parameters.Variant.NO_PREFIX && idRequirement != null) {
/* 100 */       throw new GeneralSecurityException("For given Variant NO_PREFIX the value of idRequirement must be null");
/*     */     }
/*     */     
/* 103 */     if (secretBytes.size() != 32) {
/* 104 */       throw new GeneralSecurityException("ChaCha20Poly1305 key must be constructed with key of length 32 bytes, not " + secretBytes
/*     */           
/* 106 */           .size());
/*     */     }
/* 108 */     ChaCha20Poly1305Parameters parameters = ChaCha20Poly1305Parameters.create(variant);
/* 109 */     return new ChaCha20Poly1305Key(parameters, secretBytes, 
/* 110 */         getOutputPrefix(parameters, idRequirement), idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getKeyBytes() {
/* 119 */     return this.keyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChaCha20Poly1305Parameters getParameters() {
/* 124 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 130 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 135 */     if (!(o instanceof ChaCha20Poly1305Key)) {
/* 136 */       return false;
/*     */     }
/* 138 */     ChaCha20Poly1305Key that = (ChaCha20Poly1305Key)o;
/*     */     
/* 140 */     return (that.parameters.equals(this.parameters) && that.keyBytes
/* 141 */       .equalsSecretBytes(this.keyBytes) && 
/* 142 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\ChaCha20Poly1305Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */