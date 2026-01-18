/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.internal.AdaptiveCalculator;
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
/*     */ public class AdaptiveRecvByteBufAllocator
/*     */   extends DefaultMaxMessagesRecvByteBufAllocator
/*     */ {
/*     */   public static final int DEFAULT_MINIMUM = 64;
/*     */   public static final int DEFAULT_INITIAL = 2048;
/*     */   public static final int DEFAULT_MAXIMUM = 65536;
/*     */   private final int minimum;
/*     */   private final int initial;
/*     */   @Deprecated
/*  43 */   public static final AdaptiveRecvByteBufAllocator DEFAULT = new AdaptiveRecvByteBufAllocator();
/*     */   private final int maximum;
/*     */   
/*     */   private final class HandleImpl
/*     */     extends DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle {
/*     */     HandleImpl(int minimum, int initial, int maximum) {
/*  49 */       this.calculator = new AdaptiveCalculator(minimum, initial, maximum);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final AdaptiveCalculator calculator;
/*     */ 
/*     */     
/*     */     public void lastBytesRead(int bytes) {
/*  58 */       if (bytes == attemptedBytesRead()) {
/*  59 */         this.calculator.record(bytes);
/*     */       }
/*  61 */       super.lastBytesRead(bytes);
/*     */     }
/*     */ 
/*     */     
/*     */     public int guess() {
/*  66 */       return this.calculator.nextSize();
/*     */     }
/*     */ 
/*     */     
/*     */     public void readComplete() {
/*  71 */       this.calculator.record(totalBytesRead());
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
/*     */   
/*     */   public AdaptiveRecvByteBufAllocator() {
/*  85 */     this(64, 2048, 65536);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AdaptiveRecvByteBufAllocator(int minimum, int initial, int maximum) {
/*  96 */     ObjectUtil.checkPositive(minimum, "minimum");
/*  97 */     if (initial < minimum) {
/*  98 */       throw new IllegalArgumentException("initial: " + initial);
/*     */     }
/* 100 */     if (maximum < initial) {
/* 101 */       throw new IllegalArgumentException("maximum: " + maximum);
/*     */     }
/*     */     
/* 104 */     this.minimum = minimum;
/* 105 */     this.initial = initial;
/* 106 */     this.maximum = maximum;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RecvByteBufAllocator.Handle newHandle() {
/* 112 */     return new HandleImpl(this.minimum, this.initial, this.maximum);
/*     */   }
/*     */ 
/*     */   
/*     */   public AdaptiveRecvByteBufAllocator respectMaybeMoreData(boolean respectMaybeMoreData) {
/* 117 */     super.respectMaybeMoreData(respectMaybeMoreData);
/* 118 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\AdaptiveRecvByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */