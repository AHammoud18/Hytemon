/*     */ package com.hypixel.hytale.server.worldgen.util.bounds;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ChunkBounds implements IChunkBounds {
/*     */   protected int minX;
/*     */   protected int minZ;
/*     */   
/*     */   public ChunkBounds() {
/*  11 */     this(2147483647, 2147483647, -2147483648, -2147483648);
/*     */   }
/*     */   
/*     */   protected int maxX;
/*     */   protected int maxZ;
/*     */   
/*     */   public ChunkBounds(@Nonnull IChunkBounds bounds) {
/*  18 */     this(bounds
/*  19 */         .getLowBoundX(), bounds.getLowBoundZ(), bounds
/*  20 */         .getHighBoundX(), bounds.getHighBoundZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkBounds(int minX, int minZ, int maxX, int maxZ) {
/*  25 */     this.minX = minX;
/*  26 */     this.minZ = minZ;
/*  27 */     this.maxX = maxX;
/*  28 */     this.maxZ = maxZ;
/*     */   }
/*     */   
/*     */   public ChunkBounds(int x, int z) {
/*  32 */     this.minX = this.maxX = x;
/*  33 */     this.minZ = this.maxZ = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundX() {
/*  38 */     return this.minX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowBoundZ() {
/*  43 */     return this.minZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundX() {
/*  48 */     return this.maxX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighBoundZ() {
/*  53 */     return this.maxZ;
/*     */   }
/*     */   
/*     */   public void expandNegative(int x, int z) {
/*  57 */     this.minX += x;
/*  58 */     this.minZ += z;
/*     */   }
/*     */   
/*     */   public void expandPositive(int x, int z) {
/*  62 */     this.maxX += x;
/*  63 */     this.maxZ += z;
/*     */   }
/*     */   
/*     */   public void expandNegative(double x, double z) {
/*  67 */     this.minX = MathUtil.floor(this.minX + x);
/*  68 */     this.minZ = MathUtil.floor(this.minZ + z);
/*     */   }
/*     */   
/*     */   public void expandPositive(double x, double z) {
/*  72 */     this.maxX = MathUtil.ceil(this.maxX + x);
/*  73 */     this.maxZ = MathUtil.ceil(this.maxZ + z);
/*     */   }
/*     */   
/*     */   public void include(int minX, int minZ, int maxX, int maxZ) {
/*  77 */     if (this.minX > minX) this.minX = minX; 
/*  78 */     if (this.minZ > minZ) this.minZ = minZ; 
/*  79 */     if (this.maxX < maxX) this.maxX = maxX; 
/*  80 */     if (this.maxZ < maxZ) this.maxZ = maxZ; 
/*     */   }
/*     */   
/*     */   public void include(int x, int z) {
/*  84 */     if (this.minX > x) { this.minX = x; }
/*  85 */     else if (this.maxX < x) { this.maxX = x; }
/*  86 */      if (this.minZ > z) { this.minZ = z; }
/*  87 */     else if (this.maxZ < z) { this.maxZ = z; }
/*     */   
/*     */   }
/*     */   public void include(@Nonnull IChunkBounds box) {
/*  91 */     if (this.minX > box.getLowBoundX()) this.minX = box.getLowBoundX(); 
/*  92 */     if (this.minZ > box.getLowBoundZ()) this.minZ = box.getLowBoundZ(); 
/*  93 */     if (this.maxX < box.getHighBoundX()) this.maxX = box.getHighBoundX(); 
/*  94 */     if (this.maxZ < box.getHighBoundZ()) this.maxZ = box.getHighBoundZ();
/*     */   
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 100 */     return "ChunkBounds{minX=" + this.minX + ", minZ=" + this.minZ + ", maxX=" + this.maxX + ", maxZ=" + this.maxZ + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\bounds\ChunkBounds.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */