package com.hypixel.hytale.server.worldgen.util.cache;

import javax.annotation.Nullable;

class CacheEntry<K, V> {
  @Nullable
  private V value;
  
  @Nullable
  private K key;
  
  private long timestamp;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\cache\SizedTimeoutCache$CacheEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */