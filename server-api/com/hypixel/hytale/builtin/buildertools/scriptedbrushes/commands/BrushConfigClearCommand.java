/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.tooloperations.ToolOperation;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BrushConfigClearCommand
/*    */   extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_BRUSH_CONFIG_CANNOT_USE_COMMAND_DURING_EXEC = Message.translation("server.commands.brushConfig.cannotUseCommandDuringExec");
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_BRUSH_CONFIG_CLEARED = Message.translation("server.commands.brushConfig.cleared");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BrushConfigClearCommand() {
/* 28 */     super("clear", "Clear your brush config and disable it");
/* 29 */     addAliases(new String[] { "disable" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 34 */     UUID playerUUID = playerRef.getUuid();
/*    */     
/* 36 */     PrototypePlayerBuilderToolSettings prototypeSettings = ToolOperation.getOrCreatePrototypeSettings(playerUUID);
/* 37 */     BrushConfigCommandExecutor brushConfigCommandExecutor = ToolOperation.getOrCreatePrototypeSettings(playerUUID).getBrushConfigCommandExecutor();
/*    */     
/* 39 */     if (prototypeSettings.getBrushConfig().isCurrentlyExecuting()) {
/* 40 */       playerRef.sendMessage(MESSAGE_COMMANDS_BRUSH_CONFIG_CANNOT_USE_COMMAND_DURING_EXEC);
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     brushConfigCommandExecutor.getSequentialOperations().clear();
/* 45 */     brushConfigCommandExecutor.getGlobalOperations().clear();
/*    */ 
/*    */     
/* 48 */     prototypeSettings.setUsePrototypeBrushConfigurations(false);
/*    */     
/* 50 */     playerRef.sendMessage(MESSAGE_COMMANDS_BRUSH_CONFIG_CLEARED);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\commands\BrushConfigClearCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */