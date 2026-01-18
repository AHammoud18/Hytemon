/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ConnectTimeoutException;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.IoEvent;
/*     */ import io.netty.channel.IoEventLoop;
/*     */ import io.netty.channel.IoEventLoopGroup;
/*     */ import io.netty.channel.IoRegistration;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
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
/*     */ public abstract class AbstractNioChannel
/*     */   extends AbstractChannel
/*     */ {
/*  56 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
/*     */   private final SelectableChannel ch;
/*     */   protected final int readInterestOp;
/*     */   protected final NioIoOps readOps;
/*     */   volatile IoRegistration registration;
/*     */   boolean readPending;
/*     */   
/*  63 */   private final Runnable clearReadPendingRunnable = new Runnable()
/*     */     {
/*     */       public void run() {
/*  66 */         AbstractNioChannel.this.clearReadPending0();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelPromise connectPromise;
/*     */ 
/*     */ 
/*     */   
/*     */   private Future<?> connectTimeoutFuture;
/*     */ 
/*     */ 
/*     */   
/*     */   private SocketAddress requestedRemoteAddress;
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel(Channel parent, SelectableChannel ch, int readOps) {
/*  86 */     this(parent, ch, NioIoOps.valueOf(readOps));
/*     */   }
/*     */   
/*     */   protected AbstractNioChannel(Channel parent, SelectableChannel ch, NioIoOps readOps) {
/*  90 */     super(parent);
/*  91 */     this.ch = ch;
/*  92 */     this.readInterestOp = ((NioIoOps)ObjectUtil.checkNotNull(readOps, "readOps")).value;
/*  93 */     this.readOps = readOps;
/*     */     try {
/*  95 */       ch.configureBlocking(false);
/*  96 */     } catch (IOException e) {
/*     */       try {
/*  98 */         ch.close();
/*  99 */       } catch (IOException e2) {
/* 100 */         logger.warn("Failed to close a partially initialized socket.", e2);
/*     */       } 
/*     */ 
/*     */       
/* 104 */       throw new ChannelException("Failed to enter non-blocking mode.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addAndSubmit(NioIoOps addOps) {
/* 109 */     int interestOps = selectionKey().interestOps();
/* 110 */     if (!addOps.isIncludedIn(interestOps)) {
/*     */       try {
/* 112 */         registration().submit(NioIoOps.valueOf(interestOps).with(addOps));
/* 113 */       } catch (Exception e) {
/* 114 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void removeAndSubmit(NioIoOps removeOps) {
/* 120 */     int interestOps = selectionKey().interestOps();
/* 121 */     if (removeOps.isIncludedIn(interestOps)) {
/*     */       try {
/* 123 */         registration().submit(NioIoOps.valueOf(interestOps).without(removeOps));
/* 124 */       } catch (Exception e) {
/* 125 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 132 */     return this.ch.isOpen();
/*     */   }
/*     */ 
/*     */   
/*     */   public NioUnsafe unsafe() {
/* 137 */     return (NioUnsafe)super.unsafe();
/*     */   }
/*     */   
/*     */   protected SelectableChannel javaChannel() {
/* 141 */     return this.ch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected SelectionKey selectionKey() {
/* 151 */     return (SelectionKey)registration().attachment();
/*     */   }
/*     */ 
/*     */   
/*     */   protected IoRegistration registration() {
/* 156 */     assert this.registration != null;
/* 157 */     return this.registration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected boolean isReadPending() {
/* 166 */     return this.readPending;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void setReadPending(final boolean readPending) {
/* 175 */     if (isRegistered()) {
/* 176 */       EventLoop eventLoop = eventLoop();
/* 177 */       if (eventLoop.inEventLoop()) {
/* 178 */         setReadPending0(readPending);
/*     */       } else {
/* 180 */         eventLoop.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 183 */                 AbstractNioChannel.this.setReadPending0(readPending);
/*     */               }
/*     */             });
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 191 */       this.readPending = readPending;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void clearReadPending() {
/* 199 */     if (isRegistered()) {
/* 200 */       EventLoop eventLoop = eventLoop();
/* 201 */       if (eventLoop.inEventLoop()) {
/* 202 */         clearReadPending0();
/*     */       } else {
/* 204 */         eventLoop.execute(this.clearReadPendingRunnable);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 210 */       this.readPending = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setReadPending0(boolean readPending) {
/* 215 */     this.readPending = readPending;
/* 216 */     if (!readPending) {
/* 217 */       ((AbstractNioUnsafe)unsafe()).removeReadOp();
/*     */     }
/*     */   }
/*     */   
/*     */   private void clearReadPending0() {
/* 222 */     this.readPending = false;
/* 223 */     ((AbstractNioUnsafe)unsafe()).removeReadOp();
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface NioUnsafe
/*     */     extends Channel.Unsafe
/*     */   {
/*     */     SelectableChannel ch();
/*     */ 
/*     */     
/*     */     void finishConnect();
/*     */ 
/*     */     
/*     */     void read();
/*     */ 
/*     */     
/*     */     void forceFlush();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract class AbstractNioUnsafe
/*     */     extends AbstractChannel.AbstractUnsafe
/*     */     implements NioUnsafe, NioIoHandle
/*     */   {
/*     */     protected AbstractNioUnsafe() {
/* 248 */       super(AbstractNioChannel.this);
/*     */     }
/*     */     public void close() {
/* 251 */       close(voidPromise());
/*     */     }
/*     */ 
/*     */     
/*     */     public SelectableChannel selectableChannel() {
/* 256 */       return ch();
/*     */     }
/*     */     
/*     */     Channel channel() {
/* 260 */       return (Channel)AbstractNioChannel.this;
/*     */     }
/*     */     
/*     */     protected final void removeReadOp() {
/* 264 */       IoRegistration registration = AbstractNioChannel.this.registration();
/*     */ 
/*     */ 
/*     */       
/* 268 */       if (!registration.isValid()) {
/*     */         return;
/*     */       }
/* 271 */       AbstractNioChannel.this.removeAndSubmit(AbstractNioChannel.this.readOps);
/*     */     }
/*     */ 
/*     */     
/*     */     public final SelectableChannel ch() {
/* 276 */       return AbstractNioChannel.this.javaChannel();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 284 */       if (promise.isDone() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 289 */         if (AbstractNioChannel.this.connectPromise != null)
/*     */         {
/* 291 */           throw new ConnectionPendingException();
/*     */         }
/*     */         
/* 294 */         boolean wasActive = AbstractNioChannel.this.isActive();
/* 295 */         if (AbstractNioChannel.this.doConnect(remoteAddress, localAddress)) {
/* 296 */           fulfillConnectPromise(promise, wasActive);
/*     */         } else {
/* 298 */           AbstractNioChannel.this.connectPromise = promise;
/* 299 */           AbstractNioChannel.this.requestedRemoteAddress = remoteAddress;
/*     */ 
/*     */           
/* 302 */           final int connectTimeoutMillis = AbstractNioChannel.this.config().getConnectTimeoutMillis();
/* 303 */           if (connectTimeoutMillis > 0) {
/* 304 */             AbstractNioChannel.this.connectTimeoutFuture = (Future<?>)AbstractNioChannel.this.eventLoop().schedule(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 307 */                     ChannelPromise connectPromise = AbstractNioChannel.this.connectPromise;
/* 308 */                     if (connectPromise != null && !connectPromise.isDone() && connectPromise
/* 309 */                       .tryFailure((Throwable)new ConnectTimeoutException("connection timed out after " + connectTimeoutMillis + " ms: " + remoteAddress)))
/*     */                     {
/*     */                       
/* 312 */                       AbstractNioChannel.AbstractNioUnsafe.this.close(AbstractNioChannel.AbstractNioUnsafe.this.voidPromise());
/*     */                     }
/*     */                   }
/*     */                 },  connectTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */           }
/*     */           
/* 318 */           promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 
/*     */                 public void operationComplete(ChannelFuture future)
/*     */                 {
/* 323 */                   if (future.isCancelled()) {
/* 324 */                     if (AbstractNioChannel.this.connectTimeoutFuture != null) {
/* 325 */                       AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
/*     */                     }
/* 327 */                     AbstractNioChannel.this.connectPromise = null;
/* 328 */                     AbstractNioChannel.AbstractNioUnsafe.this.close(AbstractNioChannel.AbstractNioUnsafe.this.voidPromise());
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/* 333 */       } catch (Throwable t) {
/* 334 */         promise.tryFailure(annotateConnectException(t, remoteAddress));
/* 335 */         closeIfClosed();
/*     */       } 
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
/* 340 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 347 */       boolean active = AbstractNioChannel.this.isActive();
/*     */ 
/*     */       
/* 350 */       boolean promiseSet = promise.trySuccess();
/*     */ 
/*     */ 
/*     */       
/* 354 */       if (!wasActive && active) {
/* 355 */         AbstractNioChannel.this.pipeline().fireChannelActive();
/*     */       }
/*     */ 
/*     */       
/* 359 */       if (!promiseSet) {
/* 360 */         close(voidPromise());
/*     */       }
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
/* 365 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 371 */       promise.tryFailure(cause);
/* 372 */       closeIfClosed();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void finishConnect() {
/* 380 */       assert AbstractNioChannel.this.eventLoop().inEventLoop();
/*     */       
/*     */       try {
/* 383 */         boolean wasActive = AbstractNioChannel.this.isActive();
/* 384 */         AbstractNioChannel.this.doFinishConnect();
/* 385 */         fulfillConnectPromise(AbstractNioChannel.this.connectPromise, wasActive);
/* 386 */       } catch (Throwable t) {
/* 387 */         fulfillConnectPromise(AbstractNioChannel.this.connectPromise, annotateConnectException(t, AbstractNioChannel.this.requestedRemoteAddress));
/*     */       }
/*     */       finally {
/*     */         
/* 391 */         if (AbstractNioChannel.this.connectTimeoutFuture != null) {
/* 392 */           AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
/*     */         }
/* 394 */         AbstractNioChannel.this.connectPromise = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final void flush0() {
/* 403 */       if (!isFlushPending()) {
/* 404 */         super.flush0();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void forceFlush() {
/* 411 */       super.flush0();
/*     */     }
/*     */     
/*     */     private boolean isFlushPending() {
/* 415 */       IoRegistration registration = AbstractNioChannel.this.registration();
/* 416 */       return (registration.isValid() && NioIoOps.WRITE.isIncludedIn(((SelectionKey)registration
/* 417 */           .attachment()).interestOps()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void handle(IoRegistration registration, IoEvent event) {
/*     */       try {
/* 423 */         NioIoEvent nioEvent = (NioIoEvent)event;
/* 424 */         NioIoOps nioReadyOps = nioEvent.ops();
/*     */ 
/*     */         
/* 427 */         if (nioReadyOps.contains(NioIoOps.CONNECT)) {
/*     */ 
/*     */           
/* 430 */           AbstractNioChannel.this.removeAndSubmit(NioIoOps.CONNECT);
/*     */           
/* 432 */           AbstractNioChannel.this.unsafe().finishConnect();
/*     */         } 
/*     */ 
/*     */         
/* 436 */         if (nioReadyOps.contains(NioIoOps.WRITE))
/*     */         {
/*     */           
/* 439 */           forceFlush();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 444 */         if (nioReadyOps.contains(NioIoOps.READ_AND_ACCEPT) || nioReadyOps.equals(NioIoOps.NONE)) {
/* 445 */           read();
/*     */         }
/* 447 */       } catch (CancelledKeyException ignored) {
/* 448 */         close(voidPromise());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/* 455 */     return (loop instanceof IoEventLoop && ((IoEventLoopGroup)loop).isCompatible(AbstractNioUnsafe.class));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doRegister(ChannelPromise promise) {
/* 461 */     assert this.registration == null;
/* 462 */     ((IoEventLoop)eventLoop()).register((AbstractNioUnsafe)unsafe()).addListener(f -> {
/*     */           if (f.isSuccess()) {
/*     */             this.registration = (IoRegistration)f.getNow();
/*     */             promise.setSuccess();
/*     */           } else {
/*     */             promise.setFailure(f.cause());
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 474 */     IoRegistration registration = this.registration;
/* 475 */     if (registration != null) {
/* 476 */       this.registration = null;
/* 477 */       registration.cancel();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 484 */     IoRegistration registration = this.registration;
/* 485 */     if (registration == null || !registration.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 489 */     this.readPending = true;
/*     */     
/* 491 */     addAndSubmit(this.readOps);
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
/*     */   protected final ByteBuf newDirectBuffer(ByteBuf buf) {
/* 510 */     int readableBytes = buf.readableBytes();
/* 511 */     if (readableBytes == 0) {
/* 512 */       ReferenceCountUtil.safeRelease(buf);
/* 513 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/*     */     
/* 516 */     ByteBufAllocator alloc = alloc();
/* 517 */     if (alloc.isDirectBufferPooled()) {
/* 518 */       ByteBuf byteBuf = alloc.directBuffer(readableBytes);
/* 519 */       byteBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 520 */       ReferenceCountUtil.safeRelease(buf);
/* 521 */       return byteBuf;
/*     */     } 
/*     */     
/* 524 */     ByteBuf directBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 525 */     if (directBuf != null) {
/* 526 */       directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 527 */       ReferenceCountUtil.safeRelease(buf);
/* 528 */       return directBuf;
/*     */     } 
/*     */ 
/*     */     
/* 532 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf newDirectBuffer(ReferenceCounted holder, ByteBuf buf) {
/* 542 */     int readableBytes = buf.readableBytes();
/* 543 */     if (readableBytes == 0) {
/* 544 */       ReferenceCountUtil.safeRelease(holder);
/* 545 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/*     */     
/* 548 */     ByteBufAllocator alloc = alloc();
/* 549 */     if (alloc.isDirectBufferPooled()) {
/* 550 */       ByteBuf byteBuf = alloc.directBuffer(readableBytes);
/* 551 */       byteBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 552 */       ReferenceCountUtil.safeRelease(holder);
/* 553 */       return byteBuf;
/*     */     } 
/*     */     
/* 556 */     ByteBuf directBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 557 */     if (directBuf != null) {
/* 558 */       directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 559 */       ReferenceCountUtil.safeRelease(holder);
/* 560 */       return directBuf;
/*     */     } 
/*     */ 
/*     */     
/* 564 */     if (holder != buf) {
/*     */       
/* 566 */       buf.retain();
/* 567 */       ReferenceCountUtil.safeRelease(holder);
/*     */     } 
/*     */     
/* 570 */     return buf;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 575 */     ChannelPromise promise = this.connectPromise;
/* 576 */     if (promise != null) {
/*     */       
/* 578 */       promise.tryFailure(new ClosedChannelException());
/* 579 */       this.connectPromise = null;
/*     */     } 
/*     */     
/* 582 */     Future<?> future = this.connectTimeoutFuture;
/* 583 */     if (future != null) {
/* 584 */       future.cancel(false);
/* 585 */       this.connectTimeoutFuture = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2) throws Exception;
/*     */   
/*     */   protected abstract void doFinishConnect() throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\AbstractNioChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */