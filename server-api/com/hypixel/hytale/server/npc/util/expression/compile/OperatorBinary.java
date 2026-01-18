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
/*    */ public class OperatorBinary
/*    */ {
/*    */   private Token token;
/*    */   private ValueType lhs;
/*    */   private ValueType rhs;
/*    */   private ValueType result;
/*    */   private Function<Scope, ExecutionContext.Instruction> codeGen;
/*    */   
/*    */   private OperatorBinary(Token token, ValueType lhs, ValueType rhs, ValueType result, Function<Scope, ExecutionContext.Instruction> codeGen) {
/* 24 */     this.token = token;
/* 25 */     this.lhs = lhs;
/* 26 */     this.rhs = rhs;
/* 27 */     this.result = result;
/* 28 */     this.codeGen = codeGen;
/*    */   }
/*    */   
/*    */   public ValueType getResultType() {
/* 32 */     return this.result;
/*    */   }
/*    */   
/*    */   public Function<Scope, ExecutionContext.Instruction> getCodeGen() {
/* 36 */     return this.codeGen;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   private static OperatorBinary of(Token token, ValueType lhs, ValueType rhs, ValueType result, Function<Scope, ExecutionContext.Instruction> codeGen) {
/* 41 */     return new OperatorBinary(token, lhs, rhs, result, codeGen);
/*    */   }
/*    */   
/*    */   @Nonnull
/* 45 */   private static OperatorBinary[] operators = new OperatorBinary[] { 
/* 46 */       of(Token.EXPONENTIATION, ValueType.NUMBER, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.EXPONENTIATION), 
/* 47 */       of(Token.REMAINDER, ValueType.NUMBER, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.REMAINDER), 
/* 48 */       of(Token.DIVIDE, ValueType.NUMBER, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.DIVIDE), 
/* 49 */       of(Token.MULTIPLY, ValueType.NUMBER, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.MULTIPLY), 
/* 50 */       of(Token.MINUS, ValueType.NUMBER, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.MINUS), 
/* 51 */       of(Token.PLUS, ValueType.NUMBER, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.PLUS), 
/* 52 */       of(Token.GREATER_EQUAL, ValueType.NUMBER, ValueType.NUMBER, ValueType.BOOLEAN, scope -> ExecutionContext.GREATER_EQUAL), 
/* 53 */       of(Token.GREATER, ValueType.NUMBER, ValueType.NUMBER, ValueType.BOOLEAN, scope -> ExecutionContext.GREATER), 
/* 54 */       of(Token.LESS_EQUAL, ValueType.NUMBER, ValueType.NUMBER, ValueType.BOOLEAN, scope -> ExecutionContext.LESS_EQUAL), 
/* 55 */       of(Token.LESS, ValueType.NUMBER, ValueType.NUMBER, ValueType.BOOLEAN, scope -> ExecutionContext.LESS), 
/* 56 */       of(Token.NOT_EQUAL, ValueType.NUMBER, ValueType.NUMBER, ValueType.BOOLEAN, scope -> ExecutionContext.NOT_EQUAL), 
/* 57 */       of(Token.EQUAL, ValueType.NUMBER, ValueType.NUMBER, ValueType.BOOLEAN, scope -> ExecutionContext.EQUAL), 
/* 58 */       of(Token.NOT_EQUAL, ValueType.BOOLEAN, ValueType.BOOLEAN, ValueType.BOOLEAN, scope -> ExecutionContext.NOT_EQUAL_BOOL), 
/* 59 */       of(Token.EQUAL, ValueType.BOOLEAN, ValueType.BOOLEAN, ValueType.BOOLEAN, scope -> ExecutionContext.EQUAL_BOOL), 
/* 60 */       of(Token.BITWISE_AND, ValueType.NUMBER, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.BITWISE_AND), 
/* 61 */       of(Token.BITWISE_XOR, ValueType.NUMBER, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.BITWISE_XOR), 
/* 62 */       of(Token.BITWISE_OR, ValueType.NUMBER, ValueType.NUMBER, ValueType.NUMBER, scope -> ExecutionContext.BITWISE_OR), 
/* 63 */       of(Token.LOGICAL_AND, ValueType.BOOLEAN, ValueType.BOOLEAN, ValueType.BOOLEAN, scope -> ExecutionContext.LOGICAL_AND), 
/* 64 */       of(Token.LOGICAL_OR, ValueType.BOOLEAN, ValueType.BOOLEAN, ValueType.BOOLEAN, scope -> ExecutionContext.LOGICAL_OR) };
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static OperatorBinary findOperator(Token token, ValueType lhs, ValueType rhs) {
/* 69 */     for (OperatorBinary op : operators) {
/* 70 */       if (op.token == token && op.lhs == lhs && op.rhs == rhs) {
/* 71 */         return op;
/*    */       }
/*    */     } 
/* 74 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\OperatorBinary.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */