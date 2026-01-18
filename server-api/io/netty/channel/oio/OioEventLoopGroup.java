/*    */ package io.netty.channel.oio;
/*    */ 
/*    */ import io.netty.channel.ThreadPerChannelEventLoopGroup;
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
/*    */ public class OioEventLoopGroup
/*    */   extends ThreadPerChannelEventLoopGroup
/*    */ {
/*    */   public OioEventLoopGroup() {
/* 42 */     this(0);
/*    */   }
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
/*    */   public OioEventLoopGroup(int maxChannels) {
/* 55 */     this(maxChannels, (ThreadFactory)null);
/*    */   }
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
/*    */   public OioEventLoopGroup(int maxChannels, Executor executor) {
/* 70 */     super(maxChannels, executor, new Object[0]);
/*    */   }
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
/*    */   public OioEventLoopGroup(int maxChannels, ThreadFactory threadFactory) {
/* 85 */     super(maxChannels, threadFactory, new Object[0]);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\oio\OioEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */