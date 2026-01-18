/*     */ package com.hypixel.hytale.server.core.modules.blockhealth;
/*     */ 
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateBlockDamage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.time.Instant;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BlockHealthSystem
/*     */   extends EntityTickingSystem<ChunkStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<ChunkStore, BlockHealthChunk> blockHealthComponentChunkType;
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, TimeResource> timeResourceType;
/*     */   private final Archetype<ChunkStore> archetype;
/*     */   
/*     */   public BlockHealthSystem(@Nonnull ComponentType<ChunkStore, BlockHealthChunk> blockHealthComponentChunkType) {
/* 209 */     this.blockHealthComponentChunkType = blockHealthComponentChunkType;
/* 210 */     this.timeResourceType = TimeResource.getResourceType();
/* 211 */     this.archetype = Archetype.of(new ComponentType[] { blockHealthComponentChunkType, WorldChunk.getComponentType() });
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<ChunkStore> getQuery() {
/* 216 */     return (Query<ChunkStore>)this.archetype;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 221 */     BlockHealthChunk blockHealthChunkComponent = (BlockHealthChunk)archetypeChunk.getComponent(index, this.blockHealthComponentChunkType);
/* 222 */     assert blockHealthChunkComponent != null;
/*     */     
/* 224 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 225 */     Store<EntityStore> entityStore = world.getEntityStore().getStore();
/* 226 */     TimeResource uptime = (TimeResource)world.getEntityStore().getStore().getResource(this.timeResourceType);
/* 227 */     Instant currentGameTime = uptime.getNow();
/* 228 */     Instant lastRepairGameTime = blockHealthChunkComponent.getLastRepairGameTime();
/*     */ 
/*     */     
/* 231 */     blockHealthChunkComponent.setLastRepairGameTime(currentGameTime);
/*     */ 
/*     */     
/* 234 */     if (lastRepairGameTime == null)
/*     */       return; 
/* 236 */     Map<Vector3i, FragileBlock> blockFragilityMap = blockHealthChunkComponent.getBlockFragilityMap();
/* 237 */     if (!blockFragilityMap.isEmpty()) {
/* 238 */       float f = (float)(currentGameTime.toEpochMilli() - lastRepairGameTime.toEpochMilli()) / 1000.0F;
/*     */ 
/*     */       
/* 241 */       for (Iterator<Map.Entry<Vector3i, FragileBlock>> iterator1 = blockFragilityMap.entrySet().iterator(); iterator1.hasNext(); ) {
/* 242 */         Map.Entry<Vector3i, FragileBlock> entry = iterator1.next();
/* 243 */         FragileBlock fragileBlock = entry.getValue();
/*     */         
/* 245 */         float newDuration = fragileBlock.getDurationSeconds() - f;
/*     */         
/* 247 */         if (newDuration <= 0.0F) {
/* 248 */           iterator1.remove();
/*     */           
/*     */           continue;
/*     */         } 
/* 252 */         fragileBlock.setDurationSeconds(newDuration);
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     Map<Vector3i, BlockHealth> blockHealthMap = blockHealthChunkComponent.getBlockHealthMap();
/* 257 */     if (blockHealthMap.isEmpty())
/*     */       return; 
/* 259 */     WorldChunk chunk = (WorldChunk)archetypeChunk.getComponent(index, WorldChunk.getComponentType());
/* 260 */     assert chunk != null;
/*     */ 
/*     */     
/* 263 */     Collection<PlayerRef> allPlayers = world.getPlayerRefs();
/* 264 */     ObjectArrayList<PlayerRef> visibleTo = new ObjectArrayList(allPlayers.size());
/* 265 */     for (PlayerRef playerRef : allPlayers) {
/* 266 */       Ref<EntityStore> playerReference = playerRef.getReference();
/* 267 */       if (playerReference == null || !playerReference.isValid())
/*     */         continue; 
/* 269 */       ChunkTracker chunkTrackerComponent = (ChunkTracker)entityStore.getComponent(playerReference, ChunkTracker.getComponentType());
/* 270 */       assert chunkTrackerComponent != null;
/*     */       
/* 272 */       if (chunkTrackerComponent.isLoaded(chunk.getIndex())) {
/* 273 */         visibleTo.add(playerRef);
/*     */       }
/*     */     } 
/*     */     
/* 277 */     float deltaSeconds = (float)(currentGameTime.toEpochMilli() - lastRepairGameTime.toEpochMilli()) / 1000.0F;
/* 278 */     for (Iterator<Map.Entry<Vector3i, BlockHealth>> iterator = blockHealthMap.entrySet().iterator(); iterator.hasNext(); ) {
/* 279 */       Map.Entry<Vector3i, BlockHealth> entry = iterator.next();
/* 280 */       Vector3i position = entry.getKey();
/* 281 */       BlockHealth blockHealth = entry.getValue();
/*     */ 
/*     */ 
/*     */       
/* 285 */       Instant startRegenerating = blockHealth.getLastDamageGameTime().plusSeconds(5L);
/* 286 */       if (currentGameTime.isBefore(startRegenerating))
/*     */         continue; 
/* 288 */       float healthDelta = 0.1F * deltaSeconds;
/* 289 */       float health = blockHealth.getHealth() + healthDelta;
/* 290 */       if (health < 1.0F) {
/* 291 */         blockHealth.setHealth(health);
/*     */       } else {
/* 293 */         iterator.remove();
/* 294 */         health = BlockHealth.NO_DAMAGE_INSTANCE.getHealth();
/* 295 */         healthDelta = health - blockHealth.getHealth();
/*     */       }  UpdateBlockDamage packet; int i;
/* 297 */       for (packet = new UpdateBlockDamage(new BlockPosition(position.getX(), position.getY(), position.getZ()), health, healthDelta), i = 0; i < visibleTo.size(); i++)
/* 298 */         ((PlayerRef)visibleTo.get(i)).getPacketHandler().writeNoCache((Packet)packet); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\blockhealth\BlockHealthModule$BlockHealthSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */