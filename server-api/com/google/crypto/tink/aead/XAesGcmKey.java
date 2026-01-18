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
/*     */ @Immutable
/*     */ public final class XAesGcmKey
/*     */   extends AeadKey
/*     */ {
/*     */   private final XAesGcmParameters parameters;
/*     */   private final SecretBytes keyBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private XAesGcmKey(XAesGcmParameters parameters, SecretBytes keyBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/*  43 */     this.parameters = parameters;
/*  44 */     this.keyBytes = keyBytes;
/*  45 */     this.outputPrefix = outputPrefix;
/*  46 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Bytes getOutputPrefix(XAesGcmParameters parameters, @Nullable Integer idRequirement) {
/*  51 */     if (parameters.getVariant() == XAesGcmParameters.Variant.NO_PREFIX) {
/*  52 */       return OutputPrefixUtil.EMPTY_PREFIX;
/*     */     }
/*  54 */     if (parameters.getVariant() == XAesGcmParameters.Variant.TINK) {
/*  55 */       return OutputPrefixUtil.getTinkOutputPrefix(idRequirement.intValue());
/*     */     }
/*  57 */     throw new IllegalStateException("Unknown Variant: " + parameters.getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/*  62 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static XAesGcmKey create(XAesGcmParameters parameters, SecretBytes secretBytes, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  73 */     if (parameters.getVariant() != XAesGcmParameters.Variant.NO_PREFIX && idRequirement == null) {
/*  74 */       throw new GeneralSecurityException("For given Variant " + parameters
/*     */           
/*  76 */           .getVariant() + " the value of idRequirement must be non-null");
/*     */     }
/*     */     
/*  79 */     if (parameters.getVariant() == XAesGcmParameters.Variant.NO_PREFIX && idRequirement != null) {
/*  80 */       throw new GeneralSecurityException("For given Variant NO_PREFIX the value of idRequirement must be null");
/*     */     }
/*     */     
/*  83 */     if (secretBytes.size() != 32) {
/*  84 */       throw new GeneralSecurityException("XAesGcmKey key must be constructed with key of length 32 bytes, not " + secretBytes
/*     */           
/*  86 */           .size());
/*     */     }
/*  88 */     return new XAesGcmKey(parameters, secretBytes, 
/*  89 */         getOutputPrefix(parameters, idRequirement), idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getKeyBytes() {
/*  98 */     return this.keyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public XAesGcmParameters getParameters() {
/* 103 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 109 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 114 */     if (!(o instanceof XAesGcmKey)) {
/* 115 */       return false;
/*     */     }
/* 117 */     XAesGcmKey that = (XAesGcmKey)o;
/*     */     
/* 119 */     return (that.parameters.equals(this.parameters) && that.keyBytes
/* 120 */       .equalsSecretBytes(this.keyBytes) && 
/* 121 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\XAesGcmKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */