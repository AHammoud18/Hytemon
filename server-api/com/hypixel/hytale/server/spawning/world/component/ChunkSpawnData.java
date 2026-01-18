/*    */ package com.hypixel.hytale.server.spawning.world.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import com.hypixel.hytale.server.spawning.world.ChunkEnvironmentSpawnData;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkSpawnData
/*    */   implements Component<ChunkStore>
/*    */ {
/*    */   public static ComponentType<ChunkStore, ChunkSpawnData> getComponentType() {
/* 19 */     return SpawningPlugin.get().getChunkSpawnDataComponentType();
/*    */   }
/*    */   
/* 22 */   private final Int2ObjectMap<ChunkEnvironmentSpawnData> chunkEnvironmentSpawnDataMap = (Int2ObjectMap<ChunkEnvironmentSpawnData>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   private boolean started;
/*    */   private long lastSpawn;
/*    */   
/*    */   @Nonnull
/*    */   public Int2ObjectMap<ChunkEnvironmentSpawnData> getChunkEnvironmentSpawnDataMap() {
/* 29 */     return this.chunkEnvironmentSpawnDataMap;
/*    */   }
/*    */   
/*    */   public boolean isStarted() {
/* 33 */     return this.started;
/*    */   }
/*    */   
/*    */   public void setStarted(boolean started) {
/* 37 */     this.started = started;
/*    */   }
/*    */   
/*    */   public void setLastSpawn(long lastSpawn) {
/* 41 */     this.lastSpawn = lastSpawn;
/*    */   }
/*    */   
/*    */   public long getLastSpawn() {
/* 45 */     return this.lastSpawn;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component<ChunkStore> clone() {
/* 50 */     throw new UnsupportedOperationException("Not implemented!");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ChunkEnvironmentSpawnData getEnvironmentSpawnData(int environment) {
/* 55 */     ChunkEnvironmentSpawnData chunkEnvironmentSpawnData = (ChunkEnvironmentSpawnData)this.chunkEnvironmentSpawnDataMap.get(environment);
/* 56 */     if (chunkEnvironmentSpawnData == null) throw new NullPointerException("Failed to get environment data for chunk"); 
/* 57 */     return chunkEnvironmentSpawnData;
/*    */   }
/*    */   
/*    */   public boolean isOnSpawnCooldown() {
/* 61 */     return (this.lastSpawn != 0L);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\component\ChunkSpawnData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */