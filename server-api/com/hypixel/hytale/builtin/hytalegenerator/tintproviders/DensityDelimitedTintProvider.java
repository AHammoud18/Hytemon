/*    */ package com.hypixel.hytale.builtin.hytalegenerator.tintproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.DelimiterDouble;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.delimiters.RangeDouble;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DensityDelimitedTintProvider
/*    */   extends TintProvider
/*    */ {
/*    */   @Nonnull
/* 18 */   private final List<DelimiterDouble<TintProvider>> delimiters = new ArrayList<>(); public DensityDelimitedTintProvider(@Nonnull List<DelimiterDouble<TintProvider>> delimiters, @Nonnull Density density) {
/* 19 */     for (DelimiterDouble<TintProvider> delimiter : delimiters) {
/* 20 */       RangeDouble range = delimiter.getRange();
/* 21 */       if (range.min() >= range.max())
/*    */         continue; 
/* 23 */       this.delimiters.add(delimiter);
/*    */     } 
/*    */     
/* 26 */     this.density = density;
/*    */   }
/*    */   @Nonnull
/*    */   private final Density density;
/*    */   public TintProvider.Result getValue(@Nonnull TintProvider.Context context) {
/* 31 */     double densityValue = this.density.process(new Density.Context(context));
/*    */     
/* 33 */     for (DelimiterDouble<TintProvider> delimiter : this.delimiters) {
/* 34 */       if (delimiter.getRange().contains(densityValue)) {
/* 35 */         return ((TintProvider)delimiter.getValue()).getValue(context);
/*    */       }
/*    */     } 
/* 38 */     return TintProvider.Result.WITHOUT_VALUE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\tintproviders\DensityDelimitedTintProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */