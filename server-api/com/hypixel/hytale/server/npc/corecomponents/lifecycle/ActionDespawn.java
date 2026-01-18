/*    */ package com.hypixel.hytale.server.npc.corecomponents.lifecycle;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionDespawn;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionDespawn extends ActionBase {
/*    */   protected final boolean force;
/*    */   
/*    */   public ActionDespawn(@Nonnull BuilderActionDespawn builderActionDespawn) {
/* 19 */     super((BuilderActionBase)builderActionDespawn);
/* 20 */     this.force = builderActionDespawn.isForced();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 25 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 27 */     if (this.force) {
/* 28 */       store.removeEntity(ref, RemoveReason.REMOVE);
/*    */     } else {
/* 30 */       NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 31 */       assert npcComponent != null;
/* 32 */       npcComponent.setToDespawn();
/*    */     } 
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\lifecycle\ActionDespawn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */