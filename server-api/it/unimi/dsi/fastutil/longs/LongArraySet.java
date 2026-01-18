/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.function.LongConsumer;
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
/*     */ public class LongArraySet
/*     */   extends AbstractLongSet
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient long[] a;
/*     */   protected int size;
/*     */   
/*     */   public LongArraySet(long[] a) {
/*  47 */     this.a = a;
/*  48 */     this.size = a.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArraySet() {
/*  55 */     this.a = LongArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArraySet(int capacity) {
/*  64 */     this.a = new long[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArraySet(LongCollection c) {
/*  73 */     this(c.size());
/*  74 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArraySet(Collection<? extends Long> c) {
/*  83 */     this(c.size());
/*  84 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArraySet(LongSet c) {
/*  93 */     this(c.size());
/*  94 */     int i = 0;
/*  95 */     for (LongIterator longIterator = c.iterator(); longIterator.hasNext(); ) { long x = ((Long)longIterator.next()).longValue();
/*  96 */       this.a[i] = x;
/*  97 */       i++; }
/*     */     
/*  99 */     this.size = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArraySet(Set<? extends Long> c) {
/* 108 */     this(c.size());
/* 109 */     int i = 0;
/* 110 */     for (Long x : c) {
/* 111 */       this.a[i] = x.longValue();
/* 112 */       i++;
/*     */     } 
/* 114 */     this.size = i;
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
/*     */   public LongArraySet(long[] a, int size) {
/* 129 */     this.a = a;
/* 130 */     this.size = size;
/* 131 */     if (size > a.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongArraySet of() {
/* 141 */     return ofUnchecked();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongArraySet of(long e) {
/* 151 */     return ofUnchecked(new long[] { e });
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
/*     */   
/*     */   public static LongArraySet of(long... a) {
/* 168 */     if (a.length == 2) {
/* 169 */       if (a[0] == a[1]) {
/* 170 */         throw new IllegalArgumentException("Duplicate element: " + a[1]);
/*     */       }
/* 172 */     } else if (a.length > 2) {
/*     */       
/* 174 */       LongOpenHashSet.of(a);
/*     */     } 
/* 176 */     return ofUnchecked(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongArraySet ofUnchecked() {
/* 185 */     return new LongArraySet();
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
/*     */   public static LongArraySet ofUnchecked(long... a) {
/* 200 */     return new LongArraySet(a);
/*     */   }
/*     */   
/*     */   private int findKey(long o) {
/* 204 */     for (int i = this.size; i-- != 0;) { if (this.a[i] == o) return i;  }
/* 205 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongIterator iterator() {
/* 212 */     return new LongIterator() {
/* 213 */         int next = 0;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 217 */           return (this.next < LongArraySet.this.size);
/*     */         }
/*     */ 
/*     */         
/*     */         public long nextLong() {
/* 222 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 223 */           return LongArraySet.this.a[this.next++];
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 228 */           int tail = LongArraySet.this.size-- - this.next--;
/* 229 */           System.arraycopy(LongArraySet.this.a, this.next + 1, LongArraySet.this.a, this.next, tail);
/*     */         }
/*     */ 
/*     */         
/*     */         public int skip(int n) {
/* 234 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 235 */           int remaining = LongArraySet.this.size - this.next;
/* 236 */           if (n < remaining) {
/* 237 */             this.next += n;
/* 238 */             return n;
/*     */           } 
/* 240 */           n = remaining;
/* 241 */           this.next = LongArraySet.this.size;
/* 242 */           return n;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private final class Spliterator
/*     */     implements LongSpliterator
/*     */   {
/*     */     boolean hasSplit = false;
/*     */     int pos;
/*     */     int max;
/*     */     
/*     */     public Spliterator() {
/* 256 */       this(0, LongArraySet.this.size, false);
/*     */     }
/*     */     
/*     */     private Spliterator(int pos, int max, boolean hasSplit) {
/* 260 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/* 261 */       this.pos = pos;
/* 262 */       this.max = max;
/* 263 */       this.hasSplit = hasSplit;
/*     */     }
/*     */     
/*     */     private int getWorkingMax() {
/* 267 */       return this.hasSplit ? this.max : LongArraySet.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 272 */       return 16721;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 277 */       return (getWorkingMax() - this.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(LongConsumer action) {
/* 282 */       if (this.pos >= getWorkingMax()) return false; 
/* 283 */       action.accept(LongArraySet.this.a[this.pos++]);
/* 284 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(LongConsumer action) {
/* 289 */       for (int max = getWorkingMax(); this.pos < max; this.pos++) {
/* 290 */         action.accept(LongArraySet.this.a[this.pos]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 296 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 297 */       int max = getWorkingMax();
/* 298 */       if (this.pos >= max) return 0L; 
/* 299 */       int remaining = max - this.pos;
/* 300 */       if (n < remaining) {
/* 301 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/* 302 */         return n;
/*     */       } 
/* 304 */       n = remaining;
/* 305 */       this.pos = max;
/* 306 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator trySplit() {
/* 311 */       int max = getWorkingMax();
/* 312 */       int retLen = max - this.pos >> 1;
/* 313 */       if (retLen <= 1) return null;
/*     */       
/* 315 */       this.max = max;
/* 316 */       int myNewPos = this.pos + retLen;
/* 317 */       int retMax = myNewPos;
/* 318 */       int oldPos = this.pos;
/* 319 */       this.pos = myNewPos;
/* 320 */       this.hasSplit = true;
/* 321 */       return new Spliterator(oldPos, retMax, true);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongSpliterator spliterator() {
/* 342 */     return new Spliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(long k) {
/* 347 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 352 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(long k) {
/* 357 */     int pos = findKey(k);
/* 358 */     if (pos == -1) return false; 
/* 359 */     int tail = this.size - pos - 1;
/* 360 */     for (int i = 0; i < tail; ) { this.a[pos + i] = this.a[pos + i + 1]; i++; }
/* 361 */      this.size--;
/* 362 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(long k) {
/* 367 */     int pos = findKey(k);
/* 368 */     if (pos != -1) return false; 
/* 369 */     if (this.size == this.a.length) {
/* 370 */       long[] b = new long[(this.size == 0) ? 2 : (this.size * 2)];
/* 371 */       for (int i = this.size; i-- != 0; b[i] = this.a[i]);
/* 372 */       this.a = b;
/*     */     } 
/* 374 */     this.a[this.size++] = k;
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 380 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 385 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] toLongArray() {
/* 390 */     if (this.size == 0) return LongArrays.EMPTY_ARRAY; 
/* 391 */     return Arrays.copyOf(this.a, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] toArray(long[] a) {
/* 396 */     if (a == null || a.length < this.size) a = new long[this.size]; 
/* 397 */     System.arraycopy(this.a, 0, a, 0, this.size);
/* 398 */     return a;
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
/*     */   public LongArraySet clone() {
/*     */     LongArraySet c;
/*     */     try {
/* 415 */       c = (LongArraySet)super.clone();
/* 416 */     } catch (CloneNotSupportedException cantHappen) {
/* 417 */       throw new InternalError();
/*     */     } 
/* 419 */     c.a = (long[])this.a.clone();
/* 420 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 424 */     s.defaultWriteObject();
/* 425 */     for (int i = 0; i < this.size; ) { s.writeLong(this.a[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 429 */     s.defaultReadObject();
/* 430 */     this.a = new long[this.size];
/* 431 */     for (int i = 0; i < this.size; ) { this.a[i] = s.readLong(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongArraySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */