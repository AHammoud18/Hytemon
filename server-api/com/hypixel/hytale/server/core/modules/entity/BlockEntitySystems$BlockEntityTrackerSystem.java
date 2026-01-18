/*     */ package com.hypixel.hytale.server.core.modules.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.entities.BlockEntity;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.EntityScaleComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockEntityTrackerSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */   private final ComponentType<EntityStore, BlockEntity> blockEntityComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public BlockEntityTrackerSystem(ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType, ComponentType<EntityStore, BlockEntity> blockEntityComponentType) {
/*  75 */     this.visibleComponentType = visibleComponentType;
/*  76 */     this.blockEntityComponentType = blockEntityComponentType;
/*  77 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)visibleComponentType, (Query)blockEntityComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/*  83 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  89 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  94 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  99 */     EntityTrackerSystems.Visible visible = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 100 */     BlockEntity blockEntity = (BlockEntity)archetypeChunk.getComponent(index, this.blockEntityComponentType);
/* 101 */     assert blockEntity != null;
/*     */     
/* 103 */     float entityScale = 2.0F;
/* 104 */     boolean scaleOutdated = false;
/* 105 */     EntityScaleComponent entityScaleComponent = (EntityScaleComponent)archetypeChunk.getComponent(index, EntityScaleComponent.getComponentType());
/* 106 */     if (entityScaleComponent != null) {
/* 107 */       entityScale = entityScaleComponent.getScale();
/* 108 */       scaleOutdated = entityScaleComponent.consumeNetworkOutdated();
/*     */     } 
/*     */     
/* 111 */     boolean blockIdOutdated = blockEntity.consumeBlockIdNetworkOutdated();
/* 112 */     if (blockIdOutdated || scaleOutdated) {
/* 113 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), blockEntity, visible.visibleTo, entityScale);
/* 114 */     } else if (!visible.newlyVisibleTo.isEmpty()) {
/* 115 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), blockEntity, visible.newlyVisibleTo, entityScale);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void queueUpdatesFor(Ref<EntityStore> ref, @Nonnull BlockEntity entity, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo, float entityScale) {
/* 120 */     ComponentUpdate update = new ComponentUpdate();
/* 121 */     update.type = ComponentUpdateType.Block;
/*     */     
/* 123 */     String key = entity.getBlockTypeKey();
/* 124 */     int index = BlockType.getAssetMap().getIndex(key);
/* 125 */     if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 126 */     update.blockId = index;
/* 127 */     update.entityScale = entityScale;
/*     */     
/* 129 */     for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 130 */       viewer.queueUpdate(ref, update); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\BlockEntitySystems$BlockEntityTrackerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */