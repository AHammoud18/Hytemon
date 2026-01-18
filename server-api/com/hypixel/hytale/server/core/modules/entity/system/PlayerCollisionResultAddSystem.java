/*    */ package com.hypixel.hytale.server.core.modules.entity.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.CollisionResultComponent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerCollisionResultAddSystem
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, CollisionResultComponent> collisionResultComponentType;
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, Player> playerComponentType;
/*    */   
/*    */   public PlayerCollisionResultAddSystem(@Nonnull ComponentType<EntityStore, Player> playerComponentType, @Nonnull ComponentType<EntityStore, CollisionResultComponent> collisionResultComponentType) {
/* 43 */     this.collisionResultComponentType = collisionResultComponentType;
/* 44 */     this.playerComponentType = playerComponentType;
/* 45 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)playerComponentType, (Query)Query.not((Query)collisionResultComponentType) });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 50 */     Player playerComponent = (Player)holder.getComponent(this.playerComponentType);
/* 51 */     assert playerComponent != null;
/*    */     
/* 53 */     CollisionResultComponent collisionResultComponent = new CollisionResultComponent();
/*    */     
/* 55 */     CollisionResult collisionResult = collisionResultComponent.getCollisionResult();
/* 56 */     collisionResult.setDefaultPlayerSettings();
/* 57 */     collisionResultComponent.resetLocationChange();
/*    */     
/* 59 */     playerComponent.configTriggerBlockProcessing(true, true, collisionResultComponent);
/* 60 */     holder.addComponent(this.collisionResultComponentType, (Component)collisionResultComponent);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 71 */     return this.query;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\PlayerCollisionResultAddSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */