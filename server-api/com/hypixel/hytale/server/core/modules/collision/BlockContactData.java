/*    */ package com.hypixel.hytale.server.core.modules.collision;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockContactData
/*    */ {
/* 13 */   protected final Vector3d collisionNormal = new Vector3d();
/*    */   
/* 15 */   protected final Vector3d collisionPoint = new Vector3d();
/*    */   
/*    */   protected double collisionStart;
/*    */   
/*    */   protected double collisionEnd;
/*    */   
/*    */   protected boolean onGround;
/*    */   
/*    */   protected int damage;
/*    */   protected boolean isSubmergeFluid;
/*    */   protected boolean overlapping;
/*    */   
/*    */   public void clear() {}
/*    */   
/*    */   public void assign(@Nonnull BlockContactData other) {
/* 30 */     assign(other, other.damage, other.isSubmergeFluid);
/*    */   }
/*    */   
/*    */   public void assign(@Nonnull BlockContactData other, int damage, boolean isSubmergedFluid) {
/* 34 */     this.collisionNormal.assign(other.collisionNormal);
/* 35 */     this.collisionPoint.assign(other.collisionPoint);
/* 36 */     this.collisionStart = other.collisionStart;
/* 37 */     this.collisionEnd = other.collisionEnd;
/* 38 */     this.onGround = other.onGround;
/* 39 */     this.overlapping = other.overlapping;
/* 40 */     setDamageAndSubmerged(damage, isSubmergedFluid);
/*    */   }
/*    */   
/*    */   public void setDamageAndSubmerged(int damage, boolean isSubmerge) {
/* 44 */     this.damage = damage;
/* 45 */     this.isSubmergeFluid = isSubmerge;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getCollisionNormal() {
/* 50 */     return this.collisionNormal;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getCollisionPoint() {
/* 55 */     return this.collisionPoint;
/*    */   }
/*    */   
/*    */   public double getCollisionStart() {
/* 59 */     return this.collisionStart;
/*    */   }
/*    */   
/*    */   public double getCollisionEnd() {
/* 63 */     return this.collisionEnd;
/*    */   }
/*    */   
/*    */   public boolean isOverlapping() {
/* 67 */     return this.overlapping;
/*    */   }
/*    */   
/*    */   public boolean isOnGround() {
/* 71 */     return this.onGround;
/*    */   }
/*    */   
/*    */   public int getDamage() {
/* 75 */     return this.damage;
/*    */   }
/*    */   
/*    */   public boolean isSubmergeFluid() {
/* 79 */     return this.isSubmergeFluid;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\BlockContactData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */