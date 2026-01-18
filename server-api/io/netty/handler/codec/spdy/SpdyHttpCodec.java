/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.channel.ChannelInboundHandler;
/*    */ import io.netty.channel.ChannelOutboundHandler;
/*    */ import io.netty.channel.CombinedChannelDuplexHandler;
/*    */ import io.netty.handler.codec.http.FullHttpMessage;
/*    */ import io.netty.handler.codec.http.HttpHeadersFactory;
/*    */ import java.util.HashMap;
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
/*    */ public final class SpdyHttpCodec
/*    */   extends CombinedChannelDuplexHandler<SpdyHttpDecoder, SpdyHttpEncoder>
/*    */ {
/*    */   public SpdyHttpCodec(SpdyVersion version, int maxContentLength) {
/* 32 */     super((ChannelInboundHandler)new SpdyHttpDecoder(version, maxContentLength), (ChannelOutboundHandler)new SpdyHttpEncoder(version));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public SpdyHttpCodec(SpdyVersion version, int maxContentLength, boolean validateHttpHeaders) {
/* 40 */     super((ChannelInboundHandler)new SpdyHttpDecoder(version, maxContentLength, validateHttpHeaders), (ChannelOutboundHandler)new SpdyHttpEncoder(version));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpdyHttpCodec(SpdyVersion version, int maxContentLength, HttpHeadersFactory headersFactory, HttpHeadersFactory trailersFactory) {
/* 48 */     super((ChannelInboundHandler)new SpdyHttpDecoder(version, maxContentLength, new HashMap<>(), headersFactory, trailersFactory), (ChannelOutboundHandler)new SpdyHttpEncoder(version));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyHttpCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */