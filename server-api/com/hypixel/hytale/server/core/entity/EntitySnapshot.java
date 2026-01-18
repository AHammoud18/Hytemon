/*    */ package com.hypixel.hytale.server.core.entity;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySnapshot
/*    */ {
/*    */   @Nonnull
/* 17 */   private final Vector3d position = new Vector3d();
/*    */   @Nonnull
/* 19 */   private final Vector3f bodyRotation = new Vector3f();
/*    */ 
/*    */   
/*    */   public EntitySnapshot() {}
/*    */ 
/*    */   
/*    */   public EntitySnapshot(@Nonnull Vector3d position, @Nonnull Vector3f bodyRotation) {
/* 26 */     this.position.assign(position);
/* 27 */     this.bodyRotation.assign(bodyRotation);
/*    */   }
/*    */   
/*    */   public void init(@Nonnull Vector3d position, @Nonnull Vector3f bodyRotation) {
/* 31 */     this.position.assign(position);
/* 32 */     this.bodyRotation.assign(bodyRotation);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getPosition() {
/* 37 */     return this.position;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3f getBodyRotation() {
/* 42 */     return this.bodyRotation;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 48 */     return "EntitySnapshot{position=" + String.valueOf(this.position) + ", bodyRotation=" + String.valueOf(this.bodyRotation) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\EntitySnapshot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */