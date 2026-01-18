/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntOrValidator
/*    */   extends IntValidator
/*    */ {
/*    */   private final RelationalOperator relationOne;
/*    */   private final RelationalOperator relationTwo;
/*    */   private final int valueOne;
/*    */   private final int valueTwo;
/*    */   
/*    */   private IntOrValidator(RelationalOperator relationOne, int valueOne, RelationalOperator relationTwo, int valueTwo) {
/* 18 */     this.relationOne = relationOne;
/* 19 */     this.valueOne = valueOne;
/* 20 */     this.relationTwo = relationTwo;
/* 21 */     this.valueTwo = valueTwo;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(int value) {
/* 26 */     return (compare(value, this.relationOne, this.valueOne) || compare(value, this.relationTwo, this.valueTwo));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(int value) {
/* 32 */     return errorMessage0(value, "Value");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(int value, String name) {
/* 38 */     return errorMessage0(value, "\"" + name + "\"");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private String errorMessage0(int value, String name) {
/* 43 */     return name + " should be " + name + " " + this.relationOne.asText() + " or " + this.valueOne + " " + this.relationTwo.asText() + ", but is " + this.valueTwo;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static IntOrValidator greater0OrMinus1() {
/* 48 */     return new IntOrValidator(RelationalOperator.Greater, 0, RelationalOperator.Equal, -1);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\IntOrValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */