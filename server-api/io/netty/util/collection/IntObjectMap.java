package io.netty.util.collection;

import java.util.Map;

public interface IntObjectMap<V> extends Map<Integer, V> {
  V get(int paramInt);
  
  V put(int paramInt, V paramV);
  
  V remove(int paramInt);
  
  Iterable<PrimitiveEntry<V>> entries();
  
  boolean containsKey(int paramInt);
  
  public static interface PrimitiveEntry<V> {
    int key();
    
    V value();
    
    void setValue(V param1V);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\collection\IntObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */