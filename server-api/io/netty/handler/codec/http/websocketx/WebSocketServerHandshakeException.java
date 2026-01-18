/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.handler.codec.http.DefaultHttpRequest;
/*    */ import io.netty.handler.codec.http.HttpRequest;
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
/*    */ public final class WebSocketServerHandshakeException
/*    */   extends WebSocketHandshakeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final HttpRequest request;
/*    */   
/*    */   public WebSocketServerHandshakeException(String message) {
/* 36 */     this(message, null);
/*    */   }
/*    */   
/*    */   public WebSocketServerHandshakeException(String message, HttpRequest httpRequest) {
/* 40 */     super(message);
/* 41 */     if (httpRequest != null) {
/* 42 */       this
/* 43 */         .request = (HttpRequest)new DefaultHttpRequest(httpRequest.protocolVersion(), httpRequest.method(), httpRequest.uri(), httpRequest.headers());
/*    */     } else {
/* 45 */       this.request = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpRequest request() {
/* 53 */     return this.request;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshakeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */