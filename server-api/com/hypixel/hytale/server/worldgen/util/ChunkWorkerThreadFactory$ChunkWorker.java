/*    */ package com.hypixel.hytale.server.worldgen.util;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.ChunkGeneratorResource;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class ChunkWorker
/*    */   extends Thread
/*    */ {
/*    */   protected final ChunkGenerator chunkGenerator;
/*    */   
/*    */   protected ChunkWorker(Runnable r, @Nonnull String name, ChunkGenerator chunkGenerator) {
/* 56 */     super(r, name);
/* 57 */     this.chunkGenerator = chunkGenerator;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 63 */     ChunkGeneratorResource resource = ChunkGenerator.getResource();
/* 64 */     resource.init(this.chunkGenerator);
/*    */     
/*    */     try {
/* 67 */       super.run();
/*    */     } finally {
/*    */       
/* 70 */       resource.release();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\ChunkWorkerThreadFactory$ChunkWorker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */