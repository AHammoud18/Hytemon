package com.hypixel.hytale.component.system.data;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.system.QuerySystem;
import com.hypixel.hytale.component.system.System;
import java.util.List;

public abstract class ArchetypeDataSystem<ECS_TYPE, Q, R> extends System<ECS_TYPE> implements QuerySystem<ECS_TYPE> {
  public abstract void fetch(ArchetypeChunk<ECS_TYPE> paramArchetypeChunk, Store<ECS_TYPE> paramStore, CommandBuffer<ECS_TYPE> paramCommandBuffer, Q paramQ, List<R> paramList);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\data\ArchetypeDataSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */