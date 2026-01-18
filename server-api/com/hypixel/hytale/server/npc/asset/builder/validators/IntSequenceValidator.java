/*     */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntSequenceValidator
/*     */   extends IntArrayValidator
/*     */ {
/*  10 */   private static final IntSequenceValidator VALIDATOR_BETWEEN_01 = new IntSequenceValidator(RelationalOperator.GreaterEqual, 0, RelationalOperator.LessEqual, 1, null);
/*  11 */   private static final IntSequenceValidator VALIDATOR_BETWEEN_01_WEAKLY_MONOTONIC = new IntSequenceValidator(RelationalOperator.GreaterEqual, 0, RelationalOperator.LessEqual, 1, RelationalOperator.LessEqual);
/*  12 */   private static final IntSequenceValidator VALIDATOR_BETWEEN_01_MONOTONIC = new IntSequenceValidator(RelationalOperator.GreaterEqual, 0, RelationalOperator.LessEqual, 1, RelationalOperator.Less);
/*     */   private final RelationalOperator relationLower;
/*     */   private final int lower;
/*     */   private final RelationalOperator relationUpper;
/*     */   private final int upper;
/*     */   private final RelationalOperator relationSequence;
/*     */   
/*     */   private IntSequenceValidator(RelationalOperator relationLower, int lower, RelationalOperator relationUpper, int upper, RelationalOperator relationSequence) {
/*  20 */     this.lower = lower;
/*  21 */     this.upper = upper;
/*  22 */     this.relationLower = relationLower;
/*  23 */     this.relationUpper = relationUpper;
/*  24 */     this.relationSequence = relationSequence;
/*     */   }
/*     */   
/*     */   public static IntSequenceValidator between01() {
/*  28 */     return VALIDATOR_BETWEEN_01;
/*     */   }
/*     */   
/*     */   public static IntSequenceValidator between01WeaklyMonotonic() {
/*  32 */     return VALIDATOR_BETWEEN_01_WEAKLY_MONOTONIC;
/*     */   }
/*     */   
/*     */   public static IntSequenceValidator between01Monotonic() {
/*  36 */     return VALIDATOR_BETWEEN_01_MONOTONIC;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static IntSequenceValidator between(int lower, int upper) {
/*  41 */     return new IntSequenceValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper, null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static IntSequenceValidator betweenWeaklyMonotonic(int lower, int upper) {
/*  46 */     return new IntSequenceValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper, RelationalOperator.LessEqual);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static IntSequenceValidator betweenMonotonic(int lower, int upper) {
/*  51 */     return new IntSequenceValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper, RelationalOperator.Less);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static IntSequenceValidator fromExclToIncl(int lower, int upper) {
/*  56 */     return new IntSequenceValidator(RelationalOperator.Greater, lower, RelationalOperator.LessEqual, upper, null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static IntSequenceValidator fromExclToInclWeaklyMonotonic(int lower, int upper) {
/*  61 */     return new IntSequenceValidator(RelationalOperator.Greater, lower, RelationalOperator.LessEqual, upper, RelationalOperator.LessEqual);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static IntSequenceValidator fromExclToInclMonotonic(int lower, int upper) {
/*  66 */     return new IntSequenceValidator(RelationalOperator.Greater, lower, RelationalOperator.LessEqual, upper, RelationalOperator.Less);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean test(@Nonnull int[] values) {
/*  71 */     for (int i = 0; i < values.length; i++) {
/*  72 */       int value = values[i];
/*  73 */       if (!IntValidator.compare(value, this.relationLower, this.lower) && IntValidator.compare(value, this.relationUpper, this.upper)) {
/*  74 */         return false;
/*     */       }
/*  76 */       if (i > 0 && this.relationSequence != null && !IntValidator.compare(values[i - 1], this.relationSequence, value)) {
/*  77 */         return false;
/*     */       }
/*     */     } 
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String errorMessage(int[] value) {
/*  86 */     return errorMessage0(value, "Array");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String errorMessage(int[] value, String name) {
/*  92 */     return errorMessage0(value, "\"" + name + "\"");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private String errorMessage0(int[] value, String name) {
/*  97 */     return name + name + (
/*  98 */       (this.relationLower == null) ? "" : (" values should be " + this.relationLower.asText() + " " + this.lower + " and ")) + (
/*  99 */       (this.relationUpper == null) ? "" : (" values should be " + this.relationUpper.asText() + " " + this.upper + " and ")) + " but is " + (
/* 100 */       (this.relationSequence == null) ? "" : (" succeeding values should be " + this.relationSequence.asText() + " preceding values "));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\IntSequenceValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */