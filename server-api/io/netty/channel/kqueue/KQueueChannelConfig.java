/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import io.netty.channel.unix.IntegerUnixChannelOption;
/*     */ import io.netty.channel.unix.Limits;
/*     */ import io.netty.channel.unix.RawUnixChannelOption;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Map;
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
/*     */ public class KQueueChannelConfig
/*     */   extends DefaultChannelConfig
/*     */ {
/*     */   private volatile boolean transportProvidesGuess;
/*  38 */   private volatile long maxBytesPerGatheringWrite = Limits.SSIZE_MAX;
/*     */   
/*     */   KQueueChannelConfig(AbstractKQueueChannel channel) {
/*  41 */     super((Channel)channel);
/*     */   }
/*     */   
/*     */   KQueueChannelConfig(AbstractKQueueChannel channel, RecvByteBufAllocator recvByteBufAllocator) {
/*  45 */     super((Channel)channel, recvByteBufAllocator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  51 */     return getOptions(super.getOptions(), new ChannelOption[] { KQueueChannelOption.RCV_ALLOC_TRANSPORT_PROVIDES_GUESS });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  57 */     if (option == KQueueChannelOption.RCV_ALLOC_TRANSPORT_PROVIDES_GUESS) {
/*  58 */       return (T)Boolean.valueOf(getRcvAllocTransportProvidesGuess());
/*     */     }
/*     */     try {
/*  61 */       if (option instanceof IntegerUnixChannelOption) {
/*  62 */         IntegerUnixChannelOption opt = (IntegerUnixChannelOption)option;
/*  63 */         return (T)Integer.valueOf(((AbstractKQueueChannel)this.channel).socket.getIntOpt(opt
/*  64 */               .level(), opt.optname()));
/*     */       } 
/*  66 */       if (option instanceof RawUnixChannelOption) {
/*  67 */         RawUnixChannelOption opt = (RawUnixChannelOption)option;
/*  68 */         ByteBuffer out = ByteBuffer.allocate(opt.length());
/*  69 */         ((AbstractKQueueChannel)this.channel).socket.getRawOpt(opt.level(), opt.optname(), out);
/*  70 */         return (T)out.flip();
/*     */       } 
/*  72 */     } catch (IOException e) {
/*  73 */       throw new ChannelException(e);
/*     */     } 
/*  75 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  80 */     validate(option, value);
/*     */     
/*  82 */     if (option == KQueueChannelOption.RCV_ALLOC_TRANSPORT_PROVIDES_GUESS) {
/*  83 */       setRcvAllocTransportProvidesGuess(((Boolean)value).booleanValue());
/*     */     } else {
/*     */       try {
/*  86 */         if (option instanceof IntegerUnixChannelOption) {
/*  87 */           IntegerUnixChannelOption opt = (IntegerUnixChannelOption)option;
/*  88 */           ((AbstractKQueueChannel)this.channel).socket.setIntOpt(opt.level(), opt.optname(), ((Integer)value).intValue());
/*  89 */           return true;
/*  90 */         }  if (option instanceof RawUnixChannelOption) {
/*  91 */           RawUnixChannelOption opt = (RawUnixChannelOption)option;
/*  92 */           ((AbstractKQueueChannel)this.channel).socket.setRawOpt(opt.level(), opt.optname(), (ByteBuffer)value);
/*  93 */           return true;
/*     */         } 
/*  95 */       } catch (IOException e) {
/*  96 */         throw new ChannelException(e);
/*     */       } 
/*  98 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
/* 112 */     this.transportProvidesGuess = transportProvidesGuess;
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean getRcvAllocTransportProvidesGuess() {
/* 124 */     return this.transportProvidesGuess;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 129 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 136 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 142 */     super.setWriteSpinCount(writeSpinCount);
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 148 */     super.setAllocator(allocator);
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 154 */     if (!(allocator.newHandle() instanceof RecvByteBufAllocator.ExtendedHandle)) {
/* 155 */       throw new IllegalArgumentException("allocator.newHandle() must return an object of type: " + RecvByteBufAllocator.ExtendedHandle.class);
/*     */     }
/*     */     
/* 158 */     super.setRecvByteBufAllocator(allocator);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueChannelConfig setAutoRead(boolean autoRead) {
/* 164 */     super.setAutoRead(autoRead);
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 171 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 178 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 184 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 190 */     super.setMessageSizeEstimator(estimator);
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void autoReadCleared() {
/* 196 */     ((AbstractKQueueChannel)this.channel).clearReadFilter();
/*     */   }
/*     */   
/*     */   final void setMaxBytesPerGatheringWrite(long maxBytesPerGatheringWrite) {
/* 200 */     this.maxBytesPerGatheringWrite = Math.min(Limits.SSIZE_MAX, maxBytesPerGatheringWrite);
/*     */   }
/*     */   
/*     */   final long getMaxBytesPerGatheringWrite() {
/* 204 */     return this.maxBytesPerGatheringWrite;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */