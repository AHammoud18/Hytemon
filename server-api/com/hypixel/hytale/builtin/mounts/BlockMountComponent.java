/*     */ package com.hypixel.hytale.builtin.mounts;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockMountType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.mountpoints.BlockMountPoint;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockMountComponent
/*     */   implements Component<ChunkStore> {
/*     */   public static ComponentType<ChunkStore, BlockMountComponent> getComponentType() {
/*  22 */     return MountPlugin.getInstance().getBlockMountComponentType();
/*     */   }
/*     */   
/*     */   private BlockMountType type;
/*     */   private Vector3i blockPos;
/*     */   private BlockType expectedBlockType;
/*     */   private int expectedRotation;
/*     */   @Nonnull
/*  30 */   private Map<BlockMountPoint, Ref<EntityStore>> entitiesByMountPoint = (Map<BlockMountPoint, Ref<EntityStore>>)new Object2ObjectOpenHashMap();
/*     */   @Nonnull
/*  32 */   private Map<Ref<EntityStore>, BlockMountPoint> mountPointByEntity = (Map<Ref<EntityStore>, BlockMountPoint>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMountComponent(BlockMountType type, Vector3i blockPos, BlockType expectedBlockType, int expectedRotation) {
/*  39 */     this.type = type;
/*  40 */     this.blockPos = blockPos;
/*  41 */     this.expectedBlockType = expectedBlockType;
/*  42 */     this.expectedRotation = expectedRotation;
/*     */   }
/*     */   
/*     */   public BlockMountType getType() {
/*  46 */     return this.type;
/*     */   }
/*     */   
/*     */   public Vector3i getBlockPos() {
/*  50 */     return this.blockPos;
/*     */   }
/*     */   
/*     */   public BlockType getExpectedBlockType() {
/*  54 */     return this.expectedBlockType;
/*     */   }
/*     */   
/*     */   public int getExpectedRotation() {
/*  58 */     return this.expectedRotation;
/*     */   }
/*     */   
/*     */   public boolean isDead() {
/*  62 */     clean();
/*  63 */     return this.entitiesByMountPoint.isEmpty();
/*     */   }
/*     */   
/*     */   private void clean() {
/*  67 */     this.entitiesByMountPoint.values().removeIf(ref -> !ref.isValid());
/*  68 */     this.mountPointByEntity.keySet().removeIf(ref -> !ref.isValid());
/*     */   }
/*     */   
/*     */   public void putSeatedEntity(@Nonnull BlockMountPoint mountPoint, @Nonnull Ref<EntityStore> seatedEntity) {
/*  72 */     this.entitiesByMountPoint.put(mountPoint, seatedEntity);
/*  73 */     this.mountPointByEntity.put(seatedEntity, mountPoint);
/*     */   }
/*     */   
/*     */   public void removeSeatedEntity(@Nonnull Ref<EntityStore> seatedEntity) {
/*  77 */     BlockMountPoint seat = this.mountPointByEntity.remove(seatedEntity);
/*  78 */     if (seat != null) {
/*  79 */       this.entitiesByMountPoint.remove(seat);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockMountPoint getSeatBlockBySeatedEntity(Ref<EntityStore> seatedEntity) {
/*  85 */     return this.mountPointByEntity.get(seatedEntity);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Collection<? extends Ref<EntityStore>> getSeatedEntities() {
/*  90 */     return this.entitiesByMountPoint.values();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockMountPoint findAvailableSeat(@Nonnull Vector3i targetBlock, @Nonnull BlockMountPoint[] choices, @Nonnull Vector3f whereWasClicked) {
/*  95 */     clean();
/*     */     
/*  97 */     double minDistSq = Double.MAX_VALUE;
/*  98 */     BlockMountPoint closestSeat = null;
/*  99 */     for (BlockMountPoint choice : choices) {
/* 100 */       if (!this.entitiesByMountPoint.containsKey(choice)) {
/*     */         
/* 102 */         Vector3f seatInWorldSpace = choice.computeWorldSpacePosition(targetBlock);
/* 103 */         double distSq = whereWasClicked.distanceSquaredTo(seatInWorldSpace);
/*     */         
/* 105 */         if (distSq < minDistSq) {
/* 106 */           minDistSq = distSq;
/* 107 */           closestSeat = choice;
/*     */         } 
/*     */       } 
/*     */     } 
/* 111 */     return closestSeat;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> clone() {
/* 117 */     BlockMountComponent seat = new BlockMountComponent();
/* 118 */     seat.type = this.type;
/* 119 */     seat.blockPos = this.blockPos;
/* 120 */     seat.expectedBlockType = this.expectedBlockType;
/* 121 */     seat.entitiesByMountPoint = (Map<BlockMountPoint, Ref<EntityStore>>)new Object2ObjectOpenHashMap(this.entitiesByMountPoint);
/* 122 */     seat.mountPointByEntity = (Map<Ref<EntityStore>, BlockMountPoint>)new Object2ObjectOpenHashMap(this.mountPointByEntity);
/* 123 */     return seat;
/*     */   }
/*     */   
/*     */   public BlockMountComponent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\BlockMountComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */