/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class DoubleRangeValidator
/*    */   extends DoubleValidator
/*    */ {
/*  9 */   private static final DoubleRangeValidator VALIDATOR_BETWEEN_01 = new DoubleRangeValidator(RelationalOperator.GreaterEqual, 0.0D, RelationalOperator.LessEqual, 1.0D);
/*    */   
/*    */   private final RelationalOperator relationLower;
/*    */   private final double lower;
/*    */   private final RelationalOperator relationUpper;
/*    */   private final double upper;
/*    */   
/*    */   private DoubleRangeValidator(RelationalOperator relationLower, double lower, RelationalOperator relationUpper, double upper) {
/* 17 */     this.lower = lower;
/* 18 */     this.upper = upper;
/* 19 */     this.relationLower = relationLower;
/* 20 */     this.relationUpper = relationUpper;
/*    */   }
/*    */   
/*    */   public static DoubleRangeValidator between01() {
/* 24 */     return VALIDATOR_BETWEEN_01;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static DoubleRangeValidator between(double lower, double upper) {
/* 29 */     return new DoubleRangeValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static DoubleRangeValidator fromExclToIncl(double lower, double upper) {
/* 34 */     return new DoubleRangeValidator(RelationalOperator.Greater, lower, RelationalOperator.LessEqual, upper);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static DoubleRangeValidator fromExclToExcl(double lower, double upper) {
/* 39 */     return new DoubleRangeValidator(RelationalOperator.Greater, lower, RelationalOperator.Less, upper);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(double value) {
/* 44 */     return (compare(value, this.relationLower, this.lower) && compare(value, this.relationUpper, this.upper));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(double value) {
/* 50 */     return errorMessage0(value, "Value");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(double value, String name) {
/* 56 */     return errorMessage0(value, "\"" + name + "\"");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private String errorMessage0(double value, String name) {
/* 61 */     return name + " should be " + name + " " + this.relationLower
/* 62 */       .asText() + " and " + this.lower + " " + this.relationUpper
/* 63 */       .asText() + " but is " + this.upper;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\DoubleRangeValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */