/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.layers;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RangedThicknessLayer<V>
/*    */   extends SpaceAndDepthMaterialProvider.Layer<V>
/*    */ {
/*    */   private final int min;
/*    */   private final int max;
/*    */   private final int delta;
/*    */   @Nonnull
/*    */   private final SeedGenerator seedGenerator;
/*    */   @Nullable
/*    */   private final MaterialProvider<V> materialProvider;
/*    */   
/*    */   public RangedThicknessLayer(int minInclusive, int maxInclusive, @Nonnull SeedBox seedBox, @Nullable MaterialProvider<V> materialProvider) {
/* 25 */     this.min = minInclusive;
/* 26 */     this.max = maxInclusive;
/* 27 */     this.delta = this.max - this.min;
/*    */     
/* 29 */     if (this.delta < 0) {
/* 30 */       throw new IllegalArgumentException("min greater than max");
/*    */     }
/* 32 */     this.seedGenerator = new SeedGenerator(((Integer)seedBox.createSupplier().get()).intValue());
/* 33 */     this.materialProvider = materialProvider;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getThicknessAt(int x, int y, int z, int depthIntoFloor, int depthIntoCeiling, int spaceAboveFloor, int spaceBelowCeiling, double distanceTOBiomeEdge) {
/* 43 */     if (this.delta <= 0) return this.min;
/*    */     
/* 45 */     FastRandom random = new FastRandom(this.seedGenerator.seedAt(x, z));
/* 46 */     return random.nextInt(this.delta + 1) + this.min;
/*    */   }
/*    */ 
/*    */   
/*    */   public MaterialProvider<V> getMaterialProvider() {
/* 51 */     return this.materialProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\spaceanddepth\layers\RangedThicknessLayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */