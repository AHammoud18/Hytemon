/*    */ package com.hypixel.hytale.server.npc.util;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
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
/*    */ 
/*    */ 
/*    */ public class PositionProbeWater
/*    */   extends PositionProbeBase
/*    */ {
/*    */   private double ySwim;
/*    */   
/*    */   public boolean probePosition(@Nonnull Ref<EntityStore> ref, @Nonnull Box boundingBox, @Nonnull Vector3d position, @Nonnull CollisionResult collisionResult, double swimDepth, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 28 */     this.ySwim = position.y + swimDepth + 1.0E-6D;
/* 29 */     return probePosition(ref, boundingBox, position, collisionResult, this, PositionProbeWater::blockTest, 6, componentAccessor);
/*    */   }
/*    */   
/*    */   private boolean blockTest(int code, @Nonnull BoxBlockIntersectionEvaluator boxBlockIntersection, @Nonnull CollisionConfig config) {
/* 33 */     boolean solid = ((config.blockMaterialMask & 0x4) != 0);
/* 34 */     boolean fluid = ((config.blockMaterialMask & 0x2) != 0);
/* 35 */     boolean submerged = ((config.blockMaterialMask & 0x8) != 0);
/*    */     
/* 37 */     if (solid && CollisionMath.isTouching(code)) {
/* 38 */       boolean isOnGround = boxBlockIntersection.isOnGround();
/* 39 */       this.onGround |= isOnGround;
/* 40 */       this.touchCeil |= boxBlockIntersection.touchesCeil();
/* 41 */       if (isOnGround && config.blockY > this.groundLevel) {
/* 42 */         this.groundLevel = config.blockY;
/*    */       }
/*    */     } 
/* 45 */     if ((fluid && CollisionMath.isOverlapping(code)) || submerged) {
/*    */       
/* 47 */       double yTop = (config.blockY + 1);
/* 48 */       this.inWater |= (yTop >= this.ySwim) ? 1 : 0;
/* 49 */       return false;
/*    */     } 
/* 51 */     return solid;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void reset() {
/* 56 */     super.reset();
/* 57 */     this.inWater = false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 63 */     return "PositionProbeWater{ySwim=" + this.ySwim + "} " + super
/*    */       
/* 65 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\PositionProbeWater.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */