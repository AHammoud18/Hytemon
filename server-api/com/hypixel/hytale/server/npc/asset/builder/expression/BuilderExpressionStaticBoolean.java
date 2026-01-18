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
/*    */ public class BuilderExpressionStaticBoolean
/*    */   extends BuilderExpression
/*    */ {
/*    */   private final boolean bool;
/*    */   
/*    */   public BuilderExpressionStaticBoolean(boolean bool) {
/* 17 */     this.bool = bool;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueType getType() {
/* 23 */     return ValueType.BOOLEAN;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStatic() {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getBoolean(ExecutionContext executionContext) {
/* 33 */     return this.bool;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addToScope(String name, @Nonnull StdScope scope) {
/* 38 */     scope.addVar(name, this.bool);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScope(@Nonnull StdScope scope, String name, ExecutionContext executionContext) {
/* 43 */     scope.changeValue(name, this.bool);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionStaticBoolean.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */