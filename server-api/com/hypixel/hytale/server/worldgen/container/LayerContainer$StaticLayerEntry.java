/*     */ package com.hypixel.hytale.server.worldgen.container;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateSupplier;
/*     */ import com.hypixel.hytale.server.worldgen.util.NoiseBlockArray;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StaticLayerEntry
/*     */   extends LayerContainer.LayerEntry
/*     */ {
/*     */   protected final IDoubleCoordinateSupplier min;
/*     */   protected final IDoubleCoordinateSupplier max;
/*     */   
/*     */   public StaticLayerEntry(NoiseBlockArray blockArray, ICoordinateCondition mapCondition, IDoubleCoordinateSupplier min, IDoubleCoordinateSupplier max) {
/* 182 */     super(blockArray, mapCondition);
/* 183 */     this.min = min;
/* 184 */     this.max = max;
/*     */   }
/*     */   
/*     */   public int getMinInt(int seed, int x, int z) {
/* 188 */     return MathUtil.floor(getMinValue(seed, x, z));
/*     */   }
/*     */   
/*     */   public double getMinValue(int seed, int x, int z) {
/* 192 */     return this.min.get(seed, x, z);
/*     */   }
/*     */   
/*     */   public int getMaxInt(int seed, int x, int z) {
/* 196 */     return MathUtil.floor(getMaxValue(seed, x, z));
/*     */   }
/*     */   
/*     */   public double getMaxValue(int seed, int x, int z) {
/* 200 */     return this.max.get(seed, x, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 206 */     return "StaticLayerEntry{blockArray=" + String.valueOf(this.blockArray) + ", mapCondition=" + String.valueOf(this.mapCondition) + ", min=" + String.valueOf(this.min) + ", max=" + String.valueOf(this.max) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\LayerContainer$StaticLayerEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */