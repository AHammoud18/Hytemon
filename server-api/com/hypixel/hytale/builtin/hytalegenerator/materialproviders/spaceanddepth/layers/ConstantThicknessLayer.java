/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.layers;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.spaceanddepth.SpaceAndDepthMaterialProvider;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ConstantThicknessLayer<V>
/*    */   extends SpaceAndDepthMaterialProvider.Layer<V> {
/*    */   private final int thickness;
/*    */   @Nullable
/*    */   private final MaterialProvider<V> materialProvider;
/*    */   
/*    */   public ConstantThicknessLayer(int thickness, @Nullable MaterialProvider<V> materialProvider) {
/* 14 */     this.thickness = thickness;
/* 15 */     this.materialProvider = materialProvider;
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
/* 26 */     return this.thickness;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MaterialProvider<V> getMaterialProvider() {
/* 32 */     return this.materialProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\spaceanddepth\layers\ConstantThicknessLayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */