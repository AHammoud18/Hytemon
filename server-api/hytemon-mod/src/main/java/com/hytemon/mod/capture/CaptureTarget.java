package com.hytemon.mod.capture;

import javax.annotation.Nonnull;

public record CaptureTarget(
    @Nonnull String entityId,
    @Nonnull String displayName,
    @Nonnull Disposition disposition
) {
  public enum Disposition {
    ANIMAL,
    MONSTER
  }
}
