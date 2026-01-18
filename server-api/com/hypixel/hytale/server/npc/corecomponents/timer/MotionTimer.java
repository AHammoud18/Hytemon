/*    */ package com.hypixel.hytale.server.npc.corecomponents.timer;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.random.RandomExtra;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.MotionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderMotionTimer;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.instructions.Motion;
/*    */ import com.hypixel.hytale.server.npc.movement.Steering;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class MotionTimer<T extends Motion>
/*    */   extends MotionBase
/*    */ {
/*    */   protected final T motion;
/*    */   protected final double atLeastSeconds;
/*    */   protected final double atMostSeconds;
/*    */   protected double activeTime;
/*    */   protected double timeToLive;
/*    */   
/*    */   public MotionTimer(@Nonnull BuilderMotionTimer<T> builder, @Nonnull BuilderSupport builderSupport, T motion) {
/* 30 */     double[] timerRange = builder.getTimerRange(builderSupport);
/* 31 */     this.atLeastSeconds = timerRange[0];
/* 32 */     this.atMostSeconds = timerRange[1];
/* 33 */     this.motion = motion;
/*    */   }
/*    */ 
/*    */   
/*    */   public void activate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 38 */     this.activeTime = 0.0D;
/* 39 */     this.timeToLive = RandomExtra.randomRange(this.atLeastSeconds, this.atMostSeconds);
/* 40 */     this.motion.activate(ref, role, componentAccessor);
/*    */   }
/*    */ 
/*    */   
/*    */   public void deactivate(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 45 */     this.motion.deactivate(ref, role, componentAccessor);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean computeSteering(@Nonnull Ref<EntityStore> ref, @Nonnull Role support, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Steering desiredSteering, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 50 */     if (this.activeTime >= this.timeToLive) {
/* 51 */       return false;
/*    */     }
/*    */     
/* 54 */     this.activeTime += dt;
/*    */     
/* 56 */     if (!this.motion.computeSteering(ref, support, sensorInfo, dt, desiredSteering, componentAccessor)) {
/* 57 */       this.activeTime = this.timeToLive;
/* 58 */       return false;
/*    */     } 
/*    */     
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerWithSupport(Role role) {
/* 66 */     this.motion.registerWithSupport(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 71 */     this.motion.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loaded(Role role) {
/* 76 */     this.motion.loaded(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void spawned(Role role) {
/* 81 */     this.motion.spawned(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unloaded(Role role) {
/* 86 */     this.motion.unloaded(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removed(Role role) {
/* 91 */     this.motion.removed(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void teleported(Role role, World from, World to) {
/* 96 */     this.motion.teleported(role, from, to);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\timer\MotionTimer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */