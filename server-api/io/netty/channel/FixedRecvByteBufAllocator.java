/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public class FixedRecvByteBufAllocator
/*    */   extends DefaultMaxMessagesRecvByteBufAllocator
/*    */ {
/*    */   private final int bufferSize;
/*    */   
/*    */   private final class HandleImpl
/*    */     extends DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle
/*    */   {
/*    */     private final int bufferSize;
/*    */     
/*    */     HandleImpl(int bufferSize) {
/* 32 */       this.bufferSize = bufferSize;
/*    */     }
/*    */ 
/*    */     
/*    */     public int guess() {
/* 37 */       return this.bufferSize;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FixedRecvByteBufAllocator(int bufferSize) {
/* 46 */     ObjectUtil.checkPositive(bufferSize, "bufferSize");
/* 47 */     this.bufferSize = bufferSize;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RecvByteBufAllocator.Handle newHandle() {
/* 53 */     return new HandleImpl(this.bufferSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public FixedRecvByteBufAllocator respectMaybeMoreData(boolean respectMaybeMoreData) {
/* 58 */     super.respectMaybeMoreData(respectMaybeMoreData);
/* 59 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\FixedRecvByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */