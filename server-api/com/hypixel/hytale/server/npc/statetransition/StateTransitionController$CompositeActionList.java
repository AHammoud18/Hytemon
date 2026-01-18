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
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
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
/*     */ class CompositeActionList
/*     */   implements StateTransitionController.IActionListHolder
/*     */ {
/* 228 */   private final List<StateTransitionController.PrioritisedActionList> actionLists = (List<StateTransitionController.PrioritisedActionList>)new ObjectArrayList();
/*     */   
/*     */   private int currentIndex;
/*     */   
/*     */   private CompositeActionList(StateTransitionController.PrioritisedActionList initialActionList) {
/* 233 */     this.actionLists.add(initialActionList);
/*     */   }
/*     */   
/*     */   private void addActionList(int priority, ActionList actionList) {
/* 237 */     for (int i = 0; i < this.actionLists.size(); i++) {
/* 238 */       if (priority > ((StateTransitionController.PrioritisedActionList)this.actionLists.get(i)).priority) {
/* 239 */         this.actionLists.add(i, new StateTransitionController.PrioritisedActionList(priority, actionList));
/*     */         return;
/*     */       } 
/*     */     } 
/* 243 */     this.actionLists.add(new StateTransitionController.PrioritisedActionList(priority, actionList));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 248 */     if (this.currentIndex >= this.actionLists.size()) this.currentIndex = 0; 
/* 249 */     return ((StateTransitionController.PrioritisedActionList)this.actionLists.get(this.currentIndex)).actionList.canExecute(ref, role, sensorInfo, dt, store);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 254 */     StateTransitionController.PrioritisedActionList actionList = this.actionLists.get(this.currentIndex);
/* 255 */     if (!actionList.actionList.canExecute(ref, role, sensorInfo, dt, store)) return false;
/*     */     
/* 257 */     if (actionList.actionList.execute(ref, role, sensorInfo, dt, store) && actionList.actionList.hasCompletedRun()) {
/* 258 */       this.currentIndex++;
/* 259 */       return true;
/*     */     } 
/*     */     
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCompletedRun() {
/* 267 */     if (this.currentIndex >= this.actionLists.size()) {
/* 268 */       this.currentIndex = 0;
/* 269 */       return true;
/*     */     } 
/* 271 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/* 276 */     for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 277 */       actionList.actionList.registerWithSupport(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 283 */     for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 284 */       actionList.actionList.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/* 290 */     for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 291 */       actionList.actionList.loaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/* 297 */     for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 298 */       actionList.actionList.spawned(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/* 304 */     for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 305 */       actionList.actionList.unloaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/* 311 */     for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 312 */       actionList.actionList.removed(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 318 */     for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 319 */       actionList.actionList.teleported(role, from, to);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearOnce() {
/* 325 */     for (StateTransitionController.PrioritisedActionList actionList : this.actionLists)
/* 326 */       actionList.actionList.clearOnce(); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\statetransition\StateTransitionController$CompositeActionList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */