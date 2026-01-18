/*     */ package com.hypixel.hytale.server.worldgen.cave.element;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.CaveNodeShape;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IChunkBounds;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.WorldBounds;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.LongConsumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CaveNode
/*     */   implements CaveElement
/*     */ {
/*     */   private final CaveNodeType caveNodeType;
/*     */   @Nonnull
/*     */   private final CaveNodeShape shape;
/*     */   @Nonnull
/*     */   private final WorldBounds bounds;
/*     */   private final int seedOffset;
/*     */   private final float pitch;
/*     */   private final float yaw;
/*     */   @Nullable
/*     */   private List<CavePrefab> rawCavePrefabs;
/*     */   private CavePrefab[] cavePrefabs;
/*     */   
/*     */   public CaveNode(int seedOffset, CaveNodeType caveNodeType, @Nonnull CaveNodeShape shape, float yaw, float pitch) {
/*  39 */     this.seedOffset = seedOffset;
/*  40 */     this.rawCavePrefabs = new ArrayList<>();
/*  41 */     this.caveNodeType = caveNodeType;
/*  42 */     this.shape = shape;
/*  43 */     this.yaw = yaw;
/*  44 */     this.pitch = pitch;
/*  45 */     this.bounds = new WorldBounds(shape.getBounds());
/*     */   }
/*     */   
/*     */   public int getSeedOffset() {
/*  49 */     return this.seedOffset;
/*     */   }
/*     */   
/*     */   public CaveNodeType getCaveNodeType() {
/*  53 */     return this.caveNodeType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CaveNodeShape getShape() {
/*  58 */     return this.shape;
/*     */   }
/*     */   
/*     */   public CavePrefab[] getCavePrefabs() {
/*  62 */     return this.cavePrefabs;
/*     */   }
/*     */   
/*     */   public float getYaw() {
/*  66 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  70 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public Vector3d getEnd() {
/*  74 */     return this.shape.getEnd();
/*     */   }
/*     */   
/*     */   public void addPrefab(@Nonnull CavePrefab prefab) {
/*  78 */     this.rawCavePrefabs.add(prefab);
/*  79 */     this.bounds.include((IChunkBounds)prefab.getBounds());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWorldBounds getBounds() {
/*  85 */     return (IWorldBounds)this.bounds;
/*     */   }
/*     */   
/*     */   public int getFloorPosition(int seed, double x, double z) {
/*  89 */     return MathUtil.floor(this.shape.getFloorPosition(seed, x, z));
/*     */   }
/*     */   
/*     */   public int getCeilingPosition(int seed, double x, double z) {
/*  93 */     return MathUtil.floor(this.shape.getCeilingPosition(seed, x, z));
/*     */   }
/*     */   
/*     */   public void forEachChunk(@Nonnull LongConsumer consumer) {
/*  97 */     int lowZ = this.bounds.getLowChunkZ();
/*  98 */     int highX = this.bounds.getHighChunkX();
/*  99 */     int highZ = this.bounds.getHighChunkZ();
/* 100 */     for (int x = this.bounds.getLowChunkX(); x <= highX; x++) {
/* 101 */       for (int z = lowZ; z <= highZ; z++) {
/* 102 */         consumer.accept(ChunkUtil.indexChunk(x, z));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void compile() {
/* 108 */     this.cavePrefabs = (CavePrefab[])this.rawCavePrefabs.toArray(x$0 -> new CavePrefab[x$0]);
/* 109 */     this.rawCavePrefabs = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 116 */     return "CaveNode{cavePrefabs=" + Arrays.toString((Object[])this.cavePrefabs) + "rawCavePrefabs=" + 
/* 117 */       Arrays.toString((Object[])this.cavePrefabs) + ", caveNodeType=" + String.valueOf(this.caveNodeType) + ", shape=" + String.valueOf(this.shape) + ", bounds=" + String.valueOf(this.bounds) + ", seedOffset=" + this.seedOffset + ", pitch=" + this.pitch + ", yaw=" + this.yaw + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\element\CaveNode.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */