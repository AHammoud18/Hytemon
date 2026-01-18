/*    */ package com.hypixel.hytale.server.flock.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.FlockMembership;
/*    */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*    */ import com.hypixel.hytale.server.flock.corecomponents.builders.BuilderActionFlockLeave;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionFlockLeave extends ActionBase {
/*    */   public ActionFlockLeave(@Nonnull BuilderActionFlockLeave builderActionFlockLeave) {
/* 17 */     super((BuilderActionBase)builderActionFlockLeave);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 22 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && FlockPlugin.isFlockMember(ref, store));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 27 */     super.execute(ref, role, sensorInfo, dt, store);
/* 28 */     store.tryRemoveComponent(ref, FlockMembership.getComponentType());
/* 29 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\corecomponents\ActionFlockLeave.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */