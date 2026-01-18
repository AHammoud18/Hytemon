/*    */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import com.hypixel.hytale.procedurallib.random.CoordinateRotator;
/*    */ import com.hypixel.hytale.server.worldgen.cave.CaveType;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class AbstractDistortedBody
/*    */   extends AbstractDistortedShape
/*    */ {
/*    */   @Nonnull
/*    */   protected final Vector3d o;
/*    */   protected final Vector3d v;
/*    */   @Nonnull
/*    */   protected final CoordinateRotator rotation;
/*    */   
/*    */   public AbstractDistortedBody(@Nonnull Vector3d o, Vector3d v, double yaw, double pitch, double radiusX, double radiusY, double radiusZ) {
/* 21 */     this(o, v, new CoordinateRotator(pitch, yaw), radiusX, radiusY, radiusZ);
/*    */   }
/*    */   
/*    */   private AbstractDistortedBody(@Nonnull Vector3d o, Vector3d v, @Nonnull CoordinateRotator rotation, double radiusX, double radiusY, double radiusZ) {
/* 25 */     super(o, maxX(rotation, radiusX, radiusY, radiusZ), maxY(rotation, radiusX, radiusY, radiusZ), maxZ(rotation, radiusX, radiusY, radiusZ));
/* 26 */     this.o = o;
/* 27 */     this.v = v;
/* 28 */     this.rotation = rotation;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract double getHeight(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, CaveType paramCaveType, ShapeDistortion paramShapeDistortion);
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getStart() {
/* 36 */     return new Vector3d(this.o.x, getHighBoundY(), this.o.z);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getEnd() {
/* 42 */     return new Vector3d(this.o.x, getLowBoundY(), this.o.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getHeightAtProjection(int caveSeed, double x, double z, double t, double centerY, CaveType caveType, ShapeDistortion distortion) {
/* 47 */     double dx = x - this.o.x;
/* 48 */     double dz = z - this.o.z;
/* 49 */     x = this.o.x + this.rotation.rotateX(dx, 0.0D, dz);
/* 50 */     z = this.o.z + this.rotation.rotateZ(dx, 0.0D, dz);
/* 51 */     return getHeight(caveSeed, x, z, t, centerY, caveType, distortion);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getFloor(double x, double z, double centerY, double height) {
/* 56 */     double dx = x - this.o.x;
/* 57 */     double dz = z - this.o.z;
/* 58 */     double dy = this.rotation.rotateY(dx, -height, dz);
/* 59 */     return centerY + dy;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getCeiling(double x, double z, double centerY, double height) {
/* 64 */     double dx = x - this.o.x;
/* 65 */     double dz = z - this.o.z;
/* 66 */     double dy = this.rotation.rotateY(dx, height, dz);
/* 67 */     return centerY + dy;
/*    */   }
/*    */   
/*    */   private static double maxX(@Nonnull CoordinateRotator rotation, double radiusX, double radiusY, double radiusZ) {
/* 71 */     double x1 = Math.abs(rotation.rotateX(radiusX, radiusY, radiusZ));
/* 72 */     double x2 = Math.abs(rotation.rotateX(-radiusX, radiusY, radiusZ));
/* 73 */     return MathUtil.maxValue(x1, x2);
/*    */   }
/*    */   
/*    */   private static double maxY(@Nonnull CoordinateRotator rotation, double radiusX, double radiusY, double radiusZ) {
/* 77 */     double y1 = Math.abs(rotation.rotateY(radiusX, radiusY, radiusZ));
/* 78 */     double y2 = Math.abs(rotation.rotateY(radiusX, -radiusY, radiusZ));
/* 79 */     return Math.max(y1, y2);
/*    */   }
/*    */   
/*    */   private static double maxZ(@Nonnull CoordinateRotator rotation, double radiusX, double radiusY, double radiusZ) {
/* 83 */     double z1 = Math.abs(rotation.rotateZ(radiusX, radiusY, radiusZ));
/* 84 */     double z2 = Math.abs(rotation.rotateZ(radiusX, radiusY, -radiusZ));
/* 85 */     return MathUtil.maxValue(z1, z2);
/*    */   }
/*    */   
/*    */   protected static abstract class Factory
/*    */     implements DistortedShape.Factory
/*    */   {
/*    */     public DistortedShape create(Vector3d origin, @Nonnull Vector3d direction, double length, double startWidth, double startHeight, double midWidth, double midHeight, double endWidth, double endHeight, GeneralNoise.InterpolationFunction interpolation) {
/* 92 */       double scale = 1.0D / direction.length();
/* 93 */       double nx = direction.x * scale;
/* 94 */       double ny = direction.y * scale;
/* 95 */       double nz = direction.z * scale;
/* 96 */       double yaw = TrigMathUtil.atan2(nx, nz);
/* 97 */       double pitch = TrigMathUtil.asin(-ny);
/* 98 */       return createShape(origin, direction, yaw, pitch, startWidth, startHeight, length, interpolation);
/*    */     }
/*    */     
/*    */     protected abstract DistortedShape createShape(Vector3d param1Vector3d1, Vector3d param1Vector3d2, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, GeneralNoise.InterpolationFunction param1InterpolationFunction);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\AbstractDistortedBody.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */