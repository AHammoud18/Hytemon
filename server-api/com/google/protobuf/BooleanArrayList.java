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
/*     */ final class BooleanArrayList
/*     */   extends AbstractProtobufList<Boolean>
/*     */   implements Internal.BooleanList, RandomAccess, PrimitiveNonBoxingCollection
/*     */ {
/*  26 */   private static final boolean[] EMPTY_ARRAY = new boolean[0];
/*     */   
/*  28 */   private static final BooleanArrayList EMPTY_LIST = new BooleanArrayList(EMPTY_ARRAY, 0, false);
/*     */   
/*     */   public static BooleanArrayList emptyList() {
/*  31 */     return EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean[] array;
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */   
/*     */   BooleanArrayList() {
/*  45 */     this(EMPTY_ARRAY, 0, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BooleanArrayList(boolean[] other, int size, boolean isMutable) {
/*  53 */     super(isMutable);
/*  54 */     this.array = other;
/*  55 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BooleanArrayList(BooleanArrayList other, boolean isMutable) {
/*  63 */     this(
/*  64 */         (other.size == 0) ? EMPTY_ARRAY : Arrays.copyOf(other.array, other.size), other.size, isMutable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeRange(int fromIndex, int toIndex) {
/*  71 */     ensureIsMutable();
/*  72 */     if (toIndex < fromIndex) {
/*  73 */       throw new IndexOutOfBoundsException("toIndex < fromIndex");
/*     */     }
/*     */     
/*  76 */     System.arraycopy(this.array, toIndex, this.array, fromIndex, this.size - toIndex);
/*  77 */     this.size -= toIndex - fromIndex;
/*  78 */     this.modCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  84 */     if (this == o) {
/*  85 */       return true;
/*     */     }
/*  87 */     if (!(o instanceof BooleanArrayList)) {
/*  88 */       return super.equals(o);
/*     */     }
/*  90 */     BooleanArrayList other = (BooleanArrayList)o;
/*  91 */     if (this.size != other.size) {
/*  92 */       return false;
/*     */     }
/*     */     
/*  95 */     boolean[] arr = other.array;
/*  96 */     for (int i = 0; i < this.size; i++) {
/*  97 */       if (this.array[i] != arr[i]) {
/*  98 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     int result = 1;
/* 108 */     for (int i = 0; i < this.size; i++) {
/* 109 */       result = 31 * result + Internal.hashBoolean(this.array[i]);
/*     */     }
/* 111 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Internal.BooleanList mutableCopyWithCapacity(int capacity) {
/* 116 */     if (capacity < this.size) {
/* 117 */       throw new IllegalArgumentException();
/*     */     }
/* 119 */     boolean[] newArray = (capacity == 0) ? EMPTY_ARRAY : Arrays.copyOf(this.array, capacity);
/* 120 */     return new BooleanArrayList(newArray, this.size, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean get(int index) {
/* 125 */     return Boolean.valueOf(getBoolean(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(int index) {
/* 130 */     ensureIndexInRange(index);
/* 131 */     return this.array[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object element) {
/* 136 */     if (!(element instanceof Boolean)) {
/* 137 */       return -1;
/*     */     }
/* 139 */     boolean unboxedElement = ((Boolean)element).booleanValue();
/* 140 */     int numElems = size();
/* 141 */     for (int i = 0; i < numElems; i++) {
/* 142 */       if (this.array[i] == unboxedElement) {
/* 143 */         return i;
/*     */       }
/*     */     } 
/* 146 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object element) {
/* 151 */     return (indexOf(element) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 156 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean set(int index, Boolean element) {
/* 161 */     return Boolean.valueOf(setBoolean(index, element.booleanValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setBoolean(int index, boolean element) {
/* 166 */     ensureIsMutable();
/* 167 */     ensureIndexInRange(index);
/* 168 */     boolean previousValue = this.array[index];
/* 169 */     this.array[index] = element;
/* 170 */     return previousValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Boolean element) {
/* 175 */     addBoolean(element.booleanValue());
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, Boolean element) {
/* 181 */     addBoolean(index, element.booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBoolean(boolean element) {
/* 187 */     ensureIsMutable();
/* 188 */     if (this.size == this.array.length) {
/* 189 */       int length = growSize(this.array.length);
/* 190 */       boolean[] newArray = new boolean[length];
/*     */       
/* 192 */       System.arraycopy(this.array, 0, newArray, 0, this.size);
/* 193 */       this.array = newArray;
/*     */     } 
/*     */     
/* 196 */     this.array[this.size++] = element;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addBoolean(int index, boolean element) {
/* 201 */     ensureIsMutable();
/* 202 */     if (index < 0 || index > this.size) {
/* 203 */       throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
/*     */     }
/*     */     
/* 206 */     if (this.size < this.array.length) {
/*     */       
/* 208 */       System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
/*     */     } else {
/* 210 */       int length = growSize(this.array.length);
/* 211 */       boolean[] newArray = new boolean[length];
/*     */ 
/*     */       
/* 214 */       System.arraycopy(this.array, 0, newArray, 0, index);
/*     */ 
/*     */       
/* 217 */       System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
/* 218 */       this.array = newArray;
/*     */     } 
/*     */     
/* 221 */     this.array[index] = element;
/* 222 */     this.size++;
/* 223 */     this.modCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Boolean> collection) {
/* 228 */     ensureIsMutable();
/*     */     
/* 230 */     Internal.checkNotNull(collection);
/*     */ 
/*     */     
/* 233 */     if (!(collection instanceof BooleanArrayList)) {
/* 234 */       return super.addAll(collection);
/*     */     }
/*     */     
/* 237 */     BooleanArrayList list = (BooleanArrayList)collection;
/* 238 */     if (list.size == 0) {
/* 239 */       return false;
/*     */     }
/*     */     
/* 242 */     int overflow = Integer.MAX_VALUE - this.size;
/* 243 */     if (overflow < list.size)
/*     */     {
/* 245 */       throw new OutOfMemoryError();
/*     */     }
/*     */     
/* 248 */     int newSize = this.size + list.size;
/* 249 */     if (newSize > this.array.length) {
/* 250 */       this.array = Arrays.copyOf(this.array, newSize);
/*     */     }
/*     */     
/* 253 */     System.arraycopy(list.array, 0, this.array, this.size, list.size);
/* 254 */     this.size = newSize;
/* 255 */     this.modCount++;
/* 256 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean remove(int index) {
/* 261 */     ensureIsMutable();
/* 262 */     ensureIndexInRange(index);
/* 263 */     boolean value = this.array[index];
/* 264 */     if (index < this.size - 1) {
/* 265 */       System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
/*     */     }
/* 267 */     this.size--;
/* 268 */     this.modCount++;
/* 269 */     return Boolean.valueOf(value);
/*     */   }
/*     */ 
/*     */   
/*     */   void ensureCapacity(int minCapacity) {
/* 274 */     if (minCapacity <= this.array.length) {
/*     */       return;
/*     */     }
/* 277 */     if (this.array.length == 0) {
/* 278 */       this.array = new boolean[Math.max(minCapacity, 10)];
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 284 */     int n = this.array.length;
/* 285 */     while (n < minCapacity) {
/* 286 */       n = growSize(n);
/*     */     }
/* 288 */     this.array = Arrays.copyOf(this.array, n);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int growSize(int previousSize) {
/* 293 */     return Math.max(previousSize * 3 / 2 + 1, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureIndexInRange(int index) {
/* 303 */     if (index < 0 || index >= this.size) {
/* 304 */       throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
/*     */     }
/*     */   }
/*     */   
/*     */   private String makeOutOfBoundsExceptionMessage(int index) {
/* 309 */     return "Index:" + index + ", Size:" + this.size;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\BooleanArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */