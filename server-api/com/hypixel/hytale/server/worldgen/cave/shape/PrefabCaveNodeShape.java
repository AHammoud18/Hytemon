/*     */ package com.hypixel.hytale.server.worldgen.cave.shape;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*     */ import com.hypixel.hytale.server.worldgen.cave.Cave;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGeneratorExecution;
/*     */ import com.hypixel.hytale.server.worldgen.loader.WorldGenPrefabSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.prefab.PrefabPasteUtil;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IChunkBounds;
/*     */ import com.hypixel.hytale.server.worldgen.util.bounds.IWorldBounds;
/*     */ import com.hypixel.hytale.server.worldgen.util.condition.BlockMaskCondition;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabCaveNodeShape
/*     */   implements CaveNodeShape, IWorldBounds
/*     */ {
/*     */   private final CaveType caveType;
/*     */   @Nonnull
/*     */   private final Vector3d o;
/*     */   private final Vector3d e;
/*     */   @Nonnull
/*     */   private final WorldGenPrefabSupplier prefabSupplier;
/*     */   @Nonnull
/*     */   private final PrefabRotation rotation;
/*     */   private final BlockMaskCondition configuration;
/*     */   private final int lowBoundX;
/*     */   private final int lowBoundY;
/*     */   private final int lowBoundZ;
/*     */   private final int highBoundX;
/*     */   private final int highBoundY;
/*     */   private final int highBoundZ;
/*     */   
/*     */   public PrefabCaveNodeShape(CaveType caveType, @Nonnull Vector3d o, Vector3d e, @Nonnull WorldGenPrefabSupplier prefabSupplier, @Nonnull PrefabRotation rotation, BlockMaskCondition configuration) {
/*  48 */     this.caveType = caveType;
/*  49 */     this.o = o;
/*  50 */     this.e = e;
/*  51 */     this.prefabSupplier = prefabSupplier;
/*  52 */     this.rotation = rotation;
/*  53 */     this.configuration = configuration;
/*     */     
/*  55 */     IPrefabBuffer prefab = prefabSupplier.get();
/*  56 */     IChunkBounds bounds = prefabSupplier.getBounds(prefab);
/*  57 */     this.lowBoundX = MathUtil.floor(o.x + bounds.getLowBoundX(rotation));
/*  58 */     this.lowBoundY = MathUtil.floor(o.y + prefab.getMinY());
/*  59 */     this.lowBoundZ = MathUtil.floor(o.z + bounds.getLowBoundZ(rotation));
/*  60 */     this.highBoundX = MathUtil.ceil(o.x + bounds.getHighBoundX(rotation));
/*  61 */     this.highBoundY = MathUtil.ceil(o.y + prefab.getMaxY());
/*  62 */     this.highBoundZ = MathUtil.ceil(o.z + bounds.getHighBoundZ(rotation));
/*     */   }
/*     */   
/*     */   public CaveType getCaveType() {
/*  66 */     return this.caveType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public PrefabRotation getPrefabRotation() {
/*  71 */     return this.rotation;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getO() {
/*  76 */     return this.o;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getStart() {
/*  82 */     return this.o.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getEnd() {
/*  88 */     return this.o.clone().add(this.e);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getAnchor(@Nonnull Vector3d vector, double tx, double ty, double tz) {
/*  94 */     Vector3d anchor = CaveNodeShapeUtils.getBoxAnchor(vector, this, tx, ty, tz);
/*     */     
/*  96 */     double x = MathUtil.floor(anchor.x) + 0.5D;
/*  97 */     double y = MathUtil.floor(anchor.y) + 0.5D;
/*  98 */     double z = MathUtil.floor(anchor.z) + 0.5D;
/*  99 */     return anchor.assign(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWorldBounds getBounds() {
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundX() {
/* 110 */     return this.lowBoundX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundZ() {
/* 115 */     return this.lowBoundZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundX() {
/* 120 */     return this.highBoundX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundZ() {
/* 125 */     return this.highBoundZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundY() {
/* 130 */     return this.lowBoundY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundY() {
/* 135 */     return this.highBoundY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReplace(int seed, double x, double z, int y) {
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getFloorPosition(int seed, double x, double z) {
/* 145 */     x -= this.o.x;
/* 146 */     z -= this.o.z;
/* 147 */     return this.prefabSupplier.get().getMinYAt(this.rotation, (int)x, (int)z);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCeilingPosition(int seed, double x, double z) {
/* 152 */     x -= this.o.x;
/* 153 */     z -= this.o.z;
/* 154 */     return this.prefabSupplier.get().getMaxYAt(this.rotation, (int)x, (int)z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void populateChunk(int seed, @Nonnull ChunkGeneratorExecution execution, @Nonnull Cave cave, @Nonnull CaveNode node, Random random) {
/* 159 */     int x = MathUtil.floor(this.o.x);
/* 160 */     int y = MathUtil.floor(this.o.y);
/* 161 */     int z = MathUtil.floor(this.o.z);
/* 162 */     int cx = x - ChunkUtil.minBlock(execution.getX());
/* 163 */     int cz = z - ChunkUtil.minBlock(execution.getZ());
/*     */     
/* 165 */     long externalSeed = HashUtil.hash(Double.doubleToLongBits(this.o.x), Double.doubleToLongBits(this.o.z)) * 1406794441L;
/*     */     
/* 167 */     PrefabPasteUtil.PrefabPasteBuffer buffer = (ChunkGenerator.getResource()).prefabBuffer;
/* 168 */     buffer.setSeed(seed, externalSeed);
/* 169 */     buffer.execution = execution;
/* 170 */     buffer.blockMask = this.configuration;
/* 171 */     buffer.environmentId = node.getCaveNodeType().hasEnvironment() ? node.getCaveNodeType().getEnvironment() : this.caveType.getEnvironment();
/* 172 */     buffer.priority = 8;
/*     */     
/* 174 */     if (execution.getChunkGenerator().getBenchmark().isEnabled() && ChunkUtil.isInsideChunkRelative(cx, cz)) {
/* 175 */       execution.getChunkGenerator().getBenchmark().registerPrefab("CaveNode: " + cave
/* 176 */           .getCaveType().getName() + "\t" + node.getCaveNodeType().getName() + "\t" + this.prefabSupplier.getName());
/*     */     }
/*     */ 
/*     */     
/* 180 */     PrefabPasteUtil.generate(buffer, this.rotation, this.prefabSupplier, x, y, z, cx, cz);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 186 */     return "PrefabCaveNodeShape{caveType=" + String.valueOf(this.caveType) + ", o=" + String.valueOf(this.o) + ", e=" + String.valueOf(this.e) + ", prefabSupplier=\"prefab\", rotation=" + String.valueOf(this.rotation) + ", configuration=" + String.valueOf(this.configuration) + ", lowBoundX=" + this.lowBoundX + ", lowBoundY=" + this.lowBoundY + ", lowBoundZ=" + this.lowBoundZ + ", highBoundX=" + this.highBoundX + ", highBoundY=" + this.highBoundY + ", highBoundZ=" + this.highBoundZ + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PrefabCaveNodeShapeGenerator
/*     */     implements CaveNodeShapeEnum.CaveNodeShapeGenerator
/*     */   {
/*     */     private final List<WorldGenPrefabSupplier> prefabs;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final BlockMaskCondition configuration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PrefabCaveNodeShapeGenerator(List<WorldGenPrefabSupplier> prefabs, BlockMaskCondition configuration) {
/* 209 */       this.prefabs = prefabs;
/* 210 */       this.configuration = configuration;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CaveNodeShape generateCaveNodeShape(@Nonnull Random random, CaveType caveType, @Nullable CaveNode parentNode, @Nonnull CaveNodeType.CaveNodeChildEntry childEntry, @Nonnull Vector3d origin, float yaw, float pitch) {
/* 220 */       WorldGenPrefabSupplier prefab = this.prefabs.get(random.nextInt(this.prefabs.size()));
/* 221 */       if (parentNode == null) {
/* 222 */         PrefabRotation prefabRotation = PrefabRotation.VALUES[random.nextInt(PrefabRotation.VALUES.length)];
/* 223 */         return new PrefabCaveNodeShape(caveType, origin, Vector3d.ZERO, prefab, prefabRotation, this.configuration);
/*     */       } 
/* 225 */       Vector3d offset = childEntry.getOffset().clone();
/* 226 */       PrefabRotation rotation = childEntry.getRotation(random);
/* 227 */       CaveNodeShape caveNodeShape = parentNode.getShape(); if (caveNodeShape instanceof PrefabCaveNodeShape) { PrefabCaveNodeShape parentShape = (PrefabCaveNodeShape)caveNodeShape;
/* 228 */         PrefabRotation parentRotation = parentShape.getPrefabRotation();
/* 229 */         parentRotation.rotate(offset);
/* 230 */         rotation = rotation.add(parentRotation); }
/*     */       
/* 232 */       origin.add(offset);
/* 233 */       return new PrefabCaveNodeShape(caveType, origin, Vector3d.ZERO, prefab, rotation, this.configuration);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\PrefabCaveNodeShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */