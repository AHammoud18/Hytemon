/*    */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*    */ 
/*    */ import io.netty.handler.codec.http.websocketx.WebSocketFrame;
/*    */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionFilter;
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
/*    */ class PerFrameDeflateDecoder
/*    */   extends DeflateDecoder
/*    */ {
/*    */   PerFrameDeflateDecoder(boolean noContext, int maxAllocation) {
/* 38 */     super(noContext, WebSocketExtensionFilter.NEVER_SKIP, maxAllocation);
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
/*    */   PerFrameDeflateDecoder(boolean noContext, WebSocketExtensionFilter extensionDecoderFilter, int maxAllocation) {
/* 50 */     super(noContext, extensionDecoderFilter, maxAllocation);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptInboundMessage(Object msg) throws Exception {
/* 55 */     if (!super.acceptInboundMessage(msg)) {
/* 56 */       return false;
/*    */     }
/*    */     
/* 59 */     WebSocketFrame wsFrame = (WebSocketFrame)msg;
/* 60 */     if (extensionDecoderFilter().mustSkip(wsFrame)) {
/* 61 */       return false;
/*    */     }
/*    */     
/* 64 */     return ((msg instanceof io.netty.handler.codec.http.websocketx.TextWebSocketFrame || msg instanceof io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame || msg instanceof io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame) && (wsFrame
/*    */       
/* 66 */       .rsv() & 0x4) > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int newRsv(WebSocketFrame msg) {
/* 71 */     return msg.rsv() ^ 0x4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean appendFrameTail(WebSocketFrame msg) {
/* 76 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\PerFrameDeflateDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */