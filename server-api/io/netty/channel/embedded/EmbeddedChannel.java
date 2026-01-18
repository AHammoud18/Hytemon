/*      */ package io.netty.channel.embedded;
/*      */ 
/*      */ import io.netty.channel.AbstractChannel;
/*      */ import io.netty.channel.Channel;
/*      */ import io.netty.channel.ChannelConfig;
/*      */ import io.netty.channel.ChannelFuture;
/*      */ import io.netty.channel.ChannelFutureListener;
/*      */ import io.netty.channel.ChannelHandler;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import io.netty.channel.ChannelId;
/*      */ import io.netty.channel.ChannelInitializer;
/*      */ import io.netty.channel.ChannelMetadata;
/*      */ import io.netty.channel.ChannelOutboundBuffer;
/*      */ import io.netty.channel.ChannelOutboundInvoker;
/*      */ import io.netty.channel.ChannelPipeline;
/*      */ import io.netty.channel.ChannelPromise;
/*      */ import io.netty.channel.DefaultChannelConfig;
/*      */ import io.netty.channel.DefaultChannelPipeline;
/*      */ import io.netty.channel.EventLoop;
/*      */ import io.netty.channel.RecvByteBufAllocator;
/*      */ import io.netty.util.ReferenceCountUtil;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import io.netty.util.concurrent.Ticker;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.RecyclableArrayList;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.Objects;
/*      */ import java.util.Queue;
/*      */ import java.util.concurrent.TimeUnit;
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
/*      */ public class EmbeddedChannel
/*      */   extends AbstractChannel
/*      */ {
/*   54 */   private static final SocketAddress LOCAL_ADDRESS = new EmbeddedSocketAddress();
/*   55 */   private static final SocketAddress REMOTE_ADDRESS = new EmbeddedSocketAddress();
/*      */   
/*   57 */   private static final ChannelHandler[] EMPTY_HANDLERS = new ChannelHandler[0];
/*   58 */   private enum State { OPEN, ACTIVE, CLOSED; }
/*      */   
/*   60 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(EmbeddedChannel.class);
/*      */   
/*   62 */   private static final ChannelMetadata METADATA_NO_DISCONNECT = new ChannelMetadata(false);
/*   63 */   private static final ChannelMetadata METADATA_DISCONNECT = new ChannelMetadata(true);
/*      */   private final EmbeddedEventLoop loop;
/*      */   
/*   66 */   private final ChannelFutureListener recordExceptionListener = new ChannelFutureListener()
/*      */     {
/*      */       public void operationComplete(ChannelFuture future) throws Exception {
/*   69 */         EmbeddedChannel.this.recordException(future);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   private final ChannelMetadata metadata;
/*      */   
/*      */   private final ChannelConfig config;
/*      */   
/*      */   private Queue<Object> inboundMessages;
/*      */   
/*      */   private Queue<Object> outboundMessages;
/*      */   private Throwable lastException;
/*      */   private State state;
/*      */   private int executingStackCnt;
/*      */   private boolean cancelRemainingScheduledTasks;
/*      */   
/*      */   public EmbeddedChannel() {
/*   87 */     this(builder());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EmbeddedChannel(ChannelId channelId) {
/*   96 */     this(builder().channelId(channelId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EmbeddedChannel(ChannelHandler... handlers) {
/*  105 */     this(builder().handlers(handlers));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EmbeddedChannel(boolean hasDisconnect, ChannelHandler... handlers) {
/*  116 */     this(builder().hasDisconnect(hasDisconnect).handlers(handlers));
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
/*      */   public EmbeddedChannel(boolean register, boolean hasDisconnect, ChannelHandler... handlers) {
/*  129 */     this(builder().register(register).hasDisconnect(hasDisconnect).handlers(handlers));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EmbeddedChannel(ChannelId channelId, ChannelHandler... handlers) {
/*  140 */     this(builder().channelId(channelId).handlers(handlers));
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
/*      */   public EmbeddedChannel(ChannelId channelId, boolean hasDisconnect, ChannelHandler... handlers) {
/*  153 */     this(builder().channelId(channelId).hasDisconnect(hasDisconnect).handlers(handlers));
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
/*      */   public EmbeddedChannel(ChannelId channelId, boolean register, boolean hasDisconnect, ChannelHandler... handlers) {
/*  169 */     this(builder().channelId(channelId).register(register).hasDisconnect(hasDisconnect).handlers(handlers));
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
/*      */   public EmbeddedChannel(Channel parent, ChannelId channelId, boolean register, boolean hasDisconnect, ChannelHandler... handlers) {
/*  186 */     this(builder()
/*  187 */         .parent(parent)
/*  188 */         .channelId(channelId)
/*  189 */         .register(register)
/*  190 */         .hasDisconnect(hasDisconnect)
/*  191 */         .handlers(handlers));
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
/*      */   public EmbeddedChannel(ChannelId channelId, boolean hasDisconnect, ChannelConfig config, ChannelHandler... handlers) {
/*  206 */     this(builder().channelId(channelId).hasDisconnect(hasDisconnect).config(config).handlers(handlers));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EmbeddedChannel(Builder builder) {
/*  216 */     super(builder.parent, builder.channelId);
/*  217 */     this.loop = new EmbeddedEventLoop((builder.ticker == null) ? (Ticker)new EmbeddedEventLoop.FreezableTicker() : builder.ticker);
/*  218 */     this.metadata = metadata(builder.hasDisconnect);
/*  219 */     this.config = (builder.config == null) ? (ChannelConfig)new DefaultChannelConfig((Channel)this) : builder.config;
/*  220 */     if (builder.handler == null) {
/*  221 */       setup(builder.register, builder.handlers);
/*      */     } else {
/*  223 */       setup(builder.register, builder.handler);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static ChannelMetadata metadata(boolean hasDisconnect) {
/*  228 */     return hasDisconnect ? METADATA_DISCONNECT : METADATA_NO_DISCONNECT;
/*      */   }
/*      */   
/*      */   private void setup(boolean register, ChannelHandler... handlers) {
/*  232 */     ChannelPipeline p = pipeline();
/*  233 */     p.addLast(new ChannelHandler[] { (ChannelHandler)new ChannelInitializer<Channel>()
/*      */           {
/*      */             protected void initChannel(Channel ch) {
/*  236 */               ChannelPipeline pipeline = ch.pipeline();
/*  237 */               for (ChannelHandler h : handlers) {
/*  238 */                 if (h == null) {
/*      */                   break;
/*      */                 }
/*  241 */                 pipeline.addLast(new ChannelHandler[] { h });
/*      */               } 
/*      */             }
/*      */           } });
/*  245 */     if (register) {
/*  246 */       ChannelFuture future = this.loop.register((Channel)this);
/*  247 */       assert future.isDone();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setup(boolean register, ChannelHandler handler) {
/*  252 */     ChannelPipeline p = pipeline();
/*  253 */     p.addLast(new ChannelHandler[] { handler });
/*  254 */     if (register) {
/*  255 */       ChannelFuture future = this.loop.register((Channel)this);
/*  256 */       assert future.isDone();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void register() throws Exception {
/*  264 */     ChannelFuture future = this.loop.register((Channel)this);
/*  265 */     assert future.isDone();
/*  266 */     Throwable cause = future.cause();
/*  267 */     if (cause != null) {
/*  268 */       PlatformDependent.throwException(cause);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final DefaultChannelPipeline newChannelPipeline() {
/*  274 */     return new EmbeddedChannelPipeline(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelMetadata metadata() {
/*  279 */     return this.metadata;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelConfig config() {
/*  284 */     return this.config;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOpen() {
/*  289 */     return (this.state != State.CLOSED);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isActive() {
/*  294 */     return (this.state == State.ACTIVE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Queue<Object> inboundMessages() {
/*  301 */     if (this.inboundMessages == null) {
/*  302 */       this.inboundMessages = new ArrayDeque();
/*      */     }
/*  304 */     return this.inboundMessages;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Queue<Object> lastInboundBuffer() {
/*  312 */     return inboundMessages();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Queue<Object> outboundMessages() {
/*  319 */     if (this.outboundMessages == null) {
/*  320 */       this.outboundMessages = new ArrayDeque();
/*      */     }
/*  322 */     return this.outboundMessages;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Queue<Object> lastOutboundBuffer() {
/*  330 */     return outboundMessages();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readInbound() {
/*  338 */     T message = (T)poll(this.inboundMessages);
/*  339 */     if (message != null) {
/*  340 */       ReferenceCountUtil.touch(message, "Caller of readInbound() will handle the message from this point");
/*      */     }
/*  342 */     return message;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T readOutbound() {
/*  350 */     T message = (T)poll(this.outboundMessages);
/*  351 */     if (message != null) {
/*  352 */       ReferenceCountUtil.touch(message, "Caller of readOutbound() will handle the message from this point.");
/*      */     }
/*  354 */     return message;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeInbound(Object... msgs) {
/*  365 */     ensureOpen();
/*  366 */     if (msgs.length == 0) {
/*  367 */       return isNotEmpty(this.inboundMessages);
/*      */     }
/*      */     
/*  370 */     this.executingStackCnt++;
/*      */     try {
/*  372 */       ChannelPipeline p = pipeline();
/*  373 */       for (Object m : msgs) {
/*  374 */         p.fireChannelRead(m);
/*      */       }
/*      */       
/*  377 */       flushInbound(false, voidPromise());
/*      */     } finally {
/*  379 */       this.executingStackCnt--;
/*  380 */       maybeRunPendingTasks();
/*      */     } 
/*  382 */     return isNotEmpty(this.inboundMessages);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture writeOneInbound(Object msg) {
/*  392 */     return writeOneInbound(msg, newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture writeOneInbound(Object msg, ChannelPromise promise) {
/*  402 */     this.executingStackCnt++;
/*      */     try {
/*  404 */       if (checkOpen(true)) {
/*  405 */         pipeline().fireChannelRead(msg);
/*      */       }
/*      */     } finally {
/*  408 */       this.executingStackCnt--;
/*  409 */       maybeRunPendingTasks();
/*      */     } 
/*  411 */     return checkException(promise);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EmbeddedChannel flushInbound() {
/*  420 */     flushInbound(true, voidPromise());
/*  421 */     return this;
/*      */   }
/*      */   
/*      */   private ChannelFuture flushInbound(boolean recordException, ChannelPromise promise) {
/*  425 */     this.executingStackCnt++;
/*      */     try {
/*  427 */       if (checkOpen(recordException)) {
/*  428 */         pipeline().fireChannelReadComplete();
/*  429 */         runPendingTasks();
/*      */       } 
/*      */     } finally {
/*  432 */       this.executingStackCnt--;
/*  433 */       maybeRunPendingTasks();
/*      */     } 
/*      */     
/*  436 */     return checkException(promise);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeOutbound(Object... msgs) {
/*  446 */     ensureOpen();
/*  447 */     if (msgs.length == 0) {
/*  448 */       return isNotEmpty(this.outboundMessages);
/*      */     }
/*      */     
/*  451 */     this.executingStackCnt++;
/*  452 */     RecyclableArrayList futures = RecyclableArrayList.newInstance(msgs.length);
/*      */     try {
/*      */       try {
/*  455 */         for (Object m : msgs) {
/*  456 */           if (m == null) {
/*      */             break;
/*      */           }
/*  459 */           futures.add(write(m));
/*      */         } 
/*      */         
/*  462 */         flushOutbound0();
/*      */         
/*  464 */         int size = futures.size();
/*  465 */         for (int i = 0; i < size; i++) {
/*  466 */           ChannelFuture future = (ChannelFuture)futures.get(i);
/*  467 */           if (future.isDone()) {
/*  468 */             recordException(future);
/*      */           } else {
/*      */             
/*  471 */             future.addListener((GenericFutureListener)this.recordExceptionListener);
/*      */           } 
/*      */         } 
/*      */       } finally {
/*  475 */         this.executingStackCnt--;
/*  476 */         maybeRunPendingTasks();
/*      */       } 
/*  478 */       checkException();
/*  479 */       return isNotEmpty(this.outboundMessages);
/*      */     } finally {
/*  481 */       futures.recycle();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture writeOneOutbound(Object msg) {
/*  492 */     return writeOneOutbound(msg, newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture writeOneOutbound(Object msg, ChannelPromise promise) {
/*  502 */     this.executingStackCnt++;
/*      */     try {
/*  504 */       if (checkOpen(true)) {
/*  505 */         return write(msg, promise);
/*      */       }
/*      */     } finally {
/*  508 */       this.executingStackCnt--;
/*  509 */       maybeRunPendingTasks();
/*      */     } 
/*      */     
/*  512 */     return checkException(promise);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EmbeddedChannel flushOutbound() {
/*  521 */     this.executingStackCnt++;
/*      */     try {
/*  523 */       if (checkOpen(true)) {
/*  524 */         flushOutbound0();
/*      */       }
/*      */     } finally {
/*  527 */       this.executingStackCnt--;
/*  528 */       maybeRunPendingTasks();
/*      */     } 
/*  530 */     checkException(voidPromise());
/*  531 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void flushOutbound0() {
/*  537 */     runPendingTasks();
/*      */     
/*  539 */     flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean finish() {
/*  548 */     return finish(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean finishAndReleaseAll() {
/*  558 */     return finish(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean finish(boolean releaseAll) {
/*  568 */     this.executingStackCnt++;
/*      */     try {
/*  570 */       close();
/*      */     } finally {
/*  572 */       this.executingStackCnt--;
/*  573 */       maybeRunPendingTasks();
/*      */     } 
/*      */     try {
/*  576 */       checkException();
/*  577 */       return (isNotEmpty(this.inboundMessages) || isNotEmpty(this.outboundMessages));
/*      */     } finally {
/*  579 */       if (releaseAll) {
/*  580 */         releaseAll(this.inboundMessages);
/*  581 */         releaseAll(this.outboundMessages);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean releaseInbound() {
/*  591 */     return releaseAll(this.inboundMessages);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean releaseOutbound() {
/*  599 */     return releaseAll(this.outboundMessages);
/*      */   }
/*      */   
/*      */   private static boolean releaseAll(Queue<Object> queue) {
/*  603 */     if (isNotEmpty(queue)) {
/*      */       while (true) {
/*  605 */         Object msg = queue.poll();
/*  606 */         if (msg == null) {
/*      */           break;
/*      */         }
/*  609 */         ReferenceCountUtil.release(msg);
/*      */       } 
/*  611 */       return true;
/*      */     } 
/*  613 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture close() {
/*  618 */     return close(newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public final ChannelFuture disconnect() {
/*  623 */     return disconnect(newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final ChannelFuture close(ChannelPromise promise) {
/*      */     ChannelFuture future;
/*  630 */     this.executingStackCnt++;
/*      */     
/*      */     try {
/*  633 */       runPendingTasks();
/*  634 */       future = super.close(promise);
/*      */       
/*  636 */       this.cancelRemainingScheduledTasks = true;
/*      */     } finally {
/*  638 */       this.executingStackCnt--;
/*  639 */       maybeRunPendingTasks();
/*      */     } 
/*  641 */     return future;
/*      */   }
/*      */   
/*      */   public final ChannelFuture disconnect(ChannelPromise promise) {
/*      */     ChannelFuture future;
/*  646 */     this.executingStackCnt++;
/*      */     
/*      */     try {
/*  649 */       future = super.disconnect(promise);
/*      */       
/*  651 */       if (!this.metadata.hasDisconnect()) {
/*  652 */         this.cancelRemainingScheduledTasks = true;
/*      */       }
/*      */     } finally {
/*  655 */       this.executingStackCnt--;
/*  656 */       maybeRunPendingTasks();
/*      */     } 
/*  658 */     return future;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture bind(SocketAddress localAddress) {
/*  663 */     this.executingStackCnt++;
/*      */     try {
/*  665 */       return super.bind(localAddress);
/*      */     } finally {
/*  667 */       this.executingStackCnt--;
/*  668 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress) {
/*  674 */     this.executingStackCnt++;
/*      */     try {
/*  676 */       return super.connect(remoteAddress);
/*      */     } finally {
/*  678 */       this.executingStackCnt--;
/*  679 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/*  685 */     this.executingStackCnt++;
/*      */     try {
/*  687 */       return super.connect(remoteAddress, localAddress);
/*      */     } finally {
/*  689 */       this.executingStackCnt--;
/*  690 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture deregister() {
/*  696 */     this.executingStackCnt++;
/*      */     try {
/*  698 */       return super.deregister();
/*      */     } finally {
/*  700 */       this.executingStackCnt--;
/*  701 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Channel flush() {
/*  707 */     this.executingStackCnt++;
/*      */     try {
/*  709 */       return super.flush();
/*      */     } finally {
/*  711 */       this.executingStackCnt--;
/*  712 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
/*  718 */     this.executingStackCnt++;
/*      */     try {
/*  720 */       return super.bind(localAddress, promise);
/*      */     } finally {
/*  722 */       this.executingStackCnt--;
/*  723 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/*  729 */     this.executingStackCnt++;
/*      */     try {
/*  731 */       return super.connect(remoteAddress, promise);
/*      */     } finally {
/*  733 */       this.executingStackCnt--;
/*  734 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*  740 */     this.executingStackCnt++;
/*      */     try {
/*  742 */       return super.connect(remoteAddress, localAddress, promise);
/*      */     } finally {
/*  744 */       this.executingStackCnt--;
/*  745 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture deregister(ChannelPromise promise) {
/*  751 */     this.executingStackCnt++;
/*      */     try {
/*  753 */       return super.deregister(promise);
/*      */     } finally {
/*  755 */       this.executingStackCnt--;
/*  756 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Channel read() {
/*  762 */     this.executingStackCnt++;
/*      */     try {
/*  764 */       return super.read();
/*      */     } finally {
/*  766 */       this.executingStackCnt--;
/*  767 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture write(Object msg) {
/*  773 */     this.executingStackCnt++;
/*      */     try {
/*  775 */       return super.write(msg);
/*      */     } finally {
/*  777 */       this.executingStackCnt--;
/*  778 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture write(Object msg, ChannelPromise promise) {
/*  784 */     this.executingStackCnt++;
/*      */     try {
/*  786 */       return super.write(msg, promise);
/*      */     } finally {
/*  788 */       this.executingStackCnt--;
/*  789 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object msg) {
/*  795 */     this.executingStackCnt++;
/*      */     try {
/*  797 */       return super.writeAndFlush(msg);
/*      */     } finally {
/*  799 */       this.executingStackCnt--;
/*  800 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/*  806 */     this.executingStackCnt++;
/*      */     try {
/*  808 */       return super.writeAndFlush(msg, promise);
/*      */     } finally {
/*  810 */       this.executingStackCnt--;
/*  811 */       maybeRunPendingTasks();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean isNotEmpty(Queue<Object> queue) {
/*  816 */     return (queue != null && !queue.isEmpty());
/*      */   }
/*      */   
/*      */   private static Object poll(Queue<Object> queue) {
/*  820 */     return (queue != null) ? queue.poll() : null;
/*      */   }
/*      */   
/*      */   private void maybeRunPendingTasks() {
/*  824 */     if (this.executingStackCnt == 0) {
/*  825 */       runPendingTasks();
/*      */       
/*  827 */       if (this.cancelRemainingScheduledTasks)
/*      */       {
/*  829 */         embeddedEventLoop().cancelScheduledTasks();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runPendingTasks() {
/*      */     try {
/*  840 */       embeddedEventLoop().runTasks();
/*  841 */     } catch (Exception e) {
/*  842 */       recordException(e);
/*      */     } 
/*      */     
/*      */     try {
/*  846 */       embeddedEventLoop().runScheduledTasks();
/*  847 */     } catch (Exception e) {
/*  848 */       recordException(e);
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
/*      */   public boolean hasPendingTasks() {
/*  860 */     return (embeddedEventLoop().hasPendingNormalTasks() || 
/*  861 */       embeddedEventLoop().nextScheduledTask() == 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long runScheduledPendingTasks() {
/*      */     try {
/*  871 */       return embeddedEventLoop().runScheduledTasks();
/*  872 */     } catch (Exception e) {
/*  873 */       recordException(e);
/*  874 */       return embeddedEventLoop().nextScheduledTask();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void recordException(ChannelFuture future) {
/*  879 */     if (!future.isSuccess()) {
/*  880 */       recordException(future.cause());
/*      */     }
/*      */   }
/*      */   
/*      */   private void recordException(Throwable cause) {
/*  885 */     if (this.lastException == null) {
/*  886 */       this.lastException = cause;
/*      */     } else {
/*  888 */       logger.warn("More than one exception was raised. Will report only the first one and log others.", cause);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private EmbeddedEventLoop.FreezableTicker freezableTicker() {
/*  895 */     Ticker ticker = eventLoop().ticker();
/*  896 */     if (ticker instanceof EmbeddedEventLoop.FreezableTicker) {
/*  897 */       return (EmbeddedEventLoop.FreezableTicker)ticker;
/*      */     }
/*  899 */     throw new IllegalStateException("EmbeddedChannel constructed with custom ticker, time manipulation methods are unavailable.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void advanceTimeBy(long duration, TimeUnit unit) {
/*  909 */     freezableTicker().advance(duration, unit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void freezeTime() {
/*  918 */     freezableTicker().freezeTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unfreezeTime() {
/*  929 */     freezableTicker().unfreezeTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ChannelFuture checkException(ChannelPromise promise) {
/*  936 */     Throwable t = this.lastException;
/*  937 */     if (t != null) {
/*  938 */       this.lastException = null;
/*      */       
/*  940 */       if (promise.isVoid()) {
/*  941 */         PlatformDependent.throwException(t);
/*      */       }
/*      */       
/*  944 */       return (ChannelFuture)promise.setFailure(t);
/*      */     } 
/*      */     
/*  947 */     return (ChannelFuture)promise.setSuccess();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkException() {
/*  954 */     checkException(voidPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkOpen(boolean recordException) {
/*  962 */     if (!isOpen()) {
/*  963 */       if (recordException) {
/*  964 */         recordException(new ClosedChannelException());
/*      */       }
/*  966 */       return false;
/*      */     } 
/*      */     
/*  969 */     return true;
/*      */   }
/*      */   
/*      */   private EmbeddedEventLoop embeddedEventLoop() {
/*  973 */     if (isRegistered()) {
/*  974 */       return (EmbeddedEventLoop)eventLoop();
/*      */     }
/*      */     
/*  977 */     return this.loop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void ensureOpen() {
/*  984 */     if (!checkOpen(true)) {
/*  985 */       checkException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isCompatible(EventLoop loop) {
/*  991 */     return loop instanceof EmbeddedEventLoop;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SocketAddress localAddress0() {
/*  996 */     return isActive() ? LOCAL_ADDRESS : null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SocketAddress remoteAddress0() {
/* 1001 */     return isActive() ? REMOTE_ADDRESS : null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doRegister() throws Exception {
/* 1006 */     this.state = State.ACTIVE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doBind(SocketAddress localAddress) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doDisconnect() throws Exception {
/* 1016 */     if (!this.metadata.hasDisconnect()) {
/* 1017 */       doClose();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doClose() throws Exception {
/* 1023 */     this.state = State.CLOSED;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doBeginRead() throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/* 1033 */     return new EmbeddedUnsafe();
/*      */   }
/*      */ 
/*      */   
/*      */   public Channel.Unsafe unsafe() {
/* 1038 */     return ((EmbeddedUnsafe)super.unsafe()).wrapped;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*      */     while (true) {
/* 1044 */       Object msg = in.current();
/* 1045 */       if (msg == null) {
/*      */         break;
/*      */       }
/*      */       
/* 1049 */       ReferenceCountUtil.retain(msg);
/* 1050 */       handleOutboundMessage(msg);
/* 1051 */       in.remove();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleOutboundMessage(Object msg) {
/* 1061 */     outboundMessages().add(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleInboundMessage(Object msg) {
/* 1068 */     inboundMessages().add(msg);
/*      */   }
/*      */   
/*      */   public static Builder builder() {
/* 1072 */     return new Builder();
/*      */   }
/*      */   
/*      */   public static final class Builder {
/*      */     Channel parent;
/* 1077 */     ChannelId channelId = EmbeddedChannelId.INSTANCE;
/*      */     
/*      */     boolean register = true;
/*      */     boolean hasDisconnect;
/* 1081 */     ChannelHandler[] handlers = EmbeddedChannel.EMPTY_HANDLERS;
/*      */ 
/*      */ 
/*      */     
/*      */     ChannelHandler handler;
/*      */ 
/*      */     
/*      */     ChannelConfig config;
/*      */ 
/*      */     
/*      */     Ticker ticker;
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder parent(Channel parent) {
/* 1096 */       this.parent = parent;
/* 1097 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder channelId(ChannelId channelId) {
/* 1107 */       this.channelId = Objects.<ChannelId>requireNonNull(channelId, "channelId");
/* 1108 */       return this;
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
/*      */     public Builder register(boolean register) {
/* 1120 */       this.register = register;
/* 1121 */       return this;
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
/*      */     public Builder hasDisconnect(boolean hasDisconnect) {
/* 1133 */       this.hasDisconnect = hasDisconnect;
/* 1134 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder handlers(ChannelHandler... handlers) {
/* 1144 */       this.handlers = Objects.<ChannelHandler[]>requireNonNull(handlers, "handlers");
/* 1145 */       this.handler = null;
/* 1146 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder handlers(ChannelHandler handler) {
/* 1156 */       this.handler = Objects.<ChannelHandler>requireNonNull(handler, "handler");
/* 1157 */       this.handlers = null;
/* 1158 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder config(ChannelConfig config) {
/* 1168 */       this.config = Objects.<ChannelConfig>requireNonNull(config, "config");
/* 1169 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder ticker(Ticker ticker) {
/* 1179 */       this.ticker = ticker;
/* 1180 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EmbeddedChannel build() {
/* 1190 */       return new EmbeddedChannel(this);
/*      */     }
/*      */     private Builder() {} }
/*      */   private final class EmbeddedUnsafe extends AbstractChannel.AbstractUnsafe { private EmbeddedUnsafe() {
/* 1194 */       super(EmbeddedChannel.this);
/*      */ 
/*      */ 
/*      */       
/* 1198 */       this.wrapped = new Channel.Unsafe()
/*      */         {
/*      */           public RecvByteBufAllocator.Handle recvBufAllocHandle() {
/* 1201 */             return EmbeddedChannel.EmbeddedUnsafe.this.recvBufAllocHandle();
/*      */           }
/*      */ 
/*      */           
/*      */           public SocketAddress localAddress() {
/* 1206 */             return EmbeddedChannel.EmbeddedUnsafe.this.localAddress();
/*      */           }
/*      */ 
/*      */           
/*      */           public SocketAddress remoteAddress() {
/* 1211 */             return EmbeddedChannel.EmbeddedUnsafe.this.remoteAddress();
/*      */           }
/*      */ 
/*      */           
/*      */           public void register(EventLoop eventLoop, ChannelPromise promise) {
/* 1216 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1218 */               EmbeddedChannel.EmbeddedUnsafe.this.register(eventLoop, promise);
/*      */             } finally {
/* 1220 */               EmbeddedChannel.this.executingStackCnt--;
/* 1221 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void bind(SocketAddress localAddress, ChannelPromise promise) {
/* 1227 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1229 */               EmbeddedChannel.EmbeddedUnsafe.this.bind(localAddress, promise);
/*      */             } finally {
/* 1231 */               EmbeddedChannel.this.executingStackCnt--;
/* 1232 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 1238 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1240 */               EmbeddedChannel.EmbeddedUnsafe.this.connect(remoteAddress, localAddress, promise);
/*      */             } finally {
/* 1242 */               EmbeddedChannel.this.executingStackCnt--;
/* 1243 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void disconnect(ChannelPromise promise) {
/* 1249 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1251 */               EmbeddedChannel.EmbeddedUnsafe.this.disconnect(promise);
/*      */             } finally {
/* 1253 */               EmbeddedChannel.this.executingStackCnt--;
/* 1254 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void close(ChannelPromise promise) {
/* 1260 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1262 */               EmbeddedChannel.EmbeddedUnsafe.this.close(promise);
/*      */             } finally {
/* 1264 */               EmbeddedChannel.this.executingStackCnt--;
/* 1265 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void closeForcibly() {
/* 1271 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1273 */               EmbeddedChannel.EmbeddedUnsafe.this.closeForcibly();
/*      */             } finally {
/* 1275 */               EmbeddedChannel.this.executingStackCnt--;
/* 1276 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void deregister(ChannelPromise promise) {
/* 1282 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1284 */               EmbeddedChannel.EmbeddedUnsafe.this.deregister(promise);
/*      */             } finally {
/* 1286 */               EmbeddedChannel.this.executingStackCnt--;
/* 1287 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void beginRead() {
/* 1293 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1295 */               EmbeddedChannel.EmbeddedUnsafe.this.beginRead();
/*      */             } finally {
/* 1297 */               EmbeddedChannel.this.executingStackCnt--;
/* 1298 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void write(Object msg, ChannelPromise promise) {
/* 1304 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1306 */               EmbeddedChannel.EmbeddedUnsafe.this.write(msg, promise);
/*      */             } finally {
/* 1308 */               EmbeddedChannel.this.executingStackCnt--;
/* 1309 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void flush() {
/* 1315 */             EmbeddedChannel.this.executingStackCnt++;
/*      */             try {
/* 1317 */               EmbeddedChannel.EmbeddedUnsafe.this.flush();
/*      */             } finally {
/* 1319 */               EmbeddedChannel.this.executingStackCnt--;
/* 1320 */               EmbeddedChannel.this.maybeRunPendingTasks();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public ChannelPromise voidPromise() {
/* 1326 */             return EmbeddedChannel.EmbeddedUnsafe.this.voidPromise();
/*      */           }
/*      */ 
/*      */           
/*      */           public ChannelOutboundBuffer outboundBuffer() {
/* 1331 */             return EmbeddedChannel.EmbeddedUnsafe.this.outboundBuffer();
/*      */           }
/*      */         };
/*      */     }
/*      */     final Channel.Unsafe wrapped;
/*      */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 1337 */       safeSetSuccess(promise);
/*      */     } }
/*      */ 
/*      */   
/*      */   private final class EmbeddedChannelPipeline extends DefaultChannelPipeline {
/*      */     EmbeddedChannelPipeline(EmbeddedChannel channel) {
/* 1343 */       super((Channel)channel);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onUnhandledInboundException(Throwable cause) {
/* 1348 */       EmbeddedChannel.this.recordException(cause);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void onUnhandledInboundMessage(ChannelHandlerContext ctx, Object msg) {
/* 1353 */       EmbeddedChannel.this.handleInboundMessage(msg);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\embedded\EmbeddedChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */