/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.entity.Frozen;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.FromPrefab;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.FromWorldGen;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
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
/*     */ public class AddedFromExternalSystem
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public AddedFromExternalSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/* 206 */     this.npcComponentType = npcComponentType;
/* 207 */     this.transformComponentType = TransformComponent.getComponentType();
/* 208 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, NPCSystems.AddedSystem.class));
/* 209 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcComponentType, (Query)Query.or(new Query[] { (Query)FromWorldGen.getComponentType(), (Query)FromPrefab.getComponentType() }) });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 214 */     WorldTimeResource worldTimeResource = (WorldTimeResource)commandBuffer.getResource(WorldTimeResource.getResourceType());
/*     */     
/* 216 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/* 217 */     assert npcComponent != null;
/*     */     
/* 219 */     Archetype<EntityStore> archetype = store.getArchetype(ref);
/* 220 */     boolean fromWorldGen = archetype.contains(FromWorldGen.getComponentType());
/*     */     
/* 222 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, this.transformComponentType);
/* 223 */     assert transformComponent != null;
/*     */     
/* 225 */     npcComponent.getLeashPoint().assign(transformComponent.getPosition());
/* 226 */     Vector3f bodyRotation = transformComponent.getRotation();
/* 227 */     npcComponent.setLeashHeading(bodyRotation.getYaw());
/* 228 */     npcComponent.setLeashPitch(bodyRotation.getPitch());
/* 229 */     npcComponent.setSpawnInstant(worldTimeResource.getGameTime());
/* 230 */     npcComponent.getRole().onLoadFromWorldGenOrPrefab(ref, npcComponent, (ComponentAccessor)commandBuffer);
/*     */     
/* 232 */     if (fromWorldGen) {
/* 233 */       commandBuffer.tryRemoveComponent(ref, Frozen.getComponentType());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 245 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 251 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/* 257 */     return EntityModule.get().getPreClearMarkersGroup();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\NPCSystems$AddedFromExternalSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */