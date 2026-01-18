/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldListCommand
/*    */   extends CommandBase
/*    */ {
/*    */   public WorldListCommand() {
/* 21 */     super("list", "server.commands.worlds.desc");
/* 22 */     addAliases(new String[] { "ls" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 29 */     Set<Message> worlds = (Set<Message>)Universe.get().getWorlds().keySet().stream().map(Message::raw).collect(Collectors.toSet());
/* 30 */     Message message = MessageFormat.list(Message.translation("server.commands.worlds.header"), worlds);
/*    */     
/* 32 */     context.sender().sendMessage(message);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\WorldListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */