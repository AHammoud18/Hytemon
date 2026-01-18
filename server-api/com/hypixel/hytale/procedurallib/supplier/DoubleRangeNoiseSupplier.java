/*    */ package com.hypixel.hytale.procedurallib.supplier;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoubleRangeNoiseSupplier
/*    */   implements IDoubleCoordinateSupplier
/*    */ {
/*    */   protected final IDoubleRange range;
/*    */   @Nonnull
/*    */   protected final NoiseProperty noiseProperty;
/*    */   @Nonnull
/*    */   protected final IDoubleCoordinateSupplier2d supplier2d;
/*    */   @Nonnull
/*    */   protected final IDoubleCoordinateSupplier3d supplier3d;
/*    */   
/*    */   public DoubleRangeNoiseSupplier(IDoubleRange range, @Nonnull NoiseProperty noiseProperty) {
/* 21 */     this.range = range;
/* 22 */     this.noiseProperty = noiseProperty;
/* 23 */     Objects.requireNonNull(noiseProperty); this.supplier2d = noiseProperty::get;
/* 24 */     Objects.requireNonNull(noiseProperty); this.supplier3d = noiseProperty::get;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 29 */     return this.range.getValue(seed, x, y, this.supplier2d);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 34 */     return this.range.getValue(seed, x, y, z, this.supplier3d);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 40 */     return "DoubleRangeNoiseSupplier{range=" + String.valueOf(this.range) + ", noiseProperty=" + String.valueOf(this.noiseProperty) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\DoubleRangeNoiseSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */