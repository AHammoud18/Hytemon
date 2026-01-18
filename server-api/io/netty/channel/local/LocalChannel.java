/*     */ package io.netty.channel.local;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.IoEvent;
/*     */ import io.netty.channel.IoEventLoop;
/*     */ import io.netty.channel.IoRegistration;
/*     */ import io.netty.channel.PreferHeapByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.SingleThreadEventExecutor;
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.ConnectException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.AlreadyConnectedException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalChannel
/*     */   extends AbstractChannel
/*     */ {
/*  55 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(LocalChannel.class);
/*     */ 
/*     */   
/*  58 */   private static final AtomicReferenceFieldUpdater<LocalChannel, Future> FINISH_READ_FUTURE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(LocalChannel.class, Future.class, "finishReadFuture");
/*  59 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   private static final int MAX_READER_STACK_DEPTH = 8;
/*     */   
/*  62 */   private enum State { OPEN, BOUND, CONNECTED, CLOSED; }
/*     */   
/*  64 */   private final ChannelConfig config = (ChannelConfig)new DefaultChannelConfig((Channel)this);
/*     */   
/*  66 */   final Queue<Object> inboundBuffer = PlatformDependent.newSpscQueue();
/*  67 */   private final Runnable readTask = new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  71 */         if (!LocalChannel.this.inboundBuffer.isEmpty()) {
/*  72 */           LocalChannel.this.readInbound();
/*     */         }
/*     */       }
/*     */     };
/*     */   
/*  77 */   private final Runnable shutdownHook = new Runnable()
/*     */     {
/*     */       public void run() {
/*  80 */         LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidPromise());
/*     */       }
/*     */     };
/*     */   
/*  84 */   private final Runnable finishReadTask = new Runnable()
/*     */     {
/*     */       public void run() {
/*  87 */         LocalChannel.this.finishPeerRead0(LocalChannel.this);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private IoRegistration registration;
/*     */   private volatile State state;
/*     */   private volatile LocalChannel peer;
/*     */   private volatile LocalAddress localAddress;
/*     */   private volatile LocalAddress remoteAddress;
/*     */   private volatile ChannelPromise connectPromise;
/*     */   private volatile boolean readInProgress;
/*     */   private volatile boolean writeInProgress;
/*     */   private volatile Future<?> finishReadFuture;
/*     */   
/*     */   public LocalChannel() {
/* 103 */     super(null);
/* 104 */     config().setAllocator((ByteBufAllocator)new PreferHeapByteBufAllocator(this.config.getAllocator()));
/*     */   }
/*     */   
/*     */   protected LocalChannel(LocalServerChannel parent, LocalChannel peer) {
/* 108 */     super((Channel)parent);
/* 109 */     config().setAllocator((ByteBufAllocator)new PreferHeapByteBufAllocator(this.config.getAllocator()));
/* 110 */     this.peer = peer;
/* 111 */     this.localAddress = parent.localAddress();
/* 112 */     this.remoteAddress = peer.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 117 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig config() {
/* 122 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalServerChannel parent() {
/* 127 */     return (LocalServerChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress localAddress() {
/* 132 */     return (LocalAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalAddress remoteAddress() {
/* 137 */     return (LocalAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 142 */     return (this.state != State.CLOSED);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 147 */     return (this.state == State.CONNECTED);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/* 152 */     return new LocalUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/* 157 */     return (loop instanceof io.netty.channel.SingleThreadEventLoop || (loop instanceof IoEventLoop && ((IoEventLoop)loop)
/* 158 */       .isCompatible(LocalUnsafe.class)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 163 */     return this.localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 168 */     return this.remoteAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister(ChannelPromise promise) {
/* 173 */     EventLoop loop = eventLoop();
/* 174 */     if (loop instanceof IoEventLoop) {
/* 175 */       assert this.registration == null;
/* 176 */       ((IoEventLoop)loop).register((LocalUnsafe)unsafe()).addListener(f -> {
/*     */             if (f.isSuccess()) {
/*     */               this.registration = (IoRegistration)f.getNow();
/*     */               promise.setSuccess();
/*     */             } else {
/*     */               promise.setFailure(f.cause());
/*     */             } 
/*     */           });
/*     */     } else {
/*     */       try {
/* 186 */         ((LocalUnsafe)unsafe()).registered();
/* 187 */       } catch (Throwable cause) {
/* 188 */         promise.setFailure(cause);
/*     */       } 
/* 190 */       promise.setSuccess();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDeregister() throws Exception {
/* 196 */     EventLoop loop = eventLoop();
/* 197 */     if (loop instanceof IoEventLoop) {
/* 198 */       IoRegistration registration = this.registration;
/* 199 */       if (registration != null) {
/* 200 */         this.registration = null;
/* 201 */         registration.cancel();
/*     */       } 
/*     */     } else {
/* 204 */       ((LocalUnsafe)unsafe()).unregistered();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 210 */     this
/* 211 */       .localAddress = LocalChannelRegistry.register((Channel)this, this.localAddress, localAddress);
/*     */     
/* 213 */     this.state = State.BOUND;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 218 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 223 */     final LocalChannel peer = this.peer;
/* 224 */     State oldState = this.state;
/*     */     try {
/* 226 */       if (oldState != State.CLOSED) {
/*     */         
/* 228 */         if (this.localAddress != null) {
/* 229 */           if (parent() == null) {
/* 230 */             LocalChannelRegistry.unregister(this.localAddress);
/*     */           }
/* 232 */           this.localAddress = null;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 237 */         this.state = State.CLOSED;
/*     */ 
/*     */         
/* 240 */         if (this.writeInProgress && peer != null) {
/* 241 */           finishPeerRead(peer);
/*     */         }
/*     */         
/* 244 */         ChannelPromise promise = this.connectPromise;
/* 245 */         if (promise != null) {
/*     */           
/* 247 */           promise.tryFailure(new ClosedChannelException());
/* 248 */           this.connectPromise = null;
/*     */         } 
/*     */       } 
/*     */       
/* 252 */       if (peer != null) {
/* 253 */         this.peer = null;
/*     */ 
/*     */ 
/*     */         
/* 257 */         EventLoop peerEventLoop = peer.eventLoop();
/* 258 */         final boolean peerIsActive = peer.isActive();
/*     */         try {
/* 260 */           peerEventLoop.execute(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 263 */                   peer.tryClose(peerIsActive);
/*     */                 }
/*     */               });
/* 266 */         } catch (Throwable cause) {
/* 267 */           logger.warn("Releasing Inbound Queues for channels {}-{} because exception occurred!", new Object[] { this, peer, cause });
/*     */           
/* 269 */           if (peerEventLoop.inEventLoop()) {
/* 270 */             peer.releaseInboundBuffers();
/*     */           }
/*     */           else {
/*     */             
/* 274 */             peer.close();
/*     */           } 
/* 276 */           PlatformDependent.throwException(cause);
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 281 */       if (oldState != null && oldState != State.CLOSED)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 286 */         releaseInboundBuffers();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tryClose(boolean isActive) {
/* 292 */     if (isActive) {
/* 293 */       unsafe().close(unsafe().voidPromise());
/*     */     } else {
/* 295 */       releaseInboundBuffers();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readInbound() {
/* 300 */     RecvByteBufAllocator.Handle handle = unsafe().recvBufAllocHandle();
/* 301 */     handle.reset(config());
/* 302 */     ChannelPipeline pipeline = pipeline();
/*     */     do {
/* 304 */       Object received = this.inboundBuffer.poll();
/* 305 */       if (received == null) {
/*     */         break;
/*     */       }
/* 308 */       if (received instanceof ByteBuf && this.inboundBuffer.peek() instanceof ByteBuf) {
/* 309 */         ByteBuf msg = (ByteBuf)received;
/* 310 */         ByteBuf output = handle.allocate(alloc());
/* 311 */         if (msg.readableBytes() < output.writableBytes()) {
/*     */           
/* 313 */           output.writeBytes(msg, msg.readerIndex(), msg.readableBytes());
/* 314 */           msg.release();
/* 315 */           while (received = this.inboundBuffer.peek() instanceof ByteBuf && ((ByteBuf)received)
/* 316 */             .readableBytes() < output.writableBytes()) {
/* 317 */             this.inboundBuffer.poll();
/* 318 */             msg = (ByteBuf)received;
/* 319 */             output.writeBytes(msg, msg.readerIndex(), msg.readableBytes());
/* 320 */             msg.release();
/*     */           } 
/* 322 */           handle.lastBytesRead(output.readableBytes());
/* 323 */           received = output;
/*     */         } else {
/*     */           
/* 326 */           handle.lastBytesRead(output.capacity());
/* 327 */           output.release();
/*     */         } 
/*     */       } 
/* 330 */       handle.incMessagesRead(1);
/* 331 */       pipeline.fireChannelRead(received);
/* 332 */     } while (handle.continueReading());
/* 333 */     handle.readComplete();
/* 334 */     pipeline.fireChannelReadComplete();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 339 */     if (this.readInProgress) {
/*     */       return;
/*     */     }
/*     */     
/* 343 */     Queue<Object> inboundBuffer = this.inboundBuffer;
/* 344 */     if (inboundBuffer.isEmpty()) {
/* 345 */       this.readInProgress = true;
/*     */       
/*     */       return;
/*     */     } 
/* 349 */     InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
/* 350 */     int stackDepth = threadLocals.localChannelReaderStackDepth();
/* 351 */     if (stackDepth < 8) {
/* 352 */       threadLocals.setLocalChannelReaderStackDepth(stackDepth + 1);
/*     */       try {
/* 354 */         readInbound();
/*     */       } finally {
/* 356 */         threadLocals.setLocalChannelReaderStackDepth(stackDepth);
/*     */       } 
/*     */     } else {
/*     */       try {
/* 360 */         eventLoop().execute(this.readTask);
/* 361 */       } catch (Throwable cause) {
/* 362 */         logger.warn("Closing Local channels {}-{} because exception occurred!", new Object[] { this, this.peer, cause });
/* 363 */         close();
/* 364 */         this.peer.close();
/* 365 */         PlatformDependent.throwException(cause);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 372 */     switch (this.state) {
/*     */       case OPEN:
/*     */       case BOUND:
/* 375 */         throw new NotYetConnectedException();
/*     */       case CLOSED:
/* 377 */         throw new ClosedChannelException();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 382 */     LocalChannel peer = this.peer;
/*     */     
/* 384 */     this.writeInProgress = true;
/*     */     try {
/* 386 */       ClosedChannelException exception = null;
/*     */       while (true) {
/* 388 */         Object msg = in.current();
/* 389 */         if (msg == null) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 395 */           if (peer.state == State.CONNECTED) {
/* 396 */             peer.inboundBuffer.add(ReferenceCountUtil.retain(msg));
/* 397 */             in.remove(); continue;
/*     */           } 
/* 399 */           if (exception == null) {
/* 400 */             exception = new ClosedChannelException();
/*     */           }
/* 402 */           in.remove(exception);
/*     */         }
/* 404 */         catch (Throwable cause) {
/* 405 */           in.remove(cause);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 414 */       this.writeInProgress = false;
/*     */     } 
/*     */     
/* 417 */     finishPeerRead(peer);
/*     */   }
/*     */ 
/*     */   
/*     */   private void finishPeerRead(LocalChannel peer) {
/* 422 */     if (peer.eventLoop() == eventLoop() && !peer.writeInProgress) {
/* 423 */       finishPeerRead0(peer);
/*     */     } else {
/* 425 */       runFinishPeerReadTask(peer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void runFinishTask0() {
/* 432 */     if (this.writeInProgress) {
/* 433 */       this.finishReadFuture = eventLoop().submit(this.finishReadTask);
/*     */     } else {
/* 435 */       eventLoop().execute(this.finishReadTask);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runFinishPeerReadTask(LocalChannel peer) {
/*     */     try {
/* 441 */       peer.runFinishTask0();
/* 442 */     } catch (Throwable cause) {
/* 443 */       logger.warn("Closing Local channels {}-{} because exception occurred!", new Object[] { this, peer, cause });
/* 444 */       close();
/* 445 */       peer.close();
/* 446 */       PlatformDependent.throwException(cause);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void releaseInboundBuffers() {
/* 451 */     assert eventLoop() == null || eventLoop().inEventLoop();
/* 452 */     this.readInProgress = false;
/* 453 */     Queue<Object> inboundBuffer = this.inboundBuffer;
/*     */     Object msg;
/* 455 */     while ((msg = inboundBuffer.poll()) != null) {
/* 456 */       ReferenceCountUtil.release(msg);
/*     */     }
/*     */   }
/*     */   
/*     */   private void finishPeerRead0(LocalChannel peer) {
/* 461 */     Future<?> peerFinishReadFuture = peer.finishReadFuture;
/* 462 */     if (peerFinishReadFuture != null) {
/* 463 */       if (!peerFinishReadFuture.isDone()) {
/* 464 */         runFinishPeerReadTask(peer);
/*     */         
/*     */         return;
/*     */       } 
/* 468 */       FINISH_READ_FUTURE_UPDATER.compareAndSet(peer, peerFinishReadFuture, null);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 473 */     if (peer.readInProgress && !peer.inboundBuffer.isEmpty()) {
/* 474 */       peer.readInProgress = false;
/* 475 */       peer.readInbound();
/*     */     } 
/*     */   }
/*     */   private class LocalUnsafe extends AbstractChannel.AbstractUnsafe implements LocalIoHandle { private LocalUnsafe() {
/* 479 */       super(LocalChannel.this);
/*     */     }
/*     */     
/*     */     public void close() {
/* 483 */       close(voidPromise());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handle(IoRegistration registration, IoEvent event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void registered() {
/* 498 */       if (LocalChannel.this.peer != null && LocalChannel.this.parent() != null) {
/*     */ 
/*     */         
/* 501 */         final LocalChannel peer = LocalChannel.this.peer;
/* 502 */         LocalChannel.this.state = LocalChannel.State.CONNECTED;
/*     */         
/* 504 */         peer.remoteAddress = (LocalChannel.this.parent() == null) ? null : LocalChannel.this.parent().localAddress();
/* 505 */         peer.state = LocalChannel.State.CONNECTED;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 511 */         peer.eventLoop().execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 514 */                 ChannelPromise promise = peer.connectPromise;
/*     */ 
/*     */ 
/*     */                 
/* 518 */                 if (promise != null && promise.trySuccess()) {
/* 519 */                   peer.pipeline().fireChannelActive();
/*     */                 }
/*     */               }
/*     */             });
/*     */       } 
/* 524 */       ((SingleThreadEventExecutor)LocalChannel.this.eventLoop()).addShutdownHook(LocalChannel.this.shutdownHook);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void unregistered() {
/* 530 */       ((SingleThreadEventExecutor)LocalChannel.this.eventLoop()).removeShutdownHook(LocalChannel.this.shutdownHook);
/*     */     }
/*     */ 
/*     */     
/*     */     public void closeNow() {
/* 535 */       close(voidPromise());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 541 */       if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/* 545 */       if (LocalChannel.this.state == LocalChannel.State.CONNECTED) {
/* 546 */         Exception cause = new AlreadyConnectedException();
/* 547 */         safeSetFailure(promise, cause);
/*     */         
/*     */         return;
/*     */       } 
/* 551 */       if (LocalChannel.this.connectPromise != null) {
/* 552 */         throw new ConnectionPendingException();
/*     */       }
/*     */       
/* 555 */       LocalChannel.this.connectPromise = promise;
/*     */       
/* 557 */       if (LocalChannel.this.state != LocalChannel.State.BOUND)
/*     */       {
/* 559 */         if (localAddress == null) {
/* 560 */           localAddress = new LocalAddress((Channel)LocalChannel.this);
/*     */         }
/*     */       }
/*     */       
/* 564 */       if (localAddress != null) {
/*     */         try {
/* 566 */           LocalChannel.this.doBind(localAddress);
/* 567 */         } catch (Throwable t) {
/* 568 */           safeSetFailure(promise, t);
/* 569 */           close(voidPromise());
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/* 574 */       Channel boundChannel = LocalChannelRegistry.get(remoteAddress);
/* 575 */       if (!(boundChannel instanceof LocalServerChannel)) {
/* 576 */         Exception cause = new ConnectException("connection refused: " + remoteAddress);
/* 577 */         safeSetFailure(promise, cause);
/* 578 */         close(voidPromise());
/*     */         
/*     */         return;
/*     */       } 
/* 582 */       LocalServerChannel serverChannel = (LocalServerChannel)boundChannel;
/* 583 */       LocalChannel.this.peer = serverChannel.serve(LocalChannel.this);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\local\LocalChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */