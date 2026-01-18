/*     */ package com.hypixel.hytale.server.worldgen.container;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateSupplier;
/*     */ import java.util.Arrays;
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
/*     */ public class DynamicLayer
/*     */   extends LayerContainer.Layer<LayerContainer.DynamicLayerEntry>
/*     */ {
/*     */   protected final IDoubleCoordinateSupplier offset;
/*     */   
/*     */   public DynamicLayer(LayerContainer.DynamicLayerEntry[] entries, ICoordinateCondition mapCondition, int environmentId, IDoubleCoordinateSupplier offset) {
/* 130 */     super(entries, mapCondition, environmentId);
/* 131 */     this.offset = offset;
/*     */   }
/*     */   
/*     */   public int getOffset(int seed, int x, int z) {
/* 135 */     return MathUtil.floor(this.offset.get(seed, x, z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 142 */     return "DynamicLayer{entries=" + Arrays.toString((Object[])this.entries) + ", offset=" + String.valueOf(this.offset) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\LayerContainer$DynamicLayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */