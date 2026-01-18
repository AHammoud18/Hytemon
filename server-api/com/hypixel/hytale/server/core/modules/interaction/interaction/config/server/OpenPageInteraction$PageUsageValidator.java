package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

@FunctionalInterface
public interface PageUsageValidator {
  boolean canUse(Ref<EntityStore> paramRef, Player paramPlayer, InteractionContext paramInteractionContext, ComponentAccessor<EntityStore> paramComponentAccessor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\OpenPageInteraction$PageUsageValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */