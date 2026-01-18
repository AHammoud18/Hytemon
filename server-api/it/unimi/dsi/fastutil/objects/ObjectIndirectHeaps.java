/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ public final class ObjectIndirectHeaps
/*     */ {
/*     */   public static <K> int downHeap(K[] refArray, int[] heap, int[] inv, int size, int i, Comparator<K> c) {
/*  47 */     assert i < size;
/*  48 */     int e = heap[i];
/*  49 */     K E = refArray[e];
/*     */     
/*  51 */     if (c == null) { int child; while ((child = (i << 1) + 1) < size) {
/*  52 */         int t = heap[child];
/*  53 */         int right = child + 1;
/*  54 */         if (right < size && ((Comparable<K>)refArray[heap[right]]).compareTo(refArray[t]) < 0) t = heap[child = right]; 
/*  55 */         if (((Comparable<K>)E).compareTo(refArray[t]) <= 0)
/*  56 */           break;  heap[i] = t;
/*  57 */         inv[heap[i]] = i;
/*  58 */         i = child;
/*     */       }  }
/*  60 */     else { int child; while ((child = (i << 1) + 1) < size) {
/*  61 */         int t = heap[child];
/*  62 */         int right = child + 1;
/*  63 */         if (right < size && c.compare(refArray[heap[right]], refArray[t]) < 0) t = heap[child = right]; 
/*  64 */         if (c.compare(E, refArray[t]) <= 0)
/*  65 */           break;  heap[i] = t;
/*  66 */         inv[heap[i]] = i;
/*  67 */         i = child;
/*     */       }  }
/*  69 */      heap[i] = e;
/*  70 */     inv[e] = i;
/*  71 */     return i;
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
/*     */   public static <K> int upHeap(K[] refArray, int[] heap, int[] inv, int size, int i, Comparator<K> c) {
/*  89 */     assert i < size;
/*  90 */     int e = heap[i];
/*  91 */     K E = refArray[e];
/*  92 */     if (c == null) { while (i != 0) {
/*  93 */         int parent = i - 1 >>> 1;
/*  94 */         int t = heap[parent];
/*  95 */         if (((Comparable<K>)refArray[t]).compareTo(E) <= 0)
/*  96 */           break;  heap[i] = t;
/*  97 */         inv[heap[i]] = i;
/*  98 */         i = parent;
/*     */       }  }
/* 100 */     else { while (i != 0) {
/* 101 */         int parent = i - 1 >>> 1;
/* 102 */         int t = heap[parent];
/* 103 */         if (c.compare(refArray[t], E) <= 0)
/* 104 */           break;  heap[i] = t;
/* 105 */         inv[heap[i]] = i;
/* 106 */         i = parent;
/*     */       }  }
/* 108 */      heap[i] = e;
/* 109 */     inv[e] = i;
/* 110 */     return i;
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
/*     */   public static <K> void makeHeap(K[] refArray, int offset, int length, int[] heap, int[] inv, Comparator<K> c) {
/* 124 */     ObjectArrays.ensureOffsetLength(refArray, offset, length);
/* 125 */     if (heap.length < length) throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")"); 
/* 126 */     if (inv.length < refArray.length) throw new IllegalArgumentException("The inversion array length (" + heap.length + ") is smaller than the length of the reference array (" + refArray.length + ")"); 
/* 127 */     Arrays.fill(inv, 0, refArray.length, -1);
/* 128 */     int i = length;
/* 129 */     while (i-- != 0) { heap[i] = offset + i; inv[offset + i] = i; }
/* 130 */      i = length >>> 1;
/* 131 */     for (; i-- != 0; downHeap(refArray, heap, inv, length, i, c));
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
/*     */   public static <K> void makeHeap(K[] refArray, int[] heap, int[] inv, int size, Comparator<K> c) {
/* 144 */     int i = size >>> 1;
/* 145 */     for (; i-- != 0; downHeap(refArray, heap, inv, size, i, c));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */