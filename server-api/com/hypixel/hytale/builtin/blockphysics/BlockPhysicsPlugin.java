/*    */ package com.hypixel.hytale.builtin.blockphysics;
/*    */ import com.hypixel.hytale.common.util.FormatUtil;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Options;
/*    */ import com.hypixel.hytale.server.core.asset.LoadAssetEvent;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.ValidationOption;
/*    */ import java.util.List;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockPhysicsPlugin extends JavaPlugin {
/*    */   public BlockPhysicsPlugin(@Nonnull JavaPluginInit init) {
/* 16 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 21 */     getEventRegistry().register(LoadAssetEvent.class, BlockPhysicsPlugin::validatePrefabs);
/*    */     
/* 23 */     getChunkStoreRegistry().registerSystem((ISystem)new BlockPhysicsSystems.Ticking());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void validatePrefabs(@Nonnull LoadAssetEvent event) {
/* 28 */     if (!Options.getOptionSet().has(Options.VALIDATE_PREFABS) || event.isShouldShutdown())
/* 29 */       return;  long start = System.nanoTime();
/*    */     
/* 31 */     List<ValidationOption> validatePrefabs = Options.getOptionSet().valuesOf(Options.VALIDATE_PREFABS);
/* 32 */     List<String> failedToValidatePrefabs = PrefabBufferValidator.validateAllPrefabs(validatePrefabs);
/* 33 */     if (!failedToValidatePrefabs.isEmpty()) {
/* 34 */       HytaleLogger.getLogger().at(Level.SEVERE).log("One or more prefabs failed to validate, Exiting!\n" + String.join("\n", (Iterable)failedToValidatePrefabs));
/* 35 */       event.failed(true, "failed to validate prefabs");
/*    */     } 
/*    */     
/* 38 */     HytaleLogger.getLogger().at(Level.INFO).log("Validate prefabs phase completed! Boot time %s, Took %s", FormatUtil.nanosToString(System.nanoTime() - event.getBootStart()), FormatUtil.nanosToString(System.nanoTime() - start));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockphysics\BlockPhysicsPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */