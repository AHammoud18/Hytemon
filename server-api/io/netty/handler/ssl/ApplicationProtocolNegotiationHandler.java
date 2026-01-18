/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.RecyclableArrayList;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public abstract class ApplicationProtocolNegotiationHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*  70 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ApplicationProtocolNegotiationHandler.class);
/*     */   
/*     */   private final String fallbackProtocol;
/*  73 */   private final RecyclableArrayList bufferedMessages = RecyclableArrayList.newInstance();
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx;
/*     */ 
/*     */   
/*     */   private boolean sslHandlerChecked;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ApplicationProtocolNegotiationHandler(String fallbackProtocol) {
/*  84 */     this.fallbackProtocol = (String)ObjectUtil.checkNotNull(fallbackProtocol, "fallbackProtocol");
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  89 */     this.ctx = ctx;
/*  90 */     super.handlerAdded(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/*  95 */     fireBufferedMessages();
/*  96 */     this.bufferedMessages.recycle();
/*  97 */     super.handlerRemoved(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 103 */     this.bufferedMessages.add(msg);
/* 104 */     if (!this.sslHandlerChecked) {
/* 105 */       this.sslHandlerChecked = true;
/* 106 */       if (ctx.pipeline().get(SslHandler.class) == null)
/*     */       {
/*     */         
/* 109 */         removeSelfIfPresent(ctx);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireBufferedMessages() {
/* 118 */     if (!this.bufferedMessages.isEmpty()) {
/* 119 */       for (int i = 0; i < this.bufferedMessages.size(); i++) {
/* 120 */         this.ctx.fireChannelRead(this.bufferedMessages.get(i));
/*     */       }
/* 122 */       this.ctx.fireChannelReadComplete();
/* 123 */       this.bufferedMessages.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/* 129 */     if (evt instanceof SslHandshakeCompletionEvent) {
/* 130 */       SslHandshakeCompletionEvent handshakeEvent = (SslHandshakeCompletionEvent)evt;
/*     */       try {
/* 132 */         if (handshakeEvent.isSuccess()) {
/* 133 */           SslHandler sslHandler = (SslHandler)ctx.pipeline().get(SslHandler.class);
/* 134 */           if (sslHandler == null) {
/* 135 */             throw new IllegalStateException("cannot find an SslHandler in the pipeline (required for application-level protocol negotiation)");
/*     */           }
/*     */           
/* 138 */           String protocol = sslHandler.applicationProtocol();
/* 139 */           configurePipeline(ctx, (protocol != null) ? protocol : this.fallbackProtocol);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 147 */       catch (Throwable cause) {
/* 148 */         exceptionCaught(ctx, cause);
/*     */       } finally {
/*     */         
/* 151 */         if (handshakeEvent.isSuccess()) {
/* 152 */           removeSelfIfPresent(ctx);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     if (evt instanceof io.netty.channel.socket.ChannelInputShutdownEvent) {
/* 158 */       fireBufferedMessages();
/*     */     }
/*     */     
/* 161 */     ctx.fireUserEventTriggered(evt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 166 */     fireBufferedMessages();
/* 167 */     super.channelInactive(ctx);
/*     */   }
/*     */   
/*     */   private void removeSelfIfPresent(ChannelHandlerContext ctx) {
/* 171 */     ChannelPipeline pipeline = ctx.pipeline();
/* 172 */     if (!ctx.isRemoved()) {
/* 173 */       pipeline.remove((ChannelHandler)this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void configurePipeline(ChannelHandlerContext paramChannelHandlerContext, String paramString) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handshakeFailure(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 191 */     logger.warn("{} TLS handshake failed:", ctx.channel(), cause);
/* 192 */     ctx.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/*     */     Throwable wrapped;
/* 198 */     if (cause instanceof io.netty.handler.codec.DecoderException && wrapped = cause.getCause() instanceof javax.net.ssl.SSLException) {
/*     */       try {
/* 200 */         handshakeFailure(ctx, wrapped);
/*     */         return;
/*     */       } finally {
/* 203 */         removeSelfIfPresent(ctx);
/*     */       } 
/*     */     }
/* 206 */     logger.warn("{} Failed to select the application-level protocol:", ctx.channel(), cause);
/* 207 */     ctx.fireExceptionCaught(cause);
/* 208 */     ctx.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ApplicationProtocolNegotiationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */