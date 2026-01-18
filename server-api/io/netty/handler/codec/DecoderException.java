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
/*    */ public class DecoderException
/*    */   extends CodecException
/*    */ {
/*    */   private static final long serialVersionUID = 6926716840699621852L;
/*    */   
/*    */   public DecoderException() {}
/*    */   
/*    */   public DecoderException(String message, Throwable cause) {
/* 35 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DecoderException(String message) {
/* 42 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DecoderException(Throwable cause) {
/* 49 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\DecoderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */