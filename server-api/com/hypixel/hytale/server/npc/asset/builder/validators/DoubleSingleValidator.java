/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class DoubleSingleValidator
/*    */   extends DoubleValidator
/*    */ {
/*  9 */   private static final DoubleSingleValidator VALIDATOR_GREATER_0 = new DoubleSingleValidator(RelationalOperator.Greater, 0.0D);
/* 10 */   private static final DoubleSingleValidator VALIDATOR_GREATER_EQUAL_0 = new DoubleSingleValidator(RelationalOperator.GreaterEqual, 0.0D);
/*    */   private final RelationalOperator relation;
/*    */   private final double value;
/*    */   
/*    */   private DoubleSingleValidator(RelationalOperator relation, double value) {
/* 15 */     this.value = value;
/* 16 */     this.relation = relation;
/*    */   }
/*    */   
/*    */   public static DoubleSingleValidator greater0() {
/* 20 */     return VALIDATOR_GREATER_0;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static DoubleSingleValidator greater(double threshold) {
/* 25 */     return new DoubleSingleValidator(RelationalOperator.Greater, threshold);
/*    */   }
/*    */   
/*    */   public static DoubleSingleValidator greaterEqual0() {
/* 29 */     return VALIDATOR_GREATER_EQUAL_0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(double value) {
/* 34 */     return compare(value, this.relation, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(double value) {
/* 40 */     return errorMessage0(value, "Value");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(double value, String name) {
/* 46 */     return errorMessage0(value, "\"" + name + "\"");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private String errorMessage0(double value, String name) {
/* 51 */     return name + " should be " + name + " " + this.relation.asText() + " but is " + this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\DoubleSingleValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */