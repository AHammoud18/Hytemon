/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
/*    */ import io.netty.handler.codec.http.cookie.Cookie;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class ClientCookieEncoder
/*    */ {
/*    */   @Deprecated
/*    */   public static String encode(String name, String value) {
/* 49 */     return ClientCookieEncoder.LAX.encode(name, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static String encode(Cookie cookie) {
/* 60 */     return ClientCookieEncoder.LAX.encode(cookie);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static String encode(Cookie... cookies) {
/* 71 */     return ClientCookieEncoder.LAX.encode((Cookie[])cookies);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static String encode(Iterable<Cookie> cookies) {
/* 82 */     return ClientCookieEncoder.LAX.encode(cookies);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\ClientCookieEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */