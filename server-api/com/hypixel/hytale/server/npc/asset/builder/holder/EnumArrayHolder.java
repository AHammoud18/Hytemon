/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.EnumArrayValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnumArrayHolder<E extends Enum<E>>
/*    */   extends ArrayHolder
/*    */ {
/*    */   private Class<E> clazz;
/*    */   private E[] enumConstants;
/*    */   private EnumArrayValidator validator;
/*    */   private E[] value;
/*    */   
/*    */   public EnumArrayHolder() {
/* 26 */     super(ValueType.STRING_ARRAY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 31 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, Class<E> clazz, EnumArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 35 */     this.clazz = clazz;
/* 36 */     this.enumConstants = clazz.getEnumConstants();
/* 37 */     this.validator = validator;
/* 38 */     readJSON(requiredJsonElement, 0, 2147483647, name, builderParameters);
/* 39 */     if (isStatic()) resolve(this.expression.getStringArray(null)); 
/*    */   }
/*    */   
/*    */   public E[] get(ExecutionContext executionContext) {
/* 43 */     E[] value = rawGet(executionContext);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 48 */     return value;
/*    */   }
/*    */   
/*    */   public E[] rawGet(ExecutionContext executionContext) {
/* 52 */     if (!isStatic()) {
/* 53 */       resolve(this.expression.getStringArray(executionContext));
/*    */     }
/* 55 */     return this.value;
/*    */   }
/*    */   
/*    */   public void resolve(String[] value) {
/* 59 */     this.value = (E[])BuilderBase.stringsToEnumArray(value, this.clazz, (Enum[])this.enumConstants, getName());
/* 60 */     if (this.validator != null && !this.validator.test((Enum[])this.value, this.clazz))
/* 61 */       throw new IllegalStateException(this.validator.errorMessage(this.name, this.value)); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\EnumArrayHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */