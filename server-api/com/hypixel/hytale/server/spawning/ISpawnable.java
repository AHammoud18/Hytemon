package com.hypixel.hytale.server.spawning;

import javax.annotation.Nonnull;

public interface ISpawnable {
  @Nonnull
  String getIdentifier();
  
  @Nonnull
  SpawnTestResult canSpawn(@Nonnull SpawningContext paramSpawningContext);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\ISpawnable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */