package com.hytemon.mod.interactions;

import com.hytemon.mod.HytemonMetadataKeys;
import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.capture.CaptureManager;
import com.hytemon.mod.capture.CaptureResult;
import com.hytemon.mod.capture.CaptureTarget;
import com.hytemon.mod.companion.HytemonBallPayload;
import com.hytemon.mod.player.TrainerData;
import com.hytemon.mod.player.TrainerData.PendingBallPayload;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.spatial.SpatialResource;
import com.hypixel.hytale.math.vector.Transform;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.math.vector.Vector4d;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.Entity;
import com.hypixel.hytale.server.core.entity.EntityUtils;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.TargetUtil;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bson.BsonDocument;
import org.bson.BsonString;

public class HytemonBallImpactInteraction extends SimpleInstantInteraction {
  private static final String DEFAULT_CAPTURE_ROLE = "Empty_Role";
  @Nonnull
  public static final BuilderCodec<HytemonBallImpactInteraction> CODEC = BuilderCodec.builder(
      HytemonBallImpactInteraction.class,
      HytemonBallImpactInteraction::new,
      SimpleInstantInteraction.CODEC
  ).build();

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

    Ref<EntityStore> projectileRef = interactionContext.getEntity();
    HytemonBallPayload payload = commandBuffer.getComponent(projectileRef, HytemonPlugin.get().getBallPayloadComponentType());
    Ref<EntityStore> ownerRef = payload != null ? payload.getOwnerRef() : interactionContext.getOwningEntity();
    if (ownerRef == null || !ownerRef.isValid()) {
      HytemonPlugin.get().getLogger().atWarning().log("Hytemon ball impact without valid owner.");
      commandBuffer.removeEntity(projectileRef, RemoveReason.REMOVE);
      return;
    }

    TrainerData trainerData = commandBuffer.ensureAndGetComponent(ownerRef, TrainerData.getComponentType());
    if (payload == null) {
      PendingBallPayload pending = takePendingPayload(commandBuffer, trainerData, projectileRef);
      if (pending == null) {
        logImpactDebug(commandBuffer, projectileRef, "payload_missing_pending_missing");
        sendPlayerMessage(commandBuffer, ownerRef, "Pokeball lost its payload and vanished.");
        commandBuffer.removeEntity(projectileRef, RemoveReason.REMOVE);
        return;
      }
      payload = buildPayloadFromPending(ownerRef, pending);
      logImpactDebug(commandBuffer, projectileRef, "payload_missing_pending_restored");
    } else {
      takePendingPayload(commandBuffer, trainerData, projectileRef);
      logImpactDebug(commandBuffer, projectileRef, "payload_present");
    }

    if (payload.getCaptureId() != null && !payload.getCaptureId().isBlank()) {
      if (trainerData.hasActiveCompanion()) {
        sendPlayerMessage(commandBuffer, ownerRef, "Recall your active Hytemon first.");
        commandBuffer.removeEntity(projectileRef, RemoveReason.REMOVE);
        return;
      }
      releaseCompanion(commandBuffer, ownerRef, payload, trainerData);
      commandBuffer.removeEntity(projectileRef, RemoveReason.REMOVE);
      return;
    }

    CaptureOutcome outcome = attemptCapture(commandBuffer, interactionContext, ownerRef, payload);
    if (outcome.result == CaptureResult.SUCCESS) {
      if (outcome.targetRef != null) {
        commandBuffer.removeEntity(outcome.targetRef, RemoveReason.REMOVE);
      }
      commandBuffer.removeEntity(projectileRef, RemoveReason.REMOVE);
      return;
    }

    if (outcome.result == CaptureResult.CAPTURE_INTERRUPTED_BY_BATTLE) {
      sendPlayerMessage(commandBuffer, ownerRef, "A battle begins!");
      commandBuffer.removeEntity(projectileRef, RemoveReason.REMOVE);
      return;
    }

    sendPlayerMessage(commandBuffer, ownerRef, "The capture failed.");
    commandBuffer.removeEntity(projectileRef, RemoveReason.REMOVE);
  }

  @Nonnull
  private CaptureOutcome attemptCapture(
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull InteractionContext interactionContext,
      @Nonnull Ref<EntityStore> ownerRef,
      @Nonnull HytemonBallPayload payload
  ) {
    Ref<EntityStore> targetRef = interactionContext.getTargetEntity();
    if (targetRef == null || !targetRef.isValid()) {
      targetRef = resolveTargetFromImpact(interactionContext, commandBuffer, ownerRef);
    }
    if (targetRef == null || !targetRef.isValid()) {
      sendPlayerMessage(commandBuffer, ownerRef, "No target in range.");
      return new CaptureOutcome(CaptureResult.FAILED, null);
    }
    logImpactDebug(commandBuffer, targetRef, "capture_target_found");

    Entity targetEntity = EntityUtils.getEntity(targetRef, commandBuffer);
    if (targetEntity instanceof Player) {
      sendPlayerMessage(commandBuffer, ownerRef, "You cannot capture players.");
      return new CaptureOutcome(CaptureResult.FAILED, null);
    }

    NPCEntity npcEntity = commandBuffer.getComponent(targetRef, NPCEntity.getComponentType());
    String roleId = npcEntity != null ? npcEntity.getRoleName() : null;

    ModelComponent modelComponent = commandBuffer.getComponent(targetRef, ModelComponent.getComponentType());
    String modelId = null;
    if (modelComponent != null && modelComponent.getModel() != null) {
      modelId = modelComponent.getModel().getModelAssetId();
    }

    if ((modelId == null || modelId.isBlank()) && (roleId == null || roleId.isBlank())) {
      sendPlayerMessage(commandBuffer, ownerRef, "That target cannot be captured.");
      return new CaptureOutcome(CaptureResult.FAILED, targetRef);
    }

    String entityId = roleId != null && !roleId.isBlank() ? roleId : modelId;
    String nameSource = modelId != null && !modelId.isBlank() ? modelId : entityId;
    String displayName = buildCaptureName(commandBuffer, ownerRef, entityId, nameSource);
    CaptureTarget target = new CaptureTarget(entityId, displayName, classifyDisposition(nameSource));

    PlayerRef player = commandBuffer.getComponent(ownerRef, PlayerRef.getComponentType());
    if (player == null) {
      return new CaptureOutcome(CaptureResult.FAILED, targetRef);
    }

    String ballItemId = payload.getBallItemId();
    if (ballItemId == null || ballItemId.isBlank()) {
      return new CaptureOutcome(CaptureResult.FAILED, targetRef);
    }

    ItemStack captureStack = new ItemStack(ballItemId, 1);
    CaptureManager captureManager = HytemonPlugin.get().getCaptureManager();
    CaptureResult result = captureManager.attemptCapture(commandBuffer, ownerRef, player, target, captureStack);
    if (result == CaptureResult.SUCCESS) {
      grantCapturedItem(commandBuffer, ownerRef, ballItemId, target, roleId, modelId);
      sendPlayerMessage(commandBuffer, ownerRef, "Captured " + target.displayName() + "!");
    }
    HytemonPlugin.get().getLogger().atInfo().log("Hytemon capture result: %s", result);
    return new CaptureOutcome(result, targetRef);
  }

  private void releaseCompanion(
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull Ref<EntityStore> ownerRef,
      @Nonnull HytemonBallPayload payload,
      @Nonnull TrainerData trainerData
  ) {
    String roleId = payload.getCaptureRoleId();
    String modelId = payload.getCaptureModelId();
    int roleIndex = resolveRoleIndex(roleId);
    Model spawnModel = resolveSpawnModel(modelId);
    if (roleIndex < 0) {
      sendPlayerMessage(commandBuffer, ownerRef, "Unknown creature type.");
      trainerData.clearActiveCompanion();
      return;
    }

    Transform look = TargetUtil.getLook(ownerRef, commandBuffer);
    Vector3d spawnPos = look.getPosition().clone().add(look.getDirection().normalize().scale(2.0));
    Vector3f rotation = new Vector3f(0.0f, look.getRotation().getYaw(), 0.0f);
    String captureName = Objects.requireNonNullElse(payload.getCaptureName(), roleId);
    String captureId = Objects.requireNonNullElse(payload.getCaptureId(), UUID.randomUUID().toString());

    commandBuffer.run(store -> {
      var spawned = NPCPlugin.get().spawnEntity(store, roleIndex, spawnPos, rotation, spawnModel, null, (npc, ref, refStore) -> {
        npc.getLeashPoint().assign(look.getPosition());
        npc.setLeashHeading(look.getRotation().getYaw());
        npc.setLeashPitch(look.getRotation().getPitch());
        refStore.addComponent(ref, DisplayNameComponent.getComponentType(), new DisplayNameComponent(Message.raw(captureName)));
      });
      TrainerData storedTrainer = store.ensureAndGetComponent(ownerRef, TrainerData.getComponentType());
      if (spawned != null) {
        storedTrainer.setActiveCompanion(spawned.first(), captureId);
      } else {
        storedTrainer.clearActiveCompanion();
      }
    });
  }

  private void grantCapturedItem(
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull Ref<EntityStore> playerRef,
      @Nonnull String ballItemId,
      @Nonnull CaptureTarget target,
      @Nullable String roleId,
      @Nullable String modelId
  ) {
    Player player = commandBuffer.getComponent(playerRef, Player.getComponentType());
    if (player == null) {
      return;
    }

    String captureId = UUID.randomUUID().toString();
    BsonDocument metadata = new BsonDocument();
    metadata.put(HytemonMetadataKeys.CAPTURE_ID, new BsonString(captureId));
    metadata.put(HytemonMetadataKeys.CAPTURE_NAME, new BsonString(target.displayName()));
    metadata.put(HytemonMetadataKeys.CAPTURE_DISPOSITION, new BsonString(target.disposition().name()));
    if (roleId != null) {
      metadata.put(HytemonMetadataKeys.CAPTURE_ROLE_ID, new BsonString(roleId));
    }
    if (modelId != null) {
      metadata.put(HytemonMetadataKeys.CAPTURE_MODEL_ID, new BsonString(modelId));
    }
    metadata.put(HytemonMetadataKeys.ITEM_DISPLAY_NAME, new BsonString("Pokeball (" + target.displayName() + ")"));

    ItemStack capturedStack = new ItemStack(ballItemId, 1, metadata);
    ItemContainer inventory = player.getInventory().getCombinedHotbarFirst();
    inventory.addItemStack(capturedStack);
  }

  @Nonnull
  private String buildCaptureName(
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull Ref<EntityStore> ownerRef,
      @Nonnull String entityId,
      @Nonnull String fallbackName
  ) {
    TrainerData trainerData = commandBuffer.ensureAndGetComponent(ownerRef, TrainerData.getComponentType());
    long existingCount = trainerData.getCaptures().stream()
        .filter(capture -> capture.entityId().equalsIgnoreCase(entityId))
        .count();
    String baseName = fallbackName.contains(":") ? fallbackName.substring(fallbackName.indexOf(':') + 1) : fallbackName;
    return existingCount > 0 ? baseName + " #" + (existingCount + 1) : baseName;
  }

  @Nonnull
  private CaptureTarget.Disposition classifyDisposition(@Nonnull String modelId) {
    String lower = modelId.toLowerCase(Locale.ROOT);
    if (lower.contains("goblin")
        || lower.contains("skeleton")
        || lower.contains("undead")
        || lower.contains("trork")
        || lower.contains("feran")
        || lower.contains("scarak")
        || lower.contains("outlander")
        || lower.contains("bandit")) {
      return CaptureTarget.Disposition.MONSTER;
    }
    return CaptureTarget.Disposition.ANIMAL;
  }

  private void sendPlayerMessage(
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull Ref<EntityStore> playerRef,
      @Nonnull String message
  ) {
    Player player = commandBuffer.getComponent(playerRef, Player.getComponentType());
    if (player == null) {
      return;
    }
    player.sendMessage(Message.raw(message));
  }

  @Nullable
  private Ref<EntityStore> resolveTargetFromImpact(
      @Nonnull InteractionContext context,
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull Ref<EntityStore> ownerRef
  ) {
    Vector4d hitLocation = context.getMetaStore().getIfPresentMetaObject(Interaction.HIT_LOCATION);
    Vector3d hitPosition = null;
    if (hitLocation != null) {
      hitPosition = new Vector3d(hitLocation.x, hitLocation.y, hitLocation.z);
    } else {
      TransformComponent transform = commandBuffer.getComponent(context.getEntity(), TransformComponent.getComponentType());
      if (transform != null) {
        hitPosition = transform.getPosition().clone();
      }
    }
    if (hitPosition == null) {
      return null;
    }
    SpatialResource<Ref<EntityStore>, EntityStore> spatialResource =
        (SpatialResource<Ref<EntityStore>, EntityStore>) commandBuffer.getResource(
            EntityModule.get().getNetworkSendableSpatialResourceType()
        );
    if (spatialResource == null) {
      return null;
    }
    ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
    spatialResource.getSpatialStructure().collect(hitPosition, 6.0D, (List) results);

    Ref<EntityStore> best = null;
    double bestDistance = Double.MAX_VALUE;
    for (Ref<EntityStore> ref : results) {
      if (ref == null || !ref.isValid() || ref.equals(ownerRef) || ref.equals(context.getEntity())) {
        continue;
      }
      if (commandBuffer.getArchetype(ref).contains(Player.getComponentType())) {
        continue;
      }
      TransformComponent transform = commandBuffer.getComponent(ref, TransformComponent.getComponentType());
      if (transform == null) {
        continue;
      }
      double distance = transform.getPosition().distanceSquaredTo(hitPosition);
      if (distance < bestDistance) {
        bestDistance = distance;
        best = ref;
      }
    }
    results.clear();
    return best;
  }

  @Nullable
  private PendingBallPayload takePendingPayload(
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull TrainerData trainerData,
      @Nonnull Ref<EntityStore> projectileRef
  ) {
    NetworkId networkId = commandBuffer.getComponent(projectileRef, NetworkId.getComponentType());
    if (networkId == null) {
      return null;
    }
    return trainerData.removePendingBallPayload(networkId.getId());
  }

  @Nonnull
  private HytemonBallPayload buildPayloadFromPending(
      @Nonnull Ref<EntityStore> ownerRef,
      @Nonnull PendingBallPayload pending
  ) {
    HytemonBallPayload payload = new HytemonBallPayload();
    payload.setOwnerRef(ownerRef);
    payload.setBallItemId(pending.getBallItemId());
    payload.setCaptureId(pending.getCaptureId());
    payload.setCaptureName(pending.getCaptureName());
    payload.setCaptureRoleId(pending.getCaptureRoleId());
    payload.setCaptureDisposition(pending.getCaptureDisposition());
    payload.setCaptureModelId(pending.getCaptureModelId());
    return payload;
  }

  private int resolveRoleIndex(@Nullable String roleId) {
    if (roleId != null && !roleId.isBlank()) {
      int roleIndex = NPCPlugin.get().getIndex(roleId);
      if (roleIndex >= 0) {
        return roleIndex;
      }
    }
    return NPCPlugin.get().getIndex(DEFAULT_CAPTURE_ROLE);
  }

  @Nullable
  private Model resolveSpawnModel(@Nullable String modelId) {
    if (modelId == null || modelId.isBlank()) {
      return null;
    }
    ModelAsset asset = (ModelAsset) ModelAsset.getAssetMap().getAsset(modelId);
    if (asset == null) {
      return null;
    }
    return Model.createScaledModel(asset, 1.0f);
  }

  private void logImpactDebug(
      @Nonnull CommandBuffer<EntityStore> commandBuffer,
      @Nonnull Ref<EntityStore> ref,
      @Nonnull String marker
  ) {
    NetworkId networkId = commandBuffer.getComponent(ref, NetworkId.getComponentType());
    Integer id = networkId != null ? networkId.getId() : null;
    HytemonPlugin.get().getLogger().atInfo().log("Hytemon impact debug: %s netId=%s", marker, id);
  }

  private record CaptureOutcome(@Nonnull CaptureResult result, @Nullable Ref<EntityStore> targetRef) {}
}
