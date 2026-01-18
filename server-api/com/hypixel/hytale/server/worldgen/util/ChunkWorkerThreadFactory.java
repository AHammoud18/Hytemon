/*    */ package com.hypixel.hytale.server.worldgen.util;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.ChunkGeneratorResource;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkWorkerThreadFactory
/*    */   implements ThreadFactory
/*    */ {
/* 15 */   private static final AtomicInteger FACTORY_COUNTER = new AtomicInteger();
/*    */   
/*    */   private final ChunkGenerator chunkGenerator;
/*    */   private final String threadNameFormat;
/*    */   @Nonnull
/*    */   private final Integer factoryId;
/*    */   @Nonnull
/*    */   private final AtomicInteger threadCounter;
/*    */   
/*    */   public ChunkWorkerThreadFactory(ChunkGenerator chunkGenerator, String threadNameFormat) {
/* 25 */     this.chunkGenerator = chunkGenerator;
/* 26 */     this.threadNameFormat = threadNameFormat;
/* 27 */     this.factoryId = Integer.valueOf(FACTORY_COUNTER.incrementAndGet());
/* 28 */     this.threadCounter = new AtomicInteger();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Thread newThread(Runnable r) {
/* 34 */     Integer threadId = Integer.valueOf(this.threadCounter.incrementAndGet());
/* 35 */     String threadName = String.format(this.threadNameFormat, new Object[] { this.factoryId, threadId });
/* 36 */     ChunkWorker workerThread = new ChunkWorker(r, threadName, this.chunkGenerator);
/* 37 */     workerThread.setDaemon(true);
/* 38 */     return workerThread;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 44 */     return "ChunkWorkerThreadFactory{chunkGenerator=" + String.valueOf(this.chunkGenerator) + ", threadNameFormat='" + this.threadNameFormat + "', factoryId=" + this.factoryId + ", threadCounter=" + String.valueOf(this.threadCounter) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static class ChunkWorker
/*    */     extends Thread
/*    */   {
/*    */     protected final ChunkGenerator chunkGenerator;
/*    */ 
/*    */     
/*    */     protected ChunkWorker(Runnable r, @Nonnull String name, ChunkGenerator chunkGenerator) {
/* 56 */       super(r, name);
/* 57 */       this.chunkGenerator = chunkGenerator;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void run() {
/* 63 */       ChunkGeneratorResource resource = ChunkGenerator.getResource();
/* 64 */       resource.init(this.chunkGenerator);
/*    */       
/*    */       try {
/* 67 */         super.run();
/*    */       } finally {
/*    */         
/* 70 */         resource.release();
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\ChunkWorkerThreadFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */