/*     */ package com.hypixel.hytale.server.core.modules.entity.system;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class AdventurePlayerSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*  72 */   private final ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> entityViewerComponentType = EntityTrackerSystems.EntityViewer.getComponentType(); @Nonnull
/*  73 */   private final ComponentType<EntityStore, Player> playerComponentType = Player.getComponentType(); @Nonnull
/*  74 */   private final ComponentType<EntityStore, HiddenFromAdventurePlayers> hiddenFromAdventurePlayersComponentType = HiddenFromAdventurePlayers.getComponentType(); @Nonnull
/*  75 */   private final ComponentType<EntityStore, PlayerSettings> playerSettingsComponentType = EntityModule.get().getPlayerSettingsComponentType(); @Nonnull
/*  76 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.entityViewerComponentType, (Query)this.playerComponentType, (Query)this.playerSettingsComponentType }); @Nonnull
/*  77 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Collections.singleton(new SystemDependency(Order.AFTER, EntityTrackerSystems.CollectVisible.class));
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/*  83 */     return EntityTrackerSystems.FIND_VISIBLE_ENTITIES_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  89 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  95 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 100 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 105 */     EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, this.entityViewerComponentType);
/* 106 */     assert entityViewerComponent != null;
/*     */     
/* 108 */     PlayerSettings playerSettingsComponent = (PlayerSettings)archetypeChunk.getComponent(index, this.playerSettingsComponentType);
/* 109 */     assert playerSettingsComponent != null;
/*     */     
/* 111 */     Player playerComponent = (Player)archetypeChunk.getComponent(index, this.playerComponentType);
/* 112 */     assert playerComponent != null;
/*     */     
/* 114 */     if (playerComponent.getGameMode() != GameMode.Adventure && playerSettingsComponent.showEntityMarkers())
/*     */       return; 
/* 116 */     for (Iterator<Ref<EntityStore>> iterator = entityViewerComponent.visible.iterator(); iterator.hasNext(); ) {
/* 117 */       Ref<EntityStore> ref = iterator.next();
/*     */       
/* 119 */       if (commandBuffer.getArchetype(ref).contains(this.hiddenFromAdventurePlayersComponentType)) {
/* 120 */         entityViewerComponent.hiddenCount++;
/* 121 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\HideEntitySystems$AdventurePlayerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */