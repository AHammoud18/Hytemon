/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
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
/*     */ public class IntHeapSemiIndirectPriorityQueue
/*     */   implements IntIndirectPriorityQueue
/*     */ {
/*     */   protected final int[] refArray;
/*  35 */   protected int[] heap = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected IntComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapSemiIndirectPriorityQueue(int[] refArray, int capacity, IntComparator c) {
/*  49 */     if (capacity > 0) this.heap = new int[capacity]; 
/*  50 */     this.refArray = refArray;
/*  51 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapSemiIndirectPriorityQueue(int[] refArray, int capacity) {
/*  61 */     this(refArray, capacity, (IntComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapSemiIndirectPriorityQueue(int[] refArray, IntComparator c) {
/*  72 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapSemiIndirectPriorityQueue(int[] refArray) {
/*  82 */     this(refArray, refArray.length, (IntComparator)null);
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
/*     */   public IntHeapSemiIndirectPriorityQueue(int[] refArray, int[] a, int size, IntComparator c) {
/*  99 */     this(refArray, 0, c);
/* 100 */     this.heap = a;
/* 101 */     this.size = size;
/* 102 */     IntSemiIndirectHeaps.makeHeap(refArray, a, size, c);
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
/*     */   public IntHeapSemiIndirectPriorityQueue(int[] refArray, int[] a, IntComparator c) {
/* 118 */     this(refArray, a, a.length, c);
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
/*     */   public IntHeapSemiIndirectPriorityQueue(int[] refArray, int[] a, int size) {
/* 134 */     this(refArray, a, size, null);
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
/*     */   public IntHeapSemiIndirectPriorityQueue(int[] refArray, int[] a) {
/* 149 */     this(refArray, a, a.length);
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
/* 160 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 161 */     if (index >= this.refArray.length) throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
/*     */   
/*     */   }
/*     */   
/*     */   public void enqueue(int x) {
/* 166 */     ensureElement(x);
/* 167 */     if (this.size == this.heap.length) this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 168 */     this.heap[this.size++] = x;
/* 169 */     IntSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeue() {
/* 174 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 175 */     int result = this.heap[0];
/* 176 */     this.heap[0] = this.heap[--this.size];
/* 177 */     if (this.size != 0) IntSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c); 
/* 178 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int first() {
/* 183 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 184 */     return this.heap[0];
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
/* 197 */     IntSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 203 */     IntSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 208 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 213 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 218 */     this.heap = IntArrays.trim(this.heap, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public IntComparator comparator() {
/* 223 */     return this.c;
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
/* 236 */     return (this.c == null) ? IntSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a) : IntSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 241 */     StringBuffer s = new StringBuffer();
/* 242 */     s.append("[");
/* 243 */     for (int i = 0; i < this.size; i++) {
/* 244 */       if (i != 0) s.append(", "); 
/* 245 */       s.append(this.refArray[this.heap[i]]);
/*     */     } 
/* 247 */     s.append("]");
/* 248 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntHeapSemiIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */