/*    */ package com.hypixel.hytale.builtin.adventure.memories.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MemoriesClearCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 15 */   private static final Message MESSAGE_COMMANDS_MEMORIES_CLEAR_SUCCESS = Message.translation("server.commands.memories.clear.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MemoriesClearCommand() {
/* 21 */     super("clear", "server.commands.memories.clear.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 26 */     MemoriesPlugin.get().clearRecordedMemories();
/* 27 */     context.sendMessage(MESSAGE_COMMANDS_MEMORIES_CLEAR_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\commands\MemoriesClearCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */