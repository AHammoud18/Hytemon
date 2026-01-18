/*     */ package com.hypixel.hytale.server.core.io.netty;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.io.PacketStatsRecorder;
/*     */ import com.hypixel.hytale.protocol.io.netty.PacketDecoder;
/*     */ import com.hypixel.hytale.protocol.io.netty.PacketEncoder;
/*     */ import com.hypixel.hytale.protocol.io.netty.ProtocolUtil;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Disconnect;
/*     */ import com.hypixel.hytale.protocol.packets.connection.DisconnectType;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.PacketStatsRecorderImpl;
/*     */ import com.hypixel.hytale.server.core.io.handlers.InitialPacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.transport.QUICTransport;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.handler.codec.quic.QuicChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.time.Duration;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class HytaleChannelInitializer
/*     */   extends ChannelInitializer<Channel>
/*     */ {
/*     */   protected void initChannel(Channel channel) {
/*  36 */     if (channel instanceof QuicStreamChannel) { QuicStreamChannel quicStreamChannel = (QuicStreamChannel)channel;
/*  37 */       HytaleLogger.getLogger().at(Level.INFO).log("Received stream %s to %s", 
/*  38 */           NettyUtil.formatRemoteAddress(channel), 
/*  39 */           NettyUtil.formatLocalAddress(channel));
/*     */ 
/*     */ 
/*     */       
/*  43 */       QuicChannel parentChannel = quicStreamChannel.parent();
/*  44 */       X509Certificate clientCert = (X509Certificate)parentChannel.attr(QUICTransport.CLIENT_CERTIFICATE_ATTR).get();
/*  45 */       if (clientCert != null) {
/*  46 */         channel.attr(QUICTransport.CLIENT_CERTIFICATE_ATTR).set(clientCert);
/*  47 */         HytaleLogger.getLogger().at(Level.FINE).log("Copied client certificate to stream: %s", clientCert
/*  48 */             .getSubjectX500Principal().getName());
/*     */       }  }
/*     */     else
/*  51 */     { HytaleLogger.getLogger().at(Level.INFO).log("Received connection from %s to %s", 
/*  52 */           NettyUtil.formatRemoteAddress(channel), 
/*  53 */           NettyUtil.formatLocalAddress(channel)); }
/*     */ 
/*     */ 
/*     */     
/*  57 */     PacketStatsRecorderImpl statsRecorder = new PacketStatsRecorderImpl();
/*  58 */     channel.attr(PacketStatsRecorder.CHANNEL_KEY).set(statsRecorder);
/*     */ 
/*     */ 
/*     */     
/*  62 */     Duration initialTimeout = HytaleServer.get().getConfig().getConnectionTimeouts().getInitialTimeout();
/*  63 */     channel.pipeline().addLast("timeOut", (ChannelHandler)new ReadTimeoutHandler(initialTimeout.toMillis(), TimeUnit.MILLISECONDS));
/*  64 */     channel.pipeline().addLast("packetDecoder", (ChannelHandler)new PacketDecoder());
/*     */ 
/*     */     
/*  67 */     HytaleServerConfig.RateLimitConfig rateLimitConfig = HytaleServer.get().getConfig().getRateLimitConfig();
/*  68 */     if (rateLimitConfig.isEnabled()) {
/*  69 */       channel.pipeline().addLast("rateLimit", (ChannelHandler)new RateLimitHandler(rateLimitConfig
/*  70 */             .getBurstCapacity(), rateLimitConfig.getPacketsPerSecond()));
/*     */     }
/*     */ 
/*     */     
/*  74 */     channel.pipeline().addLast("packetEncoder", (ChannelHandler)new PacketEncoder());
/*  75 */     channel.pipeline().addLast("packetArrayEncoder", (ChannelHandler)NettyUtil.PACKET_ARRAY_ENCODER_INSTANCE);
/*     */     
/*  77 */     if (NettyUtil.PACKET_LOGGER.getLevel() != Level.OFF) {
/*  78 */       channel.pipeline().addLast("logger", (ChannelHandler)NettyUtil.LOGGER);
/*     */     }
/*     */ 
/*     */     
/*  82 */     InitialPacketHandler playerConnection = new InitialPacketHandler(channel);
/*  83 */     channel.pipeline().addLast("handler", (ChannelHandler)new PlayerChannelHandler((PacketHandler)playerConnection));
/*     */ 
/*     */     
/*  86 */     channel.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new ExceptionHandler() });
/*     */     
/*  88 */     playerConnection.registered(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(@Nonnull ChannelHandlerContext ctx, Throwable cause) {
/*  93 */     ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.WARNING).withCause(cause)).log("Got exception from netty pipeline in HytaleChannelInitializer!");
/*  94 */     if (ctx.channel().isWritable()) {
/*  95 */       ctx.channel().writeAndFlush(new Disconnect("Internal server error!", DisconnectType.Crash))
/*  96 */         .addListener((GenericFutureListener)ProtocolUtil.CLOSE_ON_COMPLETE);
/*     */     } else {
/*  98 */       ProtocolUtil.closeApplicationConnection(ctx.channel());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void channelInactive(@Nonnull ChannelHandlerContext ctx) throws Exception {
/* 105 */     ProtocolUtil.closeApplicationConnection(ctx.channel());
/* 106 */     super.channelInactive(ctx);
/*     */   }
/*     */   
/*     */   private static class ExceptionHandler extends ChannelInboundHandlerAdapter {
/* 110 */     private final AtomicBoolean handled = new AtomicBoolean();
/*     */     
/*     */     public void exceptionCaught(@Nonnull ChannelHandlerContext ctx, Throwable cause) {
/*     */       String identifier;
/* 114 */       if (cause instanceof java.nio.channels.ClosedChannelException) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 120 */       ChannelHandler handler = ctx.pipeline().get("handler");
/* 121 */       if (handler instanceof PlayerChannelHandler) {
/* 122 */         identifier = ((PlayerChannelHandler)handler).getHandler().getIdentifier();
/*     */       } else {
/* 124 */         identifier = NettyUtil.formatRemoteAddress(ctx.channel());
/*     */       } 
/*     */       
/* 127 */       if (this.handled.getAndSet(true)) {
/* 128 */         if (cause instanceof java.io.IOException && cause.getMessage() != null) {
/* 129 */           switch (cause.getMessage()) {
/*     */             case "Broken pipe":
/*     */             case "Connection reset by peer":
/*     */             case "An existing connection was forcibly closed by the remote host":
/*     */               return;
/*     */           } 
/*     */         
/*     */         }
/* 137 */         ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.WARNING).withCause(cause)).log("Already handled exception in ExceptionHandler but got another!");
/*     */         
/*     */         return;
/*     */       } 
/* 141 */       if (cause instanceof io.netty.handler.timeout.TimeoutException) {
/* 142 */         boolean readTimeout = cause instanceof io.netty.handler.timeout.ReadTimeoutException;
/* 143 */         boolean writeTimeout = cause instanceof io.netty.handler.timeout.WriteTimeoutException;
/*     */         
/* 145 */         String msg = readTimeout ? "Read timeout for %s" : (writeTimeout ? "Write timeout for %s" : "Connection timeout for %s");
/* 146 */         HytaleLogger.getLogger().at(Level.INFO).log(msg, identifier);
/* 147 */         ((HytaleLogger.Api)NettyUtil.CONNECTION_EXCEPTION_LOGGER.at(Level.FINE).withCause(cause)).log(msg, identifier);
/*     */         
/* 149 */         if (ctx.channel().isWritable()) {
/* 150 */           ctx.channel().writeAndFlush(new Disconnect(readTimeout ? "Read timeout" : (writeTimeout ? "Write timeout" : "Connection timeout"), DisconnectType.Crash))
/* 151 */             .addListener((GenericFutureListener)ProtocolUtil.CLOSE_ON_COMPLETE);
/*     */         } else {
/* 153 */           ProtocolUtil.closeApplicationConnection(ctx.channel());
/*     */         } 
/*     */         return;
/*     */       } 
/* 157 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(cause)).log("Got exception from netty pipeline in ExceptionHandler: %s", cause.getMessage());
/*     */ 
/*     */       
/* 160 */       if (ctx.channel().isWritable()) {
/* 161 */         ctx.channel().writeAndFlush(new Disconnect("Internal server error!", DisconnectType.Crash))
/* 162 */           .addListener((GenericFutureListener)ProtocolUtil.CLOSE_ON_COMPLETE);
/*     */       } else {
/* 164 */         ProtocolUtil.closeApplicationConnection(ctx.channel());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\netty\HytaleChannelInitializer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */