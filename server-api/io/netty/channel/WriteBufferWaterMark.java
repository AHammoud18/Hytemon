/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public final class WriteBufferWaterMark
/*    */ {
/*    */   private static final int DEFAULT_LOW_WATER_MARK = 32768;
/*    */   private static final int DEFAULT_HIGH_WATER_MARK = 65536;
/* 41 */   public static final WriteBufferWaterMark DEFAULT = new WriteBufferWaterMark(32768, 65536, false);
/*    */ 
/*    */ 
/*    */   
/*    */   private final int low;
/*    */ 
/*    */ 
/*    */   
/*    */   private final int high;
/*    */ 
/*    */ 
/*    */   
/*    */   public WriteBufferWaterMark(int low, int high) {
/* 54 */     this(low, high, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   WriteBufferWaterMark(int low, int high, boolean validate) {
/* 61 */     if (validate) {
/* 62 */       ObjectUtil.checkPositiveOrZero(low, "low");
/* 63 */       if (high < low) {
/* 64 */         throw new IllegalArgumentException("write buffer's high water mark cannot be less than  low water mark (" + low + "): " + high);
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 70 */     this.low = low;
/* 71 */     this.high = high;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int low() {
/* 78 */     return this.low;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int high() {
/* 85 */     return this.high;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 95 */     StringBuilder builder = (new StringBuilder(55)).append("WriteBufferWaterMark(low: ").append(this.low).append(", high: ").append(this.high).append(")");
/* 96 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\WriteBufferWaterMark.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */