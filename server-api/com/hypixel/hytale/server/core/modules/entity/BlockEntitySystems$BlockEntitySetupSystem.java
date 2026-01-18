/*    */ package com.hypixel.hytale.server.core.modules.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.server.core.entity.entities.BlockEntity;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*    */ import com.hypixel.hytale.server.core.modules.physics.SimplePhysicsProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.logging.Level;
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
/*    */ public class BlockEntitySetupSystem
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, BlockEntity> blockEntityComponentType;
/*    */   
/*    */   public BlockEntitySetupSystem(ComponentType<EntityStore, BlockEntity> blockEntityComponentType) {
/* 34 */     this.blockEntityComponentType = blockEntityComponentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 39 */     if (!holder.getArchetype().contains(NetworkId.getComponentType())) {
/* 40 */       holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*    */     }
/*    */     
/* 43 */     BlockEntity blockEntityComponent = (BlockEntity)holder.getComponent(this.blockEntityComponentType);
/*    */     
/* 45 */     BoundingBox boundingBoxComponent = blockEntityComponent.createBoundingBoxComponent();
/*    */     
/* 47 */     if (boundingBoxComponent == null) {
/* 48 */       BlockEntitySystems.LOGGER.at(Level.SEVERE).log("Bounding box could not be initialized properly, defaulting to 1x1x1 dimensions for Block Entity bounding box");
/* 49 */       boundingBoxComponent = new BoundingBox(Box.horizontallyCentered(1.0D, 1.0D, 1.0D));
/*    */     } 
/*    */     
/* 52 */     holder.putComponent(BoundingBox.getComponentType(), (Component)boundingBoxComponent);
/* 53 */     SimplePhysicsProvider simplePhysicsProvider = blockEntityComponent.initPhysics(boundingBoxComponent);
/* 54 */     simplePhysicsProvider.setMoveOutOfSolid(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 64 */     return (Query)this.blockEntityComponentType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\BlockEntitySystems$BlockEntitySetupSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */