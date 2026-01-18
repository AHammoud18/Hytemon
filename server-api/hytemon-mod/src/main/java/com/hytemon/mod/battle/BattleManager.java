package com.hytemon.mod.battle;

import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.capture.CaptureTarget;
import com.hytemon.mod.player.TrainerData;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;

public class BattleManager {
  private final HytemonPlugin plugin;

  public BattleManager(@Nonnull HytemonPlugin plugin) {
    this.plugin = plugin;
  }

  public boolean shouldStartBattle(@Nonnull CaptureTarget target) {
    return target.disposition() == CaptureTarget.Disposition.MONSTER;
  }

  public void beginEncounter(
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> playerRef,
      @Nonnull PlayerRef player,
      @Nonnull CaptureTarget target
  ) {
    TrainerData trainerData = store.ensureAndGetComponent(playerRef, TrainerData.getComponentType());
    if (trainerData.isInBattle()) {
      return;
    }

    BattleEncounter encounter = new BattleEncounter(player, target, currentTick());
    trainerData.beginEncounter(encounter);
  }

  public void endEncounter(
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> playerRef
  ) {
    TrainerData trainerData = store.ensureAndGetComponent(playerRef, TrainerData.getComponentType());
    trainerData.clearEncounter();
  }

  private long currentTick() {
    return System.currentTimeMillis();
  }
}
