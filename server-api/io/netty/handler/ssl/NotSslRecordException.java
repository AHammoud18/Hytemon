/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import javax.net.ssl.SSLException;
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
/*    */ public class NotSslRecordException
/*    */   extends SSLException
/*    */ {
/*    */   private static final long serialVersionUID = -4316784434770656841L;
/*    */   
/*    */   public NotSslRecordException() {
/* 33 */     super("");
/*    */   }
/*    */   
/*    */   public NotSslRecordException(String message) {
/* 37 */     super(message);
/*    */   }
/*    */   
/*    */   public NotSslRecordException(Throwable cause) {
/* 41 */     super(cause);
/*    */   }
/*    */   
/*    */   public NotSslRecordException(String message, Throwable cause) {
/* 45 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\NotSslRecordException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */