/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.WeightedMap;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.seed.SeedBox;
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class WeightedMaterialProvider<V>
/*    */   extends MaterialProvider<V>
/*    */ {
/*    */   @Nonnull
/*    */   private final WeightedMap<MaterialProvider<V>> weightedMap;
/*    */   @Nonnull
/*    */   private final SeedGenerator seedGenerator;
/*    */   private final double noneProbability;
/*    */   
/*    */   public WeightedMaterialProvider(@Nonnull WeightedMap<MaterialProvider<V>> weightedMap, @Nonnull SeedBox seedBox, double noneProbability) {
/* 21 */     this.weightedMap = weightedMap;
/* 22 */     this.seedGenerator = new SeedGenerator(((Integer)seedBox.createSupplier().get()).intValue());
/* 23 */     this.noneProbability = noneProbability;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getVoxelTypeAt(@Nonnull MaterialProvider.Context context) {
/* 29 */     long seed = this.seedGenerator.seedAt(context.position.x, context.position.y, context.position.z);
/* 30 */     FastRandom random = new FastRandom(seed);
/* 31 */     if (this.weightedMap.size() == 0 || random.nextDouble() < this.noneProbability) {
/* 32 */       return null;
/*    */     }
/* 34 */     MaterialProvider<V> pick = (MaterialProvider<V>)this.weightedMap.pick((Random)random);
/* 35 */     return pick.getVoxelTypeAt(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\WeightedMaterialProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */