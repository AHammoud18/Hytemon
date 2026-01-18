/*    */ package com.hypixel.hytale.server.npc.util.expression.compile;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OperatorUnary
/*    */ {
/*    */   private Token token;
/*    */   private ValueType argument;
/*    */   private ValueType result;
/*    */   private Function<Scope, ExecutionContext.Instruction> codeGen;
/*    */   
/*    */   private OperatorUnary(Token token, ValueType argument, ValueType result, Function<Scope, ExecutionContext.Instruction> codeGen) {
/* 23 */     this.token = token;
/* 24 */     this.argument = argument;
/* 25 */     this.result = result;
/* 26 */     this.codeGen = codeGen;
/*    */   }
/*    */   
/*    */   public boolean hasCodeGen() {
/* 30 */     return (this.codeGen != null);
/*    */   }
/*    */   
/*    */   public ValueType getResultType() {
/* 34 */     return this.result;
/*    */   }
/*    */   
/*    */   public Function<Scope, ExecutionContext.Instruction> getCodeGen() {
/* 38 */     return this.codeGen;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private static OperatorUnary of(Token token, ValueType argument, ValueType result, Function<Scope, ExecutionContext.Instruction> codeGen) {
/* 43 */     return new OperatorUnary(token, argument, result, codeGen);
/*    */   }
/*    */   
/*    */   @Nonnull
/* 47 */   private static OperatorUnary[] operators = new OperatorUnary[] {
/* 48 */       of(Token.UNARY_PLUS, ValueType.NUMBER, ValueType.NUMBER, null), 
/* 49 */       of(Token.UNARY_MINUS, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.UNARY_MINUS), 
/* 50 */       of(Token.LOGICAL_NOT, ValueType.BOOLEAN, ValueType.BOOLEAN, scope -> ExecutionContext.LOGICAL_NOT), 
/* 51 */       of(Token.BITWISE_NOT, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.BITWISE_NOT)
/*    */     };
/*    */   
/*    */   @Nullable
/*    */   public static OperatorUnary findOperator(Token token, ValueType type) {
/* 56 */     for (OperatorUnary op : operators) {
/* 57 */       if (op.token == token && op.argument == type) {
/* 58 */         return op;
/*    */       }
/*    */     } 
/* 61 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\OperatorUnary.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */