/*    */ package com.hypixel.hytale.server.npc.util.expression.compile.ast;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.CompileContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.OperatorBinary;
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
/*    */ public class ASTOperatorBinary
/*    */   extends ASTOperator
/*    */ {
/*    */   public ASTOperatorBinary(@Nonnull OperatorBinary operatorBinary, @Nonnull Token token, int tokenPosition, @Nonnull AST lhs, @Nonnull AST rhs) {
/* 22 */     super(operatorBinary.getResultType(), token, tokenPosition);
/* 23 */     addArgument(lhs);
/* 24 */     addArgument(rhs);
/* 25 */     this.codeGen = operatorBinary.getCodeGen();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isConstant() {
/* 30 */     return false;
/*    */   }
/*    */   
/*    */   public static void fromBinaryOperator(@Nonnull Parser.ParsedToken operator, @Nonnull CompileContext compileContext) throws ParseException {
/* 34 */     Stack<AST> operandStack = compileContext.getOperandStack();
/*    */     try {
/* 36 */       AST rhs = operandStack.pop();
/* 37 */       AST lhs = operandStack.pop();
/* 38 */       OperatorBinary operatorBinary = OperatorBinary.findOperator(operator.token, lhs.returnType(), rhs.returnType());
/*    */       
/* 40 */       if (operatorBinary == null) {
/* 41 */         throw new ParseException("Type mismatch for operator " + String.valueOf(operator.token), operator.tokenPosition);
/*    */       }
/*    */ 
/*    */       
/* 45 */       if (lhs.isConstant() && rhs.isConstant()) {
/* 46 */         ExecutionContext executionContext = compileContext.getExecutionContext();
/* 47 */         List<ExecutionContext.Instruction> instructionList = compileContext.getInstructions();
/* 48 */         instructionList.clear();
/* 49 */         lhs.genCode(instructionList, null);
/* 50 */         rhs.genCode(instructionList, null);
/* 51 */         instructionList.add((ExecutionContext.Instruction)operatorBinary.getCodeGen().apply(null));
/* 52 */         ValueType ret = executionContext.execute(instructionList);
/* 53 */         if (ret == ValueType.VOID) {
/* 54 */           throw new IllegalStateException("Failed to evaluate constant binary AST");
/*    */         }
/* 56 */         operandStack.push(ASTOperand.createFromOperand(operator.token, operator.tokenPosition, executionContext.top()));
/*    */       } else {
/*    */         
/* 59 */         operandStack.push(new ASTOperatorBinary(operatorBinary, operator.token, operator.tokenPosition, lhs, rhs));
/*    */       } 
/* 61 */     } catch (NoSuchElementException e) {
/* 62 */       throw new ParseException("Not enough operands for operator '" + operator.tokenString, operator.tokenPosition);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperatorBinary.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */