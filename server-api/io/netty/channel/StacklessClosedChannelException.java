/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.internal.ThrowableUtil;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class StacklessClosedChannelException
/*    */   extends ClosedChannelException
/*    */ {
/*    */   private static final long serialVersionUID = -2214806025529435136L;
/*    */   
/*    */   public Throwable fillInStackTrace() {
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static StacklessClosedChannelException newInstance(Class<?> clazz, String method) {
/* 41 */     return (StacklessClosedChannelException)ThrowableUtil.unknownStackTrace(new StacklessClosedChannelException(), clazz, method);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\StacklessClosedChannelException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */