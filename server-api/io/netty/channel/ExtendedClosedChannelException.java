/*    */ package io.netty.channel;
/*    */ 
/*    */ import java.nio.channels.ClosedChannelException;
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
/*    */ final class ExtendedClosedChannelException
/*    */   extends ClosedChannelException
/*    */ {
/*    */   ExtendedClosedChannelException(Throwable cause) {
/* 23 */     if (cause != null) {
/* 24 */       initCause(cause);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable fillInStackTrace() {
/* 31 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ExtendedClosedChannelException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */