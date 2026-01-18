/*     */ package com.hypixel.hytale.server.core.modules.physics.util;
/*     */ 
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PhysicsMath
/*     */ {
/*     */   public static final double DENSITY_AIR = 1.2D;
/*     */   public static final double DENSITY_WATER = 998.0D;
/*     */   public static final double AIR_DENSITY = 0.001225D;
/*     */   public static final float HEADING_DIRECTION = 1.0F;
/*     */   
/*     */   public static double getAcceleration(double speed, double terminalSpeed) {
/*  22 */     double ratio = Math.abs(speed / terminalSpeed);
/*  23 */     return 32.0D * (1.0D - ratio * ratio * ratio);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getTerminalVelocity(double mass, double density, double areaMillimetersSquared, double dragCoefficient) {
/*  28 */     double massGrams = mass * 1000.0D;
/*  29 */     double areaMetersSquared = areaMillimetersSquared * 1000000.0D;
/*  30 */     return Math.sqrt(64.0D * massGrams / density * areaMetersSquared * dragCoefficient);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getRelativeDensity(Vector3d position, Box boundingBox) {
/*  36 */     return 0.001225D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double computeDragCoefficient(double terminalSpeed, double area, double mass, double gravity) {
/*  50 */     return mass * gravity / area * terminalSpeed * terminalSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double computeTerminalSpeed(double dragCoefficient, double area, double mass, double gravity) {
/*  63 */     return Math.sqrt(mass * gravity / area * dragCoefficient);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double computeProjectedArea(double x, double y, double z, @Nonnull Box box) {
/*  76 */     double area = 0.0D;
/*  77 */     if (x != 0.0D) {
/*  78 */       area += Math.abs(x) * box.depth() * box.height();
/*     */     }
/*  80 */     if (y != 0.0D) {
/*  81 */       area += Math.abs(y) * box.depth() * box.width();
/*     */     }
/*  83 */     if (z != 0.0D) {
/*  84 */       area += Math.abs(z) * box.width() * box.height();
/*     */     }
/*  86 */     return area;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double computeProjectedArea(@Nonnull Vector3d direction, @Nonnull Box box) {
/*  97 */     return computeProjectedArea(direction.x, direction.y, direction.z, box);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double volumeOfIntersection(@Nonnull Box a, @Nonnull Vector3d posA, @Nonnull Box b, @Nonnull Vector3d posB) {
/* 110 */     return volumeOfIntersection(a, posA, b, posB.x, posB.y, posB.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double volumeOfIntersection(@Nonnull Box a, @Nonnull Vector3d posA, @Nonnull Box b, double posBX, double posBY, double posBZ) {
/* 125 */     posBX -= posA.x;
/* 126 */     posBY -= posA.y;
/* 127 */     posBZ -= posA.z;
/* 128 */     return lengthOfIntersection(a.min.x, a.max.x, posBX + b.min.x, posBX + b.max.x) * 
/* 129 */       lengthOfIntersection(a.min.y, a.max.y, posBY + b.min.y, posBY + b.max.y) * 
/* 130 */       lengthOfIntersection(a.min.z, a.max.z, posBZ + b.min.z, posBZ + b.max.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double lengthOfIntersection(double aMin, double aMax, double bMin, double bMax) {
/* 143 */     double left = Math.max(aMin, bMin);
/* 144 */     double right = Math.min(aMax, bMax);
/* 145 */     return Math.max(0.0D, right - left);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float headingFromDirection(double x, double z) {
/* 156 */     return 1.0F * TrigMathUtil.atan2(-x, -z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float normalizeAngle(float rad) {
/* 166 */     rad %= 6.2831855F;
/* 167 */     if (rad < 0.0F) {
/* 168 */       rad += 6.2831855F;
/*     */     }
/* 170 */     return rad;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float normalizeTurnAngle(float rad) {
/* 180 */     rad = normalizeAngle(rad);
/* 181 */     if (rad >= 3.1415927F) {
/* 182 */       rad -= 6.2831855F;
/*     */     }
/* 184 */     return rad;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float pitchFromDirection(double x, double y, double z) {
/* 196 */     return TrigMathUtil.atan2(y, Math.sqrt(x * x + z * z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3d vectorFromAngles(float heading, float pitch, @Nonnull Vector3d outDirection) {
/* 209 */     float sx = pitchX(pitch);
/* 210 */     outDirection.y = pitchY(pitch);
/* 211 */     outDirection.x = (headingX(heading) * sx);
/* 212 */     outDirection.z = (headingZ(heading) * sx);
/* 213 */     return outDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float pitchX(float pitch) {
/* 223 */     return TrigMathUtil.cos(pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float pitchY(float pitch) {
/* 233 */     return TrigMathUtil.sin(pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float headingX(float heading) {
/* 243 */     return -TrigMathUtil.sin(1.0F * heading);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float headingZ(float heading) {
/* 253 */     return -TrigMathUtil.cos(1.0F * heading);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physic\\util\PhysicsMath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */