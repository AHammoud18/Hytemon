/*     */ package com.google.crypto.tink.keyderivation;
/*     */ 
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.prf.PrfParameters;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.Immutable;
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
/*     */ @Immutable
/*     */ public final class PrfBasedKeyDerivationParameters
/*     */   extends KeyDerivationParameters
/*     */ {
/*     */   private final PrfParameters prfParameters;
/*     */   private final Parameters derivedKeyParameters;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @Nullable
/*  32 */     private PrfParameters prfParameters = null;
/*     */     @Nullable
/*  34 */     private Parameters derivedKeyParameters = null;
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setPrfParameters(PrfParameters prfParameters) {
/*  39 */       this.prfParameters = prfParameters;
/*  40 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setDerivedKeyParameters(Parameters derivedKeyParameters) {
/*  49 */       this.derivedKeyParameters = derivedKeyParameters;
/*  50 */       return this;
/*     */     }
/*     */     
/*     */     public PrfBasedKeyDerivationParameters build() throws GeneralSecurityException {
/*  54 */       if (this.prfParameters == null) {
/*  55 */         throw new GeneralSecurityException("PrfParameters must be set.");
/*     */       }
/*  57 */       if (this.derivedKeyParameters == null) {
/*  58 */         throw new GeneralSecurityException("DerivedKeyParameters must be set.");
/*     */       }
/*  60 */       return new PrfBasedKeyDerivationParameters(this.prfParameters, this.derivedKeyParameters);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrfBasedKeyDerivationParameters(PrfParameters prfParameters, Parameters derivedKeyParameters) {
/*  69 */     this.prfParameters = prfParameters;
/*  70 */     this.derivedKeyParameters = derivedKeyParameters;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/*  74 */     return new Builder();
/*     */   }
/*     */ 
/*     */   
/*     */   public PrfParameters getPrfParameters() {
/*  79 */     return this.prfParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Parameters getDerivedKeyParameters() {
/*  88 */     return this.derivedKeyParameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  93 */     if (!(o instanceof PrfBasedKeyDerivationParameters)) {
/*  94 */       return false;
/*     */     }
/*  96 */     PrfBasedKeyDerivationParameters that = (PrfBasedKeyDerivationParameters)o;
/*  97 */     return (that.getPrfParameters().equals(getPrfParameters()) && that
/*  98 */       .getDerivedKeyParameters().equals(getDerivedKeyParameters()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     return Objects.hash(new Object[] { PrfBasedKeyDerivationParameters.class, this.prfParameters, this.derivedKeyParameters });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 108 */     return String.format("PrfBasedKeyDerivationParameters(%s, %s)", new Object[] { this.prfParameters, this.derivedKeyParameters });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\PrfBasedKeyDerivationParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */