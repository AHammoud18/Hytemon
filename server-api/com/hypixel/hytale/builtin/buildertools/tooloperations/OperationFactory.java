package com.hypixel.hytale.builtin.buildertools.tooloperations;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolOnUseInteraction;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;

public interface OperationFactory {
  @Nonnull
  ToolOperation create(@Nonnull Ref<EntityStore> paramRef, @Nonnull Player paramPlayer, @Nonnull BuilderToolOnUseInteraction paramBuilderToolOnUseInteraction, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\tooloperations\OperationFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */