package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;

import com.hypixel.hytale.server.core.prefab.PrefabRotation;
import com.hypixel.hytale.server.core.prefab.PrefabWeights;

@FunctionalInterface
public interface ChildConsumer<T> {
  void accept(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, PrefabWeights paramPrefabWeights, PrefabRotation paramPrefabRotation, T paramT);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\IPrefabBuffer$ChildConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */