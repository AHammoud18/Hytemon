/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.nio.AbstractNioByteChannel;
/*     */ import io.netty.channel.nio.AbstractNioChannel;
/*     */ import io.netty.channel.nio.NioIoOps;
/*     */ import io.netty.channel.socket.DefaultSocketChannelConfig;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.SocketChannel;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NioSocketChannel
/*     */   extends AbstractNioByteChannel
/*     */   implements SocketChannel
/*     */ {
/*  58 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSocketChannel.class);
/*  59 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*     */ 
/*     */   
/*  62 */   private static final Method OPEN_SOCKET_CHANNEL_WITH_FAMILY = SelectorProviderUtil.findOpenMethod("openSocketChannel");
/*     */   
/*     */   private final SocketChannelConfig config;
/*     */   
/*     */   private static SocketChannel newChannel(SelectorProvider provider, SocketProtocolFamily family) {
/*     */     try {
/*  68 */       SocketChannel channel = SelectorProviderUtil.<SocketChannel>newChannel(OPEN_SOCKET_CHANNEL_WITH_FAMILY, provider, family);
/*  69 */       return (channel == null) ? provider.openSocketChannel() : channel;
/*  70 */     } catch (IOException e) {
/*  71 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel() {
/*  79 */     this(DEFAULT_SELECTOR_PROVIDER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel(SelectorProvider provider) {
/*  86 */     this(provider, (SocketProtocolFamily)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public NioSocketChannel(SelectorProvider provider, InternetProtocolFamily family) {
/*  96 */     this(provider, (family == null) ? null : family.toSocketProtocolFamily());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel(SelectorProvider provider, SocketProtocolFamily family) {
/* 103 */     this(newChannel(provider, family));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel(SocketChannel socket) {
/* 110 */     this((Channel)null, socket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel(Channel parent, SocketChannel socket) {
/* 120 */     super(parent, socket);
/* 121 */     this.config = (SocketChannelConfig)new NioSocketChannelConfig(this, socket.socket());
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannel parent() {
/* 126 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketChannelConfig config() {
/* 131 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketChannel javaChannel() {
/* 136 */     return (SocketChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 141 */     SocketChannel ch = javaChannel();
/* 142 */     return (ch.isOpen() && ch.isConnected());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 147 */     return (javaChannel().socket().isOutputShutdown() || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInputShutdown() {
/* 152 */     return (javaChannel().socket().isInputShutdown() || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 157 */     Socket socket = javaChannel().socket();
/* 158 */     return ((socket.isInputShutdown() && socket.isOutputShutdown()) || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 163 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 168 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void doShutdownOutput() throws Exception {
/* 173 */     javaChannel().shutdownOutput();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput() {
/* 178 */     return shutdownOutput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
/* 183 */     EventLoop loop = eventLoop();
/* 184 */     if (loop.inEventLoop()) {
/* 185 */       ((AbstractChannel.AbstractUnsafe)unsafe()).shutdownOutput(promise);
/*     */     } else {
/* 187 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 190 */               ((AbstractChannel.AbstractUnsafe)NioSocketChannel.this.unsafe()).shutdownOutput(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 194 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownInput() {
/* 199 */     return shutdownInput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isInputShutdown0() {
/* 204 */     return isInputShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownInput(final ChannelPromise promise) {
/* 209 */     EventLoop loop = eventLoop();
/* 210 */     if (loop.inEventLoop()) {
/* 211 */       shutdownInput0(promise);
/*     */     } else {
/* 213 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 216 */               NioSocketChannel.this.shutdownInput0(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 220 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdown() {
/* 225 */     return shutdown(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdown(final ChannelPromise promise) {
/* 230 */     ChannelFuture shutdownOutputFuture = shutdownOutput();
/* 231 */     if (shutdownOutputFuture.isDone()) {
/* 232 */       shutdownOutputDone(shutdownOutputFuture, promise);
/*     */     } else {
/* 234 */       shutdownOutputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture shutdownOutputFuture) throws Exception {
/* 237 */               NioSocketChannel.this.shutdownOutputDone(shutdownOutputFuture, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 241 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void shutdownOutputDone(final ChannelFuture shutdownOutputFuture, final ChannelPromise promise) {
/* 245 */     ChannelFuture shutdownInputFuture = shutdownInput();
/* 246 */     if (shutdownInputFuture.isDone()) {
/* 247 */       shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*     */     } else {
/* 249 */       shutdownInputFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture shutdownInputFuture) throws Exception {
/* 252 */               NioSocketChannel.shutdownDone(shutdownOutputFuture, shutdownInputFuture, promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void shutdownDone(ChannelFuture shutdownOutputFuture, ChannelFuture shutdownInputFuture, ChannelPromise promise) {
/* 261 */     Throwable shutdownOutputCause = shutdownOutputFuture.cause();
/* 262 */     Throwable shutdownInputCause = shutdownInputFuture.cause();
/* 263 */     if (shutdownOutputCause != null) {
/* 264 */       if (shutdownInputCause != null) {
/* 265 */         logger.debug("Exception suppressed because a previous exception occurred.", shutdownInputCause);
/*     */       }
/*     */       
/* 268 */       promise.setFailure(shutdownOutputCause);
/* 269 */     } else if (shutdownInputCause != null) {
/* 270 */       promise.setFailure(shutdownInputCause);
/*     */     } else {
/* 272 */       promise.setSuccess();
/*     */     } 
/*     */   }
/*     */   private void shutdownInput0(ChannelPromise promise) {
/*     */     try {
/* 277 */       shutdownInput0();
/* 278 */       promise.setSuccess();
/* 279 */     } catch (Throwable t) {
/* 280 */       promise.setFailure(t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void shutdownInput0() throws Exception {
/* 285 */     javaChannel().shutdownInput();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 290 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 295 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 300 */     doBind0(localAddress);
/*     */   }
/*     */   
/*     */   private void doBind0(SocketAddress localAddress) throws Exception {
/* 304 */     SocketUtils.bind(javaChannel(), localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 309 */     if (localAddress != null) {
/* 310 */       doBind0(localAddress);
/*     */     }
/*     */     
/* 313 */     boolean success = false;
/*     */     try {
/* 315 */       boolean connected = SocketUtils.connect(javaChannel(), remoteAddress);
/* 316 */       if (!connected) {
/* 317 */         addAndSubmit(NioIoOps.CONNECT);
/*     */       }
/* 319 */       success = true;
/* 320 */       return connected;
/*     */     } finally {
/* 322 */       if (!success) {
/* 323 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 330 */     if (!javaChannel().finishConnect()) {
/* 331 */       throw new UnsupportedOperationException("finishConnect is not supported for " + getClass().getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 337 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 342 */     super.doClose();
/* 343 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadBytes(ByteBuf byteBuf) throws Exception {
/* 348 */     RecvByteBufAllocator.Handle allocHandle = unsafe().recvBufAllocHandle();
/* 349 */     allocHandle.attemptedBytesRead(byteBuf.writableBytes());
/* 350 */     return byteBuf.writeBytes(javaChannel(), allocHandle.attemptedBytesRead());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doWriteBytes(ByteBuf buf) throws Exception {
/* 355 */     int expectedWrittenBytes = buf.readableBytes();
/* 356 */     return buf.readBytes(javaChannel(), expectedWrittenBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long doWriteFileRegion(FileRegion region) throws Exception {
/* 361 */     long position = region.transferred();
/* 362 */     return region.transferTo(javaChannel(), position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void adjustMaxBytesPerGatheringWrite(int attempted, int written, int oldMaxBytesPerGatheringWrite) {
/* 369 */     if (attempted == written) {
/* 370 */       if (attempted << 1 > oldMaxBytesPerGatheringWrite) {
/* 371 */         ((NioSocketChannelConfig)this.config).setMaxBytesPerGatheringWrite(attempted << 1);
/*     */       }
/* 373 */     } else if (attempted > 4096 && written < attempted >>> 1) {
/* 374 */       ((NioSocketChannelConfig)this.config).setMaxBytesPerGatheringWrite(attempted >>> 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 380 */     SocketChannel ch = javaChannel();
/* 381 */     int writeSpinCount = config().getWriteSpinCount(); do {
/*     */       ByteBuffer buffer; long attemptedBytes; int i, j; long localWrittenBytes;
/* 383 */       if (in.isEmpty()) {
/*     */         
/* 385 */         clearOpWrite();
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 391 */       int maxBytesPerGatheringWrite = ((NioSocketChannelConfig)this.config).getMaxBytesPerGatheringWrite();
/* 392 */       ByteBuffer[] nioBuffers = in.nioBuffers(1024, maxBytesPerGatheringWrite);
/* 393 */       int nioBufferCnt = in.nioBufferCount();
/*     */ 
/*     */ 
/*     */       
/* 397 */       switch (nioBufferCnt) {
/*     */         
/*     */         case 0:
/* 400 */           writeSpinCount -= doWrite0(in);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 406 */           buffer = nioBuffers[0];
/* 407 */           i = buffer.remaining();
/* 408 */           j = ch.write(buffer);
/* 409 */           if (j <= 0) {
/* 410 */             incompleteWrite(true);
/*     */             return;
/*     */           } 
/* 413 */           adjustMaxBytesPerGatheringWrite(i, j, maxBytesPerGatheringWrite);
/* 414 */           in.removeBytes(j);
/* 415 */           writeSpinCount--;
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 422 */           attemptedBytes = in.nioBufferSize();
/* 423 */           localWrittenBytes = ch.write(nioBuffers, 0, nioBufferCnt);
/* 424 */           if (localWrittenBytes <= 0L) {
/* 425 */             incompleteWrite(true);
/*     */             
/*     */             return;
/*     */           } 
/* 429 */           adjustMaxBytesPerGatheringWrite((int)attemptedBytes, (int)localWrittenBytes, maxBytesPerGatheringWrite);
/*     */           
/* 431 */           in.removeBytes(localWrittenBytes);
/* 432 */           writeSpinCount--;
/*     */           break;
/*     */       } 
/*     */     
/* 436 */     } while (writeSpinCount > 0);
/*     */     
/* 438 */     incompleteWrite((writeSpinCount < 0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
/* 443 */     return (AbstractNioChannel.AbstractNioUnsafe)new NioSocketChannelUnsafe();
/*     */   }
/*     */   private final class NioSocketChannelUnsafe extends AbstractNioByteChannel.NioByteUnsafe { private NioSocketChannelUnsafe() {
/* 446 */       super(NioSocketChannel.this);
/*     */     }
/*     */     protected Executor prepareToClose() {
/*     */       try {
/* 450 */         if (NioSocketChannel.this.javaChannel().isOpen() && NioSocketChannel.this.config().getSoLinger() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 455 */           NioSocketChannel.this.doDeregister();
/* 456 */           return (Executor)GlobalEventExecutor.INSTANCE;
/*     */         } 
/* 458 */       } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 463 */       return null;
/*     */     } }
/*     */ 
/*     */   
/*     */   private final class NioSocketChannelConfig extends DefaultSocketChannelConfig {
/* 468 */     private volatile int maxBytesPerGatheringWrite = Integer.MAX_VALUE;
/*     */     private NioSocketChannelConfig(NioSocketChannel channel, Socket javaSocket) {
/* 470 */       super(channel, javaSocket);
/* 471 */       calculateMaxBytesPerGatheringWrite();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void autoReadCleared() {
/* 476 */       NioSocketChannel.this.clearReadPending();
/*     */     }
/*     */ 
/*     */     
/*     */     public NioSocketChannelConfig setSendBufferSize(int sendBufferSize) {
/* 481 */       super.setSendBufferSize(sendBufferSize);
/* 482 */       calculateMaxBytesPerGatheringWrite();
/* 483 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 488 */       if (option instanceof NioChannelOption) {
/* 489 */         return NioChannelOption.setOption(jdkChannel(), (NioChannelOption<T>)option, value);
/*     */       }
/* 491 */       return super.setOption(option, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T getOption(ChannelOption<T> option) {
/* 496 */       if (option instanceof NioChannelOption) {
/* 497 */         return NioChannelOption.getOption(jdkChannel(), (NioChannelOption<T>)option);
/*     */       }
/* 499 */       return (T)super.getOption(option);
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<ChannelOption<?>, Object> getOptions() {
/* 504 */       return getOptions(super.getOptions(), (ChannelOption[])NioChannelOption.getOptions(jdkChannel()));
/*     */     }
/*     */     
/*     */     void setMaxBytesPerGatheringWrite(int maxBytesPerGatheringWrite) {
/* 508 */       this.maxBytesPerGatheringWrite = maxBytesPerGatheringWrite;
/*     */     }
/*     */     
/*     */     int getMaxBytesPerGatheringWrite() {
/* 512 */       return this.maxBytesPerGatheringWrite;
/*     */     }
/*     */ 
/*     */     
/*     */     private void calculateMaxBytesPerGatheringWrite() {
/* 517 */       int newSendBufferSize = getSendBufferSize() << 1;
/* 518 */       if (newSendBufferSize > 0) {
/* 519 */         setMaxBytesPerGatheringWrite(newSendBufferSize);
/*     */       }
/*     */     }
/*     */     
/*     */     private SocketChannel jdkChannel() {
/* 524 */       return ((NioSocketChannel)this.channel).javaChannel();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\nio\NioSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */