/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public class FloatHeapPriorityQueue
/*     */   implements FloatPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  33 */   protected transient float[] heap = FloatArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected FloatComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatHeapPriorityQueue(int capacity, FloatComparator c) {
/*  47 */     if (capacity > 0) this.heap = new float[capacity]; 
/*  48 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatHeapPriorityQueue(int capacity) {
/*  57 */     this(capacity, (FloatComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatHeapPriorityQueue(FloatComparator c) {
/*  66 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatHeapPriorityQueue() {
/*  73 */     this(0, (FloatComparator)null);
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
/*     */   public FloatHeapPriorityQueue(float[] a, int size, FloatComparator c) {
/*  89 */     this(c);
/*  90 */     this.heap = a;
/*  91 */     this.size = size;
/*  92 */     FloatHeaps.makeHeap(a, size, c);
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
/*     */   public FloatHeapPriorityQueue(float[] a, FloatComparator c) {
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
/*     */   public FloatHeapPriorityQueue(float[] a, int size) {
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
/*     */   public FloatHeapPriorityQueue(float[] a) {
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
/*     */   public FloatHeapPriorityQueue(FloatCollection collection, FloatComparator c) {
/* 149 */     this(collection.toFloatArray(), c);
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
/*     */   public FloatHeapPriorityQueue(FloatCollection collection) {
/* 161 */     this(collection, (FloatComparator)null);
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
/*     */   public FloatHeapPriorityQueue(Collection<? extends Float> collection, FloatComparator c) {
/* 174 */     this(collection.size(), c);
/* 175 */     Iterator<? extends Float> iterator = collection.iterator();
/* 176 */     int size = collection.size();
/* 177 */     for (int i = 0; i < size; ) { this.heap[i] = ((Float)iterator.next()).floatValue(); i++; }
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
/*     */   public FloatHeapPriorityQueue(Collection<? extends Float> collection) {
/* 189 */     this(collection, (FloatComparator)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(float x) {
/* 194 */     if (this.size == this.heap.length) this.heap = FloatArrays.grow(this.heap, this.size + 1); 
/* 195 */     this.heap[this.size++] = x;
/* 196 */     FloatHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public float dequeueFloat() {
/* 201 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 202 */     float result = this.heap[0];
/* 203 */     this.heap[0] = this.heap[--this.size];
/* 204 */     if (this.size != 0) FloatHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 205 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public float firstFloat() {
/* 210 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 211 */     return this.heap[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 216 */     FloatHeaps.downHeap(this.heap, this.size, 0, this.c);
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
/* 231 */     this.heap = FloatArrays.trim(this.heap, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatComparator comparator() {
/* 236 */     return this.c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 240 */     s.defaultWriteObject();
/* 241 */     s.writeInt(this.heap.length);
/* 242 */     for (int i = 0; i < this.size; ) { s.writeFloat(this.heap[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 246 */     s.defaultReadObject();
/* 247 */     this.heap = new float[s.readInt()];
/* 248 */     for (int i = 0; i < this.size; ) { this.heap[i] = s.readFloat(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */