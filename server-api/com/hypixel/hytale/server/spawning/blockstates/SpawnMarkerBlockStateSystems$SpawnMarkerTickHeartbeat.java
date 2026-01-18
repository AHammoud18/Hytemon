/*     */ package com.hypixel.hytale.server.spawning.blockstates;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawnMarkerTickHeartbeat
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, SpawnMarkerBlockReference> componentType;
/*     */   
/*     */   public SpawnMarkerTickHeartbeat(ComponentType<EntityStore, SpawnMarkerBlockReference> componentType) {
/* 138 */     this.componentType = componentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/* 148 */     return (Query)this.componentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 153 */     SpawnMarkerBlockReference marker = (SpawnMarkerBlockReference)archetypeChunk.getComponent(index, this.componentType);
/* 154 */     Vector3i pos = marker.getBlockPosition();
/* 155 */     WorldChunk chunk = ((EntityStore)store.getExternalData()).getWorld().getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(pos.x, pos.z));
/* 156 */     if (chunk != null) {
/* 157 */       BlockState state = chunk.getState(pos.x, pos.y, pos.z);
/* 158 */       if (!(state instanceof SpawnMarkerBlockState)) {
/* 159 */         Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 160 */         commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/* 161 */         SpawnMarkerBlockStateSystems.LOGGER.at(Level.SEVERE).log("Removing block spawn marker due to blockstate mismatch: %s", ref);
/*     */       } else {
/* 163 */         marker.refreshOriginLostTimeout();
/*     */       } 
/* 165 */     } else if (marker.tickOriginLostTimeout(dt)) {
/* 166 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 167 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/* 168 */       SpawnMarkerBlockStateSystems.LOGGER.at(Level.SEVERE).log("Removing block spawn marker due to origin chunk being unloaded: %s", ref);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\blockstates\SpawnMarkerBlockStateSystems$SpawnMarkerTickHeartbeat.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */