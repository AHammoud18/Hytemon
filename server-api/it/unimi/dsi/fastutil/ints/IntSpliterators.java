/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharSpliterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortSpliterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class IntSpliterators
/*      */ {
/*      */   static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
/*      */   public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
/*      */   public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
/*      */   public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
/*      */   private static final int SORTED_CHARACTERISTICS = 20;
/*      */   public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
/*      */   
/*      */   public static class EmptySpliterator
/*      */     implements IntSpliterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = 8379247926738230492L;
/*      */     private static final int CHARACTERISTICS = 16448;
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/*   59 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Integer> action) {
/*   65 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
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
/*      */     public void forEachRemaining(IntConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*   94 */       return IntSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*   98 */       return IntSpliterators.EMPTY_SPLITERATOR;
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
/*      */     implements IntSpliterator {
/*      */     private final int element;
/*      */     private final IntComparator comparator;
/*      */     private boolean consumed = false;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public SingletonSpliterator(int element) {
/*  120 */       this(element, null);
/*      */     }
/*      */     
/*      */     public SingletonSpliterator(int element, IntComparator comparator) {
/*  124 */       this.element = element;
/*  125 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/*  130 */       Objects.requireNonNull(action);
/*  131 */       if (this.consumed) return false;
/*      */       
/*  133 */       this.consumed = true;
/*  134 */       action.accept(this.element);
/*  135 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
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
/*      */     public void forEachRemaining(IntConsumer action) {
/*  155 */       Objects.requireNonNull(action);
/*  156 */       if (!this.consumed) {
/*  157 */         this.consumed = true;
/*  158 */         action.accept(this.element);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public IntComparator getComparator() {
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
/*      */   public static IntSpliterator singleton(int element) {
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
/*      */   public static IntSpliterator singleton(int element, IntComparator comparator) {
/*  198 */     return new SingletonSpliterator(element, comparator);
/*      */   }
/*      */   
/*      */   private static class ArraySpliterator implements IntSpliterator {
/*      */     private static final int BASE_CHARACTERISTICS = 16720;
/*      */     final int[] array;
/*      */     private final int offset;
/*      */     private int length;
/*      */     private int curr;
/*      */     final int characteristics;
/*      */     
/*      */     public ArraySpliterator(int[] array, int offset, int length, int additionalCharacteristics) {
/*  210 */       this.array = array;
/*  211 */       this.offset = offset;
/*  212 */       this.length = length;
/*  213 */       this.characteristics = 0x4150 | additionalCharacteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
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
/*      */     public IntSpliterator trySplit() {
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
/*      */     public void forEachRemaining(IntConsumer action) {
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
/*      */     private final IntComparator comparator;
/*      */     
/*      */     public ArraySpliteratorWithComparator(int[] array, int offset, int length, int additionalCharacteristics, IntComparator comparator) {
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
/*      */     public IntComparator getComparator() {
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
/*      */   public static IntSpliterator wrap(int[] array, int offset, int length) {
/*  312 */     IntArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static IntSpliterator wrap(int[] array) {
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
/*      */   public static IntSpliterator wrap(int[] array, int offset, int length, int additionalCharacteristics) {
/*  358 */     IntArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static IntSpliterator wrapPreSorted(int[] array, int offset, int length, int additionalCharacteristics, IntComparator comparator) {
/*  390 */     IntArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static IntSpliterator wrapPreSorted(int[] array, int offset, int length, IntComparator comparator) {
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
/*      */   public static IntSpliterator wrapPreSorted(int[] array, IntComparator comparator) {
/*  444 */     return wrapPreSorted(array, 0, array.length, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SpliteratorWrapper
/*      */     implements IntSpliterator
/*      */   {
/*      */     final Spliterator<Integer> i;
/*      */ 
/*      */ 
/*      */     
/*      */     public SpliteratorWrapper(Spliterator<Integer> i) {
/*  457 */       this.i = i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/*  464 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/*  470 */       Objects.requireNonNull(action);
/*  471 */       Objects.requireNonNull(action); return this.i.tryAdvance((action instanceof Consumer) ? (Consumer<? super Integer>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Integer> action) {
/*  477 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  484 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  490 */       Objects.requireNonNull(action);
/*  491 */       Objects.requireNonNull(action); this.i.forEachRemaining((action instanceof Consumer) ? (Consumer<? super Integer>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {
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
/*      */     public IntComparator getComparator() {
/*  512 */       return IntComparators.asIntComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/*  517 */       Spliterator<Integer> innerSplit = this.i.trySplit();
/*  518 */       if (innerSplit == null) return null; 
/*  519 */       return new SpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper {
/*      */     final IntComparator comparator;
/*      */     
/*      */     public SpliteratorWrapperWithComparator(Spliterator<Integer> i, IntComparator comparator) {
/*  527 */       super(i);
/*  528 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntComparator getComparator() {
/*  533 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/*  538 */       Spliterator<Integer> innerSplit = this.i.trySplit();
/*  539 */       if (innerSplit == null) return null; 
/*  540 */       return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapper implements IntSpliterator {
/*      */     final Spliterator.OfInt i;
/*      */     
/*      */     public PrimitiveSpliteratorWrapper(Spliterator.OfInt i) {
/*  548 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/*  553 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
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
/*      */     public IntComparator getComparator() {
/*  573 */       return IntComparators.asIntComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/*  578 */       Spliterator.OfInt innerSplit = this.i.trySplit();
/*  579 */       if (innerSplit == null) return null; 
/*  580 */       return new PrimitiveSpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapperWithComparator extends PrimitiveSpliteratorWrapper {
/*      */     final IntComparator comparator;
/*      */     
/*      */     public PrimitiveSpliteratorWrapperWithComparator(Spliterator.OfInt i, IntComparator comparator) {
/*  588 */       super(i);
/*  589 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntComparator getComparator() {
/*  594 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/*  599 */       Spliterator.OfInt innerSplit = this.i.trySplit();
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
/*      */   public static IntSpliterator asIntSpliterator(Spliterator<Integer> i) {
/*  622 */     if (i instanceof IntSpliterator) return (IntSpliterator)i; 
/*  623 */     if (i instanceof Spliterator.OfInt) return new PrimitiveSpliteratorWrapper((Spliterator.OfInt)i); 
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
/*      */   public static IntSpliterator asIntSpliterator(Spliterator<Integer> i, IntComparator comparatorOverride) {
/*  655 */     if (i instanceof IntSpliterator) throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + IntSpliterator.class.getSimpleName()); 
/*  656 */     if (i instanceof Spliterator.OfInt) return new PrimitiveSpliteratorWrapperWithComparator((Spliterator.OfInt)i, comparatorOverride); 
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
/*      */   public static void onEachMatching(IntSpliterator spliterator, IntPredicate predicate, IntConsumer action) {
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
/*      */     extends AbstractIntSpliterator
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
/*      */     public boolean tryAdvance(IntConsumer action) {
/*  818 */       if (this.pos >= getMaxPos()) return false; 
/*  819 */       action.accept(get(this.pos++));
/*  820 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
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
/*      */     public IntSpliterator trySplit() {
/*  866 */       int max = getMaxPos();
/*  867 */       int splitPoint = computeSplitPoint();
/*  868 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/*  869 */       splitPointCheck(splitPoint, max);
/*  870 */       int oldPos = this.pos;
/*  871 */       IntSpliterator maybeSplit = makeForSplit(oldPos, splitPoint);
/*  872 */       if (maybeSplit != null) this.pos = splitPoint; 
/*  873 */       return maybeSplit;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int get(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract IntSpliterator makeForSplit(int param1Int1, int param1Int2);
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
/*      */     public IntSpliterator trySplit() {
/*  961 */       IntSpliterator maybeSplit = super.trySplit();
/*  962 */       if (!this.maxPosFixed && maybeSplit != null) {
/*  963 */         this.maxPos = getMaxPosFromBackingStore();
/*  964 */         this.maxPosFixed = true;
/*      */       } 
/*  966 */       return maybeSplit;
/*      */     }
/*      */     
/*      */     protected abstract int getMaxPosFromBackingStore(); }
/*      */   
/*      */   private static class IntervalSpliterator implements IntSpliterator {
/*      */     private static final int DONT_SPLIT_THRESHOLD = 2;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public IntervalSpliterator(int from, int to) {
/*  976 */       this.curr = from;
/*  977 */       this.to = to;
/*      */     }
/*      */     private int curr;
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/*  982 */       if (this.curr >= this.to) return false; 
/*  983 */       action.accept(this.curr++);
/*  984 */       return true;
/*      */     }
/*      */     private int to;
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  989 */       Objects.requireNonNull(action);
/*  990 */       for (; this.curr < this.to; this.curr++) {
/*  991 */         action.accept(this.curr);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  997 */       return this.to - this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1002 */       return 17749;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public IntComparator getComparator() {
/* 1008 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/* 1013 */       long remaining = (this.to - this.curr);
/* 1014 */       int mid = (int)(this.curr + (remaining >> 1L));
/* 1015 */       if (remaining >= 0L && remaining <= 2L) return null; 
/* 1016 */       int old_curr = this.curr;
/* 1017 */       this.curr = mid;
/* 1018 */       return new IntervalSpliterator(old_curr, mid);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1023 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1024 */       if (this.curr >= this.to) return 0L;
/*      */ 
/*      */       
/* 1027 */       long newCurr = this.curr + n;
/*      */       
/* 1029 */       if (newCurr <= this.to && newCurr >= this.curr) {
/* 1030 */         this.curr = SafeMath.safeLongToInt(newCurr);
/* 1031 */         return n;
/*      */       } 
/* 1033 */       n = (this.to - this.curr);
/* 1034 */       this.curr = this.to;
/* 1035 */       return n;
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
/*      */   public static IntSpliterator fromTo(int from, int to) {
/* 1051 */     return new IntervalSpliterator(from, to);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorConcatenator
/*      */     implements IntSpliterator
/*      */   {
/*      */     private static final int EMPTY_CHARACTERISTICS = 16448;
/*      */     
/*      */     private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
/*      */     final IntSpliterator[] a;
/*      */     int offset;
/*      */     int length;
/* 1064 */     long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
/* 1065 */     int characteristics = 0;
/*      */     
/*      */     public SpliteratorConcatenator(IntSpliterator[] a, int offset, int length) {
/* 1068 */       this.a = a;
/* 1069 */       this.offset = offset;
/* 1070 */       this.length = length;
/* 1071 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1072 */       this.characteristics = computeCharacteristics();
/*      */     }
/*      */     
/*      */     private long recomputeRemaining() {
/* 1076 */       int curLength = this.length - 1;
/* 1077 */       int curOffset = this.offset + 1;
/* 1078 */       long result = 0L;
/* 1079 */       while (curLength > 0) {
/* 1080 */         long cur = this.a[curOffset++].estimateSize();
/* 1081 */         curLength--;
/* 1082 */         if (cur == Long.MAX_VALUE) return Long.MAX_VALUE; 
/* 1083 */         result += cur;
/*      */         
/* 1085 */         if (result == Long.MAX_VALUE || result < 0L) {
/* 1086 */           return Long.MAX_VALUE;
/*      */         }
/*      */       } 
/* 1089 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     private int computeCharacteristics() {
/* 1094 */       if (this.length <= 0) {
/* 1095 */         return 16448;
/*      */       }
/* 1097 */       int current = -1;
/* 1098 */       int curLength = this.length;
/* 1099 */       int curOffset = this.offset;
/* 1100 */       if (curLength > 1) {
/* 1101 */         current &= 0xFFFFFFFA;
/*      */       }
/* 1103 */       while (curLength > 0) {
/* 1104 */         current &= this.a[curOffset++].characteristics();
/* 1105 */         curLength--;
/*      */       } 
/* 1107 */       return current;
/*      */     }
/*      */     
/*      */     private void advanceNextSpliterator() {
/* 1111 */       if (this.length <= 0) {
/* 1112 */         throw new AssertionError("advanceNextSpliterator() called with none remaining");
/*      */       }
/* 1114 */       this.offset++;
/* 1115 */       this.length--;
/* 1116 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/* 1124 */       boolean any = false;
/* 1125 */       while (this.length > 0) {
/* 1126 */         if (this.a[this.offset].tryAdvance(action)) {
/* 1127 */           any = true;
/*      */           break;
/*      */         } 
/* 1130 */         advanceNextSpliterator();
/*      */       } 
/* 1132 */       return any;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1137 */       while (this.length > 0) {
/* 1138 */         this.a[this.offset].forEachRemaining(action);
/* 1139 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {
/* 1146 */       while (this.length > 0) {
/* 1147 */         this.a[this.offset].forEachRemaining(action);
/* 1148 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1154 */       if (this.length <= 0) return 0L; 
/* 1155 */       long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
/* 1156 */       if (est < 0L)
/*      */       {
/* 1158 */         return Long.MAX_VALUE;
/*      */       }
/* 1160 */       return est;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1165 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntComparator getComparator() {
/* 1170 */       if (this.length == 1 && (this.characteristics & 0x4) != 0) {
/* 1171 */         return this.a[this.offset].getComparator();
/*      */       }
/* 1173 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/*      */       IntSpliterator split;
/* 1182 */       switch (this.length) {
/*      */         case 0:
/* 1184 */           return null;
/*      */         
/*      */         case 1:
/* 1187 */           split = this.a[this.offset].trySplit();
/*      */ 
/*      */           
/* 1190 */           this.characteristics = this.a[this.offset].characteristics();
/* 1191 */           return split;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1196 */           split = this.a[this.offset++];
/* 1197 */           this.length--;
/*      */ 
/*      */           
/* 1200 */           this.characteristics = this.a[this.offset].characteristics();
/* 1201 */           this.remainingEstimatedExceptCurrent = 0L;
/* 1202 */           return split;
/*      */       } 
/*      */ 
/*      */       
/* 1206 */       int mid = this.length >> 1;
/* 1207 */       int ret_offset = this.offset;
/* 1208 */       int new_offset = this.offset + mid;
/* 1209 */       int ret_length = mid;
/* 1210 */       int new_length = this.length - mid;
/* 1211 */       this.offset = new_offset;
/* 1212 */       this.length = new_length;
/* 1213 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1214 */       this.characteristics = computeCharacteristics();
/* 1215 */       return new SpliteratorConcatenator(this.a, ret_offset, ret_length);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1220 */       long skipped = 0L;
/* 1221 */       if (this.length <= 0) return 0L; 
/* 1222 */       while (skipped < n && this.length >= 0) {
/* 1223 */         long curSkipped = this.a[this.offset].skip(n - skipped);
/* 1224 */         skipped += curSkipped;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1231 */         if (skipped < n) advanceNextSpliterator(); 
/*      */       } 
/* 1233 */       return skipped;
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
/*      */   public static IntSpliterator concat(IntSpliterator... a) {
/* 1254 */     return concat(a, 0, a.length);
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
/*      */   public static IntSpliterator concat(IntSpliterator[] a, int offset, int length) {
/* 1278 */     return new SpliteratorConcatenator(a, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorFromIterator
/*      */     implements IntSpliterator
/*      */   {
/*      */     private static final int BATCH_INCREMENT_SIZE = 1024;
/*      */     
/*      */     private static final int BATCH_MAX_SIZE = 33554432;
/*      */     
/*      */     private final IntIterator iter;
/*      */     
/*      */     final int characteristics;
/*      */     private final boolean knownSize;
/* 1293 */     private long size = Long.MAX_VALUE;
/* 1294 */     private int nextBatchSize = 1024;
/*      */     
/* 1296 */     private IntSpliterator delegate = null;
/*      */     
/*      */     SpliteratorFromIterator(IntIterator iter, int characteristics) {
/* 1299 */       this.iter = iter;
/* 1300 */       this.characteristics = 0x100 | characteristics;
/* 1301 */       this.knownSize = false;
/*      */     }
/*      */     
/*      */     SpliteratorFromIterator(IntIterator iter, long size, int additionalCharacteristics) {
/* 1305 */       this.iter = iter;
/* 1306 */       this.knownSize = true;
/* 1307 */       this.size = size;
/* 1308 */       if ((additionalCharacteristics & 0x1000) != 0) {
/* 1309 */         this.characteristics = 0x100 | additionalCharacteristics;
/*      */       } else {
/* 1311 */         this.characteristics = 0x4140 | additionalCharacteristics;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/* 1317 */       if (this.delegate != null) {
/* 1318 */         boolean hadRemaining = this.delegate.tryAdvance(action);
/* 1319 */         if (!hadRemaining) this.delegate = null; 
/* 1320 */         return hadRemaining;
/*      */       } 
/* 1322 */       if (!this.iter.hasNext()) return false; 
/* 1323 */       this.size--;
/* 1324 */       action.accept(this.iter.nextInt());
/* 1325 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1330 */       if (this.delegate != null) {
/* 1331 */         this.delegate.forEachRemaining(action);
/* 1332 */         this.delegate = null;
/*      */       } 
/* 1334 */       this.iter.forEachRemaining(action);
/* 1335 */       this.size = 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1340 */       if (this.delegate != null) return this.delegate.estimateSize(); 
/* 1341 */       if (!this.iter.hasNext()) return 0L;
/*      */ 
/*      */       
/* 1344 */       return (this.knownSize && this.size >= 0L) ? this.size : Long.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1349 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected IntSpliterator makeForSplit(int[] batch, int len) {
/* 1353 */       return IntSpliterators.wrap(batch, 0, len, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/* 1358 */       if (!this.iter.hasNext()) return null; 
/* 1359 */       int batchSizeEst = (this.knownSize && this.size > 0L) ? (int)Math.min(this.nextBatchSize, this.size) : this.nextBatchSize;
/*      */       
/* 1361 */       int[] batch = new int[batchSizeEst];
/* 1362 */       int actualSeen = 0;
/* 1363 */       while (actualSeen < batchSizeEst && this.iter.hasNext()) {
/* 1364 */         batch[actualSeen++] = this.iter.nextInt();
/* 1365 */         this.size--;
/*      */       } 
/*      */ 
/*      */       
/* 1369 */       if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
/* 1370 */         batch = Arrays.copyOf(batch, this.nextBatchSize);
/* 1371 */         while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
/* 1372 */           batch[actualSeen++] = this.iter.nextInt();
/* 1373 */           this.size--;
/*      */         } 
/*      */       } 
/* 1376 */       this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
/*      */       
/* 1378 */       IntSpliterator split = makeForSplit(batch, actualSeen);
/* 1379 */       if (!this.iter.hasNext()) {
/* 1380 */         this.delegate = split;
/* 1381 */         return split.trySplit();
/*      */       } 
/* 1383 */       return split;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1389 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1390 */       if (this.iter instanceof IntBigListIterator) {
/* 1391 */         long skipped = ((IntBigListIterator)this.iter).skip(n);
/* 1392 */         this.size -= skipped;
/* 1393 */         return skipped;
/*      */       } 
/* 1395 */       long skippedSoFar = 0L;
/* 1396 */       while (skippedSoFar < n && this.iter.hasNext()) {
/* 1397 */         int skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
/* 1398 */         this.size -= skipped;
/* 1399 */         skippedSoFar += skipped;
/*      */       } 
/* 1401 */       return skippedSoFar;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorFromIteratorWithComparator
/*      */     extends SpliteratorFromIterator {
/*      */     private final IntComparator comparator;
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(IntIterator iter, int additionalCharacteristics, IntComparator comparator) {
/* 1410 */       super(iter, additionalCharacteristics | 0x14);
/* 1411 */       this.comparator = comparator;
/*      */     }
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(IntIterator iter, long size, int additionalCharacteristics, IntComparator comparator) {
/* 1415 */       super(iter, size, additionalCharacteristics | 0x14);
/* 1416 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntComparator getComparator() {
/* 1421 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected IntSpliterator makeForSplit(int[] array, int len) {
/* 1426 */       return IntSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
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
/*      */   public static IntSpliterator asSpliterator(IntIterator iter, long size, int additionalCharacterisitcs) {
/* 1454 */     return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
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
/*      */   public static IntSpliterator asSpliteratorFromSorted(IntIterator iter, long size, int additionalCharacterisitcs, IntComparator comparator) {
/* 1486 */     return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
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
/*      */   public static IntSpliterator asSpliteratorUnknownSize(IntIterator iter, int characterisitcs) {
/* 1509 */     return new SpliteratorFromIterator(iter, characterisitcs);
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
/*      */   public static IntSpliterator asSpliteratorFromSortedUnknownSize(IntIterator iter, int additionalCharacterisitcs, IntComparator comparator) {
/* 1538 */     return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
/*      */   }
/*      */   
/*      */   private static final class IteratorFromSpliterator implements IntIterator, IntConsumer {
/*      */     private final IntSpliterator spliterator;
/* 1543 */     private int holder = 0;
/*      */     
/*      */     private boolean hasPeeked = false;
/*      */     
/*      */     IteratorFromSpliterator(IntSpliterator spliterator) {
/* 1548 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(int item) {
/* 1553 */       this.holder = item;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1558 */       if (this.hasPeeked) return true; 
/* 1559 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1560 */       if (!hadElement) return false; 
/* 1561 */       this.hasPeeked = true;
/* 1562 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1567 */       if (this.hasPeeked) {
/* 1568 */         this.hasPeeked = false;
/* 1569 */         return this.holder;
/*      */       } 
/* 1571 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1572 */       if (!hadElement) throw new NoSuchElementException(); 
/* 1573 */       return this.holder;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1578 */       if (this.hasPeeked) {
/* 1579 */         this.hasPeeked = false;
/* 1580 */         action.accept(this.holder);
/*      */       } 
/* 1582 */       this.spliterator.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1587 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1588 */       int skipped = 0;
/* 1589 */       if (this.hasPeeked) {
/* 1590 */         this.hasPeeked = false;
/* 1591 */         this.spliterator.skip(1L);
/* 1592 */         skipped++;
/* 1593 */         n--;
/*      */       } 
/* 1595 */       if (n > 0) {
/* 1596 */         skipped += SafeMath.safeLongToInt(this.spliterator.skip(n));
/*      */       }
/* 1598 */       return skipped;
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
/*      */   public static IntIterator asIterator(IntSpliterator spliterator) {
/* 1611 */     return new IteratorFromSpliterator(spliterator);
/*      */   }
/*      */   
/*      */   private static final class ByteSpliteratorWrapper
/*      */     implements IntSpliterator {
/*      */     final ByteSpliterator spliterator;
/*      */     
/*      */     public ByteSpliteratorWrapper(ByteSpliterator spliterator) {
/* 1619 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/* 1624 */       Objects.requireNonNull(action);
/* 1625 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1630 */       Objects.requireNonNull(action);
/* 1631 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1636 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1641 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1646 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/* 1651 */       ByteSpliterator possibleSplit = this.spliterator.trySplit();
/* 1652 */       if (possibleSplit == null) return null; 
/* 1653 */       return new ByteSpliteratorWrapper(possibleSplit);
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
/*      */   public static IntSpliterator wrap(ByteSpliterator spliterator) {
/* 1670 */     return new ByteSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class ShortSpliteratorWrapper
/*      */     implements IntSpliterator {
/*      */     final ShortSpliterator spliterator;
/*      */     
/*      */     public ShortSpliteratorWrapper(ShortSpliterator spliterator) {
/* 1678 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/* 1683 */       Objects.requireNonNull(action);
/* 1684 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1689 */       Objects.requireNonNull(action);
/* 1690 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1695 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1700 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1705 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/* 1710 */       ShortSpliterator possibleSplit = this.spliterator.trySplit();
/* 1711 */       if (possibleSplit == null) return null; 
/* 1712 */       return new ShortSpliteratorWrapper(possibleSplit);
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
/*      */   public static IntSpliterator wrap(ShortSpliterator spliterator) {
/* 1729 */     return new ShortSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class CharSpliteratorWrapper
/*      */     implements IntSpliterator {
/*      */     final CharSpliterator spliterator;
/*      */     
/*      */     public CharSpliteratorWrapper(CharSpliterator spliterator) {
/* 1737 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/* 1742 */       Objects.requireNonNull(action);
/* 1743 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1748 */       Objects.requireNonNull(action);
/* 1749 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1754 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1759 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1764 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/* 1769 */       CharSpliterator possibleSplit = this.spliterator.trySplit();
/* 1770 */       if (possibleSplit == null) return null; 
/* 1771 */       return new CharSpliteratorWrapper(possibleSplit);
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
/*      */   public static IntSpliterator wrap(CharSpliterator spliterator) {
/* 1794 */     return new CharSpliteratorWrapper(spliterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */