/*     */ package io.netty.channel.epoll;
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
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public class EpollChannelConfig
/*     */   extends DefaultChannelConfig
/*     */ {
/*  39 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(EpollChannelConfig.class);
/*     */   
/*  41 */   private volatile long maxBytesPerGatheringWrite = Limits.SSIZE_MAX;
/*     */   
/*     */   protected EpollChannelConfig(Channel channel) {
/*  44 */     super(checkAbstractEpollChannel(channel));
/*     */   }
/*     */   
/*     */   protected EpollChannelConfig(Channel channel, RecvByteBufAllocator recvByteBufAllocator) {
/*  48 */     super(checkAbstractEpollChannel(channel), recvByteBufAllocator);
/*     */   }
/*     */   
/*     */   protected LinuxSocket socket() {
/*  52 */     return ((AbstractEpollChannel)this.channel).socket;
/*     */   }
/*     */   
/*     */   private static Channel checkAbstractEpollChannel(Channel channel) {
/*  56 */     if (!(channel instanceof AbstractEpollChannel)) {
/*  57 */       throw new IllegalArgumentException("channel is not AbstractEpollChannel: " + channel.getClass());
/*     */     }
/*  59 */     return channel;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  64 */     return getOptions(super.getOptions(), new ChannelOption[] { EpollChannelOption.EPOLL_MODE });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  70 */     if (option == EpollChannelOption.EPOLL_MODE) {
/*  71 */       return (T)getEpollMode();
/*     */     }
/*     */     try {
/*  74 */       if (option instanceof IntegerUnixChannelOption) {
/*  75 */         IntegerUnixChannelOption opt = (IntegerUnixChannelOption)option;
/*  76 */         return (T)Integer.valueOf(((AbstractEpollChannel)this.channel).socket.getIntOpt(opt
/*  77 */               .level(), opt.optname()));
/*     */       } 
/*  79 */       if (option instanceof RawUnixChannelOption) {
/*  80 */         RawUnixChannelOption opt = (RawUnixChannelOption)option;
/*  81 */         ByteBuffer out = ByteBuffer.allocate(opt.length());
/*  82 */         ((AbstractEpollChannel)this.channel).socket.getRawOpt(opt.level(), opt.optname(), out);
/*  83 */         return (T)out.flip();
/*     */       } 
/*  85 */     } catch (IOException e) {
/*  86 */       throw new ChannelException(e);
/*     */     } 
/*  88 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  93 */     validate(option, value);
/*  94 */     if (option == EpollChannelOption.EPOLL_MODE) {
/*  95 */       setEpollMode((EpollMode)value);
/*     */     } else {
/*     */       try {
/*  98 */         if (option instanceof IntegerUnixChannelOption) {
/*  99 */           IntegerUnixChannelOption opt = (IntegerUnixChannelOption)option;
/* 100 */           ((AbstractEpollChannel)this.channel).socket.setIntOpt(opt.level(), opt.optname(), ((Integer)value).intValue());
/* 101 */           return true;
/* 102 */         }  if (option instanceof RawUnixChannelOption) {
/* 103 */           RawUnixChannelOption opt = (RawUnixChannelOption)option;
/* 104 */           ((AbstractEpollChannel)this.channel).socket.setRawOpt(opt.level(), opt.optname(), (ByteBuffer)value);
/* 105 */           return true;
/*     */         } 
/* 107 */       } catch (IOException e) {
/* 108 */         throw new ChannelException(e);
/*     */       } 
/* 110 */       return super.setOption(option, value);
/*     */     } 
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 117 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 124 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 130 */     super.setWriteSpinCount(writeSpinCount);
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 136 */     super.setAllocator(allocator);
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 142 */     if (!(allocator.newHandle() instanceof RecvByteBufAllocator.ExtendedHandle)) {
/* 143 */       throw new IllegalArgumentException("allocator.newHandle() must return an object of type: " + RecvByteBufAllocator.ExtendedHandle.class);
/*     */     }
/*     */     
/* 146 */     super.setRecvByteBufAllocator(allocator);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollChannelConfig setAutoRead(boolean autoRead) {
/* 152 */     super.setAutoRead(autoRead);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 159 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 166 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 172 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 178 */     super.setMessageSizeEstimator(estimator);
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollMode getEpollMode() {
/* 192 */     return EpollMode.LEVEL_TRIGGERED;
/*     */   }
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
/*     */   @Deprecated
/*     */   public EpollChannelConfig setEpollMode(EpollMode mode) {
/* 207 */     LOGGER.debug("Changing the EpollMode is not supported anymore, this is just a no-op");
/* 208 */     return this;
/*     */   }
/*     */   
/*     */   private void checkChannelNotRegistered() {
/* 212 */     if (this.channel.isRegistered()) {
/* 213 */       throw new IllegalStateException("EpollMode can only be changed before channel is registered");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void autoReadCleared() {
/* 219 */     ((AbstractEpollChannel)this.channel).clearEpollIn();
/*     */   }
/*     */   
/*     */   protected final void setMaxBytesPerGatheringWrite(long maxBytesPerGatheringWrite) {
/* 223 */     this.maxBytesPerGatheringWrite = maxBytesPerGatheringWrite;
/*     */   }
/*     */   
/*     */   protected final long getMaxBytesPerGatheringWrite() {
/* 227 */     return this.maxBytesPerGatheringWrite;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */