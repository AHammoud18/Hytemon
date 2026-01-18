/*    */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class AbstractDistortedShape
/*    */   implements DistortedShape {
/*  9 */   private static final double PITCH_MIN = Math.toRadians(5.0D);
/* 10 */   private static final double PITCH_MAX = Math.toRadians(175.0D); private final int lowBoundX;
/*    */   private final int lowBoundY;
/*    */   private final int lowBoundZ;
/*    */   
/*    */   public AbstractDistortedShape(@Nonnull Vector3d o, double radiusX, double radiusY, double radiusZ) {
/* 15 */     this.lowBoundX = MathUtil.floor(o.x - radiusX);
/* 16 */     this.lowBoundY = MathUtil.floor(o.y - radiusY);
/* 17 */     this.lowBoundZ = MathUtil.floor(o.z - radiusZ);
/* 18 */     this.highBoundX = MathUtil.ceil(o.x + radiusX);
/* 19 */     this.highBoundY = MathUtil.ceil(o.y + radiusY);
/* 20 */     this.highBoundZ = MathUtil.ceil(o.z + radiusZ);
/*    */   }
/*    */   private final int highBoundX; private final int highBoundY; private final int highBoundZ;
/*    */   public AbstractDistortedShape(@Nonnull Vector3d o, @Nonnull Vector3d v, double width, double height) {
/* 24 */     this.lowBoundX = MathUtil.floor(Math.min(o.x, o.x + v.x) - width);
/* 25 */     this.lowBoundY = MathUtil.floor(Math.min(o.y, o.y + v.y) - height);
/* 26 */     this.lowBoundZ = MathUtil.floor(Math.min(o.z, o.z + v.z) - width);
/* 27 */     this.highBoundX = MathUtil.ceil(Math.max(o.x, o.x + v.x) + width);
/* 28 */     this.highBoundY = MathUtil.ceil(Math.max(o.y, o.y + v.y) + height);
/* 29 */     this.highBoundZ = MathUtil.ceil(Math.max(o.z, o.z + v.z) + width);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLowBoundX() {
/* 34 */     return this.lowBoundX;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLowBoundZ() {
/* 39 */     return this.lowBoundZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHighBoundX() {
/* 44 */     return this.highBoundX;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHighBoundZ() {
/* 49 */     return this.highBoundZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLowBoundY() {
/* 54 */     return this.lowBoundY;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHighBoundY() {
/* 59 */     return this.highBoundY;
/*    */   }
/*    */   
/*    */   public static double clampPitch(double pitch) {
/* 63 */     return MathUtil.clamp(pitch, PITCH_MIN, PITCH_MAX);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\AbstractDistortedShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */