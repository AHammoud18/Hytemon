package com.hytemon.mod.battle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record BattleAction(
    @Nonnull Type type,
    @Nonnull Actor actor,
    @Nullable String moveId,
    @Nullable String itemId
) {
  public enum Type {
    ATTACK,
    ITEM,
    SWITCH,
    RUN,
    CAPTURE
  }

  public enum Actor {
    PLAYER,
    OPPONENT
  }
}
