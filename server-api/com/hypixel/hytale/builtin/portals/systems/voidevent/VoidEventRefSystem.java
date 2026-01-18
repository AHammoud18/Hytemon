/*    */ package com.hypixel.hytale.builtin.portals.systems.voidevent;
/*    */ import com.hypixel.hytale.builtin.ambience.resources.AmbienceResource;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.VoidEvent;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventConfig;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventStage;
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public final class VoidEventRefSystem extends RefSystem<EntityStore> {
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 20 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 21 */     if (!portalWorld.exists())
/*    */       return; 
/* 23 */     VoidEventConfig voidEventConfig = portalWorld.getVoidEventConfig();
/*    */     
/* 25 */     String forcedMusic = voidEventConfig.getMusicAmbienceFX();
/* 26 */     if (forcedMusic != null) {
/* 27 */       AmbienceResource ambienceResource = (AmbienceResource)store.getResource(AmbienceResource.getResourceType());
/* 28 */       ambienceResource.setForcedMusicAmbience(forcedMusic);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 34 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 35 */     if (!portalWorld.exists())
/*    */       return; 
/* 37 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 38 */     VoidEventConfig voidEventConfig = portalWorld.getVoidEventConfig();
/*    */     
/* 40 */     String forcedMusic = voidEventConfig.getMusicAmbienceFX();
/* 41 */     if (forcedMusic != null) {
/* 42 */       AmbienceResource ambienceResource = (AmbienceResource)store.getResource(AmbienceResource.getResourceType());
/* 43 */       ambienceResource.setForcedMusicAmbience(null);
/*    */     } 
/*    */     
/* 46 */     VoidEvent voidEvent = (VoidEvent)commandBuffer.getComponent(ref, VoidEvent.getComponentType());
/* 47 */     VoidEventStage activeStage = voidEvent.getActiveStage();
/* 48 */     if (activeStage != null) {
/* 49 */       VoidEventStagesSystem.stopStage(activeStage, world, store, commandBuffer);
/* 50 */       voidEvent.setActiveStage(null);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<EntityStore> getQuery() {
/* 57 */     return (Query<EntityStore>)VoidEvent.getComponentType();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\voidevent\VoidEventRefSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */