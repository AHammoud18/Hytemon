/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
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
/*     */ public class FloatArrayFIFOQueue
/*     */   implements FloatPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int INITIAL_CAPACITY = 4;
/*     */   protected transient float[] array;
/*     */   protected transient int length;
/*     */   protected transient int start;
/*     */   protected transient int end;
/*     */   
/*     */   public FloatArrayFIFOQueue(int capacity) {
/*  60 */     if (capacity > 2147483638) throw new IllegalArgumentException("Initial capacity (" + capacity + ") exceeds " + 2147483638); 
/*  61 */     if (capacity < 0) throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative");
/*     */ 
/*     */     
/*  64 */     this.array = new float[Math.max(1, capacity + 1)];
/*  65 */     this.length = this.array.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatArrayFIFOQueue() {
/*  70 */     this(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatComparator comparator() {
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public float dequeueFloat() {
/*  85 */     if (this.start == this.end) throw new NoSuchElementException(); 
/*  86 */     float t = this.array[this.start];
/*  87 */     if (++this.start == this.length) this.start = 0; 
/*  88 */     reduce();
/*  89 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float dequeueLastFloat() {
/*  99 */     if (this.start == this.end) throw new NoSuchElementException(); 
/* 100 */     if (this.end == 0) this.end = this.length; 
/* 101 */     float t = this.array[--this.end];
/* 102 */     reduce();
/* 103 */     return t;
/*     */   }
/*     */   
/*     */   private final void resize(int size, int newLength) {
/* 107 */     float[] newArray = new float[newLength];
/* 108 */     if (this.start >= this.end)
/* 109 */     { if (size != 0) {
/* 110 */         System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
/* 111 */         System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end);
/*     */       }  }
/* 113 */     else { System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start); }
/* 114 */      this.start = 0;
/* 115 */     this.end = size;
/* 116 */     this.array = newArray;
/* 117 */     this.length = newLength;
/*     */   }
/*     */   
/*     */   private final void expand() {
/* 121 */     resize(this.length, (int)Math.min(2147483639L, 2L * this.length));
/*     */   }
/*     */   
/*     */   private final void reduce() {
/* 125 */     int size = size();
/* 126 */     if (this.length > 4 && size <= this.length / 4) resize(size, this.length / 2);
/*     */   
/*     */   }
/*     */   
/*     */   public void enqueue(float x) {
/* 131 */     this.array[this.end++] = x;
/* 132 */     if (this.end == this.length) this.end = 0; 
/* 133 */     if (this.end == this.start) expand();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enqueueFirst(float x) {
/* 142 */     if (this.start == 0) this.start = this.length; 
/* 143 */     this.array[--this.start] = x;
/* 144 */     if (this.end == this.start) expand();
/*     */   
/*     */   }
/*     */   
/*     */   public float firstFloat() {
/* 149 */     if (this.start == this.end) throw new NoSuchElementException(); 
/* 150 */     return this.array[this.start];
/*     */   }
/*     */ 
/*     */   
/*     */   public float lastFloat() {
/* 155 */     if (this.start == this.end) throw new NoSuchElementException(); 
/* 156 */     return this.array[((this.end == 0) ? this.length : this.end) - 1];
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 161 */     this.start = this.end = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 167 */     int size = size();
/* 168 */     float[] newArray = new float[size + 1];
/* 169 */     if (this.start <= this.end) { System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start); }
/*     */     else
/* 171 */     { System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
/* 172 */       System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end); }
/*     */     
/* 174 */     this.start = 0;
/* 175 */     this.length = (this.end = size) + 1;
/* 176 */     this.array = newArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 181 */     int apparentLength = this.end - this.start;
/* 182 */     return (apparentLength >= 0) ? apparentLength : (this.length + apparentLength);
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 186 */     s.defaultWriteObject();
/* 187 */     int size = size();
/* 188 */     s.writeInt(size);
/* 189 */     for (int i = this.start; size-- != 0; ) {
/* 190 */       s.writeFloat(this.array[i++]);
/* 191 */       if (i == this.length) i = 0; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 196 */     s.defaultReadObject();
/* 197 */     this.end = s.readInt();
/* 198 */     this.array = new float[this.length = HashCommon.nextPowerOfTwo(this.end + 1)];
/* 199 */     for (int i = 0; i < this.end; ) { this.array[i] = s.readFloat(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatArrayFIFOQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */