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
/*    */ public class BuilderExpressionStaticNumber
/*    */   extends BuilderExpression
/*    */ {
/*    */   private final double number;
/*    */   
/*    */   public BuilderExpressionStaticNumber(double number) {
/* 17 */     this.number = number;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueType getType() {
/* 23 */     return ValueType.NUMBER;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStatic() {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getNumber(ExecutionContext executionContext) {
/* 33 */     return this.number;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addToScope(String name, @Nonnull StdScope scope) {
/* 38 */     scope.addVar(name, this.number);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScope(@Nonnull StdScope scope, String name, ExecutionContext executionContext) {
/* 43 */     scope.changeValue(name, this.number);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionStaticNumber.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */