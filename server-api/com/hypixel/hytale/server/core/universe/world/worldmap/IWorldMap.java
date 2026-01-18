package com.hypixel.hytale.server.core.universe.world.worldmap;

import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.map.WorldMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IWorldMap {
  WorldMapSettings getWorldMapSettings();
  
  CompletableFuture<WorldMap> generate(World paramWorld, int paramInt1, int paramInt2, LongSet paramLongSet);
  
  CompletableFuture<Map<String, MapMarker>> generatePointsOfInterest(World paramWorld);
  
  default void shutdown() {}
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\IWorldMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */