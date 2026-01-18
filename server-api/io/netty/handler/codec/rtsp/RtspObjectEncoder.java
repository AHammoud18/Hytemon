/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.handler.codec.http.HttpMessage;
/*    */ import io.netty.handler.codec.http.HttpObjectEncoder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ @Deprecated
/*    */ public abstract class RtspObjectEncoder<H extends HttpMessage>
/*    */   extends HttpObjectEncoder<H>
/*    */ {
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 42 */     return msg instanceof io.netty.handler.codec.http.FullHttpMessage;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\rtsp\RtspObjectEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */