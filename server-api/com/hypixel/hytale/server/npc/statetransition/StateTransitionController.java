/*     */ package com.hypixel.hytale.server.npc.statetransition;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderFactory;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderManager;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.StateMappingHelper;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.ActionList;
/*     */ import com.hypixel.hytale.server.npc.instructions.RoleStateChange;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.statetransition.builders.BuilderStateTransition;
/*     */ import com.hypixel.hytale.server.npc.statetransition.builders.BuilderStateTransitionController;
/*     */ import com.hypixel.hytale.server.npc.statetransition.builders.BuilderStateTransitionEdges;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class StateTransitionController {
/*  28 */   private final Int2ObjectOpenHashMap<IActionListHolder> stateTransitionActions = new Int2ObjectOpenHashMap();
/*     */   
/*     */   @Nullable
/*     */   private IActionListHolder runningActions;
/*     */   
/*     */   public StateTransitionController(@Nonnull BuilderStateTransitionController builder, @Nonnull BuilderSupport support) {
/*  34 */     StateMappingHelper stateHelper = support.getStateHelper();
/*  35 */     List<BuilderStateTransition.StateTransition> stateTransitionEntries = builder.getStateTransitionEntries(support);
/*  36 */     for (BuilderStateTransition.StateTransition stateTransitionEntry : stateTransitionEntries) {
/*  37 */       ActionList actions = stateTransitionEntry.getActions();
/*  38 */       for (BuilderStateTransitionEdges.StateTransitionEdges stateTransition : stateTransitionEntry.getStateTransitionEdges()) {
/*  39 */         int priority = stateTransition.getPriority();
/*     */         
/*  41 */         int[] fromStateIndices = (stateTransition.getFromStateIndices() != null) ? stateTransition.getFromStateIndices() : stateHelper.getAllMainStates();
/*  42 */         int[] toStateIndices = (stateTransition.getToStateIndices() != null) ? stateTransition.getToStateIndices() : stateHelper.getAllMainStates();
/*  43 */         for (int fromIndex : fromStateIndices) {
/*  44 */           for (int toIndex : toStateIndices) {
/*  45 */             if (toIndex != fromIndex) {
/*     */               
/*  47 */               int combinedValue = indexStateTransitionEdge(fromIndex, toIndex);
/*  48 */               IActionListHolder currentList = (IActionListHolder)this.stateTransitionActions.get(combinedValue);
/*  49 */               if (currentList == null)
/*     */               
/*  51 */               { this.stateTransitionActions.put(combinedValue, new PrioritisedActionList(priority, actions)); }
/*     */               else
/*     */               { CompositeActionList compositeActionList;
/*     */ 
/*     */ 
/*     */                 
/*  57 */                 if (currentList instanceof CompositeActionList) {
/*     */                   
/*  59 */                   compositeActionList = (CompositeActionList)currentList;
/*     */                 } else {
/*     */                   
/*  62 */                   compositeActionList = new CompositeActionList((PrioritisedActionList)currentList);
/*  63 */                   this.stateTransitionActions.put(combinedValue, compositeActionList);
/*     */                 } 
/*     */                 
/*  66 */                 compositeActionList.addActionList(priority, actions); } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*  71 */     }  this.stateTransitionActions.trim();
/*     */   }
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  75 */     for (ObjectIterator<IActionListHolder> objectIterator = this.stateTransitionActions.values().iterator(); objectIterator.hasNext(); ) { IActionListHolder actions = objectIterator.next();
/*  76 */       actions.registerWithSupport(role); }
/*     */   
/*     */   }
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, @Nullable MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  81 */     for (ObjectIterator<IActionListHolder> objectIterator = this.stateTransitionActions.values().iterator(); objectIterator.hasNext(); ) { IActionListHolder actions = objectIterator.next();
/*  82 */       actions.motionControllerChanged(ref, npcComponent, motionController, componentAccessor); }
/*     */   
/*     */   }
/*     */   
/*     */   public void loaded(Role role) {
/*  87 */     for (ObjectIterator<IActionListHolder> objectIterator = this.stateTransitionActions.values().iterator(); objectIterator.hasNext(); ) { IActionListHolder actions = objectIterator.next();
/*  88 */       actions.loaded(role); }
/*     */   
/*     */   }
/*     */   
/*     */   public void spawned(Role role) {
/*  93 */     for (ObjectIterator<IActionListHolder> objectIterator = this.stateTransitionActions.values().iterator(); objectIterator.hasNext(); ) { IActionListHolder actions = objectIterator.next();
/*  94 */       actions.spawned(role); }
/*     */   
/*     */   }
/*     */   
/*     */   public void unloaded(Role role) {
/*  99 */     for (ObjectIterator<IActionListHolder> objectIterator = this.stateTransitionActions.values().iterator(); objectIterator.hasNext(); ) { IActionListHolder actions = objectIterator.next();
/* 100 */       actions.unloaded(role); }
/*     */   
/*     */   }
/*     */   
/*     */   public void removed(Role role) {
/* 105 */     for (ObjectIterator<IActionListHolder> objectIterator = this.stateTransitionActions.values().iterator(); objectIterator.hasNext(); ) { IActionListHolder actions = objectIterator.next();
/* 106 */       actions.removed(role); }
/*     */   
/*     */   }
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 111 */     for (ObjectIterator<IActionListHolder> objectIterator = this.stateTransitionActions.values().iterator(); objectIterator.hasNext(); ) { IActionListHolder actions = objectIterator.next();
/* 112 */       actions.teleported(role, from, to); }
/*     */   
/*     */   }
/*     */   
/*     */   public void clearOnce() {
/* 117 */     for (ObjectIterator<IActionListHolder> objectIterator = this.stateTransitionActions.values().iterator(); objectIterator.hasNext(); ) { IActionListHolder actions = objectIterator.next();
/* 118 */       actions.clearOnce(); }
/*     */   
/*     */   }
/*     */   
/*     */   public void initiateStateTransition(int fromState, int toState) {
/* 123 */     this.runningActions = (IActionListHolder)this.stateTransitionActions.get(indexStateTransitionEdge(fromState, toState));
/*     */   }
/*     */   
/*     */   public boolean isRunningTransitionActions() {
/* 127 */     return (this.runningActions != null);
/*     */   }
/*     */   
/*     */   public boolean runTransitionActions(Ref<EntityStore> ref, Role role, double dt, Store<EntityStore> store) {
/* 131 */     if (this.runningActions == null) return false;
/*     */     
/* 133 */     if (this.runningActions.canExecute(ref, role, (InfoProvider)null, dt, store) && 
/* 134 */       this.runningActions.execute(ref, role, (InfoProvider)null, dt, store) && this.runningActions.hasCompletedRun()) {
/* 135 */       this.runningActions.clearOnce();
/* 136 */       this.runningActions = null;
/* 137 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 141 */     return true;
/*     */   }
/*     */   
/*     */   public static void registerFactories(@Nonnull BuilderManager builderManager) {
/* 145 */     BuilderFactory<StateTransitionController> transitionControllerFactory = new BuilderFactory(StateTransitionController.class, "Type", BuilderStateTransitionController::new);
/* 146 */     builderManager.registerFactory(transitionControllerFactory);
/*     */     
/* 148 */     BuilderFactory<BuilderStateTransition.StateTransition> transitionEntryFactory = new BuilderFactory(BuilderStateTransition.StateTransition.class, "Type", BuilderStateTransition::new);
/* 149 */     builderManager.registerFactory(transitionEntryFactory);
/*     */     
/* 151 */     BuilderFactory<BuilderStateTransitionEdges.StateTransitionEdges> transitionFactory = new BuilderFactory(BuilderStateTransitionEdges.StateTransitionEdges.class, "Type", BuilderStateTransitionEdges::new);
/* 152 */     builderManager.registerFactory(transitionFactory);
/*     */   }
/*     */   
/*     */   public static int indexStateTransitionEdge(int from, int to) {
/* 156 */     return (from << 16) + to;
/*     */   }
/*     */   private static interface IActionListHolder extends RoleStateChange {
/*     */     boolean canExecute(Ref<EntityStore> param1Ref, Role param1Role, InfoProvider param1InfoProvider, double param1Double, Store<EntityStore> param1Store);
/*     */     boolean execute(Ref<EntityStore> param1Ref, Role param1Role, InfoProvider param1InfoProvider, double param1Double, Store<EntityStore> param1Store);
/*     */     
/*     */     boolean hasCompletedRun();
/*     */     
/*     */     void clearOnce(); }
/*     */   
/*     */   private static final class PrioritisedActionList extends Record implements IActionListHolder { private final int priority;
/*     */     private final ActionList actionList;
/*     */     
/* 169 */     private PrioritisedActionList(int priority, ActionList actionList) { this.priority = priority; this.actionList = actionList; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #169	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 169 */       //   0	7	0	this	Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList; } public int priority() { return this.priority; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #169	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #169	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/npc/statetransition/StateTransitionController$PrioritisedActionList;
/* 169 */       //   0	8	1	o	Ljava/lang/Object; } public ActionList actionList() { return this.actionList; }
/*     */ 
/*     */     
/*     */     public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 173 */       return this.actionList.canExecute(ref, role, sensorInfo, dt, store);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 178 */       return this.actionList.execute(ref, role, sensorInfo, dt, store);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasCompletedRun() {
/* 183 */       return this.actionList.hasCompletedRun();
/*     */     }
/*     */ 
/*     */     
/*     */     public void registerWithSupport(Role role) {
/* 188 */       this.actionList.registerWithSupport(role);
/*     */     }
/*     */ 
/*     */     
/*     */     public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 193 */       this.actionList.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */     }
/*     */ 
/*     */     
/*     */     public void loaded(Role role) {
/* 198 */       this.actionList.loaded(role);
/*     */     }
/*     */ 
/*     */     
/*     */     public void spawned(Role role) {
/* 203 */       this.actionList.spawned(role);
/*     */     }
/*     */ 
/*     */     
/*     */     public void unloaded(Role role) {
/* 208 */       this.actionList.unloaded(role);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removed(Role role) {
/* 213 */       this.actionList.removed(role);
/*     */     }
/*     */ 
/*     */     
/*     */     public void teleported(Role role, World from, World to) {
/* 218 */       this.actionList.teleported(role, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clearOnce() {
/* 223 */       this.actionList.clearOnce();
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class CompositeActionList implements IActionListHolder {
/* 228 */     private final List<StateTransitionController.PrioritisedActionList> actionLists = (List<StateTransitionController.PrioritisedActionList>)new ObjectArrayList();
/*     */     
/*     */     private int currentIndex;
/*     */     
/*     */     private CompositeActionList(StateTransitionController.PrioritisedActionList initialActionList) {
/* 233 */       this.actionLists.add(initialActionList);
/*     */     }
/*     */     
/*     */     private void addActionList(int priority, ActionList actionList) {
/* 237 */       for (int i = 0; i < this.actionLists.size(); i++) {
/* 238 */         if (priority > ((StateTransitionController.PrioritisedActionList)this.actionLists.get(i)).priority) {
/* 239 */           this.actionLists.add(i, new StateTransitionController.PrioritisedActionList(priority, actionList));
/*     */           return;
/*     */         } 
/*     */       } 
/* 243 */       this.actionLists.add(new StateTransitionController.PrioritisedActionList(priority, actionList));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 248 */       if (this.currentIndex >= this.actionLists.size()) this.currentIndex = 0; 
/* 249 */       return ((StateTransitionController.PrioritisedActionList)this.actionLists.get(this.currentIndex)).actionList.canExecute(ref, role, sensorInfo, dt, store);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 254 */       StateTransitionController.PrioritisedActionList actionList = this.actionLists.get(this.currentIndex);
/* 255 */       if (!actionList.actionList.canExecute(ref, role, sensorInfo, dt, store)) return false;
/*     */       
/* 257 */       if (actionList.actionList.execute(ref, role, sensorInfo, dt, store) && actionList.actionList.hasCompletedRun()) {
/* 258 */         this.currentIndex++;
/* 259 */         return true;
/*     */       } 
/*     */       
/* 262 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasCompletedRun() {
/* 267 */       if (this.currentIndex >= this.actionLists.size()) {
/* 268 */         this.currentIndex = 0;
/* 269 */         return true;
/*     */       } 
/* 271 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void registerWithSupport(Role role) {
/* 276 */       for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 277 */         actionList.actionList.registerWithSupport(role);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 283 */       for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 284 */         actionList.actionList.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void loaded(Role role) {
/* 290 */       for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 291 */         actionList.actionList.loaded(role);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void spawned(Role role) {
/* 297 */       for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 298 */         actionList.actionList.spawned(role);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void unloaded(Role role) {
/* 304 */       for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 305 */         actionList.actionList.unloaded(role);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void removed(Role role) {
/* 311 */       for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 312 */         actionList.actionList.removed(role);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void teleported(Role role, World from, World to) {
/* 318 */       for (StateTransitionController.PrioritisedActionList actionList : this.actionLists) {
/* 319 */         actionList.actionList.teleported(role, from, to);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void clearOnce() {
/* 325 */       for (StateTransitionController.PrioritisedActionList actionList : this.actionLists)
/* 326 */         actionList.actionList.clearOnce(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\statetransition\StateTransitionController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */