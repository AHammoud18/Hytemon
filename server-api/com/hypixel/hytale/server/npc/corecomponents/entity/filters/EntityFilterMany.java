/*     */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.IEntityFilter;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class EntityFilterMany
/*     */   extends EntityFilterBase implements IAnnotatedComponentCollection {
/*     */   @Nonnull
/*     */   protected final IEntityFilter[] filters;
/*     */   protected final int cost;
/*     */   
/*     */   public EntityFilterMany(@Nonnull List<IEntityFilter> filters) {
/*  25 */     if (filters == null) throw new IllegalArgumentException("Filter list can't be null"); 
/*  26 */     this.filters = (IEntityFilter[])filters.toArray(x$0 -> new IEntityFilter[x$0]);
/*     */     
/*  28 */     for (IEntityFilter filter : this.filters) {
/*  29 */       if (filter == null) throw new IllegalArgumentException("Filter cannot be null in filter list");
/*     */     
/*     */     } 
/*  32 */     IEntityFilter.prioritiseFilters(this.filters);
/*     */     
/*  34 */     int cost = 0;
/*  35 */     for (int i = 0; i < this.filters.length; i++)
/*     */     {
/*     */       
/*  38 */       cost = (int)(cost + this.filters[i].cost() * 1.0D / Math.pow(2.0D, i));
/*     */     }
/*  40 */     this.cost = cost;
/*     */   }
/*     */ 
/*     */   
/*     */   public int cost() {
/*  45 */     return this.cost;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/*  50 */     for (IEntityFilter filter : this.filters) {
/*  51 */       filter.registerWithSupport(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/*  57 */     for (IEntityFilter filter : this.filters) {
/*  58 */       filter.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/*  64 */     for (IEntityFilter filter : this.filters) {
/*  65 */       filter.loaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/*  71 */     for (IEntityFilter filter : this.filters) {
/*  72 */       filter.spawned(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/*  78 */     for (IEntityFilter filter : this.filters) {
/*  79 */       filter.unloaded(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/*  85 */     for (IEntityFilter filter : this.filters) {
/*  86 */       filter.removed(role);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/*  92 */     for (IEntityFilter filter : this.filters) {
/*  93 */       filter.teleported(role, from, to);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/*  99 */     return this.filters.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 104 */     return (IAnnotatedComponent)this.filters[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/* 109 */     super.setContext(parent, index);
/* 110 */     for (int i = 0; i < this.filters.length; i++)
/* 111 */       this.filters[i].setContext((IAnnotatedComponent)this, i); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterMany.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */