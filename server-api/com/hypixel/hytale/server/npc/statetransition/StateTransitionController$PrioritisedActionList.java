/*     */ package com.hypixel.hytale.server.npc.statetransition;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.ActionList;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PrioritisedActionList
/*     */   extends Record
/*     */   implements StateTransitionController.IActionListHolder
/*     */ {
/*     */   private final int priority;
/*     */   private final ActionList actionList;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #169	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #169	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #169	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   private PrioritisedActionList(int priority, ActionList actionList) {
/* 169 */     this.priority = priority; this.actionList = actionList; } public int priority() { return this.priority; } public ActionList actionList() { return this.actionList; }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 173 */     return this.actionList.canExecute(ref, role, sensorInfo, dt, store);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 178 */     return this.actionList.execute(ref, role, sensorInfo, dt, store);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCompletedRun() {
/* 183 */     return this.actionList.hasCompletedRun();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/* 188 */     this.actionList.registerWithSupport(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 193 */     this.actionList.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/* 198 */     this.actionList.loaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/* 203 */     this.actionList.spawned(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/* 208 */     this.actionList.unloaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/* 213 */     this.actionList.removed(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 218 */     this.actionList.teleported(role, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearOnce() {
/* 223 */     this.actionList.clearOnce();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\statetransition\StateTransitionController$PrioritisedActionList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */