package com.hytemon.mod.battle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BattleState {
  @Nonnull
  private BattlePhase phase;
  private int turnNumber;
  @Nullable
  private BattleAction pendingPlayerAction;
  @Nullable
  private BattleAction pendingOpponentAction;

  public BattleState(@Nonnull BattlePhase phase) {
    this.phase = phase;
    this.turnNumber = 1;
  }

  public BattleState(
      @Nonnull BattlePhase phase,
      int turnNumber,
      @Nullable BattleAction pendingPlayerAction,
      @Nullable BattleAction pendingOpponentAction
  ) {
    this.phase = phase;
    this.turnNumber = Math.max(1, turnNumber);
    this.pendingPlayerAction = pendingPlayerAction;
    this.pendingOpponentAction = pendingOpponentAction;
  }

  @Nonnull
  public BattlePhase phase() {
    return phase;
  }

  public void setPhase(@Nonnull BattlePhase phase) {
    this.phase = phase;
  }

  public int turnNumber() {
    return turnNumber;
  }

  public void advanceTurn() {
    this.turnNumber += 1;
    this.pendingPlayerAction = null;
    this.pendingOpponentAction = null;
    this.phase = BattlePhase.PLAYER_COMMAND;
  }

  @Nullable
  public BattleAction pendingPlayerAction() {
    return pendingPlayerAction;
  }

  public void setPendingPlayerAction(@Nullable BattleAction pendingPlayerAction) {
    this.pendingPlayerAction = pendingPlayerAction;
  }

  @Nullable
  public BattleAction pendingOpponentAction() {
    return pendingOpponentAction;
  }

  public void setPendingOpponentAction(@Nullable BattleAction pendingOpponentAction) {
    this.pendingOpponentAction = pendingOpponentAction;
  }
}
