/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CollisionResultComponent implements Component<EntityStore> {
/*    */   private final CollisionResult collisionResult;
/*    */   
/*    */   public static ComponentType<EntityStore, CollisionResultComponent> getComponentType() {
/* 15 */     return EntityModule.get().getCollisionResultComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   private final Vector3d collisionStartPosition;
/*    */   private final Vector3d collisionPositionOffset;
/*    */   private final Vector3d collisionStartPositionCopy;
/*    */   private final Vector3d collisionPositionOffsetCopy;
/*    */   private boolean pendingCollisionCheck;
/*    */   
/*    */   public CollisionResultComponent() {
/* 26 */     this.collisionResult = new CollisionResult(false, false);
/* 27 */     this.collisionStartPosition = new Vector3d();
/* 28 */     this.collisionPositionOffset = new Vector3d();
/* 29 */     this.collisionStartPositionCopy = new Vector3d();
/* 30 */     this.collisionPositionOffsetCopy = new Vector3d();
/*    */   }
/*    */   
/*    */   public CollisionResultComponent(@Nonnull CollisionResultComponent other) {
/* 34 */     this.collisionResult = other.collisionResult;
/* 35 */     this.collisionStartPosition = other.collisionStartPosition;
/* 36 */     this.collisionPositionOffset = other.collisionPositionOffset;
/* 37 */     this.collisionStartPositionCopy = other.collisionStartPositionCopy;
/* 38 */     this.collisionPositionOffsetCopy = other.collisionPositionOffsetCopy;
/* 39 */     this.pendingCollisionCheck = other.pendingCollisionCheck;
/*    */   }
/*    */   
/*    */   public CollisionResult getCollisionResult() {
/* 43 */     return this.collisionResult;
/*    */   }
/*    */   
/*    */   public Vector3d getCollisionStartPosition() {
/* 47 */     return this.collisionStartPosition;
/*    */   }
/*    */   
/*    */   public Vector3d getCollisionPositionOffset() {
/* 51 */     return this.collisionPositionOffset;
/*    */   }
/*    */   
/*    */   public Vector3d getCollisionStartPositionCopy() {
/* 55 */     return this.collisionStartPositionCopy;
/*    */   }
/*    */   
/*    */   public Vector3d getCollisionPositionOffsetCopy() {
/* 59 */     return this.collisionPositionOffsetCopy;
/*    */   }
/*    */   
/*    */   public boolean isPendingCollisionCheck() {
/* 63 */     return this.pendingCollisionCheck;
/*    */   }
/*    */   
/*    */   public void markPendingCollisionCheck() {
/* 67 */     this.pendingCollisionCheck = true;
/*    */   }
/*    */   
/*    */   public void consumePendingCollisionCheck() {
/* 71 */     this.pendingCollisionCheck = false;
/*    */   }
/*    */   
/*    */   public void resetLocationChange() {
/* 75 */     this.collisionPositionOffset.assign(Vector3d.ZERO);
/* 76 */     this.pendingCollisionCheck = false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 82 */     return new CollisionResultComponent(this);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\CollisionResultComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */