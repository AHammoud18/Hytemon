/*    */ package com.hypixel.hytale.procedurallib.random;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class RotatedCoordinateRandomizer implements ICoordinateRandomizer {
/*    */   protected final ICoordinateRandomizer randomizer;
/*    */   protected final CoordinateRotator rotation;
/*    */   
/*    */   public RotatedCoordinateRandomizer(ICoordinateRandomizer randomizer, CoordinateRotator rotation) {
/* 10 */     this.randomizer = randomizer;
/* 11 */     this.rotation = rotation;
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleX(int seed, double x, double y) {
/* 16 */     double px = this.rotation.rotateX(x, y);
/* 17 */     double py = this.rotation.rotateY(x, y);
/* 18 */     return this.randomizer.randomDoubleX(seed, px, py);
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleY(int seed, double x, double y) {
/* 23 */     double px = this.rotation.rotateX(x, y);
/* 24 */     double py = this.rotation.rotateY(x, y);
/* 25 */     return this.randomizer.randomDoubleY(seed, px, py);
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleX(int seed, double x, double y, double z) {
/* 30 */     double px = this.rotation.rotateX(x, y, z);
/* 31 */     double py = this.rotation.rotateY(x, y, z);
/* 32 */     double pz = this.rotation.rotateZ(x, y, z);
/* 33 */     return this.randomizer.randomDoubleX(seed, px, py, pz);
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleY(int seed, double x, double y, double z) {
/* 38 */     double px = this.rotation.rotateX(x, y, z);
/* 39 */     double py = this.rotation.rotateY(x, y, z);
/* 40 */     double pz = this.rotation.rotateZ(x, y, z);
/* 41 */     return this.randomizer.randomDoubleY(seed, px, py, pz);
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleZ(int seed, double x, double y, double z) {
/* 46 */     double px = this.rotation.rotateX(x, y, z);
/* 47 */     double py = this.rotation.rotateY(x, y, z);
/* 48 */     double pz = this.rotation.rotateZ(x, y, z);
/* 49 */     return this.randomizer.randomDoubleZ(seed, px, py, pz);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 55 */     return "RotatedCoordinateRandomizer{randomizer=" + String.valueOf(this.randomizer) + ", rotation=" + String.valueOf(this.rotation) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\random\RotatedCoordinateRandomizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */