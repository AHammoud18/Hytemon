/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public class ShortHeapPriorityQueue
/*     */   implements ShortPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  33 */   protected transient short[] heap = ShortArrays.EMPTY_ARRAY;
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
/*     */   public ShortHeapPriorityQueue(int capacity, ShortComparator c) {
/*  47 */     if (capacity > 0) this.heap = new short[capacity]; 
/*  48 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapPriorityQueue(int capacity) {
/*  57 */     this(capacity, (ShortComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapPriorityQueue(ShortComparator c) {
/*  66 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapPriorityQueue() {
/*  73 */     this(0, (ShortComparator)null);
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
/*     */   public ShortHeapPriorityQueue(short[] a, int size, ShortComparator c) {
/*  89 */     this(c);
/*  90 */     this.heap = a;
/*  91 */     this.size = size;
/*  92 */     ShortHeaps.makeHeap(a, size, c);
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
/*     */   public ShortHeapPriorityQueue(short[] a, ShortComparator c) {
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
/*     */   public ShortHeapPriorityQueue(short[] a, int size) {
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
/*     */   public ShortHeapPriorityQueue(short[] a) {
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
/*     */   public ShortHeapPriorityQueue(ShortCollection collection, ShortComparator c) {
/* 149 */     this(collection.toShortArray(), c);
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
/*     */   public ShortHeapPriorityQueue(ShortCollection collection) {
/* 161 */     this(collection, (ShortComparator)null);
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
/*     */   public ShortHeapPriorityQueue(Collection<? extends Short> collection, ShortComparator c) {
/* 174 */     this(collection.size(), c);
/* 175 */     Iterator<? extends Short> iterator = collection.iterator();
/* 176 */     int size = collection.size();
/* 177 */     for (int i = 0; i < size; ) { this.heap[i] = ((Short)iterator.next()).shortValue(); i++; }
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
/*     */   public ShortHeapPriorityQueue(Collection<? extends Short> collection) {
/* 189 */     this(collection, (ShortComparator)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(short x) {
/* 194 */     if (this.size == this.heap.length) this.heap = ShortArrays.grow(this.heap, this.size + 1); 
/* 195 */     this.heap[this.size++] = x;
/* 196 */     ShortHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public short dequeueShort() {
/* 201 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 202 */     short result = this.heap[0];
/* 203 */     this.heap[0] = this.heap[--this.size];
/* 204 */     if (this.size != 0) ShortHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 205 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public short firstShort() {
/* 210 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 211 */     return this.heap[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 216 */     ShortHeaps.downHeap(this.heap, this.size, 0, this.c);
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
/* 231 */     this.heap = ShortArrays.trim(this.heap, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public ShortComparator comparator() {
/* 236 */     return this.c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 240 */     s.defaultWriteObject();
/* 241 */     s.writeInt(this.heap.length);
/* 242 */     for (int i = 0; i < this.size; ) { s.writeShort(this.heap[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 246 */     s.defaultReadObject();
/* 247 */     this.heap = new short[s.readInt()];
/* 248 */     for (int i = 0; i < this.size; ) { this.heap[i] = s.readShort(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */