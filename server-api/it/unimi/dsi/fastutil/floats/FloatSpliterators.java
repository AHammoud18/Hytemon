/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharSpliterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterators;
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
/*      */ 
/*      */ 
/*      */ public final class FloatSpliterators
/*      */ {
/*      */   static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
/*      */   public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
/*      */   public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
/*      */   public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
/*      */   private static final int SORTED_CHARACTERISTICS = 20;
/*      */   public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
/*      */   
/*      */   public static class EmptySpliterator
/*      */     implements FloatSpliterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = 8379247926738230492L;
/*      */     private static final int CHARACTERISTICS = 16448;
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/*   61 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Float> action) {
/*   67 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/*   72 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*   77 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*   82 */       return 16448;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*   96 */       return FloatSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  100 */       return FloatSpliterators.EMPTY_SPLITERATOR;
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
/*  112 */   public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();
/*      */   
/*      */   private static class SingletonSpliterator
/*      */     implements FloatSpliterator {
/*      */     private final float element;
/*      */     private final FloatComparator comparator;
/*      */     private boolean consumed = false;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public SingletonSpliterator(float element) {
/*  122 */       this(element, null);
/*      */     }
/*      */     
/*      */     public SingletonSpliterator(float element, FloatComparator comparator) {
/*  126 */       this.element = element;
/*  127 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/*  132 */       Objects.requireNonNull(action);
/*  133 */       if (this.consumed) return false;
/*      */       
/*  135 */       this.consumed = true;
/*  136 */       action.accept(this.element);
/*  137 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/*  142 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  147 */       return this.consumed ? 0L : 1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  152 */       return 17749;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  157 */       Objects.requireNonNull(action);
/*  158 */       if (!this.consumed) {
/*  159 */         this.consumed = true;
/*  160 */         action.accept(this.element);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator getComparator() {
/*  166 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  171 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  172 */       if (n == 0L || this.consumed) return 0L; 
/*  173 */       this.consumed = true;
/*  174 */       return 1L;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatSpliterator singleton(float element) {
/*  185 */     return new SingletonSpliterator(element);
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
/*      */   public static FloatSpliterator singleton(float element, FloatComparator comparator) {
/*  200 */     return new SingletonSpliterator(element, comparator);
/*      */   }
/*      */   
/*      */   private static class ArraySpliterator implements FloatSpliterator {
/*      */     private static final int BASE_CHARACTERISTICS = 16720;
/*      */     final float[] array;
/*      */     private final int offset;
/*      */     private int length;
/*      */     private int curr;
/*      */     final int characteristics;
/*      */     
/*      */     public ArraySpliterator(float[] array, int offset, int length, int additionalCharacteristics) {
/*  212 */       this.array = array;
/*  213 */       this.offset = offset;
/*  214 */       this.length = length;
/*  215 */       this.characteristics = 0x4150 | additionalCharacteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/*  220 */       if (this.curr >= this.length) return false; 
/*  221 */       Objects.requireNonNull(action);
/*  222 */       action.accept(this.array[this.offset + this.curr++]);
/*  223 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  228 */       return (this.length - this.curr);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  233 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected ArraySpliterator makeForSplit(int newOffset, int newLength) {
/*  237 */       return new ArraySpliterator(this.array, newOffset, newLength, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/*  242 */       int retLength = this.length - this.curr >> 1;
/*  243 */       if (retLength <= 1) return null; 
/*  244 */       int myNewCurr = this.curr + retLength;
/*  245 */       int retOffset = this.offset + this.curr;
/*      */       
/*  247 */       this.curr = myNewCurr;
/*      */       
/*  249 */       return makeForSplit(retOffset, retLength);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  254 */       Objects.requireNonNull(action);
/*  255 */       for (; this.curr < this.length; this.curr++) {
/*  256 */         action.accept(this.array[this.offset + this.curr]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  262 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  263 */       if (this.curr >= this.length) return 0L; 
/*  264 */       int remaining = this.length - this.curr;
/*  265 */       if (n < remaining) {
/*  266 */         this.curr = SafeMath.safeLongToInt(this.curr + n);
/*  267 */         return n;
/*      */       } 
/*  269 */       n = remaining;
/*  270 */       this.curr = this.length;
/*  271 */       return n;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ArraySpliteratorWithComparator extends ArraySpliterator {
/*      */     private final FloatComparator comparator;
/*      */     
/*      */     public ArraySpliteratorWithComparator(float[] array, int offset, int length, int additionalCharacteristics, FloatComparator comparator) {
/*  279 */       super(array, offset, length, additionalCharacteristics | 0x14);
/*  280 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected ArraySpliteratorWithComparator makeForSplit(int newOffset, int newLength) {
/*  285 */       return new ArraySpliteratorWithComparator(this.array, newOffset, newLength, this.characteristics, this.comparator);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator getComparator() {
/*  290 */       return this.comparator;
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
/*      */   public static FloatSpliterator wrap(float[] array, int offset, int length) {
/*  314 */     FloatArrays.ensureOffsetLength(array, offset, length);
/*  315 */     return new ArraySpliterator(array, offset, length, 0);
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
/*      */   public static FloatSpliterator wrap(float[] array) {
/*  334 */     return new ArraySpliterator(array, 0, array.length, 0);
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
/*      */   public static FloatSpliterator wrap(float[] array, int offset, int length, int additionalCharacteristics) {
/*  360 */     FloatArrays.ensureOffsetLength(array, offset, length);
/*  361 */     return new ArraySpliterator(array, offset, length, additionalCharacteristics);
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
/*      */   public static FloatSpliterator wrapPreSorted(float[] array, int offset, int length, int additionalCharacteristics, FloatComparator comparator) {
/*  392 */     FloatArrays.ensureOffsetLength(array, offset, length);
/*  393 */     return new ArraySpliteratorWithComparator(array, offset, length, additionalCharacteristics, comparator);
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
/*      */   public static FloatSpliterator wrapPreSorted(float[] array, int offset, int length, FloatComparator comparator) {
/*  421 */     return wrapPreSorted(array, offset, length, 0, comparator);
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
/*      */   public static FloatSpliterator wrapPreSorted(float[] array, FloatComparator comparator) {
/*  446 */     return wrapPreSorted(array, 0, array.length, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SpliteratorWrapper
/*      */     implements FloatSpliterator
/*      */   {
/*      */     final Spliterator<Float> i;
/*      */ 
/*      */ 
/*      */     
/*      */     public SpliteratorWrapper(Spliterator<Float> i) {
/*  459 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/*  464 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Float> action) {
/*  470 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  475 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {
/*  481 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  486 */       return this.i.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  491 */       return this.i.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator getComparator() {
/*  496 */       return FloatComparators.asFloatComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/*  501 */       Spliterator<Float> innerSplit = this.i.trySplit();
/*  502 */       if (innerSplit == null) return null; 
/*  503 */       return new SpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper {
/*      */     final FloatComparator comparator;
/*      */     
/*      */     public SpliteratorWrapperWithComparator(Spliterator<Float> i, FloatComparator comparator) {
/*  511 */       super(i);
/*  512 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator getComparator() {
/*  517 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/*  522 */       Spliterator<Float> innerSplit = this.i.trySplit();
/*  523 */       if (innerSplit == null) return null; 
/*  524 */       return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapper implements FloatSpliterator {
/*      */     final Spliterator.OfDouble i;
/*      */     
/*      */     public PrimitiveSpliteratorWrapper(Spliterator.OfDouble i) {
/*  532 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/*  537 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  542 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  547 */       return this.i.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  552 */       return this.i.characteristics();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatComparator getComparator() {
/*  558 */       Comparator<? super Double> comp = this.i.getComparator();
/*  559 */       return (left, right) -> comp.compare(Double.valueOf(left), Double.valueOf(right));
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/*  564 */       Spliterator.OfDouble innerSplit = this.i.trySplit();
/*  565 */       if (innerSplit == null) return null; 
/*  566 */       return new PrimitiveSpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapperWithComparator extends PrimitiveSpliteratorWrapper {
/*      */     final FloatComparator comparator;
/*      */     
/*      */     public PrimitiveSpliteratorWrapperWithComparator(Spliterator.OfDouble i, FloatComparator comparator) {
/*  574 */       super(i);
/*  575 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator getComparator() {
/*  580 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/*  585 */       Spliterator.OfDouble innerSplit = this.i.trySplit();
/*  586 */       if (innerSplit == null) return null; 
/*  587 */       return new PrimitiveSpliteratorWrapperWithComparator(innerSplit, this.comparator);
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
/*      */   public static FloatSpliterator asFloatSpliterator(Spliterator<Float> i) {
/*  608 */     if (i instanceof FloatSpliterator) return (FloatSpliterator)i; 
/*  609 */     return new SpliteratorWrapper(i);
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
/*      */   public static FloatSpliterator asFloatSpliterator(Spliterator<Float> i, FloatComparator comparatorOverride) {
/*  640 */     if (i instanceof FloatSpliterator) throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + FloatSpliterator.class.getSimpleName()); 
/*  641 */     if (i instanceof Spliterator.OfDouble) return new PrimitiveSpliteratorWrapperWithComparator((Spliterator.OfDouble)i, comparatorOverride); 
/*  642 */     return new SpliteratorWrapperWithComparator(i, comparatorOverride);
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
/*      */   public static FloatSpliterator narrow(Spliterator.OfDouble i) {
/*  655 */     return new PrimitiveSpliteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleSpliterator widen(FloatSpliterator i) {
/*  665 */     return DoubleSpliterators.wrap(i);
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
/*      */   public static void onEachMatching(FloatSpliterator spliterator, FloatPredicate predicate, FloatConsumer action) {
/*  678 */     Objects.requireNonNull(predicate);
/*  679 */     Objects.requireNonNull(action);
/*  680 */     spliterator.forEachRemaining(value -> {
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
/*      */   public static void onEachMatching(FloatSpliterator spliterator, DoublePredicate predicate, DoubleConsumer action) {
/*  697 */     Objects.requireNonNull(predicate);
/*  698 */     Objects.requireNonNull(action);
/*      */ 
/*      */     
/*  701 */     spliterator.forEachRemaining(value -> {
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
/*      */     extends AbstractFloatSpliterator
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
/*  739 */       this.pos = initialPos;
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
/*  823 */       return this.pos + (getMaxPos() - this.pos) / 2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void splitPointCheck(int splitPoint, int observedMax) {
/*  829 */       if (splitPoint < this.pos || splitPoint > observedMax) {
/*  830 */         throw new IndexOutOfBoundsException("splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  837 */       return 16720;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  842 */       return getMaxPos() - this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/*  847 */       if (this.pos >= getMaxPos()) return false; 
/*  848 */       action.accept(get(this.pos++));
/*  849 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  854 */       for (int max = getMaxPos(); this.pos < max; this.pos++) {
/*  855 */         action.accept(get(this.pos));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  863 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  864 */       int max = getMaxPos();
/*  865 */       if (this.pos >= max) return 0L; 
/*  866 */       int remaining = max - this.pos;
/*  867 */       if (n < remaining) {
/*  868 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/*  869 */         return n;
/*      */       } 
/*  871 */       n = remaining;
/*  872 */       this.pos = max;
/*  873 */       return n;
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
/*      */     public FloatSpliterator trySplit() {
/*  895 */       int max = getMaxPos();
/*  896 */       int splitPoint = computeSplitPoint();
/*  897 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/*  898 */       splitPointCheck(splitPoint, max);
/*  899 */       int oldPos = this.pos;
/*  900 */       FloatSpliterator maybeSplit = makeForSplit(oldPos, splitPoint);
/*  901 */       if (maybeSplit != null) this.pos = splitPoint; 
/*  902 */       return maybeSplit;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract float get(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract FloatSpliterator makeForSplit(int param1Int1, int param1Int2);
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
/*  928 */       super(initialPos);
/*  929 */       this.maxPos = maxPos;
/*      */     }
/*      */ 
/*      */     
/*      */     protected final int getMaxPos() {
/*  934 */       return this.maxPos;
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
/*  959 */     protected int maxPos = -1;
/*      */     private boolean maxPosFixed;
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos) {
/*  963 */       super(initialPos);
/*  964 */       this.maxPosFixed = false;
/*      */     }
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos, int fixedMaxPos) {
/*  968 */       super(initialPos);
/*  969 */       this.maxPos = fixedMaxPos;
/*  970 */       this.maxPosFixed = true;
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
/*  985 */       return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/*  990 */       FloatSpliterator maybeSplit = super.trySplit();
/*  991 */       if (!this.maxPosFixed && maybeSplit != null) {
/*  992 */         this.maxPos = getMaxPosFromBackingStore();
/*  993 */         this.maxPosFixed = true;
/*      */       } 
/*  995 */       return maybeSplit;
/*      */     }
/*      */     
/*      */     protected abstract int getMaxPosFromBackingStore();
/*      */   }
/*      */   
/*      */   private static class SpliteratorConcatenator
/*      */     implements FloatSpliterator
/*      */   {
/*      */     private static final int EMPTY_CHARACTERISTICS = 16448;
/*      */     private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
/*      */     final FloatSpliterator[] a;
/*      */     int offset;
/*      */     int length;
/* 1009 */     long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
/* 1010 */     int characteristics = 0;
/*      */     
/*      */     public SpliteratorConcatenator(FloatSpliterator[] a, int offset, int length) {
/* 1013 */       this.a = a;
/* 1014 */       this.offset = offset;
/* 1015 */       this.length = length;
/* 1016 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1017 */       this.characteristics = computeCharacteristics();
/*      */     }
/*      */     
/*      */     private long recomputeRemaining() {
/* 1021 */       int curLength = this.length - 1;
/* 1022 */       int curOffset = this.offset + 1;
/* 1023 */       long result = 0L;
/* 1024 */       while (curLength > 0) {
/* 1025 */         long cur = this.a[curOffset++].estimateSize();
/* 1026 */         curLength--;
/* 1027 */         if (cur == Long.MAX_VALUE) return Long.MAX_VALUE; 
/* 1028 */         result += cur;
/*      */         
/* 1030 */         if (result == Long.MAX_VALUE || result < 0L) {
/* 1031 */           return Long.MAX_VALUE;
/*      */         }
/*      */       } 
/* 1034 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     private int computeCharacteristics() {
/* 1039 */       if (this.length <= 0) {
/* 1040 */         return 16448;
/*      */       }
/* 1042 */       int current = -1;
/* 1043 */       int curLength = this.length;
/* 1044 */       int curOffset = this.offset;
/* 1045 */       if (curLength > 1) {
/* 1046 */         current &= 0xFFFFFFFA;
/*      */       }
/* 1048 */       while (curLength > 0) {
/* 1049 */         current &= this.a[curOffset++].characteristics();
/* 1050 */         curLength--;
/*      */       } 
/* 1052 */       return current;
/*      */     }
/*      */     
/*      */     private void advanceNextSpliterator() {
/* 1056 */       if (this.length <= 0) {
/* 1057 */         throw new AssertionError("advanceNextSpliterator() called with none remaining");
/*      */       }
/* 1059 */       this.offset++;
/* 1060 */       this.length--;
/* 1061 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/* 1069 */       boolean any = false;
/* 1070 */       while (this.length > 0) {
/* 1071 */         if (this.a[this.offset].tryAdvance(action)) {
/* 1072 */           any = true;
/*      */           break;
/*      */         } 
/* 1075 */         advanceNextSpliterator();
/*      */       } 
/* 1077 */       return any;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1082 */       while (this.length > 0) {
/* 1083 */         this.a[this.offset].forEachRemaining(action);
/* 1084 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {
/* 1091 */       while (this.length > 0) {
/* 1092 */         this.a[this.offset].forEachRemaining(action);
/* 1093 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1099 */       if (this.length <= 0) return 0L; 
/* 1100 */       long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
/* 1101 */       if (est < 0L)
/*      */       {
/* 1103 */         return Long.MAX_VALUE;
/*      */       }
/* 1105 */       return est;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1110 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator getComparator() {
/* 1115 */       if (this.length == 1 && (this.characteristics & 0x4) != 0) {
/* 1116 */         return this.a[this.offset].getComparator();
/*      */       }
/* 1118 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/*      */       FloatSpliterator split;
/* 1127 */       switch (this.length) {
/*      */         case 0:
/* 1129 */           return null;
/*      */         
/*      */         case 1:
/* 1132 */           split = this.a[this.offset].trySplit();
/*      */ 
/*      */           
/* 1135 */           this.characteristics = this.a[this.offset].characteristics();
/* 1136 */           return split;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1141 */           split = this.a[this.offset++];
/* 1142 */           this.length--;
/*      */ 
/*      */           
/* 1145 */           this.characteristics = this.a[this.offset].characteristics();
/* 1146 */           this.remainingEstimatedExceptCurrent = 0L;
/* 1147 */           return split;
/*      */       } 
/*      */ 
/*      */       
/* 1151 */       int mid = this.length >> 1;
/* 1152 */       int ret_offset = this.offset;
/* 1153 */       int new_offset = this.offset + mid;
/* 1154 */       int ret_length = mid;
/* 1155 */       int new_length = this.length - mid;
/* 1156 */       this.offset = new_offset;
/* 1157 */       this.length = new_length;
/* 1158 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1159 */       this.characteristics = computeCharacteristics();
/* 1160 */       return new SpliteratorConcatenator(this.a, ret_offset, ret_length);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1165 */       long skipped = 0L;
/* 1166 */       if (this.length <= 0) return 0L; 
/* 1167 */       while (skipped < n && this.length >= 0) {
/* 1168 */         long curSkipped = this.a[this.offset].skip(n - skipped);
/* 1169 */         skipped += curSkipped;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1176 */         if (skipped < n) advanceNextSpliterator(); 
/*      */       } 
/* 1178 */       return skipped;
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
/*      */   public static FloatSpliterator concat(FloatSpliterator... a) {
/* 1199 */     return concat(a, 0, a.length);
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
/*      */   public static FloatSpliterator concat(FloatSpliterator[] a, int offset, int length) {
/* 1223 */     return new SpliteratorConcatenator(a, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorFromIterator
/*      */     implements FloatSpliterator
/*      */   {
/*      */     private static final int BATCH_INCREMENT_SIZE = 1024;
/*      */     
/*      */     private static final int BATCH_MAX_SIZE = 33554432;
/*      */     
/*      */     private final FloatIterator iter;
/*      */     
/*      */     final int characteristics;
/*      */     private final boolean knownSize;
/* 1238 */     private long size = Long.MAX_VALUE;
/* 1239 */     private int nextBatchSize = 1024;
/*      */     
/* 1241 */     private FloatSpliterator delegate = null;
/*      */     
/*      */     SpliteratorFromIterator(FloatIterator iter, int characteristics) {
/* 1244 */       this.iter = iter;
/* 1245 */       this.characteristics = 0x100 | characteristics;
/* 1246 */       this.knownSize = false;
/*      */     }
/*      */     
/*      */     SpliteratorFromIterator(FloatIterator iter, long size, int additionalCharacteristics) {
/* 1250 */       this.iter = iter;
/* 1251 */       this.knownSize = true;
/* 1252 */       this.size = size;
/* 1253 */       if ((additionalCharacteristics & 0x1000) != 0) {
/* 1254 */         this.characteristics = 0x100 | additionalCharacteristics;
/*      */       } else {
/* 1256 */         this.characteristics = 0x4140 | additionalCharacteristics;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/* 1262 */       if (this.delegate != null) {
/* 1263 */         boolean hadRemaining = this.delegate.tryAdvance(action);
/* 1264 */         if (!hadRemaining) this.delegate = null; 
/* 1265 */         return hadRemaining;
/*      */       } 
/* 1267 */       if (!this.iter.hasNext()) return false; 
/* 1268 */       this.size--;
/* 1269 */       action.accept(this.iter.nextFloat());
/* 1270 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1275 */       if (this.delegate != null) {
/* 1276 */         this.delegate.forEachRemaining(action);
/* 1277 */         this.delegate = null;
/*      */       } 
/* 1279 */       this.iter.forEachRemaining(action);
/* 1280 */       this.size = 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1285 */       if (this.delegate != null) return this.delegate.estimateSize(); 
/* 1286 */       if (!this.iter.hasNext()) return 0L;
/*      */ 
/*      */       
/* 1289 */       return (this.knownSize && this.size >= 0L) ? this.size : Long.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1294 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected FloatSpliterator makeForSplit(float[] batch, int len) {
/* 1298 */       return FloatSpliterators.wrap(batch, 0, len, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/* 1303 */       if (!this.iter.hasNext()) return null; 
/* 1304 */       int batchSizeEst = (this.knownSize && this.size > 0L) ? (int)Math.min(this.nextBatchSize, this.size) : this.nextBatchSize;
/*      */       
/* 1306 */       float[] batch = new float[batchSizeEst];
/* 1307 */       int actualSeen = 0;
/* 1308 */       while (actualSeen < batchSizeEst && this.iter.hasNext()) {
/* 1309 */         batch[actualSeen++] = this.iter.nextFloat();
/* 1310 */         this.size--;
/*      */       } 
/*      */ 
/*      */       
/* 1314 */       if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
/* 1315 */         batch = Arrays.copyOf(batch, this.nextBatchSize);
/* 1316 */         while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
/* 1317 */           batch[actualSeen++] = this.iter.nextFloat();
/* 1318 */           this.size--;
/*      */         } 
/*      */       } 
/* 1321 */       this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
/*      */       
/* 1323 */       FloatSpliterator split = makeForSplit(batch, actualSeen);
/* 1324 */       if (!this.iter.hasNext()) {
/* 1325 */         this.delegate = split;
/* 1326 */         return split.trySplit();
/*      */       } 
/* 1328 */       return split;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1334 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1335 */       if (this.iter instanceof FloatBigListIterator) {
/* 1336 */         long skipped = ((FloatBigListIterator)this.iter).skip(n);
/* 1337 */         this.size -= skipped;
/* 1338 */         return skipped;
/*      */       } 
/* 1340 */       long skippedSoFar = 0L;
/* 1341 */       while (skippedSoFar < n && this.iter.hasNext()) {
/* 1342 */         int skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
/* 1343 */         this.size -= skipped;
/* 1344 */         skippedSoFar += skipped;
/*      */       } 
/* 1346 */       return skippedSoFar;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorFromIteratorWithComparator
/*      */     extends SpliteratorFromIterator {
/*      */     private final FloatComparator comparator;
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(FloatIterator iter, int additionalCharacteristics, FloatComparator comparator) {
/* 1355 */       super(iter, additionalCharacteristics | 0x14);
/* 1356 */       this.comparator = comparator;
/*      */     }
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(FloatIterator iter, long size, int additionalCharacteristics, FloatComparator comparator) {
/* 1360 */       super(iter, size, additionalCharacteristics | 0x14);
/* 1361 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator getComparator() {
/* 1366 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected FloatSpliterator makeForSplit(float[] array, int len) {
/* 1371 */       return FloatSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
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
/*      */   public static FloatSpliterator asSpliterator(FloatIterator iter, long size, int additionalCharacterisitcs) {
/* 1399 */     return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
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
/*      */   public static FloatSpliterator asSpliteratorFromSorted(FloatIterator iter, long size, int additionalCharacterisitcs, FloatComparator comparator) {
/* 1431 */     return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
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
/*      */   public static FloatSpliterator asSpliteratorUnknownSize(FloatIterator iter, int characterisitcs) {
/* 1454 */     return new SpliteratorFromIterator(iter, characterisitcs);
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
/*      */   public static FloatSpliterator asSpliteratorFromSortedUnknownSize(FloatIterator iter, int additionalCharacterisitcs, FloatComparator comparator) {
/* 1483 */     return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
/*      */   }
/*      */   
/*      */   private static final class IteratorFromSpliterator implements FloatIterator, FloatConsumer {
/*      */     private final FloatSpliterator spliterator;
/* 1488 */     private float holder = 0.0F;
/*      */     
/*      */     private boolean hasPeeked = false;
/*      */     
/*      */     IteratorFromSpliterator(FloatSpliterator spliterator) {
/* 1493 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(float item) {
/* 1498 */       this.holder = item;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1503 */       if (this.hasPeeked) return true; 
/* 1504 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1505 */       if (!hadElement) return false; 
/* 1506 */       this.hasPeeked = true;
/* 1507 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1512 */       if (this.hasPeeked) {
/* 1513 */         this.hasPeeked = false;
/* 1514 */         return this.holder;
/*      */       } 
/* 1516 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1517 */       if (!hadElement) throw new NoSuchElementException(); 
/* 1518 */       return this.holder;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1523 */       if (this.hasPeeked) {
/* 1524 */         this.hasPeeked = false;
/* 1525 */         action.accept(this.holder);
/*      */       } 
/* 1527 */       this.spliterator.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1532 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1533 */       int skipped = 0;
/* 1534 */       if (this.hasPeeked) {
/* 1535 */         this.hasPeeked = false;
/* 1536 */         this.spliterator.skip(1L);
/* 1537 */         skipped++;
/* 1538 */         n--;
/*      */       } 
/* 1540 */       if (n > 0) {
/* 1541 */         skipped += SafeMath.safeLongToInt(this.spliterator.skip(n));
/*      */       }
/* 1543 */       return skipped;
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
/*      */   public static FloatIterator asIterator(FloatSpliterator spliterator) {
/* 1556 */     return new IteratorFromSpliterator(spliterator);
/*      */   }
/*      */   
/*      */   private static final class ByteSpliteratorWrapper
/*      */     implements FloatSpliterator {
/*      */     final ByteSpliterator spliterator;
/*      */     
/*      */     public ByteSpliteratorWrapper(ByteSpliterator spliterator) {
/* 1564 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/* 1569 */       Objects.requireNonNull(action);
/* 1570 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1575 */       Objects.requireNonNull(action);
/* 1576 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1581 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1586 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1591 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/* 1596 */       ByteSpliterator possibleSplit = this.spliterator.trySplit();
/* 1597 */       if (possibleSplit == null) return null; 
/* 1598 */       return new ByteSpliteratorWrapper(possibleSplit);
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
/*      */   public static FloatSpliterator wrap(ByteSpliterator spliterator) {
/* 1615 */     return new ByteSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class ShortSpliteratorWrapper
/*      */     implements FloatSpliterator {
/*      */     final ShortSpliterator spliterator;
/*      */     
/*      */     public ShortSpliteratorWrapper(ShortSpliterator spliterator) {
/* 1623 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/* 1628 */       Objects.requireNonNull(action);
/* 1629 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1634 */       Objects.requireNonNull(action);
/* 1635 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1640 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1645 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1650 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/* 1655 */       ShortSpliterator possibleSplit = this.spliterator.trySplit();
/* 1656 */       if (possibleSplit == null) return null; 
/* 1657 */       return new ShortSpliteratorWrapper(possibleSplit);
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
/*      */   public static FloatSpliterator wrap(ShortSpliterator spliterator) {
/* 1674 */     return new ShortSpliteratorWrapper(spliterator);
/*      */   }
/*      */   
/*      */   private static final class CharSpliteratorWrapper
/*      */     implements FloatSpliterator {
/*      */     final CharSpliterator spliterator;
/*      */     
/*      */     public CharSpliteratorWrapper(CharSpliterator spliterator) {
/* 1682 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(FloatConsumer action) {
/* 1687 */       Objects.requireNonNull(action);
/* 1688 */       Objects.requireNonNull(action); return this.spliterator.tryAdvance(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1693 */       Objects.requireNonNull(action);
/* 1694 */       Objects.requireNonNull(action); this.spliterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1699 */       return this.spliterator.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1704 */       return this.spliterator.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1709 */       return this.spliterator.skip(n);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator trySplit() {
/* 1714 */       CharSpliterator possibleSplit = this.spliterator.trySplit();
/* 1715 */       if (possibleSplit == null) return null; 
/* 1716 */       return new CharSpliteratorWrapper(possibleSplit);
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
/*      */   public static FloatSpliterator wrap(CharSpliterator spliterator) {
/* 1739 */     return new CharSpliteratorWrapper(spliterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */