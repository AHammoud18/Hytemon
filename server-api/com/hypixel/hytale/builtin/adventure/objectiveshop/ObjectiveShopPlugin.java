/*    */ package com.hypixel.hytale.builtin.adventure.objectiveshop;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.shop.ShopAsset;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceInteraction;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceRequirement;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ObjectiveShopPlugin extends JavaPlugin {
/*    */   protected static ObjectiveShopPlugin instance;
/*    */   
/*    */   public static ObjectiveShopPlugin get() {
/* 17 */     return instance;
/*    */   }
/*    */   
/*    */   public ObjectiveShopPlugin(@Nonnull JavaPluginInit init) {
/* 21 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 26 */     instance = this;
/*    */     
/* 28 */     ChoiceInteraction.CODEC.register("StartObjective", StartObjectiveInteraction.class, (Codec)StartObjectiveInteraction.CODEC);
/*    */     
/* 30 */     ChoiceRequirement.CODEC.register("CanStartObjective", CanStartObjectiveRequirement.class, (Codec)CanStartObjectiveRequirement.CODEC);
/*    */     
/* 32 */     AssetRegistry.getAssetStore(ShopAsset.class).injectLoadsAfter(ObjectiveAsset.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectiveshop\ObjectiveShopPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */