/*    */ package com.hypixel.hytale.server.core.modules.time;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.StoreSystem;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Init
/*    */   extends StoreSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType;
/*    */   
/*    */   public Init(@Nonnull ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType) {
/* 33 */     this.worldTimeResourceType = worldTimeResourceType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onSystemAddedToStore(@Nonnull Store<EntityStore> store) {
/* 38 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 39 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(this.worldTimeResourceType);
/*    */ 
/*    */     
/* 42 */     worldTimeResource.setGameTime0(world.getWorldConfig().getGameTime());
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     world.execute(() -> worldTimeResource.updateMoonPhase(world, (ComponentAccessor<EntityStore>)store));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onSystemRemovedFromStore(@Nonnull Store<EntityStore> store) {
/* 52 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 53 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(this.worldTimeResourceType);
/*    */     
/* 55 */     WorldConfig worldConfig = world.getWorldConfig();
/*    */ 
/*    */     
/* 58 */     worldConfig.setGameTime(worldTimeResource.getGameTime());
/* 59 */     worldConfig.markChanged();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\time\WorldTimeSystems$Init.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */