/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.layers;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.WeightedMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class WeightedThicknessLayer<V>
/*    */   extends SpaceAndDepthMaterialProvider.Layer<V>
/*    */ {
/*    */   @Nonnull
/*    */   private final WeightedMap<Integer> thicknessPool;
/*    */   @Nonnull
/*    */   private final SeedGenerator seedGenerator;
/*    */   @Nullable
/*    */   private final MaterialProvider<V> materialProvider;
/*    */   
/*    */   public WeightedThicknessLayer(@Nonnull WeightedMap<Integer> thicknessPool, @Nullable MaterialProvider<V> materialProvider, @Nonnull SeedBox seedBox) {
/* 24 */     this.seedGenerator = new SeedGenerator(((Integer)seedBox.createSupplier().get()).intValue());
/* 25 */     this.materialProvider = materialProvider;
/* 26 */     this.thicknessPool = thicknessPool;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getThicknessAt(int x, int y, int z, int depthIntoFloor, int depthIntoCeiling, int spaceAboveFloor, int spaceBelowCeiling, double distanceTOBiomeEdge) {
/* 37 */     if (this.thicknessPool.size() == 0) return 0; 
/* 38 */     FastRandom random = new FastRandom(this.seedGenerator.seedAt(x, z));
/* 39 */     return ((Integer)this.thicknessPool.pick((Random)random)).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public MaterialProvider<V> getMaterialProvider() {
/* 44 */     return this.materialProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\spaceanddepth\layers\WeightedThicknessLayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */