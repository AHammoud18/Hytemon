/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.AbstractReferenceCountedByteBuf;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.ObjectPool;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PromiseNotificationUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ChannelOutboundBuffer
/*     */ {
/*  63 */   static final int CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.outboundBufferEntrySizeOverhead", 96);
/*     */   
/*  65 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);
/*     */   
/*  67 */   private static final FastThreadLocal<ByteBuffer[]> NIO_BUFFERS = new FastThreadLocal<ByteBuffer[]>()
/*     */     {
/*     */       protected ByteBuffer[] initialValue() throws Exception {
/*  70 */         return new ByteBuffer[1024];
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private final Channel channel;
/*     */ 
/*     */   
/*     */   private Entry flushedEntry;
/*     */   
/*     */   private Entry unflushedEntry;
/*     */   
/*     */   private Entry tailEntry;
/*     */   
/*     */   private int flushed;
/*     */   
/*     */   private int nioBufferCount;
/*     */   
/*     */   private long nioBufferSize;
/*     */   
/*     */   private boolean inFail;
/*     */   
/*  93 */   private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> TOTAL_PENDING_SIZE_UPDATER = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
/*     */ 
/*     */   
/*     */   private volatile long totalPendingSize;
/*     */ 
/*     */   
/*  99 */   private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> UNWRITABLE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "unwritable");
/*     */   
/*     */   private volatile int unwritable;
/*     */   
/*     */   private volatile Runnable fireChannelWritabilityChangedTask;
/*     */ 
/*     */   
/*     */   ChannelOutboundBuffer(AbstractChannel channel) {
/* 107 */     this.channel = channel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMessage(Object msg, int size, ChannelPromise promise) {
/* 115 */     Entry entry = Entry.newInstance(msg, size, total(msg), promise);
/* 116 */     if (this.tailEntry == null) {
/* 117 */       this.flushedEntry = null;
/*     */     } else {
/* 119 */       Entry tail = this.tailEntry;
/* 120 */       tail.next = entry;
/*     */     } 
/* 122 */     this.tailEntry = entry;
/* 123 */     if (this.unflushedEntry == null) {
/* 124 */       this.unflushedEntry = entry;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     if (msg instanceof AbstractReferenceCountedByteBuf) {
/* 132 */       ((AbstractReferenceCountedByteBuf)msg).touch();
/*     */     } else {
/* 134 */       ReferenceCountUtil.touch(msg);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 139 */     incrementPendingOutboundBytes(entry.pendingSize, false);
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
/*     */   public void addFlush() {
/* 151 */     Entry entry = this.unflushedEntry;
/* 152 */     if (entry != null) {
/* 153 */       if (this.flushedEntry == null)
/*     */       {
/* 155 */         this.flushedEntry = entry;
/*     */       }
/*     */       do {
/* 158 */         this.flushed++;
/* 159 */         if (!entry.promise.setUncancellable()) {
/*     */           
/* 161 */           int pending = entry.cancel();
/* 162 */           decrementPendingOutboundBytes(pending, false, true);
/*     */         } 
/* 164 */         entry = entry.next;
/* 165 */       } while (entry != null);
/*     */ 
/*     */       
/* 168 */       this.unflushedEntry = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void incrementPendingOutboundBytes(long size) {
/* 177 */     incrementPendingOutboundBytes(size, true);
/*     */   }
/*     */   
/*     */   private void incrementPendingOutboundBytes(long size, boolean invokeLater) {
/* 181 */     if (size == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 185 */     long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, size);
/* 186 */     if (newWriteBufferSize > this.channel.config().getWriteBufferHighWaterMark()) {
/* 187 */       setUnwritable(invokeLater);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void decrementPendingOutboundBytes(long size) {
/* 196 */     decrementPendingOutboundBytes(size, true, true);
/*     */   }
/*     */   
/*     */   private void decrementPendingOutboundBytes(long size, boolean invokeLater, boolean notifyWritability) {
/* 200 */     if (size == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 204 */     long newWriteBufferSize = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -size);
/* 205 */     if (notifyWritability && newWriteBufferSize < this.channel.config().getWriteBufferLowWaterMark()) {
/* 206 */       setWritable(invokeLater);
/*     */     }
/*     */   }
/*     */   
/*     */   private static long total(Object msg) {
/* 211 */     if (msg instanceof ByteBuf) {
/* 212 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 214 */     if (msg instanceof FileRegion) {
/* 215 */       return ((FileRegion)msg).count();
/*     */     }
/* 217 */     if (msg instanceof ByteBufHolder) {
/* 218 */       return ((ByteBufHolder)msg).content().readableBytes();
/*     */     }
/* 220 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object current() {
/* 227 */     Entry entry = this.flushedEntry;
/* 228 */     if (entry == null) {
/* 229 */       return null;
/*     */     }
/*     */     
/* 232 */     return entry.msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long currentProgress() {
/* 240 */     Entry entry = this.flushedEntry;
/* 241 */     if (entry == null) {
/* 242 */       return 0L;
/*     */     }
/* 244 */     return entry.progress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void progress(long amount) {
/* 251 */     Entry e = this.flushedEntry;
/* 252 */     assert e != null;
/* 253 */     ChannelPromise p = e.promise;
/* 254 */     long progress = e.progress + amount;
/* 255 */     e.progress = progress;
/* 256 */     assert p != null;
/* 257 */     Class<?> promiseClass = p.getClass();
/*     */     
/* 259 */     if (promiseClass == VoidChannelPromise.class || promiseClass == DefaultChannelPromise.class) {
/*     */       return;
/*     */     }
/*     */     
/* 263 */     if (p instanceof DefaultChannelProgressivePromise) {
/* 264 */       ((DefaultChannelProgressivePromise)p).tryProgress(progress, e.total);
/* 265 */     } else if (p instanceof ChannelProgressivePromise) {
/* 266 */       ((ChannelProgressivePromise)p).tryProgress(progress, e.total);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove() {
/* 276 */     Entry e = this.flushedEntry;
/* 277 */     if (e == null) {
/* 278 */       clearNioBuffers();
/* 279 */       return false;
/*     */     } 
/* 281 */     Object msg = e.msg;
/*     */     
/* 283 */     ChannelPromise promise = e.promise;
/* 284 */     int size = e.pendingSize;
/*     */     
/* 286 */     removeEntry(e);
/*     */ 
/*     */     
/* 289 */     if (!e.cancelled) {
/*     */ 
/*     */       
/* 292 */       if (msg instanceof AbstractReferenceCountedByteBuf) {
/*     */         
/*     */         try {
/* 295 */           ((AbstractReferenceCountedByteBuf)msg).release();
/* 296 */         } catch (Throwable t) {
/* 297 */           logger.warn("Failed to release a ByteBuf: {}", msg, t);
/*     */         } 
/*     */       } else {
/* 300 */         ReferenceCountUtil.safeRelease(msg);
/*     */       } 
/* 302 */       safeSuccess(promise);
/* 303 */       decrementPendingOutboundBytes(size, false, true);
/*     */     } 
/*     */ 
/*     */     
/* 307 */     e.unguardedRecycle();
/*     */     
/* 309 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Throwable cause) {
/* 318 */     return remove0(cause, true);
/*     */   }
/*     */   
/*     */   private boolean remove0(Throwable cause, boolean notifyWritability) {
/* 322 */     Entry e = this.flushedEntry;
/* 323 */     if (e == null) {
/* 324 */       clearNioBuffers();
/* 325 */       return false;
/*     */     } 
/* 327 */     Object msg = e.msg;
/*     */     
/* 329 */     ChannelPromise promise = e.promise;
/* 330 */     int size = e.pendingSize;
/*     */     
/* 332 */     removeEntry(e);
/*     */     
/* 334 */     if (!e.cancelled) {
/*     */       
/* 336 */       ReferenceCountUtil.safeRelease(msg);
/*     */       
/* 338 */       safeFail(promise, cause);
/* 339 */       decrementPendingOutboundBytes(size, false, notifyWritability);
/*     */     } 
/*     */ 
/*     */     
/* 343 */     e.unguardedRecycle();
/*     */     
/* 345 */     return true;
/*     */   }
/*     */   
/*     */   private void removeEntry(Entry e) {
/* 349 */     if (--this.flushed == 0) {
/*     */       
/* 351 */       this.flushedEntry = null;
/* 352 */       if (e == this.tailEntry) {
/* 353 */         this.tailEntry = null;
/* 354 */         this.unflushedEntry = null;
/*     */       } 
/*     */     } else {
/* 357 */       this.flushedEntry = e.next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeBytes(long writtenBytes) {
/*     */     while (true) {
/* 367 */       Object msg = current();
/* 368 */       if (!(msg instanceof ByteBuf)) {
/* 369 */         assert writtenBytes == 0L;
/*     */         
/*     */         break;
/*     */       } 
/* 373 */       ByteBuf buf = (ByteBuf)msg;
/* 374 */       int readerIndex = buf.readerIndex();
/* 375 */       int readableBytes = buf.writerIndex() - readerIndex;
/*     */       
/* 377 */       if (readableBytes <= writtenBytes) {
/* 378 */         if (writtenBytes != 0L) {
/* 379 */           progress(readableBytes);
/* 380 */           writtenBytes -= readableBytes;
/*     */         } 
/* 382 */         remove(); continue;
/*     */       } 
/* 384 */       if (writtenBytes != 0L) {
/* 385 */         buf.readerIndex(readerIndex + (int)writtenBytes);
/* 386 */         progress(writtenBytes);
/*     */       } 
/*     */       
/*     */       break;
/*     */     } 
/* 391 */     clearNioBuffers();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void clearNioBuffers() {
/* 397 */     int count = this.nioBufferCount;
/* 398 */     if (count > 0) {
/* 399 */       this.nioBufferCount = 0;
/* 400 */       Arrays.fill((Object[])NIO_BUFFERS.get(), 0, count, (Object)null);
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
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 415 */     return nioBuffers(2147483647, 2147483647L);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int maxCount, long maxBytes) {
/* 433 */     assert maxCount > 0;
/* 434 */     assert maxBytes > 0L;
/* 435 */     long nioBufferSize = 0L;
/* 436 */     int nioBufferCount = 0;
/* 437 */     InternalThreadLocalMap threadLocalMap = InternalThreadLocalMap.get();
/* 438 */     ByteBuffer[] nioBuffers = (ByteBuffer[])NIO_BUFFERS.get(threadLocalMap);
/* 439 */     Entry entry = this.flushedEntry;
/* 440 */     while (isFlushedEntry(entry) && entry.msg instanceof ByteBuf) {
/* 441 */       if (!entry.cancelled) {
/* 442 */         ByteBuf buf = (ByteBuf)entry.msg;
/* 443 */         int readerIndex = buf.readerIndex();
/* 444 */         int readableBytes = buf.writerIndex() - readerIndex;
/*     */         
/* 446 */         if (readableBytes > 0) {
/* 447 */           if (maxBytes - readableBytes < nioBufferSize && nioBufferCount != 0) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 461 */           nioBufferSize += readableBytes;
/* 462 */           int count = entry.count;
/* 463 */           if (count == -1)
/*     */           {
/* 465 */             entry.count = count = buf.nioBufferCount();
/*     */           }
/* 467 */           int neededSpace = Math.min(maxCount, nioBufferCount + count);
/* 468 */           if (neededSpace > nioBuffers.length) {
/* 469 */             nioBuffers = expandNioBufferArray(nioBuffers, neededSpace, nioBufferCount);
/* 470 */             NIO_BUFFERS.set(threadLocalMap, nioBuffers);
/*     */           } 
/* 472 */           if (count == 1) {
/* 473 */             ByteBuffer nioBuf = entry.buf;
/* 474 */             if (nioBuf == null)
/*     */             {
/*     */               
/* 477 */               entry.buf = nioBuf = buf.internalNioBuffer(readerIndex, readableBytes);
/*     */             }
/* 479 */             nioBuffers[nioBufferCount++] = nioBuf;
/*     */           }
/*     */           else {
/*     */             
/* 483 */             nioBufferCount = nioBuffers(entry, buf, nioBuffers, nioBufferCount, maxCount);
/*     */           } 
/* 485 */           if (nioBufferCount >= maxCount) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/* 490 */       entry = entry.next;
/*     */     } 
/* 492 */     this.nioBufferCount = nioBufferCount;
/* 493 */     this.nioBufferSize = nioBufferSize;
/*     */     
/* 495 */     return nioBuffers;
/*     */   }
/*     */   
/*     */   private static int nioBuffers(Entry entry, ByteBuf buf, ByteBuffer[] nioBuffers, int nioBufferCount, int maxCount) {
/* 499 */     ByteBuffer[] nioBufs = entry.bufs;
/* 500 */     if (nioBufs == null)
/*     */     {
/*     */       
/* 503 */       entry.bufs = nioBufs = buf.nioBuffers();
/*     */     }
/* 505 */     for (int i = 0; i < nioBufs.length && nioBufferCount < maxCount; i++) {
/* 506 */       ByteBuffer nioBuf = nioBufs[i];
/* 507 */       if (nioBuf == null)
/*     */         break; 
/* 509 */       if (nioBuf.hasRemaining())
/*     */       {
/*     */         
/* 512 */         nioBuffers[nioBufferCount++] = nioBuf; } 
/*     */     } 
/* 514 */     return nioBufferCount;
/*     */   }
/*     */   
/*     */   private static ByteBuffer[] expandNioBufferArray(ByteBuffer[] array, int neededSpace, int size) {
/* 518 */     int newCapacity = array.length;
/*     */ 
/*     */     
/*     */     do {
/* 522 */       newCapacity <<= 1;
/*     */       
/* 524 */       if (newCapacity < 0) {
/* 525 */         throw new IllegalStateException();
/*     */       }
/*     */     }
/* 528 */     while (neededSpace > newCapacity);
/*     */     
/* 530 */     ByteBuffer[] newArray = new ByteBuffer[newCapacity];
/* 531 */     System.arraycopy(array, 0, newArray, 0, size);
/*     */     
/* 533 */     return newArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 542 */     return this.nioBufferCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long nioBufferSize() {
/* 551 */     return this.nioBufferSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/* 561 */     return (this.unwritable == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUserDefinedWritability(int index) {
/* 569 */     return ((this.unwritable & writabilityMask(index)) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserDefinedWritability(int index, boolean writable) {
/* 576 */     if (writable) {
/* 577 */       setUserDefinedWritability(index);
/*     */     } else {
/* 579 */       clearUserDefinedWritability(index);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setUserDefinedWritability(int index) {
/* 584 */     int mask = writabilityMask(index) ^ 0xFFFFFFFF;
/*     */     while (true) {
/* 586 */       int oldValue = this.unwritable;
/* 587 */       int newValue = oldValue & mask;
/* 588 */       if (UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue)) {
/* 589 */         if (oldValue != 0 && newValue == 0) {
/* 590 */           fireChannelWritabilityChanged(true);
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void clearUserDefinedWritability(int index) {
/* 598 */     int mask = writabilityMask(index);
/*     */     while (true) {
/* 600 */       int oldValue = this.unwritable;
/* 601 */       int newValue = oldValue | mask;
/* 602 */       if (UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue)) {
/* 603 */         if (oldValue == 0 && newValue != 0) {
/* 604 */           fireChannelWritabilityChanged(true);
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int writabilityMask(int index) {
/* 612 */     if (index < 1 || index > 31) {
/* 613 */       throw new IllegalArgumentException("index: " + index + " (expected: 1~31)");
/*     */     }
/* 615 */     return 1 << index;
/*     */   }
/*     */   
/*     */   private void setWritable(boolean invokeLater) {
/*     */     while (true) {
/* 620 */       int oldValue = this.unwritable;
/* 621 */       int newValue = oldValue & 0xFFFFFFFE;
/* 622 */       if (UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue)) {
/* 623 */         if (oldValue != 0 && newValue == 0) {
/* 624 */           fireChannelWritabilityChanged(invokeLater);
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setUnwritable(boolean invokeLater) {
/*     */     while (true) {
/* 633 */       int oldValue = this.unwritable;
/* 634 */       int newValue = oldValue | 0x1;
/* 635 */       if (UNWRITABLE_UPDATER.compareAndSet(this, oldValue, newValue)) {
/* 636 */         if (oldValue == 0) {
/* 637 */           fireChannelWritabilityChanged(invokeLater);
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fireChannelWritabilityChanged(boolean invokeLater) {
/* 645 */     final ChannelPipeline pipeline = this.channel.pipeline();
/* 646 */     if (invokeLater) {
/* 647 */       Runnable task = this.fireChannelWritabilityChangedTask;
/* 648 */       if (task == null) {
/* 649 */         this.fireChannelWritabilityChangedTask = task = new Runnable()
/*     */           {
/*     */             public void run() {
/* 652 */               pipeline.fireChannelWritabilityChanged();
/*     */             }
/*     */           };
/*     */       }
/* 656 */       this.channel.eventLoop().execute(task);
/*     */     } else {
/* 658 */       pipeline.fireChannelWritabilityChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 666 */     return this.flushed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 674 */     return (this.flushed == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void failFlushed(Throwable cause, boolean notify) {
/* 683 */     if (this.inFail) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 688 */       this.inFail = true; do {
/*     */       
/* 690 */       } while (remove0(cause, notify));
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 695 */       this.inFail = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   void close(final Throwable cause, final boolean allowChannelOpen) {
/* 700 */     if (this.inFail) {
/* 701 */       this.channel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 704 */               ChannelOutboundBuffer.this.close(cause, allowChannelOpen);
/*     */             }
/*     */           });
/*     */       
/*     */       return;
/*     */     } 
/* 710 */     this.inFail = true;
/*     */     
/* 712 */     if (!allowChannelOpen && this.channel.isOpen()) {
/* 713 */       throw new IllegalStateException("close() must be invoked after the channel is closed.");
/*     */     }
/*     */     
/* 716 */     if (!isEmpty()) {
/* 717 */       throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 722 */       Entry e = this.unflushedEntry;
/* 723 */       while (e != null) {
/*     */         
/* 725 */         int size = e.pendingSize;
/* 726 */         TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -size);
/*     */         
/* 728 */         if (!e.cancelled) {
/* 729 */           ReferenceCountUtil.safeRelease(e.msg);
/* 730 */           safeFail(e.promise, cause);
/*     */         } 
/* 732 */         e = e.unguardedRecycleAndGetNext();
/*     */       } 
/*     */     } finally {
/* 735 */       this.inFail = false;
/*     */     } 
/* 737 */     clearNioBuffers();
/*     */   }
/*     */   
/*     */   void close(ClosedChannelException cause) {
/* 741 */     close(cause, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void safeSuccess(ChannelPromise promise) {
/* 747 */     PromiseNotificationUtil.trySuccess(promise, null, (promise instanceof VoidChannelPromise) ? null : logger);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void safeFail(ChannelPromise promise, Throwable cause) {
/* 753 */     PromiseNotificationUtil.tryFailure(promise, cause, (promise instanceof VoidChannelPromise) ? null : logger);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void recycle() {}
/*     */ 
/*     */   
/*     */   public long totalPendingWriteBytes() {
/* 762 */     return this.totalPendingSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long bytesBeforeUnwritable() {
/* 771 */     long bytes = this.channel.config().getWriteBufferHighWaterMark() - this.totalPendingSize + 1L;
/*     */ 
/*     */ 
/*     */     
/* 775 */     return (bytes > 0L && isWritable()) ? bytes : 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long bytesBeforeWritable() {
/* 784 */     long bytes = this.totalPendingSize - this.channel.config().getWriteBufferLowWaterMark() + 1L;
/*     */ 
/*     */ 
/*     */     
/* 788 */     return (bytes <= 0L || isWritable()) ? 0L : bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEachFlushedMessage(MessageProcessor processor) throws Exception {
/* 797 */     ObjectUtil.checkNotNull(processor, "processor");
/*     */     
/* 799 */     Entry entry = this.flushedEntry;
/* 800 */     if (entry == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     do {
/* 805 */       if (!entry.cancelled && 
/* 806 */         !processor.processMessage(entry.msg)) {
/*     */         return;
/*     */       }
/*     */       
/* 810 */       entry = entry.next;
/* 811 */     } while (isFlushedEntry(entry));
/*     */   }
/*     */   
/*     */   private boolean isFlushedEntry(Entry e) {
/* 815 */     return (e != null && e != this.unflushedEntry);
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface MessageProcessor
/*     */   {
/*     */     boolean processMessage(Object param1Object) throws Exception;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class Entry
/*     */   {
/* 827 */     private static final Recycler<Entry> RECYCLER = new Recycler<Entry>()
/*     */       {
/*     */         protected ChannelOutboundBuffer.Entry newObject(Recycler.Handle<ChannelOutboundBuffer.Entry> handle) {
/* 830 */           return new ChannelOutboundBuffer.Entry((ObjectPool.Handle)handle);
/*     */         }
/*     */       };
/*     */     
/*     */     private final Recycler.EnhancedHandle<Entry> handle;
/*     */     Entry next;
/*     */     Object msg;
/*     */     ByteBuffer[] bufs;
/*     */     ByteBuffer buf;
/*     */     ChannelPromise promise;
/*     */     long progress;
/*     */     long total;
/*     */     int pendingSize;
/* 843 */     int count = -1;
/*     */     boolean cancelled;
/*     */     
/*     */     private Entry(ObjectPool.Handle<Entry> handle) {
/* 847 */       this.handle = (Recycler.EnhancedHandle<Entry>)handle;
/*     */     }
/*     */     
/*     */     static Entry newInstance(Object msg, int size, long total, ChannelPromise promise) {
/* 851 */       Entry entry = (Entry)RECYCLER.get();
/* 852 */       entry.msg = msg;
/* 853 */       entry.pendingSize = size + ChannelOutboundBuffer.CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD;
/* 854 */       entry.total = total;
/* 855 */       entry.promise = promise;
/* 856 */       return entry;
/*     */     }
/*     */     
/*     */     int cancel() {
/* 860 */       if (!this.cancelled) {
/* 861 */         this.cancelled = true;
/* 862 */         int pSize = this.pendingSize;
/*     */ 
/*     */         
/* 865 */         ReferenceCountUtil.safeRelease(this.msg);
/* 866 */         this.msg = Unpooled.EMPTY_BUFFER;
/*     */         
/* 868 */         this.pendingSize = 0;
/* 869 */         this.total = 0L;
/* 870 */         this.progress = 0L;
/* 871 */         this.bufs = null;
/* 872 */         this.buf = null;
/* 873 */         return pSize;
/*     */       } 
/* 875 */       return 0;
/*     */     }
/*     */     
/*     */     void unguardedRecycle() {
/* 879 */       this.next = null;
/* 880 */       this.bufs = null;
/* 881 */       this.buf = null;
/* 882 */       this.msg = null;
/* 883 */       this.promise = null;
/* 884 */       this.progress = 0L;
/* 885 */       this.total = 0L;
/* 886 */       this.pendingSize = 0;
/* 887 */       this.count = -1;
/* 888 */       this.cancelled = false;
/* 889 */       this.handle.unguardedRecycle(this);
/*     */     }
/*     */     
/*     */     Entry unguardedRecycleAndGetNext() {
/* 893 */       Entry next = this.next;
/* 894 */       unguardedRecycle();
/* 895 */       return next;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelOutboundBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */