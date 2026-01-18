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
/*    */ public class ASTOperandEmptyArray
/*    */   extends ASTOperand
/*    */ {
/*    */   public ASTOperandEmptyArray(@Nonnull Token token, int tokenPosition) {
/* 15 */     super(ValueType.EMPTY_ARRAY, token, tokenPosition);
/* 16 */     this.codeGen = (scope -> ExecutionContext.genPUSHEmptyArray());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isConstant() {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ExecutionContext.Operand asOperand() {
/* 27 */     ExecutionContext.Operand op = new ExecutionContext.Operand();
/* 28 */     op.setEmptyArray();
/* 29 */     return op;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperandEmptyArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */