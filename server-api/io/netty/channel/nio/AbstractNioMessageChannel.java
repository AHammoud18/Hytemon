/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public abstract class AbstractNioMessageChannel
/*     */   extends AbstractNioChannel
/*     */ {
/*     */   boolean inputShutdown;
/*     */   
/*     */   protected AbstractNioMessageChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
/*  41 */     super(parent, ch, readInterestOp);
/*     */   }
/*     */   
/*     */   protected AbstractNioMessageChannel(Channel parent, SelectableChannel ch, NioIoOps readOps) {
/*  45 */     super(parent, ch, readOps);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
/*  50 */     return new NioMessageUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/*  55 */     if (this.inputShutdown) {
/*     */       return;
/*     */     }
/*  58 */     super.doBeginRead();
/*     */   }
/*     */   
/*     */   protected boolean continueReading(RecvByteBufAllocator.Handle allocHandle) {
/*  62 */     return allocHandle.continueReading();
/*     */   }
/*     */   
/*     */   private final class NioMessageUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
/*     */     private NioMessageUnsafe() {
/*  67 */       this.readBuf = new ArrayList();
/*     */     }
/*     */     private final List<Object> readBuf;
/*     */     public void read() {
/*  71 */       assert AbstractNioMessageChannel.this.eventLoop().inEventLoop();
/*  72 */       ChannelConfig config = AbstractNioMessageChannel.this.config();
/*  73 */       ChannelPipeline pipeline = AbstractNioMessageChannel.this.pipeline();
/*  74 */       RecvByteBufAllocator.Handle allocHandle = AbstractNioMessageChannel.this.unsafe().recvBufAllocHandle();
/*  75 */       allocHandle.reset(config);
/*     */       
/*  77 */       boolean closed = false;
/*  78 */       Throwable exception = null;
/*     */       try {
/*     */         while (true) {
/*     */           
/*  82 */           try { int localRead = AbstractNioMessageChannel.this.doReadMessages(this.readBuf);
/*  83 */             if (localRead == 0) {
/*     */               break;
/*     */             }
/*  86 */             if (localRead < 0) {
/*  87 */               closed = true;
/*     */               
/*     */               break;
/*     */             } 
/*  91 */             allocHandle.incMessagesRead(localRead);
/*  92 */             if (!AbstractNioMessageChannel.this.continueReading(allocHandle))
/*  93 */               break;  } catch (Throwable t)
/*  94 */           { exception = t; break; }
/*     */         
/*     */         } 
/*  97 */         int size = this.readBuf.size();
/*  98 */         for (int i = 0; i < size; i++) {
/*  99 */           AbstractNioMessageChannel.this.readPending = false;
/* 100 */           pipeline.fireChannelRead(this.readBuf.get(i));
/*     */         } 
/* 102 */         this.readBuf.clear();
/* 103 */         allocHandle.readComplete();
/* 104 */         pipeline.fireChannelReadComplete();
/*     */         
/* 106 */         if (exception != null) {
/* 107 */           closed = AbstractNioMessageChannel.this.closeOnReadError(exception);
/*     */           
/* 109 */           pipeline.fireExceptionCaught(exception);
/*     */         } 
/*     */         
/* 112 */         if (closed) {
/* 113 */           AbstractNioMessageChannel.this.inputShutdown = true;
/* 114 */           if (AbstractNioMessageChannel.this.isOpen()) {
/* 115 */             close(voidPromise());
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 125 */         if (!AbstractNioMessageChannel.this.readPending && !config.isAutoRead()) {
/* 126 */           removeReadOp();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 134 */     int maxMessagesPerWrite = maxMessagesPerWrite();
/* 135 */     while (maxMessagesPerWrite > 0) {
/* 136 */       Object msg = in.current();
/* 137 */       if (msg == null) {
/*     */         break;
/*     */       }
/*     */       try {
/* 141 */         boolean done = false;
/* 142 */         for (int i = config().getWriteSpinCount() - 1; i >= 0; i--) {
/* 143 */           if (doWriteMessage(msg, in)) {
/* 144 */             done = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 149 */         if (done) {
/* 150 */           maxMessagesPerWrite--;
/* 151 */           in.remove();
/*     */         }
/*     */       
/*     */       }
/* 155 */       catch (Exception e) {
/* 156 */         if (continueOnWriteError()) {
/* 157 */           maxMessagesPerWrite--;
/* 158 */           in.remove(e); continue;
/*     */         } 
/* 160 */         throw e;
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     if (in.isEmpty()) {
/*     */       
/* 166 */       removeAndSubmit(NioIoOps.WRITE);
/*     */     } else {
/*     */       
/* 169 */       addAndSubmit(NioIoOps.WRITE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean continueOnWriteError() {
/* 177 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean closeOnReadError(Throwable cause) {
/* 181 */     if (!isActive())
/*     */     {
/* 183 */       return true;
/*     */     }
/* 185 */     if (cause instanceof java.net.PortUnreachableException) {
/* 186 */       return false;
/*     */     }
/* 188 */     if (cause instanceof java.io.IOException)
/*     */     {
/*     */       
/* 191 */       return !(this instanceof io.netty.channel.ServerChannel);
/*     */     }
/* 193 */     return true;
/*     */   }
/*     */   
/*     */   protected abstract int doReadMessages(List<Object> paramList) throws Exception;
/*     */   
/*     */   protected abstract boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\AbstractNioMessageChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */