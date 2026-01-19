package com.hytemon.mod.companion;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nullable;

public class HytemonBallPayload implements Component<EntityStore> {
  @Nullable
  private Ref<EntityStore> ownerRef;
  @Nullable
  private String ballItemId;
  @Nullable
  private String captureId;
  @Nullable
  private String captureName;
  @Nullable
  private String captureRoleId;
  @Nullable
  private String captureDisposition;
  @Nullable
  private String captureModelId;

  public void setOwnerRef(@Nullable Ref<EntityStore> ownerRef) {
    this.ownerRef = ownerRef;
  }

  @Nullable
  public Ref<EntityStore> getOwnerRef() {
    return ownerRef;
  }

  public void setBallItemId(@Nullable String ballItemId) {
    this.ballItemId = ballItemId;
  }

  @Nullable
  public String getBallItemId() {
    return ballItemId;
  }

  public void setCaptureId(@Nullable String captureId) {
    this.captureId = captureId;
  }

  @Nullable
  public String getCaptureId() {
    return captureId;
  }

  public void setCaptureName(@Nullable String captureName) {
    this.captureName = captureName;
  }

  @Nullable
  public String getCaptureName() {
    return captureName;
  }

  public void setCaptureRoleId(@Nullable String captureRoleId) {
    this.captureRoleId = captureRoleId;
  }

  @Nullable
  public String getCaptureRoleId() {
    return captureRoleId;
  }

  public void setCaptureDisposition(@Nullable String captureDisposition) {
    this.captureDisposition = captureDisposition;
  }

  @Nullable
  public String getCaptureDisposition() {
    return captureDisposition;
  }

  public void setCaptureModelId(@Nullable String captureModelId) {
    this.captureModelId = captureModelId;
  }

  @Nullable
  public String getCaptureModelId() {
    return captureModelId;
  }

  @Override
  public Component<EntityStore> clone() {
    HytemonBallPayload clone = new HytemonBallPayload();
    clone.ownerRef = this.ownerRef;
    clone.ballItemId = this.ballItemId;
    clone.captureId = this.captureId;
    clone.captureName = this.captureName;
    clone.captureRoleId = this.captureRoleId;
    clone.captureDisposition = this.captureDisposition;
    clone.captureModelId = this.captureModelId;
    return clone;
  }
}
