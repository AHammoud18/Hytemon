/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class TerrainDensityMaterialProvider<V> extends MaterialProvider<V> {
/*    */   @Nonnull
/*    */   private final FieldDelimiter<V>[] fieldDelimiters;
/*    */   
/*    */   public TerrainDensityMaterialProvider(@Nonnull List<FieldDelimiter<V>> delimiters) {
/* 12 */     this.fieldDelimiters = (FieldDelimiter<V>[])new FieldDelimiter[delimiters.size()];
/*    */     
/* 14 */     for (FieldDelimiter<V> field : delimiters) {
/* 15 */       if (field == null) throw new IllegalArgumentException("delimiters contain null value"); 
/*    */     } 
/* 17 */     for (int i = 0; i < delimiters.size(); i++) {
/* 18 */       this.fieldDelimiters[i] = delimiters.get(i);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getVoxelTypeAt(@Nonnull MaterialProvider.Context context) {
/* 25 */     for (FieldDelimiter<V> delimiter : this.fieldDelimiters) {
/* 26 */       if (delimiter.isInside(context.density)) {
/* 27 */         return delimiter.materialProvider.getVoxelTypeAt(context);
/*    */       }
/*    */     } 
/* 30 */     return null;
/*    */   }
/*    */   
/*    */   public static class FieldDelimiter<V> {
/*    */     double top;
/*    */     double bottom;
/*    */     MaterialProvider<V> materialProvider;
/*    */     
/*    */     public FieldDelimiter(@Nonnull MaterialProvider<V> materialProvider, double bottom, double top) {
/* 39 */       this.bottom = bottom;
/* 40 */       this.top = top;
/* 41 */       this.materialProvider = materialProvider;
/*    */     }
/*    */     
/*    */     boolean isInside(double fieldValue) {
/* 45 */       return (fieldValue < this.top && fieldValue >= this.bottom);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\TerrainDensityMaterialProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */