/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ObjectSpliterators
/*      */ {
/*      */   static final int BASE_SPLITERATOR_CHARACTERISTICS = 0;
/*      */   public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 64;
/*      */   public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16464;
/*      */   public static final int SET_SPLITERATOR_CHARACTERISTICS = 65;
/*      */   private static final int SORTED_CHARACTERISTICS = 20;
/*      */   public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 85;
/*      */   
/*      */   public static class EmptySpliterator<K>
/*      */     implements ObjectSpliterator<K>, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = 8379247926738230492L;
/*      */     private static final int CHARACTERISTICS = 16448;
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/*   60 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*   65 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*   70 */       return 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*   75 */       return 16448;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*   84 */       return ObjectSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*   88 */       return ObjectSpliterators.EMPTY_SPLITERATOR;
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
/*  100 */   public static final EmptySpliterator EMPTY_SPLITERATOR = new EmptySpliterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectSpliterator<K> emptySpliterator() {
/*  116 */     return EMPTY_SPLITERATOR;
/*      */   }
/*      */   
/*      */   private static class SingletonSpliterator<K>
/*      */     implements ObjectSpliterator<K> {
/*      */     private final K element;
/*      */     private final Comparator<? super K> comparator;
/*      */     private boolean consumed = false;
/*      */     private static final int CHARACTERISTICS = 17493;
/*      */     
/*      */     public SingletonSpliterator(K element) {
/*  127 */       this(element, null);
/*      */     }
/*      */     
/*      */     public SingletonSpliterator(K element, Comparator<? super K> comparator) {
/*  131 */       this.element = element;
/*  132 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/*  137 */       Objects.requireNonNull(action);
/*  138 */       if (this.consumed) return false;
/*      */       
/*  140 */       this.consumed = true;
/*  141 */       action.accept(this.element);
/*  142 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*  147 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  152 */       return this.consumed ? 0L : 1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  157 */       return 0x4455 | ((this.element != null) ? 256 : 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  162 */       Objects.requireNonNull(action);
/*  163 */       if (!this.consumed) {
/*  164 */         this.consumed = true;
/*  165 */         action.accept(this.element);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> getComparator() {
/*  171 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  176 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  177 */       if (n == 0L || this.consumed) return 0L; 
/*  178 */       this.consumed = true;
/*  179 */       return 1L;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectSpliterator<K> singleton(K element) {
/*  190 */     return new SingletonSpliterator<>(element);
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
/*      */   public static <K> ObjectSpliterator<K> singleton(K element, Comparator<? super K> comparator) {
/*  205 */     return new SingletonSpliterator<>(element, comparator);
/*      */   }
/*      */   
/*      */   private static class ArraySpliterator<K> implements ObjectSpliterator<K> {
/*      */     private static final int BASE_CHARACTERISTICS = 16464;
/*      */     final K[] array;
/*      */     private final int offset;
/*      */     private int length;
/*      */     private int curr;
/*      */     final int characteristics;
/*      */     
/*      */     public ArraySpliterator(K[] array, int offset, int length, int additionalCharacteristics) {
/*  217 */       this.array = array;
/*  218 */       this.offset = offset;
/*  219 */       this.length = length;
/*  220 */       this.characteristics = 0x4050 | additionalCharacteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/*  225 */       if (this.curr >= this.length) return false; 
/*  226 */       Objects.requireNonNull(action);
/*  227 */       action.accept(this.array[this.offset + this.curr++]);
/*  228 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  233 */       return (this.length - this.curr);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  238 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected ArraySpliterator<K> makeForSplit(int newOffset, int newLength) {
/*  242 */       return new ArraySpliterator(this.array, newOffset, newLength, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*  247 */       int retLength = this.length - this.curr >> 1;
/*  248 */       if (retLength <= 1) return null; 
/*  249 */       int myNewCurr = this.curr + retLength;
/*  250 */       int retOffset = this.offset + this.curr;
/*      */       
/*  252 */       this.curr = myNewCurr;
/*      */       
/*  254 */       return makeForSplit(retOffset, retLength);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  259 */       Objects.requireNonNull(action);
/*  260 */       for (; this.curr < this.length; this.curr++) {
/*  261 */         action.accept(this.array[this.offset + this.curr]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  267 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  268 */       if (this.curr >= this.length) return 0L; 
/*  269 */       int remaining = this.length - this.curr;
/*  270 */       if (n < remaining) {
/*  271 */         this.curr = SafeMath.safeLongToInt(this.curr + n);
/*  272 */         return n;
/*      */       } 
/*  274 */       n = remaining;
/*  275 */       this.curr = this.length;
/*  276 */       return n;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ArraySpliteratorWithComparator<K> extends ArraySpliterator<K> {
/*      */     private final Comparator<? super K> comparator;
/*      */     
/*      */     public ArraySpliteratorWithComparator(K[] array, int offset, int length, int additionalCharacteristics, Comparator<? super K> comparator) {
/*  284 */       super(array, offset, length, additionalCharacteristics | 0x14);
/*  285 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected ArraySpliteratorWithComparator<K> makeForSplit(int newOffset, int newLength) {
/*  290 */       return new ArraySpliteratorWithComparator(this.array, newOffset, newLength, this.characteristics, this.comparator);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> getComparator() {
/*  295 */       return this.comparator;
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
/*      */   public static <K> ObjectSpliterator<K> wrap(K[] array, int offset, int length) {
/*  319 */     ObjectArrays.ensureOffsetLength(array, offset, length);
/*  320 */     return new ArraySpliterator<>(array, offset, length, 0);
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
/*      */   public static <K> ObjectSpliterator<K> wrap(K[] array) {
/*  339 */     return new ArraySpliterator<>(array, 0, array.length, 0);
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
/*      */   public static <K> ObjectSpliterator<K> wrap(K[] array, int offset, int length, int additionalCharacteristics) {
/*  365 */     ObjectArrays.ensureOffsetLength(array, offset, length);
/*  366 */     return new ArraySpliterator<>(array, offset, length, additionalCharacteristics);
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
/*      */   public static <K> ObjectSpliterator<K> wrapPreSorted(K[] array, int offset, int length, int additionalCharacteristics, Comparator<? super K> comparator) {
/*  397 */     ObjectArrays.ensureOffsetLength(array, offset, length);
/*  398 */     return new ArraySpliteratorWithComparator<>(array, offset, length, additionalCharacteristics, comparator);
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
/*      */   public static <K> ObjectSpliterator<K> wrapPreSorted(K[] array, int offset, int length, Comparator<? super K> comparator) {
/*  426 */     return wrapPreSorted(array, offset, length, 0, comparator);
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
/*      */   public static <K> ObjectSpliterator<K> wrapPreSorted(K[] array, Comparator<? super K> comparator) {
/*  451 */     return wrapPreSorted(array, 0, array.length, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SpliteratorWrapper<K>
/*      */     implements ObjectSpliterator<K>
/*      */   {
/*      */     final Spliterator<K> i;
/*      */ 
/*      */ 
/*      */     
/*      */     public SpliteratorWrapper(Spliterator<K> i) {
/*  464 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/*  469 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  474 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  479 */       return this.i.estimateSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  484 */       return this.i.characteristics();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> getComparator() {
/*  489 */       return ObjectComparators.asObjectComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*  494 */       Spliterator<K> innerSplit = this.i.trySplit();
/*  495 */       if (innerSplit == null) return null; 
/*  496 */       return new SpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorWrapperWithComparator<K> extends SpliteratorWrapper<K> {
/*      */     final Comparator<? super K> comparator;
/*      */     
/*      */     public SpliteratorWrapperWithComparator(Spliterator<K> i, Comparator<? super K> comparator) {
/*  504 */       super(i);
/*  505 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> getComparator() {
/*  510 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*  515 */       Spliterator<K> innerSplit = this.i.trySplit();
/*  516 */       if (innerSplit == null) return null; 
/*  517 */       return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
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
/*      */   public static <K> ObjectSpliterator<K> asObjectSpliterator(Spliterator<K> i) {
/*  537 */     if (i instanceof ObjectSpliterator) return (ObjectSpliterator<K>)i; 
/*  538 */     return new SpliteratorWrapper<>(i);
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
/*      */   public static <K> ObjectSpliterator<K> asObjectSpliterator(Spliterator<K> i, Comparator<? super K> comparatorOverride) {
/*  568 */     if (i instanceof ObjectSpliterator) throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + ObjectSpliterator.class.getSimpleName()); 
/*  569 */     return new SpliteratorWrapperWithComparator<>(i, comparatorOverride);
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
/*      */   public static <K> void onEachMatching(Spliterator<K> spliterator, Predicate<? super K> predicate, Consumer<? super K> action) {
/*  582 */     Objects.requireNonNull(predicate);
/*  583 */     Objects.requireNonNull(action);
/*  584 */     spliterator.forEachRemaining(value -> {
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
/*      */   public static abstract class AbstractIndexBasedSpliterator<K>
/*      */     extends AbstractObjectSpliterator<K>
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
/*  622 */       this.pos = initialPos;
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
/*  706 */       return this.pos + (getMaxPos() - this.pos) / 2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void splitPointCheck(int splitPoint, int observedMax) {
/*  712 */       if (splitPoint < this.pos || splitPoint > observedMax) {
/*  713 */         throw new IndexOutOfBoundsException("splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  720 */       return 16464;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  725 */       return getMaxPos() - this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/*  730 */       if (this.pos >= getMaxPos()) return false; 
/*  731 */       action.accept(get(this.pos++));
/*  732 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  737 */       for (int max = getMaxPos(); this.pos < max; this.pos++) {
/*  738 */         action.accept(get(this.pos));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  746 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  747 */       int max = getMaxPos();
/*  748 */       if (this.pos >= max) return 0L; 
/*  749 */       int remaining = max - this.pos;
/*  750 */       if (n < remaining) {
/*  751 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/*  752 */         return n;
/*      */       } 
/*  754 */       n = remaining;
/*  755 */       this.pos = max;
/*  756 */       return n;
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
/*      */     public ObjectSpliterator<K> trySplit() {
/*  778 */       int max = getMaxPos();
/*  779 */       int splitPoint = computeSplitPoint();
/*  780 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/*  781 */       splitPointCheck(splitPoint, max);
/*  782 */       int oldPos = this.pos;
/*  783 */       ObjectSpliterator<K> maybeSplit = makeForSplit(oldPos, splitPoint);
/*  784 */       if (maybeSplit != null) this.pos = splitPoint; 
/*  785 */       return maybeSplit;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract K get(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract ObjectSpliterator<K> makeForSplit(int param1Int1, int param1Int2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class EarlyBindingSizeIndexBasedSpliterator<K>
/*      */     extends AbstractIndexBasedSpliterator<K>
/*      */   {
/*      */     protected final int maxPos;
/*      */ 
/*      */ 
/*      */     
/*      */     protected EarlyBindingSizeIndexBasedSpliterator(int initialPos, int maxPos) {
/*  811 */       super(initialPos);
/*  812 */       this.maxPos = maxPos;
/*      */     }
/*      */ 
/*      */     
/*      */     protected final int getMaxPos() {
/*  817 */       return this.maxPos;
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
/*      */   public static abstract class LateBindingSizeIndexBasedSpliterator<K>
/*      */     extends AbstractIndexBasedSpliterator<K>
/*      */   {
/*  842 */     protected int maxPos = -1;
/*      */     private boolean maxPosFixed;
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos) {
/*  846 */       super(initialPos);
/*  847 */       this.maxPosFixed = false;
/*      */     }
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos, int fixedMaxPos) {
/*  851 */       super(initialPos);
/*  852 */       this.maxPos = fixedMaxPos;
/*  853 */       this.maxPosFixed = true;
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
/*  868 */       return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*  873 */       ObjectSpliterator<K> maybeSplit = super.trySplit();
/*  874 */       if (!this.maxPosFixed && maybeSplit != null) {
/*  875 */         this.maxPos = getMaxPosFromBackingStore();
/*  876 */         this.maxPosFixed = true;
/*      */       } 
/*  878 */       return maybeSplit;
/*      */     }
/*      */     
/*      */     protected abstract int getMaxPosFromBackingStore();
/*      */   }
/*      */   
/*      */   private static class SpliteratorConcatenator<K>
/*      */     implements ObjectSpliterator<K>
/*      */   {
/*      */     private static final int EMPTY_CHARACTERISTICS = 16448;
/*      */     private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
/*      */     final ObjectSpliterator<? extends K>[] a;
/*      */     int offset;
/*      */     int length;
/*  892 */     long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
/*  893 */     int characteristics = 0;
/*      */     
/*      */     public SpliteratorConcatenator(ObjectSpliterator<? extends K>[] a, int offset, int length) {
/*  896 */       this.a = a;
/*  897 */       this.offset = offset;
/*  898 */       this.length = length;
/*  899 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*  900 */       this.characteristics = computeCharacteristics();
/*      */     }
/*      */     
/*      */     private long recomputeRemaining() {
/*  904 */       int curLength = this.length - 1;
/*  905 */       int curOffset = this.offset + 1;
/*  906 */       long result = 0L;
/*  907 */       while (curLength > 0) {
/*  908 */         long cur = this.a[curOffset++].estimateSize();
/*  909 */         curLength--;
/*  910 */         if (cur == Long.MAX_VALUE) return Long.MAX_VALUE; 
/*  911 */         result += cur;
/*      */         
/*  913 */         if (result == Long.MAX_VALUE || result < 0L) {
/*  914 */           return Long.MAX_VALUE;
/*      */         }
/*      */       } 
/*  917 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     private int computeCharacteristics() {
/*  922 */       if (this.length <= 0) {
/*  923 */         return 16448;
/*      */       }
/*  925 */       int current = -1;
/*  926 */       int curLength = this.length;
/*  927 */       int curOffset = this.offset;
/*  928 */       if (curLength > 1) {
/*  929 */         current &= 0xFFFFFFFA;
/*      */       }
/*  931 */       while (curLength > 0) {
/*  932 */         current &= this.a[curOffset++].characteristics();
/*  933 */         curLength--;
/*      */       } 
/*  935 */       return current;
/*      */     }
/*      */     
/*      */     private void advanceNextSpliterator() {
/*  939 */       if (this.length <= 0) {
/*  940 */         throw new AssertionError("advanceNextSpliterator() called with none remaining");
/*      */       }
/*  942 */       this.offset++;
/*  943 */       this.length--;
/*  944 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/*  952 */       boolean any = false;
/*  953 */       while (this.length > 0) {
/*  954 */         if (this.a[this.offset].tryAdvance(action)) {
/*  955 */           any = true;
/*      */           break;
/*      */         } 
/*  958 */         advanceNextSpliterator();
/*      */       } 
/*  960 */       return any;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  965 */       while (this.length > 0) {
/*  966 */         this.a[this.offset].forEachRemaining(action);
/*  967 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  973 */       if (this.length <= 0) return 0L; 
/*  974 */       long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
/*  975 */       if (est < 0L)
/*      */       {
/*  977 */         return Long.MAX_VALUE;
/*      */       }
/*  979 */       return est;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  984 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Comparator<? super K> getComparator() {
/*  990 */       if (this.length == 1 && (this.characteristics & 0x4) != 0) {
/*  991 */         return this.a[this.offset].getComparator();
/*      */       }
/*  993 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*      */       ObjectSpliterator<K> split;
/*      */       ObjectSpliterator<? extends K> objectSpliterator;
/* 1002 */       switch (this.length) {
/*      */         case 0:
/* 1004 */           return null;
/*      */ 
/*      */         
/*      */         case 1:
/* 1008 */           split = (ObjectSpliterator)this.a[this.offset].trySplit();
/*      */ 
/*      */           
/* 1011 */           this.characteristics = this.a[this.offset].characteristics();
/* 1012 */           return split;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1018 */           objectSpliterator = this.a[this.offset++];
/* 1019 */           this.length--;
/*      */ 
/*      */           
/* 1022 */           this.characteristics = this.a[this.offset].characteristics();
/* 1023 */           this.remainingEstimatedExceptCurrent = 0L;
/* 1024 */           return (ObjectSpliterator)objectSpliterator;
/*      */       } 
/*      */ 
/*      */       
/* 1028 */       int mid = this.length >> 1;
/* 1029 */       int ret_offset = this.offset;
/* 1030 */       int new_offset = this.offset + mid;
/* 1031 */       int ret_length = mid;
/* 1032 */       int new_length = this.length - mid;
/* 1033 */       this.offset = new_offset;
/* 1034 */       this.length = new_length;
/* 1035 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1036 */       this.characteristics = computeCharacteristics();
/* 1037 */       return new SpliteratorConcatenator(this.a, ret_offset, ret_length);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1042 */       long skipped = 0L;
/* 1043 */       if (this.length <= 0) return 0L; 
/* 1044 */       while (skipped < n && this.length >= 0) {
/* 1045 */         long curSkipped = this.a[this.offset].skip(n - skipped);
/* 1046 */         skipped += curSkipped;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1053 */         if (skipped < n) advanceNextSpliterator(); 
/*      */       } 
/* 1055 */       return skipped;
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
/*      */   @SafeVarargs
/*      */   public static <K> ObjectSpliterator<K> concat(ObjectSpliterator<? extends K>... a) {
/* 1077 */     return concat(a, 0, a.length);
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
/*      */   public static <K> ObjectSpliterator<K> concat(ObjectSpliterator<? extends K>[] a, int offset, int length) {
/* 1101 */     return new SpliteratorConcatenator<>(a, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorFromIterator<K>
/*      */     implements ObjectSpliterator<K>
/*      */   {
/*      */     private static final int BATCH_INCREMENT_SIZE = 1024;
/*      */     
/*      */     private static final int BATCH_MAX_SIZE = 33554432;
/*      */     
/*      */     private final ObjectIterator<? extends K> iter;
/*      */     
/*      */     final int characteristics;
/*      */     private final boolean knownSize;
/* 1116 */     private long size = Long.MAX_VALUE;
/* 1117 */     private int nextBatchSize = 1024;
/*      */     
/* 1119 */     private ObjectSpliterator<K> delegate = null;
/*      */     
/*      */     SpliteratorFromIterator(ObjectIterator<? extends K> iter, int characteristics) {
/* 1122 */       this.iter = iter;
/* 1123 */       this.characteristics = 0x0 | characteristics;
/* 1124 */       this.knownSize = false;
/*      */     }
/*      */     
/*      */     SpliteratorFromIterator(ObjectIterator<? extends K> iter, long size, int additionalCharacteristics) {
/* 1128 */       this.iter = iter;
/* 1129 */       this.knownSize = true;
/* 1130 */       this.size = size;
/* 1131 */       if ((additionalCharacteristics & 0x1000) != 0) {
/* 1132 */         this.characteristics = 0x0 | additionalCharacteristics;
/*      */       } else {
/* 1134 */         this.characteristics = 0x4040 | additionalCharacteristics;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/* 1140 */       if (this.delegate != null) {
/* 1141 */         boolean hadRemaining = this.delegate.tryAdvance(action);
/* 1142 */         if (!hadRemaining) this.delegate = null; 
/* 1143 */         return hadRemaining;
/*      */       } 
/* 1145 */       if (!this.iter.hasNext()) return false; 
/* 1146 */       this.size--;
/* 1147 */       action.accept(this.iter.next());
/* 1148 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/* 1153 */       if (this.delegate != null) {
/* 1154 */         this.delegate.forEachRemaining(action);
/* 1155 */         this.delegate = null;
/*      */       } 
/* 1157 */       this.iter.forEachRemaining(action);
/* 1158 */       this.size = 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1163 */       if (this.delegate != null) return this.delegate.estimateSize(); 
/* 1164 */       if (!this.iter.hasNext()) return 0L;
/*      */ 
/*      */       
/* 1167 */       return (this.knownSize && this.size >= 0L) ? this.size : Long.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1172 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected ObjectSpliterator<K> makeForSplit(K[] batch, int len) {
/* 1176 */       return ObjectSpliterators.wrap(batch, 0, len, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/* 1181 */       if (!this.iter.hasNext()) return null; 
/* 1182 */       int batchSizeEst = (this.knownSize && this.size > 0L) ? (int)Math.min(this.nextBatchSize, this.size) : this.nextBatchSize;
/*      */       
/* 1184 */       K[] batch = (K[])new Object[batchSizeEst];
/* 1185 */       int actualSeen = 0;
/* 1186 */       while (actualSeen < batchSizeEst && this.iter.hasNext()) {
/* 1187 */         batch[actualSeen++] = this.iter.next();
/* 1188 */         this.size--;
/*      */       } 
/*      */ 
/*      */       
/* 1192 */       if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
/* 1193 */         batch = Arrays.copyOf(batch, this.nextBatchSize);
/* 1194 */         while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
/* 1195 */           batch[actualSeen++] = this.iter.next();
/* 1196 */           this.size--;
/*      */         } 
/*      */       } 
/* 1199 */       this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
/*      */       
/* 1201 */       ObjectSpliterator<K> split = makeForSplit(batch, actualSeen);
/* 1202 */       if (!this.iter.hasNext()) {
/* 1203 */         this.delegate = split;
/* 1204 */         return split.trySplit();
/*      */       } 
/* 1206 */       return split;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1212 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1213 */       if (this.iter instanceof ObjectBigListIterator) {
/* 1214 */         long skipped = ((ObjectBigListIterator)this.iter).skip(n);
/* 1215 */         this.size -= skipped;
/* 1216 */         return skipped;
/*      */       } 
/* 1218 */       long skippedSoFar = 0L;
/* 1219 */       while (skippedSoFar < n && this.iter.hasNext()) {
/* 1220 */         int skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
/* 1221 */         this.size -= skipped;
/* 1222 */         skippedSoFar += skipped;
/*      */       } 
/* 1224 */       return skippedSoFar;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorFromIteratorWithComparator<K>
/*      */     extends SpliteratorFromIterator<K> {
/*      */     private final Comparator<? super K> comparator;
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(ObjectIterator<? extends K> iter, int additionalCharacteristics, Comparator<? super K> comparator) {
/* 1233 */       super(iter, additionalCharacteristics | 0x14);
/* 1234 */       this.comparator = comparator;
/*      */     }
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(ObjectIterator<? extends K> iter, long size, int additionalCharacteristics, Comparator<? super K> comparator) {
/* 1238 */       super(iter, size, additionalCharacteristics | 0x14);
/* 1239 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> getComparator() {
/* 1244 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected ObjectSpliterator<K> makeForSplit(K[] array, int len) {
/* 1249 */       return ObjectSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
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
/*      */   public static <K> ObjectSpliterator<K> asSpliterator(ObjectIterator<? extends K> iter, long size, int additionalCharacterisitcs) {
/* 1277 */     return new SpliteratorFromIterator<>(iter, size, additionalCharacterisitcs);
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
/*      */   public static <K> ObjectSpliterator<K> asSpliteratorFromSorted(ObjectIterator<? extends K> iter, long size, int additionalCharacterisitcs, Comparator<? super K> comparator) {
/* 1309 */     return new SpliteratorFromIteratorWithComparator<>(iter, size, additionalCharacterisitcs, comparator);
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
/*      */   public static <K> ObjectSpliterator<K> asSpliteratorUnknownSize(ObjectIterator<? extends K> iter, int characterisitcs) {
/* 1332 */     return new SpliteratorFromIterator<>(iter, characterisitcs);
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
/*      */   public static <K> ObjectSpliterator<K> asSpliteratorFromSortedUnknownSize(ObjectIterator<? extends K> iter, int additionalCharacterisitcs, Comparator<? super K> comparator) {
/* 1361 */     return new SpliteratorFromIteratorWithComparator<>(iter, additionalCharacterisitcs, comparator);
/*      */   }
/*      */   
/*      */   private static final class IteratorFromSpliterator<K> implements ObjectIterator<K>, Consumer<K> {
/*      */     private final ObjectSpliterator<? extends K> spliterator;
/* 1366 */     private K holder = null;
/*      */     
/*      */     private boolean hasPeeked = false;
/*      */     
/*      */     IteratorFromSpliterator(ObjectSpliterator<? extends K> spliterator) {
/* 1371 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(K item) {
/* 1376 */       this.holder = item;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1381 */       if (this.hasPeeked) return true; 
/* 1382 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1383 */       if (!hadElement) return false; 
/* 1384 */       this.hasPeeked = true;
/* 1385 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1390 */       if (this.hasPeeked) {
/* 1391 */         this.hasPeeked = false;
/* 1392 */         return this.holder;
/*      */       } 
/* 1394 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1395 */       if (!hadElement) throw new NoSuchElementException(); 
/* 1396 */       return this.holder;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/* 1401 */       if (this.hasPeeked) {
/* 1402 */         this.hasPeeked = false;
/* 1403 */         action.accept(this.holder);
/*      */       } 
/* 1405 */       this.spliterator.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1410 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1411 */       int skipped = 0;
/* 1412 */       if (this.hasPeeked) {
/* 1413 */         this.hasPeeked = false;
/* 1414 */         this.spliterator.skip(1L);
/* 1415 */         skipped++;
/* 1416 */         n--;
/*      */       } 
/* 1418 */       if (n > 0) {
/* 1419 */         skipped += SafeMath.safeLongToInt(this.spliterator.skip(n));
/*      */       }
/* 1421 */       return skipped;
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
/*      */   public static <K> ObjectIterator<K> asIterator(ObjectSpliterator<? extends K> spliterator) {
/* 1434 */     return new IteratorFromSpliterator<>(spliterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */