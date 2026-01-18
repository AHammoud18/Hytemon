/*    */ package com.hypixel.hytale.server.core.modules.entity.stamina;
/*    */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class StaminaModule extends JavaPlugin {
/* 17 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(StaminaModule.class)
/* 18 */     .depends(new Class[] { EntityModule.class
/* 19 */       }).depends(new Class[] { EntityStatsModule.class
/* 20 */       }).build();
/*    */   
/*    */   private static StaminaModule instance;
/*    */   
/*    */   private ResourceType<EntityStore, SprintStaminaRegenDelay> sprintRegenDelayResourceType;
/*    */   
/*    */   public StaminaModule(@Nonnull JavaPluginInit init) {
/* 27 */     super(init);
/* 28 */     instance = this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 33 */     this.sprintRegenDelayResourceType = getEntityStoreRegistry().registerResource(SprintStaminaRegenDelay.class, SprintStaminaRegenDelay::new);
/*    */     
/* 35 */     getEntityStoreRegistry().registerSystem((ISystem)new StaminaSystems.SprintStaminaEffectSystem());
/*    */ 
/*    */     
/* 38 */     getCodecRegistry(GameplayConfig.PLUGIN_CODEC).register(StaminaGameplayConfig.class, "Stamina", (Codec)StaminaGameplayConfig.CODEC);
/* 39 */     getEventRegistry().register(LoadedAssetsEvent.class, GameplayConfig.class, StaminaModule::onGameplayConfigsLoaded);
/*    */   }
/*    */   
/*    */   public ResourceType<EntityStore, SprintStaminaRegenDelay> getSprintRegenDelayResourceType() {
/* 43 */     return this.sprintRegenDelayResourceType;
/*    */   }
/*    */   
/*    */   protected static void onGameplayConfigsLoaded(LoadedAssetsEvent<String, GameplayConfig, AssetMap<String, GameplayConfig>> event) {
/* 47 */     SprintStaminaRegenDelay.invalidateResources();
/*    */   }
/*    */   
/*    */   public static StaminaModule get() {
/* 51 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\stamina\StaminaModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */