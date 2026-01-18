/*     */ package com.hypixel.hytale.builtin.adventure.farming;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.component.CoopResidentComponent;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.CoopBlock;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
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
/*     */ public class CoopResidentEntitySystem
/*     */   extends RefSystem<EntityStore>
/*     */ {
/* 469 */   private static final ComponentType<EntityStore, CoopResidentComponent> componentType = CoopResidentComponent.getComponentType();
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/* 473 */     return (Query)componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl AddReason reason, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl RemoveReason reason, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
/* 483 */     if (reason == RemoveReason.UNLOAD) {
/*     */       return;
/*     */     }
/*     */     
/* 487 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(ref, UUIDComponent.getComponentType());
/* 488 */     if (uuidComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 492 */     UUID uuid = uuidComponent.getUuid();
/*     */     
/* 494 */     CoopResidentComponent coopResidentComponent = (CoopResidentComponent)commandBuffer.getComponent(ref, componentType);
/* 495 */     if (coopResidentComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 499 */     Vector3i coopPosition = coopResidentComponent.getCoopLocation();
/* 500 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */     
/* 502 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(coopPosition.x, coopPosition.z);
/* 503 */     WorldChunk chunk = world.getChunkIfLoaded(chunkIndex);
/* 504 */     if (chunk == null) {
/*     */       return;
/*     */     }
/*     */     
/* 508 */     Ref<ChunkStore> chunkReference = world.getChunkStore().getChunkReference(chunkIndex);
/* 509 */     if (chunkReference == null || !chunkReference.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 513 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */     
/* 515 */     ChunkColumn chunkColumnComponent = (ChunkColumn)chunkStore.getComponent(chunkReference, ChunkColumn.getComponentType());
/* 516 */     if (chunkColumnComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 520 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/* 521 */     if (blockChunkComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 525 */     Ref<ChunkStore> sectionRef = chunkColumnComponent.getSection(ChunkUtil.chunkCoordinate(coopPosition.y));
/* 526 */     if (sectionRef == null || !sectionRef.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 530 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)chunkStore.getComponent(chunkReference, BlockComponentChunk.getComponentType());
/* 531 */     if (blockComponentChunk == null) {
/*     */       return;
/*     */     }
/*     */     
/* 535 */     int blockIndexColumn = ChunkUtil.indexBlockInColumn(coopPosition.x, coopPosition.y, coopPosition.z);
/* 536 */     Ref<ChunkStore> coopEntityReference = blockComponentChunk.getEntityReference(blockIndexColumn);
/* 537 */     if (coopEntityReference == null) {
/*     */       return;
/*     */     }
/*     */     
/* 541 */     CoopBlock coop = (CoopBlock)chunkStore.getComponent(coopEntityReference, CoopBlock.getComponentType());
/* 542 */     if (coop == null) {
/*     */       return;
/*     */     }
/*     */     
/* 546 */     coop.handleResidentDespawn(uuid);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\FarmingSystems$CoopResidentEntitySystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */