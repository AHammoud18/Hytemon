/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongBigArrays;
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
/*     */ public class ShortArrayFrontCodedBigList
/*     */   extends AbstractObjectBigList<short[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final long n;
/*     */   protected final int ratio;
/*     */   protected final short[][] array;
/*     */   protected transient long[][] p;
/*     */   
/*     */   public ShortArrayFrontCodedBigList(Iterator<short[]> arrays, int ratio) {
/* 110 */     if (ratio < 1) throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 111 */     short[][] array = ShortBigArrays.EMPTY_BIG_ARRAY;
/* 112 */     long[][] p = LongBigArrays.EMPTY_BIG_ARRAY;
/* 113 */     short[][] a = new short[2][];
/* 114 */     long curSize = 0L;
/* 115 */     long n = 0L;
/* 116 */     int b = 0;
/* 117 */     while (arrays.hasNext()) {
/* 118 */       a[b] = arrays.next();
/* 119 */       int length = (a[b]).length;
/* 120 */       if (n % ratio == 0L) {
/* 121 */         p = BigArrays.grow(p, n / ratio + 1L);
/* 122 */         BigArrays.set(p, n / ratio, curSize);
/* 123 */         array = BigArrays.grow(array, curSize + ShortArrayFrontCodedList.count(length) + length, curSize);
/* 124 */         curSize += ShortArrayFrontCodedList.writeInt(array, length, curSize);
/* 125 */         BigArrays.copyToBig(a[b], 0, array, curSize, length);
/* 126 */         curSize += length;
/*     */       } else {
/* 128 */         int minLength = Math.min((a[1 - b]).length, length);
/*     */         int common;
/* 130 */         for (common = 0; common < minLength && a[0][common] == a[1][common]; common++);
/* 131 */         length -= common;
/* 132 */         array = BigArrays.grow(array, curSize + ShortArrayFrontCodedList.count(length) + ShortArrayFrontCodedList.count(common) + length, curSize);
/* 133 */         curSize += ShortArrayFrontCodedList.writeInt(array, length, curSize);
/* 134 */         curSize += ShortArrayFrontCodedList.writeInt(array, common, curSize);
/* 135 */         BigArrays.copyToBig(a[b], common, array, curSize, length);
/* 136 */         curSize += length;
/*     */       } 
/* 138 */       b = 1 - b;
/* 139 */       n++;
/*     */     } 
/* 141 */     this.n = n;
/* 142 */     this.ratio = ratio;
/* 143 */     this.array = BigArrays.trim(array, curSize);
/* 144 */     this.p = BigArrays.trim(p, (n + ratio - 1L) / ratio);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortArrayFrontCodedBigList(Collection<short[]> c, int ratio) {
/* 154 */     this((Iterator)c.iterator(), ratio);
/*     */   }
/*     */   
/*     */   public int ratio() {
/* 158 */     return this.ratio;
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
/* 171 */     short[][] array = this.array;
/* 172 */     int delta = (int)(index % this.ratio);
/* 173 */     long pos = BigArrays.get(this.p, index / this.ratio);
/*     */     
/* 175 */     int length = ShortArrayFrontCodedList.readInt(array, pos);
/* 176 */     if (delta == 0) return length;
/*     */ 
/*     */     
/* 179 */     pos += (ShortArrayFrontCodedList.count(length) + length);
/* 180 */     length = ShortArrayFrontCodedList.readInt(array, pos);
/* 181 */     int common = ShortArrayFrontCodedList.readInt(array, pos + ShortArrayFrontCodedList.count(length));
/* 182 */     for (int i = 0; i < delta - 1; i++) {
/* 183 */       pos += (ShortArrayFrontCodedList.count(length) + ShortArrayFrontCodedList.count(common) + length);
/* 184 */       length = ShortArrayFrontCodedList.readInt(array, pos);
/* 185 */       common = ShortArrayFrontCodedList.readInt(array, pos + ShortArrayFrontCodedList.count(length));
/*     */     } 
/* 187 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(long index) {
/* 197 */     ensureRestrictedIndex(index);
/* 198 */     return length(index);
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
/*     */   private int extract(long index, short[] a, int offset, int length) {
/* 211 */     int delta = (int)(index % this.ratio);
/* 212 */     long startPos = BigArrays.get(this.p, index / this.ratio);
/*     */     
/*     */     long pos;
/* 215 */     int arrayLength = ShortArrayFrontCodedList.readInt(this.array, pos = startPos), currLen = 0;
/* 216 */     if (delta == 0) {
/* 217 */       pos = BigArrays.get(this.p, index / this.ratio) + ShortArrayFrontCodedList.count(arrayLength);
/* 218 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 219 */       return arrayLength;
/*     */     } 
/* 221 */     int common = 0;
/* 222 */     for (int i = 0; i < delta; i++) {
/* 223 */       long prevArrayPos = pos + ShortArrayFrontCodedList.count(arrayLength) + ((i != 0) ? ShortArrayFrontCodedList.count(common) : 0L);
/* 224 */       pos = prevArrayPos + arrayLength;
/* 225 */       arrayLength = ShortArrayFrontCodedList.readInt(this.array, pos);
/* 226 */       common = ShortArrayFrontCodedList.readInt(this.array, pos + ShortArrayFrontCodedList.count(arrayLength));
/* 227 */       int actualCommon = Math.min(common, length);
/* 228 */       if (actualCommon <= currLen) { currLen = actualCommon; }
/*     */       else
/* 230 */       { BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 231 */         currLen = actualCommon; }
/*     */     
/*     */     } 
/* 234 */     if (currLen < length) BigArrays.copyFromBig(this.array, pos + ShortArrayFrontCodedList.count(arrayLength) + ShortArrayFrontCodedList.count(common), a, currLen + offset, Math.min(arrayLength, length - currLen)); 
/* 235 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] get(long index) {
/* 245 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] getArray(long index) {
/* 255 */     ensureRestrictedIndex(index);
/* 256 */     int length = length(index);
/* 257 */     short[] a = new short[length];
/* 258 */     extract(index, a, 0, length);
/* 259 */     return a;
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
/*     */   public int get(long index, short[] a, int offset, int length) {
/* 273 */     ensureRestrictedIndex(index);
/* 274 */     ShortArrays.ensureOffsetLength(a, offset, length);
/* 275 */     int arrayLength = extract(index, a, offset, length);
/* 276 */     if (length >= arrayLength) return arrayLength; 
/* 277 */     return length - arrayLength;
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
/*     */   public int get(long index, short[] a) {
/* 290 */     return get(index, a, 0, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public long size64() {
/* 295 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectBigListIterator<short[]> listIterator(final long start) {
/* 300 */     ensureIndex(start);
/* 301 */     return new ObjectBigListIterator<short[]>() {
/* 302 */         short[] s = ShortArrays.EMPTY_ARRAY;
/* 303 */         long i = 0L;
/* 304 */         long pos = 0L;
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
/* 320 */           return (this.i < ShortArrayFrontCodedBigList.this.n);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 325 */           return (this.i > 0L);
/*     */         }
/*     */ 
/*     */         
/*     */         public long previousIndex() {
/* 330 */           return this.i - 1L;
/*     */         }
/*     */ 
/*     */         
/*     */         public long nextIndex() {
/* 335 */           return this.i;
/*     */         }
/*     */ 
/*     */         
/*     */         public short[] next() {
/*     */           int length;
/* 341 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 342 */           if (this.i % ShortArrayFrontCodedBigList.this.ratio == 0L) {
/* 343 */             this.pos = BigArrays.get(ShortArrayFrontCodedBigList.this.p, this.i / ShortArrayFrontCodedBigList.this.ratio);
/* 344 */             length = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedBigList.this.array, this.pos);
/* 345 */             this.s = ShortArrays.ensureCapacity(this.s, length, 0);
/* 346 */             BigArrays.copyFromBig(ShortArrayFrontCodedBigList.this.array, this.pos + ShortArrayFrontCodedList.count(length), this.s, 0, length);
/* 347 */             this.pos += (length + ShortArrayFrontCodedList.count(length));
/* 348 */             this.inSync = true;
/*     */           }
/* 350 */           else if (this.inSync) {
/* 351 */             length = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedBigList.this.array, this.pos);
/* 352 */             int common = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedBigList.this.array, this.pos + ShortArrayFrontCodedList.count(length));
/* 353 */             this.s = ShortArrays.ensureCapacity(this.s, length + common, common);
/* 354 */             BigArrays.copyFromBig(ShortArrayFrontCodedBigList.this.array, this.pos + ShortArrayFrontCodedList.count(length) + ShortArrayFrontCodedList.count(common), this.s, common, length);
/* 355 */             this.pos += (ShortArrayFrontCodedList.count(length) + ShortArrayFrontCodedList.count(common) + length);
/* 356 */             length += common;
/*     */           } else {
/* 358 */             this.s = ShortArrays.ensureCapacity(this.s, length = ShortArrayFrontCodedBigList.this.length(this.i), 0);
/* 359 */             ShortArrayFrontCodedBigList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 362 */           this.i++;
/* 363 */           return ShortArrays.copy(this.s, 0, length);
/*     */         }
/*     */ 
/*     */         
/*     */         public short[] previous() {
/* 368 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 369 */           this.inSync = false;
/* 370 */           return ShortArrayFrontCodedBigList.this.getArray(--this.i);
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
/*     */   public ShortArrayFrontCodedBigList clone() {
/* 382 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 387 */     StringBuffer s = new StringBuffer();
/* 388 */     s.append("[");
/* 389 */     for (long i = 0L; i < this.n; i++) {
/* 390 */       if (i != 0L) s.append(", "); 
/* 391 */       s.append(ShortArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 393 */     s.append("]");
/* 394 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[][] rebuildPointerArray() {
/* 404 */     long[][] p = LongBigArrays.newBigArray((this.n + this.ratio - 1L) / this.ratio);
/* 405 */     short[][] a = this.array;
/*     */     
/* 407 */     long pos = 0L;
/* 408 */     int skip = this.ratio - 1; long i, j;
/* 409 */     for (i = 0L, j = 0L; i < this.n; i++) {
/* 410 */       int length = ShortArrayFrontCodedList.readInt(a, pos);
/* 411 */       int count = ShortArrayFrontCodedList.count(length);
/*     */       
/* 413 */       skip = 0;
/* 414 */       BigArrays.set(p, j++, pos);
/* 415 */       pos += (count + length);
/* 416 */       pos += (count + ShortArrayFrontCodedList.count(ShortArrayFrontCodedList.readInt(a, pos + count)) + length);
/*     */     } 
/* 418 */     return p;
/*     */   } public void dump(DataOutputStream array, DataOutputStream pointers) throws IOException {
/*     */     int i;
/*     */     byte b;
/* 422 */     for (short[][] arrayOfShort = this.array; b < i; ) { short[] s; for (short e : s = arrayOfShort[b]) array.writeShort(e);  b++; }
/* 423 */      for (long[][] arrayOfLong = this.p; b < i; ) { long[] s; for (long e : s = arrayOfLong[b]) pointers.writeLong(e);  b++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 427 */     s.defaultReadObject();
/*     */     
/* 429 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortArrayFrontCodedBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */