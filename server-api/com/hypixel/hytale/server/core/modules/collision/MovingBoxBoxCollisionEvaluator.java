/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MovingBoxBoxCollisionEvaluator
/*     */   extends BlockContactData
/*     */   implements IBlockCollisionEvaluator
/*     */ {
/*     */   protected boolean touching;
/*     */   protected Box collider;
/*     */   @Nonnull
/*     */   protected final Vector3d pos;
/*     */   @Nonnull
/*     */   protected final Vector3d v;
/*     */   protected boolean checkForOnGround = true;
/*     */   private boolean computeOverlaps;
/*     */   @Nonnull
/*     */   protected final Collision1D cX;
/*     */   @Nonnull
/*     */   protected final Collision1D cY;
/*     */   @Nonnull
/*     */   protected final Collision1D cZ;
/*     */   
/*     */   public MovingBoxBoxCollisionEvaluator() {
/*  30 */     this.pos = new Vector3d();
/*  31 */     this.v = new Vector3d();
/*  32 */     this.cX = new Collision1D();
/*  33 */     this.cY = new Collision1D();
/*  34 */     this.cZ = new Collision1D();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCollisionStart() {
/*  39 */     return this.collisionStart;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCollisionData(@Nonnull BlockCollisionData data, @Nonnull CollisionConfig collisionConfig, int hitboxIndex) {
/*  44 */     data.setStart(this.collisionPoint, this.collisionStart);
/*  45 */     data.setEnd(this.collisionEnd, this.collisionNormal);
/*  46 */     data.setBlockData(collisionConfig);
/*  47 */     data.setDetailBoxIndex(hitboxIndex);
/*  48 */     data.setTouchingOverlapping(this.touching, isOverlapping());
/*     */   }
/*     */   
/*     */   public boolean isCheckForOnGround() {
/*  52 */     return this.checkForOnGround;
/*     */   }
/*     */   
/*     */   public void setCheckForOnGround(boolean checkForOnGround) {
/*  56 */     this.checkForOnGround = checkForOnGround;
/*     */   }
/*     */   
/*     */   public boolean isComputeOverlaps() {
/*  60 */     return this.computeOverlaps;
/*     */   }
/*     */   
/*     */   public void setComputeOverlaps(boolean computeOverlaps) {
/*  64 */     this.computeOverlaps = computeOverlaps;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MovingBoxBoxCollisionEvaluator setCollider(Box collider) {
/*  70 */     this.collider = collider;
/*  71 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public MovingBoxBoxCollisionEvaluator setMove(@Nonnull Vector3d pos, @Nonnull Vector3d v) {
/*  76 */     this.pos.assign(pos);
/*  77 */     this.v.assign(v);
/*     */     
/*  79 */     this.cX.v = v.x;
/*  80 */     this.cY.v = v.y;
/*  81 */     this.cZ.v = v.z;
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBoundingBoxColliding(@Nonnull Box blockBoundingBox, double x, double y, double z) {
/*  88 */     this.cX.p = this.pos.x - x;
/*  89 */     this.cY.p = this.pos.y - y;
/*  90 */     this.cZ.p = this.pos.z - z;
/*     */     
/*  92 */     this.onGround = false;
/*  93 */     this.touching = false;
/*  94 */     this.overlapping = false;
/*     */ 
/*     */     
/*  97 */     if (!this.cX.isColliding((blockBoundingBox.getMin()).x - (this.collider.getMax()).x, (blockBoundingBox.getMax()).x - (this.collider.getMin()).x)) {
/*  98 */       return false;
/*     */     }
/*     */     
/* 101 */     if (!this.cY.isColliding((blockBoundingBox.getMin()).y - (this.collider.getMax()).y, (blockBoundingBox.getMax()).y - (this.collider.getMin()).y)) {
/* 102 */       return false;
/*     */     }
/*     */     
/* 105 */     if (!this.cZ.isColliding((blockBoundingBox.getMin()).z - (this.collider.getMax()).z, (blockBoundingBox.getMax()).z - (this.collider.getMin()).z)) {
/* 106 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 110 */     if (this.cX.kind == 1 && this.cY.kind == 1 && this.cZ.kind == 1) {
/* 111 */       this.overlapping = true;
/* 112 */       if (!this.computeOverlaps) return false;
/*     */       
/* 114 */       this.collisionStart = 0.0D;
/* 115 */       this.collisionEnd = Double.MAX_VALUE;
/* 116 */       this.collisionNormal.assign(0.0D, 0.0D, 0.0D);
/* 117 */       if (this.cX.tLeave < this.collisionEnd) {
/* 118 */         this.collisionEnd = this.cX.tLeave;
/* 119 */         this.collisionNormal.assign(this.cX.normal, 0.0D, 0.0D);
/*     */       } 
/* 121 */       if (this.cY.tLeave < this.collisionEnd) {
/* 122 */         this.collisionEnd = this.cY.tLeave;
/* 123 */         this.collisionNormal.assign(0.0D, this.cY.normal, 0.0D);
/*     */       } 
/* 125 */       if (this.cZ.tLeave < this.collisionEnd) {
/* 126 */         this.collisionEnd = this.cZ.tLeave;
/* 127 */         this.collisionNormal.assign(0.0D, 0.0D, this.cZ.normal);
/*     */       } 
/* 129 */       return true;
/*     */     } 
/*     */     
/* 132 */     this.collisionStart = -1.7976931348623157E308D;
/* 133 */     this.collisionEnd = Double.MAX_VALUE;
/*     */     
/* 135 */     if (this.cX.kind == 0) {
/* 136 */       this.collisionNormal.assign(this.cX.normal, 0.0D, 0.0D);
/* 137 */       this.collisionStart = this.cX.tEnter;
/*     */     } 
/* 139 */     if (this.cY.kind == 0 && this.cY.tEnter > this.collisionStart) {
/* 140 */       this.collisionNormal.assign(0.0D, this.cY.normal, 0.0D);
/* 141 */       this.collisionStart = this.cY.tEnter;
/*     */     } 
/* 143 */     if (this.cZ.kind == 0 && this.cZ.tEnter > this.collisionStart) {
/* 144 */       this.collisionNormal.assign(0.0D, 0.0D, this.cZ.normal);
/* 145 */       this.collisionStart = this.cZ.tEnter;
/*     */     } 
/*     */     
/* 148 */     if (this.collisionStart > -1.7976931348623157E308D) {
/* 149 */       this.collisionEnd = MathUtil.minValue(this.cX.tLeave, this.cY.tLeave, this.cZ.tLeave);
/*     */       
/* 151 */       if (this.collisionStart > this.collisionEnd) {
/* 152 */         return false;
/*     */       }
/*     */       
/* 155 */       this.collisionPoint.assign(this.pos);
/* 156 */       this.collisionPoint.addScaled(this.v, this.collisionStart);
/*     */       
/* 158 */       if (this.checkForOnGround && this.cY.kind == 3) {
/* 159 */         this.collisionNormal.assign(0.0D, this.cY.normal, 0.0D);
/* 160 */         this.onGround = true;
/* 161 */         this.touching = true;
/* 162 */         return false;
/*     */       } 
/*     */       
/* 165 */       this.touching = (this.cX.kind >= 2 || this.cY.kind >= 2 || this.cZ.kind >= 2);
/* 166 */       return !this.touching;
/*     */     } 
/*     */     
/* 169 */     if (this.checkForOnGround && this.cY.kind == 3) {
/* 170 */       this.collisionStart = MathUtil.maxValue(this.cX.tEnter, this.cY.tEnter, this.cZ.tEnter);
/* 171 */       this.collisionEnd = MathUtil.minValue(this.cX.tLeave, this.cY.tLeave, this.cZ.tLeave);
/* 172 */       this.collisionPoint.assign(this.pos);
/* 173 */       this.collisionPoint.addScaled(this.v, this.collisionStart);
/* 174 */       this.collisionNormal.assign(0.0D, this.cY.normal, 0.0D);
/* 175 */       this.onGround = true;
/* 176 */       this.touching = true;
/*     */     } 
/*     */     
/* 179 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isTouching() {
/* 183 */     return this.touching;
/*     */   }
/*     */   
/*     */   public void setCollisionEnd(double collisionEnd) {
/* 187 */     this.collisionEnd = collisionEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Collision1D
/*     */   {
/*     */     protected static final int COLLISION_OUTSIDE = 0;
/*     */     
/*     */     protected static final int COLLISION_INSIDE = 1;
/*     */     
/*     */     protected static final int COLLISION_TOUCH_MIN = 2;
/*     */     
/*     */     protected static final int COLLISION_TOUCH_MAX = 3;
/*     */     public double p;
/*     */     public double v;
/*     */     
/*     */     boolean isColliding(double min, double max) {
/* 204 */       this.min = min;
/* 205 */       this.max = max;
/*     */       
/* 207 */       this.tEnter = -1.7976931348623157E308D;
/* 208 */       this.tLeave = Double.MAX_VALUE;
/* 209 */       this.normal = 0.0D;
/* 210 */       this.touching = false;
/*     */       
/* 212 */       double dist = min - this.p;
/* 213 */       if (dist >= -1.0E-5D) {
/* 214 */         if (this.v < dist - 1.0E-5D) {
/* 215 */           return false;
/*     */         }
/* 217 */         this.normal = -1.0D;
/* 218 */         computeTouchOrOutside(max, dist, 2);
/* 219 */         return true;
/*     */       } 
/*     */       
/* 222 */       dist = max - this.p;
/* 223 */       if (dist <= 1.0E-5D) {
/* 224 */         if (this.v > dist + 1.0E-5D) {
/* 225 */           return false;
/*     */         }
/* 227 */         this.normal = 1.0D;
/* 228 */         computeTouchOrOutside(min, dist, 3);
/* 229 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 233 */       this.tEnter = 0.0D;
/* 234 */       if (this.v < 0.0D) {
/* 235 */         this.tLeave = clampPos((min - this.p) / this.v);
/* 236 */         this.normal = 1.0D;
/* 237 */       } else if (this.v > 0.0D) {
/* 238 */         this.tLeave = clampPos((max - this.p) / this.v);
/* 239 */         this.normal = -1.0D;
/*     */       } 
/* 241 */       this.kind = 1;
/* 242 */       return true;
/*     */     }
/*     */     public double min; public double max; public double tEnter; public double tLeave; public double normal; public int kind; public boolean touching;
/*     */     private void computeTouchOrOutside(double border, double dist, int touchCode) {
/* 246 */       if (this.v != 0.0D) {
/* 247 */         this.tEnter = MathUtil.clamp(dist / this.v, 0.0D, 1.0D);
/* 248 */         if (this.tEnter != 0.0D && this.tEnter < 1.0E-8D) {
/* 249 */           this.tEnter = 0.0D;
/*     */         }
/* 251 */         this.tLeave = clampPos((border - this.p) / this.v);
/* 252 */         this.kind = 0;
/*     */       } else {
/* 254 */         this.tEnter = 0.0D;
/* 255 */         this.kind = touchCode;
/*     */       } 
/*     */     }
/*     */     
/*     */     private double clampPos(double v) {
/* 260 */       return (v >= 0.0D) ? v : 0.0D;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\MovingBoxBoxCollisionEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */