package com.hytemon.mod.commands;

import com.hytemon.mod.HytemonItems;
import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.player.TrainerData;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HytemonGiveCommand extends AbstractPlayerCommand {
  @Nonnull
  private final HytemonPlugin plugin;

  @Nonnull
  private final RequiredArg<String> itemArg = withRequiredArg(
      "item",
      "Item type to grant (pokeball, greatball, ultraball, potion, superpotion, revive)",
      (ArgumentType) ArgTypes.STRING
  );

  @Nonnull
  private final OptionalArg<Integer> quantityArg = withOptionalArg(
      "quantity",
      "Quantity to grant",
      (ArgumentType) ArgTypes.INTEGER
  );

  public HytemonGiveCommand(@Nonnull HytemonPlugin plugin) {
    super("Give Hytemon items to yourself");
    this.plugin = plugin;
    addAliases(new String[] { "give" });
  }

  @Override
  protected void execute(
      @Nonnull CommandContext context,
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> ref,
      @Nonnull PlayerRef playerRef,
      @Nonnull World world
  ) {
    String key = ((String) itemArg.get(context)).toLowerCase(Locale.ROOT);
    int quantity = quantityArg.provided(context) ? (Integer) quantityArg.get(context) : 1;
    String itemId = resolveItemId(key);

    if (itemId == null) {
      context.sender().sendMessage(Message.raw("Unknown Hytemon item: " + key));
      return;
    }

    ItemStack stack = new ItemStack(itemId, quantity);
    store.ensureAndGetComponent(ref, TrainerData.getComponentType());

    // TODO: Insert the stack into the player's inventory once the inventory API is wired.
    // Example approach: LivingEntity entity = (LivingEntity) store.getComponent(ref, LivingEntity.getComponentType());
    // entity.getInventory().getCombinedHotbarFirst().addItemStacks(List.of(stack));

    context.sender().sendMessage(Message.raw("Queued item grant: " + itemId + " x" + quantity));
  }

  @Nullable
  private String resolveItemId(@Nonnull String key) {
    return switch (key) {
      case "pokeball" -> HytemonItems.POKEBALL;
      case "greatball" -> HytemonItems.GREATBALL;
      case "ultraball" -> HytemonItems.ULTRABALL;
      case "potion" -> HytemonItems.POTION;
      case "superpotion" -> HytemonItems.SUPER_POTION;
      case "revive" -> HytemonItems.REVIVE;
      default -> null;
    };
  }
}
