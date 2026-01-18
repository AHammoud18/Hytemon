/*    */ package com.hypixel.hytale.server.core.modules.interaction.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EnumArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.SelectInteraction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InteractionSetSnapshotSourceCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 21 */   private static final EnumArgumentType<SelectInteraction.SnapshotSource> SNAPSHOT_SOURCE_ARG_TYPE = new EnumArgumentType("server.commands.parsing.argtype.snapshotsource.name", SelectInteraction.SnapshotSource.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 30 */   private final RequiredArg<SelectInteraction.SnapshotSource> snapshotSourceArg = withRequiredArg("snapshotSource", "server.commands.interaction.snapshotSource.set.snapshotSource.desc", (ArgumentType)SNAPSHOT_SOURCE_ARG_TYPE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InteractionSetSnapshotSourceCommand() {
/* 36 */     super("set", "server.commands.interaction.snapshotSource.set.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 41 */     SelectInteraction.SnapshotSource source = (SelectInteraction.SnapshotSource)this.snapshotSourceArg.get(context);
/* 42 */     SelectInteraction.SNAPSHOT_SOURCE = source;
/* 43 */     context.sendMessage(Message.translation("server.commands.interaction.snapshotSource.set")
/* 44 */         .param("source", source.name()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\commands\InteractionSetSnapshotSourceCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */