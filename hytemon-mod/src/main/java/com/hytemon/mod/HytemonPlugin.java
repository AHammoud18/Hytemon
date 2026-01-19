package com.hytemon.mod;

import com.hytemon.mod.battle.BattleManager;
import com.hytemon.mod.capture.CaptureManager;
import com.hytemon.mod.commands.HytemonCommand;
import com.hytemon.mod.companion.HytemonBallPayload;
import com.hytemon.mod.interactions.HytemonBallImpactInteraction;
import com.hytemon.mod.interactions.HytemonBallThrowInteraction;
import com.hytemon.mod.interactions.HytemonCaptureInteraction;
import com.hytemon.mod.npc.TraderNpcSpawner;
import com.hytemon.mod.player.TrainerData;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
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
  @Nullable
  private ComponentType<EntityStore, HytemonBallPayload> ballPayloadComponentType;
  @Nullable
  private ScheduledFuture<Void> companionTask;

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

  @Nonnull
  public ComponentType<EntityStore, HytemonBallPayload> getBallPayloadComponentType() {
    return Objects.requireNonNull(ballPayloadComponentType, "Ball payload component not registered");
  }

  @Override
  protected void setup() {
    instance = this;

    CommandRegistry commandRegistry = getCommandRegistry();
    this.trainerDataComponentType = EntityStore.REGISTRY.registerComponent(TrainerData.class, TrainerData::new);
    this.ballPayloadComponentType = EntityStore.REGISTRY.registerComponent(HytemonBallPayload.class, HytemonBallPayload::new);
    this.battleManager = new BattleManager(this);
    this.captureManager = new CaptureManager(this);
    this.traderNpcSpawner = new TraderNpcSpawner(this);

    getCodecRegistry(Interaction.CODEC).register(
        "hytemon_capture",
        HytemonCaptureInteraction.class,
        HytemonCaptureInteraction.CODEC
    );
    getCodecRegistry(Interaction.CODEC).register(
        "hytemon_ball_throw",
        HytemonBallThrowInteraction.class,
        HytemonBallThrowInteraction.CODEC
    );
    getCodecRegistry(Interaction.CODEC).register(
        "hytemon_ball_impact",
        HytemonBallImpactInteraction.class,
        HytemonBallImpactInteraction.CODEC
    );

    commandRegistry.registerCommand(new HytemonCommand(this));
  }

  @Override
  protected void start() {
    getLogger().atInfo().log("Hytemon plugin started");
    startCompanionUpdater();
  }

  @Override
  protected void shutdown() {
    getLogger().atInfo().log("Hytemon plugin stopped");
    if (companionTask != null) {
      companionTask.cancel(false);
      companionTask = null;
    }
  }

  private void startCompanionUpdater() {
    companionTask = (ScheduledFuture<Void>) HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(
        () -> Universe.get().getWorlds().forEach((name, world) -> world.execute(() -> updateCompanions(world))),
        500L,
        500L,
        TimeUnit.MILLISECONDS
    );
    getTaskRegistry().registerTask(companionTask);
  }

  private void updateCompanions(@Nonnull World world) {
    EntityStore entityStore = world.getEntityStore();
    entityStore.getStore().forEachChunk(
        (java.util.function.BiConsumer<ArchetypeChunk<EntityStore>, CommandBuffer<EntityStore>>)
            (chunk, commandBuffer) -> updateChunkCompanions(chunk, commandBuffer)
    );
  }

  private void updateChunkCompanions(
      @Nonnull ArchetypeChunk<EntityStore> chunk,
      @Nonnull CommandBuffer<EntityStore> commandBuffer
  ) {
    for (int i = 0; i < chunk.size(); i++) {
      TrainerData trainerData = chunk.getComponent(i, TrainerData.getComponentType());
      if (trainerData == null || !trainerData.hasActiveCompanion()) {
        continue;
      }
      TransformComponent playerTransform = chunk.getComponent(i, TransformComponent.getComponentType());
      if (playerTransform == null) {
        continue;
      }
      Ref<EntityStore> companionRef = trainerData.getActiveCompanionRef();
      if (companionRef == null || !companionRef.isValid()) {
        trainerData.clearActiveCompanion();
        continue;
      }
      NPCEntity companion = commandBuffer.getComponent(companionRef, NPCEntity.getComponentType());
      if (companion == null) {
        trainerData.clearActiveCompanion();
        continue;
      }
      companion.getLeashPoint().assign(playerTransform.getPosition());
      companion.setLeashHeading(playerTransform.getRotation().getYaw());
      companion.setLeashPitch(playerTransform.getRotation().getPitch());
    }
  }
}
