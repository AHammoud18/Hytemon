package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;

import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
import com.hypixel.hytale.server.core.universe.PlayerRef;

@FunctionalInterface
public interface BlockCustomPageSupplier<T extends com.hypixel.hytale.server.core.universe.world.meta.BlockState> {
  CustomUIPage tryCreate(PlayerRef paramPlayerRef, T paramT);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\OpenCustomUIInteraction$BlockCustomPageSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */