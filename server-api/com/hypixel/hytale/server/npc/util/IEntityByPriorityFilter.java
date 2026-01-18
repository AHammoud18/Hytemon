package com.hypixel.hytale.server.npc.util;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.function.predicate.TriPredicate;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.role.Role;
import javax.annotation.Nullable;

public interface IEntityByPriorityFilter extends TriPredicate<Ref<EntityStore>, Ref<EntityStore>, ComponentAccessor<EntityStore>> {
  void init(Role paramRole);
  
  @Nullable
  Ref<EntityStore> getHighestPriorityTarget();
  
  void cleanup();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\IEntityByPriorityFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */