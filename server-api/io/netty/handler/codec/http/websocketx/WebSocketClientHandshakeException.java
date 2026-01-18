/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.handler.codec.http.DefaultHttpResponse;
/*    */ import io.netty.handler.codec.http.HttpResponse;
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
/*    */ public final class WebSocketClientHandshakeException
/*    */   extends WebSocketHandshakeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final HttpResponse response;
/*    */   
/*    */   public WebSocketClientHandshakeException(String message) {
/* 36 */     this(message, null);
/*    */   }
/*    */   
/*    */   public WebSocketClientHandshakeException(String message, HttpResponse httpResponse) {
/* 40 */     super(message);
/* 41 */     if (httpResponse != null) {
/* 42 */       this
/* 43 */         .response = (HttpResponse)new DefaultHttpResponse(httpResponse.protocolVersion(), httpResponse.status(), httpResponse.headers());
/*    */     } else {
/* 45 */       this.response = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpResponse response() {
/* 53 */     return this.response;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshakeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */