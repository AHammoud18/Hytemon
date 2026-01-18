/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.ServerChannel;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import io.netty.channel.nio.AbstractNioByteChannel;
/*     */ import io.netty.channel.nio.AbstractNioChannel;
/*     */ import io.netty.channel.socket.DuplexChannel;
/*     */ import io.netty.channel.socket.DuplexChannelConfig;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NioDomainSocketChannel
/*     */   extends AbstractNioByteChannel
/*     */   implements DuplexChannel
/*     */ {
/*  65 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioDomainSocketChannel.class);
/*  66 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*     */ 
/*     */   
/*  69 */   private static final Method OPEN_SOCKET_CHANNEL_WITH_FAMILY = SelectorProviderUtil.findOpenMethod("openSocketChannel");
/*     */   
/*     */   private final ChannelConfig config;
/*     */   private volatile boolean isInputShutdown;
/*     */   private volatile boolean isOutputShutdown;
/*     */   
/*     */   static SocketChannel newChannel(SelectorProvider provider) {
/*  76 */     if (PlatformDependent.javaVersion() < 16) {
/*  77 */       throw new UnsupportedOperationException("Only supported on java 16+");
/*     */     }
/*     */     try {
/*  80 */       SocketChannel channel = SelectorProviderUtil.<SocketChannel>newDomainSocketChannel(OPEN_SOCKET_CHANNEL_WITH_FAMILY, provider);
/*     */       
/*  82 */       if (channel == null) {
/*  83 */         throw new ChannelException("Failed to open a socket.");
/*     */       }
/*  85 */       return channel;
/*  86 */     } catch (IOException e) {
/*  87 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDomainSocketChannel() {
/*  95 */     this(DEFAULT_SELECTOR_PROVIDER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDomainSocketChannel(SelectorProvider provider) {
/* 102 */     this(newChannel(provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDomainSocketChannel(SocketChannel socket) {
/* 109 */     this((Channel)null, socket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDomainSocketChannel(Channel parent, SocketChannel socket) {
/* 119 */     super(parent, socket);
/* 120 */     if (PlatformDependent.javaVersion() < 16) {
/* 121 */       throw new UnsupportedOperationException("Only supported on java 16+");
/*     */     }
/* 123 */     this.config = (ChannelConfig)new NioDomainSocketChannelConfig(this, socket);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerChannel parent() {
/* 128 */     return (ServerChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig config() {
/* 133 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketChannel javaChannel() {
/* 138 */     return (SocketChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 143 */     SocketChannel ch = javaChannel();
/* 144 */     return (ch.isOpen() && ch.isConnected());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 149 */     return (this.isOutputShutdown || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInputShutdown() {
/* 154 */     return (this.isInputShutdown || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 159 */     return ((isInputShutdown() && isOutputShutdown()) || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doShutdownOutput() throws Exception {
/* 164 */     javaChannel().shutdownOutput();
/* 165 */     this.isOutputShutdown = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput() {
/* 170 */     return shutdownOutput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
/* 175 */     EventLoop loop = eventLoop();
/* 176 */     if (loop.inEventLoop()) {
/* 177 */       ((AbstractChannel.AbstractUnsafe)unsafe()).shutdownOutput(promise);
/*     */     } else {
/* 179 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 182 */               ((AbstractChannel.AbstractUnsafe)NioDomainSocketChannel.this.unsafe()).shutdownOutput(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 186 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownInput() {
/* 191 */     return shutdownInput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isInputShutdown0() {
/* 196 */     return isInputShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownInput(final ChannelPromise promise) {
/* 201 */     EventLoop loop = eventLoop();
/* 202 */     if (loop.inEventLoop()) {
/* 203 */       shutdownInput0(promise);
/*     */     } else {
/* 205 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 208 */               NioDomainSocketChannel.this.shutdownInput0(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 212 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdown() {
/* 217 */     return shutdown(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdown(final ChannelPromise promise) {
/* 222 */     ChannelFuture shutdownOutputFuture = shutdownOutput();
/* 223 */     if (shutdownOutputFuture.isDone()) {
/* 224 */       shutdownOutputDone(shutdownOutputFuture, promise);
/*     */     } else {
/* 226 */       shutdownOutputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture shutdownOutputFuture) throws Exception {
/* 229 */               NioDomainSocketChannel.this.shutdownOutputDone(shutdownOutputFuture, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 233 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void shutdownOutputDone(final ChannelFuture shutdownOutputFuture, final ChannelPromise promise) {
/* 237 */     ChannelFuture shutdownInputFuture = shutdownInput();
/* 238 */     if (shutdownInputFuture.isDone()) {
/* 239 */       shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*     */     } else {
/* 241 */       shutdownInputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture shutdownInputFuture) throws Exception {
/* 244 */               NioDomainSocketChannel.shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void shutdownDone(ChannelFuture shutdownOutputFuture, ChannelFuture shutdownInputFuture, ChannelPromise promise) {
/* 253 */     Throwable shutdownOutputCause = shutdownOutputFuture.cause();
/* 254 */     Throwable shutdownInputCause = shutdownInputFuture.cause();
/* 255 */     if (shutdownOutputCause != null) {
/* 256 */       if (shutdownInputCause != null) {
/* 257 */         logger.debug("Exception suppressed because a previous exception occurred.", shutdownInputCause);
/*     */       }
/*     */       
/* 260 */       promise.setFailure(shutdownOutputCause);
/* 261 */     } else if (shutdownInputCause != null) {
/* 262 */       promise.setFailure(shutdownInputCause);
/*     */     } else {
/* 264 */       promise.setSuccess();
/*     */     } 
/*     */   }
/*     */   private void shutdownInput0(ChannelPromise promise) {
/*     */     try {
/* 269 */       shutdownInput0();
/* 270 */       promise.setSuccess();
/* 271 */     } catch (Throwable t) {
/* 272 */       promise.setFailure(t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void shutdownInput0() throws Exception {
/* 277 */     javaChannel().shutdownInput();
/* 278 */     this.isInputShutdown = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*     */     try {
/* 284 */       return javaChannel().getLocalAddress();
/* 285 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 288 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/*     */     try {
/* 294 */       return javaChannel().getRemoteAddress();
/* 295 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 298 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 303 */     SocketUtils.bind(javaChannel(), localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 308 */     if (localAddress != null) {
/* 309 */       doBind(localAddress);
/*     */     }
/*     */     
/* 312 */     boolean success = false;
/*     */     try {
/* 314 */       boolean connected = SocketUtils.connect(javaChannel(), remoteAddress);
/* 315 */       if (!connected) {
/* 316 */         selectionKey().interestOps(8);
/*     */       }
/* 318 */       success = true;
/* 319 */       return connected;
/*     */     } finally {
/* 321 */       if (!success) {
/* 322 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 329 */     if (!javaChannel().finishConnect()) {
/* 330 */       throw new UnsupportedOperationException("finishConnect is not supported for " + getClass().getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 336 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/*     */     try {
/* 342 */       super.doClose();
/*     */     } finally {
/* 344 */       javaChannel().close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadBytes(ByteBuf byteBuf) throws Exception {
/* 350 */     RecvByteBufAllocator.Handle allocHandle = unsafe().recvBufAllocHandle();
/* 351 */     allocHandle.attemptedBytesRead(byteBuf.writableBytes());
/* 352 */     return byteBuf.writeBytes(javaChannel(), allocHandle.attemptedBytesRead());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doWriteBytes(ByteBuf buf) throws Exception {
/* 357 */     int expectedWrittenBytes = buf.readableBytes();
/* 358 */     return buf.readBytes(javaChannel(), expectedWrittenBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long doWriteFileRegion(FileRegion region) throws Exception {
/* 363 */     long position = region.transferred();
/* 364 */     return region.transferTo(javaChannel(), position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void adjustMaxBytesPerGatheringWrite(int attempted, int written, int oldMaxBytesPerGatheringWrite) {
/* 371 */     if (attempted == written) {
/* 372 */       if (attempted << 1 > oldMaxBytesPerGatheringWrite) {
/* 373 */         ((NioDomainSocketChannelConfig)this.config).setMaxBytesPerGatheringWrite(attempted << 1);
/*     */       }
/* 375 */     } else if (attempted > 4096 && written < attempted >>> 1) {
/* 376 */       ((NioDomainSocketChannelConfig)this.config).setMaxBytesPerGatheringWrite(attempted >>> 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 382 */     SocketChannel ch = javaChannel();
/* 383 */     int writeSpinCount = config().getWriteSpinCount(); do {
/*     */       ByteBuffer buffer; long attemptedBytes; int i, j; long localWrittenBytes;
/* 385 */       if (in.isEmpty()) {
/*     */         
/* 387 */         clearOpWrite();
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 393 */       int maxBytesPerGatheringWrite = ((NioDomainSocketChannelConfig)this.config).getMaxBytesPerGatheringWrite();
/* 394 */       ByteBuffer[] nioBuffers = in.nioBuffers(1024, maxBytesPerGatheringWrite);
/* 395 */       int nioBufferCnt = in.nioBufferCount();
/*     */ 
/*     */ 
/*     */       
/* 399 */       switch (nioBufferCnt) {
/*     */         
/*     */         case 0:
/* 402 */           writeSpinCount -= doWrite0(in);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 408 */           buffer = nioBuffers[0];
/* 409 */           i = buffer.remaining();
/* 410 */           j = ch.write(buffer);
/* 411 */           if (j <= 0) {
/* 412 */             incompleteWrite(true);
/*     */             return;
/*     */           } 
/* 415 */           adjustMaxBytesPerGatheringWrite(i, j, maxBytesPerGatheringWrite);
/* 416 */           in.removeBytes(j);
/* 417 */           writeSpinCount--;
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 424 */           attemptedBytes = in.nioBufferSize();
/* 425 */           localWrittenBytes = ch.write(nioBuffers, 0, nioBufferCnt);
/* 426 */           if (localWrittenBytes <= 0L) {
/* 427 */             incompleteWrite(true);
/*     */             
/*     */             return;
/*     */           } 
/* 431 */           adjustMaxBytesPerGatheringWrite((int)attemptedBytes, (int)localWrittenBytes, maxBytesPerGatheringWrite);
/*     */           
/* 433 */           in.removeBytes(localWrittenBytes);
/* 434 */           writeSpinCount--;
/*     */           break;
/*     */       } 
/*     */     
/* 438 */     } while (writeSpinCount > 0);
/*     */     
/* 440 */     incompleteWrite((writeSpinCount < 0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
/* 445 */     return (AbstractNioChannel.AbstractNioUnsafe)new NioSocketChannelUnsafe();
/*     */   }
/*     */   private final class NioSocketChannelUnsafe extends AbstractNioByteChannel.NioByteUnsafe { private NioSocketChannelUnsafe() {
/* 448 */       super(NioDomainSocketChannel.this);
/*     */     } }
/*     */ 
/*     */   
/*     */   private final class NioDomainSocketChannelConfig
/*     */     extends DefaultChannelConfig implements DuplexChannelConfig {
/*     */     private volatile boolean allowHalfClosure;
/* 455 */     private volatile int maxBytesPerGatheringWrite = Integer.MAX_VALUE; private final SocketChannel javaChannel;
/*     */     
/*     */     private NioDomainSocketChannelConfig(NioDomainSocketChannel channel, SocketChannel javaChannel) {
/* 458 */       super((Channel)channel);
/* 459 */       this.javaChannel = javaChannel;
/* 460 */       calculateMaxBytesPerGatheringWrite();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isAllowHalfClosure() {
/* 465 */       return this.allowHalfClosure;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
/* 470 */       this.allowHalfClosure = allowHalfClosure;
/* 471 */       return this;
/*     */     }
/*     */     
/*     */     public Map<ChannelOption<?>, Object> getOptions() {
/* 475 */       List<ChannelOption<?>> options = new ArrayList<>();
/* 476 */       options.add(ChannelOption.SO_RCVBUF);
/* 477 */       options.add(ChannelOption.SO_SNDBUF);
/* 478 */       for (ChannelOption<?> opt : NioChannelOption.getOptions(jdkChannel())) {
/* 479 */         options.add(opt);
/*     */       }
/* 481 */       return getOptions(super.getOptions(), options.<ChannelOption>toArray(new ChannelOption[0]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T getOption(ChannelOption<T> option) {
/* 487 */       if (option == ChannelOption.SO_RCVBUF) {
/* 488 */         return (T)Integer.valueOf(getReceiveBufferSize());
/*     */       }
/* 490 */       if (option == ChannelOption.SO_SNDBUF) {
/* 491 */         return (T)Integer.valueOf(getSendBufferSize());
/*     */       }
/* 493 */       if (option instanceof NioChannelOption) {
/* 494 */         return NioChannelOption.getOption(jdkChannel(), (NioChannelOption<T>)option);
/*     */       }
/*     */       
/* 497 */       return (T)super.getOption(option);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 502 */       if (option == ChannelOption.SO_RCVBUF)
/* 503 */       { validate(option, value);
/* 504 */         setReceiveBufferSize(((Integer)value).intValue()); }
/* 505 */       else if (option == ChannelOption.SO_SNDBUF)
/* 506 */       { validate(option, value);
/* 507 */         setSendBufferSize(((Integer)value).intValue()); }
/* 508 */       else { if (option instanceof NioChannelOption) {
/* 509 */           return NioChannelOption.setOption(jdkChannel(), (NioChannelOption<T>)option, value);
/*     */         }
/* 511 */         return super.setOption(option, value); }
/*     */ 
/*     */       
/* 514 */       return true;
/*     */     }
/*     */     
/*     */     private int getReceiveBufferSize() {
/*     */       try {
/* 519 */         return ((Integer)this.javaChannel.<Integer>getOption(StandardSocketOptions.SO_RCVBUF)).intValue();
/* 520 */       } catch (IOException e) {
/* 521 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     private NioDomainSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */       try {
/* 527 */         this.javaChannel.setOption(StandardSocketOptions.SO_RCVBUF, Integer.valueOf(receiveBufferSize));
/* 528 */       } catch (IOException e) {
/* 529 */         throw new ChannelException(e);
/*     */       } 
/* 531 */       return this;
/*     */     }
/*     */     
/*     */     private int getSendBufferSize() {
/*     */       try {
/* 536 */         return ((Integer)this.javaChannel.<Integer>getOption(StandardSocketOptions.SO_SNDBUF)).intValue();
/* 537 */       } catch (IOException e) {
/* 538 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/*     */     private NioDomainSocketChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */       try {
/* 543 */         this.javaChannel.setOption(StandardSocketOptions.SO_SNDBUF, Integer.valueOf(sendBufferSize));
/* 544 */       } catch (IOException e) {
/* 545 */         throw new ChannelException(e);
/*     */       } 
/* 547 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 552 */       super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 553 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public NioDomainSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 559 */       super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 560 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 565 */       super.setWriteSpinCount(writeSpinCount);
/* 566 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 571 */       super.setAllocator(allocator);
/* 572 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 577 */       super.setRecvByteBufAllocator(allocator);
/* 578 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setAutoRead(boolean autoRead) {
/* 583 */       super.setAutoRead(autoRead);
/* 584 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setAutoClose(boolean autoClose) {
/* 589 */       super.setAutoClose(autoClose);
/* 590 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 595 */       super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 596 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 601 */       super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 602 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 607 */       super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 608 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioDomainSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 613 */       super.setMessageSizeEstimator(estimator);
/* 614 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void autoReadCleared() {
/* 619 */       NioDomainSocketChannel.this.clearReadPending();
/*     */     }
/*     */     
/*     */     void setMaxBytesPerGatheringWrite(int maxBytesPerGatheringWrite) {
/* 623 */       this.maxBytesPerGatheringWrite = maxBytesPerGatheringWrite;
/*     */     }
/*     */     
/*     */     int getMaxBytesPerGatheringWrite() {
/* 627 */       return this.maxBytesPerGatheringWrite;
/*     */     }
/*     */ 
/*     */     
/*     */     private void calculateMaxBytesPerGatheringWrite() {
/* 632 */       int newSendBufferSize = getSendBufferSize() << 1;
/* 633 */       if (newSendBufferSize > 0) {
/* 634 */         setMaxBytesPerGatheringWrite(newSendBufferSize);
/*     */       }
/*     */     }
/*     */     
/*     */     private SocketChannel jdkChannel() {
/* 639 */       return this.javaChannel;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\nio\NioDomainSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */