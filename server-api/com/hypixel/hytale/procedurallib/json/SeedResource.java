/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.ResultBuffer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface SeedResource
/*    */ {
/*    */   public static final String INFO_SEED_REPORT = "Seed Value: %s for seed %s / %s";
/*    */   public static final String INFO_SEED_OVERWRITE_REPORT = "Seed Value: %s for seed %s / %s overwritten by %s";
/*    */   
/*    */   @Nonnull
/*    */   default ResultBuffer.Bounds2d localBounds2d() {
/* 18 */     return ResultBuffer.bounds2d;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default ResultBuffer.ResultBuffer2d localBuffer2d() {
/* 23 */     return ResultBuffer.buffer2d;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default ResultBuffer.ResultBuffer3d localBuffer3d() {
/* 28 */     return ResultBuffer.buffer3d;
/*    */   }
/*    */   
/*    */   default boolean shouldReportSeeds() {
/* 32 */     return false;
/*    */   }
/*    */   
/*    */   default void reportSeeds(int seedVal, String original, String seed, @Nullable String overwritten) {
/* 36 */     if (shouldReportSeeds()) {
/* 37 */       if (overwritten == null) {
/* 38 */         writeSeedReport(String.format("Seed Value: %s for seed %s / %s", new Object[] { Integer.valueOf(seedVal), original, seed }));
/*    */       } else {
/* 40 */         writeSeedReport(String.format("Seed Value: %s for seed %s / %s overwritten by %s", new Object[] { Integer.valueOf(seedVal), original, seed, overwritten }));
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   default void writeSeedReport(String seedReport) {
/* 46 */     System.out.println(seedReport);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\SeedResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */