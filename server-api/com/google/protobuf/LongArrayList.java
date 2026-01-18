/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ final class LongArrayList
/*     */   extends AbstractProtobufList<Long>
/*     */   implements Internal.LongList, RandomAccess, PrimitiveNonBoxingCollection
/*     */ {
/*  26 */   private static final long[] EMPTY_ARRAY = new long[0];
/*     */   
/*  28 */   private static final LongArrayList EMPTY_LIST = new LongArrayList(EMPTY_ARRAY, 0, false);
/*     */   
/*     */   public static LongArrayList emptyList() {
/*  31 */     return EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long[] array;
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */   
/*     */   LongArrayList() {
/*  45 */     this(EMPTY_ARRAY, 0, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LongArrayList(long[] other, int size, boolean isMutable) {
/*  52 */     super(isMutable);
/*  53 */     this.array = other;
/*  54 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LongArrayList(LongArrayList other, boolean isMutable) {
/*  61 */     this(
/*  62 */         (other.size == 0) ? EMPTY_ARRAY : Arrays.copyOf(other.array, other.size), other.size, isMutable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeRange(int fromIndex, int toIndex) {
/*  69 */     ensureIsMutable();
/*  70 */     if (toIndex < fromIndex) {
/*  71 */       throw new IndexOutOfBoundsException("toIndex < fromIndex");
/*     */     }
/*     */     
/*  74 */     System.arraycopy(this.array, toIndex, this.array, fromIndex, this.size - toIndex);
/*  75 */     this.size -= toIndex - fromIndex;
/*  76 */     this.modCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  82 */     if (this == o) {
/*  83 */       return true;
/*     */     }
/*  85 */     if (!(o instanceof LongArrayList)) {
/*  86 */       return super.equals(o);
/*     */     }
/*  88 */     LongArrayList other = (LongArrayList)o;
/*  89 */     if (this.size != other.size) {
/*  90 */       return false;
/*     */     }
/*     */     
/*  93 */     long[] arr = other.array;
/*  94 */     for (int i = 0; i < this.size; i++) {
/*  95 */       if (this.array[i] != arr[i]) {
/*  96 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     int result = 1;
/* 106 */     for (int i = 0; i < this.size; i++) {
/* 107 */       result = 31 * result + Internal.hashLong(this.array[i]);
/*     */     }
/* 109 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Internal.LongList mutableCopyWithCapacity(int capacity) {
/* 114 */     if (capacity < this.size) {
/* 115 */       throw new IllegalArgumentException();
/*     */     }
/* 117 */     long[] newArray = (capacity == 0) ? EMPTY_ARRAY : Arrays.copyOf(this.array, capacity);
/* 118 */     return new LongArrayList(newArray, this.size, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Long get(int index) {
/* 123 */     return Long.valueOf(getLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 128 */     ensureIndexInRange(index);
/* 129 */     return this.array[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object element) {
/* 134 */     if (!(element instanceof Long)) {
/* 135 */       return -1;
/*     */     }
/* 137 */     long unboxedElement = ((Long)element).longValue();
/* 138 */     int numElems = size();
/* 139 */     for (int i = 0; i < numElems; i++) {
/* 140 */       if (this.array[i] == unboxedElement) {
/* 141 */         return i;
/*     */       }
/*     */     } 
/* 144 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object element) {
/* 149 */     return (indexOf(element) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 154 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Long set(int index, Long element) {
/* 159 */     return Long.valueOf(setLong(index, element.longValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   public long setLong(int index, long element) {
/* 164 */     ensureIsMutable();
/* 165 */     ensureIndexInRange(index);
/* 166 */     long previousValue = this.array[index];
/* 167 */     this.array[index] = element;
/* 168 */     return previousValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Long element) {
/* 173 */     addLong(element.longValue());
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, Long element) {
/* 179 */     addLong(index, element.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLong(long element) {
/* 185 */     ensureIsMutable();
/* 186 */     if (this.size == this.array.length) {
/* 187 */       int length = growSize(this.array.length);
/* 188 */       long[] newArray = new long[length];
/*     */       
/* 190 */       System.arraycopy(this.array, 0, newArray, 0, this.size);
/* 191 */       this.array = newArray;
/*     */     } 
/*     */     
/* 194 */     this.array[this.size++] = element;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addLong(int index, long element) {
/* 199 */     ensureIsMutable();
/* 200 */     if (index < 0 || index > this.size) {
/* 201 */       throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
/*     */     }
/*     */     
/* 204 */     if (this.size < this.array.length) {
/*     */       
/* 206 */       System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
/*     */     } else {
/* 208 */       int length = growSize(this.array.length);
/* 209 */       long[] newArray = new long[length];
/*     */ 
/*     */       
/* 212 */       System.arraycopy(this.array, 0, newArray, 0, index);
/*     */ 
/*     */       
/* 215 */       System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
/* 216 */       this.array = newArray;
/*     */     } 
/*     */     
/* 219 */     this.array[index] = element;
/* 220 */     this.size++;
/* 221 */     this.modCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Long> collection) {
/* 226 */     ensureIsMutable();
/*     */     
/* 228 */     Internal.checkNotNull(collection);
/*     */ 
/*     */     
/* 231 */     if (!(collection instanceof LongArrayList)) {
/* 232 */       return super.addAll(collection);
/*     */     }
/*     */     
/* 235 */     LongArrayList list = (LongArrayList)collection;
/* 236 */     if (list.size == 0) {
/* 237 */       return false;
/*     */     }
/*     */     
/* 240 */     int overflow = Integer.MAX_VALUE - this.size;
/* 241 */     if (overflow < list.size)
/*     */     {
/* 243 */       throw new OutOfMemoryError();
/*     */     }
/*     */     
/* 246 */     int newSize = this.size + list.size;
/* 247 */     if (newSize > this.array.length) {
/* 248 */       this.array = Arrays.copyOf(this.array, newSize);
/*     */     }
/*     */     
/* 251 */     System.arraycopy(list.array, 0, this.array, this.size, list.size);
/* 252 */     this.size = newSize;
/* 253 */     this.modCount++;
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Long remove(int index) {
/* 259 */     ensureIsMutable();
/* 260 */     ensureIndexInRange(index);
/* 261 */     long value = this.array[index];
/* 262 */     if (index < this.size - 1) {
/* 263 */       System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
/*     */     }
/* 265 */     this.size--;
/* 266 */     this.modCount++;
/* 267 */     return Long.valueOf(value);
/*     */   }
/*     */ 
/*     */   
/*     */   void ensureCapacity(int minCapacity) {
/* 272 */     if (minCapacity <= this.array.length) {
/*     */       return;
/*     */     }
/* 275 */     if (this.array.length == 0) {
/* 276 */       this.array = new long[Math.max(minCapacity, 10)];
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 282 */     int n = this.array.length;
/* 283 */     while (n < minCapacity) {
/* 284 */       n = growSize(n);
/*     */     }
/* 286 */     this.array = Arrays.copyOf(this.array, n);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int growSize(int previousSize) {
/* 291 */     return Math.max(previousSize * 3 / 2 + 1, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureIndexInRange(int index) {
/* 301 */     if (index < 0 || index >= this.size) {
/* 302 */       throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
/*     */     }
/*     */   }
/*     */   
/*     */   private String makeOutOfBoundsExceptionMessage(int index) {
/* 307 */     return "Index:" + index + ", Size:" + this.size;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\LongArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */