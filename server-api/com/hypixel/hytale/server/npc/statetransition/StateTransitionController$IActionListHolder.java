package com.hypixel.hytale.server.npc.statetransition;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.instructions.RoleStateChange;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;

interface IActionListHolder extends RoleStateChange {
  boolean canExecute(Ref<EntityStore> paramRef, Role paramRole, InfoProvider paramInfoProvider, double paramDouble, Store<EntityStore> paramStore);
  
  boolean execute(Ref<EntityStore> paramRef, Role paramRole, InfoProvider paramInfoProvider, double paramDouble, Store<EntityStore> paramStore);
  
  boolean hasCompletedRun();
  
  void clearOnce();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\statetransition\StateTransitionController$IActionListHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */