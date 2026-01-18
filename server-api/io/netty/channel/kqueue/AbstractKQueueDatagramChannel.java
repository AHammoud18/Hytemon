/*    */ package io.netty.channel.kqueue;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelMetadata;
/*    */ import io.netty.channel.ChannelOutboundBuffer;
/*    */ import java.io.IOException;
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
/*    */ abstract class AbstractKQueueDatagramChannel
/*    */   extends AbstractKQueueChannel
/*    */ {
/* 26 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true, 16);
/*    */   
/*    */   AbstractKQueueDatagramChannel(Channel parent, BsdSocket fd, boolean active) {
/* 29 */     super(parent, fd, active);
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelMetadata metadata() {
/* 34 */     return METADATA;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract boolean doWriteMessage(Object paramObject) throws Exception;
/*    */   
/*    */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 41 */     int maxMessagesPerWrite = maxMessagesPerWrite();
/* 42 */     while (maxMessagesPerWrite > 0) {
/* 43 */       Object msg = in.current();
/* 44 */       if (msg == null) {
/*    */         break;
/*    */       }
/*    */       
/*    */       try {
/* 49 */         boolean done = false;
/* 50 */         for (int i = config().getWriteSpinCount(); i > 0; i--) {
/* 51 */           if (doWriteMessage(msg)) {
/* 52 */             done = true;
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/* 57 */         if (done) {
/* 58 */           in.remove();
/* 59 */           maxMessagesPerWrite--;
/*    */           continue;
/*    */         } 
/*    */         break;
/* 63 */       } catch (IOException e) {
/* 64 */         maxMessagesPerWrite--;
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 69 */         in.remove(e);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 74 */     writeFilter(!in.isEmpty());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\AbstractKQueueDatagramChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */