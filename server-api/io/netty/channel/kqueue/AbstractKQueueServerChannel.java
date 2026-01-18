/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ServerChannel;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
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
/*     */ public abstract class AbstractKQueueServerChannel
/*     */   extends AbstractKQueueChannel
/*     */   implements ServerChannel
/*     */ {
/*  29 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
/*     */   
/*     */   AbstractKQueueServerChannel(BsdSocket fd) {
/*  32 */     this(fd, isSoErrorZero(fd));
/*     */   }
/*     */   
/*     */   AbstractKQueueServerChannel(BsdSocket fd, boolean active) {
/*  36 */     super((Channel)null, fd, active);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  41 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InetSocketAddress remoteAddress0() {
/*  46 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
/*  51 */     return new KQueueServerSocketUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*  56 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) throws Exception {
/*  61 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/*  68 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   abstract Channel newChildChannel(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) throws Exception;
/*     */   
/*     */   final class KQueueServerSocketUnsafe extends AbstractKQueueChannel.AbstractKQueueUnsafe { KQueueServerSocketUnsafe() {
/*  74 */       this.acceptedAddress = new byte[25];
/*     */     }
/*     */     private final byte[] acceptedAddress;
/*     */     void readReady(KQueueRecvByteAllocatorHandle allocHandle) {
/*  78 */       assert AbstractKQueueServerChannel.this.eventLoop().inEventLoop();
/*  79 */       KQueueChannelConfig kQueueChannelConfig = AbstractKQueueServerChannel.this.config();
/*  80 */       if (AbstractKQueueServerChannel.this.shouldBreakReadReady((ChannelConfig)kQueueChannelConfig)) {
/*  81 */         clearReadFilter0();
/*     */         return;
/*     */       } 
/*  84 */       ChannelPipeline pipeline = AbstractKQueueServerChannel.this.pipeline();
/*  85 */       allocHandle.reset((ChannelConfig)kQueueChannelConfig);
/*  86 */       allocHandle.attemptedBytesRead(1);
/*     */       
/*  88 */       Throwable exception = null;
/*     */       try {
/*     */         while (true) {
/*     */           
/*  92 */           try { int acceptFd = AbstractKQueueServerChannel.this.socket.accept(this.acceptedAddress);
/*  93 */             if (acceptFd == -1) {
/*     */               
/*  95 */               allocHandle.lastBytesRead(-1);
/*     */               break;
/*     */             } 
/*  98 */             allocHandle.lastBytesRead(1);
/*  99 */             allocHandle.incMessagesRead(1);
/*     */             
/* 101 */             this.readPending = false;
/* 102 */             pipeline.fireChannelRead(AbstractKQueueServerChannel.this.newChildChannel(acceptFd, this.acceptedAddress, 1, this.acceptedAddress[0]));
/*     */             
/* 104 */             if (!allocHandle.continueReading())
/* 105 */               break;  } catch (Throwable t)
/* 106 */           { exception = t; break; }
/*     */         
/* 108 */         }  allocHandle.readComplete();
/* 109 */         pipeline.fireChannelReadComplete();
/*     */         
/* 111 */         if (exception != null) {
/* 112 */           pipeline.fireExceptionCaught(exception);
/*     */         }
/*     */       } finally {
/* 115 */         if (shouldStopReading((ChannelConfig)kQueueChannelConfig))
/* 116 */           clearReadFilter0(); 
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\AbstractKQueueServerChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */