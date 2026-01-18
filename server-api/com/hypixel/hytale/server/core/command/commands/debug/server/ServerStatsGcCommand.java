/*    */ package com.hypixel.hytale.server.core.command.commands.debug.server;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import java.lang.management.GarbageCollectorMXBean;
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ServerStatsGcCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 16 */   private static final Message MESSAGE_COMMANDS_SERVER_STATS_GC_USAGE_INFO = Message.translation("server.commands.server.stats.gc.usageInfo");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerStatsGcCommand() {
/* 22 */     super("gc", "server.commands.server.stats.gc.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 27 */     for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans())
/* 28 */       context.sendMessage(MESSAGE_COMMANDS_SERVER_STATS_GC_USAGE_INFO
/* 29 */           .param("name", garbageCollectorMXBean.getName())
/* 30 */           .param("poolNames", Arrays.toString((Object[])garbageCollectorMXBean.getMemoryPoolNames()))
/* 31 */           .param("collectionCount", garbageCollectorMXBean.getCollectionCount())
/* 32 */           .param("collectionTime", garbageCollectorMXBean.getCollectionTime())); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\server\ServerStatsGcCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */