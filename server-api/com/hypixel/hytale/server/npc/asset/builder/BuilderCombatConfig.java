/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.server.npc.config.balancing.BalanceAsset;
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderCombatConfig
/*    */   extends BuilderCodecObjectHelper<String>
/*    */ {
/*    */   private boolean inline;
/*    */   
/*    */   public BuilderCombatConfig(Codec<String> codec, Validator<String> validator) {
/* 23 */     super(String.class, codec, validator);
/*    */   }
/*    */ 
/*    */   
/*    */   public String build() {
/* 28 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void readConfig(@Nonnull JsonElement data, @Nonnull ExtraInfo extraInfo) {
/* 33 */     this.inline = data.isJsonObject();
/* 34 */     super.readConfig(data, extraInfo);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String build(@Nonnull ExecutionContext context) {
/* 39 */     String override = context.getCombatConfig();
/* 40 */     if (override != null) return override; 
/* 41 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean validate(String configName, NPCLoadTimeValidationHelper loadTimeValidationHelper, @Nonnull ExecutionContext context, @Nonnull List<String> errors) {
/* 45 */     String override = context.getCombatConfig();
/*    */ 
/*    */     
/* 48 */     boolean success = true;
/*    */ 
/*    */     
/* 51 */     if (override != null && BalanceAsset.getAssetMap().getAsset(override) == null) {
/* 52 */       errors.add(String.format("%s: CombatConfig refers to a non-existent balancing file: %s", new Object[] { configName, override }));
/* 53 */       success = false;
/*    */     } 
/*    */     
/* 56 */     return success;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderCombatConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */