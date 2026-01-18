/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpdySessionHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  37 */   private static final SpdyProtocolException PROTOCOL_EXCEPTION = SpdyProtocolException.newStatic(null, SpdySessionHandler.class, "handleOutboundMessage(...)");
/*     */   
/*  39 */   private static final SpdyProtocolException STREAM_CLOSED = SpdyProtocolException.newStatic("Stream closed", SpdySessionHandler.class, "removeStream(...)");
/*     */   
/*     */   private static final int DEFAULT_WINDOW_SIZE = 65536;
/*  42 */   private int initialSendWindowSize = 65536;
/*  43 */   private int initialReceiveWindowSize = 65536;
/*  44 */   private volatile int initialSessionReceiveWindowSize = 65536;
/*     */   
/*  46 */   private final SpdySession spdySession = new SpdySession(this.initialSendWindowSize, this.initialReceiveWindowSize);
/*     */   
/*     */   private int lastGoodStreamId;
/*     */   private static final int DEFAULT_MAX_CONCURRENT_STREAMS = 2147483647;
/*  50 */   private int remoteConcurrentStreams = Integer.MAX_VALUE;
/*  51 */   private int localConcurrentStreams = Integer.MAX_VALUE;
/*     */   
/*  53 */   private final AtomicInteger pings = new AtomicInteger();
/*     */ 
/*     */   
/*     */   private boolean sentGoAwayFrame;
/*     */ 
/*     */   
/*     */   private boolean receivedGoAwayFrame;
/*     */ 
/*     */   
/*     */   private ChannelFutureListener closeSessionFutureListener;
/*     */ 
/*     */   
/*     */   private final boolean server;
/*     */ 
/*     */   
/*     */   private final int minorVersion;
/*     */ 
/*     */ 
/*     */   
/*     */   public SpdySessionHandler(SpdyVersion version, boolean server) {
/*  73 */     this.minorVersion = ((SpdyVersion)ObjectUtil.checkNotNull(version, "version")).minorVersion();
/*  74 */     this.server = server;
/*     */   }
/*     */   
/*     */   public void setSessionReceiveWindowSize(int sessionReceiveWindowSize) {
/*  78 */     ObjectUtil.checkPositiveOrZero(sessionReceiveWindowSize, "sessionReceiveWindowSize");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.initialSessionReceiveWindowSize = sessionReceiveWindowSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  90 */     if (msg instanceof SpdyDataFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 115 */       int streamId = spdyDataFrame.streamId();
/*     */       
/* 117 */       int deltaWindowSize = -1 * spdyDataFrame.content().readableBytes();
/*     */       
/* 119 */       int newSessionWindowSize = this.spdySession.updateReceiveWindowSize(0, deltaWindowSize);
/*     */ 
/*     */       
/* 122 */       if (newSessionWindowSize < 0) {
/* 123 */         issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 128 */       if (newSessionWindowSize <= this.initialSessionReceiveWindowSize / 2) {
/* 129 */         int sessionDeltaWindowSize = this.initialSessionReceiveWindowSize - newSessionWindowSize;
/* 130 */         this.spdySession.updateReceiveWindowSize(0, sessionDeltaWindowSize);
/* 131 */         SpdyWindowUpdateFrame spdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(0, sessionDeltaWindowSize);
/*     */         
/* 133 */         ctx.writeAndFlush(spdyWindowUpdateFrame);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 138 */       if (!this.spdySession.isActiveStream(streamId)) {
/* 139 */         spdyDataFrame.release();
/* 140 */         if (streamId <= this.lastGoodStreamId) {
/* 141 */           issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/* 142 */         } else if (!this.sentGoAwayFrame) {
/* 143 */           issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 150 */       if (this.spdySession.isRemoteSideClosed(streamId)) {
/* 151 */         spdyDataFrame.release();
/* 152 */         issueStreamError(ctx, streamId, SpdyStreamStatus.STREAM_ALREADY_CLOSED);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 157 */       if (!isRemoteInitiatedId(streamId) && !this.spdySession.hasReceivedReply(streamId)) {
/* 158 */         spdyDataFrame.release();
/* 159 */         issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       int newWindowSize = this.spdySession.updateReceiveWindowSize(streamId, deltaWindowSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       if (newWindowSize < this.spdySession.getReceiveWindowSizeLowerBound(streamId)) {
/* 178 */         spdyDataFrame.release();
/* 179 */         issueStreamError(ctx, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 185 */       if (newWindowSize < 0) {
/* 186 */         while (spdyDataFrame.content().readableBytes() > this.initialReceiveWindowSize) {
/*     */           
/* 188 */           SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readRetainedSlice(this.initialReceiveWindowSize));
/* 189 */           ctx.writeAndFlush(partialDataFrame);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 194 */       if (newWindowSize <= this.initialReceiveWindowSize / 2 && !spdyDataFrame.isLast()) {
/* 195 */         int streamDeltaWindowSize = this.initialReceiveWindowSize - newWindowSize;
/* 196 */         this.spdySession.updateReceiveWindowSize(streamId, streamDeltaWindowSize);
/* 197 */         SpdyWindowUpdateFrame spdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(streamId, streamDeltaWindowSize);
/*     */         
/* 199 */         ctx.writeAndFlush(spdyWindowUpdateFrame);
/*     */       } 
/*     */ 
/*     */       
/* 203 */       if (spdyDataFrame.isLast()) {
/* 204 */         halfCloseStream(streamId, true, ctx.newSucceededFuture());
/*     */       }
/*     */     }
/* 207 */     else if (msg instanceof SpdySynStreamFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 223 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 224 */       int streamId = spdySynStreamFrame.streamId();
/*     */ 
/*     */       
/* 227 */       if (spdySynStreamFrame.isInvalid() || 
/* 228 */         !isRemoteInitiatedId(streamId) || this.spdySession
/* 229 */         .isActiveStream(streamId)) {
/* 230 */         issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 235 */       if (streamId <= this.lastGoodStreamId) {
/* 236 */         issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 241 */       byte priority = spdySynStreamFrame.priority();
/* 242 */       boolean remoteSideClosed = spdySynStreamFrame.isLast();
/* 243 */       boolean localSideClosed = spdySynStreamFrame.isUnidirectional();
/* 244 */       if (!acceptStream(streamId, priority, remoteSideClosed, localSideClosed)) {
/* 245 */         issueStreamError(ctx, streamId, SpdyStreamStatus.REFUSED_STREAM);
/*     */         
/*     */         return;
/*     */       } 
/* 249 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 259 */       int streamId = spdySynReplyFrame.streamId();
/*     */ 
/*     */       
/* 262 */       if (spdySynReplyFrame.isInvalid() || 
/* 263 */         isRemoteInitiatedId(streamId) || this.spdySession
/* 264 */         .isRemoteSideClosed(streamId)) {
/* 265 */         issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 270 */       if (this.spdySession.hasReceivedReply(streamId)) {
/* 271 */         issueStreamError(ctx, streamId, SpdyStreamStatus.STREAM_IN_USE);
/*     */         
/*     */         return;
/*     */       } 
/* 275 */       this.spdySession.receivedReply(streamId);
/*     */ 
/*     */       
/* 278 */       if (spdySynReplyFrame.isLast()) {
/* 279 */         halfCloseStream(streamId, true, ctx.newSucceededFuture());
/*     */       }
/*     */     }
/* 282 */     else if (msg instanceof SpdyRstStreamFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 293 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 294 */       removeStream(spdyRstStreamFrame.streamId(), ctx.newSucceededFuture());
/*     */     }
/* 296 */     else if (msg instanceof SpdySettingsFrame) {
/*     */       
/* 298 */       SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
/*     */       
/* 300 */       int settingsMinorVersion = spdySettingsFrame.getValue(0);
/* 301 */       if (settingsMinorVersion >= 0 && settingsMinorVersion != this.minorVersion) {
/*     */         
/* 303 */         issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 308 */       int newConcurrentStreams = spdySettingsFrame.getValue(4);
/* 309 */       if (newConcurrentStreams >= 0) {
/* 310 */         this.remoteConcurrentStreams = newConcurrentStreams;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 316 */       if (spdySettingsFrame.isPersisted(7)) {
/* 317 */         spdySettingsFrame.removeValue(7);
/*     */       }
/* 319 */       spdySettingsFrame.setPersistValue(7, false);
/*     */ 
/*     */       
/* 322 */       int newInitialWindowSize = spdySettingsFrame.getValue(7);
/* 323 */       if (newInitialWindowSize >= 0) {
/* 324 */         updateInitialSendWindowSize(newInitialWindowSize);
/*     */       }
/*     */     }
/* 327 */     else if (msg instanceof SpdyPingFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 338 */       SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
/*     */       
/* 340 */       if (isRemoteInitiatedId(spdyPingFrame.id())) {
/* 341 */         ctx.writeAndFlush(spdyPingFrame);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 346 */       if (this.pings.get() == 0) {
/*     */         return;
/*     */       }
/* 349 */       this.pings.getAndDecrement();
/*     */     }
/* 351 */     else if (msg instanceof SpdyGoAwayFrame) {
/*     */       
/* 353 */       this.receivedGoAwayFrame = true;
/*     */     }
/* 355 */     else if (msg instanceof SpdyHeadersFrame) {
/*     */       
/* 357 */       SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 358 */       int streamId = spdyHeadersFrame.streamId();
/*     */ 
/*     */       
/* 361 */       if (spdyHeadersFrame.isInvalid()) {
/* 362 */         issueStreamError(ctx, streamId, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/*     */         return;
/*     */       } 
/* 366 */       if (this.spdySession.isRemoteSideClosed(streamId)) {
/* 367 */         issueStreamError(ctx, streamId, SpdyStreamStatus.INVALID_STREAM);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 372 */       if (spdyHeadersFrame.isLast()) {
/* 373 */         halfCloseStream(streamId, true, ctx.newSucceededFuture());
/*     */       }
/*     */     }
/* 376 */     else if (msg instanceof SpdyWindowUpdateFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 388 */       SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)msg;
/* 389 */       int streamId = spdyWindowUpdateFrame.streamId();
/* 390 */       int deltaWindowSize = spdyWindowUpdateFrame.deltaWindowSize();
/*     */ 
/*     */       
/* 393 */       if (streamId != 0 && this.spdySession.isLocalSideClosed(streamId)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 398 */       if (this.spdySession.getSendWindowSize(streamId) > Integer.MAX_VALUE - deltaWindowSize) {
/* 399 */         if (streamId == 0) {
/* 400 */           issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */         } else {
/* 402 */           issueStreamError(ctx, streamId, SpdyStreamStatus.FLOW_CONTROL_ERROR);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 407 */       updateSendWindowSize(ctx, streamId, deltaWindowSize);
/*     */     } 
/*     */     
/* 410 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 415 */     for (Integer streamId : this.spdySession.activeStreams().keySet()) {
/* 416 */       removeStream(streamId.intValue(), ctx.newSucceededFuture());
/*     */     }
/* 418 */     ctx.fireChannelInactive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 423 */     if (cause instanceof SpdyProtocolException) {
/* 424 */       issueSessionError(ctx, SpdySessionStatus.PROTOCOL_ERROR);
/*     */     }
/*     */     
/* 427 */     ctx.fireExceptionCaught(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 432 */     sendGoAwayFrame(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 437 */     if (msg instanceof SpdyDataFrame || msg instanceof SpdySynStreamFrame || msg instanceof SpdySynReplyFrame || msg instanceof SpdyRstStreamFrame || msg instanceof SpdySettingsFrame || msg instanceof SpdyPingFrame || msg instanceof SpdyGoAwayFrame || msg instanceof SpdyHeadersFrame || msg instanceof SpdyWindowUpdateFrame) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 447 */       handleOutboundMessage(ctx, msg, promise);
/*     */     } else {
/* 449 */       ctx.write(msg, promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleOutboundMessage(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 454 */     if (msg instanceof SpdyDataFrame) {
/*     */       
/* 456 */       SpdyDataFrame spdyDataFrame = (SpdyDataFrame)msg;
/* 457 */       int streamId = spdyDataFrame.streamId();
/*     */ 
/*     */       
/* 460 */       if (this.spdySession.isLocalSideClosed(streamId)) {
/* 461 */         spdyDataFrame.release();
/* 462 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 479 */       int dataLength = spdyDataFrame.content().readableBytes();
/* 480 */       int sendWindowSize = this.spdySession.getSendWindowSize(streamId);
/* 481 */       int sessionSendWindowSize = this.spdySession.getSendWindowSize(0);
/* 482 */       sendWindowSize = Math.min(sendWindowSize, sessionSendWindowSize);
/*     */       
/* 484 */       if (sendWindowSize <= 0) {
/*     */         
/* 486 */         this.spdySession.putPendingWrite(streamId, new SpdySession.PendingWrite(spdyDataFrame, promise)); return;
/*     */       } 
/* 488 */       if (sendWindowSize < dataLength) {
/*     */         
/* 490 */         this.spdySession.updateSendWindowSize(streamId, -1 * sendWindowSize);
/* 491 */         this.spdySession.updateSendWindowSize(0, -1 * sendWindowSize);
/*     */ 
/*     */ 
/*     */         
/* 495 */         SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(streamId, spdyDataFrame.content().readRetainedSlice(sendWindowSize));
/*     */ 
/*     */         
/* 498 */         this.spdySession.putPendingWrite(streamId, new SpdySession.PendingWrite(spdyDataFrame, promise));
/*     */ 
/*     */ 
/*     */         
/* 502 */         final ChannelHandlerContext context = ctx;
/* 503 */         ctx.write(partialDataFrame).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 506 */                 if (!future.isSuccess()) {
/* 507 */                   SpdySessionHandler.this.issueSessionError(context, SpdySessionStatus.INTERNAL_ERROR);
/*     */                 }
/*     */               }
/*     */             });
/*     */         
/*     */         return;
/*     */       } 
/* 514 */       this.spdySession.updateSendWindowSize(streamId, -1 * dataLength);
/* 515 */       this.spdySession.updateSendWindowSize(0, -1 * dataLength);
/*     */ 
/*     */ 
/*     */       
/* 519 */       final ChannelHandlerContext context = ctx;
/* 520 */       promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) throws Exception {
/* 523 */               if (!future.isSuccess()) {
/* 524 */                 SpdySessionHandler.this.issueSessionError(context, SpdySessionStatus.INTERNAL_ERROR);
/*     */               }
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */       
/* 531 */       if (spdyDataFrame.isLast()) {
/* 532 */         halfCloseStream(streamId, false, (ChannelFuture)promise);
/*     */       }
/*     */     }
/* 535 */     else if (msg instanceof SpdySynStreamFrame) {
/*     */       
/* 537 */       SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)msg;
/* 538 */       int streamId = spdySynStreamFrame.streamId();
/*     */       
/* 540 */       if (isRemoteInitiatedId(streamId)) {
/* 541 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/* 545 */       byte priority = spdySynStreamFrame.priority();
/* 546 */       boolean remoteSideClosed = spdySynStreamFrame.isUnidirectional();
/* 547 */       boolean localSideClosed = spdySynStreamFrame.isLast();
/* 548 */       if (!acceptStream(streamId, priority, remoteSideClosed, localSideClosed)) {
/* 549 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/* 553 */     } else if (msg instanceof SpdySynReplyFrame) {
/*     */       
/* 555 */       SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)msg;
/* 556 */       int streamId = spdySynReplyFrame.streamId();
/*     */ 
/*     */       
/* 559 */       if (!isRemoteInitiatedId(streamId) || this.spdySession.isLocalSideClosed(streamId)) {
/* 560 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 565 */       if (spdySynReplyFrame.isLast()) {
/* 566 */         halfCloseStream(streamId, false, (ChannelFuture)promise);
/*     */       }
/*     */     }
/* 569 */     else if (msg instanceof SpdyRstStreamFrame) {
/*     */       
/* 571 */       SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)msg;
/* 572 */       removeStream(spdyRstStreamFrame.streamId(), (ChannelFuture)promise);
/*     */     }
/* 574 */     else if (msg instanceof SpdySettingsFrame) {
/*     */       
/* 576 */       SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)msg;
/*     */       
/* 578 */       int settingsMinorVersion = spdySettingsFrame.getValue(0);
/* 579 */       if (settingsMinorVersion >= 0 && settingsMinorVersion != this.minorVersion) {
/*     */         
/* 581 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 586 */       int newConcurrentStreams = spdySettingsFrame.getValue(4);
/* 587 */       if (newConcurrentStreams >= 0) {
/* 588 */         this.localConcurrentStreams = newConcurrentStreams;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 594 */       if (spdySettingsFrame.isPersisted(7)) {
/* 595 */         spdySettingsFrame.removeValue(7);
/*     */       }
/* 597 */       spdySettingsFrame.setPersistValue(7, false);
/*     */ 
/*     */       
/* 600 */       int newInitialWindowSize = spdySettingsFrame.getValue(7);
/* 601 */       if (newInitialWindowSize >= 0) {
/* 602 */         updateInitialReceiveWindowSize(newInitialWindowSize);
/*     */       }
/*     */     }
/* 605 */     else if (msg instanceof SpdyPingFrame) {
/*     */       
/* 607 */       SpdyPingFrame spdyPingFrame = (SpdyPingFrame)msg;
/* 608 */       if (isRemoteInitiatedId(spdyPingFrame.id())) {
/* 609 */         ctx.fireExceptionCaught(new IllegalArgumentException("invalid PING ID: " + spdyPingFrame
/* 610 */               .id()));
/*     */         return;
/*     */       } 
/* 613 */       this.pings.getAndIncrement();
/*     */     } else {
/* 615 */       if (msg instanceof SpdyGoAwayFrame) {
/*     */ 
/*     */ 
/*     */         
/* 619 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         return;
/*     */       } 
/* 622 */       if (msg instanceof SpdyHeadersFrame) {
/*     */         
/* 624 */         SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)msg;
/* 625 */         int streamId = spdyHeadersFrame.streamId();
/*     */ 
/*     */         
/* 628 */         if (this.spdySession.isLocalSideClosed(streamId)) {
/* 629 */           promise.setFailure(PROTOCOL_EXCEPTION);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 634 */         if (spdyHeadersFrame.isLast()) {
/* 635 */           halfCloseStream(streamId, false, (ChannelFuture)promise);
/*     */         }
/*     */       }
/* 638 */       else if (msg instanceof SpdyWindowUpdateFrame) {
/*     */ 
/*     */         
/* 641 */         promise.setFailure(PROTOCOL_EXCEPTION);
/*     */         return;
/*     */       } 
/*     */     } 
/* 645 */     ctx.write(msg, promise);
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
/*     */   private void issueSessionError(ChannelHandlerContext ctx, SpdySessionStatus status) {
/* 660 */     sendGoAwayFrame(ctx, status).addListener((GenericFutureListener)new ClosingChannelFutureListener(ctx, ctx.newPromise()));
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
/*     */   private void issueStreamError(ChannelHandlerContext ctx, int streamId, SpdyStreamStatus status) {
/* 675 */     boolean fireChannelRead = !this.spdySession.isRemoteSideClosed(streamId);
/* 676 */     ChannelPromise promise = ctx.newPromise();
/* 677 */     removeStream(streamId, (ChannelFuture)promise);
/*     */     
/* 679 */     SpdyRstStreamFrame spdyRstStreamFrame = new DefaultSpdyRstStreamFrame(streamId, status);
/* 680 */     ctx.writeAndFlush(spdyRstStreamFrame, promise);
/* 681 */     if (fireChannelRead) {
/* 682 */       ctx.fireChannelRead(spdyRstStreamFrame);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRemoteInitiatedId(int id) {
/* 691 */     boolean serverId = SpdyCodecUtil.isServerId(id);
/* 692 */     return ((this.server && !serverId) || (!this.server && serverId));
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateInitialSendWindowSize(int newInitialWindowSize) {
/* 697 */     int deltaWindowSize = newInitialWindowSize - this.initialSendWindowSize;
/* 698 */     this.initialSendWindowSize = newInitialWindowSize;
/* 699 */     this.spdySession.updateAllSendWindowSizes(deltaWindowSize);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateInitialReceiveWindowSize(int newInitialWindowSize) {
/* 704 */     int deltaWindowSize = newInitialWindowSize - this.initialReceiveWindowSize;
/* 705 */     this.initialReceiveWindowSize = newInitialWindowSize;
/* 706 */     this.spdySession.updateAllReceiveWindowSizes(deltaWindowSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean acceptStream(int streamId, byte priority, boolean remoteSideClosed, boolean localSideClosed) {
/* 713 */     if (this.receivedGoAwayFrame || this.sentGoAwayFrame) {
/* 714 */       return false;
/*     */     }
/*     */     
/* 717 */     boolean remote = isRemoteInitiatedId(streamId);
/* 718 */     int maxConcurrentStreams = remote ? this.localConcurrentStreams : this.remoteConcurrentStreams;
/* 719 */     if (this.spdySession.numActiveStreams(remote) >= maxConcurrentStreams) {
/* 720 */       return false;
/*     */     }
/* 722 */     this.spdySession.acceptStream(streamId, priority, remoteSideClosed, localSideClosed, this.initialSendWindowSize, this.initialReceiveWindowSize, remote);
/*     */ 
/*     */     
/* 725 */     if (remote) {
/* 726 */       this.lastGoodStreamId = streamId;
/*     */     }
/* 728 */     return true;
/*     */   }
/*     */   
/*     */   private void halfCloseStream(int streamId, boolean remote, ChannelFuture future) {
/* 732 */     if (remote) {
/* 733 */       this.spdySession.closeRemoteSide(streamId, isRemoteInitiatedId(streamId));
/*     */     } else {
/* 735 */       this.spdySession.closeLocalSide(streamId, isRemoteInitiatedId(streamId));
/*     */     } 
/* 737 */     if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
/* 738 */       future.addListener((GenericFutureListener)this.closeSessionFutureListener);
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeStream(int streamId, ChannelFuture future) {
/* 743 */     this.spdySession.removeStream(streamId, STREAM_CLOSED, isRemoteInitiatedId(streamId));
/*     */     
/* 745 */     if (this.closeSessionFutureListener != null && this.spdySession.noActiveStreams()) {
/* 746 */       future.addListener((GenericFutureListener)this.closeSessionFutureListener);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateSendWindowSize(final ChannelHandlerContext ctx, int streamId, int deltaWindowSize) {
/* 751 */     this.spdySession.updateSendWindowSize(streamId, deltaWindowSize);
/*     */ 
/*     */     
/*     */     while (true) {
/* 755 */       SpdySession.PendingWrite pendingWrite = this.spdySession.getPendingWrite(streamId);
/* 756 */       if (pendingWrite == null) {
/*     */         return;
/*     */       }
/*     */       
/* 760 */       SpdyDataFrame spdyDataFrame = pendingWrite.spdyDataFrame;
/* 761 */       int dataFrameSize = spdyDataFrame.content().readableBytes();
/* 762 */       int writeStreamId = spdyDataFrame.streamId();
/* 763 */       int sendWindowSize = this.spdySession.getSendWindowSize(writeStreamId);
/* 764 */       int sessionSendWindowSize = this.spdySession.getSendWindowSize(0);
/* 765 */       sendWindowSize = Math.min(sendWindowSize, sessionSendWindowSize);
/*     */       
/* 767 */       if (sendWindowSize <= 0)
/*     */         return; 
/* 769 */       if (sendWindowSize < dataFrameSize) {
/*     */         
/* 771 */         this.spdySession.updateSendWindowSize(writeStreamId, -1 * sendWindowSize);
/* 772 */         this.spdySession.updateSendWindowSize(0, -1 * sendWindowSize);
/*     */ 
/*     */ 
/*     */         
/* 776 */         SpdyDataFrame partialDataFrame = new DefaultSpdyDataFrame(writeStreamId, spdyDataFrame.content().readRetainedSlice(sendWindowSize));
/*     */ 
/*     */ 
/*     */         
/* 780 */         ctx.writeAndFlush(partialDataFrame).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/* 783 */                 if (!future.isSuccess()) {
/* 784 */                   SpdySessionHandler.this.issueSessionError(ctx, SpdySessionStatus.INTERNAL_ERROR);
/*     */                 }
/*     */               }
/*     */             });
/*     */         continue;
/*     */       } 
/* 790 */       this.spdySession.removePendingWrite(writeStreamId);
/* 791 */       this.spdySession.updateSendWindowSize(writeStreamId, -1 * dataFrameSize);
/* 792 */       this.spdySession.updateSendWindowSize(0, -1 * dataFrameSize);
/*     */ 
/*     */       
/* 795 */       if (spdyDataFrame.isLast()) {
/* 796 */         halfCloseStream(writeStreamId, false, (ChannelFuture)pendingWrite.promise);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 801 */       ctx.writeAndFlush(spdyDataFrame, pendingWrite.promise).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) throws Exception {
/* 804 */               if (!future.isSuccess()) {
/* 805 */                 SpdySessionHandler.this.issueSessionError(ctx, SpdySessionStatus.INTERNAL_ERROR);
/*     */               }
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendGoAwayFrame(ChannelHandlerContext ctx, ChannelPromise future) {
/* 815 */     if (!ctx.channel().isActive()) {
/* 816 */       ctx.close(future);
/*     */       
/*     */       return;
/*     */     } 
/* 820 */     ChannelFuture f = sendGoAwayFrame(ctx, SpdySessionStatus.OK);
/* 821 */     if (this.spdySession.noActiveStreams()) {
/* 822 */       f.addListener((GenericFutureListener)new ClosingChannelFutureListener(ctx, future));
/*     */     } else {
/* 824 */       this.closeSessionFutureListener = new ClosingChannelFutureListener(ctx, future);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelFuture sendGoAwayFrame(ChannelHandlerContext ctx, SpdySessionStatus status) {
/* 831 */     if (!this.sentGoAwayFrame) {
/* 832 */       this.sentGoAwayFrame = true;
/* 833 */       SpdyGoAwayFrame spdyGoAwayFrame = new DefaultSpdyGoAwayFrame(this.lastGoodStreamId, status);
/* 834 */       return ctx.writeAndFlush(spdyGoAwayFrame);
/*     */     } 
/* 836 */     return ctx.newSucceededFuture();
/*     */   }
/*     */   
/*     */   private static final class ClosingChannelFutureListener
/*     */     implements ChannelFutureListener {
/*     */     private final ChannelHandlerContext ctx;
/*     */     private final ChannelPromise promise;
/*     */     
/*     */     ClosingChannelFutureListener(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 845 */       this.ctx = ctx;
/* 846 */       this.promise = promise;
/*     */     }
/*     */ 
/*     */     
/*     */     public void operationComplete(ChannelFuture sentGoAwayFuture) throws Exception {
/* 851 */       this.ctx.close(this.promise);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdySessionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */