/*     */ package io.netty.buffer;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public interface PoolArenaMetric
/*     */   extends SizeClassesMetric
/*     */ {
/*     */   int numThreadCaches();
/*     */   
/*     */   @Deprecated
/*     */   int numTinySubpages();
/*     */   
/*     */   int numSmallSubpages();
/*     */   
/*     */   int numChunkLists();
/*     */   
/*     */   @Deprecated
/*     */   List<PoolSubpageMetric> tinySubpages();
/*     */   
/*     */   List<PoolSubpageMetric> smallSubpages();
/*     */   
/*     */   List<PoolChunkListMetric> chunkLists();
/*     */   
/*     */   long numAllocations();
/*     */   
/*     */   @Deprecated
/*     */   long numTinyAllocations();
/*     */   
/*     */   long numSmallAllocations();
/*     */   
/*     */   long numNormalAllocations();
/*     */   
/*     */   long numHugeAllocations();
/*     */   
/*     */   default long numChunkAllocations() {
/*  99 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long numDeallocations();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   long numTinyDeallocations();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long numSmallDeallocations();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long numNormalDeallocations();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long numHugeDeallocations();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default long numChunkDeallocations() {
/* 134 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long numActiveAllocations();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   long numActiveTinyAllocations();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long numActiveSmallAllocations();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long numActiveNormalAllocations();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long numActiveHugeAllocations();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default long numActiveChunks() {
/* 169 */     return -1L;
/*     */   }
/*     */   
/*     */   long numActiveBytes();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PoolArenaMetric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */