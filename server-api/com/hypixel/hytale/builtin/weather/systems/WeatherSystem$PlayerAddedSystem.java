/*    */ package com.hypixel.hytale.builtin.weather.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.weather.components.WeatherTracker;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerAddedSystem
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/* 66 */   private static final ComponentType<EntityStore, PlayerRef> PLAYER_REF_COMPONENT_TYPE = PlayerRef.getComponentType();
/* 67 */   private static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/* 68 */   private static final ComponentType<EntityStore, WeatherTracker> WEATHER_TRACKER_COMPONENT_TYPE = WeatherTracker.getComponentType();
/* 69 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Archetype.of(new ComponentType[] { PLAYER_REF_COMPONENT_TYPE, TRANSFORM_COMPONENT_TYPE });
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 73 */     return QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 78 */     holder.ensureComponent(WEATHER_TRACKER_COMPONENT_TYPE);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/* 84 */     WeatherTracker weatherTrackerComponent = (WeatherTracker)holder.ensureAndGetComponent(WEATHER_TRACKER_COMPONENT_TYPE);
/* 85 */     weatherTrackerComponent.clear();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\weather\systems\WeatherSystem$PlayerAddedSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */