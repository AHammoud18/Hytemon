/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public abstract class AbstractTrafficShapingHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  54 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractTrafficShapingHandler.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long DEFAULT_CHECK_INTERVAL = 1000L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long DEFAULT_MAX_TIME = 15000L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final long DEFAULT_MAX_SIZE = 4194304L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final long MINIMAL_WAIT = 10L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TrafficCounter trafficCounter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long writeLimit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile long readLimit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   protected volatile long maxTime = 15000L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   protected volatile long checkInterval = 1000L;
/*     */ 
/*     */   
/* 103 */   static final AttributeKey<Boolean> READ_SUSPENDED = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".READ_SUSPENDED");
/* 104 */   static final AttributeKey<Runnable> REOPEN_TASK = AttributeKey.valueOf(AbstractTrafficShapingHandler.class
/* 105 */       .getName() + ".REOPEN_TASK");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   volatile long maxWriteDelay = 4000L;
/*     */ 
/*     */ 
/*     */   
/* 114 */   volatile long maxWriteSize = 4194304L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int userDefinedWritabilityIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int CHANNEL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int GLOBAL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int GLOBALCHANNEL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTrafficCounter(TrafficCounter newTrafficCounter) {
/* 142 */     this.trafficCounter = newTrafficCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int userDefinedWritabilityIndex() {
/* 153 */     return 1;
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
/*     */   protected AbstractTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval, long maxTime) {
/* 169 */     this.maxTime = ObjectUtil.checkPositive(maxTime, "maxTime");
/*     */     
/* 171 */     this.userDefinedWritabilityIndex = userDefinedWritabilityIndex();
/* 172 */     this.writeLimit = writeLimit;
/* 173 */     this.readLimit = readLimit;
/* 174 */     this.checkInterval = checkInterval;
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
/*     */   protected AbstractTrafficShapingHandler(long writeLimit, long readLimit, long checkInterval) {
/* 188 */     this(writeLimit, readLimit, checkInterval, 15000L);
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
/*     */   protected AbstractTrafficShapingHandler(long writeLimit, long readLimit) {
/* 201 */     this(writeLimit, readLimit, 1000L, 15000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractTrafficShapingHandler() {
/* 209 */     this(0L, 0L, 1000L, 15000L);
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
/*     */   protected AbstractTrafficShapingHandler(long checkInterval) {
/* 221 */     this(0L, 0L, checkInterval, 15000L);
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
/*     */   public void configure(long newWriteLimit, long newReadLimit, long newCheckInterval) {
/* 238 */     configure(newWriteLimit, newReadLimit);
/* 239 */     configure(newCheckInterval);
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
/*     */   public void configure(long newWriteLimit, long newReadLimit) {
/* 254 */     this.writeLimit = newWriteLimit;
/* 255 */     this.readLimit = newReadLimit;
/* 256 */     if (this.trafficCounter != null) {
/* 257 */       this.trafficCounter.resetAccounting(TrafficCounter.milliSecondFromNano());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(long newCheckInterval) {
/* 267 */     this.checkInterval = newCheckInterval;
/* 268 */     if (this.trafficCounter != null) {
/* 269 */       this.trafficCounter.configure(this.checkInterval);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWriteLimit() {
/* 277 */     return this.writeLimit;
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
/*     */   public void setWriteLimit(long writeLimit) {
/* 290 */     this.writeLimit = writeLimit;
/* 291 */     if (this.trafficCounter != null) {
/* 292 */       this.trafficCounter.resetAccounting(TrafficCounter.milliSecondFromNano());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getReadLimit() {
/* 300 */     return this.readLimit;
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
/*     */   public void setReadLimit(long readLimit) {
/* 313 */     this.readLimit = readLimit;
/* 314 */     if (this.trafficCounter != null) {
/* 315 */       this.trafficCounter.resetAccounting(TrafficCounter.milliSecondFromNano());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCheckInterval() {
/* 323 */     return this.checkInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCheckInterval(long checkInterval) {
/* 330 */     this.checkInterval = checkInterval;
/* 331 */     if (this.trafficCounter != null) {
/* 332 */       this.trafficCounter.configure(checkInterval);
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
/*     */   
/*     */   public void setMaxTimeWait(long maxTime) {
/* 348 */     this.maxTime = ObjectUtil.checkPositive(maxTime, "maxTime");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxTimeWait() {
/* 355 */     return this.maxTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxWriteDelay() {
/* 362 */     return this.maxWriteDelay;
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
/*     */   public void setMaxWriteDelay(long maxWriteDelay) {
/* 376 */     this.maxWriteDelay = ObjectUtil.checkPositive(maxWriteDelay, "maxWriteDelay");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxWriteSize() {
/* 383 */     return this.maxWriteSize;
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
/*     */   public void setMaxWriteSize(long maxWriteSize) {
/* 399 */     this.maxWriteSize = maxWriteSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doAccounting(TrafficCounter counter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ReopenReadTimerTask
/*     */     implements Runnable
/*     */   {
/*     */     final ChannelHandlerContext ctx;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ReopenReadTimerTask(ChannelHandlerContext ctx) {
/* 419 */       this.ctx = ctx;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 424 */       Channel channel = this.ctx.channel();
/* 425 */       ChannelConfig config = channel.config();
/* 426 */       if (!config.isAutoRead() && AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
/*     */ 
/*     */         
/* 429 */         if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 430 */           AbstractTrafficShapingHandler.logger.debug("Not unsuspend: " + config.isAutoRead() + ':' + 
/* 431 */               AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */         }
/* 433 */         channel.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(Boolean.valueOf(false));
/*     */       } else {
/*     */         
/* 436 */         if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 437 */           if (config.isAutoRead() && !AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
/* 438 */             if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 439 */               AbstractTrafficShapingHandler.logger.debug("Unsuspend: " + config.isAutoRead() + ':' + 
/* 440 */                   AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */             }
/*     */           }
/* 443 */           else if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 444 */             AbstractTrafficShapingHandler.logger.debug("Normal unsuspend: " + config.isAutoRead() + ':' + 
/* 445 */                 AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */           } 
/*     */         }
/*     */         
/* 449 */         channel.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(Boolean.valueOf(false));
/* 450 */         config.setAutoRead(true);
/* 451 */         channel.read();
/*     */       } 
/* 453 */       if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 454 */         AbstractTrafficShapingHandler.logger.debug("Unsuspend final status => " + config.isAutoRead() + ':' + 
/* 455 */             AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void releaseReadSuspended(ChannelHandlerContext ctx) {
/* 464 */     Channel channel = ctx.channel();
/* 465 */     channel.attr(READ_SUSPENDED).set(Boolean.valueOf(false));
/* 466 */     channel.config().setAutoRead(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 471 */     long size = calculateSize(msg);
/* 472 */     long now = TrafficCounter.milliSecondFromNano();
/* 473 */     if (size > 0L) {
/*     */       
/* 475 */       long wait = this.trafficCounter.readTimeToWait(size, this.readLimit, this.maxTime, now);
/* 476 */       wait = checkWaitReadTime(ctx, wait, now);
/* 477 */       if (wait >= 10L) {
/*     */ 
/*     */         
/* 480 */         Channel channel = ctx.channel();
/* 481 */         ChannelConfig config = channel.config();
/* 482 */         if (logger.isDebugEnabled()) {
/* 483 */           logger.debug("Read suspend: " + wait + ':' + config.isAutoRead() + ':' + 
/* 484 */               isHandlerActive(ctx));
/*     */         }
/* 486 */         if (config.isAutoRead() && isHandlerActive(ctx)) {
/* 487 */           config.setAutoRead(false);
/* 488 */           channel.attr(READ_SUSPENDED).set(Boolean.valueOf(true));
/*     */ 
/*     */           
/* 491 */           Attribute<Runnable> attr = channel.attr(REOPEN_TASK);
/* 492 */           Runnable reopenTask = (Runnable)attr.get();
/* 493 */           if (reopenTask == null) {
/* 494 */             reopenTask = new ReopenReadTimerTask(ctx);
/* 495 */             attr.set(reopenTask);
/*     */           } 
/* 497 */           ctx.executor().schedule(reopenTask, wait, TimeUnit.MILLISECONDS);
/* 498 */           if (logger.isDebugEnabled()) {
/* 499 */             logger.debug("Suspend final status => " + config.isAutoRead() + ':' + 
/* 500 */                 isHandlerActive(ctx) + " will reopened at: " + wait);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 505 */     informReadOperation(ctx, now);
/* 506 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 511 */     Channel channel = ctx.channel();
/* 512 */     if (channel.hasAttr(REOPEN_TASK))
/*     */     {
/* 514 */       channel.attr(REOPEN_TASK).set(null);
/*     */     }
/* 516 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long checkWaitReadTime(ChannelHandlerContext ctx, long wait, long now) {
/* 527 */     return wait;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void informReadOperation(ChannelHandlerContext ctx, long now) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isHandlerActive(ChannelHandlerContext ctx) {
/* 539 */     Boolean suspended = (Boolean)ctx.channel().attr(READ_SUSPENDED).get();
/* 540 */     return (suspended == null || Boolean.FALSE.equals(suspended));
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) {
/* 545 */     if (isHandlerActive(ctx))
/*     */     {
/* 547 */       ctx.read();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 554 */     long size = calculateSize(msg);
/* 555 */     long now = TrafficCounter.milliSecondFromNano();
/* 556 */     if (size > 0L) {
/*     */       
/* 558 */       long wait = this.trafficCounter.writeTimeToWait(size, this.writeLimit, this.maxTime, now);
/* 559 */       if (wait >= 10L) {
/* 560 */         if (logger.isDebugEnabled()) {
/* 561 */           logger.debug("Write suspend: " + wait + ':' + ctx.channel().config().isAutoRead() + ':' + 
/* 562 */               isHandlerActive(ctx));
/*     */         }
/* 564 */         submitWrite(ctx, msg, size, wait, now, promise);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 569 */     submitWrite(ctx, msg, size, 0L, now, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void submitWrite(ChannelHandlerContext ctx, Object msg, long delay, ChannelPromise promise) {
/* 575 */     submitWrite(ctx, msg, calculateSize(msg), delay, 
/* 576 */         TrafficCounter.milliSecondFromNano(), promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 584 */     setUserDefinedWritability(ctx, true);
/* 585 */     super.channelRegistered(ctx);
/*     */   }
/*     */   
/*     */   void setUserDefinedWritability(ChannelHandlerContext ctx, boolean writable) {
/* 589 */     ChannelOutboundBuffer cob = ctx.channel().unsafe().outboundBuffer();
/* 590 */     if (cob != null) {
/* 591 */       cob.setUserDefinedWritability(this.userDefinedWritabilityIndex, writable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void checkWriteSuspend(ChannelHandlerContext ctx, long delay, long queueSize) {
/* 602 */     if (queueSize > this.maxWriteSize || delay > this.maxWriteDelay) {
/* 603 */       setUserDefinedWritability(ctx, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void releaseWriteSuspended(ChannelHandlerContext ctx) {
/* 610 */     setUserDefinedWritability(ctx, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrafficCounter trafficCounter() {
/* 618 */     return this.trafficCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 629 */     StringBuilder builder = (new StringBuilder(290)).append("TrafficShaping with Write Limit: ").append(this.writeLimit).append(" Read Limit: ").append(this.readLimit).append(" CheckInterval: ").append(this.checkInterval).append(" maxDelay: ").append(this.maxWriteDelay).append(" maxSize: ").append(this.maxWriteSize).append(" and Counter: ");
/* 630 */     if (this.trafficCounter != null) {
/* 631 */       builder.append(this.trafficCounter);
/*     */     } else {
/* 633 */       builder.append("none");
/*     */     } 
/* 635 */     return builder.toString();
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
/*     */   protected long calculateSize(Object msg) {
/* 647 */     if (msg instanceof ByteBuf) {
/* 648 */       return ((ByteBuf)msg).readableBytes();
/*     */     }
/* 650 */     if (msg instanceof ByteBufHolder) {
/* 651 */       return ((ByteBufHolder)msg).content().readableBytes();
/*     */     }
/* 653 */     if (msg instanceof FileRegion) {
/* 654 */       return ((FileRegion)msg).count();
/*     */     }
/* 656 */     return -1L;
/*     */   }
/*     */   
/*     */   abstract void submitWrite(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, long paramLong1, long paramLong2, long paramLong3, ChannelPromise paramChannelPromise);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\traffic\AbstractTrafficShapingHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */