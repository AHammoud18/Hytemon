/*      */ package io.netty.channel;
/*      */ 
/*      */ import io.netty.channel.socket.ChannelOutputShutdownEvent;
/*      */ import io.netty.channel.socket.ChannelOutputShutdownException;
/*      */ import io.netty.util.DefaultAttributeMap;
/*      */ import io.netty.util.ReferenceCountUtil;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.Promise;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.io.IOException;
/*      */ import java.net.ConnectException;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.NoRouteToHostException;
/*      */ import java.net.SocketAddress;
/*      */ import java.net.SocketException;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.NotYetConnectedException;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.RejectedExecutionException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractChannel
/*      */   extends DefaultAttributeMap
/*      */   implements Channel
/*      */ {
/*   43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
/*      */   
/*      */   private final Channel parent;
/*      */   private final ChannelId id;
/*      */   private final Channel.Unsafe unsafe;
/*      */   private final DefaultChannelPipeline pipeline;
/*   49 */   private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);
/*   50 */   private final CloseFuture closeFuture = new CloseFuture(this);
/*      */   
/*      */   private volatile SocketAddress localAddress;
/*      */   
/*      */   private volatile SocketAddress remoteAddress;
/*      */   
/*      */   private volatile EventLoop eventLoop;
/*      */   
/*      */   private volatile boolean registered;
/*      */   
/*      */   private boolean closeInitiated;
/*      */   
/*      */   private Throwable initialCloseCause;
/*      */   
/*      */   private boolean strValActive;
/*      */   
/*      */   private String strVal;
/*      */ 
/*      */   
/*      */   protected AbstractChannel(Channel parent) {
/*   70 */     this.parent = parent;
/*   71 */     this.id = newId();
/*   72 */     this.unsafe = newUnsafe();
/*   73 */     this.pipeline = newChannelPipeline();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractChannel(Channel parent, ChannelId id) {
/*   83 */     this.parent = parent;
/*   84 */     this.id = id;
/*   85 */     this.unsafe = newUnsafe();
/*   86 */     this.pipeline = newChannelPipeline();
/*      */   }
/*      */   
/*      */   protected final int maxMessagesPerWrite() {
/*   90 */     ChannelConfig config = config();
/*   91 */     if (config instanceof DefaultChannelConfig) {
/*   92 */       return ((DefaultChannelConfig)config).getMaxMessagesPerWrite();
/*      */     }
/*   94 */     Integer value = config.<Integer>getOption(ChannelOption.MAX_MESSAGES_PER_WRITE);
/*   95 */     if (value == null) {
/*   96 */       return Integer.MAX_VALUE;
/*      */     }
/*   98 */     return value.intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelId id() {
/*  103 */     return this.id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ChannelId newId() {
/*  111 */     return DefaultChannelId.newInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DefaultChannelPipeline newChannelPipeline() {
/*  118 */     return new DefaultChannelPipeline(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public Channel parent() {
/*  123 */     return this.parent;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline pipeline() {
/*  128 */     return this.pipeline;
/*      */   }
/*      */ 
/*      */   
/*      */   public EventLoop eventLoop() {
/*  133 */     EventLoop eventLoop = this.eventLoop;
/*  134 */     if (eventLoop == null) {
/*  135 */       throw new IllegalStateException("channel not registered to an event loop");
/*      */     }
/*  137 */     return eventLoop;
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress localAddress() {
/*  142 */     SocketAddress localAddress = this.localAddress;
/*  143 */     if (localAddress == null) {
/*      */       try {
/*  145 */         this.localAddress = localAddress = unsafe().localAddress();
/*  146 */       } catch (Error e) {
/*  147 */         throw e;
/*  148 */       } catch (Throwable t) {
/*      */         
/*  150 */         return null;
/*      */       } 
/*      */     }
/*  153 */     return localAddress;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void invalidateLocalAddress() {
/*  161 */     this.localAddress = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public SocketAddress remoteAddress() {
/*  166 */     SocketAddress remoteAddress = this.remoteAddress;
/*  167 */     if (remoteAddress == null) {
/*      */       try {
/*  169 */         this.remoteAddress = remoteAddress = unsafe().remoteAddress();
/*  170 */       } catch (Error e) {
/*  171 */         throw e;
/*  172 */       } catch (Throwable t) {
/*      */         
/*  174 */         return null;
/*      */       } 
/*      */     }
/*  177 */     return remoteAddress;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void invalidateRemoteAddress() {
/*  185 */     this.remoteAddress = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRegistered() {
/*  190 */     return this.registered;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture closeFuture() {
/*  195 */     return this.closeFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   public Channel.Unsafe unsafe() {
/*  200 */     return this.unsafe;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int hashCode() {
/*  213 */     return this.id.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean equals(Object o) {
/*  222 */     return (this == o);
/*      */   }
/*      */ 
/*      */   
/*      */   public final int compareTo(Channel o) {
/*  227 */     if (this == o) {
/*  228 */       return 0;
/*      */     }
/*      */     
/*  231 */     return id().compareTo(o.id());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  242 */     boolean active = isActive();
/*  243 */     if (this.strValActive == active && this.strVal != null) {
/*  244 */       return this.strVal;
/*      */     }
/*      */     
/*  247 */     SocketAddress remoteAddr = remoteAddress();
/*  248 */     SocketAddress localAddr = localAddress();
/*  249 */     if (remoteAddr != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  258 */       StringBuilder buf = (new StringBuilder(96)).append("[id: 0x").append(this.id.asShortText()).append(", L:").append(localAddr).append(active ? " - " : " ! ").append("R:").append(remoteAddr).append(']');
/*  259 */       this.strVal = buf.toString();
/*  260 */     } else if (localAddr != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  266 */       StringBuilder buf = (new StringBuilder(64)).append("[id: 0x").append(this.id.asShortText()).append(", L:").append(localAddr).append(']');
/*  267 */       this.strVal = buf.toString();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  272 */       StringBuilder buf = (new StringBuilder(16)).append("[id: 0x").append(this.id.asShortText()).append(']');
/*  273 */       this.strVal = buf.toString();
/*      */     } 
/*      */     
/*  276 */     this.strValActive = active;
/*  277 */     return this.strVal;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelPromise voidPromise() {
/*  282 */     return this.pipeline.voidPromise();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract class AbstractUnsafe
/*      */     implements Channel.Unsafe
/*      */   {
/*  290 */     private volatile ChannelOutboundBuffer outboundBuffer = new ChannelOutboundBuffer(AbstractChannel.this);
/*      */     
/*      */     private RecvByteBufAllocator.Handle recvHandle;
/*      */     private boolean inFlush0;
/*      */     private boolean neverRegistered = true;
/*      */     
/*      */     private void assertEventLoop() {
/*  297 */       assert !AbstractChannel.this.registered || AbstractChannel.this.eventLoop.inEventLoop();
/*      */     }
/*      */ 
/*      */     
/*      */     public RecvByteBufAllocator.Handle recvBufAllocHandle() {
/*  302 */       if (this.recvHandle == null) {
/*  303 */         this.recvHandle = AbstractChannel.this.config().<RecvByteBufAllocator>getRecvByteBufAllocator().newHandle();
/*      */       }
/*  305 */       return this.recvHandle;
/*      */     }
/*      */ 
/*      */     
/*      */     public final ChannelOutboundBuffer outboundBuffer() {
/*  310 */       return this.outboundBuffer;
/*      */     }
/*      */ 
/*      */     
/*      */     public final SocketAddress localAddress() {
/*  315 */       return AbstractChannel.this.localAddress0();
/*      */     }
/*      */ 
/*      */     
/*      */     public final SocketAddress remoteAddress() {
/*  320 */       return AbstractChannel.this.remoteAddress0();
/*      */     }
/*      */ 
/*      */     
/*      */     public final void register(EventLoop eventLoop, final ChannelPromise promise) {
/*  325 */       ObjectUtil.checkNotNull(eventLoop, "eventLoop");
/*  326 */       if (AbstractChannel.this.isRegistered()) {
/*  327 */         promise.setFailure(new IllegalStateException("registered to an event loop already"));
/*      */         return;
/*      */       } 
/*  330 */       if (!AbstractChannel.this.isCompatible(eventLoop)) {
/*  331 */         promise.setFailure(new IllegalStateException("incompatible event loop type: " + eventLoop
/*  332 */               .getClass().getName()));
/*      */         
/*      */         return;
/*      */       } 
/*  336 */       AbstractChannel.this.eventLoop = eventLoop;
/*      */ 
/*      */       
/*  339 */       AbstractChannelHandlerContext context = AbstractChannel.this.pipeline.tail;
/*      */       do {
/*  341 */         context.contextExecutor = null;
/*  342 */         context = context.prev;
/*  343 */       } while (context != null);
/*      */       
/*  345 */       if (eventLoop.inEventLoop()) {
/*  346 */         register0(promise);
/*      */       } else {
/*      */         try {
/*  349 */           eventLoop.execute(new Runnable()
/*      */               {
/*      */                 public void run() {
/*  352 */                   AbstractChannel.AbstractUnsafe.this.register0(promise);
/*      */                 }
/*      */               });
/*  355 */         } catch (Throwable t) {
/*  356 */           AbstractChannel.logger.warn("Force-closing a channel whose registration task was not accepted by an event loop: {}", AbstractChannel.this, t);
/*      */ 
/*      */           
/*  359 */           closeForcibly();
/*  360 */           AbstractChannel.this.closeFuture.setClosed();
/*  361 */           safeSetFailure(promise, t);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void register0(final ChannelPromise promise) {
/*  369 */       if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*      */         return;
/*      */       }
/*  372 */       ChannelPromise registerPromise = AbstractChannel.this.newPromise();
/*  373 */       final boolean firstRegistration = this.neverRegistered;
/*  374 */       registerPromise.addListener(new ChannelFutureListener()
/*      */           {
/*      */             public void operationComplete(ChannelFuture future) throws Exception {
/*  377 */               if (future.isSuccess()) {
/*  378 */                 AbstractChannel.AbstractUnsafe.this.neverRegistered = false;
/*  379 */                 AbstractChannel.this.registered = true;
/*      */ 
/*      */ 
/*      */                 
/*  383 */                 AbstractChannel.this.pipeline.invokeHandlerAddedIfNeeded();
/*      */                 
/*  385 */                 AbstractChannel.AbstractUnsafe.this.safeSetSuccess(promise);
/*  386 */                 AbstractChannel.this.pipeline.fireChannelRegistered();
/*      */ 
/*      */                 
/*  389 */                 if (AbstractChannel.this.isActive()) {
/*  390 */                   if (firstRegistration) {
/*  391 */                     AbstractChannel.this.pipeline.fireChannelActive();
/*  392 */                   } else if (AbstractChannel.this.config().isAutoRead()) {
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  397 */                     AbstractChannel.AbstractUnsafe.this.beginRead();
/*      */                   } 
/*      */                 }
/*      */               } else {
/*      */                 
/*  402 */                 AbstractChannel.AbstractUnsafe.this.closeForcibly();
/*  403 */                 AbstractChannel.this.closeFuture.setClosed();
/*  404 */                 AbstractChannel.AbstractUnsafe.this.safeSetFailure(promise, future.cause());
/*      */               } 
/*      */             }
/*      */           });
/*  408 */       AbstractChannel.this.doRegister(registerPromise);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void bind(SocketAddress localAddress, ChannelPromise promise) {
/*  413 */       assertEventLoop();
/*      */       
/*  415 */       if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  420 */       if (Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST)) && localAddress instanceof InetSocketAddress && 
/*      */         
/*  422 */         !((InetSocketAddress)localAddress).getAddress().isAnyLocalAddress() && 
/*  423 */         !PlatformDependent.isWindows() && !PlatformDependent.maybeSuperUser())
/*      */       {
/*      */         
/*  426 */         AbstractChannel.logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address (" + localAddress + ") anyway as requested.");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  432 */       boolean wasActive = AbstractChannel.this.isActive();
/*      */       try {
/*  434 */         AbstractChannel.this.doBind(localAddress);
/*  435 */       } catch (Throwable t) {
/*  436 */         safeSetFailure(promise, t);
/*  437 */         closeIfClosed();
/*      */         
/*      */         return;
/*      */       } 
/*  441 */       if (!wasActive && AbstractChannel.this.isActive()) {
/*  442 */         invokeLater(new Runnable()
/*      */             {
/*      */               public void run() {
/*  445 */                 AbstractChannel.this.pipeline.fireChannelActive();
/*      */               }
/*      */             });
/*      */       }
/*      */       
/*  450 */       safeSetSuccess(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void disconnect(ChannelPromise promise) {
/*  455 */       assertEventLoop();
/*      */       
/*  457 */       if (!promise.setUncancellable()) {
/*      */         return;
/*      */       }
/*      */       
/*  461 */       boolean wasActive = AbstractChannel.this.isActive();
/*      */       try {
/*  463 */         AbstractChannel.this.doDisconnect();
/*      */         
/*  465 */         AbstractChannel.this.remoteAddress = null;
/*  466 */         AbstractChannel.this.localAddress = null;
/*  467 */       } catch (Throwable t) {
/*  468 */         safeSetFailure(promise, t);
/*  469 */         closeIfClosed();
/*      */         
/*      */         return;
/*      */       } 
/*  473 */       if (wasActive && !AbstractChannel.this.isActive()) {
/*  474 */         invokeLater(new Runnable()
/*      */             {
/*      */               public void run() {
/*  477 */                 AbstractChannel.this.pipeline.fireChannelInactive();
/*      */               }
/*      */             });
/*      */       }
/*      */       
/*  482 */       safeSetSuccess(promise);
/*  483 */       closeIfClosed();
/*      */     }
/*      */ 
/*      */     
/*      */     public void close(ChannelPromise promise) {
/*  488 */       assertEventLoop();
/*      */ 
/*      */       
/*  491 */       ClosedChannelException closedChannelException = StacklessClosedChannelException.newInstance(AbstractChannel.class, "close(ChannelPromise)");
/*  492 */       close(promise, closedChannelException, closedChannelException);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void shutdownOutput(ChannelPromise promise) {
/*  500 */       assertEventLoop();
/*  501 */       shutdownOutput(promise, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void shutdownOutput(ChannelPromise promise, Throwable cause) {
/*  510 */       if (!promise.setUncancellable()) {
/*      */         return;
/*      */       }
/*      */       
/*  514 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/*  515 */       if (outboundBuffer == null) {
/*  516 */         promise.setFailure(new ClosedChannelException());
/*      */         return;
/*      */       } 
/*  519 */       this.outboundBuffer = null;
/*      */ 
/*      */ 
/*      */       
/*  523 */       ChannelOutputShutdownException channelOutputShutdownException = (cause == null) ? new ChannelOutputShutdownException("Channel output shutdown") : new ChannelOutputShutdownException("Channel output shutdown", cause);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  532 */         AbstractChannel.this.doShutdownOutput();
/*  533 */         promise.setSuccess();
/*  534 */       } catch (Throwable err) {
/*  535 */         promise.setFailure(err);
/*      */       } finally {
/*  537 */         closeOutboundBufferForShutdown(AbstractChannel.this.pipeline, outboundBuffer, (Throwable)channelOutputShutdownException);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void closeOutboundBufferForShutdown(ChannelPipeline pipeline, ChannelOutboundBuffer buffer, Throwable cause) {
/*  543 */       buffer.failFlushed(cause, false);
/*  544 */       buffer.close(cause, true);
/*  545 */       pipeline.fireUserEventTriggered(ChannelOutputShutdownEvent.INSTANCE);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void close(final ChannelPromise promise, final Throwable cause, final ClosedChannelException closeCause) {
/*  550 */       if (!promise.setUncancellable()) {
/*      */         return;
/*      */       }
/*      */       
/*  554 */       if (AbstractChannel.this.closeInitiated) {
/*  555 */         if (AbstractChannel.this.closeFuture.isDone()) {
/*      */           
/*  557 */           safeSetSuccess(promise);
/*  558 */         } else if (!(promise instanceof VoidChannelPromise)) {
/*      */           
/*  560 */           AbstractChannel.this.closeFuture.addListener(new ChannelFutureListener()
/*      */               {
/*      */                 public void operationComplete(ChannelFuture future) throws Exception {
/*  563 */                   promise.setSuccess();
/*      */                 }
/*      */               });
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*  570 */       AbstractChannel.this.closeInitiated = true;
/*      */       
/*  572 */       final boolean wasActive = AbstractChannel.this.isActive();
/*  573 */       final ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/*  574 */       this.outboundBuffer = null;
/*  575 */       Executor closeExecutor = prepareToClose();
/*  576 */       if (closeExecutor != null) {
/*  577 */         closeExecutor.execute(new Runnable()
/*      */             {
/*      */               public void run()
/*      */               {
/*      */                 try {
/*  582 */                   AbstractChannel.AbstractUnsafe.this.doClose0(promise);
/*      */                 } finally {
/*      */                   
/*  585 */                   AbstractChannel.AbstractUnsafe.this.invokeLater(new Runnable()
/*      */                       {
/*      */                         public void run() {
/*  588 */                           if (outboundBuffer != null) {
/*      */                             
/*  590 */                             outboundBuffer.failFlushed(cause, false);
/*  591 */                             outboundBuffer.close(closeCause);
/*      */                           } 
/*  593 */                           AbstractChannel.AbstractUnsafe.this.fireChannelInactiveAndDeregister(wasActive);
/*      */                         }
/*      */                       });
/*      */                 } 
/*      */               }
/*      */             });
/*      */       } else {
/*      */         
/*      */         try {
/*  602 */           doClose0(promise);
/*      */         } finally {
/*  604 */           if (outboundBuffer != null) {
/*      */             
/*  606 */             outboundBuffer.failFlushed(cause, false);
/*  607 */             outboundBuffer.close(closeCause);
/*      */           } 
/*      */         } 
/*  610 */         if (this.inFlush0) {
/*  611 */           invokeLater(new Runnable()
/*      */               {
/*      */                 public void run() {
/*  614 */                   AbstractChannel.AbstractUnsafe.this.fireChannelInactiveAndDeregister(wasActive);
/*      */                 }
/*      */               });
/*      */         } else {
/*  618 */           fireChannelInactiveAndDeregister(wasActive);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void doClose0(ChannelPromise promise) {
/*      */       try {
/*  625 */         AbstractChannel.this.doClose();
/*  626 */         AbstractChannel.this.closeFuture.setClosed();
/*  627 */         safeSetSuccess(promise);
/*  628 */       } catch (Throwable t) {
/*  629 */         AbstractChannel.this.closeFuture.setClosed();
/*  630 */         safeSetFailure(promise, t);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void fireChannelInactiveAndDeregister(boolean wasActive) {
/*  635 */       deregister(voidPromise(), (wasActive && !AbstractChannel.this.isActive()));
/*      */     }
/*      */ 
/*      */     
/*      */     public final void closeForcibly() {
/*  640 */       assertEventLoop();
/*      */       
/*      */       try {
/*  643 */         AbstractChannel.this.doClose();
/*  644 */       } catch (Exception e) {
/*  645 */         AbstractChannel.logger.warn("Failed to close a channel.", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public final void deregister(ChannelPromise promise) {
/*  651 */       assertEventLoop();
/*      */       
/*  653 */       deregister(promise, false);
/*      */     }
/*      */     
/*      */     private void deregister(final ChannelPromise promise, final boolean fireChannelInactive) {
/*  657 */       if (!promise.setUncancellable()) {
/*      */         return;
/*      */       }
/*      */       
/*  661 */       if (!AbstractChannel.this.registered) {
/*  662 */         safeSetSuccess(promise);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  675 */       invokeLater(new Runnable()
/*      */           {
/*      */             public void run() {
/*      */               try {
/*  679 */                 AbstractChannel.this.doDeregister();
/*  680 */               } catch (Throwable t) {
/*  681 */                 AbstractChannel.logger.warn("Unexpected exception occurred while deregistering a channel.", t);
/*      */               } finally {
/*  683 */                 if (fireChannelInactive) {
/*  684 */                   AbstractChannel.this.pipeline.fireChannelInactive();
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  690 */                 if (AbstractChannel.this.registered) {
/*  691 */                   AbstractChannel.this.registered = false;
/*  692 */                   AbstractChannel.this.pipeline.fireChannelUnregistered();
/*      */                 } 
/*  694 */                 AbstractChannel.AbstractUnsafe.this.safeSetSuccess(promise);
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     public final void beginRead() {
/*  702 */       assertEventLoop();
/*      */       
/*      */       try {
/*  705 */         AbstractChannel.this.doBeginRead();
/*  706 */       } catch (Exception e) {
/*  707 */         invokeLater(new Runnable()
/*      */             {
/*      */               public void run() {
/*  710 */                 AbstractChannel.this.pipeline.fireExceptionCaught(e);
/*      */               }
/*      */             });
/*  713 */         close(voidPromise());
/*      */       } 
/*      */     }
/*      */     
/*      */     public final void write(Object msg, ChannelPromise promise) {
/*      */       int size;
/*  719 */       assertEventLoop();
/*      */       
/*  721 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/*  722 */       if (outboundBuffer == null) {
/*      */         
/*      */         try {
/*  725 */           ReferenceCountUtil.release(msg);
/*      */         
/*      */         }
/*      */         finally {
/*      */ 
/*      */           
/*  731 */           safeSetFailure(promise, 
/*  732 */               newClosedChannelException(AbstractChannel.this.initialCloseCause, "write(Object, ChannelPromise)"));
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*      */       try {
/*  739 */         msg = AbstractChannel.this.filterOutboundMessage(msg);
/*  740 */         size = AbstractChannel.this.pipeline.estimatorHandle().size(msg);
/*  741 */         if (size < 0) {
/*  742 */           size = 0;
/*      */         }
/*  744 */       } catch (Throwable t) {
/*      */         try {
/*  746 */           ReferenceCountUtil.release(msg);
/*      */         } finally {
/*  748 */           safeSetFailure(promise, t);
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*  753 */       outboundBuffer.addMessage(msg, size, promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void flush() {
/*  758 */       assertEventLoop();
/*      */       
/*  760 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/*  761 */       if (outboundBuffer == null) {
/*      */         return;
/*      */       }
/*      */       
/*  765 */       outboundBuffer.addFlush();
/*  766 */       flush0();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void flush0() {
/*  771 */       if (this.inFlush0) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  776 */       ChannelOutboundBuffer outboundBuffer = this.outboundBuffer;
/*  777 */       if (outboundBuffer == null || outboundBuffer.isEmpty()) {
/*      */         return;
/*      */       }
/*      */       
/*  781 */       this.inFlush0 = true;
/*      */ 
/*      */       
/*  784 */       if (!AbstractChannel.this.isActive()) {
/*      */         
/*      */         try {
/*  787 */           if (!outboundBuffer.isEmpty()) {
/*  788 */             if (AbstractChannel.this.isOpen()) {
/*  789 */               outboundBuffer.failFlushed(new NotYetConnectedException(), true);
/*      */             } else {
/*      */               
/*  792 */               outboundBuffer.failFlushed(newClosedChannelException(AbstractChannel.this.initialCloseCause, "flush0()"), false);
/*      */             } 
/*      */           }
/*      */         } finally {
/*  796 */           this.inFlush0 = false;
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       try {
/*  802 */         AbstractChannel.this.doWrite(outboundBuffer);
/*  803 */       } catch (Throwable t) {
/*  804 */         handleWriteError(t);
/*      */       } finally {
/*  806 */         this.inFlush0 = false;
/*      */       } 
/*      */     }
/*      */     
/*      */     protected final void handleWriteError(Throwable t) {
/*  811 */       if (t instanceof IOException && AbstractChannel.this.config().isAutoClose()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  820 */         AbstractChannel.this.initialCloseCause = t;
/*  821 */         close(voidPromise(), t, newClosedChannelException(t, "flush0()"));
/*      */       } else {
/*      */         try {
/*  824 */           shutdownOutput(voidPromise(), t);
/*  825 */         } catch (Throwable t2) {
/*  826 */           AbstractChannel.this.initialCloseCause = t;
/*  827 */           close(voidPromise(), t2, newClosedChannelException(t, "flush0()"));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private ClosedChannelException newClosedChannelException(Throwable cause, String method) {
/*  834 */       ClosedChannelException exception = StacklessClosedChannelException.newInstance(AbstractUnsafe.class, method);
/*  835 */       if (cause != null) {
/*  836 */         exception.initCause(cause);
/*      */       }
/*  838 */       return exception;
/*      */     }
/*      */ 
/*      */     
/*      */     public final ChannelPromise voidPromise() {
/*  843 */       assertEventLoop();
/*      */       
/*  845 */       return AbstractChannel.this.unsafeVoidPromise;
/*      */     }
/*      */     
/*      */     protected final boolean ensureOpen(ChannelPromise promise) {
/*  849 */       if (AbstractChannel.this.isOpen()) {
/*  850 */         return true;
/*      */       }
/*      */       
/*  853 */       safeSetFailure(promise, newClosedChannelException(AbstractChannel.this.initialCloseCause, "ensureOpen(ChannelPromise)"));
/*  854 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final void safeSetSuccess(ChannelPromise promise) {
/*  861 */       if (!(promise instanceof VoidChannelPromise) && !promise.trySuccess()) {
/*  862 */         AbstractChannel.logger.warn("Failed to mark a promise as success because it is done already: {}", promise);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final void safeSetFailure(ChannelPromise promise, Throwable cause) {
/*  870 */       if (!(promise instanceof VoidChannelPromise) && !promise.tryFailure(cause)) {
/*  871 */         AbstractChannel.logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, cause);
/*      */       }
/*      */     }
/*      */     
/*      */     protected final void closeIfClosed() {
/*  876 */       if (AbstractChannel.this.isOpen()) {
/*      */         return;
/*      */       }
/*  879 */       close(voidPromise());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void invokeLater(Runnable task) {
/*      */       try {
/*  895 */         AbstractChannel.this.eventLoop().execute(task);
/*  896 */       } catch (RejectedExecutionException e) {
/*  897 */         AbstractChannel.logger.warn("Can't invoke task later as EventLoop rejected it", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final Throwable annotateConnectException(Throwable cause, SocketAddress remoteAddress) {
/*  905 */       if (cause instanceof ConnectException) {
/*  906 */         return new AbstractChannel.AnnotatedConnectException((ConnectException)cause, remoteAddress);
/*      */       }
/*  908 */       if (cause instanceof NoRouteToHostException) {
/*  909 */         return new AbstractChannel.AnnotatedNoRouteToHostException((NoRouteToHostException)cause, remoteAddress);
/*      */       }
/*  911 */       if (cause instanceof SocketException) {
/*  912 */         return new AbstractChannel.AnnotatedSocketException((SocketException)cause, remoteAddress);
/*      */       }
/*      */       
/*  915 */       return cause;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Executor prepareToClose() {
/*  925 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void doRegister() throws Exception {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doRegister(ChannelPromise promise) {
/*      */     try {
/*  963 */       doRegister();
/*  964 */     } catch (Throwable cause) {
/*  965 */       promise.setFailure(cause);
/*      */       return;
/*      */     } 
/*  968 */     promise.setSuccess();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doShutdownOutput() throws Exception {
/*  991 */     doClose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doDeregister() throws Exception {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object filterOutboundMessage(Object msg) throws Exception {
/* 1018 */     return msg;
/*      */   } protected abstract AbstractUnsafe newUnsafe(); protected abstract boolean isCompatible(EventLoop paramEventLoop); protected abstract SocketAddress localAddress0(); protected abstract SocketAddress remoteAddress0();
/*      */   protected abstract void doBind(SocketAddress paramSocketAddress) throws Exception;
/*      */   protected void validateFileRegion(DefaultFileRegion region, long position) throws IOException {
/* 1022 */     DefaultFileRegion.validate(region, position);
/*      */   } protected abstract void doDisconnect() throws Exception;
/*      */   protected abstract void doClose() throws Exception;
/*      */   protected abstract void doBeginRead() throws Exception;
/*      */   protected abstract void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception;
/*      */   static final class CloseFuture extends DefaultChannelPromise { CloseFuture(AbstractChannel ch) {
/* 1028 */       super(ch);
/*      */     }
/*      */ 
/*      */     
/*      */     public ChannelPromise setSuccess() {
/* 1033 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ChannelPromise setFailure(Throwable cause) {
/* 1038 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean trySuccess() {
/* 1043 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryFailure(Throwable cause) {
/* 1048 */       throw new IllegalStateException();
/*      */     }
/*      */     
/*      */     boolean setClosed() {
/* 1052 */       return super.trySuccess();
/*      */     } }
/*      */ 
/*      */   
/*      */   private static final class AnnotatedConnectException
/*      */     extends ConnectException {
/*      */     private static final long serialVersionUID = 3901958112696433556L;
/*      */     
/*      */     AnnotatedConnectException(ConnectException exception, SocketAddress remoteAddress) {
/* 1061 */       super(exception.getMessage() + ": " + remoteAddress);
/* 1062 */       initCause(exception);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Throwable fillInStackTrace() {
/* 1068 */       return this;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class AnnotatedNoRouteToHostException
/*      */     extends NoRouteToHostException {
/*      */     private static final long serialVersionUID = -6801433937592080623L;
/*      */     
/*      */     AnnotatedNoRouteToHostException(NoRouteToHostException exception, SocketAddress remoteAddress) {
/* 1077 */       super(exception.getMessage() + ": " + remoteAddress);
/* 1078 */       initCause(exception);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Throwable fillInStackTrace() {
/* 1084 */       return this;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class AnnotatedSocketException
/*      */     extends SocketException {
/*      */     private static final long serialVersionUID = 3896743275010454039L;
/*      */     
/*      */     AnnotatedSocketException(SocketException exception, SocketAddress remoteAddress) {
/* 1093 */       super(exception.getMessage() + ": " + remoteAddress);
/* 1094 */       initCause(exception);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Throwable fillInStackTrace() {
/* 1100 */       return this;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\AbstractChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */