/*    */ package io.netty.channel.local;
/*    */ 
/*    */ import io.netty.channel.MultiThreadIoEventLoopGroup;
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
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class LocalEventLoopGroup
/*    */   extends MultiThreadIoEventLoopGroup
/*    */ {
/*    */   public LocalEventLoopGroup() {
/* 32 */     this(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalEventLoopGroup(int nThreads) {
/* 41 */     this(nThreads, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalEventLoopGroup(ThreadFactory threadFactory) {
/* 50 */     this(0, threadFactory);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
/* 60 */     super(nThreads, threadFactory, LocalIoHandler.newFactory());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\local\LocalEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */