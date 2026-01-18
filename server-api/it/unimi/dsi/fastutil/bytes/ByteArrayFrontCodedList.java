/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class ByteArrayFrontCodedList
/*     */   extends AbstractObjectList<byte[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final byte[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public ByteArrayFrontCodedList(Iterator<byte[]> arrays, int ratio) {
/* 107 */     if (ratio < 1) throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 108 */     byte[][] array = ByteBigArrays.EMPTY_BIG_ARRAY;
/* 109 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 110 */     byte[][] a = new byte[2][];
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
/*     */   public ByteArrayFrontCodedList(Collection<byte[]> c, int ratio) {
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
/*     */   static int readInt(byte[][] a, long pos) {
/* 163 */     byte b0 = BigArrays.get(a, pos);
/* 164 */     if (b0 >= 0) return b0; 
/* 165 */     byte b1 = BigArrays.get(a, pos + 1L);
/* 166 */     if (b1 >= 0) return -b0 - 1 << 7 | b1; 
/* 167 */     byte b2 = BigArrays.get(a, pos + 2L);
/* 168 */     if (b2 >= 0) return -b0 - 1 << 14 | -b1 - 1 << 7 | b2; 
/* 169 */     byte b3 = BigArrays.get(a, pos + 3L);
/* 170 */     if (b3 >= 0) return -b0 - 1 << 21 | -b1 - 1 << 14 | -b2 - 1 << 7 | b3; 
/* 171 */     return -b0 - 1 << 28 | -b1 - 1 << 21 | -b2 - 1 << 14 | -b3 - 1 << 7 | BigArrays.get(a, pos + 4L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int count(int length) {
/* 181 */     if (length < 128) return 1; 
/* 182 */     if (length < 16384) return 2; 
/* 183 */     if (length < 2097152) return 3; 
/* 184 */     if (length < 268435456) return 4; 
/* 185 */     return 5;
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
/*     */   static int writeInt(byte[][] a, int length, long pos) {
/* 197 */     int count = count(length);
/* 198 */     BigArrays.set(a, pos + count - 1L, (byte)(length & 0x7F));
/* 199 */     for (int i = count - 1; i-- != 0; ) {
/* 200 */       length >>>= 7;
/* 201 */       BigArrays.set(a, pos + i, (byte)(-(length & 0x7F) - 1));
/*     */     } 
/* 203 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ratio() {
/* 212 */     return this.ratio;
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
/* 226 */     byte[][] array = this.array;
/* 227 */     int delta = index % this.ratio;
/* 228 */     long pos = this.p[index / this.ratio];
/* 229 */     int length = readInt(array, pos);
/* 230 */     if (delta == 0) return length;
/*     */ 
/*     */     
/* 233 */     pos += (count(length) + length);
/* 234 */     length = readInt(array, pos);
/* 235 */     int common = readInt(array, pos + count(length));
/* 236 */     for (int i = 0; i < delta - 1; i++) {
/* 237 */       pos += (count(length) + count(common) + length);
/* 238 */       length = readInt(array, pos);
/* 239 */       common = readInt(array, pos + count(length));
/*     */     } 
/* 241 */     return length + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int arrayLength(int index) {
/* 251 */     ensureRestrictedIndex(index);
/* 252 */     return length(index);
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
/*     */   private int extract(int index, byte[] a, int offset, int length) {
/* 265 */     int delta = index % this.ratio;
/* 266 */     long startPos = this.p[index / this.ratio];
/*     */     
/*     */     long pos;
/* 269 */     int arrayLength = readInt(this.array, pos = startPos), currLen = 0;
/* 270 */     if (delta == 0) {
/* 271 */       pos = this.p[index / this.ratio] + count(arrayLength);
/* 272 */       BigArrays.copyFromBig(this.array, pos, a, offset, Math.min(length, arrayLength));
/* 273 */       return arrayLength;
/*     */     } 
/* 275 */     int common = 0;
/* 276 */     for (int i = 0; i < delta; i++) {
/* 277 */       long prevArrayPos = pos + count(arrayLength) + ((i != 0) ? count(common) : 0L);
/* 278 */       pos = prevArrayPos + arrayLength;
/* 279 */       arrayLength = readInt(this.array, pos);
/* 280 */       common = readInt(this.array, pos + count(arrayLength));
/* 281 */       int actualCommon = Math.min(common, length);
/* 282 */       if (actualCommon <= currLen) { currLen = actualCommon; }
/*     */       else
/* 284 */       { BigArrays.copyFromBig(this.array, prevArrayPos, a, currLen + offset, actualCommon - currLen);
/* 285 */         currLen = actualCommon; }
/*     */     
/*     */     } 
/* 288 */     if (currLen < length) BigArrays.copyFromBig(this.array, pos + count(arrayLength) + count(common), a, currLen + offset, Math.min(arrayLength, length - currLen)); 
/* 289 */     return arrayLength + common;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] get(int index) {
/* 299 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getArray(int index) {
/* 309 */     ensureRestrictedIndex(index);
/* 310 */     int length = length(index);
/* 311 */     byte[] a = new byte[length];
/* 312 */     extract(index, a, 0, length);
/* 313 */     return a;
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
/*     */   public int get(int index, byte[] a, int offset, int length) {
/* 327 */     ensureRestrictedIndex(index);
/* 328 */     ByteArrays.ensureOffsetLength(a, offset, length);
/* 329 */     int arrayLength = extract(index, a, offset, length);
/* 330 */     if (length >= arrayLength) return arrayLength; 
/* 331 */     return length - arrayLength;
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
/*     */   public int get(int index, byte[] a) {
/* 344 */     return get(index, a, 0, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 349 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectListIterator<byte[]> listIterator(final int start) {
/* 354 */     ensureIndex(start);
/* 355 */     return new ObjectListIterator<byte[]>() {
/* 356 */         byte[] s = ByteArrays.EMPTY_ARRAY;
/* 357 */         int i = 0;
/* 358 */         long pos = 0L;
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
/* 374 */           return (this.i < ByteArrayFrontCodedList.this.n);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 379 */           return (this.i > 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 384 */           return this.i - 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 389 */           return this.i;
/*     */         }
/*     */ 
/*     */         
/*     */         public byte[] next() {
/*     */           int length;
/* 395 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 396 */           if (this.i % ByteArrayFrontCodedList.this.ratio == 0) {
/* 397 */             this.pos = ByteArrayFrontCodedList.this.p[this.i / ByteArrayFrontCodedList.this.ratio];
/* 398 */             length = ByteArrayFrontCodedList.readInt(ByteArrayFrontCodedList.this.array, this.pos);
/* 399 */             this.s = ByteArrays.ensureCapacity(this.s, length, 0);
/* 400 */             BigArrays.copyFromBig(ByteArrayFrontCodedList.this.array, this.pos + ByteArrayFrontCodedList.count(length), this.s, 0, length);
/* 401 */             this.pos += (length + ByteArrayFrontCodedList.count(length));
/* 402 */             this.inSync = true;
/*     */           }
/* 404 */           else if (this.inSync) {
/* 405 */             length = ByteArrayFrontCodedList.readInt(ByteArrayFrontCodedList.this.array, this.pos);
/* 406 */             int common = ByteArrayFrontCodedList.readInt(ByteArrayFrontCodedList.this.array, this.pos + ByteArrayFrontCodedList.count(length));
/* 407 */             this.s = ByteArrays.ensureCapacity(this.s, length + common, common);
/* 408 */             BigArrays.copyFromBig(ByteArrayFrontCodedList.this.array, this.pos + ByteArrayFrontCodedList.count(length) + ByteArrayFrontCodedList.count(common), this.s, common, length);
/* 409 */             this.pos += (ByteArrayFrontCodedList.count(length) + ByteArrayFrontCodedList.count(common) + length);
/* 410 */             length += common;
/*     */           } else {
/* 412 */             this.s = ByteArrays.ensureCapacity(this.s, length = ByteArrayFrontCodedList.this.length(this.i), 0);
/* 413 */             ByteArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 416 */           this.i++;
/* 417 */           return ByteArrays.copy(this.s, 0, length);
/*     */         }
/*     */ 
/*     */         
/*     */         public byte[] previous() {
/* 422 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 423 */           this.inSync = false;
/* 424 */           return ByteArrayFrontCodedList.this.getArray(--this.i);
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
/*     */   public ByteArrayFrontCodedList clone() {
/* 436 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 441 */     StringBuffer s = new StringBuffer();
/* 442 */     s.append("[");
/* 443 */     for (int i = 0; i < this.n; i++) {
/* 444 */       if (i != 0) s.append(", "); 
/* 445 */       s.append(ByteArrayList.wrap(getArray(i)).toString());
/*     */     } 
/* 447 */     s.append("]");
/* 448 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long[] rebuildPointerArray() {
/* 458 */     long[] p = new long[(this.n + this.ratio - 1) / this.ratio];
/* 459 */     byte[][] a = this.array;
/*     */     
/* 461 */     long pos = 0L;
/* 462 */     for (int i = 0, j = 0, skip = this.ratio - 1; i < this.n; i++) {
/* 463 */       int length = readInt(a, pos);
/* 464 */       int count = count(length);
/* 465 */       if (++skip == this.ratio)
/* 466 */       { skip = 0;
/* 467 */         p[j++] = pos;
/* 468 */         pos += (count + length); }
/* 469 */       else { pos += (count + count(readInt(a, pos + count)) + length); }
/*     */     
/* 471 */     }  return p;
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 475 */     s.defaultReadObject();
/*     */     
/* 477 */     this.p = rebuildPointerArray();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */