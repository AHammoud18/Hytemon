package com.hypixel.hytale.server.core.universe.world.path;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;

public interface IPathWaypoint {
  int getOrder();
  
  Vector3d getWaypointPosition(@Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
  
  Vector3f getWaypointRotation(@Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
  
  double getPauseTime();
  
  float getObservationAngle();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\path\IPathWaypoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */