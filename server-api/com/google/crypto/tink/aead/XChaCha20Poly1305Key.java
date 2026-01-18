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
/*     */ 
/*     */ @Immutable
/*     */ public final class XChaCha20Poly1305Key
/*     */   extends AeadKey
/*     */ {
/*     */   private final XChaCha20Poly1305Parameters parameters;
/*     */   private final SecretBytes keyBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private XChaCha20Poly1305Key(XChaCha20Poly1305Parameters parameters, SecretBytes keyBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/*  50 */     this.parameters = parameters;
/*  51 */     this.keyBytes = keyBytes;
/*  52 */     this.outputPrefix = outputPrefix;
/*  53 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Bytes getOutputPrefix(XChaCha20Poly1305Parameters parameters, @Nullable Integer idRequirement) {
/*  58 */     if (parameters.getVariant() == XChaCha20Poly1305Parameters.Variant.NO_PREFIX) {
/*  59 */       return OutputPrefixUtil.EMPTY_PREFIX;
/*     */     }
/*  61 */     if (parameters.getVariant() == XChaCha20Poly1305Parameters.Variant.CRUNCHY) {
/*  62 */       return OutputPrefixUtil.getLegacyOutputPrefix(idRequirement.intValue());
/*     */     }
/*  64 */     if (parameters.getVariant() == XChaCha20Poly1305Parameters.Variant.TINK) {
/*  65 */       return OutputPrefixUtil.getTinkOutputPrefix(idRequirement.intValue());
/*     */     }
/*  67 */     throw new IllegalStateException("Unknown Variant: " + parameters.getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/*  72 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   @AccessesPartialKey
/*     */   public static XChaCha20Poly1305Key create(SecretBytes secretBytes) throws GeneralSecurityException {
/*  83 */     return create(XChaCha20Poly1305Parameters.Variant.NO_PREFIX, secretBytes, null);
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
/*     */   public static XChaCha20Poly1305Key create(XChaCha20Poly1305Parameters.Variant variant, SecretBytes secretBytes, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  96 */     if (variant != XChaCha20Poly1305Parameters.Variant.NO_PREFIX && idRequirement == null) {
/*  97 */       throw new GeneralSecurityException("For given Variant " + variant + " the value of idRequirement must be non-null");
/*     */     }
/*     */     
/* 100 */     if (variant == XChaCha20Poly1305Parameters.Variant.NO_PREFIX && idRequirement != null) {
/* 101 */       throw new GeneralSecurityException("For given Variant NO_PREFIX the value of idRequirement must be null");
/*     */     }
/*     */     
/* 104 */     if (secretBytes.size() != 32) {
/* 105 */       throw new GeneralSecurityException("XChaCha20Poly1305 key must be constructed with key of length 32 bytes, not " + secretBytes
/*     */           
/* 107 */           .size());
/*     */     }
/* 109 */     XChaCha20Poly1305Parameters parameters = XChaCha20Poly1305Parameters.create(variant);
/* 110 */     return new XChaCha20Poly1305Key(parameters, secretBytes, 
/* 111 */         getOutputPrefix(parameters, idRequirement), idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getKeyBytes() {
/* 120 */     return this.keyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public XChaCha20Poly1305Parameters getParameters() {
/* 125 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 131 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 136 */     if (!(o instanceof XChaCha20Poly1305Key)) {
/* 137 */       return false;
/*     */     }
/* 139 */     XChaCha20Poly1305Key that = (XChaCha20Poly1305Key)o;
/*     */     
/* 141 */     return (that.parameters.equals(this.parameters) && that.keyBytes
/* 142 */       .equalsSecretBytes(this.keyBytes) && 
/* 143 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\XChaCha20Poly1305Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */