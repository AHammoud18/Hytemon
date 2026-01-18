/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.handler.codec.http.HttpVersion;
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public final class RtspVersions
/*    */ {
/* 29 */   public static final HttpVersion RTSP_1_0 = new HttpVersion("RTSP", 1, 0, true);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static HttpVersion valueOf(String text) {
/* 38 */     ObjectUtil.checkNotNull(text, "text");
/*    */     
/* 40 */     text = text.trim().toUpperCase();
/* 41 */     if ("RTSP/1.0".equals(text)) {
/* 42 */       return RTSP_1_0;
/*    */     }
/*    */     
/* 45 */     return new HttpVersion(text, true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\rtsp\RtspVersions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */