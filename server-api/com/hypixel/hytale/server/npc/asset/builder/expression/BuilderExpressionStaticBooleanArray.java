/*    */ package com.hypixel.hytale.server.npc.asset.builder.expression;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderExpressionStaticBooleanArray
/*    */   extends BuilderExpression
/*    */ {
/* 19 */   public static final BuilderExpressionStaticBooleanArray INSTANCE_EMPTY = new BuilderExpressionStaticBooleanArray(ArrayUtil.EMPTY_BOOLEAN_ARRAY);
/*    */   
/*    */   private final boolean[] booleanArray;
/*    */ 
/*    */   
/*    */   public BuilderExpressionStaticBooleanArray(boolean[] array) {
/* 25 */     this.booleanArray = array;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueType getType() {
/* 31 */     return ValueType.BOOLEAN_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStatic() {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean[] getBooleanArray(ExecutionContext executionContext) {
/* 41 */     return this.booleanArray;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addToScope(String name, @Nonnull StdScope scope) {
/* 46 */     scope.addVar(name, this.booleanArray);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScope(@Nonnull StdScope scope, String name, ExecutionContext executionContext) {
/* 51 */     scope.changeValue(name, this.booleanArray);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static BuilderExpressionStaticBooleanArray fromJSON(@Nonnull JsonArray jsonArray) {
/* 56 */     int size = jsonArray.size();
/* 57 */     boolean[] array = new boolean[size];
/*    */     
/* 59 */     for (int i = 0; i < size; i++) {
/* 60 */       JsonElement element = jsonArray.get(i);
/* 61 */       if (!element.isJsonPrimitive()) return null; 
/* 62 */       JsonPrimitive primitive = element.getAsJsonPrimitive();
/* 63 */       if (!primitive.isBoolean()) return null; 
/* 64 */       array[i] = primitive.getAsBoolean();
/*    */     } 
/* 66 */     return new BuilderExpressionStaticBooleanArray(array);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionStaticBooleanArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */