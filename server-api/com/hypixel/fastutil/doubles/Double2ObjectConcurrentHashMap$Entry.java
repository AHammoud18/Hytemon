package com.hypixel.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;

public interface Entry<V> extends Double2ObjectMap.Entry<V> {
  boolean isEmpty();
  
  @Deprecated
  Double getKey();
  
  double getDoubleKey();
  
  V getValue();
  
  int hashCode();
  
  String toString();
  
  boolean equals(Object paramObject);
  
  V setValue(V paramV);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\fastutil\doubles\Double2ObjectConcurrentHashMap$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */