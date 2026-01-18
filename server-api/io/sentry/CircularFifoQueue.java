/*     */ package io.sentry;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CircularFifoQueue<E>
/*     */   extends AbstractCollection<E>
/*     */   implements Queue<E>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8423413834657610406L;
/*     */   @NotNull
/*     */   private transient E[] elements;
/*  61 */   private transient int start = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private transient int end = 0;
/*     */ 
/*     */   
/*     */   private transient boolean full = false;
/*     */ 
/*     */   
/*     */   private final int maxElements;
/*     */ 
/*     */   
/*     */   public CircularFifoQueue() {
/*  79 */     this(32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CircularFifoQueue(int size) {
/*  90 */     if (size <= 0) {
/*  91 */       throw new IllegalArgumentException("The size must be greater than 0");
/*     */     }
/*  93 */     this.elements = (E[])new Object[size];
/*  94 */     this.maxElements = this.elements.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CircularFifoQueue(@NotNull Collection<? extends E> coll) {
/* 105 */     this(coll.size());
/* 106 */     addAll(coll);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(@NotNull ObjectOutputStream out) throws IOException {
/* 117 */     out.defaultWriteObject();
/* 118 */     out.writeInt(size());
/* 119 */     for (E e : this) {
/* 120 */       out.writeObject(e);
/*     */     }
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
/*     */   private void readObject(@NotNull ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 134 */     in.defaultReadObject();
/* 135 */     this.elements = (E[])new Object[this.maxElements];
/* 136 */     int size = in.readInt();
/* 137 */     for (int i = 0; i < size; i++) {
/* 138 */       this.elements[i] = (E)in.readObject();
/*     */     }
/* 140 */     this.start = 0;
/* 141 */     this.full = (size == this.maxElements);
/* 142 */     if (this.full) {
/* 143 */       this.end = 0;
/*     */     } else {
/* 145 */       this.end = size;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 157 */     int size = 0;
/*     */     
/* 159 */     if (this.end < this.start) {
/* 160 */       size = this.maxElements - this.start + this.end;
/* 161 */     } else if (this.end == this.start) {
/* 162 */       size = this.full ? this.maxElements : 0;
/*     */     } else {
/* 164 */       size = this.end - this.start;
/*     */     } 
/*     */     
/* 167 */     return size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 177 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFull() {
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAtFullCapacity() {
/* 197 */     return (size() == this.maxElements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 206 */     return this.maxElements;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 212 */     this.full = false;
/* 213 */     this.start = 0;
/* 214 */     this.end = 0;
/* 215 */     Arrays.fill((Object[])this.elements, (Object)null);
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
/*     */   public boolean add(@NotNull E element) {
/* 228 */     if (null == element) {
/* 229 */       throw new NullPointerException("Attempted to add null object to queue");
/*     */     }
/*     */     
/* 232 */     if (isAtFullCapacity()) {
/* 233 */       remove();
/*     */     }
/*     */     
/* 236 */     this.elements[this.end++] = element;
/*     */     
/* 238 */     if (this.end >= this.maxElements) {
/* 239 */       this.end = 0;
/*     */     }
/*     */     
/* 242 */     if (this.end == this.start) {
/* 243 */       this.full = true;
/*     */     }
/*     */     
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public E get(int index) {
/* 257 */     int sz = size();
/* 258 */     if (index < 0 || index >= sz) {
/* 259 */       throw new NoSuchElementException(
/* 260 */           String.format("The specified index (%1$d) is outside the available range [0, %2$d)", new Object[] {
/*     */               
/* 262 */               Integer.valueOf(index), Integer.valueOf(sz)
/*     */             }));
/*     */     }
/* 265 */     int idx = (this.start + index) % this.maxElements;
/* 266 */     return this.elements[idx];
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
/*     */   
/*     */   public boolean offer(@NotNull E element) {
/* 281 */     return add(element);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public E poll() {
/* 286 */     if (isEmpty()) {
/* 287 */       return null;
/*     */     }
/* 289 */     return remove();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public E element() {
/* 294 */     if (isEmpty()) {
/* 295 */       throw new NoSuchElementException("queue is empty");
/*     */     }
/* 297 */     return peek();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public E peek() {
/* 302 */     if (isEmpty()) {
/* 303 */       return null;
/*     */     }
/* 305 */     return this.elements[this.start];
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public E remove() {
/* 310 */     if (isEmpty()) {
/* 311 */       throw new NoSuchElementException("queue is empty");
/*     */     }
/*     */     
/* 314 */     E element = this.elements[this.start];
/* 315 */     if (null != element) {
/* 316 */       this.elements[this.start++] = null;
/*     */       
/* 318 */       if (this.start >= this.maxElements) {
/* 319 */         this.start = 0;
/*     */       }
/* 321 */       this.full = false;
/*     */     } 
/* 323 */     return element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int increment(int index) {
/* 334 */     index++;
/* 335 */     if (index >= this.maxElements) {
/* 336 */       index = 0;
/*     */     }
/* 338 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int decrement(int index) {
/* 348 */     index--;
/* 349 */     if (index < 0) {
/* 350 */       index = this.maxElements - 1;
/*     */     }
/* 352 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Iterator<E> iterator() {
/* 362 */     return new Iterator<E>()
/*     */       {
/* 364 */         private int index = CircularFifoQueue.this.start;
/* 365 */         private int lastReturnedIndex = -1;
/* 366 */         private boolean isFirst = CircularFifoQueue.this.full;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 370 */           return (this.isFirst || this.index != CircularFifoQueue.this.end);
/*     */         }
/*     */ 
/*     */         
/*     */         public E next() {
/* 375 */           if (!hasNext()) {
/* 376 */             throw new NoSuchElementException();
/*     */           }
/* 378 */           this.isFirst = false;
/* 379 */           this.lastReturnedIndex = this.index;
/* 380 */           this.index = CircularFifoQueue.this.increment(this.index);
/* 381 */           return (E)CircularFifoQueue.this.elements[this.lastReturnedIndex];
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 386 */           if (this.lastReturnedIndex == -1) {
/* 387 */             throw new IllegalStateException();
/*     */           }
/*     */ 
/*     */           
/* 391 */           if (this.lastReturnedIndex == CircularFifoQueue.this.start) {
/* 392 */             CircularFifoQueue.this.remove();
/* 393 */             this.lastReturnedIndex = -1;
/*     */             
/*     */             return;
/*     */           } 
/* 397 */           int pos = this.lastReturnedIndex + 1;
/* 398 */           if (CircularFifoQueue.this.start < this.lastReturnedIndex && pos < CircularFifoQueue.this.end) {
/*     */             
/* 400 */             System.arraycopy(CircularFifoQueue.this.elements, pos, CircularFifoQueue.this.elements, this.lastReturnedIndex, CircularFifoQueue.this.end - pos);
/*     */           } else {
/*     */             
/* 403 */             while (pos != CircularFifoQueue.this.end) {
/* 404 */               if (pos >= CircularFifoQueue.this.maxElements) {
/* 405 */                 CircularFifoQueue.this.elements[pos - 1] = CircularFifoQueue.this.elements[0];
/* 406 */                 pos = 0; continue;
/*     */               } 
/* 408 */               CircularFifoQueue.this.elements[CircularFifoQueue.this.decrement(pos)] = CircularFifoQueue.this.elements[pos];
/* 409 */               pos = CircularFifoQueue.this.increment(pos);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 414 */           this.lastReturnedIndex = -1;
/* 415 */           CircularFifoQueue.this.end = CircularFifoQueue.this.decrement(CircularFifoQueue.this.end);
/* 416 */           CircularFifoQueue.this.elements[CircularFifoQueue.this.end] = null;
/* 417 */           CircularFifoQueue.this.full = false;
/* 418 */           this.index = CircularFifoQueue.this.decrement(this.index);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\CircularFifoQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */