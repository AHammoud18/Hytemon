/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.AbstractReferenceCountedByteBuf;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.PromiseCombiner;
/*     */ import io.netty.util.internal.ObjectPool;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PendingWriteQueue
/*     */ {
/*  35 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PendingWriteQueue.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private static final int PENDING_WRITE_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.pendingWriteSizeOverhead", 64);
/*     */   
/*     */   private final ChannelOutboundInvoker invoker;
/*     */   
/*     */   private final EventExecutor executor;
/*     */   
/*     */   private final PendingBytesTracker tracker;
/*     */   private PendingWrite head;
/*     */   private PendingWrite tail;
/*     */   private int size;
/*     */   private long bytes;
/*     */   
/*     */   public PendingWriteQueue(ChannelHandlerContext ctx) {
/*  54 */     this.tracker = PendingBytesTracker.newTracker(ctx.channel());
/*  55 */     this.invoker = ctx;
/*  56 */     this.executor = ctx.executor();
/*     */   }
/*     */   
/*     */   public PendingWriteQueue(Channel channel) {
/*  60 */     this.tracker = PendingBytesTracker.newTracker(channel);
/*  61 */     this.invoker = channel;
/*  62 */     this.executor = (EventExecutor)channel.eventLoop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  69 */     assert this.executor.inEventLoop();
/*  70 */     return (this.head == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  77 */     assert this.executor.inEventLoop();
/*  78 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long bytes() {
/*  86 */     assert this.executor.inEventLoop();
/*  87 */     return this.bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int size(Object msg) {
/*  93 */     int messageSize = this.tracker.size(msg);
/*  94 */     if (messageSize < 0)
/*     */     {
/*  96 */       messageSize = 0;
/*     */     }
/*  98 */     return messageSize + PENDING_WRITE_OVERHEAD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Object msg, ChannelPromise promise) {
/* 105 */     assert this.executor.inEventLoop();
/* 106 */     ObjectUtil.checkNotNull(msg, "msg");
/* 107 */     ObjectUtil.checkNotNull(promise, "promise");
/*     */ 
/*     */     
/* 110 */     int messageSize = size(msg);
/*     */     
/* 112 */     PendingWrite write = PendingWrite.newInstance(msg, messageSize, promise);
/* 113 */     PendingWrite currentTail = this.tail;
/* 114 */     if (currentTail == null) {
/* 115 */       this.tail = this.head = write;
/*     */     } else {
/* 117 */       currentTail.next = write;
/* 118 */       this.tail = write;
/*     */     } 
/* 120 */     this.size++;
/* 121 */     this.bytes += messageSize;
/* 122 */     this.tracker.incrementPendingOutboundBytes(write.size);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (msg instanceof AbstractReferenceCountedByteBuf) {
/* 128 */       ((AbstractReferenceCountedByteBuf)msg).touch();
/*     */     } else {
/* 130 */       ReferenceCountUtil.touch(msg);
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
/*     */   public ChannelFuture removeAndWriteAll() {
/* 142 */     assert this.executor.inEventLoop();
/*     */     
/* 144 */     if (isEmpty()) {
/* 145 */       return null;
/*     */     }
/*     */     
/* 148 */     ChannelPromise p = this.invoker.newPromise();
/* 149 */     PromiseCombiner combiner = new PromiseCombiner(this.executor);
/*     */ 
/*     */     
/*     */     try {
/* 153 */       for (PendingWrite write = this.head; write != null; write = this.head) {
/* 154 */         this.head = this.tail = null;
/* 155 */         this.size = 0;
/* 156 */         this.bytes = 0L;
/*     */         
/* 158 */         while (write != null) {
/* 159 */           PendingWrite next = write.next;
/* 160 */           Object msg = write.msg;
/* 161 */           ChannelPromise promise = write.promise;
/* 162 */           recycle(write, false);
/* 163 */           if (!(promise instanceof VoidChannelPromise)) {
/* 164 */             combiner.add(promise);
/*     */           }
/* 166 */           this.invoker.write(msg, promise);
/* 167 */           write = next;
/*     */         } 
/*     */       } 
/* 170 */       combiner.finish(p);
/* 171 */     } catch (Throwable cause) {
/* 172 */       p.setFailure(cause);
/*     */     } 
/* 174 */     assertEmpty();
/* 175 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAndFailAll(Throwable cause) {
/* 183 */     assert this.executor.inEventLoop();
/* 184 */     ObjectUtil.checkNotNull(cause, "cause");
/*     */ 
/*     */     
/* 187 */     for (PendingWrite write = this.head; write != null; write = this.head) {
/* 188 */       this.head = this.tail = null;
/* 189 */       this.size = 0;
/* 190 */       this.bytes = 0L;
/* 191 */       while (write != null) {
/* 192 */         PendingWrite next = write.next;
/* 193 */         ReferenceCountUtil.safeRelease(write.msg);
/* 194 */         ChannelPromise promise = write.promise;
/* 195 */         recycle(write, false);
/* 196 */         safeFail(promise, cause);
/* 197 */         write = next;
/*     */       } 
/*     */     } 
/* 200 */     assertEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAndFail(Throwable cause) {
/* 208 */     assert this.executor.inEventLoop();
/* 209 */     ObjectUtil.checkNotNull(cause, "cause");
/*     */     
/* 211 */     PendingWrite write = this.head;
/* 212 */     if (write == null) {
/*     */       return;
/*     */     }
/* 215 */     ReferenceCountUtil.safeRelease(write.msg);
/* 216 */     ChannelPromise promise = write.promise;
/* 217 */     safeFail(promise, cause);
/* 218 */     recycle(write, true);
/*     */   }
/*     */   
/*     */   private void assertEmpty() {
/* 222 */     assert this.tail == null && this.head == null && this.size == 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture removeAndWrite() {
/* 233 */     assert this.executor.inEventLoop();
/* 234 */     PendingWrite write = this.head;
/* 235 */     if (write == null) {
/* 236 */       return null;
/*     */     }
/* 238 */     Object msg = write.msg;
/* 239 */     ChannelPromise promise = write.promise;
/* 240 */     recycle(write, true);
/* 241 */     return this.invoker.write(msg, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelPromise remove() {
/* 251 */     assert this.executor.inEventLoop();
/* 252 */     PendingWrite write = this.head;
/* 253 */     if (write == null) {
/* 254 */       return null;
/*     */     }
/* 256 */     ChannelPromise promise = write.promise;
/* 257 */     ReferenceCountUtil.safeRelease(write.msg);
/* 258 */     recycle(write, true);
/* 259 */     return promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object current() {
/* 266 */     assert this.executor.inEventLoop();
/* 267 */     PendingWrite write = this.head;
/* 268 */     if (write == null) {
/* 269 */       return null;
/*     */     }
/* 271 */     return write.msg;
/*     */   }
/*     */   
/*     */   private void recycle(PendingWrite write, boolean update) {
/* 275 */     PendingWrite next = write.next;
/* 276 */     long writeSize = write.size;
/*     */     
/* 278 */     if (update) {
/* 279 */       if (next == null) {
/*     */ 
/*     */         
/* 282 */         this.head = this.tail = null;
/* 283 */         this.size = 0;
/* 284 */         this.bytes = 0L;
/*     */       } else {
/* 286 */         this.head = next;
/* 287 */         this.size--;
/* 288 */         this.bytes -= writeSize;
/* 289 */         assert this.size > 0 && this.bytes >= 0L;
/*     */       } 
/*     */     }
/*     */     
/* 293 */     write.recycle();
/* 294 */     this.tracker.decrementPendingOutboundBytes(writeSize);
/*     */   }
/*     */   
/*     */   private static void safeFail(ChannelPromise promise, Throwable cause) {
/* 298 */     if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
/* 299 */       logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class PendingWrite
/*     */   {
/* 307 */     private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>()
/*     */       {
/*     */         protected PendingWriteQueue.PendingWrite newObject(Recycler.Handle<PendingWriteQueue.PendingWrite> handle)
/*     */         {
/* 311 */           return new PendingWriteQueue.PendingWrite((ObjectPool.Handle)handle);
/*     */         }
/*     */       };
/*     */     
/*     */     private final ObjectPool.Handle<PendingWrite> handle;
/*     */     private PendingWrite next;
/*     */     private long size;
/*     */     private ChannelPromise promise;
/*     */     private Object msg;
/*     */     
/*     */     private PendingWrite(ObjectPool.Handle<PendingWrite> handle) {
/* 322 */       this.handle = handle;
/*     */     }
/*     */     
/*     */     static PendingWrite newInstance(Object msg, int size, ChannelPromise promise) {
/* 326 */       PendingWrite write = (PendingWrite)RECYCLER.get();
/* 327 */       write.size = size;
/* 328 */       write.msg = msg;
/* 329 */       write.promise = promise;
/* 330 */       return write;
/*     */     }
/*     */     
/*     */     private void recycle() {
/* 334 */       this.size = 0L;
/* 335 */       this.next = null;
/* 336 */       this.msg = null;
/* 337 */       this.promise = null;
/* 338 */       this.handle.recycle(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\PendingWriteQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */