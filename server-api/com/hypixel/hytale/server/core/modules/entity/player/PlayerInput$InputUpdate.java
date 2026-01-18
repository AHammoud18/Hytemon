package com.hypixel.hytale.server.core.modules.entity.player;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public interface InputUpdate {
  void apply(CommandBuffer<EntityStore> paramCommandBuffer, ArchetypeChunk<EntityStore> paramArchetypeChunk, int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerInput$InputUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */