/*    */ package io.netty.handler.codec.http.websocketx.extensions;
/*    */ 
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import io.netty.handler.codec.http.websocketx.WebSocketFrame;
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
/*    */ public abstract class WebSocketExtensionDecoder
/*    */   extends MessageToMessageDecoder<WebSocketFrame>
/*    */ {
/*    */   public WebSocketExtensionDecoder() {
/* 27 */     super(WebSocketFrame.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\WebSocketExtensionDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */