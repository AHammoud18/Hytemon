/*    */ package com.hypixel.hytale.server.npc.util.expression;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.CompileContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Lexer;
/*    */ import com.hypixel.hytale.server.npc.util.expression.compile.Token;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Expression
/*    */ {
/*    */   static {
/* 25 */     lexer = new Lexer((Supplier)Token.END, (Supplier)Token.IDENTIFIER, (Supplier)Token.STRING, (Supplier)Token.NUMBER, Arrays.<Token>stream(Token.values()).filter(token -> (token.get() != null)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   private final CompileContext compileContext = new CompileContext(); @Nonnull
/* 34 */   private final ExecutionContext executionContext = this.compileContext.getExecutionContext();
/*    */   private static final Lexer<Token> lexer;
/*    */   
/*    */   public ValueType compile(@Nonnull String expression, Scope scope, @Nonnull List<ExecutionContext.Instruction> instructions, boolean fullResolve) {
/* 38 */     this.compileContext.compile(expression, scope, fullResolve);
/* 39 */     instructions.clear();
/* 40 */     instructions.addAll(this.compileContext.getInstructions());
/* 41 */     return this.compileContext.getResultType();
/*    */   }
/*    */   
/*    */   public ValueType compile(@Nonnull String expression, Scope compileScope, @Nonnull List<ExecutionContext.Instruction> instructions) {
/* 45 */     return compile(expression, compileScope, instructions, false);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ExecutionContext execute(@Nonnull List<ExecutionContext.Instruction> instructions, Scope scope) {
/* 50 */     this.executionContext.execute(instructions, scope);
/* 51 */     return this.executionContext;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ExecutionContext execute(@Nonnull ExecutionContext.Instruction[] instructions, Scope scope) {
/* 56 */     this.executionContext.execute(instructions, scope);
/* 57 */     return this.executionContext;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ExecutionContext evaluate(@Nonnull String expression, Scope scope) {
/* 62 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 63 */     compile(expression, scope, (List<ExecutionContext.Instruction>)objectArrayList, true);
/* 64 */     return execute((List<ExecutionContext.Instruction>)objectArrayList, scope);
/*    */   }
/*    */   
/*    */   public static ValueType compileStatic(@Nonnull String expression, Scope scope, @Nonnull List<ExecutionContext.Instruction> instructions) {
/* 68 */     CompileContext compileContext = new CompileContext();
/*    */     
/* 70 */     compileContext.compile(expression, scope, false);
/* 71 */     instructions.clear();
/* 72 */     instructions.addAll(compileContext.getInstructions());
/* 73 */     return compileContext.getResultType();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Lexer<Token> getLexerInstance() {
/* 78 */     return lexer;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\Expression.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */