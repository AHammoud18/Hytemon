/*    */ package com.hypixel.hytale.server.core.command.commands.server;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class KickCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 17 */   private static final Message MESSAGE_COMMANDS_KICK_SUCCESS = Message.translation("server.commands.kick.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 23 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.kick.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public KickCommand() {
/* 29 */     super("kick", "server.commands.kick.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 34 */     PlayerRef playerToKick = (PlayerRef)this.playerArg.get(context);
/* 35 */     playerToKick.getPacketHandler().disconnect("You were kicked.");
/* 36 */     context.sendMessage(MESSAGE_COMMANDS_KICK_SUCCESS
/* 37 */         .param("username", playerToKick.getUsername()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\KickCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */