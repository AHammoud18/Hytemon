/*    */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.http.websocketx.WebSocketFrame;
/*    */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilter;
/*    */ import java.util.List;
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
/*    */ class PerMessageDeflateDecoder
/*    */   extends DeflateDecoder
/*    */ {
/*    */   private boolean compressing;
/*    */   
/*    */   PerMessageDeflateDecoder(boolean noContext, int maxAllocation) {
/* 43 */     super(noContext, WebSocketExtensionFilter.NEVER_SKIP, maxAllocation);
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
/*    */   PerMessageDeflateDecoder(boolean noContext, WebSocketExtensionFilter extensionDecoderFilter, int maxAllocation) {
/* 55 */     super(noContext, extensionDecoderFilter, maxAllocation);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptInboundMessage(Object msg) throws Exception {
/* 60 */     if (!super.acceptInboundMessage(msg)) {
/* 61 */       return false;
/*    */     }
/*    */     
/* 64 */     WebSocketFrame wsFrame = (WebSocketFrame)msg;
/* 65 */     if (extensionDecoderFilter().mustSkip(wsFrame)) {
/* 66 */       if (this.compressing) {
/* 67 */         throw new IllegalStateException("Cannot skip per message deflate decoder, compression in progress");
/*    */       }
/* 69 */       return false;
/*    */     } 
/*    */     
/* 72 */     return (((wsFrame instanceof io.netty.handler.codec.http.websocketx.TextWebSocketFrame || wsFrame instanceof io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame) && (wsFrame
/* 73 */       .rsv() & 0x4) > 0) || (wsFrame instanceof io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame && this.compressing));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int newRsv(WebSocketFrame msg) {
/* 79 */     return ((msg.rsv() & 0x4) > 0) ? (
/* 80 */       msg.rsv() ^ 0x4) : msg.rsv();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean appendFrameTail(WebSocketFrame msg) {
/* 85 */     return msg.isFinalFragment();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/* 91 */     super.decode(ctx, msg, out);
/*    */     
/* 93 */     if (msg.isFinalFragment()) {
/* 94 */       this.compressing = false;
/* 95 */     } else if (msg instanceof io.netty.handler.codec.http.websocketx.TextWebSocketFrame || msg instanceof io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame) {
/* 96 */       this.compressing = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\PerMessageDeflateDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */