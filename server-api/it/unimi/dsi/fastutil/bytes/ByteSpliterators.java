/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ByteSpliterators
/*      */ {
/*      */   static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
/*      */   public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
/*      */   public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
/*      */   public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
/*      */   private static final int SORTED_CHARACTERISTICS = 20;
/*      */   public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
/*      */   
/*      */   public static class EmptySpliterator
/*      */     implements ByteSpliterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = 8379247926738230492L;
/*      */     private static final int CHARACTERISTICS = 16448;
/*      */     
/*      */     public boolean tryAdvance(ByteConsumer action) {
/*   61 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Byte> action) {
/*   67 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator trySplit() {
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
/*      */     public void forEachRemaining(ByteConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*   96 */       return ByteSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  100 */       return ByteSpliterators.EMPTY_SPLITERATOR;
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
/*      */     implements ByteSpliterator {
/*      */     private final byte element;
/*      */     private final ByteComparator comparator;
/*      */     private boolean consumed = false;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public SingletonSpliterator(byte element) {
/*  122 */       this(element, null);
/*      */     }
/*      */     
/*      */     public SingletonSpliterator(byte element, ByteComparator comparator) {
/*  126 */       this.element = element;
/*  127 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(ByteConsumer action) {
/*  132 */       Objects.requireNonNull(action);
/*  133 */       if (this.consumed) return false;
/*      */       
/*  135 */       this.consumed = true;
/*  136 */       action.accept(this.element);
/*  137 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator trySplit() {
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
/*      */     public void forEachRemaining(ByteConsumer action) {
/*  157 */       Objects.requireNonNull(action);
/*  158 */       if (!this.consumed) {
/*  159 */         this.consumed = true;
/*  160 */         action.accept(this.element);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteComparator getComparator() {
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
/*      */   public static ByteSpliterator singleton(byte element) {
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
/*      */   public static ByteSpliterator singleton(byte element, ByteComparator comparator) {
/*  200 */     return new SingletonSpliterator(element, comparator);
/*      */   }
/*      */   
/*      */   private static class ArraySpliterator implements ByteSpliterator {
/*      */     private static final int BASE_CHARACTERISTICS = 16720;
/*      */     final byte[] array;
/*      */     private final int offset;
/*      */     private int length;
/*      */     private int curr;
/*      */     final int characteristics;
/*      */     
/*      */     public ArraySpliterator(byte[] array, int offset, int length, int additionalCharacteristics) {
/*  212 */       this.array = array;
/*  213 */       this.offset = offset;
/*  214 */       this.length = length;
/*  215 */       this.characteristics = 0x4150 | additionalCharacteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(ByteConsumer action) {
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
/*      */     public ByteSpliterator trySplit() {
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
/*      */     public void forEachRemaining(ByteConsumer action) {
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
/*      */     private final ByteComparator comparator;
/*      */     
/*      */     public ArraySpliteratorWithComparator(byte[] array, int offset, int length, int additionalCharacteristics, ByteComparator comparator) {
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
/*      */     public ByteComparator getComparator() {
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
/*      */   public static ByteSpliterator wrap(byte[] array, int offset, int length) {
/*  314 */     ByteArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static ByteSpliterator wrap(byte[] array) {
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
/*      */   public static ByteSpliterator wrap(byte[] array, int offset, int length, int additionalCharacteristics) {
/*  360 */     ByteArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static ByteSpliterator wrapPreSorted(byte[] array, int offset, int length, int additionalCharacteristics, ByteComparator comparator) {
/*  392 */     ByteArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static ByteSpliterator wrapPreSorted(byte[] array, int offset, int length, ByteComparator comparator) {
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
/*      */   public static ByteSpliterator wrapPreSorted(byte[] array, ByteComparator comparator) {
/*  446 */     return wrapPreSorted(array, 0, array.length, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SpliteratorWrapper
/*      */     implements ByteSpliterator
/*      */   {
/*      */     final Spliterator<Byte> i;
/*      */ 
/*      */ 
/*      */     
/*      */     public SpliteratorWrapper(Spliterator<Byte> i) {
/*  459 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(ByteConsumer action) {
/*  464 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Byte> action) {
/*  470 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/*  475 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {
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
/*      */     public ByteComparator getComparator() {
/*  496 */       return ByteComparators.asByteComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator trySplit() {
/*  501 */       Spliterator<Byte> innerSplit = this.i.trySplit();
/*  502 */       if (innerSplit == null) return null; 
/*  503 */       return new SpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper {
/*      */     final ByteComparator comparator;
/*      */     
/*      */     public SpliteratorWrapperWithComparator(Spliterator<Byte> i, ByteComparator comparator) {
/*  511 */       super(i);
/*  512 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteComparator getComparator() {
/*  517 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator trySplit() {
/*  522 */       Spliterator<Byte> innerSplit = this.i.trySplit();
/*  523 */       if (innerSplit == null) return null; 
/*  524 */       return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapper implements ByteSpliterator {
/*      */     final Spliterator.OfInt i;
/*      */     
/*      */     public PrimitiveSpliteratorWrapper(Spliterator.OfInt i) {
/*  532 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(ByteConsumer action) {
/*  537 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
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
/*      */     public ByteComparator getComparator() {
/*  558 */       Comparator<? super Integer> comp = this.i.getComparator();
/*  559 */       return (left, right) -> comp.compare(Integer.valueOf(left), Integer.valueOf(right));
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator trySplit() {
/*  564 */       Spliterator.OfInt innerSplit = this.i.trySplit();
/*  565 */       if (innerSplit == null) return null; 
/*  566 */       return new PrimitiveSpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapperWithComparator extends PrimitiveSpliteratorWrapper {
/*      */     final ByteComparator comparator;
/*      */     
/*      */     public PrimitiveSpliteratorWrapperWithComparator(Spliterator.OfInt i, ByteComparator comparator) {
/*  574 */       super(i);
/*  575 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteComparator getComparator() {
/*  580 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator trySplit() {
/*  585 */       Spliterator.OfInt innerSplit = this.i.trySplit();
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
/*      */   public static ByteSpliterator asByteSpliterator(Spliterator<Byte> i) {
/*  608 */     if (i instanceof ByteSpliterator) return (ByteSpliterator)i; 
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
/*      */   public static ByteSpliterator asByteSpliterator(Spliterator<Byte> i, ByteComparator comparatorOverride) {
/*  640 */     if (i instanceof ByteSpliterator) throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + ByteSpliterator.class.getSimpleName()); 
/*  641 */     if (i instanceof Spliterator.OfInt) return new PrimitiveSpliteratorWrapperWithComparator((Spliterator.OfInt)i, comparatorOverride); 
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
/*      */   public static ByteSpliterator narrow(Spliterator.OfInt i) {
/*  655 */     return new PrimitiveSpliteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntSpliterator widen(ByteSpliterator i) {
/*  665 */     return IntSpliterators.wrap(i);
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
/*      */   public static void onEachMatching(ByteSpliterator spliterator, BytePredicate predicate, ByteConsumer action) {
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
/*      */   public static void onEachMatching(ByteSpliterator spliterator, IntPredicate predicate, IntConsumer action) {
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
/*      */     extends AbstractByteSpliterator
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
/*      */     public boolean tryAdvance(ByteConsumer action) {
/*  847 */       if (this.pos >= getMaxPos()) return false; 
/*  848 */       action.accept(get(this.pos++));
/*  849 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
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
/*      */     public ByteSpliterator trySplit() {
/*  895 */       int max = getMaxPos();
/*  896 */       int splitPoint = computeSplitPoint();
/*  897 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/*  898 */       splitPointCheck(splitPoint, max);
/*  899 */       int oldPos = this.pos;
/*  900 */       ByteSpliterator maybeSplit = makeForSplit(oldPos, splitPoint);
/*  901 */       if (maybeSplit != null) this.pos = splitPoint; 
/*  902 */       return maybeSplit;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract byte get(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract ByteSpliterator makeForSplit(int param1Int1, int param1Int2);
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
/*      */     public ByteSpliterator trySplit() {
/*  990 */       ByteSpliterator maybeSplit = super.trySplit();
/*  991 */       if (!this.maxPosFixed && maybeSplit != null) {
/*  992 */         this.maxPos = getMaxPosFromBackingStore();
/*  993 */         this.maxPosFixed = true;
/*      */       } 
/*  995 */       return maybeSplit;
/*      */     }
/*      */     
/*      */     protected abstract int getMaxPosFromBackingStore(); }
/*      */   
/*      */   private static class IntervalSpliterator implements ByteSpliterator {
/*      */     private static final int DONT_SPLIT_THRESHOLD = 2;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public IntervalSpliterator(byte from, byte to) {
/* 1005 */       this.curr = from;
/* 1006 */       this.to = to;
/*      */     }
/*      */     private byte curr;
/*      */     
/*      */     public boolean tryAdvance(ByteConsumer action) {
/* 1011 */       if (this.curr >= this.to) return false; 
/* 1012 */       this.curr = (byte)(this.curr + 1); action.accept(this.curr);
/* 1013 */       return true;
/*      */     }
/*      */     private byte to;
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/* 1018 */       Objects.requireNonNull(action);
/* 1019 */       for (; this.curr < this.to; this.curr = (byte)(this.curr + 1)) {
/* 1020 */         action.accept(this.curr);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1026 */       return (this.to - this.curr);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1031 */       return 17749;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteComparator getComparator() {
/* 1037 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator trySplit() {
/* 1042 */       int remaining = this.to - this.curr;
/* 1043 */       byte mid = (byte)(this.curr + (remaining >> 1));
/* 1044 */       if (remaining >= 0 && remaining <= 2) return null; 
/* 1045 */       byte old_curr = this.curr;
/* 1046 */       this.curr = mid;
/* 1047 */       return new IntervalSpliterator(old_curr, mid);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1052 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1053 */       if (this.curr >= this.to) return 0L;
/*      */ 
/*      */       
/* 1056 */       long newCurr = this.curr + n;
/*      */       
/* 1058 */       if (newCurr <= this.to && newCurr >= this.curr) {
/* 1059 */         this.curr = SafeMath.safeLongToByte(newCurr);
/* 1060 */         return n;
/*      */       } 
/* 1062 */       n = (this.to - this.curr);
/* 1063 */       this.curr = this.to;
/* 1064 */       return n;
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
/*      */   public static ByteSpliterator fromTo(byte from, byte to) {
/* 1080 */     return new IntervalSpliterator(from, to);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorConcatenator
/*      */     implements ByteSpliterator
/*      */   {
/*      */     private static final int EMPTY_CHARACTERISTICS = 16448;
/*      */     
/*      */     private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
/*      */     final ByteSpliterator[] a;
/*      */     int offset;
/*      */     int length;
/* 1093 */     long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
/* 1094 */     int characteristics = 0;
/*      */     
/*      */     public SpliteratorConcatenator(ByteSpliterator[] a, int offset, int length) {
/* 1097 */       this.a = a;
/* 1098 */       this.offset = offset;
/* 1099 */       this.length = length;
/* 1100 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1101 */       this.characteristics = computeCharacteristics();
/*      */     }
/*      */     
/*      */     private long recomputeRemaining() {
/* 1105 */       int curLength = this.length - 1;
/* 1106 */       int curOffset = this.offset + 1;
/* 1107 */       long result = 0L;
/* 1108 */       while (curLength > 0) {
/* 1109 */         long cur = this.a[curOffset++].estimateSize();
/* 1110 */         curLength--;
/* 1111 */         if (cur == Long.MAX_VALUE) return Long.MAX_VALUE; 
/* 1112 */         result += cur;
/*      */         
/* 1114 */         if (result == Long.MAX_VALUE || result < 0L) {
/* 1115 */           return Long.MAX_VALUE;
/*      */         }
/*      */       } 
/* 1118 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     private int computeCharacteristics() {
/* 1123 */       if (this.length <= 0) {
/* 1124 */         return 16448;
/*      */       }
/* 1126 */       int current = -1;
/* 1127 */       int curLength = this.length;
/* 1128 */       int curOffset = this.offset;
/* 1129 */       if (curLength > 1) {
/* 1130 */         current &= 0xFFFFFFFA;
/*      */       }
/* 1132 */       while (curLength > 0) {
/* 1133 */         current &= this.a[curOffset++].characteristics();
/* 1134 */         curLength--;
/*      */       } 
/* 1136 */       return current;
/*      */     }
/*      */     
/*      */     private void advanceNextSpliterator() {
/* 1140 */       if (this.length <= 0) {
/* 1141 */         throw new AssertionError("advanceNextSpliterator() called with none remaining");
/*      */       }
/* 1143 */       this.offset++;
/* 1144 */       this.length--;
/* 1145 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(ByteConsumer action) {
/* 1153 */       boolean any = false;
/* 1154 */       while (this.length > 0) {
/* 1155 */         if (this.a[this.offset].tryAdvance(action)) {
/* 1156 */           any = true;
/*      */           break;
/*      */         } 
/* 1159 */         advanceNextSpliterator();
/*      */       } 
/* 1161 */       return any;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/* 1166 */       while (this.length > 0) {
/* 1167 */         this.a[this.offset].forEachRemaining(action);
/* 1168 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {
/* 1175 */       while (this.length > 0) {
/* 1176 */         this.a[this.offset].forEachRemaining(action);
/* 1177 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1183 */       if (this.length <= 0) return 0L; 
/* 1184 */       long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
/* 1185 */       if (est < 0L)
/*      */       {
/* 1187 */         return Long.MAX_VALUE;
/*      */       }
/* 1189 */       return est;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1194 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteComparator getComparator() {
/* 1199 */       if (this.length == 1 && (this.characteristics & 0x4) != 0) {
/* 1200 */         return this.a[this.offset].getComparator();
/*      */       }
/* 1202 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteSpliterator trySplit() {
/*      */       ByteSpliterator split;
/* 1211 */       switch (this.length) {
/*      */         case 0:
/* 1213 */           return null;
/*      */         
/*      */         case 1:
/* 1216 */           split = this.a[this.offset].trySplit();
/*      */ 
/*      */           
/* 1219 */           this.characteristics = this.a[this.offset].characteristics();
/* 1220 */           return split;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1225 */           split = this.a[this.offset++];
/* 1226 */           this.length--;
/*      */ 
/*      */           
/* 1229 */           this.characteristics = this.a[this.offset].characteristics();
/* 1230 */           this.remainingEstimatedExceptCurrent = 0L;
/* 1231 */           return split;
/*      */       } 
/*      */ 
/*      */       
/* 1235 */       int mid = this.length >> 1;
/* 1236 */       int ret_offset = this.offset;
/* 1237 */       int new_offset = this.offset + mid;
/* 1238 */       int ret_length = mid;
/* 1239 */       int new_length = this.length - mid;
/* 1240 */       this.offset = new_offset;
/* 1241 */       this.length = new_length;
/* 1242 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1243 */       this.characteristics = computeCharacteristics();
/* 1244 */       return new SpliteratorConcatenator(this.a, ret_offset, ret_length);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1249 */       long skipped = 0L;
/* 1250 */       if (this.length <= 0) return 0L; 
/* 1251 */       while (skipped < n && this.length >= 0) {
/* 1252 */         long curSkipped = this.a[this.offset].skip(n - skipped);
/* 1253 */         skipped += curSkipped;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1260 */         if (skipped < n) advanceNextSpliterator(); 
/*      */       } 
/* 1262 */       return skipped;
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
/*      */   public static ByteSpliterator concat(ByteSpliterator... a) {
/* 1283 */     return concat(a, 0, a.length);
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
/*      */   public static ByteSpliterator concat(ByteSpliterator[] a, int offset, int length) {
/* 1307 */     return new SpliteratorConcatenator(a, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorFromIterator
/*      */     implements ByteSpliterator
/*      */   {
/*      */     private static final int BATCH_INCREMENT_SIZE = 1024;
/*      */     
/*      */     private static final int BATCH_MAX_SIZE = 33554432;
/*      */     
/*      */     private final ByteIterator iter;
/*      */     
/*      */     final int characteristics;
/*      */     private final boolean knownSize;
/* 1322 */     private long size = Long.MAX_VALUE;
/* 1323 */     private int nextBatchSize = 1024;
/*      */     
/* 1325 */     private ByteSpliterator delegate = null;
/*      */     
/*      */     SpliteratorFromIterator(ByteIterator iter, int characteristics) {
/* 1328 */       this.iter = iter;
/* 1329 */       this.characteristics = 0x100 | characteristics;
/* 1330 */       this.knownSize = false;
/*      */     }
/*      */     
/*      */     SpliteratorFromIterator(ByteIterator iter, long size, int additionalCharacteristics) {
/* 1334 */       this.iter = iter;
/* 1335 */       this.knownSize = true;
/* 1336 */       this.size = size;
/* 1337 */       if ((additionalCharacteristics & 0x1000) != 0) {
/* 1338 */         this.characteristics = 0x100 | additionalCharacteristics;
/*      */       } else {
/* 1340 */         this.characteristics = 0x4140 | additionalCharacteristics;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(ByteConsumer action) {
/* 1346 */       if (this.delegate != null) {
/* 1347 */         boolean hadRemaining = this.delegate.tryAdvance(action);
/* 1348 */         if (!hadRemaining) this.delegate = null; 
/* 1349 */         return hadRemaining;
/*      */       } 
/* 1351 */       if (!this.iter.hasNext()) return false; 
/* 1352 */       this.size--;
/* 1353 */       action.accept(this.iter.nextByte());
/* 1354 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/* 1359 */       if (this.delegate != null) {
/* 1360 */         this.delegate.forEachRemaining(action);
/* 1361 */         this.delegate = null;
/*      */       } 
/* 1363 */       this.iter.forEachRemaining(action);
/* 1364 */       this.size = 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1369 */       if (this.delegate != null) return this.delegate.estimateSize(); 
/* 1370 */       if (!this.iter.hasNext()) return 0L;
/*      */ 
/*      */       
/* 1373 */       return (this.knownSize && this.size >= 0L) ? this.size : Long.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1378 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected ByteSpliterator makeForSplit(byte[] batch, int len) {
/* 1382 */       return ByteSpliterators.wrap(batch, 0, len, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSpliterator trySplit() {
/* 1387 */       if (!this.iter.hasNext()) return null; 
/* 1388 */       int batchSizeEst = (this.knownSize && this.size > 0L) ? (int)Math.min(this.nextBatchSize, this.size) : this.nextBatchSize;
/*      */       
/* 1390 */       byte[] batch = new byte[batchSizeEst];
/* 1391 */       int actualSeen = 0;
/* 1392 */       while (actualSeen < batchSizeEst && this.iter.hasNext()) {
/* 1393 */         batch[actualSeen++] = this.iter.nextByte();
/* 1394 */         this.size--;
/*      */       } 
/*      */ 
/*      */       
/* 1398 */       if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
/* 1399 */         batch = Arrays.copyOf(batch, this.nextBatchSize);
/* 1400 */         while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
/* 1401 */           batch[actualSeen++] = this.iter.nextByte();
/* 1402 */           this.size--;
/*      */         } 
/*      */       } 
/* 1405 */       this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
/*      */       
/* 1407 */       ByteSpliterator split = makeForSplit(batch, actualSeen);
/* 1408 */       if (!this.iter.hasNext()) {
/* 1409 */         this.delegate = split;
/* 1410 */         return split.trySplit();
/*      */       } 
/* 1412 */       return split;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1418 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1419 */       if (this.iter instanceof ByteBigListIterator) {
/* 1420 */         long skipped = ((ByteBigListIterator)this.iter).skip(n);
/* 1421 */         this.size -= skipped;
/* 1422 */         return skipped;
/*      */       } 
/* 1424 */       long skippedSoFar = 0L;
/* 1425 */       while (skippedSoFar < n && this.iter.hasNext()) {
/* 1426 */         int skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
/* 1427 */         this.size -= skipped;
/* 1428 */         skippedSoFar += skipped;
/*      */       } 
/* 1430 */       return skippedSoFar;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorFromIteratorWithComparator
/*      */     extends SpliteratorFromIterator {
/*      */     private final ByteComparator comparator;
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(ByteIterator iter, int additionalCharacteristics, ByteComparator comparator) {
/* 1439 */       super(iter, additionalCharacteristics | 0x14);
/* 1440 */       this.comparator = comparator;
/*      */     }
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(ByteIterator iter, long size, int additionalCharacteristics, ByteComparator comparator) {
/* 1444 */       super(iter, size, additionalCharacteristics | 0x14);
/* 1445 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteComparator getComparator() {
/* 1450 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected ByteSpliterator makeForSplit(byte[] array, int len) {
/* 1455 */       return ByteSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
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
/*      */   public static ByteSpliterator asSpliterator(ByteIterator iter, long size, int additionalCharacterisitcs) {
/* 1483 */     return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
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
/*      */   public static ByteSpliterator asSpliteratorFromSorted(ByteIterator iter, long size, int additionalCharacterisitcs, ByteComparator comparator) {
/* 1515 */     return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
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
/*      */   public static ByteSpliterator asSpliteratorUnknownSize(ByteIterator iter, int characterisitcs) {
/* 1538 */     return new SpliteratorFromIterator(iter, characterisitcs);
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
/*      */   public static ByteSpliterator asSpliteratorFromSortedUnknownSize(ByteIterator iter, int additionalCharacterisitcs, ByteComparator comparator) {
/* 1567 */     return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
/*      */   }
/*      */   
/*      */   private static final class IteratorFromSpliterator implements ByteIterator, ByteConsumer {
/*      */     private final ByteSpliterator spliterator;
/* 1572 */     private byte holder = 0;
/*      */     
/*      */     private boolean hasPeeked = false;
/*      */     
/*      */     IteratorFromSpliterator(ByteSpliterator spliterator) {
/* 1577 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(byte item) {
/* 1582 */       this.holder = item;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1587 */       if (this.hasPeeked) return true; 
/* 1588 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1589 */       if (!hadElement) return false; 
/* 1590 */       this.hasPeeked = true;
/* 1591 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1596 */       if (this.hasPeeked) {
/* 1597 */         this.hasPeeked = false;
/* 1598 */         return this.holder;
/*      */       } 
/* 1600 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1601 */       if (!hadElement) throw new NoSuchElementException(); 
/* 1602 */       return this.holder;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/* 1607 */       if (this.hasPeeked) {
/* 1608 */         this.hasPeeked = false;
/* 1609 */         action.accept(this.holder);
/*      */       } 
/* 1611 */       this.spliterator.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1616 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1617 */       int skipped = 0;
/* 1618 */       if (this.hasPeeked) {
/* 1619 */         this.hasPeeked = false;
/* 1620 */         this.spliterator.skip(1L);
/* 1621 */         skipped++;
/* 1622 */         n--;
/*      */       } 
/* 1624 */       if (n > 0) {
/* 1625 */         skipped += SafeMath.safeLongToInt(this.spliterator.skip(n));
/*      */       }
/* 1627 */       return skipped;
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
/*      */   public static ByteIterator asIterator(ByteSpliterator spliterator) {
/* 1640 */     return new IteratorFromSpliterator(spliterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */