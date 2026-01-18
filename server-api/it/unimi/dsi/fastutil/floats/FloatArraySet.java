/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FloatArraySet
/*     */   extends AbstractFloatSet
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient float[] a;
/*     */   protected int size;
/*     */   
/*     */   public FloatArraySet(float[] a) {
/*  47 */     this.a = a;
/*  48 */     this.size = a.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatArraySet() {
/*  55 */     this.a = FloatArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatArraySet(int capacity) {
/*  64 */     this.a = new float[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatArraySet(FloatCollection c) {
/*  73 */     this(c.size());
/*  74 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatArraySet(Collection<? extends Float> c) {
/*  83 */     this(c.size());
/*  84 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatArraySet(FloatSet c) {
/*  93 */     this(c.size());
/*  94 */     int i = 0;
/*  95 */     for (FloatIterator floatIterator = c.iterator(); floatIterator.hasNext(); ) { float x = ((Float)floatIterator.next()).floatValue();
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
/*     */   public FloatArraySet(Set<? extends Float> c) {
/* 108 */     this(c.size());
/* 109 */     int i = 0;
/* 110 */     for (Float x : c) {
/* 111 */       this.a[i] = x.floatValue();
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
/*     */   public FloatArraySet(float[] a, int size) {
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
/*     */   public static FloatArraySet of() {
/* 141 */     return ofUnchecked();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatArraySet of(float e) {
/* 151 */     return ofUnchecked(new float[] { e });
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
/*     */   public static FloatArraySet of(float... a) {
/* 168 */     if (a.length == 2) {
/* 169 */       if (Float.floatToIntBits(a[0]) == Float.floatToIntBits(a[1])) {
/* 170 */         throw new IllegalArgumentException("Duplicate element: " + a[1]);
/*     */       }
/* 172 */     } else if (a.length > 2) {
/*     */       
/* 174 */       FloatOpenHashSet.of(a);
/*     */     } 
/* 176 */     return ofUnchecked(a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatArraySet ofUnchecked() {
/* 185 */     return new FloatArraySet();
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
/*     */   public static FloatArraySet ofUnchecked(float... a) {
/* 200 */     return new FloatArraySet(a);
/*     */   }
/*     */   
/*     */   private int findKey(float o) {
/* 204 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(this.a[i]) == Float.floatToIntBits(o)) return i;  }
/* 205 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatIterator iterator() {
/* 212 */     return new FloatIterator() {
/* 213 */         int next = 0;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 217 */           return (this.next < FloatArraySet.this.size);
/*     */         }
/*     */ 
/*     */         
/*     */         public float nextFloat() {
/* 222 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 223 */           return FloatArraySet.this.a[this.next++];
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 228 */           int tail = FloatArraySet.this.size-- - this.next--;
/* 229 */           System.arraycopy(FloatArraySet.this.a, this.next + 1, FloatArraySet.this.a, this.next, tail);
/*     */         }
/*     */ 
/*     */         
/*     */         public int skip(int n) {
/* 234 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 235 */           int remaining = FloatArraySet.this.size - this.next;
/* 236 */           if (n < remaining) {
/* 237 */             this.next += n;
/* 238 */             return n;
/*     */           } 
/* 240 */           n = remaining;
/* 241 */           this.next = FloatArraySet.this.size;
/* 242 */           return n;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private final class Spliterator
/*     */     implements FloatSpliterator
/*     */   {
/*     */     boolean hasSplit = false;
/*     */     int pos;
/*     */     int max;
/*     */     
/*     */     public Spliterator() {
/* 256 */       this(0, FloatArraySet.this.size, false);
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
/* 267 */       return this.hasSplit ? this.max : FloatArraySet.this.size;
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
/*     */     public boolean tryAdvance(FloatConsumer action) {
/* 282 */       if (this.pos >= getWorkingMax()) return false; 
/* 283 */       action.accept(FloatArraySet.this.a[this.pos++]);
/* 284 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(FloatConsumer action) {
/* 289 */       for (int max = getWorkingMax(); this.pos < max; this.pos++) {
/* 290 */         action.accept(FloatArraySet.this.a[this.pos]);
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
/*     */     public FloatSpliterator trySplit() {
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
/*     */   public FloatSpliterator spliterator() {
/* 342 */     return new Spliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(float k) {
/* 347 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 352 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(float k) {
/* 357 */     int pos = findKey(k);
/* 358 */     if (pos == -1) return false; 
/* 359 */     int tail = this.size - pos - 1;
/* 360 */     for (int i = 0; i < tail; ) { this.a[pos + i] = this.a[pos + i + 1]; i++; }
/* 361 */      this.size--;
/* 362 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(float k) {
/* 367 */     int pos = findKey(k);
/* 368 */     if (pos != -1) return false; 
/* 369 */     if (this.size == this.a.length) {
/* 370 */       float[] b = new float[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public float[] toFloatArray() {
/* 390 */     if (this.size == 0) return FloatArrays.EMPTY_ARRAY; 
/* 391 */     return Arrays.copyOf(this.a, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] toArray(float[] a) {
/* 396 */     if (a == null || a.length < this.size) a = new float[this.size]; 
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
/*     */   public FloatArraySet clone() {
/*     */     FloatArraySet c;
/*     */     try {
/* 415 */       c = (FloatArraySet)super.clone();
/* 416 */     } catch (CloneNotSupportedException cantHappen) {
/* 417 */       throw new InternalError();
/*     */     } 
/* 419 */     c.a = (float[])this.a.clone();
/* 420 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 424 */     s.defaultWriteObject();
/* 425 */     for (int i = 0; i < this.size; ) { s.writeFloat(this.a[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 429 */     s.defaultReadObject();
/* 430 */     this.a = new float[this.size];
/* 431 */     for (int i = 0; i < this.size; ) { this.a[i] = s.readFloat(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatArraySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */