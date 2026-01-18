/*    */ package com.hypixel.hytale.server.worldgen.util.bounds;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class WorldBounds
/*    */   extends ChunkBounds implements IWorldBounds {
/*    */   protected int minY;
/*    */   protected int maxY;
/*    */   
/*    */   public WorldBounds() {
/* 12 */     this.minY = Integer.MAX_VALUE;
/* 13 */     this.maxY = Integer.MIN_VALUE;
/*    */   }
/*    */   
/*    */   public WorldBounds(@Nonnull IWorldBounds bounds) {
/* 17 */     super(bounds);
/* 18 */     this.minY = bounds.getLowBoundY();
/* 19 */     this.maxY = bounds.getHighBoundY();
/*    */   }
/*    */   
/*    */   public WorldBounds(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/* 23 */     super(minX, minZ, maxX, maxZ);
/* 24 */     this.minY = minY;
/* 25 */     this.maxY = maxY;
/*    */   }
/*    */   
/*    */   public WorldBounds(int x, int y, int z) {
/* 29 */     super(x, z);
/* 30 */     this.minY = this.maxY = y;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLowBoundY() {
/* 35 */     return this.minY;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHighBoundY() {
/* 40 */     return this.maxY;
/*    */   }
/*    */   
/*    */   public void expandNegative(double x, double y, double z) {
/* 44 */     expandNegative(x, z);
/* 45 */     this.minY = MathUtil.floor(this.minY + y);
/*    */   }
/*    */   
/*    */   public void expandPositive(double x, double y, double z) {
/* 49 */     expandPositive(x, z);
/* 50 */     this.maxY = MathUtil.ceil(this.maxY + y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void include(@Nonnull IChunkBounds bounds) {
/* 55 */     super.include(bounds);
/* 56 */     if (bounds instanceof IWorldBounds) { IWorldBounds worldBounds = (IWorldBounds)bounds;
/* 57 */       if (this.minY > worldBounds.getLowBoundY()) this.minY = worldBounds.getLowBoundY(); 
/* 58 */       if (this.maxY < worldBounds.getHighBoundY()) this.maxY = worldBounds.getHighBoundY();  }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\bounds\WorldBounds.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */