/*    */ package com.hypixel.hytale.builtin.adventure.npcshop.npc;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.shop.ShopAsset;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShopExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final ShopExistsValidator DEFAULT_INSTANCE = new ShopExistsValidator();
/*    */ 
/*    */   
/*    */   private ShopExistsValidator() {}
/*    */ 
/*    */   
/*    */   private ShopExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "Shop";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String marker) {
/* 32 */     return (ShopAsset.getAssetMap().getAsset(marker) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String marker, String attributeName) {
/* 38 */     return "The shop asset with the name \"" + marker + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return ShopAsset.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ShopExistsValidator required() {
/* 52 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ShopExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 57 */     return new ShopExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcshop\npc\ShopExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */