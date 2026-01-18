/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelInitializer;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public abstract class Http3PushStreamServerInitializer
/*    */   extends ChannelInitializer<QuicStreamChannel>
/*    */ {
/*    */   private final long pushId;
/*    */   
/*    */   protected Http3PushStreamServerInitializer(long pushId) {
/* 37 */     this.pushId = ObjectUtil.checkPositiveOrZero(pushId, "pushId");
/*    */   }
/*    */ 
/*    */   
/*    */   protected final void initChannel(QuicStreamChannel ch) {
/* 42 */     if (!Http3CodecUtils.isServerInitiatedQuicStream(ch)) {
/* 43 */       throw new IllegalArgumentException("Using server push stream initializer for client stream: " + ch
/* 44 */           .streamId());
/*    */     }
/* 46 */     Http3CodecUtils.verifyIsUnidirectional(ch);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     ByteBuf buffer = ch.alloc().buffer(16);
/* 52 */     Http3CodecUtils.writeVariableLengthInteger(buffer, 1L);
/* 53 */     Http3CodecUtils.writeVariableLengthInteger(buffer, this.pushId);
/* 54 */     ch.write(buffer);
/*    */     
/* 56 */     Http3ConnectionHandler connectionHandler = Http3CodecUtils.getConnectionHandlerOrClose(ch.parent());
/* 57 */     if (connectionHandler == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 62 */     ChannelPipeline pipeline = ch.pipeline();
/* 63 */     Http3RequestStreamEncodeStateValidator encodeStateValidator = new Http3RequestStreamEncodeStateValidator();
/*    */     
/* 65 */     pipeline.addLast(new ChannelHandler[] { connectionHandler.newCodec(encodeStateValidator, Http3RequestStreamCodecState.NO_STATE) });
/* 66 */     pipeline.addLast(new ChannelHandler[] { (ChannelHandler)encodeStateValidator });
/*    */     
/* 68 */     pipeline.addLast(new ChannelHandler[] { connectionHandler.newPushStreamValidationHandler(ch, Http3RequestStreamCodecState.NO_STATE) });
/* 69 */     initPushStream(ch);
/*    */   }
/*    */   
/*    */   protected abstract void initPushStream(QuicStreamChannel paramQuicStreamChannel);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3PushStreamServerInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */