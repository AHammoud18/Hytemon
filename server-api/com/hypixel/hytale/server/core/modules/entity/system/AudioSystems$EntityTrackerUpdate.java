/*    */ package com.hypixel.hytale.server.core.modules.entity.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.SystemGroup;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*    */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.AudioComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityTrackerUpdate
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/* 31 */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType = EntityTrackerSystems.Visible.getComponentType();
/* 32 */   private final ComponentType<EntityStore, AudioComponent> audioComponentType = AudioComponent.getComponentType();
/* 33 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.visibleComponentType, (Query)this.audioComponentType });
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SystemGroup<EntityStore> getGroup() {
/* 38 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 44 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 49 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 54 */     EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 55 */     assert visibleComponent != null;
/*    */     
/* 57 */     AudioComponent audioComponent = (AudioComponent)archetypeChunk.getComponent(index, this.audioComponentType);
/* 58 */     assert audioComponent != null;
/*    */     
/* 60 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*    */     
/* 62 */     if (audioComponent.consumeNetworkOutdated()) {
/* 63 */       queueUpdatesFor(ref, audioComponent, visibleComponent.visibleTo);
/* 64 */     } else if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/* 65 */       queueUpdatesFor(ref, audioComponent, visibleComponent.newlyVisibleTo);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull AudioComponent audioComponent, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 72 */     ComponentUpdate update = new ComponentUpdate();
/* 73 */     update.type = ComponentUpdateType.Audio;
/* 74 */     update.soundEventIds = audioComponent.getSoundEventIds();
/* 75 */     for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> entry : visibleTo.entrySet())
/* 76 */       ((EntityTrackerSystems.EntityViewer)entry.getValue()).queueUpdate(ref, update); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\AudioSystems$EntityTrackerUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */