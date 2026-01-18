/*    */ package io.netty.channel.epoll;
/*    */ 
/*    */ import io.netty.channel.RecvByteBufAllocator;
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
/*    */ final class EpollRecvByteAllocatorStreamingHandle
/*    */   extends EpollRecvByteAllocatorHandle
/*    */ {
/*    */   EpollRecvByteAllocatorStreamingHandle(RecvByteBufAllocator.ExtendedHandle handle) {
/* 22 */     super(handle);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean maybeMoreDataToRead() {
/* 33 */     return (lastBytesRead() == attemptedBytesRead() || isReceivedRdHup());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollRecvByteAllocatorStreamingHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */