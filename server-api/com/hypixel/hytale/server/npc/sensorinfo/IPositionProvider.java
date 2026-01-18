package com.hypixel.hytale.server.npc.sensorinfo;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nullable;

public interface IPositionProvider {
  boolean hasPosition();
  
  boolean providePosition(Vector3d paramVector3d);
  
  double getX();
  
  double getY();
  
  double getZ();
  
  @Nullable
  Ref<EntityStore> getTarget();
  
  void clear();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\IPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */