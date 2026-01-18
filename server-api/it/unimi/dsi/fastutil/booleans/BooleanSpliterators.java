/*      */ package it.unimi.dsi.fastutil.booleans;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BooleanSpliterators
/*      */ {
/*      */   static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
/*      */   public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
/*      */   public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
/*      */   public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
/*      */   private static final int SORTED_CHARACTERISTICS = 20;
/*      */   public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
/*      */   
/*      */   public static class EmptySpliterator
/*      */     implements BooleanSpliterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = 8379247926738230492L;
/*      */     private static final int CHARACTERISTICS = 16448;
/*      */     
/*      */     public boolean tryAdvance(BooleanConsumer action) {
/*   59 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Boolean> action) {
/*   65 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanSpliterator trySplit() {
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
/*      */     public void forEachRemaining(BooleanConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*   94 */       return BooleanSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*   98 */       return BooleanSpliterators.EMPTY_SPLITERATOR;
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
/*      */     implements BooleanSpliterator {
/*      */     private final boolean element;
/*      */     private final BooleanComparator comparator;
/*      */     private boolean consumed = false;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public SingletonSpliterator(boolean element) {
/*  120 */       this(element, null);
/*      */     }
/*      */     
/*      */     public SingletonSpliterator(boolean element, BooleanComparator comparator) {
/*  124 */       this.element = element;
/*  125 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(BooleanConsumer action) {
/*  130 */       Objects.requireNonNull(action);
/*  131 */       if (this.consumed) return false;
/*      */       
/*  133 */       this.consumed = true;
/*  134 */       action.accept(this.element);
/*  135 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanSpliterator trySplit() {
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
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  155 */       Objects.requireNonNull(action);
/*  156 */       if (!this.consumed) {
/*  157 */         this.consumed = true;
/*  158 */         action.accept(this.element);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanComparator getComparator() {
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
/*      */   public static BooleanSpliterator singleton(boolean element) {
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
/*      */   public static BooleanSpliterator singleton(boolean element, BooleanComparator comparator) {
/*  198 */     return new SingletonSpliterator(element, comparator);
/*      */   }
/*      */   
/*      */   private static class ArraySpliterator implements BooleanSpliterator {
/*      */     private static final int BASE_CHARACTERISTICS = 16720;
/*      */     final boolean[] array;
/*      */     private final int offset;
/*      */     private int length;
/*      */     private int curr;
/*      */     final int characteristics;
/*      */     
/*      */     public ArraySpliterator(boolean[] array, int offset, int length, int additionalCharacteristics) {
/*  210 */       this.array = array;
/*  211 */       this.offset = offset;
/*  212 */       this.length = length;
/*  213 */       this.characteristics = 0x4150 | additionalCharacteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(BooleanConsumer action) {
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
/*      */     public BooleanSpliterator trySplit() {
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
/*      */     public void forEachRemaining(BooleanConsumer action) {
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
/*      */     private final BooleanComparator comparator;
/*      */     
/*      */     public ArraySpliteratorWithComparator(boolean[] array, int offset, int length, int additionalCharacteristics, BooleanComparator comparator) {
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
/*      */     public BooleanComparator getComparator() {
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
/*      */   public static BooleanSpliterator wrap(boolean[] array, int offset, int length) {
/*  312 */     BooleanArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static BooleanSpliterator wrap(boolean[] array) {
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
/*      */   public static BooleanSpliterator wrap(boolean[] array, int offset, int length, int additionalCharacteristics) {
/*  358 */     BooleanArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static BooleanSpliterator wrapPreSorted(boolean[] array, int offset, int length, int additionalCharacteristics, BooleanComparator comparator) {
/*  390 */     BooleanArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static BooleanSpliterator wrapPreSorted(boolean[] array, int offset, int length, BooleanComparator comparator) {
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
/*      */   public static BooleanSpliterator wrapPreSorted(boolean[] array, BooleanComparator comparator) {
/*  444 */     return wrapPreSorted(array, 0, array.length, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SpliteratorWrapper
/*      */     implements BooleanSpliterator
/*      */   {
/*      */     final Spliterator<Boolean> i;
/*      */ 
/*      */ 
/*      */     
/*      */     public SpliteratorWrapper(Spliterator<Boolean> i) {
/*  457 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(BooleanConsumer action) {
/*  462 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Boolean> action) {
/*  468 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  473 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {
/*  479 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  484 */       return this.i.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  489 */       return this.i.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanComparator getComparator() {
/*  494 */       return BooleanComparators.asBooleanComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanSpliterator trySplit() {
/*  499 */       Spliterator<Boolean> innerSplit = this.i.trySplit();
/*  500 */       if (innerSplit == null) return null; 
/*  501 */       return new SpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper {
/*      */     final BooleanComparator comparator;
/*      */     
/*      */     public SpliteratorWrapperWithComparator(Spliterator<Boolean> i, BooleanComparator comparator) {
/*  509 */       super(i);
/*  510 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanComparator getComparator() {
/*  515 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanSpliterator trySplit() {
/*  520 */       Spliterator<Boolean> innerSplit = this.i.trySplit();
/*  521 */       if (innerSplit == null) return null; 
/*  522 */       return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
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
/*      */   public static BooleanSpliterator asBooleanSpliterator(Spliterator<Boolean> i) {
/*  543 */     if (i instanceof BooleanSpliterator) return (BooleanSpliterator)i; 
/*  544 */     return new SpliteratorWrapper(i);
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
/*      */   public static BooleanSpliterator asBooleanSpliterator(Spliterator<Boolean> i, BooleanComparator comparatorOverride) {
/*  575 */     if (i instanceof BooleanSpliterator) throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + BooleanSpliterator.class.getSimpleName()); 
/*  576 */     return new SpliteratorWrapperWithComparator(i, comparatorOverride);
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
/*      */   public static void onEachMatching(BooleanSpliterator spliterator, BooleanPredicate predicate, BooleanConsumer action) {
/*  589 */     Objects.requireNonNull(predicate);
/*  590 */     Objects.requireNonNull(action);
/*  591 */     spliterator.forEachRemaining(value -> {
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
/*      */     extends AbstractBooleanSpliterator
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
/*  629 */       this.pos = initialPos;
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
/*  713 */       return this.pos + (getMaxPos() - this.pos) / 2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void splitPointCheck(int splitPoint, int observedMax) {
/*  719 */       if (splitPoint < this.pos || splitPoint > observedMax) {
/*  720 */         throw new IndexOutOfBoundsException("splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  727 */       return 16720;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  732 */       return getMaxPos() - this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(BooleanConsumer action) {
/*  737 */       if (this.pos >= getMaxPos()) return false; 
/*  738 */       action.accept(get(this.pos++));
/*  739 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  744 */       for (int max = getMaxPos(); this.pos < max; this.pos++) {
/*  745 */         action.accept(get(this.pos));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  753 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  754 */       int max = getMaxPos();
/*  755 */       if (this.pos >= max) return 0L; 
/*  756 */       int remaining = max - this.pos;
/*  757 */       if (n < remaining) {
/*  758 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/*  759 */         return n;
/*      */       } 
/*  761 */       n = remaining;
/*  762 */       this.pos = max;
/*  763 */       return n;
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
/*      */     public BooleanSpliterator trySplit() {
/*  785 */       int max = getMaxPos();
/*  786 */       int splitPoint = computeSplitPoint();
/*  787 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/*  788 */       splitPointCheck(splitPoint, max);
/*  789 */       int oldPos = this.pos;
/*  790 */       BooleanSpliterator maybeSplit = makeForSplit(oldPos, splitPoint);
/*  791 */       if (maybeSplit != null) this.pos = splitPoint; 
/*  792 */       return maybeSplit;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract boolean get(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract BooleanSpliterator makeForSplit(int param1Int1, int param1Int2);
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
/*  818 */       super(initialPos);
/*  819 */       this.maxPos = maxPos;
/*      */     }
/*      */ 
/*      */     
/*      */     protected final int getMaxPos() {
/*  824 */       return this.maxPos;
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
/*  849 */     protected int maxPos = -1;
/*      */     private boolean maxPosFixed;
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos) {
/*  853 */       super(initialPos);
/*  854 */       this.maxPosFixed = false;
/*      */     }
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos, int fixedMaxPos) {
/*  858 */       super(initialPos);
/*  859 */       this.maxPos = fixedMaxPos;
/*  860 */       this.maxPosFixed = true;
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
/*  875 */       return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanSpliterator trySplit() {
/*  880 */       BooleanSpliterator maybeSplit = super.trySplit();
/*  881 */       if (!this.maxPosFixed && maybeSplit != null) {
/*  882 */         this.maxPos = getMaxPosFromBackingStore();
/*  883 */         this.maxPosFixed = true;
/*      */       } 
/*  885 */       return maybeSplit;
/*      */     }
/*      */     
/*      */     protected abstract int getMaxPosFromBackingStore();
/*      */   }
/*      */   
/*      */   private static class SpliteratorConcatenator
/*      */     implements BooleanSpliterator
/*      */   {
/*      */     private static final int EMPTY_CHARACTERISTICS = 16448;
/*      */     private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
/*      */     final BooleanSpliterator[] a;
/*      */     int offset;
/*      */     int length;
/*  899 */     long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
/*  900 */     int characteristics = 0;
/*      */     
/*      */     public SpliteratorConcatenator(BooleanSpliterator[] a, int offset, int length) {
/*  903 */       this.a = a;
/*  904 */       this.offset = offset;
/*  905 */       this.length = length;
/*  906 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*  907 */       this.characteristics = computeCharacteristics();
/*      */     }
/*      */     
/*      */     private long recomputeRemaining() {
/*  911 */       int curLength = this.length - 1;
/*  912 */       int curOffset = this.offset + 1;
/*  913 */       long result = 0L;
/*  914 */       while (curLength > 0) {
/*  915 */         long cur = this.a[curOffset++].estimateSize();
/*  916 */         curLength--;
/*  917 */         if (cur == Long.MAX_VALUE) return Long.MAX_VALUE; 
/*  918 */         result += cur;
/*      */         
/*  920 */         if (result == Long.MAX_VALUE || result < 0L) {
/*  921 */           return Long.MAX_VALUE;
/*      */         }
/*      */       } 
/*  924 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     private int computeCharacteristics() {
/*  929 */       if (this.length <= 0) {
/*  930 */         return 16448;
/*      */       }
/*  932 */       int current = -1;
/*  933 */       int curLength = this.length;
/*  934 */       int curOffset = this.offset;
/*  935 */       if (curLength > 1) {
/*  936 */         current &= 0xFFFFFFFA;
/*      */       }
/*  938 */       while (curLength > 0) {
/*  939 */         current &= this.a[curOffset++].characteristics();
/*  940 */         curLength--;
/*      */       } 
/*  942 */       return current;
/*      */     }
/*      */     
/*      */     private void advanceNextSpliterator() {
/*  946 */       if (this.length <= 0) {
/*  947 */         throw new AssertionError("advanceNextSpliterator() called with none remaining");
/*      */       }
/*  949 */       this.offset++;
/*  950 */       this.length--;
/*  951 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(BooleanConsumer action) {
/*  959 */       boolean any = false;
/*  960 */       while (this.length > 0) {
/*  961 */         if (this.a[this.offset].tryAdvance(action)) {
/*  962 */           any = true;
/*      */           break;
/*      */         } 
/*  965 */         advanceNextSpliterator();
/*      */       } 
/*  967 */       return any;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  972 */       while (this.length > 0) {
/*  973 */         this.a[this.offset].forEachRemaining(action);
/*  974 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {
/*  981 */       while (this.length > 0) {
/*  982 */         this.a[this.offset].forEachRemaining(action);
/*  983 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  989 */       if (this.length <= 0) return 0L; 
/*  990 */       long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
/*  991 */       if (est < 0L)
/*      */       {
/*  993 */         return Long.MAX_VALUE;
/*      */       }
/*  995 */       return est;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1000 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanComparator getComparator() {
/* 1005 */       if (this.length == 1 && (this.characteristics & 0x4) != 0) {
/* 1006 */         return this.a[this.offset].getComparator();
/*      */       }
/* 1008 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BooleanSpliterator trySplit() {
/*      */       BooleanSpliterator split;
/* 1017 */       switch (this.length) {
/*      */         case 0:
/* 1019 */           return null;
/*      */         
/*      */         case 1:
/* 1022 */           split = this.a[this.offset].trySplit();
/*      */ 
/*      */           
/* 1025 */           this.characteristics = this.a[this.offset].characteristics();
/* 1026 */           return split;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1031 */           split = this.a[this.offset++];
/* 1032 */           this.length--;
/*      */ 
/*      */           
/* 1035 */           this.characteristics = this.a[this.offset].characteristics();
/* 1036 */           this.remainingEstimatedExceptCurrent = 0L;
/* 1037 */           return split;
/*      */       } 
/*      */ 
/*      */       
/* 1041 */       int mid = this.length >> 1;
/* 1042 */       int ret_offset = this.offset;
/* 1043 */       int new_offset = this.offset + mid;
/* 1044 */       int ret_length = mid;
/* 1045 */       int new_length = this.length - mid;
/* 1046 */       this.offset = new_offset;
/* 1047 */       this.length = new_length;
/* 1048 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1049 */       this.characteristics = computeCharacteristics();
/* 1050 */       return new SpliteratorConcatenator(this.a, ret_offset, ret_length);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1055 */       long skipped = 0L;
/* 1056 */       if (this.length <= 0) return 0L; 
/* 1057 */       while (skipped < n && this.length >= 0) {
/* 1058 */         long curSkipped = this.a[this.offset].skip(n - skipped);
/* 1059 */         skipped += curSkipped;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1066 */         if (skipped < n) advanceNextSpliterator(); 
/*      */       } 
/* 1068 */       return skipped;
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
/*      */   public static BooleanSpliterator concat(BooleanSpliterator... a) {
/* 1089 */     return concat(a, 0, a.length);
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
/*      */   public static BooleanSpliterator concat(BooleanSpliterator[] a, int offset, int length) {
/* 1113 */     return new SpliteratorConcatenator(a, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorFromIterator
/*      */     implements BooleanSpliterator
/*      */   {
/*      */     private static final int BATCH_INCREMENT_SIZE = 1024;
/*      */     
/*      */     private static final int BATCH_MAX_SIZE = 33554432;
/*      */     
/*      */     private final BooleanIterator iter;
/*      */     
/*      */     final int characteristics;
/*      */     private final boolean knownSize;
/* 1128 */     private long size = Long.MAX_VALUE;
/* 1129 */     private int nextBatchSize = 1024;
/*      */     
/* 1131 */     private BooleanSpliterator delegate = null;
/*      */     
/*      */     SpliteratorFromIterator(BooleanIterator iter, int characteristics) {
/* 1134 */       this.iter = iter;
/* 1135 */       this.characteristics = 0x100 | characteristics;
/* 1136 */       this.knownSize = false;
/*      */     }
/*      */     
/*      */     SpliteratorFromIterator(BooleanIterator iter, long size, int additionalCharacteristics) {
/* 1140 */       this.iter = iter;
/* 1141 */       this.knownSize = true;
/* 1142 */       this.size = size;
/* 1143 */       if ((additionalCharacteristics & 0x1000) != 0) {
/* 1144 */         this.characteristics = 0x100 | additionalCharacteristics;
/*      */       } else {
/* 1146 */         this.characteristics = 0x4140 | additionalCharacteristics;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(BooleanConsumer action) {
/* 1152 */       if (this.delegate != null) {
/* 1153 */         boolean hadRemaining = this.delegate.tryAdvance(action);
/* 1154 */         if (!hadRemaining) this.delegate = null; 
/* 1155 */         return hadRemaining;
/*      */       } 
/* 1157 */       if (!this.iter.hasNext()) return false; 
/* 1158 */       this.size--;
/* 1159 */       action.accept(this.iter.nextBoolean());
/* 1160 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/* 1165 */       if (this.delegate != null) {
/* 1166 */         this.delegate.forEachRemaining(action);
/* 1167 */         this.delegate = null;
/*      */       } 
/* 1169 */       this.iter.forEachRemaining(action);
/* 1170 */       this.size = 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1175 */       if (this.delegate != null) return this.delegate.estimateSize(); 
/* 1176 */       if (!this.iter.hasNext()) return 0L;
/*      */ 
/*      */       
/* 1179 */       return (this.knownSize && this.size >= 0L) ? this.size : Long.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1184 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected BooleanSpliterator makeForSplit(boolean[] batch, int len) {
/* 1188 */       return BooleanSpliterators.wrap(batch, 0, len, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanSpliterator trySplit() {
/* 1193 */       if (!this.iter.hasNext()) return null; 
/* 1194 */       int batchSizeEst = (this.knownSize && this.size > 0L) ? (int)Math.min(this.nextBatchSize, this.size) : this.nextBatchSize;
/*      */       
/* 1196 */       boolean[] batch = new boolean[batchSizeEst];
/* 1197 */       int actualSeen = 0;
/* 1198 */       while (actualSeen < batchSizeEst && this.iter.hasNext()) {
/* 1199 */         batch[actualSeen++] = this.iter.nextBoolean();
/* 1200 */         this.size--;
/*      */       } 
/*      */ 
/*      */       
/* 1204 */       if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
/* 1205 */         batch = Arrays.copyOf(batch, this.nextBatchSize);
/* 1206 */         while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
/* 1207 */           batch[actualSeen++] = this.iter.nextBoolean();
/* 1208 */           this.size--;
/*      */         } 
/*      */       } 
/* 1211 */       this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
/*      */       
/* 1213 */       BooleanSpliterator split = makeForSplit(batch, actualSeen);
/* 1214 */       if (!this.iter.hasNext()) {
/* 1215 */         this.delegate = split;
/* 1216 */         return split.trySplit();
/*      */       } 
/* 1218 */       return split;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1224 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1225 */       if (this.iter instanceof BooleanBigListIterator) {
/* 1226 */         long skipped = ((BooleanBigListIterator)this.iter).skip(n);
/* 1227 */         this.size -= skipped;
/* 1228 */         return skipped;
/*      */       } 
/* 1230 */       long skippedSoFar = 0L;
/* 1231 */       while (skippedSoFar < n && this.iter.hasNext()) {
/* 1232 */         int skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
/* 1233 */         this.size -= skipped;
/* 1234 */         skippedSoFar += skipped;
/*      */       } 
/* 1236 */       return skippedSoFar;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorFromIteratorWithComparator
/*      */     extends SpliteratorFromIterator {
/*      */     private final BooleanComparator comparator;
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(BooleanIterator iter, int additionalCharacteristics, BooleanComparator comparator) {
/* 1245 */       super(iter, additionalCharacteristics | 0x14);
/* 1246 */       this.comparator = comparator;
/*      */     }
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(BooleanIterator iter, long size, int additionalCharacteristics, BooleanComparator comparator) {
/* 1250 */       super(iter, size, additionalCharacteristics | 0x14);
/* 1251 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanComparator getComparator() {
/* 1256 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected BooleanSpliterator makeForSplit(boolean[] array, int len) {
/* 1261 */       return BooleanSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
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
/*      */   public static BooleanSpliterator asSpliterator(BooleanIterator iter, long size, int additionalCharacterisitcs) {
/* 1289 */     return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
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
/*      */   public static BooleanSpliterator asSpliteratorFromSorted(BooleanIterator iter, long size, int additionalCharacterisitcs, BooleanComparator comparator) {
/* 1321 */     return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
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
/*      */   public static BooleanSpliterator asSpliteratorUnknownSize(BooleanIterator iter, int characterisitcs) {
/* 1344 */     return new SpliteratorFromIterator(iter, characterisitcs);
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
/*      */   public static BooleanSpliterator asSpliteratorFromSortedUnknownSize(BooleanIterator iter, int additionalCharacterisitcs, BooleanComparator comparator) {
/* 1373 */     return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
/*      */   }
/*      */   
/*      */   private static final class IteratorFromSpliterator
/*      */     implements BooleanIterator, BooleanConsumer {
/*      */     private final BooleanSpliterator spliterator;
/*      */     private boolean holder = false;
/*      */     private boolean hasPeeked = false;
/*      */     
/*      */     IteratorFromSpliterator(BooleanSpliterator spliterator) {
/* 1383 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(boolean item) {
/* 1388 */       this.holder = item;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1393 */       if (this.hasPeeked) return true; 
/* 1394 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1395 */       if (!hadElement) return false; 
/* 1396 */       this.hasPeeked = true;
/* 1397 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1402 */       if (this.hasPeeked) {
/* 1403 */         this.hasPeeked = false;
/* 1404 */         return this.holder;
/*      */       } 
/* 1406 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1407 */       if (!hadElement) throw new NoSuchElementException(); 
/* 1408 */       return this.holder;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/* 1413 */       if (this.hasPeeked) {
/* 1414 */         this.hasPeeked = false;
/* 1415 */         action.accept(this.holder);
/*      */       } 
/* 1417 */       this.spliterator.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1422 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1423 */       int skipped = 0;
/* 1424 */       if (this.hasPeeked) {
/* 1425 */         this.hasPeeked = false;
/* 1426 */         this.spliterator.skip(1L);
/* 1427 */         skipped++;
/* 1428 */         n--;
/*      */       } 
/* 1430 */       if (n > 0) {
/* 1431 */         skipped += SafeMath.safeLongToInt(this.spliterator.skip(n));
/*      */       }
/* 1433 */       return skipped;
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
/*      */   public static BooleanIterator asIterator(BooleanSpliterator spliterator) {
/* 1446 */     return new IteratorFromSpliterator(spliterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */