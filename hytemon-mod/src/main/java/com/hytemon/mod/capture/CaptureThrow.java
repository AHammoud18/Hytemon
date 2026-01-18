package com.hytemon.mod.capture;

import javax.annotation.Nonnull;

public record CaptureThrow(
    @Nonnull String itemId,
    @Nonnull String projectileAssetId,
    float speed,
    float arcHeight
) {}
