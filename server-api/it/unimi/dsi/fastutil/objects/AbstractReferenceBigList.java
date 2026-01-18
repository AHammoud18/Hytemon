/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import it.unimi.dsi.fastutil.Stack;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
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
/*     */ 
/*     */ 
/*     */ public abstract class AbstractReferenceBigList<K>
/*     */   extends AbstractReferenceCollection<K>
/*     */   implements ReferenceBigList<K>, Stack<K>
/*     */ {
/*     */   protected void ensureIndex(long index) {
/*  53 */     if (index < 0L) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  54 */     if (index > size64()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size64() + ")");
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
/*  65 */     if (index < 0L) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  66 */     if (index >= size64()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + size64() + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(long index, K k) {
/*  76 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/*  87 */     add(size64(), k);
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K remove(long i) {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K set(long index, K k) {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(long index, Collection<? extends K> c) {
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
/*     */     //   29: ifeq -> 51
/*     */     //   32: aload_0
/*     */     //   33: lload_1
/*     */     //   34: dup2
/*     */     //   35: lconst_1
/*     */     //   36: ladd
/*     */     //   37: lstore_1
/*     */     //   38: aload #4
/*     */     //   40: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   45: invokevirtual add : (JLjava/lang/Object;)V
/*     */     //   48: goto -> 22
/*     */     //   51: iload #5
/*     */     //   53: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #114	-> 0
/*     */     //   #115	-> 5
/*     */     //   #116	-> 13
/*     */     //   #117	-> 22
/*     */     //   #118	-> 51
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	54	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList;
/*     */     //   0	54	1	index	J
/*     */     //   0	54	3	c	Ljava/util/Collection;
/*     */     //   13	41	4	i	Ljava/util/Iterator;
/*     */     //   22	32	5	retVal	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	54	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList<TK;>;
/*     */     //   0	54	3	c	Ljava/util/Collection<+TK;>;
/*     */     //   13	41	4	i	Ljava/util/Iterator<+TK;>;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 129 */     return addAll(size64(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigListIterator<K> iterator() {
/* 139 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigListIterator<K> listIterator() {
/* 149 */     return listIterator(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigListIterator<K> listIterator(long index) {
/* 159 */     ensureIndex(index);
/* 160 */     return new ObjectBigListIterators.AbstractIndexBasedBigListIterator<K>(0L, index)
/*     */       {
/*     */         protected final K get(long i) {
/* 163 */           return (K)AbstractReferenceBigList.this.get(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(long i, K k) {
/* 168 */           AbstractReferenceBigList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(long i, K k) {
/* 173 */           AbstractReferenceBigList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(long i) {
/* 178 */           AbstractReferenceBigList.this.remove(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final long getMaxPos() {
/* 183 */           return AbstractReferenceBigList.this.size64();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator<K> extends ObjectBigSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
/*     */     final ReferenceBigList<K> l;
/*     */     
/*     */     IndexBasedSpliterator(ReferenceBigList<K> l, long pos) {
/* 192 */       super(pos);
/* 193 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(ReferenceBigList<K> l, long pos, long maxPos) {
/* 197 */       super(pos, maxPos);
/* 198 */       this.l = l;
/*     */     }
/*     */ 
/*     */     
/*     */     protected final long getMaxPosFromBackingStore() {
/* 203 */       return this.l.size64();
/*     */     }
/*     */ 
/*     */     
/*     */     protected final K get(long i) {
/* 208 */       return (K)this.l.get(i);
/*     */     }
/*     */ 
/*     */     
/*     */     protected final IndexBasedSpliterator<K> makeForSplit(long pos, long maxPos) {
/* 213 */       return new IndexBasedSpliterator(this.l, pos, maxPos);
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
/*     */   public boolean contains(Object k) {
/* 225 */     return (indexOf(k) >= 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long indexOf(Object k) {
/* 230 */     ObjectBigListIterator<K> i = listIterator();
/*     */     
/* 232 */     while (i.hasNext()) {
/* 233 */       K e = i.next();
/* 234 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 236 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long lastIndexOf(Object k) {
/* 241 */     ObjectBigListIterator<K> i = listIterator(size64());
/*     */     
/* 243 */     while (i.hasPrevious()) {
/* 244 */       K e = (K)i.previous();
/* 245 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 247 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(long size) {
/* 252 */     long i = size64();
/* 253 */     if (size > i) { for (; i++ < size; add((K)null)); }
/* 254 */     else { for (; i-- != size; remove(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public ReferenceBigList<K> subList(long from, long to) {
/* 259 */     ensureIndex(from);
/* 260 */     ensureIndex(to);
/* 261 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 262 */     return (this instanceof RandomAccess) ? new ReferenceRandomAccessSubList<>(this, from, to) : new ReferenceSubList<>(this, from, to);
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
/*     */   public void forEach(Consumer<? super K> action) {
/* 274 */     if (this instanceof RandomAccess) {
/* 275 */       for (long i = 0L, max = size64(); i < max; i++) {
/* 276 */         action.accept((K)get(i));
/*     */       }
/*     */     } else {
/* 279 */       super.forEach(action);
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
/* 292 */     ensureIndex(to);
/* 293 */     ObjectBigListIterator<K> i = listIterator(from);
/* 294 */     long n = to - from;
/* 295 */     if (n < 0L) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 296 */     while (n-- != 0L) {
/* 297 */       i.next();
/* 298 */       i.remove();
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
/*     */   public void addElements(long index, K[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[Ljava/lang/Object;JJ)V
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
/*     */     //   46: invokestatic get : ([[Ljava/lang/Object;J)Ljava/lang/Object;
/*     */     //   49: invokevirtual add : (JLjava/lang/Object;)V
/*     */     //   52: goto -> 20
/*     */     //   55: aload_0
/*     */     //   56: lload_1
/*     */     //   57: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator;
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
/*     */     //   84: invokestatic get : ([[Ljava/lang/Object;J)Ljava/lang/Object;
/*     */     //   87: invokeinterface add : (Ljava/lang/Object;)V
/*     */     //   92: goto -> 62
/*     */     //   95: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #311	-> 0
/*     */     //   #312	-> 5
/*     */     //   #313	-> 13
/*     */     //   #314	-> 20
/*     */     //   #316	-> 55
/*     */     //   #317	-> 62
/*     */     //   #319	-> 95
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   62	33	8	iter	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator;
/*     */     //   0	96	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList;
/*     */     //   0	96	1	index	J
/*     */     //   0	96	3	a	[[Ljava/lang/Object;
/*     */     //   0	96	4	offset	J
/*     */     //   0	96	6	length	J
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   62	33	8	iter	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator<TK;>;
/*     */     //   0	96	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList<TK;>;
/*     */     //   0	96	3	a	[[TK;
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
/*     */   public void addElements(long index, K[][] a) {
/* 328 */     addElements(index, a, 0L, BigArrays.length((Object[][])a));
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
/*     */   public void getElements(long from, Object[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[Ljava/lang/Object;JJ)V
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
/*     */     //   110: invokevirtual get : (J)Ljava/lang/Object;
/*     */     //   113: invokestatic set : ([[Ljava/lang/Object;JLjava/lang/Object;)V
/*     */     //   116: goto -> 82
/*     */     //   119: goto -> 162
/*     */     //   122: aload_0
/*     */     //   123: lload_1
/*     */     //   124: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator;
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
/*     */     //   151: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   156: invokestatic set : ([[Ljava/lang/Object;JLjava/lang/Object;)V
/*     */     //   159: goto -> 129
/*     */     //   162: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #340	-> 0
/*     */     //   #341	-> 5
/*     */     //   #342	-> 13
/*     */     //   #343	-> 72
/*     */     //   #344	-> 79
/*     */     //   #345	-> 82
/*     */     //   #346	-> 119
/*     */     //   #347	-> 122
/*     */     //   #348	-> 129
/*     */     //   #350	-> 162
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   82	37	8	current	J
/*     */     //   129	33	8	i	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator;
/*     */     //   0	163	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList;
/*     */     //   0	163	1	from	J
/*     */     //   0	163	3	a	[[Ljava/lang/Object;
/*     */     //   0	163	4	offset	J
/*     */     //   0	163	6	length	J
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   129	33	8	i	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator<TK;>;
/*     */     //   0	163	0	this	Lit/unimi/dsi/fastutil/objects/AbstractReferenceBigList<TK;>;
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
/*     */   public void setElements(long index, K[][] a, long offset, long length) {
/* 354 */     ensureIndex(index);
/* 355 */     BigArrays.ensureOffsetLength((Object[][])a, offset, length);
/* 356 */     if (index + length > size64()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size64() + ")"); 
/* 357 */     if (this instanceof RandomAccess) {
/* 358 */       long i; for (i = 0L; i < length; i++) {
/* 359 */         set(i + index, (K)BigArrays.get((Object[][])a, i + offset));
/*     */       }
/*     */     } else {
/* 362 */       ObjectBigListIterator<K> iter = listIterator(index);
/* 363 */       long i = 0L;
/* 364 */       if (i < length) {
/* 365 */         iter.next();
/* 366 */         iter.set((K)BigArrays.get((Object[][])a, offset + i++));
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
/* 378 */     removeElements(0L, size64());
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
/* 390 */     return (int)Math.min(2147483647L, size64());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 400 */     ObjectIterator<K> i = iterator();
/* 401 */     int h = 1;
/* 402 */     long s = size64();
/* 403 */     while (s-- != 0L) {
/* 404 */       K k = i.next();
/* 405 */       h = 31 * h + System.identityHashCode(k);
/*     */     } 
/* 407 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 412 */     if (o == this) return true; 
/* 413 */     if (!(o instanceof BigList)) return false; 
/* 414 */     BigList<?> l = (BigList)o;
/* 415 */     long s = size64();
/* 416 */     if (s != l.size64()) return false; 
/* 417 */     BigListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 418 */     while (s-- != 0L) { if (i1.next() != i2.next()) return false;  }
/* 419 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(K o) {
/* 424 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public K pop() {
/* 429 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 430 */     return remove(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public K top() {
/* 435 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 436 */     return (K)get(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public K peek(int i) {
/* 441 */     return (K)get(size64() - 1L - i);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 446 */     StringBuilder s = new StringBuilder();
/* 447 */     ObjectIterator<K> i = iterator();
/* 448 */     long n = size64();
/*     */     
/* 450 */     boolean first = true;
/* 451 */     s.append("[");
/* 452 */     while (n-- != 0L) {
/* 453 */       if (first) { first = false; }
/* 454 */       else { s.append(", "); }
/* 455 */        K k = i.next();
/* 456 */       if (this == k) { s.append("(this big list)"); continue; }
/* 457 */        s.append(String.valueOf(k));
/*     */     } 
/* 459 */     s.append("]");
/* 460 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ReferenceSubList<K>
/*     */     extends AbstractReferenceBigList<K>
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceBigList<K> l;
/*     */     protected final long from;
/*     */     protected long to;
/*     */     
/*     */     public ReferenceSubList(ReferenceBigList<K> l, long from, long to) {
/* 474 */       this.l = l;
/* 475 */       this.from = from;
/* 476 */       this.to = to;
/*     */     }
/*     */     
/*     */     private boolean assertRange() {
/* 480 */       assert this.from <= this.l.size64();
/* 481 */       assert this.to <= this.l.size64();
/* 482 */       assert this.to >= this.from;
/* 483 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(K k) {
/* 488 */       this.l.add(this.to, k);
/* 489 */       this.to++;
/* 490 */       assert assertRange();
/* 491 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long index, K k) {
/* 496 */       ensureIndex(index);
/* 497 */       this.l.add(this.from + index, k);
/* 498 */       this.to++;
/* 499 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 504 */       ensureIndex(index);
/* 505 */       this.to += c.size();
/* 506 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long index) {
/* 511 */       ensureRestrictedIndex(index);
/* 512 */       return (K)this.l.get(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long index) {
/* 517 */       ensureRestrictedIndex(index);
/* 518 */       this.to--;
/* 519 */       return (K)this.l.remove(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(long index, K k) {
/* 524 */       ensureRestrictedIndex(index);
/* 525 */       return (K)this.l.set(this.from + index, k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 530 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 535 */       ensureIndex(from);
/* 536 */       if (from + length > size64()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size64() + ")"); 
/* 537 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 542 */       ensureIndex(from);
/* 543 */       ensureIndex(to);
/* 544 */       this.l.removeElements(this.from + from, this.from + to);
/* 545 */       this.to -= to - from;
/* 546 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 551 */       ensureIndex(index);
/* 552 */       this.l.addElements(this.from + index, a, offset, length);
/* 553 */       this.to += length;
/* 554 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends ObjectBigListIterators.AbstractIndexBasedBigListIterator<K>
/*     */     {
/*     */       RandomAccessIter(long pos) {
/* 563 */         super(0L, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final K get(long i) {
/* 568 */         return (K)AbstractReferenceBigList.ReferenceSubList.this.l.get(AbstractReferenceBigList.ReferenceSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(long i, K k) {
/* 574 */         AbstractReferenceBigList.ReferenceSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(long i, K k) {
/* 579 */         AbstractReferenceBigList.ReferenceSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(long i) {
/* 584 */         AbstractReferenceBigList.ReferenceSubList.this.remove(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final long getMaxPos() {
/* 589 */         return AbstractReferenceBigList.ReferenceSubList.this.to - AbstractReferenceBigList.ReferenceSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(K k) {
/* 594 */         super.add(k);
/* 595 */         assert AbstractReferenceBigList.ReferenceSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 600 */         super.remove();
/* 601 */         assert AbstractReferenceBigList.ReferenceSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements ObjectBigListIterator<K> {
/*     */       private ObjectBigListIterator<K> parent;
/*     */       
/*     */       ParentWrappingIter(ObjectBigListIterator<K> parent) {
/* 609 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public long nextIndex() {
/* 614 */         return this.parent.nextIndex() - AbstractReferenceBigList.ReferenceSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public long previousIndex() {
/* 619 */         return this.parent.previousIndex() - AbstractReferenceBigList.ReferenceSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 624 */         return (this.parent.nextIndex() < AbstractReferenceBigList.ReferenceSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 629 */         return (this.parent.previousIndex() >= AbstractReferenceBigList.ReferenceSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public K next() {
/* 634 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 635 */         return this.parent.next();
/*     */       }
/*     */ 
/*     */       
/*     */       public K previous() {
/* 640 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 641 */         return (K)this.parent.previous();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(K k) {
/* 646 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(K k) {
/* 651 */         this.parent.set(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 656 */         this.parent.remove();
/*     */       }
/*     */ 
/*     */       
/*     */       public long back(long n) {
/* 661 */         if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 662 */         long currentPos = this.parent.previousIndex();
/* 663 */         long parentNewPos = currentPos - n;
/*     */ 
/*     */ 
/*     */         
/* 667 */         if (parentNewPos < AbstractReferenceBigList.ReferenceSubList.this.from - 1L) parentNewPos = AbstractReferenceBigList.ReferenceSubList.this.from - 1L; 
/* 668 */         long toSkip = parentNewPos - currentPos;
/* 669 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public long skip(long n) {
/* 674 */         if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 675 */         long currentPos = this.parent.nextIndex();
/* 676 */         long parentNewPos = currentPos + n;
/* 677 */         if (parentNewPos > AbstractReferenceBigList.ReferenceSubList.this.to) parentNewPos = AbstractReferenceBigList.ReferenceSubList.this.to; 
/* 678 */         long toSkip = parentNewPos - currentPos;
/* 679 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long index) {
/* 685 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 690 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 695 */       return (this.l instanceof RandomAccess) ? new AbstractReferenceBigList.IndexBasedSpliterator<>(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 700 */       ensureIndex(from);
/* 701 */       ensureIndex(to);
/* 702 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 705 */       return new ReferenceSubList(this, from, to);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ReferenceRandomAccessSubList<K> extends ReferenceSubList<K> implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public ReferenceRandomAccessSubList(ReferenceBigList<K> l, long from, long to) {
/* 713 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 718 */       ensureIndex(from);
/* 719 */       ensureIndex(to);
/* 720 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 723 */       return new ReferenceRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractReferenceBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */