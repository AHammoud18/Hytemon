/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ByteIndirectHeaps
/*     */ {
/*     */   public static int downHeap(byte[] refArray, int[] heap, int[] inv, int size, int i, ByteComparator c) {
/*  46 */     assert i < size;
/*  47 */     int e = heap[i];
/*  48 */     byte E = refArray[e];
/*     */     
/*  50 */     if (c == null) { int child; while ((child = (i << 1) + 1) < size) {
/*  51 */         int t = heap[child];
/*  52 */         int right = child + 1;
/*  53 */         if (right < size && refArray[heap[right]] < refArray[t]) t = heap[child = right]; 
/*  54 */         if (E <= refArray[t])
/*  55 */           break;  heap[i] = t;
/*  56 */         inv[heap[i]] = i;
/*  57 */         i = child;
/*     */       }  }
/*  59 */     else { int child; while ((child = (i << 1) + 1) < size) {
/*  60 */         int t = heap[child];
/*  61 */         int right = child + 1;
/*  62 */         if (right < size && c.compare(refArray[heap[right]], refArray[t]) < 0) t = heap[child = right]; 
/*  63 */         if (c.compare(E, refArray[t]) <= 0)
/*  64 */           break;  heap[i] = t;
/*  65 */         inv[heap[i]] = i;
/*  66 */         i = child;
/*     */       }  }
/*  68 */      heap[i] = e;
/*  69 */     inv[e] = i;
/*  70 */     return i;
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
/*     */   public static int upHeap(byte[] refArray, int[] heap, int[] inv, int size, int i, ByteComparator c) {
/*  88 */     assert i < size;
/*  89 */     int e = heap[i];
/*  90 */     byte E = refArray[e];
/*  91 */     if (c == null) { while (i != 0) {
/*  92 */         int parent = i - 1 >>> 1;
/*  93 */         int t = heap[parent];
/*  94 */         if (refArray[t] <= E)
/*  95 */           break;  heap[i] = t;
/*  96 */         inv[heap[i]] = i;
/*  97 */         i = parent;
/*     */       }  }
/*  99 */     else { while (i != 0) {
/* 100 */         int parent = i - 1 >>> 1;
/* 101 */         int t = heap[parent];
/* 102 */         if (c.compare(refArray[t], E) <= 0)
/* 103 */           break;  heap[i] = t;
/* 104 */         inv[heap[i]] = i;
/* 105 */         i = parent;
/*     */       }  }
/* 107 */      heap[i] = e;
/* 108 */     inv[e] = i;
/* 109 */     return i;
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
/*     */   public static void makeHeap(byte[] refArray, int offset, int length, int[] heap, int[] inv, ByteComparator c) {
/* 123 */     ByteArrays.ensureOffsetLength(refArray, offset, length);
/* 124 */     if (heap.length < length) throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")"); 
/* 125 */     if (inv.length < refArray.length) throw new IllegalArgumentException("The inversion array length (" + heap.length + ") is smaller than the length of the reference array (" + refArray.length + ")"); 
/* 126 */     Arrays.fill(inv, 0, refArray.length, -1);
/* 127 */     int i = length;
/* 128 */     while (i-- != 0) { heap[i] = offset + i; inv[offset + i] = i; }
/* 129 */      i = length >>> 1;
/* 130 */     for (; i-- != 0; downHeap(refArray, heap, inv, length, i, c));
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
/*     */   public static void makeHeap(byte[] refArray, int[] heap, int[] inv, int size, ByteComparator c) {
/* 143 */     int i = size >>> 1;
/* 144 */     for (; i-- != 0; downHeap(refArray, heap, inv, size, i, c));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */