/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.PriorityQueue;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectArrayFIFOQueue<K>
/*     */   implements PriorityQueue<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int INITIAL_CAPACITY = 4;
/*     */   protected transient K[] array;
/*     */   protected transient int length;
/*     */   protected transient int start;
/*     */   protected transient int end;
/*     */   
/*     */   public ObjectArrayFIFOQueue(int capacity) {
/*  62 */     if (capacity > 2147483638) throw new IllegalArgumentException("Initial capacity (" + capacity + ") exceeds " + 2147483638); 
/*  63 */     if (capacity < 0) throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
/*     */ 
/*     */     
/*  66 */     this.array = (K[])new Object[Math.max(1, capacity + 1)];
/*  67 */     this.length = this.array.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectArrayFIFOQueue() {
/*  72 */     this(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparator<? super K> comparator() {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public K dequeue() {
/*  87 */     if (this.start == this.end) throw new NoSuchElementException(); 
/*  88 */     K t = this.array[this.start];
/*  89 */     this.array[this.start] = null;
/*  90 */     if (++this.start == this.length) this.start = 0; 
/*  91 */     reduce();
/*  92 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K dequeueLast() {
/* 102 */     if (this.start == this.end) throw new NoSuchElementException(); 
/* 103 */     if (this.end == 0) this.end = this.length; 
/* 104 */     K t = this.array[--this.end];
/* 105 */     this.array[this.end] = null;
/* 106 */     reduce();
/* 107 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   private final void resize(int size, int newLength) {
/* 112 */     K[] newArray = (K[])new Object[newLength];
/* 113 */     if (this.start >= this.end)
/* 114 */     { if (size != 0) {
/* 115 */         System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
/* 116 */         System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end);
/*     */       }  }
/* 118 */     else { System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start); }
/* 119 */      this.start = 0;
/* 120 */     this.end = size;
/* 121 */     this.array = newArray;
/* 122 */     this.length = newLength;
/*     */   }
/*     */   
/*     */   private final void expand() {
/* 126 */     resize(this.length, (int)Math.min(2147483639L, 2L * this.length));
/*     */   }
/*     */   
/*     */   private final void reduce() {
/* 130 */     int size = size();
/* 131 */     if (this.length > 4 && size <= this.length / 4) resize(size, this.length / 2);
/*     */   
/*     */   }
/*     */   
/*     */   public void enqueue(K x) {
/* 136 */     this.array[this.end++] = x;
/* 137 */     if (this.end == this.length) this.end = 0; 
/* 138 */     if (this.end == this.start) expand();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enqueueFirst(K x) {
/* 147 */     if (this.start == 0) this.start = this.length; 
/* 148 */     this.array[--this.start] = x;
/* 149 */     if (this.end == this.start) expand();
/*     */   
/*     */   }
/*     */   
/*     */   public K first() {
/* 154 */     if (this.start == this.end) throw new NoSuchElementException(); 
/* 155 */     return this.array[this.start];
/*     */   }
/*     */ 
/*     */   
/*     */   public K last() {
/* 160 */     if (this.start == this.end) throw new NoSuchElementException(); 
/* 161 */     return this.array[((this.end == 0) ? this.length : this.end) - 1];
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 166 */     if (this.start <= this.end) { Arrays.fill((Object[])this.array, this.start, this.end, (Object)null); }
/*     */     else
/* 168 */     { Arrays.fill((Object[])this.array, this.start, this.length, (Object)null);
/* 169 */       Arrays.fill((Object[])this.array, 0, this.end, (Object)null); }
/*     */     
/* 171 */     this.start = this.end = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 177 */     int size = size();
/* 178 */     K[] newArray = (K[])new Object[size + 1];
/* 179 */     if (this.start <= this.end) { System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start); }
/*     */     else
/* 181 */     { System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
/* 182 */       System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end); }
/*     */     
/* 184 */     this.start = 0;
/* 185 */     this.length = (this.end = size) + 1;
/* 186 */     this.array = newArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 191 */     int apparentLength = this.end - this.start;
/* 192 */     return (apparentLength >= 0) ? apparentLength : (this.length + apparentLength);
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 196 */     s.defaultWriteObject();
/* 197 */     int size = size();
/* 198 */     s.writeInt(size);
/* 199 */     for (int i = this.start; size-- != 0; ) {
/* 200 */       s.writeObject(this.array[i++]);
/* 201 */       if (i == this.length) i = 0;
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 207 */     s.defaultReadObject();
/* 208 */     this.end = s.readInt();
/* 209 */     this.array = (K[])new Object[this.length = HashCommon.nextPowerOfTwo(this.end + 1)];
/* 210 */     for (int i = 0; i < this.end; ) { this.array[i] = (K)s.readObject(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectArrayFIFOQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */