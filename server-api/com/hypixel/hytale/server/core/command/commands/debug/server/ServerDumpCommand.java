/*    */ package com.hypixel.hytale.server.core.command.commands.debug.server;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.util.DumpUtil;
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ServerDumpCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 18 */   private static final Message MESSAGE_COMMANDS_SERVER_DUMP_DUMPING_STATE = Message.translation("server.commands.server.dump.dumpingState");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 24 */   private final FlagArg jsonFlag = withFlagArg("json", "server.commands.server.dump.json.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerDumpCommand() {
/* 30 */     super("dump", "server.commands.server.dump.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 35 */     context.sendMessage(MESSAGE_COMMANDS_SERVER_DUMP_DUMPING_STATE);
/*    */     
/* 37 */     if (this.jsonFlag.provided(context)) {
/*    */       try {
/* 39 */         Path path = DumpUtil.dumpToJson();
/* 40 */         context.sendMessage(Message.translation("server.commands.server.dump.finished")
/* 41 */             .param("filepath", path.toAbsolutePath().toString()));
/* 42 */       } catch (IOException e) {
/* 43 */         context.sendMessage(Message.translation("server.commands.server.dump.error")
/* 44 */             .param("error", e.getMessage()));
/* 45 */         throw SneakyThrow.sneakyThrow(e);
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 51 */     Path file = DumpUtil.dump(false, false);
/* 52 */     context.sendMessage(Message.translation("server.commands.server.dump.finished")
/* 53 */         .param("filepath", file.toAbsolutePath().toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\server\ServerDumpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */