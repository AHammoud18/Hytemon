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
/*    */ abstract class PendingBytesTracker
/*    */   implements MessageSizeEstimator.Handle
/*    */ {
/*    */   private final MessageSizeEstimator.Handle estimatorHandle;
/*    */   
/*    */   private PendingBytesTracker(MessageSizeEstimator.Handle estimatorHandle) {
/* 24 */     this.estimatorHandle = (MessageSizeEstimator.Handle)ObjectUtil.checkNotNull(estimatorHandle, "estimatorHandle");
/*    */   }
/*    */ 
/*    */   
/*    */   public final int size(Object msg) {
/* 29 */     return this.estimatorHandle.size(msg);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void incrementPendingOutboundBytes(long paramLong);
/*    */   
/*    */   static PendingBytesTracker newTracker(Channel channel) {
/* 36 */     if (channel.pipeline() instanceof DefaultChannelPipeline) {
/* 37 */       return new DefaultChannelPipelinePendingBytesTracker((DefaultChannelPipeline)channel.pipeline());
/*    */     }
/* 39 */     ChannelOutboundBuffer buffer = channel.unsafe().outboundBuffer();
/* 40 */     MessageSizeEstimator.Handle handle = channel.config().getMessageSizeEstimator().newHandle();
/*    */ 
/*    */ 
/*    */     
/* 44 */     return (buffer == null) ? 
/* 45 */       new NoopPendingBytesTracker(handle) : new ChannelOutboundBufferPendingBytesTracker(buffer, handle);
/*    */   }
/*    */   
/*    */   public abstract void decrementPendingOutboundBytes(long paramLong);
/*    */   
/*    */   private static final class DefaultChannelPipelinePendingBytesTracker
/*    */     extends PendingBytesTracker {
/*    */     DefaultChannelPipelinePendingBytesTracker(DefaultChannelPipeline pipeline) {
/* 53 */       super(pipeline.estimatorHandle());
/* 54 */       this.pipeline = pipeline;
/*    */     }
/*    */ 
/*    */     
/*    */     public void incrementPendingOutboundBytes(long bytes) {
/* 59 */       this.pipeline.incrementPendingOutboundBytes(bytes);
/*    */     }
/*    */     private final DefaultChannelPipeline pipeline;
/*    */     
/*    */     public void decrementPendingOutboundBytes(long bytes) {
/* 64 */       this.pipeline.decrementPendingOutboundBytes(bytes);
/*    */     }
/*    */   }
/*    */   
/*    */   private static final class ChannelOutboundBufferPendingBytesTracker
/*    */     extends PendingBytesTracker {
/*    */     private final ChannelOutboundBuffer buffer;
/*    */     
/*    */     ChannelOutboundBufferPendingBytesTracker(ChannelOutboundBuffer buffer, MessageSizeEstimator.Handle estimatorHandle) {
/* 73 */       super(estimatorHandle);
/* 74 */       this.buffer = buffer;
/*    */     }
/*    */ 
/*    */     
/*    */     public void incrementPendingOutboundBytes(long bytes) {
/* 79 */       this.buffer.incrementPendingOutboundBytes(bytes);
/*    */     }
/*    */ 
/*    */     
/*    */     public void decrementPendingOutboundBytes(long bytes) {
/* 84 */       this.buffer.decrementPendingOutboundBytes(bytes);
/*    */     }
/*    */   }
/*    */   
/*    */   private static final class NoopPendingBytesTracker
/*    */     extends PendingBytesTracker {
/*    */     NoopPendingBytesTracker(MessageSizeEstimator.Handle estimatorHandle) {
/* 91 */       super(estimatorHandle);
/*    */     }
/*    */     
/*    */     public void incrementPendingOutboundBytes(long bytes) {}
/*    */     
/*    */     public void decrementPendingOutboundBytes(long bytes) {}
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\PendingBytesTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */