/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class CharBigListIterators
/*     */ {
/*     */   public static class EmptyBigListIterator
/*     */     implements CharBigListIterator, Serializable, Cloneable
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
/*     */     public char nextChar() {
/*  57 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char previousChar() {
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
/*  87 */       return CharBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(CharConsumer action) {}
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEachRemaining(Consumer<? super Character> action) {}
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 100 */       return CharBigListIterators.EMPTY_BIG_LIST_ITERATOR;
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
/* 112 */   public static final EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new EmptyBigListIterator();
/*     */   
/*     */   private static class SingletonBigListIterator
/*     */     implements CharBigListIterator {
/*     */     private final char element;
/*     */     private int curr;
/*     */     
/*     */     public SingletonBigListIterator(char element) {
/* 120 */       this.element = element;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 125 */       return (this.curr == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 130 */       return (this.curr == 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 135 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 136 */       this.curr = 1;
/* 137 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public char previousChar() {
/* 142 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 143 */       this.curr = 0;
/* 144 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(CharConsumer action) {
/* 149 */       Objects.requireNonNull(action);
/* 150 */       if (this.curr == 0) {
/* 151 */         action.accept(this.element);
/* 152 */         this.curr = 1;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextIndex() {
/* 158 */       return this.curr;
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousIndex() {
/* 163 */       return (this.curr - 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public long back(long n) {
/* 168 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 169 */       if (n == 0L || this.curr < 1) return 0L; 
/* 170 */       this.curr = 1;
/* 171 */       return 1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 176 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 177 */       if (n == 0L || this.curr > 0) return 0L; 
/* 178 */       this.curr = 0;
/* 179 */       return 1L;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharBigListIterator singleton(char element) {
/* 190 */     return new SingletonBigListIterator(element);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBigListIterator
/*     */     implements CharBigListIterator {
/*     */     protected final CharBigListIterator i;
/*     */     
/*     */     public UnmodifiableBigListIterator(CharBigListIterator i) {
/* 198 */       this.i = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 203 */       return this.i.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 208 */       return this.i.hasPrevious();
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 213 */       return this.i.nextChar();
/*     */     }
/*     */ 
/*     */     
/*     */     public char previousChar() {
/* 218 */       return this.i.previousChar();
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextIndex() {
/* 223 */       return this.i.nextIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousIndex() {
/* 228 */       return this.i.previousIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(CharConsumer action) {
/* 233 */       this.i.forEachRemaining(action);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEachRemaining(Consumer<? super Character> action) {
/* 239 */       this.i.forEachRemaining(action);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharBigListIterator unmodifiable(CharBigListIterator i) {
/* 250 */     return new UnmodifiableBigListIterator(i);
/*     */   }
/*     */   
/*     */   public static class BigListIteratorListIterator
/*     */     implements CharBigListIterator {
/*     */     protected final CharListIterator i;
/*     */     
/*     */     protected BigListIteratorListIterator(CharListIterator i) {
/* 258 */       this.i = i;
/*     */     }
/*     */     
/*     */     private int intDisplacement(long n) {
/* 262 */       if (n < -2147483648L || n > 2147483647L) throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements"); 
/* 263 */       return (int)n;
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(char ok) {
/* 268 */       this.i.set(ok);
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(char ok) {
/* 273 */       this.i.add(ok);
/*     */     }
/*     */ 
/*     */     
/*     */     public int back(int n) {
/* 278 */       return this.i.back(n);
/*     */     }
/*     */ 
/*     */     
/*     */     public long back(long n) {
/* 283 */       return this.i.back(intDisplacement(n));
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 288 */       this.i.remove();
/*     */     }
/*     */ 
/*     */     
/*     */     public int skip(int n) {
/* 293 */       return this.i.skip(n);
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 298 */       return this.i.skip(intDisplacement(n));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 303 */       return this.i.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/* 308 */       return this.i.hasPrevious();
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 313 */       return this.i.nextChar();
/*     */     }
/*     */ 
/*     */     
/*     */     public char previousChar() {
/* 318 */       return this.i.previousChar();
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextIndex() {
/* 323 */       return this.i.nextIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousIndex() {
/* 328 */       return this.i.previousIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(CharConsumer action) {
/* 333 */       this.i.forEachRemaining(action);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEachRemaining(Consumer<? super Character> action) {
/* 339 */       this.i.forEachRemaining(action);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharBigListIterator asBigListIterator(CharListIterator i) {
/* 350 */     return new BigListIteratorListIterator(i);
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
/*     */   public static abstract class AbstractIndexBasedBigIterator
/*     */     extends AbstractCharIterator
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
/* 395 */       this.minPos = minPos;
/* 396 */       this.pos = initialPos;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 444 */       return (this.pos < getMaxPos());
/*     */     }
/*     */ 
/*     */     
/*     */     public char nextChar() {
/* 449 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 450 */       return get(this.lastReturned = this.pos++);
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 455 */       if (this.lastReturned == -1L) throw new IllegalStateException(); 
/* 456 */       remove(this.lastReturned);
/*     */       
/* 458 */       if (this.lastReturned < this.pos) this.pos--; 
/* 459 */       this.lastReturned = -1L;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(CharConsumer action) {
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
/*     */       //   30: invokevirtual get : (J)C
/*     */       //   33: invokeinterface accept : (C)V
/*     */       //   38: goto -> 0
/*     */       //   41: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #464	-> 0
/*     */       //   #465	-> 12
/*     */       //   #467	-> 41
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	42	0	this	Lit/unimi/dsi/fastutil/chars/CharBigListIterators$AbstractIndexBasedBigIterator;
/*     */       //   0	42	1	action	Lit/unimi/dsi/fastutil/chars/CharConsumer;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 470 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 471 */       long max = getMaxPos();
/* 472 */       long remaining = max - this.pos;
/* 473 */       if (n < remaining) {
/* 474 */         this.pos += n;
/*     */       } else {
/* 476 */         n = remaining;
/* 477 */         this.pos = max;
/*     */       } 
/* 479 */       this.lastReturned = this.pos - 1L;
/* 480 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     public int skip(int n) {
/* 485 */       return SafeMath.safeLongToInt(skip(n));
/*     */     }
/*     */ 
/*     */     
/*     */     protected abstract char get(long param1Long);
/*     */ 
/*     */     
/*     */     protected abstract void remove(long param1Long);
/*     */     
/*     */     protected abstract long getMaxPos();
/*     */   }
/*     */   
/*     */   public static abstract class AbstractIndexBasedBigListIterator
/*     */     extends AbstractIndexBasedBigIterator
/*     */     implements CharBigListIterator
/*     */   {
/*     */     protected AbstractIndexBasedBigListIterator(long minPos, long initialPos) {
/* 502 */       super(minPos, initialPos);
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
/*     */     protected abstract void add(long param1Long, char param1Char);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract void set(long param1Long, char param1Char);
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
/* 535 */       return (this.pos > this.minPos);
/*     */     }
/*     */ 
/*     */     
/*     */     public char previousChar() {
/* 540 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 541 */       return get(this.lastReturned = --this.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public long nextIndex() {
/* 546 */       return this.pos;
/*     */     }
/*     */ 
/*     */     
/*     */     public long previousIndex() {
/* 551 */       return this.pos - 1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(char k) {
/* 556 */       add(this.pos++, k);
/* 557 */       this.lastReturned = -1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(char k) {
/* 562 */       if (this.lastReturned == -1L) throw new IllegalStateException(); 
/* 563 */       set(this.lastReturned, k);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long back(long n) {
/* 570 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 571 */       long remaining = this.pos - this.minPos;
/* 572 */       if (n < remaining) {
/* 573 */         this.pos -= n;
/*     */       } else {
/* 575 */         n = remaining;
/* 576 */         this.pos = this.minPos;
/*     */       } 
/* 578 */       this.lastReturned = this.pos;
/* 579 */       return n;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharBigListIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */