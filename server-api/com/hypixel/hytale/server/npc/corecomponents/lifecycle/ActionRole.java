/*    */ package com.hypixel.hytale.server.npc.corecomponents.lifecycle;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionRole;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.systems.RoleChangeSystem;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionRole
/*    */   extends ActionBase
/*    */ {
/*    */   protected final int roleIndex;
/*    */   protected final String kind;
/*    */   
/*    */   public ActionRole(@Nonnull BuilderActionRole builder, @Nonnull BuilderSupport builderSupport) {
/* 27 */     super((BuilderActionBase)builder);
/* 28 */     this.kind = builder.getRole(builderSupport);
/* 29 */     this.roleIndex = NPCPlugin.get().getIndex(this.kind);
/* 30 */     this.changeAppearance = builder.getChangeAppearance(builderSupport);
/*    */     
/* 32 */     String stateString = builder.getState(builderSupport);
/* 33 */     if (stateString != null) {
/* 34 */       String[] split = stateString.split("\\.");
/* 35 */       this.state = split[0];
/* 36 */       this.subState = (split.length > 1 && split[1] != null && !split[1].isEmpty()) ? split[1] : null;
/*    */     } else {
/* 38 */       this.state = null;
/* 39 */       this.subState = null;
/*    */     } 
/*    */   } protected final boolean changeAppearance; @Nullable
/*    */   protected final String state; @Nullable
/*    */   protected final String subState;
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 45 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && this.roleIndex >= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 50 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 52 */     if (role.isRoleChangeRequested()) return false; 
/* 53 */     RoleChangeSystem.requestRoleChange(ref, role, this.roleIndex, this.changeAppearance, this.state, this.subState, (ComponentAccessor)store);
/*    */ 
/*    */     
/* 56 */     role.setReachedTerminalAction(true);
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\lifecycle\ActionRole.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */