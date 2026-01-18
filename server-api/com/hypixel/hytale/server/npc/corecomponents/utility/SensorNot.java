/*     */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorNot;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.support.DebugSupport;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.EntityPositionProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SensorNot extends SensorBase implements IAnnotatedComponentCollection {
/*     */   protected final Sensor sensor;
/*     */   protected final int targetSlot;
/*     */   protected final int autoUnlockTargetSlot;
/*  28 */   protected final EntityPositionProvider positionProvider = new EntityPositionProvider();
/*     */   
/*     */   public SensorNot(@Nonnull BuilderSensorNot builder, @Nonnull BuilderSupport support, Sensor sensor) {
/*  31 */     super((BuilderSensorBase)builder);
/*  32 */     this.sensor = sensor;
/*  33 */     this.targetSlot = builder.getUsedTargetSlot(support);
/*  34 */     this.autoUnlockTargetSlot = builder.getAutoUnlockTargetSlot(support);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*  39 */     if (!super.matches(ref, role, dt, store) || this.sensor.matches(ref, role, dt, store)) {
/*  40 */       this.positionProvider.clear();
/*  41 */       if (this.autoUnlockTargetSlot >= 0) role.getMarkedEntitySupport().clearMarkedEntity(this.autoUnlockTargetSlot); 
/*  42 */       DebugSupport debugSupport = role.getDebugSupport();
/*  43 */       if (debugSupport.isTraceSensorFails()) debugSupport.setLastFailingSensor(this.sensor); 
/*  44 */       return false;
/*     */     } 
/*     */     
/*  47 */     Ref<EntityStore> targetRef = (this.targetSlot >= 0) ? role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot) : null;
/*  48 */     this.positionProvider.setTarget(targetRef, (ComponentAccessor)store);
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/*  54 */     return (InfoProvider)this.positionProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  59 */     this.sensor.registerWithSupport(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  64 */     this.sensor.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/*  69 */     this.sensor.loaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/*  74 */     this.sensor.spawned(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/*  79 */     this.sensor.unloaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/*  84 */     this.sensor.removed(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/*  89 */     this.sensor.teleported(role, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/*  94 */     this.sensor.done();
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/*  99 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 104 */     if (index >= componentCount()) throw new IndexOutOfBoundsException(); 
/* 105 */     return (IAnnotatedComponent)this.sensor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/* 110 */     super.setContext(parent, index);
/* 111 */     this.sensor.setContext((IAnnotatedComponent)this, index);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorNot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */