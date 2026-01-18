/*    */ package com.hypixel.hytale.procedurallib.random;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CoordinateOriginRotator
/*    */   extends CoordinateRotator {
/*    */   private final double originX;
/*    */   private final double originY;
/*    */   private final double originZ;
/*    */   
/*    */   public CoordinateOriginRotator(double pitch, double yaw, double originX, double originY, double originZ) {
/* 12 */     super(pitch, yaw);
/* 13 */     this.originX = originX;
/* 14 */     this.originY = originY;
/* 15 */     this.originZ = originZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleX(int seed, double x, double y) {
/* 20 */     x -= this.originX;
/* 21 */     y -= this.originY;
/* 22 */     return this.originX + rotateX(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleY(int seed, double x, double y) {
/* 27 */     x -= this.originX;
/* 28 */     y -= this.originY;
/* 29 */     return this.originY + rotateY(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleX(int seed, double x, double y, double z) {
/* 34 */     x -= this.originX;
/* 35 */     y -= this.originY;
/* 36 */     z -= this.originZ;
/* 37 */     return this.originX + rotateX(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleY(int seed, double x, double y, double z) {
/* 42 */     x -= this.originX;
/* 43 */     y -= this.originY;
/* 44 */     z -= this.originZ;
/* 45 */     return this.originY + rotateY(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public double randomDoubleZ(int seed, double x, double y, double z) {
/* 50 */     x -= this.originX;
/* 51 */     y -= this.originY;
/* 52 */     z -= this.originZ;
/* 53 */     return this.originZ + rotateZ(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "CoordinateOriginRotator{pitch=" + this.pitch + ", yaw=" + this.yaw + ", originX=" + this.originX + ", originY=" + this.originY + ", originZ=" + this.originZ + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\random\CoordinateOriginRotator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */