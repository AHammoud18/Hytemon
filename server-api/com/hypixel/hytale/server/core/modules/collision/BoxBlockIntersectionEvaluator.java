/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BoxBlockIntersectionEvaluator
/*     */   extends BlockContactData
/*     */   implements IBlockCollisionEvaluator
/*     */ {
/*     */   @Nonnull
/*  12 */   protected Box box = new Box();
/*     */   
/*  14 */   protected Vector3d worldUp = Vector3d.UP;
/*     */   
/*     */   protected boolean touchCeil;
/*     */   protected int resultCode;
/*     */   
/*     */   public BoxBlockIntersectionEvaluator() {
/*  20 */     setStartEnd(0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCollisionData(@Nonnull BlockCollisionData data, @Nonnull CollisionConfig collisionConfig, int hitboxIndex) {
/*  25 */     data.setStart(this.collisionPoint, this.collisionStart);
/*  26 */     data.setEnd(this.collisionEnd, this.collisionNormal);
/*  27 */     data.setBlockData(collisionConfig);
/*  28 */     data.setDetailBoxIndex(hitboxIndex);
/*  29 */     data.setTouchingOverlapping(CollisionMath.isTouching(this.resultCode), CollisionMath.isOverlapping(this.resultCode));
/*     */   }
/*     */   
/*     */   public Vector3d getWorldUp() {
/*  33 */     return this.worldUp;
/*     */   }
/*     */   
/*     */   public void setWorldUp(Vector3d worldUp) {
/*  37 */     this.worldUp = worldUp;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BoxBlockIntersectionEvaluator setBox(@Nonnull Box box) {
/*  42 */     this.box.assign(box);
/*  43 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BoxBlockIntersectionEvaluator expandBox(double radius) {
/*  48 */     this.box.expand(radius);
/*  49 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BoxBlockIntersectionEvaluator setPosition(@Nonnull Vector3d pos) {
/*  54 */     this.collisionPoint.assign(pos);
/*  55 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BoxBlockIntersectionEvaluator setBox(@Nonnull Box box, @Nonnull Vector3d pos) {
/*  60 */     return setBox(box).setPosition(pos);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BoxBlockIntersectionEvaluator offsetPosition(@Nonnull Vector3d offset) {
/*  65 */     this.collisionPoint.add(offset);
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BoxBlockIntersectionEvaluator setStartEnd(double start, double end) {
/*  71 */     this.collisionStart = start;
/*  72 */     this.collisionEnd = end;
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int intersectBox(@Nonnull Box otherBox, double x, double y, double z) {
/*  78 */     return CollisionMath.intersectAABBs(this.collisionPoint.x, this.collisionPoint.y, this.collisionPoint.z, this.box, x, y, z, otherBox);
/*     */   }
/*     */   
/*     */   public int intersectBoxComputeTouch(@Nonnull Box otherBox, double x, double y, double z) {
/*  82 */     int code = CollisionMath.intersectAABBs(this.collisionPoint.x, this.collisionPoint.y, this.collisionPoint.z, this.box, x, y, z, otherBox);
/*     */     
/*  84 */     this.resultCode = code;
/*  85 */     this.onGround = false;
/*  86 */     this.touchCeil = false;
/*  87 */     this.collisionNormal.assign(0.0D, 0.0D, 0.0D);
/*  88 */     this.overlapping = CollisionMath.isOverlapping(this.resultCode);
/*     */     
/*  90 */     if ((code & 0x7) != 0)
/*     */     {
/*     */       
/*  93 */       if (this.worldUp.y != 0.0D) {
/*  94 */         if ((code & 0x2) != 0) {
/*  95 */           this.collisionNormal.assign(0.0D, (y + otherBox.min.y < this.collisionPoint.y + this.box.min.y) ? 1.0D : -1.0D, 0.0D);
/*  96 */           this.onGround = (this.collisionNormal.y == this.worldUp.y);
/*  97 */           this.touchCeil = !this.onGround;
/*  98 */         } else if ((code & 0x1) != 0) {
/*  99 */           this.collisionNormal.assign((x + otherBox.min.x < this.collisionPoint.x + this.box.min.x) ? 1.0D : -1.0D, 0.0D, 0.0D);
/*     */         } else {
/* 101 */           this.collisionNormal.assign(0.0D, 0.0D, (z + otherBox.min.z < this.collisionPoint.z + this.box.min.z) ? 1.0D : -1.0D);
/*     */         } 
/* 103 */       } else if (this.worldUp.x != 0.0D) {
/* 104 */         if ((code & 0x1) != 0) {
/* 105 */           this.collisionNormal.assign((x + otherBox.min.x < this.collisionPoint.x + this.box.min.x) ? 1.0D : -1.0D, 0.0D, 0.0D);
/* 106 */           this.onGround = (this.collisionNormal.x == this.worldUp.x);
/* 107 */           this.touchCeil = !this.onGround;
/* 108 */         } else if ((code & 0x2) != 0) {
/* 109 */           this.collisionNormal.assign(0.0D, (y + otherBox.min.y < this.collisionPoint.y + this.box.min.y) ? 1.0D : -1.0D, 0.0D);
/*     */         } else {
/* 111 */           this.collisionNormal.assign(0.0D, 0.0D, (z + otherBox.min.z < this.collisionPoint.z + this.box.min.z) ? 1.0D : -1.0D);
/*     */         }
/*     */       
/* 114 */       } else if ((code & 0x4) != 0) {
/* 115 */         this.collisionNormal.assign(0.0D, 0.0D, (z + otherBox.min.z < this.collisionPoint.z + this.box.min.z) ? 1.0D : -1.0D);
/* 116 */         this.onGround = (this.collisionNormal.z == this.worldUp.z);
/* 117 */         this.touchCeil = !this.onGround;
/* 118 */       } else if ((code & 0x2) != 0) {
/* 119 */         this.collisionNormal.assign(0.0D, (y + otherBox.min.y < this.collisionPoint.y + this.box.min.y) ? 1.0D : -1.0D, 0.0D);
/*     */       } else {
/* 121 */         this.collisionNormal.assign((x + otherBox.min.x < this.collisionPoint.x + this.box.min.x) ? 1.0D : -1.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     }
/*     */     
/* 125 */     return code;
/*     */   }
/*     */   
/*     */   public int intersectBoxComputeOnGround(@Nonnull Box otherBox, double x, double y, double z) {
/* 129 */     int code = CollisionMath.intersectAABBs(this.collisionPoint.x, this.collisionPoint.y, this.collisionPoint.z, this.box, x, y, z, otherBox);
/*     */     
/* 131 */     this.resultCode = code;
/* 132 */     this.onGround = false;
/* 133 */     this.touchCeil = false;
/* 134 */     if ((code & 0x7) != 0) {
/* 135 */       if (this.worldUp.y != 0.0D && (code & 0x2) != 0) {
/* 136 */         this.onGround = ((y + otherBox.min.y - this.collisionPoint.y - this.box.min.y) * this.worldUp.y < 0.0D);
/* 137 */         this.touchCeil = !this.onGround;
/* 138 */       } else if (this.worldUp.x != 0.0D && (code & 0x1) != 0) {
/* 139 */         this.onGround = ((x + otherBox.min.x - this.collisionPoint.x - this.box.min.x) * this.worldUp.x < 0.0D);
/* 140 */         this.touchCeil = !this.onGround;
/* 141 */       } else if (this.worldUp.z != 0.0D && (code & 0x4) != 0) {
/* 142 */         this.onGround = ((z + otherBox.min.z - this.collisionPoint.z - this.box.min.z) * this.worldUp.z < 0.0D);
/* 143 */         this.touchCeil = !this.onGround;
/*     */       } 
/*     */     }
/* 146 */     return code;
/*     */   }
/*     */   
/*     */   public boolean isBoxIntersecting(@Nonnull Box otherBox, double x, double y, double z) {
/* 150 */     return !CollisionMath.isDisjoint(intersectBoxComputeTouch(otherBox, x, y, z));
/*     */   }
/*     */   
/*     */   public boolean isTouching() {
/* 154 */     return CollisionMath.isTouching(this.resultCode);
/*     */   }
/*     */   
/*     */   public boolean touchesCeil() {
/* 158 */     return this.touchCeil;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\BoxBlockIntersectionEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */