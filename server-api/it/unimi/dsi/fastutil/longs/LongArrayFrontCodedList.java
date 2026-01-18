/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
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
/*     */ public class LongArrayFrontCodedList
/*     */   extends AbstractObjectList<long[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final long[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public LongArrayFrontCodedList(Iterator<long[]> arrays, int ratio) {
/* 106 */     if (ratio < 1) throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 107 */     long[][] array = LongBigArrays.EMPTY_BIG_ARRAY;
/* 108 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 109 */     long[][] a = new long[2][];
/* 110 */     long curSize = 0L;
/* 111 */     int n = 0, b = 0;
/* 112 */     while (arrays.hasNext()) {
/* 113 */       a[b] = arrays.next();
/* 114 */       int length = (a[b]).length;
/* 115 */       if (n % ratio == 0) {
/* 116 */         p = LongArrays.grow(p, n / ratio + 1);
/* 117 */         p[n / ratio] = curSize;
/* 118 */         array = BigArrays.grow(array, curSize + count(length) + length, curSize);
/* 119 */         curSize += writeInt(array, length, curSize);
/* 120 */         BigArrays.copyToBig(a[b], 0, array, curSize, length);
/* 121 */         curSize += length;
/*     */       } else {
/* 123 */         int minLength = Math.min((a[1 - b]).length, length);
/*     */         int common;
/* 125 */         for (common = 0; common < minLength && a[0][common] == a[1][common]; common++);
/* 126 */         length -= common;
/* 127 */         array = BigArrays.grow(array, curSize + count(length) + count(common) + length, curSize);
/* 128 */         curSize += writeInt(array, length, curSize);
/* 129 */         curSize += writeInt(array, common, curSize);
/* 130 */         BigArrays.copyToBig(a[b], common, array, curSize, length);
/* 131 */         curSize += length;
/*     */       } 
/* 133 */       b = 1 - b;
/* 134 */       n++;
/*     */     } 
/* 136 */     this.n = n;
/* 137 */     this.ratio = ratio;
/* 138 */     this.array = BigArrays.trim(array, curSize);
/* 139 */     this.p = LongArrays.trim(p, (n + ratio - 1) / ratio);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArrayFrontCodedList(Collection<long[]> c, int ratio) {
/* 149 */     this((Iterator)c.iterator(), ratio);
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
/*     */   static int readInt(long[][] a, long pos) {
/* 162 */     return (int)BigArrays.get(a, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int count(int length) {
/* 172 */     return 1;
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
/*     */   static int writeInt(long[][] a, int length, long pos) {
/* 184 */     BigArrays.set(a, pos, length);
/* 185 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ratio() {
/* 194 */     return this.ratio;
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
/* 208 */     long[][] array = this.array;
/* 209 */     int delta = index % this.ratio;
/* 210 */     long pos = this.p[index / this.ratio];
/* 211 */     int length = readInt(array, pos);
/* 212 */     if (delta == 0) return length;
/*     */ 
/*     */     
/* 215 */     pos += (count(length) + length);
/* 216 */     length = readInt(array, pos);
/* 217 */     int common = readInt(array, pos + count(length));
/* 218 */     for (int i = 0; i < delta - 1; i++) {
/* 219 */       pos += (count(length) + count(common) + length);
/* 220 */       length = readInt(array, pos);
/* 221 */       common = readInt(array, pos + count(length));
/*     */     } 
/* 223 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(int index) {
/* 233 */     ensureRestrictedIndex(index);
/* 234 */     return length(index);
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
/*     */   private int extract(int index, long[] a, int offset, int length) {
/* 247 */     int delta = index % this.ratio;
/* 248 */     long startPos = this.p[index / this.ratio];
/*     */     
/*     */     long pos;
/* 251 */     int arrayLength = readInt(this.array, pos = startPos), currLen = 0;
/* 252 */     if (delta == 0) {
/* 253 */       pos = this.p[index / this.ratio] + count(arrayLength);
/* 254 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 255 */       return arrayLength;
/*     */     } 
/* 257 */     int common = 0;
/* 258 */     for (int i = 0; i < delta; i++) {
/* 259 */       long prevArrayPos = pos + count(arrayLength) + ((i != 0) ? count(common) : 0L);
/* 260 */       pos = prevArrayPos + arrayLength;
/* 261 */       arrayLength = readInt(this.array, pos);
/* 262 */       common = readInt(this.array, pos + count(arrayLength));
/* 263 */       int actualCommon = Math.min(common, length);
/* 264 */       if (actualCommon <= currLen) { currLen = actualCommon; }
/*     */       else
/* 266 */       { BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 267 */         currLen = actualCommon; }
/*     */     
/*     */     } 
/* 270 */     if (currLen < length) BigArrays.copyFromBig(this.array, pos + count(arrayLength) + count(common), a, currLen + offset, Math.min(arrayLength, length - currLen)); 
/* 271 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] get(int index) {
/* 281 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] getArray(int index) {
/* 291 */     ensureRestrictedIndex(index);
/* 292 */     int length = length(index);
/* 293 */     long[] a = new long[length];
/* 294 */     extract(index, a, 0, length);
/* 295 */     return a;
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
/*     */   public int get(int index, long[] a, int offset, int length) {
/* 309 */     ensureRestrictedIndex(index);
/* 310 */     LongArrays.ensureOffsetLength(a, offset, length);
/* 311 */     int arrayLength = extract(index, a, offset, length);
/* 312 */     if (length >= arrayLength) return arrayLength; 
/* 313 */     return length - arrayLength;
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
/*     */   public int get(int index, long[] a) {
/* 326 */     return get(index, a, 0, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 331 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectListIterator<long[]> listIterator(final int start) {
/* 336 */     ensureIndex(start);
/* 337 */     return new ObjectListIterator<long[]>() {
/* 338 */         long[] s = LongArrays.EMPTY_ARRAY;
/* 339 */         int i = 0;
/* 340 */         long pos = 0L;
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
/* 356 */           return (this.i < LongArrayFrontCodedList.this.n);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 361 */           return (this.i > 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 366 */           return this.i - 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 371 */           return this.i;
/*     */         }
/*     */ 
/*     */         
/*     */         public long[] next() {
/*     */           int length;
/* 377 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 378 */           if (this.i % LongArrayFrontCodedList.this.ratio == 0) {
/* 379 */             this.pos = LongArrayFrontCodedList.this.p[this.i / LongArrayFrontCodedList.this.ratio];
/* 380 */             length = LongArrayFrontCodedList.readInt(LongArrayFrontCodedList.this.array, this.pos);
/* 381 */             this.s = LongArrays.ensureCapacity(this.s, length, 0);
/* 382 */             BigArrays.copyFromBig(LongArrayFrontCodedList.this.array, this.pos + LongArrayFrontCodedList.count(length), this.s, 0, length);
/* 383 */             this.pos += (length + LongArrayFrontCodedList.count(length));
/* 384 */             this.inSync = true;
/*     */           }
/* 386 */           else if (this.inSync) {
/* 387 */             length = LongArrayFrontCodedList.readInt(LongArrayFrontCodedList.this.array, this.pos);
/* 388 */             int common = LongArrayFrontCodedList.readInt(LongArrayFrontCodedList.this.array, this.pos + LongArrayFrontCodedList.count(length));
/* 389 */             this.s = LongArrays.ensureCapacity(this.s, length + common, common);
/* 390 */             BigArrays.copyFromBig(LongArrayFrontCodedList.this.array, this.pos + LongArrayFrontCodedList.count(length) + LongArrayFrontCodedList.count(common), this.s, common, length);
/* 391 */             this.pos += (LongArrayFrontCodedList.count(length) + LongArrayFrontCodedList.count(common) + length);
/* 392 */             length += common;
/*     */           } else {
/* 394 */             this.s = LongArrays.ensureCapacity(this.s, length = LongArrayFrontCodedList.this.length(this.i), 0);
/* 395 */             LongArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 398 */           this.i++;
/* 399 */           return LongArrays.copy(this.s, 0, length);
/*     */         }
/*     */ 
/*     */         
/*     */         public long[] previous() {
/* 404 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 405 */           this.inSync = false;
/* 406 */           return LongArrayFrontCodedList.this.getArray(--this.i);
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
/*     */   public LongArrayFrontCodedList clone() {
/* 418 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 423 */     StringBuffer s = new StringBuffer();
/* 424 */     s.append("[");
/* 425 */     for (int i = 0; i < this.n; i++) {
/* 426 */       if (i != 0) s.append(", "); 
/* 427 */       s.append(LongArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 429 */     s.append("]");
/* 430 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[] rebuildPointerArray() {
/* 440 */     long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
/* 441 */     long[][] a = this.array;
/*     */     
/* 443 */     long pos = 0L;
/* 444 */     for (int i = 0, j = 0, skip = this.ratio - 1; i < this.n; i++) {
/* 445 */       int length = readInt(a, pos);
/* 446 */       int count = count(length);
/* 447 */       if (++skip == this.ratio)
/* 448 */       { skip = 0;
/* 449 */         p[j++] = pos;
/* 450 */         pos += (count + length); }
/* 451 */       else { pos += (count + count(readInt(a, pos + count)) + length); }
/*     */     
/* 453 */     }  return p;
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 457 */     s.defaultReadObject();
/*     */     
/* 459 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */