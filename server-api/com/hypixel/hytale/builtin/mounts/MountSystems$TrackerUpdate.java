/*     */ package com.hypixel.hytale.builtin.mounts;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.BlockMount;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.protocol.MountedUpdate;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.mountpoints.BlockMountPoint;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ public class TrackerUpdate
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*  51 */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType = EntityTrackerSystems.Visible.getComponentType(); @Nonnull
/*  52 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.componentType, (Query)MountedComponent.getComponentType() });
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/*  58 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  64 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  69 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  74 */     EntityTrackerSystems.Visible visible = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.componentType);
/*  75 */     MountedComponent mounted = (MountedComponent)archetypeChunk.getComponent(index, MountedComponent.getComponentType());
/*     */     
/*  77 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */     
/*  79 */     if (mounted.consumeNetworkOutdated()) {
/*  80 */       queueUpdatesFor(ref, visible.visibleTo, mounted);
/*  81 */     } else if (!visible.newlyVisibleTo.isEmpty()) {
/*  82 */       queueUpdatesFor(ref, visible.newlyVisibleTo, mounted);
/*     */     } 
/*     */   }
/*     */   private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo, @Nonnull MountedComponent component) {
/*     */     MountedUpdate mountedUpdate;
/*  87 */     ComponentUpdate update = new ComponentUpdate();
/*  88 */     update.type = ComponentUpdateType.Mounted;
/*     */     
/*  90 */     Ref<EntityStore> mountedToEntity = component.getMountedToEntity();
/*  91 */     Ref<ChunkStore> mountedToBlock = component.getMountedToBlock();
/*     */     
/*  93 */     Vector3f offset = component.getAttachmentOffset();
/*  94 */     Vector3f netOffset = new Vector3f(offset.x, offset.y, offset.z);
/*     */ 
/*     */     
/*  97 */     if (mountedToEntity != null) {
/*  98 */       int mountedToNetworkId = ((NetworkId)ref.getStore().getComponent(mountedToEntity, NetworkId.getComponentType())).getId();
/*     */ 
/*     */ 
/*     */       
/* 102 */       mountedUpdate = new MountedUpdate(mountedToNetworkId, netOffset, component.getControllerType(), null);
/*     */     
/*     */     }
/* 105 */     else if (mountedToBlock != null) {
/* 106 */       BlockMountComponent blockMountComponent = (BlockMountComponent)mountedToBlock.getStore().getComponent(mountedToBlock, BlockMountComponent.getComponentType());
/* 107 */       if (blockMountComponent == null)
/* 108 */         return;  BlockMountPoint occupiedSeat = blockMountComponent.getSeatBlockBySeatedEntity(ref);
/* 109 */       if (occupiedSeat == null)
/* 110 */         return;  BlockType blockType = blockMountComponent.getExpectedBlockType();
/* 111 */       Vector3f position = occupiedSeat.computeWorldSpacePosition(blockMountComponent.getBlockPos());
/* 112 */       Vector3f rotationEuler = occupiedSeat.computeRotationEuler(blockMountComponent.getExpectedRotation());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       BlockMount blockMount = new BlockMount(blockMountComponent.getType(), new Vector3f(position.x, position.y, position.z), new Vector3f(rotationEuler.x, rotationEuler.y, rotationEuler.z), BlockType.getAssetMap().getIndex(blockType.getId()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       mountedUpdate = new MountedUpdate(0, netOffset, component.getControllerType(), blockMount);
/*     */     }
/*     */     else {
/*     */       
/* 126 */       throw new UnsupportedOperationException("Couldn't create MountedUpdate packet for MountedComponent");
/*     */     } 
/*     */     
/* 129 */     update.mounted = mountedUpdate;
/* 130 */     for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 131 */       viewer.queueUpdate(ref, update); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\MountSystems$TrackerUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */