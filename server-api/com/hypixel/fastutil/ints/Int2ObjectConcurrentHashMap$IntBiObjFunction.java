package com.hypixel.fastutil.ints;

@FunctionalInterface
public interface IntBiObjFunction<V, X, J> {
  J apply(int paramInt, V paramV, X paramX);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\fastutil\ints\Int2ObjectConcurrentHashMap$IntBiObjFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */