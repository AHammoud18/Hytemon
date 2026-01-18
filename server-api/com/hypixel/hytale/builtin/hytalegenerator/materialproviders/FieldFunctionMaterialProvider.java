/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class FieldFunctionMaterialProvider<V>
/*    */   extends MaterialProvider<V>
/*    */ {
/*    */   @Nonnull
/*    */   private final Density density;
/*    */   @Nonnull
/*    */   private final FieldDelimiter<V>[] fieldDelimiters;
/*    */   
/*    */   public FieldFunctionMaterialProvider(@Nonnull Density density, @Nonnull List<FieldDelimiter<V>> delimiters) {
/* 17 */     this.density = density;
/* 18 */     this.fieldDelimiters = (FieldDelimiter<V>[])new FieldDelimiter[delimiters.size()];
/*    */     
/* 20 */     for (FieldDelimiter<V> field : delimiters) {
/* 21 */       if (field == null) throw new IllegalArgumentException("delimiters contain null value"); 
/*    */     } 
/* 23 */     for (int i = 0; i < delimiters.size(); i++) {
/* 24 */       this.fieldDelimiters[i] = delimiters.get(i);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getVoxelTypeAt(@Nonnull MaterialProvider.Context context) {
/* 32 */     Density.Context childContext = new Density.Context(context);
/* 33 */     double densityValue = this.density.process(childContext);
/*    */     
/* 35 */     for (FieldDelimiter<V> delimiter : this.fieldDelimiters) {
/* 36 */       if (delimiter.isInside(densityValue)) {
/* 37 */         return delimiter.materialProvider.getVoxelTypeAt(context);
/*    */       }
/*    */     } 
/* 40 */     return null;
/*    */   }
/*    */   
/*    */   public static class FieldDelimiter<V> {
/*    */     double top;
/*    */     double bottom;
/*    */     MaterialProvider<V> materialProvider;
/*    */     
/*    */     public FieldDelimiter(@Nonnull MaterialProvider<V> materialProvider, double bottom, double top) {
/* 49 */       this.bottom = bottom;
/* 50 */       this.top = top;
/* 51 */       this.materialProvider = materialProvider;
/*    */     }
/*    */     
/*    */     boolean isInside(double fieldValue) {
/* 55 */       return (fieldValue < this.top && fieldValue >= this.bottom);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\FieldFunctionMaterialProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */