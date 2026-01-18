/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.MathUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.AbstractList;
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
/*     */ final class CodecOutputList
/*     */   extends AbstractList<Object>
/*     */   implements RandomAccess
/*     */ {
/*  31 */   private static final CodecOutputListRecycler NOOP_RECYCLER = new CodecOutputListRecycler()
/*     */     {
/*     */       public void recycle(CodecOutputList object) {}
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static final FastThreadLocal<CodecOutputLists> CODEC_OUTPUT_LISTS_POOL = new FastThreadLocal<CodecOutputLists>()
/*     */     {
/*     */       
/*     */       protected CodecOutputList.CodecOutputLists initialValue() throws Exception
/*     */       {
/*  43 */         return new CodecOutputList.CodecOutputLists(16);
/*     */       }
/*     */     };
/*     */   private final CodecOutputListRecycler recycler;
/*     */   private int size;
/*     */   private int maxSeenSize;
/*     */   private Object[] array;
/*     */   private boolean insertSinceRecycled;
/*     */   
/*     */   private static interface CodecOutputListRecycler {
/*     */     void recycle(CodecOutputList param1CodecOutputList); }
/*     */   
/*     */   private static final class CodecOutputLists implements CodecOutputListRecycler { private final CodecOutputList[] elements;
/*     */     private final int mask;
/*     */     
/*     */     CodecOutputLists(int numElements) {
/*  59 */       this.elements = new CodecOutputList[MathUtil.safeFindNextPositivePowerOfTwo(numElements)];
/*  60 */       for (int i = 0; i < this.elements.length; i++)
/*     */       {
/*  62 */         this.elements[i] = new CodecOutputList(this, 16);
/*     */       }
/*  64 */       this.count = this.elements.length;
/*  65 */       this.currentIdx = this.elements.length;
/*  66 */       this.mask = this.elements.length - 1;
/*     */     }
/*     */     private int currentIdx; private int count;
/*     */     public CodecOutputList getOrCreate() {
/*  70 */       if (this.count == 0)
/*     */       {
/*     */         
/*  73 */         return new CodecOutputList(CodecOutputList.NOOP_RECYCLER, 4);
/*     */       }
/*  75 */       this.count--;
/*     */       
/*  77 */       int idx = this.currentIdx - 1 & this.mask;
/*  78 */       CodecOutputList list = this.elements[idx];
/*  79 */       this.currentIdx = idx;
/*  80 */       return list;
/*     */     }
/*     */ 
/*     */     
/*     */     public void recycle(CodecOutputList codecOutputList) {
/*  85 */       int idx = this.currentIdx;
/*  86 */       this.elements[idx] = codecOutputList;
/*  87 */       this.currentIdx = idx + 1 & this.mask;
/*  88 */       this.count++;
/*  89 */       assert this.count <= this.elements.length;
/*     */     } }
/*     */ 
/*     */   
/*     */   static CodecOutputList newInstance() {
/*  94 */     return ((CodecOutputLists)CODEC_OUTPUT_LISTS_POOL.get()).getOrCreate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CodecOutputList(CodecOutputListRecycler recycler, int size) {
/* 104 */     this.recycler = recycler;
/* 105 */     this.array = new Object[size];
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get(int index) {
/* 110 */     checkIndex(index);
/* 111 */     return this.array[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 116 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Object element) {
/* 121 */     ObjectUtil.checkNotNull(element, "element");
/*     */     try {
/* 123 */       insert(this.size, element);
/* 124 */     } catch (IndexOutOfBoundsException ignore) {
/*     */       
/* 126 */       expandArray();
/* 127 */       insert(this.size, element);
/*     */     } 
/* 129 */     this.size++;
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object set(int index, Object element) {
/* 135 */     ObjectUtil.checkNotNull(element, "element");
/* 136 */     checkIndex(index);
/*     */     
/* 138 */     Object old = this.array[index];
/* 139 */     insert(index, element);
/* 140 */     return old;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int index, Object element) {
/* 145 */     ObjectUtil.checkNotNull(element, "element");
/* 146 */     checkIndex(index);
/*     */     
/* 148 */     if (this.size == this.array.length) {
/* 149 */       expandArray();
/*     */     }
/*     */     
/* 152 */     if (index != this.size) {
/* 153 */       System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
/*     */     }
/*     */     
/* 156 */     insert(index, element);
/* 157 */     this.size++;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object remove(int index) {
/* 162 */     checkIndex(index);
/* 163 */     Object old = this.array[index];
/*     */     
/* 165 */     int len = this.size - index - 1;
/* 166 */     if (len > 0) {
/* 167 */       System.arraycopy(this.array, index + 1, this.array, index, len);
/*     */     }
/* 169 */     this.array[--this.size] = null;
/*     */     
/* 171 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 178 */     this.maxSeenSize = Math.max(this.maxSeenSize, this.size);
/* 179 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean insertSinceRecycled() {
/* 186 */     return this.insertSinceRecycled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void recycle() {
/* 193 */     int len = Math.max(this.maxSeenSize, this.size);
/* 194 */     for (int i = 0; i < len; i++) {
/* 195 */       this.array[i] = null;
/*     */     }
/* 197 */     this.size = 0;
/* 198 */     this.maxSeenSize = 0;
/* 199 */     this.insertSinceRecycled = false;
/*     */     
/* 201 */     this.recycler.recycle(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object getUnsafe(int index) {
/* 208 */     return this.array[index];
/*     */   }
/*     */   
/*     */   private void checkIndex(int index) {
/* 212 */     if (index >= this.size) {
/* 213 */       throw new IndexOutOfBoundsException("expected: index < (" + this.size + "),but actual is (" + this.size + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void insert(int index, Object element) {
/* 219 */     this.array[index] = element;
/* 220 */     this.insertSinceRecycled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void expandArray() {
/* 225 */     int newCapacity = this.array.length << 1;
/*     */     
/* 227 */     if (newCapacity < 0) {
/* 228 */       throw new OutOfMemoryError();
/*     */     }
/*     */     
/* 231 */     Object[] newArray = new Object[newCapacity];
/* 232 */     System.arraycopy(this.array, 0, newArray, 0, this.array.length);
/*     */     
/* 234 */     this.array = newArray;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\CodecOutputList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */