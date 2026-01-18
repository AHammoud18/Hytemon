package com.hypixel.hytale.server.worldgen.util.cache;

import javax.annotation.Nullable;

public interface Cache<K, V> {
  void shutdown();
  
  void cleanup();
  
  @Nullable
  V get(K paramK);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\cache\Cache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */