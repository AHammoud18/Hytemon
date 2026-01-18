/*    */ package com.hypixel.hytale.server.core.modules.physics;
/*    */ 
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RestingSupport
/*    */ {
/*    */   protected int supportMinX;
/*    */   protected int supportMaxX;
/*    */   protected int supportMinZ;
/*    */   protected int supportMaxZ;
/*    */   protected int supportMinY;
/*    */   protected int supportMaxY;
/*    */   @Nullable
/*    */   protected int[] supportBlocks;
/*    */   
/*    */   public boolean hasChanged(@Nonnull World world) {
/* 27 */     if (this.supportBlocks == null) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     int index = 0;
/* 32 */     for (int z = this.supportMinZ; z <= this.supportMaxZ; z++) {
/* 33 */       for (int x = this.supportMinX; x <= this.supportMaxX; x++) {
/* 34 */         WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 35 */         if (chunk != null) {
/* 36 */           for (int y = this.supportMinY; y <= this.supportMaxY; y++) {
/* 37 */             if (this.supportBlocks[index++] != chunk.getBlock(x, y, z)) {
/* 38 */               return true;
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   public void rest(@Nonnull World world, @Nonnull Box boundingBox, @Nonnull Vector3d position) {
/* 48 */     if (this.supportBlocks == null) {
/* 49 */       int maxSize = (int)(Math.ceil(boundingBox.width() + 1.0D) * Math.ceil(boundingBox.depth() + 1.0D) * Math.ceil(boundingBox.height() + 1.0D));
/* 50 */       this.supportBlocks = new int[maxSize];
/*    */     } 
/*    */     
/* 53 */     this.supportMinX = MathUtil.floor(position.x + boundingBox.min.x);
/* 54 */     this.supportMaxX = MathUtil.floor(position.x + boundingBox.max.x);
/* 55 */     this.supportMinZ = MathUtil.floor(position.z + boundingBox.min.z);
/* 56 */     this.supportMaxZ = MathUtil.floor(position.z + boundingBox.max.z);
/* 57 */     this.supportMinY = MathUtil.floor(position.y + boundingBox.min.y);
/* 58 */     this.supportMaxY = MathUtil.floor(position.y + boundingBox.max.y);
/*    */ 
/*    */     
/* 61 */     int index = 0;
/* 62 */     for (int z = this.supportMinZ; z <= this.supportMaxZ; z++) {
/* 63 */       for (int x = this.supportMinX; x <= this.supportMaxX; x++) {
/* 64 */         WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 65 */         if (chunk != null) {
/* 66 */           for (int y = this.supportMinY; y <= this.supportMaxY; y++) {
/* 67 */             this.supportBlocks[index++] = chunk.getBlock(x, y, z);
/*    */           }
/*    */         } else {
/* 70 */           for (int y = this.supportMinY; y <= this.supportMaxY; y++) {
/* 71 */             this.supportBlocks[index++] = 1;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clear() {
/* 79 */     this.supportBlocks = null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physics\RestingSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */