/*    */ package io.netty.channel;
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
/*    */ public final class DefaultSelectStrategyFactory
/*    */   implements SelectStrategyFactory
/*    */ {
/* 22 */   public static final SelectStrategyFactory INSTANCE = new DefaultSelectStrategyFactory();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SelectStrategy newSelectStrategy() {
/* 28 */     return DefaultSelectStrategy.INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\DefaultSelectStrategyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */