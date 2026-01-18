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
/*    */ public final class InvalidChunkTerminationException
/*    */   extends CorruptedFrameException
/*    */ {
/*    */   private static final long serialVersionUID = 536224937231200736L;
/*    */   
/*    */   public InvalidChunkTerminationException() {
/* 31 */     super("Chunk data sections must be terminated by a CR LF octet pair");
/*    */   }
/*    */   
/*    */   public InvalidChunkTerminationException(String message, Throwable cause) {
/* 35 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public InvalidChunkTerminationException(String message) {
/* 39 */     super(message);
/*    */   }
/*    */   
/*    */   public InvalidChunkTerminationException(Throwable cause) {
/* 43 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\InvalidChunkTerminationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */