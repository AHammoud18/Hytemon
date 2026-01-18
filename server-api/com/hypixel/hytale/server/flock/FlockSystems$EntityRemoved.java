/*    */ package com.hypixel.hytale.server.flock;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class EntityRemoved
/*    */   extends RefSystem<EntityStore>
/*    */ {
/* 25 */   private final ComponentType<EntityStore, UUIDComponent> flockIdComponentType = UUIDComponent.getComponentType();
/* 26 */   private final ComponentType<EntityStore, EntityGroup> entityGroupComponentType = EntityGroup.getComponentType();
/*    */   private final ComponentType<EntityStore, Flock> flockComponentType;
/*    */   private final Archetype<EntityStore> archetype;
/*    */   
/*    */   public EntityRemoved(ComponentType<EntityStore, Flock> flockComponentType) {
/* 31 */     this.flockComponentType = flockComponentType;
/* 32 */     this.archetype = Archetype.of(new ComponentType[] { this.flockIdComponentType, this.entityGroupComponentType, flockComponentType });
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 37 */     return (Query<EntityStore>)this.archetype;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 46 */     UUID flockId = ((UUIDComponent)store.getComponent(ref, this.flockIdComponentType)).getUuid();
/* 47 */     EntityGroup entityGroup = (EntityGroup)store.getComponent(ref, this.entityGroupComponentType);
/* 48 */     Flock flock = (Flock)store.getComponent(ref, this.flockComponentType);
/*    */     
/* 50 */     switch (FlockSystems.null.$SwitchMap$com$hypixel$hytale$component$RemoveReason[reason.ordinal()]) {
/*    */       case 1:
/* 52 */         entityGroup.setDissolved(true);
/*    */         
/* 54 */         for (Ref<EntityStore> memberRef : (Iterable<Ref<EntityStore>>)entityGroup.getMemberList()) {
/* 55 */           commandBuffer.removeComponent(memberRef, FlockMembership.getComponentType());
/*    */           
/* 57 */           TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(memberRef, TransformComponent.getComponentType());
/* 58 */           assert transformComponent != null;
/*    */           
/* 60 */           transformComponent.markChunkDirty((ComponentAccessor)commandBuffer);
/*    */         } 
/*    */         
/* 63 */         flock.setRemovedStatus(Flock.FlockRemovedStatus.DISSOLVED);
/* 64 */         entityGroup.clear();
/*    */         
/* 66 */         if (flock.isTrace()) {
/* 67 */           FlockPlugin.get().getLogger().at(Level.INFO).log("Flock %s: Dissolving", flockId);
/*    */         }
/*    */         break;
/*    */       case 2:
/* 71 */         flock.setRemovedStatus(Flock.FlockRemovedStatus.UNLOADED);
/* 72 */         entityGroup.clear();
/*    */         
/* 74 */         if (flock.isTrace())
/* 75 */           FlockPlugin.get().getLogger().at(Level.INFO).log("Flock %s: Flock unloaded, size=%s", flockId, entityGroup.size()); 
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\FlockSystems$EntityRemoved.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */