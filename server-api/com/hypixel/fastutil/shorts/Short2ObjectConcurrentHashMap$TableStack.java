package com.hypixel.fastutil.shorts;

public final class TableStack<V> {
  public int length;
  
  public int index;
  
  public Short2ObjectConcurrentHashMap.Node<V>[] tab;
  
  public TableStack<V> next;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\fastutil\shorts\Short2ObjectConcurrentHashMap$TableStack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */