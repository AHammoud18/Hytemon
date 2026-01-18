/*     */ package com.hypixel.hytale.server.npc.instructions;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ActionList
/*     */ {
/*  19 */   public static final ActionList EMPTY_ACTION_LIST = new ActionList(Action.EMPTY_ARRAY);
/*     */   
/*     */   @Nonnull
/*     */   protected final Action[] actions;
/*     */   
/*     */   protected boolean blocking;
/*     */   protected boolean atomic;
/*     */   protected int actionIndex;
/*     */   
/*     */   public ActionList(@Nonnull Action[] actions) {
/*  29 */     this.actions = actions;
/*  30 */     Objects.requireNonNull(actions, "Action array in sequence must not be null");
/*  31 */     for (Action action : actions) {
/*  32 */       Objects.requireNonNull(action, "Action in sequence can't be null");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBlocking(boolean blocking) {
/*  37 */     this.blocking = blocking;
/*     */   }
/*     */   
/*     */   public void setAtomic(boolean atomic) {
/*  41 */     this.atomic = atomic;
/*     */   }
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  45 */     if (this.actions.length == 0) return false;
/*     */     
/*  47 */     if (this.blocking) {
/*  48 */       if (this.actionIndex >= this.actions.length) this.actionIndex = 0; 
/*  49 */       return this.actions[this.actionIndex].canExecute(ref, role, sensorInfo, dt, store);
/*     */     } 
/*     */     
/*  52 */     for (Action action : this.actions) {
/*  53 */       if (action.canExecute(ref, role, sensorInfo, dt, store)) {
/*  54 */         if (!this.atomic) return true; 
/*  55 */       } else if (this.atomic) {
/*  56 */         return false;
/*     */       } 
/*     */     } 
/*  59 */     return this.atomic;
/*     */   }
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  63 */     if (this.blocking) {
/*  64 */       Action action = this.actions[this.actionIndex];
/*  65 */       if (!action.canExecute(ref, role, sensorInfo, dt, store)) return false;
/*     */       
/*  67 */       if (!action.isActivated()) action.activate(role, sensorInfo); 
/*  68 */       if (!action.execute(ref, role, sensorInfo, dt, store)) return false;
/*     */       
/*  70 */       action.deactivate(role, sensorInfo);
/*  71 */       this.actionIndex++;
/*  72 */       return (this.actionIndex >= this.actions.length);
/*     */     } 
/*     */     
/*  75 */     for (Action action : this.actions) {
/*  76 */       if (action.canExecute(ref, role, sensorInfo, dt, store)) {
/*  77 */         if (!action.isActivated()) action.activate(role, sensorInfo); 
/*  78 */         action.execute(ref, role, sensorInfo, dt, store);
/*  79 */       } else if (action.isActivated()) {
/*  80 */         action.deactivate(role, sensorInfo);
/*     */       } 
/*     */     } 
/*  83 */     return true;
/*     */   }
/*     */   
/*     */   public boolean hasCompletedRun() {
/*  87 */     if (this.actionIndex >= this.actions.length) {
/*  88 */       this.actionIndex = 0;
/*  89 */       return true;
/*     */     } 
/*  91 */     return false;
/*     */   }
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent) {
/*  95 */     for (int i = 0; i < this.actions.length; i++) {
/*  96 */       this.actions[i].setContext(parent, i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerWithSupport(Role role) {
/* 101 */     for (Action action : this.actions) {
/* 102 */       action.registerWithSupport(role);
/*     */     }
/*     */   }
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 107 */     for (Action action : this.actions) {
/* 108 */       action.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loaded(Role role) {
/* 113 */     for (Action action : this.actions) {
/* 114 */       action.loaded(role);
/*     */     }
/*     */   }
/*     */   
/*     */   public void spawned(Role role) {
/* 119 */     for (Action action : this.actions) {
/* 120 */       action.spawned(role);
/*     */     }
/*     */   }
/*     */   
/*     */   public void unloaded(Role role) {
/* 125 */     for (Action action : this.actions) {
/* 126 */       action.unloaded(role);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removed(Role role) {
/* 131 */     for (Action action : this.actions) {
/* 132 */       action.removed(role);
/*     */     }
/*     */   }
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 137 */     for (Action action : this.actions) {
/* 138 */       action.teleported(role, from, to);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearOnce() {
/* 143 */     for (Action action : this.actions) {
/* 144 */       action.clearOnce();
/*     */     }
/* 146 */     this.actionIndex = 0;
/*     */   }
/*     */   
/*     */   public void onEndMotion() {
/* 150 */     if (!this.blocking) clearOnce(); 
/*     */   }
/*     */   
/*     */   public void setOnce() {
/* 154 */     for (Action action : this.actions) {
/* 155 */       action.setOnce();
/*     */     }
/*     */   }
/*     */   
/*     */   public int actionCount() {
/* 160 */     return this.actions.length;
/*     */   }
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 164 */     return this.actions[index];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\instructions\ActionList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */