/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.util.AsciiString;
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
/*    */ public enum WebSocketVersion
/*    */ {
/* 31 */   UNKNOWN(AsciiString.cached("")),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   V00(AsciiString.cached("0")),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   V07(AsciiString.cached("7")),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   V08(AsciiString.cached("8")),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   V13(AsciiString.cached("13"));
/*    */   
/*    */   private final AsciiString headerValue;
/*    */   
/*    */   WebSocketVersion(AsciiString headerValue) {
/* 61 */     this.headerValue = headerValue;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toHttpHeaderValue() {
/* 67 */     return toAsciiString().toString();
/*    */   }
/*    */   
/*    */   AsciiString toAsciiString() {
/* 71 */     if (this == UNKNOWN)
/*    */     {
/* 73 */       throw new IllegalStateException("Unknown web socket version: " + this);
/*    */     }
/* 75 */     return this.headerValue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */