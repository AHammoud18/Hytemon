/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
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
/*    */ public class AddSystem
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 25 */   private static final ComponentType<EntityStore, ChunkTracker> CHUNK_TRACKER_COMPONENT_TYPE = ChunkTracker.getComponentType();
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 29 */     return (Query)CHUNK_TRACKER_COMPONENT_TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 34 */     ChunkTracker chunkTrackerComponent = (ChunkTracker)holder.getComponent(CHUNK_TRACKER_COMPONENT_TYPE);
/* 35 */     assert chunkTrackerComponent != null;
/*    */     
/* 37 */     chunkTrackerComponent.setReadyForChunks(true);
/*    */   }
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerChunkTrackerSystems$AddSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */