/*     */ package com.hypixel.hytale.server.core.universe.world.meta.state;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.Transform;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MarkerProvider
/*     */   implements WorldMapManager.MarkerProvider
/*     */ {
/* 136 */   public static final MarkerProvider INSTANCE = new MarkerProvider();
/*     */ 
/*     */   
/*     */   public void update(World world, GameplayConfig gameplayConfig, WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 140 */     BlockMapMarkersResource resource = (BlockMapMarkersResource)world.getChunkStore().getStore().getResource(BlockMapMarkersResource.getResourceType());
/* 141 */     Long2ObjectMap<BlockMapMarkersResource.BlockMapMarkerData> markers = resource.getMarkers();
/*     */     
/* 143 */     for (ObjectIterator<BlockMapMarkersResource.BlockMapMarkerData> objectIterator = markers.values().iterator(); objectIterator.hasNext(); ) { BlockMapMarkersResource.BlockMapMarkerData markerData = objectIterator.next();
/* 144 */       Vector3i position = markerData.getPosition();
/* 145 */       Transform transform = new Transform();
/* 146 */       transform.position = new Position((position.getX() + 0.5F), position.getY(), (position.getZ() + 0.5F));
/* 147 */       transform.orientation = new Direction(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       MapMarker marker = new MapMarker(markerData.getMarkerId(), markerData.getName(), markerData.getIcon(), transform, null);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       tracker.trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, marker); }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\state\BlockMapMarker$MarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */