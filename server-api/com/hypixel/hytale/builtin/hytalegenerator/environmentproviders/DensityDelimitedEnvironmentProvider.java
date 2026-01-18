/*    */ package com.hypixel.hytale.builtin.hytalegenerator.environmentproviders;
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
/*    */ 
/*    */ public class DensityDelimitedEnvironmentProvider
/*    */   extends EnvironmentProvider
/*    */ {
/*    */   @Nonnull
/* 19 */   private final List<DelimiterDouble<EnvironmentProvider>> delimiters = new ArrayList<>(); public DensityDelimitedEnvironmentProvider(@Nonnull List<DelimiterDouble<EnvironmentProvider>> delimiters, @Nonnull Density density) {
/* 20 */     for (DelimiterDouble<EnvironmentProvider> delimiter : delimiters) {
/* 21 */       RangeDouble range = delimiter.getRange();
/* 22 */       if (range.min() >= range.max())
/*    */         continue; 
/* 24 */       this.delimiters.add(delimiter);
/*    */     } 
/*    */     
/* 27 */     this.density = density;
/*    */   }
/*    */   @Nonnull
/*    */   private final Density density;
/*    */   public int getValue(@Nonnull EnvironmentProvider.Context context) {
/* 32 */     double densityValue = this.density.process(new Density.Context(context));
/*    */     
/* 34 */     for (DelimiterDouble<EnvironmentProvider> delimiter : this.delimiters) {
/* 35 */       if (delimiter.getRange().contains(densityValue)) {
/* 36 */         return ((EnvironmentProvider)delimiter.getValue()).getValue(context);
/*    */       }
/*    */     } 
/* 39 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\environmentproviders\DensityDelimitedEnvironmentProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */