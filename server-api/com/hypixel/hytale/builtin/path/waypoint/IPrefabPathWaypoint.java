package com.hypixel.hytale.builtin.path.waypoint;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.server.core.universe.world.path.IPath;
import com.hypixel.hytale.server.core.universe.world.path.IPathWaypoint;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.UUID;
import javax.annotation.Nonnull;

public interface IPrefabPathWaypoint extends IPathWaypoint {
  void onReplaced();
  
  void initialise(@Nonnull UUID paramUUID, @Nonnull String paramString, int paramInt1, double paramDouble, float paramFloat, int paramInt2, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
  
  IPath<IPrefabPathWaypoint> getParentPath();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\waypoint\IPrefabPathWaypoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */