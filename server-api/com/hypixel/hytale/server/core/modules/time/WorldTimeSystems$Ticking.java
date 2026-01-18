/*    */ package com.hypixel.hytale.server.core.modules.time;
/*    */ 
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.tick.TickingSystem;
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
/*    */ public class Ticking
/*    */   extends TickingSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType;
/*    */   
/*    */   public Ticking(@Nonnull ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType) {
/* 80 */     this.worldTimeResourceType = worldTimeResourceType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 85 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(this.worldTimeResourceType);
/* 86 */     worldTimeResource.tick(dt, store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\time\WorldTimeSystems$Ticking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */