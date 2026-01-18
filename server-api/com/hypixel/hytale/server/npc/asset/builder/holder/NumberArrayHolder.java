/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticNumberArray;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleArrayValidator;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.IntArrayValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NumberArrayHolder
/*    */   extends ArrayHolder
/*    */ {
/*    */   protected IntArrayValidator intArrayValidator;
/*    */   protected DoubleArrayValidator doubleArrayValidator;
/*    */   
/*    */   public NumberArrayHolder() {
/* 24 */     super(ValueType.NUMBER_ARRAY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 29 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, int minLength, int maxLength, IntArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 33 */     readJSON(requiredJsonElement, minLength, maxLength, name, builderParameters);
/* 34 */     this.intArrayValidator = validator;
/* 35 */     if (isStatic()) validate(this.expression.getIntegerArray(null)); 
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, int minLength, int maxLength, DoubleArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 39 */     readJSON(requiredJsonElement, minLength, maxLength, name, builderParameters);
/* 40 */     this.doubleArrayValidator = validator;
/* 41 */     if (isStatic()) validate(this.expression.getNumberArray(null)); 
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, int minLength, int maxLength, int[] defaultValue, IntArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 45 */     readJSON(optionalJsonElement, minLength, maxLength, BuilderExpressionStaticNumberArray.convertIntToDoubleArray(defaultValue), name, builderParameters);
/* 46 */     this.intArrayValidator = validator;
/* 47 */     if (isStatic()) validate(this.expression.getIntegerArray(null)); 
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, int minLength, int maxLength, double[] defaultValue, DoubleArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 51 */     readJSON(optionalJsonElement, minLength, maxLength, defaultValue, name, builderParameters);
/* 52 */     this.doubleArrayValidator = validator;
/* 53 */     if (isStatic()) validate(this.expression.getNumberArray(null)); 
/*    */   }
/*    */   
/*    */   public double[] get(ExecutionContext executionContext) {
/* 57 */     double[] value = rawGet(executionContext);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     return value;
/*    */   }
/*    */   
/*    */   public double[] rawGet(ExecutionContext executionContext) {
/* 66 */     double[] value = this.expression.getNumberArray(executionContext);
/* 67 */     if (!isStatic()) validate(value); 
/* 68 */     return value;
/*    */   }
/*    */   
/*    */   public int[] getIntArray(ExecutionContext executionContext) {
/* 72 */     int[] value = rawGetIntArray(executionContext);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     return value;
/*    */   }
/*    */   
/*    */   public int[] rawGetIntArray(ExecutionContext executionContext) {
/* 81 */     int[] value = this.expression.getIntegerArray(executionContext);
/* 82 */     if (!isStatic()) validate(value); 
/* 83 */     return value;
/*    */   }
/*    */   
/*    */   public void validate(@Nullable int[] value) {
/* 87 */     if (value != null) validateLength(value.length); 
/* 88 */     if (this.intArrayValidator != null && !this.intArrayValidator.test(value)) {
/* 89 */       throw new IllegalStateException(this.intArrayValidator.errorMessage(value, this.name));
/*    */     }
/*    */   }
/*    */   
/*    */   public void validate(@Nullable double[] value) {
/* 94 */     if (value != null) validateLength(value.length); 
/* 95 */     if (this.doubleArrayValidator != null && !this.doubleArrayValidator.test(value))
/* 96 */       throw new IllegalStateException(this.doubleArrayValidator.errorMessage(value, this.name)); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\NumberArrayHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */