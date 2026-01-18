/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.CameraManager;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PlayerCameraAddSystem extends HolderSystem<EntityStore> {
/* 13 */   private static final ComponentType<EntityStore, CameraManager> CAMERA_MANAGER_COMPONENT_TYPE = CameraManager.getComponentType();
/* 14 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)PlayerRef.getComponentType(), (Query)Query.not((Query)CAMERA_MANAGER_COMPONENT_TYPE) });
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 19 */     return QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 24 */     holder.ensureComponent(CAMERA_MANAGER_COMPONENT_TYPE);
/*    */   }
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerCameraAddSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */