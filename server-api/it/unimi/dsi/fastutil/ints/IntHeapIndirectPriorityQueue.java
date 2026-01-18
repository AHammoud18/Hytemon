/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public class IntHeapIndirectPriorityQueue
/*     */   extends IntHeapSemiIndirectPriorityQueue
/*     */ {
/*     */   protected final int[] inv;
/*     */   
/*     */   public IntHeapIndirectPriorityQueue(int[] refArray, int capacity, IntComparator c) {
/*  45 */     super(refArray, capacity, c);
/*  46 */     this.inv = new int[refArray.length];
/*  47 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapIndirectPriorityQueue(int[] refArray, int capacity) {
/*  57 */     this(refArray, capacity, (IntComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapIndirectPriorityQueue(int[] refArray, IntComparator c) {
/*  68 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapIndirectPriorityQueue(int[] refArray) {
/*  78 */     this(refArray, refArray.length, (IntComparator)null);
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
/*     */   public IntHeapIndirectPriorityQueue(int[] refArray, int[] a, int size, IntComparator c) {
/*  95 */     this(refArray, 0, c);
/*  96 */     this.heap = a;
/*  97 */     this.size = size;
/*  98 */     int i = size;
/*  99 */     while (i-- != 0) {
/* 100 */       if (this.inv[a[i]] != -1) throw new IllegalArgumentException("Index " + a[i] + " appears twice in the heap"); 
/* 101 */       this.inv[a[i]] = i;
/*     */     } 
/* 103 */     IntIndirectHeaps.makeHeap(refArray, a, this.inv, size, c);
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
/*     */   public IntHeapIndirectPriorityQueue(int[] refArray, int[] a, IntComparator c) {
/* 119 */     this(refArray, a, a.length, c);
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
/*     */   public IntHeapIndirectPriorityQueue(int[] refArray, int[] a, int size) {
/* 135 */     this(refArray, a, size, (IntComparator)null);
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
/*     */   public IntHeapIndirectPriorityQueue(int[] refArray, int[] a) {
/* 150 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(int x) {
/* 155 */     if (this.inv[x] >= 0) throw new IllegalArgumentException("Index " + x + " belongs to the queue"); 
/* 156 */     if (this.size == this.heap.length) this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 157 */     this.heap[this.size] = x; this.inv[x] = this.size++;
/* 158 */     IntIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(int index) {
/* 163 */     return (this.inv[index] >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeue() {
/* 168 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 169 */     int result = this.heap[0];
/* 170 */     if (--this.size != 0) { this.heap[0] = this.heap[this.size]; this.inv[this.heap[this.size]] = 0; }
/* 171 */      this.inv[result] = -1;
/* 172 */     if (this.size != 0) IntIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c); 
/* 173 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 178 */     IntIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed(int index) {
/* 183 */     int pos = this.inv[index];
/* 184 */     if (pos < 0) throw new IllegalArgumentException("Index " + index + " does not belong to the queue"); 
/* 185 */     int newPos = IntIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, pos, this.c);
/* 186 */     IntIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, this.c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 192 */     IntIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int index) {
/* 197 */     int result = this.inv[index];
/* 198 */     if (result < 0) return false; 
/* 199 */     this.inv[index] = -1;
/* 200 */     if (result < --this.size) {
/* 201 */       this.heap[result] = this.heap[this.size]; this.inv[this.heap[this.size]] = result;
/* 202 */       int newPos = IntIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, result, this.c);
/* 203 */       IntIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, this.c);
/*     */     } 
/* 205 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 210 */     this.size = 0;
/* 211 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntHeapIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */