/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.handler.codec.CorruptedFrameException;
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
/*    */ public final class InvalidChunkExtensionException
/*    */   extends CorruptedFrameException
/*    */ {
/*    */   private static final long serialVersionUID = 536224937231200736L;
/*    */   
/*    */   public InvalidChunkExtensionException() {
/* 31 */     super("Line Feed must be preceded by Carriage Return when terminating HTTP chunk header lines");
/*    */   }
/*    */   
/*    */   public InvalidChunkExtensionException(String message, Throwable cause) {
/* 35 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public InvalidChunkExtensionException(String message) {
/* 39 */     super(message);
/*    */   }
/*    */   
/*    */   public InvalidChunkExtensionException(Throwable cause) {
/* 43 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\InvalidChunkExtensionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */