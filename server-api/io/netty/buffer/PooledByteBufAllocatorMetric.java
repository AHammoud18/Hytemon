/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public final class PooledByteBufAllocatorMetric
/*     */   implements ByteBufAllocatorMetric
/*     */ {
/*     */   private final PooledByteBufAllocator allocator;
/*     */   
/*     */   PooledByteBufAllocatorMetric(PooledByteBufAllocator allocator) {
/*  31 */     this.allocator = allocator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int numHeapArenas() {
/*  38 */     return this.allocator.numHeapArenas();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int numDirectArenas() {
/*  45 */     return this.allocator.numDirectArenas();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PoolArenaMetric> heapArenas() {
/*  52 */     return this.allocator.heapArenas();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PoolArenaMetric> directArenas() {
/*  59 */     return this.allocator.directArenas();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int numThreadLocalCaches() {
/*  66 */     return this.allocator.numThreadLocalCaches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int tinyCacheSize() {
/*  76 */     return this.allocator.tinyCacheSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int smallCacheSize() {
/*  83 */     return this.allocator.smallCacheSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int normalCacheSize() {
/*  90 */     return this.allocator.normalCacheSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int chunkSize() {
/*  97 */     return this.allocator.chunkSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public long usedHeapMemory() {
/* 102 */     return this.allocator.usedHeapMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public long usedDirectMemory() {
/* 107 */     return this.allocator.usedDirectMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 112 */     StringBuilder sb = new StringBuilder(256);
/* 113 */     sb.append(StringUtil.simpleClassName(this))
/* 114 */       .append("(usedHeapMemory: ").append(usedHeapMemory())
/* 115 */       .append("; usedDirectMemory: ").append(usedDirectMemory())
/* 116 */       .append("; numHeapArenas: ").append(numHeapArenas())
/* 117 */       .append("; numDirectArenas: ").append(numDirectArenas())
/* 118 */       .append("; smallCacheSize: ").append(smallCacheSize())
/* 119 */       .append("; normalCacheSize: ").append(normalCacheSize())
/* 120 */       .append("; numThreadLocalCaches: ").append(numThreadLocalCaches())
/* 121 */       .append("; chunkSize: ").append(chunkSize()).append(')');
/* 122 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PooledByteBufAllocatorMetric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */