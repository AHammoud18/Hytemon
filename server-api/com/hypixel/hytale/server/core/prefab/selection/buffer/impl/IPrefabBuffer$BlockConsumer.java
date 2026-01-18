package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import javax.annotation.Nullable;

@FunctionalInterface
public interface BlockConsumer<T> {
  void accept(int paramInt1, int paramInt2, int paramInt3, int paramInt4, @Nullable Holder<ChunkStore> paramHolder, int paramInt5, int paramInt6, int paramInt7, T paramT, int paramInt8, int paramInt9);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\IPrefabBuffer$BlockConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */