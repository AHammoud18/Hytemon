/*    */ package com.hypixel.hytale.server.core.universe.world.map;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapChunk;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapImage;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*    */ import com.hypixel.hytale.protocol.packets.worldmap.UpdateWorldMap;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldMap
/*    */   implements NetworkSerializable<UpdateWorldMap>
/*    */ {
/* 26 */   private final Map<String, MapMarker> pointsOfInterest = (Map<String, MapMarker>)new Object2ObjectOpenHashMap();
/*    */   
/*    */   @Nonnull
/*    */   private final Long2ObjectMap<MapImage> chunks;
/*    */   private UpdateWorldMap packet;
/*    */   
/*    */   public WorldMap(int chunks) {
/* 33 */     this.chunks = (Long2ObjectMap<MapImage>)new Long2ObjectOpenHashMap(chunks);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Map<String, MapMarker> getPointsOfInterest() {
/* 38 */     return this.pointsOfInterest;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Long2ObjectMap<MapImage> getChunks() {
/* 43 */     return this.chunks;
/*    */   }
/*    */   
/*    */   public void addPointOfInterest(String id, String name, String markerType, @Nonnull Vector3i pos) {
/* 47 */     addPointOfInterest(id, name, markerType, new Transform(pos));
/*    */   }
/*    */   
/*    */   public void addPointOfInterest(String id, String name, String markerType, @Nonnull Vector3d pos) {
/* 51 */     addPointOfInterest(id, name, markerType, new Transform(pos));
/*    */   }
/*    */   
/*    */   public void addPointOfInterest(String id, String name, String markerType, @Nonnull Transform transform) {
/* 55 */     MapMarker old = this.pointsOfInterest.putIfAbsent(id, new MapMarker(id, name, markerType, PositionUtil.toTransformPacket(transform), null));
/* 56 */     if (old != null) throw new IllegalArgumentException("Id " + id + " already exists!");
/*    */   
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public UpdateWorldMap toPacket() {
/* 62 */     if (this.packet != null) return this.packet;
/*    */     
/* 64 */     MapChunk[] mapChunks = new MapChunk[this.chunks.size()];
/* 65 */     int i = 0;
/* 66 */     for (ObjectIterator<Long2ObjectMap.Entry<MapImage>> objectIterator = this.chunks.long2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ObjectMap.Entry<MapImage> entry = objectIterator.next();
/* 67 */       long index = entry.getLongKey();
/* 68 */       int chunkX = ChunkUtil.xOfChunkIndex(index);
/* 69 */       int chunkZ = ChunkUtil.zOfChunkIndex(index);
/* 70 */       mapChunks[i++] = new MapChunk(chunkX, chunkZ, (MapImage)entry.getValue()); }
/*    */     
/* 72 */     return this
/*    */       
/* 74 */       .packet = new UpdateWorldMap(mapChunks, (MapMarker[])this.pointsOfInterest.values().toArray(x$0 -> new MapMarker[x$0]), null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 82 */     return "WorldMap{pointsOfInterest=" + String.valueOf(this.pointsOfInterest) + ", chunks=" + String.valueOf(this.chunks) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\map\WorldMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */