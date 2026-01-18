/*    */ package io.netty.handler.codec;
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
/*    */ public class CodecException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -1464830400709348473L;
/*    */   
/*    */   public CodecException() {}
/*    */   
/*    */   public CodecException(String message, Throwable cause) {
/* 35 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CodecException(String message) {
/* 42 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CodecException(Throwable cause) {
/* 49 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\CodecException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */