/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.IndirectPriorityQueue;
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
/*     */ 
/*     */ public class ObjectArrayIndirectPriorityQueue<K>
/*     */   implements IndirectPriorityQueue<K>
/*     */ {
/*     */   protected K[] refArray;
/*  40 */   protected int[] array = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */   
/*     */   protected Comparator<? super K> c;
/*     */ 
/*     */   
/*     */   protected int firstIndex;
/*     */ 
/*     */   
/*     */   protected boolean firstIndexValid;
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int capacity, Comparator<? super K> c) {
/*  58 */     if (capacity > 0) this.array = new int[capacity]; 
/*  59 */     this.refArray = refArray;
/*  60 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int capacity) {
/*  70 */     this(refArray, capacity, (Comparator<? super K>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, Comparator<? super K> c) {
/*  81 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray) {
/*  91 */     this(refArray, refArray.length, (Comparator<? super K>)null);
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int[] a, int size, Comparator<? super K> c) {
/* 106 */     this(refArray, 0, c);
/* 107 */     this.array = a;
/* 108 */     this.size = size;
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int[] a, Comparator<? super K> c) {
/* 122 */     this(refArray, a, a.length, c);
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int[] a, int size) {
/* 136 */     this(refArray, a, size, null);
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int[] a) {
/* 149 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 155 */     if (this.firstIndexValid) return this.firstIndex; 
/* 156 */     this.firstIndexValid = true;
/* 157 */     int i = this.size;
/* 158 */     int firstIndex = --i;
/* 159 */     K first = this.refArray[this.array[firstIndex]];
/* 160 */     if (this.c == null) { while (i-- != 0) {
/* 161 */         if (((Comparable<K>)this.refArray[this.array[i]]).compareTo(first) < 0) first = this.refArray[this.array[firstIndex = i]]; 
/*     */       }  }
/* 163 */     else { while (i-- != 0) {
/* 164 */         if (this.c.compare(this.refArray[this.array[i]], first) < 0) first = this.refArray[this.array[firstIndex = i]]; 
/*     */       }  }
/* 166 */      return this.firstIndex = firstIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findLast() {
/* 172 */     int i = this.size;
/* 173 */     int lastIndex = --i;
/* 174 */     K last = this.refArray[this.array[lastIndex]];
/* 175 */     if (this.c == null)
/* 176 */     { while (i-- != 0) { if (((Comparable<K>)last).compareTo(this.refArray[this.array[i]]) < 0) last = this.refArray[this.array[lastIndex = i]];  }
/*     */        }
/* 178 */     else { while (i-- != 0) { if (this.c.compare(last, this.refArray[this.array[i]]) < 0) last = this.refArray[this.array[lastIndex = i]];  }
/*     */        }
/* 180 */      return lastIndex;
/*     */   }
/*     */   
/*     */   protected final void ensureNonEmpty() {
/* 184 */     if (this.size == 0) throw new NoSuchElementException();
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
/* 195 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 196 */     if (index >= this.refArray.length) throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
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
/* 210 */     ensureElement(x);
/* 211 */     if (this.size == this.array.length) this.array = IntArrays.grow(this.array, this.size + 1); 
/* 212 */     if (this.firstIndexValid)
/* 213 */     { if (this.c == null)
/* 214 */       { if (((Comparable<K>)this.refArray[x]).compareTo(this.refArray[this.array[this.firstIndex]]) < 0) this.firstIndex = this.size;  }
/* 215 */       else if (this.c.compare(this.refArray[x], this.refArray[this.array[this.firstIndex]]) < 0) { this.firstIndex = this.size; }  }
/* 216 */     else { this.firstIndexValid = false; }
/* 217 */      this.array[this.size++] = x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int dequeue() {
/* 222 */     ensureNonEmpty();
/* 223 */     int firstIndex = findFirst();
/* 224 */     int result = this.array[firstIndex];
/* 225 */     if (--this.size != 0) System.arraycopy(this.array, firstIndex + 1, this.array, firstIndex, this.size - firstIndex); 
/* 226 */     this.firstIndexValid = false;
/* 227 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int first() {
/* 232 */     ensureNonEmpty();
/* 233 */     return this.array[findFirst()];
/*     */   }
/*     */ 
/*     */   
/*     */   public int last() {
/* 238 */     ensureNonEmpty();
/* 239 */     return this.array[findLast()];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 244 */     ensureNonEmpty();
/* 245 */     this.firstIndexValid = false;
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
/* 257 */     ensureElement(index);
/* 258 */     if (index == this.firstIndex) this.firstIndexValid = false;
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 264 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int index) {
/* 269 */     ensureElement(index);
/* 270 */     int[] a = this.array;
/* 271 */     int i = this.size; do {  }
/* 272 */     while (i-- != 0 && a[i] != index);
/* 273 */     if (i < 0) return false; 
/* 274 */     this.firstIndexValid = false;
/* 275 */     if (--this.size != 0) System.arraycopy(a, i + 1, a, i, this.size - i); 
/* 276 */     return true;
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
/* 289 */     K top = this.refArray[this.array[findFirst()]];
/* 290 */     int i = this.size, c = 0;
/* 291 */     while (i-- != 0) { if (top.equals(this.refArray[this.array[i]])) a[c++] = this.array[i];  }
/* 292 */      return c;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 297 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 302 */     this.size = 0;
/* 303 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 308 */     this.array = IntArrays.trim(this.array, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparator<? super K> comparator() {
/* 313 */     return this.c;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 318 */     StringBuffer s = new StringBuffer();
/* 319 */     s.append("[");
/* 320 */     for (int i = 0; i < this.size; i++) {
/* 321 */       if (i != 0) s.append(", "); 
/* 322 */       s.append(this.refArray[this.array[i]]);
/*     */     } 
/* 324 */     s.append("]");
/* 325 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectArrayIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */