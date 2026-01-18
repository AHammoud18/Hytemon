/*     */ package io.netty.handler.timeout;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.Ticker;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ public class IdleStateHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/* 101 */   private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*     */ 
/*     */   
/* 104 */   private final ChannelFutureListener writeListener = new ChannelFutureListener()
/*     */     {
/*     */       public void operationComplete(ChannelFuture future) throws Exception {
/* 107 */         IdleStateHandler.this.lastWriteTime = IdleStateHandler.this.ticker.nanoTime();
/* 108 */         IdleStateHandler.this.firstWriterIdleEvent = IdleStateHandler.this.firstAllIdleEvent = true;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final boolean observeOutput;
/*     */   private final long readerIdleTimeNanos;
/*     */   private final long writerIdleTimeNanos;
/*     */   private final long allIdleTimeNanos;
/* 117 */   private Ticker ticker = Ticker.systemTicker();
/*     */ 
/*     */   
/*     */   private Future<?> readerIdleTimeout;
/*     */ 
/*     */   
/*     */   private long lastReadTime;
/*     */ 
/*     */   
/*     */   private boolean firstReaderIdleEvent = true;
/*     */ 
/*     */   
/*     */   private Future<?> writerIdleTimeout;
/*     */ 
/*     */   
/*     */   private long lastWriteTime;
/*     */ 
/*     */   
/*     */   private boolean firstWriterIdleEvent = true;
/*     */ 
/*     */   
/*     */   private Future<?> allIdleTimeout;
/*     */ 
/*     */   
/*     */   private boolean firstAllIdleEvent = true;
/*     */ 
/*     */   
/*     */   private byte state;
/*     */   
/*     */   private static final byte ST_INITIALIZED = 1;
/*     */   
/*     */   private static final byte ST_DESTROYED = 2;
/*     */   
/*     */   private boolean reading;
/*     */   
/*     */   private long lastChangeCheckTimeStamp;
/*     */   
/*     */   private int lastMessageHashCode;
/*     */   
/*     */   private long lastPendingWriteBytes;
/*     */   
/*     */   private long lastFlushProgress;
/*     */ 
/*     */   
/*     */   public IdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
/* 162 */     this(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds, TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
/* 172 */     this(false, readerIdleTime, writerIdleTime, allIdleTime, unit);
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
/*     */   public IdleStateHandler(boolean observeOutput, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
/* 200 */     ObjectUtil.checkNotNull(unit, "unit");
/*     */     
/* 202 */     this.observeOutput = observeOutput;
/*     */     
/* 204 */     if (readerIdleTime <= 0L) {
/* 205 */       this.readerIdleTimeNanos = 0L;
/*     */     } else {
/* 207 */       this.readerIdleTimeNanos = Math.max(unit.toNanos(readerIdleTime), MIN_TIMEOUT_NANOS);
/*     */     } 
/* 209 */     if (writerIdleTime <= 0L) {
/* 210 */       this.writerIdleTimeNanos = 0L;
/*     */     } else {
/* 212 */       this.writerIdleTimeNanos = Math.max(unit.toNanos(writerIdleTime), MIN_TIMEOUT_NANOS);
/*     */     } 
/* 214 */     if (allIdleTime <= 0L) {
/* 215 */       this.allIdleTimeNanos = 0L;
/*     */     } else {
/* 217 */       this.allIdleTimeNanos = Math.max(unit.toNanos(allIdleTime), MIN_TIMEOUT_NANOS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getReaderIdleTimeInMillis() {
/* 226 */     return TimeUnit.NANOSECONDS.toMillis(this.readerIdleTimeNanos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWriterIdleTimeInMillis() {
/* 234 */     return TimeUnit.NANOSECONDS.toMillis(this.writerIdleTimeNanos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAllIdleTimeInMillis() {
/* 242 */     return TimeUnit.NANOSECONDS.toMillis(this.allIdleTimeNanos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 247 */     this.ticker = ctx.executor().ticker();
/* 248 */     if (ctx.channel().isActive() && ctx.channel().isRegistered())
/*     */     {
/*     */       
/* 251 */       initialize(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 260 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 266 */     if (ctx.channel().isActive()) {
/* 267 */       initialize(ctx);
/*     */     }
/* 269 */     super.channelRegistered(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 277 */     initialize(ctx);
/* 278 */     super.channelActive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 283 */     destroy();
/* 284 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 289 */     if (this.readerIdleTimeNanos > 0L || this.allIdleTimeNanos > 0L) {
/* 290 */       this.reading = true;
/* 291 */       this.firstReaderIdleEvent = this.firstAllIdleEvent = true;
/*     */     } 
/* 293 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 298 */     if ((this.readerIdleTimeNanos > 0L || this.allIdleTimeNanos > 0L) && this.reading) {
/* 299 */       this.lastReadTime = this.ticker.nanoTime();
/* 300 */       this.reading = false;
/*     */     } 
/* 302 */     ctx.fireChannelReadComplete();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 308 */     if (this.writerIdleTimeNanos > 0L || this.allIdleTimeNanos > 0L) {
/* 309 */       ctx.write(msg, promise.unvoid()).addListener((GenericFutureListener)this.writeListener);
/*     */     } else {
/* 311 */       ctx.write(msg, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetReadTimeout() {
/* 319 */     if (this.readerIdleTimeNanos > 0L || this.allIdleTimeNanos > 0L) {
/* 320 */       this.lastReadTime = this.ticker.nanoTime();
/* 321 */       this.reading = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetWriteTimeout() {
/* 329 */     if (this.writerIdleTimeNanos > 0L || this.allIdleTimeNanos > 0L) {
/* 330 */       this.lastWriteTime = this.ticker.nanoTime();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize(ChannelHandlerContext ctx) {
/* 337 */     switch (this.state) {
/*     */       case 1:
/*     */       case 2:
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 345 */     this.state = 1;
/* 346 */     initOutputChanged(ctx);
/*     */     
/* 348 */     this.lastReadTime = this.lastWriteTime = this.ticker.nanoTime();
/* 349 */     if (this.readerIdleTimeNanos > 0L) {
/* 350 */       this.readerIdleTimeout = schedule(ctx, new ReaderIdleTimeoutTask(ctx), this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */     
/* 353 */     if (this.writerIdleTimeNanos > 0L) {
/* 354 */       this.writerIdleTimeout = schedule(ctx, new WriterIdleTimeoutTask(ctx), this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */     
/* 357 */     if (this.allIdleTimeNanos > 0L) {
/* 358 */       this.allIdleTimeout = schedule(ctx, new AllIdleTimeoutTask(ctx), this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Future<?> schedule(ChannelHandlerContext ctx, Runnable task, long delay, TimeUnit unit) {
/* 367 */     return (Future<?>)ctx.executor().schedule(task, delay, unit);
/*     */   }
/*     */   
/*     */   private void destroy() {
/* 371 */     this.state = 2;
/*     */     
/* 373 */     if (this.readerIdleTimeout != null) {
/* 374 */       this.readerIdleTimeout.cancel(false);
/* 375 */       this.readerIdleTimeout = null;
/*     */     } 
/* 377 */     if (this.writerIdleTimeout != null) {
/* 378 */       this.writerIdleTimeout.cancel(false);
/* 379 */       this.writerIdleTimeout = null;
/*     */     } 
/* 381 */     if (this.allIdleTimeout != null) {
/* 382 */       this.allIdleTimeout.cancel(false);
/* 383 */       this.allIdleTimeout = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
/* 392 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IdleStateEvent newIdleStateEvent(IdleState state, boolean first) {
/* 399 */     switch (state) {
/*     */       case ALL_IDLE:
/* 401 */         return first ? IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT : IdleStateEvent.ALL_IDLE_STATE_EVENT;
/*     */       case READER_IDLE:
/* 403 */         return first ? IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT : IdleStateEvent.READER_IDLE_STATE_EVENT;
/*     */       case WRITER_IDLE:
/* 405 */         return first ? IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT : IdleStateEvent.WRITER_IDLE_STATE_EVENT;
/*     */     } 
/* 407 */     throw new IllegalArgumentException("Unhandled: state=" + state + ", first=" + first);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initOutputChanged(ChannelHandlerContext ctx) {
/* 415 */     if (this.observeOutput) {
/* 416 */       Channel channel = ctx.channel();
/* 417 */       Channel.Unsafe unsafe = channel.unsafe();
/* 418 */       ChannelOutboundBuffer buf = unsafe.outboundBuffer();
/*     */       
/* 420 */       if (buf != null) {
/* 421 */         this.lastMessageHashCode = System.identityHashCode(buf.current());
/* 422 */         this.lastPendingWriteBytes = buf.totalPendingWriteBytes();
/* 423 */         this.lastFlushProgress = buf.currentProgress();
/*     */       } 
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
/*     */   private boolean hasOutputChanged(ChannelHandlerContext ctx, boolean first) {
/* 436 */     if (this.observeOutput) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 443 */       if (this.lastChangeCheckTimeStamp != this.lastWriteTime) {
/* 444 */         this.lastChangeCheckTimeStamp = this.lastWriteTime;
/*     */ 
/*     */         
/* 447 */         if (!first) {
/* 448 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 452 */       Channel channel = ctx.channel();
/* 453 */       Channel.Unsafe unsafe = channel.unsafe();
/* 454 */       ChannelOutboundBuffer buf = unsafe.outboundBuffer();
/*     */       
/* 456 */       if (buf != null) {
/* 457 */         int messageHashCode = System.identityHashCode(buf.current());
/* 458 */         long pendingWriteBytes = buf.totalPendingWriteBytes();
/*     */         
/* 460 */         if (messageHashCode != this.lastMessageHashCode || pendingWriteBytes != this.lastPendingWriteBytes) {
/* 461 */           this.lastMessageHashCode = messageHashCode;
/* 462 */           this.lastPendingWriteBytes = pendingWriteBytes;
/*     */           
/* 464 */           if (!first) {
/* 465 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 469 */         long flushProgress = buf.currentProgress();
/* 470 */         if (flushProgress != this.lastFlushProgress) {
/* 471 */           this.lastFlushProgress = flushProgress;
/* 472 */           return !first;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 477 */     return false;
/*     */   }
/*     */   
/*     */   private static abstract class AbstractIdleTask
/*     */     implements Runnable {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     AbstractIdleTask(ChannelHandlerContext ctx) {
/* 485 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 490 */       if (!this.ctx.channel().isOpen()) {
/*     */         return;
/*     */       }
/*     */       
/* 494 */       run(this.ctx);
/*     */     }
/*     */     
/*     */     protected abstract void run(ChannelHandlerContext param1ChannelHandlerContext);
/*     */   }
/*     */   
/*     */   private final class ReaderIdleTimeoutTask
/*     */     extends AbstractIdleTask {
/*     */     ReaderIdleTimeoutTask(ChannelHandlerContext ctx) {
/* 503 */       super(ctx);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void run(ChannelHandlerContext ctx) {
/* 508 */       long nextDelay = IdleStateHandler.this.readerIdleTimeNanos;
/* 509 */       if (!IdleStateHandler.this.reading) {
/* 510 */         nextDelay -= IdleStateHandler.this.ticker.nanoTime() - IdleStateHandler.this.lastReadTime;
/*     */       }
/*     */       
/* 513 */       if (nextDelay <= 0L) {
/*     */         
/* 515 */         IdleStateHandler.this.readerIdleTimeout = IdleStateHandler.this.schedule(ctx, this, IdleStateHandler.this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */         
/* 517 */         boolean first = IdleStateHandler.this.firstReaderIdleEvent;
/* 518 */         IdleStateHandler.this.firstReaderIdleEvent = false;
/*     */         
/*     */         try {
/* 521 */           IdleStateEvent event = IdleStateHandler.this.newIdleStateEvent(IdleState.READER_IDLE, first);
/* 522 */           IdleStateHandler.this.channelIdle(ctx, event);
/* 523 */         } catch (Throwable t) {
/* 524 */           ctx.fireExceptionCaught(t);
/*     */         } 
/*     */       } else {
/*     */         
/* 528 */         IdleStateHandler.this.readerIdleTimeout = IdleStateHandler.this.schedule(ctx, this, nextDelay, TimeUnit.NANOSECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class WriterIdleTimeoutTask
/*     */     extends AbstractIdleTask {
/*     */     WriterIdleTimeoutTask(ChannelHandlerContext ctx) {
/* 536 */       super(ctx);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void run(ChannelHandlerContext ctx) {
/* 542 */       long lastWriteTime = IdleStateHandler.this.lastWriteTime;
/* 543 */       long nextDelay = IdleStateHandler.this.writerIdleTimeNanos - IdleStateHandler.this.ticker.nanoTime() - lastWriteTime;
/* 544 */       if (nextDelay <= 0L) {
/*     */         
/* 546 */         IdleStateHandler.this.writerIdleTimeout = IdleStateHandler.this.schedule(ctx, this, IdleStateHandler.this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */         
/* 548 */         boolean first = IdleStateHandler.this.firstWriterIdleEvent;
/* 549 */         IdleStateHandler.this.firstWriterIdleEvent = false;
/*     */         
/*     */         try {
/* 552 */           if (IdleStateHandler.this.hasOutputChanged(ctx, first)) {
/*     */             return;
/*     */           }
/*     */           
/* 556 */           IdleStateEvent event = IdleStateHandler.this.newIdleStateEvent(IdleState.WRITER_IDLE, first);
/* 557 */           IdleStateHandler.this.channelIdle(ctx, event);
/* 558 */         } catch (Throwable t) {
/* 559 */           ctx.fireExceptionCaught(t);
/*     */         } 
/*     */       } else {
/*     */         
/* 563 */         IdleStateHandler.this.writerIdleTimeout = IdleStateHandler.this.schedule(ctx, this, nextDelay, TimeUnit.NANOSECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class AllIdleTimeoutTask
/*     */     extends AbstractIdleTask {
/*     */     AllIdleTimeoutTask(ChannelHandlerContext ctx) {
/* 571 */       super(ctx);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void run(ChannelHandlerContext ctx) {
/* 577 */       long nextDelay = IdleStateHandler.this.allIdleTimeNanos;
/* 578 */       if (!IdleStateHandler.this.reading) {
/* 579 */         nextDelay -= IdleStateHandler.this.ticker.nanoTime() - Math.max(IdleStateHandler.this.lastReadTime, IdleStateHandler.this.lastWriteTime);
/*     */       }
/* 581 */       if (nextDelay <= 0L) {
/*     */ 
/*     */         
/* 584 */         IdleStateHandler.this.allIdleTimeout = IdleStateHandler.this.schedule(ctx, this, IdleStateHandler.this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */         
/* 586 */         boolean first = IdleStateHandler.this.firstAllIdleEvent;
/* 587 */         IdleStateHandler.this.firstAllIdleEvent = false;
/*     */         
/*     */         try {
/* 590 */           if (IdleStateHandler.this.hasOutputChanged(ctx, first)) {
/*     */             return;
/*     */           }
/*     */           
/* 594 */           IdleStateEvent event = IdleStateHandler.this.newIdleStateEvent(IdleState.ALL_IDLE, first);
/* 595 */           IdleStateHandler.this.channelIdle(ctx, event);
/* 596 */         } catch (Throwable t) {
/* 597 */           ctx.fireExceptionCaught(t);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 602 */         IdleStateHandler.this.allIdleTimeout = IdleStateHandler.this.schedule(ctx, this, nextDelay, TimeUnit.NANOSECONDS);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\timeout\IdleStateHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */