/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Http3ServerConnectionHandler
/*    */   extends Http3ConnectionHandler
/*    */ {
/*    */   private final ChannelHandler requestStreamHandler;
/*    */   
/*    */   public Http3ServerConnectionHandler(ChannelHandler requestStreamHandler) {
/* 41 */     this(requestStreamHandler, (ChannelHandler)null, (LongFunction<ChannelHandler>)null, (Http3SettingsFrame)null, true);
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
/*    */   public Http3ServerConnectionHandler(ChannelHandler requestStreamHandler, @Nullable ChannelHandler inboundControlStreamHandler, @Nullable LongFunction<ChannelHandler> unknownInboundStreamHandlerFactory, @Nullable Http3SettingsFrame localSettings, boolean disableQpackDynamicTable) {
/* 63 */     super(true, inboundControlStreamHandler, unknownInboundStreamHandlerFactory, localSettings, disableQpackDynamicTable);
/*    */     
/* 65 */     this.requestStreamHandler = (ChannelHandler)ObjectUtil.checkNotNull(requestStreamHandler, "requestStreamHandler");
/*    */   }
/*    */ 
/*    */   
/*    */   void initBidirectionalStream(ChannelHandlerContext ctx, QuicStreamChannel streamChannel) {
/* 70 */     ChannelPipeline pipeline = streamChannel.pipeline();
/* 71 */     Http3RequestStreamEncodeStateValidator encodeStateValidator = new Http3RequestStreamEncodeStateValidator();
/* 72 */     Http3RequestStreamDecodeStateValidator decodeStateValidator = new Http3RequestStreamDecodeStateValidator();
/*    */     
/* 74 */     pipeline.addLast(new ChannelHandler[] { newCodec(encodeStateValidator, decodeStateValidator) });
/* 75 */     pipeline.addLast(new ChannelHandler[] { (ChannelHandler)encodeStateValidator });
/* 76 */     pipeline.addLast(new ChannelHandler[] { (ChannelHandler)decodeStateValidator });
/* 77 */     pipeline.addLast(new ChannelHandler[] { newRequestStreamValidationHandler(streamChannel, encodeStateValidator, decodeStateValidator) });
/* 78 */     pipeline.addLast(new ChannelHandler[] { this.requestStreamHandler });
/*    */   }
/*    */ 
/*    */   
/*    */   void initUnidirectionalStream(ChannelHandlerContext ctx, QuicStreamChannel streamChannel) {
/* 83 */     long maxTableCapacity = maxTableCapacity();
/* 84 */     streamChannel.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new Http3UnidirectionalStreamInboundServerHandler(this.codecFactory, this.localControlStreamHandler, this.remoteControlStreamHandler, this.unknownInboundStreamHandlerFactory, () -> new QpackEncoderHandler(Long.valueOf(maxTableCapacity), this.qpackDecoder), () -> new QpackDecoderHandler(this.qpackEncoder)) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3ServerConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */