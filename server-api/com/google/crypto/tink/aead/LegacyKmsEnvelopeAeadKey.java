/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.util.Bytes;
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
/*     */ public final class LegacyKmsEnvelopeAeadKey
/*     */   extends AeadKey
/*     */ {
/*     */   private final LegacyKmsEnvelopeAeadParameters parameters;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private LegacyKmsEnvelopeAeadKey(LegacyKmsEnvelopeAeadParameters parameters, Bytes outputPrefix, @Nullable Integer idRequirement) {
/*  50 */     this.parameters = parameters;
/*  51 */     this.outputPrefix = outputPrefix;
/*  52 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static LegacyKmsEnvelopeAeadKey create(LegacyKmsEnvelopeAeadParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*     */     Bytes outputPrefix;
/*  59 */     if (parameters.getVariant() == LegacyKmsEnvelopeAeadParameters.Variant.NO_PREFIX) {
/*  60 */       if (idRequirement != null) {
/*  61 */         throw new GeneralSecurityException("For given Variant NO_PREFIX the value of idRequirement must be null");
/*     */       }
/*     */       
/*  64 */       outputPrefix = OutputPrefixUtil.EMPTY_PREFIX;
/*  65 */     } else if (parameters.getVariant() == LegacyKmsEnvelopeAeadParameters.Variant.TINK) {
/*  66 */       if (idRequirement == null) {
/*  67 */         throw new GeneralSecurityException("For given Variant TINK the value of idRequirement must be non-null");
/*     */       }
/*     */       
/*  70 */       outputPrefix = OutputPrefixUtil.getTinkOutputPrefix(idRequirement.intValue());
/*     */     } else {
/*  72 */       throw new GeneralSecurityException("Unknown Variant: " + parameters.getVariant());
/*     */     } 
/*  74 */     return new LegacyKmsEnvelopeAeadKey(parameters, outputPrefix, idRequirement);
/*     */   }
/*     */ 
/*     */   
/*     */   public static LegacyKmsEnvelopeAeadKey create(LegacyKmsEnvelopeAeadParameters parameters) throws GeneralSecurityException {
/*  79 */     return create(parameters, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/*  84 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public LegacyKmsEnvelopeAeadParameters getParameters() {
/*  89 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getIdRequirementOrNull() {
/*  94 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/*  99 */     if (!(o instanceof LegacyKmsEnvelopeAeadKey)) {
/* 100 */       return false;
/*     */     }
/* 102 */     LegacyKmsEnvelopeAeadKey that = (LegacyKmsEnvelopeAeadKey)o;
/* 103 */     return (that.parameters.equals(this.parameters) && Objects.equals(that.idRequirement, this.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\LegacyKmsEnvelopeAeadKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */