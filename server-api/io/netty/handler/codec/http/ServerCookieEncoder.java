/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.handler.codec.http.cookie.Cookie;
/*    */ import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
/*    */ import java.util.Collection;
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
/*    */ public final class ServerCookieEncoder
/*    */ {
/*    */   @Deprecated
/*    */   public static String encode(String name, String value) {
/* 53 */     return ServerCookieEncoder.LAX.encode(name, value);
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
/* 64 */     return ServerCookieEncoder.LAX.encode(cookie);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static List<String> encode(Cookie... cookies) {
/* 75 */     return ServerCookieEncoder.LAX.encode((Cookie[])cookies);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static List<String> encode(Collection<Cookie> cookies) {
/* 86 */     return ServerCookieEncoder.LAX.encode(cookies);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static List<String> encode(Iterable<Cookie> cookies) {
/* 97 */     return ServerCookieEncoder.LAX.encode(cookies);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\ServerCookieEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */