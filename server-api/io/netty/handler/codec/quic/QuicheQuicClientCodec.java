/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelPromise;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.SocketAddress;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class QuicheQuicClientCodec
/*    */   extends QuicheQuicCodec
/*    */ {
/*    */   private final Function<QuicChannel, ? extends QuicSslEngine> sslEngineProvider;
/*    */   private final Executor sslTaskExecutor;
/*    */   
/*    */   QuicheQuicClientCodec(QuicheConfig config, Function<QuicChannel, ? extends QuicSslEngine> sslEngineProvider, Executor sslTaskExecutor, int localConnIdLength, FlushStrategy flushStrategy) {
/* 42 */     super(config, localConnIdLength, flushStrategy);
/* 43 */     this.sslEngineProvider = sslEngineProvider;
/* 44 */     this.sslTaskExecutor = sslTaskExecutor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected QuicheQuicChannel quicPacketRead(ChannelHandlerContext ctx, InetSocketAddress sender, InetSocketAddress recipient, QuicPacketType type, long version, ByteBuf scid, ByteBuf dcid, ByteBuf token, ByteBuf senderSockaddrMemory, ByteBuf recipientSockaddrMemory, Consumer<QuicheQuicChannel> freeTask, int localConnIdLength, QuicheConfig config) {
/* 54 */     ByteBuffer key = dcid.internalNioBuffer(dcid.readerIndex(), dcid.readableBytes());
/* 55 */     return getChannel(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void connectQuicChannel(QuicheQuicChannel channel, SocketAddress remoteAddress, SocketAddress localAddress, ByteBuf senderSockaddrMemory, ByteBuf recipientSockaddrMemory, Consumer<QuicheQuicChannel> freeTask, int localConnIdLength, QuicheConfig config, ChannelPromise promise) {
/*    */     try {
/* 64 */       channel.connectNow(this.sslEngineProvider, this.sslTaskExecutor, freeTask, config.nativeAddress(), localConnIdLength, config
/* 65 */           .isDatagramSupported(), senderSockaddrMemory
/* 66 */           .internalNioBuffer(0, senderSockaddrMemory.capacity()), recipientSockaddrMemory
/* 67 */           .internalNioBuffer(0, recipientSockaddrMemory.capacity()));
/* 68 */     } catch (Throwable cause) {
/*    */       
/* 70 */       promise.setFailure(cause);
/*    */       
/*    */       return;
/*    */     } 
/* 74 */     addChannel(channel);
/* 75 */     channel.finishConnect();
/* 76 */     promise.setSuccess();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicClientCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */