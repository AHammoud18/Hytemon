package com.hypixel.hytale.builtin.adventure.npcobjectives.task;

import com.hypixel.hytale.builtin.adventure.objectives.Objective;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;

public interface KillTask {
  void checkKilledEntity(Store<EntityStore> paramStore, Ref<EntityStore> paramRef, Objective paramObjective, NPCEntity paramNPCEntity, Damage paramDamage);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\task\KillTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */