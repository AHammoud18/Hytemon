/*    */ package com.hypixel.hytale.server.core.io.transport;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.io.netty.HytaleChannelInitializer;
/*    */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*    */ import io.netty.bootstrap.ServerBootstrap;
/*    */ import io.netty.channel.ChannelFuture;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelOption;
/*    */ import io.netty.channel.EventLoopGroup;
/*    */ import io.netty.channel.ServerChannel;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class TCPTransport
/*    */   implements Transport {
/* 19 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 28 */   private final EventLoopGroup bossGroup = NettyUtil.getEventLoopGroup(1, "ServerBossGroup"); @Nonnull
/* 29 */   private final EventLoopGroup workerGroup = NettyUtil.getEventLoopGroup("ServerWorkerGroup");
/*    */   public TCPTransport() throws InterruptedException {
/* 31 */     Class<? extends ServerChannel> serverChannel = NettyUtil.getServerChannel();
/* 32 */     LOGGER.at(Level.INFO).log("Using Server Channel: %s...", serverChannel.getSimpleName());
/* 33 */     this
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 39 */       .bootstrap = ((ServerBootstrap)((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).group(this.bossGroup, this.workerGroup).channel(serverChannel)).option(ChannelOption.SO_BACKLOG, Integer.valueOf(256))).option(ChannelOption.SO_REUSEADDR, Boolean.valueOf(true))).childHandler((ChannelHandler)new HytaleChannelInitializer()).validate();
/*    */     
/* 41 */     this.bootstrap.register().sync();
/*    */   }
/*    */   private final ServerBootstrap bootstrap;
/*    */   
/*    */   @Nonnull
/*    */   public TransportType getType() {
/* 47 */     return TransportType.TCP;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture bind(InetSocketAddress address) throws InterruptedException {
/* 52 */     return this.bootstrap.bind(address).sync();
/*    */   }
/*    */ 
/*    */   
/*    */   public void shutdown() {
/* 57 */     LOGGER.at(Level.INFO).log("Shutting down bossGroup...");
/*    */     try {
/* 59 */       this.bossGroup.shutdownGracefully(0L, 1L, TimeUnit.SECONDS).await(1L, TimeUnit.SECONDS);
/* 60 */     } catch (InterruptedException e) {
/* 61 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to await for listener to close!");
/* 62 */       Thread.currentThread().interrupt();
/*    */     } 
/* 64 */     LOGGER.at(Level.INFO).log("Shutting down workerGroup...");
/*    */     try {
/* 66 */       this.workerGroup.shutdownGracefully(0L, 1L, TimeUnit.SECONDS).await(1L, TimeUnit.SECONDS);
/* 67 */     } catch (InterruptedException e) {
/* 68 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to await for listener to close!");
/* 69 */       Thread.currentThread().interrupt();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\transport\TCPTransport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */