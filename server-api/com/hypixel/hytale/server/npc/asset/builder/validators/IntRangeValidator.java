/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class IntRangeValidator
/*    */   extends IntValidator
/*    */ {
/*    */   private final RelationalOperator relationLower;
/*    */   private final int lower;
/*    */   private final RelationalOperator relationUpper;
/*    */   private final int upper;
/*    */   
/*    */   public IntRangeValidator(RelationalOperator relationLower, int lower, RelationalOperator relationUpper, int upper) {
/* 15 */     this.lower = lower;
/* 16 */     this.upper = upper;
/* 17 */     this.relationLower = relationLower;
/* 18 */     this.relationUpper = relationUpper;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(int value) {
/* 23 */     return (compare(value, this.relationLower, this.lower) && compare(value, this.relationUpper, this.upper));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(int value) {
/* 29 */     return errorMessage0(value, "Value");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(int value, String name) {
/* 35 */     return errorMessage0(value, "\"" + name + "\"");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private String errorMessage0(int value, String name) {
/* 40 */     return name + " should be " + name + " " + this.relationLower
/* 41 */       .asText() + " and " + this.lower + " " + this.relationUpper
/* 42 */       .asText() + " but is " + this.upper;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static IntRangeValidator fromInclToExcl(int lower, int upper) {
/* 47 */     return new IntRangeValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.Less, upper);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static IntRangeValidator fromExclToIncl(int lower, int upper) {
/* 52 */     return new IntRangeValidator(RelationalOperator.Greater, lower, RelationalOperator.LessEqual, upper);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static IntRangeValidator between(int lower, int upper) {
/* 57 */     return new IntRangeValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\IntRangeValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */