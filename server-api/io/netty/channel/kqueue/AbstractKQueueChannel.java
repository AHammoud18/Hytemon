/*     */ package io.netty.channel.kqueue;
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
/*     */ import io.netty.channel.unix.UnixChannel;
/*     */ import io.netty.channel.unix.UnixChannelUtil;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.AlreadyConnectedException;
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
/*     */ abstract class AbstractKQueueChannel
/*     */   extends AbstractChannel
/*     */   implements UnixChannel
/*     */ {
/*  63 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*     */   private ChannelPromise connectPromise;
/*     */   
/*     */   private Future<?> connectTimeoutFuture;
/*     */   
/*     */   private SocketAddress requestedRemoteAddress;
/*     */   
/*     */   final BsdSocket socket;
/*     */   
/*     */   private IoRegistration registration;
/*     */   
/*     */   private boolean readFilterEnabled;
/*     */   private boolean writeFilterEnabled;
/*     */   boolean readReadyRunnablePending;
/*     */   boolean inputClosedSeenErrorOnRead;
/*     */   protected volatile boolean active;
/*     */   private volatile SocketAddress local;
/*     */   private volatile SocketAddress remote;
/*     */   
/*     */   AbstractKQueueChannel(Channel parent, BsdSocket fd, boolean active) {
/*  84 */     super(parent);
/*  85 */     this.socket = (BsdSocket)ObjectUtil.checkNotNull(fd, "fd");
/*  86 */     this.active = active;
/*  87 */     if (active) {
/*     */ 
/*     */       
/*  90 */       this.local = fd.localAddress();
/*  91 */       this.remote = fd.remoteAddress();
/*     */     } 
/*     */   }
/*     */   
/*     */   AbstractKQueueChannel(Channel parent, BsdSocket fd, SocketAddress remote) {
/*  96 */     super(parent);
/*  97 */     this.socket = (BsdSocket)ObjectUtil.checkNotNull(fd, "fd");
/*  98 */     this.active = true;
/*     */ 
/*     */     
/* 101 */     this.remote = remote;
/* 102 */     this.local = fd.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/* 107 */     return (loop instanceof IoEventLoop && ((IoEventLoop)loop).isCompatible(AbstractKQueueUnsafe.class));
/*     */   }
/*     */   
/*     */   static boolean isSoErrorZero(BsdSocket fd) {
/*     */     try {
/* 112 */       return (fd.getSoError() == 0);
/* 113 */     } catch (IOException e) {
/* 114 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final IoRegistration registration() {
/* 119 */     assert this.registration != null;
/* 120 */     return this.registration;
/*     */   }
/*     */ 
/*     */   
/*     */   public final FileDescriptor fd() {
/* 125 */     return (FileDescriptor)this.socket;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 130 */     return this.active;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 135 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 140 */     this.active = false;
/*     */ 
/*     */     
/* 143 */     this.inputClosedSeenErrorOnRead = true;
/* 144 */     this.socket.close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 149 */     doClose();
/*     */   }
/*     */   
/*     */   void resetCachedAddresses() {
/* 153 */     this.local = this.socket.localAddress();
/* 154 */     this.remote = this.socket.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 159 */     return this.socket.isOpen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 167 */     readFilter(false);
/* 168 */     writeFilter(false);
/* 169 */     clearRdHup0();
/*     */     
/* 171 */     IoRegistration registration = this.registration;
/* 172 */     if (registration != null) {
/* 173 */       registration.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   private void clearRdHup0() {
/* 178 */     submit(KQueueIoOps.newOps(Native.EVFILT_SOCK, Native.EV_DELETE_DISABLE, Native.NOTE_RDHUP));
/*     */   }
/*     */   
/*     */   private void submit(KQueueIoOps ops) {
/*     */     try {
/* 183 */       this.registration.submit(ops);
/* 184 */     } catch (Exception e) {
/* 185 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void doBeginRead() throws Exception {
/* 192 */     AbstractKQueueUnsafe unsafe = (AbstractKQueueUnsafe)unsafe();
/* 193 */     unsafe.readPending = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     readFilter(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister(ChannelPromise promise) {
/* 203 */     ((IoEventLoop)eventLoop()).register((AbstractKQueueUnsafe)unsafe()).addListener(f -> {
/*     */           if (f.isSuccess()) {
/*     */             this.registration = (IoRegistration)f.getNow();
/*     */             this.readReadyRunnablePending = false;
/*     */             submit(KQueueIoOps.newOps(Native.EVFILT_SOCK, Native.EV_ADD, Native.NOTE_RDHUP));
/*     */             if (this.writeFilterEnabled) {
/*     */               submit(Native.WRITE_ENABLED_OPS);
/*     */             }
/*     */             if (this.readFilterEnabled) {
/*     */               submit(Native.READ_ENABLED_OPS);
/*     */             }
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
/* 237 */     return newDirectBuffer(buf, buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ByteBuf newDirectBuffer(Object holder, ByteBuf buf) {
/* 246 */     int readableBytes = buf.readableBytes();
/* 247 */     if (readableBytes == 0) {
/* 248 */       ReferenceCountUtil.release(holder);
/* 249 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/*     */     
/* 252 */     ByteBufAllocator alloc = alloc();
/* 253 */     if (alloc.isDirectBufferPooled()) {
/* 254 */       return newDirectBuffer0(holder, buf, alloc, readableBytes);
/*     */     }
/*     */     
/* 257 */     ByteBuf directBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 258 */     if (directBuf == null) {
/* 259 */       return newDirectBuffer0(holder, buf, alloc, readableBytes);
/*     */     }
/*     */     
/* 262 */     directBuf.writeBytes(buf, buf.readerIndex(), readableBytes);
/* 263 */     ReferenceCountUtil.safeRelease(holder);
/* 264 */     return directBuf;
/*     */   }
/*     */   
/*     */   private static ByteBuf newDirectBuffer0(Object holder, ByteBuf buf, ByteBufAllocator alloc, int capacity) {
/* 268 */     ByteBuf directBuf = alloc.directBuffer(capacity);
/* 269 */     directBuf.writeBytes(buf, buf.readerIndex(), capacity);
/* 270 */     ReferenceCountUtil.safeRelease(holder);
/* 271 */     return directBuf;
/*     */   }
/*     */   
/*     */   protected static void checkResolvable(InetSocketAddress addr) {
/* 275 */     if (addr.isUnresolved()) {
/* 276 */       throw new UnresolvedAddressException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int doReadBytes(ByteBuf byteBuf) throws Exception {
/* 284 */     int localReadAmount, writerIndex = byteBuf.writerIndex();
/*     */     
/* 286 */     unsafe().recvBufAllocHandle().attemptedBytesRead(byteBuf.writableBytes());
/* 287 */     if (byteBuf.hasMemoryAddress()) {
/* 288 */       localReadAmount = this.socket.readAddress(byteBuf.memoryAddress(), writerIndex, byteBuf.capacity());
/*     */     } else {
/* 290 */       ByteBuffer buf = byteBuf.internalNioBuffer(writerIndex, byteBuf.writableBytes());
/* 291 */       localReadAmount = this.socket.read(buf, buf.position(), buf.limit());
/*     */     } 
/* 293 */     if (localReadAmount > 0) {
/* 294 */       byteBuf.writerIndex(writerIndex + localReadAmount);
/*     */     }
/* 296 */     return localReadAmount;
/*     */   }
/*     */   
/*     */   protected final int doWriteBytes(ChannelOutboundBuffer in, ByteBuf buf) throws Exception {
/* 300 */     if (buf.hasMemoryAddress()) {
/* 301 */       int localFlushedAmount = this.socket.writeAddress(buf.memoryAddress(), buf.readerIndex(), buf.writerIndex());
/* 302 */       if (localFlushedAmount > 0) {
/* 303 */         in.removeBytes(localFlushedAmount);
/* 304 */         return 1;
/*     */       } 
/*     */     } else {
/*     */       
/* 308 */       ByteBuffer nioBuf = (buf.nioBufferCount() == 1) ? buf.internalNioBuffer(buf.readerIndex(), buf.readableBytes()) : buf.nioBuffer();
/* 309 */       int localFlushedAmount = this.socket.write(nioBuf, nioBuf.position(), nioBuf.limit());
/* 310 */       if (localFlushedAmount > 0) {
/* 311 */         nioBuf.position(nioBuf.position() + localFlushedAmount);
/* 312 */         in.removeBytes(localFlushedAmount);
/* 313 */         return 1;
/*     */       } 
/*     */     } 
/* 316 */     return Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   final boolean shouldBreakReadReady(ChannelConfig config) {
/* 320 */     return (this.socket.isInputShutdown() && (this.inputClosedSeenErrorOnRead || !isAllowHalfClosure(config)));
/*     */   }
/*     */   
/*     */   private static boolean isAllowHalfClosure(ChannelConfig config) {
/* 324 */     if (config instanceof KQueueDomainSocketChannelConfig) {
/* 325 */       return ((KQueueDomainSocketChannelConfig)config).isAllowHalfClosure();
/*     */     }
/*     */     
/* 328 */     return (config instanceof SocketChannelConfig && ((SocketChannelConfig)config)
/* 329 */       .isAllowHalfClosure());
/*     */   }
/*     */ 
/*     */   
/*     */   final void clearReadFilter() {
/* 334 */     if (isRegistered()) {
/* 335 */       EventLoop loop = eventLoop();
/* 336 */       final AbstractKQueueUnsafe unsafe = (AbstractKQueueUnsafe)unsafe();
/* 337 */       if (loop.inEventLoop()) {
/* 338 */         unsafe.clearReadFilter0();
/*     */       } else {
/*     */         
/* 341 */         loop.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 344 */                 if (!unsafe.readPending && !AbstractKQueueChannel.this.config().isAutoRead())
/*     */                 {
/* 346 */                   unsafe.clearReadFilter0();
/*     */                 }
/*     */               }
/*     */             });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 354 */       this.readFilterEnabled = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   void readFilter(boolean readFilterEnabled) throws IOException {
/* 359 */     if (this.readFilterEnabled != readFilterEnabled) {
/* 360 */       this.readFilterEnabled = readFilterEnabled;
/* 361 */       submit(readFilterEnabled ? Native.READ_ENABLED_OPS : Native.READ_DISABLED_OPS);
/*     */     } 
/*     */   }
/*     */   
/*     */   void writeFilter(boolean writeFilterEnabled) throws IOException {
/* 366 */     if (this.writeFilterEnabled != writeFilterEnabled) {
/* 367 */       this.writeFilterEnabled = writeFilterEnabled;
/* 368 */       submit(writeFilterEnabled ? Native.WRITE_ENABLED_OPS : Native.WRITE_DISABLED_OPS);
/*     */     } 
/*     */   }
/*     */   public abstract class AbstractKQueueUnsafe extends AbstractChannel.AbstractUnsafe implements KQueueIoHandle { boolean readPending;
/*     */     public AbstractKQueueUnsafe() {
/* 373 */       super(AbstractKQueueChannel.this);
/*     */     }
/*     */     private KQueueRecvByteAllocatorHandle allocHandle;
/*     */     
/*     */     Channel channel() {
/* 378 */       return (Channel)AbstractKQueueChannel.this;
/*     */     }
/*     */ 
/*     */     
/*     */     public int ident() {
/* 383 */       return AbstractKQueueChannel.this.fd().intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 388 */       close(voidPromise());
/*     */     }
/*     */ 
/*     */     
/*     */     public void handle(IoRegistration registration, IoEvent event) {
/* 393 */       KQueueIoEvent kqueueEvent = (KQueueIoEvent)event;
/* 394 */       short filter = kqueueEvent.filter();
/* 395 */       short flags = kqueueEvent.flags();
/* 396 */       int fflags = kqueueEvent.fflags();
/* 397 */       long data = kqueueEvent.data();
/*     */ 
/*     */ 
/*     */       
/* 401 */       if (filter == Native.EVFILT_WRITE) {
/* 402 */         writeReady();
/* 403 */       } else if (filter == Native.EVFILT_READ) {
/*     */         
/* 405 */         KQueueRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
/* 406 */         readReady(allocHandle);
/* 407 */       } else if (filter == Native.EVFILT_SOCK && (fflags & Native.NOTE_RDHUP) != 0) {
/* 408 */         readEOF();
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 415 */       if ((flags & Native.EV_EOF) != 0) {
/* 416 */         readEOF();
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
/*     */     final boolean shouldStopReading(ChannelConfig config) {
/* 429 */       return (!this.readPending && !config.isAutoRead());
/*     */     }
/*     */     
/*     */     final boolean failConnectPromise(Throwable cause) {
/* 433 */       if (AbstractKQueueChannel.this.connectPromise != null) {
/*     */ 
/*     */ 
/*     */         
/* 437 */         ChannelPromise connectPromise = AbstractKQueueChannel.this.connectPromise;
/* 438 */         AbstractKQueueChannel.this.connectPromise = null;
/* 439 */         if (connectPromise.tryFailure((cause instanceof ConnectException) ? cause : (
/* 440 */             new ConnectException("failed to connect")).initCause(cause))) {
/* 441 */           closeIfClosed();
/* 442 */           return true;
/*     */         } 
/*     */       } 
/* 445 */       return false;
/*     */     }
/*     */     
/*     */     private void writeReady() {
/* 449 */       if (AbstractKQueueChannel.this.connectPromise != null) {
/*     */         
/* 451 */         finishConnect();
/* 452 */       } else if (!AbstractKQueueChannel.this.socket.isOutputShutdown()) {
/*     */         
/* 454 */         super.flush0();
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
/*     */     void shutdownInput(boolean readEOF) {
/* 467 */       if (readEOF && AbstractKQueueChannel.this.connectPromise != null) {
/* 468 */         finishConnect();
/*     */       }
/* 470 */       if (!AbstractKQueueChannel.this.socket.isInputShutdown()) {
/* 471 */         if (AbstractKQueueChannel.isAllowHalfClosure((ChannelConfig)AbstractKQueueChannel.this.config())) {
/*     */           try {
/* 473 */             AbstractKQueueChannel.this.socket.shutdown(true, false);
/* 474 */           } catch (IOException ignored) {
/*     */ 
/*     */             
/* 477 */             fireEventAndClose(ChannelInputShutdownEvent.INSTANCE);
/*     */             return;
/* 479 */           } catch (NotYetConnectedException notYetConnectedException) {}
/*     */ 
/*     */ 
/*     */           
/* 483 */           if (shouldStopReading((ChannelConfig)AbstractKQueueChannel.this.config())) {
/* 484 */             clearReadFilter0();
/*     */           }
/* 486 */           AbstractKQueueChannel.this.pipeline().fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */         } else {
/* 488 */           close(voidPromise());
/*     */           return;
/*     */         } 
/*     */       }
/* 492 */       if (!readEOF && !AbstractKQueueChannel.this.inputClosedSeenErrorOnRead) {
/* 493 */         AbstractKQueueChannel.this.inputClosedSeenErrorOnRead = true;
/* 494 */         AbstractKQueueChannel.this.pipeline().fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void readEOF() {
/* 500 */       KQueueRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
/* 501 */       allocHandle.readEOF();
/*     */       
/* 503 */       if (AbstractKQueueChannel.this.isActive()) {
/*     */ 
/*     */ 
/*     */         
/* 507 */         readReady(allocHandle);
/*     */       } else {
/*     */         
/* 510 */         shutdownInput(true);
/*     */       } 
/*     */ 
/*     */       
/* 514 */       AbstractKQueueChannel.this.clearRdHup0();
/*     */     }
/*     */ 
/*     */     
/*     */     public KQueueRecvByteAllocatorHandle recvBufAllocHandle() {
/* 519 */       if (this.allocHandle == null) {
/* 520 */         this
/* 521 */           .allocHandle = new KQueueRecvByteAllocatorHandle((RecvByteBufAllocator.ExtendedHandle)super.recvBufAllocHandle());
/*     */       }
/* 523 */       return this.allocHandle;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final void flush0() {
/* 531 */       if (!AbstractKQueueChannel.this.writeFilterEnabled) {
/* 532 */         super.flush0();
/*     */       }
/*     */     }
/*     */     
/*     */     protected final void clearReadFilter0() {
/* 537 */       assert AbstractKQueueChannel.this.eventLoop().inEventLoop();
/*     */       try {
/* 539 */         this.readPending = false;
/* 540 */         AbstractKQueueChannel.this.readFilter(false);
/* 541 */       } catch (IOException e) {
/*     */ 
/*     */         
/* 544 */         AbstractKQueueChannel.this.pipeline().fireExceptionCaught(e);
/* 545 */         AbstractKQueueChannel.this.unsafe().close(AbstractKQueueChannel.this.unsafe().voidPromise());
/*     */       } 
/*     */     }
/*     */     
/*     */     private void fireEventAndClose(Object evt) {
/* 550 */       AbstractKQueueChannel.this.pipeline().fireUserEventTriggered(evt);
/* 551 */       close(voidPromise());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void connect(final SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 559 */       if (promise.isDone() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 564 */         if (AbstractKQueueChannel.this.connectPromise != null) {
/* 565 */           throw new ConnectionPendingException();
/*     */         }
/*     */         
/* 568 */         boolean wasActive = AbstractKQueueChannel.this.isActive();
/* 569 */         if (AbstractKQueueChannel.this.doConnect(remoteAddress, localAddress)) {
/* 570 */           fulfillConnectPromise(promise, wasActive);
/*     */         } else {
/* 572 */           AbstractKQueueChannel.this.connectPromise = promise;
/* 573 */           AbstractKQueueChannel.this.requestedRemoteAddress = remoteAddress;
/*     */ 
/*     */           
/* 576 */           final int connectTimeoutMillis = AbstractKQueueChannel.this.config().getConnectTimeoutMillis();
/* 577 */           if (connectTimeoutMillis > 0) {
/* 578 */             AbstractKQueueChannel.this.connectTimeoutFuture = (Future<?>)AbstractKQueueChannel.this.eventLoop().schedule(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 581 */                     ChannelPromise connectPromise = AbstractKQueueChannel.this.connectPromise;
/* 582 */                     if (connectPromise != null && !connectPromise.isDone() && connectPromise
/* 583 */                       .tryFailure((Throwable)new ConnectTimeoutException("connection timed out after " + connectTimeoutMillis + " ms: " + remoteAddress)))
/*     */                     {
/*     */                       
/* 586 */                       AbstractKQueueChannel.AbstractKQueueUnsafe.this.close(AbstractKQueueChannel.AbstractKQueueUnsafe.this.voidPromise());
/*     */                     }
/*     */                   }
/*     */                 },  connectTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */           }
/*     */           
/* 592 */           promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 
/*     */                 public void operationComplete(ChannelFuture future)
/*     */                 {
/* 597 */                   if (future.isCancelled()) {
/* 598 */                     if (AbstractKQueueChannel.this.connectTimeoutFuture != null) {
/* 599 */                       AbstractKQueueChannel.this.connectTimeoutFuture.cancel(false);
/*     */                     }
/* 601 */                     AbstractKQueueChannel.this.connectPromise = null;
/* 602 */                     AbstractKQueueChannel.AbstractKQueueUnsafe.this.close(AbstractKQueueChannel.AbstractKQueueUnsafe.this.voidPromise());
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/* 607 */       } catch (Throwable t) {
/* 608 */         closeIfClosed();
/* 609 */         promise.tryFailure(annotateConnectException(t, remoteAddress));
/*     */       } 
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, boolean wasActive) {
/* 614 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */       
/* 618 */       AbstractKQueueChannel.this.active = true;
/*     */ 
/*     */ 
/*     */       
/* 622 */       boolean active = AbstractKQueueChannel.this.isActive();
/*     */ 
/*     */       
/* 625 */       boolean promiseSet = promise.trySuccess();
/*     */ 
/*     */ 
/*     */       
/* 629 */       if (!wasActive && active) {
/* 630 */         AbstractKQueueChannel.this.pipeline().fireChannelActive();
/*     */       }
/*     */ 
/*     */       
/* 634 */       if (!promiseSet) {
/* 635 */         close(voidPromise());
/*     */       }
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise promise, Throwable cause) {
/* 640 */       if (promise == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 646 */       promise.tryFailure(cause);
/* 647 */       closeIfClosed();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void finishConnect() {
/* 654 */       assert AbstractKQueueChannel.this.eventLoop().inEventLoop();
/*     */       
/* 656 */       boolean connectStillInProgress = false;
/*     */       try {
/* 658 */         boolean wasActive = AbstractKQueueChannel.this.isActive();
/* 659 */         if (!doFinishConnect()) {
/* 660 */           connectStillInProgress = true;
/*     */           return;
/*     */         } 
/* 663 */         fulfillConnectPromise(AbstractKQueueChannel.this.connectPromise, wasActive);
/* 664 */       } catch (Throwable t) {
/* 665 */         fulfillConnectPromise(AbstractKQueueChannel.this.connectPromise, annotateConnectException(t, AbstractKQueueChannel.this.requestedRemoteAddress));
/*     */       } finally {
/* 667 */         if (!connectStillInProgress) {
/*     */ 
/*     */           
/* 670 */           if (AbstractKQueueChannel.this.connectTimeoutFuture != null) {
/* 671 */             AbstractKQueueChannel.this.connectTimeoutFuture.cancel(false);
/*     */           }
/* 673 */           AbstractKQueueChannel.this.connectPromise = null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean doFinishConnect() throws Exception {
/* 679 */       if (AbstractKQueueChannel.this.socket.finishConnect()) {
/* 680 */         AbstractKQueueChannel.this.writeFilter(false);
/* 681 */         if (AbstractKQueueChannel.this.requestedRemoteAddress instanceof InetSocketAddress) {
/* 682 */           AbstractKQueueChannel.this.remote = UnixChannelUtil.computeRemoteAddr((InetSocketAddress)AbstractKQueueChannel.this.requestedRemoteAddress, AbstractKQueueChannel.this.socket.remoteAddress());
/*     */         }
/* 684 */         AbstractKQueueChannel.this.requestedRemoteAddress = null;
/* 685 */         return true;
/*     */       } 
/* 687 */       AbstractKQueueChannel.this.writeFilter(true);
/* 688 */       return false;
/*     */     }
/*     */     
/*     */     abstract void readReady(KQueueRecvByteAllocatorHandle param1KQueueRecvByteAllocatorHandle); }
/*     */   
/*     */   protected void doBind(SocketAddress local) throws Exception {
/* 694 */     if (local instanceof InetSocketAddress) {
/* 695 */       checkResolvable((InetSocketAddress)local);
/*     */     }
/* 697 */     this.socket.bind(local);
/* 698 */     this.local = this.socket.localAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 705 */     if (localAddress instanceof InetSocketAddress) {
/* 706 */       checkResolvable((InetSocketAddress)localAddress);
/*     */     }
/*     */ 
/*     */     
/* 710 */     InetSocketAddress remoteSocketAddr = (remoteAddress instanceof InetSocketAddress) ? (InetSocketAddress)remoteAddress : null;
/* 711 */     if (remoteSocketAddr != null) {
/* 712 */       checkResolvable(remoteSocketAddr);
/*     */     }
/*     */     
/* 715 */     if (this.remote != null)
/*     */     {
/*     */ 
/*     */       
/* 719 */       throw new AlreadyConnectedException();
/*     */     }
/*     */     
/* 722 */     if (localAddress != null) {
/* 723 */       this.socket.bind(localAddress);
/*     */     }
/*     */     
/* 726 */     boolean connected = doConnect0(remoteAddress, localAddress);
/* 727 */     if (connected) {
/* 728 */       this
/* 729 */         .remote = (remoteSocketAddr == null) ? remoteAddress : UnixChannelUtil.computeRemoteAddr(remoteSocketAddr, this.socket.remoteAddress());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 734 */     this.local = this.socket.localAddress();
/* 735 */     return connected;
/*     */   }
/*     */   
/*     */   protected boolean doConnect0(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 739 */     boolean success = false;
/*     */     try {
/* 741 */       boolean connected = this.socket.connect(remoteAddress);
/* 742 */       if (!connected) {
/* 743 */         writeFilter(true);
/*     */       }
/* 745 */       success = true;
/* 746 */       return connected;
/*     */     } finally {
/* 748 */       if (!success) {
/* 749 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 756 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 761 */     return this.remote;
/*     */   }
/*     */   
/*     */   protected abstract AbstractKQueueUnsafe newUnsafe();
/*     */   
/*     */   public abstract KQueueChannelConfig config();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\AbstractKQueueChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */