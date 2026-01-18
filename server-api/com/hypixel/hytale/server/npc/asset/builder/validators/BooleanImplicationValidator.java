/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BooleanImplicationValidator
/*    */   extends Validator
/*    */ {
/*    */   private final String[] antecedentSet;
/*    */   private final boolean antecedentState;
/*    */   private final String[] consequentSet;
/*    */   private final boolean consequentState;
/*    */   private final boolean anyAntecedent;
/*    */   
/*    */   private BooleanImplicationValidator(String[] antecedentSet, boolean antecedentState, String[] consequentSet, boolean consequentState, boolean anyAntecedent) {
/* 21 */     this.antecedentSet = antecedentSet;
/* 22 */     this.antecedentState = antecedentState;
/* 23 */     this.consequentSet = consequentSet;
/* 24 */     this.consequentState = consequentState;
/* 25 */     this.anyAntecedent = anyAntecedent;
/*    */   }
/*    */   
/*    */   public boolean test(@Nonnull boolean[] antecedents, @Nonnull boolean[] consequents) {
/* 29 */     boolean antecedent = this.anyAntecedent ? anyMatch(antecedents, this.antecedentState) : allMatch(antecedents, this.antecedentState);
/* 30 */     return (!antecedent || allMatch(consequents, this.consequentState));
/*    */   }
/*    */   
/*    */   private boolean allMatch(@Nonnull boolean[] values, boolean expected) {
/* 34 */     for (boolean value : values) {
/* 35 */       if (value != expected) return false; 
/*    */     } 
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   private boolean anyMatch(@Nonnull boolean[] values, boolean expected) {
/* 41 */     for (boolean value : values) {
/* 42 */       if (value == expected) return true; 
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage() {
/* 49 */     return String.format("If %s%s%s %s, all members of %s must be %s", new Object[] { this.anyAntecedent ? "any of " : "all members of ", 
/* 50 */           Arrays.toString((Object[])this.antecedentSet), this.anyAntecedent ? " is set to" : " are set to", Boolean.valueOf(this.antecedentState), Arrays.toString((Object[])this.consequentSet), Boolean.valueOf(this.consequentState) });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BooleanImplicationValidator withAttributes(String[] antecedentSet, boolean antecedentState, String[] consequentSet, boolean consequentState, boolean anyAntecedent) {
/* 55 */     return new BooleanImplicationValidator(antecedentSet, antecedentState, consequentSet, consequentState, anyAntecedent);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\BooleanImplicationValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */