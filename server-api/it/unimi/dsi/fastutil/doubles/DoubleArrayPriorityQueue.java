/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
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
/*     */ public class DoubleArrayPriorityQueue
/*     */   implements DoublePriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  35 */   protected transient double[] array = DoubleArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */   
/*     */   protected DoubleComparator c;
/*     */ 
/*     */   
/*     */   protected transient int firstIndex;
/*     */ 
/*     */   
/*     */   protected transient boolean firstIndexValid;
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayPriorityQueue(int capacity, DoubleComparator c) {
/*  53 */     if (capacity > 0) this.array = new double[capacity]; 
/*  54 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayPriorityQueue(int capacity) {
/*  63 */     this(capacity, (DoubleComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayPriorityQueue(DoubleComparator c) {
/*  72 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayPriorityQueue() {
/*  79 */     this(0, (DoubleComparator)null);
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
/*     */   public DoubleArrayPriorityQueue(double[] a, int size, DoubleComparator c) {
/*  93 */     this(c);
/*  94 */     this.array = a;
/*  95 */     this.size = size;
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
/*     */   public DoubleArrayPriorityQueue(double[] a, DoubleComparator c) {
/* 108 */     this(a, a.length, c);
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
/*     */   public DoubleArrayPriorityQueue(double[] a, int size) {
/* 121 */     this(a, size, null);
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
/*     */   public DoubleArrayPriorityQueue(double[] a) {
/* 133 */     this(a, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 139 */     if (this.firstIndexValid) return this.firstIndex; 
/* 140 */     this.firstIndexValid = true;
/* 141 */     int i = this.size;
/* 142 */     int firstIndex = --i;
/* 143 */     double first = this.array[firstIndex];
/* 144 */     if (this.c == null)
/* 145 */     { while (i-- != 0) { if (Double.compare(this.array[i], first) < 0) first = this.array[firstIndex = i];  }  }
/* 146 */     else { while (i-- != 0) {
/* 147 */         if (this.c.compare(this.array[i], first) < 0) first = this.array[firstIndex = i]; 
/*     */       }  }
/* 149 */      return this.firstIndex = firstIndex;
/*     */   }
/*     */   
/*     */   private void ensureNonEmpty() {
/* 153 */     if (this.size == 0) throw new NoSuchElementException();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(double x) {
/* 159 */     if (this.size == this.array.length) this.array = DoubleArrays.grow(this.array, this.size + 1); 
/* 160 */     if (this.firstIndexValid)
/* 161 */     { if (this.c == null)
/* 162 */       { if (Double.compare(x, this.array[this.firstIndex]) < 0) this.firstIndex = this.size;  }
/* 163 */       else if (this.c.compare(x, this.array[this.firstIndex]) < 0) { this.firstIndex = this.size; }  }
/* 164 */     else { this.firstIndexValid = false; }
/* 165 */      this.array[this.size++] = x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double dequeueDouble() {
/* 170 */     ensureNonEmpty();
/* 171 */     int first = findFirst();
/* 172 */     double result = this.array[first];
/* 173 */     System.arraycopy(this.array, first + 1, this.array, first, --this.size - first);
/* 174 */     this.firstIndexValid = false;
/* 175 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public double firstDouble() {
/* 180 */     ensureNonEmpty();
/* 181 */     return this.array[findFirst()];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 186 */     ensureNonEmpty();
/* 187 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 192 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 197 */     this.size = 0;
/* 198 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 203 */     this.array = DoubleArrays.trim(this.array, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleComparator comparator() {
/* 208 */     return this.c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 212 */     s.defaultWriteObject();
/* 213 */     s.writeInt(this.array.length);
/* 214 */     for (int i = 0; i < this.size; ) { s.writeDouble(this.array[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 218 */     s.defaultReadObject();
/* 219 */     this.array = new double[s.readInt()];
/* 220 */     for (int i = 0; i < this.size; ) { this.array[i] = s.readDouble(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleArrayPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */