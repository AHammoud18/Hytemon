/*    */ package io.netty.handler.timeout;
/*    */ 
/*    */ import io.netty.channel.ChannelException;
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
/*    */ public class TimeoutException
/*    */   extends ChannelException
/*    */ {
/*    */   private static final long serialVersionUID = 4673641882869672533L;
/*    */   
/*    */   TimeoutException() {}
/*    */   
/*    */   TimeoutException(String message, boolean shared) {
/* 32 */     super(message, null, shared);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable fillInStackTrace() {
/* 38 */     return (Throwable)this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\timeout\TimeoutException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */