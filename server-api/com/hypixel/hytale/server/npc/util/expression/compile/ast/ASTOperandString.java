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
/*    */ public class ASTOperandString
/*    */   extends ASTOperand
/*    */ {
/*    */   protected final String constantString;
/*    */   
/*    */   public ASTOperandString(@Nonnull Token token, int tokenPosition, String constantString) {
/* 18 */     super(ValueType.STRING, token, tokenPosition);
/* 19 */     this.constantString = constantString;
/* 20 */     this.codeGen = (scope -> ExecutionContext.genPUSH(this.constantString));
/*    */   }
/*    */   
/*    */   public ASTOperandString(@Nonnull Token token, int tokenPosition, @Nonnull Scope scope, String identifier) {
/* 24 */     this(token, tokenPosition, scope.getString(identifier));
/* 25 */     if (!scope.isConstant(identifier)) throw new IllegalArgumentException("Value must be constant: " + identifier);
/*    */   
/*    */   }
/*    */   
/*    */   public String getString() {
/* 30 */     return this.constantString;
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
/* 42 */     op.set(this.constantString);
/* 43 */     return op;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperandString.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */