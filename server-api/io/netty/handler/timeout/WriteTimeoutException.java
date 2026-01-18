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
/*    */ public final class WriteTimeoutException
/*    */   extends TimeoutException
/*    */ {
/*    */   private static final long serialVersionUID = -144786655770296065L;
/* 26 */   public static final WriteTimeoutException INSTANCE = new WriteTimeoutException(true);
/*    */   
/*    */   public WriteTimeoutException() {}
/*    */   
/*    */   public WriteTimeoutException(String message) {
/* 31 */     super(message, false);
/*    */   }
/*    */   
/*    */   private WriteTimeoutException(boolean shared) {
/* 35 */     super(null, shared);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\timeout\WriteTimeoutException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */