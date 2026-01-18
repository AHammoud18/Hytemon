/*     */ package com.hypixel.hytale.server.npc.util;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.collision.BoxBlockIntersectionEvaluator;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionFilter;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*     */ import com.hypixel.hytale.server.core.modules.collision.WorldUtil;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PositionProbeBase
/*     */ {
/*     */   private static final int lastWaterCheckDistanceMinSquared = 25;
/*     */   protected boolean touchCeil;
/*     */   protected boolean onGround;
/*     */   protected boolean inWater;
/*     */   protected boolean validPosition = true;
/*  32 */   protected int groundLevel = -1;
/*  33 */   protected int waterLevel = -1;
/*  34 */   protected int heightOverGround = -1;
/*  35 */   protected int heightOverWater = -1;
/*  36 */   protected int heightOverSurface = -1;
/*  37 */   protected int depthBelowSurface = -1;
/*     */   
/*  39 */   private int lastWaterCheckX = Integer.MAX_VALUE;
/*  40 */   private int lastWaterCheckZ = Integer.MAX_VALUE;
/*  41 */   private int lastWaterCheckLevel = -2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> boolean probePosition(@Nonnull Ref<EntityStore> ref, @Nonnull Box boundingBox, @Nonnull Vector3d position, @Nonnull CollisionResult collisionResult, @Nonnull T t, @Nonnull CollisionFilter<BoxBlockIntersectionEvaluator, T> blockTest, int materialSet, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  51 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/*  53 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  54 */     assert transformComponent != null;
/*     */ 
/*     */     
/*  57 */     ChunkStore chunkStore = world.getChunkStore();
/*  58 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(position.x, position.z);
/*  59 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*     */     
/*  61 */     reset();
/*     */     
/*  63 */     if (chunkRef == null || !chunkRef.isValid()) {
/*  64 */       this.waterLevel = -1;
/*  65 */       this.groundLevel = -1;
/*  66 */       this.lastWaterCheckLevel = -2;
/*  67 */       return false;
/*     */     } 
/*     */     
/*  70 */     int x = MathUtil.floor(position.x);
/*  71 */     int y = MathUtil.floor(position.y);
/*  72 */     int z = MathUtil.floor(position.z);
/*     */     
/*  74 */     this.validPosition = (CollisionModule.get().validatePosition(world, boundingBox, position, materialSet, t, blockTest, collisionResult) != -1);
/*     */     
/*  76 */     Store<ChunkStore> chunkStoreAccessor = chunkStore.getStore();
/*  77 */     ChunkColumn chunkColumnComponent = (ChunkColumn)chunkStoreAccessor.getComponent(chunkRef, ChunkColumn.getComponentType());
/*  78 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStoreAccessor.getComponent(chunkRef, BlockChunk.getComponentType());
/*     */     
/*  80 */     if (chunkColumnComponent == null || blockChunkComponent == null) {
/*  81 */       this.groundLevel = -1;
/*  82 */       this.waterLevel = -1;
/*  83 */       this.lastWaterCheckLevel = -2;
/*     */     } else {
/*  85 */       this.groundLevel = blockChunkComponent.getHeight(x, z);
/*  86 */       this.waterLevel = updateWaterLevel((ComponentAccessor<ChunkStore>)chunkStoreAccessor, chunkColumnComponent, blockChunkComponent, x, z);
/*     */     } 
/*     */     
/*  89 */     this.heightOverGround = y - this.groundLevel;
/*     */     
/*  91 */     if (this.waterLevel < this.groundLevel) {
/*  92 */       this.heightOverSurface = this.heightOverGround;
/*  93 */     } else if (y > this.waterLevel) {
/*  94 */       this.heightOverSurface = y - this.waterLevel;
/*     */     } else {
/*  96 */       this.depthBelowSurface = this.waterLevel - y;
/*     */     } 
/*  98 */     return this.validPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int updateWaterLevel(@Nonnull ComponentAccessor<ChunkStore> chunkStore, @Nonnull ChunkColumn chunkColumn, @Nonnull BlockChunk blockChunk, int x, int z) {
/* 106 */     if (this.lastWaterCheckLevel < -1 || movedFarEnough(x, z)) {
/* 107 */       this.lastWaterCheckX = x;
/* 108 */       this.lastWaterCheckZ = z;
/* 109 */       this.lastWaterCheckLevel = WorldUtil.getWaterLevel(chunkStore, chunkColumn, blockChunk, x, z, this.groundLevel);
/*     */     } 
/* 111 */     return this.lastWaterCheckLevel;
/*     */   }
/*     */   
/*     */   private boolean movedFarEnough(int x, int z) {
/* 115 */     x -= this.lastWaterCheckX;
/* 116 */     z -= this.lastWaterCheckZ;
/* 117 */     return (x * x + z * z > 25);
/*     */   }
/*     */   
/*     */   protected void reset() {
/* 121 */     this.touchCeil = false;
/* 122 */     this.onGround = false;
/* 123 */     this.inWater = false;
/* 124 */     this.validPosition = true;
/* 125 */     this.heightOverGround = -1;
/* 126 */     this.heightOverSurface = -1;
/* 127 */     this.heightOverWater = -1;
/* 128 */     this.depthBelowSurface = -1;
/*     */   }
/*     */   
/*     */   public boolean isValidPosition() {
/* 132 */     return this.validPosition;
/*     */   }
/*     */   
/*     */   public boolean isTouchCeil() {
/* 136 */     return this.touchCeil;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/* 140 */     return this.onGround;
/*     */   }
/*     */   
/*     */   public boolean isInWater() {
/* 144 */     return this.inWater;
/*     */   }
/*     */   
/*     */   public int getGroundLevel() {
/* 148 */     return this.groundLevel;
/*     */   }
/*     */   
/*     */   public int getWaterLevel() {
/* 152 */     return this.waterLevel;
/*     */   }
/*     */   
/*     */   public int getHeightOverGround() {
/* 156 */     return this.heightOverGround;
/*     */   }
/*     */   
/*     */   public int getHeightOverSurface() {
/* 160 */     return this.heightOverSurface;
/*     */   }
/*     */   
/*     */   public int getDepthBelowSurface() {
/* 164 */     return this.depthBelowSurface;
/*     */   }
/*     */   
/*     */   public int getHeightOverWater() {
/* 168 */     return this.heightOverWater;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 174 */     return "PositionProbeBase{touchCeil=" + this.touchCeil + ", onGround=" + this.onGround + ", inWater=" + this.inWater + ", validPosition=" + this.validPosition + ", groundLevel=" + this.groundLevel + ", waterLevel=" + this.waterLevel + ", heightOverGround=" + this.heightOverGround + ", heightOverSurface=" + this.heightOverSurface + ", depthBelowSurface=" + this.depthBelowSurface + ", heightOverWater=" + this.heightOverWater + ", lastWaterCheckX=" + this.lastWaterCheckX + ", lastWaterCheckZ=" + this.lastWaterCheckZ + ", lastWaterCheckLevel=" + this.lastWaterCheckLevel + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\PositionProbeBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */