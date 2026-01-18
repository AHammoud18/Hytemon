/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.ThreadAwareExecutor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IoHandlerFactory
/*    */ {
/*    */   IoHandler newHandler(ThreadAwareExecutor paramThreadAwareExecutor);
/*    */   
/*    */   default boolean isChangingThreadSupported() {
/* 42 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\IoHandlerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */