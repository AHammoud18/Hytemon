/*     */ package com.hypixel.hytale.server.npc.asset.builder.expression;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderExpressionStaticNumberArray
/*     */   extends BuilderExpression
/*     */ {
/*  19 */   public static final BuilderExpressionStaticNumberArray INSTANCE_EMPTY = new BuilderExpressionStaticNumberArray(ArrayUtil.EMPTY_DOUBLE_ARRAY);
/*     */   
/*     */   private final double[] numberArray;
/*     */   
/*     */   @Nullable
/*     */   private int[] cachedIntArray;
/*     */   
/*     */   public BuilderExpressionStaticNumberArray(double[] array) {
/*  27 */     this.numberArray = array;
/*  28 */     this.cachedIntArray = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ValueType getType() {
/*  34 */     return ValueType.NUMBER_ARRAY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStatic() {
/*  39 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] getNumberArray(ExecutionContext executionContext) {
/*  44 */     return this.numberArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getIntegerArray(ExecutionContext executionContext) {
/*  49 */     createCacheIfAbsent();
/*  50 */     return this.cachedIntArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToScope(String name, @Nonnull StdScope scope) {
/*  55 */     scope.addVar(name, this.numberArray);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScope(@Nonnull StdScope scope, String name, ExecutionContext executionContext) {
/*  60 */     scope.changeValue(name, this.numberArray);
/*     */   }
/*     */   
/*     */   private void createCacheIfAbsent() {
/*  64 */     if (this.cachedIntArray == null) {
/*  65 */       this.cachedIntArray = convertDoubleToIntArray(this.numberArray);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BuilderExpressionStaticNumberArray fromJSON(@Nonnull JsonArray jsonArray) {
/*  71 */     int size = jsonArray.size();
/*  72 */     double[] array = new double[size];
/*     */     
/*  74 */     for (int i = 0; i < size; i++) {
/*  75 */       JsonElement element = jsonArray.get(i);
/*  76 */       if (!element.isJsonPrimitive()) return null; 
/*  77 */       JsonPrimitive primitive = element.getAsJsonPrimitive();
/*  78 */       if (!primitive.isNumber()) return null; 
/*  79 */       array[i] = primitive.getAsDouble();
/*     */     } 
/*  81 */     return new BuilderExpressionStaticNumberArray(array);
/*     */   }
/*     */   
/*     */   public static int[] convertDoubleToIntArray(@Nullable double[] source) {
/*  85 */     if (source == null) return null; 
/*  86 */     int length = source.length;
/*  87 */     int[] result = new int[length];
/*  88 */     for (int i = 0; i < length; i++)
/*     */     {
/*  90 */       result[i] = (int)Math.round(source[i]);
/*     */     }
/*  92 */     return result;
/*     */   }
/*     */   
/*     */   public static double[] convertIntToDoubleArray(@Nullable int[] source) {
/*  96 */     if (source == null) return null; 
/*  97 */     int length = source.length;
/*  98 */     double[] result = new double[length];
/*  99 */     for (int i = 0; i < length; i++)
/*     */     {
/* 101 */       result[i] = source[i];
/*     */     }
/* 103 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionStaticNumberArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */