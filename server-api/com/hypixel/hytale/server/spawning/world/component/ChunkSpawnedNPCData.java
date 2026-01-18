/*    */ package com.hypixel.hytale.server.spawning.world.component;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.Object2DoubleMapCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkSpawnedNPCData
/*    */   implements Component<ChunkStore>
/*    */ {
/*    */   public static final BuilderCodec<ChunkSpawnedNPCData> CODEC;
/*    */   
/*    */   static {
/* 45 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ChunkSpawnedNPCData.class, ChunkSpawnedNPCData::new).append(new KeyedCodec("EnvironmentSpawnCounts", (Codec)new Object2DoubleMapCodec((Codec)Codec.STRING, Object2DoubleOpenHashMap::new, false)), (chunk, o) -> { Int2DoubleMap map = chunk.environmentSpawnCounts; map.clear(); ObjectIterator<Object2DoubleMap.Entry<String>> objectIterator = o.object2DoubleEntrySet().iterator(); while (objectIterator.hasNext()) { Object2DoubleMap.Entry<String> entry = objectIterator.next(); String key = (String)entry.getKey(); int index = Environment.getIndexOrUnknown(key, "Failed to find environment '%s' while deserializing spawned NPC data", new Object[] { key }); map.put(index, entry.getDoubleValue()); }  }chunk -> { Object2DoubleOpenHashMap<String> map = new Object2DoubleOpenHashMap(); IndexedLookupTableAssetMap<String, Environment> assetMap = Environment.getAssetMap(); ObjectIterator<Int2DoubleMap.Entry> objectIterator = chunk.environmentSpawnCounts.int2DoubleEntrySet().iterator(); while (objectIterator.hasNext()) { Int2DoubleMap.Entry entry = objectIterator.next(); Environment environment = (Environment)assetMap.getAsset(entry.getIntKey()); String key = (environment != null) ? environment.getId() : Environment.UNKNOWN.getId(); map.put(key, entry.getDoubleValue()); }  return (Function)map; }).add()).build();
/*    */   }
/*    */   public static ComponentType<ChunkStore, ChunkSpawnedNPCData> getComponentType() {
/* 48 */     return SpawningPlugin.get().getChunkSpawnedNPCDataComponentType();
/*    */   }
/*    */   
/* 51 */   private final Int2DoubleMap environmentSpawnCounts = (Int2DoubleMap)new Int2DoubleOpenHashMap();
/*    */   
/*    */   public double getEnvironmentSpawnCount(int environment) {
/* 54 */     return this.environmentSpawnCounts.get(environment);
/*    */   }
/*    */   
/*    */   public void setEnvironmentSpawnCount(int environment, double count) {
/* 58 */     this.environmentSpawnCounts.put(environment, count);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<ChunkStore> clone() {
/* 65 */     ChunkSpawnedNPCData data = new ChunkSpawnedNPCData();
/* 66 */     data.environmentSpawnCounts.putAll((Map)this.environmentSpawnCounts);
/* 67 */     return data;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\component\ChunkSpawnedNPCData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */