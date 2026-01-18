package com.hypixel.hytale.server.npc.util;

import javax.annotation.Nullable;

public interface IAnnotatedComponentCollection extends IAnnotatedComponent {
  int componentCount();
  
  @Nullable
  IAnnotatedComponent getComponent(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\IAnnotatedComponentCollection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */