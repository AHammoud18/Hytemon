/*    */ package com.hypixel.hytale.server.core.io.netty;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.protocol.io.netty.ProtocolUtil;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RateLimitHandler
/*    */   extends ChannelInboundHandlerAdapter
/*    */ {
/* 18 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*    */   
/*    */   private final int maxTokens;
/*    */   private final int refillRate;
/*    */   private int tokens;
/*    */   private long lastRefillTime;
/*    */   
/*    */   public RateLimitHandler(int maxTokens, int refillRate) {
/* 26 */     this.maxTokens = maxTokens;
/* 27 */     this.refillRate = refillRate;
/* 28 */     this.tokens = maxTokens;
/* 29 */     this.lastRefillTime = System.nanoTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void refillTokens() {
/* 37 */     long now = System.nanoTime();
/* 38 */     long elapsedNanos = now - this.lastRefillTime;
/*    */     
/* 40 */     long tokensToAdd = elapsedNanos * this.refillRate / 1000000000L;
/*    */     
/* 42 */     if (tokensToAdd > 0L) {
/* 43 */       this.tokens = (int)Math.min(this.maxTokens, this.tokens + tokensToAdd);
/* 44 */       this.lastRefillTime = now;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void channelRead(ChannelHandlerContext ctx, Object msg) {
/* 50 */     refillTokens();
/*    */     
/* 52 */     if (this.tokens > 0) {
/* 53 */       this.tokens--;
/* 54 */       ctx.fireChannelRead(msg);
/*    */     } else {
/* 56 */       LOGGER.at(Level.WARNING).log("Rate limit exceeded for %s, disconnecting", 
/* 57 */           NettyUtil.formatRemoteAddress(ctx.channel()));
/*    */       
/* 59 */       ProtocolUtil.closeApplicationConnection(ctx.channel(), 1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\netty\RateLimitHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */