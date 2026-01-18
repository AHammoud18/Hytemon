/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceArraySet<K>
/*     */   extends AbstractReferenceSet<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient Object[] a;
/*     */   protected int size;
/*     */   
/*     */   public ReferenceArraySet(Object[] a) {
/*  48 */     this.a = a;
/*  49 */     this.size = a.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArraySet() {
/*  56 */     this.a = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArraySet(int capacity) {
/*  65 */     this.a = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArraySet(ReferenceCollection<K> c) {
/*  74 */     this(c.size());
/*  75 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArraySet(Collection<? extends K> c) {
/*  84 */     this(c.size());
/*  85 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArraySet(ReferenceSet<K> c) {
/*  94 */     this(c.size());
/*  95 */     int i = 0;
/*  96 */     for (ObjectIterator<K> objectIterator = c.iterator(); objectIterator.hasNext(); ) { Object x = objectIterator.next();
/*  97 */       this.a[i] = x;
/*  98 */       i++; }
/*     */     
/* 100 */     this.size = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArraySet(Set<? extends K> c) {
/* 109 */     this(c.size());
/* 110 */     int i = 0;
/* 111 */     for (K x : c) {
/* 112 */       this.a[i] = x;
/* 113 */       i++;
/*     */     } 
/* 115 */     this.size = i;
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
/*     */   public ReferenceArraySet(Object[] a, int size) {
/* 130 */     this.a = a;
/* 131 */     this.size = size;
/* 132 */     if (size > a.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceArraySet<K> of() {
/* 142 */     return ofUnchecked();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceArraySet<K> of(K e) {
/* 152 */     return ofUnchecked((K[])new Object[] { e });
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
/*     */   @SafeVarargs
/*     */   public static <K> ReferenceArraySet<K> of(K... a) {
/* 169 */     if (a.length == 2) {
/* 170 */       if (a[0] == a[1]) {
/* 171 */         throw new IllegalArgumentException("Duplicate element: " + a[1]);
/*     */       }
/* 173 */     } else if (a.length > 2) {
/*     */       
/* 175 */       ReferenceOpenHashSet.of(a);
/*     */     } 
/* 177 */     return ofUnchecked(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceArraySet<K> ofUnchecked() {
/* 186 */     return new ReferenceArraySet<>();
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
/*     */   @SafeVarargs
/*     */   public static <K> ReferenceArraySet<K> ofUnchecked(K... a) {
/* 201 */     return new ReferenceArraySet<>((Object[])a);
/*     */   }
/*     */   
/*     */   private int findKey(Object o) {
/* 205 */     for (int i = this.size; i-- != 0;) { if (this.a[i] == o) return i;  }
/* 206 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 213 */     return new ObjectIterator<K>() {
/* 214 */         int next = 0;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 218 */           return (this.next < ReferenceArraySet.this.size);
/*     */         }
/*     */ 
/*     */         
/*     */         public K next() {
/* 223 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 224 */           return (K)ReferenceArraySet.this.a[this.next++];
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 229 */           int tail = ReferenceArraySet.this.size-- - this.next--;
/* 230 */           System.arraycopy(ReferenceArraySet.this.a, this.next + 1, ReferenceArraySet.this.a, this.next, tail);
/* 231 */           ReferenceArraySet.this.a[ReferenceArraySet.this.size] = null;
/*     */         }
/*     */ 
/*     */         
/*     */         public int skip(int n) {
/* 236 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 237 */           int remaining = ReferenceArraySet.this.size - this.next;
/* 238 */           if (n < remaining) {
/* 239 */             this.next += n;
/* 240 */             return n;
/*     */           } 
/* 242 */           n = remaining;
/* 243 */           this.next = ReferenceArraySet.this.size;
/* 244 */           return n;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private final class Spliterator
/*     */     implements ObjectSpliterator<K>
/*     */   {
/*     */     boolean hasSplit = false;
/*     */     int pos;
/*     */     int max;
/*     */     
/*     */     public Spliterator() {
/* 258 */       this(0, ReferenceArraySet.this.size, false);
/*     */     }
/*     */     
/*     */     private Spliterator(int pos, int max, boolean hasSplit) {
/* 262 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/* 263 */       this.pos = pos;
/* 264 */       this.max = max;
/* 265 */       this.hasSplit = hasSplit;
/*     */     }
/*     */     
/*     */     private int getWorkingMax() {
/* 269 */       return this.hasSplit ? this.max : ReferenceArraySet.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 274 */       return 16465;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 279 */       return (getWorkingMax() - this.pos);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super K> action) {
/* 285 */       if (this.pos >= getWorkingMax()) return false; 
/* 286 */       action.accept((K)ReferenceArraySet.this.a[this.pos++]);
/* 287 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 293 */       for (int max = getWorkingMax(); this.pos < max; this.pos++) {
/* 294 */         action.accept((K)ReferenceArraySet.this.a[this.pos]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 300 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 301 */       int max = getWorkingMax();
/* 302 */       if (this.pos >= max) return 0L; 
/* 303 */       int remaining = max - this.pos;
/* 304 */       if (n < remaining) {
/* 305 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/* 306 */         return n;
/*     */       } 
/* 308 */       n = remaining;
/* 309 */       this.pos = max;
/* 310 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> trySplit() {
/* 315 */       int max = getWorkingMax();
/* 316 */       int retLen = max - this.pos >> 1;
/* 317 */       if (retLen <= 1) return null;
/*     */       
/* 319 */       this.max = max;
/* 320 */       int myNewPos = this.pos + retLen;
/* 321 */       int retMax = myNewPos;
/* 322 */       int oldPos = this.pos;
/* 323 */       this.pos = myNewPos;
/* 324 */       this.hasSplit = true;
/* 325 */       return new Spliterator(oldPos, retMax, true);
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
/*     */   public ObjectSpliterator<K> spliterator() {
/* 346 */     return new Spliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object k) {
/* 351 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 356 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object k) {
/* 361 */     int pos = findKey(k);
/* 362 */     if (pos == -1) return false; 
/* 363 */     int tail = this.size - pos - 1;
/* 364 */     for (int i = 0; i < tail; ) { this.a[pos + i] = this.a[pos + i + 1]; i++; }
/* 365 */      this.size--;
/* 366 */     this.a[this.size] = null;
/* 367 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/* 372 */     int pos = findKey(k);
/* 373 */     if (pos != -1) return false; 
/* 374 */     if (this.size == this.a.length) {
/* 375 */       Object[] b = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 376 */       for (int i = this.size; i-- != 0; b[i] = this.a[i]);
/* 377 */       this.a = b;
/*     */     } 
/* 379 */     this.a[this.size++] = k;
/* 380 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 385 */     Arrays.fill(this.a, 0, this.size, (Object)null);
/* 386 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 391 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 396 */     int size = size();
/*     */     
/* 398 */     if (size == 0) return ObjectArrays.EMPTY_ARRAY; 
/* 399 */     return Arrays.copyOf(this.a, size, Object[].class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 405 */     if (a == null) {
/* 406 */       a = (T[])new Object[this.size];
/* 407 */     } else if (a.length < this.size) {
/* 408 */       a = (T[])Array.newInstance(a.getClass().getComponentType(), this.size);
/*     */     } 
/* 410 */     System.arraycopy(this.a, 0, a, 0, this.size);
/* 411 */     if (a.length > this.size) {
/* 412 */       a[this.size] = null;
/*     */     }
/* 414 */     return a;
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
/*     */   public ReferenceArraySet<K> clone() {
/*     */     ReferenceArraySet<K> c;
/*     */     try {
/* 431 */       c = (ReferenceArraySet<K>)super.clone();
/* 432 */     } catch (CloneNotSupportedException cantHappen) {
/* 433 */       throw new InternalError();
/*     */     } 
/* 435 */     c.a = (Object[])this.a.clone();
/* 436 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 440 */     s.defaultWriteObject();
/* 441 */     for (int i = 0; i < this.size; ) { s.writeObject(this.a[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 445 */     s.defaultReadObject();
/* 446 */     this.a = new Object[this.size];
/* 447 */     for (int i = 0; i < this.size; ) { this.a[i] = s.readObject(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceArraySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */