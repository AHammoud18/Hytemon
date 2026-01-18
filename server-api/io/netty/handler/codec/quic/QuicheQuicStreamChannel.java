/*      */ package io.netty.handler.codec.quic;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.channel.Channel;
/*      */ import io.netty.channel.ChannelConfig;
/*      */ import io.netty.channel.ChannelFuture;
/*      */ import io.netty.channel.ChannelId;
/*      */ import io.netty.channel.ChannelMetadata;
/*      */ import io.netty.channel.ChannelOutboundBuffer;
/*      */ import io.netty.channel.ChannelOutboundInvoker;
/*      */ import io.netty.channel.ChannelPipeline;
/*      */ import io.netty.channel.ChannelPromise;
/*      */ import io.netty.channel.DefaultChannelId;
/*      */ import io.netty.channel.DefaultChannelPipeline;
/*      */ import io.netty.channel.EventLoop;
/*      */ import io.netty.channel.PendingWriteQueue;
/*      */ import io.netty.channel.RecvByteBufAllocator;
/*      */ import io.netty.channel.VoidChannelPromise;
/*      */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*      */ import io.netty.channel.socket.ChannelInputShutdownReadComplete;
/*      */ import io.netty.channel.socket.ChannelOutputShutdownException;
/*      */ import io.netty.util.DefaultAttributeMap;
/*      */ import io.netty.util.ReferenceCountUtil;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import io.netty.util.concurrent.Promise;
/*      */ import io.netty.util.concurrent.PromiseNotifier;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.util.concurrent.RejectedExecutionException;
/*      */ import org.jetbrains.annotations.Nullable;
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
/*      */ final class QuicheQuicStreamChannel
/*      */   extends DefaultAttributeMap
/*      */   implements QuicStreamChannel
/*      */ {
/*   53 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
/*   54 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(QuicheQuicStreamChannel.class);
/*      */   
/*      */   private final QuicheQuicChannel parent;
/*      */   
/*      */   private final ChannelId id;
/*      */   
/*      */   private final ChannelPipeline pipeline;
/*      */   private final QuicStreamChannelUnsafe unsafe;
/*      */   private final ChannelPromise closePromise;
/*      */   private final PendingWriteQueue queue;
/*      */   private final QuicStreamChannelConfig config;
/*      */   private final QuicStreamAddress address;
/*      */   private boolean readable;
/*      */   private boolean readPending;
/*      */   private boolean inRecv;
/*      */   private boolean inWriteQueued;
/*      */   private boolean finReceived;
/*      */   private boolean finSent;
/*      */   private volatile boolean registered;
/*      */   private volatile boolean writable = true;
/*      */   private volatile boolean active = true;
/*      */   private volatile boolean inputShutdown;
/*      */   private volatile boolean outputShutdown;
/*      */   private volatile QuicStreamPriority priority;
/*      */   private volatile long capacity;
/*      */   
/*      */   QuicheQuicStreamChannel(QuicheQuicChannel parent, long streamId) {
/*   81 */     this.parent = parent;
/*   82 */     this.id = (ChannelId)DefaultChannelId.newInstance();
/*   83 */     this.unsafe = new QuicStreamChannelUnsafe();
/*   84 */     this.pipeline = (ChannelPipeline)new DefaultChannelPipeline((Channel)this) {
/*      */       
/*      */       };
/*   87 */     this.config = new QuicheQuicStreamChannelConfig(this);
/*   88 */     this.address = new QuicStreamAddress(streamId);
/*   89 */     this.closePromise = newPromise();
/*   90 */     this.queue = new PendingWriteQueue((Channel)this);
/*      */ 
/*      */     
/*   93 */     if (parent.streamType(streamId) == QuicStreamType.UNIDIRECTIONAL && parent.isStreamLocalCreated(streamId)) {
/*   94 */       this.inputShutdown = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicStreamAddress localAddress() {
/*  100 */     return this.address;
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicStreamAddress remoteAddress() {
/*  105 */     return this.address;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLocalCreated() {
/*  110 */     return parent().isStreamLocalCreated(streamId());
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicStreamType type() {
/*  115 */     return parent().streamType(streamId());
/*      */   }
/*      */ 
/*      */   
/*      */   public long streamId() {
/*  120 */     return this.address.streamId();
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicStreamPriority priority() {
/*  125 */     return this.priority;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture updatePriority(QuicStreamPriority priority, ChannelPromise promise) {
/*  130 */     if (eventLoop().inEventLoop()) {
/*  131 */       updatePriority0(priority, promise);
/*      */     } else {
/*  133 */       eventLoop().execute(() -> updatePriority0(priority, promise));
/*      */     } 
/*  135 */     return (ChannelFuture)promise;
/*      */   }
/*      */   
/*      */   private void updatePriority0(QuicStreamPriority priority, ChannelPromise promise) {
/*  139 */     assert eventLoop().inEventLoop();
/*  140 */     if (!promise.setUncancellable()) {
/*      */       return;
/*      */     }
/*      */     try {
/*  144 */       parent().streamPriority(streamId(), (byte)priority.urgency(), priority.isIncremental());
/*  145 */     } catch (Throwable cause) {
/*  146 */       promise.setFailure(cause);
/*      */       return;
/*      */     } 
/*  149 */     this.priority = priority;
/*  150 */     promise.setSuccess();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInputShutdown() {
/*  155 */     return this.inputShutdown;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdownOutput(ChannelPromise promise) {
/*  160 */     if (eventLoop().inEventLoop()) {
/*  161 */       shutdownOutput0(promise);
/*      */     } else {
/*  163 */       eventLoop().execute(() -> shutdownOutput0(promise));
/*      */     } 
/*  165 */     return (ChannelFuture)promise;
/*      */   }
/*      */   
/*      */   private void shutdownOutput0(ChannelPromise promise) {
/*  169 */     assert eventLoop().inEventLoop();
/*  170 */     if (!promise.setUncancellable()) {
/*      */       return;
/*      */     }
/*  173 */     this.outputShutdown = true;
/*  174 */     this.unsafe.writeWithoutCheckChannelState(QuicStreamFrame.EMPTY_FIN, promise);
/*  175 */     this.unsafe.flush();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdownInput(int error, ChannelPromise promise) {
/*  180 */     if (eventLoop().inEventLoop()) {
/*  181 */       shutdownInput0(error, promise);
/*      */     } else {
/*  183 */       eventLoop().execute(() -> shutdownInput0(error, promise));
/*      */     } 
/*  185 */     return (ChannelFuture)promise;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdownOutput(int error, ChannelPromise promise) {
/*  190 */     if (eventLoop().inEventLoop()) {
/*  191 */       shutdownOutput0(error, promise);
/*      */     } else {
/*  193 */       eventLoop().execute(() -> shutdownOutput0(error, promise));
/*      */     } 
/*  195 */     return (ChannelFuture)promise;
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicheQuicChannel parent() {
/*  200 */     return this.parent;
/*      */   }
/*      */   
/*      */   private void shutdownInput0(int err, ChannelPromise channelPromise) {
/*  204 */     assert eventLoop().inEventLoop();
/*  205 */     if (!channelPromise.setUncancellable()) {
/*      */       return;
/*      */     }
/*  208 */     this.inputShutdown = true;
/*  209 */     parent().streamShutdown(streamId(), true, false, err, channelPromise);
/*  210 */     closeIfDone();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOutputShutdown() {
/*  215 */     return this.outputShutdown;
/*      */   }
/*      */   
/*      */   private void shutdownOutput0(int error, ChannelPromise channelPromise) {
/*  219 */     assert eventLoop().inEventLoop();
/*  220 */     if (!channelPromise.setUncancellable()) {
/*      */       return;
/*      */     }
/*  223 */     parent().streamShutdown(streamId(), false, true, error, channelPromise);
/*  224 */     this.outputShutdown = true;
/*  225 */     closeIfDone();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isShutdown() {
/*  230 */     return (this.outputShutdown && this.inputShutdown);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdown(ChannelPromise channelPromise) {
/*  235 */     if (eventLoop().inEventLoop()) {
/*  236 */       shutdown0(channelPromise);
/*      */     } else {
/*  238 */       eventLoop().execute(() -> shutdown0(channelPromise));
/*      */     } 
/*  240 */     return (ChannelFuture)channelPromise;
/*      */   }
/*      */   
/*      */   private void shutdown0(ChannelPromise promise) {
/*  244 */     assert eventLoop().inEventLoop();
/*  245 */     if (!promise.setUncancellable()) {
/*      */       return;
/*      */     }
/*  248 */     this.inputShutdown = true;
/*  249 */     this.outputShutdown = true;
/*  250 */     this.unsafe.writeWithoutCheckChannelState(QuicStreamFrame.EMPTY_FIN, this.unsafe.voidPromise());
/*  251 */     this.unsafe.flush();
/*  252 */     parent().streamShutdown(streamId(), true, false, 0, promise);
/*  253 */     closeIfDone();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture shutdown(int error, ChannelPromise promise) {
/*  258 */     if (eventLoop().inEventLoop()) {
/*  259 */       shutdown0(error, promise);
/*      */     } else {
/*  261 */       eventLoop().execute(() -> shutdown0(error, promise));
/*      */     } 
/*  263 */     return (ChannelFuture)promise;
/*      */   }
/*      */   
/*      */   private void shutdown0(int error, ChannelPromise channelPromise) {
/*  267 */     assert eventLoop().inEventLoop();
/*  268 */     if (!channelPromise.setUncancellable()) {
/*      */       return;
/*      */     }
/*  271 */     this.inputShutdown = true;
/*  272 */     this.outputShutdown = true;
/*  273 */     parent().streamShutdown(streamId(), true, true, error, channelPromise);
/*  274 */     closeIfDone();
/*      */   }
/*      */   
/*      */   private void sendFinIfNeeded() throws Exception {
/*  278 */     if (!this.finSent) {
/*  279 */       this.finSent = true;
/*  280 */       parent().streamSendFin(streamId());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void closeIfDone() {
/*  285 */     if (this.finSent && (this.finReceived || (type() == QuicStreamType.UNIDIRECTIONAL && isLocalCreated()))) {
/*  286 */       unsafe().close(unsafe().voidPromise());
/*      */     }
/*      */   }
/*      */   
/*      */   private void removeStreamFromParent() {
/*  291 */     if (!this.active && this.finReceived) {
/*  292 */       parent().streamClosed(streamId());
/*  293 */       this.inputShutdown = true;
/*  294 */       this.outputShutdown = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicStreamChannel flush() {
/*  300 */     this.pipeline.flush();
/*  301 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicStreamChannel read() {
/*  306 */     this.pipeline.read();
/*  307 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicStreamChannelConfig config() {
/*  312 */     return this.config;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOpen() {
/*  317 */     return this.active;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isActive() {
/*  322 */     return isOpen();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelMetadata metadata() {
/*  327 */     return METADATA;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelId id() {
/*  332 */     return this.id;
/*      */   }
/*      */ 
/*      */   
/*      */   public EventLoop eventLoop() {
/*  337 */     return this.parent.eventLoop();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRegistered() {
/*  342 */     return this.registered;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture closeFuture() {
/*  347 */     return (ChannelFuture)this.closePromise;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  352 */     return this.writable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long bytesBeforeUnwritable() {
/*  358 */     return Math.max(this.capacity, 0L);
/*      */   }
/*      */ 
/*      */   
/*      */   public long bytesBeforeWritable() {
/*  363 */     if (this.writable) {
/*  364 */       return 0L;
/*      */     }
/*      */     
/*  367 */     return 8L;
/*      */   }
/*      */ 
/*      */   
/*      */   public QuicStreamChannelUnsafe unsafe() {
/*  372 */     return this.unsafe;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline pipeline() {
/*  377 */     return this.pipeline;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*  382 */     return this.config.getAllocator();
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(Channel o) {
/*  387 */     return this.id.compareTo(o.id());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  395 */     return this.id.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*  404 */     return (this == o);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  409 */     return "[id: 0x" + this.id.asShortText() + ", " + this.address + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean writable(long capacity) {
/*  416 */     assert eventLoop().inEventLoop();
/*  417 */     if (capacity < 0L) {
/*      */       
/*  419 */       if (capacity != Quiche.QUICHE_ERR_DONE) {
/*  420 */         if (!this.queue.isEmpty()) {
/*  421 */           if (capacity == Quiche.QUICHE_ERR_STREAM_STOPPED) {
/*  422 */             this.queue.removeAndFailAll((Throwable)new ChannelOutputShutdownException("STOP_SENDING frame received"));
/*      */             
/*  424 */             return false;
/*      */           } 
/*  426 */           this.queue.removeAndFailAll(Quiche.convertToException((int)capacity));
/*      */         }
/*  428 */         else if (capacity == Quiche.QUICHE_ERR_STREAM_STOPPED) {
/*      */           
/*  430 */           return false;
/*      */         } 
/*      */         
/*  433 */         this.finSent = true;
/*  434 */         unsafe().close(unsafe().voidPromise());
/*      */       } 
/*  436 */       return false;
/*      */     } 
/*  438 */     this.capacity = capacity;
/*  439 */     boolean mayNeedWrite = unsafe().writeQueued();
/*      */     
/*  441 */     updateWritabilityIfNeeded((this.capacity > 0L));
/*  442 */     return mayNeedWrite;
/*      */   }
/*      */   
/*      */   private void updateWritabilityIfNeeded(boolean newWritable) {
/*  446 */     if (this.writable != newWritable) {
/*  447 */       this.writable = newWritable;
/*  448 */       this.pipeline.fireChannelWritabilityChanged();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void readable() {
/*  456 */     assert eventLoop().inEventLoop();
/*      */     
/*  458 */     this.readable = true;
/*  459 */     if (this.readPending) {
/*  460 */       unsafe().recv();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final class QuicStreamChannelUnsafe
/*      */     implements Channel.Unsafe
/*      */   {
/*      */     private RecvByteBufAllocator.Handle recvHandle;
/*  469 */     private final ChannelPromise voidPromise = (ChannelPromise)new VoidChannelPromise((Channel)QuicheQuicStreamChannel.this, false);
/*      */ 
/*      */     
/*      */     public void connect(SocketAddress remote, SocketAddress local, ChannelPromise promise) {
/*  473 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  474 */       promise.setFailure(new UnsupportedOperationException());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public RecvByteBufAllocator.Handle recvBufAllocHandle() {
/*  480 */       if (this.recvHandle == null) {
/*  481 */         this.recvHandle = QuicheQuicStreamChannel.this.config.getRecvByteBufAllocator().newHandle();
/*      */       }
/*  483 */       return this.recvHandle;
/*      */     }
/*      */ 
/*      */     
/*      */     public SocketAddress localAddress() {
/*  488 */       return QuicheQuicStreamChannel.this.address;
/*      */     }
/*      */ 
/*      */     
/*      */     public SocketAddress remoteAddress() {
/*  493 */       return QuicheQuicStreamChannel.this.address;
/*      */     }
/*      */ 
/*      */     
/*      */     public void register(EventLoop eventLoop, ChannelPromise promise) {
/*  498 */       assert eventLoop.inEventLoop();
/*  499 */       if (!promise.setUncancellable()) {
/*      */         return;
/*      */       }
/*  502 */       if (QuicheQuicStreamChannel.this.registered) {
/*  503 */         promise.setFailure(new IllegalStateException());
/*      */         return;
/*      */       } 
/*  506 */       if (eventLoop != QuicheQuicStreamChannel.this.parent.eventLoop()) {
/*  507 */         promise.setFailure(new IllegalArgumentException());
/*      */         return;
/*      */       } 
/*  510 */       QuicheQuicStreamChannel.this.registered = true;
/*  511 */       promise.setSuccess();
/*  512 */       QuicheQuicStreamChannel.this.pipeline.fireChannelRegistered();
/*  513 */       QuicheQuicStreamChannel.this.pipeline.fireChannelActive();
/*      */     }
/*      */ 
/*      */     
/*      */     public void bind(SocketAddress localAddress, ChannelPromise promise) {
/*  518 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  519 */       if (!promise.setUncancellable()) {
/*      */         return;
/*      */       }
/*  522 */       promise.setFailure(new UnsupportedOperationException());
/*      */     }
/*      */ 
/*      */     
/*      */     public void disconnect(ChannelPromise promise) {
/*  527 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  528 */       close(promise);
/*      */     }
/*      */ 
/*      */     
/*      */     public void close(ChannelPromise promise) {
/*  533 */       close(null, promise);
/*      */     }
/*      */     
/*      */     void close(@Nullable ClosedChannelException writeFailCause, ChannelPromise promise) {
/*  537 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  538 */       if (!promise.setUncancellable()) {
/*      */         return;
/*      */       }
/*  541 */       if (!QuicheQuicStreamChannel.this.active || QuicheQuicStreamChannel.this.closePromise.isDone()) {
/*  542 */         if (promise.isVoid()) {
/*      */           return;
/*      */         }
/*  545 */         QuicheQuicStreamChannel.this.closePromise.addListener((GenericFutureListener)new PromiseNotifier(new Promise[] { (Promise)promise }));
/*      */         return;
/*      */       } 
/*  548 */       QuicheQuicStreamChannel.this.active = false;
/*      */       
/*      */       try {
/*  551 */         QuicheQuicStreamChannel.this.sendFinIfNeeded();
/*  552 */       } catch (Exception exception) {
/*      */       
/*      */       } finally {
/*  555 */         if (!QuicheQuicStreamChannel.this.queue.isEmpty()) {
/*      */           
/*  557 */           if (writeFailCause == null) {
/*  558 */             writeFailCause = new ClosedChannelException();
/*      */           }
/*  560 */           QuicheQuicStreamChannel.this.queue.removeAndFailAll(writeFailCause);
/*      */         } 
/*      */         
/*  563 */         promise.trySuccess();
/*  564 */         QuicheQuicStreamChannel.this.closePromise.trySuccess();
/*  565 */         if (QuicheQuicStreamChannel.this.type() == QuicStreamType.UNIDIRECTIONAL && QuicheQuicStreamChannel.this.isLocalCreated()) {
/*  566 */           QuicheQuicStreamChannel.this.inputShutdown = true;
/*  567 */           QuicheQuicStreamChannel.this.outputShutdown = true;
/*      */ 
/*      */           
/*  570 */           QuicheQuicStreamChannel.this.parent().streamClosed(QuicheQuicStreamChannel.this.streamId());
/*      */         } else {
/*  572 */           QuicheQuicStreamChannel.this.removeStreamFromParent();
/*      */         } 
/*      */       } 
/*  575 */       if (QuicheQuicStreamChannel.this.inWriteQueued) {
/*  576 */         invokeLater(() -> deregister(voidPromise(), true));
/*      */       } else {
/*  578 */         deregister(voidPromise(), true);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void deregister(ChannelPromise promise, boolean fireChannelInactive) {
/*  583 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  584 */       if (!promise.setUncancellable()) {
/*      */         return;
/*      */       }
/*      */       
/*  588 */       if (!QuicheQuicStreamChannel.this.registered) {
/*  589 */         promise.trySuccess();
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
/*  602 */       invokeLater(() -> {
/*      */             if (fireChannelInactive) {
/*      */               QuicheQuicStreamChannel.this.pipeline.fireChannelInactive();
/*      */             }
/*      */             if (QuicheQuicStreamChannel.this.registered) {
/*      */               QuicheQuicStreamChannel.this.registered = false;
/*      */               QuicheQuicStreamChannel.this.pipeline.fireChannelUnregistered();
/*      */             } 
/*      */             promise.setSuccess();
/*      */           });
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void invokeLater(Runnable task) {
/*      */       try {
/*  631 */         QuicheQuicStreamChannel.this.eventLoop().execute(task);
/*  632 */       } catch (RejectedExecutionException e) {
/*  633 */         QuicheQuicStreamChannel.LOGGER.warn("Can't invoke task later as EventLoop rejected it", e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void closeForcibly() {
/*  639 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  640 */       close(QuicheQuicStreamChannel.this.unsafe().voidPromise());
/*      */     }
/*      */ 
/*      */     
/*      */     public void deregister(ChannelPromise promise) {
/*  645 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  646 */       deregister(promise, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void beginRead() {
/*  651 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  652 */       QuicheQuicStreamChannel.this.readPending = true;
/*  653 */       if (QuicheQuicStreamChannel.this.readable) {
/*  654 */         QuicheQuicStreamChannel.this.unsafe().recv();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  661 */         QuicheQuicStreamChannel.this.parent().connectionSendAndFlush();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void closeIfNeeded(boolean wasFinSent) {
/*  670 */       if (!wasFinSent && QuicheQuicStreamChannel.this.finSent && (QuicheQuicStreamChannel.this
/*  671 */         .type() == QuicStreamType.UNIDIRECTIONAL || QuicheQuicStreamChannel.this.finReceived))
/*      */       {
/*  673 */         close(voidPromise());
/*      */       }
/*      */     }
/*      */     
/*      */     boolean writeQueued() {
/*  678 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  679 */       boolean wasFinSent = QuicheQuicStreamChannel.this.finSent;
/*  680 */       QuicheQuicStreamChannel.this.inWriteQueued = true;
/*      */       try {
/*  682 */         if (QuicheQuicStreamChannel.this.queue.isEmpty()) {
/*  683 */           return false;
/*      */         }
/*  685 */         boolean written = false;
/*      */         while (true) {
/*  687 */           Object msg = QuicheQuicStreamChannel.this.queue.current();
/*  688 */           if (msg == null) {
/*      */             break;
/*      */           }
/*      */           try {
/*  692 */             int res = write0(msg);
/*  693 */             if (res == 1) {
/*  694 */               QuicheQuicStreamChannel.this.queue.remove().setSuccess();
/*  695 */               written = true; continue;
/*  696 */             }  if (res == 0 || res == Quiche.QUICHE_ERR_DONE)
/*      */               break; 
/*  698 */             if (res == Quiche.QUICHE_ERR_STREAM_STOPPED) {
/*      */ 
/*      */ 
/*      */               
/*  702 */               QuicheQuicStreamChannel.this.queue.removeAndFailAll((Throwable)new ChannelOutputShutdownException("STOP_SENDING frame received"));
/*      */               
/*      */               break;
/*      */             } 
/*  706 */             QuicheQuicStreamChannel.this.queue.remove().setFailure(Quiche.convertToException(res));
/*      */           }
/*  708 */           catch (Exception e) {
/*  709 */             QuicheQuicStreamChannel.this.queue.remove().setFailure(e);
/*      */           } 
/*      */         } 
/*  712 */         if (written) {
/*  713 */           QuicheQuicStreamChannel.this.updateWritabilityIfNeeded(true);
/*      */         }
/*  715 */         return written;
/*      */       } finally {
/*  717 */         closeIfNeeded(wasFinSent);
/*  718 */         QuicheQuicStreamChannel.this.inWriteQueued = false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void write(Object msg, ChannelPromise promise) {
/*  724 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  725 */       if (!promise.setUncancellable()) {
/*  726 */         ReferenceCountUtil.release(msg);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  731 */       if (!QuicheQuicStreamChannel.this.isOpen()) {
/*  732 */         queueAndFailAll(msg, promise, new ClosedChannelException());
/*  733 */       } else if (QuicheQuicStreamChannel.this.finSent) {
/*  734 */         queueAndFailAll(msg, promise, (Throwable)new ChannelOutputShutdownException("Fin was sent already"));
/*  735 */       } else if (!QuicheQuicStreamChannel.this.queue.isEmpty()) {
/*      */ 
/*      */         
/*      */         try {
/*  739 */           msg = filterMsg(msg);
/*  740 */         } catch (UnsupportedOperationException e) {
/*  741 */           ReferenceCountUtil.release(msg);
/*  742 */           promise.setFailure(e);
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/*  747 */         ReferenceCountUtil.touch(msg);
/*  748 */         QuicheQuicStreamChannel.this.queue.add(msg, promise);
/*      */ 
/*      */         
/*  751 */         writeQueued();
/*      */       } else {
/*  753 */         assert QuicheQuicStreamChannel.this.queue.isEmpty();
/*  754 */         writeWithoutCheckChannelState(msg, promise);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void queueAndFailAll(Object msg, ChannelPromise promise, Throwable cause) {
/*  760 */       ReferenceCountUtil.touch(msg);
/*      */       
/*  762 */       QuicheQuicStreamChannel.this.queue.add(msg, promise);
/*  763 */       QuicheQuicStreamChannel.this.queue.removeAndFailAll(cause);
/*      */     }
/*      */     
/*      */     private Object filterMsg(Object msg) {
/*  767 */       if (msg instanceof ByteBuf) {
/*  768 */         ByteBuf buffer = (ByteBuf)msg;
/*  769 */         if (!buffer.isDirect()) {
/*  770 */           ByteBuf tmpBuffer = QuicheQuicStreamChannel.this.alloc().directBuffer(buffer.readableBytes());
/*  771 */           tmpBuffer.writeBytes(buffer, buffer.readerIndex(), buffer.readableBytes());
/*  772 */           buffer.release();
/*  773 */           return tmpBuffer;
/*      */         } 
/*  775 */       } else if (msg instanceof QuicStreamFrame) {
/*  776 */         QuicStreamFrame frame = (QuicStreamFrame)msg;
/*  777 */         ByteBuf buffer = frame.content();
/*  778 */         if (!buffer.isDirect()) {
/*  779 */           ByteBuf tmpBuffer = QuicheQuicStreamChannel.this.alloc().directBuffer(buffer.readableBytes());
/*  780 */           tmpBuffer.writeBytes(buffer, buffer.readerIndex(), buffer.readableBytes());
/*  781 */           QuicStreamFrame tmpFrame = frame.replace(tmpBuffer);
/*  782 */           frame.release();
/*  783 */           return tmpFrame;
/*      */         } 
/*      */       } else {
/*  786 */         throw new UnsupportedOperationException("unsupported message type: " + 
/*  787 */             StringUtil.simpleClassName(msg));
/*      */       } 
/*  789 */       return msg;
/*      */     }
/*      */     
/*      */     void writeWithoutCheckChannelState(Object msg, ChannelPromise promise) {
/*      */       try {
/*  794 */         msg = filterMsg(msg);
/*  795 */       } catch (UnsupportedOperationException e) {
/*  796 */         ReferenceCountUtil.release(msg);
/*  797 */         promise.setFailure(e);
/*      */       } 
/*      */       
/*  800 */       boolean wasFinSent = QuicheQuicStreamChannel.this.finSent;
/*  801 */       boolean mayNeedWritabilityUpdate = false;
/*      */       try {
/*  803 */         int res = write0(msg);
/*  804 */         if (res > 0)
/*  805 */         { ReferenceCountUtil.release(msg);
/*  806 */           promise.setSuccess();
/*  807 */           mayNeedWritabilityUpdate = (QuicheQuicStreamChannel.this.capacity == 0L); }
/*  808 */         else if (res == 0 || res == Quiche.QUICHE_ERR_DONE)
/*      */         
/*  810 */         { ReferenceCountUtil.touch(msg);
/*  811 */           QuicheQuicStreamChannel.this.queue.add(msg, promise);
/*  812 */           mayNeedWritabilityUpdate = true; }
/*  813 */         else { if (res == Quiche.QUICHE_ERR_STREAM_STOPPED) {
/*  814 */             throw new ChannelOutputShutdownException("STOP_SENDING frame received");
/*      */           }
/*  816 */           throw Quiche.convertToException(res); }
/*      */       
/*  818 */       } catch (Exception e) {
/*  819 */         ReferenceCountUtil.release(msg);
/*  820 */         promise.setFailure(e);
/*  821 */         mayNeedWritabilityUpdate = (QuicheQuicStreamChannel.this.capacity == 0L);
/*      */       } finally {
/*  823 */         if (mayNeedWritabilityUpdate) {
/*  824 */           QuicheQuicStreamChannel.this.updateWritabilityIfNeeded(false);
/*      */         }
/*  826 */         closeIfNeeded(wasFinSent);
/*      */       } 
/*      */     } private int write0(Object msg) throws Exception {
/*      */       boolean fin;
/*      */       ByteBuf buffer;
/*  831 */       if (QuicheQuicStreamChannel.this.type() == QuicStreamType.UNIDIRECTIONAL && !QuicheQuicStreamChannel.this.isLocalCreated()) {
/*  832 */         throw new UnsupportedOperationException("Writes on non-local created streams that are unidirectional are not supported");
/*      */       }
/*      */       
/*  835 */       if (QuicheQuicStreamChannel.this.finSent) {
/*  836 */         throw new ChannelOutputShutdownException("Fin was sent already");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  841 */       if (msg instanceof ByteBuf) {
/*  842 */         fin = false;
/*  843 */         buffer = (ByteBuf)msg;
/*      */       } else {
/*  845 */         QuicStreamFrame frame = (QuicStreamFrame)msg;
/*  846 */         fin = frame.hasFin();
/*  847 */         buffer = frame.content();
/*      */       } 
/*      */       
/*  850 */       boolean readable = buffer.isReadable();
/*  851 */       if (!fin && !readable) {
/*  852 */         return 1;
/*      */       }
/*      */       
/*  855 */       boolean sendSomething = false;
/*      */       try {
/*      */         do {
/*  858 */           int res = QuicheQuicStreamChannel.this.parent().streamSend(QuicheQuicStreamChannel.this.streamId(), buffer, fin);
/*      */ 
/*      */           
/*  861 */           long cap = QuicheQuicStreamChannel.this.parent.streamCapacity(QuicheQuicStreamChannel.this.streamId());
/*  862 */           if (cap >= 0L) {
/*  863 */             QuicheQuicStreamChannel.this.capacity = cap;
/*      */           }
/*  865 */           if (res < 0) {
/*  866 */             return res;
/*      */           }
/*  868 */           if (readable && res == 0) {
/*  869 */             return 0;
/*      */           }
/*  871 */           sendSomething = true;
/*  872 */           buffer.skipBytes(res);
/*  873 */         } while (buffer.isReadable());
/*      */         
/*  875 */         if (fin) {
/*  876 */           QuicheQuicStreamChannel.this.finSent = true;
/*  877 */           QuicheQuicStreamChannel.this.outputShutdown = true;
/*      */         } 
/*  879 */         return 1;
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/*  885 */         if (sendSomething) {
/*  886 */           QuicheQuicStreamChannel.this.parent.connectionSendAndFlush();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void flush() {
/*  893 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ChannelPromise voidPromise() {
/*  899 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  900 */       return this.voidPromise;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public ChannelOutboundBuffer outboundBuffer() {
/*  906 */       return null;
/*      */     }
/*      */     
/*      */     private void closeOnRead(ChannelPipeline pipeline, boolean readFrames) {
/*  910 */       if (readFrames && QuicheQuicStreamChannel.this.finReceived && QuicheQuicStreamChannel.this.finSent) {
/*  911 */         close(voidPromise());
/*  912 */       } else if (QuicheQuicStreamChannel.this.config.isAllowHalfClosure()) {
/*  913 */         if (QuicheQuicStreamChannel.this.finReceived)
/*      */         {
/*      */           
/*  916 */           pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*  917 */           pipeline.fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
/*  918 */           if (QuicheQuicStreamChannel.this.finSent)
/*      */           {
/*      */             
/*  921 */             close(voidPromise());
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  927 */         close(voidPromise());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void handleReadException(ChannelPipeline pipeline, @Nullable ByteBuf byteBuf, Throwable cause, RecvByteBufAllocator.Handle allocHandle, boolean readFrames) {
/*  934 */       if (byteBuf != null) {
/*  935 */         if (byteBuf.isReadable()) {
/*  936 */           pipeline.fireChannelRead(byteBuf);
/*      */         } else {
/*  938 */           byteBuf.release();
/*      */         } 
/*      */       }
/*      */       
/*  942 */       readComplete(allocHandle, pipeline);
/*  943 */       pipeline.fireExceptionCaught(cause);
/*  944 */       if (QuicheQuicStreamChannel.this.finReceived) {
/*  945 */         closeOnRead(pipeline, readFrames);
/*      */       }
/*      */     }
/*      */     
/*      */     void recv() {
/*  950 */       assert QuicheQuicStreamChannel.this.eventLoop().inEventLoop();
/*  951 */       if (QuicheQuicStreamChannel.this.inRecv) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  957 */       QuicheQuicStreamChannel.this.inRecv = true;
/*      */       try {
/*  959 */         ChannelPipeline pipeline = QuicheQuicStreamChannel.this.pipeline();
/*  960 */         QuicheQuicStreamChannelConfig config = (QuicheQuicStreamChannelConfig)QuicheQuicStreamChannel.this.config();
/*      */ 
/*      */         
/*  963 */         DirectIoByteBufAllocator allocator = config.allocator;
/*      */         
/*  965 */         RecvByteBufAllocator.Handle allocHandle = recvBufAllocHandle();
/*  966 */         boolean readFrames = config.isReadFrames();
/*      */ 
/*      */ 
/*      */         
/*  970 */         while (QuicheQuicStreamChannel.this.active && QuicheQuicStreamChannel.this.readPending && QuicheQuicStreamChannel.this.readable) {
/*  971 */           allocHandle.reset((ChannelConfig)config);
/*  972 */           ByteBuf byteBuf = null;
/*  973 */           QuicheQuicChannel parent = QuicheQuicStreamChannel.this.parent();
/*      */ 
/*      */ 
/*      */           
/*  977 */           boolean readCompleteNeeded = false;
/*  978 */           boolean continueReading = true;
/*      */           try {
/*  980 */             while (!QuicheQuicStreamChannel.this.finReceived && continueReading) {
/*  981 */               byteBuf = allocHandle.allocate(allocator);
/*  982 */               allocHandle.attemptedBytesRead(byteBuf.writableBytes());
/*  983 */               QuicheQuicChannel.StreamRecvResult result = parent.streamRecv(QuicheQuicStreamChannel.this.streamId(), byteBuf);
/*  984 */               switch (result) {
/*      */                 
/*      */                 case DONE:
/*  987 */                   QuicheQuicStreamChannel.this.readable = false;
/*      */                   break;
/*      */ 
/*      */                 
/*      */                 case FIN:
/*  992 */                   QuicheQuicStreamChannel.this.readable = false;
/*  993 */                   QuicheQuicStreamChannel.this.finReceived = true;
/*  994 */                   QuicheQuicStreamChannel.this.inputShutdown = true;
/*      */                   break;
/*      */                 case OK:
/*      */                   break;
/*      */                 default:
/*  999 */                   throw new Error("Unexpected StreamRecvResult: " + result);
/*      */               } 
/* 1001 */               allocHandle.lastBytesRead(byteBuf.readableBytes());
/* 1002 */               if (allocHandle.lastBytesRead() <= 0) {
/* 1003 */                 byteBuf.release();
/* 1004 */                 if (QuicheQuicStreamChannel.this.finReceived && readFrames) {
/*      */ 
/*      */                   
/* 1007 */                   byteBuf = Unpooled.EMPTY_BUFFER;
/*      */                 } else {
/* 1009 */                   byteBuf = null;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/* 1014 */               allocHandle.incMessagesRead(1);
/* 1015 */               readCompleteNeeded = true;
/*      */ 
/*      */ 
/*      */               
/* 1019 */               QuicheQuicStreamChannel.this.readPending = false;
/*      */               
/* 1021 */               if (readFrames) {
/* 1022 */                 pipeline.fireChannelRead(new DefaultQuicStreamFrame(byteBuf, QuicheQuicStreamChannel.this.finReceived));
/*      */               } else {
/* 1024 */                 pipeline.fireChannelRead(byteBuf);
/*      */               } 
/* 1026 */               byteBuf = null;
/* 1027 */               continueReading = allocHandle.continueReading();
/*      */             } 
/*      */             
/* 1030 */             if (readCompleteNeeded) {
/* 1031 */               readComplete(allocHandle, pipeline);
/*      */             }
/* 1033 */             if (QuicheQuicStreamChannel.this.finReceived) {
/* 1034 */               QuicheQuicStreamChannel.this.readable = false;
/* 1035 */               closeOnRead(pipeline, readFrames);
/*      */             } 
/* 1037 */           } catch (Throwable cause) {
/* 1038 */             QuicheQuicStreamChannel.this.readable = false;
/* 1039 */             handleReadException(pipeline, byteBuf, cause, allocHandle, readFrames);
/*      */           } 
/*      */         } 
/*      */       } finally {
/*      */         
/* 1044 */         QuicheQuicStreamChannel.this.inRecv = false;
/* 1045 */         QuicheQuicStreamChannel.this.removeStreamFromParent();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void readComplete(RecvByteBufAllocator.Handle allocHandle, ChannelPipeline pipeline) {
/* 1053 */       allocHandle.readComplete();
/* 1054 */       pipeline.fireChannelReadComplete();
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicStreamChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */