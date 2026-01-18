/*     */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleSequenceValidator
/*     */   extends DoubleArrayValidator
/*     */ {
/*  10 */   private static final DoubleSequenceValidator VALIDATOR_BETWEEN_01 = new DoubleSequenceValidator(RelationalOperator.GreaterEqual, 0.0D, RelationalOperator.LessEqual, 1.0D, null);
/*  11 */   private static final DoubleSequenceValidator VALIDATOR_BETWEEN_01_WEAKLY_MONOTONIC = new DoubleSequenceValidator(RelationalOperator.GreaterEqual, 0.0D, RelationalOperator.LessEqual, 1.0D, RelationalOperator.LessEqual);
/*  12 */   private static final DoubleSequenceValidator VALIDATOR_BETWEEN_01_MONOTONIC = new DoubleSequenceValidator(RelationalOperator.GreaterEqual, 0.0D, RelationalOperator.LessEqual, 1.0D, RelationalOperator.Less);
/*  13 */   private static final DoubleSequenceValidator VALIDATOR_WEAKLY_MONOTONIC = new DoubleSequenceValidator(RelationalOperator.GreaterEqual, -1.7976931348623157E308D, RelationalOperator.LessEqual, Double.MAX_VALUE, RelationalOperator.LessEqual);
/*  14 */   private static final DoubleSequenceValidator VALIDATOR_MONOTONIC = new DoubleSequenceValidator(RelationalOperator.GreaterEqual, -1.7976931348623157E308D, RelationalOperator.LessEqual, Double.MAX_VALUE, RelationalOperator.Less);
/*     */   
/*     */   private final RelationalOperator relationLower;
/*     */   private final double lower;
/*     */   private final RelationalOperator relationUpper;
/*     */   private final double upper;
/*     */   private final RelationalOperator relationSequence;
/*     */   
/*     */   private DoubleSequenceValidator(RelationalOperator relationLower, double lower, RelationalOperator relationUpper, double upper, RelationalOperator relationSequence) {
/*  23 */     this.lower = lower;
/*  24 */     this.upper = upper;
/*  25 */     this.relationLower = relationLower;
/*  26 */     this.relationUpper = relationUpper;
/*  27 */     this.relationSequence = relationSequence;
/*     */   }
/*     */   
/*     */   public static DoubleSequenceValidator between01() {
/*  31 */     return VALIDATOR_BETWEEN_01;
/*     */   }
/*     */   
/*     */   public static DoubleSequenceValidator between01WeaklyMonotonic() {
/*  35 */     return VALIDATOR_BETWEEN_01_WEAKLY_MONOTONIC;
/*     */   }
/*     */   
/*     */   public static DoubleSequenceValidator between01Monotonic() {
/*  39 */     return VALIDATOR_BETWEEN_01_MONOTONIC;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DoubleSequenceValidator between(double lower, double upper) {
/*  44 */     return new DoubleSequenceValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper, null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DoubleSequenceValidator betweenWeaklyMonotonic(double lower, double upper) {
/*  49 */     return new DoubleSequenceValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper, RelationalOperator.LessEqual);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DoubleSequenceValidator betweenMonotonic(double lower, double upper) {
/*  54 */     return new DoubleSequenceValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper, RelationalOperator.Less);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DoubleSequenceValidator fromExclToIncl(double lower, double upper) {
/*  59 */     return new DoubleSequenceValidator(RelationalOperator.Greater, lower, RelationalOperator.LessEqual, upper, null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DoubleSequenceValidator fromExclToInclWeaklyMonotonic(double lower, double upper) {
/*  64 */     return new DoubleSequenceValidator(RelationalOperator.Greater, lower, RelationalOperator.LessEqual, upper, RelationalOperator.LessEqual);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DoubleSequenceValidator fromExclToInclMonotonic(double lower, double upper) {
/*  69 */     return new DoubleSequenceValidator(RelationalOperator.Greater, lower, RelationalOperator.LessEqual, upper, RelationalOperator.Less);
/*     */   }
/*     */   
/*     */   public static DoubleSequenceValidator monotonic() {
/*  73 */     return VALIDATOR_MONOTONIC;
/*     */   }
/*     */   
/*     */   public static DoubleSequenceValidator weaklyMonotonic() {
/*  77 */     return VALIDATOR_WEAKLY_MONOTONIC;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean test(@Nonnull double[] values) {
/*  82 */     for (int i = 0; i < values.length; i++) {
/*  83 */       double value = values[i];
/*  84 */       if (!DoubleValidator.compare(value, this.relationLower, this.lower) && DoubleValidator.compare(value, this.relationUpper, this.upper)) {
/*  85 */         return false;
/*     */       }
/*  87 */       if (i > 0 && this.relationSequence != null && !DoubleValidator.compare(values[i - 1], this.relationSequence, value)) {
/*  88 */         return false;
/*     */       }
/*     */     } 
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String errorMessage(double[] value) {
/*  97 */     return errorMessage0(value, "Array");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String errorMessage(double[] value, String name) {
/* 103 */     return errorMessage0(value, "\"" + name + "\"");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private String errorMessage0(double[] value, String name) {
/* 108 */     return name + name + (
/* 109 */       (this.relationLower == null) ? "" : (" values should be " + this.relationLower.asText() + " " + this.lower + " and ")) + (
/* 110 */       (this.relationUpper == null) ? "" : (" values should be " + this.relationUpper.asText() + " " + this.upper + " and ")) + " but is " + (
/* 111 */       (this.relationSequence == null) ? "" : (" succeeding values should be " + this.relationSequence.asText() + " preceding values "));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\DoubleSequenceValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */