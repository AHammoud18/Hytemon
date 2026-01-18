/*    */ package io.netty.handler.codec.http.cookie;
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
/*    */ public final class CookieHeaderNames
/*    */ {
/*    */   public static final String PATH = "Path";
/*    */   public static final String EXPIRES = "Expires";
/*    */   public static final String MAX_AGE = "Max-Age";
/*    */   public static final String DOMAIN = "Domain";
/*    */   public static final String SECURE = "Secure";
/*    */   public static final String HTTPONLY = "HTTPOnly";
/*    */   public static final String SAMESITE = "SameSite";
/*    */   public static final String PARTITIONED = "Partitioned";
/*    */   
/*    */   public enum SameSite
/*    */   {
/* 40 */     Lax,
/* 41 */     Strict,
/* 42 */     None;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static SameSite of(String name) {
/* 51 */       if (name != null) {
/* 52 */         for (SameSite each : (SameSite[])SameSite.class.getEnumConstants()) {
/* 53 */           if (each.name().equalsIgnoreCase(name)) {
/* 54 */             return each;
/*    */           }
/*    */         } 
/*    */       }
/* 58 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\cookie\CookieHeaderNames.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */