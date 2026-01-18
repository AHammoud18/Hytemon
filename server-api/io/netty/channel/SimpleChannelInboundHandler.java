/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.TypeParameterMatcher;
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
/*     */ public abstract class SimpleChannelInboundHandler<I>
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   private final boolean autoRelease;
/*     */   
/*     */   protected SimpleChannelInboundHandler() {
/*  51 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleChannelInboundHandler(boolean autoRelease) {
/*  61 */     this.matcher = TypeParameterMatcher.find(this, SimpleChannelInboundHandler.class, "I");
/*  62 */     this.autoRelease = autoRelease;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleChannelInboundHandler(Class<? extends I> inboundMessageType) {
/*  69 */     this(inboundMessageType, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleChannelInboundHandler(Class<? extends I> inboundMessageType, boolean autoRelease) {
/*  80 */     this.matcher = TypeParameterMatcher.get(inboundMessageType);
/*  81 */     this.autoRelease = autoRelease;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptInboundMessage(Object msg) throws Exception {
/*  89 */     return this.matcher.match(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  94 */     boolean release = true;
/*     */     try {
/*  96 */       if (acceptInboundMessage(msg)) {
/*     */         
/*  98 */         I imsg = (I)msg;
/*  99 */         channelRead0(ctx, imsg);
/*     */       } else {
/* 101 */         release = false;
/* 102 */         ctx.fireChannelRead(msg);
/*     */       } 
/*     */     } finally {
/* 105 */       if (this.autoRelease && release)
/* 106 */         ReferenceCountUtil.release(msg); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void channelRead0(ChannelHandlerContext paramChannelHandlerContext, I paramI) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\SimpleChannelInboundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */