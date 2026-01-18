/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeaconSpawnExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final BeaconSpawnExistsValidator DEFAULT_INSTANCE = new BeaconSpawnExistsValidator();
/*    */ 
/*    */   
/*    */   private BeaconSpawnExistsValidator() {}
/*    */ 
/*    */   
/*    */   private BeaconSpawnExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "BeaconNPCSpawn";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String beacon) {
/* 32 */     return (BeaconNPCSpawn.getAssetMap().getAsset(beacon) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String beacon, String attributeName) {
/* 38 */     return "The beacon spawn with the name \"" + beacon + "\" does not exist for attribute \"" + attributeName + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return BeaconNPCSpawn.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static BeaconSpawnExistsValidator required() {
/* 53 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BeaconSpawnExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 58 */     return new BeaconSpawnExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\BeaconSpawnExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */