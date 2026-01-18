/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.ScriptedBrushAsset;
/*    */ import com.hypixel.hytale.builtin.buildertools.tooloperations.ToolOperation;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class LoadByNameCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 57 */   private static final Message MESSAGE_COMMANDS_BRUSH_CONFIG_LOADED = Message.translation("server.commands.brushConfig.loaded");
/*    */   @Nonnull
/* 59 */   private static final Message MESSAGE_COMMANDS_BRUSH_CONFIG_CANNOT_USE_COMMAND_DURING_EXEC = Message.translation("server.commands.brushConfig.cannotUseCommandDuringExec");
/*    */   
/*    */   @Nonnull
/* 62 */   private final RequiredArg<ScriptedBrushAsset> brushNameArg = withRequiredArg("brushName", "The name of the scripted brush asset to load", (ArgumentType)new AssetArgumentType("server.commands.parsing.argtype.asset.scriptedbrush.name", ScriptedBrushAsset.class, "server.commands.parsing.argtype.asset.scriptedbrush.usage"));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LoadByNameCommand() {
/* 69 */     super("Load a scripted brush by name");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 74 */     UUID playerUUID = playerRef.getUuid();
/*    */     
/* 76 */     PrototypePlayerBuilderToolSettings prototypeSettings = ToolOperation.getOrCreatePrototypeSettings(playerUUID);
/* 77 */     BrushConfig brushConfig = prototypeSettings.getBrushConfig();
/* 78 */     BrushConfigCommandExecutor brushConfigCommandExecutor = prototypeSettings.getBrushConfigCommandExecutor();
/*    */     
/* 80 */     if (brushConfig.isCurrentlyExecuting()) {
/* 81 */       playerRef.sendMessage(MESSAGE_COMMANDS_BRUSH_CONFIG_CANNOT_USE_COMMAND_DURING_EXEC);
/*    */       
/*    */       return;
/*    */     } 
/* 85 */     ScriptedBrushAsset brushAssetArg = (ScriptedBrushAsset)this.brushNameArg.get(context);
/*    */ 
/*    */     
/* 88 */     brushAssetArg.loadIntoExecutor(brushConfigCommandExecutor);
/*    */ 
/*    */     
/* 91 */     prototypeSettings.setCurrentlyLoadedBrushConfigName(brushAssetArg.getId());
/*    */ 
/*    */     
/* 94 */     prototypeSettings.setUsePrototypeBrushConfigurations(true);
/*    */     
/* 96 */     playerRef.sendMessage(MESSAGE_COMMANDS_BRUSH_CONFIG_LOADED.param("name", brushAssetArg.getId()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\commands\BrushConfigLoadCommand$LoadByNameCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */