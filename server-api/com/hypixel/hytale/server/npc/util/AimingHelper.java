/*    */ package com.hypixel.hytale.server.npc.util;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AimingHelper
/*    */ {
/*    */   public static final int MIN_GRAVITY_FOR_PARABOLA = 3;
/*    */   
/*    */   public static double ensurePossibleThrowSpeed(double distance, double y, double gravity, double throwSpeed) {
/* 15 */     double x2 = distance * distance;
/*    */ 
/*    */     
/* 18 */     if (x2 < 1.0000000000000002E-10D) {
/* 19 */       double t = y * gravity;
/*    */       
/* 21 */       if (t <= 0.0D) return throwSpeed; 
/* 22 */       double d1 = Math.sqrt(2.0D * t);
/* 23 */       return Math.max(d1, throwSpeed);
/*    */     } 
/*    */     
/* 26 */     double c = (Math.sqrt(y * y + x2) - y) / x2;
/* 27 */     double minThrowSpeed = Math.sqrt(gravity / c);
/* 28 */     return Math.max(minThrowSpeed, throwSpeed);
/*    */   }
/*    */   
/*    */   public static boolean computePitch(double distance, double height, double velocity, double gravity, @Nonnull float[] resultingPitch) {
/* 32 */     if (distance * distance < 1.0000000000000002E-10D) {
/* 33 */       float pitch; double d1 = height * gravity;
/*    */       
/* 35 */       if (d1 <= 0.0D) {
/* 36 */         resultingPitch[0] = (height > 0.0D) ? 1.5707964F : -1.5707964F;
/* 37 */         resultingPitch[1] = -resultingPitch[0];
/* 38 */         return true;
/*    */       } 
/*    */ 
/*    */       
/* 42 */       double peak = 0.5D * velocity * velocity / gravity;
/*    */       
/* 44 */       if (height > 0.0D) {
/* 45 */         if (peak < height - 1.0E-5D) return false; 
/* 46 */         pitch = 1.5707964F;
/*    */       } else {
/* 48 */         if (peak > height + 1.0E-5D) return false; 
/* 49 */         pitch = -1.5707964F;
/*    */       } 
/* 51 */       resultingPitch[1] = pitch; resultingPitch[0] = pitch;
/* 52 */       return true;
/*    */     } 
/*    */     
/* 55 */     if (gravity < 3.0D) {
/* 56 */       if (height == 0.0D && distance == 0.0D) return false; 
/* 57 */       resultingPitch[1] = TrigMathUtil.atan2(height, distance); resultingPitch[0] = TrigMathUtil.atan2(height, distance);
/* 58 */       return true;
/*    */     } 
/*    */     
/* 61 */     if (resultingPitch.length != 2) throw new IllegalArgumentException("computePitch requires a result array of size 2 for storing the resulting pitch"); 
/* 62 */     double c = gravity / velocity * velocity;
/* 63 */     double cx = c * distance - 1.0E-5D;
/* 64 */     double cy = c * height - 1.0E-5D;
/* 65 */     double k = 1.0D - cx * cx - 2.0D * cy;
/*    */     
/* 67 */     if (k < 0.0D) {
/* 68 */       return false;
/*    */     }
/*    */     
/* 71 */     k = Math.sqrt(k);
/* 72 */     resultingPitch[0] = TrigMathUtil.atan((1.0D - k) / cx);
/* 73 */     resultingPitch[1] = TrigMathUtil.atan((1.0D + k) / cx);
/* 74 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\AimingHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */