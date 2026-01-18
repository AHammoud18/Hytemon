/*     */ package com.hypixel.hytale.server.npc.util;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.iterator.BlockIterator;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class RayBlockHitTest
/*     */   implements BlockIterator.BlockIteratorProcedure
/*     */ {
/*  22 */   public static final ThreadLocal<RayBlockHitTest> THREAD_LOCAL = ThreadLocal.withInitial(RayBlockHitTest::new);
/*     */   
/*     */   @Nullable
/*     */   private World world;
/*     */   
/*     */   @Nullable
/*     */   private WorldChunk chunk;
/*  29 */   private final Vector3d origin = new Vector3d();
/*  30 */   private final Vector3d direction = new Vector3d();
/*     */   
/*     */   private int blockSet;
/*     */   
/*  34 */   private final Vector3d hitPosition = new Vector3d(Vector3d.MIN);
/*     */   
/*     */   private short lastBlockRevision;
/*     */   
/*     */   public boolean accept(int x, int y, int z, double px, double py, double pz, double qx, double qy, double qz) {
/*  39 */     if (!ChunkUtil.isInsideChunk(this.chunk.getX(), this.chunk.getZ(), x, z)) {
/*  40 */       this.chunk = this.world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/*  41 */       if (this.chunk == null) {
/*  42 */         this.hitPosition.assign(Vector3d.MIN);
/*  43 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/*  47 */     int blockId = this.chunk.getBlock(x, y, z);
/*  48 */     if (blockId == 0) return true; 
/*  49 */     if (blockId == 1) return false;
/*     */     
/*  51 */     this.lastBlockRevision = this.chunk.getBlockChunk().getSectionAtBlockY(y).getLocalChangeCounter();
/*  52 */     if (BlockSetModule.getInstance().blockInSet(this.blockSet, blockId)) {
/*  53 */       this.hitPosition.assign(x, y, z);
/*     */     }
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getHitPosition() {
/*  60 */     return this.hitPosition;
/*     */   }
/*     */   
/*     */   public short getLastBlockRevision() {
/*  64 */     return this.lastBlockRevision;
/*     */   }
/*     */   
/*     */   public boolean init(@Nonnull Ref<EntityStore> ref, int blockSet, float pitch, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  68 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  69 */     assert transformComponent != null;
/*     */     
/*  71 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/*  72 */     assert modelComponent != null;
/*     */     
/*  74 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/*  75 */     assert headRotationComponent != null;
/*     */     
/*  77 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/*  79 */     this.blockSet = blockSet;
/*  80 */     this.origin.assign(transformComponent.getPosition());
/*  81 */     this.origin.y += modelComponent.getModel().getEyeHeight();
/*  82 */     this.world = world;
/*  83 */     this.chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(this.origin.x, this.origin.z));
/*  84 */     if (this.chunk == null) return false;
/*     */ 
/*     */ 
/*     */     
/*  88 */     float yaw = headRotationComponent.getRotation().getYaw();
/*  89 */     this.direction.assign(yaw, pitch);
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   public boolean run(double range) {
/*  94 */     BlockIterator.iterate(this.origin, this.direction, range, this);
/*  95 */     return !this.hitPosition.equals(Vector3d.MIN);
/*     */   }
/*     */   
/*     */   public void clear() {
/*  99 */     this.world = null;
/* 100 */     this.chunk = null;
/* 101 */     this.hitPosition.assign(Vector3d.MIN);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\RayBlockHitTest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */