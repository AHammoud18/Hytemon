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
/*    */ final class Http3UnidirectionalStreamInboundClientHandler
/*    */   extends Http3UnidirectionalStreamInboundHandler
/*    */ {
/*    */   private final LongFunction<ChannelHandler> pushStreamHandlerFactory;
/*    */   
/*    */   Http3UnidirectionalStreamInboundClientHandler(Http3FrameCodec.Http3FrameCodecFactory codecFactory, Http3ControlStreamInboundHandler localControlStreamHandler, Http3ControlStreamOutboundHandler remoteControlStreamHandler, @Nullable LongFunction<ChannelHandler> unknownStreamHandlerFactory, @Nullable LongFunction<ChannelHandler> pushStreamHandlerFactory, Supplier<ChannelHandler> qpackEncoderHandlerFactory, Supplier<ChannelHandler> qpackDecoderHandlerFactory) {
/* 37 */     super(codecFactory, localControlStreamHandler, remoteControlStreamHandler, unknownStreamHandlerFactory, qpackEncoderHandlerFactory, qpackDecoderHandlerFactory);
/*    */     
/* 39 */     this
/* 40 */       .pushStreamHandlerFactory = (pushStreamHandlerFactory == null) ? (__ -> Http3UnidirectionalStreamInboundHandler.ReleaseHandler.INSTANCE) : pushStreamHandlerFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   void initPushStream(ChannelHandlerContext ctx, long pushId) {
/* 46 */     Long maxPushId = this.remoteControlStreamHandler.sentMaxPushId();
/* 47 */     if (maxPushId == null) {
/* 48 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_ID_ERROR, "Received push stream before sending MAX_PUSH_ID frame.", false);
/*    */     }
/* 50 */     else if (maxPushId.longValue() < pushId) {
/* 51 */       Http3CodecUtils.connectionError(ctx, Http3ErrorCode.H3_ID_ERROR, "Received push stream with ID " + pushId + " greater than the max push ID " + maxPushId + '.', false);
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 56 */       ChannelHandler pushStreamHandler = this.pushStreamHandlerFactory.apply(pushId);
/* 57 */       ctx.pipeline().replace((ChannelHandler)this, null, pushStreamHandler);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3UnidirectionalStreamInboundClientHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */