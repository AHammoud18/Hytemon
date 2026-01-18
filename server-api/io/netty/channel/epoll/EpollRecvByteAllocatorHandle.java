/*    */ package io.netty.channel.epoll;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufAllocator;
/*    */ import io.netty.channel.RecvByteBufAllocator;
/*    */ import io.netty.channel.unix.PreferredDirectByteBufAllocator;
/*    */ import io.netty.util.UncheckedBooleanSupplier;
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
/*    */ class EpollRecvByteAllocatorHandle
/*    */   extends RecvByteBufAllocator.DelegatingHandle
/*    */   implements RecvByteBufAllocator.ExtendedHandle
/*    */ {
/* 26 */   private final PreferredDirectByteBufAllocator preferredDirectByteBufAllocator = new PreferredDirectByteBufAllocator();
/*    */   
/* 28 */   private final UncheckedBooleanSupplier defaultMaybeMoreDataSupplier = new UncheckedBooleanSupplier()
/*    */     {
/*    */       public boolean get() {
/* 31 */         return EpollRecvByteAllocatorHandle.this.maybeMoreDataToRead();
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   EpollRecvByteAllocatorHandle(RecvByteBufAllocator.ExtendedHandle handle) {
/* 37 */     super((RecvByteBufAllocator.Handle)handle);
/*    */   }
/*    */   private boolean receivedRdHup;
/*    */   final void receivedRdHup() {
/* 41 */     this.receivedRdHup = true;
/*    */   }
/*    */   
/*    */   final boolean isReceivedRdHup() {
/* 45 */     return this.receivedRdHup;
/*    */   }
/*    */   
/*    */   boolean maybeMoreDataToRead() {
/* 49 */     return (lastBytesRead() == attemptedBytesRead());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final ByteBuf allocate(ByteBufAllocator alloc) {
/* 55 */     this.preferredDirectByteBufAllocator.updateAllocator(alloc);
/* 56 */     return delegate().allocate((ByteBufAllocator)this.preferredDirectByteBufAllocator);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean continueReading(UncheckedBooleanSupplier maybeMoreDataSupplier) {
/* 61 */     return (isReceivedRdHup() || ((RecvByteBufAllocator.ExtendedHandle)delegate()).continueReading(maybeMoreDataSupplier));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean continueReading() {
/* 67 */     return continueReading(this.defaultMaybeMoreDataSupplier);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollRecvByteAllocatorHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */