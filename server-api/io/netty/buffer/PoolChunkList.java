/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
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
/*     */ final class PoolChunkList<T>
/*     */   implements PoolChunkListMetric
/*     */ {
/*  31 */   private static final Iterator<PoolChunkMetric> EMPTY_METRICS = Collections.emptyIterator();
/*     */   
/*     */   private final PoolArena<T> arena;
/*     */   
/*     */   private final PoolChunkList<T> nextList;
/*     */   
/*     */   private final int minUsage;
/*     */   
/*     */   private final int maxUsage;
/*     */   
/*     */   private final int maxCapacity;
/*     */   private PoolChunk<T> head;
/*     */   private final int freeMinThreshold;
/*     */   private final int freeMaxThreshold;
/*     */   private PoolChunkList<T> prevList;
/*     */   
/*     */   PoolChunkList(PoolArena<T> arena, PoolChunkList<T> nextList, int minUsage, int maxUsage, int chunkSize) {
/*  48 */     assert minUsage <= maxUsage;
/*  49 */     this.arena = arena;
/*  50 */     this.nextList = nextList;
/*  51 */     this.minUsage = minUsage;
/*  52 */     this.maxUsage = maxUsage;
/*  53 */     this.maxCapacity = calculateMaxCapacity(minUsage, chunkSize);
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
/*  70 */     this.freeMinThreshold = (maxUsage == 100) ? 0 : (int)(chunkSize * (100.0D - maxUsage + 0.99999999D) / 100.0D);
/*  71 */     this.freeMaxThreshold = (minUsage == 100) ? 0 : (int)(chunkSize * (100.0D - minUsage + 0.99999999D) / 100.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int calculateMaxCapacity(int minUsage, int chunkSize) {
/*  79 */     minUsage = minUsage0(minUsage);
/*     */     
/*  81 */     if (minUsage == 100)
/*     */     {
/*  83 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     return (int)(chunkSize * (100L - minUsage) / 100L);
/*     */   }
/*     */   
/*     */   void prevList(PoolChunkList<T> prevList) {
/*  95 */     assert this.prevList == null;
/*  96 */     this.prevList = prevList;
/*     */   }
/*     */   
/*     */   boolean allocate(PooledByteBuf<T> buf, int reqCapacity, int sizeIdx, PoolThreadCache threadCache) {
/* 100 */     int normCapacity = this.arena.sizeClass.sizeIdx2size(sizeIdx);
/* 101 */     if (normCapacity > this.maxCapacity)
/*     */     {
/*     */       
/* 104 */       return false;
/*     */     }
/*     */     
/* 107 */     for (PoolChunk<T> cur = this.head; cur != null; cur = cur.next) {
/* 108 */       if (cur.allocate(buf, reqCapacity, sizeIdx, threadCache)) {
/* 109 */         if (cur.freeBytes <= this.freeMinThreshold) {
/* 110 */           remove(cur);
/* 111 */           this.nextList.add(cur);
/*     */         } 
/* 113 */         return true;
/*     */       } 
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   boolean free(PoolChunk<T> chunk, long handle, int normCapacity, ByteBuffer nioBuffer) {
/* 120 */     chunk.free(handle, normCapacity, nioBuffer);
/* 121 */     if (chunk.freeBytes > this.freeMaxThreshold) {
/* 122 */       remove(chunk);
/*     */       
/* 124 */       return move0(chunk);
/*     */     } 
/* 126 */     return true;
/*     */   }
/*     */   
/*     */   private boolean move(PoolChunk<T> chunk) {
/* 130 */     assert chunk.usage() < this.maxUsage;
/*     */     
/* 132 */     if (chunk.freeBytes > this.freeMaxThreshold)
/*     */     {
/* 134 */       return move0(chunk);
/*     */     }
/*     */ 
/*     */     
/* 138 */     add0(chunk);
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean move0(PoolChunk<T> chunk) {
/* 147 */     if (this.prevList == null) {
/*     */ 
/*     */       
/* 150 */       assert chunk.usage() == 0;
/* 151 */       return false;
/*     */     } 
/* 153 */     return this.prevList.move(chunk);
/*     */   }
/*     */   
/*     */   void add(PoolChunk<T> chunk) {
/* 157 */     if (chunk.freeBytes <= this.freeMinThreshold) {
/* 158 */       this.nextList.add(chunk);
/*     */       return;
/*     */     } 
/* 161 */     add0(chunk);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void add0(PoolChunk<T> chunk) {
/* 168 */     chunk.parent = this;
/* 169 */     if (this.head == null) {
/* 170 */       this.head = chunk;
/* 171 */       chunk.prev = null;
/* 172 */       chunk.next = null;
/*     */     } else {
/* 174 */       chunk.prev = null;
/* 175 */       chunk.next = this.head;
/* 176 */       this.head.prev = chunk;
/* 177 */       this.head = chunk;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void remove(PoolChunk<T> cur) {
/* 182 */     if (cur == this.head) {
/* 183 */       this.head = cur.next;
/* 184 */       if (this.head != null) {
/* 185 */         this.head.prev = null;
/*     */       }
/*     */     } else {
/* 188 */       PoolChunk<T> next = cur.next;
/* 189 */       cur.prev.next = next;
/* 190 */       if (next != null) {
/* 191 */         next.prev = cur.prev;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int minUsage() {
/* 198 */     return minUsage0(this.minUsage);
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxUsage() {
/* 203 */     return Math.min(this.maxUsage, 100);
/*     */   }
/*     */   
/*     */   private static int minUsage0(int value) {
/* 207 */     return Math.max(1, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<PoolChunkMetric> iterator() {
/* 212 */     this.arena.lock();
/*     */     try {
/* 214 */       if (this.head == null) {
/* 215 */         return EMPTY_METRICS;
/*     */       }
/* 217 */       List<PoolChunkMetric> metrics = new ArrayList<>();
/* 218 */       PoolChunk<T> cur = this.head; do {
/* 219 */         metrics.add(cur);
/* 220 */         cur = cur.next;
/* 221 */       } while (cur != null);
/*     */ 
/*     */ 
/*     */       
/* 225 */       return metrics.iterator();
/*     */     } finally {
/* 227 */       this.arena.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 233 */     StringBuilder buf = new StringBuilder();
/* 234 */     this.arena.lock();
/*     */     try {
/* 236 */       if (this.head == null) {
/* 237 */         return "none";
/*     */       }
/*     */       
/* 240 */       PoolChunk<T> cur = this.head; while (true) {
/* 241 */         buf.append(cur);
/* 242 */         cur = cur.next;
/* 243 */         if (cur == null) {
/*     */           break;
/*     */         }
/* 246 */         buf.append(StringUtil.NEWLINE);
/*     */       } 
/*     */     } finally {
/* 249 */       this.arena.unlock();
/*     */     } 
/* 251 */     return buf.toString();
/*     */   }
/*     */   
/*     */   void destroy(PoolArena<T> arena) {
/* 255 */     PoolChunk<T> chunk = this.head;
/* 256 */     while (chunk != null) {
/* 257 */       arena.destroyChunk(chunk);
/* 258 */       chunk = chunk.next;
/*     */     } 
/* 260 */     this.head = null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PoolChunkList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */