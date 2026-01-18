/*    */ package io.netty.handler.codec.http3;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelInitializer;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*    */ import io.netty.util.internal.StringUtil;
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
/*    */ public abstract class Http3RequestStreamInitializer
/*    */   extends ChannelInitializer<QuicStreamChannel>
/*    */ {
/*    */   protected abstract void initRequestStream(QuicStreamChannel paramQuicStreamChannel);
/*    */   
/*    */   protected final void initChannel(QuicStreamChannel ch) {
/* 31 */     ChannelPipeline pipeline = ch.pipeline();
/* 32 */     Http3ConnectionHandler connectionHandler = (Http3ConnectionHandler)ch.parent().pipeline().get(Http3ConnectionHandler.class);
/* 33 */     if (connectionHandler == null) {
/* 34 */       throw new IllegalStateException("Couldn't obtain the " + 
/* 35 */           StringUtil.simpleClassName(Http3ConnectionHandler.class) + " of the parent Channel");
/*    */     }
/*    */     
/* 38 */     Http3RequestStreamEncodeStateValidator encodeStateValidator = new Http3RequestStreamEncodeStateValidator();
/* 39 */     Http3RequestStreamDecodeStateValidator decodeStateValidator = new Http3RequestStreamDecodeStateValidator();
/*    */ 
/*    */     
/* 42 */     pipeline.addLast(new ChannelHandler[] { connectionHandler.newCodec(encodeStateValidator, decodeStateValidator) });
/*    */     
/* 44 */     pipeline.addLast(new ChannelHandler[] { (ChannelHandler)encodeStateValidator });
/* 45 */     pipeline.addLast(new ChannelHandler[] { (ChannelHandler)decodeStateValidator });
/* 46 */     pipeline.addLast(new ChannelHandler[] { connectionHandler.newRequestStreamValidationHandler(ch, encodeStateValidator, decodeStateValidator) });
/*    */     
/* 48 */     initRequestStream(ch);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3RequestStreamInitializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */