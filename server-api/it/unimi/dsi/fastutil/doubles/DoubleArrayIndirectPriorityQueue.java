/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
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
/*     */ 
/*     */ public class DoubleArrayIndirectPriorityQueue
/*     */   implements DoubleIndirectPriorityQueue
/*     */ {
/*     */   protected double[] refArray;
/*  38 */   protected int[] array = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */   
/*     */   protected DoubleComparator c;
/*     */ 
/*     */   
/*     */   protected int firstIndex;
/*     */ 
/*     */   
/*     */   protected boolean firstIndexValid;
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int capacity, DoubleComparator c) {
/*  56 */     if (capacity > 0) this.array = new int[capacity]; 
/*  57 */     this.refArray = refArray;
/*  58 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int capacity) {
/*  68 */     this(refArray, capacity, (DoubleComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, DoubleComparator c) {
/*  79 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray) {
/*  89 */     this(refArray, refArray.length, (DoubleComparator)null);
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
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int[] a, int size, DoubleComparator c) {
/* 104 */     this(refArray, 0, c);
/* 105 */     this.array = a;
/* 106 */     this.size = size;
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
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int[] a, DoubleComparator c) {
/* 120 */     this(refArray, a, a.length, c);
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
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int[] a, int size) {
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
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int[] a) {
/* 147 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 153 */     if (this.firstIndexValid) return this.firstIndex; 
/* 154 */     this.firstIndexValid = true;
/* 155 */     int i = this.size;
/* 156 */     int firstIndex = --i;
/* 157 */     double first = this.refArray[this.array[firstIndex]];
/* 158 */     if (this.c == null) { while (i-- != 0) {
/* 159 */         if (Double.compare(this.refArray[this.array[i]], first) < 0) first = this.refArray[this.array[firstIndex = i]]; 
/*     */       }  }
/* 161 */     else { while (i-- != 0) {
/* 162 */         if (this.c.compare(this.refArray[this.array[i]], first) < 0) first = this.refArray[this.array[firstIndex = i]]; 
/*     */       }  }
/* 164 */      return this.firstIndex = firstIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findLast() {
/* 170 */     int i = this.size;
/* 171 */     int lastIndex = --i;
/* 172 */     double last = this.refArray[this.array[lastIndex]];
/* 173 */     if (this.c == null)
/* 174 */     { while (i-- != 0) { if (Double.compare(last, this.refArray[this.array[i]]) < 0) last = this.refArray[this.array[lastIndex = i]];  }
/*     */        }
/* 176 */     else { while (i-- != 0) { if (this.c.compare(last, this.refArray[this.array[i]]) < 0) last = this.refArray[this.array[lastIndex = i]];  }
/*     */        }
/* 178 */      return lastIndex;
/*     */   }
/*     */   
/*     */   protected final void ensureNonEmpty() {
/* 182 */     if (this.size == 0) throw new NoSuchElementException();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void ensureElement(int index) {
/* 193 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 194 */     if (index >= this.refArray.length) throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
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
/*     */ 
/*     */   
/*     */   public void enqueue(int x) {
/* 208 */     ensureElement(x);
/* 209 */     if (this.size == this.array.length) this.array = IntArrays.grow(this.array, this.size + 1); 
/* 210 */     if (this.firstIndexValid)
/* 211 */     { if (this.c == null)
/* 212 */       { if (Double.compare(this.refArray[x], this.refArray[this.array[this.firstIndex]]) < 0) this.firstIndex = this.size;  }
/* 213 */       else if (this.c.compare(this.refArray[x], this.refArray[this.array[this.firstIndex]]) < 0) { this.firstIndex = this.size; }  }
/* 214 */     else { this.firstIndexValid = false; }
/* 215 */      this.array[this.size++] = x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeue() {
/* 220 */     ensureNonEmpty();
/* 221 */     int firstIndex = findFirst();
/* 222 */     int result = this.array[firstIndex];
/* 223 */     if (--this.size != 0) System.arraycopy(this.array, firstIndex + 1, this.array, firstIndex, this.size - firstIndex); 
/* 224 */     this.firstIndexValid = false;
/* 225 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int first() {
/* 230 */     ensureNonEmpty();
/* 231 */     return this.array[findFirst()];
/*     */   }
/*     */ 
/*     */   
/*     */   public int last() {
/* 236 */     ensureNonEmpty();
/* 237 */     return this.array[findLast()];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 242 */     ensureNonEmpty();
/* 243 */     this.firstIndexValid = false;
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
/*     */   public void changed(int index) {
/* 255 */     ensureElement(index);
/* 256 */     if (index == this.firstIndex) this.firstIndexValid = false;
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 262 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int index) {
/* 267 */     ensureElement(index);
/* 268 */     int[] a = this.array;
/* 269 */     int i = this.size; do {  }
/* 270 */     while (i-- != 0 && a[i] != index);
/* 271 */     if (i < 0) return false; 
/* 272 */     this.firstIndexValid = false;
/* 273 */     if (--this.size != 0) System.arraycopy(a, i + 1, a, i, this.size - i); 
/* 274 */     return true;
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
/* 287 */     double top = this.refArray[this.array[findFirst()]];
/* 288 */     int i = this.size, c = 0;
/* 289 */     while (i-- != 0) { if (Double.doubleToLongBits(top) == Double.doubleToLongBits(this.refArray[this.array[i]])) a[c++] = this.array[i];  }
/* 290 */      return c;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 295 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 300 */     this.size = 0;
/* 301 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 306 */     this.array = IntArrays.trim(this.array, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleComparator comparator() {
/* 311 */     return this.c;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 316 */     StringBuffer s = new StringBuffer();
/* 317 */     s.append("[");
/* 318 */     for (int i = 0; i < this.size; i++) {
/* 319 */       if (i != 0) s.append(", "); 
/* 320 */       s.append(this.refArray[this.array[i]]);
/*     */     } 
/* 322 */     s.append("]");
/* 323 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleArrayIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */