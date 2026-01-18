/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
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
/*     */ public class DefaultChannelConfig
/*     */   implements ChannelConfig
/*     */ {
/*  48 */   private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
/*     */   
/*  53 */   private static final AtomicIntegerFieldUpdater<DefaultChannelConfig> AUTOREAD_UPDATER = AtomicIntegerFieldUpdater.newUpdater(DefaultChannelConfig.class, "autoRead");
/*     */   
/*  55 */   private static final AtomicReferenceFieldUpdater<DefaultChannelConfig, WriteBufferWaterMark> WATERMARK_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultChannelConfig.class, WriteBufferWaterMark.class, "writeBufferWaterMark");
/*     */ 
/*     */   
/*     */   protected final Channel channel;
/*     */   
/*  60 */   private volatile ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
/*     */   private volatile RecvByteBufAllocator rcvBufAllocator;
/*  62 */   private volatile MessageSizeEstimator msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
/*     */   
/*  64 */   private volatile int connectTimeoutMillis = 30000;
/*  65 */   private volatile int writeSpinCount = 16;
/*  66 */   private volatile int maxMessagesPerWrite = Integer.MAX_VALUE;
/*     */   
/*  68 */   private volatile int autoRead = 1;
/*     */   
/*     */   private volatile boolean autoClose = true;
/*  71 */   private volatile WriteBufferWaterMark writeBufferWaterMark = WriteBufferWaterMark.DEFAULT;
/*     */   private volatile boolean pinEventExecutor = true;
/*     */   
/*     */   public DefaultChannelConfig(Channel channel) {
/*  75 */     this(channel, new AdaptiveRecvByteBufAllocator());
/*     */   }
/*     */   
/*     */   protected DefaultChannelConfig(Channel channel, RecvByteBufAllocator allocator) {
/*  79 */     setRecvByteBufAllocator(allocator, channel.metadata());
/*  80 */     this.channel = channel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  86 */     return getOptions(null, (ChannelOption<?>[])new ChannelOption[] { ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.AUTO_CLOSE, ChannelOption.RECVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.WRITE_BUFFER_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR, ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP, ChannelOption.MAX_MESSAGES_PER_WRITE });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> result, ChannelOption<?>... options) {
/*  96 */     if (result == null) {
/*  97 */       result = new IdentityHashMap<>();
/*     */     }
/*  99 */     for (ChannelOption<?> o : options) {
/* 100 */       result.put(o, getOption(o));
/*     */     }
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setOptions(Map<ChannelOption<?>, ?> options) {
/* 108 */     ObjectUtil.checkNotNull(options, "options");
/*     */     
/* 110 */     boolean setAllOptions = true;
/* 111 */     for (Map.Entry<ChannelOption<?>, ?> e : options.entrySet()) {
/* 112 */       if (!setOption(e.getKey(), e.getValue())) {
/* 113 */         setAllOptions = false;
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return setAllOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/* 123 */     ObjectUtil.checkNotNull(option, "option");
/*     */     
/* 125 */     if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
/* 126 */       return (T)Integer.valueOf(getConnectTimeoutMillis());
/*     */     }
/* 128 */     if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
/* 129 */       return (T)Integer.valueOf(getMaxMessagesPerRead());
/*     */     }
/* 131 */     if (option == ChannelOption.WRITE_SPIN_COUNT) {
/* 132 */       return (T)Integer.valueOf(getWriteSpinCount());
/*     */     }
/* 134 */     if (option == ChannelOption.ALLOCATOR) {
/* 135 */       return (T)getAllocator();
/*     */     }
/* 137 */     if (option == ChannelOption.RECVBUF_ALLOCATOR) {
/* 138 */       return (T)getRecvByteBufAllocator();
/*     */     }
/* 140 */     if (option == ChannelOption.AUTO_READ) {
/* 141 */       return (T)Boolean.valueOf(isAutoRead());
/*     */     }
/* 143 */     if (option == ChannelOption.AUTO_CLOSE) {
/* 144 */       return (T)Boolean.valueOf(isAutoClose());
/*     */     }
/* 146 */     if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
/* 147 */       return (T)Integer.valueOf(getWriteBufferHighWaterMark());
/*     */     }
/* 149 */     if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
/* 150 */       return (T)Integer.valueOf(getWriteBufferLowWaterMark());
/*     */     }
/* 152 */     if (option == ChannelOption.WRITE_BUFFER_WATER_MARK) {
/* 153 */       return (T)getWriteBufferWaterMark();
/*     */     }
/* 155 */     if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
/* 156 */       return (T)getMessageSizeEstimator();
/*     */     }
/* 158 */     if (option == ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
/* 159 */       return (T)Boolean.valueOf(getPinEventExecutorPerGroup());
/*     */     }
/* 161 */     if (option == ChannelOption.MAX_MESSAGES_PER_WRITE) {
/* 162 */       return (T)Integer.valueOf(getMaxMessagesPerWrite());
/*     */     }
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 170 */     validate(option, value);
/*     */     
/* 172 */     if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
/* 173 */       setConnectTimeoutMillis(((Integer)value).intValue());
/* 174 */     } else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
/* 175 */       setMaxMessagesPerRead(((Integer)value).intValue());
/* 176 */     } else if (option == ChannelOption.WRITE_SPIN_COUNT) {
/* 177 */       setWriteSpinCount(((Integer)value).intValue());
/* 178 */     } else if (option == ChannelOption.ALLOCATOR) {
/* 179 */       setAllocator((ByteBufAllocator)value);
/* 180 */     } else if (option == ChannelOption.RECVBUF_ALLOCATOR) {
/* 181 */       setRecvByteBufAllocator((RecvByteBufAllocator)value);
/* 182 */     } else if (option == ChannelOption.AUTO_READ) {
/* 183 */       setAutoRead(((Boolean)value).booleanValue());
/* 184 */     } else if (option == ChannelOption.AUTO_CLOSE) {
/* 185 */       setAutoClose(((Boolean)value).booleanValue());
/* 186 */     } else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
/* 187 */       setWriteBufferHighWaterMark(((Integer)value).intValue());
/* 188 */     } else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
/* 189 */       setWriteBufferLowWaterMark(((Integer)value).intValue());
/* 190 */     } else if (option == ChannelOption.WRITE_BUFFER_WATER_MARK) {
/* 191 */       setWriteBufferWaterMark((WriteBufferWaterMark)value);
/* 192 */     } else if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
/* 193 */       setMessageSizeEstimator((MessageSizeEstimator)value);
/* 194 */     } else if (option == ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
/* 195 */       setPinEventExecutorPerGroup(((Boolean)value).booleanValue());
/* 196 */     } else if (option == ChannelOption.MAX_MESSAGES_PER_WRITE) {
/* 197 */       setMaxMessagesPerWrite(((Integer)value).intValue());
/*     */     } else {
/* 199 */       return false;
/*     */     } 
/*     */     
/* 202 */     return true;
/*     */   }
/*     */   
/*     */   protected <T> void validate(ChannelOption<T> option, T value) {
/* 206 */     ((ChannelOption<T>)ObjectUtil.checkNotNull(option, "option")).validate(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getConnectTimeoutMillis() {
/* 211 */     return this.connectTimeoutMillis;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 216 */     ObjectUtil.checkPositiveOrZero(connectTimeoutMillis, "connectTimeoutMillis");
/* 217 */     this.connectTimeoutMillis = connectTimeoutMillis;
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getMaxMessagesPerRead() {
/*     */     try {
/* 231 */       MaxMessagesRecvByteBufAllocator allocator = getRecvByteBufAllocator();
/* 232 */       return allocator.maxMessagesPerRead();
/* 233 */     } catch (ClassCastException e) {
/* 234 */       throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", e);
/*     */     } 
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
/*     */   public ChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/*     */     try {
/* 249 */       MaxMessagesRecvByteBufAllocator allocator = getRecvByteBufAllocator();
/* 250 */       allocator.maxMessagesPerRead(maxMessagesPerRead);
/* 251 */       return this;
/* 252 */     } catch (ClassCastException e) {
/* 253 */       throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxMessagesPerWrite() {
/* 263 */     return this.maxMessagesPerWrite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelConfig setMaxMessagesPerWrite(int maxMessagesPerWrite) {
/* 271 */     this.maxMessagesPerWrite = ObjectUtil.checkPositive(maxMessagesPerWrite, "maxMessagesPerWrite");
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWriteSpinCount() {
/* 277 */     return this.writeSpinCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 282 */     ObjectUtil.checkPositive(writeSpinCount, "writeSpinCount");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     if (writeSpinCount == Integer.MAX_VALUE) {
/* 288 */       writeSpinCount--;
/*     */     }
/* 290 */     this.writeSpinCount = writeSpinCount;
/* 291 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator getAllocator() {
/* 296 */     return this.allocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 301 */     this.allocator = (ByteBufAllocator)ObjectUtil.checkNotNull(allocator, "allocator");
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends RecvByteBufAllocator> T getRecvByteBufAllocator() {
/* 308 */     return (T)this.rcvBufAllocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 313 */     this.rcvBufAllocator = (RecvByteBufAllocator)ObjectUtil.checkNotNull(allocator, "allocator");
/* 314 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setRecvByteBufAllocator(RecvByteBufAllocator allocator, ChannelMetadata metadata) {
/* 324 */     ObjectUtil.checkNotNull(allocator, "allocator");
/* 325 */     ObjectUtil.checkNotNull(metadata, "metadata");
/* 326 */     if (allocator instanceof MaxMessagesRecvByteBufAllocator) {
/* 327 */       ((MaxMessagesRecvByteBufAllocator)allocator).maxMessagesPerRead(metadata.defaultMaxMessagesPerRead());
/*     */     }
/* 329 */     setRecvByteBufAllocator(allocator);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAutoRead() {
/* 334 */     return (this.autoRead == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setAutoRead(boolean autoRead) {
/* 339 */     boolean oldAutoRead = (AUTOREAD_UPDATER.getAndSet(this, autoRead ? 1 : 0) == 1);
/* 340 */     if (autoRead && !oldAutoRead) {
/* 341 */       this.channel.read();
/* 342 */     } else if (!autoRead && oldAutoRead) {
/* 343 */       autoReadCleared();
/*     */     } 
/* 345 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void autoReadCleared() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoClose() {
/* 356 */     return this.autoClose;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setAutoClose(boolean autoClose) {
/* 361 */     this.autoClose = autoClose;
/* 362 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWriteBufferHighWaterMark() {
/* 367 */     return this.writeBufferWaterMark.high();
/*     */   }
/*     */   
/*     */   public ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/*     */     WriteBufferWaterMark waterMark;
/* 372 */     ObjectUtil.checkPositiveOrZero(writeBufferHighWaterMark, "writeBufferHighWaterMark");
/*     */     do {
/* 374 */       waterMark = this.writeBufferWaterMark;
/* 375 */       if (writeBufferHighWaterMark < waterMark.low()) {
/* 376 */         throw new IllegalArgumentException("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + waterMark
/*     */             
/* 378 */             .low() + "): " + writeBufferHighWaterMark);
/*     */       }
/*     */     }
/* 381 */     while (!WATERMARK_UPDATER.compareAndSet(this, waterMark, new WriteBufferWaterMark(waterMark
/* 382 */           .low(), writeBufferHighWaterMark, false)));
/* 383 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWriteBufferLowWaterMark() {
/* 390 */     return this.writeBufferWaterMark.low();
/*     */   }
/*     */   
/*     */   public ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/*     */     WriteBufferWaterMark waterMark;
/* 395 */     ObjectUtil.checkPositiveOrZero(writeBufferLowWaterMark, "writeBufferLowWaterMark");
/*     */     do {
/* 397 */       waterMark = this.writeBufferWaterMark;
/* 398 */       if (writeBufferLowWaterMark > waterMark.high()) {
/* 399 */         throw new IllegalArgumentException("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + waterMark
/*     */             
/* 401 */             .high() + "): " + writeBufferLowWaterMark);
/*     */       }
/*     */     }
/* 404 */     while (!WATERMARK_UPDATER.compareAndSet(this, waterMark, new WriteBufferWaterMark(writeBufferLowWaterMark, waterMark
/* 405 */           .high(), false)));
/* 406 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 413 */     this.writeBufferWaterMark = (WriteBufferWaterMark)ObjectUtil.checkNotNull(writeBufferWaterMark, "writeBufferWaterMark");
/* 414 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public WriteBufferWaterMark getWriteBufferWaterMark() {
/* 419 */     return this.writeBufferWaterMark;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageSizeEstimator getMessageSizeEstimator() {
/* 424 */     return this.msgSizeEstimator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 429 */     this.msgSizeEstimator = (MessageSizeEstimator)ObjectUtil.checkNotNull(estimator, "estimator");
/* 430 */     return this;
/*     */   }
/*     */   
/*     */   private ChannelConfig setPinEventExecutorPerGroup(boolean pinEventExecutor) {
/* 434 */     this.pinEventExecutor = pinEventExecutor;
/* 435 */     return this;
/*     */   }
/*     */   
/*     */   private boolean getPinEventExecutorPerGroup() {
/* 439 */     return this.pinEventExecutor;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\DefaultChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */