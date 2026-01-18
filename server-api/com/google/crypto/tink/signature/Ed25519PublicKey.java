/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ public final class Ed25519PublicKey
/*     */   extends SignaturePublicKey
/*     */ {
/*     */   private final Ed25519Parameters parameters;
/*     */   private final Bytes publicKeyBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private Ed25519PublicKey(Ed25519Parameters parameters, Bytes publicKeyBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/*  42 */     this.parameters = parameters;
/*  43 */     this.publicKeyBytes = publicKeyBytes;
/*  44 */     this.outputPrefix = outputPrefix;
/*  45 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Bytes createOutputPrefix(Ed25519Parameters parameters, @Nullable Integer idRequirement) {
/*  50 */     if (parameters.getVariant() == Ed25519Parameters.Variant.NO_PREFIX) {
/*  51 */       return OutputPrefixUtil.EMPTY_PREFIX;
/*     */     }
/*  53 */     if (parameters.getVariant() == Ed25519Parameters.Variant.CRUNCHY || parameters
/*  54 */       .getVariant() == Ed25519Parameters.Variant.LEGACY) {
/*  55 */       return OutputPrefixUtil.getLegacyOutputPrefix(idRequirement.intValue());
/*     */     }
/*  57 */     if (parameters.getVariant() == Ed25519Parameters.Variant.TINK) {
/*  58 */       return OutputPrefixUtil.getTinkOutputPrefix(idRequirement.intValue());
/*     */     }
/*  60 */     throw new IllegalStateException("Unknown Variant: " + parameters.getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/*  65 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   @AccessesPartialKey
/*     */   public static Ed25519PublicKey create(Bytes publicKeyBytes) throws GeneralSecurityException {
/*  75 */     return create(Ed25519Parameters.Variant.NO_PREFIX, publicKeyBytes, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static Ed25519PublicKey create(Ed25519Parameters.Variant variant, Bytes publicKeyBytes, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  86 */     Ed25519Parameters parameters = Ed25519Parameters.create(variant);
/*  87 */     if (!variant.equals(Ed25519Parameters.Variant.NO_PREFIX) && idRequirement == null) {
/*  88 */       throw new GeneralSecurityException("For given Variant " + variant + " the value of idRequirement must be non-null");
/*     */     }
/*     */ 
/*     */     
/*  92 */     if (variant.equals(Ed25519Parameters.Variant.NO_PREFIX) && idRequirement != null) {
/*  93 */       throw new GeneralSecurityException("For given Variant NO_PREFIX the value of idRequirement must be null");
/*     */     }
/*     */     
/*  96 */     if (publicKeyBytes.size() != 32) {
/*  97 */       throw new GeneralSecurityException("Ed25519 key must be constructed with key of length 32 bytes, not " + publicKeyBytes
/*     */           
/*  99 */           .size());
/*     */     }
/*     */     
/* 102 */     return new Ed25519PublicKey(parameters, publicKeyBytes, 
/* 103 */         createOutputPrefix(parameters, idRequirement), idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public Bytes getPublicKeyBytes() {
/* 112 */     return this.publicKeyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ed25519Parameters getParameters() {
/* 117 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 123 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 128 */     if (!(o instanceof Ed25519PublicKey)) {
/* 129 */       return false;
/*     */     }
/* 131 */     Ed25519PublicKey that = (Ed25519PublicKey)o;
/*     */     
/* 133 */     return (that.parameters.equals(this.parameters) && that.publicKeyBytes
/* 134 */       .equals(this.publicKeyBytes) && 
/* 135 */       Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\Ed25519PublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */