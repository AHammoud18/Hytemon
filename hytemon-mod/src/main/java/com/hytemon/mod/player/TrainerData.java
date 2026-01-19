package com.hytemon.mod.player;

import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.battle.BattleEncounter;
import com.hytemon.mod.battle.BattleState;
import com.hytemon.mod.capture.CaptureTarget;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TrainerData implements Component<EntityStore> {
  @Nonnull
  private final List<CaptureTarget> captures = new ArrayList<>();
  @Nonnull
  private final Map<Integer, PendingBallPayload> pendingBallPayloads = new HashMap<>();
  @Nullable
  private BattleEncounter activeEncounter;
  @Nullable
  private com.hypixel.hytale.component.Ref<EntityStore> activeCompanionRef;
  @Nullable
  private String activeCaptureId;

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

  public void setActiveCompanion(@Nonnull com.hypixel.hytale.component.Ref<EntityStore> ref, @Nonnull String captureId) {
    this.activeCompanionRef = ref;
    this.activeCaptureId = captureId;
  }

  public void setActiveCaptureId(@Nonnull String captureId) {
    this.activeCaptureId = captureId;
  }

  public void clearActiveCompanion() {
    this.activeCompanionRef = null;
    this.activeCaptureId = null;
  }

  public boolean hasActiveCompanion() {
    return activeCompanionRef != null && activeCompanionRef.isValid();
  }

  public boolean hasActiveCapture() {
    return activeCaptureId != null;
  }

  @Nullable
  public com.hypixel.hytale.component.Ref<EntityStore> getActiveCompanionRef() {
    return activeCompanionRef;
  }

  @Nullable
  public String getActiveCaptureId() {
    return activeCaptureId;
  }

  public void putPendingBallPayload(int projectileNetworkId, @Nonnull PendingBallPayload payload) {
    pendingBallPayloads.put(projectileNetworkId, payload);
  }

  @Nullable
  public PendingBallPayload removePendingBallPayload(int projectileNetworkId) {
    return pendingBallPayloads.remove(projectileNetworkId);
  }

  @Override
  public Component<EntityStore> clone() {
    TrainerData clone = new TrainerData();
    clone.captures.addAll(this.captures);
    if (this.activeEncounter != null) {
      clone.activeEncounter = cloneEncounter(this.activeEncounter);
    }
    clone.activeCompanionRef = this.activeCompanionRef;
    clone.activeCaptureId = this.activeCaptureId;
    clone.pendingBallPayloads.putAll(this.pendingBallPayloads);
    return clone;
  }

  @Nonnull
  private BattleEncounter cloneEncounter(@Nonnull BattleEncounter encounter) {
    BattleState state = new BattleState(
        encounter.state().phase(),
        encounter.state().turnNumber(),
        encounter.state().pendingPlayerAction(),
        encounter.state().pendingOpponentAction()
    );

    BattleEncounter clone = new BattleEncounter(
        encounter.player(),
        encounter.target(),
        encounter.startedAtTick(),
        state
    );
    clone.setLastResolvedAction(encounter.lastResolvedAction());
    return clone;
  }

  public static final class PendingBallPayload {
    @Nonnull
    private final String ballItemId;
    @Nullable
    private final String captureId;
    @Nullable
    private final String captureName;
    @Nullable
    private final String captureRoleId;
    @Nullable
    private final String captureDisposition;
    @Nullable
    private final String captureModelId;

    public PendingBallPayload(
        @Nonnull String ballItemId,
        @Nullable String captureId,
        @Nullable String captureName,
        @Nullable String captureRoleId,
        @Nullable String captureDisposition,
        @Nullable String captureModelId
    ) {
      this.ballItemId = ballItemId;
      this.captureId = captureId;
      this.captureName = captureName;
      this.captureRoleId = captureRoleId;
      this.captureDisposition = captureDisposition;
      this.captureModelId = captureModelId;
    }

    @Nonnull
    public String getBallItemId() {
      return ballItemId;
    }

    @Nullable
    public String getCaptureId() {
      return captureId;
    }

    @Nullable
    public String getCaptureName() {
      return captureName;
    }

    @Nullable
    public String getCaptureRoleId() {
      return captureRoleId;
    }

    @Nullable
    public String getCaptureDisposition() {
      return captureDisposition;
    }

    @Nullable
    public String getCaptureModelId() {
      return captureModelId;
    }
  }
}
