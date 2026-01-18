/*     */ package com.hypixel.hytale.server.npc.commands;
/*     */ 
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.benchmark.TimeDistributionRecorder;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.util.SensorSupportBenchmark;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import java.util.Formatter;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class NPCBenchmarkCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/*  25 */   private static final Message MESSAGE_COMMANDS_NPC_BENCHMARK_START_FAILED = Message.translation("server.commands.npc.benchmark.startFailed");
/*     */   @Nonnull
/*  27 */   private static final Message MESSAGE_COMMANDS_NPC_BENCHMARK_DONE = Message.translation("server.commands.npc.benchmark.done");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  33 */   private final FlagArg roleArg = withFlagArg("roles", "server.commands.npc.benchmark.role.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  39 */   private final FlagArg sensorSupportArg = withFlagArg("sensorsupport", "server.commands.npc.benchmark.sensor.desc");
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  44 */   private final OptionalArg<Double> secondsArg = (OptionalArg<Double>)
/*  45 */     withOptionalArg("seconds", "server.commands.npc.benchmark.role.seconds", (ArgumentType)ArgTypes.DOUBLE)
/*  46 */     .addValidator(Validators.greaterThan(Double.valueOf(0.0D)));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCBenchmarkCommand() {
/*  52 */     super("benchmark", "server.commands.npc.benchmark.desc");
/*     */   }
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/*     */     boolean success;
/*  57 */     double seconds = this.secondsArg.provided(context) ? ((Double)this.secondsArg.get(context)).doubleValue() : 30.0D;
/*     */ 
/*     */     
/*  60 */     if (((Boolean)this.roleArg.get(context)).booleanValue()) {
/*  61 */       success = NPCPlugin.get().startRoleBenchmark(seconds, distribution -> {
/*     */             StringBuilder sb = (new StringBuilder()).append("Role benchmark seconds=").append(seconds).append('\n');
/*     */             
/*     */             Formatter formatter = new Formatter(sb);
/*     */             
/*     */             if (!distribution.isEmpty()) {
/*     */               TimeDistributionRecorder recorder = (TimeDistributionRecorder)distribution.get(-1);
/*     */               
/*     */               recorder.formatHeader(formatter);
/*     */               sb.append('\n');
/*     */               IntArrayList sortedIndices = new IntArrayList((IntCollection)distribution.keySet());
/*     */               sortedIndices.rem(-1);
/*     */               sortedIndices.sort(());
/*     */               for (int i = 0; i < sortedIndices.size(); i++) {
/*     */                 int role = sortedIndices.getInt(i);
/*     */                 logRoleDistribution((TimeDistributionRecorder)distribution.get(role), sb, formatter, NPCPlugin.get().getName(role));
/*     */               } 
/*     */               logRoleDistribution((TimeDistributionRecorder)distribution.get(-1), sb, formatter, "ALL");
/*     */             } 
/*     */             context.sendMessage(MESSAGE_COMMANDS_NPC_BENCHMARK_DONE);
/*     */             NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */           });
/*  83 */     } else if (((Boolean)this.sensorSupportArg.get(context)).booleanValue()) {
/*  84 */       success = NPCPlugin.get().startSensorSupportBenchmark(seconds, sensorSupportData -> {
/*     */             StringBuilder sb = (new StringBuilder()).append("PositionCache benchmark seconds=").append(seconds).append('\n');
/*     */             
/*     */             Formatter formatter = new Formatter(sb);
/*     */             
/*     */             if (!sensorSupportData.isEmpty()) {
/*     */               IntArrayList sortedIndices = new IntArrayList((IntCollection)sensorSupportData.keySet());
/*     */               sortedIndices.rem(-1);
/*     */               sortedIndices.sort(());
/*     */               SensorSupportBenchmark data = (SensorSupportBenchmark)sensorSupportData.get(-1);
/*     */               sb.append("PositionCache Update Times\n");
/*     */               data.formatHeaderUpdateTimes(formatter);
/*     */               sb.append('\n');
/*     */               int i;
/*     */               for (i = 0; i < sortedIndices.size(); i++) {
/*     */                 int role = sortedIndices.getInt(i);
/*     */                 SensorSupportBenchmark bm = (SensorSupportBenchmark)sensorSupportData.get(role);
/*     */                 if (bm.haveUpdateTimes()) {
/*     */                   logSensorSupportUpdateTime(bm, sb, formatter, NPCPlugin.get().getName(role));
/*     */                 }
/*     */               } 
/*     */               logSensorSupportUpdateTime((SensorSupportBenchmark)sensorSupportData.get(-1), sb, formatter, "ALL");
/*     */               sb.append("PositionCache Line of sight\n");
/*     */               data.formatHeaderLoS(formatter);
/*     */               sb.append('\n');
/*     */               for (i = 0; i < sortedIndices.size(); i++) {
/*     */                 int role = sortedIndices.getInt(i);
/*     */                 logSensorSupportLoS((SensorSupportBenchmark)sensorSupportData.get(role), sb, formatter, NPCPlugin.get().getName(role));
/*     */               } 
/*     */               logSensorSupportLoS((SensorSupportBenchmark)sensorSupportData.get(-1), sb, formatter, "ALL");
/*     */             } 
/*     */             context.sendMessage(MESSAGE_COMMANDS_NPC_BENCHMARK_DONE);
/*     */             NPCPlugin.get().getLogger().at(Level.INFO).log(sb.toString());
/*     */           });
/*     */     } else {
/* 119 */       success = false;
/*     */     } 
/*     */     
/* 122 */     if (success) {
/* 123 */       context.sendMessage(Message.translation("server.commands.npc.benchmark.startedFor")
/* 124 */           .param("seconds", seconds));
/*     */     } else {
/* 126 */       context.sendMessage(MESSAGE_COMMANDS_NPC_BENCHMARK_START_FAILED);
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void logRoleDistribution(@Nonnull TimeDistributionRecorder rec, @Nonnull StringBuilder sb, @Nonnull Formatter formatter, @Nonnull String name) {
/* 143 */     rec.formatValues(formatter, 10000L);
/* 144 */     sb.append("|").append(name).append('\n');
/*     */   }
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
/*     */ 
/*     */   
/*     */   private static void logSensorSupportUpdateTime(@Nonnull SensorSupportBenchmark bm, @Nonnull StringBuilder sb, @Nonnull Formatter formatter, @Nonnull String name) {
/* 159 */     bm.formatValuesUpdateTimePlayer(formatter);
/* 160 */     sb.append('|').append(name).append('\n');
/* 161 */     bm.formatValuesUpdateTimeEntity(formatter);
/* 162 */     sb.append('|').append(name).append('\n');
/*     */   }
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
/*     */ 
/*     */   
/*     */   private static void logSensorSupportLoS(@Nonnull SensorSupportBenchmark bm, @Nonnull StringBuilder sb, @Nonnull Formatter formatter, @Nonnull String name) {
/* 177 */     if (bm.formatValuesLoS(formatter))
/* 178 */       sb.append('|').append(name).append('\n'); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCBenchmarkCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */