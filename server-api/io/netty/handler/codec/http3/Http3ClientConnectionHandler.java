/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*    */ import java.util.function.LongFunction;
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
/*    */ public final class Http3ClientConnectionHandler
/*    */   extends Http3ConnectionHandler
/*    */ {
/*    */   private final LongFunction<ChannelHandler> pushStreamHandlerFactory;
/*    */   
/*    */   public Http3ClientConnectionHandler() {
/* 33 */     this((ChannelHandler)null, (LongFunction<ChannelHandler>)null, (LongFunction<ChannelHandler>)null, (Http3SettingsFrame)null, true);
/*    */   }
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
/*    */   public Http3ClientConnectionHandler(@Nullable ChannelHandler inboundControlStreamHandler, @Nullable LongFunction<ChannelHandler> pushStreamHandlerFactory, @Nullable LongFunction<ChannelHandler> unknownInboundStreamHandlerFactory, @Nullable Http3SettingsFrame localSettings, boolean disableQpackDynamicTable) {
/* 57 */     super(false, inboundControlStreamHandler, unknownInboundStreamHandlerFactory, localSettings, disableQpackDynamicTable);
/*    */     
/* 59 */     this.pushStreamHandlerFactory = pushStreamHandlerFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   void initBidirectionalStream(ChannelHandlerContext ctx, QuicStreamChannel channel) {
/* 65 */     Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_STREAM_CREATION_ERROR, "Server initiated bidirectional streams are not allowed", true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   void initUnidirectionalStream(ChannelHandlerContext ctx, QuicStreamChannel streamChannel) {
/* 71 */     long maxTableCapacity = maxTableCapacity();
/* 72 */     streamChannel.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new Http3UnidirectionalStreamInboundClientHandler(this.codecFactory, this.localControlStreamHandler, this.remoteControlStreamHandler, this.unknownInboundStreamHandlerFactory, this.pushStreamHandlerFactory, () -> new QpackEncoderHandler(Long.valueOf(maxTableCapacity), this.qpackDecoder), () -> new QpackDecoderHandler(this.qpackEncoder)) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3ClientConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */