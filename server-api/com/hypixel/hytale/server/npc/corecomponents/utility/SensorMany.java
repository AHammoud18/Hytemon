/*     */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorMany;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.WrappedInfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class SensorMany
/*     */   extends SensorBase implements IAnnotatedComponentCollection {
/*     */   @Nonnull
/*     */   protected final Sensor[] sensors;
/*     */   protected final int autoUnlockTargetSlot;
/*     */   protected final WrappedInfoProvider infoProvider;
/*     */   
/*     */   public SensorMany(@Nonnull BuilderSensorMany builder, @Nonnull BuilderSupport support, @Nonnull List<Sensor> sensors) {
/*  31 */     super((BuilderSensorBase)builder);
/*  32 */     if (sensors == null) throw new IllegalArgumentException("Sensor list can't be null"); 
/*  33 */     this.sensors = (Sensor[])sensors.toArray(x$0 -> new Sensor[x$0]);
/*  34 */     for (Sensor sensor : this.sensors) {
/*  35 */       if (sensor == null) throw new IllegalArgumentException("Sensor in sensor list can't be null"); 
/*     */     } 
/*  37 */     this.autoUnlockTargetSlot = builder.getAutoUnlockedTargetSlot(support);
/*  38 */     this.infoProvider = createInfoProvider();
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/*  43 */     for (Sensor s : this.sensors) s.done();
/*     */   
/*     */   }
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  48 */     for (Sensor sensor : this.sensors) {
/*  49 */       sensor.registerWithSupport(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  55 */     for (Sensor sensor : this.sensors) {
/*  56 */       sensor.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/*  62 */     for (Sensor sensor : this.sensors) {
/*  63 */       sensor.loaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/*  69 */     for (Sensor sensor : this.sensors) {
/*  70 */       sensor.spawned(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/*  76 */     for (Sensor sensor : this.sensors) {
/*  77 */       sensor.unloaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/*  83 */     for (Sensor sensor : this.sensors) {
/*  84 */       sensor.removed(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/*  90 */     for (Sensor sensor : this.sensors) {
/*  91 */       sensor.teleported(role, from, to);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/*  97 */     return (InfoProvider)this.infoProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/* 102 */     return this.sensors.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 107 */     return (IAnnotatedComponent)this.sensors[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/* 112 */     super.setContext(parent, index);
/* 113 */     for (int i = 0; i < this.sensors.length; i++)
/* 114 */       this.sensors[i].setContext((IAnnotatedComponent)this, i); 
/*     */   }
/*     */   
/*     */   protected abstract WrappedInfoProvider createInfoProvider();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorMany.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */