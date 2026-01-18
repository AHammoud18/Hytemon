/*    */ package com.hypixel.hytale.server.core.modules.entity.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.DelayedSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UpdateEntitySeedSystem
/*    */   extends DelayedSystem<EntityStore>
/*    */ {
/*    */   public UpdateEntitySeedSystem() {
/* 15 */     super(1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void delayedTick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 20 */     ((EntityStore)store.getExternalData()).getWorld().updateEntitySeed(store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\UpdateEntitySeedSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */