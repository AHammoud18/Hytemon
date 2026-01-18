/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import com.hypixel.hytale.server.npc.config.ItemAttitudeGroup;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemAttitudeGroupExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final ItemAttitudeGroupExistsValidator DEFAULT_INSTANCE = new ItemAttitudeGroupExistsValidator();
/*    */ 
/*    */   
/*    */   private ItemAttitudeGroupExistsValidator() {}
/*    */ 
/*    */   
/*    */   private ItemAttitudeGroupExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "ItemAttitudeGroup";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String attitudeGroup) {
/* 32 */     return (ItemAttitudeGroup.getAssetMap().getAsset(attitudeGroup) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String attitudeGroup, String attributeName) {
/* 38 */     return "The item attitude group with the name \"" + attitudeGroup + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return ItemAttitudeGroup.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemAttitudeGroupExistsValidator required() {
/* 53 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ItemAttitudeGroupExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 58 */     return new ItemAttitudeGroupExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\ItemAttitudeGroupExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */