package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;

@FunctionalInterface
public interface FluidConsumer<T> {
  void accept(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte paramByte, T paramT);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\IPrefabBuffer$FluidConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */