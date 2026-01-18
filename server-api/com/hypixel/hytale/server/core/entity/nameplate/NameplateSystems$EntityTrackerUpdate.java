/*     */ package com.hypixel.hytale.server.core.entity.nameplate;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.protocol.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTrackerUpdate
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Nameplate> componentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public EntityTrackerUpdate(@Nonnull ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType, @Nonnull ComponentType<EntityStore, Nameplate> componentType) {
/*  52 */     this.visibleComponentType = visibleComponentType;
/*  53 */     this.componentType = componentType;
/*  54 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)visibleComponentType, (Query)componentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/*  60 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  66 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  71 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  76 */     EntityTrackerSystems.Visible visibleComponent = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/*  77 */     assert visibleComponent != null;
/*     */     
/*  79 */     Nameplate nameplateComponent = (Nameplate)archetypeChunk.getComponent(index, this.componentType);
/*  80 */     assert nameplateComponent != null;
/*     */ 
/*     */     
/*  83 */     if (nameplateComponent.consumeNetworkOutdated()) {
/*  84 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), nameplateComponent, visibleComponent.visibleTo);
/*  85 */     } else if (!visibleComponent.newlyVisibleTo.isEmpty()) {
/*  86 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), nameplateComponent, visibleComponent.newlyVisibleTo);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void queueUpdatesFor(@Nonnull Ref<EntityStore> ref, @Nonnull Nameplate nameplateComponent, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/*  98 */     ComponentUpdate update = new ComponentUpdate();
/*  99 */     update.type = ComponentUpdateType.Nameplate;
/* 100 */     update.nameplate = new Nameplate();
/* 101 */     update.nameplate.text = nameplateComponent.getText();
/*     */     
/* 103 */     for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 104 */       viewer.queueUpdate(ref, update); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\nameplate\NameplateSystems$EntityTrackerUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */