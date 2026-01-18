/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.UncheckedBooleanSupplier;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface RecvByteBufAllocator
/*     */ {
/*     */   Handle newHandle();
/*     */   
/*     */   public static class DelegatingHandle
/*     */     implements Handle
/*     */   {
/*     */     private final RecvByteBufAllocator.Handle delegate;
/*     */     
/*     */     public DelegatingHandle(RecvByteBufAllocator.Handle delegate) {
/* 126 */       this.delegate = (RecvByteBufAllocator.Handle)ObjectUtil.checkNotNull(delegate, "delegate");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final RecvByteBufAllocator.Handle delegate() {
/* 134 */       return this.delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf allocate(ByteBufAllocator alloc) {
/* 139 */       return this.delegate.allocate(alloc);
/*     */     }
/*     */ 
/*     */     
/*     */     public int guess() {
/* 144 */       return this.delegate.guess();
/*     */     }
/*     */ 
/*     */     
/*     */     public void reset(ChannelConfig config) {
/* 149 */       this.delegate.reset(config);
/*     */     }
/*     */ 
/*     */     
/*     */     public void incMessagesRead(int numMessages) {
/* 154 */       this.delegate.incMessagesRead(numMessages);
/*     */     }
/*     */ 
/*     */     
/*     */     public void lastBytesRead(int bytes) {
/* 159 */       this.delegate.lastBytesRead(bytes);
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastBytesRead() {
/* 164 */       return this.delegate.lastBytesRead();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueReading() {
/* 169 */       return this.delegate.continueReading();
/*     */     }
/*     */ 
/*     */     
/*     */     public int attemptedBytesRead() {
/* 174 */       return this.delegate.attemptedBytesRead();
/*     */     }
/*     */ 
/*     */     
/*     */     public void attemptedBytesRead(int bytes) {
/* 179 */       this.delegate.attemptedBytesRead(bytes);
/*     */     }
/*     */ 
/*     */     
/*     */     public void readComplete() {
/* 184 */       this.delegate.readComplete();
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface ExtendedHandle extends Handle {
/*     */     boolean continueReading(UncheckedBooleanSupplier param1UncheckedBooleanSupplier);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static interface Handle {
/*     */     ByteBuf allocate(ByteBufAllocator param1ByteBufAllocator);
/*     */     
/*     */     int guess();
/*     */     
/*     */     void reset(ChannelConfig param1ChannelConfig);
/*     */     
/*     */     void incMessagesRead(int param1Int);
/*     */     
/*     */     void lastBytesRead(int param1Int);
/*     */     
/*     */     int lastBytesRead();
/*     */     
/*     */     void attemptedBytesRead(int param1Int);
/*     */     
/*     */     int attemptedBytesRead();
/*     */     
/*     */     boolean continueReading();
/*     */     
/*     */     void readComplete();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\RecvByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */