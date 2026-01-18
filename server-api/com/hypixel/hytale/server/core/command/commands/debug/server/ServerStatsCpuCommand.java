/*    */ package com.hypixel.hytale.server.core.command.commands.debug.server;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.FormatUtil;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.sun.management.OperatingSystemMXBean;
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.lang.management.OperatingSystemMXBean;
/*    */ import java.lang.management.RuntimeMXBean;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ServerStatsCpuCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 18 */   private static final Message MESSAGE_COMMANDS_SERVER_STATS_CPU_FULL_USAGE_INFO = Message.translation("server.commands.server.stats.cpu.fullUsageInfo");
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_SERVER_STATS_FULL_INFO_UNAVAILABLE = Message.translation("server.commands.server.stats.fullInfoUnavailable");
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_SERVER_STATS_CPU_USAGE_INFO = Message.translation("server.commands.server.stats.cpu.usageInfo");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerStatsCpuCommand() {
/* 28 */     super("cpu", "server.commands.server.stats.cpu.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 33 */     RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
/* 34 */     OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
/*    */     
/* 36 */     if (operatingSystemMXBean instanceof OperatingSystemMXBean) { OperatingSystemMXBean sunOSBean = (OperatingSystemMXBean)operatingSystemMXBean;
/* 37 */       context.sendMessage(MESSAGE_COMMANDS_SERVER_STATS_CPU_FULL_USAGE_INFO
/* 38 */           .param("systemLoad", sunOSBean.getSystemCpuLoad())
/* 39 */           .param("processLoad", sunOSBean.getProcessCpuLoad())); }
/*    */     else
/* 41 */     { context.sendMessage(MESSAGE_COMMANDS_SERVER_STATS_FULL_INFO_UNAVAILABLE); }
/*    */ 
/*    */     
/* 44 */     context.sendMessage(MESSAGE_COMMANDS_SERVER_STATS_CPU_USAGE_INFO
/* 45 */         .param("loadAverage", operatingSystemMXBean.getSystemLoadAverage())
/* 46 */         .param("processUptime", FormatUtil.timeUnitToString(runtimeMXBean.getUptime(), TimeUnit.MILLISECONDS)));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\server\ServerStatsCpuCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */