/*    */ package com.hypixel.hytale.server.core.modules.entity.hitboxcollision;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Setup
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, HitboxCollision> componentType;
/*    */   private final ComponentType<EntityStore, Player> playerComponentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public Setup(ComponentType<EntityStore, HitboxCollision> componentType, ComponentType<EntityStore, Player> playerComponentType) {
/* 26 */     this.componentType = componentType;
/* 27 */     this.playerComponentType = playerComponentType;
/* 28 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)playerComponentType, (Query)Query.not((Query)componentType) });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 34 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 39 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 41 */     int hitboxCollisionConfigIndex = world.getGameplayConfig().getPlayerConfig().getHitboxCollisionConfigIndex();
/* 42 */     if (hitboxCollisionConfigIndex == -1)
/*    */       return; 
/* 44 */     holder.addComponent(this.componentType, new HitboxCollision((HitboxCollisionConfig)HitboxCollisionConfig.getAssetMap().getAsset(hitboxCollisionConfigIndex)));
/*    */   }
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\hitboxcollision\HitboxCollisionSystems$Setup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */