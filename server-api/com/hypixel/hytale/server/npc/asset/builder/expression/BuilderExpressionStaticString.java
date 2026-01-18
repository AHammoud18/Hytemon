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
/*    */ public class BuilderExpressionStaticString
/*    */   extends BuilderExpression
/*    */ {
/*    */   private final String string;
/*    */   
/*    */   public BuilderExpressionStaticString(String string) {
/* 17 */     this.string = string;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueType getType() {
/* 23 */     return ValueType.STRING;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStatic() {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getString(ExecutionContext executionContext) {
/* 33 */     return this.string;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addToScope(String name, @Nonnull StdScope scope) {
/* 38 */     scope.addVar(name, this.string);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScope(@Nonnull StdScope scope, String name, ExecutionContext executionContext) {
/* 43 */     scope.changeValue(name, this.string);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionStaticString.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */