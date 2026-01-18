/*    */ package io.netty.handler.timeout;
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
/*    */ public final class ReadTimeoutException
/*    */   extends TimeoutException
/*    */ {
/*    */   private static final long serialVersionUID = 169287984113283421L;
/* 26 */   public static final ReadTimeoutException INSTANCE = new ReadTimeoutException(true);
/*    */   
/*    */   public ReadTimeoutException() {}
/*    */   
/*    */   public ReadTimeoutException(String message) {
/* 31 */     super(message, false);
/*    */   }
/*    */   
/*    */   private ReadTimeoutException(boolean shared) {
/* 35 */     super(null, shared);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\timeout\ReadTimeoutException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */