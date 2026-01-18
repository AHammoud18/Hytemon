/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public class IntHeapPriorityQueue
/*     */   implements IntPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  33 */   protected transient int[] heap = IntArrays.EMPTY_ARRAY;
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
/*     */   public IntHeapPriorityQueue(int capacity, IntComparator c) {
/*  47 */     if (capacity > 0) this.heap = new int[capacity]; 
/*  48 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapPriorityQueue(int capacity) {
/*  57 */     this(capacity, (IntComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapPriorityQueue(IntComparator c) {
/*  66 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapPriorityQueue() {
/*  73 */     this(0, (IntComparator)null);
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
/*     */   public IntHeapPriorityQueue(int[] a, int size, IntComparator c) {
/*  89 */     this(c);
/*  90 */     this.heap = a;
/*  91 */     this.size = size;
/*  92 */     IntHeaps.makeHeap(a, size, c);
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
/*     */   public IntHeapPriorityQueue(int[] a, IntComparator c) {
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
/*     */   public IntHeapPriorityQueue(int[] a, int size) {
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
/*     */   public IntHeapPriorityQueue(int[] a) {
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
/*     */   public IntHeapPriorityQueue(IntCollection collection, IntComparator c) {
/* 149 */     this(collection.toIntArray(), c);
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
/*     */   public IntHeapPriorityQueue(IntCollection collection) {
/* 161 */     this(collection, (IntComparator)null);
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
/*     */   public IntHeapPriorityQueue(Collection<? extends Integer> collection, IntComparator c) {
/* 174 */     this(collection.size(), c);
/* 175 */     Iterator<? extends Integer> iterator = collection.iterator();
/* 176 */     int size = collection.size();
/* 177 */     for (int i = 0; i < size; ) { this.heap[i] = ((Integer)iterator.next()).intValue(); i++; }
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
/*     */   public IntHeapPriorityQueue(Collection<? extends Integer> collection) {
/* 189 */     this(collection, (IntComparator)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(int x) {
/* 194 */     if (this.size == this.heap.length) this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 195 */     this.heap[this.size++] = x;
/* 196 */     IntHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeueInt() {
/* 201 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 202 */     int result = this.heap[0];
/* 203 */     this.heap[0] = this.heap[--this.size];
/* 204 */     if (this.size != 0) IntHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 205 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int firstInt() {
/* 210 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 211 */     return this.heap[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 216 */     IntHeaps.downHeap(this.heap, this.size, 0, this.c);
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
/* 231 */     this.heap = IntArrays.trim(this.heap, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public IntComparator comparator() {
/* 236 */     return this.c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 240 */     s.defaultWriteObject();
/* 241 */     s.writeInt(this.heap.length);
/* 242 */     for (int i = 0; i < this.size; ) { s.writeInt(this.heap[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 246 */     s.defaultReadObject();
/* 247 */     this.heap = new int[s.readInt()];
/* 248 */     for (int i = 0; i < this.size; ) { this.heap[i] = s.readInt(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */