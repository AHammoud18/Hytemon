/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharSpliterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatSpliterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortSpliterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoublePredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DoubleSpliterators
/*      */ {
/*      */   static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
/*      */   public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
/*      */   public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
/*      */   public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
/*      */   private static final int SORTED_CHARACTERISTICS = 20;
/*      */   public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
/*      */   
/*      */   public static class EmptySpliterator
/*      */     implements DoubleSpliterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = 8379247926738230492L;
/*      */     private static final int CHARACTERISTICS = 16448;
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/*   59 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Double> action) {
/*   65 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
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
/*      */     public void forEachRemaining(DoubleConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*   94 */       return DoubleSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*   98 */       return DoubleSpliterators.EMPTY_SPLITERATOR;
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
/*      */     implements DoubleSpliterator {
/*      */     private final double element;
/*      */     private final DoubleComparator comparator;
/*      */     private boolean consumed = false;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public SingletonSpliterator(double element) {
/*  120 */       this(element, null);
/*      */     }
/*      */     
/*      */     public SingletonSpliterator(double element, DoubleComparator comparator) {
/*  124 */       this.element = element;
/*  125 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/*  130 */       Objects.requireNonNull(action);
/*  131 */       if (this.consumed) return false;
/*      */       
/*  133 */       this.consumed = true;
/*  134 */       action.accept(this.element);
/*  135 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
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
/*      */     public void forEachRemaining(DoubleConsumer action) {
/*  155 */       Objects.requireNonNull(action);
/*  156 */       if (!this.consumed) {
/*  157 */         this.consumed = true;
/*  158 */         action.accept(this.element);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleComparator getComparator() {
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
/*      */   public static DoubleSpliterator singleton(double element) {
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
/*      */   public static DoubleSpliterator singleton(double element, DoubleComparator comparator) {
/*  198 */     return new SingletonSpliterator(element, comparator);
/*      */   }
/*      */   
/*      */   private static class ArraySpliterator implements DoubleSpliterator {
/*      */     private static final int BASE_CHARACTERISTICS = 16720;
/*      */     final double[] array;
/*      */     private final int offset;
/*      */     private int length;
/*      */     private int curr;
/*      */     final int characteristics;
/*      */     
/*      */     public ArraySpliterator(double[] array, int offset, int length, int additionalCharacteristics) {
/*  210 */       this.array = array;
/*  211 */       this.offset = offset;
/*  212 */       this.length = length;
/*  213 */       this.characteristics = 0x4150 | additionalCharacteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
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
/*      */     public DoubleSpliterator trySplit() {
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
/*      */     public void forEachRemaining(DoubleConsumer action) {
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
/*      */     private final DoubleComparator comparator;
/*      */     
/*      */     public ArraySpliteratorWithComparator(double[] array, int offset, int length, int additionalCharacteristics, DoubleComparator comparator) {
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
/*      */     public DoubleComparator getComparator() {
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
/*      */   public static DoubleSpliterator wrap(double[] array, int offset, int length) {
/*  312 */     DoubleArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static DoubleSpliterator wrap(double[] array) {
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
/*      */   public static DoubleSpliterator wrap(double[] array, int offset, int length, int additionalCharacteristics) {
/*  358 */     DoubleArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static DoubleSpliterator wrapPreSorted(double[] array, int offset, int length, int additionalCharacteristics, DoubleComparator comparator) {
/*  390 */     DoubleArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static DoubleSpliterator wrapPreSorted(double[] array, int offset, int length, DoubleComparator comparator) {
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
/*      */   public static DoubleSpliterator wrapPreSorted(double[] array, DoubleComparator comparator) {
/*  444 */     return wrapPreSorted(array, 0, array.length, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SpliteratorWrapper
/*      */     implements DoubleSpliterator
/*      */   {
/*      */     final Spliterator<Double> i;
/*      */ 
/*      */ 
/*      */     
/*      */     public SpliteratorWrapper(Spliterator<Double> i) {
/*  457 */       this.i = i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/*  464 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/*  470 */       Objects.requireNonNull(action);
/*  471 */       Objects.requireNonNull(action); return this.i.tryAdvance((action instanceof Consumer) ? (Consumer<? super Double>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Double> action) {
/*  477 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/*  484 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/*  490 */       Objects.requireNonNull(action);
/*  491 */       Objects.requireNonNull(action); this.i.forEachRemaining((action instanceof Consumer) ? (Consumer<? super Double>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {
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
/*      */     public DoubleComparator getComparator() {
/*  512 */       return DoubleComparators.asDoubleComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/*  517 */       Spliterator<Double> innerSplit = this.i.trySplit();
/*  518 */       if (innerSplit == null) return null; 
/*  519 */       return new SpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper {
/*      */     final DoubleComparator comparator;
/*      */     
/*      */     public SpliteratorWrapperWithComparator(Spliterator<Double> i, DoubleComparator comparator) {
/*  527 */       super(i);
/*  528 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleComparator getComparator() {
/*  533 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/*  538 */       Spliterator<Double> innerSplit = this.i.trySplit();
/*  539 */       if (innerSplit == null) return null; 
/*  540 */       return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapper implements DoubleSpliterator {
/*      */     final Spliterator.OfDouble i;
/*      */     
/*      */     public PrimitiveSpliteratorWrapper(Spliterator.OfDouble i) {
/*  548 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/*  553 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
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
/*      */     public DoubleComparator getComparator() {
/*  573 */       return DoubleComparators.asDoubleComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/*  578 */       Spliterator.OfDouble innerSplit = this.i.trySplit();
/*  579 */       if (innerSplit == null) return null; 
/*  580 */       return new PrimitiveSpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapperWithComparator extends PrimitiveSpliteratorWrapper {
/*      */     final DoubleComparator comparator;
/*      */     
/*      */     public PrimitiveSpliteratorWrapperWithComparator(Spliterator.OfDouble i, DoubleComparator comparator) {
/*  588 */       super(i);
/*  589 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleComparator getComparator() {
/*  594 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/*  599 */       Spliterator.OfDouble innerSplit = this.i.trySplit();
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
/*      */   public static DoubleSpliterator asDoubleSpliterator(Spliterator<Double> i) {
/*  622 */     if (i instanceof DoubleSpliterator) return (DoubleSpliterator)i; 
/*  623 */     if (i instanceof Spliterator.OfDouble) return new PrimitiveSpliteratorWrapper((Spliterator.OfDouble)i); 
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
/*      */   public static DoubleSpliterator asDoubleSpliterator(Spliterator<Double> i, DoubleComparator comparatorOverride) {
/*  655 */     if (i instanceof DoubleSpliterator) throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + DoubleSpliterator.class.getSimpleName()); 
/*  656 */     if (i instanceof Spliterator.OfDouble) return new PrimitiveSpliteratorWrapperWithComparator((Spliterator.OfDouble)i, comparatorOverride); 
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
/*      */   public static void onEachMatching(DoubleSpliterator spliterator, DoublePredicate predicate, DoubleConsumer action) {
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
/*      */     extends AbstractDoubleSpliterator
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
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/*  818 */       if (this.pos >= getMaxPos()) return false; 
/*  819 */       action.accept(get(this.pos++));
/*  820 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
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
/*      */     public DoubleSpliterator trySplit() {
/*  866 */       int max = getMaxPos();
/*  867 */       int splitPoint = computeSplitPoint();
/*  868 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/*  869 */       splitPointCheck(splitPoint, max);
/*  870 */       int oldPos = this.pos;
/*  871 */       DoubleSpliterator maybeSplit = makeForSplit(oldPos, splitPoint);
/*  872 */       if (maybeSplit != null) this.pos = splitPoint; 
/*  873 */       return maybeSplit;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract double get(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract DoubleSpliterator makeForSplit(int param1Int1, int param1Int2);
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
/*      */     public DoubleSpliterator trySplit() {
/*  961 */       DoubleSpliterator maybeSplit = super.trySplit();
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
/*      */   private static class SpliteratorConcatenator
/*      */     implements DoubleSpliterator
/*      */   {
/*      */     private static final int EMPTY_CHARACTERISTICS = 16448;
/*      */     private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
/*      */     final DoubleSpliterator[] a;
/*      */     int offset;
/*      */     int length;
/*  980 */     long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
/*  981 */     int characteristics = 0;
/*      */     
/*      */     public SpliteratorConcatenator(DoubleSpliterator[] a, int offset, int length) {
/*  984 */       this.a = a;
/*  985 */       this.offset = offset;
/*  986 */       this.length = length;
/*  987 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*  988 */       this.characteristics = computeCharacteristics();
/*      */     }
/*      */     
/*      */     private long recomputeRemaining() {
/*  992 */       int curLength = this.length - 1;
/*  993 */       int curOffset = this.offset + 1;
/*  994 */       long result = 0L;
/*  995 */       while (curLength > 0) {
/*  996 */         long cur = this.a[curOffset++].estimateSize();
/*  997 */         curLength--;
/*  998 */         if (cur == Long.MAX_VALUE) return Long.MAX_VALUE; 
/*  999 */         result += cur;
/*      */         
/* 1001 */         if (result == Long.MAX_VALUE || result < 0L) {
/* 1002 */           return Long.MAX_VALUE;
/*      */         }
/*      */       } 
/* 1005 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     private int computeCharacteristics() {
/* 1010 */       if (this.length <= 0) {
/* 1011 */         return 16448;
/*      */       }
/* 1013 */       int current = -1;
/* 1014 */       int curLength = this.length;
/* 1015 */       int curOffset = this.offset;
/* 1016 */       if (curLength > 1) {
/* 1017 */         current &= 0xFFFFFFFA;
/*      */       }
/* 1019 */       while (curLength > 0) {
/* 1020 */         current &= this.a[curOffset++].characteristics();
/* 1021 */         curLength--;
/*      */       } 
/* 1023 */       return current;
/*      */     }
/*      */     
/*      */     private void advanceNextSpliterator() {
/* 1027 */       if (this.length <= 0) {
/* 1028 */         throw new AssertionError("advanceNextSpliterator() called with none remaining");
/*      */       }
/* 1030 */       this.offset++;
/* 1031 */       this.length--;
/* 1032 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/* 1040 */       boolean any = false;
/* 1041 */       while (this.length > 0) {
/* 1042 */         if (this.a[this.offset].tryAdvance(action)) {
/* 1043 */           any = true;
/*      */           break;
/*      */         } 
/* 1046 */         advanceNextSpliterator();
/*      */       } 
/* 1048 */       return any;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1053 */       while (this.length > 0) {
/* 1054 */         this.a[this.offset].forEachRemaining(action);
/* 1055 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {
/* 1062 */       while (this.length > 0) {
/* 1063 */         this.a[this.offset].forEachRemaining(action);
/* 1064 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1070 */       if (this.length <= 0) return 0L; 
/* 1071 */       long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
/* 1072 */       if (est < 0L)
/*      */       {
/* 1074 */         return Long.MAX_VALUE;
/*      */       }
/* 1076 */       return est;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1081 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleComparator getComparator() {
/* 1086 */       if (this.length == 1 && (this.characteristics & 0x4) != 0) {
/* 1087 */         return this.a[this.offset].getComparator();
/*      */       }
/* 1089 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/*      */       DoubleSpliterator split;
/* 1098 */       switch (this.length) {
/*      */         case 0:
/* 1100 */           return null;
/*      */         
/*      */         case 1:
/* 1103 */           split = this.a[this.offset].trySplit();
/*      */ 
/*      */           
/* 1106 */           this.characteristics = this.a[this.offset].characteristics();
/* 1107 */           return split;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1112 */           split = this.a[this.offset++];
/* 1113 */           this.length--;
/*      */ 
/*      */           
/* 1116 */           this.characteristics = this.a[this.offset].characteristics();
/* 1117 */           this.remainingEstimatedExceptCurrent = 0L;
/* 1118 */           return split;
/*      */       } 
/*      */ 
/*      */       
/* 1122 */       int mid = this.length >> 1;
/* 1123 */       int ret_offset = this.offset;
/* 1124 */       int new_offset = this.offset + mid;
/* 1125 */       int ret_length = mid;
/* 1126 */       int new_length = this.length - mid;
/* 1127 */       this.offset = new_offset;
/* 1128 */       this.length = new_length;
/* 1129 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1130 */       this.characteristics = computeCharacteristics();
/* 1131 */       return new SpliteratorConcatenator(this.a, ret_offset, ret_length);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1136 */       long skipped = 0L;
/* 1137 */       if (this.length <= 0) return 0L; 
/* 1138 */       while (skipped < n && this.length >= 0) {
/* 1139 */         long curSkipped = this.a[this.offset].skip(n - skipped);
/* 1140 */         skipped += curSkipped;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1147 */         if (skipped < n) advanceNextSpliterator(); 
/*      */       } 
/* 1149 */       return skipped;
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
/*      */   public static DoubleSpliterator concat(DoubleSpliterator... a) {
/* 1170 */     return concat(a, 0, a.length);
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
/*      */   public static DoubleSpliterator concat(DoubleSpliterator[] a, int offset, int length) {
/* 1194 */     return new SpliteratorConcatenator(a, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorFromIterator
/*      */     implements DoubleSpliterator
/*      */   {
/*      */     private static final int BATCH_INCREMENT_SIZE = 1024;
/*      */     
/*      */     private static final int BATCH_MAX_SIZE = 33554432;
/*      */     
/*      */     private final DoubleIterator iter;
/*      */     
/*      */     final int characteristics;
/*      */     private final boolean knownSize;
/* 1209 */     private long size = Long.MAX_VALUE;
/* 1210 */     private int nextBatchSize = 1024;
/*      */     
/* 1212 */     private DoubleSpliterator delegate = null;
/*      */     
/*      */     SpliteratorFromIterator(DoubleIterator iter, int characteristics) {
/* 1215 */       this.iter = iter;
/* 1216 */       this.characteristics = 0x100 | characteristics;
/* 1217 */       this.knownSize = false;
/*      */     }
/*      */     
/*      */     SpliteratorFromIterator(DoubleIterator iter, long size, int additionalCharacteristics) {
/* 1221 */       this.iter = iter;
/* 1222 */       this.knownSize = true;
/* 1223 */       this.size = size;
/* 1224 */       if ((additionalCharacteristics & 0x1000) != 0) {
/* 1225 */         this.characteristics = 0x100 | additionalCharacteristics;
/*      */       } else {
/* 1227 */         this.characteristics = 0x4140 | additionalCharacteristics;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/* 1233 */       if (this.delegate != null) {
/* 1234 */         boolean hadRemaining = this.delegate.tryAdvance(action);
/* 1235 */         if (!hadRemaining) this.delegate = null; 
/* 1236 */         return hadRemaining;
/*      */       } 
/* 1238 */       if (!this.iter.hasNext()) return false; 
/* 1239 */       this.size--;
/* 1240 */       action.accept(this.iter.nextDouble());
/* 1241 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1246 */       if (this.delegate != null) {
/* 1247 */         this.delegate.forEachRemaining(action);
/* 1248 */         this.delegate = null;
/*      */       } 
/* 1250 */       this.iter.forEachRemaining(action);
/* 1251 */       this.size = 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1256 */       if (this.delegate != null) return this.delegate.estimateSize(); 
/* 1257 */       if (!this.iter.hasNext()) return 0L;
/*      */ 
/*      */       
/* 1260 */       return (this.knownSize && this.size >= 0L) ? this.size : Long.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1265 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected DoubleSpliterator makeForSplit(double[] batch, int len) {
/* 1269 */       return DoubleSpliterators.wrap(batch, 0, len, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/* 1274 */       if (!this.iter.hasNext()) return null; 
/* 1275 */       int batchSizeEst = (this.knownSize && this.size > 0L) ? (int)Math.min(this.nextBatchSize, this.size) : this.nextBatchSize;
/*      */       
/* 1277 */       double[] batch = new double[batchSizeEst];
/* 1278 */       int actualSeen = 0;
/* 1279 */       while (actualSeen < batchSizeEst && this.iter.hasNext()) {
/* 1280 */         batch[actualSeen++] = this.iter.nextDouble();
/* 1281 */         this.size--;
/*      */       } 
/*      */ 
/*      */       
/* 1285 */       if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
/* 1286 */         batch = Arrays.copyOf(batch, this.nextBatchSize);
/* 1287 */         while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
/* 1288 */           batch[actualSeen++] = this.iter.nextDouble();
/* 1289 */           this.size--;
/*      */         } 
/*      */       } 
/* 1292 */       this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
/*      */       
/* 1294 */       DoubleSpliterator split = makeForSplit(batch, actualSeen);
/* 1295 */       if (!this.iter.hasNext()) {
/* 1296 */         this.delegate = split;
/* 1297 */         return split.trySplit();
/*      */       } 
/* 1299 */       return split;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1305 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1306 */       if (this.iter instanceof DoubleBigListIterator) {
/* 1307 */         long skipped = ((DoubleBigListIterator)this.iter).skip(n);
/* 1308 */         this.size -= skipped;
/* 1309 */         return skipped;
/*      */       } 
/* 1311 */       long skippedSoFar = 0L;
/* 1312 */       while (skippedSoFar < n && this.iter.hasNext()) {
/* 1313 */         int skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
/* 1314 */         this.size -= skipped;
/* 1315 */         skippedSoFar += skipped;
/*      */       } 
/* 1317 */       return skippedSoFar;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorFromIteratorWithComparator
/*      */     extends SpliteratorFromIterator {
/*      */     private final DoubleComparator comparator;
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(DoubleIterator iter, int additionalCharacteristics, DoubleComparator comparator) {
/* 1326 */       super(iter, additionalCharacteristics | 0x14);
/* 1327 */       this.comparator = comparator;
/*      */     }
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(DoubleIterator iter, long size, int additionalCharacteristics, DoubleComparator comparator) {
/* 1331 */       super(iter, size, additionalCharacteristics | 0x14);
/* 1332 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleComparator getComparator() {
/* 1337 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected DoubleSpliterator makeForSplit(double[] array, int len) {
/* 1342 */       return DoubleSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
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
/*      */   public static DoubleSpliterator asSpliterator(DoubleIterator iter, long size, int additionalCharacterisitcs) {
/* 1370 */     return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
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
/*      */   public static DoubleSpliterator asSpliteratorFromSorted(DoubleIterator iter, long size, int additionalCharacterisitcs, DoubleComparator comparator) {
/* 1402 */     return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
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
/*      */   public static DoubleSpliterator asSpliteratorUnknownSize(DoubleIterator iter, int characterisitcs) {
/* 1425 */     return new SpliteratorFromIterator(iter, characterisitcs);
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
/*      */   public static DoubleSpliterator asSpliteratorFromSortedUnknownSize(DoubleIterator iter, int additionalCharacterisitcs, DoubleComparator comparator) {
/* 1454 */     return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
/*      */   }
/*      */   
/*      */   private static final class IteratorFromSpliterator implements DoubleIterator, DoubleConsumer {
/*      */     private final DoubleSpliterator spliterator;
/* 1459 */     private double holder = 0.0D;
/*      */     
/*      */     private boolean hasPeeked = false;
/*      */     
/*      */     IteratorFromSpliterator(DoubleSpliterator spliterator) {
/* 1464 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(double item) {
/* 1469 */       this.holder = item;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1474 */       if (this.hasPeeked) return true; 
/* 1475 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1476 */       if (!hadElement) return false; 
/* 1477 */       this.hasPeeked = true;
/* 1478 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1483 */       if (this.hasPeeked) {
/* 1484 */         this.hasPeeked = false;
/* 1485 */         return this.holder;
/*      */       } 
/* 1487 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1488 */       if (!hadElement) throw new NoSuchElementException(); 
/* 1489 */       return this.holder;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1494 */       if (this.hasPeeked) {
/* 1495 */         this.hasPeeked = false;
/* 1496 */         action.accept(this.holder);
/*      */       } 
/* 1498 */       this.spliterator.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1503 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1504 */       int skipped = 0;
/* 1505 */       if (this.hasPeeked) {
/* 1506 */         this.hasPeeked = false;
/* 1507 */         this.spliterator.skip(1L);
/* 1508 */         skipped++;
/* 1509 */         n--;
/*      */       } 
/* 1511 */       if (n > 0) {
/* 1512 */         skipped += SafeMath.safeLongToInt(this.spliterator.skip(n));
/*      */       }
/* 1514 */       return skipped;
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
/*      */   public static DoubleIterator asIterator(DoubleSpliterator spliterator) {
/* 1527 */     return new IteratorFromSpliterator(spliterator);
/*      */   }
/*      */   
/*      */   private static final class ByteSpliteratorWrapper
/*      */     implements DoubleSpliterator {
/*      */     final ByteSpliterator spliterator;
/*      */     
/*      */     public ByteSpliteratorWrapper(ByteSpliterator spliterator) {
/* 1535 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/* 1540 */       Objects.requireNonNull(action);
/* 1541 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1546 */       Objects.requireNonNull(action);
/* 1547 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1552 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1557 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1562 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/* 1567 */       ByteSpliterator possibleSplit = this.spliterator.trySplit();
/* 1568 */       if (possibleSplit == null) return null; 
/* 1569 */       return new ByteSpliteratorWrapper(possibleSplit);
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
/*      */   public static DoubleSpliterator wrap(ByteSpliterator spliterator) {
/* 1586 */     return new ByteSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class ShortSpliteratorWrapper
/*      */     implements DoubleSpliterator {
/*      */     final ShortSpliterator spliterator;
/*      */     
/*      */     public ShortSpliteratorWrapper(ShortSpliterator spliterator) {
/* 1594 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/* 1599 */       Objects.requireNonNull(action);
/* 1600 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1605 */       Objects.requireNonNull(action);
/* 1606 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1611 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1616 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1621 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/* 1626 */       ShortSpliterator possibleSplit = this.spliterator.trySplit();
/* 1627 */       if (possibleSplit == null) return null; 
/* 1628 */       return new ShortSpliteratorWrapper(possibleSplit);
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
/*      */   public static DoubleSpliterator wrap(ShortSpliterator spliterator) {
/* 1645 */     return new ShortSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class CharSpliteratorWrapper
/*      */     implements DoubleSpliterator {
/*      */     final CharSpliterator spliterator;
/*      */     
/*      */     public CharSpliteratorWrapper(CharSpliterator spliterator) {
/* 1653 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/* 1658 */       Objects.requireNonNull(action);
/* 1659 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1664 */       Objects.requireNonNull(action);
/* 1665 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1670 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1675 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1680 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/* 1685 */       CharSpliterator possibleSplit = this.spliterator.trySplit();
/* 1686 */       if (possibleSplit == null) return null; 
/* 1687 */       return new CharSpliteratorWrapper(possibleSplit);
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
/*      */   public static DoubleSpliterator wrap(CharSpliterator spliterator) {
/* 1710 */     return new CharSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class IntSpliteratorWrapper
/*      */     implements DoubleSpliterator {
/*      */     final IntSpliterator spliterator;
/*      */     
/*      */     public IntSpliteratorWrapper(IntSpliterator spliterator) {
/* 1718 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/* 1723 */       Objects.requireNonNull(action);
/* 1724 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1729 */       Objects.requireNonNull(action);
/* 1730 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1735 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1740 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1745 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/* 1750 */       IntSpliterator possibleSplit = this.spliterator.trySplit();
/* 1751 */       if (possibleSplit == null) return null; 
/* 1752 */       return new IntSpliteratorWrapper(possibleSplit);
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
/*      */   public static DoubleSpliterator wrap(IntSpliterator spliterator) {
/* 1769 */     return new IntSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class FloatSpliteratorWrapper
/*      */     implements DoubleSpliterator {
/*      */     final FloatSpliterator spliterator;
/*      */     
/*      */     public FloatSpliteratorWrapper(FloatSpliterator spliterator) {
/* 1777 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(DoubleConsumer action) {
/* 1782 */       Objects.requireNonNull(action);
/* 1783 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1788 */       Objects.requireNonNull(action);
/* 1789 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1794 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1799 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1804 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator trySplit() {
/* 1809 */       FloatSpliterator possibleSplit = this.spliterator.trySplit();
/* 1810 */       if (possibleSplit == null) return null; 
/* 1811 */       return new FloatSpliteratorWrapper(possibleSplit);
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
/*      */   public static DoubleSpliterator wrap(FloatSpliterator spliterator) {
/* 1828 */     return new FloatSpliteratorWrapper(spliterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */