package com.hypixel.hytale.server.worldgen.cave;

import com.hypixel.hytale.server.worldgen.cave.element.CaveNode;

@FunctionalInterface
public interface PrefabPlacementFunction {
  int generate(int paramInt, double paramDouble1, double paramDouble2, CaveNode paramCaveNode);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\CavePrefabPlacement$PrefabPlacementFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */