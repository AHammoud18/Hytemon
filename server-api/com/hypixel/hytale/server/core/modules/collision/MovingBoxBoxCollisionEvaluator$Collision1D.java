/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Collision1D
/*     */ {
/*     */   protected static final int COLLISION_OUTSIDE = 0;
/*     */   protected static final int COLLISION_INSIDE = 1;
/*     */   protected static final int COLLISION_TOUCH_MIN = 2;
/*     */   protected static final int COLLISION_TOUCH_MAX = 3;
/*     */   public double p;
/*     */   public double v;
/*     */   public double min;
/*     */   public double max;
/*     */   public double tEnter;
/*     */   public double tLeave;
/*     */   public double normal;
/*     */   public int kind;
/*     */   public boolean touching;
/*     */   
/*     */   boolean isColliding(double min, double max) {
/* 204 */     this.min = min;
/* 205 */     this.max = max;
/*     */     
/* 207 */     this.tEnter = -1.7976931348623157E308D;
/* 208 */     this.tLeave = Double.MAX_VALUE;
/* 209 */     this.normal = 0.0D;
/* 210 */     this.touching = false;
/*     */     
/* 212 */     double dist = min - this.p;
/* 213 */     if (dist >= -1.0E-5D) {
/* 214 */       if (this.v < dist - 1.0E-5D) {
/* 215 */         return false;
/*     */       }
/* 217 */       this.normal = -1.0D;
/* 218 */       computeTouchOrOutside(max, dist, 2);
/* 219 */       return true;
/*     */     } 
/*     */     
/* 222 */     dist = max - this.p;
/* 223 */     if (dist <= 1.0E-5D) {
/* 224 */       if (this.v > dist + 1.0E-5D) {
/* 225 */         return false;
/*     */       }
/* 227 */       this.normal = 1.0D;
/* 228 */       computeTouchOrOutside(min, dist, 3);
/* 229 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 233 */     this.tEnter = 0.0D;
/* 234 */     if (this.v < 0.0D) {
/* 235 */       this.tLeave = clampPos((min - this.p) / this.v);
/* 236 */       this.normal = 1.0D;
/* 237 */     } else if (this.v > 0.0D) {
/* 238 */       this.tLeave = clampPos((max - this.p) / this.v);
/* 239 */       this.normal = -1.0D;
/*     */     } 
/* 241 */     this.kind = 1;
/* 242 */     return true;
/*     */   }
/*     */   
/*     */   private void computeTouchOrOutside(double border, double dist, int touchCode) {
/* 246 */     if (this.v != 0.0D) {
/* 247 */       this.tEnter = MathUtil.clamp(dist / this.v, 0.0D, 1.0D);
/* 248 */       if (this.tEnter != 0.0D && this.tEnter < 1.0E-8D) {
/* 249 */         this.tEnter = 0.0D;
/*     */       }
/* 251 */       this.tLeave = clampPos((border - this.p) / this.v);
/* 252 */       this.kind = 0;
/*     */     } else {
/* 254 */       this.tEnter = 0.0D;
/* 255 */       this.kind = touchCode;
/*     */     } 
/*     */   }
/*     */   
/*     */   private double clampPos(double v) {
/* 260 */     return (v >= 0.0D) ? v : 0.0D;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\MovingBoxBoxCollisionEvaluator$Collision1D.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */