/*     */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorAdjustPosition;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.support.DebugSupport;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.IPositionProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.PositionProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SensorAdjustPosition
/*     */   extends SensorBase
/*     */   implements IAnnotatedComponentCollection {
/*     */   protected final Sensor sensor;
/*     */   @Nonnull
/*     */   protected final Vector3d offset;
/*  32 */   protected final PositionProvider positionProvider = new PositionProvider();
/*     */   
/*     */   public SensorAdjustPosition(@Nonnull BuilderSensorAdjustPosition builder, @Nonnull BuilderSupport support, Sensor sensor) {
/*  35 */     super((BuilderSensorBase)builder);
/*  36 */     this.sensor = sensor;
/*  37 */     this.offset = builder.getOffset(support);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*  42 */     if (!super.matches(ref, role, dt, store) || !this.sensor.matches(ref, role, dt, store)) {
/*  43 */       this.positionProvider.clear();
/*  44 */       DebugSupport debugSupport = role.getDebugSupport();
/*  45 */       if (debugSupport.isTraceSensorFails()) debugSupport.setLastFailingSensor(this.sensor); 
/*  46 */       return false;
/*     */     } 
/*     */     
/*  49 */     InfoProvider sensorInfo = this.sensor.getSensorInfo();
/*  50 */     if (sensorInfo == null) return false;
/*     */     
/*  52 */     IPositionProvider otherPositionProvider = sensorInfo.getPositionProvider();
/*  53 */     double x = otherPositionProvider.getX() + this.offset.x;
/*  54 */     double y = otherPositionProvider.getY() + this.offset.y;
/*  55 */     double z = otherPositionProvider.getZ() + this.offset.z;
/*  56 */     this.positionProvider.setTarget(x, y, z);
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/*  62 */     return (InfoProvider)this.positionProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  67 */     this.sensor.registerWithSupport(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  72 */     this.sensor.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/*  77 */     this.sensor.loaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/*  82 */     this.sensor.spawned(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/*  87 */     this.sensor.unloaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/*  92 */     this.sensor.removed(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/*  97 */     this.sensor.teleported(role, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/* 102 */     this.sensor.done();
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/* 107 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 112 */     if (index >= componentCount()) throw new IndexOutOfBoundsException(); 
/* 113 */     return (IAnnotatedComponent)this.sensor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/* 118 */     super.setContext(parent, index);
/* 119 */     this.sensor.setContext((IAnnotatedComponent)this, index);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorAdjustPosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */