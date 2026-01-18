/*     */ package com.google.crypto.tink.keyderivation;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.prf.PrfKey;
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
/*     */ public final class PrfBasedKeyDerivationKey
/*     */   extends KeyDerivationKey
/*     */ {
/*     */   private final PrfBasedKeyDerivationParameters parameters;
/*     */   private final PrfKey prfKey;
/*     */   private final Integer idRequirementOrNull;
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static PrfBasedKeyDerivationKey create(PrfBasedKeyDerivationParameters parameters, PrfKey prfKey, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  43 */     if (!parameters.getPrfParameters().equals(prfKey.getParameters())) {
/*  44 */       throw new GeneralSecurityException("PrfParameters of passed in PrfBasedKeyDerivationParameters and passed in prfKey parameters object must match. DerivationParameters gave: " + parameters
/*     */ 
/*     */           
/*  47 */           .getPrfParameters() + ", key gives: " + prfKey
/*     */           
/*  49 */           .getParameters());
/*     */     }
/*  51 */     if (parameters.getDerivedKeyParameters().hasIdRequirement() && 
/*  52 */       idRequirement == null) {
/*  53 */       throw new GeneralSecurityException("Derived key has an ID requirement, but no idRequirement was passed in on creation of this key");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  58 */     if (!parameters.getDerivedKeyParameters().hasIdRequirement() && 
/*  59 */       idRequirement != null) {
/*  60 */       throw new GeneralSecurityException("Derived key has no ID requirement, but idRequirement was passed in on creation of this key");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  65 */     return new PrfBasedKeyDerivationKey(parameters, prfKey, idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrfBasedKeyDerivationKey(PrfBasedKeyDerivationParameters parameters, PrfKey prfKey, @Nullable Integer idRequirement) {
/*  74 */     this.parameters = parameters;
/*  75 */     this.prfKey = prfKey;
/*  76 */     this.idRequirementOrNull = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public PrfKey getPrfKey() {
/*  85 */     return this.prfKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrfBasedKeyDerivationParameters getParameters() {
/*  90 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/*  96 */     return this.idRequirementOrNull;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key other) {
/* 101 */     if (!(other instanceof PrfBasedKeyDerivationKey)) {
/* 102 */       return false;
/*     */     }
/*     */     
/* 105 */     PrfBasedKeyDerivationKey otherKey = (PrfBasedKeyDerivationKey)other;
/* 106 */     return (otherKey.getParameters().equals(getParameters()) && otherKey.prfKey
/* 107 */       .equalsKey((Key)this.prfKey) && 
/* 108 */       Objects.equals(otherKey.idRequirementOrNull, this.idRequirementOrNull));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\PrfBasedKeyDerivationKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */