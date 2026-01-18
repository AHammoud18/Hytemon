/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectBigList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBigListIterator;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ public class LongArrayFrontCodedBigList
/*     */   extends AbstractObjectBigList<long[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final long n;
/*     */   protected final int ratio;
/*     */   protected final long[][] array;
/*     */   protected transient long[][] p;
/*     */   
/*     */   public LongArrayFrontCodedBigList(Iterator<long[]> arrays, int ratio) {
/* 109 */     if (ratio < 1) throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 110 */     long[][] array = LongBigArrays.EMPTY_BIG_ARRAY;
/* 111 */     long[][] p = LongBigArrays.EMPTY_BIG_ARRAY;
/* 112 */     long[][] a = new long[2][];
/* 113 */     long curSize = 0L;
/* 114 */     long n = 0L;
/* 115 */     int b = 0;
/* 116 */     while (arrays.hasNext()) {
/* 117 */       a[b] = arrays.next();
/* 118 */       int length = (a[b]).length;
/* 119 */       if (n % ratio == 0L) {
/* 120 */         p = BigArrays.grow(p, n / ratio + 1L);
/* 121 */         BigArrays.set(p, n / ratio, curSize);
/* 122 */         array = BigArrays.grow(array, curSize + LongArrayFrontCodedList.count(length) + length, curSize);
/* 123 */         curSize += LongArrayFrontCodedList.writeInt(array, length, curSize);
/* 124 */         BigArrays.copyToBig(a[b], 0, array, curSize, length);
/* 125 */         curSize += length;
/*     */       } else {
/* 127 */         int minLength = Math.min((a[1 - b]).length, length);
/*     */         int common;
/* 129 */         for (common = 0; common < minLength && a[0][common] == a[1][common]; common++);
/* 130 */         length -= common;
/* 131 */         array = BigArrays.grow(array, curSize + LongArrayFrontCodedList.count(length) + LongArrayFrontCodedList.count(common) + length, curSize);
/* 132 */         curSize += LongArrayFrontCodedList.writeInt(array, length, curSize);
/* 133 */         curSize += LongArrayFrontCodedList.writeInt(array, common, curSize);
/* 134 */         BigArrays.copyToBig(a[b], common, array, curSize, length);
/* 135 */         curSize += length;
/*     */       } 
/* 137 */       b = 1 - b;
/* 138 */       n++;
/*     */     } 
/* 140 */     this.n = n;
/* 141 */     this.ratio = ratio;
/* 142 */     this.array = BigArrays.trim(array, curSize);
/* 143 */     this.p = BigArrays.trim(p, (n + ratio - 1L) / ratio);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArrayFrontCodedBigList(Collection<long[]> c, int ratio) {
/* 153 */     this((Iterator)c.iterator(), ratio);
/*     */   }
/*     */   
/*     */   public int ratio() {
/* 157 */     return this.ratio;
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
/*     */   private int length(long index) {
/* 170 */     long[][] array = this.array;
/* 171 */     int delta = (int)(index % this.ratio);
/* 172 */     long pos = BigArrays.get(this.p, index / this.ratio);
/*     */     
/* 174 */     int length = LongArrayFrontCodedList.readInt(array, pos);
/* 175 */     if (delta == 0) return length;
/*     */ 
/*     */     
/* 178 */     pos += (LongArrayFrontCodedList.count(length) + length);
/* 179 */     length = LongArrayFrontCodedList.readInt(array, pos);
/* 180 */     int common = LongArrayFrontCodedList.readInt(array, pos + LongArrayFrontCodedList.count(length));
/* 181 */     for (int i = 0; i < delta - 1; i++) {
/* 182 */       pos += (LongArrayFrontCodedList.count(length) + LongArrayFrontCodedList.count(common) + length);
/* 183 */       length = LongArrayFrontCodedList.readInt(array, pos);
/* 184 */       common = LongArrayFrontCodedList.readInt(array, pos + LongArrayFrontCodedList.count(length));
/*     */     } 
/* 186 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(long index) {
/* 196 */     ensureRestrictedIndex(index);
/* 197 */     return length(index);
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
/*     */   private int extract(long index, long[] a, int offset, int length) {
/* 210 */     int delta = (int)(index % this.ratio);
/* 211 */     long startPos = BigArrays.get(this.p, index / this.ratio);
/*     */     
/*     */     long pos;
/* 214 */     int arrayLength = LongArrayFrontCodedList.readInt(this.array, pos = startPos), currLen = 0;
/* 215 */     if (delta == 0) {
/* 216 */       pos = BigArrays.get(this.p, index / this.ratio) + LongArrayFrontCodedList.count(arrayLength);
/* 217 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 218 */       return arrayLength;
/*     */     } 
/* 220 */     int common = 0;
/* 221 */     for (int i = 0; i < delta; i++) {
/* 222 */       long prevArrayPos = pos + LongArrayFrontCodedList.count(arrayLength) + ((i != 0) ? LongArrayFrontCodedList.count(common) : 0L);
/* 223 */       pos = prevArrayPos + arrayLength;
/* 224 */       arrayLength = LongArrayFrontCodedList.readInt(this.array, pos);
/* 225 */       common = LongArrayFrontCodedList.readInt(this.array, pos + LongArrayFrontCodedList.count(arrayLength));
/* 226 */       int actualCommon = Math.min(common, length);
/* 227 */       if (actualCommon <= currLen) { currLen = actualCommon; }
/*     */       else
/* 229 */       { BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 230 */         currLen = actualCommon; }
/*     */     
/*     */     } 
/* 233 */     if (currLen < length) BigArrays.copyFromBig(this.array, pos + LongArrayFrontCodedList.count(arrayLength) + LongArrayFrontCodedList.count(common), a, currLen + offset, Math.min(arrayLength, length - currLen)); 
/* 234 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] get(long index) {
/* 244 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] getArray(long index) {
/* 254 */     ensureRestrictedIndex(index);
/* 255 */     int length = length(index);
/* 256 */     long[] a = new long[length];
/* 257 */     extract(index, a, 0, length);
/* 258 */     return a;
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
/*     */   public int get(long index, long[] a, int offset, int length) {
/* 272 */     ensureRestrictedIndex(index);
/* 273 */     LongArrays.ensureOffsetLength(a, offset, length);
/* 274 */     int arrayLength = extract(index, a, offset, length);
/* 275 */     if (length >= arrayLength) return arrayLength; 
/* 276 */     return length - arrayLength;
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
/*     */   public int get(long index, long[] a) {
/* 289 */     return get(index, a, 0, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public long size64() {
/* 294 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectBigListIterator<long[]> listIterator(final long start) {
/* 299 */     ensureIndex(start);
/* 300 */     return new ObjectBigListIterator<long[]>() {
/* 301 */         long[] s = LongArrays.EMPTY_ARRAY;
/* 302 */         long i = 0L;
/* 303 */         long pos = 0L;
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
/* 319 */           return (this.i < LongArrayFrontCodedBigList.this.n);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 324 */           return (this.i > 0L);
/*     */         }
/*     */ 
/*     */         
/*     */         public long previousIndex() {
/* 329 */           return this.i - 1L;
/*     */         }
/*     */ 
/*     */         
/*     */         public long nextIndex() {
/* 334 */           return this.i;
/*     */         }
/*     */ 
/*     */         
/*     */         public long[] next() {
/*     */           int length;
/* 340 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 341 */           if (this.i % LongArrayFrontCodedBigList.this.ratio == 0L) {
/* 342 */             this.pos = BigArrays.get(LongArrayFrontCodedBigList.this.p, this.i / LongArrayFrontCodedBigList.this.ratio);
/* 343 */             length = LongArrayFrontCodedList.readInt(LongArrayFrontCodedBigList.this.array, this.pos);
/* 344 */             this.s = LongArrays.ensureCapacity(this.s, length, 0);
/* 345 */             BigArrays.copyFromBig(LongArrayFrontCodedBigList.this.array, this.pos + LongArrayFrontCodedList.count(length), this.s, 0, length);
/* 346 */             this.pos += (length + LongArrayFrontCodedList.count(length));
/* 347 */             this.inSync = true;
/*     */           }
/* 349 */           else if (this.inSync) {
/* 350 */             length = LongArrayFrontCodedList.readInt(LongArrayFrontCodedBigList.this.array, this.pos);
/* 351 */             int common = LongArrayFrontCodedList.readInt(LongArrayFrontCodedBigList.this.array, this.pos + LongArrayFrontCodedList.count(length));
/* 352 */             this.s = LongArrays.ensureCapacity(this.s, length + common, common);
/* 353 */             BigArrays.copyFromBig(LongArrayFrontCodedBigList.this.array, this.pos + LongArrayFrontCodedList.count(length) + LongArrayFrontCodedList.count(common), this.s, common, length);
/* 354 */             this.pos += (LongArrayFrontCodedList.count(length) + LongArrayFrontCodedList.count(common) + length);
/* 355 */             length += common;
/*     */           } else {
/* 357 */             this.s = LongArrays.ensureCapacity(this.s, length = LongArrayFrontCodedBigList.this.length(this.i), 0);
/* 358 */             LongArrayFrontCodedBigList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 361 */           this.i++;
/* 362 */           return LongArrays.copy(this.s, 0, length);
/*     */         }
/*     */ 
/*     */         
/*     */         public long[] previous() {
/* 367 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 368 */           this.inSync = false;
/* 369 */           return LongArrayFrontCodedBigList.this.getArray(--this.i);
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
/*     */   public LongArrayFrontCodedBigList clone() {
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 386 */     StringBuffer s = new StringBuffer();
/* 387 */     s.append("[");
/* 388 */     for (long i = 0L; i < this.n; i++) {
/* 389 */       if (i != 0L) s.append(", "); 
/* 390 */       s.append(LongArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 392 */     s.append("]");
/* 393 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[][] rebuildPointerArray() {
/* 403 */     long[][] p = LongBigArrays.newBigArray((this.n + this.ratio - 1L) / this.ratio);
/* 404 */     long[][] a = this.array;
/*     */     
/* 406 */     long pos = 0L;
/* 407 */     int skip = this.ratio - 1; long i, j;
/* 408 */     for (i = 0L, j = 0L; i < this.n; i++) {
/* 409 */       int length = LongArrayFrontCodedList.readInt(a, pos);
/* 410 */       int count = LongArrayFrontCodedList.count(length);
/*     */       
/* 412 */       skip = 0;
/* 413 */       BigArrays.set(p, j++, pos);
/* 414 */       pos += (count + length);
/* 415 */       pos += (count + LongArrayFrontCodedList.count(LongArrayFrontCodedList.readInt(a, pos + count)) + length);
/*     */     } 
/* 417 */     return p; } public void dump(DataOutputStream array, DataOutputStream pointers) throws IOException {
/*     */     long[][] arrayOfLong;
/*     */     int i;
/*     */     byte b;
/* 421 */     for (arrayOfLong = this.array, i = arrayOfLong.length, b = 0; b < i; ) { long[] s; for (long e : s = arrayOfLong[b]) array.writeLong(e);  b++; }
/* 422 */      for (arrayOfLong = this.p, i = arrayOfLong.length, b = 0; b < i; ) { long[] s; for (long e : s = arrayOfLong[b]) pointers.writeLong(e);  b++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 426 */     s.defaultReadObject();
/*     */     
/* 428 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongArrayFrontCodedBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */