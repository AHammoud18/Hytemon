package com.hypixel.hytale.server.npc.util;

public interface IComponentExecutionControl {
  boolean processDelay(float paramFloat);
  
  void clearOnce();
  
  void setOnce();
  
  boolean isTriggered();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\IComponentExecutionControl.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */