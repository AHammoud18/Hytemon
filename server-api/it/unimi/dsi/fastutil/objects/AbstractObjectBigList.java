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
/*     */ import java.util.Objects;
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
/*     */ public abstract class AbstractObjectBigList<K>
/*     */   extends AbstractObjectCollection<K>
/*     */   implements ObjectBigList<K>, Stack<K>
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
/*     */     //   0	54	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList;
/*     */     //   0	54	1	index	J
/*     */     //   0	54	3	c	Ljava/util/Collection;
/*     */     //   13	41	4	i	Ljava/util/Iterator;
/*     */     //   22	32	5	retVal	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	54	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList<TK;>;
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
/* 163 */           return (K)AbstractObjectBigList.this.get(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(long i, K k) {
/* 168 */           AbstractObjectBigList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(long i, K k) {
/* 173 */           AbstractObjectBigList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(long i) {
/* 178 */           AbstractObjectBigList.this.remove(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final long getMaxPos() {
/* 183 */           return AbstractObjectBigList.this.size64();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator<K> extends ObjectBigSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
/*     */     final ObjectBigList<K> l;
/*     */     
/*     */     IndexBasedSpliterator(ObjectBigList<K> l, long pos) {
/* 192 */       super(pos);
/* 193 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(ObjectBigList<K> l, long pos, long maxPos) {
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
/* 234 */       if (Objects.equals(k, e)) return i.previousIndex(); 
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
/* 245 */       if (Objects.equals(k, e)) return i.nextIndex(); 
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
/*     */   public ObjectBigList<K> subList(long from, long to) {
/* 259 */     ensureIndex(from);
/* 260 */     ensureIndex(to);
/* 261 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 262 */     return (this instanceof RandomAccess) ? new ObjectRandomAccessSubList<>(this, from, to) : new ObjectSubList<>(this, from, to);
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
/*     */     //   0	96	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList;
/*     */     //   0	96	1	index	J
/*     */     //   0	96	3	a	[[Ljava/lang/Object;
/*     */     //   0	96	4	offset	J
/*     */     //   0	96	6	length	J
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   62	33	8	iter	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator<TK;>;
/*     */     //   0	96	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList<TK;>;
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
/*     */     //   0	163	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList;
/*     */     //   0	163	1	from	J
/*     */     //   0	163	3	a	[[Ljava/lang/Object;
/*     */     //   0	163	4	offset	J
/*     */     //   0	163	6	length	J
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   129	33	8	i	Lit/unimi/dsi/fastutil/objects/ObjectBigListIterator<TK;>;
/*     */     //   0	163	0	this	Lit/unimi/dsi/fastutil/objects/AbstractObjectBigList<TK;>;
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
/* 405 */       h = 31 * h + ((k == null) ? 0 : k.hashCode());
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
/* 418 */     while (s-- != 0L) { if (!Objects.equals(i1.next(), i2.next())) return false;  }
/* 419 */      return true;
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
/*     */   public int compareTo(BigList<? extends K> l) {
/* 434 */     if (l == this) return 0; 
/* 435 */     if (l instanceof ObjectBigList) {
/* 436 */       ObjectBigListIterator<K> objectBigListIterator1 = listIterator(), objectBigListIterator2 = ((ObjectBigList)l).listIterator();
/*     */ 
/*     */       
/* 439 */       while (objectBigListIterator1.hasNext() && objectBigListIterator2.hasNext()) {
/* 440 */         K e1 = objectBigListIterator1.next();
/* 441 */         K e2 = objectBigListIterator2.next(); int r;
/* 442 */         if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0) return r; 
/*     */       } 
/* 444 */       return objectBigListIterator2.hasNext() ? -1 : (objectBigListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 446 */     BigListIterator<? extends K> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 448 */     while (i1.hasNext() && i2.hasNext()) {
/* 449 */       int r; if ((r = ((Comparable<Object>)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 451 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(K o) {
/* 456 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public K pop() {
/* 461 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 462 */     return remove(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public K top() {
/* 467 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 468 */     return (K)get(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public K peek(int i) {
/* 473 */     return (K)get(size64() - 1L - i);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 478 */     StringBuilder s = new StringBuilder();
/* 479 */     ObjectIterator<K> i = iterator();
/* 480 */     long n = size64();
/*     */     
/* 482 */     boolean first = true;
/* 483 */     s.append("[");
/* 484 */     while (n-- != 0L) {
/* 485 */       if (first) { first = false; }
/* 486 */       else { s.append(", "); }
/* 487 */        K k = i.next();
/* 488 */       if (this == k) { s.append("(this big list)"); continue; }
/* 489 */        s.append(String.valueOf(k));
/*     */     } 
/* 491 */     s.append("]");
/* 492 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ObjectSubList<K>
/*     */     extends AbstractObjectBigList<K>
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectBigList<K> l;
/*     */     protected final long from;
/*     */     protected long to;
/*     */     
/*     */     public ObjectSubList(ObjectBigList<K> l, long from, long to) {
/* 506 */       this.l = l;
/* 507 */       this.from = from;
/* 508 */       this.to = to;
/*     */     }
/*     */     
/*     */     private boolean assertRange() {
/* 512 */       assert this.from <= this.l.size64();
/* 513 */       assert this.to <= this.l.size64();
/* 514 */       assert this.to >= this.from;
/* 515 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(K k) {
/* 520 */       this.l.add(this.to, k);
/* 521 */       this.to++;
/* 522 */       assert assertRange();
/* 523 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long index, K k) {
/* 528 */       ensureIndex(index);
/* 529 */       this.l.add(this.from + index, k);
/* 530 */       this.to++;
/* 531 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 536 */       ensureIndex(index);
/* 537 */       this.to += c.size();
/* 538 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(long index) {
/* 543 */       ensureRestrictedIndex(index);
/* 544 */       return (K)this.l.get(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(long index) {
/* 549 */       ensureRestrictedIndex(index);
/* 550 */       this.to--;
/* 551 */       return (K)this.l.remove(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(long index, K k) {
/* 556 */       ensureRestrictedIndex(index);
/* 557 */       return (K)this.l.set(this.from + index, k);
/*     */     }
/*     */ 
/*     */     
/*     */     public long size64() {
/* 562 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 567 */       ensureIndex(from);
/* 568 */       if (from + length > size64()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size64() + ")"); 
/* 569 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 574 */       ensureIndex(from);
/* 575 */       ensureIndex(to);
/* 576 */       this.l.removeElements(this.from + from, this.from + to);
/* 577 */       this.to -= to - from;
/* 578 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 583 */       ensureIndex(index);
/* 584 */       this.l.addElements(this.from + index, a, offset, length);
/* 585 */       this.to += length;
/* 586 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends ObjectBigListIterators.AbstractIndexBasedBigListIterator<K>
/*     */     {
/*     */       RandomAccessIter(long pos) {
/* 595 */         super(0L, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final K get(long i) {
/* 600 */         return (K)AbstractObjectBigList.ObjectSubList.this.l.get(AbstractObjectBigList.ObjectSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(long i, K k) {
/* 606 */         AbstractObjectBigList.ObjectSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(long i, K k) {
/* 611 */         AbstractObjectBigList.ObjectSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(long i) {
/* 616 */         AbstractObjectBigList.ObjectSubList.this.remove(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final long getMaxPos() {
/* 621 */         return AbstractObjectBigList.ObjectSubList.this.to - AbstractObjectBigList.ObjectSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(K k) {
/* 626 */         super.add(k);
/* 627 */         assert AbstractObjectBigList.ObjectSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 632 */         super.remove();
/* 633 */         assert AbstractObjectBigList.ObjectSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements ObjectBigListIterator<K> {
/*     */       private ObjectBigListIterator<K> parent;
/*     */       
/*     */       ParentWrappingIter(ObjectBigListIterator<K> parent) {
/* 641 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public long nextIndex() {
/* 646 */         return this.parent.nextIndex() - AbstractObjectBigList.ObjectSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public long previousIndex() {
/* 651 */         return this.parent.previousIndex() - AbstractObjectBigList.ObjectSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 656 */         return (this.parent.nextIndex() < AbstractObjectBigList.ObjectSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 661 */         return (this.parent.previousIndex() >= AbstractObjectBigList.ObjectSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public K next() {
/* 666 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 667 */         return this.parent.next();
/*     */       }
/*     */ 
/*     */       
/*     */       public K previous() {
/* 672 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 673 */         return (K)this.parent.previous();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(K k) {
/* 678 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(K k) {
/* 683 */         this.parent.set(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 688 */         this.parent.remove();
/*     */       }
/*     */ 
/*     */       
/*     */       public long back(long n) {
/* 693 */         if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 694 */         long currentPos = this.parent.previousIndex();
/* 695 */         long parentNewPos = currentPos - n;
/*     */ 
/*     */ 
/*     */         
/* 699 */         if (parentNewPos < AbstractObjectBigList.ObjectSubList.this.from - 1L) parentNewPos = AbstractObjectBigList.ObjectSubList.this.from - 1L; 
/* 700 */         long toSkip = parentNewPos - currentPos;
/* 701 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public long skip(long n) {
/* 706 */         if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 707 */         long currentPos = this.parent.nextIndex();
/* 708 */         long parentNewPos = currentPos + n;
/* 709 */         if (parentNewPos > AbstractObjectBigList.ObjectSubList.this.to) parentNewPos = AbstractObjectBigList.ObjectSubList.this.to; 
/* 710 */         long toSkip = parentNewPos - currentPos;
/* 711 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long index) {
/* 717 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 722 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 727 */       return (this.l instanceof RandomAccess) ? new AbstractObjectBigList.IndexBasedSpliterator<>(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 732 */       ensureIndex(from);
/* 733 */       ensureIndex(to);
/* 734 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 737 */       return new ObjectSubList(this, from, to);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ObjectRandomAccessSubList<K> extends ObjectSubList<K> implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public ObjectRandomAccessSubList(ObjectBigList<K> l, long from, long to) {
/* 745 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 750 */       ensureIndex(from);
/* 751 */       ensureIndex(to);
/* 752 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 755 */       return new ObjectRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractObjectBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */