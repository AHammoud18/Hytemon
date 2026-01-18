/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoubleOrValidator
/*    */   extends DoubleValidator
/*    */ {
/* 12 */   private static final DoubleOrValidator GREATER_EQUAL_0_OR_MINUS_1 = new DoubleOrValidator(RelationalOperator.GreaterEqual, 0.0D, RelationalOperator.Equal, -1.0D);
/*    */   
/*    */   private final RelationalOperator relationOne;
/*    */   private final RelationalOperator relationTwo;
/*    */   private final double valueOne;
/*    */   private final double valueTwo;
/*    */   
/*    */   private DoubleOrValidator(RelationalOperator relationOne, double valueOne, RelationalOperator relationTwo, double valueTwo) {
/* 20 */     this.relationOne = relationOne;
/* 21 */     this.valueOne = valueOne;
/* 22 */     this.relationTwo = relationTwo;
/* 23 */     this.valueTwo = valueTwo;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(double value) {
/* 28 */     return (compare(value, this.relationOne, this.valueOne) || compare(value, this.relationTwo, this.valueTwo));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(double value) {
/* 34 */     return errorMessage0(value, "Value");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(double value, String name) {
/* 40 */     return errorMessage0(value, "\"" + name + "\"");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private String errorMessage0(double value, String name) {
/* 45 */     return name + " should be " + name + " " + this.relationOne.asText() + " or " + this.valueOne + " " + this.relationTwo.asText() + ", but is " + this.valueTwo;
/*    */   }
/*    */   
/*    */   public static DoubleOrValidator greaterEqual0OrMinus1() {
/* 49 */     return GREATER_EQUAL_0_OR_MINUS_1;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\DoubleOrValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */