package com.hypixel.hytale.server.core.prefab.selection;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.sneakythrow.consumer.ThrowableConsumer;
import javax.annotation.Nonnull;

public interface SelectionProvider {
  <T extends Throwable> void computeSelectionCopy(@Nonnull Ref<EntityStore> paramRef, @Nonnull Player paramPlayer, @Nonnull ThrowableConsumer<BlockSelection, T> paramThrowableConsumer, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\SelectionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */