package com.hypixel.hytale.server.worldgen.util.condition;

import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;

@FunctionalInterface
public interface BlockMaskCondition {
  boolean eval(int paramInt1, int paramInt2, BlockFluidEntry paramBlockFluidEntry);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\condition\BlockMaskCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */