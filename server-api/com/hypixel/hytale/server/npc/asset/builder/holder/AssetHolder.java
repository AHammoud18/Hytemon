/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpression;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.expression.BuilderExpressionStaticString;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetHolder
/*    */   extends StringHolderBase
/*    */ {
/*    */   protected AssetValidator assetValidator;
/*    */   
/*    */   public void validate(ExecutionContext context) {
/* 21 */     get(context);
/*    */   }
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, AssetValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 25 */     readJSON(requiredJsonElement, name, builderParameters);
/* 26 */     this.assetValidator = validator;
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, String defaultValue, AssetValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 30 */     readJSON(optionalJsonElement, () -> new BuilderExpressionStaticString(defaultValue), name, builderParameters);
/* 31 */     this.assetValidator = validator;
/*    */   }
/*    */   
/*    */   public String get(ExecutionContext executionContext) {
/* 35 */     String value = rawGet(executionContext);
/* 36 */     validateRelations(executionContext, value);
/*    */ 
/*    */ 
/*    */     
/* 40 */     return value;
/*    */   }
/*    */   
/*    */   public String rawGet(ExecutionContext executionContext) {
/* 44 */     String value = this.expression.getString(executionContext);
/* 45 */     if (this.assetValidator != null) {
/* 46 */       BuilderBase.validateAsset(value, this.assetValidator, this.name, true);
/*    */     }
/* 48 */     return value;
/*    */   }
/*    */   
/*    */   public void staticValidate() {
/* 52 */     if (this.assetValidator == null || !isStatic())
/*    */       return; 
/* 54 */     BuilderBase.validateAsset(this.expression.getString(null), this.assetValidator, this.name, true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\AssetHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */