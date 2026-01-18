/*     */ package com.hypixel.hytale.server.npc.util.expression;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.DoubleSupplier;
/*     */ import java.util.function.Supplier;
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
/*     */ public class ExecutionContext
/*     */ {
/*     */   public static final int STACK_GROW_INCREMENT = 8;
/*     */   protected Scope scope;
/*     */   protected Operand[] operandStack;
/*     */   protected int stackTop;
/*     */   protected ValueType lastPushedType;
/*     */   protected String combatConfig;
/*     */   protected Map<String, String> interactionVars;
/*     */   
/*     */   public static class Operand
/*     */   {
/*     */     public ValueType type;
/*     */     public String string;
/*     */     public double number;
/*     */     public boolean bool;
/*     */     @Nullable
/*     */     public double[] numberArray;
/*     */     @Nullable
/*     */     public String[] stringArray;
/*     */     @Nullable
/*     */     public boolean[] boolArray;
/*     */     
/*     */     public ValueType set(String value) {
/*  46 */       reInit(ValueType.STRING);
/*  47 */       this.string = value;
/*  48 */       return this.type;
/*     */     }
/*     */     
/*     */     public ValueType set(double value) {
/*  52 */       reInit(ValueType.NUMBER);
/*  53 */       this.number = value;
/*  54 */       return this.type;
/*     */     }
/*     */     
/*     */     public ValueType set(boolean value) {
/*  58 */       reInit(ValueType.BOOLEAN);
/*  59 */       this.bool = value;
/*  60 */       return this.type;
/*     */     }
/*     */     
/*     */     public ValueType set(String[] value) {
/*  64 */       reInit(ValueType.STRING_ARRAY);
/*  65 */       this.stringArray = value;
/*  66 */       return this.type;
/*     */     }
/*     */     
/*     */     public ValueType set(double[] value) {
/*  70 */       reInit(ValueType.NUMBER_ARRAY);
/*  71 */       this.numberArray = value;
/*  72 */       return this.type;
/*     */     }
/*     */     
/*     */     public ValueType set(boolean[] value) {
/*  76 */       reInit(ValueType.BOOLEAN_ARRAY);
/*  77 */       this.boolArray = value;
/*  78 */       return this.type;
/*     */     }
/*     */     
/*     */     public ValueType setEmptyArray() {
/*  82 */       reInit(ValueType.EMPTY_ARRAY);
/*  83 */       return this.type;
/*     */     }
/*     */     
/*     */     private void reInit(ValueType type) {
/*  87 */       this.type = type;
/*  88 */       this.numberArray = null;
/*  89 */       this.stringArray = null;
/*  90 */       this.boolArray = null;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/*  96 */       return "Operand{type=" + String.valueOf(this.type) + ", string='" + this.string + "', number=" + this.number + ", bool=" + this.bool + ", numberArray=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 101 */         Arrays.toString(this.numberArray) + ", stringArray=" + 
/* 102 */         Arrays.toString((Object[])this.stringArray) + ", boolArray=" + 
/* 103 */         Arrays.toString(this.boolArray) + "}";
/*     */     }
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
/*     */   public ExecutionContext(Scope scope) {
/* 120 */     this.scope = scope;
/* 121 */     this.operandStack = new Operand[8];
/* 122 */     for (int i = 0; i < this.operandStack.length; i++) {
/* 123 */       this.operandStack[i] = new Operand();
/*     */     }
/*     */   }
/*     */   
/*     */   public ExecutionContext() {
/* 128 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueType execute(@Nonnull List<Instruction> instructions, Scope scope) {
/* 136 */     setScope(scope);
/* 137 */     return execute(instructions);
/*     */   }
/*     */   
/*     */   public ValueType execute(@Nonnull List<Instruction> instructions) {
/* 141 */     Objects.requireNonNull(this.scope, "Scope not initialised executing instructions");
/* 142 */     Objects.requireNonNull(instructions, "Instruction sequence is null executing instructions");
/*     */     
/* 144 */     this.stackTop = -1;
/* 145 */     this.lastPushedType = ValueType.VOID;
/* 146 */     instructions.forEach(instruction -> instruction.execute(this));
/* 147 */     return getType();
/*     */   }
/*     */   
/*     */   public ValueType execute(@Nonnull Instruction[] instructions, Scope scope) {
/* 151 */     setScope(scope);
/* 152 */     return execute(instructions);
/*     */   }
/*     */   
/*     */   public ValueType execute(@Nonnull Instruction[] instructions) {
/* 156 */     Objects.requireNonNull(this.scope, "Scope not initialised executing instructions");
/* 157 */     Objects.requireNonNull(instructions, "Instruction sequence is null executing instructions");
/*     */     
/*     */     try {
/* 160 */       this.stackTop = -1;
/* 161 */       this.lastPushedType = ValueType.VOID;
/* 162 */       for (Instruction instruction : instructions) {
/* 163 */         instruction.execute(this);
/*     */       }
/* 165 */       return getType();
/* 166 */     } catch (Throwable t) {
/* 167 */       throw new IllegalStateException("Failed to execute instruction sequence: ", t);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ValueType getType() {
/* 172 */     return this.lastPushedType;
/*     */   }
/*     */   
/*     */   public Operand top() {
/* 176 */     return get(0);
/*     */   }
/*     */   
/*     */   public Scope setScope(Scope scope) {
/* 180 */     Scope oldScope = getScope();
/* 181 */     this.scope = scope;
/* 182 */     return oldScope;
/*     */   }
/*     */   
/*     */   public Scope getScope() {
/* 186 */     return this.scope;
/*     */   }
/*     */   
/*     */   public String getCombatConfig() {
/* 190 */     return this.combatConfig;
/*     */   }
/*     */   
/*     */   public void setCombatConfig(String combatConfig) {
/* 194 */     this.combatConfig = combatConfig;
/*     */   }
/*     */   
/*     */   public Map<String, String> getInteractionVars() {
/* 198 */     return this.interactionVars;
/*     */   }
/*     */   
/*     */   public void setInteractionVars(Map<String, String> interactionVars) {
/* 202 */     this.interactionVars = interactionVars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Operand push() {
/* 210 */     this.stackTop++;
/* 211 */     if (this.operandStack.length <= this.stackTop) {
/* 212 */       int i = this.operandStack.length;
/* 213 */       this.operandStack = Arrays.<Operand>copyOf(this.operandStack, i + 8);
/* 214 */       while (i < this.operandStack.length) {
/* 215 */         this.operandStack[i++] = new Operand();
/*     */       }
/*     */     } 
/* 218 */     return this.operandStack[this.stackTop];
/*     */   }
/*     */   
/*     */   public void push(String value) {
/* 222 */     this.lastPushedType = push().set(value);
/*     */   }
/*     */   
/*     */   public void push(double value) {
/* 226 */     this.lastPushedType = push().set(value);
/*     */   }
/*     */   
/*     */   public void push(int value) {
/* 230 */     this.lastPushedType = push().set(value);
/*     */   }
/*     */   
/*     */   public void push(boolean value) {
/* 234 */     this.lastPushedType = push().set(value);
/*     */   }
/*     */   
/*     */   public void push(String[] value) {
/* 238 */     this.lastPushedType = push().set(value);
/*     */   }
/*     */   
/*     */   public void push(double[] value) {
/* 242 */     this.lastPushedType = push().set(value);
/*     */   }
/*     */   
/*     */   public void push(boolean[] value) {
/* 246 */     this.lastPushedType = push().set(value);
/*     */   }
/*     */   
/*     */   public void pushEmptyArray() {
/* 250 */     this.lastPushedType = push().setEmptyArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Operand popPush(int popCount) {
/* 257 */     this.stackTop -= popCount - 1;
/* 258 */     return this.operandStack[this.stackTop];
/*     */   }
/*     */   
/*     */   public void popPush(String value, int popCount) {
/* 262 */     this.lastPushedType = popPush(popCount).set(value);
/*     */   }
/*     */   
/*     */   public void popPush(double value, int popCount) {
/* 266 */     this.lastPushedType = popPush(popCount).set(value);
/*     */   }
/*     */   
/*     */   public void popPush(int value, int popCount) {
/* 270 */     this.lastPushedType = popPush(popCount).set(value);
/*     */   }
/*     */   
/*     */   public void popPush(boolean value, int popCount) {
/* 274 */     this.lastPushedType = popPush(popCount).set(value);
/*     */   }
/*     */   
/*     */   public void popPush(String[] value, int popCount) {
/* 278 */     this.lastPushedType = popPush(popCount).set(value);
/*     */   }
/*     */   
/*     */   public void popPush(double[] value, int popCount) {
/* 282 */     this.lastPushedType = popPush(popCount).set(value);
/*     */   }
/*     */   
/*     */   public void popPush(boolean[] value, int popCount) {
/* 286 */     this.lastPushedType = popPush(popCount).set(value);
/*     */   }
/*     */   
/*     */   public void popPushEmptyArray(int popCount) {
/* 290 */     this.lastPushedType = popPush(popCount).setEmptyArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Operand pop() {
/* 297 */     this.lastPushedType = ValueType.VOID;
/* 298 */     return this.operandStack[this.stackTop--];
/*     */   }
/*     */   
/*     */   public double popNumber() {
/* 302 */     return (pop()).number;
/*     */   }
/*     */   
/*     */   public int popInt() {
/* 306 */     return (int)(pop()).number;
/*     */   }
/*     */   
/*     */   public String popString() {
/* 310 */     return (pop()).string;
/*     */   }
/*     */   
/*     */   public boolean popBoolean() {
/* 314 */     return (pop()).bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] popNumberArray() {
/* 319 */     return ((top()).type != ValueType.EMPTY_ARRAY) ? (pop()).numberArray : ArrayUtil.EMPTY_DOUBLE_ARRAY;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String[] popStringArray() {
/* 324 */     return ((top()).type != ValueType.EMPTY_ARRAY) ? (pop()).stringArray : ArrayUtil.EMPTY_STRING_ARRAY;
/*     */   }
/*     */   
/*     */   public boolean[] popBooleanArray() {
/* 328 */     return ((top()).type != ValueType.EMPTY_ARRAY) ? (pop()).boolArray : ArrayUtil.EMPTY_BOOLEAN_ARRAY;
/*     */   }
/*     */   
/*     */   public String popAsString() {
/* 332 */     Operand op = pop();
/* 333 */     switch (op.type) { default: throw new MatchException(null, null);case VOID: case STRING: case NUMBER: case BOOLEAN: case NUMBER_ARRAY: case STRING_ARRAY: case BOOLEAN_ARRAY: case EMPTY_ARRAY: break; }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 341 */       "[]";
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Instruction
/*     */   {
/*     */     void execute(ExecutionContext param1ExecutionContext);
/*     */   }
/*     */   
/*     */   protected Operand get(int index) {
/* 351 */     return this.operandStack[this.stackTop - index];
/*     */   }
/*     */   
/*     */   public double getNumber(int index) {
/* 355 */     return (get(index)).number;
/*     */   }
/*     */   
/*     */   public int getInt(int index) {
/* 359 */     return (int)(get(index)).number;
/*     */   }
/*     */   
/*     */   public String getString(int index) {
/* 363 */     return (get(index)).string;
/*     */   }
/*     */   
/*     */   public boolean getBoolean(int index) {
/* 367 */     return (get(index)).bool;
/*     */   }
/*     */   
/*     */   public double[] getNumberArray(int index) {
/* 371 */     return (get(index)).numberArray;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String[] getStringArray(int index) {
/* 376 */     return (get(index)).stringArray;
/*     */   }
/*     */   
/*     */   public boolean[] getBooleanArray(int index) {
/* 380 */     return (get(index)).boolArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genPUSH(String value) {
/* 389 */     return context -> context.push(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genPUSH(double value) {
/* 396 */     return context -> context.push(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genPUSH(boolean value) {
/* 403 */     return context -> context.push(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genPUSH(String[] value) {
/* 410 */     return context -> context.push(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genPUSH(double[] value) {
/* 417 */     return context -> context.push(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genPUSH(boolean[] value) {
/* 424 */     return context -> context.push(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genPUSHEmptyArray() {
/* 431 */     return ExecutionContext::pushEmptyArray;
/*     */   }
/*     */   @Nonnull
/*     */   public static Instruction genREAD(String ident, @Nonnull ValueType type, @Nullable Scope scope) {
/*     */     Supplier<String> supplier3;
/*     */     DoubleSupplier doubleSupplier;
/*     */     BooleanSupplier booleanSupplier;
/*     */     Supplier<String[]> supplier2;
/*     */     Supplier<double[]> supplier1;
/*     */     Supplier<boolean[]> supplier;
/* 441 */     if (scope == null) {
/* 442 */       switch (type) { case STRING: 
/*     */         case NUMBER: 
/*     */         case BOOLEAN: 
/*     */         case STRING_ARRAY: 
/*     */         case NUMBER_ARRAY:
/*     */         
/*     */         case BOOLEAN_ARRAY:
/* 449 */          }  throw new RuntimeException("ExecutionContext: Invalid read type");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 456 */     switch (type) {
/*     */       case STRING:
/* 458 */         supplier3 = scope.getStringSupplier(ident);
/*     */ 
/*     */       
/*     */       case NUMBER:
/* 462 */         doubleSupplier = scope.getNumberSupplier(ident);
/*     */ 
/*     */       
/*     */       case BOOLEAN:
/* 466 */         booleanSupplier = scope.getBooleanSupplier(ident);
/*     */ 
/*     */       
/*     */       case STRING_ARRAY:
/* 470 */         supplier2 = scope.getStringArraySupplier(ident);
/*     */ 
/*     */       
/*     */       case NUMBER_ARRAY:
/* 474 */         supplier1 = scope.getNumberArraySupplier(ident);
/*     */ 
/*     */       
/*     */       case BOOLEAN_ARRAY:
/* 478 */         supplier = scope.getBooleanArraySupplier(ident);
/*     */     } 
/*     */     
/* 481 */     throw new RuntimeException("ExecutionContext: Invalid read type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genCALL(String ident, int numArgs, @Nullable Scope scope) {
/* 491 */     if (scope == null) {
/* 492 */       return context -> context.scope.getFunction(ident).call(context, numArgs);
/*     */     }
/*     */ 
/*     */     
/* 496 */     Scope.Function function = scope.getFunction(ident);
/* 497 */     return context -> function.call(context, numArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genNumberPACK(int size) {
/* 505 */     return context -> {
/*     */         double[] array = new double[size];
/*     */         for (int i = 0; i < size; i++) {
/*     */           array[i] = context.getNumber(size - i);
/*     */         }
/*     */         context.popPush(array, size);
/*     */       };
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genStringPACK(int size) {
/* 516 */     return context -> {
/*     */         String[] array = new String[size];
/*     */         for (int i = 0; i < size; i++) {
/*     */           array[i] = context.getString(size - i);
/*     */         }
/*     */         context.popPush(array, size);
/*     */       };
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genBooleanPACK(int size) {
/* 527 */     return context -> {
/*     */         boolean[] array = new boolean[size];
/*     */         for (int i = 0; i < size; i++) {
/*     */           array[i] = context.getBoolean(size - i);
/*     */         }
/*     */         context.popPush(array, size);
/*     */       };
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Instruction genPACK(@Nonnull ValueType arrayType, int size) {
/* 538 */     switch (arrayType) { case NUMBER_ARRAY: 
/*     */       case STRING_ARRAY:
/*     */       
/*     */       case BOOLEAN_ARRAY:
/* 542 */        }  throw new IllegalStateException("Cannot create PACK instruction for type " + String.valueOf(arrayType));
/*     */   }
/*     */   public static final Instruction UNARY_PLUS = context -> {
/*     */     
/*     */     }; public static final Instruction UNARY_MINUS; public static final Instruction LOGICAL_NOT; public static final Instruction BITWISE_NOT; public static final Instruction EXPONENTIATION; public static final Instruction REMAINDER; public static final Instruction DIVIDE; public static final Instruction MULTIPLY; public static final Instruction MINUS; public static final Instruction PLUS;
/*     */   public static final Instruction GREATER_EQUAL;
/*     */   
/*     */   static {
/* 550 */     UNARY_MINUS = (context -> context.push(-context.popNumber()));
/* 551 */     LOGICAL_NOT = (context -> context.push(!context.popBoolean()));
/* 552 */     BITWISE_NOT = (context -> context.push(context.popInt() ^ 0xFFFFFFFF));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 557 */     EXPONENTIATION = (context -> context.popPush(Math.pow(context.getNumber(1), context.getNumber(0)), 2));
/* 558 */     REMAINDER = (context -> context.popPush(context.getNumber(1) % context.getNumber(0), 2));
/* 559 */     DIVIDE = (context -> context.popPush(context.getNumber(1) / context.getNumber(0), 2));
/* 560 */     MULTIPLY = (context -> context.popPush(context.getNumber(1) * context.getNumber(0), 2));
/* 561 */     MINUS = (context -> context.popPush(context.getNumber(1) - context.getNumber(0), 2));
/* 562 */     PLUS = (context -> context.popPush(context.getNumber(1) + context.getNumber(0), 2));
/* 563 */     GREATER_EQUAL = (context -> context.popPush((context.getNumber(1) >= context.getNumber(0)), 2));
/* 564 */     GREATER = (context -> context.popPush((context.getNumber(1) > context.getNumber(0)), 2));
/* 565 */     LESS_EQUAL = (context -> context.popPush((context.getNumber(1) <= context.getNumber(0)), 2));
/* 566 */     LESS = (context -> context.popPush((context.getNumber(1) < context.getNumber(0)), 2));
/* 567 */     NOT_EQUAL = (context -> context.popPush((context.getNumber(1) != context.getNumber(0)), 2));
/* 568 */     EQUAL = (context -> context.popPush((context.getNumber(1) == context.getNumber(0)), 2));
/* 569 */     NOT_EQUAL_BOOL = (context -> context.popPush((context.getBoolean(1) != context.getBoolean(0)), 2));
/* 570 */     EQUAL_BOOL = (context -> context.popPush((context.getBoolean(1) == context.getBoolean(0)), 2));
/* 571 */     BITWISE_AND = (context -> context.popPush(context.getInt(1) & context.getInt(0), 2));
/* 572 */     BITWISE_XOR = (context -> context.popPush(context.getInt(1) ^ context.getInt(0), 2));
/* 573 */     BITWISE_OR = (context -> context.popPush(context.getInt(1) | context.getInt(0), 2));
/* 574 */     LOGICAL_AND = (context -> context.popPush((context.getBoolean(1) && context.getBoolean(0)), 2));
/* 575 */     LOGICAL_OR = (context -> context.popPush((context.getBoolean(1) || context.getBoolean(0)), 2));
/*     */   }
/*     */   public static final Instruction GREATER; public static final Instruction LESS_EQUAL; public static final Instruction LESS; public static final Instruction NOT_EQUAL; public static final Instruction EQUAL; public static final Instruction NOT_EQUAL_BOOL; public static final Instruction EQUAL_BOOL; public static final Instruction BITWISE_AND; public static final Instruction BITWISE_XOR; public static final Instruction BITWISE_OR; public static final Instruction LOGICAL_AND; public static final Instruction LOGICAL_OR;
/*     */   @Nonnull
/*     */   public String toString() {
/* 580 */     return "ExecutionContext{scope=" + String.valueOf(this.scope) + ", operandStack=" + 
/*     */       
/* 582 */       Arrays.toString((Object[])this.operandStack) + ", stackTop=" + this.stackTop + ", lastPushedType=" + String.valueOf(this.lastPushedType) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\ExecutionContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */