/*     */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionSequence;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.ActionList;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ActionSequence extends ActionBase implements IAnnotatedComponentCollection {
/*     */   @Nonnull
/*     */   protected final ActionList actions;
/*     */   
/*     */   public ActionSequence(@Nonnull BuilderActionSequence builder, @Nonnull BuilderSupport support) {
/*  27 */     super((BuilderActionBase)builder);
/*  28 */     this.actions = builder.getActionList(support);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  33 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && this.actions.canExecute(ref, role, sensorInfo, dt, store));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  38 */     super.execute(ref, role, sensorInfo, dt, store);
/*  39 */     return this.actions.execute(ref, role, sensorInfo, dt, store);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  44 */     this.actions.registerWithSupport(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  49 */     this.actions.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/*  54 */     this.actions.loaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/*  59 */     this.actions.spawned(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/*  64 */     this.actions.unloaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/*  69 */     this.actions.removed(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/*  74 */     this.actions.teleported(role, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearOnce() {
/*  79 */     super.clearOnce();
/*  80 */     this.actions.clearOnce();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnce() {
/*  85 */     super.setOnce();
/*  86 */     this.actions.setOnce();
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/*  91 */     return this.actions.actionCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/*  96 */     return this.actions.getComponent(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/* 101 */     super.setContext(parent, index);
/* 102 */     this.actions.setContext(parent);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\ActionSequence.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */