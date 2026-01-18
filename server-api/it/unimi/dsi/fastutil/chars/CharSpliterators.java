/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ public final class CharSpliterators
/*      */ {
/*      */   static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
/*      */   public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
/*      */   public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
/*      */   public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
/*      */   private static final int SORTED_CHARACTERISTICS = 20;
/*      */   public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
/*      */   
/*      */   public static class EmptySpliterator
/*      */     implements CharSpliterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = 8379247926738230492L;
/*      */     private static final int CHARACTERISTICS = 16448;
/*      */     
/*      */     public boolean tryAdvance(CharConsumer action) {
/*   61 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Character> action) {
/*   67 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
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
/*      */     public void forEachRemaining(CharConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*   96 */       return CharSpliterators.EMPTY_SPLITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  100 */       return CharSpliterators.EMPTY_SPLITERATOR;
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
/*      */     implements CharSpliterator {
/*      */     private final char element;
/*      */     private final CharComparator comparator;
/*      */     private boolean consumed = false;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public SingletonSpliterator(char element) {
/*  122 */       this(element, null);
/*      */     }
/*      */     
/*      */     public SingletonSpliterator(char element, CharComparator comparator) {
/*  126 */       this.element = element;
/*  127 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(CharConsumer action) {
/*  132 */       Objects.requireNonNull(action);
/*  133 */       if (this.consumed) return false;
/*      */       
/*  135 */       this.consumed = true;
/*  136 */       action.accept(this.element);
/*  137 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
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
/*      */     public void forEachRemaining(CharConsumer action) {
/*  157 */       Objects.requireNonNull(action);
/*  158 */       if (!this.consumed) {
/*  159 */         this.consumed = true;
/*  160 */         action.accept(this.element);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public CharComparator getComparator() {
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
/*      */   public static CharSpliterator singleton(char element) {
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
/*      */   public static CharSpliterator singleton(char element, CharComparator comparator) {
/*  200 */     return new SingletonSpliterator(element, comparator);
/*      */   }
/*      */   
/*      */   private static class ArraySpliterator implements CharSpliterator {
/*      */     private static final int BASE_CHARACTERISTICS = 16720;
/*      */     final char[] array;
/*      */     private final int offset;
/*      */     private int length;
/*      */     private int curr;
/*      */     final int characteristics;
/*      */     
/*      */     public ArraySpliterator(char[] array, int offset, int length, int additionalCharacteristics) {
/*  212 */       this.array = array;
/*  213 */       this.offset = offset;
/*  214 */       this.length = length;
/*  215 */       this.characteristics = 0x4150 | additionalCharacteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(CharConsumer action) {
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
/*      */     public CharSpliterator trySplit() {
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
/*      */     public void forEachRemaining(CharConsumer action) {
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
/*      */     private final CharComparator comparator;
/*      */     
/*      */     public ArraySpliteratorWithComparator(char[] array, int offset, int length, int additionalCharacteristics, CharComparator comparator) {
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
/*      */     public CharComparator getComparator() {
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
/*      */   public static CharSpliterator wrap(char[] array, int offset, int length) {
/*  314 */     CharArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static CharSpliterator wrap(char[] array) {
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
/*      */   public static CharSpliterator wrap(char[] array, int offset, int length, int additionalCharacteristics) {
/*  360 */     CharArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static CharSpliterator wrapPreSorted(char[] array, int offset, int length, int additionalCharacteristics, CharComparator comparator) {
/*  392 */     CharArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static CharSpliterator wrapPreSorted(char[] array, int offset, int length, CharComparator comparator) {
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
/*      */   public static CharSpliterator wrapPreSorted(char[] array, CharComparator comparator) {
/*  446 */     return wrapPreSorted(array, 0, array.length, comparator);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SpliteratorWrapper
/*      */     implements CharSpliterator
/*      */   {
/*      */     final Spliterator<Character> i;
/*      */ 
/*      */ 
/*      */     
/*      */     public SpliteratorWrapper(Spliterator<Character> i) {
/*  459 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(CharConsumer action) {
/*  464 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean tryAdvance(Consumer<? super Character> action) {
/*  470 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/*  475 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {
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
/*      */     public CharComparator getComparator() {
/*  496 */       return CharComparators.asCharComparator(this.i.getComparator());
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
/*  501 */       Spliterator<Character> innerSplit = this.i.trySplit();
/*  502 */       if (innerSplit == null) return null; 
/*  503 */       return new SpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper {
/*      */     final CharComparator comparator;
/*      */     
/*      */     public SpliteratorWrapperWithComparator(Spliterator<Character> i, CharComparator comparator) {
/*  511 */       super(i);
/*  512 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharComparator getComparator() {
/*  517 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
/*  522 */       Spliterator<Character> innerSplit = this.i.trySplit();
/*  523 */       if (innerSplit == null) return null; 
/*  524 */       return new SpliteratorWrapperWithComparator(innerSplit, this.comparator);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapper implements CharSpliterator {
/*      */     final Spliterator.OfInt i;
/*      */     
/*      */     public PrimitiveSpliteratorWrapper(Spliterator.OfInt i) {
/*  532 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(CharConsumer action) {
/*  537 */       return this.i.tryAdvance(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
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
/*      */     public CharComparator getComparator() {
/*  558 */       Comparator<? super Integer> comp = this.i.getComparator();
/*  559 */       return (left, right) -> comp.compare(Integer.valueOf(left), Integer.valueOf(right));
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
/*  564 */       Spliterator.OfInt innerSplit = this.i.trySplit();
/*  565 */       if (innerSplit == null) return null; 
/*  566 */       return new PrimitiveSpliteratorWrapper(innerSplit);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveSpliteratorWrapperWithComparator extends PrimitiveSpliteratorWrapper {
/*      */     final CharComparator comparator;
/*      */     
/*      */     public PrimitiveSpliteratorWrapperWithComparator(Spliterator.OfInt i, CharComparator comparator) {
/*  574 */       super(i);
/*  575 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharComparator getComparator() {
/*  580 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
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
/*      */   public static CharSpliterator asCharSpliterator(Spliterator<Character> i) {
/*  608 */     if (i instanceof CharSpliterator) return (CharSpliterator)i; 
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
/*      */   public static CharSpliterator asCharSpliterator(Spliterator<Character> i, CharComparator comparatorOverride) {
/*  640 */     if (i instanceof CharSpliterator) throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + CharSpliterator.class.getSimpleName()); 
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
/*      */   public static CharSpliterator narrow(Spliterator.OfInt i) {
/*  655 */     return new PrimitiveSpliteratorWrapper(i);
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
/*      */   public static IntSpliterator widen(CharSpliterator i) {
/*  671 */     return IntSpliterators.wrap(i);
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
/*      */   public static void onEachMatching(CharSpliterator spliterator, CharPredicate predicate, CharConsumer action) {
/*  684 */     Objects.requireNonNull(predicate);
/*  685 */     Objects.requireNonNull(action);
/*  686 */     spliterator.forEachRemaining(value -> {
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
/*      */   public static void onEachMatching(CharSpliterator spliterator, IntPredicate predicate, IntConsumer action) {
/*  703 */     Objects.requireNonNull(predicate);
/*  704 */     Objects.requireNonNull(action);
/*      */ 
/*      */     
/*  707 */     spliterator.forEachRemaining(value -> {
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
/*      */     extends AbstractCharSpliterator
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
/*  745 */       this.pos = initialPos;
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
/*  829 */       return this.pos + (getMaxPos() - this.pos) / 2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void splitPointCheck(int splitPoint, int observedMax) {
/*  835 */       if (splitPoint < this.pos || splitPoint > observedMax) {
/*  836 */         throw new IndexOutOfBoundsException("splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  843 */       return 16720;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  848 */       return getMaxPos() - this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(CharConsumer action) {
/*  853 */       if (this.pos >= getMaxPos()) return false; 
/*  854 */       action.accept(get(this.pos++));
/*  855 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/*  860 */       for (int max = getMaxPos(); this.pos < max; this.pos++) {
/*  861 */         action.accept(get(this.pos));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  869 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  870 */       int max = getMaxPos();
/*  871 */       if (this.pos >= max) return 0L; 
/*  872 */       int remaining = max - this.pos;
/*  873 */       if (n < remaining) {
/*  874 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/*  875 */         return n;
/*      */       } 
/*  877 */       n = remaining;
/*  878 */       this.pos = max;
/*  879 */       return n;
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
/*      */     public CharSpliterator trySplit() {
/*  901 */       int max = getMaxPos();
/*  902 */       int splitPoint = computeSplitPoint();
/*  903 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/*  904 */       splitPointCheck(splitPoint, max);
/*  905 */       int oldPos = this.pos;
/*  906 */       CharSpliterator maybeSplit = makeForSplit(oldPos, splitPoint);
/*  907 */       if (maybeSplit != null) this.pos = splitPoint; 
/*  908 */       return maybeSplit;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract char get(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract CharSpliterator makeForSplit(int param1Int1, int param1Int2);
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
/*  934 */       super(initialPos);
/*  935 */       this.maxPos = maxPos;
/*      */     }
/*      */ 
/*      */     
/*      */     protected final int getMaxPos() {
/*  940 */       return this.maxPos;
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
/*  965 */     protected int maxPos = -1;
/*      */     private boolean maxPosFixed;
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos) {
/*  969 */       super(initialPos);
/*  970 */       this.maxPosFixed = false;
/*      */     }
/*      */     
/*      */     protected LateBindingSizeIndexBasedSpliterator(int initialPos, int fixedMaxPos) {
/*  974 */       super(initialPos);
/*  975 */       this.maxPos = fixedMaxPos;
/*  976 */       this.maxPosFixed = true;
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
/*  991 */       return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
/*  996 */       CharSpliterator maybeSplit = super.trySplit();
/*  997 */       if (!this.maxPosFixed && maybeSplit != null) {
/*  998 */         this.maxPos = getMaxPosFromBackingStore();
/*  999 */         this.maxPosFixed = true;
/*      */       } 
/* 1001 */       return maybeSplit;
/*      */     }
/*      */     
/*      */     protected abstract int getMaxPosFromBackingStore(); }
/*      */   
/*      */   private static class IntervalSpliterator implements CharSpliterator {
/*      */     private static final int DONT_SPLIT_THRESHOLD = 2;
/*      */     private static final int CHARACTERISTICS = 17749;
/*      */     
/*      */     public IntervalSpliterator(char from, char to) {
/* 1011 */       this.curr = from;
/* 1012 */       this.to = to;
/*      */     }
/*      */     private char curr;
/*      */     
/*      */     public boolean tryAdvance(CharConsumer action) {
/* 1017 */       if (this.curr >= this.to) return false; 
/* 1018 */       this.curr = (char)(this.curr + 1); action.accept(this.curr);
/* 1019 */       return true;
/*      */     }
/*      */     private char to;
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1024 */       Objects.requireNonNull(action);
/* 1025 */       for (; this.curr < this.to; this.curr = (char)(this.curr + 1)) {
/* 1026 */         action.accept(this.curr);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1032 */       return (this.to - this.curr);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1037 */       return 17749;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public CharComparator getComparator() {
/* 1043 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
/* 1048 */       int remaining = this.to - this.curr;
/* 1049 */       char mid = (char)(this.curr + (remaining >> 1));
/* 1050 */       if (remaining >= 0 && remaining <= 2) return null; 
/* 1051 */       char old_curr = this.curr;
/* 1052 */       this.curr = mid;
/* 1053 */       return new IntervalSpliterator(old_curr, mid);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1058 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1059 */       if (this.curr >= this.to) return 0L;
/*      */ 
/*      */       
/* 1062 */       long newCurr = this.curr + n;
/*      */       
/* 1064 */       if (newCurr <= this.to && newCurr >= this.curr) {
/* 1065 */         this.curr = SafeMath.safeLongToChar(newCurr);
/* 1066 */         return n;
/*      */       } 
/* 1068 */       n = (this.to - this.curr);
/* 1069 */       this.curr = this.to;
/* 1070 */       return n;
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
/*      */   public static CharSpliterator fromTo(char from, char to) {
/* 1086 */     return new IntervalSpliterator(from, to);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorConcatenator
/*      */     implements CharSpliterator
/*      */   {
/*      */     private static final int EMPTY_CHARACTERISTICS = 16448;
/*      */     
/*      */     private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
/*      */     final CharSpliterator[] a;
/*      */     int offset;
/*      */     int length;
/* 1099 */     long remainingEstimatedExceptCurrent = Long.MAX_VALUE;
/* 1100 */     int characteristics = 0;
/*      */     
/*      */     public SpliteratorConcatenator(CharSpliterator[] a, int offset, int length) {
/* 1103 */       this.a = a;
/* 1104 */       this.offset = offset;
/* 1105 */       this.length = length;
/* 1106 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1107 */       this.characteristics = computeCharacteristics();
/*      */     }
/*      */     
/*      */     private long recomputeRemaining() {
/* 1111 */       int curLength = this.length - 1;
/* 1112 */       int curOffset = this.offset + 1;
/* 1113 */       long result = 0L;
/* 1114 */       while (curLength > 0) {
/* 1115 */         long cur = this.a[curOffset++].estimateSize();
/* 1116 */         curLength--;
/* 1117 */         if (cur == Long.MAX_VALUE) return Long.MAX_VALUE; 
/* 1118 */         result += cur;
/*      */         
/* 1120 */         if (result == Long.MAX_VALUE || result < 0L) {
/* 1121 */           return Long.MAX_VALUE;
/*      */         }
/*      */       } 
/* 1124 */       return result;
/*      */     }
/*      */ 
/*      */     
/*      */     private int computeCharacteristics() {
/* 1129 */       if (this.length <= 0) {
/* 1130 */         return 16448;
/*      */       }
/* 1132 */       int current = -1;
/* 1133 */       int curLength = this.length;
/* 1134 */       int curOffset = this.offset;
/* 1135 */       if (curLength > 1) {
/* 1136 */         current &= 0xFFFFFFFA;
/*      */       }
/* 1138 */       while (curLength > 0) {
/* 1139 */         current &= this.a[curOffset++].characteristics();
/* 1140 */         curLength--;
/*      */       } 
/* 1142 */       return current;
/*      */     }
/*      */     
/*      */     private void advanceNextSpliterator() {
/* 1146 */       if (this.length <= 0) {
/* 1147 */         throw new AssertionError("advanceNextSpliterator() called with none remaining");
/*      */       }
/* 1149 */       this.offset++;
/* 1150 */       this.length--;
/* 1151 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(CharConsumer action) {
/* 1159 */       boolean any = false;
/* 1160 */       while (this.length > 0) {
/* 1161 */         if (this.a[this.offset].tryAdvance(action)) {
/* 1162 */           any = true;
/*      */           break;
/*      */         } 
/* 1165 */         advanceNextSpliterator();
/*      */       } 
/* 1167 */       return any;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1172 */       while (this.length > 0) {
/* 1173 */         this.a[this.offset].forEachRemaining(action);
/* 1174 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {
/* 1181 */       while (this.length > 0) {
/* 1182 */         this.a[this.offset].forEachRemaining(action);
/* 1183 */         advanceNextSpliterator();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1189 */       if (this.length <= 0) return 0L; 
/* 1190 */       long est = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
/* 1191 */       if (est < 0L)
/*      */       {
/* 1193 */         return Long.MAX_VALUE;
/*      */       }
/* 1195 */       return est;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1200 */       return this.characteristics;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharComparator getComparator() {
/* 1205 */       if (this.length == 1 && (this.characteristics & 0x4) != 0) {
/* 1206 */         return this.a[this.offset].getComparator();
/*      */       }
/* 1208 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
/*      */       CharSpliterator split;
/* 1217 */       switch (this.length) {
/*      */         case 0:
/* 1219 */           return null;
/*      */         
/*      */         case 1:
/* 1222 */           split = this.a[this.offset].trySplit();
/*      */ 
/*      */           
/* 1225 */           this.characteristics = this.a[this.offset].characteristics();
/* 1226 */           return split;
/*      */ 
/*      */ 
/*      */         
/*      */         case 2:
/* 1231 */           split = this.a[this.offset++];
/* 1232 */           this.length--;
/*      */ 
/*      */           
/* 1235 */           this.characteristics = this.a[this.offset].characteristics();
/* 1236 */           this.remainingEstimatedExceptCurrent = 0L;
/* 1237 */           return split;
/*      */       } 
/*      */ 
/*      */       
/* 1241 */       int mid = this.length >> 1;
/* 1242 */       int ret_offset = this.offset;
/* 1243 */       int new_offset = this.offset + mid;
/* 1244 */       int ret_length = mid;
/* 1245 */       int new_length = this.length - mid;
/* 1246 */       this.offset = new_offset;
/* 1247 */       this.length = new_length;
/* 1248 */       this.remainingEstimatedExceptCurrent = recomputeRemaining();
/* 1249 */       this.characteristics = computeCharacteristics();
/* 1250 */       return new SpliteratorConcatenator(this.a, ret_offset, ret_length);
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1255 */       long skipped = 0L;
/* 1256 */       if (this.length <= 0) return 0L; 
/* 1257 */       while (skipped < n && this.length >= 0) {
/* 1258 */         long curSkipped = this.a[this.offset].skip(n - skipped);
/* 1259 */         skipped += curSkipped;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1266 */         if (skipped < n) advanceNextSpliterator(); 
/*      */       } 
/* 1268 */       return skipped;
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
/*      */   public static CharSpliterator concat(CharSpliterator... a) {
/* 1289 */     return concat(a, 0, a.length);
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
/*      */   public static CharSpliterator concat(CharSpliterator[] a, int offset, int length) {
/* 1313 */     return new SpliteratorConcatenator(a, offset, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SpliteratorFromIterator
/*      */     implements CharSpliterator
/*      */   {
/*      */     private static final int BATCH_INCREMENT_SIZE = 1024;
/*      */     
/*      */     private static final int BATCH_MAX_SIZE = 33554432;
/*      */     
/*      */     private final CharIterator iter;
/*      */     
/*      */     final int characteristics;
/*      */     private final boolean knownSize;
/* 1328 */     private long size = Long.MAX_VALUE;
/* 1329 */     private int nextBatchSize = 1024;
/*      */     
/* 1331 */     private CharSpliterator delegate = null;
/*      */     
/*      */     SpliteratorFromIterator(CharIterator iter, int characteristics) {
/* 1334 */       this.iter = iter;
/* 1335 */       this.characteristics = 0x100 | characteristics;
/* 1336 */       this.knownSize = false;
/*      */     }
/*      */     
/*      */     SpliteratorFromIterator(CharIterator iter, long size, int additionalCharacteristics) {
/* 1340 */       this.iter = iter;
/* 1341 */       this.knownSize = true;
/* 1342 */       this.size = size;
/* 1343 */       if ((additionalCharacteristics & 0x1000) != 0) {
/* 1344 */         this.characteristics = 0x100 | additionalCharacteristics;
/*      */       } else {
/* 1346 */         this.characteristics = 0x4140 | additionalCharacteristics;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(CharConsumer action) {
/* 1352 */       if (this.delegate != null) {
/* 1353 */         boolean hadRemaining = this.delegate.tryAdvance(action);
/* 1354 */         if (!hadRemaining) this.delegate = null; 
/* 1355 */         return hadRemaining;
/*      */       } 
/* 1357 */       if (!this.iter.hasNext()) return false; 
/* 1358 */       this.size--;
/* 1359 */       action.accept(this.iter.nextChar());
/* 1360 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1365 */       if (this.delegate != null) {
/* 1366 */         this.delegate.forEachRemaining(action);
/* 1367 */         this.delegate = null;
/*      */       } 
/* 1369 */       this.iter.forEachRemaining(action);
/* 1370 */       this.size = 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/* 1375 */       if (this.delegate != null) return this.delegate.estimateSize(); 
/* 1376 */       if (!this.iter.hasNext()) return 0L;
/*      */ 
/*      */       
/* 1379 */       return (this.knownSize && this.size >= 0L) ? this.size : Long.MAX_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1384 */       return this.characteristics;
/*      */     }
/*      */     
/*      */     protected CharSpliterator makeForSplit(char[] batch, int len) {
/* 1388 */       return CharSpliterators.wrap(batch, 0, len, this.characteristics);
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSpliterator trySplit() {
/* 1393 */       if (!this.iter.hasNext()) return null; 
/* 1394 */       int batchSizeEst = (this.knownSize && this.size > 0L) ? (int)Math.min(this.nextBatchSize, this.size) : this.nextBatchSize;
/*      */       
/* 1396 */       char[] batch = new char[batchSizeEst];
/* 1397 */       int actualSeen = 0;
/* 1398 */       while (actualSeen < batchSizeEst && this.iter.hasNext()) {
/* 1399 */         batch[actualSeen++] = this.iter.nextChar();
/* 1400 */         this.size--;
/*      */       } 
/*      */ 
/*      */       
/* 1404 */       if (batchSizeEst < this.nextBatchSize && this.iter.hasNext()) {
/* 1405 */         batch = Arrays.copyOf(batch, this.nextBatchSize);
/* 1406 */         while (this.iter.hasNext() && actualSeen < this.nextBatchSize) {
/* 1407 */           batch[actualSeen++] = this.iter.nextChar();
/* 1408 */           this.size--;
/*      */         } 
/*      */       } 
/* 1411 */       this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
/*      */       
/* 1413 */       CharSpliterator split = makeForSplit(batch, actualSeen);
/* 1414 */       if (!this.iter.hasNext()) {
/* 1415 */         this.delegate = split;
/* 1416 */         return split.trySplit();
/*      */       } 
/* 1418 */       return split;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 1424 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1425 */       if (this.iter instanceof CharBigListIterator) {
/* 1426 */         long skipped = ((CharBigListIterator)this.iter).skip(n);
/* 1427 */         this.size -= skipped;
/* 1428 */         return skipped;
/*      */       } 
/* 1430 */       long skippedSoFar = 0L;
/* 1431 */       while (skippedSoFar < n && this.iter.hasNext()) {
/* 1432 */         int skipped = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
/* 1433 */         this.size -= skipped;
/* 1434 */         skippedSoFar += skipped;
/*      */       } 
/* 1436 */       return skippedSoFar;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SpliteratorFromIteratorWithComparator
/*      */     extends SpliteratorFromIterator {
/*      */     private final CharComparator comparator;
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(CharIterator iter, int additionalCharacteristics, CharComparator comparator) {
/* 1445 */       super(iter, additionalCharacteristics | 0x14);
/* 1446 */       this.comparator = comparator;
/*      */     }
/*      */     
/*      */     SpliteratorFromIteratorWithComparator(CharIterator iter, long size, int additionalCharacteristics, CharComparator comparator) {
/* 1450 */       super(iter, size, additionalCharacteristics | 0x14);
/* 1451 */       this.comparator = comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharComparator getComparator() {
/* 1456 */       return this.comparator;
/*      */     }
/*      */ 
/*      */     
/*      */     protected CharSpliterator makeForSplit(char[] array, int len) {
/* 1461 */       return CharSpliterators.wrapPreSorted(array, 0, len, this.characteristics, this.comparator);
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
/*      */   public static CharSpliterator asSpliterator(CharIterator iter, long size, int additionalCharacterisitcs) {
/* 1489 */     return new SpliteratorFromIterator(iter, size, additionalCharacterisitcs);
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
/*      */   public static CharSpliterator asSpliteratorFromSorted(CharIterator iter, long size, int additionalCharacterisitcs, CharComparator comparator) {
/* 1521 */     return new SpliteratorFromIteratorWithComparator(iter, size, additionalCharacterisitcs, comparator);
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
/*      */   public static CharSpliterator asSpliteratorUnknownSize(CharIterator iter, int characterisitcs) {
/* 1544 */     return new SpliteratorFromIterator(iter, characterisitcs);
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
/*      */   public static CharSpliterator asSpliteratorFromSortedUnknownSize(CharIterator iter, int additionalCharacterisitcs, CharComparator comparator) {
/* 1573 */     return new SpliteratorFromIteratorWithComparator(iter, additionalCharacterisitcs, comparator);
/*      */   }
/*      */   
/*      */   private static final class IteratorFromSpliterator implements CharIterator, CharConsumer {
/*      */     private final CharSpliterator spliterator;
/* 1578 */     private char holder = Character.MIN_VALUE;
/*      */     
/*      */     private boolean hasPeeked = false;
/*      */     
/*      */     IteratorFromSpliterator(CharSpliterator spliterator) {
/* 1583 */       this.spliterator = spliterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public void accept(char item) {
/* 1588 */       this.holder = item;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1593 */       if (this.hasPeeked) return true; 
/* 1594 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1595 */       if (!hadElement) return false; 
/* 1596 */       this.hasPeeked = true;
/* 1597 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1602 */       if (this.hasPeeked) {
/* 1603 */         this.hasPeeked = false;
/* 1604 */         return this.holder;
/*      */       } 
/* 1606 */       boolean hadElement = this.spliterator.tryAdvance(this);
/* 1607 */       if (!hadElement) throw new NoSuchElementException(); 
/* 1608 */       return this.holder;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1613 */       if (this.hasPeeked) {
/* 1614 */         this.hasPeeked = false;
/* 1615 */         action.accept(this.holder);
/*      */       } 
/* 1617 */       this.spliterator.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1622 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1623 */       int skipped = 0;
/* 1624 */       if (this.hasPeeked) {
/* 1625 */         this.hasPeeked = false;
/* 1626 */         this.spliterator.skip(1L);
/* 1627 */         skipped++;
/* 1628 */         n--;
/*      */       } 
/* 1630 */       if (n > 0) {
/* 1631 */         skipped += SafeMath.safeLongToInt(this.spliterator.skip(n));
/*      */       }
/* 1633 */       return skipped;
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
/*      */   public static CharIterator asIterator(CharSpliterator spliterator) {
/* 1646 */     return new IteratorFromSpliterator(spliterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */