/*    */ package com.hypixel.hytale.server.core.command.commands.server;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MaxPlayersCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 22 */   private final OptionalArg<Integer> amountArg = withOptionalArg("amount", "server.commands.maxplayers.amount.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MaxPlayersCommand() {
/* 28 */     super("maxplayers", "server.commands.maxplayers.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 33 */     if (this.amountArg.provided(context)) {
/* 34 */       int maxPlayers = ((Integer)this.amountArg.get(context)).intValue();
/* 35 */       HytaleServer.get().getConfig().setMaxPlayers(maxPlayers);
/* 36 */       context.sendMessage(Message.translation("server.commands.maxplayers.set")
/* 37 */           .param("maxPlayers", maxPlayers));
/*    */     } else {
/* 39 */       int maxPlayers = HytaleServer.get().getConfig().getMaxPlayers();
/* 40 */       context.sendMessage(Message.translation("server.commands.maxplayers.get")
/* 41 */           .param("maxPlayers", maxPlayers));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\MaxPlayersCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */