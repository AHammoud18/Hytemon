/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityEffectExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final EntityEffectExistsValidator DEFAULT_INSTANCE = new EntityEffectExistsValidator();
/*    */ 
/*    */   
/*    */   private EntityEffectExistsValidator() {}
/*    */ 
/*    */   
/*    */   private EntityEffectExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "EntityEffect";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String effect) {
/* 32 */     return (EntityEffect.getAssetMap().getAsset(effect) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String effect, String attributeName) {
/* 38 */     return "The entity effect with the name \"" + effect + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return EntityEffect.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EntityEffectExistsValidator required() {
/* 53 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static EntityEffectExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 58 */     return new EntityEffectExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\EntityEffectExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */