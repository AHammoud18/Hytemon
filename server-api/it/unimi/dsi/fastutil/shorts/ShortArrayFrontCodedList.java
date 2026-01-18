/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public class ShortArrayFrontCodedList
/*     */   extends AbstractObjectList<short[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final short[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public ShortArrayFrontCodedList(Iterator<short[]> arrays, int ratio) {
/* 107 */     if (ratio < 1) throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 108 */     short[][] array = ShortBigArrays.EMPTY_BIG_ARRAY;
/* 109 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 110 */     short[][] a = new short[2][];
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
/*     */   public ShortArrayFrontCodedList(Collection<short[]> c, int ratio) {
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
/*     */   static int readInt(short[][] a, long pos) {
/* 163 */     short s0 = BigArrays.get(a, pos);
/* 164 */     return (s0 >= 0) ? s0 : (s0 << 16 | BigArrays.get(a, pos + 1L) & 0xFFFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int count(int length) {
/* 174 */     return (length < 32768) ? 1 : 2;
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
/*     */   static int writeInt(short[][] a, int length, long pos) {
/* 186 */     if (length < 32768) {
/* 187 */       BigArrays.set(a, pos, (short)length);
/* 188 */       return 1;
/*     */     } 
/* 190 */     BigArrays.set(a, pos++, (short)(-(length >>> 16) - 1));
/* 191 */     BigArrays.set(a, pos, (short)(length & 0xFFFF));
/* 192 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ratio() {
/* 201 */     return this.ratio;
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
/* 215 */     short[][] array = this.array;
/* 216 */     int delta = index % this.ratio;
/* 217 */     long pos = this.p[index / this.ratio];
/* 218 */     int length = readInt(array, pos);
/* 219 */     if (delta == 0) return length;
/*     */ 
/*     */     
/* 222 */     pos += (count(length) + length);
/* 223 */     length = readInt(array, pos);
/* 224 */     int common = readInt(array, pos + count(length));
/* 225 */     for (int i = 0; i < delta - 1; i++) {
/* 226 */       pos += (count(length) + count(common) + length);
/* 227 */       length = readInt(array, pos);
/* 228 */       common = readInt(array, pos + count(length));
/*     */     } 
/* 230 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(int index) {
/* 240 */     ensureRestrictedIndex(index);
/* 241 */     return length(index);
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
/*     */   private int extract(int index, short[] a, int offset, int length) {
/* 254 */     int delta = index % this.ratio;
/* 255 */     long startPos = this.p[index / this.ratio];
/*     */     
/*     */     long pos;
/* 258 */     int arrayLength = readInt(this.array, pos = startPos), currLen = 0;
/* 259 */     if (delta == 0) {
/* 260 */       pos = this.p[index / this.ratio] + count(arrayLength);
/* 261 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 262 */       return arrayLength;
/*     */     } 
/* 264 */     int common = 0;
/* 265 */     for (int i = 0; i < delta; i++) {
/* 266 */       long prevArrayPos = pos + count(arrayLength) + ((i != 0) ? count(common) : 0L);
/* 267 */       pos = prevArrayPos + arrayLength;
/* 268 */       arrayLength = readInt(this.array, pos);
/* 269 */       common = readInt(this.array, pos + count(arrayLength));
/* 270 */       int actualCommon = Math.min(common, length);
/* 271 */       if (actualCommon <= currLen) { currLen = actualCommon; }
/*     */       else
/* 273 */       { BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 274 */         currLen = actualCommon; }
/*     */     
/*     */     } 
/* 277 */     if (currLen < length) BigArrays.copyFromBig(this.array, pos + count(arrayLength) + count(common), a, currLen + offset, Math.min(arrayLength, length - currLen)); 
/* 278 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] get(int index) {
/* 288 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] getArray(int index) {
/* 298 */     ensureRestrictedIndex(index);
/* 299 */     int length = length(index);
/* 300 */     short[] a = new short[length];
/* 301 */     extract(index, a, 0, length);
/* 302 */     return a;
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
/*     */   public int get(int index, short[] a, int offset, int length) {
/* 316 */     ensureRestrictedIndex(index);
/* 317 */     ShortArrays.ensureOffsetLength(a, offset, length);
/* 318 */     int arrayLength = extract(index, a, offset, length);
/* 319 */     if (length >= arrayLength) return arrayLength; 
/* 320 */     return length - arrayLength;
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
/*     */   public int get(int index, short[] a) {
/* 333 */     return get(index, a, 0, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 338 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectListIterator<short[]> listIterator(final int start) {
/* 343 */     ensureIndex(start);
/* 344 */     return new ObjectListIterator<short[]>() {
/* 345 */         short[] s = ShortArrays.EMPTY_ARRAY;
/* 346 */         int i = 0;
/* 347 */         long pos = 0L;
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
/* 363 */           return (this.i < ShortArrayFrontCodedList.this.n);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 368 */           return (this.i > 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 373 */           return this.i - 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 378 */           return this.i;
/*     */         }
/*     */ 
/*     */         
/*     */         public short[] next() {
/*     */           int length;
/* 384 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 385 */           if (this.i % ShortArrayFrontCodedList.this.ratio == 0) {
/* 386 */             this.pos = ShortArrayFrontCodedList.this.p[this.i / ShortArrayFrontCodedList.this.ratio];
/* 387 */             length = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedList.this.array, this.pos);
/* 388 */             this.s = ShortArrays.ensureCapacity(this.s, length, 0);
/* 389 */             BigArrays.copyFromBig(ShortArrayFrontCodedList.this.array, this.pos + ShortArrayFrontCodedList.count(length), this.s, 0, length);
/* 390 */             this.pos += (length + ShortArrayFrontCodedList.count(length));
/* 391 */             this.inSync = true;
/*     */           }
/* 393 */           else if (this.inSync) {
/* 394 */             length = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedList.this.array, this.pos);
/* 395 */             int common = ShortArrayFrontCodedList.readInt(ShortArrayFrontCodedList.this.array, this.pos + ShortArrayFrontCodedList.count(length));
/* 396 */             this.s = ShortArrays.ensureCapacity(this.s, length + common, common);
/* 397 */             BigArrays.copyFromBig(ShortArrayFrontCodedList.this.array, this.pos + ShortArrayFrontCodedList.count(length) + ShortArrayFrontCodedList.count(common), this.s, common, length);
/* 398 */             this.pos += (ShortArrayFrontCodedList.count(length) + ShortArrayFrontCodedList.count(common) + length);
/* 399 */             length += common;
/*     */           } else {
/* 401 */             this.s = ShortArrays.ensureCapacity(this.s, length = ShortArrayFrontCodedList.this.length(this.i), 0);
/* 402 */             ShortArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 405 */           this.i++;
/* 406 */           return ShortArrays.copy(this.s, 0, length);
/*     */         }
/*     */ 
/*     */         
/*     */         public short[] previous() {
/* 411 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 412 */           this.inSync = false;
/* 413 */           return ShortArrayFrontCodedList.this.getArray(--this.i);
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
/*     */   public ShortArrayFrontCodedList clone() {
/* 425 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 430 */     StringBuffer s = new StringBuffer();
/* 431 */     s.append("[");
/* 432 */     for (int i = 0; i < this.n; i++) {
/* 433 */       if (i != 0) s.append(", "); 
/* 434 */       s.append(ShortArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 436 */     s.append("]");
/* 437 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[] rebuildPointerArray() {
/* 447 */     long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
/* 448 */     short[][] a = this.array;
/*     */     
/* 450 */     long pos = 0L;
/* 451 */     for (int i = 0, j = 0, skip = this.ratio - 1; i < this.n; i++) {
/* 452 */       int length = readInt(a, pos);
/* 453 */       int count = count(length);
/* 454 */       if (++skip == this.ratio)
/* 455 */       { skip = 0;
/* 456 */         p[j++] = pos;
/* 457 */         pos += (count + length); }
/* 458 */       else { pos += (count + count(readInt(a, pos + count)) + length); }
/*     */     
/* 460 */     }  return p;
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 464 */     s.defaultReadObject();
/*     */     
/* 466 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */