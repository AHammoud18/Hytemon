/*    */ package com.hypixel.hytale.server.core.modules.collision;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityContactData
/*    */ {
/* 15 */   protected final Vector3d collisionPoint = new Vector3d();
/*    */   protected double collisionStart;
/*    */   protected double collisionEnd;
/*    */   @Nullable
/*    */   protected Ref<EntityStore> entityReference;
/*    */   protected String collisionDetailName;
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getCollisionPoint() {
/* 24 */     return this.collisionPoint;
/*    */   }
/*    */   
/*    */   public double getCollisionStart() {
/* 28 */     return this.collisionStart;
/*    */   }
/*    */   
/*    */   public double getCollisionEnd() {
/* 32 */     return this.collisionEnd;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> getEntityReference() {
/* 37 */     return this.entityReference;
/*    */   }
/*    */   
/*    */   public String getCollisionDetailName() {
/* 41 */     return this.collisionDetailName;
/*    */   }
/*    */   
/*    */   public void assign(@Nonnull Vector3d position, double start, double end, Ref<EntityStore> entity, String collisionDetailName) {
/* 45 */     this.collisionPoint.assign(position);
/* 46 */     this.collisionStart = start;
/* 47 */     this.collisionEnd = end;
/* 48 */     this.entityReference = entity;
/* 49 */     this.collisionDetailName = collisionDetailName;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 53 */     this.entityReference = null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\EntityContactData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */