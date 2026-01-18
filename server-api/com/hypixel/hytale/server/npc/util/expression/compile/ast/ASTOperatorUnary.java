/*    */ package com.hypixel.hytale.server.npc.util.expression.compile.ast;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.CompileContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.OperatorUnary;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Parser;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Token;
/*    */ import java.text.ParseException;
/*    */ import java.util.List;
/*    */ import java.util.NoSuchElementException;
/*    */ import java.util.Stack;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASTOperatorUnary
/*    */   extends ASTOperator
/*    */ {
/*    */   public ASTOperatorUnary(@Nonnull OperatorUnary operatorUnary, @Nonnull Token token, int tokenPosition, @Nonnull AST argument) {
/* 22 */     super(operatorUnary.getResultType(), token, tokenPosition);
/* 23 */     addArgument(argument);
/* 24 */     this.codeGen = operatorUnary.getCodeGen();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isConstant() {
/* 29 */     return false;
/*    */   }
/*    */   
/*    */   public static void fromUnaryOperator(@Nonnull Parser.ParsedToken operand, @Nonnull CompileContext compileContext) throws ParseException {
/* 33 */     int tokenPosition = operand.tokenPosition;
/* 34 */     Token token = operand.token;
/*    */     
/* 36 */     Stack<AST> operandStack = compileContext.getOperandStack();
/*    */     try {
/* 38 */       AST node = operandStack.pop();
/* 39 */       OperatorUnary operatorUnary = OperatorUnary.findOperator(token, node.returnType());
/*    */       
/* 41 */       if (operatorUnary == null) {
/* 42 */         throw new ParseException("Type mismatch for operator " + String.valueOf(token), tokenPosition);
/*    */       }
/*    */       
/* 45 */       if (node.isConstant() && operatorUnary.hasCodeGen()) {
/* 46 */         ExecutionContext executionContext = compileContext.getExecutionContext();
/* 47 */         List<ExecutionContext.Instruction> instructionList = compileContext.getInstructions();
/*    */ 
/*    */         
/* 50 */         instructionList.clear();
/* 51 */         node.genCode(instructionList, null);
/* 52 */         instructionList.add((ExecutionContext.Instruction)operatorUnary.getCodeGen().apply(null));
/* 53 */         ValueType ret = executionContext.execute(instructionList);
/* 54 */         if (ret == ValueType.VOID) {
/* 55 */           throw new IllegalStateException("Failed to evaluate constant unary AST");
/*    */         }
/* 57 */         operandStack.push(ASTOperand.createFromOperand(token, tokenPosition, executionContext.top()));
/* 58 */       } else if (operatorUnary.hasCodeGen()) {
/*    */ 
/*    */         
/* 61 */         operandStack.push(new ASTOperatorUnary(operatorUnary, token, tokenPosition, node));
/*    */       } else {
/*    */         
/* 64 */         operandStack.push(node);
/*    */       } 
/* 66 */     } catch (NoSuchElementException e) {
/* 67 */       throw new ParseException("Not enough operands for operator '" + operand.tokenString, tokenPosition);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperatorUnary.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */