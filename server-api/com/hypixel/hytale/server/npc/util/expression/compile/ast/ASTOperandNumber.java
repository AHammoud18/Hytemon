/*    */ package com.hypixel.hytale.server.npc.util.expression.compile.ast;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Token;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASTOperandNumber
/*    */   extends ASTOperand
/*    */ {
/*    */   private final double constantNumber;
/*    */   
/*    */   public ASTOperandNumber(@Nonnull Token token, int tokenPosition, double constantNumber) {
/* 18 */     super(ValueType.NUMBER, token, tokenPosition);
/* 19 */     this.constantNumber = constantNumber;
/* 20 */     this.codeGen = (scope -> ExecutionContext.genPUSH(this.constantNumber));
/*    */   }
/*    */   
/*    */   public ASTOperandNumber(@Nonnull Token token, int tokenPosition, @Nonnull Scope scope, String identifier) {
/* 24 */     this(token, tokenPosition, scope.getNumber(identifier));
/* 25 */     if (!scope.isConstant(identifier)) throw new IllegalArgumentException("Value must be constant: " + identifier);
/*    */   
/*    */   }
/*    */   
/*    */   public double getNumber() {
/* 30 */     return this.constantNumber;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isConstant() {
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ExecutionContext.Operand asOperand() {
/* 41 */     ExecutionContext.Operand op = new ExecutionContext.Operand();
/* 42 */     op.set(this.constantNumber);
/* 43 */     return op;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperandNumber.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */