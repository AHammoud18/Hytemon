package io.netty.util.collection;

import java.util.Map;

public interface ByteObjectMap<V> extends Map<Byte, V> {
  V get(byte paramByte);
  
  V put(byte paramByte, V paramV);
  
  V remove(byte paramByte);
  
  Iterable<PrimitiveEntry<V>> entries();
  
  boolean containsKey(byte paramByte);
  
  public static interface PrimitiveEntry<V> {
    byte key();
    
    V value();
    
    void setValue(V param1V);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\collection\ByteObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */