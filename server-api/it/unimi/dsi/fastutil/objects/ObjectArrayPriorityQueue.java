/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ public class ObjectArrayPriorityQueue<K>
/*     */   implements PriorityQueue<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  37 */   protected transient K[] array = (K[])ObjectArrays.EMPTY_ARRAY;
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
/*     */   protected transient int firstIndex;
/*     */ 
/*     */   
/*     */   protected transient boolean firstIndexValid;
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayPriorityQueue(int capacity, Comparator<? super K> c) {
/*  56 */     if (capacity > 0) this.array = (K[])new Object[capacity]; 
/*  57 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayPriorityQueue(int capacity) {
/*  66 */     this(capacity, (Comparator<? super K>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayPriorityQueue(Comparator<? super K> c) {
/*  75 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayPriorityQueue() {
/*  82 */     this(0, (Comparator<? super K>)null);
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
/*     */   public ObjectArrayPriorityQueue(K[] a, int size, Comparator<? super K> c) {
/*  96 */     this(c);
/*  97 */     this.array = a;
/*  98 */     this.size = size;
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
/*     */   public ObjectArrayPriorityQueue(K[] a, Comparator<? super K> c) {
/* 111 */     this(a, a.length, c);
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
/*     */   public ObjectArrayPriorityQueue(K[] a, int size) {
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
/*     */   public ObjectArrayPriorityQueue(K[] a) {
/* 136 */     this(a, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 142 */     if (this.firstIndexValid) return this.firstIndex; 
/* 143 */     this.firstIndexValid = true;
/* 144 */     int i = this.size;
/* 145 */     int firstIndex = --i;
/* 146 */     K first = this.array[firstIndex];
/* 147 */     if (this.c == null)
/* 148 */     { while (i-- != 0) { if (((Comparable<K>)this.array[i]).compareTo(first) < 0) first = this.array[firstIndex = i];  }  }
/* 149 */     else { while (i-- != 0) {
/* 150 */         if (this.c.compare(this.array[i], first) < 0) first = this.array[firstIndex = i]; 
/*     */       }  }
/* 152 */      return this.firstIndex = firstIndex;
/*     */   }
/*     */   
/*     */   private void ensureNonEmpty() {
/* 156 */     if (this.size == 0) throw new NoSuchElementException();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(K x) {
/* 162 */     if (this.size == this.array.length) this.array = ObjectArrays.grow(this.array, this.size + 1); 
/* 163 */     if (this.firstIndexValid)
/* 164 */     { if (this.c == null)
/* 165 */       { if (((Comparable<K>)x).compareTo(this.array[this.firstIndex]) < 0) this.firstIndex = this.size;  }
/* 166 */       else if (this.c.compare(x, this.array[this.firstIndex]) < 0) { this.firstIndex = this.size; }  }
/* 167 */     else { this.firstIndexValid = false; }
/* 168 */      this.array[this.size++] = x;
/*     */   }
/*     */ 
/*     */   
/*     */   public K dequeue() {
/* 173 */     ensureNonEmpty();
/* 174 */     int first = findFirst();
/* 175 */     K result = this.array[first];
/* 176 */     System.arraycopy(this.array, first + 1, this.array, first, --this.size - first);
/* 177 */     this.array[this.size] = null;
/* 178 */     this.firstIndexValid = false;
/* 179 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public K first() {
/* 184 */     ensureNonEmpty();
/* 185 */     return this.array[findFirst()];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 190 */     ensureNonEmpty();
/* 191 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 196 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 201 */     Arrays.fill((Object[])this.array, 0, this.size, (Object)null);
/* 202 */     this.size = 0;
/* 203 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 208 */     this.array = ObjectArrays.trim(this.array, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparator<? super K> comparator() {
/* 213 */     return this.c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 217 */     s.defaultWriteObject();
/* 218 */     s.writeInt(this.array.length);
/* 219 */     for (int i = 0; i < this.size; ) { s.writeObject(this.array[i]); i++; }
/*     */   
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 224 */     s.defaultReadObject();
/* 225 */     this.array = (K[])new Object[s.readInt()];
/* 226 */     for (int i = 0; i < this.size; ) { this.array[i] = (K)s.readObject(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectArrayPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */