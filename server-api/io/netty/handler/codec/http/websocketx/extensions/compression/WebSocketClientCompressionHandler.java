/*    */ package io.netty.handler.codec.http.websocketx.extensions.compression;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandler;
/*    */ import io.netty.handler.codec.http.websocketx.extensions.WebSocketClientExtensionHandshaker;
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
/*    */ public final class WebSocketClientCompressionHandler
/*    */   extends WebSocketClientExtensionHandler
/*    */ {
/*    */   @Deprecated
/* 34 */   public static final WebSocketClientCompressionHandler INSTANCE = new WebSocketClientCompressionHandler();
/*    */   
/*    */   private WebSocketClientCompressionHandler() {
/* 37 */     this(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WebSocketClientCompressionHandler(int maxAllocation) {
/* 46 */     super(new WebSocketClientExtensionHandshaker[] { new PerMessageDeflateClientExtensionHandshaker(maxAllocation), new DeflateFrameClientExtensionHandshaker(false, maxAllocation), new DeflateFrameClientExtensionHandshaker(true, maxAllocation) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\compression\WebSocketClientCompressionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */