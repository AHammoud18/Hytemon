/*    */ package org.jline.utils;
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
/*    */ public class Timeout
/*    */ {
/*    */   private final long timeout;
/* 52 */   private long cur = 0L;
/* 53 */   private long end = Long.MAX_VALUE;
/*    */   
/*    */   public Timeout(long timeout) {
/* 56 */     this.timeout = timeout;
/*    */   }
/*    */   
/*    */   public boolean isInfinite() {
/* 60 */     return (this.timeout <= 0L);
/*    */   }
/*    */   
/*    */   public boolean isFinite() {
/* 64 */     return (this.timeout > 0L);
/*    */   }
/*    */   
/*    */   public boolean elapsed() {
/* 68 */     if (this.timeout > 0L) {
/* 69 */       this.cur = System.currentTimeMillis();
/* 70 */       if (this.end == Long.MAX_VALUE) {
/* 71 */         this.end = this.cur + this.timeout;
/*    */       }
/* 73 */       return (this.cur >= this.end);
/*    */     } 
/* 75 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public long timeout() {
/* 80 */     return (this.timeout > 0L) ? Math.max(1L, this.end - this.cur) : this.timeout;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\Timeout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */