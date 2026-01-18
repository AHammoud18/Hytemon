/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
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
/*     */ public abstract class AbstractEpollServerChannel
/*     */   extends AbstractEpollChannel
/*     */   implements ServerChannel
/*     */ {
/*  30 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
/*     */   
/*     */   protected AbstractEpollServerChannel(int fd) {
/*  33 */     this(new LinuxSocket(fd), false);
/*     */   }
/*     */   
/*     */   protected AbstractEpollServerChannel(LinuxSocket fd) {
/*  37 */     this(fd, isSoErrorZero(fd));
/*     */   }
/*     */   
/*     */   protected AbstractEpollServerChannel(LinuxSocket fd, boolean active) {
/*  41 */     super((Channel)null, fd, active, EpollIoOps.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  46 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InetSocketAddress remoteAddress0() {
/*  51 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
/*  56 */     return new EpollServerSocketUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*  61 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) throws Exception {
/*  66 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   final class EpollServerSocketUnsafe
/*     */     extends AbstractEpollChannel.AbstractEpollUnsafe {
/*     */     private final byte[] acceptedAddress;
/*     */     
/*     */     EpollServerSocketUnsafe() {
/*  74 */       this.acceptedAddress = new byte[25];
/*     */     }
/*     */ 
/*     */     
/*     */     public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
/*  79 */       channelPromise.setFailure(new UnsupportedOperationException());
/*     */     }
/*     */ 
/*     */     
/*     */     void epollInReady() {
/*  84 */       assert AbstractEpollServerChannel.this.eventLoop().inEventLoop();
/*  85 */       EpollChannelConfig epollChannelConfig = AbstractEpollServerChannel.this.config();
/*  86 */       if (AbstractEpollServerChannel.this.shouldBreakEpollInReady((ChannelConfig)epollChannelConfig)) {
/*  87 */         clearEpollIn0();
/*     */         return;
/*     */       } 
/*  90 */       EpollRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
/*  91 */       ChannelPipeline pipeline = AbstractEpollServerChannel.this.pipeline();
/*  92 */       allocHandle.reset((ChannelConfig)epollChannelConfig);
/*  93 */       allocHandle.attemptedBytesRead(1);
/*     */       
/*  95 */       Throwable exception = null;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*     */         while (true) {
/*     */           
/* 102 */           try { allocHandle.lastBytesRead(AbstractEpollServerChannel.this.socket.accept(this.acceptedAddress));
/* 103 */             if (allocHandle.lastBytesRead() == -1) {
/*     */               break;
/*     */             }
/*     */             
/* 107 */             allocHandle.incMessagesRead(1);
/*     */             
/* 109 */             this.readPending = false;
/* 110 */             pipeline.fireChannelRead(AbstractEpollServerChannel.this.newChildChannel(allocHandle.lastBytesRead(), this.acceptedAddress, 1, this.acceptedAddress[0]));
/*     */             
/* 112 */             if (!allocHandle.continueReading())
/* 113 */               break;  } catch (Throwable t)
/* 114 */           { exception = t; break; }
/*     */         
/* 116 */         }  allocHandle.readComplete();
/* 117 */         pipeline.fireChannelReadComplete();
/*     */         
/* 119 */         if (exception != null) {
/* 120 */           pipeline.fireExceptionCaught(exception);
/*     */         }
/*     */       } finally {
/* 123 */         if (shouldStopReading((ChannelConfig)epollChannelConfig)) {
/* 124 */           AbstractEpollServerChannel.this.clearEpollIn();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 132 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected abstract Channel newChildChannel(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\AbstractEpollServerChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */