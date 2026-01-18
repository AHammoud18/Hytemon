/*     */ package com.hypixel.hytale.server.core.modules.entity.system;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.EntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdateLocationSystems {
/*     */   @Nonnull
/*  33 */   private static final Message MESSAGE_GENERAL_PLAYER_IN_INVALID_CHUNK = Message.translation("server.general.playerInInvalidChunk");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  39 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SpawnSystem
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     public Query<EntityStore> getQuery() {
/*  48 */       return (Query<EntityStore>)TransformComponent.getComponentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  53 */       TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/*  54 */       assert transformComponent != null;
/*     */ 
/*     */       
/*  57 */       Ref<ChunkStore> chunkRef = transformComponent.getChunkRef();
/*  58 */       if (chunkRef == null || !chunkRef.isValid()) {
/*  59 */         UpdateLocationSystems.updateLocation(ref, transformComponent, ((EntityStore)store.getExternalData()).getWorld(), commandBuffer);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TickingSystem
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     public Query<EntityStore> getQuery() {
/*  75 */       return (Query<EntityStore>)TransformComponent.getComponentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  80 */       World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*  81 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */       
/*  83 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/*  84 */       assert transformComponent != null;
/*     */       
/*  86 */       UpdateLocationSystems.updateLocation(ref, transformComponent, world, commandBuffer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateLocation(@Nonnull Ref<EntityStore> ref, @Nonnull TransformComponent transformComponent, @Nullable World world, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 102 */     if (world == null) {
/*     */       return;
/*     */     }
/* 105 */     Vector3d position = transformComponent.getPosition();
/* 106 */     if (position.getY() < -32.0D && !commandBuffer.getArchetype(ref).contains(Player.getComponentType())) {
/* 107 */       LOGGER.at(Level.WARNING).log("Unable to move entity below the world! -32 < " + String.valueOf(position));
/* 108 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 112 */     ChunkStore chunkStore = world.getChunkStore();
/* 113 */     Store<ChunkStore> chunkComponentStore = chunkStore.getStore();
/*     */     
/* 115 */     int chunkX = MathUtil.floor(position.getX()) >> 5;
/* 116 */     int chunkZ = MathUtil.floor(position.getZ()) >> 5;
/*     */ 
/*     */     
/* 119 */     Ref<ChunkStore> oldChunkRef = transformComponent.getChunkRef();
/* 120 */     boolean hasOldChunk = false;
/* 121 */     int oldChunkX = 0;
/* 122 */     int oldChunkZ = 0;
/*     */ 
/*     */     
/* 125 */     if (oldChunkRef != null && oldChunkRef.isValid()) {
/* 126 */       WorldChunk oldWorldChunkComponent = (WorldChunk)chunkComponentStore.getComponent(oldChunkRef, WorldChunk.getComponentType());
/* 127 */       if (oldWorldChunkComponent != null) {
/* 128 */         hasOldChunk = true;
/* 129 */         oldChunkX = oldWorldChunkComponent.getX();
/* 130 */         oldChunkZ = oldWorldChunkComponent.getZ();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 135 */     if (!hasOldChunk || oldChunkX != chunkX || oldChunkZ != chunkZ) {
/* 136 */       long newChunkIndex = ChunkUtil.indexChunk(chunkX, chunkZ);
/* 137 */       Ref<ChunkStore> newChunkRef = chunkStore.getChunkReference(newChunkIndex);
/*     */       
/* 139 */       if (newChunkRef != null && newChunkRef.isValid()) {
/*     */         
/* 141 */         WorldChunk newWorldChunkComponent = (WorldChunk)chunkComponentStore.getComponent(newChunkRef, WorldChunk.getComponentType());
/* 142 */         updateChunk(ref, transformComponent, oldChunkRef, newChunkRef, newWorldChunkComponent, (ComponentAccessor<ChunkStore>)chunkComponentStore, (ComponentAccessor<EntityStore>)commandBuffer);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 147 */         LOGGER.at(Level.WARNING).log("Entity has moved into a chunk that isn't currently loaded! " + chunkX + ", " + chunkZ + ", " + String.valueOf(transformComponent));
/* 148 */         CompletableFutureUtil._catch(chunkStore.getChunkReferenceAsync(newChunkIndex).thenAcceptAsync(asyncChunkRef -> { if (asyncChunkRef == null || !asyncChunkRef.isValid()) { updateChunkAsync(ref, null, null, chunkComponentStore); } else { WorldChunk asyncWorldChunk = (WorldChunk)chunkComponentStore.getComponent(asyncChunkRef, WorldChunk.getComponentType()); updateChunkAsync(ref, asyncChunkRef, asyncWorldChunk, chunkComponentStore); }  }(Executor)world));
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateChunkAsync(@Nonnull Ref<EntityStore> ref, @Nullable Ref<ChunkStore> newChunkRef, @Nullable WorldChunk newWorldChunk, @Nonnull Store<ChunkStore> chunkComponentStore) {
/* 174 */     if (!ref.isValid())
/*     */       return; 
/* 176 */     Store<EntityStore> entityStore = ref.getStore();
/* 177 */     TransformComponent transformComponent = (TransformComponent)entityStore.getComponent(ref, TransformComponent.getComponentType());
/* 178 */     assert transformComponent != null;
/*     */     
/* 180 */     Ref<ChunkStore> oldChunkRef = transformComponent.getChunkRef();
/* 181 */     updateChunk(ref, transformComponent, oldChunkRef, newChunkRef, newWorldChunk, (ComponentAccessor<ChunkStore>)chunkComponentStore, (ComponentAccessor<EntityStore>)entityStore);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateChunk(@Nonnull Ref<EntityStore> ref, @Nonnull TransformComponent transformComponent, @Nullable Ref<ChunkStore> oldChunkRef, @Nullable Ref<ChunkStore> newChunkRef, @Nullable WorldChunk newWorldChunkComponent, @Nonnull ComponentAccessor<ChunkStore> chunkComponentStore, @Nonnull ComponentAccessor<EntityStore> entityComponentAccessor) {
/* 203 */     boolean isPlayer = entityComponentAccessor.getArchetype(ref).contains(Player.getComponentType());
/*     */ 
/*     */     
/* 206 */     if (newWorldChunkComponent == null) {
/* 207 */       handleInvalidChunk(ref, transformComponent, isPlayer, entityComponentAccessor);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 212 */     if (newWorldChunkComponent.not(ChunkFlag.INIT)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 217 */     assert newChunkRef != null;
/*     */     
/* 219 */     if (!isPlayer) {
/* 220 */       updateEntityInChunk(ref, oldChunkRef, newChunkRef, newWorldChunkComponent, chunkComponentStore, entityComponentAccessor);
/*     */     }
/*     */ 
/*     */     
/* 224 */     transformComponent.setChunkLocation(newChunkRef, newWorldChunkComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handleInvalidChunk(@Nonnull Ref<EntityStore> ref, @Nonnull TransformComponent transformComponent, boolean isPlayer, @Nonnull ComponentAccessor<EntityStore> entityComponentAccessor) {
/* 241 */     if (!isPlayer) {
/* 242 */       LOGGER.at(Level.SEVERE).log("Entity is in a chunk that can't be loaded! Removing! %s", transformComponent);
/* 243 */       entityComponentAccessor.removeEntity(ref, EntityStore.REGISTRY.newHolder(), RemoveReason.REMOVE);
/*     */     }
/*     */     else {
/*     */       
/* 247 */       LOGGER.at(Level.SEVERE).log("Player is in a chunk that can't be loaded! Moving (-%d,0,0)! %s", 32, transformComponent);
/*     */       
/* 249 */       Vector3d position = transformComponent.getPosition();
/* 250 */       Vector3f bodyRotation = transformComponent.getRotation();
/* 251 */       Vector3d targetPosition = position.clone().subtract(32.0D, 0.0D, 0.0D);
/*     */       
/* 253 */       PlayerRef playerRefComponent = (PlayerRef)entityComponentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 254 */       assert playerRefComponent != null;
/*     */       
/* 256 */       entityComponentAccessor.addComponent(ref, Teleport.getComponentType(), (Component)new Teleport(targetPosition, bodyRotation));
/* 257 */       playerRefComponent.sendMessage(MESSAGE_GENERAL_PLAYER_IN_INVALID_CHUNK);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateEntityInChunk(@Nonnull Ref<EntityStore> ref, @Nullable Ref<ChunkStore> oldChunkRef, @Nonnull Ref<ChunkStore> newChunkRef, @Nonnull WorldChunk newWorldChunk, @Nonnull ComponentAccessor<ChunkStore> chunkComponentStore, @Nonnull ComponentAccessor<EntityStore> entityComponentAccessor) {
/* 279 */     if (oldChunkRef != null && oldChunkRef.isValid()) {
/* 280 */       EntityChunk oldEntityChunkComponent = (EntityChunk)chunkComponentStore.getComponent(oldChunkRef, EntityChunk.getComponentType());
/* 281 */       assert oldEntityChunkComponent != null;
/*     */       
/* 283 */       oldEntityChunkComponent.removeEntityReference(ref);
/*     */     } 
/*     */ 
/*     */     
/* 287 */     EntityChunk newEntityChunkComponent = (EntityChunk)chunkComponentStore.getComponent(newChunkRef, EntityChunk.getComponentType());
/* 288 */     assert newEntityChunkComponent != null;
/*     */ 
/*     */     
/* 291 */     if (newWorldChunk.not(ChunkFlag.TICKING)) {
/* 292 */       Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 293 */       entityComponentAccessor.removeEntity(ref, holder, RemoveReason.UNLOAD);
/* 294 */       newEntityChunkComponent.addEntityHolder(holder);
/*     */     } else {
/* 296 */       newEntityChunkComponent.addEntityReference(ref);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\UpdateLocationSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */