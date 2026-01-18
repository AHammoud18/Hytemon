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
/*     */ public abstract class SimpleUserEventChannelHandler<I>
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   private final boolean autoRelease;
/*     */   
/*     */   protected SimpleUserEventChannelHandler() {
/*  51 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleUserEventChannelHandler(boolean autoRelease) {
/*  61 */     this.matcher = TypeParameterMatcher.find(this, SimpleUserEventChannelHandler.class, "I");
/*  62 */     this.autoRelease = autoRelease;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleUserEventChannelHandler(Class<? extends I> eventType) {
/*  69 */     this(eventType, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleUserEventChannelHandler(Class<? extends I> eventType, boolean autoRelease) {
/*  80 */     this.matcher = TypeParameterMatcher.get(eventType);
/*  81 */     this.autoRelease = autoRelease;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean acceptEvent(Object evt) throws Exception {
/*  89 */     return this.matcher.match(evt);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/*  94 */     boolean release = true;
/*     */     try {
/*  96 */       if (acceptEvent(evt)) {
/*     */         
/*  98 */         I ievt = (I)evt;
/*  99 */         eventReceived(ctx, ievt);
/*     */       } else {
/* 101 */         release = false;
/* 102 */         ctx.fireUserEventTriggered(evt);
/*     */       } 
/*     */     } finally {
/* 105 */       if (this.autoRelease && release)
/* 106 */         ReferenceCountUtil.release(evt); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void eventReceived(ChannelHandlerContext paramChannelHandlerContext, I paramI) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\SimpleUserEventChannelHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */