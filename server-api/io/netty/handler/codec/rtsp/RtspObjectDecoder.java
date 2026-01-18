/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.handler.codec.http.HttpMessage;
/*    */ import io.netty.handler.codec.http.HttpObjectDecoder;
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
/*    */ public abstract class RtspObjectDecoder
/*    */   extends HttpObjectDecoder
/*    */ {
/*    */   protected RtspObjectDecoder() {
/* 64 */     this(4096, 8192, 8192);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected RtspObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxContentLength) {
/* 71 */     super(maxInitialLineLength, maxHeaderSize, maxContentLength * 2, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected RtspObjectDecoder(int maxInitialLineLength, int maxHeaderSize, int maxContentLength, boolean validateHeaders) {
/* 76 */     super(maxInitialLineLength, maxHeaderSize, maxContentLength * 2, false, validateHeaders);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isContentAlwaysEmpty(HttpMessage msg) {
/* 83 */     boolean empty = super.isContentAlwaysEmpty(msg);
/* 84 */     if (empty) {
/* 85 */       return true;
/*    */     }
/* 87 */     if (!msg.headers().contains((CharSequence)RtspHeaderNames.CONTENT_LENGTH)) {
/* 88 */       return true;
/*    */     }
/* 90 */     return empty;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\rtsp\RtspObjectDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */