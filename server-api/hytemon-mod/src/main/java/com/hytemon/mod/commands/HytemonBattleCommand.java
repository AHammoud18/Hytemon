package com.hytemon.mod.commands;

import com.hytemon.mod.HytemonPlugin;
import com.hytemon.mod.battle.BattleManager;
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
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import java.util.Locale;
import javax.annotation.Nonnull;

public class HytemonBattleCommand extends AbstractPlayerCommand {
  @Nonnull
  private final HytemonPlugin plugin;

  @Nonnull
  private final RequiredArg<String> actionArg = withRequiredArg(
      "action",
      "start or end",
      (ArgumentType) ArgTypes.STRING
  );

  @Nonnull
  private final OptionalArg<String> targetIdArg = withOptionalArg(
      "targetId",
      "Target entity id",
      (ArgumentType) ArgTypes.STRING
  );

  @Nonnull
  private final OptionalArg<String> displayNameArg = withOptionalArg(
      "displayName",
      "Display name for the target",
      (ArgumentType) ArgTypes.STRING
  );

  public HytemonBattleCommand(@Nonnull HytemonPlugin plugin) {
    super("Start or end a Hytemon battle");
    this.plugin = plugin;
    addAliases(new String[] { "battle" });
  }

  @Override
  protected void execute(
      @Nonnull CommandContext context,
      @Nonnull Store<EntityStore> store,
      @Nonnull Ref<EntityStore> ref,
      @Nonnull PlayerRef playerRef,
      @Nonnull World world
  ) {
    String action = ((String) actionArg.get(context)).toLowerCase(Locale.ROOT);
    BattleManager battleManager = plugin.getBattleManager();

    if (action.equals("end")) {
      battleManager.endEncounter(store, ref);
      context.sender().sendMessage(Message.raw("Hytemon battle ended."));
      return;
    }

    String targetId = targetIdArg.provided(context) ? (String) targetIdArg.get(context) : "Hytale:Monster";
    String displayName = displayNameArg.provided(context) ? (String) displayNameArg.get(context) : targetId;
    CaptureTarget target = new CaptureTarget(targetId, displayName, CaptureTarget.Disposition.MONSTER);

    battleManager.beginEncounter(store, ref, playerRef, target);
    context.sender().sendMessage(Message.raw("Hytemon battle started against " + displayName + "."));
  }
}
