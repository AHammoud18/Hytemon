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
/*    */ public class BuilderExpressionDynamicNumberArray
/*    */   extends BuilderExpressionDynamic
/*    */ {
/*    */   public BuilderExpressionDynamicNumberArray(String expression, ExecutionContext.Instruction[] instructionSequence) {
/* 15 */     super(expression, instructionSequence);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueType getType() {
/* 21 */     return ValueType.NUMBER_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] getNumberArray(@Nonnull ExecutionContext executionContext) {
/* 26 */     execute(executionContext);
/* 27 */     return executionContext.popNumberArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getIntegerArray(@Nonnull ExecutionContext executionContext) {
/* 32 */     return BuilderExpressionStaticNumberArray.convertDoubleToIntArray(getNumberArray(executionContext));
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScope(@Nonnull StdScope scope, String name, @Nonnull ExecutionContext executionContext) {
/* 37 */     scope.changeValue(name, getNumberArray(executionContext));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionDynamicNumberArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */