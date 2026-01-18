/*    */ package io.netty.channel.nio;
/*    */ 
/*    */ import io.netty.channel.IoEvent;
/*    */ import io.netty.channel.IoHandle;
/*    */ import io.netty.channel.IoRegistration;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.nio.channels.SelectableChannel;
/*    */ import java.nio.channels.SelectionKey;
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
/*    */ public abstract class NioSelectableChannelIoHandle<S extends SelectableChannel>
/*    */   implements IoHandle, NioIoHandle
/*    */ {
/*    */   private final S channel;
/*    */   
/*    */   public NioSelectableChannelIoHandle(S channel) {
/* 36 */     this.channel = (S)ObjectUtil.checkNotNull(channel, "channel");
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(IoRegistration registration, IoEvent ioEvent) {
/* 41 */     SelectionKey key = (SelectionKey)registration.attachment();
/* 42 */     handle(this.channel, key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws Exception {
/* 47 */     this.channel.close();
/*    */   }
/*    */ 
/*    */   
/*    */   public SelectableChannel selectableChannel() {
/* 52 */     return (SelectableChannel)this.channel;
/*    */   }
/*    */   
/*    */   protected abstract void handle(S paramS, SelectionKey paramSelectionKey);
/*    */   
/*    */   protected void deregister(S channel) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\NioSelectableChannelIoHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */