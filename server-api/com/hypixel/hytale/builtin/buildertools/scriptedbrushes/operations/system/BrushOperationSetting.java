/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system;
/*     */ 
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.server.core.command.system.ParseResult;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BrushOperationSetting<T>
/*     */ {
/*     */   private final String name;
/*     */   private final String description;
/*     */   private String input;
/*     */   private final T defaultValue;
/*     */   @Nullable
/*     */   private T value;
/*     */   private final ArgumentType<T> argumentType;
/*     */   @Nullable
/*     */   private final Validator<T> valueValidator;
/*     */   @Nullable
/*     */   private final Function<BrushOperationSetting<T>, String> toStringFunction;
/*     */   
/*     */   public BrushOperationSetting(String name, String description, T defaultValue, ArgumentType<T> argumentType) {
/*  29 */     this(name, description, defaultValue, argumentType, null, null);
/*     */   }
/*     */   
/*     */   public BrushOperationSetting(String name, String description, T defaultValue, ArgumentType<T> argumentType, Function<BrushOperationSetting<T>, String> toStringFunction) {
/*  33 */     this(name, description, defaultValue, argumentType, null, toStringFunction);
/*     */   }
/*     */   
/*     */   public BrushOperationSetting(String name, String description, T defaultValue, ArgumentType<T> argumentType, @Nullable Validator<T> valueValidator, @Nullable Function<BrushOperationSetting<T>, String> toStringFunction) {
/*  37 */     this.name = name;
/*  38 */     this.description = description;
/*  39 */     this.defaultValue = defaultValue;
/*  40 */     this.value = defaultValue;
/*  41 */     this.argumentType = argumentType;
/*  42 */     this.valueValidator = valueValidator;
/*  43 */     this.toStringFunction = toStringFunction;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BrushOperationSetting<T> setValue(T value) {
/*  48 */     this.value = value;
/*  49 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BrushOperationSetting<T> setValueUnsafe(String input, Object value) {
/*  54 */     this.input = input;
/*     */     
/*  56 */     this.value = (T)value;
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ParseResult parseAndSetValue(String[] input) {
/*  68 */     ParseResult parseResult = new ParseResult();
/*  69 */     T newValue = (T)this.argumentType.parse(input, parseResult);
/*     */     
/*  71 */     if (!parseResult.failed()) this.value = newValue;
/*     */     
/*  73 */     return parseResult;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getInput() {
/*  78 */     return this.input;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  82 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  86 */     return this.description;
/*     */   }
/*     */   
/*     */   public T getDefaultValue() {
/*  90 */     return this.defaultValue;
/*     */   }
/*     */   
/*     */   public ArgumentType<T> getArgumentType() {
/*  94 */     return this.argumentType;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Validator<T> getValueValidator() {
/*  99 */     return this.valueValidator;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T getValue() {
/* 104 */     return this.value;
/*     */   }
/*     */   
/*     */   public String getValueString() {
/* 108 */     if (this.toStringFunction != null) {
/* 109 */       return this.toStringFunction.apply(this);
/*     */     }
/*     */     
/* 112 */     return this.value.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\system\BrushOperationSetting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */