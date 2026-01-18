/*    */ package com.hypixel.hytale.server.npc.corecomponents.lifecycle;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionDie;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionDie extends ActionBase {
/*    */   public ActionDie(@Nonnull BuilderActionDie builder) {
/* 18 */     super((BuilderActionBase)builder);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 23 */     super.execute(ref, role, sensorInfo, dt, store);
/* 24 */     DeathComponent.tryAddComponent(store, ref, new Damage(Damage.NULL_SOURCE, DamageCause.PHYSICAL, 0.0F));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 29 */     role.setReachedTerminalAction(true);
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\lifecycle\ActionDie.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */