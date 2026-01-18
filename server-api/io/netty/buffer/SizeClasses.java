/*     */ package io.netty.buffer;
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
/*     */ final class SizeClasses
/*     */   implements SizeClassesMetric
/*     */ {
/*     */   static final int LOG2_QUANTUM = 4;
/*     */   private static final int LOG2_SIZE_CLASS_GROUP = 2;
/*     */   private static final int LOG2_MAX_LOOKUP_SIZE = 12;
/*     */   private static final int LOG2GROUP_IDX = 1;
/*     */   private static final int LOG2DELTA_IDX = 2;
/*     */   private static final int NDELTA_IDX = 3;
/*     */   private static final int PAGESIZE_IDX = 4;
/*     */   private static final int SUBPAGE_IDX = 5;
/*     */   private static final int LOG2_DELTA_LOOKUP_IDX = 6;
/*     */   private static final byte no = 0;
/*     */   private static final byte yes = 1;
/*     */   final int pageSize;
/*     */   final int pageShifts;
/*     */   final int chunkSize;
/*     */   final int directMemoryCacheAlignment;
/*     */   final int nSizes;
/*     */   final int nSubpages;
/*     */   final int nPSizes;
/*     */   final int lookupMaxSize;
/*     */   final int smallMaxSizeIdx;
/*     */   private final int[] pageIdx2sizeTab;
/*     */   private final int[] sizeIdx2sizeTab;
/*     */   private final int[] size2idxTab;
/*     */   
/*     */   SizeClasses(int pageSize, int pageShifts, int chunkSize, int directMemoryCacheAlignment) {
/* 118 */     int group = PoolThreadCache.log2(chunkSize) - 4 - 2 + 1;
/*     */ 
/*     */ 
/*     */     
/* 122 */     short[][] sizeClasses = new short[group << 2][7];
/*     */     
/* 124 */     int normalMaxSize = -1;
/* 125 */     int nSizes = 0;
/* 126 */     int size = 0;
/*     */     
/* 128 */     int log2Group = 4;
/* 129 */     int log2Delta = 4;
/* 130 */     int ndeltaLimit = 4;
/*     */     
/*     */     int nDelta;
/*     */     
/* 134 */     for (nDelta = 0; nDelta < ndeltaLimit; nDelta++, nSizes++) {
/* 135 */       short[] sizeClass = newSizeClass(nSizes, log2Group, log2Delta, nDelta, pageShifts);
/* 136 */       sizeClasses[nSizes] = sizeClass;
/* 137 */       size = sizeOf(sizeClass, directMemoryCacheAlignment);
/*     */     } 
/*     */     
/* 140 */     log2Group += 2;
/*     */ 
/*     */     
/* 143 */     for (; size < chunkSize; log2Group++, log2Delta++) {
/* 144 */       for (nDelta = 1; nDelta <= ndeltaLimit && size < chunkSize; nDelta++, nSizes++) {
/* 145 */         short[] sizeClass = newSizeClass(nSizes, log2Group, log2Delta, nDelta, pageShifts);
/* 146 */         sizeClasses[nSizes] = sizeClass;
/* 147 */         size = normalMaxSize = sizeOf(sizeClass, directMemoryCacheAlignment);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 152 */     assert chunkSize == normalMaxSize;
/*     */     
/* 154 */     int smallMaxSizeIdx = 0;
/* 155 */     int lookupMaxSize = 0;
/* 156 */     int nPSizes = 0;
/* 157 */     int nSubpages = 0;
/* 158 */     for (int idx = 0; idx < nSizes; idx++) {
/* 159 */       short[] sz = sizeClasses[idx];
/* 160 */       if (sz[4] == 1) {
/* 161 */         nPSizes++;
/*     */       }
/* 163 */       if (sz[5] == 1) {
/* 164 */         nSubpages++;
/* 165 */         smallMaxSizeIdx = idx;
/*     */       } 
/* 167 */       if (sz[6] != 0) {
/* 168 */         lookupMaxSize = sizeOf(sz, directMemoryCacheAlignment);
/*     */       }
/*     */     } 
/* 171 */     this.smallMaxSizeIdx = smallMaxSizeIdx;
/* 172 */     this.lookupMaxSize = lookupMaxSize;
/* 173 */     this.nPSizes = nPSizes;
/* 174 */     this.nSubpages = nSubpages;
/* 175 */     this.nSizes = nSizes;
/*     */     
/* 177 */     this.pageSize = pageSize;
/* 178 */     this.pageShifts = pageShifts;
/* 179 */     this.chunkSize = chunkSize;
/* 180 */     this.directMemoryCacheAlignment = directMemoryCacheAlignment;
/*     */ 
/*     */     
/* 183 */     this.sizeIdx2sizeTab = newIdx2SizeTab(sizeClasses, nSizes, directMemoryCacheAlignment);
/* 184 */     this.pageIdx2sizeTab = newPageIdx2sizeTab(sizeClasses, nSizes, nPSizes, directMemoryCacheAlignment);
/* 185 */     this.size2idxTab = newSize2idxTab(lookupMaxSize, sizeClasses);
/*     */   }
/*     */ 
/*     */   
/*     */   private static short[] newSizeClass(int index, int log2Group, int log2Delta, int nDelta, int pageShifts) {
/*     */     short isMultiPageSize;
/* 191 */     if (log2Delta >= pageShifts) {
/* 192 */       isMultiPageSize = 1;
/*     */     } else {
/* 194 */       int pageSize = 1 << pageShifts;
/* 195 */       int size = calculateSize(log2Group, nDelta, log2Delta);
/*     */       
/* 197 */       isMultiPageSize = (size == size / pageSize * pageSize) ? 1 : 0;
/*     */     } 
/*     */     
/* 200 */     int log2Ndelta = (nDelta == 0) ? 0 : PoolThreadCache.log2(nDelta);
/*     */     
/* 202 */     byte remove = (1 << log2Ndelta < nDelta) ? 1 : 0;
/*     */     
/* 204 */     int log2Size = (log2Delta + log2Ndelta == log2Group) ? (log2Group + 1) : log2Group;
/* 205 */     if (log2Size == log2Group) {
/* 206 */       remove = 1;
/*     */     }
/*     */     
/* 209 */     short isSubpage = (log2Size < pageShifts + 2) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 213 */     int log2DeltaLookup = (log2Size < 12 || (log2Size == 12 && remove == 0)) ? log2Delta : 0;
/*     */     
/* 215 */     return new short[] { (short)index, (short)log2Group, (short)log2Delta, (short)nDelta, isMultiPageSize, isSubpage, (short)log2DeltaLookup };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] newIdx2SizeTab(short[][] sizeClasses, int nSizes, int directMemoryCacheAlignment) {
/* 222 */     int[] sizeIdx2sizeTab = new int[nSizes];
/*     */     
/* 224 */     for (int i = 0; i < nSizes; i++) {
/* 225 */       short[] sizeClass = sizeClasses[i];
/* 226 */       sizeIdx2sizeTab[i] = sizeOf(sizeClass, directMemoryCacheAlignment);
/*     */     } 
/* 228 */     return sizeIdx2sizeTab;
/*     */   }
/*     */   
/*     */   private static int calculateSize(int log2Group, int nDelta, int log2Delta) {
/* 232 */     return (1 << log2Group) + (nDelta << log2Delta);
/*     */   }
/*     */   
/*     */   private static int sizeOf(short[] sizeClass, int directMemoryCacheAlignment) {
/* 236 */     int log2Group = sizeClass[1];
/* 237 */     int log2Delta = sizeClass[2];
/* 238 */     int nDelta = sizeClass[3];
/*     */     
/* 240 */     int size = calculateSize(log2Group, nDelta, log2Delta);
/*     */     
/* 242 */     return alignSizeIfNeeded(size, directMemoryCacheAlignment);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] newPageIdx2sizeTab(short[][] sizeClasses, int nSizes, int nPSizes, int directMemoryCacheAlignment) {
/* 247 */     int[] pageIdx2sizeTab = new int[nPSizes];
/* 248 */     int pageIdx = 0;
/* 249 */     for (int i = 0; i < nSizes; i++) {
/* 250 */       short[] sizeClass = sizeClasses[i];
/* 251 */       if (sizeClass[4] == 1) {
/* 252 */         pageIdx2sizeTab[pageIdx++] = sizeOf(sizeClass, directMemoryCacheAlignment);
/*     */       }
/*     */     } 
/* 255 */     return pageIdx2sizeTab;
/*     */   }
/*     */   
/*     */   private static int[] newSize2idxTab(int lookupMaxSize, short[][] sizeClasses) {
/* 259 */     int[] size2idxTab = new int[lookupMaxSize >> 4];
/* 260 */     int idx = 0;
/* 261 */     int size = 0;
/*     */     
/* 263 */     for (int i = 0; size <= lookupMaxSize; i++) {
/* 264 */       int log2Delta = sizeClasses[i][2];
/* 265 */       int times = 1 << log2Delta - 4;
/*     */       
/* 267 */       while (size <= lookupMaxSize && times-- > 0) {
/* 268 */         size2idxTab[idx++] = i;
/* 269 */         size = idx + 1 << 4;
/*     */       } 
/*     */     } 
/* 272 */     return size2idxTab;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeIdx2size(int sizeIdx) {
/* 277 */     return this.sizeIdx2sizeTab[sizeIdx];
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeIdx2sizeCompute(int sizeIdx) {
/* 282 */     int group = sizeIdx >> 2;
/* 283 */     int mod = sizeIdx & 0x3;
/*     */ 
/*     */     
/* 286 */     int groupSize = (group == 0) ? 0 : (32 << group);
/*     */     
/* 288 */     int shift = (group == 0) ? 1 : group;
/* 289 */     int lgDelta = shift + 4 - 1;
/* 290 */     int modSize = mod + 1 << lgDelta;
/*     */     
/* 292 */     return groupSize + modSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public long pageIdx2size(int pageIdx) {
/* 297 */     return this.pageIdx2sizeTab[pageIdx];
/*     */   }
/*     */ 
/*     */   
/*     */   public long pageIdx2sizeCompute(int pageIdx) {
/* 302 */     int group = pageIdx >> 2;
/* 303 */     int mod = pageIdx & 0x3;
/*     */ 
/*     */     
/* 306 */     long groupSize = (group == 0) ? 0L : (1L << this.pageShifts + 2 - 1 << group);
/*     */     
/* 308 */     int shift = (group == 0) ? 1 : group;
/* 309 */     int log2Delta = shift + this.pageShifts - 1;
/* 310 */     int modSize = mod + 1 << log2Delta;
/*     */     
/* 312 */     return groupSize + modSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size2SizeIdx(int size) {
/* 317 */     if (size == 0) {
/* 318 */       return 0;
/*     */     }
/* 320 */     if (size > this.chunkSize) {
/* 321 */       return this.nSizes;
/*     */     }
/*     */     
/* 324 */     size = alignSizeIfNeeded(size, this.directMemoryCacheAlignment);
/*     */     
/* 326 */     if (size <= this.lookupMaxSize)
/*     */     {
/* 328 */       return this.size2idxTab[size - 1 >> 4];
/*     */     }
/*     */     
/* 331 */     int x = PoolThreadCache.log2((size << 1) - 1);
/*     */     
/* 333 */     int shift = (x < 7) ? 0 : (x - 6);
/*     */     
/* 335 */     int group = shift << 2;
/*     */ 
/*     */     
/* 338 */     int log2Delta = (x < 7) ? 4 : (x - 2 - 1);
/*     */     
/* 340 */     int mod = size - 1 >> log2Delta & 0x3;
/*     */     
/* 342 */     return group + mod;
/*     */   }
/*     */ 
/*     */   
/*     */   public int pages2pageIdx(int pages) {
/* 347 */     return pages2pageIdxCompute(pages, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int pages2pageIdxFloor(int pages) {
/* 352 */     return pages2pageIdxCompute(pages, true);
/*     */   }
/*     */   
/*     */   private int pages2pageIdxCompute(int pages, boolean floor) {
/* 356 */     int pageSize = pages << this.pageShifts;
/* 357 */     if (pageSize > this.chunkSize) {
/* 358 */       return this.nPSizes;
/*     */     }
/*     */     
/* 361 */     int x = PoolThreadCache.log2((pageSize << 1) - 1);
/*     */ 
/*     */     
/* 364 */     int shift = (x < 2 + this.pageShifts) ? 0 : (x - 2 + this.pageShifts);
/*     */     
/* 366 */     int group = shift << 2;
/*     */ 
/*     */     
/* 369 */     int log2Delta = (x < 2 + this.pageShifts + 1) ? this.pageShifts : (x - 2 - 1);
/*     */     
/* 371 */     int mod = pageSize - 1 >> log2Delta & 0x3;
/*     */     
/* 373 */     int pageIdx = group + mod;
/*     */     
/* 375 */     if (floor && this.pageIdx2sizeTab[pageIdx] > pages << this.pageShifts) {
/* 376 */       pageIdx--;
/*     */     }
/*     */     
/* 379 */     return pageIdx;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int alignSizeIfNeeded(int size, int directMemoryCacheAlignment) {
/* 384 */     if (directMemoryCacheAlignment <= 0) {
/* 385 */       return size;
/*     */     }
/* 387 */     int delta = size & directMemoryCacheAlignment - 1;
/* 388 */     return (delta == 0) ? size : (size + directMemoryCacheAlignment - delta);
/*     */   }
/*     */ 
/*     */   
/*     */   public int normalizeSize(int size) {
/* 393 */     if (size == 0) {
/* 394 */       return this.sizeIdx2sizeTab[0];
/*     */     }
/* 396 */     size = alignSizeIfNeeded(size, this.directMemoryCacheAlignment);
/* 397 */     if (size <= this.lookupMaxSize) {
/* 398 */       int ret = this.sizeIdx2sizeTab[this.size2idxTab[size - 1 >> 4]];
/* 399 */       assert ret == normalizeSizeCompute(size);
/* 400 */       return ret;
/*     */     } 
/* 402 */     return normalizeSizeCompute(size);
/*     */   }
/*     */   
/*     */   private static int normalizeSizeCompute(int size) {
/* 406 */     int x = PoolThreadCache.log2((size << 1) - 1);
/*     */     
/* 408 */     int log2Delta = (x < 7) ? 4 : (x - 2 - 1);
/* 409 */     int delta = 1 << log2Delta;
/* 410 */     int delta_mask = delta - 1;
/* 411 */     return size + delta_mask & (delta_mask ^ 0xFFFFFFFF);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\SizeClasses.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */