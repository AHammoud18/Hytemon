/*    */ package com.hypixel.hytale.server.npc.util.expression;
/*    */ 
/*    */ import java.util.function.BooleanSupplier;
/*    */ import java.util.function.DoubleSupplier;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ public interface Scope
/*    */ {
/*    */   Supplier<String> getStringSupplier(String paramString);
/*    */   
/*    */   DoubleSupplier getNumberSupplier(String paramString);
/*    */   
/*    */   BooleanSupplier getBooleanSupplier(String paramString);
/*    */   
/*    */   Supplier<String[]> getStringArraySupplier(String paramString);
/*    */   
/*    */   Supplier<double[]> getNumberArraySupplier(String paramString);
/*    */   
/*    */   Supplier<boolean[]> getBooleanArraySupplier(String paramString);
/*    */   
/*    */   Function getFunction(String paramString);
/*    */   
/*    */   default String getString(String name) {
/* 38 */     return getStringSupplier(name).get();
/*    */   }
/*    */   
/*    */   default double getNumber(String name) {
/* 42 */     return getNumberSupplier(name).getAsDouble();
/*    */   }
/*    */   
/*    */   default boolean getBoolean(String name) {
/* 46 */     return getBooleanSupplier(name).getAsBoolean();
/*    */   }
/*    */   
/*    */   default String[] getStringArray(String name) {
/* 50 */     return getStringArraySupplier(name).get();
/*    */   }
/*    */   
/*    */   default double[] getNumberArray(String name) {
/* 54 */     return getNumberArraySupplier(name).get();
/*    */   }
/*    */   
/*    */   default boolean[] getBooleanArray(String name) {
/* 58 */     return getBooleanArraySupplier(name).get();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isConstant(String paramString);
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   ValueType getType(String paramString);
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   static String encodeFunctionName(@Nonnull String name, @Nonnull ValueType[] values) {
/* 74 */     StringBuilder stringBuilder = (new StringBuilder(name)).append('@');
/* 75 */     for (int i = 0; i < values.length; i++) {
/* 76 */       stringBuilder.append(encodeType(values[i]));
/*    */     }
/* 78 */     return stringBuilder.toString();
/*    */   }
/*    */   
/*    */   static char encodeType(@Nonnull ValueType type) {
/* 82 */     switch (type) { case NUMBER: 
/*    */       case STRING: 
/*    */       case BOOLEAN: 
/*    */       case NUMBER_ARRAY: 
/*    */       case STRING_ARRAY:
/*    */       
/*    */       case BOOLEAN_ARRAY:
/* 89 */        }  throw new IllegalStateException("Type cannot be encoded for function name: " + String.valueOf(type));
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface Function {
/*    */     void call(ExecutionContext param1ExecutionContext, int param1Int);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\Scope.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */