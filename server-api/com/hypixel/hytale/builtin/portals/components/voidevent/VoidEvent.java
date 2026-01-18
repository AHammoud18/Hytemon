/*    */ package com.hypixel.hytale.builtin.portals.components.voidevent;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.PortalsPlugin;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventConfig;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventStage;
/*    */ import com.hypixel.hytale.builtin.portals.integrations.PortalGameplayConfig;
/*    */ import com.hypixel.hytale.builtin.portals.utils.spatial.SpatialHashGrid;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class VoidEvent
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, VoidEvent> getComponentType() {
/* 18 */     return PortalsPlugin.getInstance().getVoidEventComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   public static final double MIN_BLOCKS_BETWEEN_SPAWNERS = 62.0D;
/* 23 */   private SpatialHashGrid<Ref<EntityStore>> voidSpawners = new SpatialHashGrid(62.0D);
/*    */   private VoidEventStage activeStage;
/*    */   
/*    */   public VoidEventConfig getConfig(World world) {
/* 27 */     PortalGameplayConfig portalConfig = (PortalGameplayConfig)world.getGameplayConfig().getPluginConfig().get(PortalGameplayConfig.class);
/* 28 */     return portalConfig.getVoidEvent();
/*    */   }
/*    */   
/*    */   public SpatialHashGrid<Ref<EntityStore>> getVoidSpawners() {
/* 32 */     return this.voidSpawners;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public VoidEventStage getActiveStage() {
/* 37 */     return this.activeStage;
/*    */   }
/*    */   
/*    */   public void setActiveStage(@Nullable VoidEventStage activeStage) {
/* 41 */     this.activeStage = activeStage;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Component<EntityStore> clone() {
/* 47 */     VoidEvent clone = new VoidEvent();
/* 48 */     clone.voidSpawners = this.voidSpawners;
/* 49 */     clone.activeStage = this.activeStage;
/* 50 */     return clone;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\components\voidevent\VoidEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */