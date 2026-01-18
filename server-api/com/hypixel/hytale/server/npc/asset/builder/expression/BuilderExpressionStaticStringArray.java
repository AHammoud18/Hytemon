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
/*    */ public class BuilderExpressionStaticStringArray
/*    */   extends BuilderExpression
/*    */ {
/* 19 */   public static final BuilderExpressionStaticStringArray INSTANCE_EMPTY = new BuilderExpressionStaticStringArray(ArrayUtil.EMPTY_STRING_ARRAY);
/*    */   
/*    */   private final String[] stringArray;
/*    */ 
/*    */   
/*    */   public BuilderExpressionStaticStringArray(String[] array) {
/* 25 */     this.stringArray = array;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ValueType getType() {
/* 31 */     return ValueType.STRING_ARRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStatic() {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getStringArray(ExecutionContext executionContext) {
/* 41 */     return this.stringArray;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addToScope(String name, @Nonnull StdScope scope) {
/* 46 */     scope.addVar(name, this.stringArray);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateScope(@Nonnull StdScope scope, String name, ExecutionContext executionContext) {
/* 51 */     scope.changeValue(name, this.stringArray);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static BuilderExpressionStaticStringArray fromJSON(@Nonnull JsonArray jsonArray) {
/* 56 */     int size = jsonArray.size();
/* 57 */     String[] array = new String[size];
/*    */     
/* 59 */     for (int i = 0; i < size; i++) {
/* 60 */       JsonElement element = jsonArray.get(i);
/* 61 */       if (!element.isJsonPrimitive()) return null; 
/* 62 */       JsonPrimitive primitive = element.getAsJsonPrimitive();
/* 63 */       if (!primitive.isString()) return null; 
/* 64 */       array[i] = primitive.getAsString();
/*    */     } 
/* 66 */     return new BuilderExpressionStaticStringArray(array);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\expression\BuilderExpressionStaticStringArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */