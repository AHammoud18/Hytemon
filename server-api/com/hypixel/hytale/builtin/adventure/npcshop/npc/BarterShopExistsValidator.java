/*    */ package com.hypixel.hytale.builtin.adventure.npcshop.npc;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.shop.barter.BarterShopAsset;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BarterShopExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 13 */   private static final BarterShopExistsValidator DEFAULT_INSTANCE = new BarterShopExistsValidator();
/*    */ 
/*    */   
/*    */   private BarterShopExistsValidator() {}
/*    */ 
/*    */   
/*    */   private BarterShopExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 20 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 26 */     return "BarterShop";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String marker) {
/* 31 */     return (BarterShopAsset.getAssetMap().getAsset(marker) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String marker, String attributeName) {
/* 37 */     return "The barter shop asset with the name \"" + marker + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 43 */     return BarterShopAsset.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static BarterShopExistsValidator required() {
/* 51 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BarterShopExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 56 */     return new BarterShopExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcshop\npc\BarterShopExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */