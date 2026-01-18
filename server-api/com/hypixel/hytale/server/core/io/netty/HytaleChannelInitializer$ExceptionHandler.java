/*     */ package com.hypixel.hytale.server.core.io.netty;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.io.netty.ProtocolUtil;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Disconnect;
/*     */ import com.hypixel.hytale.protocol.packets.connection.DisconnectType;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
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
/*     */ class ExceptionHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/* 110 */   private final AtomicBoolean handled = new AtomicBoolean();
/*     */   
/*     */   public void exceptionCaught(@Nonnull ChannelHandlerContext ctx, Throwable cause) {
/*     */     String identifier;
/* 114 */     if (cause instanceof java.nio.channels.ClosedChannelException) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 120 */     ChannelHandler handler = ctx.pipeline().get("handler");
/* 121 */     if (handler instanceof PlayerChannelHandler) {
/* 122 */       identifier = ((PlayerChannelHandler)handler).getHandler().getIdentifier();
/*     */     } else {
/* 124 */       identifier = NettyUtil.formatRemoteAddress(ctx.channel());
/*     */     } 
/*     */     
/* 127 */     if (this.handled.getAndSet(true)) {
/* 128 */       if (cause instanceof java.io.IOException && cause.getMessage() != null) {
/* 129 */         switch (cause.getMessage()) {
/*     */           case "Broken pipe":
/*     */           case "Connection reset by peer":
/*     */           case "An existing connection was forcibly closed by the remote host":
/*     */             return;
/*     */         } 
/*     */       
/*     */       }
/* 137 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.WARNING).withCause(cause)).log("Already handled exception in ExceptionHandler but got another!");
/*     */       
/*     */       return;
/*     */     } 
/* 141 */     if (cause instanceof io.netty.handler.timeout.TimeoutException) {
/* 142 */       boolean readTimeout = cause instanceof io.netty.handler.timeout.ReadTimeoutException;
/* 143 */       boolean writeTimeout = cause instanceof io.netty.handler.timeout.WriteTimeoutException;
/*     */       
/* 145 */       String msg = readTimeout ? "Read timeout for %s" : (writeTimeout ? "Write timeout for %s" : "Connection timeout for %s");
/* 146 */       HytaleLogger.getLogger().at(Level.INFO).log(msg, identifier);
/* 147 */       ((HytaleLogger.Api)NettyUtil.CONNECTION_EXCEPTION_LOGGER.at(Level.FINE).withCause(cause)).log(msg, identifier);
/*     */       
/* 149 */       if (ctx.channel().isWritable()) {
/* 150 */         ctx.channel().writeAndFlush(new Disconnect(readTimeout ? "Read timeout" : (writeTimeout ? "Write timeout" : "Connection timeout"), DisconnectType.Crash))
/* 151 */           .addListener((GenericFutureListener)ProtocolUtil.CLOSE_ON_COMPLETE);
/*     */       } else {
/* 153 */         ProtocolUtil.closeApplicationConnection(ctx.channel());
/*     */       } 
/*     */       return;
/*     */     } 
/* 157 */     ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(cause)).log("Got exception from netty pipeline in ExceptionHandler: %s", cause.getMessage());
/*     */ 
/*     */     
/* 160 */     if (ctx.channel().isWritable()) {
/* 161 */       ctx.channel().writeAndFlush(new Disconnect("Internal server error!", DisconnectType.Crash))
/* 162 */         .addListener((GenericFutureListener)ProtocolUtil.CLOSE_ON_COMPLETE);
/*     */     } else {
/* 164 */       ProtocolUtil.closeApplicationConnection(ctx.channel());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\netty\HytaleChannelInitializer$ExceptionHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */