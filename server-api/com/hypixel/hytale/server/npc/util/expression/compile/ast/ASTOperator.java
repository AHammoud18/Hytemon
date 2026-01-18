/*    */ package com.hypixel.hytale.server.npc.util.expression.compile.ast;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.CompileContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Parser;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Token;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.text.ParseException;
/*    */ import java.util.List;
/*    */ import java.util.NoSuchElementException;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ASTOperator
/*    */   extends AST
/*    */ {
/* 23 */   private final List<AST> arguments = (List<AST>)new ObjectArrayList();
/*    */   
/*    */   public ASTOperator(@Nonnull ValueType returnType, @Nonnull Token token, int tokenPosition) {
/* 26 */     super(returnType, token, tokenPosition);
/*    */   }
/*    */   
/*    */   public void addArgument(@Nonnull AST argument) {
/* 30 */     this.arguments.add(argument);
/* 31 */     argument.setParent(this);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<AST> getArguments() {
/* 36 */     return this.arguments;
/*    */   }
/*    */ 
/*    */   
/*    */   public ValueType genCode(@Nonnull List<ExecutionContext.Instruction> list, Scope scope) {
/* 41 */     this.arguments.forEach(ast -> ast.genCode(list, scope));
/* 42 */     return super.genCode(list, scope);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void fromParsedOperator(@Nonnull Parser.ParsedToken operand, @Nonnull CompileContext compileContext) throws ParseException {
/*    */     try {
/* 48 */       if (operand.token.isUnary()) {
/* 49 */         ASTOperatorUnary.fromUnaryOperator(operand, compileContext);
/*    */       } else {
/* 51 */         ASTOperatorBinary.fromBinaryOperator(operand, compileContext);
/*    */       } 
/* 53 */     } catch (NoSuchElementException e) {
/* 54 */       throw new ParseException("Not enough operands for operator '" + operand.tokenString, operand.tokenPosition);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */