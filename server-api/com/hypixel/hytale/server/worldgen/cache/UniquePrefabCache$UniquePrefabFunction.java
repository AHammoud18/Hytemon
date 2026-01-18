package com.hypixel.hytale.server.worldgen.cache;

import com.hypixel.hytale.server.worldgen.container.UniquePrefabContainer;

@FunctionalInterface
public interface UniquePrefabFunction {
  UniquePrefabContainer.UniquePrefabEntry[] get(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cache\UniquePrefabCache$UniquePrefabFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */