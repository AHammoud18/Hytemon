package com.hytemon.mod.capture;

import com.hytemon.mod.HytemonItems;
import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.battle.BattleManager;
import com.hytemon.mod.player.TrainerData;
import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nonnull;

public class CaptureManager {
  private final HytemonPlugin plugin;
  private final BattleManager battleManager;

  public CaptureManager(@Nonnull HytemonPlugin plugin) {
    this.plugin = plugin;
    this.battleManager = plugin.getBattleManager();
  }

  @Nonnull
  public CaptureResult attemptCapture(
      @Nonnull ComponentAccessor<EntityStore> componentAccessor,
      @Nonnull Ref<EntityStore> playerRef,
      @Nonnull PlayerRef player,
      @Nonnull CaptureTarget target,
      @Nonnull ItemStack captureItem
  ) {
    Objects.requireNonNull(target, "target");
    TrainerData trainerData = componentAccessor.ensureAndGetComponent(
        playerRef,
        TrainerData.getComponentType()
    );

    if (battleManager.shouldStartBattle(target)) {
      battleManager.beginEncounter(componentAccessor, playerRef, player, target);
      return CaptureResult.CAPTURE_INTERRUPTED_BY_BATTLE;
    }

    double chance = baseCaptureChance(target) * captureItemMultiplier(captureItem);
    double roll = ThreadLocalRandom.current().nextDouble();
    if (roll <= chance) {
      trainerData.addCapture(target);
      return CaptureResult.SUCCESS;
    }

    return CaptureResult.FAILED;
  }

  public void throwCaptureItem(
      @Nonnull ComponentAccessor<EntityStore> componentAccessor,
      @Nonnull Ref<EntityStore> playerRef,
      @Nonnull ItemStack captureItem
  ) {
    // TODO: Wire this into the Interaction system to spawn a projectile and sync with clients.
    // For example, register a RootInteraction asset that triggers ProjectileSpawn and
    // use InteractionManager.startChain to drive the throw animation.
    componentAccessor.ensureAndGetComponent(playerRef, TrainerData.getComponentType());
  }

  @Nonnull
  public CaptureThrow buildThrowProfile(@Nonnull ItemStack captureItem) {
    String itemId = captureItem.getItemId();
    String projectileId = switch (itemId) {
      case HytemonItems.GREATBALL -> HytemonItems.GREATBALL_PROJECTILE;
      case HytemonItems.ULTRABALL -> HytemonItems.ULTRABALL_PROJECTILE;
      default -> HytemonItems.POKEBALL_PROJECTILE;
    };
    float speed = itemId.equals(HytemonItems.ULTRABALL) ? 1.4f : 1.1f;
    float arcHeight = itemId.equals(HytemonItems.POKEBALL) ? 0.45f : 0.35f;

    return new CaptureThrow(itemId, projectileId, speed, arcHeight);
  }

  private double baseCaptureChance(@Nonnull CaptureTarget target) {
    return switch (target.disposition()) {
      case ANIMAL -> 0.45;
      case MONSTER -> 0.2;
    };
  }

  private double captureItemMultiplier(@Nonnull ItemStack captureItem) {
    String itemId = captureItem.getItemId();
    return switch (itemId) {
      case HytemonItems.GREATBALL -> 1.4;
      case HytemonItems.ULTRABALL -> 1.8;
      default -> 1.0;
    };
  }

  @Nonnull
  public String sanitizeCaptureId(@Nonnull String id) {
    return id.trim().toLowerCase(Locale.ROOT);
  }
}
