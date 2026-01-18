/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.random.CoordinateRotator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class RotateNoiseProperty
/*    */   implements NoiseProperty {
/*    */   protected final NoiseProperty noise;
/*    */   protected final CoordinateRotator rotation;
/*    */   
/*    */   public RotateNoiseProperty(NoiseProperty noise, CoordinateRotator rotation) {
/* 12 */     this.noise = noise;
/* 13 */     this.rotation = rotation;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 18 */     double px = this.rotation.rotateX(x, y);
/* 19 */     double py = this.rotation.rotateY(x, y);
/* 20 */     return this.noise.get(seed, px, py);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 25 */     double px = this.rotation.rotateX(x, y, z);
/* 26 */     double py = this.rotation.rotateY(x, y, z);
/* 27 */     double pz = this.rotation.rotateZ(x, y, z);
/* 28 */     return this.noise.get(seed, px, py, pz);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 34 */     return "RotateNoiseProperty{noise=" + String.valueOf(this.noise) + ", rotation=" + String.valueOf(this.rotation) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\RotateNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */