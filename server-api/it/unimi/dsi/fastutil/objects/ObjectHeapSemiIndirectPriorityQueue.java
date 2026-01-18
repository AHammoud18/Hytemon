/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.IndirectPriorityQueue;
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
/*     */ 
/*     */ public class ObjectHeapSemiIndirectPriorityQueue<K>
/*     */   implements IndirectPriorityQueue<K>
/*     */ {
/*     */   protected final K[] refArray;
/*  38 */   protected int[] heap = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Comparator<? super K> c;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int capacity, Comparator<? super K> c) {
/*  52 */     if (capacity > 0) this.heap = new int[capacity]; 
/*  53 */     this.refArray = refArray;
/*  54 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int capacity) {
/*  64 */     this(refArray, capacity, (Comparator<? super K>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, Comparator<? super K> c) {
/*  75 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray) {
/*  85 */     this(refArray, refArray.length, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int[] a, int size, Comparator<? super K> c) {
/* 102 */     this(refArray, 0, c);
/* 103 */     this.heap = a;
/* 104 */     this.size = size;
/* 105 */     ObjectSemiIndirectHeaps.makeHeap(refArray, a, size, (Comparator)c);
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
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int[] a, Comparator<? super K> c) {
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
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int[] a, int size) {
/* 137 */     this(refArray, a, size, null);
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
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int[] a) {
/* 152 */     this(refArray, a, a.length);
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
/* 163 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 164 */     if (index >= this.refArray.length) throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
/*     */   
/*     */   }
/*     */   
/*     */   public void enqueue(int x) {
/* 169 */     ensureElement(x);
/* 170 */     if (this.size == this.heap.length) this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 171 */     this.heap[this.size++] = x;
/* 172 */     ObjectSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeue() {
/* 177 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 178 */     int result = this.heap[0];
/* 179 */     this.heap[0] = this.heap[--this.size];
/* 180 */     if (this.size != 0) ObjectSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, (Comparator)this.c); 
/* 181 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int first() {
/* 186 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 187 */     return this.heap[0];
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
/* 200 */     ObjectSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 206 */     ObjectSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 211 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 216 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 221 */     this.heap = IntArrays.trim(this.heap, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparator<? super K> comparator() {
/* 226 */     return this.c;
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
/* 239 */     return (this.c == null) ? ObjectSemiIndirectHeaps.<K>front(this.refArray, this.heap, this.size, a) : ObjectSemiIndirectHeaps.<K>front(this.refArray, this.heap, this.size, a, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 244 */     StringBuffer s = new StringBuffer();
/* 245 */     s.append("[");
/* 246 */     for (int i = 0; i < this.size; i++) {
/* 247 */       if (i != 0) s.append(", "); 
/* 248 */       s.append(this.refArray[this.heap[i]]);
/*     */     } 
/* 250 */     s.append("]");
/* 251 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectHeapSemiIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */