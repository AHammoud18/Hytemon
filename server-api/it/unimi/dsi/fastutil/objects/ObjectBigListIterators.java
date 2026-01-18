/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.Serializable;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectBigListIterators
/*     */ {
/*     */   public static class EmptyBigListIterator<K>
/*     */     implements ObjectBigListIterator<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean hasNext() {
/*  47 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/*  52 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/*  57 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/*  62 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextIndex() {
/*  67 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousIndex() {
/*  72 */       return -1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/*  77 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public long back(long n) {
/*  82 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/*  87 */       return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {}
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/*  95 */       return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
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
/* 107 */   public static final EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new EmptyBigListIterator();
/*     */   
/*     */   private static class SingletonBigListIterator<K>
/*     */     implements ObjectBigListIterator<K> {
/*     */     private final K element;
/*     */     private int curr;
/*     */     
/*     */     public SingletonBigListIterator(K element) {
/* 115 */       this.element = element;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 120 */       return (this.curr == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 125 */       return (this.curr == 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 130 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 131 */       this.curr = 1;
/* 132 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/* 137 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 138 */       this.curr = 0;
/* 139 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 144 */       Objects.requireNonNull(action);
/* 145 */       if (this.curr == 0) {
/* 146 */         action.accept(this.element);
/* 147 */         this.curr = 1;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextIndex() {
/* 153 */       return this.curr;
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousIndex() {
/* 158 */       return (this.curr - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public long back(long n) {
/* 163 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 164 */       if (n == 0L || this.curr < 1) return 0L; 
/* 165 */       this.curr = 1;
/* 166 */       return 1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 171 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 172 */       if (n == 0L || this.curr > 0) return 0L; 
/* 173 */       this.curr = 0;
/* 174 */       return 1L;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectBigListIterator<K> singleton(K element) {
/* 185 */     return new SingletonBigListIterator<>(element);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBigListIterator<K>
/*     */     implements ObjectBigListIterator<K> {
/*     */     protected final ObjectBigListIterator<? extends K> i;
/*     */     
/*     */     public UnmodifiableBigListIterator(ObjectBigListIterator<? extends K> i) {
/* 193 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 198 */       return this.i.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 203 */       return this.i.hasPrevious();
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 208 */       return this.i.next();
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/* 213 */       return (K)this.i.previous();
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextIndex() {
/* 218 */       return this.i.nextIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousIndex() {
/* 223 */       return this.i.previousIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 228 */       this.i.forEachRemaining(action);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectBigListIterator<K> unmodifiable(ObjectBigListIterator<? extends K> i) {
/* 239 */     return new UnmodifiableBigListIterator<>(i);
/*     */   }
/*     */   
/*     */   public static class BigListIteratorListIterator<K>
/*     */     implements ObjectBigListIterator<K> {
/*     */     protected final ObjectListIterator<K> i;
/*     */     
/*     */     protected BigListIteratorListIterator(ObjectListIterator<K> i) {
/* 247 */       this.i = i;
/*     */     }
/*     */     
/*     */     private int intDisplacement(long n) {
/* 251 */       if (n < -2147483648L || n > 2147483647L) throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements"); 
/* 252 */       return (int)n;
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(K ok) {
/* 257 */       this.i.set(ok);
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(K ok) {
/* 262 */       this.i.add(ok);
/*     */     }
/*     */ 
/*     */     
/*     */     public int back(int n) {
/* 267 */       return this.i.back(n);
/*     */     }
/*     */ 
/*     */     
/*     */     public long back(long n) {
/* 272 */       return this.i.back(intDisplacement(n));
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 277 */       this.i.remove();
/*     */     }
/*     */ 
/*     */     
/*     */     public int skip(int n) {
/* 282 */       return this.i.skip(n);
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 287 */       return this.i.skip(intDisplacement(n));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 292 */       return this.i.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 297 */       return this.i.hasPrevious();
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 302 */       return this.i.next();
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/* 307 */       return this.i.previous();
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextIndex() {
/* 312 */       return this.i.nextIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousIndex() {
/* 317 */       return this.i.previousIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 322 */       this.i.forEachRemaining(action);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectBigListIterator<K> asBigListIterator(ObjectListIterator<K> i) {
/* 333 */     return new BigListIteratorListIterator<>(i);
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
/*     */   public static abstract class AbstractIndexBasedBigIterator<K>
/*     */     extends AbstractObjectIterator<K>
/*     */   {
/*     */     protected final long minPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected long pos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected long lastReturned;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected AbstractIndexBasedBigIterator(long minPos, long initialPos) {
/* 378 */       this.minPos = minPos;
/* 379 */       this.pos = initialPos;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract K get(long param1Long);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract void remove(long param1Long);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract long getMaxPos();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 427 */       return (this.pos < getMaxPos());
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 432 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 433 */       return get(this.lastReturned = this.pos++);
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 438 */       if (this.lastReturned == -1L) throw new IllegalStateException(); 
/* 439 */       remove(this.lastReturned);
/*     */       
/* 441 */       if (this.lastReturned < this.pos) this.pos--; 
/* 442 */       this.lastReturned = -1L;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield pos : J
/*     */       //   4: aload_0
/*     */       //   5: invokevirtual getMaxPos : ()J
/*     */       //   8: lcmp
/*     */       //   9: ifge -> 41
/*     */       //   12: aload_1
/*     */       //   13: aload_0
/*     */       //   14: aload_0
/*     */       //   15: aload_0
/*     */       //   16: dup
/*     */       //   17: getfield pos : J
/*     */       //   20: dup2_x1
/*     */       //   21: lconst_1
/*     */       //   22: ladd
/*     */       //   23: putfield pos : J
/*     */       //   26: dup2_x1
/*     */       //   27: putfield lastReturned : J
/*     */       //   30: invokevirtual get : (J)Ljava/lang/Object;
/*     */       //   33: invokeinterface accept : (Ljava/lang/Object;)V
/*     */       //   38: goto -> 0
/*     */       //   41: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #447	-> 0
/*     */       //   #448	-> 12
/*     */       //   #450	-> 41
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	42	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterators$AbstractIndexBasedBigIterator;
/*     */       //   0	42	1	action	Ljava/util/function/Consumer;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	42	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterators$AbstractIndexBasedBigIterator<TK;>;
/*     */       //   0	42	1	action	Ljava/util/function/Consumer<-TK;>;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 453 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 454 */       long max = getMaxPos();
/* 455 */       long remaining = max - this.pos;
/* 456 */       if (n < remaining) {
/* 457 */         this.pos += n;
/*     */       } else {
/* 459 */         n = remaining;
/* 460 */         this.pos = max;
/*     */       } 
/* 462 */       this.lastReturned = this.pos - 1L;
/* 463 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     public int skip(int n) {
/* 468 */       return SafeMath.safeLongToInt(skip(n));
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
/*     */   public static abstract class AbstractIndexBasedBigListIterator<K>
/*     */     extends AbstractIndexBasedBigIterator<K>
/*     */     implements ObjectBigListIterator<K>
/*     */   {
/*     */     protected AbstractIndexBasedBigListIterator(long minPos, long initialPos) {
/* 485 */       super(minPos, initialPos);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract void add(long param1Long, K param1K);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract void set(long param1Long, K param1K);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 518 */       return (this.pos > this.minPos);
/*     */     }
/*     */ 
/*     */     
/*     */     public K previous() {
/* 523 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 524 */       return get(this.lastReturned = --this.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextIndex() {
/* 529 */       return this.pos;
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousIndex() {
/* 534 */       return this.pos - 1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(K k) {
/* 539 */       add(this.pos++, k);
/* 540 */       this.lastReturned = -1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(K k) {
/* 545 */       if (this.lastReturned == -1L) throw new IllegalStateException(); 
/* 546 */       set(this.lastReturned, k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long back(long n) {
/* 553 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 554 */       long remaining = this.pos - this.minPos;
/* 555 */       if (n < remaining) {
/* 556 */         this.pos -= n;
/*     */       } else {
/* 558 */         n = remaining;
/* 559 */         this.pos = this.minPos;
/*     */       } 
/* 561 */       this.lastReturned = this.pos;
/* 562 */       return n;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectBigListIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */