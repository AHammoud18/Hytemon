package com.hypixel.hytale.server.core.universe.world.accessor;

import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;

@FunctionalInterface
public interface TestBlockFunction {
  boolean test(int paramInt1, int paramInt2, int paramInt3, BlockType paramBlockType, int paramInt4, int paramInt5);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\accessor\IChunkAccessorSync$TestBlockFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */