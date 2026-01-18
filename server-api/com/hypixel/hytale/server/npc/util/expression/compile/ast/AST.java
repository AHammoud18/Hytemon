/*     */ package com.hypixel.hytale.server.npc.util.expression.compile.ast;
/*     */ 
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*     */ import com.hypixel.hytale.server.npc.util.expression.compile.Token;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ public abstract class AST
/*     */ {
/*     */   @Nonnull
/*     */   private final ValueType valueType;
/*     */   @Nonnull
/*     */   private final Token token;
/*     */   private final int tokenPosition;
/*     */   private AST parent;
/*     */   @Nullable
/*     */   protected Function<Scope, ExecutionContext.Instruction> codeGen;
/*     */   
/*     */   public AST(@Nonnull ValueType valueType, @Nonnull Token token, int tokenPosition) {
/*  37 */     Objects.requireNonNull(valueType, "ValueType can't be null");
/*  38 */     Objects.requireNonNull(token, "Token can't be null");
/*  39 */     this.valueType = valueType;
/*  40 */     this.token = token;
/*  41 */     this.tokenPosition = tokenPosition;
/*  42 */     this.codeGen = null;
/*     */   }
/*     */   
/*     */   public AST getParent() {
/*  46 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void setParent(AST parent) {
/*  50 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ValueType getValueType() {
/*  55 */     return this.valueType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Token getToken() {
/*  60 */     return this.token;
/*     */   }
/*     */   
/*     */   public int getTokenPosition() {
/*  64 */     return this.tokenPosition;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Function<Scope, ExecutionContext.Instruction> getCodeGen() {
/*  69 */     return this.codeGen;
/*     */   }
/*     */   
/*     */   public abstract boolean isConstant();
/*     */   
/*     */   public ExecutionContext.Operand asOperand() {
/*  75 */     throw new IllegalStateException("AST: Cannot be returned as operand");
/*     */   }
/*     */   
/*     */   public String getString() {
/*  79 */     throw new IllegalStateException("AST: Cannot return string");
/*     */   }
/*     */   
/*     */   public boolean getBoolean() {
/*  83 */     throw new IllegalStateException("AST: Cannot return boolean");
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumber() {
/*  88 */     throw new IllegalStateException("AST: Cannot return number");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ValueType returnType() {
/*  93 */     return getValueType();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueType genCode(@Nonnull List<ExecutionContext.Instruction> list, Scope scope) {
/* 112 */     Objects.requireNonNull(getCodeGen(), "Missing CodeGen in AST");
/* 113 */     list.add(getCodeGen().apply(scope));
/* 114 */     return getValueType();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\compile\ast\AST.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */