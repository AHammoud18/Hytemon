/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticBooleanArray;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticNumberArray;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticStringArray;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ArrayHolder
/*    */   extends ValueHolder
/*    */ {
/*    */   protected int minLength;
/* 18 */   protected int maxLength = Integer.MAX_VALUE;
/*    */   
/*    */   public ArrayHolder(ValueType valueType) {
/* 21 */     super(valueType);
/*    */   }
/*    */   
/*    */   protected void readJSON(@Nonnull JsonElement requiredJsonElement, int minLength, int maxLength, String name, @Nonnull BuilderParameters builderParameters) {
/* 25 */     setLength(minLength, maxLength);
/* 26 */     readJSON(requiredJsonElement, name, builderParameters);
/*    */   }
/*    */   
/*    */   protected void readJSON(JsonElement optionalJsonElement, int minLength, int maxLength, double[] defaultValue, String name, @Nonnull BuilderParameters builderParameters) {
/* 30 */     setLength(minLength, maxLength);
/* 31 */     readJSON(optionalJsonElement, () -> new BuilderExpressionStaticNumberArray(defaultValue), name, builderParameters);
/*    */   }
/*    */   
/*    */   protected void readJSON(JsonElement optionalJsonElement, int minLength, int maxLength, String[] defaultValue, String name, @Nonnull BuilderParameters builderParameters) {
/* 35 */     setLength(minLength, maxLength);
/* 36 */     readJSON(optionalJsonElement, () -> new BuilderExpressionStaticStringArray(defaultValue), name, builderParameters);
/*    */   }
/*    */   
/*    */   protected void readJSON(JsonElement optionalJsonElement, int minLength, int maxLength, boolean[] defaultValue, String name, @Nonnull BuilderParameters builderParameters) {
/* 40 */     setLength(minLength, maxLength);
/* 41 */     readJSON(optionalJsonElement, () -> new BuilderExpressionStaticBooleanArray(defaultValue), name, builderParameters);
/*    */   }
/*    */   
/*    */   protected void validateLength(int length) {
/* 45 */     if (length < this.minLength || length > this.maxLength) {
/* 46 */       StringBuilder errorString = new StringBuilder(100);
/* 47 */       errorString.append(this.name).append(": Invalid array size in array holder (Should be ");
/* 48 */       if (this.minLength == this.maxLength) {
/* 49 */         errorString.append(this.minLength);
/* 50 */       } else if (this.maxLength < Integer.MAX_VALUE) {
/* 51 */         errorString.append("between ").append(this.minLength).append(" and ").append(this.maxLength);
/*    */       } else {
/* 53 */         errorString.append("a minimum length of ").append(this.minLength);
/*    */       } 
/* 55 */       errorString.append(" but is ").append(length).append(')');
/* 56 */       throw new IllegalStateException(errorString.toString());
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void setLength(int minLength, int maxLength) {
/* 61 */     if (minLength > maxLength) throw new IllegalArgumentException("Illegal length for array in array holder specified"); 
/* 62 */     if (minLength < 0) throw new IllegalArgumentException("Illegal minimum length for array in array holder specified"); 
/* 63 */     this.minLength = minLength;
/* 64 */     this.maxLength = maxLength;
/*    */   }
/*    */   
/*    */   protected void setLength(int length) {
/* 68 */     setLength(length, length);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\ArrayHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */