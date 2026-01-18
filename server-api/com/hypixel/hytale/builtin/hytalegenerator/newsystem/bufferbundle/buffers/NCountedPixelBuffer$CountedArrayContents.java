/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.ArrayUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.MemInstrument;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class CountedArrayContents<T>
/*    */   implements MemInstrument
/*    */ {
/* 21 */   private final T[] array = (T[])new Object[NCountedPixelBuffer.SIZE_VOXEL_GRID.x * NCountedPixelBuffer.SIZE_VOXEL_GRID.y * NCountedPixelBuffer.SIZE_VOXEL_GRID.z];
/* 22 */   private final List<T> allBiomes = new ArrayList<>(1);
/*    */ 
/*    */   
/*    */   public void copyFrom(@Nonnull CountedArrayContents<T> countedArrayContents) {
/* 26 */     ArrayUtil.copy((Object[])countedArrayContents.array, (Object[])this.array);
/* 27 */     this.allBiomes.clear();
/* 28 */     this.allBiomes.addAll(countedArrayContents.allBiomes);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public MemInstrument.Report getMemoryUsage() {
/* 35 */     long size_bytes = 16L + 8L * this.array.length;
/*    */ 
/*    */ 
/*    */     
/* 39 */     size_bytes += 32L;
/* 40 */     size_bytes += 8L * this.allBiomes.size();
/*    */     
/* 42 */     return new MemInstrument.Report(size_bytes);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\buffers\NCountedPixelBuffer$CountedArrayContents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */