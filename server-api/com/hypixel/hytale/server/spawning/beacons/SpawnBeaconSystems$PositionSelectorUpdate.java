/*     */ package com.hypixel.hytale.server.spawning.beacons;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.util.FloodFillEntryPoolProviderSimple;
/*     */ import com.hypixel.hytale.server.spawning.util.FloodFillPositionSelector;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class PositionSelectorUpdate
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, FloodFillPositionSelector> componentType;
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */   private final ResourceType<EntityStore, FloodFillEntryPoolProviderSimple> floodFillEntryPoolProviderSimpleResourceType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/* 165 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, SpawnBeaconSystems.CheckDespawn.class));
/*     */ 
/*     */   
/*     */   public PositionSelectorUpdate(ComponentType<EntityStore, FloodFillPositionSelector> componentType, ResourceType<EntityStore, FloodFillEntryPoolProviderSimple> floodFillEntryPoolProviderSimpleResourceType) {
/* 169 */     this.componentType = componentType;
/* 170 */     this.transformComponentType = TransformComponent.getComponentType();
/* 171 */     this.floodFillEntryPoolProviderSimpleResourceType = floodFillEntryPoolProviderSimpleResourceType;
/* 172 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)this.transformComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 178 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 184 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 194 */     FloodFillPositionSelector positionSelectorComponent = (FloodFillPositionSelector)archetypeChunk.getComponent(index, this.componentType);
/* 195 */     assert positionSelectorComponent != null;
/*     */     
/* 197 */     if (positionSelectorComponent.shouldRebuildCache() && positionSelectorComponent.tickCalculatePositionsAfter(dt)) {
/* 198 */       positionSelectorComponent.init();
/* 199 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 200 */       assert transformComponent != null;
/*     */       
/* 202 */       Vector3d position = transformComponent.getPosition();
/* 203 */       FloodFillEntryPoolProviderSimple poolProvider = (FloodFillEntryPoolProviderSimple)store.getResource(this.floodFillEntryPoolProviderSimpleResourceType);
/* 204 */       positionSelectorComponent.buildPositionCache(position, poolProvider.getPool());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\beacons\SpawnBeaconSystems$PositionSelectorUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */