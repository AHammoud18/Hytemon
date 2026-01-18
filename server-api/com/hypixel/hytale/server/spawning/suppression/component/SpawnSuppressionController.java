/*    */ package com.hypixel.hytale.server.spawning.suppression.component;
/*    */ 
/*    */ import com.hypixel.fastutil.longs.Long2ObjectConcurrentHashMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.ObjectMapCodec;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import com.hypixel.hytale.server.spawning.suppression.SpawnSuppressorEntry;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnSuppressionController
/*    */   implements Resource<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<SpawnSuppressionController> CODEC;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SpawnSuppressionController.class, SpawnSuppressionController::new).append(new KeyedCodec("SpawnSuppressorMap", (Codec)new ObjectMapCodec((Codec)SpawnSuppressorEntry.CODEC, ConcurrentHashMap::new, UUID::toString, UUID::fromString, false)), (spawnSuppressionController, o) -> spawnSuppressionController.spawnSuppressorMap = o, spawnSuppressionController -> spawnSuppressionController.spawnSuppressorMap).add()).build();
/*    */   }
/*    */   public static ResourceType<EntityStore, SpawnSuppressionController> getResourceType() {
/* 37 */     return SpawningPlugin.get().getSpawnSuppressionControllerResourceType();
/*    */   }
/*    */   
/* 40 */   private final Long2ObjectConcurrentHashMap<ChunkSuppressionEntry> chunkSuppressionMap = new Long2ObjectConcurrentHashMap(true, ChunkUtil.indexChunk(-2147483648, -2147483648));
/* 41 */   private Map<UUID, SpawnSuppressorEntry> spawnSuppressorMap = new ConcurrentHashMap<>();
/*    */   
/*    */   public Map<UUID, SpawnSuppressorEntry> getSpawnSuppressorMap() {
/* 44 */     return this.spawnSuppressorMap;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Long2ObjectConcurrentHashMap<ChunkSuppressionEntry> getChunkSuppressionMap() {
/* 49 */     return this.chunkSuppressionMap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<EntityStore> clone() {
/* 56 */     SpawnSuppressionController controller = new SpawnSuppressionController();
/* 57 */     controller.chunkSuppressionMap.putAll(this.chunkSuppressionMap);
/* 58 */     controller.spawnSuppressorMap.putAll(this.spawnSuppressorMap);
/* 59 */     return controller;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\component\SpawnSuppressionController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */