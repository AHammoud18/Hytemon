/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
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
/*     */ public class IntArrayIndirectPriorityQueue
/*     */   implements IntIndirectPriorityQueue
/*     */ {
/*     */   protected int[] refArray;
/*  37 */   protected int[] array = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */   
/*     */   protected IntComparator c;
/*     */ 
/*     */   
/*     */   protected int firstIndex;
/*     */ 
/*     */   
/*     */   protected boolean firstIndexValid;
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int capacity, IntComparator c) {
/*  55 */     if (capacity > 0) this.array = new int[capacity]; 
/*  56 */     this.refArray = refArray;
/*  57 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int capacity) {
/*  67 */     this(refArray, capacity, (IntComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, IntComparator c) {
/*  78 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray) {
/*  88 */     this(refArray, refArray.length, (IntComparator)null);
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int[] a, int size, IntComparator c) {
/* 103 */     this(refArray, 0, c);
/* 104 */     this.array = a;
/* 105 */     this.size = size;
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int[] a, IntComparator c) {
/* 119 */     this(refArray, a, a.length, c);
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int[] a, int size) {
/* 133 */     this(refArray, a, size, null);
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int[] a) {
/* 146 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 152 */     if (this.firstIndexValid) return this.firstIndex; 
/* 153 */     this.firstIndexValid = true;
/* 154 */     int i = this.size;
/* 155 */     int firstIndex = --i;
/* 156 */     int first = this.refArray[this.array[firstIndex]];
/* 157 */     if (this.c == null) { while (i-- != 0) {
/* 158 */         if (this.refArray[this.array[i]] < first) first = this.refArray[this.array[firstIndex = i]]; 
/*     */       }  }
/* 160 */     else { while (i-- != 0) {
/* 161 */         if (this.c.compare(this.refArray[this.array[i]], first) < 0) first = this.refArray[this.array[firstIndex = i]]; 
/*     */       }  }
/* 163 */      return this.firstIndex = firstIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findLast() {
/* 169 */     int i = this.size;
/* 170 */     int lastIndex = --i;
/* 171 */     int last = this.refArray[this.array[lastIndex]];
/* 172 */     if (this.c == null)
/* 173 */     { while (i-- != 0) { if (last < this.refArray[this.array[i]]) last = this.refArray[this.array[lastIndex = i]];  }
/*     */        }
/* 175 */     else { while (i-- != 0) { if (this.c.compare(last, this.refArray[this.array[i]]) < 0) last = this.refArray[this.array[lastIndex = i]];  }
/*     */        }
/* 177 */      return lastIndex;
/*     */   }
/*     */   
/*     */   protected final void ensureNonEmpty() {
/* 181 */     if (this.size == 0) throw new NoSuchElementException();
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
/* 192 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 193 */     if (index >= this.refArray.length) throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
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
/* 207 */     ensureElement(x);
/* 208 */     if (this.size == this.array.length) this.array = IntArrays.grow(this.array, this.size + 1); 
/* 209 */     if (this.firstIndexValid)
/* 210 */     { if (this.c == null)
/* 211 */       { if (this.refArray[x] < this.refArray[this.array[this.firstIndex]]) this.firstIndex = this.size;  }
/* 212 */       else if (this.c.compare(this.refArray[x], this.refArray[this.array[this.firstIndex]]) < 0) { this.firstIndex = this.size; }  }
/* 213 */     else { this.firstIndexValid = false; }
/* 214 */      this.array[this.size++] = x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeue() {
/* 219 */     ensureNonEmpty();
/* 220 */     int firstIndex = findFirst();
/* 221 */     int result = this.array[firstIndex];
/* 222 */     if (--this.size != 0) System.arraycopy(this.array, firstIndex + 1, this.array, firstIndex, this.size - firstIndex); 
/* 223 */     this.firstIndexValid = false;
/* 224 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int first() {
/* 229 */     ensureNonEmpty();
/* 230 */     return this.array[findFirst()];
/*     */   }
/*     */ 
/*     */   
/*     */   public int last() {
/* 235 */     ensureNonEmpty();
/* 236 */     return this.array[findLast()];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 241 */     ensureNonEmpty();
/* 242 */     this.firstIndexValid = false;
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
/* 254 */     ensureElement(index);
/* 255 */     if (index == this.firstIndex) this.firstIndexValid = false;
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 261 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int index) {
/* 266 */     ensureElement(index);
/* 267 */     int[] a = this.array;
/* 268 */     int i = this.size; do {  }
/* 269 */     while (i-- != 0 && a[i] != index);
/* 270 */     if (i < 0) return false; 
/* 271 */     this.firstIndexValid = false;
/* 272 */     if (--this.size != 0) System.arraycopy(a, i + 1, a, i, this.size - i); 
/* 273 */     return true;
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
/* 286 */     int top = this.refArray[this.array[findFirst()]];
/* 287 */     int i = this.size, c = 0;
/* 288 */     while (i-- != 0) { if (top == this.refArray[this.array[i]]) a[c++] = this.array[i];  }
/* 289 */      return c;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 294 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 299 */     this.size = 0;
/* 300 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 305 */     this.array = IntArrays.trim(this.array, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public IntComparator comparator() {
/* 310 */     return this.c;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 315 */     StringBuffer s = new StringBuffer();
/* 316 */     s.append("[");
/* 317 */     for (int i = 0; i < this.size; i++) {
/* 318 */       if (i != 0) s.append(", "); 
/* 319 */       s.append(this.refArray[this.array[i]]);
/*     */     } 
/* 321 */     s.append("]");
/* 322 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntArrayIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */