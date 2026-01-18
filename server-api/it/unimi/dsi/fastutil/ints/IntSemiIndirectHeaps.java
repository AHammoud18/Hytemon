/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public final class IntSemiIndirectHeaps
/*     */ {
/*     */   public static int downHeap(int[] refArray, int[] heap, int size, int i, IntComparator c) {
/*  43 */     assert i < size;
/*  44 */     int e = heap[i];
/*  45 */     int E = refArray[e];
/*     */     
/*  47 */     if (c == null) { int child; while ((child = (i << 1) + 1) < size) {
/*  48 */         int t = heap[child];
/*  49 */         int right = child + 1;
/*  50 */         if (right < size && refArray[heap[right]] < refArray[t]) t = heap[child = right]; 
/*  51 */         if (E <= refArray[t])
/*  52 */           break;  heap[i] = t;
/*  53 */         i = child;
/*     */       }  }
/*  55 */     else { int child; while ((child = (i << 1) + 1) < size) {
/*  56 */         int t = heap[child];
/*  57 */         int right = child + 1;
/*  58 */         if (right < size && c.compare(refArray[heap[right]], refArray[t]) < 0) t = heap[child = right]; 
/*  59 */         if (c.compare(E, refArray[t]) <= 0)
/*  60 */           break;  heap[i] = t;
/*  61 */         i = child;
/*     */       }  }
/*  63 */      heap[i] = e;
/*  64 */     return i;
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
/*     */ 
/*     */   
/*     */   public static int upHeap(int[] refArray, int[] heap, int size, int i, IntComparator c) {
/*  80 */     assert i < size;
/*  81 */     int e = heap[i];
/*  82 */     int E = refArray[e];
/*  83 */     if (c == null) { while (i != 0) {
/*  84 */         int parent = i - 1 >>> 1;
/*  85 */         int t = heap[parent];
/*  86 */         if (refArray[t] <= E)
/*  87 */           break;  heap[i] = t;
/*  88 */         i = parent;
/*     */       }  }
/*  90 */     else { while (i != 0) {
/*  91 */         int parent = i - 1 >>> 1;
/*  92 */         int t = heap[parent];
/*  93 */         if (c.compare(refArray[t], E) <= 0)
/*  94 */           break;  heap[i] = t;
/*  95 */         i = parent;
/*     */       }  }
/*  97 */      heap[i] = e;
/*  98 */     return i;
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
/*     */   public static void makeHeap(int[] refArray, int offset, int length, int[] heap, IntComparator c) {
/* 111 */     IntArrays.ensureOffsetLength(refArray, offset, length);
/* 112 */     if (heap.length < length) throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")"); 
/* 113 */     int i = length;
/* 114 */     for (; i-- != 0; heap[i] = offset + i);
/* 115 */     i = length >>> 1;
/* 116 */     for (; i-- != 0; downHeap(refArray, heap, length, i, c));
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
/*     */   public static int[] makeHeap(int[] refArray, int offset, int length, IntComparator c) {
/* 129 */     int[] heap = (length <= 0) ? IntArrays.EMPTY_ARRAY : new int[length];
/* 130 */     makeHeap(refArray, offset, length, heap, c);
/* 131 */     return heap;
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
/*     */   public static void makeHeap(int[] refArray, int[] heap, int size, IntComparator c) {
/* 143 */     int i = size >>> 1;
/* 144 */     for (; i-- != 0; downHeap(refArray, heap, size, i, c));
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
/*     */   public static int front(int[] refArray, int[] heap, int size, int[] a) {
/* 169 */     int top = refArray[heap[0]];
/* 170 */     int j = 0;
/* 171 */     int l = 0;
/* 172 */     int r = 1;
/* 173 */     int f = 0;
/* 174 */     for (int i = 0; i < r; i++) {
/* 175 */       if (i == f) {
/* 176 */         if (l >= r)
/* 177 */           break;  f = (f << 1) + 1;
/* 178 */         i = l;
/* 179 */         l = -1;
/*     */       } 
/* 181 */       if (top == refArray[heap[i]]) {
/* 182 */         a[j++] = heap[i];
/* 183 */         if (l == -1) l = i * 2 + 1; 
/* 184 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 187 */     return j;
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
/*     */   public static int front(int[] refArray, int[] heap, int size, int[] a, IntComparator c) {
/* 212 */     int top = refArray[heap[0]];
/* 213 */     int j = 0;
/* 214 */     int l = 0;
/* 215 */     int r = 1;
/* 216 */     int f = 0;
/* 217 */     for (int i = 0; i < r; i++) {
/* 218 */       if (i == f) {
/* 219 */         if (l >= r)
/* 220 */           break;  f = (f << 1) + 1;
/* 221 */         i = l;
/* 222 */         l = -1;
/*     */       } 
/* 224 */       if (c.compare(top, refArray[heap[i]]) == 0) {
/* 225 */         a[j++] = heap[i];
/* 226 */         if (l == -1) l = i * 2 + 1; 
/* 227 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 230 */     return j;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntSemiIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */