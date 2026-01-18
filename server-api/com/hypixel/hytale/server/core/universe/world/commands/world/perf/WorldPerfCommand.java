/*     */ package com.hypixel.hytale.server.core.universe.world.commands.world.perf;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.metrics.metric.HistoricMetric;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldPerfCommand
/*     */   extends AbstractWorldCommand
/*     */ {
/*     */   public static final double PRECISION = 1000.0D;
/*     */   @Nonnull
/*  30 */   private final FlagArg allFlag = withFlagArg("all", "server.commands.world.perf.all.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  36 */   private final FlagArg deltaFlag = withFlagArg("delta", "server.commands.world.perf.delta.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldPerfCommand() {
/*  42 */     super("perf", "server.commands.world.perf.desc");
/*  43 */     addSubCommand((AbstractCommand)new WorldPerfGraphCommand());
/*  44 */     addSubCommand((AbstractCommand)new WorldPerfResetCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  49 */     HistoricMetric historicMetric = world.getBufferedTickLengthMetricSet();
/*  50 */     long[] periods = historicMetric.getPeriodsNanos();
/*  51 */     int tickStepNanos = world.getTickStepNanos();
/*     */     
/*  53 */     Message msg = Message.empty();
/*  54 */     boolean showDelta = this.deltaFlag.provided(context);
/*  55 */     boolean showAll = this.allFlag.provided(context);
/*     */     
/*  57 */     if (context.sender() instanceof com.hypixel.hytale.server.core.entity.entities.Player) {
/*     */ 
/*     */       
/*  60 */       for (int i = 0; i < periods.length; i++) {
/*  61 */         String length = FormatUtil.timeUnitToString(periods[i], TimeUnit.NANOSECONDS, true);
/*  62 */         double average = historicMetric.getAverage(i);
/*  63 */         long min = historicMetric.calculateMin(i);
/*  64 */         long max = historicMetric.calculateMax(i);
/*     */         
/*  66 */         if (showDelta) {
/*  67 */           String value = FormatUtil.simpleTimeUnitFormat(min, average, max, TimeUnit.NANOSECONDS, TimeUnit.MILLISECONDS, 3);
/*  68 */           String padding = " ".repeat(Math.max(0, 24 - value.length()));
/*  69 */           msg.insert(Message.translation("server.commands.world.perf.period")
/*  70 */               .param("length", length)
/*  71 */               .param("padding", padding)
/*  72 */               .param("value", value)
/*  73 */               .insert("\n"));
/*     */         } else {
/*     */           
/*  76 */           msg.insert(Message.translation("server.commands.world.perf.tpsTime")
/*  77 */               .param("time", length)
/*  78 */               .param("tps", FormatUtil.simpleFormat(min, average, max, d1 -> tpsFromDelta(d1, tickStepNanos), 2))
/*  79 */               .insert("\n"));
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/*  85 */       String tickLimitFormatted = FormatUtil.simpleTimeUnitFormat(tickStepNanos, TimeUnit.NANOSECONDS, 3);
/*  86 */       msg.insert(Message.translation("server.commands.world.perf.tickLimit")
/*  87 */           .param("tickLimit", tickLimitFormatted)
/*  88 */           .insert("\n"));
/*     */       
/*  90 */       for (int i = 0; i < periods.length; i++) {
/*  91 */         String length = FormatUtil.timeUnitToString(periods[i], TimeUnit.NANOSECONDS, true);
/*  92 */         double average = historicMetric.getAverage(i);
/*  93 */         long min = historicMetric.calculateMin(i);
/*  94 */         long max = historicMetric.calculateMax(i);
/*     */         
/*  96 */         if (showDelta) {
/*  97 */           String value = FormatUtil.simpleTimeUnitFormat(min, average, max, TimeUnit.NANOSECONDS, TimeUnit.MILLISECONDS, 3);
/*  98 */           String padding = " ".repeat(Math.max(0, 24 - value.length()));
/*  99 */           msg.insert(Message.translation("server.commands.world.perf.period")
/* 100 */               .param("length", length)
/* 101 */               .param("padding", padding)
/* 102 */               .param("value", value)
/* 103 */               .insert("\n"));
/*     */         }
/*     */         else {
/*     */           
/* 107 */           msg.insert(Message.translation("server.commands.world.perf.tpsMinMaxMetric")
/* 108 */               .param("time", length)
/* 109 */               .param("min", tpsFromDelta(max, tickStepNanos))
/* 110 */               .param("avg", tpsFromDelta(average, tickStepNanos))
/* 111 */               .param("max", tpsFromDelta(min, tickStepNanos))
/* 112 */               .insert("\n"));
/*     */         } 
/*     */         
/* 115 */         if (showAll) {
/* 116 */           msg.insert(Message.translation("server.commands.world.perf.deltaMinMaxMetric")
/* 117 */               .param("time", length)
/* 118 */               .param("min", min)
/* 119 */               .param("avg", (long)average)
/* 120 */               .param("max", max)
/* 121 */               .insert("\n"));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     context.sendMessage(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double tpsFromDelta(long delta, long min) {
/* 137 */     long adjustedDelta = delta;
/* 138 */     if (adjustedDelta < min) adjustedDelta = min; 
/* 139 */     return Math.round(1.0D / adjustedDelta * 1.0E9D * 1000.0D) / 1000.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double tpsFromDelta(double delta, long min) {
/* 150 */     double adjustedDelta = delta;
/* 151 */     if (adjustedDelta < min) adjustedDelta = min; 
/* 152 */     return Math.round(1.0D / adjustedDelta * 1.0E9D * 1000.0D) / 1000.0D;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\perf\WorldPerfCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */