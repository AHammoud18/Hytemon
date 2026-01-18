/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectHeapIndirectPriorityQueue<K>
/*     */   extends ObjectHeapSemiIndirectPriorityQueue<K>
/*     */ {
/*     */   protected final int[] inv;
/*     */   
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int capacity, Comparator<? super K> c) {
/*  47 */     super(refArray, capacity, c);
/*  48 */     this.inv = new int[refArray.length];
/*  49 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int capacity) {
/*  59 */     this(refArray, capacity, (Comparator<? super K>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, Comparator<? super K> c) {
/*  70 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray) {
/*  80 */     this(refArray, refArray.length, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int[] a, int size, Comparator<? super K> c) {
/*  97 */     this(refArray, 0, c);
/*  98 */     this.heap = a;
/*  99 */     this.size = size;
/* 100 */     int i = size;
/* 101 */     while (i-- != 0) {
/* 102 */       if (this.inv[a[i]] != -1) throw new IllegalArgumentException("Index " + a[i] + " appears twice in the heap"); 
/* 103 */       this.inv[a[i]] = i;
/*     */     } 
/* 105 */     ObjectIndirectHeaps.makeHeap(refArray, a, this.inv, size, (Comparator)c);
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
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int[] a, Comparator<? super K> c) {
/* 121 */     this(refArray, a, a.length, c);
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
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int[] a, int size) {
/* 137 */     this(refArray, a, size, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int[] a) {
/* 152 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(int x) {
/* 157 */     if (this.inv[x] >= 0) throw new IllegalArgumentException("Index " + x + " belongs to the queue"); 
/* 158 */     if (this.size == this.heap.length) this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 159 */     this.heap[this.size] = x; this.inv[x] = this.size++;
/* 160 */     ObjectIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(int index) {
/* 165 */     return (this.inv[index] >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeue() {
/* 170 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 171 */     int result = this.heap[0];
/* 172 */     if (--this.size != 0) { this.heap[0] = this.heap[this.size]; this.inv[this.heap[this.size]] = 0; }
/* 173 */      this.inv[result] = -1;
/* 174 */     if (this.size != 0) ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, (Comparator)this.c); 
/* 175 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 180 */     ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed(int index) {
/* 185 */     int pos = this.inv[index];
/* 186 */     if (pos < 0) throw new IllegalArgumentException("Index " + index + " does not belong to the queue"); 
/* 187 */     int newPos = ObjectIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, pos, (Comparator)this.c);
/* 188 */     ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 194 */     ObjectIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int index) {
/* 199 */     int result = this.inv[index];
/* 200 */     if (result < 0) return false; 
/* 201 */     this.inv[index] = -1;
/* 202 */     if (result < --this.size) {
/* 203 */       this.heap[result] = this.heap[this.size]; this.inv[this.heap[this.size]] = result;
/* 204 */       int newPos = ObjectIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, result, (Comparator)this.c);
/* 205 */       ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, (Comparator)this.c);
/*     */     } 
/* 207 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 212 */     this.size = 0;
/* 213 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectHeapIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */