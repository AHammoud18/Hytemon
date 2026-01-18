/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ConnectTimeoutException;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.IoEvent;
/*     */ import io.netty.channel.IoEventLoop;
/*     */ import io.netty.channel.IoRegistration;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import io.netty.channel.socket.ChannelInputShutdownReadComplete;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.channel.unix.FileDescriptor;
/*     */ import io.netty.channel.unix.IovArray;
/*     */ import io.netty.channel.unix.Socket;
/*     */ import io.netty.channel.unix.UnixChannel;
/*     */ import io.netty.channel.unix.UnixChannelUtil;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.AlreadyConnectedException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.nio.channels.UnresolvedAddressException;
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
/*     */ abstract class AbstractEpollChannel
/*     */   extends AbstractChannel
/*     */   implements UnixChannel
/*     */ {
/*  67 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*     */   protected final LinuxSocket socket;
/*     */   
/*     */   private ChannelPromise connectPromise;
/*     */   
/*     */   private Future<?> connectTimeoutFuture;
/*     */   
/*     */   private SocketAddress requestedRemoteAddress;
/*     */   
/*     */   private volatile SocketAddress local;
/*     */   
/*     */   private volatile SocketAddress remote;
/*     */   private IoRegistration registration;
/*     */   boolean inputClosedSeenErrorOnRead;
/*     */   private EpollIoOps ops;
/*     */   private EpollIoOps inital;
/*     */   protected volatile boolean active;
/*     */   
/*     */   AbstractEpollChannel(Channel parent, LinuxSocket fd, boolean active, EpollIoOps initialOps) {
/*  87 */     super(parent);
/*  88 */     this.socket = (LinuxSocket)ObjectUtil.checkNotNull(fd, "fd");
/*  89 */     this.active = active;
/*  90 */     if (active) {
/*     */ 
/*     */       
/*  93 */       this.local = fd.localAddress();
/*  94 */       this.remote = fd.remoteAddress();
/*     */     } 
/*  96 */     this.ops = initialOps;
/*     */   }
/*     */   
/*     */   AbstractEpollChannel(Channel parent, LinuxSocket fd, SocketAddress remote, EpollIoOps initialOps) {
/* 100 */     super(parent);
/* 101 */     this.socket = (LinuxSocket)ObjectUtil.checkNotNull(fd, "fd");
/* 102 */     this.active = true;
/*     */ 
/*     */     
/* 105 */     this.remote = remote;
/* 106 */     this.local = fd.localAddress();
/* 107 */     this.ops = initialOps;
/*     */   }
/*     */   
/*     */   static boolean isSoErrorZero(Socket fd) {
/*     */     try {
/* 112 */       return (fd.getSoError() == 0);
/* 113 */     } catch (IOException e) {
/* 114 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setFlag(int flag) throws IOException {
/* 119 */     if (this.ops.contains(flag)) {
/*     */       return;
/*     */     }
/*     */     
/* 123 */     this.ops = this.ops.with(EpollIoOps.valueOf(flag));
/* 124 */     if (isRegistered()) {
/* 125 */       IoRegistration registration = registration();
/* 126 */       registration.submit(this.ops);
/*     */     } else {
/* 128 */       this.ops = this.ops.with(EpollIoOps.valueOf(flag));
/*     */     } 
/*     */   }
/*     */   
/*     */   void clearFlag(int flag) throws IOException {
/* 133 */     IoRegistration registration = registration();
/* 134 */     if (!this.ops.contains(flag)) {
/*     */       return;
/*     */     }
/*     */     
/* 138 */     this.ops = this.ops.without(EpollIoOps.valueOf(flag));
/* 139 */     registration.submit(this.ops);
/*     */   }
/*     */   
/*     */   protected final IoRegistration registration() {
/* 143 */     assert this.registration != null;
/* 144 */     return this.registration;
/*     */   }
/*     */   
/*     */   boolean isFlagSet(int flag) {
/* 148 */     return ((this.ops.value & flag) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public final FileDescriptor fd() {
/* 153 */     return (FileDescriptor)this.socket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 161 */     return this.active;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 166 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 171 */     this.active = false;
/*     */ 
/*     */     
/* 174 */     this.inputClosedSeenErrorOnRead = true;
/*     */     try {
/* 176 */       ChannelPromise promise = this.connectPromise;
/* 177 */       if (promise != null) {
/*     */         
/* 179 */         promise.tryFailure(new ClosedChannelException());
/* 180 */         this.connectPromise = null;
/*     */       } 
/*     */       
/* 183 */       Future<?> future = this.connectTimeoutFuture;
/* 184 */       if (future != null) {
/* 185 */         future.cancel(false);
/* 186 */         this.connectTimeoutFuture = null;
/*     */       } 
/*     */       
/* 189 */       if (isRegistered()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 194 */         EventLoop loop = eventLoop();
/* 195 */         if (loop.inEventLoop()) {
/* 196 */           doDeregister();
/*     */         } else {
/* 198 */           loop.execute(new Runnable()
/*     */               {
/*     */                 public void run() {
/*     */                   try {
/* 202 */                     AbstractEpollChannel.this.doDeregister();
/* 203 */                   } catch (Throwable cause) {
/* 204 */                     AbstractEpollChannel.this.pipeline().fireExceptionCaught(cause);
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 211 */       this.socket.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   void resetCachedAddresses() {
/* 216 */     this.local = this.socket.localAddress();
/* 217 */     this.remote = this.socket.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 222 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 227 */     return this.socket.isOpen();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 232 */     IoRegistration registration = this.registration;
/* 233 */     if (registration != null) {
/* 234 */       this.ops = this.inital;
/* 235 */       registration.cancel();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/* 241 */     return (loop instanceof IoEventLoop && ((IoEventLoop)loop).isCompatible(AbstractEpollUnsafe.class));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 247 */     AbstractEpollUnsafe unsafe = (AbstractEpollUnsafe)unsafe();
/* 248 */     unsafe.readPending = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     setFlag(Native.EPOLLIN);
/*     */   }
/*     */   
/*     */   final boolean shouldBreakEpollInReady(ChannelConfig config) {
/* 257 */     return (this.socket.isInputShutdown() && (this.inputClosedSeenErrorOnRead || !isAllowHalfClosure(config)));
/*     */   }
/*     */   
/*     */   private static boolean isAllowHalfClosure(ChannelConfig config) {
/* 261 */     if (config instanceof EpollDomainSocketChannelConfig) {
/* 262 */       return ((EpollDomainSocketChannelConfig)config).isAllowHalfClosure();
/*     */     }
/* 264 */     return (config instanceof SocketChannelConfig && ((SocketChannelConfig)config)
/* 265 */       .isAllowHalfClosure());
/*     */   }
/*     */ 
/*     */   
/*     */   final void clearEpollIn() {
/* 270 */     if (isRegistered()) {
/* 271 */       EventLoop loop = eventLoop();
/* 272 */       final AbstractEpollUnsafe unsafe = (AbstractEpollUnsafe)unsafe();
/* 273 */       if (loop.inEventLoop()) {
/* 274 */         unsafe.clearEpollIn0();
/*     */       } else {
/*     */         
/* 277 */         loop.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 280 */                 if (!unsafe.readPending && !AbstractEpollChannel.this.config().isAutoRead())
/*     */                 {
/* 282 */                   unsafe.clearEpollIn0();
/*     */                 }
/*     */               }
/*     */             });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 290 */       this.ops = this.ops.without(EpollIoOps.EPOLLIN);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister(ChannelPromise promise) {
/* 296 */     ((IoEventLoop)eventLoop()).register((AbstractEpollUnsafe)unsafe()).addListener(f -> {
/*     */           if (f.isSuccess()) {
/*     */             this.registration = (IoRegistration)f.getNow();
/*     */             this.registration.submit(this.ops);
/*     */             this.inital = this.ops;
/*     */             promise.setSuccess();
/*     */           } else {
/*     */             promise.setFailure(f.cause());
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf newDirectBuffer(ByteBuf buf) {
/* 315 */     return newDirectBuffer(buf, buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf newDirectBuffer(Object holder, ByteBuf buf) {
/* 324 */     int readableBytes = buf.readableBytes();
/* 325 */     if (readableBytes == 0) {
/* 326 */       ReferenceCountUtil.release(holder);
/* 327 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/*     */     
/* 330 */     ByteBufAllocator alloc = alloc();
/* 331 */     if (alloc.isDirectBufferPooled()) {
/* 332 */       return newDirectBuffer0(holder, buf, alloc, readableBytes);
/*     */     }
/*     */     
/* 335 */     ByteBuf directBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 336 */     if (directBuf == null) {
/* 337 */       return newDirectBuffer0(holder, buf, alloc, readableBytes);
/*     */     }
/*     */     
/* 340 */     directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 341 */     ReferenceCountUtil.safeRelease(holder);
/* 342 */     return directBuf;
/*     */   }
/*     */   
/*     */   private static ByteBuf newDirectBuffer0(Object holder, ByteBuf buf, ByteBufAllocator alloc, int capacity) {
/* 346 */     ByteBuf directBuf = alloc.directBuffer(capacity);
/* 347 */     directBuf.writeBytes(buf, buf.readerIndex(), capacity);
/* 348 */     ReferenceCountUtil.safeRelease(holder);
/* 349 */     return directBuf;
/*     */   }
/*     */   
/*     */   protected static void checkResolvable(InetSocketAddress addr) {
/* 353 */     if (addr.isUnresolved()) {
/* 354 */       throw new UnresolvedAddressException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int doReadBytes(ByteBuf byteBuf) throws Exception {
/* 362 */     int localReadAmount, writerIndex = byteBuf.writerIndex();
/*     */     
/* 364 */     unsafe().recvBufAllocHandle().attemptedBytesRead(byteBuf.writableBytes());
/* 365 */     if (byteBuf.hasMemoryAddress()) {
/* 366 */       localReadAmount = this.socket.recvAddress(byteBuf.memoryAddress(), writerIndex, byteBuf.capacity());
/*     */     } else {
/* 368 */       ByteBuffer buf = byteBuf.internalNioBuffer(writerIndex, byteBuf.writableBytes());
/* 369 */       localReadAmount = this.socket.recv(buf, buf.position(), buf.limit());
/*     */     } 
/* 371 */     if (localReadAmount > 0) {
/* 372 */       byteBuf.writerIndex(writerIndex + localReadAmount);
/*     */     }
/* 374 */     return localReadAmount;
/*     */   }
/*     */   
/*     */   protected final int doWriteBytes(ChannelOutboundBuffer in, ByteBuf buf) throws Exception {
/* 378 */     if (buf.hasMemoryAddress()) {
/* 379 */       int localFlushedAmount = this.socket.sendAddress(buf.memoryAddress(), buf.readerIndex(), buf.writerIndex());
/* 380 */       if (localFlushedAmount > 0) {
/* 381 */         in.removeBytes(localFlushedAmount);
/* 382 */         return 1;
/*     */       } 
/*     */     } else {
/*     */       
/* 386 */       ByteBuffer nioBuf = (buf.nioBufferCount() == 1) ? buf.internalNioBuffer(buf.readerIndex(), buf.readableBytes()) : buf.nioBuffer();
/* 387 */       int localFlushedAmount = this.socket.send(nioBuf, nioBuf.position(), nioBuf.limit());
/* 388 */       if (localFlushedAmount > 0) {
/* 389 */         nioBuf.position(nioBuf.position() + localFlushedAmount);
/* 390 */         in.removeBytes(localFlushedAmount);
/* 391 */         return 1;
/*     */       } 
/*     */     } 
/* 394 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final long doWriteOrSendBytes(ByteBuf data, InetSocketAddress remoteAddress, boolean fastOpen) throws IOException {
/* 403 */     assert !fastOpen || remoteAddress != null : "fastOpen requires a remote address";
/* 404 */     if (data.hasMemoryAddress()) {
/* 405 */       long memoryAddress = data.memoryAddress();
/* 406 */       if (remoteAddress == null) {
/* 407 */         return this.socket.sendAddress(memoryAddress, data.readerIndex(), data.writerIndex());
/*     */       }
/* 409 */       return this.socket.sendToAddress(memoryAddress, data.readerIndex(), data.writerIndex(), remoteAddress
/* 410 */           .getAddress(), remoteAddress.getPort(), fastOpen);
/*     */     } 
/*     */     
/* 413 */     if (data.nioBufferCount() > 1) {
/* 414 */       IovArray array = ((NativeArrays)this.registration.attachment()).cleanIovArray();
/* 415 */       array.add(data, data.readerIndex(), data.readableBytes());
/* 416 */       int cnt = array.count();
/* 417 */       assert cnt != 0;
/*     */       
/* 419 */       if (remoteAddress == null) {
/* 420 */         return this.socket.writevAddresses(array.memoryAddress(0), cnt);
/*     */       }
/* 422 */       return this.socket.sendToAddresses(array.memoryAddress(0), cnt, remoteAddress
/* 423 */           .getAddress(), remoteAddress.getPort(), fastOpen);
/*     */     } 
/*     */     
/* 426 */     ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
/* 427 */     if (remoteAddress == null) {
/* 428 */       return this.socket.send(nioData, nioData.position(), nioData.limit());
/*     */     }
/* 430 */     return this.socket.sendTo(nioData, nioData.position(), nioData.limit(), remoteAddress
/* 431 */         .getAddress(), remoteAddress.getPort(), fastOpen);
/*     */   } protected abstract class AbstractEpollUnsafe extends AbstractChannel.AbstractUnsafe implements EpollIoHandle { boolean readPending;
/*     */     protected AbstractEpollUnsafe() {
/* 434 */       super(AbstractEpollChannel.this);
/*     */     }
/*     */     private EpollRecvByteAllocatorHandle allocHandle;
/*     */     
/*     */     Channel channel() {
/* 439 */       return (Channel)AbstractEpollChannel.this;
/*     */     }
/*     */ 
/*     */     
/*     */     public FileDescriptor fd() {
/* 444 */       return AbstractEpollChannel.this.fd();
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 449 */       close(voidPromise());
/*     */     }
/*     */ 
/*     */     
/*     */     public void handle(IoRegistration registration, IoEvent event) {
/* 454 */       EpollIoEvent epollEvent = (EpollIoEvent)event;
/* 455 */       int ops = (epollEvent.ops()).value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 470 */       if ((ops & EpollIoOps.EPOLL_ERR_OUT_MASK) != 0)
/*     */       {
/* 472 */         epollOutReady();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 480 */       if ((ops & EpollIoOps.EPOLL_ERR_IN_MASK) != 0)
/*     */       {
/* 482 */         epollInReady();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 488 */       if ((ops & EpollIoOps.EPOLL_RDHUP_MASK) != 0) {
/* 489 */         epollRdHupReady();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final boolean shouldStopReading(ChannelConfig config) {
/* 505 */       return (!this.readPending && !config.isAutoRead());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final void epollRdHupReady() {
/* 513 */       recvBufAllocHandle().receivedRdHup();
/*     */       
/* 515 */       if (AbstractEpollChannel.this.isActive()) {
/*     */ 
/*     */ 
/*     */         
/* 519 */         epollInReady();
/*     */       } else {
/*     */         
/* 522 */         shutdownInput(false);
/*     */       } 
/*     */ 
/*     */       
/* 526 */       clearEpollRdHup();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void clearEpollRdHup() {
/*     */       try {
/* 534 */         AbstractEpollChannel.this.clearFlag(Native.EPOLLRDHUP);
/* 535 */       } catch (IOException e) {
/* 536 */         AbstractEpollChannel.this.pipeline().fireExceptionCaught(e);
/* 537 */         close(voidPromise());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void shutdownInput(boolean allDataRead) {
/* 545 */       if (!AbstractEpollChannel.this.socket.isInputShutdown()) {
/* 546 */         if (AbstractEpollChannel.isAllowHalfClosure((ChannelConfig)AbstractEpollChannel.this.config())) {
/*     */           try {
/* 548 */             AbstractEpollChannel.this.socket.shutdown(true, false);
/* 549 */           } catch (IOException ignored) {
/*     */ 
/*     */             
/* 552 */             fireEventAndClose(ChannelInputShutdownEvent.INSTANCE);
/*     */             return;
/* 554 */           } catch (NotYetConnectedException notYetConnectedException) {}
/*     */ 
/*     */ 
/*     */           
/* 558 */           if (shouldStopReading((ChannelConfig)AbstractEpollChannel.this.config())) {
/* 559 */             clearEpollIn0();
/*     */           }
/* 561 */           AbstractEpollChannel.this.pipeline().fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */         } else {
/* 563 */           close(voidPromise());
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/* 568 */       if (allDataRead && !AbstractEpollChannel.this.inputClosedSeenErrorOnRead) {
/* 569 */         AbstractEpollChannel.this.inputClosedSeenErrorOnRead = true;
/* 570 */         AbstractEpollChannel.this.pipeline().fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void fireEventAndClose(Object evt) {
/* 575 */       AbstractEpollChannel.this.pipeline().fireUserEventTriggered(evt);
/* 576 */       close(voidPromise());
/*     */     }
/*     */ 
/*     */     
/*     */     public EpollRecvByteAllocatorHandle recvBufAllocHandle() {
/* 581 */       if (this.allocHandle == null) {
/* 582 */         this.allocHandle = newEpollHandle((RecvByteBufAllocator.ExtendedHandle)super.recvBufAllocHandle());
/*     */       }
/* 584 */       return this.allocHandle;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     EpollRecvByteAllocatorHandle newEpollHandle(RecvByteBufAllocator.ExtendedHandle handle) {
/* 592 */       return new EpollRecvByteAllocatorHandle(handle);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final void flush0() {
/* 600 */       if (!AbstractEpollChannel.this.isFlagSet(Native.EPOLLOUT)) {
/* 601 */         super.flush0();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final void epollOutReady() {
/* 609 */       if (AbstractEpollChannel.this.connectPromise != null) {
/*     */         
/* 611 */         finishConnect();
/* 612 */       } else if (!AbstractEpollChannel.this.socket.isOutputShutdown()) {
/*     */         
/* 614 */         super.flush0();
/*     */       } 
/*     */     }
/*     */     
/*     */     protected final void clearEpollIn0() {
/* 619 */       assert AbstractEpollChannel.this.eventLoop().inEventLoop();
/*     */       try {
/* 621 */         this.readPending = false;
/* 622 */         if (!AbstractEpollChannel.this.ops.contains(EpollIoOps.EPOLLIN)) {
/*     */           return;
/*     */         }
/* 625 */         AbstractEpollChannel.this.ops = AbstractEpollChannel.this.ops.without(EpollIoOps.EPOLLIN);
/* 626 */         IoRegistration registration = AbstractEpollChannel.this.registration();
/* 627 */         registration.submit(AbstractEpollChannel.this.ops);
/* 628 */       } catch (UncheckedIOException e) {
/*     */ 
/*     */         
/* 631 */         AbstractEpollChannel.this.pipeline().fireExceptionCaught(e);
/* 632 */         AbstractEpollChannel.this.unsafe().close(AbstractEpollChannel.this.unsafe().voidPromise());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 641 */       if (promise.isDone() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 646 */         if (AbstractEpollChannel.this.connectPromise != null) {
/* 647 */           throw new ConnectionPendingException();
/*     */         }
/*     */         
/* 650 */         boolean wasActive = AbstractEpollChannel.this.isActive();
/* 651 */         if (AbstractEpollChannel.this.doConnect(remoteAddress, localAddress)) {
/* 652 */           fulfillConnectPromise(promise, wasActive);
/*     */         } else {
/* 654 */           AbstractEpollChannel.this.connectPromise = promise;
/* 655 */           AbstractEpollChannel.this.requestedRemoteAddress = remoteAddress;
/*     */ 
/*     */           
/* 658 */           final int connectTimeoutMillis = AbstractEpollChannel.this.config().getConnectTimeoutMillis();
/* 659 */           if (connectTimeoutMillis > 0) {
/* 660 */             AbstractEpollChannel.this.connectTimeoutFuture = (Future<?>)AbstractEpollChannel.this.eventLoop().schedule(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 663 */                     ChannelPromise connectPromise = AbstractEpollChannel.this.connectPromise;
/* 664 */                     if (connectPromise != null && !connectPromise.isDone() && connectPromise
/* 665 */                       .tryFailure((Throwable)new ConnectTimeoutException("connection timed out after " + connectTimeoutMillis + " ms: " + remoteAddress)))
/*     */                     {
/*     */                       
/* 668 */                       AbstractEpollChannel.AbstractEpollUnsafe.this.close(AbstractEpollChannel.AbstractEpollUnsafe.this.voidPromise());
/*     */                     }
/*     */                   }
/*     */                 },  connectTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */           }
/*     */           
/* 674 */           promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 
/*     */                 public void operationComplete(ChannelFuture future)
/*     */                 {
/* 679 */                   if (future.isCancelled()) {
/* 680 */                     if (AbstractEpollChannel.this.connectTimeoutFuture != null) {
/* 681 */                       AbstractEpollChannel.this.connectTimeoutFuture.cancel(false);
/*     */                     }
/* 683 */                     AbstractEpollChannel.this.connectPromise = null;
/* 684 */                     AbstractEpollChannel.AbstractEpollUnsafe.this.close(AbstractEpollChannel.AbstractEpollUnsafe.this.voidPromise());
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/* 689 */       } catch (Throwable t) {
/* 690 */         closeIfClosed();
/* 691 */         promise.tryFailure(annotateConnectException(t, remoteAddress));
/*     */       } 
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
/* 696 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */       
/* 700 */       AbstractEpollChannel.this.active = true;
/*     */ 
/*     */ 
/*     */       
/* 704 */       boolean active = AbstractEpollChannel.this.isActive();
/*     */ 
/*     */       
/* 707 */       boolean promiseSet = promise.trySuccess();
/*     */ 
/*     */ 
/*     */       
/* 711 */       if (!wasActive && active) {
/* 712 */         AbstractEpollChannel.this.pipeline().fireChannelActive();
/*     */       }
/*     */ 
/*     */       
/* 716 */       if (!promiseSet) {
/* 717 */         close(voidPromise());
/*     */       }
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
/* 722 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 728 */       promise.tryFailure(cause);
/* 729 */       closeIfClosed();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void finishConnect() {
/* 736 */       assert AbstractEpollChannel.this.eventLoop().inEventLoop();
/*     */       
/* 738 */       boolean connectStillInProgress = false;
/*     */       try {
/* 740 */         boolean wasActive = AbstractEpollChannel.this.isActive();
/* 741 */         if (!doFinishConnect()) {
/* 742 */           connectStillInProgress = true;
/*     */           return;
/*     */         } 
/* 745 */         fulfillConnectPromise(AbstractEpollChannel.this.connectPromise, wasActive);
/* 746 */       } catch (Throwable t) {
/* 747 */         fulfillConnectPromise(AbstractEpollChannel.this.connectPromise, annotateConnectException(t, AbstractEpollChannel.this.requestedRemoteAddress));
/*     */       } finally {
/* 749 */         if (!connectStillInProgress) {
/*     */ 
/*     */           
/* 752 */           if (AbstractEpollChannel.this.connectTimeoutFuture != null) {
/* 753 */             AbstractEpollChannel.this.connectTimeoutFuture.cancel(false);
/*     */           }
/* 755 */           AbstractEpollChannel.this.connectPromise = null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean doFinishConnect() throws Exception {
/* 764 */       if (AbstractEpollChannel.this.socket.finishConnect()) {
/* 765 */         AbstractEpollChannel.this.clearFlag(Native.EPOLLOUT);
/* 766 */         if (AbstractEpollChannel.this.requestedRemoteAddress instanceof InetSocketAddress) {
/* 767 */           AbstractEpollChannel.this.remote = UnixChannelUtil.computeRemoteAddr((InetSocketAddress)AbstractEpollChannel.this.requestedRemoteAddress, AbstractEpollChannel.this.socket.remoteAddress());
/*     */         }
/* 769 */         AbstractEpollChannel.this.requestedRemoteAddress = null;
/*     */         
/* 771 */         return true;
/*     */       } 
/* 773 */       AbstractEpollChannel.this.setFlag(Native.EPOLLOUT);
/* 774 */       return false;
/*     */     }
/*     */     
/*     */     abstract void epollInReady(); }
/*     */   
/*     */   protected void doBind(SocketAddress local) throws Exception {
/* 780 */     if (local instanceof InetSocketAddress) {
/* 781 */       checkResolvable((InetSocketAddress)local);
/*     */     }
/* 783 */     this.socket.bind(local);
/* 784 */     this.local = this.socket.localAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 791 */     if (localAddress instanceof InetSocketAddress) {
/* 792 */       checkResolvable((InetSocketAddress)localAddress);
/*     */     }
/*     */ 
/*     */     
/* 796 */     InetSocketAddress remoteSocketAddr = (remoteAddress instanceof InetSocketAddress) ? (InetSocketAddress)remoteAddress : null;
/* 797 */     if (remoteSocketAddr != null) {
/* 798 */       checkResolvable(remoteSocketAddr);
/*     */     }
/*     */     
/* 801 */     if (this.remote != null)
/*     */     {
/*     */ 
/*     */       
/* 805 */       throw new AlreadyConnectedException();
/*     */     }
/*     */     
/* 808 */     if (localAddress != null) {
/* 809 */       this.socket.bind(localAddress);
/*     */     }
/*     */     
/* 812 */     boolean connected = doConnect0(remoteAddress);
/* 813 */     if (connected) {
/* 814 */       this
/* 815 */         .remote = (remoteSocketAddr == null) ? remoteAddress : UnixChannelUtil.computeRemoteAddr(remoteSocketAddr, this.socket.remoteAddress());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 820 */     this.local = this.socket.localAddress();
/* 821 */     return connected;
/*     */   }
/*     */   
/*     */   boolean doConnect0(SocketAddress remote) throws Exception {
/* 825 */     boolean success = false;
/*     */     try {
/* 827 */       boolean connected = this.socket.connect(remote);
/* 828 */       if (!connected) {
/* 829 */         setFlag(Native.EPOLLOUT);
/*     */       }
/* 831 */       success = true;
/* 832 */       return connected;
/*     */     } finally {
/* 834 */       if (!success) {
/* 835 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 842 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 847 */     return this.remote;
/*     */   }
/*     */   
/*     */   public abstract EpollChannelConfig config();
/*     */   
/*     */   protected abstract AbstractEpollUnsafe newUnsafe();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\AbstractEpollChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */