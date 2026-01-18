/*    */ package com.hypixel.hytale.server.npc.asset.builder.expression;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderExpressionStaticEmptyArray
/*    */   extends BuilderExpression
/*    */ {
/* 15 */   public static final BuilderExpressionStaticEmptyArray INSTANCE = new BuilderExpressionStaticEmptyArray();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueType getType() {
/* 20 */     return ValueType.EMPTY_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStatic() {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] getNumberArray(ExecutionContext executionContext) {
/* 30 */     return ArrayUtil.EMPTY_DOUBLE_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getIntegerArray(ExecutionContext executionContext) {
/* 35 */     return ArrayUtil.EMPTY_INT_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String[] getStringArray(ExecutionContext executionContext) {
/* 41 */     return ArrayUtil.EMPTY_STRING_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean[] getBooleanArray(ExecutionContext executionContext) {
/* 46 */     return ArrayUtil.EMPTY_BOOLEAN_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addToScope(String name, @Nonnull StdScope scope) {
/* 51 */     scope.addConstEmptyArray(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScope(@Nonnull StdScope scope, String name, ExecutionContext executionContext) {
/* 56 */     scope.changeValueToEmptyArray(name);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionStaticEmptyArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */