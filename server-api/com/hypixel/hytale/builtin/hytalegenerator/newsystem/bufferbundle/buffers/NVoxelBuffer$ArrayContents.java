/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.MemInstrument;
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
/*    */ public class ArrayContents<T>
/*    */   implements MemInstrument
/*    */ {
/* 17 */   private final T[] array = (T[])new Object[NVoxelBuffer.SIZE.x * NVoxelBuffer.SIZE.y * NVoxelBuffer.SIZE.z];
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MemInstrument.Report getMemoryUsage() {
/* 23 */     long size_bytes = 16L + 8L * this.array.length;
/*    */     
/* 25 */     return new MemInstrument.Report(size_bytes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\buffers\NVoxelBuffer$ArrayContents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */