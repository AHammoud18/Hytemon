package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;

@FunctionalInterface
public interface BlockComparingPredicate<T> {
  boolean test(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Holder<ChunkStore> paramHolder, T paramT);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\IPrefabBuffer$BlockComparingPredicate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */