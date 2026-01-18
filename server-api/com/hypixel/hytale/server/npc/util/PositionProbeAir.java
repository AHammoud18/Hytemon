/*    */ package com.hypixel.hytale.server.npc.util;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.BlockMaterial;
/*    */ import com.hypixel.hytale.server.core.modules.collision.BoxBlockIntersectionEvaluator;
/*    */ import com.hypixel.hytale.server.core.modules.collision.CollisionConfig;
/*    */ import com.hypixel.hytale.server.core.modules.collision.CollisionMath;
/*    */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PositionProbeAir
/*    */   extends PositionProbeBase
/*    */ {
/*    */   protected boolean inAir;
/*    */   protected boolean onSolid;
/*    */   protected boolean collideWithFluid;
/*    */   
/*    */   public boolean probePosition(@Nonnull Ref<EntityStore> ref, @Nonnull Box boundingBox, @Nonnull Vector3d position, @Nonnull CollisionResult collisionResult, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 29 */     this.collideWithFluid = ((collisionResult.getConfig().getCollisionByMaterial() & 0x2) != 0);
/* 30 */     this.inAir = probePosition(ref, boundingBox, position, collisionResult, this, PositionProbeAir::blockTest, 6, componentAccessor);
/* 31 */     return this.inAir;
/*    */   }
/*    */   
/*    */   private boolean blockTest(int code, @Nonnull BoxBlockIntersectionEvaluator boxBlockIntersection, @Nonnull CollisionConfig config) {
/* 35 */     if (CollisionMath.isTouching(code)) {
/* 36 */       if (config.blockMaterial == BlockMaterial.Solid) {
/* 37 */         boolean isOnGround = boxBlockIntersection.isOnGround();
/* 38 */         this.onGround |= isOnGround;
/* 39 */         this.touchCeil |= boxBlockIntersection.touchesCeil();
/* 40 */         this.onSolid |= isOnGround;
/* 41 */         if (isOnGround && config.blockY > this.groundLevel) {
/* 42 */           this.groundLevel = config.blockY;
/*    */         }
/*    */       } 
/* 45 */       return false;
/* 46 */     }  if (CollisionMath.isOverlapping(code) && (config.blockMaterialMask & 0x2) != 0) {
/* 47 */       this.inWater = true;
/* 48 */       return this.collideWithFluid;
/*    */     } 
/* 50 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isInAir() {
/* 54 */     return this.inAir;
/*    */   }
/*    */   
/*    */   public boolean isOnSolid() {
/* 58 */     return this.onSolid;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void reset() {
/* 63 */     super.reset();
/* 64 */     this.inAir = false;
/* 65 */     this.onSolid = false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 71 */     return "PositionProbeAir{inAir=" + this.inAir + ", onSolid=" + this.onSolid + "} " + super
/*    */ 
/*    */       
/* 74 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\PositionProbeAir.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */