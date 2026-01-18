package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;

@FunctionalInterface
public interface BlockComparingPrefabPredicate<T> {
  boolean test(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Holder<ChunkStore> paramHolder1, float paramFloat1, int paramInt5, int paramInt6, int paramInt7, Holder<ChunkStore> paramHolder2, float paramFloat2, int paramInt8, int paramInt9, T paramT);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\IPrefabBuffer$BlockComparingPrefabPredicate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */