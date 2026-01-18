/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.BooleanArrayValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ValueType;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BooleanArrayHolder
/*    */   extends ArrayHolder
/*    */ {
/*    */   protected BooleanArrayValidator booleanArrayValidator;
/*    */   
/*    */   public BooleanArrayHolder() {
/* 21 */     super(ValueType.BOOLEAN_ARRAY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 26 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, int minLength, int maxLength, BooleanArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 30 */     readJSON(requiredJsonElement, minLength, maxLength, name, builderParameters);
/* 31 */     this.booleanArrayValidator = validator;
/* 32 */     if (isStatic()) validate(this.expression.getBooleanArray(null)); 
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, int minLength, int maxLength, boolean[] defaultValue, BooleanArrayValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 36 */     readJSON(optionalJsonElement, minLength, maxLength, defaultValue, name, builderParameters);
/* 37 */     this.booleanArrayValidator = validator;
/* 38 */     if (isStatic()) validate(this.expression.getBooleanArray(null)); 
/*    */   }
/*    */   
/*    */   public boolean[] get(ExecutionContext executionContext) {
/* 42 */     boolean[] value = rawGet(executionContext);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     return value;
/*    */   }
/*    */   
/*    */   public boolean[] rawGet(ExecutionContext executionContext) {
/* 51 */     boolean[] value = this.expression.getBooleanArray(executionContext);
/* 52 */     if (!isStatic()) validate(value); 
/* 53 */     return value;
/*    */   }
/*    */   
/*    */   public void validate(@Nullable boolean[] value) {
/* 57 */     if (value != null) validateLength(value.length); 
/* 58 */     if (this.booleanArrayValidator != null && !this.booleanArrayValidator.test(value))
/* 59 */       throw new IllegalStateException(this.booleanArrayValidator.errorMessage(this.name, value)); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\BooleanArrayHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */