/*     */ package com.hypixel.hytale.server.npc.corecomponents;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SensorWithEntityFilters
/*     */   extends SensorBase
/*     */   implements IAnnotatedComponentCollection
/*     */ {
/*     */   @Nonnull
/*     */   private final IEntityFilter[] filters;
/*     */   
/*     */   public SensorWithEntityFilters(@Nonnull BuilderSensorBase builderSensorBase, @Nonnull IEntityFilter[] filters) {
/*  30 */     super(builderSensorBase);
/*  31 */     this.filters = filters;
/*  32 */     IEntityFilter.prioritiseFilters(this.filters);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  37 */     for (IEntityFilter filter : this.filters) {
/*  38 */       filter.registerWithSupport(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  44 */     for (IEntityFilter filter : this.filters) {
/*  45 */       filter.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/*  51 */     for (IEntityFilter filter : this.filters) {
/*  52 */       filter.loaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/*  58 */     for (IEntityFilter filter : this.filters) {
/*  59 */       filter.spawned(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/*  65 */     for (IEntityFilter filter : this.filters) {
/*  66 */       filter.unloaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/*  72 */     for (IEntityFilter filter : this.filters) {
/*  73 */       filter.removed(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/*  79 */     for (IEntityFilter filter : this.filters) {
/*  80 */       filter.teleported(role, from, to);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/*  86 */     return this.filters.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/*  91 */     return this.filters[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/*  96 */     super.setContext(parent, index);
/*  97 */     for (int i = 0; i < this.filters.length; i++) {
/*  98 */       this.filters[i].setContext(this, i);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean matchesFilters(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 103 */     for (IEntityFilter filter : this.filters) {
/* 104 */       if (!filter.matchesEntity(ref, targetRef, role, store)) return false; 
/*     */     } 
/* 106 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\SensorWithEntityFilters.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */