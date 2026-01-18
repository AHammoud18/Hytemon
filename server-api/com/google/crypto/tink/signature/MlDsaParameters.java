/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.util.Objects;
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
/*     */ public final class MlDsaParameters
/*     */   extends SignatureParameters
/*     */ {
/*     */   private final MlDsaInstance mlDsaInstance;
/*     */   private final Variant variant;
/*     */   
/*     */   @Immutable
/*     */   public static final class Variant
/*     */   {
/*  32 */     public static final Variant TINK = new Variant("TINK");
/*  33 */     public static final Variant NO_PREFIX = new Variant("NO_PREFIX");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private Variant(String name) {
/*  38 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  43 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   public static final class MlDsaInstance
/*     */   {
/*  56 */     public static final MlDsaInstance ML_DSA_65 = new MlDsaInstance("ML_DSA_65");
/*  57 */     public static final MlDsaInstance ML_DSA_87 = new MlDsaInstance("ML_DSA_87");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private MlDsaInstance(String name) {
/*  62 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  67 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MlDsaParameters create(MlDsaInstance mlDsaInstance, Variant variant) {
/*  76 */     return new MlDsaParameters(mlDsaInstance, variant);
/*     */   }
/*     */   
/*     */   private MlDsaParameters(MlDsaInstance mlDsaInstance, Variant variant) {
/*  80 */     this.mlDsaInstance = mlDsaInstance;
/*  81 */     this.variant = variant;
/*     */   }
/*     */   
/*     */   public MlDsaInstance getMlDsaInstance() {
/*  85 */     return this.mlDsaInstance;
/*     */   }
/*     */   
/*     */   public Variant getVariant() {
/*  89 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  94 */     if (!(o instanceof MlDsaParameters)) {
/*  95 */       return false;
/*     */     }
/*  97 */     MlDsaParameters other = (MlDsaParameters)o;
/*  98 */     return (other.getMlDsaInstance() == getMlDsaInstance() && other.getVariant() == getVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     return Objects.hash(new Object[] { MlDsaParameters.class, this.mlDsaInstance, this.variant });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIdRequirement() {
/* 108 */     return (this.variant != Variant.NO_PREFIX);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 113 */     return "ML-DSA Parameters (ML-DSA instance: " + this.mlDsaInstance + ", variant: " + this.variant + ")";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\MlDsaParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */