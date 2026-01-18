/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
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
/*     */ public class ShortHeapSemiIndirectPriorityQueue
/*     */   implements ShortIndirectPriorityQueue
/*     */ {
/*     */   protected final short[] refArray;
/*  36 */   protected int[] heap = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ShortComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapSemiIndirectPriorityQueue(short[] refArray, int capacity, ShortComparator c) {
/*  50 */     if (capacity > 0) this.heap = new int[capacity]; 
/*  51 */     this.refArray = refArray;
/*  52 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapSemiIndirectPriorityQueue(short[] refArray, int capacity) {
/*  62 */     this(refArray, capacity, (ShortComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapSemiIndirectPriorityQueue(short[] refArray, ShortComparator c) {
/*  73 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapSemiIndirectPriorityQueue(short[] refArray) {
/*  83 */     this(refArray, refArray.length, (ShortComparator)null);
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
/*     */   public ShortHeapSemiIndirectPriorityQueue(short[] refArray, int[] a, int size, ShortComparator c) {
/* 100 */     this(refArray, 0, c);
/* 101 */     this.heap = a;
/* 102 */     this.size = size;
/* 103 */     ShortSemiIndirectHeaps.makeHeap(refArray, a, size, c);
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
/*     */   public ShortHeapSemiIndirectPriorityQueue(short[] refArray, int[] a, ShortComparator c) {
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
/*     */   public ShortHeapSemiIndirectPriorityQueue(short[] refArray, int[] a, int size) {
/* 135 */     this(refArray, a, size, null);
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
/*     */   public ShortHeapSemiIndirectPriorityQueue(short[] refArray, int[] a) {
/* 150 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void ensureElement(int index) {
/* 161 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 162 */     if (index >= this.refArray.length) throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
/*     */   
/*     */   }
/*     */   
/*     */   public void enqueue(int x) {
/* 167 */     ensureElement(x);
/* 168 */     if (this.size == this.heap.length) this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 169 */     this.heap[this.size++] = x;
/* 170 */     ShortSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeue() {
/* 175 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 176 */     int result = this.heap[0];
/* 177 */     this.heap[0] = this.heap[--this.size];
/* 178 */     if (this.size != 0) ShortSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c); 
/* 179 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int first() {
/* 184 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 185 */     return this.heap[0];
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
/*     */   public void changed() {
/* 198 */     ShortSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 204 */     ShortSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 209 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 214 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 219 */     this.heap = IntArrays.trim(this.heap, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public ShortComparator comparator() {
/* 224 */     return this.c;
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
/*     */   public int front(int[] a) {
/* 237 */     return (this.c == null) ? ShortSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a) : ShortSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 242 */     StringBuffer s = new StringBuffer();
/* 243 */     s.append("[");
/* 244 */     for (int i = 0; i < this.size; i++) {
/* 245 */       if (i != 0) s.append(", "); 
/* 246 */       s.append(this.refArray[this.heap[i]]);
/*     */     } 
/* 248 */     s.append("]");
/* 249 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortHeapSemiIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */