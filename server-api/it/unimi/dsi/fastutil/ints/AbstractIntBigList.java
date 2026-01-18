/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.IntConsumer;
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
/*     */ public abstract class AbstractIntBigList
/*     */   extends AbstractIntCollection
/*     */   implements IntBigList, IntStack
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
/*     */   public void add(long index, int k) {
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
/*     */   public boolean add(int k) {
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
/*     */   public int removeInt(long i) {
/*  96 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int set(long index, int k) {
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
/*     */   public boolean addAll(long index, Collection<? extends Integer> c) {
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
/*     */     //   45: checkcast java/lang/Integer
/*     */     //   48: invokevirtual add : (JLjava/lang/Integer;)V
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
/*     */     //   0	57	0	this	Lit/unimi/dsi/fastutil/ints/AbstractIntBigList;
/*     */     //   0	57	1	index	J
/*     */     //   0	57	3	c	Ljava/util/Collection;
/*     */     //   13	44	4	i	Ljava/util/Iterator;
/*     */     //   22	35	5	retVal	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	57	3	c	Ljava/util/Collection<+Ljava/lang/Integer;>;
/*     */     //   13	44	4	i	Ljava/util/Iterator<+Ljava/lang/Integer;>;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Integer> c) {
/* 127 */     return addAll(size64(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBigListIterator iterator() {
/* 137 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBigListIterator listIterator() {
/* 147 */     return listIterator(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBigListIterator listIterator(long index) {
/* 157 */     ensureIndex(index);
/* 158 */     return new IntBigListIterators.AbstractIndexBasedBigListIterator(0L, index)
/*     */       {
/*     */         protected final int get(long i) {
/* 161 */           return AbstractIntBigList.this.getInt(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(long i, int k) {
/* 166 */           AbstractIntBigList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(long i, int k) {
/* 171 */           AbstractIntBigList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(long i) {
/* 176 */           AbstractIntBigList.this.removeInt(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final long getMaxPos() {
/* 181 */           return AbstractIntBigList.this.size64();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator extends IntBigSpliterators.LateBindingSizeIndexBasedSpliterator {
/*     */     final IntBigList l;
/*     */     
/*     */     IndexBasedSpliterator(IntBigList l, long pos) {
/* 190 */       super(pos);
/* 191 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(IntBigList l, long pos, long maxPos) {
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
/*     */     protected final int get(long i) {
/* 206 */       return this.l.getInt(i);
/*     */     }
/*     */ 
/*     */     
/*     */     protected final IndexBasedSpliterator makeForSplit(long pos, long maxPos) {
/* 211 */       return new IndexBasedSpliterator(this.l, pos, maxPos);
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
/*     */   public boolean contains(int k) {
/* 223 */     return (indexOf(k) >= 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long indexOf(int k) {
/* 228 */     IntBigListIterator i = listIterator();
/*     */     
/* 230 */     while (i.hasNext()) {
/* 231 */       int e = i.nextInt();
/* 232 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 234 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long lastIndexOf(int k) {
/* 239 */     IntBigListIterator i = listIterator(size64());
/*     */     
/* 241 */     while (i.hasPrevious()) {
/* 242 */       int e = i.previousInt();
/* 243 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 245 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(long size) {
/* 250 */     long i = size64();
/* 251 */     if (size > i) { for (; i++ < size; add(0)); }
/* 252 */     else { for (; i-- != size; remove(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public IntBigList subList(long from, long to) {
/* 257 */     ensureIndex(from);
/* 258 */     ensureIndex(to);
/* 259 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 260 */     return (this instanceof RandomAccess) ? new IntRandomAccessSubList(this, from, to) : new IntSubList(this, from, to);
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
/*     */   public void forEach(IntConsumer action) {
/* 272 */     if (this instanceof RandomAccess) {
/* 273 */       for (long i = 0L, max = size64(); i < max; i++) {
/* 274 */         action.accept(getInt(i));
/*     */       }
/*     */     } else {
/* 277 */       super.forEach(action);
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
/* 290 */     ensureIndex(to);
/* 291 */     IntBigListIterator i = listIterator(from);
/* 292 */     long n = to - from;
/* 293 */     if (n < 0L) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 294 */     while (n-- != 0L) {
/* 295 */       i.nextInt();
/* 296 */       i.remove();
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
/*     */   public void addElements(long index, int[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[IJJ)V
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
/*     */     //   46: invokestatic get : ([[IJ)I
/*     */     //   49: invokevirtual add : (JI)V
/*     */     //   52: goto -> 20
/*     */     //   55: aload_0
/*     */     //   56: lload_1
/*     */     //   57: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/ints/IntBigListIterator;
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
/*     */     //   84: invokestatic get : ([[IJ)I
/*     */     //   87: invokeinterface add : (I)V
/*     */     //   92: goto -> 62
/*     */     //   95: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #309	-> 0
/*     */     //   #310	-> 5
/*     */     //   #311	-> 13
/*     */     //   #312	-> 20
/*     */     //   #314	-> 55
/*     */     //   #315	-> 62
/*     */     //   #317	-> 95
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   62	33	8	iter	Lit/unimi/dsi/fastutil/ints/IntBigListIterator;
/*     */     //   0	96	0	this	Lit/unimi/dsi/fastutil/ints/AbstractIntBigList;
/*     */     //   0	96	1	index	J
/*     */     //   0	96	3	a	[[I
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
/*     */   public void addElements(long index, int[][] a) {
/* 326 */     addElements(index, a, 0L, BigArrays.length(a));
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
/*     */   public void getElements(long from, int[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[IJJ)V
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
/*     */     //   110: invokevirtual getInt : (J)I
/*     */     //   113: invokestatic set : ([[IJI)V
/*     */     //   116: goto -> 82
/*     */     //   119: goto -> 162
/*     */     //   122: aload_0
/*     */     //   123: lload_1
/*     */     //   124: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/ints/IntBigListIterator;
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
/*     */     //   151: invokeinterface nextInt : ()I
/*     */     //   156: invokestatic set : ([[IJI)V
/*     */     //   159: goto -> 129
/*     */     //   162: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #338	-> 0
/*     */     //   #339	-> 5
/*     */     //   #340	-> 13
/*     */     //   #341	-> 72
/*     */     //   #342	-> 79
/*     */     //   #343	-> 82
/*     */     //   #344	-> 119
/*     */     //   #345	-> 122
/*     */     //   #346	-> 129
/*     */     //   #348	-> 162
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   82	37	8	current	J
/*     */     //   129	33	8	i	Lit/unimi/dsi/fastutil/ints/IntBigListIterator;
/*     */     //   0	163	0	this	Lit/unimi/dsi/fastutil/ints/AbstractIntBigList;
/*     */     //   0	163	1	from	J
/*     */     //   0	163	3	a	[[I
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
/*     */   public void setElements(long index, int[][] a, long offset, long length) {
/* 352 */     ensureIndex(index);
/* 353 */     BigArrays.ensureOffsetLength(a, offset, length);
/* 354 */     if (index + length > size64()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size64() + ")"); 
/* 355 */     if (this instanceof RandomAccess) {
/* 356 */       long i; for (i = 0L; i < length; i++) {
/* 357 */         set(i + index, BigArrays.get(a, i + offset));
/*     */       }
/*     */     } else {
/* 360 */       IntBigListIterator iter = listIterator(index);
/* 361 */       long i = 0L;
/* 362 */       if (i < length) {
/* 363 */         iter.nextInt();
/* 364 */         iter.set(BigArrays.get(a, offset + i++));
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
/* 376 */     removeElements(0L, size64());
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
/* 388 */     return (int)Math.min(2147483647L, size64());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 398 */     IntIterator i = iterator();
/* 399 */     int h = 1;
/* 400 */     long s = size64();
/* 401 */     while (s-- != 0L) {
/* 402 */       int k = i.nextInt();
/* 403 */       h = 31 * h + k;
/*     */     } 
/* 405 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 410 */     if (o == this) return true; 
/* 411 */     if (!(o instanceof BigList)) return false; 
/* 412 */     BigList<?> l = (BigList)o;
/* 413 */     long s = size64();
/* 414 */     if (s != l.size64()) return false; 
/* 415 */     if (l instanceof IntBigList) {
/* 416 */       IntBigListIterator intBigListIterator1 = listIterator(), intBigListIterator2 = ((IntBigList)l).listIterator();
/* 417 */       while (s-- != 0L) { if (intBigListIterator1.nextInt() != intBigListIterator2.nextInt()) return false;  }
/* 418 */        return true;
/*     */     } 
/* 420 */     BigListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 421 */     while (s-- != 0L) { if (!Objects.equals(i1.next(), i2.next())) return false;  }
/* 422 */      return true;
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
/*     */   public int compareTo(BigList<? extends Integer> l) {
/* 437 */     if (l == this) return 0; 
/* 438 */     if (l instanceof IntBigList) {
/* 439 */       IntBigListIterator intBigListIterator1 = listIterator(), intBigListIterator2 = ((IntBigList)l).listIterator();
/*     */ 
/*     */       
/* 442 */       while (intBigListIterator1.hasNext() && intBigListIterator2.hasNext()) {
/* 443 */         int e1 = intBigListIterator1.nextInt();
/* 444 */         int e2 = intBigListIterator2.nextInt(); int r;
/* 445 */         if ((r = Integer.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 447 */       return intBigListIterator2.hasNext() ? -1 : (intBigListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 449 */     BigListIterator<? extends Integer> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 451 */     while (i1.hasNext() && i2.hasNext()) {
/* 452 */       int r; if ((r = ((Comparable<Object>)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 454 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(int o) {
/* 459 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int popInt() {
/* 464 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 465 */     return removeInt(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public int topInt() {
/* 470 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 471 */     return getInt(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public int peekInt(int i) {
/* 476 */     return getInt(size64() - 1L - i);
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
/*     */   public boolean rem(int k) {
/* 488 */     long index = indexOf(k);
/* 489 */     if (index == -1L) return false; 
/* 490 */     removeInt(index);
/* 491 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(long index, IntCollection c) {
/* 502 */     return addAll(index, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(IntCollection c) {
/* 513 */     return addAll(size64(), c);
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
/*     */   public void add(long index, Integer ok) {
/* 525 */     add(index, ok.intValue());
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
/*     */   public Integer set(long index, Integer ok) {
/* 537 */     return Integer.valueOf(set(index, ok.intValue()));
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
/*     */   public Integer get(long index) {
/* 549 */     return Integer.valueOf(getInt(index));
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
/* 561 */     return indexOf(((Integer)ok).intValue());
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
/* 573 */     return lastIndexOf(((Integer)ok).intValue());
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
/*     */   public Integer remove(long index) {
/* 585 */     return Integer.valueOf(removeInt(index));
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
/*     */   public void push(Integer o) {
/* 597 */     push(o.intValue());
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
/*     */   public Integer pop() {
/* 609 */     return Integer.valueOf(popInt());
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
/*     */   public Integer top() {
/* 621 */     return Integer.valueOf(topInt());
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
/*     */   public Integer peek(int i) {
/* 633 */     return Integer.valueOf(peekInt(i));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 638 */     StringBuilder s = new StringBuilder();
/* 639 */     IntIterator i = iterator();
/* 640 */     long n = size64();
/*     */     
/* 642 */     boolean first = true;
/* 643 */     s.append("[");
/* 644 */     while (n-- != 0L) {
/* 645 */       if (first) { first = false; }
/* 646 */       else { s.append(", "); }
/* 647 */        int k = i.nextInt();
/* 648 */       s.append(String.valueOf(k));
/*     */     } 
/* 650 */     s.append("]");
/* 651 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class IntSubList
/*     */     extends AbstractIntBigList
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final IntBigList l;
/*     */     protected final long from;
/*     */     protected long to;
/*     */     
/*     */     public IntSubList(IntBigList l, long from, long to) {
/* 665 */       this.l = l;
/* 666 */       this.from = from;
/* 667 */       this.to = to;
/*     */     }
/*     */     
/*     */     private boolean assertRange() {
/* 671 */       assert this.from <= this.l.size64();
/* 672 */       assert this.to <= this.l.size64();
/* 673 */       assert this.to >= this.from;
/* 674 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(int k) {
/* 679 */       this.l.add(this.to, k);
/* 680 */       this.to++;
/* 681 */       assert assertRange();
/* 682 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long index, int k) {
/* 687 */       ensureIndex(index);
/* 688 */       this.l.add(this.from + index, k);
/* 689 */       this.to++;
/* 690 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends Integer> c) {
/* 695 */       ensureIndex(index);
/* 696 */       this.to += c.size();
/* 697 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getInt(long index) {
/* 702 */       ensureRestrictedIndex(index);
/* 703 */       return this.l.getInt(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public int removeInt(long index) {
/* 708 */       ensureRestrictedIndex(index);
/* 709 */       this.to--;
/* 710 */       return this.l.removeInt(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public int set(long index, int k) {
/* 715 */       ensureRestrictedIndex(index);
/* 716 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 721 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(long from, int[][] a, long offset, long length) {
/* 726 */       ensureIndex(from);
/* 727 */       if (from + length > size64()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size64() + ")"); 
/* 728 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 733 */       ensureIndex(from);
/* 734 */       ensureIndex(to);
/* 735 */       this.l.removeElements(this.from + from, this.from + to);
/* 736 */       this.to -= to - from;
/* 737 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, int[][] a, long offset, long length) {
/* 742 */       ensureIndex(index);
/* 743 */       this.l.addElements(this.from + index, a, offset, length);
/* 744 */       this.to += length;
/* 745 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends IntBigListIterators.AbstractIndexBasedBigListIterator
/*     */     {
/*     */       RandomAccessIter(long pos) {
/* 754 */         super(0L, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int get(long i) {
/* 759 */         return AbstractIntBigList.IntSubList.this.l.getInt(AbstractIntBigList.IntSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(long i, int k) {
/* 765 */         AbstractIntBigList.IntSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(long i, int k) {
/* 770 */         AbstractIntBigList.IntSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(long i) {
/* 775 */         AbstractIntBigList.IntSubList.this.removeInt(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final long getMaxPos() {
/* 780 */         return AbstractIntBigList.IntSubList.this.to - AbstractIntBigList.IntSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(int k) {
/* 785 */         super.add(k);
/* 786 */         assert AbstractIntBigList.IntSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 791 */         super.remove();
/* 792 */         assert AbstractIntBigList.IntSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements IntBigListIterator {
/*     */       private IntBigListIterator parent;
/*     */       
/*     */       ParentWrappingIter(IntBigListIterator parent) {
/* 800 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public long nextIndex() {
/* 805 */         return this.parent.nextIndex() - AbstractIntBigList.IntSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public long previousIndex() {
/* 810 */         return this.parent.previousIndex() - AbstractIntBigList.IntSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 815 */         return (this.parent.nextIndex() < AbstractIntBigList.IntSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 820 */         return (this.parent.previousIndex() >= AbstractIntBigList.IntSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextInt() {
/* 825 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 826 */         return this.parent.nextInt();
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousInt() {
/* 831 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 832 */         return this.parent.previousInt();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(int k) {
/* 837 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(int k) {
/* 842 */         this.parent.set(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 847 */         this.parent.remove();
/*     */       }
/*     */ 
/*     */       
/*     */       public long back(long n) {
/* 852 */         if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 853 */         long currentPos = this.parent.previousIndex();
/* 854 */         long parentNewPos = currentPos - n;
/*     */ 
/*     */ 
/*     */         
/* 858 */         if (parentNewPos < AbstractIntBigList.IntSubList.this.from - 1L) parentNewPos = AbstractIntBigList.IntSubList.this.from - 1L; 
/* 859 */         long toSkip = parentNewPos - currentPos;
/* 860 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public long skip(long n) {
/* 865 */         if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 866 */         long currentPos = this.parent.nextIndex();
/* 867 */         long parentNewPos = currentPos + n;
/* 868 */         if (parentNewPos > AbstractIntBigList.IntSubList.this.to) parentNewPos = AbstractIntBigList.IntSubList.this.to; 
/* 869 */         long toSkip = parentNewPos - currentPos;
/* 870 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBigListIterator listIterator(long index) {
/* 876 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 881 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator spliterator() {
/* 886 */       return (this.l instanceof RandomAccess) ? new AbstractIntBigList.IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBigList subList(long from, long to) {
/* 891 */       ensureIndex(from);
/* 892 */       ensureIndex(to);
/* 893 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 896 */       return new IntSubList(this, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(int k) {
/* 901 */       long index = indexOf(k);
/* 902 */       if (index == -1L) return false; 
/* 903 */       this.to--;
/* 904 */       this.l.removeInt(this.from + index);
/* 905 */       assert assertRange();
/* 906 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, IntCollection c) {
/* 911 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, IntBigList l) {
/* 916 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class IntRandomAccessSubList extends IntSubList implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public IntRandomAccessSubList(IntBigList l, long from, long to) {
/* 924 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBigList subList(long from, long to) {
/* 929 */       ensureIndex(from);
/* 930 */       ensureIndex(to);
/* 931 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 934 */       return new IntRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\AbstractIntBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */