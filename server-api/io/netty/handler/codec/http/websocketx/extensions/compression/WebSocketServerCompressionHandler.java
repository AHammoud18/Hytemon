/*    */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*    */ 
/*    */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandler;
/*    */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketServerExtensionHandshaker;
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
/*    */ public class WebSocketServerCompressionHandler
/*    */   extends WebSocketServerExtensionHandler
/*    */ {
/*    */   @Deprecated
/*    */   public WebSocketServerCompressionHandler() {
/* 36 */     this(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WebSocketServerCompressionHandler(int maxAllocation) {
/* 46 */     super(new WebSocketServerExtensionHandshaker[] { new PerMessageDeflateServerExtensionHandshaker(maxAllocation), new DeflateFrameServerExtensionHandshaker(6, maxAllocation) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\WebSocketServerCompressionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */