/*    */ package io.netty.util.concurrent;
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
/*    */ public class BlockingOperationException
/*    */   extends IllegalStateException
/*    */ {
/*    */   private static final long serialVersionUID = 2462223247762460301L;
/*    */   
/*    */   public BlockingOperationException() {}
/*    */   
/*    */   public BlockingOperationException(String s) {
/* 31 */     super(s);
/*    */   }
/*    */   
/*    */   public BlockingOperationException(Throwable cause) {
/* 35 */     super(cause);
/*    */   }
/*    */   
/*    */   public BlockingOperationException(String message, Throwable cause) {
/* 39 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\BlockingOperationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */