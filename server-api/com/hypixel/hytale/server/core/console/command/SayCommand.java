/*    */ package com.hypixel.hytale.server.core.console.command;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.console.ConsoleSender;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.awt.Color;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SayCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 22 */   private static final Color SAY_COMMAND_COLOR = Color.CYAN;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SayCommand() {
/* 28 */     super("say", "server.commands.say.desc");
/* 29 */     addAliases(new String[] { "broadcast" });
/* 30 */     setAllowsExtraArguments(true);
/*    */   }
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/*    */     Message result;
/* 35 */     String rawArgs = CommandUtil.stripCommandName(context.getInputString()).trim();
/*    */     
/* 37 */     if (rawArgs.isEmpty()) {
/* 38 */       context.sendMessage(Message.translation("server.commands.parsing.error.wrongNumberRequiredParameters")
/* 39 */           .param("expected", 1)
/* 40 */           .param("actual", 0));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 45 */     if (rawArgs.charAt(0) == '{') {
/*    */       try {
/* 47 */         result = Message.parse(rawArgs).color(SAY_COMMAND_COLOR);
/* 48 */       } catch (IllegalArgumentException e) {
/* 49 */         context.sendMessage(Message.raw("Failed to parse formatted message: " + e.getMessage()));
/*    */ 
/*    */ 
/*    */         
/*    */         return;
/*    */       } 
/*    */     } else {
/* 56 */       result = Message.translation("server.chat.broadcastMessage").param("username", context.sender().getDisplayName()).param("message", rawArgs).color(SAY_COMMAND_COLOR);
/*    */     } 
/*    */     
/* 59 */     Universe.get().getWorlds().values()
/* 60 */       .forEach(world -> world.getPlayerRefs().forEach(()));
/*    */ 
/*    */     
/* 63 */     ConsoleSender.INSTANCE.sendMessage(result);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\console\command\SayCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */