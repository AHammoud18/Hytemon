/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class RootInteractionValidator
/*    */   extends AssetValidator
/*    */ {
/*    */   private RootInteractionValidator() {}
/*    */   
/*    */   private RootInteractionValidator(EnumSet<AssetValidator.Config> config) {
/* 15 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 21 */     return "Interaction";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String value) {
/* 26 */     return (RootInteraction.getAssetMap().getAsset(value) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String value, String attribute) {
/* 32 */     return "Interaction \"" + value + "\" does not exist for attribute \"" + attribute + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 38 */     return RootInteraction.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static RootInteractionValidator required() {
/* 47 */     return new RootInteractionValidator();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RootInteractionValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 52 */     return new RootInteractionValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\RootInteractionValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */