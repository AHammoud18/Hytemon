/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FlockAssetExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final FlockAssetExistsValidator DEFAULT_INSTANCE = new FlockAssetExistsValidator();
/*    */ 
/*    */   
/*    */   private FlockAssetExistsValidator() {}
/*    */ 
/*    */   
/*    */   private FlockAssetExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "FlockAsset";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String flockAsset) {
/* 32 */     return (FlockAsset.getAssetMap().getAsset(flockAsset) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String flockAsset, String attribute) {
/* 38 */     return "The flock asset with the name \"" + flockAsset + "\" does not exist in attribute \"" + attribute + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return FlockAsset.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static FlockAssetExistsValidator required() {
/* 52 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static FlockAssetExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 57 */     return new FlockAssetExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\FlockAssetExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */