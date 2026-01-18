/*    */ package io.netty.util.concurrent;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Ticker
/*    */ {
/*    */   static Ticker systemTicker() {
/* 30 */     return SystemTicker.INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static MockTicker newMockTicker() {
/* 39 */     return new DefaultMockTicker();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long initialNanoTime();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long nanoTime();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void sleep(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void sleepMillis(long delayMillis) throws InterruptedException {
/* 72 */     sleep(delayMillis, TimeUnit.MILLISECONDS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\Ticker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */