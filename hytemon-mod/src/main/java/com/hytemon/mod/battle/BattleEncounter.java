package com.hytemon.mod.battle;

import com.hytemon.mod.capture.CaptureTarget;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import javax.annotation.Nonnull;

public record BattleEncounter(
    @Nonnull PlayerRef player,
    @Nonnull CaptureTarget target,
    long startedAtTick
) {}
