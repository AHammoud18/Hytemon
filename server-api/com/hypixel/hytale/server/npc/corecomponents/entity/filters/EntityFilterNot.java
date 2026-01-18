/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity.filters;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.IEntityFilter;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*    */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponentCollection;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class EntityFilterNot
/*    */   extends EntityFilterBase implements IAnnotatedComponentCollection {
/*    */   protected final IEntityFilter filter;
/*    */   
/*    */   public EntityFilterNot(IEntityFilter filter) {
/* 23 */     this.filter = filter;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 28 */     return !this.filter.matchesEntity(ref, targetRef, role, store);
/*    */   }
/*    */ 
/*    */   
/*    */   public int cost() {
/* 33 */     return this.filter.cost();
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerWithSupport(Role role) {
/* 38 */     this.filter.registerWithSupport(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 43 */     this.filter.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loaded(Role role) {
/* 48 */     this.filter.loaded(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void spawned(Role role) {
/* 53 */     this.filter.spawned(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unloaded(Role role) {
/* 58 */     this.filter.unloaded(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removed(Role role) {
/* 63 */     this.filter.removed(role);
/*    */   }
/*    */ 
/*    */   
/*    */   public void teleported(Role role, World from, World to) {
/* 68 */     this.filter.teleported(role, from, to);
/*    */   }
/*    */ 
/*    */   
/*    */   public int componentCount() {
/* 73 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public IAnnotatedComponent getComponent(int index) {
/* 78 */     if (index >= componentCount()) throw new IndexOutOfBoundsException(); 
/* 79 */     return (IAnnotatedComponent)this.filter;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setContext(IAnnotatedComponent parent, int index) {
/* 84 */     super.setContext(parent, index);
/* 85 */     this.filter.setContext((IAnnotatedComponent)this, index);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\filters\EntityFilterNot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */