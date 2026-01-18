/*     */ package io.netty.buffer;
/*     */ 
/*     */ import java.util.concurrent.locks.ReentrantLock;
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
/*     */ final class PoolSubpage<T>
/*     */   implements PoolSubpageMetric
/*     */ {
/*     */   final PoolChunk<T> chunk;
/*     */   final int elemSize;
/*     */   private final int pageShifts;
/*     */   private final int runOffset;
/*     */   private final int runSize;
/*     */   private final long[] bitmap;
/*     */   private final int bitmapLength;
/*     */   private final int maxNumElems;
/*     */   final int headIndex;
/*     */   PoolSubpage<T> prev;
/*     */   PoolSubpage<T> next;
/*     */   boolean doNotDestroy;
/*     */   private int nextAvail;
/*     */   private int numAvail;
/*     */   final ReentrantLock lock;
/*     */   
/*     */   PoolSubpage(int headIndex) {
/*  52 */     this.chunk = null;
/*  53 */     this.lock = new ReentrantLock();
/*  54 */     this.pageShifts = -1;
/*  55 */     this.runOffset = -1;
/*  56 */     this.elemSize = -1;
/*  57 */     this.runSize = -1;
/*  58 */     this.bitmap = null;
/*  59 */     this.bitmapLength = -1;
/*  60 */     this.maxNumElems = 0;
/*  61 */     this.headIndex = headIndex;
/*     */   }
/*     */   
/*     */   PoolSubpage(PoolSubpage<T> head, PoolChunk<T> chunk, int pageShifts, int runOffset, int runSize, int elemSize) {
/*  65 */     this.headIndex = head.headIndex;
/*  66 */     this.chunk = chunk;
/*  67 */     this.pageShifts = pageShifts;
/*  68 */     this.runOffset = runOffset;
/*  69 */     this.runSize = runSize;
/*  70 */     this.elemSize = elemSize;
/*     */     
/*  72 */     this.doNotDestroy = true;
/*     */     
/*  74 */     this.maxNumElems = this.numAvail = runSize / elemSize;
/*  75 */     int bitmapLength = this.maxNumElems >>> 6;
/*  76 */     if ((this.maxNumElems & 0x3F) != 0) {
/*  77 */       bitmapLength++;
/*     */     }
/*  79 */     this.bitmapLength = bitmapLength;
/*  80 */     this.bitmap = new long[bitmapLength];
/*  81 */     this.nextAvail = 0;
/*     */     
/*  83 */     this.lock = null;
/*  84 */     addToPool(head);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long allocate() {
/*  91 */     if (this.numAvail == 0 || !this.doNotDestroy) {
/*  92 */       return -1L;
/*     */     }
/*     */     
/*  95 */     int bitmapIdx = getNextAvail();
/*  96 */     if (bitmapIdx < 0) {
/*  97 */       removeFromPool();
/*  98 */       throw new AssertionError("No next available bitmap index found (bitmapIdx = " + bitmapIdx + "), even though there are supposed to be (numAvail = " + this.numAvail + ") out of (maxNumElems = " + this.maxNumElems + ") available indexes.");
/*     */     } 
/*     */ 
/*     */     
/* 102 */     int q = bitmapIdx >>> 6;
/* 103 */     int r = bitmapIdx & 0x3F;
/* 104 */     assert (this.bitmap[q] >>> r & 0x1L) == 0L;
/* 105 */     this.bitmap[q] = this.bitmap[q] | 1L << r;
/*     */     
/* 107 */     if (--this.numAvail == 0) {
/* 108 */       removeFromPool();
/*     */     }
/*     */     
/* 111 */     return toHandle(bitmapIdx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean free(PoolSubpage<T> head, int bitmapIdx) {
/* 119 */     int q = bitmapIdx >>> 6;
/* 120 */     int r = bitmapIdx & 0x3F;
/* 121 */     assert (this.bitmap[q] >>> r & 0x1L) != 0L;
/* 122 */     this.bitmap[q] = this.bitmap[q] ^ 1L << r;
/*     */     
/* 124 */     setNextAvail(bitmapIdx);
/*     */     
/* 126 */     if (this.numAvail++ == 0) {
/* 127 */       addToPool(head);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       if (this.maxNumElems > 1) {
/* 133 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 137 */     if (this.numAvail != this.maxNumElems) {
/* 138 */       return true;
/*     */     }
/*     */     
/* 141 */     if (this.prev == this.next)
/*     */     {
/* 143 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 147 */     this.doNotDestroy = false;
/* 148 */     removeFromPool();
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToPool(PoolSubpage<T> head) {
/* 154 */     assert this.prev == null && this.next == null;
/* 155 */     this.prev = head;
/* 156 */     this.next = head.next;
/* 157 */     this.next.prev = this;
/* 158 */     head.next = this;
/*     */   }
/*     */   
/*     */   private void removeFromPool() {
/* 162 */     assert this.prev != null && this.next != null;
/* 163 */     this.prev.next = this.next;
/* 164 */     this.next.prev = this.prev;
/* 165 */     this.next = null;
/* 166 */     this.prev = null;
/*     */   }
/*     */   
/*     */   private void setNextAvail(int bitmapIdx) {
/* 170 */     this.nextAvail = bitmapIdx;
/*     */   }
/*     */   
/*     */   private int getNextAvail() {
/* 174 */     int nextAvail = this.nextAvail;
/* 175 */     if (nextAvail >= 0) {
/* 176 */       this.nextAvail = -1;
/* 177 */       return nextAvail;
/*     */     } 
/* 179 */     return findNextAvail();
/*     */   }
/*     */   
/*     */   private int findNextAvail() {
/* 183 */     for (int i = 0; i < this.bitmapLength; i++) {
/* 184 */       long bits = this.bitmap[i];
/* 185 */       if ((bits ^ 0xFFFFFFFFFFFFFFFFL) != 0L) {
/* 186 */         return findNextAvail0(i, bits);
/*     */       }
/*     */     } 
/* 189 */     return -1;
/*     */   }
/*     */   
/*     */   private int findNextAvail0(int i, long bits) {
/* 193 */     int baseVal = i << 6;
/* 194 */     for (int j = 0; j < 64; j++) {
/* 195 */       if ((bits & 0x1L) == 0L) {
/* 196 */         int val = baseVal | j;
/* 197 */         if (val < this.maxNumElems) {
/* 198 */           return val;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/* 203 */       bits >>>= 1L;
/*     */     } 
/* 205 */     return -1;
/*     */   }
/*     */   
/*     */   private long toHandle(int bitmapIdx) {
/* 209 */     int pages = this.runSize >> this.pageShifts;
/* 210 */     return this.runOffset << 49L | pages << 34L | 0x200000000L | 0x100000000L | bitmapIdx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     int numAvail;
/* 220 */     if (this.chunk == null) {
/*     */       
/* 222 */       numAvail = 0;
/*     */     } else {
/*     */       boolean doNotDestroy;
/* 225 */       PoolSubpage<T> head = this.chunk.arena.smallSubpagePools[this.headIndex];
/* 226 */       head.lock();
/*     */       try {
/* 228 */         doNotDestroy = this.doNotDestroy;
/* 229 */         numAvail = this.numAvail;
/*     */       } finally {
/* 231 */         head.unlock();
/*     */       } 
/* 233 */       if (!doNotDestroy)
/*     */       {
/* 235 */         return "(" + this.runOffset + ": not in use)";
/*     */       }
/*     */     } 
/*     */     
/* 239 */     return "(" + this.runOffset + ": " + (this.maxNumElems - numAvail) + '/' + this.maxNumElems + ", offset: " + this.runOffset + ", length: " + this.runSize + ", elemSize: " + this.elemSize + ')';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxNumElements() {
/* 245 */     return this.maxNumElems;
/*     */   }
/*     */ 
/*     */   
/*     */   public int numAvailable() {
/* 250 */     if (this.chunk == null)
/*     */     {
/* 252 */       return 0;
/*     */     }
/* 254 */     PoolSubpage<T> head = this.chunk.arena.smallSubpagePools[this.headIndex];
/* 255 */     head.lock();
/*     */     try {
/* 257 */       return this.numAvail;
/*     */     } finally {
/* 259 */       head.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int elementSize() {
/* 265 */     return this.elemSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int pageSize() {
/* 270 */     return 1 << this.pageShifts;
/*     */   }
/*     */   
/*     */   boolean isDoNotDestroy() {
/* 274 */     if (this.chunk == null)
/*     */     {
/* 276 */       return true;
/*     */     }
/* 278 */     PoolSubpage<T> head = this.chunk.arena.smallSubpagePools[this.headIndex];
/* 279 */     head.lock();
/*     */     try {
/* 281 */       return this.doNotDestroy;
/*     */     } finally {
/* 283 */       head.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   void destroy() {
/* 288 */     if (this.chunk != null) {
/* 289 */       this.chunk.destroy();
/*     */     }
/*     */   }
/*     */   
/*     */   void lock() {
/* 294 */     this.lock.lock();
/*     */   }
/*     */   
/*     */   void unlock() {
/* 298 */     this.lock.unlock();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PoolSubpage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */