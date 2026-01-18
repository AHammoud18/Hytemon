/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
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
/*     */ public class ShortHeapIndirectPriorityQueue
/*     */   extends ShortHeapSemiIndirectPriorityQueue
/*     */ {
/*     */   protected final int[] inv;
/*     */   
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int capacity, ShortComparator c) {
/*  46 */     super(refArray, capacity, c);
/*  47 */     this.inv = new int[refArray.length];
/*  48 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int capacity) {
/*  58 */     this(refArray, capacity, (ShortComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, ShortComparator c) {
/*  69 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray) {
/*  79 */     this(refArray, refArray.length, (ShortComparator)null);
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
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int[] a, int size, ShortComparator c) {
/*  96 */     this(refArray, 0, c);
/*  97 */     this.heap = a;
/*  98 */     this.size = size;
/*  99 */     int i = size;
/* 100 */     while (i-- != 0) {
/* 101 */       if (this.inv[a[i]] != -1) throw new IllegalArgumentException("Index " + a[i] + " appears twice in the heap"); 
/* 102 */       this.inv[a[i]] = i;
/*     */     } 
/* 104 */     ShortIndirectHeaps.makeHeap(refArray, a, this.inv, size, c);
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
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int[] a, ShortComparator c) {
/* 120 */     this(refArray, a, a.length, c);
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
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int[] a, int size) {
/* 136 */     this(refArray, a, size, (ShortComparator)null);
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
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int[] a) {
/* 151 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(int x) {
/* 156 */     if (this.inv[x] >= 0) throw new IllegalArgumentException("Index " + x + " belongs to the queue"); 
/* 157 */     if (this.size == this.heap.length) this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 158 */     this.heap[this.size] = x; this.inv[x] = this.size++;
/* 159 */     ShortIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(int index) {
/* 164 */     return (this.inv[index] >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeue() {
/* 169 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 170 */     int result = this.heap[0];
/* 171 */     if (--this.size != 0) { this.heap[0] = this.heap[this.size]; this.inv[this.heap[this.size]] = 0; }
/* 172 */      this.inv[result] = -1;
/* 173 */     if (this.size != 0) ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c); 
/* 174 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 179 */     ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed(int index) {
/* 184 */     int pos = this.inv[index];
/* 185 */     if (pos < 0) throw new IllegalArgumentException("Index " + index + " does not belong to the queue"); 
/* 186 */     int newPos = ShortIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, pos, this.c);
/* 187 */     ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, this.c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 193 */     ShortIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int index) {
/* 198 */     int result = this.inv[index];
/* 199 */     if (result < 0) return false; 
/* 200 */     this.inv[index] = -1;
/* 201 */     if (result < --this.size) {
/* 202 */       this.heap[result] = this.heap[this.size]; this.inv[this.heap[this.size]] = result;
/* 203 */       int newPos = ShortIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, result, this.c);
/* 204 */       ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, this.c);
/*     */     } 
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 211 */     this.size = 0;
/* 212 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortHeapIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */