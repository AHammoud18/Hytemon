/*      */ package io.netty.channel;
/*      */ 
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.util.Attribute;
/*      */ import io.netty.util.AttributeKey;
/*      */ import io.netty.util.Recycler;
/*      */ import io.netty.util.ReferenceCountUtil;
/*      */ import io.netty.util.ResourceLeakHint;
/*      */ import io.netty.util.concurrent.AbstractEventExecutor;
/*      */ import io.netty.util.concurrent.EventExecutor;
/*      */ import io.netty.util.internal.ObjectPool;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PromiseNotificationUtil;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import io.netty.util.internal.SystemPropertyUtil;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.net.SocketAddress;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*      */ abstract class AbstractChannelHandlerContext
/*      */   implements ChannelHandlerContext, ResourceLeakHint
/*      */ {
/*   61 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannelHandlerContext.class);
/*      */   
/*      */   volatile AbstractChannelHandlerContext next;
/*      */   
/*      */   volatile AbstractChannelHandlerContext prev;
/*   66 */   private static final AtomicIntegerFieldUpdater<AbstractChannelHandlerContext> HANDLER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(AbstractChannelHandlerContext.class, "handlerState");
/*      */ 
/*      */   
/*      */   private static final int ADD_PENDING = 1;
/*      */ 
/*      */   
/*      */   private static final int ADD_COMPLETE = 2;
/*      */ 
/*      */   
/*      */   private static final int REMOVE_COMPLETE = 3;
/*      */ 
/*      */   
/*      */   private static final int INIT = 0;
/*      */ 
/*      */   
/*      */   private final DefaultChannelPipeline pipeline;
/*      */ 
/*      */   
/*      */   private final String name;
/*      */ 
/*      */   
/*      */   private final boolean ordered;
/*      */ 
/*      */   
/*      */   private final int executionMask;
/*      */ 
/*      */   
/*      */   final EventExecutor childExecutor;
/*      */ 
/*      */   
/*      */   EventExecutor contextExecutor;
/*      */ 
/*      */   
/*      */   private ChannelFuture succeededFuture;
/*      */ 
/*      */   
/*      */   private Tasks invokeTasks;
/*      */ 
/*      */   
/*  105 */   private volatile int handlerState = 0;
/*      */ 
/*      */   
/*      */   AbstractChannelHandlerContext(DefaultChannelPipeline pipeline, EventExecutor executor, String name, Class<? extends ChannelHandler> handlerClass) {
/*  109 */     this.name = (String)ObjectUtil.checkNotNull(name, "name");
/*  110 */     this.pipeline = pipeline;
/*  111 */     this.childExecutor = executor;
/*  112 */     this.executionMask = ChannelHandlerMask.mask(handlerClass);
/*      */     
/*  114 */     this.ordered = (executor == null || executor instanceof io.netty.util.concurrent.OrderedEventExecutor);
/*      */   }
/*      */ 
/*      */   
/*      */   public Channel channel() {
/*  119 */     return this.pipeline.channel();
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPipeline pipeline() {
/*  124 */     return this.pipeline;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*  129 */     return channel().config().getAllocator();
/*      */   }
/*      */ 
/*      */   
/*      */   public EventExecutor executor() {
/*  134 */     EventExecutor ex = this.contextExecutor;
/*  135 */     if (ex == null) {
/*  136 */       this.contextExecutor = ex = (this.childExecutor != null) ? this.childExecutor : (EventExecutor)channel().eventLoop();
/*      */     }
/*  138 */     return ex;
/*      */   }
/*      */ 
/*      */   
/*      */   public String name() {
/*  143 */     return this.name;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext fireChannelRegistered() {
/*  148 */     AbstractChannelHandlerContext next = findContextInbound(2);
/*  149 */     if (next.executor().inEventLoop()) {
/*  150 */       if (next.invokeHandler()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  155 */           ChannelHandler handler = next.handler();
/*  156 */           DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  157 */           if (handler == headContext) {
/*  158 */             headContext.channelRegistered(next);
/*  159 */           } else if (handler instanceof ChannelInboundHandlerAdapter) {
/*  160 */             ((ChannelInboundHandlerAdapter)handler).channelRegistered(next);
/*      */           } else {
/*  162 */             ((ChannelInboundHandler)handler).channelRegistered(next);
/*      */           } 
/*  164 */         } catch (Throwable t) {
/*  165 */           next.invokeExceptionCaught(t);
/*      */         } 
/*      */       } else {
/*  168 */         next.fireChannelRegistered();
/*      */       } 
/*      */     } else {
/*  171 */       next.executor().execute(this::fireChannelRegistered);
/*      */     } 
/*  173 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext fireChannelUnregistered() {
/*  178 */     AbstractChannelHandlerContext next = findContextInbound(4);
/*  179 */     if (next.executor().inEventLoop()) {
/*  180 */       if (next.invokeHandler()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  185 */           ChannelHandler handler = next.handler();
/*  186 */           DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  187 */           if (handler == headContext) {
/*  188 */             headContext.channelUnregistered(next);
/*  189 */           } else if (handler instanceof ChannelInboundHandlerAdapter) {
/*  190 */             ((ChannelInboundHandlerAdapter)handler).channelUnregistered(next);
/*      */           } else {
/*  192 */             ((ChannelInboundHandler)handler).channelUnregistered(next);
/*      */           } 
/*  194 */         } catch (Throwable t) {
/*  195 */           next.invokeExceptionCaught(t);
/*      */         } 
/*      */       } else {
/*  198 */         next.fireChannelUnregistered();
/*      */       } 
/*      */     } else {
/*  201 */       next.executor().execute(this::fireChannelUnregistered);
/*      */     } 
/*  203 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext fireChannelActive() {
/*  208 */     AbstractChannelHandlerContext next = findContextInbound(8);
/*  209 */     if (next.executor().inEventLoop()) {
/*  210 */       if (next.invokeHandler()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  215 */           ChannelHandler handler = next.handler();
/*  216 */           DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  217 */           if (handler == headContext) {
/*  218 */             headContext.channelActive(next);
/*  219 */           } else if (handler instanceof ChannelInboundHandlerAdapter) {
/*  220 */             ((ChannelInboundHandlerAdapter)handler).channelActive(next);
/*      */           } else {
/*  222 */             ((ChannelInboundHandler)handler).channelActive(next);
/*      */           } 
/*  224 */         } catch (Throwable t) {
/*  225 */           next.invokeExceptionCaught(t);
/*      */         } 
/*      */       } else {
/*  228 */         next.fireChannelActive();
/*      */       } 
/*      */     } else {
/*  231 */       next.executor().execute(this::fireChannelActive);
/*      */     } 
/*  233 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext fireChannelInactive() {
/*  238 */     AbstractChannelHandlerContext next = findContextInbound(16);
/*  239 */     if (next.executor().inEventLoop()) {
/*  240 */       if (next.invokeHandler()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  245 */           ChannelHandler handler = next.handler();
/*  246 */           DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  247 */           if (handler == headContext) {
/*  248 */             headContext.channelInactive(next);
/*  249 */           } else if (handler instanceof ChannelInboundHandlerAdapter) {
/*  250 */             ((ChannelInboundHandlerAdapter)handler).channelInactive(next);
/*      */           } else {
/*  252 */             ((ChannelInboundHandler)handler).channelInactive(next);
/*      */           } 
/*  254 */         } catch (Throwable t) {
/*  255 */           next.invokeExceptionCaught(t);
/*      */         } 
/*      */       } else {
/*  258 */         next.fireChannelInactive();
/*      */       } 
/*      */     } else {
/*  261 */       next.executor().execute(this::fireChannelInactive);
/*      */     } 
/*  263 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
/*  268 */     AbstractChannelHandlerContext next = findContextInbound(1);
/*  269 */     ObjectUtil.checkNotNull(cause, "cause");
/*  270 */     if (next.executor().inEventLoop()) {
/*  271 */       next.invokeExceptionCaught(cause);
/*      */     } else {
/*      */       try {
/*  274 */         next.executor().execute(() -> next.invokeExceptionCaught(cause));
/*  275 */       } catch (Throwable t) {
/*  276 */         if (logger.isWarnEnabled()) {
/*  277 */           logger.warn("Failed to submit an exceptionCaught() event.", t);
/*  278 */           logger.warn("The exceptionCaught() event that was failed to submit was:", cause);
/*      */         } 
/*      */       } 
/*      */     } 
/*  282 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private void invokeExceptionCaught(Throwable cause) {
/*  287 */     if (invokeHandler()) {
/*      */       try {
/*  289 */         handler().exceptionCaught(this, cause);
/*  290 */       } catch (Throwable error) {
/*  291 */         if (logger.isDebugEnabled()) {
/*  292 */           logger.debug("An exception was thrown by a user handler's exceptionCaught() method while handling the following exception:", cause);
/*      */ 
/*      */         
/*      */         }
/*  296 */         else if (logger.isWarnEnabled()) {
/*  297 */           logger.warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a user handler's exceptionCaught() method while handling the following exception:", error, cause);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  304 */       fireExceptionCaught(cause);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext fireUserEventTriggered(Object event) {
/*  310 */     ObjectUtil.checkNotNull(event, "event");
/*  311 */     AbstractChannelHandlerContext next = findContextInbound(128);
/*  312 */     if (next.executor().inEventLoop()) {
/*  313 */       if (next.invokeHandler()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  318 */           ChannelHandler handler = next.handler();
/*  319 */           DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  320 */           if (handler == headContext) {
/*  321 */             headContext.userEventTriggered(next, event);
/*  322 */           } else if (handler instanceof ChannelInboundHandlerAdapter) {
/*  323 */             ((ChannelInboundHandlerAdapter)handler).userEventTriggered(next, event);
/*      */           } else {
/*  325 */             ((ChannelInboundHandler)handler).userEventTriggered(next, event);
/*      */           } 
/*  327 */         } catch (Throwable t) {
/*  328 */           next.invokeExceptionCaught(t);
/*      */         } 
/*      */       } else {
/*  331 */         next.fireUserEventTriggered(event);
/*      */       } 
/*      */     } else {
/*  334 */       next.executor().execute(() -> fireUserEventTriggered(event));
/*      */     } 
/*  336 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext fireChannelRead(Object msg) {
/*  341 */     AbstractChannelHandlerContext next = findContextInbound(32);
/*  342 */     if (next.executor().inEventLoop()) {
/*  343 */       Object m = this.pipeline.touch(msg, next);
/*  344 */       if (next.invokeHandler()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  349 */           ChannelHandler handler = next.handler();
/*  350 */           DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  351 */           if (handler == headContext) {
/*  352 */             headContext.channelRead(next, m);
/*  353 */           } else if (handler instanceof ChannelDuplexHandler) {
/*  354 */             ((ChannelDuplexHandler)handler).channelRead(next, m);
/*      */           } else {
/*  356 */             ((ChannelInboundHandler)handler).channelRead(next, m);
/*      */           } 
/*  358 */         } catch (Throwable t) {
/*  359 */           next.invokeExceptionCaught(t);
/*      */         } 
/*      */       } else {
/*  362 */         next.fireChannelRead(m);
/*      */       } 
/*      */     } else {
/*  365 */       next.executor().execute(() -> fireChannelRead(msg));
/*      */     } 
/*  367 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext fireChannelReadComplete() {
/*  372 */     AbstractChannelHandlerContext next = findContextInbound(64);
/*  373 */     if (next.executor().inEventLoop()) {
/*  374 */       if (next.invokeHandler()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  379 */           ChannelHandler handler = next.handler();
/*  380 */           DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  381 */           if (handler == headContext) {
/*  382 */             headContext.channelReadComplete(next);
/*  383 */           } else if (handler instanceof ChannelDuplexHandler) {
/*  384 */             ((ChannelDuplexHandler)handler).channelReadComplete(next);
/*      */           } else {
/*  386 */             ((ChannelInboundHandler)handler).channelReadComplete(next);
/*      */           } 
/*  388 */         } catch (Throwable t) {
/*  389 */           next.invokeExceptionCaught(t);
/*      */         } 
/*      */       } else {
/*  392 */         next.fireChannelReadComplete();
/*      */       } 
/*      */     } else {
/*  395 */       next.executor().execute((getInvokeTasks()).invokeChannelReadCompleteTask);
/*      */     } 
/*  397 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext fireChannelWritabilityChanged() {
/*  402 */     AbstractChannelHandlerContext next = findContextInbound(256);
/*  403 */     if (next.executor().inEventLoop()) {
/*  404 */       if (next.invokeHandler()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  409 */           ChannelHandler handler = next.handler();
/*  410 */           DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  411 */           if (handler == headContext) {
/*  412 */             headContext.channelWritabilityChanged(next);
/*  413 */           } else if (handler instanceof ChannelInboundHandlerAdapter) {
/*  414 */             ((ChannelInboundHandlerAdapter)handler).channelWritabilityChanged(next);
/*      */           } else {
/*  416 */             ((ChannelInboundHandler)handler).channelWritabilityChanged(next);
/*      */           } 
/*  418 */         } catch (Throwable t) {
/*  419 */           next.invokeExceptionCaught(t);
/*      */         } 
/*      */       } else {
/*  422 */         next.fireChannelWritabilityChanged();
/*      */       } 
/*      */     } else {
/*  425 */       next.executor().execute((getInvokeTasks()).invokeChannelWritableStateChangedTask);
/*      */     } 
/*  427 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture bind(SocketAddress localAddress) {
/*  432 */     return bind(localAddress, newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress) {
/*  437 */     return connect(remoteAddress, newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/*  442 */     return connect(remoteAddress, localAddress, newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture disconnect() {
/*  447 */     return disconnect(newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture close() {
/*  452 */     return close(newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture deregister() {
/*  457 */     return deregister(newPromise());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture bind(final SocketAddress localAddress, final ChannelPromise promise) {
/*  462 */     ObjectUtil.checkNotNull(localAddress, "localAddress");
/*  463 */     if (isNotValidPromise(promise, false))
/*      */     {
/*  465 */       return promise;
/*      */     }
/*      */     
/*  468 */     final AbstractChannelHandlerContext next = findContextOutbound(512);
/*  469 */     EventExecutor executor = next.executor();
/*  470 */     if (executor.inEventLoop()) {
/*  471 */       next.invokeBind(localAddress, promise);
/*      */     } else {
/*  473 */       safeExecute(executor, new Runnable()
/*      */           {
/*      */             public void run() {
/*  476 */               next.invokeBind(localAddress, promise);
/*      */             }
/*      */           }promise, null, false);
/*      */     } 
/*  480 */     return promise;
/*      */   }
/*      */   
/*      */   private void invokeBind(SocketAddress localAddress, ChannelPromise promise) {
/*  484 */     if (invokeHandler()) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  489 */         ChannelHandler handler = handler();
/*  490 */         DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  491 */         if (handler == headContext) {
/*  492 */           headContext.bind(this, localAddress, promise);
/*  493 */         } else if (handler instanceof ChannelDuplexHandler) {
/*  494 */           ((ChannelDuplexHandler)handler).bind(this, localAddress, promise);
/*  495 */         } else if (handler instanceof ChannelOutboundHandlerAdapter) {
/*  496 */           ((ChannelOutboundHandlerAdapter)handler).bind(this, localAddress, promise);
/*      */         } else {
/*  498 */           ((ChannelOutboundHandler)handler).bind(this, localAddress, promise);
/*      */         } 
/*  500 */       } catch (Throwable t) {
/*  501 */         notifyOutboundHandlerException(t, promise);
/*      */       } 
/*      */     } else {
/*  504 */       bind(localAddress, promise);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/*  510 */     return connect(remoteAddress, null, promise);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
/*  516 */     ObjectUtil.checkNotNull(remoteAddress, "remoteAddress");
/*      */     
/*  518 */     if (isNotValidPromise(promise, false))
/*      */     {
/*  520 */       return promise;
/*      */     }
/*      */     
/*  523 */     final AbstractChannelHandlerContext next = findContextOutbound(1024);
/*  524 */     EventExecutor executor = next.executor();
/*  525 */     if (executor.inEventLoop()) {
/*  526 */       next.invokeConnect(remoteAddress, localAddress, promise);
/*      */     } else {
/*  528 */       safeExecute(executor, new Runnable()
/*      */           {
/*      */             public void run() {
/*  531 */               next.invokeConnect(remoteAddress, localAddress, promise);
/*      */             }
/*      */           }promise, null, false);
/*      */     } 
/*  535 */     return promise;
/*      */   }
/*      */   
/*      */   private void invokeConnect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*  539 */     if (invokeHandler()) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  544 */         ChannelHandler handler = handler();
/*  545 */         DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  546 */         if (handler == headContext) {
/*  547 */           headContext.connect(this, remoteAddress, localAddress, promise);
/*  548 */         } else if (handler instanceof ChannelDuplexHandler) {
/*  549 */           ((ChannelDuplexHandler)handler).connect(this, remoteAddress, localAddress, promise);
/*  550 */         } else if (handler instanceof ChannelOutboundHandlerAdapter) {
/*  551 */           ((ChannelOutboundHandlerAdapter)handler).connect(this, remoteAddress, localAddress, promise);
/*      */         } else {
/*  553 */           ((ChannelOutboundHandler)handler).connect(this, remoteAddress, localAddress, promise);
/*      */         } 
/*  555 */       } catch (Throwable t) {
/*  556 */         notifyOutboundHandlerException(t, promise);
/*      */       } 
/*      */     } else {
/*  559 */       connect(remoteAddress, localAddress, promise);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture disconnect(final ChannelPromise promise) {
/*  565 */     if (!channel().metadata().hasDisconnect())
/*      */     {
/*      */       
/*  568 */       return close(promise);
/*      */     }
/*  570 */     if (isNotValidPromise(promise, false))
/*      */     {
/*  572 */       return promise;
/*      */     }
/*      */     
/*  575 */     final AbstractChannelHandlerContext next = findContextOutbound(2048);
/*  576 */     EventExecutor executor = next.executor();
/*  577 */     if (executor.inEventLoop()) {
/*  578 */       next.invokeDisconnect(promise);
/*      */     } else {
/*  580 */       safeExecute(executor, new Runnable()
/*      */           {
/*      */             public void run() {
/*  583 */               next.invokeDisconnect(promise);
/*      */             }
/*      */           },  promise, null, false);
/*      */     } 
/*  587 */     return promise;
/*      */   }
/*      */   
/*      */   private void invokeDisconnect(ChannelPromise promise) {
/*  591 */     if (invokeHandler()) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  596 */         ChannelHandler handler = handler();
/*  597 */         DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  598 */         if (handler == headContext) {
/*  599 */           headContext.disconnect(this, promise);
/*  600 */         } else if (handler instanceof ChannelDuplexHandler) {
/*  601 */           ((ChannelDuplexHandler)handler).disconnect(this, promise);
/*  602 */         } else if (handler instanceof ChannelOutboundHandlerAdapter) {
/*  603 */           ((ChannelOutboundHandlerAdapter)handler).disconnect(this, promise);
/*      */         } else {
/*  605 */           ((ChannelOutboundHandler)handler).disconnect(this, promise);
/*      */         } 
/*  607 */       } catch (Throwable t) {
/*  608 */         notifyOutboundHandlerException(t, promise);
/*      */       } 
/*      */     } else {
/*  611 */       disconnect(promise);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture close(final ChannelPromise promise) {
/*  617 */     if (isNotValidPromise(promise, false))
/*      */     {
/*  619 */       return promise;
/*      */     }
/*      */     
/*  622 */     final AbstractChannelHandlerContext next = findContextOutbound(4096);
/*  623 */     EventExecutor executor = next.executor();
/*  624 */     if (executor.inEventLoop()) {
/*  625 */       next.invokeClose(promise);
/*      */     } else {
/*  627 */       safeExecute(executor, new Runnable()
/*      */           {
/*      */             public void run() {
/*  630 */               next.invokeClose(promise);
/*      */             }
/*      */           },  promise, null, false);
/*      */     } 
/*      */     
/*  635 */     return promise;
/*      */   }
/*      */   
/*      */   private void invokeClose(ChannelPromise promise) {
/*  639 */     if (invokeHandler()) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  644 */         ChannelHandler handler = handler();
/*  645 */         DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  646 */         if (handler == headContext) {
/*  647 */           headContext.close(this, promise);
/*  648 */         } else if (handler instanceof ChannelDuplexHandler) {
/*  649 */           ((ChannelDuplexHandler)handler).close(this, promise);
/*  650 */         } else if (handler instanceof ChannelOutboundHandlerAdapter) {
/*  651 */           ((ChannelOutboundHandlerAdapter)handler).close(this, promise);
/*      */         } else {
/*  653 */           ((ChannelOutboundHandler)handler).close(this, promise);
/*      */         } 
/*  655 */       } catch (Throwable t) {
/*  656 */         notifyOutboundHandlerException(t, promise);
/*      */       } 
/*      */     } else {
/*  659 */       close(promise);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture deregister(final ChannelPromise promise) {
/*  665 */     if (isNotValidPromise(promise, false))
/*      */     {
/*  667 */       return promise;
/*      */     }
/*      */     
/*  670 */     final AbstractChannelHandlerContext next = findContextOutbound(8192);
/*  671 */     EventExecutor executor = next.executor();
/*  672 */     if (executor.inEventLoop()) {
/*  673 */       next.invokeDeregister(promise);
/*      */     } else {
/*  675 */       safeExecute(executor, new Runnable()
/*      */           {
/*      */             public void run() {
/*  678 */               next.invokeDeregister(promise);
/*      */             }
/*      */           },  promise, null, false);
/*      */     } 
/*      */     
/*  683 */     return promise;
/*      */   }
/*      */   
/*      */   private void invokeDeregister(ChannelPromise promise) {
/*  687 */     if (invokeHandler()) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  692 */         ChannelHandler handler = handler();
/*  693 */         DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  694 */         if (handler == headContext) {
/*  695 */           headContext.deregister(this, promise);
/*  696 */         } else if (handler instanceof ChannelDuplexHandler) {
/*  697 */           ((ChannelDuplexHandler)handler).deregister(this, promise);
/*  698 */         } else if (handler instanceof ChannelOutboundHandlerAdapter) {
/*  699 */           ((ChannelOutboundHandlerAdapter)handler).deregister(this, promise);
/*      */         } else {
/*  701 */           ((ChannelOutboundHandler)handler).deregister(this, promise);
/*      */         } 
/*  703 */       } catch (Throwable t) {
/*  704 */         notifyOutboundHandlerException(t, promise);
/*      */       } 
/*      */     } else {
/*  707 */       deregister(promise);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext read() {
/*  713 */     AbstractChannelHandlerContext next = findContextOutbound(16384);
/*  714 */     if (next.executor().inEventLoop()) {
/*  715 */       if (next.invokeHandler()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  720 */           ChannelHandler handler = next.handler();
/*  721 */           DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  722 */           if (handler == headContext) {
/*  723 */             headContext.read(next);
/*  724 */           } else if (handler instanceof ChannelDuplexHandler) {
/*  725 */             ((ChannelDuplexHandler)handler).read(next);
/*  726 */           } else if (handler instanceof ChannelOutboundHandlerAdapter) {
/*  727 */             ((ChannelOutboundHandlerAdapter)handler).read(next);
/*      */           } else {
/*  729 */             ((ChannelOutboundHandler)handler).read(next);
/*      */           } 
/*  731 */         } catch (Throwable t) {
/*  732 */           invokeExceptionCaught(t);
/*      */         } 
/*      */       } else {
/*  735 */         next.read();
/*      */       } 
/*      */     } else {
/*  738 */       next.executor().execute((getInvokeTasks()).invokeReadTask);
/*      */     } 
/*  740 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture write(Object msg) {
/*  745 */     ChannelPromise promise = newPromise();
/*  746 */     write(msg, false, promise);
/*  747 */     return promise;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture write(Object msg, ChannelPromise promise) {
/*  752 */     write(msg, false, promise);
/*  753 */     return promise;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelHandlerContext flush() {
/*  758 */     AbstractChannelHandlerContext next = findContextOutbound(65536);
/*  759 */     EventExecutor executor = next.executor();
/*  760 */     if (executor.inEventLoop()) {
/*  761 */       next.invokeFlush();
/*      */     } else {
/*  763 */       Tasks tasks = next.invokeTasks;
/*  764 */       if (tasks == null) {
/*  765 */         next.invokeTasks = tasks = new Tasks(next);
/*      */       }
/*  767 */       safeExecute(executor, tasks.invokeFlushTask, channel().voidPromise(), null, false);
/*      */     } 
/*      */     
/*  770 */     return this;
/*      */   }
/*      */   
/*      */   private void invokeFlush() {
/*  774 */     if (invokeHandler()) {
/*  775 */       invokeFlush0();
/*      */     } else {
/*  777 */       flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void invokeFlush0() {
/*      */     try {
/*  786 */       ChannelHandler handler = handler();
/*  787 */       DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  788 */       if (handler == headContext) {
/*  789 */         headContext.flush(this);
/*  790 */       } else if (handler instanceof ChannelDuplexHandler) {
/*  791 */         ((ChannelDuplexHandler)handler).flush(this);
/*  792 */       } else if (handler instanceof ChannelOutboundHandlerAdapter) {
/*  793 */         ((ChannelOutboundHandlerAdapter)handler).flush(this);
/*      */       } else {
/*  795 */         ((ChannelOutboundHandler)handler).flush(this);
/*      */       } 
/*  797 */     } catch (Throwable t) {
/*  798 */       invokeExceptionCaught(t);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/*  804 */     write(msg, true, promise);
/*  805 */     return promise;
/*      */   }
/*      */   
/*      */   void write(Object msg, boolean flush, ChannelPromise promise) {
/*  809 */     if (validateWrite(msg, promise)) {
/*  810 */       AbstractChannelHandlerContext next = findContextOutbound(flush ? 
/*  811 */           98304 : 32768);
/*  812 */       Object m = this.pipeline.touch(msg, next);
/*  813 */       EventExecutor executor = next.executor();
/*  814 */       if (executor.inEventLoop()) {
/*  815 */         if (next.invokeHandler()) {
/*      */ 
/*      */           
/*      */           try {
/*      */             
/*  820 */             ChannelHandler handler = next.handler();
/*  821 */             DefaultChannelPipeline.HeadContext headContext = this.pipeline.head;
/*  822 */             if (handler == headContext) {
/*  823 */               headContext.write(next, msg, promise);
/*  824 */             } else if (handler instanceof ChannelDuplexHandler) {
/*  825 */               ((ChannelDuplexHandler)handler).write(next, msg, promise);
/*  826 */             } else if (handler instanceof ChannelOutboundHandlerAdapter) {
/*  827 */               ((ChannelOutboundHandlerAdapter)handler).write(next, msg, promise);
/*      */             } else {
/*  829 */               ((ChannelOutboundHandler)handler).write(next, msg, promise);
/*      */             } 
/*  831 */           } catch (Throwable t) {
/*  832 */             notifyOutboundHandlerException(t, promise);
/*      */           } 
/*  834 */           if (flush) {
/*  835 */             next.invokeFlush0();
/*      */           }
/*      */         } else {
/*  838 */           next.write(msg, flush, promise);
/*      */         } 
/*      */       } else {
/*  841 */         WriteTask task = WriteTask.newInstance(this, m, promise, flush);
/*  842 */         if (!safeExecute(executor, task, promise, m, !flush))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  847 */           task.cancel();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean validateWrite(Object msg, ChannelPromise promise) {
/*  854 */     ObjectUtil.checkNotNull(msg, "msg");
/*      */     try {
/*  856 */       if (isNotValidPromise(promise, true)) {
/*  857 */         ReferenceCountUtil.release(msg);
/*  858 */         return false;
/*      */       } 
/*  860 */     } catch (RuntimeException e) {
/*  861 */       ReferenceCountUtil.release(msg);
/*  862 */       throw e;
/*      */     } 
/*  864 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture writeAndFlush(Object msg) {
/*  869 */     return writeAndFlush(msg, newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void notifyOutboundHandlerException(Throwable cause, ChannelPromise promise) {
/*  875 */     PromiseNotificationUtil.tryFailure(promise, cause, (promise instanceof VoidChannelPromise) ? null : logger);
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPromise newPromise() {
/*  880 */     return new DefaultChannelPromise(channel(), executor());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelProgressivePromise newProgressivePromise() {
/*  885 */     return new DefaultChannelProgressivePromise(channel(), executor());
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture newSucceededFuture() {
/*  890 */     ChannelFuture succeededFuture = this.succeededFuture;
/*  891 */     if (succeededFuture == null) {
/*  892 */       this.succeededFuture = succeededFuture = new SucceededChannelFuture(channel(), executor());
/*      */     }
/*  894 */     return succeededFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelFuture newFailedFuture(Throwable cause) {
/*  899 */     return new FailedChannelFuture(channel(), executor(), cause);
/*      */   }
/*      */   
/*      */   private boolean isNotValidPromise(ChannelPromise promise, boolean allowVoidPromise) {
/*  903 */     ObjectUtil.checkNotNull(promise, "promise");
/*      */     
/*  905 */     if (promise.isDone()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  910 */       if (promise.isCancelled()) {
/*  911 */         return true;
/*      */       }
/*  913 */       throw new IllegalArgumentException("promise already done: " + promise);
/*      */     } 
/*      */     
/*  916 */     if (promise.channel() != channel()) {
/*  917 */       throw new IllegalArgumentException(String.format("promise.channel does not match: %s (expected: %s)", new Object[] { promise
/*  918 */               .channel(), channel() }));
/*      */     }
/*      */     
/*  921 */     if (promise.getClass() == DefaultChannelPromise.class) {
/*  922 */       return false;
/*      */     }
/*      */     
/*  925 */     if (!allowVoidPromise && promise instanceof VoidChannelPromise) {
/*  926 */       throw new IllegalArgumentException(
/*  927 */           StringUtil.simpleClassName(VoidChannelPromise.class) + " not allowed for this operation");
/*      */     }
/*      */     
/*  930 */     if (promise instanceof AbstractChannel.CloseFuture) {
/*  931 */       throw new IllegalArgumentException(
/*  932 */           StringUtil.simpleClassName(AbstractChannel.CloseFuture.class) + " not allowed in a pipeline");
/*      */     }
/*  934 */     return false;
/*      */   }
/*      */   
/*      */   private AbstractChannelHandlerContext findContextInbound(int mask) {
/*  938 */     AbstractChannelHandlerContext ctx = this;
/*  939 */     EventExecutor currentExecutor = executor();
/*      */     while (true) {
/*  941 */       ctx = ctx.next;
/*  942 */       if (!skipContext(ctx, currentExecutor, mask, 510))
/*  943 */         return ctx; 
/*      */     } 
/*      */   }
/*      */   private AbstractChannelHandlerContext findContextOutbound(int mask) {
/*  947 */     AbstractChannelHandlerContext ctx = this;
/*  948 */     EventExecutor currentExecutor = executor();
/*      */     while (true) {
/*  950 */       ctx = ctx.prev;
/*  951 */       if (!skipContext(ctx, currentExecutor, mask, 130560)) {
/*  952 */         return ctx;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean skipContext(AbstractChannelHandlerContext ctx, EventExecutor currentExecutor, int mask, int onlyMask) {
/*  958 */     return ((ctx.executionMask & (onlyMask | mask)) == 0 || (ctx
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  963 */       .executor() == currentExecutor && (ctx.executionMask & mask) == 0));
/*      */   }
/*      */ 
/*      */   
/*      */   public ChannelPromise voidPromise() {
/*  968 */     return channel().voidPromise();
/*      */   }
/*      */   
/*      */   final void setRemoved() {
/*  972 */     this.handlerState = 3;
/*      */   }
/*      */   
/*      */   final boolean setAddComplete() {
/*      */     while (true) {
/*  977 */       int oldState = this.handlerState;
/*  978 */       if (oldState == 3) {
/*  979 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  984 */       if (HANDLER_STATE_UPDATER.compareAndSet(this, oldState, 2)) {
/*  985 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   final void setAddPending() {
/*  991 */     boolean updated = HANDLER_STATE_UPDATER.compareAndSet(this, 0, 1);
/*  992 */     assert updated;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final void callHandlerAdded() throws Exception {
/*  998 */     if (setAddComplete()) {
/*  999 */       handler().handlerAdded(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final void callHandlerRemoved() throws Exception {
/*      */     try {
/* 1006 */       if (this.handlerState == 2) {
/* 1007 */         handler().handlerRemoved(this);
/*      */       }
/*      */     } finally {
/*      */       
/* 1011 */       setRemoved();
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
/*      */   boolean invokeHandler() {
/* 1025 */     int handlerState = this.handlerState;
/* 1026 */     return (handlerState == 2 || (!this.ordered && handlerState == 1));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRemoved() {
/* 1031 */     return (this.handlerState == 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> Attribute<T> attr(AttributeKey<T> key) {
/* 1036 */     return channel().attr(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> boolean hasAttr(AttributeKey<T> key) {
/* 1041 */     return channel().hasAttr(key);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean safeExecute(EventExecutor executor, Runnable runnable, ChannelPromise promise, Object msg, boolean lazy) {
/*      */     try {
/* 1047 */       if (lazy && executor instanceof AbstractEventExecutor) {
/* 1048 */         ((AbstractEventExecutor)executor).lazyExecute(runnable);
/*      */       } else {
/* 1050 */         executor.execute(runnable);
/*      */       } 
/* 1052 */       return true;
/* 1053 */     } catch (Throwable cause) {
/*      */       try {
/* 1055 */         if (msg != null) {
/* 1056 */           ReferenceCountUtil.release(msg);
/*      */         }
/*      */       } finally {
/* 1059 */         promise.setFailure(cause);
/*      */       } 
/* 1061 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String toHintString() {
/* 1067 */     return '\'' + this.name + "' will handle the message from this point.";
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1072 */     return StringUtil.simpleClassName(ChannelHandlerContext.class) + '(' + this.name + ", " + channel() + ')';
/*      */   }
/*      */   
/*      */   Tasks getInvokeTasks() {
/* 1076 */     Tasks tasks = this.invokeTasks;
/* 1077 */     if (tasks == null) {
/* 1078 */       this.invokeTasks = tasks = new Tasks(this);
/*      */     }
/* 1080 */     return tasks;
/*      */   }
/*      */   
/*      */   static final class WriteTask implements Runnable {
/* 1084 */     private static final Recycler<WriteTask> RECYCLER = new Recycler<WriteTask>()
/*      */       {
/*      */         protected AbstractChannelHandlerContext.WriteTask newObject(Recycler.Handle<AbstractChannelHandlerContext.WriteTask> handle) {
/* 1087 */           return new AbstractChannelHandlerContext.WriteTask((ObjectPool.Handle)handle);
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*      */     static WriteTask newInstance(AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise, boolean flush) {
/* 1093 */       WriteTask task = (WriteTask)RECYCLER.get();
/* 1094 */       init(task, ctx, msg, promise, flush);
/* 1095 */       return task;
/*      */     }
/*      */ 
/*      */     
/* 1099 */     private static final boolean ESTIMATE_TASK_SIZE_ON_SUBMIT = SystemPropertyUtil.getBoolean("io.netty.transport.estimateSizeOnSubmit", true);
/*      */ 
/*      */ 
/*      */     
/* 1103 */     private static final int WRITE_TASK_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.writeTaskSizeOverhead", 32);
/*      */     
/*      */     private final ObjectPool.Handle<WriteTask> handle;
/*      */     private AbstractChannelHandlerContext ctx;
/*      */     private Object msg;
/*      */     private ChannelPromise promise;
/*      */     private int size;
/*      */     
/*      */     private WriteTask(ObjectPool.Handle<WriteTask> handle) {
/* 1112 */       this.handle = handle;
/*      */     }
/*      */ 
/*      */     
/*      */     static void init(WriteTask task, AbstractChannelHandlerContext ctx, Object msg, ChannelPromise promise, boolean flush) {
/* 1117 */       task.ctx = ctx;
/* 1118 */       task.msg = msg;
/* 1119 */       task.promise = promise;
/*      */       
/* 1121 */       if (ESTIMATE_TASK_SIZE_ON_SUBMIT) {
/* 1122 */         task.size = ctx.pipeline.estimatorHandle().size(msg) + WRITE_TASK_OVERHEAD;
/* 1123 */         ctx.pipeline.incrementPendingOutboundBytes(task.size);
/*      */       } else {
/* 1125 */         task.size = 0;
/*      */       } 
/* 1127 */       if (flush) {
/* 1128 */         task.size |= Integer.MIN_VALUE;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/*      */       try {
/* 1135 */         decrementPendingOutboundBytes();
/* 1136 */         this.ctx.write(this.msg, (this.size < 0), this.promise);
/*      */       } finally {
/* 1138 */         recycle();
/*      */       } 
/*      */     }
/*      */     
/*      */     void cancel() {
/*      */       try {
/* 1144 */         decrementPendingOutboundBytes();
/*      */       } finally {
/* 1146 */         recycle();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void decrementPendingOutboundBytes() {
/* 1151 */       if (ESTIMATE_TASK_SIZE_ON_SUBMIT) {
/* 1152 */         this.ctx.pipeline.decrementPendingOutboundBytes((this.size & Integer.MAX_VALUE));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private void recycle() {
/* 1158 */       this.ctx = null;
/* 1159 */       this.msg = null;
/* 1160 */       this.promise = null;
/* 1161 */       this.handle.recycle(this);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class Tasks {
/*      */     final Runnable invokeChannelReadCompleteTask;
/*      */     private final Runnable invokeReadTask;
/*      */     private final Runnable invokeChannelWritableStateChangedTask;
/*      */     private final Runnable invokeFlushTask;
/*      */     
/*      */     Tasks(AbstractChannelHandlerContext ctx) {
/* 1172 */       Objects.requireNonNull(ctx); this.invokeChannelReadCompleteTask = ctx::fireChannelReadComplete;
/* 1173 */       Objects.requireNonNull(ctx); this.invokeReadTask = ctx::read;
/* 1174 */       Objects.requireNonNull(ctx); this.invokeChannelWritableStateChangedTask = ctx::fireChannelWritabilityChanged;
/* 1175 */       Objects.requireNonNull(ctx); this.invokeFlushTask = (() -> rec$.invokeFlush());
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\AbstractChannelHandlerContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */