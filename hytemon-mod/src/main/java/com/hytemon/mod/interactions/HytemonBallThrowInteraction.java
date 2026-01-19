package com.hytemon.mod.interactions;

import com.hytemon.mod.HytemonItems;
import com.hytemon.mod.HytemonMetadataKeys;
import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.companion.HytemonBallPayload;
import com.hytemon.mod.player.TrainerData;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.Direction;
import com.hypixel.hytale.protocol.Interaction;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionSyncData;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.protocol.WaitForDataFrom;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.modules.projectile.ProjectileModule;
import com.hypixel.hytale.server.core.modules.projectile.config.ProjectileConfig;
import com.hypixel.hytale.server.core.util.PositionUtil;
import com.hypixel.hytale.server.core.util.TargetUtil;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HytemonBallThrowInteraction extends SimpleInstantInteraction {
  @Nonnull
  public static final BuilderCodec<HytemonBallThrowInteraction> CODEC = BuilderCodec.builder(
      HytemonBallThrowInteraction.class,
      HytemonBallThrowInteraction::new,
      SimpleInstantInteraction.CODEC
  ).appendInherited(
      new KeyedCodec<>("Config", (Codec) Codec.STRING),
      (interaction, value) -> interaction.configId = value,
      interaction -> interaction.configId,
      (interaction, parent) -> interaction.configId = parent.configId
  ).add().build();

  @Nullable
  private String configId;

  @Override
  protected void firstRun(
      @Nonnull InteractionType interactionType,
      @Nonnull InteractionContext interactionContext,
      @Nonnull CooldownHandler cooldownHandler
  ) {
    CommandBuffer<EntityStore> commandBuffer = interactionContext.getCommandBuffer();
    if (commandBuffer == null) {
      return;
    }

    Ref<EntityStore> playerRef = interactionContext.getOwningEntity();
    if (playerRef == null || !playerRef.isValid()) {
      return;
    }

    ItemStack heldItem = interactionContext.getHeldItem();
    if (heldItem == null || heldItem.isEmpty()) {
      return;
    }

    TrainerData trainerData = commandBuffer.ensureAndGetComponent(playerRef, TrainerData.getComponentType());
    BallMetadata metadata = readBallMetadata(heldItem);
    if (metadata.isCaptured() && trainerData.hasActiveCapture()) {
      if (metadata.captureId != null && metadata.captureId.equals(trainerData.getActiveCaptureId())) {
        Ref<EntityStore> activeRef = trainerData.getActiveCompanionRef();
        if (activeRef != null && activeRef.isValid()) {
          commandBuffer.removeEntity(activeRef, com.hypixel.hytale.component.RemoveReason.REMOVE);
        }
        trainerData.clearActiveCompanion();
        String name = metadata.captureName != null ? metadata.captureName : "Hytemon";
        sendPlayerMessage(commandBuffer, playerRef, "Returned " + name + ".");
      } else {
        sendPlayerMessage(commandBuffer, playerRef, "Recall your active Hytemon first.");
      }
      interactionContext.getState().state = InteractionState.Failed;
      return;
    }

    ProjectileConfig config = resolveConfig(heldItem);
    if (config == null) {
      return;
    }

    SpawnData spawnData = resolveSpawnData(interactionContext, commandBuffer, playerRef);
    if (metadata.isCaptured() && metadata.captureId != null) {
      trainerData.setActiveCaptureId(metadata.captureId);
    }

    Ref<EntityStore> projectileRef = ProjectileModule.get().spawnProjectile(
        spawnData.generatedUuid,
        playerRef,
        commandBuffer,
        config,
        spawnData.position,
        spawnData.direction
    );

    if (projectileRef == null) {
      HytemonPlugin.get().getLogger().atInfo().log("Hytemon ball throw failed to spawn projectile.");
      if (metadata.isCaptured() && metadata.captureId != null) {
        trainerData.clearActiveCompanion();
      }
      return;
    }

    HytemonBallPayload payload = new HytemonBallPayload();
    payload.setOwnerRef(playerRef);
    payload.setBallItemId(heldItem.getItemId());
    payload.setCaptureId(metadata.captureId);
    payload.setCaptureName(metadata.captureName);
    payload.setCaptureRoleId(metadata.captureRoleId);
    payload.setCaptureDisposition(metadata.captureDisposition);
    payload.setCaptureModelId(metadata.captureModelId);
    commandBuffer.addComponent(projectileRef, HytemonPlugin.get().getBallPayloadComponentType(), payload);
    TrainerData.PendingBallPayload pendingPayload = new TrainerData.PendingBallPayload(
        heldItem.getItemId(),
        metadata.captureId,
        metadata.captureName,
        metadata.captureRoleId,
        metadata.captureDisposition,
        metadata.captureModelId
    );
    commandBuffer.run(store -> {
      NetworkId networkId = store.getComponent(projectileRef, NetworkId.getComponentType());
      if (networkId == null) {
        HytemonPlugin.get().getLogger().atWarning().log("Hytemon ball spawned without NetworkId.");
        return;
      }
      TrainerData storedTrainer = store.getComponent(playerRef, TrainerData.getComponentType());
      if (storedTrainer != null) {
        storedTrainer.putPendingBallPayload(networkId.getId(), pendingPayload);
      }
      HytemonPlugin.get().getLogger().atInfo().log(
          "Hytemon ball spawned. netId=%d item=%s captureId=%s role=%s",
          networkId.getId(),
          heldItem.getItemId(),
          metadata.captureId,
          metadata.captureRoleId
      );
    });

    if (metadata.isCaptured()) {
      interactionContext.getState().state = InteractionState.Failed;
    }
  }

  @Nonnull
  @Override
  public WaitForDataFrom getWaitForDataFrom() {
    return WaitForDataFrom.Client;
  }

  @Override
  public boolean needsRemoteSync() {
    return true;
  }

  @Nonnull
  @Override
  protected Interaction generatePacket() {
    return (Interaction) new com.hypixel.hytale.protocol.ProjectileInteraction();
  }

  @Override
  protected void configurePacket(Interaction packet) {
    super.configurePacket(packet);
    com.hypixel.hytale.protocol.ProjectileInteraction p = (com.hypixel.hytale.protocol.ProjectileInteraction) packet;
    if (configId == null || configId.isBlank()) {
      throw new IllegalStateException("HytemonBallThrowInteraction has no Config.");
    }
    p.configId = configId;
  }

  @Nullable
  private ProjectileConfig resolveConfig(@Nonnull ItemStack itemStack) {
    if (configId != null && !configId.isBlank()) {
      return (ProjectileConfig) ProjectileConfig.getAssetMap().getAsset(configId);
    }
    String itemId = itemStack.getItemId();
    String fallbackConfig = switch (itemId) {
      case HytemonItems.GREATBALL -> "Projectile_Config_Hytemon_Greatball";
      case HytemonItems.ULTRABALL -> "Projectile_Config_Hytemon_Ultraball";
      default -> "Projectile_Config_Hytemon_Pokeball";
    };
    return (ProjectileConfig) ProjectileConfig.getAssetMap().getAsset(fallbackConfig);
  }

  @Nonnull
  private SpawnData resolveSpawnData(
      @Nonnull InteractionContext context,
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull Ref<EntityStore> playerRef
  ) {
    InteractionSyncData clientState = context.getClientState();
    if (clientState != null && clientState.attackerPos != null && clientState.attackerRot != null) {
      Vector3d position = PositionUtil.toVector3d(clientState.attackerPos);
      Vector3f lookVec = PositionUtil.toRotation(clientState.attackerRot);
      Vector3d direction = new Vector3d(lookVec.getYaw(), lookVec.getPitch());
      return new SpawnData(position, direction, clientState.generatedUUID);
    }

    Transform look = TargetUtil.getLook(playerRef, commandBuffer);
    return new SpawnData(look.getPosition(), look.getDirection(), null);
  }

  @Nonnull
  private BallMetadata readBallMetadata(@Nonnull ItemStack itemStack) {
    String captureId = itemStack.getFromMetadataOrNull(HytemonMetadataKeys.CAPTURE_ID, Codec.STRING);
    String captureName = itemStack.getFromMetadataOrNull(HytemonMetadataKeys.CAPTURE_NAME, Codec.STRING);
    String captureRoleId = itemStack.getFromMetadataOrNull(HytemonMetadataKeys.CAPTURE_ROLE_ID, Codec.STRING);
    String captureDisposition = itemStack.getFromMetadataOrNull(HytemonMetadataKeys.CAPTURE_DISPOSITION, Codec.STRING);
    String captureModelId = itemStack.getFromMetadataOrNull(HytemonMetadataKeys.CAPTURE_MODEL_ID, Codec.STRING);
    return new BallMetadata(captureId, captureName, captureRoleId, captureDisposition, captureModelId);
  }

  private void sendPlayerMessage(
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull Ref<EntityStore> playerRef,
      @Nonnull String message
  ) {
    com.hypixel.hytale.server.core.entity.entities.Player player =
        commandBuffer.getComponent(playerRef, com.hypixel.hytale.server.core.entity.entities.Player.getComponentType());
    if (player != null) {
      player.sendMessage(Message.raw(message));
    }
  }

  private record BallMetadata(
      @Nullable String captureId,
      @Nullable String captureName,
      @Nullable String captureRoleId,
      @Nullable String captureDisposition,
      @Nullable String captureModelId
  ) {
    boolean isCaptured() {
      return captureId != null && !captureId.isBlank();
    }
  }

  private record SpawnData(@Nonnull Vector3d position, @Nonnull Vector3d direction, @Nullable UUID generatedUuid) {}
}
