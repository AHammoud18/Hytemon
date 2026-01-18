package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;

@FunctionalInterface
public interface RawBlockConsumer<T> {
  void accept(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float paramFloat, Holder<ChunkStore> paramHolder, int paramInt6, int paramInt7, int paramInt8, T paramT);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\IPrefabBuffer$RawBlockConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */