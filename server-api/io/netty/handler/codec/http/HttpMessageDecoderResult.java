/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.handler.codec.DecoderResult;
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
/*    */ public final class HttpMessageDecoderResult
/*    */   extends DecoderResult
/*    */ {
/*    */   private final int initialLineLength;
/*    */   private final int headerSize;
/*    */   
/*    */   HttpMessageDecoderResult(int initialLineLength, int headerSize) {
/* 33 */     super((Throwable)SIGNAL_SUCCESS);
/* 34 */     this.initialLineLength = initialLineLength;
/* 35 */     this.headerSize = headerSize;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int initialLineLength() {
/* 42 */     return this.initialLineLength;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int headerSize() {
/* 49 */     return this.headerSize;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int totalSize() {
/* 56 */     return this.initialLineLength + this.headerSize;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpMessageDecoderResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */