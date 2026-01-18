/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public class ShortArrayPriorityQueue
/*     */   implements ShortPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  35 */   protected transient short[] array = ShortArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */   
/*     */   protected ShortComparator c;
/*     */ 
/*     */   
/*     */   protected transient int firstIndex;
/*     */ 
/*     */   
/*     */   protected transient boolean firstIndexValid;
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortArrayPriorityQueue(int capacity, ShortComparator c) {
/*  53 */     if (capacity > 0) this.array = new short[capacity]; 
/*  54 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortArrayPriorityQueue(int capacity) {
/*  63 */     this(capacity, (ShortComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortArrayPriorityQueue(ShortComparator c) {
/*  72 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortArrayPriorityQueue() {
/*  79 */     this(0, (ShortComparator)null);
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
/*     */   public ShortArrayPriorityQueue(short[] a, int size, ShortComparator c) {
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
/*     */   public ShortArrayPriorityQueue(short[] a, ShortComparator c) {
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
/*     */   public ShortArrayPriorityQueue(short[] a, int size) {
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
/*     */   public ShortArrayPriorityQueue(short[] a) {
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
/* 143 */     short first = this.array[firstIndex];
/* 144 */     if (this.c == null)
/* 145 */     { while (i-- != 0) { if (this.array[i] < first) first = this.array[firstIndex = i];  }  }
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
/*     */   public void enqueue(short x) {
/* 159 */     if (this.size == this.array.length) this.array = ShortArrays.grow(this.array, this.size + 1); 
/* 160 */     if (this.firstIndexValid)
/* 161 */     { if (this.c == null)
/* 162 */       { if (x < this.array[this.firstIndex]) this.firstIndex = this.size;  }
/* 163 */       else if (this.c.compare(x, this.array[this.firstIndex]) < 0) { this.firstIndex = this.size; }  }
/* 164 */     else { this.firstIndexValid = false; }
/* 165 */      this.array[this.size++] = x;
/*     */   }
/*     */ 
/*     */   
/*     */   public short dequeueShort() {
/* 170 */     ensureNonEmpty();
/* 171 */     int first = findFirst();
/* 172 */     short result = this.array[first];
/* 173 */     System.arraycopy(this.array, first + 1, this.array, first, --this.size - first);
/* 174 */     this.firstIndexValid = false;
/* 175 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public short firstShort() {
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
/* 203 */     this.array = ShortArrays.trim(this.array, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public ShortComparator comparator() {
/* 208 */     return this.c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 212 */     s.defaultWriteObject();
/* 213 */     s.writeInt(this.array.length);
/* 214 */     for (int i = 0; i < this.size; ) { s.writeShort(this.array[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 218 */     s.defaultReadObject();
/* 219 */     this.array = new short[s.readInt()];
/* 220 */     for (int i = 0; i < this.size; ) { this.array[i] = s.readShort(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortArrayPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */