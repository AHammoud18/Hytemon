/*     */ package io.netty.handler.timeout;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReadTimeoutHandler
/*     */   extends IdleStateHandler
/*     */ {
/*     */   private boolean closed;
/*     */   
/*     */   public ReadTimeoutHandler(int timeoutSeconds) {
/*  72 */     this(timeoutSeconds, TimeUnit.SECONDS);
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
/*     */   public ReadTimeoutHandler(long timeout, TimeUnit unit) {
/*  84 */     super(timeout, 0L, 0L, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
/*  89 */     assert evt.state() == IdleState.READER_IDLE;
/*  90 */     readTimedOut(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
/*  97 */     if (!this.closed) {
/*  98 */       ctx.fireExceptionCaught((Throwable)ReadTimeoutException.INSTANCE);
/*  99 */       ctx.close();
/* 100 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\timeout\ReadTimeoutHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */