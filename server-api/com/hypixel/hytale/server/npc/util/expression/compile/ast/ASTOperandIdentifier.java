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
/*    */ public class ASTOperandIdentifier
/*    */   extends ASTOperand
/*    */ {
/*    */   private final String identifier;
/*    */   
/*    */   public ASTOperandIdentifier(@Nonnull ValueType returnType, @Nonnull Token token, int tokenPosition, String identifier) {
/* 17 */     super(returnType, token, tokenPosition);
/* 18 */     this.identifier = identifier;
/* 19 */     this.codeGen = (scope -> ExecutionContext.genREAD(this.identifier, getValueType(), scope));
/*    */   }
/*    */   
/*    */   public String getIdentifier() {
/* 23 */     return this.identifier;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isConstant() {
/* 28 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperandIdentifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */