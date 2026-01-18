/*     */ package com.hypixel.hytale.protocol.io.netty;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.handler.codec.quic.QuicChannel;
/*     */ import io.netty.handler.codec.quic.QuicTransportError;
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
/*     */ public final class ProtocolUtil
/*     */ {
/*     */   public static final int APPLICATION_NO_ERROR = 0;
/*     */   public static final int APPLICATION_RATE_LIMITED = 1;
/*     */   public static final int APPLICATION_AUTH_FAILED = 2;
/*     */   public static final int APPLICATION_INVALID_VERSION = 3;
/*  42 */   public static final ChannelFutureListener CLOSE_ON_COMPLETE = ProtocolUtil::closeApplicationOnComplete;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeConnection(@Nonnull Channel channel) {
/*  51 */     closeConnection(channel, QuicTransportError.PROTOCOL_VIOLATION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeConnection(@Nonnull Channel channel, @Nonnull QuicTransportError error) {
/*  62 */     int errorCode = (int)error.code();
/*     */     
/*  64 */     if (channel instanceof QuicChannel) { QuicChannel quicChannel = (QuicChannel)channel;
/*  65 */       quicChannel.close(false, errorCode, Unpooled.EMPTY_BUFFER);
/*     */       
/*     */       return; }
/*     */     
/*  69 */     Channel parent = channel.parent();
/*  70 */     if (parent instanceof QuicChannel) { QuicChannel quicChannel = (QuicChannel)parent;
/*  71 */       quicChannel.close(false, errorCode, Unpooled.EMPTY_BUFFER); }
/*     */     else
/*  73 */     { channel.close(); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeApplicationConnection(@Nonnull Channel channel) {
/*  82 */     closeApplicationConnection(channel, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeApplicationConnection(@Nonnull Channel channel, int errorCode) {
/*  93 */     if (channel instanceof QuicChannel) { QuicChannel quicChannel = (QuicChannel)channel;
/*  94 */       quicChannel.close(true, errorCode, Unpooled.EMPTY_BUFFER);
/*     */       
/*     */       return; }
/*     */     
/*  98 */     Channel parent = channel.parent();
/*  99 */     if (parent instanceof QuicChannel) { QuicChannel quicChannel = (QuicChannel)parent;
/* 100 */       quicChannel.close(true, errorCode, Unpooled.EMPTY_BUFFER); }
/*     */     else
/* 102 */     { channel.close(); }
/*     */   
/*     */   }
/*     */   
/*     */   private static void closeApplicationOnComplete(ChannelFuture future) {
/* 107 */     closeApplicationConnection(future.channel());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\io\netty\ProtocolUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */