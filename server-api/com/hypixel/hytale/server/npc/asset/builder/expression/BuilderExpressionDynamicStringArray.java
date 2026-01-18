/*    */ package com.hypixel.hytale.server.npc.asset.builder.expression;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderExpressionDynamicStringArray
/*    */   extends BuilderExpressionDynamic
/*    */ {
/*    */   public BuilderExpressionDynamicStringArray(String expression, ExecutionContext.Instruction[] instructionSequence) {
/* 16 */     super(expression, instructionSequence);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueType getType() {
/* 22 */     return ValueType.STRING_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String[] getStringArray(@Nonnull ExecutionContext executionContext) {
/* 28 */     execute(executionContext);
/* 29 */     return executionContext.popStringArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScope(@Nonnull StdScope scope, String name, @Nonnull ExecutionContext executionContext) {
/* 34 */     scope.changeValue(name, getStringArray(executionContext));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionDynamicStringArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */