/*    */ package com.hypixel.hytale.server.core.modules.time;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.StoreSystem;
/*    */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
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
/*    */ public class WorldTimeSystems
/*    */ {
/*    */   public static class Init
/*    */     extends StoreSystem<EntityStore>
/*    */   {
/*    */     @Nonnull
/*    */     private final ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType;
/*    */     
/*    */     public Init(@Nonnull ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType) {
/* 33 */       this.worldTimeResourceType = worldTimeResourceType;
/*    */     }
/*    */ 
/*    */     
/*    */     public void onSystemAddedToStore(@Nonnull Store<EntityStore> store) {
/* 38 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/* 39 */       WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(this.worldTimeResourceType);
/*    */ 
/*    */       
/* 42 */       worldTimeResource.setGameTime0(world.getWorldConfig().getGameTime());
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 47 */       world.execute(() -> worldTimeResource.updateMoonPhase(world, (ComponentAccessor<EntityStore>)store));
/*    */     }
/*    */ 
/*    */     
/*    */     public void onSystemRemovedFromStore(@Nonnull Store<EntityStore> store) {
/* 52 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/* 53 */       WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(this.worldTimeResourceType);
/*    */       
/* 55 */       WorldConfig worldConfig = world.getWorldConfig();
/*    */ 
/*    */       
/* 58 */       worldConfig.setGameTime(worldTimeResource.getGameTime());
/* 59 */       worldConfig.markChanged();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Ticking
/*    */     extends TickingSystem<EntityStore>
/*    */   {
/*    */     @Nonnull
/*    */     private final ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Ticking(@Nonnull ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType) {
/* 80 */       this.worldTimeResourceType = worldTimeResourceType;
/*    */     }
/*    */ 
/*    */     
/*    */     public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 85 */       WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(this.worldTimeResourceType);
/* 86 */       worldTimeResource.tick(dt, store);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\time\WorldTimeSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */