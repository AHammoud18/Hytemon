/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.handler.codec.DecoderException;
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
/*    */ public final class InvalidLineSeparatorException
/*    */   extends DecoderException
/*    */ {
/*    */   private static final long serialVersionUID = 536224937231200736L;
/*    */   
/*    */   public InvalidLineSeparatorException() {
/* 34 */     super("Line Feed must be preceded by Carriage Return when terminating HTTP start- and header field-lines");
/*    */   }
/*    */   
/*    */   public InvalidLineSeparatorException(String message, Throwable cause) {
/* 38 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public InvalidLineSeparatorException(String message) {
/* 42 */     super(message);
/*    */   }
/*    */   
/*    */   public InvalidLineSeparatorException(Throwable cause) {
/* 46 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\InvalidLineSeparatorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */