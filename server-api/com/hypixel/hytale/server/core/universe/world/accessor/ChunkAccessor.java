/*    */ package com.hypixel.hytale.server.core.universe.world.accessor;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public interface ChunkAccessor<WorldChunk extends BlockAccessor>
/*    */   extends IChunkAccessorSync<WorldChunk>
/*    */ {
/*    */   default int getFluidId(int x, int y, int z) {
/* 15 */     WorldChunk chunk = getChunk(ChunkUtil.indexChunkFromBlock(x, z));
/* 16 */     return (chunk != null) ? chunk.getFluidId(x, y, z) : 0;
/*    */   }
/*    */   
/*    */   default boolean performBlockUpdate(int x, int y, int z) {
/* 20 */     return performBlockUpdate(x, y, z, true);
/*    */   }
/*    */   
/*    */   default boolean performBlockUpdate(int x, int y, int z, boolean allowPartialLoad) {
/* 24 */     boolean success = true;
/* 25 */     for (int ix = -1; ix < 2; ix++) {
/* 26 */       int wx = x + ix;
/* 27 */       for (int iz = -1; iz < 2; iz++) {
/* 28 */         int wz = z + iz;
/*    */         
/* 30 */         WorldChunk worldChunk = allowPartialLoad ? getNonTickingChunk(ChunkUtil.indexChunkFromBlock(wx, wz)) : getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(wx, wz));
/* 31 */         if (worldChunk == null) {
/*    */           
/* 33 */           success = false;
/*    */         } else {
/*    */           
/* 36 */           for (int iy = -1; iy < 2; iy++) {
/* 37 */             int wy = y + iy;
/*    */             
/* 39 */             worldChunk.setTicking(wx, wy, wz, true);
/*    */           } 
/*    */         } 
/*    */       } 
/* 43 */     }  return success;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\accessor\ChunkAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */