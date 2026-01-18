/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ final class SystemTicker
/*    */   implements Ticker
/*    */ {
/* 22 */   static final SystemTicker INSTANCE = new SystemTicker();
/* 23 */   private static final long START_TIME = System.nanoTime();
/*    */ 
/*    */   
/*    */   public long initialNanoTime() {
/* 27 */     return START_TIME;
/*    */   }
/*    */ 
/*    */   
/*    */   public long nanoTime() {
/* 32 */     return System.nanoTime() - START_TIME;
/*    */   }
/*    */ 
/*    */   
/*    */   public void sleep(long delay, TimeUnit unit) throws InterruptedException {
/* 37 */     Objects.requireNonNull(unit, "unit");
/* 38 */     unit.sleep(delay);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\SystemTicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */