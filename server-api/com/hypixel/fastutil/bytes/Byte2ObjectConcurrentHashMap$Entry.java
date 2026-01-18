package com.hypixel.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;

public interface Entry<V> extends Byte2ObjectMap.Entry<V> {
  boolean isEmpty();
  
  @Deprecated
  Byte getKey();
  
  byte getByteKey();
  
  V getValue();
  
  int hashCode();
  
  String toString();
  
  boolean equals(Object paramObject);
  
  V setValue(V paramV);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\fastutil\bytes\Byte2ObjectConcurrentHashMap$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */