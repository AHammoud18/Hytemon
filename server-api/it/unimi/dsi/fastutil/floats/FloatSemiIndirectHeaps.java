/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FloatSemiIndirectHeaps
/*     */ {
/*     */   public static int downHeap(float[] refArray, int[] heap, int size, int i, FloatComparator c) {
/*  45 */     assert i < size;
/*  46 */     int e = heap[i];
/*  47 */     float E = refArray[e];
/*     */     
/*  49 */     if (c == null) { int child; while ((child = (i << 1) + 1) < size) {
/*  50 */         int t = heap[child];
/*  51 */         int right = child + 1;
/*  52 */         if (right < size && Float.compare(refArray[heap[right]], refArray[t]) < 0) t = heap[child = right]; 
/*  53 */         if (Float.compare(E, refArray[t]) <= 0)
/*  54 */           break;  heap[i] = t;
/*  55 */         i = child;
/*     */       }  }
/*  57 */     else { int child; while ((child = (i << 1) + 1) < size) {
/*  58 */         int t = heap[child];
/*  59 */         int right = child + 1;
/*  60 */         if (right < size && c.compare(refArray[heap[right]], refArray[t]) < 0) t = heap[child = right]; 
/*  61 */         if (c.compare(E, refArray[t]) <= 0)
/*  62 */           break;  heap[i] = t;
/*  63 */         i = child;
/*     */       }  }
/*  65 */      heap[i] = e;
/*  66 */     return i;
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
/*     */   public static int upHeap(float[] refArray, int[] heap, int size, int i, FloatComparator c) {
/*  82 */     assert i < size;
/*  83 */     int e = heap[i];
/*  84 */     float E = refArray[e];
/*  85 */     if (c == null) { while (i != 0) {
/*  86 */         int parent = i - 1 >>> 1;
/*  87 */         int t = heap[parent];
/*  88 */         if (Float.compare(refArray[t], E) <= 0)
/*  89 */           break;  heap[i] = t;
/*  90 */         i = parent;
/*     */       }  }
/*  92 */     else { while (i != 0) {
/*  93 */         int parent = i - 1 >>> 1;
/*  94 */         int t = heap[parent];
/*  95 */         if (c.compare(refArray[t], E) <= 0)
/*  96 */           break;  heap[i] = t;
/*  97 */         i = parent;
/*     */       }  }
/*  99 */      heap[i] = e;
/* 100 */     return i;
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
/*     */   public static void makeHeap(float[] refArray, int offset, int length, int[] heap, FloatComparator c) {
/* 113 */     FloatArrays.ensureOffsetLength(refArray, offset, length);
/* 114 */     if (heap.length < length) throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")"); 
/* 115 */     int i = length;
/* 116 */     for (; i-- != 0; heap[i] = offset + i);
/* 117 */     i = length >>> 1;
/* 118 */     for (; i-- != 0; downHeap(refArray, heap, length, i, c));
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
/*     */   public static int[] makeHeap(float[] refArray, int offset, int length, FloatComparator c) {
/* 131 */     int[] heap = (length <= 0) ? IntArrays.EMPTY_ARRAY : new int[length];
/* 132 */     makeHeap(refArray, offset, length, heap, c);
/* 133 */     return heap;
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
/*     */   public static void makeHeap(float[] refArray, int[] heap, int size, FloatComparator c) {
/* 145 */     int i = size >>> 1;
/* 146 */     for (; i-- != 0; downHeap(refArray, heap, size, i, c));
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
/*     */   public static int front(float[] refArray, int[] heap, int size, int[] a) {
/* 171 */     float top = refArray[heap[0]];
/* 172 */     int j = 0;
/* 173 */     int l = 0;
/* 174 */     int r = 1;
/* 175 */     int f = 0;
/* 176 */     for (int i = 0; i < r; i++) {
/* 177 */       if (i == f) {
/* 178 */         if (l >= r)
/* 179 */           break;  f = (f << 1) + 1;
/* 180 */         i = l;
/* 181 */         l = -1;
/*     */       } 
/* 183 */       if (Float.compare(top, refArray[heap[i]]) == 0) {
/* 184 */         a[j++] = heap[i];
/* 185 */         if (l == -1) l = i * 2 + 1; 
/* 186 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 189 */     return j;
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
/*     */   public static int front(float[] refArray, int[] heap, int size, int[] a, FloatComparator c) {
/* 214 */     float top = refArray[heap[0]];
/* 215 */     int j = 0;
/* 216 */     int l = 0;
/* 217 */     int r = 1;
/* 218 */     int f = 0;
/* 219 */     for (int i = 0; i < r; i++) {
/* 220 */       if (i == f) {
/* 221 */         if (l >= r)
/* 222 */           break;  f = (f << 1) + 1;
/* 223 */         i = l;
/* 224 */         l = -1;
/*     */       } 
/* 226 */       if (c.compare(top, refArray[heap[i]]) == 0) {
/* 227 */         a[j++] = heap[i];
/* 228 */         if (l == -1) l = i * 2 + 1; 
/* 229 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 232 */     return j;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatSemiIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */