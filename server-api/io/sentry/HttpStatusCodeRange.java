/*    */ package io.sentry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class HttpStatusCodeRange
/*    */ {
/*    */   public static final int DEFAULT_MIN = 500;
/*    */   public static final int DEFAULT_MAX = 599;
/*    */   private final int min;
/*    */   private final int max;
/*    */   
/*    */   public HttpStatusCodeRange(int min, int max) {
/* 17 */     this.min = min;
/* 18 */     this.max = max;
/*    */   }
/*    */   
/*    */   public HttpStatusCodeRange(int statusCode) {
/* 22 */     this.min = statusCode;
/* 23 */     this.max = statusCode;
/*    */   }
/*    */   
/*    */   public boolean isInRange(int statusCode) {
/* 27 */     return (statusCode >= this.min && statusCode <= this.max);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\HttpStatusCodeRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */