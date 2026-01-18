/*     */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.WeightedAction;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionRandom;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ActionRandom
/*     */   extends ActionBase
/*     */ {
/*     */   @Nonnull
/*     */   protected final WeightedAction[] actions;
/*     */   @Nonnull
/*     */   protected final WeightedAction[] availableActions;
/*     */   protected int availableActionsCount;
/*     */   protected double totalWeight;
/*     */   @Nullable
/*     */   protected WeightedAction current;
/*     */   
/*     */   public ActionRandom(@Nonnull BuilderActionRandom builder, @Nonnull BuilderSupport support) {
/*  34 */     super((BuilderActionBase)builder);
/*  35 */     this.actions = (WeightedAction[])builder.getActions(support).toArray(x$0 -> new WeightedAction[x$0]);
/*  36 */     for (WeightedAction action : this.actions) {
/*  37 */       if (action == null) throw new IllegalArgumentException("WeightedAction in Random actions list can't be null"); 
/*     */     } 
/*  39 */     this.availableActions = new WeightedAction[this.actions.length];
/*  40 */     this.availableActionsCount = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  45 */     int length = this.actions.length;
/*  46 */     if (!super.canExecute(ref, role, sensorInfo, dt, store) || length == 0) return false;
/*     */     
/*  48 */     if (this.current != null) return this.current.canExecute(ref, role, sensorInfo, dt, store);
/*     */     
/*  50 */     this.availableActionsCount = 0;
/*  51 */     this.totalWeight = 0.0D;
/*  52 */     for (WeightedAction action : this.actions) {
/*  53 */       if (action.canExecute(ref, role, sensorInfo, dt, store)) {
/*  54 */         this.availableActions[this.availableActionsCount++] = action;
/*  55 */         this.totalWeight += action.getWeight();
/*     */       } 
/*     */     } 
/*  58 */     return (this.availableActionsCount > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  63 */     super.execute(ref, role, sensorInfo, dt, store);
/*     */     
/*  65 */     if (this.availableActionsCount == 0) return true;
/*     */     
/*  67 */     if (this.current == null) {
/*  68 */       this.current = this.availableActions[0];
/*  69 */       this.totalWeight *= ThreadLocalRandom.current().nextDouble();
/*  70 */       this.totalWeight -= this.current.getWeight();
/*     */       
/*  72 */       for (int i = 1; i < this.availableActionsCount && this.totalWeight >= 0.0D; i++) {
/*  73 */         this.current = this.availableActions[i];
/*  74 */         this.totalWeight -= this.current.getWeight();
/*     */       } 
/*  76 */       this.current.activate(role, sensorInfo);
/*     */     } 
/*     */     
/*  79 */     boolean finished = this.current.execute(ref, role, sensorInfo, dt, store);
/*  80 */     if (finished) {
/*  81 */       this.current.clearOnce();
/*  82 */       this.current.deactivate(role, sensorInfo);
/*  83 */       this.current = null;
/*     */     } 
/*     */     
/*  86 */     return finished;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  91 */     for (WeightedAction action : this.actions) {
/*  92 */       action.registerWithSupport(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  98 */     for (WeightedAction action : this.actions) {
/*  99 */       action.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/* 105 */     for (WeightedAction action : this.actions) {
/* 106 */       action.loaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/* 112 */     for (WeightedAction action : this.actions) {
/* 113 */       action.spawned(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/* 119 */     for (WeightedAction action : this.actions) {
/* 120 */       action.unloaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/* 126 */     for (WeightedAction action : this.actions) {
/* 127 */       action.removed(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 133 */     for (WeightedAction action : this.actions)
/* 134 */       action.teleported(role, from, to); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\ActionRandom.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */