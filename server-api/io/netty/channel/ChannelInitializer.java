/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ @Sharable
/*     */ public abstract class ChannelInitializer<C extends Channel>
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*  56 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
/*     */ 
/*     */   
/*  59 */   private final Set<ChannelHandlerContext> initMap = ConcurrentHashMap.newKeySet();
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
/*     */   public final void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/*  77 */     if (initChannel(ctx)) {
/*     */ 
/*     */       
/*  80 */       ctx.pipeline().fireChannelRegistered();
/*     */ 
/*     */       
/*  83 */       removeState(ctx);
/*     */     } else {
/*     */       
/*  86 */       ctx.fireChannelRegistered();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/*  95 */     if (logger.isWarnEnabled()) {
/*  96 */       logger.warn("Failed to initialize a channel. Closing: " + ctx.channel(), cause);
/*     */     }
/*  98 */     ctx.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 106 */     if (ctx.channel().isRegistered())
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 111 */       if (initChannel(ctx))
/*     */       {
/*     */         
/* 114 */         removeState(ctx);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 121 */     this.initMap.remove(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean initChannel(ChannelHandlerContext ctx) throws Exception {
/* 126 */     if (this.initMap.add(ctx)) {
/*     */       try {
/* 128 */         initChannel((C)ctx.channel());
/* 129 */       } catch (Throwable cause) {
/*     */ 
/*     */         
/* 132 */         exceptionCaught(ctx, cause);
/*     */       } finally {
/* 134 */         if (!ctx.isRemoved()) {
/* 135 */           ctx.pipeline().remove(this);
/*     */         }
/*     */       } 
/* 138 */       return true;
/*     */     } 
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeState(final ChannelHandlerContext ctx) {
/* 145 */     if (ctx.isRemoved()) {
/* 146 */       this.initMap.remove(ctx);
/*     */     }
/*     */     else {
/*     */       
/* 150 */       ctx.executor().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 153 */               ChannelInitializer.this.initMap.remove(ctx);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void initChannel(C paramC) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */