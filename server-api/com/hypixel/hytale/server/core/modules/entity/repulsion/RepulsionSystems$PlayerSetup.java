/*    */ package com.hypixel.hytale.server.core.modules.entity.repulsion;
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
/*    */ 
/*    */ public class PlayerSetup
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, Repulsion> componentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public PlayerSetup(ComponentType<EntityStore, Repulsion> componentType, ComponentType<EntityStore, Player> playerComponentType) {
/* 42 */     this.componentType = componentType;
/* 43 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)playerComponentType, (Query)Query.not((Query)componentType) });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 49 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 54 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 56 */     int repulsionConfigIndex = world.getGameplayConfig().getPlayerConfig().getRepulsionConfigIndex();
/* 57 */     if (repulsionConfigIndex == -1) {
/* 58 */       if (holder.getComponent(this.componentType) != null) {
/* 59 */         holder.removeComponent(this.componentType);
/*    */       }
/*    */     } else {
/* 62 */       RepulsionConfig repulsion = (RepulsionConfig)RepulsionConfig.getAssetMap().getAsset(repulsionConfigIndex);
/* 63 */       if (holder.getComponent(this.componentType) != null) {
/* 64 */         holder.removeComponent(this.componentType);
/*    */       }
/* 66 */       holder.addComponent(this.componentType, new Repulsion(repulsion));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\repulsion\RepulsionSystems$PlayerSetup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */