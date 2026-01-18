/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharSpliterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortSpliterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LongSpliterators
/*      */ {
/*      */   static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
/*      */   public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
/*      */   public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
/*      */   public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
/*      */   private static final int SORTED_CHARACTERISTICS = 20;
/*      */   public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
/*      */   
/*      */   public static class EmptySpliterator
/*      */     implements LongSpliterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = 8379247926738230492L;
/*      */     private static final int CHARACTERISTICS = 16448;
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/*   59 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Long> action) {
/*   65 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*   70 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*   75 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*   80 */       return 16448;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*   94 */       return LongSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*   98 */       return LongSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();
/*      */   
/*      */   private static class SingletonSpliterator
/*      */     implements LongSpliterator {
/*      */     private final long element;
/*      */     private final LongComparator comparator;
/*      */     private boolean consumed = false;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public SingletonSpliterator(long element) {
/*  120 */       this(element, null);
/*      */     }
/*      */     
/*      */     public SingletonSpliterator(long element, LongComparator comparator) {
/*  124 */       this.element = element;
/*  125 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/*  130 */       Objects.requireNonNull(action);
/*  131 */       if (this.consumed) return false;
/*      */       
/*  133 */       this.consumed = true;
/*  134 */       action.accept(this.element);
/*  135 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*  140 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  145 */       return this.consumed ? 0L : 1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  150 */       return 17749;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  155 */       Objects.requireNonNull(action);
/*  156 */       if (!this.consumed) {
/*  157 */         this.consumed = true;
/*  158 */         action.accept(this.element);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator getComparator() {
/*  164 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  169 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  170 */       if (n == 0L || this.consumed) return 0L; 
/*  171 */       this.consumed = true;
/*  172 */       return 1L;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator singleton(long element) {
/*  183 */     return new SingletonSpliterator(element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator singleton(long element, LongComparator comparator) {
/*  198 */     return new SingletonSpliterator(element, comparator);
/*      */   }
/*      */   
/*      */   private static class ArraySpliterator implements LongSpliterator {
/*      */     private static final int BASE_CHARACTERISTICS = 16720;
/*      */     final long[] array;
/*      */     private final int offset;
/*      */     private int length;
/*      */     private int curr;
/*      */     final int characteristics;
/*      */     
/*      */     public ArraySpliterator(long[] array, int offset, int length, int additionalCharacteristics) {
/*  210 */       this.array = array;
/*  211 */       this.offset = offset;
/*  212 */       this.length = length;
/*  213 */       this.characteristics = 0x4150 | additionalCharacteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/*  218 */       if (this.curr >= this.length) return false; 
/*  219 */       Objects.requireNonNull(action);
/*  220 */       action.accept(this.array[this.offset + this.curr++]);
/*  221 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  226 */       return (this.length - this.curr);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  231 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected ArraySpliterator makeForSplit(int newOffset, int newLength) {
/*  235 */       return new ArraySpliterator(this.array, newOffset, newLength, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*  240 */       int retLength = this.length - this.curr >> 1;
/*  241 */       if (retLength <= 1) return null; 
/*  242 */       int myNewCurr = this.curr + retLength;
/*  243 */       int retOffset = this.offset + this.curr;
/*      */       
/*  245 */       this.curr = myNewCurr;
/*      */       
/*  247 */       return makeForSplit(retOffset, retLength);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  252 */       Objects.requireNonNull(action);
/*  253 */       for (; this.curr < this.length; this.curr++) {
/*  254 */         action.accept(this.array[this.offset + this.curr]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  260 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  261 */       if (this.curr >= this.length) return 0L; 
/*  262 */       int remaining = this.length - this.curr;
/*  263 */       if (n < remaining) {
/*  264 */         this.curr = SafeMath.safeLongToInt(this.curr + n);
/*  265 */         return n;
/*      */       } 
/*  267 */       n = remaining;
/*  268 */       this.curr = this.length;
/*  269 */       return n;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ArraySpliteratorWithComparator extends ArraySpliterator {
/*      */     private final LongComparator comparator;
/*      */     
/*      */     public ArraySpliteratorWithComparator(long[] array, int offset, int length, int additionalCharacteristics, LongComparator comparator) {
/*  277 */       super(array, offset, length, additionalCharacteristics | 0x14);
/*  278 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected ArraySpliteratorWithComparator makeForSplit(int newOffset, int newLength) {
/*  283 */       return new ArraySpliteratorWithComparator(this.array, newOffset, newLength, this.characteristics, this.comparator);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator getComparator() {
/*  288 */       return this.comparator;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrap(long[] array, int offset, int length) {
/*  312 */     LongArrays.ensureOffsetLength(array, offset, length);
/*  313 */     return new ArraySpliterator(array, offset, length, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrap(long[] array) {
/*  332 */     return new ArraySpliterator(array, 0, array.length, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrap(long[] array, int offset, int length, int additionalCharacteristics) {
/*  358 */     LongArrays.ensureOffsetLength(array, offset, length);
/*  359 */     return new ArraySpliterator(array, offset, length, additionalCharacteristics);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrapPreSorted(long[] array, int offset, int length, int additionalCharacteristics, LongComparator comparator) {
/*  390 */     LongArrays.ensureOffsetLength(array, offset, length);
/*  391 */     return new ArraySpliteratorWithComparator(array, offset, length, additionalCharacteristics, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrapPreSorted(long[] array, int offset, int length, LongComparator comparator) {
/*  419 */     return wrapPreSorted(array, offset, length, 0, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrapPreSorted(long[] array, LongComparator comparator) {
/*  444 */     return wrapPreSorted(array, 0, array.length, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SpliteratorWrapper
/*      */     implements LongSpliterator
/*      */   {
/*      */     final Spliterator<Long> i;
/*      */ 
/*      */ 
/*      */     
/*      */     public SpliteratorWrapper(Spliterator<Long> i) {
/*  457 */       this.i = i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/*  464 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/*  470 */       Objects.requireNonNull(action);
/*  471 */       Objects.requireNonNull(action); return this.i.tryAdvance((action instanceof Consumer) ? (Consumer<? super Long>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Long> action) {
/*  477 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  484 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  490 */       Objects.requireNonNull(action);
/*  491 */       Objects.requireNonNull(action); this.i.forEachRemaining((action instanceof Consumer) ? (Consumer<? super Long>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {
/*  497 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  502 */       return this.i.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  507 */       return this.i.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator getComparator() {
/*  512 */       return LongComparators.asLongComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*  517 */       Spliterator<Long> innerSplit = this.i.trySplit();
/*  518 */       if (innerSplit == null) return null; 
/*  519 */       return new SpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper {
/*      */     final LongComparator comparator;
/*      */     
/*      */     public SpliteratorWrapperWithComparator(Spliterator<Long> i, LongComparator comparator) {
/*  527 */       super(i);
/*  528 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator getComparator() {
/*  533 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*  538 */       Spliterator<Long> innerSplit = this.i.trySplit();
/*  539 */       if (innerSplit == null) return null; 
/*  540 */       return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapper implements LongSpliterator {
/*      */     final Spliterator.OfLong i;
/*      */     
/*      */     public PrimitiveSpliteratorWrapper(Spliterator.OfLong i) {
/*  548 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/*  553 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  558 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  563 */       return this.i.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  568 */       return this.i.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator getComparator() {
/*  573 */       return LongComparators.asLongComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*  578 */       Spliterator.OfLong innerSplit = this.i.trySplit();
/*  579 */       if (innerSplit == null) return null; 
/*  580 */       return new PrimitiveSpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapperWithComparator extends PrimitiveSpliteratorWrapper {
/*      */     final LongComparator comparator;
/*      */     
/*      */     public PrimitiveSpliteratorWrapperWithComparator(Spliterator.OfLong i, LongComparator comparator) {
/*  588 */       super(i);
/*  589 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator getComparator() {
/*  594 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*  599 */       Spliterator.OfLong innerSplit = this.i.trySplit();
/*  600 */       if (innerSplit == null) return null; 
/*  601 */       return new PrimitiveSpliteratorWrapperWithComparator(innerSplit, this.comparator);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator asLongSpliterator(Spliterator<Long> i) {
/*  622 */     if (i instanceof LongSpliterator) return (LongSpliterator)i; 
/*  623 */     if (i instanceof Spliterator.OfLong) return new PrimitiveSpliteratorWrapper((Spliterator.OfLong)i); 
/*  624 */     return new SpliteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator asLongSpliterator(Spliterator<Long> i, LongComparator comparatorOverride) {
/*  655 */     if (i instanceof LongSpliterator) throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + LongSpliterator.class.getSimpleName()); 
/*  656 */     if (i instanceof Spliterator.OfLong) return new PrimitiveSpliteratorWrapperWithComparator((Spliterator.OfLong)i, comparatorOverride); 
/*  657 */     return new SpliteratorWrapperWithComparator(i, comparatorOverride);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void onEachMatching(LongSpliterator spliterator, LongPredicate predicate, LongConsumer action) {
/*  670 */     Objects.requireNonNull(predicate);
/*  671 */     Objects.requireNonNull(action);
/*  672 */     spliterator.forEachRemaining(value -> {
/*      */           if (predicate.test(value)) {
/*      */             action.accept(value);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class AbstractIndexBasedSpliterator
/*      */     extends AbstractLongSpliterator
/*      */   {
/*      */     protected int pos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AbstractIndexBasedSpliterator(int initialPos) {
/*  710 */       this.pos = initialPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int computeSplitPoint() {
/*  794 */       return this.pos + (getMaxPos() - this.pos) / 2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void splitPointCheck(int splitPoint, int observedMax) {
/*  800 */       if (splitPoint < this.pos || splitPoint > observedMax) {
/*  801 */         throw new IndexOutOfBoundsException("splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  808 */       return 16720;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  813 */       return getMaxPos() - this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/*  818 */       if (this.pos >= getMaxPos()) return false; 
/*  819 */       action.accept(get(this.pos++));
/*  820 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  825 */       for (int max = getMaxPos(); this.pos < max; this.pos++) {
/*  826 */         action.accept(get(this.pos));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  834 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  835 */       int max = getMaxPos();
/*  836 */       if (this.pos >= max) return 0L; 
/*  837 */       int remaining = max - this.pos;
/*  838 */       if (n < remaining) {
/*  839 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/*  840 */         return n;
/*      */       } 
/*  842 */       n = remaining;
/*  843 */       this.pos = max;
/*  844 */       return n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*  866 */       int max = getMaxPos();
/*  867 */       int splitPoint = computeSplitPoint();
/*  868 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/*  869 */       splitPointCheck(splitPoint, max);
/*  870 */       int oldPos = this.pos;
/*  871 */       LongSpliterator maybeSplit = makeForSplit(oldPos, splitPoint);
/*  872 */       if (maybeSplit != null) this.pos = splitPoint; 
/*  873 */       return maybeSplit;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract long get(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract LongSpliterator makeForSplit(int param1Int1, int param1Int2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class EarlyBindingSizeIndexBasedSpliterator
/*      */     extends AbstractIndexBasedSpliterator
/*      */   {
/*      */     protected final int maxPos;
/*      */ 
/*      */ 
/*      */     
/*      */     protected EarlyBindingSizeIndexBasedSpliterator(int initialPos, int maxPos) {
/*  899 */       super(initialPos);
/*  900 */       this.maxPos = maxPos;
/*      */     }
/*      */ 
/*      */     
/*      */     protected final int getMaxPos() {
/*  905 */       return this.maxPos;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class LateBindingSizeIndexBasedSpliterator
/*      */     extends AbstractIndexBasedSpliterator
/*      */   {
/*  930 */     protected int maxPos = -1;
/*      */     private boolean maxPosFixed;
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos) {
/*  934 */       super(initialPos);
/*  935 */       this.maxPosFixed = false;
/*      */     }
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos, int fixedMaxPos) {
/*  939 */       super(initialPos);
/*  940 */       this.maxPos = fixedMaxPos;
/*  941 */       this.maxPosFixed = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final int getMaxPos() {
/*  956 */       return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*  961 */       LongSpliterator maybeSplit = super.trySplit();
/*  962 */       if (!this.maxPosFixed && maybeSplit != null) {
/*  963 */         this.maxPos = getMaxPosFromBackingStore();
/*  964 */         this.maxPosFixed = true;
/*      */       } 
/*  966 */       return maybeSplit;
/*      */     }
/*      */     
/*      */     protected abstract int getMaxPosFromBackingStore();
/*      */   }
/*      */   
/*      */   private static class IntervalSpliterator implements LongSpliterator {
/*      */     private static final int DONT_SPLIT_THRESHOLD = 2;
/*      */     private static final long MAX_SPLIT_SIZE = 1073741824L;
/*      */     
/*      */     public IntervalSpliterator(long from, long to) {
/*  977 */       this.curr = from;
/*  978 */       this.to = to;
/*      */     }
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/*  983 */       if (this.curr >= this.to) return false; 
/*  984 */       action.accept(this.curr++);
/*  985 */       return true;
/*      */     }
/*      */     private long curr; private long to;
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  990 */       Objects.requireNonNull(action);
/*  991 */       for (; this.curr < this.to; this.curr++) {
/*  992 */         action.accept(this.curr);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  998 */       return this.to - this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1003 */       return 17749;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public LongComparator getComparator() {
/* 1009 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/* 1014 */       long remaining = this.to - this.curr;
/* 1015 */       long mid = this.curr + (remaining >> 1L);
/*      */       
/* 1017 */       if (remaining > 2147483648L || remaining < 0L) {
/* 1018 */         mid = this.curr + 1073741824L;
/*      */       }
/* 1020 */       if (remaining >= 0L && remaining <= 2L) return null; 
/* 1021 */       long old_curr = this.curr;
/* 1022 */       this.curr = mid;
/* 1023 */       return new IntervalSpliterator(old_curr, mid);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1028 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1029 */       if (this.curr >= this.to) return 0L;
/*      */ 
/*      */       
/* 1032 */       long newCurr = this.curr + n;
/*      */       
/* 1034 */       if (newCurr <= this.to && newCurr >= this.curr) {
/* 1035 */         this.curr = newCurr;
/* 1036 */         return n;
/*      */       } 
/* 1038 */       n = this.to - this.curr;
/* 1039 */       this.curr = this.to;
/* 1040 */       return n;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator fromTo(long from, long to) {
/* 1056 */     return new IntervalSpliterator(from, to);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorConcatenator
/*      */     implements LongSpliterator
/*      */   {
/*      */     private static final int EMPTY_CHARACTERISTICS = 16448;
/*      */     
/*      */     private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
/*      */     final LongSpliterator[] a;
/*      */     int offset;
/*      */     int length;
/* 1069 */     long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
/* 1070 */     int characteristics = 0;
/*      */     
/*      */     public SpliteratorConcatenator(LongSpliterator[] a, int offset, int length) {
/* 1073 */       this.a = a;
/* 1074 */       this.offset = offset;
/* 1075 */       this.length = length;
/* 1076 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1077 */       this.characteristics = computeCharacteristics();
/*      */     }
/*      */     
/*      */     private long recomputeRemaining() {
/* 1081 */       int curLength = this.length - 1;
/* 1082 */       int curOffset = this.offset + 1;
/* 1083 */       long result = 0L;
/* 1084 */       while (curLength > 0) {
/* 1085 */         long cur = this.a[curOffset++].estimateSize();
/* 1086 */         curLength--;
/* 1087 */         if (cur == Long.MAX_VALUE) return Long.MAX_VALUE; 
/* 1088 */         result += cur;
/*      */         
/* 1090 */         if (result == Long.MAX_VALUE || result < 0L) {
/* 1091 */           return Long.MAX_VALUE;
/*      */         }
/*      */       } 
/* 1094 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     private int computeCharacteristics() {
/* 1099 */       if (this.length <= 0) {
/* 1100 */         return 16448;
/*      */       }
/* 1102 */       int current = -1;
/* 1103 */       int curLength = this.length;
/* 1104 */       int curOffset = this.offset;
/* 1105 */       if (curLength > 1) {
/* 1106 */         current &= 0xFFFFFFFA;
/*      */       }
/* 1108 */       while (curLength > 0) {
/* 1109 */         current &= this.a[curOffset++].characteristics();
/* 1110 */         curLength--;
/*      */       } 
/* 1112 */       return current;
/*      */     }
/*      */     
/*      */     private void advanceNextSpliterator() {
/* 1116 */       if (this.length <= 0) {
/* 1117 */         throw new AssertionError("advanceNextSpliterator() called with none remaining");
/*      */       }
/* 1119 */       this.offset++;
/* 1120 */       this.length--;
/* 1121 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/* 1129 */       boolean any = false;
/* 1130 */       while (this.length > 0) {
/* 1131 */         if (this.a[this.offset].tryAdvance(action)) {
/* 1132 */           any = true;
/*      */           break;
/*      */         } 
/* 1135 */         advanceNextSpliterator();
/*      */       } 
/* 1137 */       return any;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1142 */       while (this.length > 0) {
/* 1143 */         this.a[this.offset].forEachRemaining(action);
/* 1144 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {
/* 1151 */       while (this.length > 0) {
/* 1152 */         this.a[this.offset].forEachRemaining(action);
/* 1153 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1159 */       if (this.length <= 0) return 0L; 
/* 1160 */       long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
/* 1161 */       if (est < 0L)
/*      */       {
/* 1163 */         return Long.MAX_VALUE;
/*      */       }
/* 1165 */       return est;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1170 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator getComparator() {
/* 1175 */       if (this.length == 1 && (this.characteristics & 0x4) != 0) {
/* 1176 */         return this.a[this.offset].getComparator();
/*      */       }
/* 1178 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*      */       LongSpliterator split;
/* 1187 */       switch (this.length) {
/*      */         case 0:
/* 1189 */           return null;
/*      */         
/*      */         case 1:
/* 1192 */           split = this.a[this.offset].trySplit();
/*      */ 
/*      */           
/* 1195 */           this.characteristics = this.a[this.offset].characteristics();
/* 1196 */           return split;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1201 */           split = this.a[this.offset++];
/* 1202 */           this.length--;
/*      */ 
/*      */           
/* 1205 */           this.characteristics = this.a[this.offset].characteristics();
/* 1206 */           this.remainingEstimatedExceptCurrent = 0L;
/* 1207 */           return split;
/*      */       } 
/*      */ 
/*      */       
/* 1211 */       int mid = this.length >> 1;
/* 1212 */       int ret_offset = this.offset;
/* 1213 */       int new_offset = this.offset + mid;
/* 1214 */       int ret_length = mid;
/* 1215 */       int new_length = this.length - mid;
/* 1216 */       this.offset = new_offset;
/* 1217 */       this.length = new_length;
/* 1218 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1219 */       this.characteristics = computeCharacteristics();
/* 1220 */       return new SpliteratorConcatenator(this.a, ret_offset, ret_length);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1225 */       long skipped = 0L;
/* 1226 */       if (this.length <= 0) return 0L; 
/* 1227 */       while (skipped < n && this.length >= 0) {
/* 1228 */         long curSkipped = this.a[this.offset].skip(n - skipped);
/* 1229 */         skipped += curSkipped;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1236 */         if (skipped < n) advanceNextSpliterator(); 
/*      */       } 
/* 1238 */       return skipped;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator concat(LongSpliterator... a) {
/* 1259 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator concat(LongSpliterator[] a, int offset, int length) {
/* 1283 */     return new SpliteratorConcatenator(a, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorFromIterator
/*      */     implements LongSpliterator
/*      */   {
/*      */     private static final int BATCH_INCREMENT_SIZE = 1024;
/*      */     
/*      */     private static final int BATCH_MAX_SIZE = 33554432;
/*      */     
/*      */     private final LongIterator iter;
/*      */     
/*      */     final int characteristics;
/*      */     private final boolean knownSize;
/* 1298 */     private long size = Long.MAX_VALUE;
/* 1299 */     private int nextBatchSize = 1024;
/*      */     
/* 1301 */     private LongSpliterator delegate = null;
/*      */     
/*      */     SpliteratorFromIterator(LongIterator iter, int characteristics) {
/* 1304 */       this.iter = iter;
/* 1305 */       this.characteristics = 0x100 | characteristics;
/* 1306 */       this.knownSize = false;
/*      */     }
/*      */     
/*      */     SpliteratorFromIterator(LongIterator iter, long size, int additionalCharacteristics) {
/* 1310 */       this.iter = iter;
/* 1311 */       this.knownSize = true;
/* 1312 */       this.size = size;
/* 1313 */       if ((additionalCharacteristics & 0x1000) != 0) {
/* 1314 */         this.characteristics = 0x100 | additionalCharacteristics;
/*      */       } else {
/* 1316 */         this.characteristics = 0x4140 | additionalCharacteristics;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/* 1322 */       if (this.delegate != null) {
/* 1323 */         boolean hadRemaining = this.delegate.tryAdvance(action);
/* 1324 */         if (!hadRemaining) this.delegate = null; 
/* 1325 */         return hadRemaining;
/*      */       } 
/* 1327 */       if (!this.iter.hasNext()) return false; 
/* 1328 */       this.size--;
/* 1329 */       action.accept(this.iter.nextLong());
/* 1330 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1335 */       if (this.delegate != null) {
/* 1336 */         this.delegate.forEachRemaining(action);
/* 1337 */         this.delegate = null;
/*      */       } 
/* 1339 */       this.iter.forEachRemaining(action);
/* 1340 */       this.size = 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1345 */       if (this.delegate != null) return this.delegate.estimateSize(); 
/* 1346 */       if (!this.iter.hasNext()) return 0L;
/*      */ 
/*      */       
/* 1349 */       return (this.knownSize && this.size >= 0L) ? this.size : Long.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1354 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected LongSpliterator makeForSplit(long[] batch, int len) {
/* 1358 */       return LongSpliterators.wrap(batch, 0, len, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/* 1363 */       if (!this.iter.hasNext()) return null; 
/* 1364 */       int batchSizeEst = (this.knownSize && this.size > 0L) ? (int)Math.min(this.nextBatchSize, this.size) : this.nextBatchSize;
/*      */       
/* 1366 */       long[] batch = new long[batchSizeEst];
/* 1367 */       int actualSeen = 0;
/* 1368 */       while (actualSeen < batchSizeEst && this.iter.hasNext()) {
/* 1369 */         batch[actualSeen++] = this.iter.nextLong();
/* 1370 */         this.size--;
/*      */       } 
/*      */ 
/*      */       
/* 1374 */       if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
/* 1375 */         batch = Arrays.copyOf(batch, this.nextBatchSize);
/* 1376 */         while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
/* 1377 */           batch[actualSeen++] = this.iter.nextLong();
/* 1378 */           this.size--;
/*      */         } 
/*      */       } 
/* 1381 */       this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
/*      */       
/* 1383 */       LongSpliterator split = makeForSplit(batch, actualSeen);
/* 1384 */       if (!this.iter.hasNext()) {
/* 1385 */         this.delegate = split;
/* 1386 */         return split.trySplit();
/*      */       } 
/* 1388 */       return split;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1394 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1395 */       if (this.iter instanceof LongBigListIterator) {
/* 1396 */         long skipped = ((LongBigListIterator)this.iter).skip(n);
/* 1397 */         this.size -= skipped;
/* 1398 */         return skipped;
/*      */       } 
/* 1400 */       long skippedSoFar = 0L;
/* 1401 */       while (skippedSoFar < n && this.iter.hasNext()) {
/* 1402 */         int skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
/* 1403 */         this.size -= skipped;
/* 1404 */         skippedSoFar += skipped;
/*      */       } 
/* 1406 */       return skippedSoFar;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorFromIteratorWithComparator
/*      */     extends SpliteratorFromIterator {
/*      */     private final LongComparator comparator;
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(LongIterator iter, int additionalCharacteristics, LongComparator comparator) {
/* 1415 */       super(iter, additionalCharacteristics | 0x14);
/* 1416 */       this.comparator = comparator;
/*      */     }
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(LongIterator iter, long size, int additionalCharacteristics, LongComparator comparator) {
/* 1420 */       super(iter, size, additionalCharacteristics | 0x14);
/* 1421 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator getComparator() {
/* 1426 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected LongSpliterator makeForSplit(long[] array, int len) {
/* 1431 */       return LongSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator asSpliterator(LongIterator iter, long size, int additionalCharacterisitcs) {
/* 1459 */     return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator asSpliteratorFromSorted(LongIterator iter, long size, int additionalCharacterisitcs, LongComparator comparator) {
/* 1491 */     return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator asSpliteratorUnknownSize(LongIterator iter, int characterisitcs) {
/* 1514 */     return new SpliteratorFromIterator(iter, characterisitcs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator asSpliteratorFromSortedUnknownSize(LongIterator iter, int additionalCharacterisitcs, LongComparator comparator) {
/* 1543 */     return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
/*      */   }
/*      */   
/*      */   private static final class IteratorFromSpliterator implements LongIterator, LongConsumer {
/*      */     private final LongSpliterator spliterator;
/* 1548 */     private long holder = 0L;
/*      */     
/*      */     private boolean hasPeeked = false;
/*      */     
/*      */     IteratorFromSpliterator(LongSpliterator spliterator) {
/* 1553 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(long item) {
/* 1558 */       this.holder = item;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1563 */       if (this.hasPeeked) return true; 
/* 1564 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1565 */       if (!hadElement) return false; 
/* 1566 */       this.hasPeeked = true;
/* 1567 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1572 */       if (this.hasPeeked) {
/* 1573 */         this.hasPeeked = false;
/* 1574 */         return this.holder;
/*      */       } 
/* 1576 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1577 */       if (!hadElement) throw new NoSuchElementException(); 
/* 1578 */       return this.holder;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1583 */       if (this.hasPeeked) {
/* 1584 */         this.hasPeeked = false;
/* 1585 */         action.accept(this.holder);
/*      */       } 
/* 1587 */       this.spliterator.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1592 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1593 */       int skipped = 0;
/* 1594 */       if (this.hasPeeked) {
/* 1595 */         this.hasPeeked = false;
/* 1596 */         this.spliterator.skip(1L);
/* 1597 */         skipped++;
/* 1598 */         n--;
/*      */       } 
/* 1600 */       if (n > 0) {
/* 1601 */         skipped += SafeMath.safeLongToInt(this.spliterator.skip(n));
/*      */       }
/* 1603 */       return skipped;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator asIterator(LongSpliterator spliterator) {
/* 1616 */     return new IteratorFromSpliterator(spliterator);
/*      */   }
/*      */   
/*      */   private static final class ByteSpliteratorWrapper
/*      */     implements LongSpliterator {
/*      */     final ByteSpliterator spliterator;
/*      */     
/*      */     public ByteSpliteratorWrapper(ByteSpliterator spliterator) {
/* 1624 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/* 1629 */       Objects.requireNonNull(action);
/* 1630 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1635 */       Objects.requireNonNull(action);
/* 1636 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1641 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1646 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1651 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/* 1656 */       ByteSpliterator possibleSplit = this.spliterator.trySplit();
/* 1657 */       if (possibleSplit == null) return null; 
/* 1658 */       return new ByteSpliteratorWrapper(possibleSplit);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrap(ByteSpliterator spliterator) {
/* 1675 */     return new ByteSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class ShortSpliteratorWrapper
/*      */     implements LongSpliterator {
/*      */     final ShortSpliterator spliterator;
/*      */     
/*      */     public ShortSpliteratorWrapper(ShortSpliterator spliterator) {
/* 1683 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/* 1688 */       Objects.requireNonNull(action);
/* 1689 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1694 */       Objects.requireNonNull(action);
/* 1695 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1700 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1705 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1710 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/* 1715 */       ShortSpliterator possibleSplit = this.spliterator.trySplit();
/* 1716 */       if (possibleSplit == null) return null; 
/* 1717 */       return new ShortSpliteratorWrapper(possibleSplit);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrap(ShortSpliterator spliterator) {
/* 1734 */     return new ShortSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class CharSpliteratorWrapper
/*      */     implements LongSpliterator {
/*      */     final CharSpliterator spliterator;
/*      */     
/*      */     public CharSpliteratorWrapper(CharSpliterator spliterator) {
/* 1742 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/* 1747 */       Objects.requireNonNull(action);
/* 1748 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1753 */       Objects.requireNonNull(action);
/* 1754 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1759 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1764 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1769 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/* 1774 */       CharSpliterator possibleSplit = this.spliterator.trySplit();
/* 1775 */       if (possibleSplit == null) return null; 
/* 1776 */       return new CharSpliteratorWrapper(possibleSplit);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrap(CharSpliterator spliterator) {
/* 1799 */     return new CharSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class IntSpliteratorWrapper
/*      */     implements LongSpliterator {
/*      */     final IntSpliterator spliterator;
/*      */     
/*      */     public IntSpliteratorWrapper(IntSpliterator spliterator) {
/* 1807 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/* 1812 */       Objects.requireNonNull(action);
/* 1813 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1818 */       Objects.requireNonNull(action);
/* 1819 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1824 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1829 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1834 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/* 1839 */       IntSpliterator possibleSplit = this.spliterator.trySplit();
/* 1840 */       if (possibleSplit == null) return null; 
/* 1841 */       return new IntSpliteratorWrapper(possibleSplit);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongSpliterator wrap(IntSpliterator spliterator) {
/* 1858 */     return new IntSpliteratorWrapper(spliterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */