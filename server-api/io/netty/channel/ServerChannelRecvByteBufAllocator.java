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
/*    */ public final class ServerChannelRecvByteBufAllocator
/*    */   extends DefaultMaxMessagesRecvByteBufAllocator
/*    */ {
/*    */   public ServerChannelRecvByteBufAllocator() {
/* 23 */     super(1, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecvByteBufAllocator.Handle newHandle() {
/* 28 */     return new DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle()
/*    */       {
/*    */         public int guess() {
/* 31 */           return 128;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ServerChannelRecvByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */