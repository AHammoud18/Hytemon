/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnumSetHolder<E extends Enum<E>>
/*    */   extends ArrayHolder
/*    */ {
/*    */   private Class<E> clazz;
/*    */   private E[] enumConstants;
/*    */   private EnumSet<E> value;
/*    */   
/*    */   public EnumSetHolder() {
/* 23 */     super(ValueType.STRING_ARRAY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 28 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, Class<E> clazz, String name, @Nonnull BuilderParameters builderParameters) {
/* 32 */     this.clazz = clazz;
/* 33 */     this.enumConstants = clazz.getEnumConstants();
/* 34 */     readJSON(requiredJsonElement, 0, 2147483647, name, builderParameters);
/* 35 */     if (isStatic()) {
/* 36 */       this.value = BuilderBase.stringsToEnumSet(this.expression.getStringArray(null), clazz, (Enum[])this.enumConstants, getName());
/*    */     }
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, @Nonnull EnumSet<E> defaultValue, Class<E> clazz, String name, @Nonnull BuilderParameters builderParameters) {
/* 41 */     this.clazz = clazz;
/* 42 */     this.enumConstants = clazz.getEnumConstants();
/* 43 */     readJSON(optionalJsonElement, 0, 2147483647, BuilderBase.enumSetToStrings(defaultValue), name, builderParameters);
/* 44 */     if (isStatic()) {
/* 45 */       this.value = BuilderBase.stringsToEnumSet(this.expression.getStringArray(null), clazz, (Enum[])this.enumConstants, getName());
/*    */     }
/*    */   }
/*    */   
/*    */   public EnumSet<E> get(ExecutionContext executionContext) {
/* 50 */     EnumSet<E> value = rawGet(executionContext);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 55 */     return value;
/*    */   }
/*    */   
/*    */   public EnumSet<E> rawGet(ExecutionContext executionContext) {
/* 59 */     if (!isStatic()) {
/* 60 */       this.value = BuilderBase.stringsToEnumSet(this.expression.getStringArray(executionContext), this.clazz, (Enum[])this.enumConstants, getName());
/*    */     }
/* 62 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\EnumSetHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */