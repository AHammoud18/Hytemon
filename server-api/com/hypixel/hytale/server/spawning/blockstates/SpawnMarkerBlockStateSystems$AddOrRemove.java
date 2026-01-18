/*    */ package com.hypixel.hytale.server.spawning.blockstates;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.entity.reference.PersistentRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*    */ public class AddOrRemove
/*    */   extends RefSystem<ChunkStore>
/*    */ {
/*    */   private final ComponentType<ChunkStore, SpawnMarkerBlockState> componentType;
/*    */   
/*    */   public AddOrRemove(ComponentType<ChunkStore, SpawnMarkerBlockState> componentType) {
/* 32 */     this.componentType = componentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<ChunkStore> getQuery() {
/* 37 */     return (Query)this.componentType;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 46 */     if (reason == RemoveReason.REMOVE) {
/* 47 */       SpawnMarkerBlockState state = (SpawnMarkerBlockState)store.getComponent(ref, this.componentType);
/* 48 */       PersistentRef markerReference = state.getSpawnMarkerReference();
/* 49 */       if (markerReference == null)
/* 50 */         return;  World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 51 */       world.execute(() -> {
/*    */             Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*    */             Ref<EntityStore> marker = markerReference.getEntity((ComponentAccessor)entityStore);
/*    */             if (marker != null)
/*    */               entityStore.removeEntity(marker, RemoveReason.REMOVE); 
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\blockstates\SpawnMarkerBlockStateSystems$AddOrRemove.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */