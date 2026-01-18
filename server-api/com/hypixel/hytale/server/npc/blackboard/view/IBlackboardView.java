package com.hypixel.hytale.server.npc.blackboard.view;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import javax.annotation.Nonnull;

public interface IBlackboardView<View extends IBlackboardView<View>> {
  boolean isOutdated(@Nonnull Ref<EntityStore> paramRef, @Nonnull Store<EntityStore> paramStore);
  
  View getUpdatedView(@Nonnull Ref<EntityStore> paramRef, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
  
  void initialiseEntity(@Nonnull Ref<EntityStore> paramRef, @Nonnull NPCEntity paramNPCEntity);
  
  void cleanup();
  
  void onWorldRemoved();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\IBlackboardView.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */