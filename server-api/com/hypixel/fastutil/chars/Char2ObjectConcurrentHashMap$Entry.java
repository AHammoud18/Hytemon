package com.hypixel.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;

public interface Entry<V> extends Char2ObjectMap.Entry<V> {
  boolean isEmpty();
  
  @Deprecated
  Character getKey();
  
  char getCharKey();
  
  V getValue();
  
  int hashCode();
  
  String toString();
  
  boolean equals(Object paramObject);
  
  V setValue(V paramV);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\fastutil\chars\Char2ObjectConcurrentHashMap$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */