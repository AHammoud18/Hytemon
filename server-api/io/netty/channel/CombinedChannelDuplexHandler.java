/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.SocketAddress;
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
/*     */ 
/*     */ public class CombinedChannelDuplexHandler<I extends ChannelInboundHandler, O extends ChannelOutboundHandler>
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(CombinedChannelDuplexHandler.class);
/*     */ 
/*     */   
/*     */   private DelegatingChannelHandlerContext inboundCtx;
/*     */   
/*     */   private DelegatingChannelHandlerContext outboundCtx;
/*     */   
/*     */   private volatile boolean handlerAdded;
/*     */   
/*     */   private I inboundHandler;
/*     */   
/*     */   private O outboundHandler;
/*     */ 
/*     */   
/*     */   protected CombinedChannelDuplexHandler() {
/*  49 */     ensureNotSharable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombinedChannelDuplexHandler(I inboundHandler, O outboundHandler) {
/*  56 */     ensureNotSharable();
/*  57 */     init(inboundHandler, outboundHandler);
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
/*     */   protected final void init(I inboundHandler, O outboundHandler) {
/*  69 */     validate(inboundHandler, outboundHandler);
/*  70 */     this.inboundHandler = inboundHandler;
/*  71 */     this.outboundHandler = outboundHandler;
/*     */   }
/*     */   
/*     */   private void validate(I inboundHandler, O outboundHandler) {
/*  75 */     if (this.inboundHandler != null) {
/*  76 */       throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class
/*  77 */           .getSimpleName() + " was constructed with non-default constructor.");
/*     */     }
/*     */ 
/*     */     
/*  81 */     ObjectUtil.checkNotNull(inboundHandler, "inboundHandler");
/*  82 */     ObjectUtil.checkNotNull(outboundHandler, "outboundHandler");
/*     */     
/*  84 */     if (inboundHandler instanceof ChannelOutboundHandler) {
/*  85 */       throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class
/*     */           
/*  87 */           .getSimpleName() + " to get combined.");
/*     */     }
/*  89 */     if (outboundHandler instanceof ChannelInboundHandler) {
/*  90 */       throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class
/*     */           
/*  92 */           .getSimpleName() + " to get combined.");
/*     */     }
/*     */   }
/*     */   
/*     */   protected final I inboundHandler() {
/*  97 */     return this.inboundHandler;
/*     */   }
/*     */   
/*     */   protected final O outboundHandler() {
/* 101 */     return this.outboundHandler;
/*     */   }
/*     */   
/*     */   private void checkAdded() {
/* 105 */     if (!this.handlerAdded) {
/* 106 */       throw new IllegalStateException("handler not added to pipeline yet");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void removeInboundHandler() {
/* 114 */     checkAdded();
/* 115 */     this.inboundCtx.remove();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void removeOutboundHandler() {
/* 122 */     checkAdded();
/* 123 */     this.outboundCtx.remove();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 128 */     if (this.inboundHandler == null) {
/* 129 */       throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class
/* 130 */           .getSimpleName() + " if " + CombinedChannelDuplexHandler.class
/* 131 */           .getSimpleName() + " was constructed with the default constructor.");
/*     */     }
/*     */ 
/*     */     
/* 135 */     this.outboundCtx = new DelegatingChannelHandlerContext(ctx, (ChannelHandler)this.outboundHandler);
/* 136 */     this.inboundCtx = new DelegatingChannelHandlerContext(ctx, (ChannelHandler)this.inboundHandler)
/*     */       {
/*     */         public ChannelHandlerContext fireExceptionCaught(Throwable cause)
/*     */         {
/* 140 */           if (!CombinedChannelDuplexHandler.this.outboundCtx.removed) {
/*     */ 
/*     */             
/*     */             try {
/* 144 */               CombinedChannelDuplexHandler.this.outboundHandler.exceptionCaught(CombinedChannelDuplexHandler.this.outboundCtx, cause);
/* 145 */             } catch (Throwable error) {
/* 146 */               if (CombinedChannelDuplexHandler.logger.isDebugEnabled()) {
/* 147 */                 CombinedChannelDuplexHandler.logger.debug("An exception was thrown by a user handler's exceptionCaught() method while handling the following exception:", cause);
/*     */ 
/*     */               
/*     */               }
/* 151 */               else if (CombinedChannelDuplexHandler.logger.isWarnEnabled()) {
/* 152 */                 CombinedChannelDuplexHandler.logger.warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a user handler's exceptionCaught() method while handling the following exception:", error, cause);
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 159 */             super.fireExceptionCaught(cause);
/*     */           } 
/* 161 */           return this;
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 167 */     this.handlerAdded = true;
/*     */     
/*     */     try {
/* 170 */       this.inboundHandler.handlerAdded(this.inboundCtx);
/*     */     } finally {
/* 172 */       this.outboundHandler.handlerAdded(this.outboundCtx);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/*     */     try {
/* 179 */       this.inboundCtx.remove();
/*     */     } finally {
/* 181 */       this.outboundCtx.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 187 */     assert ctx == this.inboundCtx.ctx;
/* 188 */     if (!this.inboundCtx.removed) {
/* 189 */       this.inboundHandler.channelRegistered(this.inboundCtx);
/*     */     } else {
/* 191 */       this.inboundCtx.fireChannelRegistered();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
/* 197 */     assert ctx == this.inboundCtx.ctx;
/* 198 */     if (!this.inboundCtx.removed) {
/* 199 */       this.inboundHandler.channelUnregistered(this.inboundCtx);
/*     */     } else {
/* 201 */       this.inboundCtx.fireChannelUnregistered();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 207 */     assert ctx == this.inboundCtx.ctx;
/* 208 */     if (!this.inboundCtx.removed) {
/* 209 */       this.inboundHandler.channelActive(this.inboundCtx);
/*     */     } else {
/* 211 */       this.inboundCtx.fireChannelActive();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 217 */     assert ctx == this.inboundCtx.ctx;
/* 218 */     if (!this.inboundCtx.removed) {
/* 219 */       this.inboundHandler.channelInactive(this.inboundCtx);
/*     */     } else {
/* 221 */       this.inboundCtx.fireChannelInactive();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 227 */     assert ctx == this.inboundCtx.ctx;
/* 228 */     if (!this.inboundCtx.removed) {
/* 229 */       this.inboundHandler.exceptionCaught(this.inboundCtx, cause);
/*     */     } else {
/* 231 */       this.inboundCtx.fireExceptionCaught(cause);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/* 237 */     assert ctx == this.inboundCtx.ctx;
/* 238 */     if (!this.inboundCtx.removed) {
/* 239 */       this.inboundHandler.userEventTriggered(this.inboundCtx, evt);
/*     */     } else {
/* 241 */       this.inboundCtx.fireUserEventTriggered(evt);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 247 */     assert ctx == this.inboundCtx.ctx;
/* 248 */     if (!this.inboundCtx.removed) {
/* 249 */       this.inboundHandler.channelRead(this.inboundCtx, msg);
/*     */     } else {
/* 251 */       this.inboundCtx.fireChannelRead(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 257 */     assert ctx == this.inboundCtx.ctx;
/* 258 */     if (!this.inboundCtx.removed) {
/* 259 */       this.inboundHandler.channelReadComplete(this.inboundCtx);
/*     */     } else {
/* 261 */       this.inboundCtx.fireChannelReadComplete();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
/* 267 */     assert ctx == this.inboundCtx.ctx;
/* 268 */     if (!this.inboundCtx.removed) {
/* 269 */       this.inboundHandler.channelWritabilityChanged(this.inboundCtx);
/*     */     } else {
/* 271 */       this.inboundCtx.fireChannelWritabilityChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 279 */     assert ctx == this.outboundCtx.ctx;
/* 280 */     if (!this.outboundCtx.removed) {
/* 281 */       this.outboundHandler.bind(this.outboundCtx, localAddress, promise);
/*     */     } else {
/* 283 */       this.outboundCtx.bind(localAddress, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 292 */     assert ctx == this.outboundCtx.ctx;
/* 293 */     if (!this.outboundCtx.removed) {
/* 294 */       this.outboundHandler.connect(this.outboundCtx, remoteAddress, localAddress, promise);
/*     */     } else {
/* 296 */       this.outboundCtx.connect(remoteAddress, localAddress, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 302 */     assert ctx == this.outboundCtx.ctx;
/* 303 */     if (!this.outboundCtx.removed) {
/* 304 */       this.outboundHandler.disconnect(this.outboundCtx, promise);
/*     */     } else {
/* 306 */       this.outboundCtx.disconnect(promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 312 */     assert ctx == this.outboundCtx.ctx;
/* 313 */     if (!this.outboundCtx.removed) {
/* 314 */       this.outboundHandler.close(this.outboundCtx, promise);
/*     */     } else {
/* 316 */       this.outboundCtx.close(promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 322 */     assert ctx == this.outboundCtx.ctx;
/* 323 */     if (!this.outboundCtx.removed) {
/* 324 */       this.outboundHandler.deregister(this.outboundCtx, promise);
/*     */     } else {
/* 326 */       this.outboundCtx.deregister(promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 332 */     assert ctx == this.outboundCtx.ctx;
/* 333 */     if (!this.outboundCtx.removed) {
/* 334 */       this.outboundHandler.read(this.outboundCtx);
/*     */     } else {
/* 336 */       this.outboundCtx.read();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 342 */     assert ctx == this.outboundCtx.ctx;
/* 343 */     if (!this.outboundCtx.removed) {
/* 344 */       this.outboundHandler.write(this.outboundCtx, msg, promise);
/*     */     } else {
/* 346 */       this.outboundCtx.write(msg, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 352 */     assert ctx == this.outboundCtx.ctx;
/* 353 */     if (!this.outboundCtx.removed) {
/* 354 */       this.outboundHandler.flush(this.outboundCtx);
/*     */     } else {
/* 356 */       this.outboundCtx.flush();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class DelegatingChannelHandlerContext
/*     */     implements ChannelHandlerContext {
/*     */     private final ChannelHandlerContext ctx;
/*     */     private final ChannelHandler handler;
/*     */     boolean removed;
/*     */     
/*     */     DelegatingChannelHandlerContext(ChannelHandlerContext ctx, ChannelHandler handler) {
/* 367 */       this.ctx = ctx;
/* 368 */       this.handler = handler;
/*     */     }
/*     */ 
/*     */     
/*     */     public Channel channel() {
/* 373 */       return this.ctx.channel();
/*     */     }
/*     */ 
/*     */     
/*     */     public EventExecutor executor() {
/* 378 */       return this.ctx.executor();
/*     */     }
/*     */ 
/*     */     
/*     */     public String name() {
/* 383 */       return this.ctx.name();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandler handler() {
/* 388 */       return this.ctx.handler();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRemoved() {
/* 393 */       return (this.removed || this.ctx.isRemoved());
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext fireChannelRegistered() {
/* 398 */       this.ctx.fireChannelRegistered();
/* 399 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext fireChannelUnregistered() {
/* 404 */       this.ctx.fireChannelUnregistered();
/* 405 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext fireChannelActive() {
/* 410 */       this.ctx.fireChannelActive();
/* 411 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext fireChannelInactive() {
/* 416 */       this.ctx.fireChannelInactive();
/* 417 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
/* 422 */       this.ctx.fireExceptionCaught(cause);
/* 423 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext fireUserEventTriggered(Object event) {
/* 428 */       this.ctx.fireUserEventTriggered(event);
/* 429 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext fireChannelRead(Object msg) {
/* 434 */       this.ctx.fireChannelRead(msg);
/* 435 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext fireChannelReadComplete() {
/* 440 */       this.ctx.fireChannelReadComplete();
/* 441 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext fireChannelWritabilityChanged() {
/* 446 */       this.ctx.fireChannelWritabilityChanged();
/* 447 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture bind(SocketAddress localAddress) {
/* 452 */       return this.ctx.bind(localAddress);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture connect(SocketAddress remoteAddress) {
/* 457 */       return this.ctx.connect(remoteAddress);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/* 462 */       return this.ctx.connect(remoteAddress, localAddress);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture disconnect() {
/* 467 */       return this.ctx.disconnect();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture close() {
/* 472 */       return this.ctx.close();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture deregister() {
/* 477 */       return this.ctx.deregister();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
/* 482 */       return this.ctx.bind(localAddress, promise);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
/* 487 */       return this.ctx.connect(remoteAddress, promise);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 493 */       return this.ctx.connect(remoteAddress, localAddress, promise);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture disconnect(ChannelPromise promise) {
/* 498 */       return this.ctx.disconnect(promise);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture close(ChannelPromise promise) {
/* 503 */       return this.ctx.close(promise);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture deregister(ChannelPromise promise) {
/* 508 */       return this.ctx.deregister(promise);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext read() {
/* 513 */       this.ctx.read();
/* 514 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture write(Object msg) {
/* 519 */       return this.ctx.write(msg);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture write(Object msg, ChannelPromise promise) {
/* 524 */       return this.ctx.write(msg, promise);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelHandlerContext flush() {
/* 529 */       this.ctx.flush();
/* 530 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
/* 535 */       return this.ctx.writeAndFlush(msg, promise);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture writeAndFlush(Object msg) {
/* 540 */       return this.ctx.writeAndFlush(msg);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPipeline pipeline() {
/* 545 */       return this.ctx.pipeline();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBufAllocator alloc() {
/* 550 */       return this.ctx.alloc();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPromise newPromise() {
/* 555 */       return this.ctx.newPromise();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelProgressivePromise newProgressivePromise() {
/* 560 */       return this.ctx.newProgressivePromise();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture newSucceededFuture() {
/* 565 */       return this.ctx.newSucceededFuture();
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelFuture newFailedFuture(Throwable cause) {
/* 570 */       return this.ctx.newFailedFuture(cause);
/*     */     }
/*     */ 
/*     */     
/*     */     public ChannelPromise voidPromise() {
/* 575 */       return this.ctx.voidPromise();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> Attribute<T> attr(AttributeKey<T> key) {
/* 580 */       return this.ctx.channel().attr(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> boolean hasAttr(AttributeKey<T> key) {
/* 585 */       return this.ctx.channel().hasAttr(key);
/*     */     }
/*     */     
/*     */     final void remove() {
/* 589 */       EventExecutor executor = executor();
/* 590 */       if (executor.inEventLoop()) {
/* 591 */         remove0();
/*     */       } else {
/* 593 */         executor.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 596 */                 CombinedChannelDuplexHandler.DelegatingChannelHandlerContext.this.remove0();
/*     */               }
/*     */             });
/*     */       } 
/*     */     }
/*     */     
/*     */     private void remove0() {
/* 603 */       if (!this.removed) {
/* 604 */         this.removed = true;
/*     */         try {
/* 606 */           this.handler.handlerRemoved(this);
/* 607 */         } catch (Throwable cause) {
/* 608 */           fireExceptionCaught(new ChannelPipelineException(this.handler
/* 609 */                 .getClass().getName() + ".handlerRemoved() has thrown an exception.", cause));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\CombinedChannelDuplexHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */