/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class IntSingleValidator
/*    */   extends IntValidator
/*    */ {
/*  9 */   private static final IntSingleValidator VALIDATOR_GREATER_EQUAL_0 = new IntSingleValidator(RelationalOperator.GreaterEqual, 0);
/* 10 */   private static final IntSingleValidator VALIDATOR_GREATER_0 = new IntSingleValidator(RelationalOperator.Greater, 0);
/*    */   
/*    */   private final RelationalOperator relation;
/*    */   private final int value;
/*    */   
/*    */   private IntSingleValidator(RelationalOperator relation, int value) {
/* 16 */     this.value = value;
/* 17 */     this.relation = relation;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(int value) {
/* 22 */     return compare(value, this.relation, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(int value) {
/* 28 */     return errorMessage0(value, "Value");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(int value, String name) {
/* 34 */     return errorMessage0(value, "\"" + name + "\"");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private String errorMessage0(int value, String name) {
/* 39 */     return name + " should be " + name + " " + this.relation.asText() + " but is " + this.value;
/*    */   }
/*    */   
/*    */   public static IntValidator greaterEqual0() {
/* 43 */     return VALIDATOR_GREATER_EQUAL_0;
/*    */   }
/*    */   
/*    */   public static IntValidator greater0() {
/* 47 */     return VALIDATOR_GREATER_0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\IntSingleValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */