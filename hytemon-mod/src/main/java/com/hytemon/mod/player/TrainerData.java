package com.hytemon.mod.player;

import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.battle.BattleEncounter;
import com.hytemon.mod.capture.CaptureTarget;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TrainerData implements Component<EntityStore> {
  @Nonnull
  private final List<CaptureTarget> captures = new ArrayList<>();
  @Nullable
  private BattleEncounter activeEncounter;

  @Nonnull
  public static ComponentType<EntityStore, TrainerData> getComponentType() {
    return HytemonPlugin.get().getTrainerDataComponentType();
  }

  public void addCapture(@Nonnull CaptureTarget target) {
    captures.add(target);
  }

  @Nonnull
  public List<CaptureTarget> getCaptures() {
    return Collections.unmodifiableList(captures);
  }

  public boolean isInBattle() {
    return activeEncounter != null;
  }

  @Nullable
  public BattleEncounter getActiveEncounter() {
    return activeEncounter;
  }

  public void beginEncounter(@Nonnull BattleEncounter encounter) {
    this.activeEncounter = encounter;
  }

  public void clearEncounter() {
    this.activeEncounter = null;
  }

  @Override
  public Component<EntityStore> clone() {
    TrainerData clone = new TrainerData();
    clone.captures.addAll(this.captures);
    clone.activeEncounter = this.activeEncounter;
    return clone;
  }
}
