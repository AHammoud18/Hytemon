/*    */ package com.hypixel.hytale.server.core.modules.prefabspawner;
/*    */ 
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.modules.prefabspawner.commands.PrefabSpawnerCommand;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.BlockStateModule;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabSpawnerModule
/*    */   extends JavaPlugin
/*    */ {
/*    */   @Nonnull
/* 20 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(PrefabSpawnerModule.class)
/* 21 */     .depends(new Class[] { BlockStateModule.class
/* 22 */       }).build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabSpawnerModule(@Nonnull JavaPluginInit init) {
/* 30 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 35 */     getBlockStateRegistry().registerBlockState(PrefabSpawnerState.class, "prefabspawner", PrefabSpawnerState.CODEC);
/* 36 */     getCommandRegistry().registerCommand((AbstractCommand)new PrefabSpawnerCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\prefabspawner\PrefabSpawnerModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */