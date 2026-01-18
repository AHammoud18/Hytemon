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
/*     */ public abstract class DefaultMaxMessagesRecvByteBufAllocator
/*     */   implements MaxMessagesRecvByteBufAllocator
/*     */ {
/*     */   private final boolean ignoreBytesRead;
/*     */   private volatile int maxMessagesPerRead;
/*     */   private volatile boolean respectMaybeMoreData = true;
/*     */   
/*     */   public DefaultMaxMessagesRecvByteBufAllocator() {
/*  34 */     this(1);
/*     */   }
/*     */   
/*     */   public DefaultMaxMessagesRecvByteBufAllocator(int maxMessagesPerRead) {
/*  38 */     this(maxMessagesPerRead, false);
/*     */   }
/*     */   
/*     */   DefaultMaxMessagesRecvByteBufAllocator(int maxMessagesPerRead, boolean ignoreBytesRead) {
/*  42 */     this.ignoreBytesRead = ignoreBytesRead;
/*  43 */     maxMessagesPerRead(maxMessagesPerRead);
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxMessagesPerRead() {
/*  48 */     return this.maxMessagesPerRead;
/*     */   }
/*     */ 
/*     */   
/*     */   public MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int maxMessagesPerRead) {
/*  53 */     ObjectUtil.checkPositive(maxMessagesPerRead, "maxMessagesPerRead");
/*  54 */     this.maxMessagesPerRead = maxMessagesPerRead;
/*  55 */     return this;
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
/*     */ 
/*     */   
/*     */   public DefaultMaxMessagesRecvByteBufAllocator respectMaybeMoreData(boolean respectMaybeMoreData) {
/*  71 */     this.respectMaybeMoreData = respectMaybeMoreData;
/*  72 */     return this;
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
/*     */   
/*     */   public final boolean respectMaybeMoreData() {
/*  87 */     return this.respectMaybeMoreData;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract class MaxMessageHandle
/*     */     implements RecvByteBufAllocator.ExtendedHandle
/*     */   {
/*     */     private ChannelConfig config;
/*     */     private int maxMessagePerRead;
/*     */     private int totalMessages;
/*     */     private int totalBytesRead;
/*     */     private int attemptedBytesRead;
/*     */     private int lastBytesRead;
/* 100 */     private final boolean respectMaybeMoreData = DefaultMaxMessagesRecvByteBufAllocator.this.respectMaybeMoreData;
/* 101 */     private final UncheckedBooleanSupplier defaultMaybeMoreSupplier = new UncheckedBooleanSupplier()
/*     */       {
/*     */         public boolean get() {
/* 104 */           return (DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle.this.attemptedBytesRead == DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle.this.lastBytesRead);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void reset(ChannelConfig config) {
/* 113 */       this.config = config;
/* 114 */       this.maxMessagePerRead = DefaultMaxMessagesRecvByteBufAllocator.this.maxMessagesPerRead();
/* 115 */       this.totalMessages = this.totalBytesRead = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuf allocate(ByteBufAllocator alloc) {
/* 120 */       return alloc.ioBuffer(guess());
/*     */     }
/*     */ 
/*     */     
/*     */     public final void incMessagesRead(int amt) {
/* 125 */       this.totalMessages += amt;
/*     */     }
/*     */ 
/*     */     
/*     */     public void lastBytesRead(int bytes) {
/* 130 */       this.lastBytesRead = bytes;
/* 131 */       if (bytes > 0) {
/* 132 */         this.totalBytesRead += bytes;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public final int lastBytesRead() {
/* 138 */       return this.lastBytesRead;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueReading() {
/* 143 */       return continueReading(this.defaultMaybeMoreSupplier);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueReading(UncheckedBooleanSupplier maybeMoreDataSupplier) {
/* 148 */       return (this.config.isAutoRead() && (!this.respectMaybeMoreData || maybeMoreDataSupplier
/* 149 */         .get()) && this.totalMessages < this.maxMessagePerRead && (DefaultMaxMessagesRecvByteBufAllocator.this
/* 150 */         .ignoreBytesRead || this.totalBytesRead > 0));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void readComplete() {}
/*     */ 
/*     */     
/*     */     public int attemptedBytesRead() {
/* 159 */       return this.attemptedBytesRead;
/*     */     }
/*     */ 
/*     */     
/*     */     public void attemptedBytesRead(int bytes) {
/* 164 */       this.attemptedBytesRead = bytes;
/*     */     }
/*     */     
/*     */     protected final int totalBytesRead() {
/* 168 */       return (this.totalBytesRead < 0) ? Integer.MAX_VALUE : this.totalBytesRead;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\DefaultMaxMessagesRecvByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */