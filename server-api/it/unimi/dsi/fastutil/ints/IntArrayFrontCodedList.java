/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrays;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.RandomAccess;
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
/*     */ public class IntArrayFrontCodedList
/*     */   extends AbstractObjectList<int[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final int[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public IntArrayFrontCodedList(Iterator<int[]> arrays, int ratio) {
/* 107 */     if (ratio < 1) throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 108 */     int[][] array = IntBigArrays.EMPTY_BIG_ARRAY;
/* 109 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 110 */     int[][] a = new int[2][];
/* 111 */     long curSize = 0L;
/* 112 */     int n = 0, b = 0;
/* 113 */     while (arrays.hasNext()) {
/* 114 */       a[b] = arrays.next();
/* 115 */       int length = (a[b]).length;
/* 116 */       if (n % ratio == 0) {
/* 117 */         p = LongArrays.grow(p, n / ratio + 1);
/* 118 */         p[n / ratio] = curSize;
/* 119 */         array = BigArrays.grow(array, curSize + count(length) + length, curSize);
/* 120 */         curSize += writeInt(array, length, curSize);
/* 121 */         BigArrays.copyToBig(a[b], 0, array, curSize, length);
/* 122 */         curSize += length;
/*     */       } else {
/* 124 */         int minLength = Math.min((a[1 - b]).length, length);
/*     */         int common;
/* 126 */         for (common = 0; common < minLength && a[0][common] == a[1][common]; common++);
/* 127 */         length -= common;
/* 128 */         array = BigArrays.grow(array, curSize + count(length) + count(common) + length, curSize);
/* 129 */         curSize += writeInt(array, length, curSize);
/* 130 */         curSize += writeInt(array, common, curSize);
/* 131 */         BigArrays.copyToBig(a[b], common, array, curSize, length);
/* 132 */         curSize += length;
/*     */       } 
/* 134 */       b = 1 - b;
/* 135 */       n++;
/*     */     } 
/* 137 */     this.n = n;
/* 138 */     this.ratio = ratio;
/* 139 */     this.array = BigArrays.trim(array, curSize);
/* 140 */     this.p = LongArrays.trim(p, (n + ratio - 1) / ratio);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayFrontCodedList(Collection<int[]> c, int ratio) {
/* 150 */     this((Iterator)c.iterator(), ratio);
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
/*     */   static int readInt(int[][] a, long pos) {
/* 163 */     return BigArrays.get(a, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int count(int length) {
/* 173 */     return 1;
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
/*     */   static int writeInt(int[][] a, int length, long pos) {
/* 185 */     BigArrays.set(a, pos, length);
/* 186 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ratio() {
/* 195 */     return this.ratio;
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
/*     */   private int length(int index) {
/* 209 */     int[][] array = this.array;
/* 210 */     int delta = index % this.ratio;
/* 211 */     long pos = this.p[index / this.ratio];
/* 212 */     int length = readInt(array, pos);
/* 213 */     if (delta == 0) return length;
/*     */ 
/*     */     
/* 216 */     pos += (count(length) + length);
/* 217 */     length = readInt(array, pos);
/* 218 */     int common = readInt(array, pos + count(length));
/* 219 */     for (int i = 0; i < delta - 1; i++) {
/* 220 */       pos += (count(length) + count(common) + length);
/* 221 */       length = readInt(array, pos);
/* 222 */       common = readInt(array, pos + count(length));
/*     */     } 
/* 224 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(int index) {
/* 234 */     ensureRestrictedIndex(index);
/* 235 */     return length(index);
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
/*     */   private int extract(int index, int[] a, int offset, int length) {
/* 248 */     int delta = index % this.ratio;
/* 249 */     long startPos = this.p[index / this.ratio];
/*     */     
/*     */     long pos;
/* 252 */     int arrayLength = readInt(this.array, pos = startPos), currLen = 0;
/* 253 */     if (delta == 0) {
/* 254 */       pos = this.p[index / this.ratio] + count(arrayLength);
/* 255 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 256 */       return arrayLength;
/*     */     } 
/* 258 */     int common = 0;
/* 259 */     for (int i = 0; i < delta; i++) {
/* 260 */       long prevArrayPos = pos + count(arrayLength) + ((i != 0) ? count(common) : 0L);
/* 261 */       pos = prevArrayPos + arrayLength;
/* 262 */       arrayLength = readInt(this.array, pos);
/* 263 */       common = readInt(this.array, pos + count(arrayLength));
/* 264 */       int actualCommon = Math.min(common, length);
/* 265 */       if (actualCommon <= currLen) { currLen = actualCommon; }
/*     */       else
/* 267 */       { BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 268 */         currLen = actualCommon; }
/*     */     
/*     */     } 
/* 271 */     if (currLen < length) BigArrays.copyFromBig(this.array, pos + count(arrayLength) + count(common), a, currLen + offset, Math.min(arrayLength, length - currLen)); 
/* 272 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] get(int index) {
/* 282 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getArray(int index) {
/* 292 */     ensureRestrictedIndex(index);
/* 293 */     int length = length(index);
/* 294 */     int[] a = new int[length];
/* 295 */     extract(index, a, 0, length);
/* 296 */     return a;
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
/*     */   public int get(int index, int[] a, int offset, int length) {
/* 310 */     ensureRestrictedIndex(index);
/* 311 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 312 */     int arrayLength = extract(index, a, offset, length);
/* 313 */     if (length >= arrayLength) return arrayLength; 
/* 314 */     return length - arrayLength;
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
/*     */   public int get(int index, int[] a) {
/* 327 */     return get(index, a, 0, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 332 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectListIterator<int[]> listIterator(final int start) {
/* 337 */     ensureIndex(start);
/* 338 */     return new ObjectListIterator<int[]>() {
/* 339 */         int[] s = IntArrays.EMPTY_ARRAY;
/* 340 */         int i = 0;
/* 341 */         long pos = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         boolean inSync;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 357 */           return (this.i < IntArrayFrontCodedList.this.n);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 362 */           return (this.i > 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 367 */           return this.i - 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 372 */           return this.i;
/*     */         }
/*     */ 
/*     */         
/*     */         public int[] next() {
/*     */           int length;
/* 378 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 379 */           if (this.i % IntArrayFrontCodedList.this.ratio == 0) {
/* 380 */             this.pos = IntArrayFrontCodedList.this.p[this.i / IntArrayFrontCodedList.this.ratio];
/* 381 */             length = IntArrayFrontCodedList.readInt(IntArrayFrontCodedList.this.array, this.pos);
/* 382 */             this.s = IntArrays.ensureCapacity(this.s, length, 0);
/* 383 */             BigArrays.copyFromBig(IntArrayFrontCodedList.this.array, this.pos + IntArrayFrontCodedList.count(length), this.s, 0, length);
/* 384 */             this.pos += (length + IntArrayFrontCodedList.count(length));
/* 385 */             this.inSync = true;
/*     */           }
/* 387 */           else if (this.inSync) {
/* 388 */             length = IntArrayFrontCodedList.readInt(IntArrayFrontCodedList.this.array, this.pos);
/* 389 */             int common = IntArrayFrontCodedList.readInt(IntArrayFrontCodedList.this.array, this.pos + IntArrayFrontCodedList.count(length));
/* 390 */             this.s = IntArrays.ensureCapacity(this.s, length + common, common);
/* 391 */             BigArrays.copyFromBig(IntArrayFrontCodedList.this.array, this.pos + IntArrayFrontCodedList.count(length) + IntArrayFrontCodedList.count(common), this.s, common, length);
/* 392 */             this.pos += (IntArrayFrontCodedList.count(length) + IntArrayFrontCodedList.count(common) + length);
/* 393 */             length += common;
/*     */           } else {
/* 395 */             this.s = IntArrays.ensureCapacity(this.s, length = IntArrayFrontCodedList.this.length(this.i), 0);
/* 396 */             IntArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 399 */           this.i++;
/* 400 */           return IntArrays.copy(this.s, 0, length);
/*     */         }
/*     */ 
/*     */         
/*     */         public int[] previous() {
/* 405 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 406 */           this.inSync = false;
/* 407 */           return IntArrayFrontCodedList.this.getArray(--this.i);
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
/*     */   public IntArrayFrontCodedList clone() {
/* 419 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 424 */     StringBuffer s = new StringBuffer();
/* 425 */     s.append("[");
/* 426 */     for (int i = 0; i < this.n; i++) {
/* 427 */       if (i != 0) s.append(", "); 
/* 428 */       s.append(IntArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 430 */     s.append("]");
/* 431 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[] rebuildPointerArray() {
/* 441 */     long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
/* 442 */     int[][] a = this.array;
/*     */     
/* 444 */     long pos = 0L;
/* 445 */     for (int i = 0, j = 0, skip = this.ratio - 1; i < this.n; i++) {
/* 446 */       int length = readInt(a, pos);
/* 447 */       int count = count(length);
/* 448 */       if (++skip == this.ratio)
/* 449 */       { skip = 0;
/* 450 */         p[j++] = pos;
/* 451 */         pos += (count + length); }
/* 452 */       else { pos += (count + count(readInt(a, pos + count)) + length); }
/*     */     
/* 454 */     }  return p;
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 458 */     s.defaultReadObject();
/*     */     
/* 460 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */