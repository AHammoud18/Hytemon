/*    */ package com.hypixel.hytale.server.flock.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.corecomponents.builders.BuilderActionFlockState;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionFlockState
/*    */   extends ActionBase {
/*    */   protected final String state;
/*    */   
/*    */   public ActionFlockState(@Nonnull BuilderActionFlockState builderActionFlockState, @Nonnull BuilderSupport support) {
/* 21 */     super((BuilderActionBase)builderActionFlockState);
/*    */ 
/*    */     
/* 24 */     String[] split = builderActionFlockState.getState(support).split("\\.");
/* 25 */     this.state = split[0];
/* 26 */     this.subState = (split.length > 1 && split[1] != null && !split[1].isEmpty()) ? split[1] : null;
/*    */   }
/*    */   @Nullable
/*    */   protected final String subState;
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 31 */     super.execute(ref, role, sensorInfo, dt, store);
/* 32 */     role.getStateSupport().flockSetState(ref, this.state, this.subState, (ComponentAccessor)store);
/* 33 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\corecomponents\ActionFlockState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */