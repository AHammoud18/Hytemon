/*    */ package com.hypixel.hytale.server.npc.corecomponents.lifecycle;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionDelayDespawn;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionDelayDespawn extends ActionBase {
/*    */   protected final float time;
/*    */   protected final boolean shorten;
/*    */   
/*    */   public ActionDelayDespawn(@Nonnull BuilderActionDelayDespawn builderActionDelayDespawn) {
/* 19 */     super((BuilderActionBase)builderActionDelayDespawn);
/* 20 */     this.time = builderActionDelayDespawn.getTime();
/* 21 */     this.shorten = builderActionDelayDespawn.getShorten();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 26 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 28 */     NPCEntity npcEntity = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 29 */     assert npcEntity != null;
/*    */     
/* 31 */     double delay = npcEntity.getDespawnTime();
/* 32 */     if ((this.shorten && delay < this.time) || (!this.shorten && delay > this.time)) {
/* 33 */       npcEntity.setDespawnTime(this.time);
/*    */     }
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\lifecycle\ActionDelayDespawn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */