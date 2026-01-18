package com.hypixel.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ObjectMap;

public interface Entry<V> extends Float2ObjectMap.Entry<V> {
  boolean isEmpty();
  
  @Deprecated
  Float getKey();
  
  float getFloatKey();
  
  V getValue();
  
  int hashCode();
  
  String toString();
  
  boolean equals(Object paramObject);
  
  V setValue(V paramV);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\fastutil\floats\Float2ObjectConcurrentHashMap$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */