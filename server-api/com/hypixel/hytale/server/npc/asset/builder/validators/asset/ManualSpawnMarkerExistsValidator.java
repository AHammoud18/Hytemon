/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ManualSpawnMarkerExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final ManualSpawnMarkerExistsValidator DEFAULT_INSTANCE = new ManualSpawnMarkerExistsValidator();
/*    */ 
/*    */   
/*    */   private ManualSpawnMarkerExistsValidator() {}
/*    */ 
/*    */   
/*    */   private ManualSpawnMarkerExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "SpawnMarker";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String marker) {
/* 32 */     SpawnMarker spawner = (SpawnMarker)SpawnMarker.getAssetMap().getAsset(marker);
/* 33 */     return (spawner != null && spawner.isManualTrigger());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String marker, String attributeName) {
/* 39 */     return "The spawn marker with the name \"" + marker + "\" does not exist or is not a manual spawn marker for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 45 */     return SpawnMarker.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ManualSpawnMarkerExistsValidator required() {
/* 53 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ManualSpawnMarkerExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 58 */     return new ManualSpawnMarkerExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\ManualSpawnMarkerExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */