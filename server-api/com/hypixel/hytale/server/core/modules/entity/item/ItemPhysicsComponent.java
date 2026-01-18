/*    */ package com.hypixel.hytale.server.core.modules.entity.item;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ItemPhysicsComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, ItemPhysicsComponent> getComponentType() {
/* 18 */     return EntityModule.get().getItemPhysicsComponentType();
/*    */   }
/*    */   
/* 21 */   public Vector3d scaledVelocity = new Vector3d();
/* 22 */   public CollisionResult collisionResult = new CollisionResult();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemPhysicsComponent(Vector3d scaledVelocity, CollisionResult collisionResult) {
/* 28 */     this.scaledVelocity = scaledVelocity;
/* 29 */     this.collisionResult = collisionResult;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 35 */     return new ItemPhysicsComponent(this.scaledVelocity, this.collisionResult);
/*    */   }
/*    */   
/*    */   public ItemPhysicsComponent() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\ItemPhysicsComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */