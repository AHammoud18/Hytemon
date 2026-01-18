/*     */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorBlockType;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.IPositionProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SensorBlockType extends SensorBase implements IAnnotatedComponentCollection {
/*     */   protected final Sensor sensor;
/*     */   
/*     */   public SensorBlockType(@Nonnull BuilderSensorBlockType builder, @Nonnull BuilderSupport support, Sensor sensor) {
/*  30 */     super((BuilderSensorBase)builder);
/*  31 */     this.sensor = sensor;
/*  32 */     this.blockSet = builder.getBlockSet(support);
/*     */   }
/*     */   protected final int blockSet;
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*  37 */     if (!super.matches(ref, role, dt, store) || !this.sensor.matches(ref, role, dt, store)) return false;
/*     */     
/*  39 */     InfoProvider sensorInfo = this.sensor.getSensorInfo();
/*  40 */     if (sensorInfo == null) return false;
/*     */     
/*  42 */     IPositionProvider positionProvider = sensorInfo.getPositionProvider();
/*  43 */     int x = MathUtil.floor(positionProvider.getX());
/*  44 */     int y = MathUtil.floor(positionProvider.getY());
/*  45 */     int z = MathUtil.floor(positionProvider.getZ());
/*     */     
/*  47 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  49 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/*  50 */     if (chunk == null) {
/*  51 */       positionProvider.clear();
/*  52 */       return false;
/*     */     } 
/*     */     
/*  55 */     int blockId = chunk.getBlock(x, y, z);
/*  56 */     if (!BlockSetModule.getInstance().blockInSet(this.blockSet, blockId)) {
/*  57 */       positionProvider.clear();
/*  58 */       return false;
/*     */     } 
/*     */     
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/*  66 */     return this.sensor.getSensorInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  71 */     this.sensor.registerWithSupport(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  76 */     this.sensor.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/*  81 */     this.sensor.loaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/*  86 */     this.sensor.spawned(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/*  91 */     this.sensor.unloaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/*  96 */     this.sensor.removed(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 101 */     this.sensor.teleported(role, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/* 106 */     this.sensor.done();
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/* 111 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 116 */     if (index >= componentCount()) throw new IndexOutOfBoundsException(); 
/* 117 */     return (IAnnotatedComponent)this.sensor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/* 122 */     super.setContext(parent, index);
/* 123 */     this.sensor.setContext((IAnnotatedComponent)this, index);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorBlockType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */