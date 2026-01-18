/*    */ package com.hypixel.hytale.server.core.modules.entityui;
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.modules.entityui.asset.CombatTextUIComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entityui.asset.CombatTextUIComponentAnimationEvent;
/*    */ import com.hypixel.hytale.server.core.modules.entityui.asset.CombatTextUIComponentOpacityAnimationEvent;
/*    */ import com.hypixel.hytale.server.core.modules.entityui.asset.CombatTextUIComponentPositionAnimationEvent;
/*    */ import com.hypixel.hytale.server.core.modules.entityui.asset.CombatTextUIComponentScaleAnimationEvent;
/*    */ import com.hypixel.hytale.server.core.modules.entityui.asset.EntityStatUIComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entityui.asset.EntityUIComponent;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ public class EntityUIModule extends JavaPlugin {
/* 22 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(EntityUIModule.class)
/* 23 */     .depends(new Class[] { EntityStatsModule.class
/* 24 */       }).build();
/*    */   private static EntityUIModule instance;
/*    */   private ComponentType<EntityStore, UIComponentList> uiComponentListType;
/*    */   
/*    */   public static EntityUIModule get() {
/* 29 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityUIModule(@Nonnull JavaPluginInit init) {
/* 35 */     super(init);
/* 36 */     instance = this;
/*    */   }
/*    */   
/*    */   public ComponentType<EntityStore, UIComponentList> getUIComponentListType() {
/* 40 */     return this.uiComponentListType;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 45 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(EntityUIComponent.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new EntityUIComponent[x$0]))
/* 46 */         .setPath("Entity/UI"))
/* 47 */         .setCodec((AssetCodec)EntityUIComponent.CODEC))
/* 48 */         .setKeyFunction(EntityUIComponent::getId))
/* 49 */         .setPacketGenerator((AssetPacketGenerator)new EntityUIComponentPacketGenerator())
/* 50 */         .setReplaceOnRemove(EntityUIComponent::getUnknownFor))
/* 51 */         .loadsAfter(new Class[] { EntityStatType.class
/* 52 */           })).build());
/*    */     
/* 54 */     getCodecRegistry(EntityUIComponent.CODEC).register("EntityStat", EntityStatUIComponent.class, EntityStatUIComponent.CODEC);
/* 55 */     getCodecRegistry(EntityUIComponent.CODEC).register("CombatText", CombatTextUIComponent.class, CombatTextUIComponent.CODEC);
/*    */     
/* 57 */     getCodecRegistry(CombatTextUIComponentAnimationEvent.CODEC).register("Scale", CombatTextUIComponentScaleAnimationEvent.class, CombatTextUIComponentScaleAnimationEvent.CODEC);
/* 58 */     getCodecRegistry(CombatTextUIComponentAnimationEvent.CODEC).register("Position", CombatTextUIComponentPositionAnimationEvent.class, CombatTextUIComponentPositionAnimationEvent.CODEC);
/* 59 */     getCodecRegistry(CombatTextUIComponentAnimationEvent.CODEC).register("Opacity", CombatTextUIComponentOpacityAnimationEvent.class, CombatTextUIComponentOpacityAnimationEvent.CODEC);
/*    */     
/* 61 */     this.uiComponentListType = getEntityStoreRegistry().registerComponent(UIComponentList.class, "UIComponentList", UIComponentList.CODEC);
/* 62 */     ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType = EntityModule.get().getVisibleComponentType();
/* 63 */     getEntityStoreRegistry().registerSystem((ISystem)new UIComponentSystems.Setup(this.uiComponentListType));
/* 64 */     getEntityStoreRegistry().registerSystem((ISystem)new UIComponentSystems.Update(visibleComponentType, this.uiComponentListType));
/* 65 */     getEntityStoreRegistry().registerSystem((ISystem)new UIComponentSystems.Remove(visibleComponentType, this.uiComponentListType));
/*    */     
/* 67 */     getEventRegistry().register(LoadedAssetsEvent.class, EntityUIComponent.class, this::onLoadedAssetsEvent);
/*    */   }
/*    */   
/*    */   private void onLoadedAssetsEvent(LoadedAssetsEvent<String, EntityUIComponent, IndexedLookupTableAssetMap<String, EntityUIComponent>> event) {
/* 71 */     Universe.get().getWorlds().forEach((s, world) -> world.execute(()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\EntityUIModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */