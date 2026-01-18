package com.hypixel.hytale.server.core.universe.world.chunk.state;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import javax.annotation.Nullable;

public interface TickableBlockState {
  void tick(float paramFloat, int paramInt, ArchetypeChunk<ChunkStore> paramArchetypeChunk, Store<ChunkStore> paramStore, CommandBuffer<ChunkStore> paramCommandBuffer);
  
  Vector3i getPosition();
  
  Vector3i getBlockPosition();
  
  @Nullable
  WorldChunk getChunk();
  
  void invalidate();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\state\TickableBlockState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */