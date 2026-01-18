/*     */ package com.hypixel.hytale.builtin.portals.systems.voidevent;
/*     */ import com.hypixel.hytale.builtin.portals.components.voidevent.VoidEvent;
/*     */ import com.hypixel.hytale.builtin.portals.components.voidevent.VoidSpawner;
/*     */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.InvasionPortalConfig;
/*     */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventConfig;
/*     */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*     */ import com.hypixel.hytale.builtin.portals.utils.posqueries.PositionPredicate;
/*     */ import com.hypixel.hytale.builtin.portals.utils.posqueries.SpatialQuery;
/*     */ import com.hypixel.hytale.builtin.portals.utils.posqueries.generators.SearchBelow;
/*     */ import com.hypixel.hytale.builtin.portals.utils.posqueries.generators.SearchCone;
/*     */ import com.hypixel.hytale.builtin.portals.utils.posqueries.predicates.FitsAPortal;
/*     */ import com.hypixel.hytale.builtin.portals.utils.posqueries.predicates.NotNearAnyInHashGrid;
/*     */ import com.hypixel.hytale.builtin.portals.utils.posqueries.predicates.NotNearPointXZ;
/*     */ import com.hypixel.hytale.builtin.portals.utils.spatial.SpatialHashGrid;
/*     */ import com.hypixel.hytale.common.util.RandomUtil;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class VoidInvasionPortalsSpawnSystem extends DelayedEntitySystem<EntityStore> {
/*     */   private static final int MAX_PORTALS = 24;
/*     */   
/*     */   public VoidInvasionPortalsSpawnSystem() {
/*  45 */     super(2.0F);
/*     */   }
/*     */   private CompletableFuture<Vector3d> findPortalSpawnPos;
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*     */     Vector3d portalPos;
/*  50 */     VoidEvent voidEvent = (VoidEvent)archetypeChunk.getComponent(index, VoidEvent.getComponentType());
/*     */     
/*  52 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  54 */     if (this.findPortalSpawnPos == null) {
/*  55 */       SpatialHashGrid<Ref<EntityStore>> spawners = cleanupAndGetSpawners(voidEvent);
/*  56 */       if (spawners.size() >= 24)
/*  57 */         return;  this.findPortalSpawnPos = findPortalSpawnPosition(world, voidEvent, commandBuffer);
/*     */       return;
/*     */     } 
/*  60 */     if (!this.findPortalSpawnPos.isDone()) {
/*     */       return;
/*     */     }
/*     */     try {
/*  64 */       portalPos = this.findPortalSpawnPos.join();
/*  65 */       this.findPortalSpawnPos = null;
/*  66 */     } catch (Throwable t) {
/*  67 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(t)).log("Error trying to find a void event spawn position");
/*     */       return;
/*     */     } 
/*  70 */     if (portalPos == null) {
/*     */       return;
/*     */     }
/*     */     
/*  74 */     Holder<EntityStore> voidSpawnerHolder = EntityStore.REGISTRY.newHolder();
/*  75 */     voidSpawnerHolder.addComponent(VoidSpawner.getComponentType(), (Component)new VoidSpawner());
/*  76 */     voidSpawnerHolder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(portalPos, new Vector3f()));
/*  77 */     Ref<EntityStore> voidSpawner = commandBuffer.addEntity(voidSpawnerHolder, AddReason.SPAWN);
/*  78 */     voidEvent.getVoidSpawners().add(portalPos, voidSpawner);
/*     */     
/*  80 */     VoidEventConfig eventConfig = voidEvent.getConfig(world);
/*  81 */     if (eventConfig == null) {
/*  82 */       HytaleLogger.getLogger().at(Level.WARNING).log("There's a Void Event entity but no void event config in the gameplay config");
/*     */       return;
/*     */     } 
/*  85 */     InvasionPortalConfig invasionPortalConfig = eventConfig.getInvasionPortalConfig();
/*     */     
/*  87 */     Vector3i portalBlockPos = portalPos.toVector3i();
/*  88 */     world.getChunkAsync(ChunkUtil.indexChunkFromBlock(portalBlockPos.x, portalBlockPos.z)).thenAcceptAsync(chunk -> { BlockType blockType = invasionPortalConfig.getBlockType(); chunk.setBlock(portalBlockPos.x, portalBlockPos.y, portalBlockPos.z, blockType, 4); }(Executor)world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CompletableFuture<Vector3d> findPortalSpawnPosition(World world, VoidEvent voidEvent, CommandBuffer<EntityStore> commandBuffer) {
/*  95 */     PortalWorld portalWorld = (PortalWorld)commandBuffer.getResource(PortalWorld.getResourceType());
/*  96 */     if (!portalWorld.exists()) return null;
/*     */     
/*  98 */     Vector3d spawnPos = portalWorld.getSpawnPoint().getPosition();
/*     */     
/* 100 */     Transform playerTransform = findRandomPlayerTransform(world, commandBuffer);
/* 101 */     if (playerTransform == null) return null; 
/* 102 */     Vector3d origin = playerTransform.getPosition().clone().add(0.0D, 5.0D, 0.0D);
/* 103 */     Vector3d direction = playerTransform.getDirection();
/*     */     
/* 105 */     SpatialHashGrid<Ref<EntityStore>> existingSpawners = voidEvent.getVoidSpawners();
/* 106 */     NotNearAnyInHashGrid noNearbySpawners = new NotNearAnyInHashGrid(existingSpawners, 62.0D);
/*     */     
/* 108 */     return CompletableFuture.supplyAsync(() -> (Vector3d)(new SearchCone(direction, 48.0D, 64.0D, 90.0D, 8)).filter((PositionPredicate)noNearbySpawners).filter((PositionPredicate)new NotNearPointXZ(spawnPos, 18.0D)).then((SpatialQuery)new SearchBelow(12)).filter((PositionPredicate)new FitsAPortal()).execute(world, origin).orElse(null), (Executor)world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Transform findRandomPlayerTransform(World world, CommandBuffer<EntityStore> commandBuffer) {
/* 120 */     Collection<PlayerRef> playerRefs = world.getPlayerRefs();
/* 121 */     if (playerRefs.isEmpty()) return null;
/*     */     
/* 123 */     ObjectArrayList<Ref> objectArrayList = new ObjectArrayList(playerRefs.size());
/* 124 */     for (PlayerRef playerRef : playerRefs) {
/* 125 */       objectArrayList.add(playerRef.getReference());
/*     */     }
/*     */     
/* 128 */     Ref<EntityStore> randomPlayer = (Ref<EntityStore>)RandomUtil.selectRandom((List)objectArrayList);
/*     */     
/* 130 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(randomPlayer, TransformComponent.getComponentType());
/* 131 */     assert transformComponent != null;
/*     */     
/* 133 */     return transformComponent.getTransform();
/*     */   }
/*     */   
/*     */   private SpatialHashGrid<Ref<EntityStore>> cleanupAndGetSpawners(VoidEvent voidEvent) {
/* 137 */     SpatialHashGrid<Ref<EntityStore>> spawners = voidEvent.getVoidSpawners();
/* 138 */     spawners.removeIf(ref -> !ref.isValid());
/* 139 */     return spawners;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Query<EntityStore> getQuery() {
/* 145 */     return (Query<EntityStore>)VoidEvent.getComponentType();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\voidevent\VoidInvasionPortalsSpawnSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */