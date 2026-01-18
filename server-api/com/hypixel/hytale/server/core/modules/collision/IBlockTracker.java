package com.hypixel.hytale.server.core.modules.collision;

import com.hypixel.hytale.math.vector.Vector3i;

public interface IBlockTracker {
  Vector3i getPosition(int paramInt);
  
  int getCount();
  
  boolean track(int paramInt1, int paramInt2, int paramInt3);
  
  void trackNew(int paramInt1, int paramInt2, int paramInt3);
  
  boolean isTracked(int paramInt1, int paramInt2, int paramInt3);
  
  void untrack(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\IBlockTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */