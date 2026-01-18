package com.hypixel.hytale.server.core.universe.world.worldmap;

import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;

public interface MarkerProvider {
  void update(World paramWorld, GameplayConfig paramGameplayConfig, WorldMapTracker paramWorldMapTracker, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\WorldMapManager$MarkerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */