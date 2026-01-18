package com.hypixel.hytale.component.system;

import com.hypixel.hytale.component.ArchetypeChunk;

public abstract class ArchetypeChunkSystem<ECS_TYPE> extends System<ECS_TYPE> implements QuerySystem<ECS_TYPE> {
  public abstract void onSystemAddedToArchetypeChunk(ArchetypeChunk<ECS_TYPE> paramArchetypeChunk);
  
  public abstract void onSystemRemovedFromArchetypeChunk(ArchetypeChunk<ECS_TYPE> paramArchetypeChunk);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\ArchetypeChunkSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */