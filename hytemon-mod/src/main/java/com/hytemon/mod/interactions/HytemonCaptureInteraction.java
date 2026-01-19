package com.hytemon.mod.interactions;

import com.hytemon.mod.HytemonMetadataKeys;
import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.capture.CaptureManager;
import com.hytemon.mod.capture.CaptureResult;
import com.hytemon.mod.capture.CaptureTarget;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.Entity;
import com.hypixel.hytale.server.core.entity.EntityUtils;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.TargetUtil;
import com.hypixel.hytale.component.RemoveReason;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bson.BsonDocument;
import org.bson.BsonString;

public class HytemonCaptureInteraction extends SimpleInstantInteraction {
  @Nonnull
  public static final BuilderCodec<HytemonCaptureInteraction> CODEC = BuilderCodec.builder(
      HytemonCaptureInteraction.class,
      HytemonCaptureInteraction::new,
      SimpleInstantInteraction.CODEC
  ).appendInherited(
      new KeyedCodec<>("BallItemId", (Codec) Codec.STRING),
      (interaction, value) -> interaction.ballItemId = value,
      interaction -> interaction.ballItemId,
      (interaction, parent) -> interaction.ballItemId = parent.ballItemId
  ).add().build();

  @Nullable
  private String ballItemId;

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
    Ref<EntityStore> targetRef = interactionContext.getTargetEntity();
    if (targetRef == null || !targetRef.isValid()) {
      targetRef = TargetUtil.getTargetEntity(playerRef, 8.0f, commandBuffer);
    }
    if (targetRef == null || !targetRef.isValid()) {
      sendPlayerMessage(commandBuffer, playerRef, "No target in range.");
      return;
    }

    Entity targetEntity = EntityUtils.getEntity(targetRef, commandBuffer);
    if (targetEntity instanceof Player) {
      sendPlayerMessage(commandBuffer, playerRef, "You cannot capture players.");
      return;
    }

    ModelComponent modelComponent = commandBuffer.getComponent(targetRef, ModelComponent.getComponentType());
    if (modelComponent == null || modelComponent.getModel() == null) {
      sendPlayerMessage(commandBuffer, playerRef, "That target cannot be captured.");
      return;
    }

    String modelId = modelComponent.getModel().getModelAssetId();
    if (modelId == null || modelId.isBlank()) {
      sendPlayerMessage(commandBuffer, playerRef, "That target cannot be captured.");
      return;
    }

    NPCEntity npcEntity = commandBuffer.getComponent(targetRef, NPCEntity.getComponentType());
    String roleId = npcEntity != null ? npcEntity.getRoleName() : null;
    String entityId = roleId != null ? roleId : modelId;
    CaptureTarget target = new CaptureTarget(entityId, entityId, classifyDisposition(modelId));
    PlayerRef player = commandBuffer.getComponent(playerRef, PlayerRef.getComponentType());
    if (player == null) {
      return;
    }

    String resolvedItemId = resolveBallItemId(heldItem);
    if (resolvedItemId == null) {
      sendPlayerMessage(commandBuffer, playerRef, "No capture item available.");
      return;
    }

    ItemStack captureStack = new ItemStack(resolvedItemId, 1);
    CaptureManager captureManager = HytemonPlugin.get().getCaptureManager();
    CaptureResult result = captureManager.attemptCapture(commandBuffer, playerRef, player, target, captureStack);

    if (result == CaptureResult.SUCCESS) {
      commandBuffer.removeEntity(targetRef, RemoveReason.REMOVE);
      grantCapturedItem(commandBuffer, playerRef, resolvedItemId, target, roleId, modelId);
      sendPlayerMessage(commandBuffer, playerRef, "Captured " + target.displayName() + "!");
      return;
    }

    if (result == CaptureResult.CAPTURE_INTERRUPTED_BY_BATTLE) {
      sendPlayerMessage(commandBuffer, playerRef, "A battle begins!");
      return;
    }

    if (result == CaptureResult.TARGET_ALREADY_CAPTURED) {
      sendPlayerMessage(commandBuffer, playerRef, "You already captured this creature.");
      return;
    }

    sendPlayerMessage(commandBuffer, playerRef, "The capture failed.");
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

    BsonDocument metadata = new BsonDocument();
    metadata.put(HytemonMetadataKeys.CAPTURE_ID, new BsonString(UUID.randomUUID().toString()));
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

  @Nullable
  private String resolveBallItemId(@Nullable ItemStack heldItem) {
    if (ballItemId != null && !ballItemId.isBlank()) {
      return ballItemId;
    }
    if (heldItem == null || heldItem.isEmpty()) {
      return null;
    }
    return heldItem.getItemId();
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
}
