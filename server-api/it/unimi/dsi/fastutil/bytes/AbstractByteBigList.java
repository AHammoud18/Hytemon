/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByteBigList
/*     */   extends AbstractByteCollection
/*     */   implements ByteBigList, ByteStack
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
/*     */   public void add(long index, byte k) {
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
/*     */   public boolean add(byte k) {
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
/*     */   public byte removeByte(long i) {
/*  96 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte set(long index, byte k) {
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
/*     */   public boolean addAll(long index, Collection<? extends Byte> c) {
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
/*     */     //   45: checkcast java/lang/Byte
/*     */     //   48: invokevirtual add : (JLjava/lang/Byte;)V
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
/*     */     //   0	57	0	this	Lit/unimi/dsi/fastutil/bytes/AbstractByteBigList;
/*     */     //   0	57	1	index	J
/*     */     //   0	57	3	c	Ljava/util/Collection;
/*     */     //   13	44	4	i	Ljava/util/Iterator;
/*     */     //   22	35	5	retVal	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	57	3	c	Ljava/util/Collection<+Ljava/lang/Byte;>;
/*     */     //   13	44	4	i	Ljava/util/Iterator<+Ljava/lang/Byte;>;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Byte> c) {
/* 127 */     return addAll(size64(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBigListIterator iterator() {
/* 137 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBigListIterator listIterator() {
/* 147 */     return listIterator(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBigListIterator listIterator(long index) {
/* 157 */     ensureIndex(index);
/* 158 */     return new ByteBigListIterators.AbstractIndexBasedBigListIterator(0L, index)
/*     */       {
/*     */         protected final byte get(long i) {
/* 161 */           return AbstractByteBigList.this.getByte(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(long i, byte k) {
/* 166 */           AbstractByteBigList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(long i, byte k) {
/* 171 */           AbstractByteBigList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(long i) {
/* 176 */           AbstractByteBigList.this.removeByte(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final long getMaxPos() {
/* 181 */           return AbstractByteBigList.this.size64();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator extends ByteBigSpliterators.LateBindingSizeIndexBasedSpliterator {
/*     */     final ByteBigList l;
/*     */     
/*     */     IndexBasedSpliterator(ByteBigList l, long pos) {
/* 190 */       super(pos);
/* 191 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(ByteBigList l, long pos, long maxPos) {
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
/*     */     protected final byte get(long i) {
/* 206 */       return this.l.getByte(i);
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
/* 218 */       return ByteSpliterators.widen(spliterator());
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
/*     */   public boolean contains(byte k) {
/* 232 */     return (indexOf(k) >= 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long indexOf(byte k) {
/* 237 */     ByteBigListIterator i = listIterator();
/*     */     
/* 239 */     while (i.hasNext()) {
/* 240 */       byte e = i.nextByte();
/* 241 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 243 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long lastIndexOf(byte k) {
/* 248 */     ByteBigListIterator i = listIterator(size64());
/*     */     
/* 250 */     while (i.hasPrevious()) {
/* 251 */       byte e = i.previousByte();
/* 252 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 254 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(long size) {
/* 259 */     long i = size64();
/* 260 */     if (size > i) { for (; i++ < size; add((byte)0)); }
/* 261 */     else { for (; i-- != size; remove(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public ByteBigList subList(long from, long to) {
/* 266 */     ensureIndex(from);
/* 267 */     ensureIndex(to);
/* 268 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 269 */     return (this instanceof RandomAccess) ? new ByteRandomAccessSubList(this, from, to) : new ByteSubList(this, from, to);
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
/*     */   public void forEach(ByteConsumer action) {
/* 281 */     if (this instanceof RandomAccess) {
/* 282 */       for (long i = 0L, max = size64(); i < max; i++) {
/* 283 */         action.accept(getByte(i));
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
/* 300 */     ByteBigListIterator i = listIterator(from);
/* 301 */     long n = to - from;
/* 302 */     if (n < 0L) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 303 */     while (n-- != 0L) {
/* 304 */       i.nextByte();
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
/*     */   public void addElements(long index, byte[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[BJJ)V
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
/*     */     //   46: invokestatic get : ([[BJ)B
/*     */     //   49: invokevirtual add : (JB)V
/*     */     //   52: goto -> 20
/*     */     //   55: aload_0
/*     */     //   56: lload_1
/*     */     //   57: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/bytes/ByteBigListIterator;
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
/*     */     //   84: invokestatic get : ([[BJ)B
/*     */     //   87: invokeinterface add : (B)V
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
/*     */     //   62	33	8	iter	Lit/unimi/dsi/fastutil/bytes/ByteBigListIterator;
/*     */     //   0	96	0	this	Lit/unimi/dsi/fastutil/bytes/AbstractByteBigList;
/*     */     //   0	96	1	index	J
/*     */     //   0	96	3	a	[[B
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
/*     */   public void addElements(long index, byte[][] a) {
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
/*     */   public void getElements(long from, byte[][] a, long offset, long length) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual ensureIndex : (J)V
/*     */     //   5: aload_3
/*     */     //   6: lload #4
/*     */     //   8: lload #6
/*     */     //   10: invokestatic ensureOffsetLength : ([[BJJ)V
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
/*     */     //   110: invokevirtual getByte : (J)B
/*     */     //   113: invokestatic set : ([[BJB)V
/*     */     //   116: goto -> 82
/*     */     //   119: goto -> 162
/*     */     //   122: aload_0
/*     */     //   123: lload_1
/*     */     //   124: invokevirtual listIterator : (J)Lit/unimi/dsi/fastutil/bytes/ByteBigListIterator;
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
/*     */     //   151: invokeinterface nextByte : ()B
/*     */     //   156: invokestatic set : ([[BJB)V
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
/*     */     //   129	33	8	i	Lit/unimi/dsi/fastutil/bytes/ByteBigListIterator;
/*     */     //   0	163	0	this	Lit/unimi/dsi/fastutil/bytes/AbstractByteBigList;
/*     */     //   0	163	1	from	J
/*     */     //   0	163	3	a	[[B
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
/*     */   public void setElements(long index, byte[][] a, long offset, long length) {
/* 361 */     ensureIndex(index);
/* 362 */     BigArrays.ensureOffsetLength(a, offset, length);
/* 363 */     if (index + length > size64()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size64() + ")"); 
/* 364 */     if (this instanceof RandomAccess) {
/* 365 */       long i; for (i = 0L; i < length; i++) {
/* 366 */         set(i + index, BigArrays.get(a, i + offset));
/*     */       }
/*     */     } else {
/* 369 */       ByteBigListIterator iter = listIterator(index);
/* 370 */       long i = 0L;
/* 371 */       if (i < length) {
/* 372 */         iter.nextByte();
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
/* 407 */     ByteIterator i = iterator();
/* 408 */     int h = 1;
/* 409 */     long s = size64();
/* 410 */     while (s-- != 0L) {
/* 411 */       byte k = i.nextByte();
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
/* 424 */     if (l instanceof ByteBigList) {
/* 425 */       ByteBigListIterator byteBigListIterator1 = listIterator(), byteBigListIterator2 = ((ByteBigList)l).listIterator();
/* 426 */       while (s-- != 0L) { if (byteBigListIterator1.nextByte() != byteBigListIterator2.nextByte()) return false;  }
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
/*     */   public int compareTo(BigList<? extends Byte> l) {
/* 446 */     if (l == this) return 0; 
/* 447 */     if (l instanceof ByteBigList) {
/* 448 */       ByteBigListIterator byteBigListIterator1 = listIterator(), byteBigListIterator2 = ((ByteBigList)l).listIterator();
/*     */ 
/*     */       
/* 451 */       while (byteBigListIterator1.hasNext() && byteBigListIterator2.hasNext()) {
/* 452 */         byte e1 = byteBigListIterator1.nextByte();
/* 453 */         byte e2 = byteBigListIterator2.nextByte(); int r;
/* 454 */         if ((r = Byte.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 456 */       return byteBigListIterator2.hasNext() ? -1 : (byteBigListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 458 */     BigListIterator<? extends Byte> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 460 */     while (i1.hasNext() && i2.hasNext()) {
/* 461 */       int r; if ((r = ((Comparable<Object>)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 463 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(byte o) {
/* 468 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte popByte() {
/* 473 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 474 */     return removeByte(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte topByte() {
/* 479 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 480 */     return getByte(size64() - 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte peekByte(int i) {
/* 485 */     return getByte(size64() - 1L - i);
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
/*     */   public boolean rem(byte k) {
/* 497 */     long index = indexOf(k);
/* 498 */     if (index == -1L) return false; 
/* 499 */     removeByte(index);
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
/*     */   public boolean addAll(long index, ByteCollection c) {
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
/*     */   public boolean addAll(ByteCollection c) {
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
/*     */   public void add(long index, Byte ok) {
/* 534 */     add(index, ok.byteValue());
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
/*     */   public Byte set(long index, Byte ok) {
/* 546 */     return Byte.valueOf(set(index, ok.byteValue()));
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
/*     */   public Byte get(long index) {
/* 558 */     return Byte.valueOf(getByte(index));
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
/* 570 */     return indexOf(((Byte)ok).byteValue());
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
/* 582 */     return lastIndexOf(((Byte)ok).byteValue());
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
/*     */   public Byte remove(long index) {
/* 594 */     return Byte.valueOf(removeByte(index));
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
/*     */   public void push(Byte o) {
/* 606 */     push(o.byteValue());
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
/*     */   public Byte pop() {
/* 618 */     return Byte.valueOf(popByte());
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
/*     */   public Byte top() {
/* 630 */     return Byte.valueOf(topByte());
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
/*     */   public Byte peek(int i) {
/* 642 */     return Byte.valueOf(peekByte(i));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 647 */     StringBuilder s = new StringBuilder();
/* 648 */     ByteIterator i = iterator();
/* 649 */     long n = size64();
/*     */     
/* 651 */     boolean first = true;
/* 652 */     s.append("[");
/* 653 */     while (n-- != 0L) {
/* 654 */       if (first) { first = false; }
/* 655 */       else { s.append(", "); }
/* 656 */        byte k = i.nextByte();
/* 657 */       s.append(String.valueOf(k));
/*     */     } 
/* 659 */     s.append("]");
/* 660 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ByteSubList
/*     */     extends AbstractByteBigList
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ByteBigList l;
/*     */     protected final long from;
/*     */     protected long to;
/*     */     
/*     */     public ByteSubList(ByteBigList l, long from, long to) {
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
/*     */     public boolean add(byte k) {
/* 688 */       this.l.add(this.to, k);
/* 689 */       this.to++;
/* 690 */       assert assertRange();
/* 691 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(long index, byte k) {
/* 696 */       ensureIndex(index);
/* 697 */       this.l.add(this.from + index, k);
/* 698 */       this.to++;
/* 699 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends Byte> c) {
/* 704 */       ensureIndex(index);
/* 705 */       this.to += c.size();
/* 706 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getByte(long index) {
/* 711 */       ensureRestrictedIndex(index);
/* 712 */       return this.l.getByte(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte removeByte(long index) {
/* 717 */       ensureRestrictedIndex(index);
/* 718 */       this.to--;
/* 719 */       return this.l.removeByte(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte set(long index, byte k) {
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
/*     */     public void getElements(long from, byte[][] a, long offset, long length) {
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
/*     */     public void addElements(long index, byte[][] a, long offset, long length) {
/* 751 */       ensureIndex(index);
/* 752 */       this.l.addElements(this.from + index, a, offset, length);
/* 753 */       this.to += length;
/* 754 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends ByteBigListIterators.AbstractIndexBasedBigListIterator
/*     */     {
/*     */       RandomAccessIter(long pos) {
/* 763 */         super(0L, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final byte get(long i) {
/* 768 */         return AbstractByteBigList.ByteSubList.this.l.getByte(AbstractByteBigList.ByteSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(long i, byte k) {
/* 774 */         AbstractByteBigList.ByteSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(long i, byte k) {
/* 779 */         AbstractByteBigList.ByteSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(long i) {
/* 784 */         AbstractByteBigList.ByteSubList.this.removeByte(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final long getMaxPos() {
/* 789 */         return AbstractByteBigList.ByteSubList.this.to - AbstractByteBigList.ByteSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(byte k) {
/* 794 */         super.add(k);
/* 795 */         assert AbstractByteBigList.ByteSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 800 */         super.remove();
/* 801 */         assert AbstractByteBigList.ByteSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements ByteBigListIterator {
/*     */       private ByteBigListIterator parent;
/*     */       
/*     */       ParentWrappingIter(ByteBigListIterator parent) {
/* 809 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public long nextIndex() {
/* 814 */         return this.parent.nextIndex() - AbstractByteBigList.ByteSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public long previousIndex() {
/* 819 */         return this.parent.previousIndex() - AbstractByteBigList.ByteSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 824 */         return (this.parent.nextIndex() < AbstractByteBigList.ByteSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 829 */         return (this.parent.previousIndex() >= AbstractByteBigList.ByteSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public byte nextByte() {
/* 834 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 835 */         return this.parent.nextByte();
/*     */       }
/*     */ 
/*     */       
/*     */       public byte previousByte() {
/* 840 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 841 */         return this.parent.previousByte();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(byte k) {
/* 846 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(byte k) {
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
/* 867 */         if (parentNewPos < AbstractByteBigList.ByteSubList.this.from - 1L) parentNewPos = AbstractByteBigList.ByteSubList.this.from - 1L; 
/* 868 */         long toSkip = parentNewPos - currentPos;
/* 869 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public long skip(long n) {
/* 874 */         if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 875 */         long currentPos = this.parent.nextIndex();
/* 876 */         long parentNewPos = currentPos + n;
/* 877 */         if (parentNewPos > AbstractByteBigList.ByteSubList.this.to) parentNewPos = AbstractByteBigList.ByteSubList.this.to; 
/* 878 */         long toSkip = parentNewPos - currentPos;
/* 879 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBigListIterator listIterator(long index) {
/* 885 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 890 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 895 */       return (this.l instanceof RandomAccess) ? new AbstractByteBigList.IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 900 */       if (this.l instanceof RandomAccess) {
/* 901 */         return ByteSpliterators.widen(spliterator());
/*     */       }
/* 903 */       return super.intSpliterator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ByteBigList subList(long from, long to) {
/* 909 */       ensureIndex(from);
/* 910 */       ensureIndex(to);
/* 911 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 914 */       return new ByteSubList(this, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(byte k) {
/* 919 */       long index = indexOf(k);
/* 920 */       if (index == -1L) return false; 
/* 921 */       this.to--;
/* 922 */       this.l.removeByte(this.from + index);
/* 923 */       assert assertRange();
/* 924 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, ByteCollection c) {
/* 929 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(long index, ByteBigList l) {
/* 934 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ByteRandomAccessSubList extends ByteSubList implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public ByteRandomAccessSubList(ByteBigList l, long from, long to) {
/* 942 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBigList subList(long from, long to) {
/* 947 */       ensureIndex(from);
/* 948 */       ensureIndex(to);
/* 949 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 952 */       return new ByteRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByteBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */