package com.hytemon.mod;

import com.hytemon.mod.battle.BattleManager;
import com.hytemon.mod.capture.CaptureManager;
import com.hytemon.mod.commands.HytemonCommand;
import com.hytemon.mod.npc.TraderNpcSpawner;
import com.hytemon.mod.player.TrainerData;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HytemonPlugin extends JavaPlugin {
  private static HytemonPlugin instance;
  @Nullable
  private ComponentType<EntityStore, TrainerData> trainerDataComponentType;
  @Nullable
  private CaptureManager captureManager;
  @Nullable
  private BattleManager battleManager;
  @Nullable
  private TraderNpcSpawner traderNpcSpawner;

  public HytemonPlugin(@Nonnull JavaPluginInit init) {
    super(init);
  }

  @Nonnull
  public static HytemonPlugin get() {
    return Objects.requireNonNull(instance, "HytemonPlugin not initialized");
  }

  @Nonnull
  public ComponentType<EntityStore, TrainerData> getTrainerDataComponentType() {
    return Objects.requireNonNull(trainerDataComponentType, "Trainer data component not registered");
  }

  @Nonnull
  public CaptureManager getCaptureManager() {
    return Objects.requireNonNull(captureManager, "Capture manager not initialized");
  }

  @Nonnull
  public BattleManager getBattleManager() {
    return Objects.requireNonNull(battleManager, "Battle manager not initialized");
  }

  @Nonnull
  public TraderNpcSpawner getTraderNpcSpawner() {
    return Objects.requireNonNull(traderNpcSpawner, "Trader spawner not initialized");
  }

  @Override
  protected void setup() {
    instance = this;

    CommandRegistry commandRegistry = getCommandRegistry();
    this.trainerDataComponentType = EntityStore.REGISTRY.registerComponent(TrainerData.class, TrainerData::new);
    this.captureManager = new CaptureManager(this);
    this.battleManager = new BattleManager(this);
    this.traderNpcSpawner = new TraderNpcSpawner(this);

    commandRegistry.registerCommand(new HytemonCommand(this));
  }

  @Override
  protected void start() {
    getLogger().atInfo().log("Hytemon plugin started");
  }

  @Override
  protected void shutdown() {
    getLogger().atInfo().log("Hytemon plugin stopped");
  }
}
