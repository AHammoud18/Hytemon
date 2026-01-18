/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.MathUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntSupplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface MpscIntQueue
/*     */ {
/*     */   static MpscIntQueue create(int size, int emptyValue) {
/*  44 */     return new MpscAtomicIntegerArrayQueue(size, emptyValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean offer(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int poll();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int drain(int paramInt, IntConsumer paramIntConsumer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int fill(int paramInt, IntSupplier paramIntSupplier);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isEmpty();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class MpscAtomicIntegerArrayQueue
/*     */     extends AtomicIntegerArray
/*     */     implements MpscIntQueue
/*     */   {
/*     */     private static final long serialVersionUID = 8740338425124821455L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     private static final AtomicLongFieldUpdater<MpscAtomicIntegerArrayQueue> PRODUCER_INDEX = AtomicLongFieldUpdater.newUpdater(MpscAtomicIntegerArrayQueue.class, "producerIndex");
/*     */     
/* 101 */     private static final AtomicLongFieldUpdater<MpscAtomicIntegerArrayQueue> PRODUCER_LIMIT = AtomicLongFieldUpdater.newUpdater(MpscAtomicIntegerArrayQueue.class, "producerLimit");
/*     */     
/* 103 */     private static final AtomicLongFieldUpdater<MpscAtomicIntegerArrayQueue> CONSUMER_INDEX = AtomicLongFieldUpdater.newUpdater(MpscAtomicIntegerArrayQueue.class, "consumerIndex");
/*     */     private final int mask;
/*     */     private final int emptyValue;
/*     */     private volatile long producerIndex;
/*     */     private volatile long producerLimit;
/*     */     private volatile long consumerIndex;
/*     */     
/*     */     public MpscAtomicIntegerArrayQueue(int capacity, int emptyValue) {
/* 111 */       super(MathUtil.safeFindNextPositivePowerOfTwo(capacity));
/* 112 */       if (emptyValue != 0) {
/* 113 */         this.emptyValue = emptyValue;
/* 114 */         int end = length() - 1;
/* 115 */         for (int i = 0; i < end; i++) {
/* 116 */           lazySet(i, emptyValue);
/*     */         }
/* 118 */         getAndSet(end, emptyValue);
/*     */       } else {
/* 120 */         this.emptyValue = 0;
/*     */       } 
/* 122 */       this.mask = length() - 1;
/*     */     }
/*     */     
/*     */     public boolean offer(int value) {
/*     */       long pIndex;
/* 127 */       if (value == this.emptyValue) {
/* 128 */         throw new IllegalArgumentException("Cannot offer the \"empty\" value: " + this.emptyValue);
/*     */       }
/*     */       
/* 131 */       int mask = this.mask;
/* 132 */       long producerLimit = this.producerLimit;
/*     */       
/*     */       do {
/* 135 */         pIndex = this.producerIndex;
/* 136 */         if (pIndex < producerLimit)
/* 137 */           continue;  long cIndex = this.consumerIndex;
/* 138 */         producerLimit = cIndex + mask + 1L;
/* 139 */         if (pIndex >= producerLimit)
/*     */         {
/* 141 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 145 */         PRODUCER_LIMIT.lazySet(this, producerLimit);
/*     */       
/*     */       }
/* 148 */       while (!PRODUCER_INDEX.compareAndSet(this, pIndex, pIndex + 1L));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       int offset = (int)(pIndex & mask);
/* 155 */       lazySet(offset, value);
/*     */       
/* 157 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int poll() {
/* 162 */       long cIndex = this.consumerIndex;
/* 163 */       int offset = (int)(cIndex & this.mask);
/*     */       
/* 165 */       int value = get(offset);
/* 166 */       if (this.emptyValue == value)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 172 */         if (cIndex != this.producerIndex) {
/*     */           do {
/* 174 */             value = get(offset);
/* 175 */           } while (this.emptyValue == value);
/*     */         } else {
/* 177 */           return this.emptyValue;
/*     */         } 
/*     */       }
/* 180 */       lazySet(offset, this.emptyValue);
/* 181 */       CONSUMER_INDEX.lazySet(this, cIndex + 1L);
/* 182 */       return value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int drain(int limit, IntConsumer consumer) {
/* 187 */       Objects.requireNonNull(consumer, "consumer");
/* 188 */       ObjectUtil.checkPositiveOrZero(limit, "limit");
/* 189 */       if (limit == 0) {
/* 190 */         return 0;
/*     */       }
/* 192 */       int mask = this.mask;
/* 193 */       long cIndex = this.consumerIndex;
/* 194 */       for (int i = 0; i < limit; i++) {
/* 195 */         long index = cIndex + i;
/* 196 */         int offset = (int)(index & mask);
/* 197 */         int value = get(offset);
/* 198 */         if (this.emptyValue == value) {
/* 199 */           return i;
/*     */         }
/* 201 */         lazySet(offset, this.emptyValue);
/*     */         
/* 203 */         CONSUMER_INDEX.lazySet(this, index + 1L);
/* 204 */         consumer.accept(value);
/*     */       } 
/* 206 */       return limit;
/*     */     }
/*     */     public int fill(int limit, IntSupplier supplier) {
/*     */       long pIndex;
/*     */       int actualLimit;
/* 211 */       Objects.requireNonNull(supplier, "supplier");
/* 212 */       ObjectUtil.checkPositiveOrZero(limit, "limit");
/* 213 */       if (limit == 0) {
/* 214 */         return 0;
/*     */       }
/* 216 */       int mask = this.mask;
/* 217 */       long capacity = (mask + 1);
/* 218 */       long producerLimit = this.producerLimit;
/*     */ 
/*     */       
/*     */       do {
/* 222 */         pIndex = this.producerIndex;
/* 223 */         long available = producerLimit - pIndex;
/* 224 */         if (available <= 0L) {
/* 225 */           long cIndex = this.consumerIndex;
/* 226 */           producerLimit = cIndex + capacity;
/* 227 */           available = producerLimit - pIndex;
/* 228 */           if (available <= 0L)
/*     */           {
/* 230 */             return 0;
/*     */           }
/*     */           
/* 233 */           PRODUCER_LIMIT.lazySet(this, producerLimit);
/*     */         } 
/*     */         
/* 236 */         actualLimit = Math.min((int)available, limit);
/* 237 */       } while (!PRODUCER_INDEX.compareAndSet(this, pIndex, pIndex + actualLimit));
/*     */       
/* 239 */       for (int i = 0; i < actualLimit; i++) {
/*     */         
/* 241 */         int offset = (int)(pIndex + i & mask);
/* 242 */         lazySet(offset, supplier.getAsInt());
/*     */       } 
/* 244 */       return actualLimit;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 250 */       long cIndex = this.consumerIndex;
/* 251 */       long pIndex = this.producerIndex;
/* 252 */       return (cIndex >= pIndex);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 258 */       long after = this.consumerIndex;
/*     */       
/*     */       while (true) {
/* 261 */         long before = after;
/* 262 */         long pIndex = this.producerIndex;
/* 263 */         after = this.consumerIndex;
/* 264 */         if (before == after) {
/* 265 */           long size = pIndex - after;
/*     */ 
/*     */ 
/*     */           
/* 269 */           return (size < 0L) ? 0 : ((size > 2147483647L) ? Integer.MAX_VALUE : (int)size);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\MpscIntQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */