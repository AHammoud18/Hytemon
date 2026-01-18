package com.hypixel.hytale.builtin.path.path;

import com.hypixel.hytale.builtin.path.waypoint.IPrefabPathWaypoint;
import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.path.IPath;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;

public interface IPrefabPath extends IPath<IPrefabPathWaypoint> {
  short registerNewWaypoint(@Nonnull IPrefabPathWaypoint paramIPrefabPathWaypoint, int paramInt);
  
  void registerNewWaypointAt(int paramInt1, @Nonnull IPrefabPathWaypoint paramIPrefabPathWaypoint, int paramInt2);
  
  void addLoadedWaypoint(@Nonnull IPrefabPathWaypoint paramIPrefabPathWaypoint, int paramInt1, int paramInt2, int paramInt3);
  
  void removeWaypoint(int paramInt1, int paramInt2);
  
  void unloadWaypoint(int paramInt);
  
  boolean hasLoadedWaypoints();
  
  boolean isFullyLoaded();
  
  int loadedWaypointCount();
  
  int getWorldGenId();
  
  Vector3d getNearestWaypointPosition(@Nonnull Vector3d paramVector3d, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
  
  void mergeInto(@Nonnull IPrefabPath paramIPrefabPath, int paramInt, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
  
  void compact(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\path\IPrefabPath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */