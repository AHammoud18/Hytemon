/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.function.LongFunction;
/*    */ import java.util.function.Supplier;
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
/*    */ final class Http3UnidirectionalStreamInboundServerHandler
/*    */   extends Http3UnidirectionalStreamInboundHandler
/*    */ {
/*    */   Http3UnidirectionalStreamInboundServerHandler(Http3FrameCodec.Http3FrameCodecFactory codecFactory, Http3ControlStreamInboundHandler localControlStreamHandler, Http3ControlStreamOutboundHandler remoteControlStreamHandler, @Nullable LongFunction<ChannelHandler> unknownStreamHandlerFactory, Supplier<ChannelHandler> qpackEncoderHandlerFactory, Supplier<ChannelHandler> qpackDecoderHandlerFactory) {
/* 35 */     super(codecFactory, localControlStreamHandler, remoteControlStreamHandler, unknownStreamHandlerFactory, qpackEncoderHandlerFactory, qpackDecoderHandlerFactory);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   void initPushStream(ChannelHandlerContext ctx, long id) {
/* 41 */     Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_STREAM_CREATION_ERROR, "Server received push stream.", false);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3UnidirectionalStreamInboundServerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */