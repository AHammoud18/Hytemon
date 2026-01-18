package com.hypixel.hytale.server.core.modules.interaction;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public interface IInteractionSimulationHandler {
  void setState(InteractionType paramInteractionType, boolean paramBoolean);
  
  boolean isCharging(boolean paramBoolean, float paramFloat, InteractionType paramInteractionType, InteractionContext paramInteractionContext, Ref<EntityStore> paramRef, CooldownHandler paramCooldownHandler);
  
  boolean shouldCancelCharging(boolean paramBoolean, float paramFloat, InteractionType paramInteractionType, InteractionContext paramInteractionContext, Ref<EntityStore> paramRef, CooldownHandler paramCooldownHandler);
  
  float getChargeValue(boolean paramBoolean, float paramFloat, InteractionType paramInteractionType, InteractionContext paramInteractionContext, Ref<EntityStore> paramRef, CooldownHandler paramCooldownHandler);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\IInteractionSimulationHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */