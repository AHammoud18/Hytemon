/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Sharable
/*     */ public class GlobalChannelTrafficShapingHandler
/*     */   extends AbstractTrafficShapingHandler
/*     */ {
/*  93 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalChannelTrafficShapingHandler.class);
/*     */ 
/*     */ 
/*     */   
/*  97 */   final ConcurrentMap<Integer, PerChannel> channelQueues = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   private final AtomicLong queuesSize = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private final AtomicLong cumulativeReadBytes = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   volatile long maxGlobalWriteSize = 419430400L;
/*     */   
/*     */   private volatile long writeChannelLimit;
/*     */   
/*     */   private volatile long readChannelLimit;
/*     */   
/*     */   private static final float DEFAULT_DEVIATION = 0.1F;
/*     */   
/*     */   private static final float MAX_DEVIATION = 0.4F;
/*     */   
/*     */   private static final float DEFAULT_SLOWDOWN = 0.4F;
/*     */   
/*     */   private static final float DEFAULT_ACCELERATION = -0.1F;
/*     */   
/*     */   private volatile float maxDeviation;
/*     */   
/*     */   private volatile float accelerationFactor;
/*     */   
/*     */   private volatile float slowDownFactor;
/*     */   
/*     */   private volatile boolean readDeviationActive;
/*     */   
/*     */   private volatile boolean writeDeviationActive;
/*     */ 
/*     */   
/*     */   static final class PerChannel
/*     */   {
/*     */     ArrayDeque<GlobalChannelTrafficShapingHandler.ToSend> messagesQueue;
/*     */     TrafficCounter channelTrafficCounter;
/*     */     long queueSize;
/*     */     long lastWriteTimestamp;
/*     */     long lastReadTimestamp;
/*     */   }
/*     */   
/*     */   void createGlobalTrafficCounter(ScheduledExecutorService executor) {
/* 153 */     setMaxDeviation(0.1F, 0.4F, -0.1F);
/* 154 */     ObjectUtil.checkNotNullWithIAE(executor, "executor");
/* 155 */     TrafficCounter tc = new GlobalChannelTrafficCounter(this, executor, "GlobalChannelTC", this.checkInterval);
/* 156 */     setTrafficCounter(tc);
/* 157 */     tc.start();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int userDefinedWritabilityIndex() {
/* 162 */     return 3;
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
/*     */   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit, long checkInterval, long maxTime) {
/* 188 */     super(writeGlobalLimit, readGlobalLimit, checkInterval, maxTime);
/* 189 */     createGlobalTrafficCounter(executor);
/* 190 */     this.writeChannelLimit = writeChannelLimit;
/* 191 */     this.readChannelLimit = readChannelLimit;
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
/*     */   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit, long checkInterval) {
/* 215 */     super(writeGlobalLimit, readGlobalLimit, checkInterval);
/* 216 */     this.writeChannelLimit = writeChannelLimit;
/* 217 */     this.readChannelLimit = readChannelLimit;
/* 218 */     createGlobalTrafficCounter(executor);
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
/*     */   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long writeGlobalLimit, long readGlobalLimit, long writeChannelLimit, long readChannelLimit) {
/* 238 */     super(writeGlobalLimit, readGlobalLimit);
/* 239 */     this.writeChannelLimit = writeChannelLimit;
/* 240 */     this.readChannelLimit = readChannelLimit;
/* 241 */     createGlobalTrafficCounter(executor);
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
/*     */   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor, long checkInterval) {
/* 254 */     super(checkInterval);
/* 255 */     createGlobalTrafficCounter(executor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GlobalChannelTrafficShapingHandler(ScheduledExecutorService executor) {
/* 265 */     createGlobalTrafficCounter(executor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float maxDeviation() {
/* 272 */     return this.maxDeviation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float accelerationFactor() {
/* 279 */     return this.accelerationFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float slowDownFactor() {
/* 286 */     return this.slowDownFactor;
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
/*     */   public void setMaxDeviation(float maxDeviation, float slowDownFactor, float accelerationFactor) {
/* 301 */     if (maxDeviation > 0.4F) {
/* 302 */       throw new IllegalArgumentException("maxDeviation must be <= 0.4");
/*     */     }
/* 304 */     ObjectUtil.checkPositiveOrZero(slowDownFactor, "slowDownFactor");
/* 305 */     if (accelerationFactor > 0.0F) {
/* 306 */       throw new IllegalArgumentException("accelerationFactor must be <= 0");
/*     */     }
/* 308 */     this.maxDeviation = maxDeviation;
/* 309 */     this.accelerationFactor = 1.0F + accelerationFactor;
/* 310 */     this.slowDownFactor = 1.0F + slowDownFactor;
/*     */   }
/*     */ 
/*     */   
/*     */   private void computeDeviationCumulativeBytes() {
/* 315 */     long maxWrittenBytes = 0L;
/* 316 */     long maxReadBytes = 0L;
/* 317 */     long minWrittenBytes = Long.MAX_VALUE;
/* 318 */     long minReadBytes = Long.MAX_VALUE;
/* 319 */     for (PerChannel perChannel : this.channelQueues.values()) {
/* 320 */       long value = perChannel.channelTrafficCounter.cumulativeWrittenBytes();
/* 321 */       if (maxWrittenBytes < value) {
/* 322 */         maxWrittenBytes = value;
/*     */       }
/* 324 */       if (minWrittenBytes > value) {
/* 325 */         minWrittenBytes = value;
/*     */       }
/* 327 */       value = perChannel.channelTrafficCounter.cumulativeReadBytes();
/* 328 */       if (maxReadBytes < value) {
/* 329 */         maxReadBytes = value;
/*     */       }
/* 331 */       if (minReadBytes > value) {
/* 332 */         minReadBytes = value;
/*     */       }
/*     */     } 
/* 335 */     boolean multiple = (this.channelQueues.size() > 1);
/* 336 */     this.readDeviationActive = (multiple && minReadBytes < maxReadBytes / 2L);
/* 337 */     this.writeDeviationActive = (multiple && minWrittenBytes < maxWrittenBytes / 2L);
/* 338 */     this.cumulativeWrittenBytes.set(maxWrittenBytes);
/* 339 */     this.cumulativeReadBytes.set(maxReadBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doAccounting(TrafficCounter counter) {
/* 344 */     computeDeviationCumulativeBytes();
/* 345 */     super.doAccounting(counter);
/*     */   }
/*     */   
/*     */   private long computeBalancedWait(float maxLocal, float maxGlobal, long wait) {
/* 349 */     if (maxGlobal == 0.0F)
/*     */     {
/* 351 */       return wait;
/*     */     }
/* 353 */     float ratio = maxLocal / maxGlobal;
/*     */     
/* 355 */     if (ratio > this.maxDeviation) {
/* 356 */       if (ratio < 1.0F - this.maxDeviation) {
/* 357 */         return wait;
/*     */       }
/* 359 */       ratio = this.slowDownFactor;
/* 360 */       if (wait < 10L) {
/* 361 */         wait = 10L;
/*     */       }
/*     */     } else {
/*     */       
/* 365 */       ratio = this.accelerationFactor;
/*     */     } 
/* 367 */     return (long)((float)wait * ratio);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMaxGlobalWriteSize() {
/* 374 */     return this.maxGlobalWriteSize;
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
/*     */   public void setMaxGlobalWriteSize(long maxGlobalWriteSize) {
/* 388 */     this.maxGlobalWriteSize = ObjectUtil.checkPositive(maxGlobalWriteSize, "maxGlobalWriteSize");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long queuesSize() {
/* 395 */     return this.queuesSize.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureChannel(long newWriteLimit, long newReadLimit) {
/* 403 */     this.writeChannelLimit = newWriteLimit;
/* 404 */     this.readChannelLimit = newReadLimit;
/* 405 */     long now = TrafficCounter.milliSecondFromNano();
/* 406 */     for (PerChannel perChannel : this.channelQueues.values()) {
/* 407 */       perChannel.channelTrafficCounter.resetAccounting(now);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWriteChannelLimit() {
/* 415 */     return this.writeChannelLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWriteChannelLimit(long writeLimit) {
/* 422 */     this.writeChannelLimit = writeLimit;
/* 423 */     long now = TrafficCounter.milliSecondFromNano();
/* 424 */     for (PerChannel perChannel : this.channelQueues.values()) {
/* 425 */       perChannel.channelTrafficCounter.resetAccounting(now);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getReadChannelLimit() {
/* 433 */     return this.readChannelLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadChannelLimit(long readLimit) {
/* 440 */     this.readChannelLimit = readLimit;
/* 441 */     long now = TrafficCounter.milliSecondFromNano();
/* 442 */     for (PerChannel perChannel : this.channelQueues.values()) {
/* 443 */       perChannel.channelTrafficCounter.resetAccounting(now);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void release() {
/* 451 */     this.trafficCounter.stop();
/*     */   }
/*     */ 
/*     */   
/*     */   private PerChannel getOrSetPerChannel(ChannelHandlerContext ctx) {
/* 456 */     Channel channel = ctx.channel();
/* 457 */     Integer key = Integer.valueOf(channel.hashCode());
/* 458 */     PerChannel perChannel = this.channelQueues.get(key);
/* 459 */     if (perChannel == null) {
/* 460 */       perChannel = new PerChannel();
/* 461 */       perChannel.messagesQueue = new ArrayDeque<>();
/*     */       
/* 463 */       perChannel
/* 464 */         .channelTrafficCounter = new TrafficCounter(this, null, "ChannelTC" + ctx.channel().hashCode(), this.checkInterval);
/* 465 */       perChannel.queueSize = 0L;
/* 466 */       perChannel.lastReadTimestamp = TrafficCounter.milliSecondFromNano();
/* 467 */       perChannel.lastWriteTimestamp = perChannel.lastReadTimestamp;
/* 468 */       this.channelQueues.put(key, perChannel);
/*     */     } 
/* 470 */     return perChannel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 475 */     getOrSetPerChannel(ctx);
/* 476 */     this.trafficCounter.resetCumulativeTime();
/* 477 */     super.handlerAdded(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 482 */     this.trafficCounter.resetCumulativeTime();
/* 483 */     Channel channel = ctx.channel();
/* 484 */     Integer key = Integer.valueOf(channel.hashCode());
/* 485 */     PerChannel perChannel = this.channelQueues.remove(key);
/* 486 */     if (perChannel != null)
/*     */     {
/* 488 */       synchronized (perChannel) {
/* 489 */         if (channel.isActive()) {
/* 490 */           for (ToSend toSend : perChannel.messagesQueue) {
/* 491 */             long size = calculateSize(toSend.toSend);
/* 492 */             this.trafficCounter.bytesRealWriteFlowControl(size);
/* 493 */             perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
/* 494 */             perChannel.queueSize -= size;
/* 495 */             this.queuesSize.addAndGet(-size);
/* 496 */             ctx.write(toSend.toSend, toSend.promise);
/*     */           } 
/*     */         } else {
/* 499 */           this.queuesSize.addAndGet(-perChannel.queueSize);
/* 500 */           for (ToSend toSend : perChannel.messagesQueue) {
/* 501 */             if (toSend.toSend instanceof ByteBuf) {
/* 502 */               ((ByteBuf)toSend.toSend).release();
/*     */             }
/*     */           } 
/*     */         } 
/* 506 */         perChannel.messagesQueue.clear();
/*     */       } 
/*     */     }
/* 509 */     releaseWriteSuspended(ctx);
/* 510 */     releaseReadSuspended(ctx);
/* 511 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 516 */     long size = calculateSize(msg);
/* 517 */     long now = TrafficCounter.milliSecondFromNano();
/* 518 */     if (size > 0L) {
/*     */       
/* 520 */       long waitGlobal = this.trafficCounter.readTimeToWait(size, getReadLimit(), this.maxTime, now);
/* 521 */       Integer key = Integer.valueOf(ctx.channel().hashCode());
/* 522 */       PerChannel perChannel = this.channelQueues.get(key);
/* 523 */       long wait = 0L;
/* 524 */       if (perChannel != null) {
/* 525 */         wait = perChannel.channelTrafficCounter.readTimeToWait(size, this.readChannelLimit, this.maxTime, now);
/* 526 */         if (this.readDeviationActive) {
/*     */ 
/*     */           
/* 529 */           long maxLocalRead = perChannel.channelTrafficCounter.cumulativeReadBytes();
/* 530 */           long maxGlobalRead = this.cumulativeReadBytes.get();
/* 531 */           if (maxLocalRead <= 0L) {
/* 532 */             maxLocalRead = 0L;
/*     */           }
/* 534 */           if (maxGlobalRead < maxLocalRead) {
/* 535 */             maxGlobalRead = maxLocalRead;
/*     */           }
/* 537 */           wait = computeBalancedWait((float)maxLocalRead, (float)maxGlobalRead, wait);
/*     */         } 
/*     */       } 
/* 540 */       if (wait < waitGlobal) {
/* 541 */         wait = waitGlobal;
/*     */       }
/* 543 */       wait = checkWaitReadTime(ctx, wait, now);
/* 544 */       if (wait >= 10L) {
/*     */ 
/*     */         
/* 547 */         Channel channel = ctx.channel();
/* 548 */         ChannelConfig config = channel.config();
/* 549 */         if (logger.isDebugEnabled()) {
/* 550 */           logger.debug("Read Suspend: " + wait + ':' + config.isAutoRead() + ':' + 
/* 551 */               isHandlerActive(ctx));
/*     */         }
/* 553 */         if (config.isAutoRead() && isHandlerActive(ctx)) {
/* 554 */           config.setAutoRead(false);
/* 555 */           channel.attr(READ_SUSPENDED).set(Boolean.valueOf(true));
/*     */ 
/*     */           
/* 558 */           Attribute<Runnable> attr = channel.attr(REOPEN_TASK);
/* 559 */           Runnable reopenTask = (Runnable)attr.get();
/* 560 */           if (reopenTask == null) {
/* 561 */             reopenTask = new AbstractTrafficShapingHandler.ReopenReadTimerTask(ctx);
/* 562 */             attr.set(reopenTask);
/*     */           } 
/* 564 */           ctx.executor().schedule(reopenTask, wait, TimeUnit.MILLISECONDS);
/* 565 */           if (logger.isDebugEnabled()) {
/* 566 */             logger.debug("Suspend final status => " + config.isAutoRead() + ':' + 
/* 567 */                 isHandlerActive(ctx) + " will reopened at: " + wait);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 572 */     informReadOperation(ctx, now);
/* 573 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long checkWaitReadTime(ChannelHandlerContext ctx, long wait, long now) {
/* 578 */     Integer key = Integer.valueOf(ctx.channel().hashCode());
/* 579 */     PerChannel perChannel = this.channelQueues.get(key);
/* 580 */     if (perChannel != null && 
/* 581 */       wait > this.maxTime && now + wait - perChannel.lastReadTimestamp > this.maxTime) {
/* 582 */       wait = this.maxTime;
/*     */     }
/*     */     
/* 585 */     return wait;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void informReadOperation(ChannelHandlerContext ctx, long now) {
/* 590 */     Integer key = Integer.valueOf(ctx.channel().hashCode());
/* 591 */     PerChannel perChannel = this.channelQueues.get(key);
/* 592 */     if (perChannel != null)
/* 593 */       perChannel.lastReadTimestamp = now; 
/*     */   }
/*     */   
/*     */   private static final class ToSend
/*     */   {
/*     */     final long relativeTimeAction;
/*     */     final Object toSend;
/*     */     final ChannelPromise promise;
/*     */     final long size;
/*     */     
/*     */     private ToSend(long delay, Object toSend, long size, ChannelPromise promise) {
/* 604 */       this.relativeTimeAction = delay;
/* 605 */       this.toSend = toSend;
/* 606 */       this.size = size;
/* 607 */       this.promise = promise;
/*     */     }
/*     */   }
/*     */   
/*     */   protected long maximumCumulativeWrittenBytes() {
/* 612 */     return this.cumulativeWrittenBytes.get();
/*     */   }
/*     */   
/*     */   protected long maximumCumulativeReadBytes() {
/* 616 */     return this.cumulativeReadBytes.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<TrafficCounter> channelTrafficCounters() {
/* 624 */     return new AbstractCollection<TrafficCounter>()
/*     */       {
/*     */         public Iterator<TrafficCounter> iterator() {
/* 627 */           return new Iterator<TrafficCounter>() {
/* 628 */               final Iterator<GlobalChannelTrafficShapingHandler.PerChannel> iter = GlobalChannelTrafficShapingHandler.this.channelQueues.values().iterator();
/*     */               
/*     */               public boolean hasNext() {
/* 631 */                 return this.iter.hasNext();
/*     */               }
/*     */               
/*     */               public TrafficCounter next() {
/* 635 */                 return ((GlobalChannelTrafficShapingHandler.PerChannel)this.iter.next()).channelTrafficCounter;
/*     */               }
/*     */               
/*     */               public void remove() {
/* 639 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */         }
/*     */         
/*     */         public int size() {
/* 645 */           return GlobalChannelTrafficShapingHandler.this.channelQueues.size();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 653 */     long size = calculateSize(msg);
/* 654 */     long now = TrafficCounter.milliSecondFromNano();
/* 655 */     if (size > 0L) {
/*     */       
/* 657 */       long waitGlobal = this.trafficCounter.writeTimeToWait(size, getWriteLimit(), this.maxTime, now);
/* 658 */       Integer key = Integer.valueOf(ctx.channel().hashCode());
/* 659 */       PerChannel perChannel = this.channelQueues.get(key);
/* 660 */       long wait = 0L;
/* 661 */       if (perChannel != null) {
/* 662 */         wait = perChannel.channelTrafficCounter.writeTimeToWait(size, this.writeChannelLimit, this.maxTime, now);
/* 663 */         if (this.writeDeviationActive) {
/*     */ 
/*     */           
/* 666 */           long maxLocalWrite = perChannel.channelTrafficCounter.cumulativeWrittenBytes();
/* 667 */           long maxGlobalWrite = this.cumulativeWrittenBytes.get();
/* 668 */           if (maxLocalWrite <= 0L) {
/* 669 */             maxLocalWrite = 0L;
/*     */           }
/* 671 */           if (maxGlobalWrite < maxLocalWrite) {
/* 672 */             maxGlobalWrite = maxLocalWrite;
/*     */           }
/* 674 */           wait = computeBalancedWait((float)maxLocalWrite, (float)maxGlobalWrite, wait);
/*     */         } 
/*     */       } 
/* 677 */       if (wait < waitGlobal) {
/* 678 */         wait = waitGlobal;
/*     */       }
/* 680 */       if (wait >= 10L) {
/* 681 */         if (logger.isDebugEnabled()) {
/* 682 */           logger.debug("Write suspend: " + wait + ':' + ctx.channel().config().isAutoRead() + ':' + 
/* 683 */               isHandlerActive(ctx));
/*     */         }
/* 685 */         submitWrite(ctx, msg, size, wait, now, promise);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 690 */     submitWrite(ctx, msg, size, 0L, now, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void submitWrite(final ChannelHandlerContext ctx, Object msg, long size, long writedelay, long now, ChannelPromise promise) {
/*     */     ToSend newToSend;
/* 697 */     Channel channel = ctx.channel();
/* 698 */     Integer key = Integer.valueOf(channel.hashCode());
/* 699 */     PerChannel perChannel = this.channelQueues.get(key);
/* 700 */     if (perChannel == null)
/*     */     {
/*     */       
/* 703 */       perChannel = getOrSetPerChannel(ctx);
/*     */     }
/*     */     
/* 706 */     long delay = writedelay;
/* 707 */     boolean globalSizeExceeded = false;
/*     */     
/* 709 */     synchronized (perChannel) {
/* 710 */       if (writedelay == 0L && perChannel.messagesQueue.isEmpty()) {
/* 711 */         this.trafficCounter.bytesRealWriteFlowControl(size);
/* 712 */         perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
/* 713 */         ctx.write(msg, promise);
/* 714 */         perChannel.lastWriteTimestamp = now;
/*     */         return;
/*     */       } 
/* 717 */       if (delay > this.maxTime && now + delay - perChannel.lastWriteTimestamp > this.maxTime) {
/* 718 */         delay = this.maxTime;
/*     */       }
/* 720 */       newToSend = new ToSend(delay + now, msg, size, promise);
/* 721 */       perChannel.messagesQueue.addLast(newToSend);
/* 722 */       perChannel.queueSize += size;
/* 723 */       this.queuesSize.addAndGet(size);
/* 724 */       checkWriteSuspend(ctx, delay, perChannel.queueSize);
/* 725 */       if (this.queuesSize.get() > this.maxGlobalWriteSize) {
/* 726 */         globalSizeExceeded = true;
/*     */       }
/*     */     } 
/* 729 */     if (globalSizeExceeded) {
/* 730 */       setUserDefinedWritability(ctx, false);
/*     */     }
/* 732 */     final long futureNow = newToSend.relativeTimeAction;
/* 733 */     final PerChannel forSchedule = perChannel;
/* 734 */     ctx.executor().schedule(new Runnable()
/*     */         {
/*     */           public void run() {
/* 737 */             GlobalChannelTrafficShapingHandler.this.sendAllValid(ctx, forSchedule, futureNow);
/*     */           }
/*     */         }delay, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendAllValid(ChannelHandlerContext ctx, PerChannel perChannel, long now) {
/* 744 */     synchronized (perChannel) {
/* 745 */       ToSend newToSend = perChannel.messagesQueue.pollFirst();
/* 746 */       for (; newToSend != null; newToSend = perChannel.messagesQueue.pollFirst()) {
/* 747 */         if (newToSend.relativeTimeAction <= now) {
/* 748 */           long size = newToSend.size;
/* 749 */           this.trafficCounter.bytesRealWriteFlowControl(size);
/* 750 */           perChannel.channelTrafficCounter.bytesRealWriteFlowControl(size);
/* 751 */           perChannel.queueSize -= size;
/* 752 */           this.queuesSize.addAndGet(-size);
/* 753 */           ctx.write(newToSend.toSend, newToSend.promise);
/* 754 */           perChannel.lastWriteTimestamp = now;
/*     */         } else {
/* 756 */           perChannel.messagesQueue.addFirst(newToSend);
/*     */           break;
/*     */         } 
/*     */       } 
/* 760 */       if (perChannel.messagesQueue.isEmpty()) {
/* 761 */         releaseWriteSuspended(ctx);
/*     */       }
/*     */     } 
/* 764 */     ctx.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 769 */     return (new StringBuilder(340)).append(super.toString())
/* 770 */       .append(" Write Channel Limit: ").append(this.writeChannelLimit)
/* 771 */       .append(" Read Channel Limit: ").append(this.readChannelLimit).toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\traffic\GlobalChannelTrafficShapingHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */