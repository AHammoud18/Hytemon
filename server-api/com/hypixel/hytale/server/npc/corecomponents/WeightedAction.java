/*     */ package com.hypixel.hytale.server.npc.corecomponents;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderWeightedAction;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Action;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.ComponentInfo;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WeightedAction
/*     */   extends AnnotatedComponentBase implements Action {
/*     */   @Nullable
/*     */   private final Action action;
/*     */   private final double weight;
/*     */   
/*     */   public WeightedAction(@Nonnull BuilderWeightedAction builder, @Nonnull BuilderSupport support) {
/*  26 */     this.action = builder.getAction(support);
/*  27 */     this.weight = builder.getWeight(support);
/*     */   }
/*     */   
/*     */   public double getWeight() {
/*  31 */     return this.weight;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  36 */     return this.action.canExecute(ref, role, sensorInfo, dt, store);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  41 */     return this.action.execute(ref, role, sensorInfo, dt, store);
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate(Role role, InfoProvider infoProvider) {
/*  46 */     this.action.activate(role, infoProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate(Role role, InfoProvider infoProvider) {
/*  51 */     this.action.deactivate(role, infoProvider);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActivated() {
/*  56 */     return this.action.isActivated();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getInfo(Role role, ComponentInfo holder) {
/*  61 */     this.action.getInfo(role, holder);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processDelay(float dt) {
/*  66 */     return this.action.processDelay(dt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearOnce() {
/*  71 */     this.action.clearOnce();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnce() {
/*  76 */     this.action.setOnce();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTriggered() {
/*  81 */     return this.action.isTriggered();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  86 */     this.action.registerWithSupport(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  91 */     this.action.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/*  96 */     this.action.loaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/* 101 */     this.action.spawned(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/* 106 */     this.action.unloaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/* 111 */     this.action.removed(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 116 */     this.action.teleported(role, from, to);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\WeightedAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */