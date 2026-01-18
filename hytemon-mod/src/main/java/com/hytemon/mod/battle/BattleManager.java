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

    BattleState initialState = new BattleState(BattlePhase.INTRO);
    BattleEncounter encounter = new BattleEncounter(player, target, currentTick(), initialState);
    trainerData.beginEncounter(encounter);
  }

  public void endEncounter(
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> playerRef
  ) {
    TrainerData trainerData = store.ensureAndGetComponent(playerRef, TrainerData.getComponentType());
    trainerData.clearEncounter();
  }

  public void queuePlayerAction(
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> playerRef,
      @Nonnull BattleAction action
  ) {
    TrainerData trainerData = store.ensureAndGetComponent(playerRef, TrainerData.getComponentType());
    BattleEncounter encounter = trainerData.getActiveEncounter();
    if (encounter == null) {
      return;
    }

    BattleState state = encounter.state();
    state.setPendingPlayerAction(action);
    if (state.phase() == BattlePhase.PLAYER_COMMAND) {
      state.setPhase(BattlePhase.OPPONENT_COMMAND);
    }
  }

  public void queueOpponentAction(
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> playerRef,
      @Nonnull BattleAction action
  ) {
    TrainerData trainerData = store.ensureAndGetComponent(playerRef, TrainerData.getComponentType());
    BattleEncounter encounter = trainerData.getActiveEncounter();
    if (encounter == null) {
      return;
    }

    BattleState state = encounter.state();
    state.setPendingOpponentAction(action);
    if (state.pendingPlayerAction() != null && state.phase() != BattlePhase.RESOLVE_TURN) {
      state.setPhase(BattlePhase.RESOLVE_TURN);
    }
  }

  public void resolveTurn(
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> playerRef
  ) {
    TrainerData trainerData = store.ensureAndGetComponent(playerRef, TrainerData.getComponentType());
    BattleEncounter encounter = trainerData.getActiveEncounter();
    if (encounter == null) {
      return;
    }

    BattleState state = encounter.state();
    if (state.phase() != BattlePhase.RESOLVE_TURN) {
      return;
    }

    BattleAction resolved = state.pendingPlayerAction() != null
        ? state.pendingPlayerAction()
        : state.pendingOpponentAction();
    encounter.setLastResolvedAction(resolved);
    state.advanceTurn();
  }

  private long currentTick() {
    return System.currentTimeMillis();
  }
}
