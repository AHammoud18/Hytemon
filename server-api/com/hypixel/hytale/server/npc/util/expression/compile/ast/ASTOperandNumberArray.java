/*    */ package com.hypixel.hytale.server.npc.util.expression.compile.ast;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Token;
/*    */ import java.util.Stack;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASTOperandNumberArray
/*    */   extends ASTOperand
/*    */ {
/*    */   private final double[] constantNumberArray;
/*    */   
/*    */   public ASTOperandNumberArray(@Nonnull Token token, int tokenPosition, double[] constantNumberArray) {
/* 19 */     super(ValueType.NUMBER_ARRAY, token, tokenPosition);
/* 20 */     this.constantNumberArray = constantNumberArray;
/* 21 */     this.codeGen = (scope -> ExecutionContext.genPUSH(this.constantNumberArray));
/*    */   }
/*    */   
/*    */   public ASTOperandNumberArray(@Nonnull Token token, int tokenPosition, @Nonnull Scope scope, String identifier) {
/* 25 */     this(token, tokenPosition, scope.getNumberArray(identifier));
/* 26 */     if (!scope.isConstant(identifier)) throw new IllegalArgumentException("Value must be constant: " + identifier); 
/*    */   }
/*    */   
/*    */   public ASTOperandNumberArray(@Nonnull Token token, int tokenPosition, @Nonnull Stack<AST> operandStack, int firstArgument, int argumentCount) {
/* 30 */     this(token, tokenPosition, new double[argumentCount]);
/* 31 */     for (int i = 0; i < argumentCount; i++) {
/* 32 */       this.constantNumberArray[i] = ((AST)operandStack.get(firstArgument + i)).getNumber();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isConstant() {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ExecutionContext.Operand asOperand() {
/* 44 */     ExecutionContext.Operand op = new ExecutionContext.Operand();
/* 45 */     op.set(this.constantNumberArray);
/* 46 */     return op;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperandNumberArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */