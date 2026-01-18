/*    */ package com.hypixel.hytale.server.npc.asset.builder.holder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderBase;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderParameters;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetArrayHolder
/*    */   extends StringArrayHolder
/*    */ {
/*    */   protected AssetValidator assetValidator;
/*    */   
/*    */   public void readJSON(@Nonnull JsonElement requiredJsonElement, int minLength, int maxLength, AssetValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 21 */     readJSON(requiredJsonElement, minLength, maxLength, name, builderParameters);
/* 22 */     this.assetValidator = validator;
/*    */   }
/*    */   
/*    */   public void readJSON(JsonElement optionalJsonElement, int minLength, int maxLength, String[] defaultValue, AssetValidator validator, String name, @Nonnull BuilderParameters builderParameters) {
/* 26 */     readJSON(optionalJsonElement, minLength, maxLength, defaultValue, name, builderParameters);
/* 27 */     this.assetValidator = validator;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String[] get(ExecutionContext executionContext) {
/* 33 */     String[] value = rawGet(executionContext);
/*    */     
/* 35 */     validateRelations(executionContext, value);
/*    */ 
/*    */ 
/*    */     
/* 39 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String[] rawGet(ExecutionContext executionContext) {
/* 45 */     String[] value = this.expression.getStringArray(executionContext);
/* 46 */     if (this.assetValidator != null) {
/* 47 */       BuilderBase.validateAssetList(value, this.assetValidator, this.name, true);
/*    */     }
/* 49 */     return value;
/*    */   }
/*    */   
/*    */   public void staticValidate() {
/* 53 */     if (this.assetValidator == null || !this.expression.isStatic())
/*    */       return; 
/* 55 */     BuilderBase.validateAssetList(this.expression.getStringArray(null), this.assetValidator, this.name, true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\holder\AssetArrayHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */