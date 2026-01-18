/*    */ package com.hypixel.hytale.server.npc.util.expression.compile.ast;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.CompileContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Token;
/*    */ import java.text.ParseException;
/*    */ import java.util.List;
/*    */ import java.util.Stack;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASTOperatorFunctionCall
/*    */   extends ASTOperator
/*    */ {
/*    */   private final String functionName;
/*    */   
/*    */   public ASTOperatorFunctionCall(@Nonnull ValueType returnType, String functionName, int tokenPosition) {
/* 22 */     super(returnType, Token.FUNCTION_CALL, tokenPosition);
/* 23 */     this.functionName = functionName;
/* 24 */     this.codeGen = (scope -> ExecutionContext.genCALL(this.functionName, getArguments().size(), scope));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isConstant() {
/* 29 */     return false;
/*    */   }
/*    */   
/*    */   public static void fromParsedFunction(int argumentCount, @Nonnull CompileContext compileContext) throws ParseException {
/*    */     ASTOperandIdentifier identifier;
/* 34 */     Stack<AST> operandStack = compileContext.getOperandStack();
/* 35 */     int len = operandStack.size();
/* 36 */     AST functionNameAST = operandStack.get(len - argumentCount - 1);
/*    */     
/* 38 */     if (functionNameAST instanceof ASTOperandIdentifier) { identifier = (ASTOperandIdentifier)functionNameAST; }
/* 39 */     else { throw new ParseException("Expected identifier for function name but found type " + String.valueOf(functionNameAST.getValueType()), functionNameAST.getTokenPosition()); }
/*    */ 
/*    */ 
/*    */     
/* 43 */     StringBuilder name = (new StringBuilder(identifier.getIdentifier())).append('@');
/* 44 */     boolean isConstant = true;
/*    */     
/* 46 */     int firstArgument = len - argumentCount;
/* 47 */     for (int i = firstArgument; i < len; i++) {
/* 48 */       AST ast = operandStack.get(i);
/* 49 */       name.append(Scope.encodeType(ast.getValueType()));
/* 50 */       isConstant &= ast.isConstant();
/*    */     } 
/*    */     
/* 53 */     String functionName = name.toString();
/* 54 */     Scope scope = compileContext.getScope();
/* 55 */     ValueType resultType = scope.getType(functionName);
/*    */     
/* 57 */     if (resultType == null) {
/* 58 */       throw new IllegalStateException("Unable to find function (or argument types are not matching):" + functionName);
/*    */     }
/*    */ 
/*    */     
/* 62 */     isConstant &= scope.isConstant(functionName);
/*    */     
/* 64 */     if (isConstant) {
/* 65 */       List<ExecutionContext.Instruction> instructionList = compileContext.getInstructions();
/* 66 */       ExecutionContext executionContext = compileContext.getExecutionContext();
/*    */       
/* 68 */       instructionList.clear();
/* 69 */       for (int k = firstArgument; k < len; k++) {
/* 70 */         ((AST)operandStack.get(k)).genCode(instructionList, null);
/*    */       }
/* 72 */       instructionList.add(ExecutionContext.genCALL(functionName, argumentCount, null));
/* 73 */       ValueType ret = executionContext.execute(instructionList, scope);
/* 74 */       if (ret == ValueType.VOID) {
/* 75 */         throw new IllegalStateException("Failed to evaluate constant function AST");
/*    */       }
/*    */       
/* 78 */       operandStack.setSize(firstArgument - 1);
/* 79 */       operandStack.push(ASTOperand.createFromOperand(functionNameAST.getToken(), functionNameAST.getTokenPosition(), executionContext.top()));
/*    */       
/*    */       return;
/*    */     } 
/* 83 */     ASTOperatorFunctionCall function = new ASTOperatorFunctionCall(resultType, functionName, functionNameAST.getTokenPosition());
/*    */ 
/*    */     
/* 86 */     for (int j = firstArgument; j < len; j++) {
/* 87 */       function.addArgument(operandStack.get(j));
/*    */     }
/*    */ 
/*    */     
/* 91 */     operandStack.setSize(firstArgument - 1);
/* 92 */     operandStack.push(function);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\ASTOperatorFunctionCall.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */