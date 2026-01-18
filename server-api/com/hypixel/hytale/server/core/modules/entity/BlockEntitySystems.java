/*     */ package com.hypixel.hytale.server.core.modules.entity;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.server.core.entity.entities.BlockEntity;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.EntityScaleComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.modules.physics.SimplePhysicsProvider;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BlockEntitySystems {
/*  28 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   public static class BlockEntitySetupSystem extends HolderSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, BlockEntity> blockEntityComponentType;
/*     */     
/*     */     public BlockEntitySetupSystem(ComponentType<EntityStore, BlockEntity> blockEntityComponentType) {
/*  34 */       this.blockEntityComponentType = blockEntityComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  39 */       if (!holder.getArchetype().contains(NetworkId.getComponentType())) {
/*  40 */         holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*     */       }
/*     */       
/*  43 */       BlockEntity blockEntityComponent = (BlockEntity)holder.getComponent(this.blockEntityComponentType);
/*     */       
/*  45 */       BoundingBox boundingBoxComponent = blockEntityComponent.createBoundingBoxComponent();
/*     */       
/*  47 */       if (boundingBoxComponent == null) {
/*  48 */         BlockEntitySystems.LOGGER.at(Level.SEVERE).log("Bounding box could not be initialized properly, defaulting to 1x1x1 dimensions for Block Entity bounding box");
/*  49 */         boundingBoxComponent = new BoundingBox(Box.horizontallyCentered(1.0D, 1.0D, 1.0D));
/*     */       } 
/*     */       
/*  52 */       holder.putComponent(BoundingBox.getComponentType(), (Component)boundingBoxComponent);
/*  53 */       SimplePhysicsProvider simplePhysicsProvider = blockEntityComponent.initPhysics(boundingBoxComponent);
/*  54 */       simplePhysicsProvider.setMoveOutOfSolid(false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/*  64 */       return (Query)this.blockEntityComponentType;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BlockEntityTrackerSystem extends EntityTickingSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */     private final ComponentType<EntityStore, BlockEntity> blockEntityComponentType;
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public BlockEntityTrackerSystem(ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType, ComponentType<EntityStore, BlockEntity> blockEntityComponentType) {
/*  75 */       this.visibleComponentType = visibleComponentType;
/*  76 */       this.blockEntityComponentType = blockEntityComponentType;
/*  77 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)visibleComponentType, (Query)blockEntityComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public SystemGroup<EntityStore> getGroup() {
/*  83 */       return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  89 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  94 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  99 */       EntityTrackerSystems.Visible visible = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 100 */       BlockEntity blockEntity = (BlockEntity)archetypeChunk.getComponent(index, this.blockEntityComponentType);
/* 101 */       assert blockEntity != null;
/*     */       
/* 103 */       float entityScale = 2.0F;
/* 104 */       boolean scaleOutdated = false;
/* 105 */       EntityScaleComponent entityScaleComponent = (EntityScaleComponent)archetypeChunk.getComponent(index, EntityScaleComponent.getComponentType());
/* 106 */       if (entityScaleComponent != null) {
/* 107 */         entityScale = entityScaleComponent.getScale();
/* 108 */         scaleOutdated = entityScaleComponent.consumeNetworkOutdated();
/*     */       } 
/*     */       
/* 111 */       boolean blockIdOutdated = blockEntity.consumeBlockIdNetworkOutdated();
/* 112 */       if (blockIdOutdated || scaleOutdated) {
/* 113 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), blockEntity, visible.visibleTo, entityScale);
/* 114 */       } else if (!visible.newlyVisibleTo.isEmpty()) {
/* 115 */         queueUpdatesFor(archetypeChunk.getReferenceTo(index), blockEntity, visible.newlyVisibleTo, entityScale);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static void queueUpdatesFor(Ref<EntityStore> ref, @Nonnull BlockEntity entity, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo, float entityScale) {
/* 120 */       ComponentUpdate update = new ComponentUpdate();
/* 121 */       update.type = ComponentUpdateType.Block;
/*     */       
/* 123 */       String key = entity.getBlockTypeKey();
/* 124 */       int index = BlockType.getAssetMap().getIndex(key);
/* 125 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 126 */       update.blockId = index;
/* 127 */       update.entityScale = entityScale;
/*     */       
/* 129 */       for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 130 */         viewer.queueUpdate(ref, update); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Ticking
/*     */     extends EntityTickingSystem<EntityStore> implements DisableProcessingAssert {
/* 136 */     private final ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*     */     private final ComponentType<EntityStore, BlockEntity> blockEntityComponentType;
/*     */     private final Archetype<EntityStore> archetype;
/*     */     
/*     */     public Ticking(@Nonnull ComponentType<EntityStore, BlockEntity> blockEntityComponentType) {
/* 141 */       this.blockEntityComponentType = blockEntityComponentType;
/* 142 */       this.archetype = Archetype.of(new ComponentType[] { this.transformComponentType, blockEntityComponentType, Velocity.getComponentType() });
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 147 */       return (Query<EntityStore>)this.archetype;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 152 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 153 */       assert transformComponent != null;
/*     */       
/* 155 */       BlockEntity blockEntityComponent = (BlockEntity)archetypeChunk.getComponent(index, this.blockEntityComponentType);
/* 156 */       assert blockEntityComponent != null;
/*     */       
/* 158 */       Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/* 159 */       assert velocityComponent != null;
/*     */       
/*     */       try {
/* 162 */         blockEntityComponent.getSimplePhysicsProvider().tick(dt, velocityComponent, ((EntityStore)store.getExternalData()).getWorld(), transformComponent, archetypeChunk.getReferenceTo(index), (ComponentAccessor)commandBuffer);
/* 163 */       } catch (Throwable throwable) {
/* 164 */         ((HytaleLogger.Api)BlockEntitySystems.LOGGER.at(Level.SEVERE).withCause(throwable)).log("Exception while ticking entity. Removing entity %s", blockEntityComponent);
/* 165 */         commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\BlockEntitySystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */