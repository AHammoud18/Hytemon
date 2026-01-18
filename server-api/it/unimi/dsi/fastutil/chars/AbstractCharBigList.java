/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.Spliterator;
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
/*     */ public abstract class AbstractCharBigList
/*     */   extends AbstractCharCollection
/*     */   implements CharBigList, CharStack
/*     */ {
/*     */   protected void ensureIndex(long index) {
/*  51 */     if (index < 0L) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  52 */     if (index > size64()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size64() + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void ensureRestrictedIndex(long index) {
/*  63 */     if (index < 0L) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  64 */     if (index >= size64()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + size64() + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(long index, char k) {
/*  74 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(char k) {
/*  85 */     add(size64(), k);
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char removeChar(long i) {
/*  96 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char set(long index, char k) {
/* 106 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(long index, Collection<? extends Character> c) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   11: astore #4
/*     */     //   13: aload #4
/*     */     //   15: invokeinterface hasNext : ()Z
/*     */     //   20: istore #5
/*     */     //   22: aload #4
/*     */     //   24: invokeinterface hasNext : ()Z
/*     */     //   29: ifeq -> 54
/*     */     //   32: aload_0
/*     */     //   33: lload_1
/*     */     //   34: dup2
/*     */     //   35: lconst_1
/*     */     //   36: ladd
/*     */     //   37: lstore_1
/*     */     //   38: aload #4
/*     */     //   40: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   45: checkcast java/lang/Character
/*     */     //   48: invokevirtual add : (JLjava/lang/Character;)V
/*     */     //   51: goto -> 22
/*     */     //   54: iload #5
/*     */     //   56: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #112	-> 0
/*     */     //   #113	-> 5
/*     */     //   #114	-> 13
/*     */     //   #115	-> 22
/*     */     //   #116	-> 54
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	57	0	this	Lit/unimi/dsi/fastutil/chars/AbstractCharBigList;
/*     */     //   0	57	1	index	J
/*     */     //   0	57	3	c	Ljava/util/Collection;
/*     */     //   13	44	4	i	Ljava/util/Iterator;
/*     */     //   22	35	5	retVal	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	57	3	c	Ljava/util/Collection<+Ljava/lang/Character;>;
/*     */     //   13	44	4	i	Ljava/util/Iterator<+Ljava/lang/Character;>;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Character> c) {
/* 127 */     return addAll(size64(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBigListIterator iterator() {
/* 137 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBigListIterator listIterator() {
/* 147 */     return listIterator(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBigListIterator listIterator(long index) {
/* 157 */     ensureIndex(index);
/* 158 */     return new CharBigListIterators.AbstractIndexBasedBigListIterator(0L, index)
/*     */       {
/*     */         protected final char get(long i) {
/* 161 */           return AbstractCharBigList.this.getChar(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(long i, char k) {
/* 166 */           AbstractCharBigList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(long i, char k) {
/* 171 */           AbstractCharBigList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(long i) {
/* 176 */           AbstractCharBigList.this.removeChar(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final long getMaxPos() {
/* 181 */           return AbstractCharBigList.this.size64();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator extends CharBigSpliterators.LateBindingSizeIndexBasedSpliterator {
/*     */     final CharBigList l;
/*     */     
/*     */     IndexBasedSpliterator(CharBigList l, long pos) {
/* 190 */       super(pos);
/* 191 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(CharBigList l, long pos, long maxPos) {
/* 195 */       super(pos, maxPos);
/* 196 */       this.l = l;
/*     */     }
/*     */ 
/*     */     
/*     */     protected final long getMaxPosFromBackingStore() {
/* 201 */       return this.l.size64();
/*     */     }
/*     */ 
/*     */     
/*     */     protected final char get(long i) {
/* 206 */       return this.l.getChar(i);
/*     */     }
/*     */ 
/*     */     
/*     */     protected final IndexBasedSpliterator makeForSplit(long pos, long maxPos) {
/* 211 */       return new IndexBasedSpliterator(this.l, pos, maxPos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IntSpliterator intSpliterator() {
/* 217 */     if (this instanceof RandomAccess) {
/* 218 */       return CharSpliterators.widen(spliterator());
/*     */     }
/* 220 */     return super.intSpliterator();
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
/*     */   public boolean contains(char k) {
/* 232 */     return (indexOf(k) >= 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long indexOf(char k) {
/* 237 */     CharBigListIterator i = listIterator();
/*     */     
/* 239 */     while (i.hasNext()) {
/* 240 */       char e = i.nextChar();
/* 241 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 243 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long lastIndexOf(char k) {
/* 248 */     CharBigListIterator i = listIterator(size64());
/*     */     
/* 250 */     while (i.hasPrevious()) {
/* 251 */       char e = i.previousChar();
/* 252 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 254 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(long size) {
/* 259 */     long i = size64();
/* 260 */     if (size > i) { for (; i++ < size; add(false)); }
/* 261 */     else { for (; i-- != size; remove(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public CharBigList subList(long from, long to) {
/* 266 */     ensureIndex(from);
/* 267 */     ensureIndex(to);
/* 268 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 269 */     return (this instanceof RandomAccess) ? new CharRandomAccessSubList(this, from, to) : new CharSubList(this, from, to);
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
/*     */   public void forEach(CharConsumer action) {
/* 281 */     if (this instanceof RandomAccess) {
/* 282 */       for (long i = 0L, max = size64(); i < max; i++) {
/* 283 */         action.accept(getChar(i));
/*     */       }
/*     */     } else {
/* 286 */       super.forEach(action);
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
/*     */   public void removeElements(long from, long to) {
/* 299 */     ensureIndex(to);
/* 300 */     CharBigListIterator i = listIterator(from);
/* 301 */     long n = to - from;
/* 302 */     if (n < 0L) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 303 */     while (n-- != 0L) {
/* 304 */       i.nextChar();
/* 305 */       i.remove();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(long index, char[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[CJJ)V
/*     */     //   13: aload_0
/*     */     //   14: instanceof java/util/RandomAccess
/*     */     //   17: ifeq -> 55
/*     */     //   20: lload #6
/*     */     //   22: dup2
/*     */     //   23: lconst_1
/*     */     //   24: lsub
/*     */     //   25: lstore #6
/*     */     //   27: lconst_0
/*     */     //   28: lcmp
/*     */     //   29: ifeq -> 95
/*     */     //   32: aload_0
/*     */     //   33: lload_1
/*     */     //   34: dup2
/*     */     //   35: lconst_1
/*     */     //   36: ladd
/*     */     //   37: lstore_1
/*     */     //   38: aload_3
/*     */     //   39: lload #4
/*     */     //   41: dup2
/*     */     //   42: lconst_1
/*     */     //   43: ladd
/*     */     //   44: lstore #4
/*     */     //   46: invokestatic get : ([[CJ)C
/*     */     //   49: invokevirtual add : (JC)V
/*     */     //   52: goto -> 20
/*     */     //   55: aload_0
/*     */     //   56: lload_1
/*     */     //   57: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/chars/CharBigListIterator;
/*     */     //   60: astore #8
/*     */     //   62: lload #6
/*     */     //   64: dup2
/*     */     //   65: lconst_1
/*     */     //   66: lsub
/*     */     //   67: lstore #6
/*     */     //   69: lconst_0
/*     */     //   70: lcmp
/*     */     //   71: ifeq -> 95
/*     */     //   74: aload #8
/*     */     //   76: aload_3
/*     */     //   77: lload #4
/*     */     //   79: dup2
/*     */     //   80: lconst_1
/*     */     //   81: ladd
/*     */     //   82: lstore #4
/*     */     //   84: invokestatic get : ([[CJ)C
/*     */     //   87: invokeinterface add : (C)V
/*     */     //   92: goto -> 62
/*     */     //   95: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #318	-> 0
/*     */     //   #319	-> 5
/*     */     //   #320	-> 13
/*     */     //   #321	-> 20
/*     */     //   #323	-> 55
/*     */     //   #324	-> 62
/*     */     //   #326	-> 95
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   62	33	8	iter	Lit/unimi/dsi/fastutil/chars/CharBigListIterator;
/*     */     //   0	96	0	this	Lit/unimi/dsi/fastutil/chars/AbstractCharBigList;
/*     */     //   0	96	1	index	J
/*     */     //   0	96	3	a	[[C
/*     */     //   0	96	4	offset	J
/*     */     //   0	96	6	length	J
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
/*     */   
/*     */   public void addElements(long index, char[][] a) {
/* 335 */     addElements(index, a, 0L, BigArrays.length(a));
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
/*     */   public void getElements(long from, char[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[CJJ)V
/*     */     //   13: lload_1
/*     */     //   14: lload #6
/*     */     //   16: ladd
/*     */     //   17: aload_0
/*     */     //   18: invokevirtual size64 : ()J
/*     */     //   21: lcmp
/*     */     //   22: ifle -> 72
/*     */     //   25: new java/lang/IndexOutOfBoundsException
/*     */     //   28: dup
/*     */     //   29: new java/lang/StringBuilder
/*     */     //   32: dup
/*     */     //   33: invokespecial <init> : ()V
/*     */     //   36: ldc 'End index ('
/*     */     //   38: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   41: lload_1
/*     */     //   42: lload #6
/*     */     //   44: ladd
/*     */     //   45: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   48: ldc ') is greater than list size ('
/*     */     //   50: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   53: aload_0
/*     */     //   54: invokevirtual size64 : ()J
/*     */     //   57: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*     */     //   60: ldc ')'
/*     */     //   62: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   65: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   68: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   71: athrow
/*     */     //   72: aload_0
/*     */     //   73: instanceof java/util/RandomAccess
/*     */     //   76: ifeq -> 122
/*     */     //   79: lload_1
/*     */     //   80: lstore #8
/*     */     //   82: lload #6
/*     */     //   84: dup2
/*     */     //   85: lconst_1
/*     */     //   86: lsub
/*     */     //   87: lstore #6
/*     */     //   89: lconst_0
/*     */     //   90: lcmp
/*     */     //   91: ifeq -> 119
/*     */     //   94: aload_3
/*     */     //   95: lload #4
/*     */     //   97: dup2
/*     */     //   98: lconst_1
/*     */     //   99: ladd
/*     */     //   100: lstore #4
/*     */     //   102: aload_0
/*     */     //   103: lload #8
/*     */     //   105: dup2
/*     */     //   106: lconst_1
/*     */     //   107: ladd
/*     */     //   108: lstore #8
/*     */     //   110: invokevirtual getChar : (J)C
/*     */     //   113: invokestatic set : ([[CJC)V
/*     */     //   116: goto -> 82
/*     */     //   119: goto -> 162
/*     */     //   122: aload_0
/*     */     //   123: lload_1
/*     */     //   124: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/chars/CharBigListIterator;
/*     */     //   127: astore #8
/*     */     //   129: lload #6
/*     */     //   131: dup2
/*     */     //   132: lconst_1
/*     */     //   133: lsub
/*     */     //   134: lstore #6
/*     */     //   136: lconst_0
/*     */     //   137: lcmp
/*     */     //   138: ifeq -> 162
/*     */     //   141: aload_3
/*     */     //   142: lload #4
/*     */     //   144: dup2
/*     */     //   145: lconst_1
/*     */     //   146: ladd
/*     */     //   147: lstore #4
/*     */     //   149: aload #8
/*     */     //   151: invokeinterface nextChar : ()C
/*     */     //   156: invokestatic set : ([[CJC)V
/*     */     //   159: goto -> 129
/*     */     //   162: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #347	-> 0
/*     */     //   #348	-> 5
/*     */     //   #349	-> 13
/*     */     //   #350	-> 72
/*     */     //   #351	-> 79
/*     */     //   #352	-> 82
/*     */     //   #353	-> 119
/*     */     //   #354	-> 122
/*     */     //   #355	-> 129
/*     */     //   #357	-> 162
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   82	37	8	current	J
/*     */     //   129	33	8	i	Lit/unimi/dsi/fastutil/chars/CharBigListIterator;
/*     */     //   0	163	0	this	Lit/unimi/dsi/fastutil/chars/AbstractCharBigList;
/*     */     //   0	163	1	from	J
/*     */     //   0	163	3	a	[[C
/*     */     //   0	163	4	offset	J
/*     */     //   0	163	6	length	J
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
/*     */   public void setElements(long index, char[][] a, long offset, long length) {
/* 361 */     ensureIndex(index);
/* 362 */     BigArrays.ensureOffsetLength(a, offset, length);
/* 363 */     if (index + length > size64()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size64() + ")"); 
/* 364 */     if (this instanceof RandomAccess) {
/* 365 */       long i; for (i = 0L; i < length; i++) {
/* 366 */         set(i + index, BigArrays.get(a, i + offset));
/*     */       }
/*     */     } else {
/* 369 */       CharBigListIterator iter = listIterator(index);
/* 370 */       long i = 0L;
/* 371 */       if (i < length) {
/* 372 */         iter.nextChar();
/* 373 */         iter.set(BigArrays.get(a, offset + i++));
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 385 */     removeElements(0L, size64());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 397 */     return (int)Math.min(2147483647L, size64());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 407 */     CharIterator i = iterator();
/* 408 */     int h = 1;
/* 409 */     long s = size64();
/* 410 */     while (s-- != 0L) {
/* 411 */       char k = i.nextChar();
/* 412 */       h = 31 * h + k;
/*     */     } 
/* 414 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 419 */     if (o == this) return true; 
/* 420 */     if (!(o instanceof BigList)) return false; 
/* 421 */     BigList<?> l = (BigList)o;
/* 422 */     long s = size64();
/* 423 */     if (s != l.size64()) return false; 
/* 424 */     if (l instanceof CharBigList) {
/* 425 */       CharBigListIterator charBigListIterator1 = listIterator(), charBigListIterator2 = ((CharBigList)l).listIterator();
/* 426 */       while (s-- != 0L) { if (charBigListIterator1.nextChar() != charBigListIterator2.nextChar()) return false;  }
/* 427 */        return true;
/*     */     } 
/* 429 */     BigListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 430 */     while (s-- != 0L) { if (!Objects.equals(i1.next(), i2.next())) return false;  }
/* 431 */      return true;
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
/*     */ 
/*     */   
/*     */   public int compareTo(BigList<? extends Character> l) {
/* 446 */     if (l == this) return 0; 
/* 447 */     if (l instanceof CharBigList) {
/* 448 */       CharBigListIterator charBigListIterator1 = listIterator(), charBigListIterator2 = ((CharBigList)l).listIterator();
/*     */ 
/*     */       
/* 451 */       while (charBigListIterator1.hasNext() && charBigListIterator2.hasNext()) {
/* 452 */         char e1 = charBigListIterator1.nextChar();
/* 453 */         char e2 = charBigListIterator2.nextChar(); int r;
/* 454 */         if ((r = Character.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 456 */       return charBigListIterator2.hasNext() ? -1 : (charBigListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 458 */     BigListIterator<? extends Character> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 460 */     while (i1.hasNext() && i2.hasNext()) {
/* 461 */       int r; if ((r = ((Comparable<Object>)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 463 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(char o) {
/* 468 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public char popChar() {
/* 473 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 474 */     return removeChar(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public char topChar() {
/* 479 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 480 */     return getChar(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public char peekChar(int i) {
/* 485 */     return getChar(size64() - 1L - i);
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
/*     */   public boolean rem(char k) {
/* 497 */     long index = indexOf(k);
/* 498 */     if (index == -1L) return false; 
/* 499 */     removeChar(index);
/* 500 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(long index, CharCollection c) {
/* 511 */     return addAll(index, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(CharCollection c) {
/* 522 */     return addAll(size64(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void add(long index, Character ok) {
/* 534 */     add(index, ok.charValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Character set(long index, Character ok) {
/* 546 */     return Character.valueOf(set(index, ok.charValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Character get(long index) {
/* 558 */     return Character.valueOf(getChar(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public long indexOf(Object ok) {
/* 570 */     return indexOf(((Character)ok).charValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public long lastIndexOf(Object ok) {
/* 582 */     return lastIndexOf(((Character)ok).charValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Character remove(long index) {
/* 594 */     return Character.valueOf(removeChar(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void push(Character o) {
/* 606 */     push(o.charValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Character pop() {
/* 618 */     return Character.valueOf(popChar());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Character top() {
/* 630 */     return Character.valueOf(topChar());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Character peek(int i) {
/* 642 */     return Character.valueOf(peekChar(i));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 647 */     StringBuilder s = new StringBuilder();
/* 648 */     CharIterator i = iterator();
/* 649 */     long n = size64();
/*     */     
/* 651 */     boolean first = true;
/* 652 */     s.append("[");
/* 653 */     while (n-- != 0L) {
/* 654 */       if (first) { first = false; }
/* 655 */       else { s.append(", "); }
/* 656 */        char k = i.nextChar();
/* 657 */       s.append(String.valueOf(k));
/*     */     } 
/* 659 */     s.append("]");
/* 660 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CharSubList
/*     */     extends AbstractCharBigList
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final CharBigList l;
/*     */     protected final long from;
/*     */     protected long to;
/*     */     
/*     */     public CharSubList(CharBigList l, long from, long to) {
/* 674 */       this.l = l;
/* 675 */       this.from = from;
/* 676 */       this.to = to;
/*     */     }
/*     */     
/*     */     private boolean assertRange() {
/* 680 */       assert this.from <= this.l.size64();
/* 681 */       assert this.to <= this.l.size64();
/* 682 */       assert this.to >= this.from;
/* 683 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(char k) {
/* 688 */       this.l.add(this.to, k);
/* 689 */       this.to++;
/* 690 */       assert assertRange();
/* 691 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long index, char k) {
/* 696 */       ensureIndex(index);
/* 697 */       this.l.add(this.from + index, k);
/* 698 */       this.to++;
/* 699 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends Character> c) {
/* 704 */       ensureIndex(index);
/* 705 */       this.to += c.size();
/* 706 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public char getChar(long index) {
/* 711 */       ensureRestrictedIndex(index);
/* 712 */       return this.l.getChar(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public char removeChar(long index) {
/* 717 */       ensureRestrictedIndex(index);
/* 718 */       this.to--;
/* 719 */       return this.l.removeChar(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public char set(long index, char k) {
/* 724 */       ensureRestrictedIndex(index);
/* 725 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 730 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(long from, char[][] a, long offset, long length) {
/* 735 */       ensureIndex(from);
/* 736 */       if (from + length > size64()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size64() + ")"); 
/* 737 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 742 */       ensureIndex(from);
/* 743 */       ensureIndex(to);
/* 744 */       this.l.removeElements(this.from + from, this.from + to);
/* 745 */       this.to -= to - from;
/* 746 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, char[][] a, long offset, long length) {
/* 751 */       ensureIndex(index);
/* 752 */       this.l.addElements(this.from + index, a, offset, length);
/* 753 */       this.to += length;
/* 754 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends CharBigListIterators.AbstractIndexBasedBigListIterator
/*     */     {
/*     */       RandomAccessIter(long pos) {
/* 763 */         super(0L, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final char get(long i) {
/* 768 */         return AbstractCharBigList.CharSubList.this.l.getChar(AbstractCharBigList.CharSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(long i, char k) {
/* 774 */         AbstractCharBigList.CharSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(long i, char k) {
/* 779 */         AbstractCharBigList.CharSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(long i) {
/* 784 */         AbstractCharBigList.CharSubList.this.removeChar(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final long getMaxPos() {
/* 789 */         return AbstractCharBigList.CharSubList.this.to - AbstractCharBigList.CharSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(char k) {
/* 794 */         super.add(k);
/* 795 */         assert AbstractCharBigList.CharSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 800 */         super.remove();
/* 801 */         assert AbstractCharBigList.CharSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements CharBigListIterator {
/*     */       private CharBigListIterator parent;
/*     */       
/*     */       ParentWrappingIter(CharBigListIterator parent) {
/* 809 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public long nextIndex() {
/* 814 */         return this.parent.nextIndex() - AbstractCharBigList.CharSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public long previousIndex() {
/* 819 */         return this.parent.previousIndex() - AbstractCharBigList.CharSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 824 */         return (this.parent.nextIndex() < AbstractCharBigList.CharSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 829 */         return (this.parent.previousIndex() >= AbstractCharBigList.CharSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public char nextChar() {
/* 834 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 835 */         return this.parent.nextChar();
/*     */       }
/*     */ 
/*     */       
/*     */       public char previousChar() {
/* 840 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 841 */         return this.parent.previousChar();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(char k) {
/* 846 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(char k) {
/* 851 */         this.parent.set(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 856 */         this.parent.remove();
/*     */       }
/*     */ 
/*     */       
/*     */       public long back(long n) {
/* 861 */         if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 862 */         long currentPos = this.parent.previousIndex();
/* 863 */         long parentNewPos = currentPos - n;
/*     */ 
/*     */ 
/*     */         
/* 867 */         if (parentNewPos < AbstractCharBigList.CharSubList.this.from - 1L) parentNewPos = AbstractCharBigList.CharSubList.this.from - 1L; 
/* 868 */         long toSkip = parentNewPos - currentPos;
/* 869 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public long skip(long n) {
/* 874 */         if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 875 */         long currentPos = this.parent.nextIndex();
/* 876 */         long parentNewPos = currentPos + n;
/* 877 */         if (parentNewPos > AbstractCharBigList.CharSubList.this.to) parentNewPos = AbstractCharBigList.CharSubList.this.to; 
/* 878 */         long toSkip = parentNewPos - currentPos;
/* 879 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBigListIterator listIterator(long index) {
/* 885 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 890 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSpliterator spliterator() {
/* 895 */       return (this.l instanceof RandomAccess) ? new AbstractCharBigList.IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 900 */       if (this.l instanceof RandomAccess) {
/* 901 */         return CharSpliterators.widen(spliterator());
/*     */       }
/* 903 */       return super.intSpliterator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CharBigList subList(long from, long to) {
/* 909 */       ensureIndex(from);
/* 910 */       ensureIndex(to);
/* 911 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 914 */       return new CharSubList(this, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(char k) {
/* 919 */       long index = indexOf(k);
/* 920 */       if (index == -1L) return false; 
/* 921 */       this.to--;
/* 922 */       this.l.removeChar(this.from + index);
/* 923 */       assert assertRange();
/* 924 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, CharCollection c) {
/* 929 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, CharBigList l) {
/* 934 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CharRandomAccessSubList extends CharSubList implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public CharRandomAccessSubList(CharBigList l, long from, long to) {
/* 942 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBigList subList(long from, long to) {
/* 947 */       ensureIndex(from);
/* 948 */       ensureIndex(to);
/* 949 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 952 */       return new CharRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\AbstractCharBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */