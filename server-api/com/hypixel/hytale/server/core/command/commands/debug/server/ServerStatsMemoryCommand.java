/*    */ package com.hypixel.hytale.server.core.command.commands.debug.server;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.FormatUtil;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.sun.management.OperatingSystemMXBean;
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.lang.management.MemoryMXBean;
/*    */ import java.lang.management.MemoryUsage;
/*    */ import java.lang.management.OperatingSystemMXBean;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ServerStatsMemoryCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 19 */   private static final Message MESSAGE_COMMANDS_SERVER_STATS_MEMORY_FULL_USAGE_INFO = Message.translation("server.commands.server.stats.memory.fullUsageInfo");
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_COMMANDS_SERVER_STATS_FULL_INFO_UNAVAILABLE = Message.translation("server.commands.server.stats.fullInfoUnavailable");
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_SERVER_STATS_MEMORY_USAGE_INFO = Message.translation("server.commands.server.stats.memory.usageInfo");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerStatsMemoryCommand() {
/* 29 */     super("memory", "server.commands.server.stats.memory.desc");
/* 30 */     addAliases(new String[] { "mem" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 35 */     OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
/*    */     
/* 37 */     if (operatingSystemMXBean instanceof OperatingSystemMXBean) { OperatingSystemMXBean sunOSBean = (OperatingSystemMXBean)operatingSystemMXBean;
/* 38 */       context.sendMessage(MESSAGE_COMMANDS_SERVER_STATS_MEMORY_FULL_USAGE_INFO
/* 39 */           .param("totalPhysicalMemory", FormatUtil.bytesToString(sunOSBean.getTotalPhysicalMemorySize()))
/* 40 */           .param("freePhysicalMemory", FormatUtil.bytesToString(sunOSBean.getFreePhysicalMemorySize()))
/* 41 */           .param("totalSwapMemory", FormatUtil.bytesToString(sunOSBean.getTotalSwapSpaceSize()))
/* 42 */           .param("freeSwapMemory", FormatUtil.bytesToString(sunOSBean.getFreeSwapSpaceSize()))); }
/*    */     else
/* 44 */     { context.sendMessage(MESSAGE_COMMANDS_SERVER_STATS_FULL_INFO_UNAVAILABLE); }
/*    */ 
/*    */     
/* 47 */     MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
/* 48 */     context.sendMessage(MESSAGE_COMMANDS_SERVER_STATS_MEMORY_USAGE_INFO
/* 49 */         .param("heapMemoryUsage", formatMemoryUsage(memoryMXBean.getHeapMemoryUsage()))
/* 50 */         .param("nonHeapMemoryUsage", formatMemoryUsage(memoryMXBean.getNonHeapMemoryUsage()))
/* 51 */         .param("objectsPendingFinalizationCount", memoryMXBean.getObjectPendingFinalizationCount()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private static Message formatMemoryUsage(@Nonnull MemoryUsage memoryUsage) {
/* 62 */     return Message.translation("server.commands.server.stats.memory.usage")
/* 63 */       .param("init", FormatUtil.bytesToString(memoryUsage.getInit()))
/* 64 */       .param("used", FormatUtil.bytesToString(memoryUsage.getUsed()))
/* 65 */       .param("committed", FormatUtil.bytesToString(memoryUsage.getCommitted()))
/* 66 */       .param("max", FormatUtil.bytesToString(memoryUsage.getMax()))
/* 67 */       .param("free", FormatUtil.bytesToString(memoryUsage.getMax() - memoryUsage.getCommitted()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\server\ServerStatsMemoryCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */