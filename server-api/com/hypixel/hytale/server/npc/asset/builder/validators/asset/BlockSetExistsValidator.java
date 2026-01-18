/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSetExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 13 */   private static final BlockSetExistsValidator DEFAULT_INSTANCE = new BlockSetExistsValidator();
/*    */ 
/*    */   
/*    */   private BlockSetExistsValidator() {}
/*    */ 
/*    */   
/*    */   private BlockSetExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 20 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 26 */     return "BlockSet";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String blockSet) {
/* 31 */     return (BlockSet.getAssetMap().getAsset(blockSet) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String blockSet, String attribute) {
/* 37 */     return "The block set with the name \"" + blockSet + "\" does not exist in attribute \"" + attribute + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 43 */     return BlockSet.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static BlockSetExistsValidator required() {
/* 51 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BlockSetExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 56 */     return new BlockSetExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\BlockSetExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */