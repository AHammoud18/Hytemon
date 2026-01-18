/*    */ package com.hypixel.hytale.server.core.modules.entity.damage;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.EntityEventSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DamageEventSystem
/*    */   extends EntityEventSystem<EntityStore, Damage>
/*    */ {
/*    */   protected DamageEventSystem() {
/* 15 */     super(Damage.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\damage\DamageEventSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */