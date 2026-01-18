/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.concurrent.ThreadFactory;
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
/*    */ @Deprecated
/*    */ public class DefaultEventLoopGroup
/*    */   extends MultithreadEventLoopGroup
/*    */ {
/*    */   public DefaultEventLoopGroup() {
/* 31 */     this(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultEventLoopGroup(int nThreads) {
/* 40 */     this(nThreads, (ThreadFactory)null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultEventLoopGroup(ThreadFactory threadFactory) {
/* 49 */     this(0, threadFactory);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
/* 59 */     super(nThreads, threadFactory, new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultEventLoopGroup(int nThreads, Executor executor) {
/* 69 */     super(nThreads, executor, new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   protected EventLoop newChild(Executor executor, Object... args) throws Exception {
/* 74 */     return new DefaultEventLoop(this, executor);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\DefaultEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */