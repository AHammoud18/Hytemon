/*    */ package com.github.luben.zstd;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ZstdDictTrainer {
/*    */   private final int allocatedSize;
/*    */   private final ByteBuffer trainingSamples;
/*    */   private final List<Integer> sampleSizes;
/*    */   private final int dictSize;
/*    */   private long filledSize;
/*    */   private int level;
/*    */   
/*    */   public ZstdDictTrainer(int paramInt1, int paramInt2) {
/* 16 */     this(paramInt1, paramInt2, Zstd.defaultCompressionLevel());
/*    */   }
/*    */   
/*    */   public ZstdDictTrainer(int paramInt1, int paramInt2, int paramInt3) {
/* 20 */     this.trainingSamples = ByteBuffer.allocateDirect(paramInt1);
/* 21 */     this.sampleSizes = new ArrayList<>();
/* 22 */     this.allocatedSize = paramInt1;
/* 23 */     this.dictSize = paramInt2;
/* 24 */     this.level = paramInt3;
/*    */   }
/*    */   
/*    */   public synchronized boolean addSample(byte[] paramArrayOfbyte) {
/* 28 */     if (this.filledSize + paramArrayOfbyte.length > this.allocatedSize) {
/* 29 */       return false;
/*    */     }
/* 31 */     this.trainingSamples.put(paramArrayOfbyte);
/* 32 */     this.sampleSizes.add(Integer.valueOf(paramArrayOfbyte.length));
/* 33 */     this.filledSize += paramArrayOfbyte.length;
/* 34 */     return true;
/*    */   }
/*    */   
/*    */   public ByteBuffer trainSamplesDirect() throws ZstdException {
/* 38 */     return trainSamplesDirect(false);
/*    */   }
/*    */   
/*    */   public synchronized ByteBuffer trainSamplesDirect(boolean paramBoolean) throws ZstdException {
/* 42 */     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(this.dictSize);
/* 43 */     long l = Zstd.trainFromBufferDirect(this.trainingSamples, copyToIntArray(this.sampleSizes), byteBuffer, paramBoolean, this.level);
/* 44 */     if (Zstd.isError(l)) {
/* 45 */       byteBuffer.limit(0);
/* 46 */       throw new ZstdException(l);
/*    */     } 
/* 48 */     byteBuffer.limit(Long.valueOf(l).intValue());
/* 49 */     return byteBuffer;
/*    */   }
/*    */   
/*    */   public byte[] trainSamples() throws ZstdException {
/* 53 */     return trainSamples(false);
/*    */   }
/*    */   
/*    */   public byte[] trainSamples(boolean paramBoolean) throws ZstdException {
/* 57 */     ByteBuffer byteBuffer = trainSamplesDirect(paramBoolean);
/* 58 */     byte[] arrayOfByte = new byte[byteBuffer.remaining()];
/* 59 */     byteBuffer.get(arrayOfByte);
/* 60 */     return arrayOfByte;
/*    */   }
/*    */   
/*    */   private int[] copyToIntArray(List<Integer> paramList) {
/* 64 */     int[] arrayOfInt = new int[paramList.size()];
/* 65 */     byte b = 0;
/* 66 */     for (Integer integer : paramList) {
/* 67 */       arrayOfInt[b] = integer.intValue();
/* 68 */       b++;
/*    */     } 
/* 70 */     return arrayOfInt;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\ZstdDictTrainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */