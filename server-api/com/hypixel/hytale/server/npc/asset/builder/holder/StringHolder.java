/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticString;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringHolder
/*    */   extends StringHolderBase
/*    */ {
/*    */   protected StringValidator stringValidator;
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 20 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, StringValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 24 */     readJSON(requiredJsonElement, name, builderParameters);
/* 25 */     this.stringValidator = validator;
/* 26 */     if (isStatic()) validate(this.expression.getString(null)); 
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, String defaultValue, StringValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 30 */     readJSON(optionalJsonElement, () -> new BuilderExpressionStaticString(defaultValue), name, builderParameters);
/* 31 */     this.stringValidator = validator;
/* 32 */     if (isStatic()) validate(this.expression.getString(null)); 
/*    */   }
/*    */   
/*    */   public String get(ExecutionContext executionContext) {
/* 36 */     String value = rawGet(executionContext);
/* 37 */     validateRelations(executionContext, value);
/*    */ 
/*    */ 
/*    */     
/* 41 */     return value;
/*    */   }
/*    */   
/*    */   public String rawGet(ExecutionContext executionContext) {
/* 45 */     String value = this.expression.getString(executionContext);
/* 46 */     if (!isStatic()) validate(value); 
/* 47 */     return value;
/*    */   }
/*    */   
/*    */   public void validate(String value) {
/* 51 */     if (this.stringValidator != null && !this.stringValidator.test(value))
/* 52 */       throw new IllegalStateException(this.stringValidator.errorMessage(value, this.name)); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\StringHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */