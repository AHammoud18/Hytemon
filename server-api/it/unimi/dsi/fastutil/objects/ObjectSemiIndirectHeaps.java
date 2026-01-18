/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectSemiIndirectHeaps
/*     */ {
/*     */   public static <K> int downHeap(K[] refArray, int[] heap, int size, int i, Comparator<K> c) {
/*  46 */     assert i < size;
/*  47 */     int e = heap[i];
/*  48 */     K E = refArray[e];
/*     */     
/*  50 */     if (c == null) { int child; while ((child = (i << 1) + 1) < size) {
/*  51 */         int t = heap[child];
/*  52 */         int right = child + 1;
/*  53 */         if (right < size && ((Comparable<K>)refArray[heap[right]]).compareTo(refArray[t]) < 0) t = heap[child = right]; 
/*  54 */         if (((Comparable<K>)E).compareTo(refArray[t]) <= 0)
/*  55 */           break;  heap[i] = t;
/*  56 */         i = child;
/*     */       }  }
/*  58 */     else { int child; while ((child = (i << 1) + 1) < size) {
/*  59 */         int t = heap[child];
/*  60 */         int right = child + 1;
/*  61 */         if (right < size && c.compare(refArray[heap[right]], refArray[t]) < 0) t = heap[child = right]; 
/*  62 */         if (c.compare(E, refArray[t]) <= 0)
/*  63 */           break;  heap[i] = t;
/*  64 */         i = child;
/*     */       }  }
/*  66 */      heap[i] = e;
/*  67 */     return i;
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
/*     */   public static <K> int upHeap(K[] refArray, int[] heap, int size, int i, Comparator<K> c) {
/*  83 */     assert i < size;
/*  84 */     int e = heap[i];
/*  85 */     K E = refArray[e];
/*  86 */     if (c == null) { while (i != 0) {
/*  87 */         int parent = i - 1 >>> 1;
/*  88 */         int t = heap[parent];
/*  89 */         if (((Comparable<K>)refArray[t]).compareTo(E) <= 0)
/*  90 */           break;  heap[i] = t;
/*  91 */         i = parent;
/*     */       }  }
/*  93 */     else { while (i != 0) {
/*  94 */         int parent = i - 1 >>> 1;
/*  95 */         int t = heap[parent];
/*  96 */         if (c.compare(refArray[t], E) <= 0)
/*  97 */           break;  heap[i] = t;
/*  98 */         i = parent;
/*     */       }  }
/* 100 */      heap[i] = e;
/* 101 */     return i;
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
/*     */   public static <K> void makeHeap(K[] refArray, int offset, int length, int[] heap, Comparator<K> c) {
/* 114 */     ObjectArrays.ensureOffsetLength(refArray, offset, length);
/* 115 */     if (heap.length < length) throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")"); 
/* 116 */     int i = length;
/* 117 */     for (; i-- != 0; heap[i] = offset + i);
/* 118 */     i = length >>> 1;
/* 119 */     for (; i-- != 0; downHeap(refArray, heap, length, i, c));
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
/*     */   public static <K> int[] makeHeap(K[] refArray, int offset, int length, Comparator<K> c) {
/* 132 */     int[] heap = (length <= 0) ? IntArrays.EMPTY_ARRAY : new int[length];
/* 133 */     makeHeap(refArray, offset, length, heap, c);
/* 134 */     return heap;
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
/*     */   public static <K> void makeHeap(K[] refArray, int[] heap, int size, Comparator<K> c) {
/* 146 */     int i = size >>> 1;
/* 147 */     for (; i-- != 0; downHeap(refArray, heap, size, i, c));
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
/*     */   public static <K> int front(K[] refArray, int[] heap, int size, int[] a) {
/* 172 */     K top = refArray[heap[0]];
/* 173 */     int j = 0;
/* 174 */     int l = 0;
/* 175 */     int r = 1;
/* 176 */     int f = 0;
/* 177 */     for (int i = 0; i < r; i++) {
/* 178 */       if (i == f) {
/* 179 */         if (l >= r)
/* 180 */           break;  f = (f << 1) + 1;
/* 181 */         i = l;
/* 182 */         l = -1;
/*     */       } 
/* 184 */       if (((Comparable<K>)top).compareTo(refArray[heap[i]]) == 0) {
/* 185 */         a[j++] = heap[i];
/* 186 */         if (l == -1) l = i * 2 + 1; 
/* 187 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 190 */     return j;
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
/*     */   public static <K> int front(K[] refArray, int[] heap, int size, int[] a, Comparator<K> c) {
/* 215 */     K top = refArray[heap[0]];
/* 216 */     int j = 0;
/* 217 */     int l = 0;
/* 218 */     int r = 1;
/* 219 */     int f = 0;
/* 220 */     for (int i = 0; i < r; i++) {
/* 221 */       if (i == f) {
/* 222 */         if (l >= r)
/* 223 */           break;  f = (f << 1) + 1;
/* 224 */         i = l;
/* 225 */         l = -1;
/*     */       } 
/* 227 */       if (c.compare(top, refArray[heap[i]]) == 0) {
/* 228 */         a[j++] = heap[i];
/* 229 */         if (l == -1) l = i * 2 + 1; 
/* 230 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 233 */     return j;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectSemiIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */