/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelInitializer;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.quic.QuicStreamChannel;
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
/*    */ public abstract class Http3PushStreamClientInitializer
/*    */   extends ChannelInitializer<QuicStreamChannel>
/*    */ {
/*    */   protected abstract void initPushStream(QuicStreamChannel paramQuicStreamChannel);
/*    */   
/*    */   protected final void initChannel(QuicStreamChannel ch) {
/* 33 */     if (Http3CodecUtils.isServerInitiatedQuicStream(ch)) {
/* 34 */       throw new IllegalArgumentException("Using client push stream initializer for server stream: " + ch
/* 35 */           .streamId());
/*    */     }
/* 37 */     Http3CodecUtils.verifyIsUnidirectional(ch);
/*    */     
/* 39 */     Http3ConnectionHandler connectionHandler = Http3CodecUtils.getConnectionHandlerOrClose(ch.parent());
/* 40 */     if (connectionHandler == null) {
/*    */       return;
/*    */     }
/*    */     
/* 44 */     ChannelPipeline pipeline = ch.pipeline();
/* 45 */     Http3RequestStreamDecodeStateValidator decodeStateValidator = new Http3RequestStreamDecodeStateValidator();
/*    */     
/* 47 */     pipeline.addLast(new ChannelHandler[] { connectionHandler.newCodec(Http3RequestStreamCodecState.NO_STATE, decodeStateValidator) });
/* 48 */     pipeline.addLast(new ChannelHandler[] { (ChannelHandler)decodeStateValidator });
/*    */     
/* 50 */     pipeline.addLast(new ChannelHandler[] { connectionHandler.newPushStreamValidationHandler(ch, decodeStateValidator) });
/* 51 */     initPushStream(ch);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3PushStreamClientInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */