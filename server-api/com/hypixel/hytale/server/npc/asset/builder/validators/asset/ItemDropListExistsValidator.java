/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import com.hypixel.hytale.server.npc.util.InventoryHelper;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemDropListExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final ItemDropListExistsValidator DEFAULT_INSTANCE = new ItemDropListExistsValidator();
/*    */ 
/*    */   
/*    */   private ItemDropListExistsValidator() {}
/*    */ 
/*    */   
/*    */   private ItemDropListExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "ItemDropList";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String value) {
/* 32 */     return InventoryHelper.itemDropListKeyExists(value);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String value, String attribute) {
/* 38 */     return "The item drop list with the name \"" + value + "\" does not exist for attribute \"" + attribute + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return ItemDropList.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemDropListExistsValidator required() {
/* 52 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ItemDropListExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 57 */     return new ItemDropListExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\ItemDropListExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */