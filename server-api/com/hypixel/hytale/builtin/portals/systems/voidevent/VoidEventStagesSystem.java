/*    */ package com.hypixel.hytale.builtin.portals.systems.voidevent;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.VoidEvent;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventConfig;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventStage;
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.DelayedEntitySystem;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.List;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class VoidEventStagesSystem
/*    */   extends DelayedEntitySystem<EntityStore> {
/*    */   public VoidEventStagesSystem() {
/* 25 */     super(1.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 30 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 31 */     if (!portalWorld.exists())
/*    */       return; 
/* 33 */     Ref<EntityStore> eventRef = portalWorld.getVoidEventRef();
/* 34 */     VoidEvent voidEvent = (eventRef == null) ? null : (VoidEvent)commandBuffer.getComponent(eventRef, VoidEvent.getComponentType());
/* 35 */     if (voidEvent == null)
/*    */       return; 
/* 37 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 38 */     VoidEventConfig voidEventConfig = portalWorld.getVoidEventConfig();
/*    */     
/* 40 */     double elapsedSecondsInPortal = portalWorld.getElapsedSeconds(world);
/* 41 */     int timeLimitSeconds = portalWorld.getTimeLimitSeconds();
/* 42 */     int shouldStartAfter = voidEventConfig.getShouldStartAfterSeconds(timeLimitSeconds);
/* 43 */     int elapsedSecondsInEvent = (int)Math.max(0.0D, elapsedSecondsInPortal - shouldStartAfter);
/*    */     
/* 45 */     VoidEventStage currentStage = voidEvent.getActiveStage();
/* 46 */     VoidEventStage desiredStage = computeAppropriateStage(voidEventConfig, elapsedSecondsInEvent);
/*    */     
/* 48 */     if (currentStage == desiredStage)
/*    */       return; 
/* 50 */     if (currentStage != null) {
/* 51 */       stopStage(currentStage, world, store, commandBuffer);
/*    */     }
/* 53 */     if (desiredStage != null) {
/* 54 */       startStage(desiredStage, world, store, commandBuffer);
/*    */     }
/* 56 */     voidEvent.setActiveStage(desiredStage);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private VoidEventStage computeAppropriateStage(VoidEventConfig config, int elapsedSeconds) {
/* 61 */     List<VoidEventStage> stages = config.getStagesSortedByStartTime();
/* 62 */     for (int i = stages.size() - 1; i >= 0; i--) {
/* 63 */       VoidEventStage stage = stages.get(i);
/* 64 */       if (elapsedSeconds > stage.getSecondsInto()) {
/* 65 */         return stage;
/*    */       }
/*    */     } 
/* 68 */     return null;
/*    */   }
/*    */   
/*    */   public static void startStage(VoidEventStage stage, World world, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer) {
/* 72 */     HytaleLogger.getLogger().at(Level.INFO).log("Starting stage SecondsInto=" + stage.getSecondsInto() + " in portal void event");
/*    */     
/* 74 */     String forcedWeatherId = stage.getForcedWeatherId();
/* 75 */     if (forcedWeatherId != null) {
/* 76 */       WeatherResource weatherResource = (WeatherResource)store.getResource(WeatherResource.getResourceType());
/* 77 */       weatherResource.setForcedWeather(forcedWeatherId);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void stopStage(VoidEventStage stage, World world, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer) {
/* 82 */     HytaleLogger.getLogger().at(Level.INFO).log("Stopping stage SecondsInto=" + stage.getSecondsInto() + " in portal void event");
/* 83 */     String forcedWeatherId = stage.getForcedWeatherId();
/* 84 */     if (forcedWeatherId != null) {
/* 85 */       WeatherResource weatherResource = (WeatherResource)store.getResource(WeatherResource.getResourceType());
/* 86 */       weatherResource.setForcedWeather(null);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<EntityStore> getQuery() {
/* 93 */     return (Query<EntityStore>)VoidEvent.getComponentType();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\voidevent\VoidEventStagesSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */