/*     */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ActionWithDelay;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionWithDelay;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionTimeout;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Action;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ActionTimeout extends ActionWithDelay implements IAnnotatedComponentCollection {
/*     */   protected final boolean delayAfter;
/*     */   @Nullable
/*     */   protected final Action action;
/*     */   
/*     */   public ActionTimeout(@Nonnull BuilderActionTimeout builderActionTimeout, @Nonnull BuilderSupport builderSupport) {
/*  28 */     super((BuilderActionWithDelay)builderActionTimeout, builderSupport);
/*  29 */     this.action = builderActionTimeout.getAction(builderSupport);
/*  30 */     this.delayAfter = builderActionTimeout.isDelayAfter();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  35 */     if (!super.canExecute(ref, role, sensorInfo, dt, store) || (this.action != null && !this.action.canExecute(ref, role, sensorInfo, dt, store))) {
/*  36 */       return false;
/*     */     }
/*     */     
/*  39 */     if (!isDelaying() && isDelayPrepared()) {
/*  40 */       startDelay(role.getEntitySupport());
/*     */     }
/*     */     
/*  43 */     return !isDelaying();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*  48 */     super.execute(ref, role, sensorInfo, dt, store);
/*  49 */     if (this.action != null) this.action.execute(ref, role, sensorInfo, dt, store); 
/*  50 */     prepareDelay();
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  56 */     if (this.action != null) this.action.registerWithSupport(role); 
/*  57 */     if (this.delayAfter) {
/*  58 */       clearDelay();
/*     */     } else {
/*  60 */       prepareDelay();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  66 */     if (this.action != null) this.action.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   
/*     */   }
/*     */   
/*     */   public void loaded(Role role) {
/*  71 */     if (this.action != null) this.action.loaded(role);
/*     */   
/*     */   }
/*     */   
/*     */   public void spawned(Role role) {
/*  76 */     if (this.action != null) this.action.spawned(role);
/*     */   
/*     */   }
/*     */   
/*     */   public void unloaded(Role role) {
/*  81 */     if (this.action != null) this.action.unloaded(role);
/*     */   
/*     */   }
/*     */   
/*     */   public void removed(Role role) {
/*  86 */     if (this.action != null) this.action.removed(role);
/*     */   
/*     */   }
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/*  91 */     if (this.action != null) this.action.teleported(role, from, to);
/*     */   
/*     */   }
/*     */   
/*     */   public void clearOnce() {
/*  96 */     super.clearOnce();
/*  97 */     if (this.delayAfter) {
/*  98 */       clearDelay();
/*     */     } else {
/* 100 */       prepareDelay();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/* 106 */     return (this.action != null) ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 112 */     if (index >= componentCount()) throw new IndexOutOfBoundsException(); 
/* 113 */     return (IAnnotatedComponent)this.action;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\ActionTimeout.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */