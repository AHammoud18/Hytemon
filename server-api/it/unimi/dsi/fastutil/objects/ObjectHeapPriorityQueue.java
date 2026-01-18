/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.PriorityQueue;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ public class ObjectHeapPriorityQueue<K>
/*     */   implements PriorityQueue<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  34 */   protected transient K[] heap = (K[])ObjectArrays.EMPTY_ARRAY;
/*     */ 
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
/*     */   public ObjectHeapPriorityQueue(int capacity, Comparator<? super K> c) {
/*  49 */     if (capacity > 0) this.heap = (K[])new Object[capacity]; 
/*  50 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapPriorityQueue(int capacity) {
/*  59 */     this(capacity, (Comparator<? super K>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapPriorityQueue(Comparator<? super K> c) {
/*  68 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapPriorityQueue() {
/*  75 */     this(0, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapPriorityQueue(K[] a, int size, Comparator<? super K> c) {
/*  91 */     this(c);
/*  92 */     this.heap = a;
/*  93 */     this.size = size;
/*  94 */     ObjectHeaps.makeHeap(a, size, (Comparator)c);
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
/*     */   public ObjectHeapPriorityQueue(K[] a, Comparator<? super K> c) {
/* 109 */     this(a, a.length, c);
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
/*     */   public ObjectHeapPriorityQueue(K[] a, int size) {
/* 124 */     this(a, size, null);
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
/*     */   public ObjectHeapPriorityQueue(K[] a) {
/* 138 */     this(a, a.length);
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
/*     */   public ObjectHeapPriorityQueue(Collection<? extends K> collection, Comparator<? super K> c) {
/* 152 */     this((K[])collection.toArray(), c);
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
/*     */   public ObjectHeapPriorityQueue(Collection<? extends K> collection) {
/* 164 */     this(collection, (Comparator<? super K>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(K x) {
/* 169 */     if (this.size == this.heap.length) this.heap = ObjectArrays.grow(this.heap, this.size + 1); 
/* 170 */     this.heap[this.size++] = x;
/* 171 */     ObjectHeaps.upHeap(this.heap, this.size, this.size - 1, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public K dequeue() {
/* 176 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 177 */     K result = this.heap[0];
/* 178 */     this.heap[0] = this.heap[--this.size];
/* 179 */     this.heap[this.size] = null;
/* 180 */     if (this.size != 0) ObjectHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 181 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public K first() {
/* 186 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 187 */     return this.heap[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 192 */     ObjectHeaps.downHeap(this.heap, this.size, 0, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 197 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 202 */     Arrays.fill((Object[])this.heap, 0, this.size, (Object)null);
/* 203 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 208 */     this.heap = ObjectArrays.trim(this.heap, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparator<? super K> comparator() {
/* 213 */     return this.c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 217 */     s.defaultWriteObject();
/* 218 */     s.writeInt(this.heap.length);
/* 219 */     for (int i = 0; i < this.size; ) { s.writeObject(this.heap[i]); i++; }
/*     */   
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 224 */     s.defaultReadObject();
/* 225 */     this.heap = (K[])new Object[s.readInt()];
/* 226 */     for (int i = 0; i < this.size; ) { this.heap[i] = (K)s.readObject(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */