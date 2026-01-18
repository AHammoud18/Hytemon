/*    */ package com.hypixel.hytale.server.npc.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.instructions.Action;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class ActionBase
/*    */   extends AnnotatedComponentBase
/*    */   implements Action {
/*    */   protected boolean once;
/*    */   protected boolean triggered;
/*    */   protected boolean active;
/*    */   
/*    */   public ActionBase(@Nonnull BuilderActionBase builderActionBase) {
/* 20 */     this.once = builderActionBase.isOnce();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 25 */     return (!this.once || !this.triggered);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     setOnce();
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void activate(Role role, InfoProvider infoProvider) {
/* 36 */     this.active = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate(Role role, InfoProvider infoProvider) {
/* 41 */     this.active = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isActivated() {
/* 46 */     return this.active;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggered() {
/* 51 */     return this.triggered;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearOnce() {
/* 56 */     this.triggered = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOnce() {
/* 61 */     this.triggered = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processDelay(float dt) {
/* 66 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\ActionBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */