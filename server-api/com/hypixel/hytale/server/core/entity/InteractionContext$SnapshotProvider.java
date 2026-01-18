package com.hypixel.hytale.server.core.entity;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

@Deprecated
@FunctionalInterface
public interface SnapshotProvider {
  EntitySnapshot getSnapshot(CommandBuffer<EntityStore> paramCommandBuffer, Ref<EntityStore> paramRef, int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\InteractionContext$SnapshotProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */