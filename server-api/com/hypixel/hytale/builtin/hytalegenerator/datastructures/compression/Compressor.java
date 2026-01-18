/*    */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.compression;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Compressor {
/*  6 */   private final int MIN_RUN = 7;
/*    */   @Nonnull
/*    */   public <T> CompressedArray<T> compressOnReference(@Nonnull T[] in) {
/*  9 */     int currentRun = 0;
/* 10 */     int resultIndex = 0;
/* 11 */     Object runObj = null;
/* 12 */     Object[] result = new Object[in.length];
/* 13 */     for (int i = 0; i < result.length; i++) {
/* 14 */       if (in[i] != runObj && currentRun >= 7) {
/*    */         
/* 16 */         result[resultIndex] = new Run(runObj, currentRun);
/* 17 */         currentRun = 0;
/* 18 */         resultIndex++;
/* 19 */         runObj = in[i];
/*    */       }
/* 21 */       else if (in[i] != runObj && currentRun < 7) {
/*    */         
/* 23 */         while (currentRun > 0) {
/* 24 */           result[resultIndex] = runObj;
/* 25 */           resultIndex++;
/* 26 */           currentRun--;
/*    */         } 
/* 28 */         currentRun = 0;
/* 29 */         runObj = in[i];
/*    */       }
/*    */       else {
/*    */         
/* 33 */         currentRun++;
/*    */       } 
/*    */     } 
/*    */     
/* 37 */     if (currentRun >= 7) { result[resultIndex] = new Run(runObj, currentRun); }
/* 38 */     else { while (currentRun > 0) {
/*    */         
/* 40 */         result[resultIndex] = runObj;
/* 41 */         resultIndex++;
/* 42 */         currentRun--;
/*    */       }  }
/* 44 */      Object[] trimmedResult = new Object[resultIndex];
/* 45 */     System.arraycopy(result, 0, trimmedResult, 0, trimmedResult.length);
/* 46 */     return new CompressedArray<>(trimmedResult, in.length);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public <T> T[] decompress(@Nonnull CompressedArray<T> compressedArray) {
/* 51 */     int caIndex = 0;
/* 52 */     int runIndex = 0;
/* 53 */     int outIndex = 0;
/*    */     
/* 55 */     Object[] ca = compressedArray.data;
/* 56 */     Object[] out = new Object[compressedArray.initialLength];
/* 57 */     for (; caIndex < ca.length; caIndex++) {
/* 58 */       if (ca[caIndex] instanceof Run) {
/*    */         
/* 60 */         Run run = (Run)ca[caIndex];
/* 61 */         for (runIndex = 0; runIndex < run.length; runIndex++) {
/* 62 */           out[outIndex++] = run.obj;
/*    */         }
/*    */       } else {
/*    */         
/* 66 */         out[outIndex++] = ca[caIndex];
/*    */       } 
/*    */     } 
/* 69 */     return (T[])out;
/*    */   }
/*    */   
/*    */   public static class Run { Object obj;
/*    */     int length;
/*    */     
/*    */     private Run(Object obj, int length) {
/* 76 */       this.obj = obj;
/* 77 */       this.length = length;
/*    */     } }
/*    */   
/*    */   public static class CompressedArray<T> {
/*    */     private final Object[] data;
/*    */     private final int initialLength;
/*    */     
/*    */     private CompressedArray(Object[] data, int initialLength) {
/* 85 */       this.data = data;
/* 86 */       this.initialLength = initialLength;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\compression\Compressor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */