package com.hytemon.mod.npc;

import com.hytemon.mod.HytemonPlugin;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import it.unimi.dsi.fastutil.Pair;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TraderNpcSpawner {
  public static final String TRADER_ROLE_ID = "Hytemon:Trader";
  public static final String TRADER_SHOP_ID = "Hytemon:TraderShop";

  private final HytemonPlugin plugin;

  public TraderNpcSpawner(@Nonnull HytemonPlugin plugin) {
    this.plugin = plugin;
  }

  @Nullable
  public Pair<Ref<EntityStore>, INonPlayerCharacter> spawnTrader(
      @Nonnull Store<EntityStore> store,
      @Nonnull Vector3d position,
      @Nonnull Vector3f rotation
  ) {
    return NPCPlugin.get().spawnNPC(store, TRADER_ROLE_ID, null, position, rotation);
  }
}
