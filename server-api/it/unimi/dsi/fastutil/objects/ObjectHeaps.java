/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ public final class ObjectHeaps
/*     */ {
/*     */   public static <K> int downHeap(K[] heap, int size, int i, Comparator<? super K> c) {
/*  43 */     assert i < size;
/*  44 */     K e = heap[i];
/*     */     
/*  46 */     if (c == null) { int child; while ((child = (i << 1) + 1) < size) {
/*  47 */         K t = heap[child];
/*  48 */         int right = child + 1;
/*  49 */         if (right < size && ((Comparable<K>)heap[right]).compareTo(t) < 0) t = heap[child = right]; 
/*  50 */         if (((Comparable<K>)e).compareTo(t) <= 0)
/*  51 */           break;  heap[i] = t;
/*  52 */         i = child;
/*     */       }  }
/*  54 */     else { int child; while ((child = (i << 1) + 1) < size) {
/*  55 */         K t = heap[child];
/*  56 */         int right = child + 1;
/*  57 */         if (right < size && c.compare(heap[right], t) < 0) t = heap[child = right]; 
/*  58 */         if (c.compare(e, t) <= 0)
/*  59 */           break;  heap[i] = t;
/*  60 */         i = child;
/*     */       }  }
/*  62 */      heap[i] = e;
/*  63 */     return i;
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
/*     */   public static <K> int upHeap(K[] heap, int size, int i, Comparator<K> c) {
/*  77 */     assert i < size;
/*  78 */     K e = heap[i];
/*  79 */     if (c == null) { while (i != 0) {
/*  80 */         int parent = i - 1 >>> 1;
/*  81 */         K t = heap[parent];
/*  82 */         if (((Comparable<K>)t).compareTo(e) <= 0)
/*  83 */           break;  heap[i] = t;
/*  84 */         i = parent;
/*     */       }  }
/*  86 */     else { while (i != 0) {
/*  87 */         int parent = i - 1 >>> 1;
/*  88 */         K t = heap[parent];
/*  89 */         if (c.compare(t, e) <= 0)
/*  90 */           break;  heap[i] = t;
/*  91 */         i = parent;
/*     */       }  }
/*  93 */      heap[i] = e;
/*  94 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> void makeHeap(K[] heap, int size, Comparator<K> c) {
/* 105 */     int i = size >>> 1;
/* 106 */     for (; i-- != 0; downHeap(heap, size, i, c));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */