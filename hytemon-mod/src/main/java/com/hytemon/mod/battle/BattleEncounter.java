package com.hytemon.mod.battle;

import com.hytemon.mod.capture.CaptureTarget;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BattleEncounter {
  @Nonnull
  private final PlayerRef player;
  @Nonnull
  private final CaptureTarget target;
  private final long startedAtTick;
  @Nonnull
  private BattleState state;
  @Nullable
  private BattleAction lastResolvedAction;

  public BattleEncounter(
      @Nonnull PlayerRef player,
      @Nonnull CaptureTarget target,
      long startedAtTick,
      @Nonnull BattleState state
  ) {
    this.player = player;
    this.target = target;
    this.startedAtTick = startedAtTick;
    this.state = state;
  }

  @Nonnull
  public PlayerRef player() {
    return player;
  }

  @Nonnull
  public CaptureTarget target() {
    return target;
  }

  public long startedAtTick() {
    return startedAtTick;
  }

  @Nonnull
  public BattleState state() {
    return state;
  }

  public void updateState(@Nonnull BattleState state) {
    this.state = state;
  }

  @Nullable
  public BattleAction lastResolvedAction() {
    return lastResolvedAction;
  }

  public void setLastResolvedAction(@Nullable BattleAction lastResolvedAction) {
    this.lastResolvedAction = lastResolvedAction;
  }
}
