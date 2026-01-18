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
/*     */ final class DoubleArrayList
/*     */   extends AbstractProtobufList<Double>
/*     */   implements Internal.DoubleList, RandomAccess, PrimitiveNonBoxingCollection
/*     */ {
/*  26 */   private static final double[] EMPTY_ARRAY = new double[0];
/*     */   
/*  28 */   private static final DoubleArrayList EMPTY_LIST = new DoubleArrayList(EMPTY_ARRAY, 0, false);
/*     */   
/*     */   public static DoubleArrayList emptyList() {
/*  31 */     return EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] array;
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */   
/*     */   DoubleArrayList() {
/*  45 */     this(EMPTY_ARRAY, 0, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DoubleArrayList(double[] other, int size, boolean isMutable) {
/*  52 */     super(isMutable);
/*  53 */     this.array = other;
/*  54 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DoubleArrayList(DoubleArrayList other, boolean isMutable) {
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
/*  85 */     if (!(o instanceof DoubleArrayList)) {
/*  86 */       return super.equals(o);
/*     */     }
/*  88 */     DoubleArrayList other = (DoubleArrayList)o;
/*  89 */     if (this.size != other.size) {
/*  90 */       return false;
/*     */     }
/*     */     
/*  93 */     double[] arr = other.array;
/*  94 */     for (int i = 0; i < this.size; i++) {
/*  95 */       if (Double.doubleToLongBits(this.array[i]) != Double.doubleToLongBits(arr[i])) {
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
/* 107 */       long bits = Double.doubleToLongBits(this.array[i]);
/* 108 */       result = 31 * result + Internal.hashLong(bits);
/*     */     } 
/* 110 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Internal.DoubleList mutableCopyWithCapacity(int capacity) {
/* 115 */     if (capacity < this.size) {
/* 116 */       throw new IllegalArgumentException();
/*     */     }
/* 118 */     double[] newArray = (capacity == 0) ? EMPTY_ARRAY : Arrays.copyOf(this.array, capacity);
/* 119 */     return new DoubleArrayList(newArray, this.size, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Double get(int index) {
/* 124 */     return Double.valueOf(getDouble(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/* 129 */     ensureIndexInRange(index);
/* 130 */     return this.array[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object element) {
/* 135 */     if (!(element instanceof Double)) {
/* 136 */       return -1;
/*     */     }
/* 138 */     double unboxedElement = ((Double)element).doubleValue();
/* 139 */     int numElems = size();
/* 140 */     for (int i = 0; i < numElems; i++) {
/* 141 */       if (this.array[i] == unboxedElement) {
/* 142 */         return i;
/*     */       }
/*     */     } 
/* 145 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object element) {
/* 150 */     return (indexOf(element) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 155 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Double set(int index, Double element) {
/* 160 */     return Double.valueOf(setDouble(index, element.doubleValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   public double setDouble(int index, double element) {
/* 165 */     ensureIsMutable();
/* 166 */     ensureIndexInRange(index);
/* 167 */     double previousValue = this.array[index];
/* 168 */     this.array[index] = element;
/* 169 */     return previousValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Double element) {
/* 174 */     addDouble(element.doubleValue());
/* 175 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, Double element) {
/* 180 */     addDouble(index, element.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDouble(double element) {
/* 186 */     ensureIsMutable();
/* 187 */     if (this.size == this.array.length) {
/* 188 */       int length = growSize(this.array.length);
/* 189 */       double[] newArray = new double[length];
/*     */       
/* 191 */       System.arraycopy(this.array, 0, newArray, 0, this.size);
/* 192 */       this.array = newArray;
/*     */     } 
/*     */     
/* 195 */     this.array[this.size++] = element;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addDouble(int index, double element) {
/* 200 */     ensureIsMutable();
/* 201 */     if (index < 0 || index > this.size) {
/* 202 */       throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
/*     */     }
/*     */     
/* 205 */     if (this.size < this.array.length) {
/*     */       
/* 207 */       System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
/*     */     } else {
/* 209 */       int length = growSize(this.array.length);
/* 210 */       double[] newArray = new double[length];
/*     */ 
/*     */       
/* 213 */       System.arraycopy(this.array, 0, newArray, 0, index);
/*     */ 
/*     */       
/* 216 */       System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
/* 217 */       this.array = newArray;
/*     */     } 
/*     */     
/* 220 */     this.array[index] = element;
/* 221 */     this.size++;
/* 222 */     this.modCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Double> collection) {
/* 227 */     ensureIsMutable();
/*     */     
/* 229 */     Internal.checkNotNull(collection);
/*     */ 
/*     */     
/* 232 */     if (!(collection instanceof DoubleArrayList)) {
/* 233 */       return super.addAll(collection);
/*     */     }
/*     */     
/* 236 */     DoubleArrayList list = (DoubleArrayList)collection;
/* 237 */     if (list.size == 0) {
/* 238 */       return false;
/*     */     }
/*     */     
/* 241 */     int overflow = Integer.MAX_VALUE - this.size;
/* 242 */     if (overflow < list.size)
/*     */     {
/* 244 */       throw new OutOfMemoryError();
/*     */     }
/*     */     
/* 247 */     int newSize = this.size + list.size;
/* 248 */     if (newSize > this.array.length) {
/* 249 */       this.array = Arrays.copyOf(this.array, newSize);
/*     */     }
/*     */     
/* 252 */     System.arraycopy(list.array, 0, this.array, this.size, list.size);
/* 253 */     this.size = newSize;
/* 254 */     this.modCount++;
/* 255 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Double remove(int index) {
/* 260 */     ensureIsMutable();
/* 261 */     ensureIndexInRange(index);
/* 262 */     double value = this.array[index];
/* 263 */     if (index < this.size - 1) {
/* 264 */       System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
/*     */     }
/* 266 */     this.size--;
/* 267 */     this.modCount++;
/* 268 */     return Double.valueOf(value);
/*     */   }
/*     */ 
/*     */   
/*     */   void ensureCapacity(int minCapacity) {
/* 273 */     if (minCapacity <= this.array.length) {
/*     */       return;
/*     */     }
/* 276 */     if (this.array.length == 0) {
/* 277 */       this.array = new double[Math.max(minCapacity, 10)];
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 283 */     int n = this.array.length;
/* 284 */     while (n < minCapacity) {
/* 285 */       n = growSize(n);
/*     */     }
/* 287 */     this.array = Arrays.copyOf(this.array, n);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int growSize(int previousSize) {
/* 292 */     return Math.max(previousSize * 3 / 2 + 1, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureIndexInRange(int index) {
/* 302 */     if (index < 0 || index >= this.size) {
/* 303 */       throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
/*     */     }
/*     */   }
/*     */   
/*     */   private String makeOutOfBoundsExceptionMessage(int index) {
/* 308 */     return "Index:" + index + ", Size:" + this.size;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\DoubleArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */