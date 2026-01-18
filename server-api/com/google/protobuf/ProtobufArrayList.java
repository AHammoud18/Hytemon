/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ final class ProtobufArrayList<E>
/*     */   extends AbstractProtobufList<E>
/*     */   implements RandomAccess
/*     */ {
/*  19 */   private static final Object[] EMPTY_ARRAY = new Object[0];
/*     */   
/*  21 */   private static final ProtobufArrayList<Object> EMPTY_LIST = new ProtobufArrayList((E[])EMPTY_ARRAY, 0, false);
/*     */   private E[] array;
/*     */   private int size;
/*     */   
/*     */   public static <E> ProtobufArrayList<E> emptyList() {
/*  26 */     return (ProtobufArrayList)EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ProtobufArrayList() {
/*  34 */     this((E[])EMPTY_ARRAY, 0, true);
/*     */   }
/*     */   
/*     */   private ProtobufArrayList(E[] array, int size, boolean isMutable) {
/*  38 */     super(isMutable);
/*  39 */     this.array = array;
/*  40 */     this.size = size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtobufArrayList<E> mutableCopyWithCapacity(int capacity) {
/*  46 */     if (capacity < this.size) {
/*  47 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  50 */     E[] newArray = (capacity == 0) ? (E[])EMPTY_ARRAY : Arrays.<E>copyOf(this.array, capacity);
/*     */     
/*  52 */     return new ProtobufArrayList(newArray, this.size, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(E element) {
/*  57 */     ensureIsMutable();
/*     */     
/*  59 */     if (this.size == this.array.length) {
/*  60 */       int length = growSize(this.array.length);
/*  61 */       E[] newArray = Arrays.copyOf(this.array, length);
/*     */       
/*  63 */       this.array = newArray;
/*     */     } 
/*     */     
/*  66 */     this.array[this.size++] = element;
/*  67 */     this.modCount++;
/*     */     
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int growSize(int previousSize) {
/*  74 */     return Math.max(previousSize * 3 / 2 + 1, 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, E element) {
/*  79 */     ensureIsMutable();
/*     */     
/*  81 */     if (index < 0 || index > this.size) {
/*  82 */       throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
/*     */     }
/*     */     
/*  85 */     if (this.size < this.array.length) {
/*     */       
/*  87 */       System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
/*     */     } else {
/*  89 */       int length = growSize(this.array.length);
/*  90 */       E[] newArray = createArray(length);
/*     */ 
/*     */       
/*  93 */       System.arraycopy(this.array, 0, newArray, 0, index);
/*     */ 
/*     */       
/*  96 */       System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
/*  97 */       this.array = newArray;
/*     */     } 
/*     */     
/* 100 */     this.array[index] = element;
/* 101 */     this.size++;
/* 102 */     this.modCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public E get(int index) {
/* 107 */     ensureIndexInRange(index);
/* 108 */     return this.array[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public E remove(int index) {
/* 113 */     ensureIsMutable();
/* 114 */     ensureIndexInRange(index);
/*     */     
/* 116 */     E value = this.array[index];
/* 117 */     if (index < this.size - 1) {
/* 118 */       System.arraycopy(this.array, index + 1, this.array, index, this.size - index - 1);
/*     */     }
/*     */     
/* 121 */     this.size--;
/* 122 */     this.modCount++;
/* 123 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public E set(int index, E element) {
/* 128 */     ensureIsMutable();
/* 129 */     ensureIndexInRange(index);
/*     */     
/* 131 */     E toReturn = this.array[index];
/* 132 */     this.array[index] = element;
/*     */     
/* 134 */     this.modCount++;
/* 135 */     return toReturn;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 140 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void ensureCapacity(int minCapacity) {
/* 146 */     if (minCapacity <= this.array.length) {
/*     */       return;
/*     */     }
/* 149 */     if (this.array.length == 0) {
/* 150 */       this.array = (E[])new Object[Math.max(minCapacity, 10)];
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 156 */     int n = this.array.length;
/* 157 */     while (n < minCapacity) {
/* 158 */       n = growSize(n);
/*     */     }
/* 160 */     this.array = Arrays.copyOf(this.array, n);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <E> E[] createArray(int capacity) {
/* 165 */     return (E[])new Object[capacity];
/*     */   }
/*     */   
/*     */   private void ensureIndexInRange(int index) {
/* 169 */     if (index < 0 || index >= this.size) {
/* 170 */       throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
/*     */     }
/*     */   }
/*     */   
/*     */   private String makeOutOfBoundsExceptionMessage(int index) {
/* 175 */     return "Index:" + index + ", Size:" + this.size;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ProtobufArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */