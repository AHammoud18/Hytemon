/*    */ package com.hypixel.hytale.server.worldgen;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ZoneBiomeResult;
/*    */ import com.hypixel.hytale.server.worldgen.zone.Zone;
/*    */ import com.hypixel.hytale.server.worldgen.zone.ZoneDiscoveryConfig;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BiomeDataSystem extends DelayedEntitySystem<EntityStore> {
/*    */   public BiomeDataSystem() {
/* 24 */     super(1.0F);
/*    */   }
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*    */     ChunkGenerator generator;
/* 29 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 30 */     IWorldGen worldGen = world.getChunkStore().getGenerator();
/*    */     
/* 32 */     Player playerComponent = (Player)archetypeChunk.getComponent(index, Player.getComponentType());
/* 33 */     assert playerComponent != null;
/*    */     
/* 35 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*    */     
/* 37 */     WorldMapTracker worldMapTracker = playerComponent.getWorldMapTracker();
/*    */     
/* 39 */     if (worldGen instanceof ChunkGenerator) { generator = (ChunkGenerator)worldGen; }
/* 40 */     else { worldMapTracker.updateCurrentZoneAndBiome(ref, null, null, (ComponentAccessor)commandBuffer);
/*    */       
/*    */       return; }
/*    */     
/* 44 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 45 */     assert transformComponent != null;
/*    */     
/* 47 */     Vector3d position = transformComponent.getPosition();
/* 48 */     int seed = (int)world.getWorldConfig().getSeed();
/* 49 */     int x = (int)position.getX();
/* 50 */     int z = (int)position.getZ();
/*    */     
/* 52 */     ZoneBiomeResult result = generator.getZoneBiomeResultAt(seed, x, z);
/* 53 */     Biome biome = result.getBiome();
/* 54 */     Zone zone = result.getZoneResult().getZone();
/* 55 */     ZoneDiscoveryConfig discoveryConfig = zone.discoveryConfig();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 66 */     WorldMapTracker.ZoneDiscoveryInfo zoneDiscoveryInfo = new WorldMapTracker.ZoneDiscoveryInfo(discoveryConfig.zone(), zone.name(), discoveryConfig.display(), discoveryConfig.soundEventId(), discoveryConfig.icon(), discoveryConfig.major(), discoveryConfig.duration(), discoveryConfig.fadeInDuration(), discoveryConfig.fadeOutDuration());
/*    */ 
/*    */     
/* 69 */     worldMapTracker.updateCurrentZoneAndBiome(ref, zoneDiscoveryInfo, biome.getName(), (ComponentAccessor)commandBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<EntityStore> getQuery() {
/* 75 */     return (Query<EntityStore>)Archetype.of(new ComponentType[] { Player.getComponentType(), TransformComponent.getComponentType() });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\BiomeDataSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */