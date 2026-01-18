/*     */ package com.hypixel.hytale.server.npc.util.expression.compile;
/*     */ 
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Expression;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*     */ import com.hypixel.hytale.server.npc.util.expression.compile.ast.AST;
/*     */ import com.hypixel.hytale.server.npc.util.expression.compile.ast.ASTOperand;
/*     */ import com.hypixel.hytale.server.npc.util.expression.compile.ast.ASTOperator;
/*     */ import com.hypixel.hytale.server.npc.util.expression.compile.ast.ASTOperatorFunctionCall;
/*     */ import com.hypixel.hytale.server.npc.util.expression.compile.ast.ASTOperatorTuple;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.text.ParseException;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CompileContext
/*     */   implements Parser.ParsedTokenConsumer
/*     */ {
/*  22 */   private final Parser parser = new Parser(Expression.getLexerInstance());
/*  23 */   private final Stack<AST> operandStack = new Stack<>();
/*     */   
/*     */   @Nonnull
/*     */   private final ExecutionContext executionContext;
/*     */   
/*     */   private Scope scope;
/*     */   private List<ExecutionContext.Instruction> instructions;
/*  30 */   private ValueType resultType = ValueType.VOID;
/*     */   
/*     */   public CompileContext() {
/*  33 */     this.executionContext = new ExecutionContext();
/*     */   }
/*     */   
/*     */   public CompileContext(Scope scope) {
/*  37 */     this();
/*  38 */     this.scope = scope;
/*     */   }
/*     */   
/*     */   public Scope getScope() {
/*  42 */     return this.scope;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Stack<AST> getOperandStack() {
/*  47 */     return this.operandStack;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ExecutionContext getExecutionContext() {
/*  52 */     return this.executionContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueType compile(@Nonnull String expression, Scope compileScope, boolean fullResolve) {
/*  65 */     return compile0(expression, compileScope, fullResolve, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueType compile(@Nonnull String expression, Scope compileScope, boolean fullResolve, List<ExecutionContext.Instruction> instructions) {
/*  79 */     ValueType valueType = compile0(expression, compileScope, fullResolve, instructions);
/*     */     
/*  81 */     setInstructions(null);
/*  82 */     return valueType;
/*     */   }
/*     */   
/*     */   protected ValueType compile0(@Nonnull String expression, Scope compileScope, boolean fullResolve, List<ExecutionContext.Instruction> instructions) {
/*  86 */     Scope saveScope = this.scope;
/*  87 */     Scope oldScope = this.executionContext.setScope(compileScope);
/*  88 */     this.scope = compileScope;
/*  89 */     setInstructions(instructions);
/*  90 */     compile(expression, fullResolve);
/*  91 */     this.executionContext.setScope(oldScope);
/*  92 */     this.scope = saveScope;
/*  93 */     return this.resultType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueType compile(@Nonnull String expression, boolean fullResolve) {
/*     */     try {
/* 105 */       this.operandStack.clear();
/* 106 */       this.resultType = ValueType.VOID;
/* 107 */       if (this.instructions == null) {
/* 108 */         this.instructions = (List<ExecutionContext.Instruction>)new ObjectArrayList();
/*     */       }
/* 110 */       this.instructions.clear();
/* 111 */       this.parser.parse(expression, this);
/* 112 */       this.resultType = ((AST)this.operandStack.getFirst()).genCode(this.instructions, fullResolve ? this.scope : null);
/* 113 */       return this.resultType;
/* 114 */     } catch (Throwable t) {
/* 115 */       throw new IllegalStateException("Error compiling expression '" + expression + "': " + t.getMessage(), t);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<ExecutionContext.Instruction> getInstructions() {
/* 120 */     return this.instructions;
/*     */   }
/*     */   
/*     */   public void setInstructions(List<ExecutionContext.Instruction> instructionList) {
/* 124 */     this.instructions = instructionList;
/*     */   }
/*     */   
/*     */   public ValueType getResultType() {
/* 128 */     return this.resultType;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ExecutionContext.Operand getAsOperand() {
/* 133 */     if (this.operandStack.size() != 1) throw new IllegalArgumentException("There must be 1 element on stack to get as operand"); 
/* 134 */     AST ast = this.operandStack.getFirst();
/*     */     
/* 136 */     return ast.isConstant() ? ast.asOperand() : null;
/*     */   }
/*     */   
/*     */   public void checkResultType(ValueType type) {
/* 140 */     if (type == ValueType.VOID) throw new IllegalArgumentException("Result type can't be void"); 
/* 141 */     if (this.resultType == ValueType.VOID) throw new IllegalArgumentException("Compiled expression result type can't be void"); 
/* 142 */     if (type != this.resultType) {
/* 143 */       throw new IllegalStateException("Result type expected is " + String.valueOf(type) + " but got " + String.valueOf(this.resultType));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushOperand(@Nonnull Parser.ParsedToken parsedToken) {
/* 153 */     this.operandStack.push(ASTOperand.createFromParsedToken(parsedToken, this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void processOperator(@Nonnull Parser.ParsedToken operator) throws ParseException {
/* 158 */     ASTOperator.fromParsedOperator(operator, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processFunction(int argumentCount) throws ParseException {
/* 163 */     ASTOperatorFunctionCall.fromParsedFunction(argumentCount, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processTuple(@Nonnull Parser.ParsedToken openingToken, int argumentCount) {
/* 168 */     ASTOperatorTuple.fromParsedTuple(openingToken, argumentCount, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/* 173 */     if (this.operandStack.size() != 1)
/* 174 */       throw new IllegalStateException("Need exactly one returned value in expression"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\CompileContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */