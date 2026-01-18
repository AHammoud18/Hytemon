/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterators;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.Stream;
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
/*     */ public final class CharSets
/*     */ {
/*     */   static final int ARRAY_SET_CUTOFF = 4;
/*     */   
/*     */   public static class EmptySet
/*     */     extends CharCollections.EmptyCollection
/*     */     implements CharSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public boolean remove(char ok) {
/*  52 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/*  57 */       return CharSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  63 */       return (o instanceof Set && ((Set)o).isEmpty());
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(char k) {
/*  69 */       return super.rem(k);
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  73 */       return CharSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */ 
/*     */   
/*  85 */   static final CharSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new CharArraySet(CharArrays.EMPTY_ARRAY));
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
/*     */   public static CharSet emptySet() {
/*  97 */     return EMPTY_SET;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends AbstractCharSet
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final char element;
/*     */ 
/*     */     
/*     */     protected Singleton(char element) {
/* 111 */       this.element = element;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(char k) {
/* 116 */       return (k == this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char k) {
/* 121 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharListIterator iterator() {
/* 126 */       return CharIterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSpliterator spliterator() {
/* 131 */       return CharSpliterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 136 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public char[] toCharArray() {
/* 141 */       return new char[] { this.element };
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void forEach(Consumer<? super Character> action) {
/* 147 */       action.accept(Character.valueOf(this.element));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Character> c) {
/* 152 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 157 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 162 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean removeIf(Predicate<? super Character> filter) {
/* 168 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(CharConsumer action) {
/* 173 */       action.accept(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(CharCollection c) {
/* 178 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeAll(CharCollection c) {
/* 183 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(CharCollection c) {
/* 188 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeIf(CharPredicate filter) {
/* 193 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntIterator intIterator() {
/* 198 */       return (IntIterator)IntIterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 203 */       return IntSpliterators.singleton(this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Object[] toArray() {
/* 209 */       return new Object[] { Character.valueOf(this.element) };
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 214 */       return this;
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
/*     */   public static CharSet singleton(char element) {
/* 226 */     return new Singleton(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSet singleton(Character element) {
/* 237 */     return new Singleton(element.charValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedSet
/*     */     extends CharCollections.SynchronizedCollection implements CharSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected SynchronizedSet(CharSet s, Object sync) {
/* 245 */       super(s, sync);
/*     */     }
/*     */     
/*     */     protected SynchronizedSet(CharSet s) {
/* 249 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char k) {
/* 254 */       synchronized (this.sync) {
/* 255 */         return this.collection.rem(k);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(char k) {
/* 262 */       return super.rem(k);
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
/*     */   public static CharSet synchronize(CharSet s) {
/* 274 */     return new SynchronizedSet(s);
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
/*     */   public static CharSet synchronize(CharSet s, Object sync) {
/* 287 */     return new SynchronizedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSet
/*     */     extends CharCollections.UnmodifiableCollection implements CharSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected UnmodifiableSet(CharSet s) {
/* 295 */       super(s);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char k) {
/* 300 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 305 */       if (o == this) return true; 
/* 306 */       return this.collection.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 311 */       return this.collection.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean rem(char k) {
/* 317 */       return super.rem(k);
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
/*     */   public static CharSet unmodifiable(CharSet s) {
/* 329 */     return new UnmodifiableSet(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSet fromTo(final char from, final char to) {
/* 340 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char x) {
/* 343 */           return (x >= from && x < to);
/*     */         }
/*     */ 
/*     */         
/*     */         public CharIterator iterator() {
/* 348 */           return CharIterators.fromTo(from, to);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 353 */           long size = to - from;
/* 354 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSet from(final char from) {
/* 367 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char x) {
/* 370 */           return (x >= from);
/*     */         }
/*     */ 
/*     */         
/*     */         public CharIterator iterator() {
/* 375 */           return CharIterators.concat(new CharIterator[] { CharIterators.fromTo(this.val$from, '￿'), 
/* 376 */                 CharSets.singleton('￿').iterator() });
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 381 */           long size = 65535L - from + 1L;
/* 382 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSet to(final char to) {
/* 394 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char x) {
/* 397 */           return (x < to);
/*     */         }
/*     */ 
/*     */         
/*     */         public CharIterator iterator() {
/* 402 */           return CharIterators.fromTo(false, to);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 407 */           long size = to - 0L;
/* 408 */           return (size >= 0L && size <= 2147483647L) ? (int)size : Integer.MAX_VALUE;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */