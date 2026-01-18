/*    */ package com.hypixel.hytale.server.worldgen.util;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ public final class ChunkThreadPoolExecutor extends ThreadPoolExecutor {
/*  8 */   private static final AtomicInteger GENERATION_COUNTER = new AtomicInteger(0);
/*    */ 
/*    */   
/*    */   private final int generation;
/*    */ 
/*    */   
/*    */   private final Runnable shutdownHook;
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, Runnable shutdownHook) {
/* 19 */     super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
/* 20 */     this.generation = GENERATION_COUNTER.getAndIncrement();
/* 21 */     this.shutdownHook = shutdownHook;
/* 22 */     LogUtil.getLogger().at(Level.INFO).log("Initialized ChunkGenerator-%d executor", this.generation);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void terminated() {
/* 27 */     this.shutdownHook.run();
/* 28 */     LogUtil.getLogger().at(Level.INFO).log("ChunkGenerator-%d executor shutdown complete", this.generation);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\ChunkThreadPoolExecutor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */