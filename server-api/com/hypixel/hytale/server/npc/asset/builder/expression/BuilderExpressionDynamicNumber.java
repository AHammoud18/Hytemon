/*    */ package com.hypixel.hytale.server.npc.asset.builder.expression;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderExpressionDynamicNumber
/*    */   extends BuilderExpressionDynamic
/*    */ {
/*    */   public BuilderExpressionDynamicNumber(String expression, ExecutionContext.Instruction[] instructionSequence) {
/* 15 */     super(expression, instructionSequence);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueType getType() {
/* 21 */     return ValueType.NUMBER;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getNumber(@Nonnull ExecutionContext executionContext) {
/* 26 */     execute(executionContext);
/* 27 */     return executionContext.popNumber();
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScope(@Nonnull StdScope scope, String name, @Nonnull ExecutionContext executionContext) {
/* 32 */     scope.changeValue(name, getNumber(executionContext));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionDynamicNumber.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */