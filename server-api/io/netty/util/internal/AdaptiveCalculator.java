/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public final class AdaptiveCalculator
/*     */ {
/*     */   private static final int INDEX_INCREMENT = 4;
/*     */   private static final int INDEX_DECREMENT = 1;
/*     */   private static final int[] SIZE_TABLE;
/*     */   private final int minIndex;
/*     */   private final int maxIndex;
/*     */   private final int minCapacity;
/*     */   private final int maxCapacity;
/*     */   private int index;
/*     */   private int nextSize;
/*     */   private boolean decreaseNow;
/*     */   
/*     */   static {
/*  35 */     List<Integer> sizeTable = new ArrayList<>(); int i;
/*  36 */     for (i = 16; i < 512; i += 16) {
/*  37 */       sizeTable.add(Integer.valueOf(i));
/*     */     }
/*     */ 
/*     */     
/*  41 */     for (i = 512; i > 0; i <<= 1) {
/*  42 */       sizeTable.add(Integer.valueOf(i));
/*     */     }
/*     */     
/*  45 */     SIZE_TABLE = new int[sizeTable.size()];
/*  46 */     for (i = 0; i < SIZE_TABLE.length; i++) {
/*  47 */       SIZE_TABLE[i] = ((Integer)sizeTable.get(i)).intValue();
/*     */     }
/*     */   }
/*     */   
/*     */   private static int getSizeTableIndex(int size) {
/*  52 */     int mid, a, low = 0, high = SIZE_TABLE.length - 1; while (true) {
/*  53 */       if (high < low) {
/*  54 */         return low;
/*     */       }
/*  56 */       if (high == low) {
/*  57 */         return high;
/*     */       }
/*     */       
/*  60 */       mid = low + high >>> 1;
/*  61 */       a = SIZE_TABLE[mid];
/*  62 */       int b = SIZE_TABLE[mid + 1];
/*  63 */       if (size > b) {
/*  64 */         low = mid + 1; continue;
/*  65 */       }  if (size < a)
/*  66 */       { high = mid - 1; continue; }  break;
/*  67 */     }  if (size == a) {
/*  68 */       return mid;
/*     */     }
/*  70 */     return mid + 1;
/*     */   }
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
/*     */   public AdaptiveCalculator(int minimum, int initial, int maximum) {
/*  84 */     ObjectUtil.checkPositive(minimum, "minimum");
/*  85 */     if (initial < minimum) {
/*  86 */       throw new IllegalArgumentException("initial: " + initial);
/*     */     }
/*  88 */     if (maximum < initial) {
/*  89 */       throw new IllegalArgumentException("maximum: " + maximum);
/*     */     }
/*     */     
/*  92 */     int minIndex = getSizeTableIndex(minimum);
/*  93 */     if (SIZE_TABLE[minIndex] < minimum) {
/*  94 */       this.minIndex = minIndex + 1;
/*     */     } else {
/*  96 */       this.minIndex = minIndex;
/*     */     } 
/*     */     
/*  99 */     int maxIndex = getSizeTableIndex(maximum);
/* 100 */     if (SIZE_TABLE[maxIndex] > maximum) {
/* 101 */       this.maxIndex = maxIndex - 1;
/*     */     } else {
/* 103 */       this.maxIndex = maxIndex;
/*     */     } 
/*     */     
/* 106 */     int initialIndex = getSizeTableIndex(initial);
/* 107 */     if (SIZE_TABLE[initialIndex] > initial) {
/* 108 */       this.index = initialIndex - 1;
/*     */     } else {
/* 110 */       this.index = initialIndex;
/*     */     } 
/* 112 */     this.minCapacity = minimum;
/* 113 */     this.maxCapacity = maximum;
/* 114 */     this.nextSize = Math.max(SIZE_TABLE[this.index], this.minCapacity);
/*     */   }
/*     */   
/*     */   public void record(int size) {
/* 118 */     if (size <= SIZE_TABLE[Math.max(0, this.index - 1)]) {
/* 119 */       if (this.decreaseNow) {
/* 120 */         this.index = Math.max(this.index - 1, this.minIndex);
/* 121 */         this.nextSize = Math.max(SIZE_TABLE[this.index], this.minCapacity);
/* 122 */         this.decreaseNow = false;
/*     */       } else {
/* 124 */         this.decreaseNow = true;
/*     */       } 
/* 126 */     } else if (size >= this.nextSize) {
/* 127 */       this.index = Math.min(this.index + 4, this.maxIndex);
/* 128 */       this.nextSize = Math.min(SIZE_TABLE[this.index], this.maxCapacity);
/* 129 */       this.decreaseNow = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int nextSize() {
/* 134 */     return this.nextSize;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\AdaptiveCalculator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */