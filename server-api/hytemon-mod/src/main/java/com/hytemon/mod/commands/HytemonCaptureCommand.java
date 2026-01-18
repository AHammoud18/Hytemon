package com.hytemon.mod.commands;

import com.hytemon.mod.HytemonItems;
import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.capture.CaptureManager;
import com.hytemon.mod.capture.CaptureResult;
import com.hytemon.mod.capture.CaptureTarget;
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

public class HytemonCaptureCommand extends AbstractPlayerCommand {
  @Nonnull
  private final HytemonPlugin plugin;

  @Nonnull
  private final RequiredArg<String> targetIdArg = withRequiredArg(
      "targetId",
      "Entity id to simulate capture (e.g. Hytale:Sheep)",
      (ArgumentType) ArgTypes.STRING
  );

  @Nonnull
  private final OptionalArg<String> displayNameArg = withOptionalArg(
      "displayName",
      "Display name for the target",
      (ArgumentType) ArgTypes.STRING
  );

  @Nonnull
  private final OptionalArg<String> dispositionArg = withOptionalArg(
      "disposition",
      "Target disposition (animal or monster)",
      (ArgumentType) ArgTypes.STRING
  );

  public HytemonCaptureCommand(@Nonnull HytemonPlugin plugin) {
    super("Simulate a capture attempt");
    this.plugin = plugin;
    addAliases(new String[] { "capture" });
  }

  @Override
  protected void execute(
      @Nonnull CommandContext context,
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> ref,
      @Nonnull PlayerRef playerRef,
      @Nonnull World world
  ) {
    String targetId = (String) targetIdArg.get(context);
    String displayName = displayNameArg.provided(context)
        ? (String) displayNameArg.get(context)
        : targetId;
    CaptureTarget.Disposition disposition = parseDisposition(
        dispositionArg.provided(context) ? (String) dispositionArg.get(context) : "animal"
    );

    CaptureTarget target = new CaptureTarget(targetId, displayName, disposition);
    ItemStack pokeball = new ItemStack(HytemonItems.POKEBALL, 1);
    CaptureManager captureManager = plugin.getCaptureManager();
    CaptureResult result = captureManager.attemptCapture(store, ref, playerRef, target, pokeball);

    context.sender().sendMessage(Message.raw("Capture result for " + target.displayName() + ": " + result));
  }

  @Nonnull
  private CaptureTarget.Disposition parseDisposition(@Nonnull String value) {
    String normalized = value.toLowerCase(Locale.ROOT);
    return normalized.contains("monster")
        ? CaptureTarget.Disposition.MONSTER
        : CaptureTarget.Disposition.ANIMAL;
  }
}
