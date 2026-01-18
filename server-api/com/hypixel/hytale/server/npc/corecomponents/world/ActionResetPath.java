/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionResetPath;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionResetPath extends ActionBase {
/*    */   public ActionResetPath(@Nonnull BuilderActionResetPath builder) {
/* 15 */     super((BuilderActionBase)builder);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 20 */     super.execute(ref, role, sensorInfo, dt, store);
/* 21 */     role.getWorldSupport().requestNewPath();
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\ActionResetPath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */