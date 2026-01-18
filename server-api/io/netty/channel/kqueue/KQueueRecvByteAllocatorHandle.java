/*    */ package io.netty.channel.kqueue;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class KQueueRecvByteAllocatorHandle
/*    */   extends RecvByteBufAllocator.DelegatingHandle
/*    */   implements RecvByteBufAllocator.ExtendedHandle
/*    */ {
/* 30 */   private final PreferredDirectByteBufAllocator preferredDirectByteBufAllocator = new PreferredDirectByteBufAllocator();
/*    */ 
/*    */   
/* 33 */   private final UncheckedBooleanSupplier defaultMaybeMoreDataSupplier = new UncheckedBooleanSupplier()
/*    */     {
/*    */       public boolean get() {
/* 36 */         return KQueueRecvByteAllocatorHandle.this.maybeMoreDataToRead();
/*    */       }
/*    */     };
/*    */   
/*    */   private boolean readEOF;
/*    */   
/*    */   KQueueRecvByteAllocatorHandle(RecvByteBufAllocator.ExtendedHandle handle) {
/* 43 */     super((RecvByteBufAllocator.Handle)handle);
/*    */   }
/*    */   
/*    */   private long numberBytesPending;
/*    */   
/*    */   public ByteBuf allocate(ByteBufAllocator alloc) {
/* 49 */     this.preferredDirectByteBufAllocator.updateAllocator(alloc);
/* 50 */     return delegate().allocate((ByteBufAllocator)this.preferredDirectByteBufAllocator);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean continueReading(UncheckedBooleanSupplier maybeMoreDataSupplier) {
/* 55 */     return (this.readEOF || ((RecvByteBufAllocator.ExtendedHandle)delegate()).continueReading(maybeMoreDataSupplier));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueReading() {
/* 61 */     return continueReading(this.defaultMaybeMoreDataSupplier);
/*    */   }
/*    */   
/*    */   void readEOF() {
/* 65 */     this.readEOF = true;
/*    */   }
/*    */   
/*    */   void numberBytesPending(long numberBytesPending) {
/* 69 */     this.numberBytesPending = numberBytesPending;
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
/*    */   private boolean maybeMoreDataToRead() {
/* 83 */     return (lastBytesRead() == attemptedBytesRead());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueRecvByteAllocatorHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */