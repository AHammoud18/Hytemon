package com.hypixel.hytale.server.npc.corecomponents;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.instructions.RoleStateChange;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.util.IEntityByPriorityFilter;
import java.util.List;

public interface ISensorEntityPrioritiser extends RoleStateChange {
  IEntityByPriorityFilter getNPCPrioritiser();
  
  IEntityByPriorityFilter getPlayerPrioritiser();
  
  Ref<EntityStore> pickTarget(Ref<EntityStore> paramRef1, Role paramRole, Vector3d paramVector3d, Ref<EntityStore> paramRef2, Ref<EntityStore> paramRef3, boolean paramBoolean, Store<EntityStore> paramStore);
  
  boolean providesFilters();
  
  void buildProvidedFilters(List<IEntityFilter> paramList);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\ISensorEntityPrioritiser.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */