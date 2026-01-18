/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class CharHeaps
/*     */ {
/*     */   public static int downHeap(char[] heap, int size, int i, CharComparator c) {
/*  41 */     assert i < size;
/*  42 */     char e = heap[i];
/*     */     
/*  44 */     if (c == null) { int child; while ((child = (i << 1) + 1) < size) {
/*  45 */         char t = heap[child];
/*  46 */         int right = child + 1;
/*  47 */         if (right < size && heap[right] < t) t = heap[child = right]; 
/*  48 */         if (e <= t)
/*  49 */           break;  heap[i] = t;
/*  50 */         i = child;
/*     */       }  }
/*  52 */     else { int child; while ((child = (i << 1) + 1) < size) {
/*  53 */         char t = heap[child];
/*  54 */         int right = child + 1;
/*  55 */         if (right < size && c.compare(heap[right], t) < 0) t = heap[child = right]; 
/*  56 */         if (c.compare(e, t) <= 0)
/*  57 */           break;  heap[i] = t;
/*  58 */         i = child;
/*     */       }  }
/*  60 */      heap[i] = e;
/*  61 */     return i;
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
/*     */   public static int upHeap(char[] heap, int size, int i, CharComparator c) {
/*  75 */     assert i < size;
/*  76 */     char e = heap[i];
/*  77 */     if (c == null) { while (i != 0) {
/*  78 */         int parent = i - 1 >>> 1;
/*  79 */         char t = heap[parent];
/*  80 */         if (t <= e)
/*  81 */           break;  heap[i] = t;
/*  82 */         i = parent;
/*     */       }  }
/*  84 */     else { while (i != 0) {
/*  85 */         int parent = i - 1 >>> 1;
/*  86 */         char t = heap[parent];
/*  87 */         if (c.compare(t, e) <= 0)
/*  88 */           break;  heap[i] = t;
/*  89 */         i = parent;
/*     */       }  }
/*  91 */      heap[i] = e;
/*  92 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void makeHeap(char[] heap, int size, CharComparator c) {
/* 103 */     int i = size >>> 1;
/* 104 */     for (; i-- != 0; downHeap(heap, size, i, c));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */