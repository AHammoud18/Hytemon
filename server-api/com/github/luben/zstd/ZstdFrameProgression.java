/*    */ package com.github.luben.zstd;
/*    */ 
/*    */ 
/*    */ public class ZstdFrameProgression
/*    */ {
/*    */   private long ingested;
/*    */   private long consumed;
/*    */   private long produced;
/*    */   private long flushed;
/*    */   private int currentJobID;
/*    */   private int nbActiveWorkers;
/*    */   
/*    */   public ZstdFrameProgression(long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt1, int paramInt2) {
/* 14 */     this.ingested = paramLong1;
/* 15 */     this.consumed = paramLong2;
/* 16 */     this.produced = paramLong3;
/* 17 */     this.flushed = paramLong4;
/* 18 */     this.currentJobID = paramInt1;
/* 19 */     this.nbActiveWorkers = paramInt2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getIngested() {
/* 26 */     return this.ingested;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getConsumed() {
/* 34 */     return this.consumed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getProduced() {
/* 41 */     return this.produced;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getFlushed() {
/* 48 */     return this.flushed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCurrentJobID() {
/* 55 */     return this.currentJobID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNbActiveWorkers() {
/* 62 */     return this.nbActiveWorkers;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdFrameProgression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */