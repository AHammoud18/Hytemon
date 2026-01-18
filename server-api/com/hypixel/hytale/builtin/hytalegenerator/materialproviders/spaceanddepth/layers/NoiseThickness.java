/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.layers;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class NoiseThickness<V>
/*    */   extends SpaceAndDepthMaterialProvider.Layer<V>
/*    */ {
/*    */   @Nonnull
/*    */   private final Density density;
/*    */   @Nullable
/*    */   private final MaterialProvider<V> materialProvider;
/*    */   
/*    */   public NoiseThickness(@Nonnull Density density, @Nullable MaterialProvider<V> materialProvider) {
/* 19 */     this.density = density;
/* 20 */     this.materialProvider = materialProvider;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getThicknessAt(int x, int y, int z, int depthIntoFloor, int depthIntoCeiling, int spaceAboveFloor, int spaceBelowCeiling, double distanceToBiomeEdge) {
/* 30 */     Density.Context childContext = new Density.Context();
/* 31 */     childContext.position = new Vector3d(x, y, z);
/* 32 */     childContext.distanceToBiomeEdge = distanceToBiomeEdge;
/*    */     
/* 34 */     return (int)this.density.process(childContext);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MaterialProvider<V> getMaterialProvider() {
/* 40 */     return this.materialProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\spaceanddepth\layers\NoiseThickness.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */