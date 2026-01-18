package com.hypixel.hytale.server.worldgen.cache;

import com.hypixel.hytale.server.worldgen.cave.Cave;
import com.hypixel.hytale.server.worldgen.cave.CaveType;

@FunctionalInterface
public interface CaveFunction {
  Cave compute(CaveType paramCaveType, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cache\CaveGeneratorCache$CaveFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */