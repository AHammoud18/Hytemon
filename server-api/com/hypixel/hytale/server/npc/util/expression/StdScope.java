/*     */ package com.hypixel.hytale.server.npc.util.expression;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import java.util.HashMap;
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
/*     */ public class StdScope
/*     */   implements Scope
/*     */ {
/*  22 */   protected static final SymbolStringArray VAR_EMPTY_STRING_ARRAY = new SymbolStringArray(false, () -> ArrayUtil.EMPTY_STRING_ARRAY);
/*  23 */   protected static final SymbolNumberArray VAR_EMPTY_NUMBER_ARRAY = new SymbolNumberArray(false, () -> ArrayUtil.EMPTY_DOUBLE_ARRAY);
/*  24 */   protected static final SymbolBooleanArray VAR_EMPTY_BOOLEAN_ARRAY = new SymbolBooleanArray(false, () -> ArrayUtil.EMPTY_BOOLEAN_ARRAY);
/*  25 */   protected static final SymbolStringArray VAR_NULL_STRING_ARRAY = new SymbolStringArray(false, () -> null);
/*  26 */   protected static final SymbolNumberArray VAR_NULL_NUMBER_ARRAY = new SymbolNumberArray(false, () -> null);
/*  27 */   protected static final SymbolBooleanArray VAR_NULL_BOOLEAN_ARRAY = new SymbolBooleanArray(false, () -> null);
/*  28 */   protected static final SymbolString VAR_NULL_STRING = new SymbolString(false, () -> null);
/*  29 */   protected static final SymbolString VAR_EMPTY_STRING = new SymbolString(false, () -> "");
/*  30 */   protected static final SymbolBoolean VAR_BOOLEAN_TRUE = new SymbolBoolean(false, () -> true);
/*  31 */   protected static final SymbolBoolean VAR_BOOLEAN_FALSE = new SymbolBoolean(false, () -> false);
/*  32 */   protected static final SymbolStringArray CONST_EMPTY_STRING_ARRAY = new SymbolStringArray(true, () -> ArrayUtil.EMPTY_STRING_ARRAY);
/*  33 */   protected static final SymbolNumberArray CONST_EMPTY_NUMBER_ARRAY = new SymbolNumberArray(true, () -> ArrayUtil.EMPTY_DOUBLE_ARRAY);
/*  34 */   protected static final SymbolBooleanArray CONST_EMPTY_BOOLEAN_ARRAY = new SymbolBooleanArray(true, () -> ArrayUtil.EMPTY_BOOLEAN_ARRAY);
/*  35 */   protected static final SymbolStringArray CONST_NULL_STRING_ARRAY = new SymbolStringArray(true, () -> null);
/*  36 */   protected static final SymbolNumberArray CONST_NULL_NUMBER_ARRAY = new SymbolNumberArray(true, () -> null);
/*  37 */   protected static final SymbolBooleanArray CONST_NULL_BOOLEAN_ARRAY = new SymbolBooleanArray(true, () -> null);
/*  38 */   protected static final SymbolString CONST_NULL_STRING = new SymbolString(true, () -> null);
/*  39 */   protected static final SymbolString CONST_EMPTY_STRING = new SymbolString(true, () -> "");
/*  40 */   protected static final SymbolBoolean CONST_BOOLEAN_TRUE = new SymbolBoolean(true, () -> true);
/*  41 */   protected static final SymbolBoolean CONST_BOOLEAN_FALSE = new SymbolBoolean(true, () -> false);
/*     */   protected Scope parent;
/*     */   protected Map<String, Symbol> symbolTable;
/*     */   
/*     */   protected static class Symbol {
/*     */     public final boolean isConstant;
/*     */     public final ValueType valueType;
/*     */     
/*     */     public Symbol(boolean isConstant, ValueType valueType) {
/*  50 */       this.isConstant = isConstant;
/*  51 */       this.valueType = valueType;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class SymbolString extends Symbol {
/*     */     public final Supplier<String> value;
/*     */     
/*     */     public SymbolString(boolean isConstant, Supplier<String> value) {
/*  59 */       super(isConstant, ValueType.STRING);
/*  60 */       this.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class SymbolNumber extends Symbol {
/*     */     public final DoubleSupplier value;
/*     */     
/*     */     public SymbolNumber(boolean isConstant, DoubleSupplier value) {
/*  68 */       super(isConstant, ValueType.NUMBER);
/*  69 */       this.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class SymbolBoolean extends Symbol {
/*     */     public final BooleanSupplier value;
/*     */     
/*     */     public SymbolBoolean(boolean isConstant, BooleanSupplier value) {
/*  77 */       super(isConstant, ValueType.BOOLEAN);
/*  78 */       this.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class SymbolStringArray extends Symbol {
/*     */     public final Supplier<String[]> value;
/*     */     
/*     */     public SymbolStringArray(boolean isConstant, Supplier<String[]> value) {
/*  86 */       super(isConstant, ValueType.STRING_ARRAY);
/*  87 */       this.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class SymbolNumberArray extends Symbol {
/*     */     public final Supplier<double[]> value;
/*     */     
/*     */     public SymbolNumberArray(boolean isConstant, Supplier<double[]> value) {
/*  95 */       super(isConstant, ValueType.NUMBER_ARRAY);
/*  96 */       this.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class SymbolBooleanArray extends Symbol {
/*     */     public final Supplier<boolean[]> value;
/*     */     
/*     */     public SymbolBooleanArray(boolean isConstant, Supplier<boolean[]> value) {
/* 104 */       super(isConstant, ValueType.BOOLEAN_ARRAY);
/* 105 */       this.value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class SymbolFunction extends Symbol {
/*     */     public final Scope.Function value;
/*     */     
/*     */     public SymbolFunction(boolean isConstant, ValueType returnType, Scope.Function value) {
/* 113 */       super(isConstant, returnType);
/* 114 */       this.value = value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StdScope(Scope parent) {
/* 123 */     this.parent = parent;
/* 124 */     this.symbolTable = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static StdScope copyOf(@Nonnull StdScope other) {
/* 135 */     StdScope scope = new StdScope(other.parent);
/* 136 */     scope.mergeSymbols(other);
/* 137 */     return scope;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public StdScope merge(@Nonnull StdScope other) {
/* 148 */     mergeSymbols(other);
/* 149 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static StdScope mergeScopes(@Nonnull StdScope first, @Nonnull StdScope second) {
/* 154 */     return copyOf(first).merge(second);
/*     */   }
/*     */   
/*     */   protected void mergeSymbols(@Nonnull StdScope other) {
/* 158 */     other.symbolTable.forEach(this::add);
/*     */   }
/*     */   
/*     */   protected void add(String name, Symbol symbol) {
/* 162 */     if (this.symbolTable.containsKey(name)) {
/* 163 */       throw new IllegalStateException("Trying to add symbol twice to scope " + name);
/*     */     }
/* 165 */     this.symbolTable.put(name, symbol);
/*     */   }
/*     */   
/*     */   public void addConst(String name, @Nullable String value) {
/* 169 */     if (value == null) {
/* 170 */       add(name, CONST_NULL_STRING);
/* 171 */     } else if (value.isEmpty()) {
/* 172 */       add(name, CONST_EMPTY_STRING);
/*     */     } else {
/* 174 */       add(name, new SymbolString(true, () -> value));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addConst(String name, double value) {
/* 179 */     add(name, new SymbolNumber(true, () -> value));
/*     */   }
/*     */   
/*     */   public void addConst(String name, boolean value) {
/* 183 */     add(name, value ? CONST_BOOLEAN_TRUE : CONST_BOOLEAN_FALSE);
/*     */   }
/*     */   
/*     */   public void addConst(String name, @Nullable String[] value) {
/* 187 */     if (value == null) {
/* 188 */       add(name, CONST_NULL_STRING_ARRAY);
/* 189 */     } else if (value.length == 0) {
/* 190 */       add(name, CONST_EMPTY_STRING_ARRAY);
/*     */     } else {
/* 192 */       add(name, new SymbolStringArray(true, () -> value));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addConst(String name, @Nullable double[] value) {
/* 197 */     if (value == null) {
/* 198 */       add(name, CONST_NULL_NUMBER_ARRAY);
/* 199 */     } else if (value.length == 0) {
/* 200 */       add(name, CONST_EMPTY_NUMBER_ARRAY);
/*     */     } else {
/* 202 */       add(name, new SymbolNumberArray(true, () -> value));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addConst(String name, @Nullable boolean[] value) {
/* 207 */     if (value == null) {
/* 208 */       add(name, CONST_NULL_BOOLEAN_ARRAY);
/* 209 */     } else if (value.length == 0) {
/* 210 */       add(name, CONST_EMPTY_BOOLEAN_ARRAY);
/*     */     } else {
/* 212 */       add(name, new SymbolBooleanArray(true, () -> value));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addConstEmptyArray(String name) {
/* 217 */     add(name, new Symbol(true, ValueType.EMPTY_ARRAY));
/*     */   }
/*     */   
/*     */   public void addVar(String name, @Nullable String value) {
/* 221 */     if (value == null) {
/* 222 */       add(name, VAR_NULL_STRING);
/* 223 */     } else if (value.isEmpty()) {
/* 224 */       add(name, VAR_EMPTY_STRING);
/*     */     } else {
/* 226 */       add(name, new SymbolString(false, () -> value));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addVar(String name, double value) {
/* 231 */     add(name, new SymbolNumber(false, () -> value));
/*     */   }
/*     */   
/*     */   public void addVar(String name, boolean value) {
/* 235 */     add(name, value ? VAR_BOOLEAN_TRUE : VAR_BOOLEAN_FALSE);
/*     */   }
/*     */   
/*     */   public void addVar(String name, @Nullable String[] value) {
/* 239 */     if (value == null) {
/* 240 */       add(name, VAR_NULL_STRING_ARRAY);
/* 241 */     } else if (value.length == 0) {
/* 242 */       add(name, VAR_EMPTY_STRING_ARRAY);
/*     */     } else {
/* 244 */       add(name, new SymbolStringArray(false, () -> value));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addVar(String name, @Nullable double[] value) {
/* 249 */     if (value == null) {
/* 250 */       add(name, VAR_NULL_NUMBER_ARRAY);
/* 251 */     } else if (value.length == 0) {
/* 252 */       add(name, VAR_EMPTY_NUMBER_ARRAY);
/*     */     } else {
/* 254 */       add(name, new SymbolNumberArray(false, () -> value));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addVar(String name, @Nullable boolean[] value) {
/* 259 */     if (value == null) {
/* 260 */       add(name, VAR_NULL_BOOLEAN_ARRAY);
/* 261 */     } else if (value.length == 0) {
/* 262 */       add(name, VAR_EMPTY_BOOLEAN_ARRAY);
/*     */     } else {
/* 264 */       add(name, new SymbolBooleanArray(false, () -> value));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addInvariant(@Nonnull String name, Scope.Function function, ValueType returnType, @Nonnull ValueType... argumentTypes) {
/* 269 */     add(Scope.encodeFunctionName(name, argumentTypes), new SymbolFunction(true, returnType, function));
/*     */     
/* 271 */     add(name, new SymbolFunction(false, returnType, null));
/*     */   }
/*     */   
/*     */   public void addVariant(@Nonnull String name, Scope.Function function, ValueType returnType, @Nonnull ValueType... argumentTypes) {
/* 275 */     add(Scope.encodeFunctionName(name, argumentTypes), new SymbolFunction(false, returnType, function));
/*     */     
/* 277 */     add(name, new SymbolFunction(false, returnType, null));
/*     */   }
/*     */   
/*     */   public void addSupplier(String name, Supplier<String> value) {
/* 281 */     add(name, new SymbolString(false, value));
/*     */   }
/*     */   
/*     */   public void addSupplier(String name, DoubleSupplier value) {
/* 285 */     add(name, new SymbolNumber(false, value));
/*     */   }
/*     */   
/*     */   public void addSupplier(String name, BooleanSupplier value) {
/* 289 */     add(name, new SymbolBoolean(false, value));
/*     */   }
/*     */   
/*     */   public void addStringArraySupplier(String name, Supplier<String[]> value) {
/* 293 */     add(name, new SymbolStringArray(false, value));
/*     */   }
/*     */   
/*     */   public void addDoubleArraySupplier(String name, Supplier<double[]> value) {
/* 297 */     add(name, new SymbolNumberArray(false, value));
/*     */   }
/*     */   
/*     */   public void addBooleanArraySupplier(String name, Supplier<boolean[]> value) {
/* 301 */     add(name, new SymbolBooleanArray(false, value));
/*     */   }
/*     */   
/*     */   protected Symbol get(String name) {
/* 305 */     return this.symbolTable.get(name);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Symbol get(String name, ValueType valueType) {
/* 310 */     Symbol symbol = this.symbolTable.get(name);
/*     */     
/* 312 */     if (symbol == null) {
/* 313 */       throw new IllegalStateException("Can't find symbol " + name + " in symbol table");
/*     */     }
/* 315 */     if (!ValueType.isAssignableType(valueType, symbol.valueType)) {
/* 316 */       throw new IllegalStateException("Type mismatch with " + name + ". Got " + String.valueOf(valueType) + " but expected " + String.valueOf(symbol.valueType));
/*     */     }
/*     */     
/* 319 */     return symbol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void replace(String name, @Nonnull Symbol symbol) {
/* 330 */     Symbol oldSymbol = get(name, symbol.valueType);
/* 331 */     if (oldSymbol.isConstant) {
/* 332 */       throw new IllegalStateException("Can't replace a constant in symbol table: " + name);
/*     */     }
/* 334 */     if (symbol.isConstant) {
/* 335 */       throw new IllegalStateException("Can't replace a variable with a constant: " + name);
/*     */     }
/* 337 */     this.symbolTable.put(name, symbol);
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
/*     */   public void changeValue(String name, @Nullable String value) {
/* 349 */     if (value == null) {
/* 350 */       replace(name, VAR_NULL_STRING);
/* 351 */     } else if (value.isEmpty()) {
/* 352 */       replace(name, VAR_EMPTY_STRING);
/*     */     } else {
/* 354 */       replace(name, new SymbolString(false, () -> value));
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
/*     */   public void changeValue(String name, double value) {
/* 367 */     replace(name, new SymbolNumber(false, () -> value));
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
/*     */   public void changeValue(String name, boolean value) {
/* 379 */     replace(name, value ? VAR_BOOLEAN_TRUE : VAR_BOOLEAN_FALSE);
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
/*     */   public void changeValue(String name, @Nullable String[] value) {
/* 391 */     if (value == null) {
/* 392 */       replace(name, VAR_NULL_STRING_ARRAY);
/* 393 */     } else if (value.length == 0) {
/* 394 */       replace(name, VAR_EMPTY_STRING_ARRAY);
/*     */     } else {
/* 396 */       replace(name, new SymbolStringArray(false, () -> value));
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
/*     */   public void changeValue(String name, @Nullable double[] value) {
/* 409 */     if (value == null) {
/* 410 */       replace(name, VAR_NULL_NUMBER_ARRAY);
/* 411 */     } else if (value.length == 0) {
/* 412 */       replace(name, VAR_EMPTY_NUMBER_ARRAY);
/*     */     } else {
/* 414 */       replace(name, new SymbolNumberArray(false, () -> value));
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
/*     */   public void changeValue(String name, @Nullable boolean[] value) {
/* 427 */     if (value == null) {
/* 428 */       replace(name, VAR_NULL_BOOLEAN_ARRAY);
/* 429 */     } else if (value.length == 0) {
/* 430 */       replace(name, VAR_EMPTY_BOOLEAN_ARRAY);
/*     */     } else {
/* 432 */       replace(name, new SymbolBooleanArray(false, () -> value));
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
/*     */   public void changeValueToEmptyArray(String name) {
/* 444 */     Symbol symbol = get(name);
/*     */     
/* 446 */     Objects.requireNonNull(symbol, "Can't find symbol in symbol table in changeValue()");
/* 447 */     if (symbol.isConstant) {
/* 448 */       throw new IllegalStateException("Can't replace a constant in symbol table: " + name);
/*     */     }
/*     */     
/* 451 */     switch (symbol.valueType) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 457 */         throw new IllegalStateException("Can't assign an empty array to symbol " + name + "  of type " + String.valueOf(symbol.valueType));
/*     */       case EMPTY_ARRAY:
/*     */         return;
/*     */       case NUMBER_ARRAY:
/* 461 */         this.symbolTable.put(name, VAR_EMPTY_NUMBER_ARRAY);
/*     */         return;
/*     */       case STRING_ARRAY:
/* 464 */         this.symbolTable.put(name, VAR_EMPTY_STRING_ARRAY); return;
/*     */       case BOOLEAN_ARRAY:
/*     */         break;
/* 467 */     }  this.symbolTable.put(name, VAR_EMPTY_BOOLEAN_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Supplier<String> getStringSupplier(String name) {
/* 475 */     Symbol symbol = get(name);
/* 476 */     if (symbol == null) {
/* 477 */       if (this.parent != null) {
/* 478 */         return this.parent.getStringSupplier(name);
/*     */       }
/* 480 */       throw new IllegalStateException("Unable to find symbol: " + name);
/*     */     } 
/* 482 */     if (symbol instanceof SymbolString) {
/* 483 */       return ((SymbolString)symbol).value;
/*     */     }
/* 485 */     throw new IllegalStateException("Symbol is not a string: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleSupplier getNumberSupplier(String name) {
/* 490 */     Symbol symbol = get(name);
/* 491 */     if (symbol == null) {
/* 492 */       if (this.parent != null) {
/* 493 */         return this.parent.getNumberSupplier(name);
/*     */       }
/* 495 */       throw new IllegalStateException("Unable to find symbol: " + name);
/*     */     } 
/* 497 */     if (symbol instanceof SymbolNumber) {
/* 498 */       return ((SymbolNumber)symbol).value;
/*     */     }
/* 500 */     throw new IllegalStateException("Symbol is not a number: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public BooleanSupplier getBooleanSupplier(String name) {
/* 505 */     Symbol symbol = get(name);
/* 506 */     if (symbol == null) {
/* 507 */       if (this.parent != null) {
/* 508 */         return this.parent.getBooleanSupplier(name);
/*     */       }
/* 510 */       throw new IllegalStateException("Unable to find symbol: " + name);
/*     */     } 
/* 512 */     if (symbol instanceof SymbolBoolean) {
/* 513 */       return ((SymbolBoolean)symbol).value;
/*     */     }
/* 515 */     throw new IllegalStateException("Symbol is not a boolean: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Supplier<String[]> getStringArraySupplier(String name) {
/* 520 */     Symbol symbol = get(name);
/* 521 */     if (symbol == null) {
/* 522 */       if (this.parent != null) {
/* 523 */         return this.parent.getStringArraySupplier(name);
/*     */       }
/* 525 */       throw new IllegalStateException("Unable to find symbol: " + name);
/*     */     } 
/*     */     
/* 528 */     if (symbol.valueType == ValueType.EMPTY_ARRAY) {
/* 529 */       return () -> ArrayUtil.EMPTY_STRING_ARRAY;
/*     */     }
/*     */     
/* 532 */     if (symbol instanceof SymbolStringArray) {
/* 533 */       return ((SymbolStringArray)symbol).value;
/*     */     }
/* 535 */     throw new IllegalStateException("Symbol is not a string array: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Supplier<double[]> getNumberArraySupplier(String name) {
/* 540 */     Symbol symbol = get(name);
/* 541 */     if (symbol == null) {
/* 542 */       if (this.parent != null) {
/* 543 */         return this.parent.getNumberArraySupplier(name);
/*     */       }
/* 545 */       throw new IllegalStateException("Unable to find symbol: " + name);
/*     */     } 
/*     */     
/* 548 */     if (symbol.valueType == ValueType.EMPTY_ARRAY) {
/* 549 */       return () -> ArrayUtil.EMPTY_DOUBLE_ARRAY;
/*     */     }
/*     */     
/* 552 */     if (symbol instanceof SymbolNumberArray) {
/* 553 */       return ((SymbolNumberArray)symbol).value;
/*     */     }
/* 555 */     throw new IllegalStateException("Symbol is not a number array: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Supplier<boolean[]> getBooleanArraySupplier(String name) {
/* 560 */     Symbol symbol = get(name);
/* 561 */     if (symbol == null) {
/* 562 */       if (this.parent != null) {
/* 563 */         return this.parent.getBooleanArraySupplier(name);
/*     */       }
/* 565 */       throw new IllegalStateException("Unable to find symbol: " + name);
/*     */     } 
/*     */     
/* 568 */     if (symbol.valueType == ValueType.EMPTY_ARRAY) {
/* 569 */       return () -> ArrayUtil.EMPTY_BOOLEAN_ARRAY;
/*     */     }
/*     */     
/* 572 */     if (symbol instanceof SymbolBooleanArray) {
/* 573 */       return ((SymbolBooleanArray)symbol).value;
/*     */     }
/* 575 */     throw new IllegalStateException("Symbol is not a boolean array: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Scope.Function getFunction(String name) {
/* 581 */     Symbol symbol = get(name);
/* 582 */     if (symbol == null) {
/* 583 */       if (this.parent != null) {
/* 584 */         return this.parent.getFunction(name);
/*     */       }
/* 586 */       throw new IllegalStateException("Unable to find function: " + name);
/*     */     } 
/* 588 */     if (symbol instanceof SymbolFunction) {
/* 589 */       return ((SymbolFunction)symbol).value;
/*     */     }
/* 591 */     throw new IllegalStateException("Symbol is not a function: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstant(String name) {
/* 596 */     Symbol symbol = get(name);
/*     */     
/* 598 */     if (symbol != null) {
/* 599 */       return symbol.isConstant;
/*     */     }
/* 601 */     if (this.parent == null) {
/* 602 */       throw new IllegalStateException("Unable to find symbol: " + name);
/*     */     }
/* 604 */     return this.parent.isConstant(name);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ValueType getType(String name) {
/* 610 */     Symbol symbol = get(name);
/*     */     
/* 612 */     if (symbol != null) {
/* 613 */       return symbol.valueType;
/*     */     }
/* 615 */     if (this.parent != null) {
/* 616 */       return this.parent.getType(name);
/*     */     }
/* 618 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\StdScope.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */