/*    */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ @Deprecated(forRemoval = true)
/*    */ public class Rotation3D
/*    */ {
/*    */   public Rotation rotationYaw;
/*    */   public Rotation rotationPitch;
/*    */   public Rotation rotationRoll;
/*    */   
/*    */   public Rotation3D(Rotation rotationYaw, Rotation rotationPitch, Rotation rotationRoll) {
/* 16 */     this.rotationYaw = rotationYaw;
/* 17 */     this.rotationPitch = rotationPitch;
/* 18 */     this.rotationRoll = rotationRoll;
/*    */   }
/*    */   
/*    */   public void assign(Rotation yaw, Rotation pitch, Rotation roll) {
/* 22 */     this.rotationYaw = yaw;
/* 23 */     this.rotationPitch = pitch;
/* 24 */     this.rotationRoll = roll;
/*    */   }
/*    */   
/*    */   public void assign(@Nonnull RotationTuple rotation) {
/* 28 */     assign(rotation
/* 29 */         .yaw(), rotation
/* 30 */         .pitch(), rotation
/* 31 */         .roll());
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(@Nonnull Rotation3D toAdd) {
/* 36 */     this.rotationYaw = this.rotationYaw.add(toAdd.rotationYaw);
/* 37 */     this.rotationPitch = this.rotationPitch.add(toAdd.rotationPitch);
/* 38 */     this.rotationRoll = this.rotationRoll.add(toAdd.rotationRoll);
/*    */   }
/*    */   
/*    */   public void subtract(@Nonnull Rotation3D toSubtract) {
/* 42 */     this.rotationYaw = this.rotationYaw.subtract(toSubtract.rotationYaw);
/* 43 */     this.rotationPitch = this.rotationPitch.subtract(toSubtract.rotationPitch);
/* 44 */     this.rotationRoll = this.rotationRoll.subtract(toSubtract.rotationRoll);
/*    */   }
/*    */   
/*    */   public void negate() {
/* 48 */     assign(Rotation.None
/* 49 */         .subtract(this.rotationYaw), Rotation.None
/* 50 */         .subtract(this.rotationPitch), Rotation.None
/* 51 */         .subtract(this.rotationRoll));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Rotation3D rotateSelfBy(@Nonnull Rotation rotationYawToRotate, @Nonnull Rotation rotationPitchToRotate, @Nonnull Rotation rotationRollToRotate) {
/* 58 */     Vector3f vector3f = new Vector3f(this.rotationPitch.getDegrees(), this.rotationYaw.getDegrees(), this.rotationRoll.getDegrees());
/* 59 */     vector3f = Rotation.rotate(vector3f, rotationYawToRotate, rotationPitchToRotate, rotationRollToRotate);
/*    */     
/* 61 */     assign(
/* 62 */         Rotation.closestOfDegrees(vector3f.y), 
/* 63 */         Rotation.closestOfDegrees(vector3f.x), 
/* 64 */         Rotation.closestOfDegrees(vector3f.z));
/*    */     
/* 66 */     return this;
/*    */   }
/*    */   
/*    */   public void rotateSelfBy(@Nonnull Rotation3D rotation) {
/* 70 */     rotateSelfBy(rotation.rotationYaw, rotation.rotationPitch, rotation.rotationRoll);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\Rotation3D.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */