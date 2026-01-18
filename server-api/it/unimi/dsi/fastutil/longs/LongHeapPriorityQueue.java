/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
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
/*     */ public class LongHeapPriorityQueue
/*     */   implements LongPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  33 */   protected transient long[] heap = LongArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected LongComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHeapPriorityQueue(int capacity, LongComparator c) {
/*  47 */     if (capacity > 0) this.heap = new long[capacity]; 
/*  48 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHeapPriorityQueue(int capacity) {
/*  57 */     this(capacity, (LongComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHeapPriorityQueue(LongComparator c) {
/*  66 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHeapPriorityQueue() {
/*  73 */     this(0, (LongComparator)null);
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
/*     */   public LongHeapPriorityQueue(long[] a, int size, LongComparator c) {
/*  89 */     this(c);
/*  90 */     this.heap = a;
/*  91 */     this.size = size;
/*  92 */     LongHeaps.makeHeap(a, size, c);
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
/*     */   public LongHeapPriorityQueue(long[] a, LongComparator c) {
/* 107 */     this(a, a.length, c);
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
/*     */   public LongHeapPriorityQueue(long[] a, int size) {
/* 122 */     this(a, size, null);
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
/*     */   public LongHeapPriorityQueue(long[] a) {
/* 136 */     this(a, a.length);
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
/*     */   public LongHeapPriorityQueue(LongCollection collection, LongComparator c) {
/* 149 */     this(collection.toLongArray(), c);
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
/*     */   public LongHeapPriorityQueue(LongCollection collection) {
/* 161 */     this(collection, (LongComparator)null);
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
/*     */   public LongHeapPriorityQueue(Collection<? extends Long> collection, LongComparator c) {
/* 174 */     this(collection.size(), c);
/* 175 */     Iterator<? extends Long> iterator = collection.iterator();
/* 176 */     int size = collection.size();
/* 177 */     for (int i = 0; i < size; ) { this.heap[i] = ((Long)iterator.next()).longValue(); i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHeapPriorityQueue(Collection<? extends Long> collection) {
/* 189 */     this(collection, (LongComparator)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(long x) {
/* 194 */     if (this.size == this.heap.length) this.heap = LongArrays.grow(this.heap, this.size + 1); 
/* 195 */     this.heap[this.size++] = x;
/* 196 */     LongHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public long dequeueLong() {
/* 201 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 202 */     long result = this.heap[0];
/* 203 */     this.heap[0] = this.heap[--this.size];
/* 204 */     if (this.size != 0) LongHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 205 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public long firstLong() {
/* 210 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 211 */     return this.heap[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 216 */     LongHeaps.downHeap(this.heap, this.size, 0, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 221 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 226 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 231 */     this.heap = LongArrays.trim(this.heap, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public LongComparator comparator() {
/* 236 */     return this.c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 240 */     s.defaultWriteObject();
/* 241 */     s.writeInt(this.heap.length);
/* 242 */     for (int i = 0; i < this.size; ) { s.writeLong(this.heap[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 246 */     s.defaultReadObject();
/* 247 */     this.heap = new long[s.readInt()];
/* 248 */     for (int i = 0; i < this.size; ) { this.heap[i] = s.readLong(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */