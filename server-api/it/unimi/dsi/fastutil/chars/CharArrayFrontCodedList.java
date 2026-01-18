/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public class CharArrayFrontCodedList
/*     */   extends AbstractObjectList<char[]>
/*     */   implements Serializable, Cloneable, RandomAccess
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected final int n;
/*     */   protected final int ratio;
/*     */   protected final char[][] array;
/*     */   protected transient long[] p;
/*     */   
/*     */   public CharArrayFrontCodedList(Iterator<char[]> arrays, int ratio) {
/* 107 */     if (ratio < 1) throw new IllegalArgumentException("Illegal ratio (" + ratio + ")"); 
/* 108 */     char[][] array = CharBigArrays.EMPTY_BIG_ARRAY;
/* 109 */     long[] p = LongArrays.EMPTY_ARRAY;
/* 110 */     char[][] a = new char[2][];
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
/*     */   public CharArrayFrontCodedList(Collection<char[]> c, int ratio) {
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
/*     */   static int readInt(char[][] a, long pos) {
/* 163 */     char c0 = BigArrays.get(a, pos);
/* 164 */     return (c0 < '耀') ? c0 : ((c0 & 0x7FFF) << 16 | BigArrays.get(a, pos + 1L));
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
/*     */   static int writeInt(char[][] a, int length, long pos) {
/* 186 */     if (length < 32768) {
/* 187 */       BigArrays.set(a, pos, (char)length);
/* 188 */       return 1;
/*     */     } 
/* 190 */     BigArrays.set(a, pos++, (char)(length >>> 16 | 0x8000));
/* 191 */     BigArrays.set(a, pos, (char)(length & 0xFFFF));
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
/* 215 */     char[][] array = this.array;
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
/*     */   private int extract(int index, char[] a, int offset, int length) {
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
/*     */   public char[] get(int index) {
/* 288 */     return getArray(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] getArray(int index) {
/* 298 */     ensureRestrictedIndex(index);
/* 299 */     int length = length(index);
/* 300 */     char[] a = new char[length];
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
/*     */   public int get(int index, char[] a, int offset, int length) {
/* 316 */     ensureRestrictedIndex(index);
/* 317 */     CharArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public int get(int index, char[] a) {
/* 333 */     return get(index, a, 0, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 338 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectListIterator<char[]> listIterator(final int start) {
/* 343 */     ensureIndex(start);
/* 344 */     return new ObjectListIterator<char[]>() {
/* 345 */         char[] s = CharArrays.EMPTY_ARRAY;
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
/* 363 */           return (this.i < CharArrayFrontCodedList.this.n);
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
/*     */         public char[] next() {
/*     */           int length;
/* 384 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 385 */           if (this.i % CharArrayFrontCodedList.this.ratio == 0) {
/* 386 */             this.pos = CharArrayFrontCodedList.this.p[this.i / CharArrayFrontCodedList.this.ratio];
/* 387 */             length = CharArrayFrontCodedList.readInt(CharArrayFrontCodedList.this.array, this.pos);
/* 388 */             this.s = CharArrays.ensureCapacity(this.s, length, 0);
/* 389 */             BigArrays.copyFromBig(CharArrayFrontCodedList.this.array, this.pos + CharArrayFrontCodedList.count(length), this.s, 0, length);
/* 390 */             this.pos += (length + CharArrayFrontCodedList.count(length));
/* 391 */             this.inSync = true;
/*     */           }
/* 393 */           else if (this.inSync) {
/* 394 */             length = CharArrayFrontCodedList.readInt(CharArrayFrontCodedList.this.array, this.pos);
/* 395 */             int common = CharArrayFrontCodedList.readInt(CharArrayFrontCodedList.this.array, this.pos + CharArrayFrontCodedList.count(length));
/* 396 */             this.s = CharArrays.ensureCapacity(this.s, length + common, common);
/* 397 */             BigArrays.copyFromBig(CharArrayFrontCodedList.this.array, this.pos + CharArrayFrontCodedList.count(length) + CharArrayFrontCodedList.count(common), this.s, common, length);
/* 398 */             this.pos += (CharArrayFrontCodedList.count(length) + CharArrayFrontCodedList.count(common) + length);
/* 399 */             length += common;
/*     */           } else {
/* 401 */             this.s = CharArrays.ensureCapacity(this.s, length = CharArrayFrontCodedList.this.length(this.i), 0);
/* 402 */             CharArrayFrontCodedList.this.extract(this.i, this.s, 0, length);
/*     */           } 
/*     */           
/* 405 */           this.i++;
/* 406 */           return CharArrays.copy(this.s, 0, length);
/*     */         }
/*     */ 
/*     */         
/*     */         public char[] previous() {
/* 411 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 412 */           this.inSync = false;
/* 413 */           return CharArrayFrontCodedList.this.getArray(--this.i);
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
/*     */   public CharArrayFrontCodedList clone() {
/* 425 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 430 */     StringBuffer s = new StringBuffer();
/* 431 */     s.append("[");
/* 432 */     for (int i = 0; i < this.n; i++) {
/* 433 */       if (i != 0) s.append(", "); 
/* 434 */       s.append(CharArrayList.wrap(getArray(i)).toString());
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
/* 448 */     char[][] a = this.array;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharArrayFrontCodedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */