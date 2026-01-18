package com.hytemon.mod.commands;

import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.npc.TraderNpcSpawner;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.Pair;
import javax.annotation.Nonnull;

public class HytemonSpawnTraderCommand extends AbstractPlayerCommand {
  @Nonnull
  private final HytemonPlugin plugin;

  public HytemonSpawnTraderCommand(@Nonnull HytemonPlugin plugin) {
    super("Spawn a Hytemon trader NPC");
    this.plugin = plugin;
    addAliases(new String[] { "spawntrader" });
  }

  @Override
  protected void execute(
      @Nonnull CommandContext context,
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> ref,
      @Nonnull PlayerRef playerRef,
      @Nonnull World world
  ) {
    TransformComponent transform = (TransformComponent) store.getComponent(ref, TransformComponent.getComponentType());
    if (transform == null) {
      context.sender().sendMessage(Message.raw("Unable to determine player position."));
      return;
    }

    Vector3d spawnPos = transform.getPosition().clone().add(1.5, 0.0, 1.5);
    Vector3f spawnRot = transform.getRotation().clone();

    TraderNpcSpawner spawner = plugin.getTraderNpcSpawner();
    Pair<?, ?> result = spawner.spawnTrader(store, spawnPos, spawnRot);

    if (result == null) {
      context.sender().sendMessage(Message.raw("Failed to spawn trader. Ensure the NPC role asset exists."));
    } else {
      context.sender().sendMessage(Message.raw("Spawned Hytemon trader at your location."));
    }
  }
}
