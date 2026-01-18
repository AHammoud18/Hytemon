/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderActionRecomputePath;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionRecomputePath
/*    */   extends ActionBase
/*    */ {
/*    */   public ActionRecomputePath(@Nonnull BuilderActionRecomputePath builder) {
/* 24 */     super((BuilderActionBase)builder);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider infoProvider, double dt, @Nonnull Store<EntityStore> store) {
/* 29 */     super.execute(ref, role, infoProvider, dt, store);
/* 30 */     role.getActiveMotionController().setForceRecomputePath(true);
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\ActionRecomputePath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */