/*     */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.MotionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderMotionSequence;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Motion;
/*     */ import com.hypixel.hytale.server.npc.movement.Steering;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class MotionSequence<T extends Motion>
/*     */   extends MotionBase
/*     */   implements IAnnotatedComponentCollection
/*     */ {
/*     */   protected final boolean looped;
/*     */   protected final boolean restartOnActivate;
/*     */   protected final T[] steps;
/*     */   protected boolean finished;
/*     */   protected int index;
/*     */   @Nullable
/*     */   protected T activeMotion;
/*     */   
/*     */   public MotionSequence(@Nonnull BuilderMotionSequence<T> builder, T[] steps) {
/*  34 */     restart();
/*  35 */     this.looped = builder.isLooped();
/*  36 */     this.restartOnActivate = builder.isRestartOnActivate();
/*  37 */     this.steps = steps;
/*     */   }
/*     */ 
/*     */   
/*     */   public void activate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  42 */     if (this.restartOnActivate) {
/*  43 */       deactivate(ref, role, componentAccessor);
/*  44 */       restart();
/*     */     } 
/*  46 */     if (this.finished) {
/*     */       return;
/*     */     }
/*  49 */     doActivate(ref, role, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  54 */     if (this.activeMotion != null) {
/*  55 */       this.activeMotion.deactivate(ref, role, componentAccessor);
/*  56 */       this.activeMotion = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  62 */     if (this.finished) {
/*  63 */       desiredSteering.clear();
/*  64 */       return false;
/*     */     } 
/*     */     
/*  67 */     T currentActiveMotion = this.activeMotion;
/*     */     
/*     */     do {
/*  70 */       Objects.requireNonNull(this.activeMotion, "Active motion not set");
/*  71 */       if (this.activeMotion.computeSteering(ref, role, sensorInfo, dt, desiredSteering, componentAccessor)) {
/*  72 */         return true;
/*     */       }
/*  74 */       if (this.index + 1 < this.steps.length) {
/*  75 */         activateNext(ref, this.index + 1, role, componentAccessor);
/*  76 */       } else if (this.looped) {
/*  77 */         activateNext(ref, 0, role, componentAccessor);
/*     */       } else {
/*     */         break;
/*     */       } 
/*  81 */     } while (this.activeMotion != currentActiveMotion);
/*     */     
/*  83 */     deactivate(ref, role, componentAccessor);
/*  84 */     this.finished = true;
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  90 */     for (T step : this.steps) {
/*  91 */       step.registerWithSupport(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  97 */     for (T step : this.steps) {
/*  98 */       step.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/* 104 */     for (T step : this.steps) {
/* 105 */       step.loaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/* 111 */     for (T step : this.steps) {
/* 112 */       step.spawned(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/* 118 */     for (T step : this.steps) {
/* 119 */       step.unloaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/* 125 */     for (T step : this.steps) {
/* 126 */       step.removed(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 132 */     for (T step : this.steps) {
/* 133 */       step.teleported(role, from, to);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/* 139 */     return this.steps.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 144 */     return (IAnnotatedComponent)this.steps[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/* 149 */     for (int i = 0; i < this.steps.length; i++) {
/* 150 */       this.steps[i].setContext(parent, i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void restart() {
/* 155 */     this.index = 0;
/* 156 */     this.finished = false;
/*     */   }
/*     */   
/*     */   protected void doActivate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 160 */     if (this.steps.length == 0) throw new IllegalArgumentException("Motion sequence must have steps!"); 
/* 161 */     if (this.index < 0 || this.index >= this.steps.length)
/* 162 */       throw new IndexOutOfBoundsException(String.format("Motion sequence index out of range (%s) must be less than size (%s)", new Object[] {
/* 163 */               Integer.valueOf(this.index), Integer.valueOf(this.steps.length)
/*     */             })); 
/* 165 */     this.activeMotion = this.steps[this.index];
/* 166 */     Objects.requireNonNull(this.activeMotion, "Active motion must not be null");
/* 167 */     this.activeMotion.activate(ref, role, componentAccessor);
/*     */   }
/*     */   
/*     */   protected void activateNext(@Nonnull Ref<EntityStore> ref, int newIndex, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 171 */     this.activeMotion.deactivate(ref, role, componentAccessor);
/* 172 */     this.index = newIndex;
/* 173 */     doActivate(ref, role, componentAccessor);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\MotionSequence.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */