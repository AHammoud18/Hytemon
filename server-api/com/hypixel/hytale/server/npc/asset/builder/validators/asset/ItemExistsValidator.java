/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import com.hypixel.hytale.server.npc.util.InventoryHelper;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ItemExistsValidator
/*    */   extends AssetValidator {
/* 12 */   private static final ItemExistsValidator DEFAULT_INSTANCE = new ItemExistsValidator();
/*    */   
/*    */   public static final String DROPLIST_PREFIX = "Droplist:";
/*    */   
/*    */   private boolean requireBlock;
/*    */   
/*    */   private boolean allowDroplist;
/*    */ 
/*    */   
/*    */   private ItemExistsValidator() {}
/*    */ 
/*    */   
/*    */   private ItemExistsValidator(boolean requireBlock, boolean allowDroplist) {
/* 25 */     this.requireBlock = requireBlock;
/* 26 */     this.allowDroplist = allowDroplist;
/*    */   }
/*    */   
/*    */   private ItemExistsValidator(EnumSet<AssetValidator.Config> config, boolean requireBlock, boolean allowDroplist) {
/* 30 */     super(config);
/* 31 */     this.requireBlock = requireBlock;
/* 32 */     this.allowDroplist = allowDroplist;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 38 */     return "Item";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String item) {
/* 43 */     if (item == null || item.isEmpty()) return false;
/*    */     
/* 45 */     if (item.startsWith("Droplist:")) {
/* 46 */       return (ItemDropList.getAssetMap().getAsset(item.substring("Droplist:".length())) != null);
/*    */     }
/*    */     
/* 49 */     return this.requireBlock ? InventoryHelper.itemKeyIsBlockType(item) : InventoryHelper.itemKeyExists(item);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String item, String attributeName) {
/* 55 */     return "The item " + (this.allowDroplist ? "or droplist " : "") + "with the name \"" + item + "\" does not exist" + (this.requireBlock ? " or is not a block" : "") + " for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 61 */     return Item.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemExistsValidator required() {
/* 69 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ItemExistsValidator requireBlock() {
/* 74 */     return new ItemExistsValidator(true, false);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ItemExistsValidator orDroplist() {
/* 79 */     return new ItemExistsValidator(false, true);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ItemExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 84 */     return new ItemExistsValidator(config, false, false);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ItemExistsValidator orDroplistWithConfig(EnumSet<AssetValidator.Config> config) {
/* 89 */     return new ItemExistsValidator(config, false, true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\ItemExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */