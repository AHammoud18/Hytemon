/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.List;
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
/*     */ public class OptionalSslHandler
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final SslContext sslContext;
/*     */   
/*     */   public OptionalSslHandler(SslContext sslContext) {
/*  39 */     this.sslContext = (SslContext)ObjectUtil.checkNotNull(sslContext, "sslContext");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws Exception {
/*  44 */     if (in.readableBytes() < 5) {
/*     */       return;
/*     */     }
/*  47 */     if (SslHandler.isEncrypted(in, false)) {
/*  48 */       handleSsl(context);
/*     */     } else {
/*  50 */       handleNonSsl(context);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleSsl(ChannelHandlerContext context) {
/*  55 */     SslHandler sslHandler = null;
/*     */     try {
/*  57 */       sslHandler = newSslHandler(context, this.sslContext);
/*  58 */       context.pipeline().replace((ChannelHandler)this, newSslHandlerName(), (ChannelHandler)sslHandler);
/*  59 */       sslHandler = null;
/*     */     }
/*     */     finally {
/*     */       
/*  63 */       if (sslHandler != null) {
/*  64 */         ReferenceCountUtil.safeRelease(sslHandler.engine());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleNonSsl(ChannelHandlerContext context) {
/*  70 */     ChannelHandler handler = newNonSslHandler(context);
/*  71 */     if (handler != null) {
/*  72 */       context.pipeline().replace((ChannelHandler)this, newNonSslHandlerName(), handler);
/*     */     } else {
/*  74 */       context.pipeline().remove((ChannelHandler)this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String newSslHandlerName() {
/*  83 */     return null;
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
/*     */   protected SslHandler newSslHandler(ChannelHandlerContext context, SslContext sslContext) {
/*  97 */     return sslContext.newHandler(context.alloc());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String newNonSslHandlerName() {
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChannelHandler newNonSslHandler(ChannelHandlerContext context) {
/* 115 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OptionalSslHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */