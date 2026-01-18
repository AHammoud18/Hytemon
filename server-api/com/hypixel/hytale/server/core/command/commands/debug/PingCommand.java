/*     */ package com.hypixel.hytale.server.core.command.commands.debug;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.common.util.StringUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.metrics.metric.HistoricMetric;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.packets.connection.PongType;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PingCommand
/*     */   extends AbstractTargetPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  36 */   private final FlagArg detailFlag = withFlagArg("detail", "server.commands.ping.detail.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PingCommand() {
/*  42 */     super("ping", "server.commands.ping.desc");
/*  43 */     setPermissionGroup(GameMode.Adventure);
/*  44 */     addSubCommand((AbstractCommand)new Clear());
/*  45 */     addSubCommand((AbstractCommand)new Graph());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  50 */     if (this.detailFlag.provided(context)) {
/*  51 */       sendDetailedMessage(context, playerRef);
/*     */     } else {
/*  53 */       sendShortMessage(context, playerRef);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendDetailedMessage(@Nonnull CommandContext context, @Nonnull PlayerRef playerRef) {
/*  64 */     Message msg = Message.join(new Message[] {
/*  65 */           Message.raw(playerRef.getUsername()), 
/*  66 */           Message.raw(" ping:")
/*     */         });
/*     */     
/*  69 */     for (PongType pingType : PongType.values()) {
/*  70 */       PacketHandler.PingInfo pingInfo = playerRef.getPacketHandler().getPingInfo(pingType);
/*  71 */       HistoricMetric historicMetric = pingInfo.getPingMetricSet();
/*     */       
/*  73 */       long[] periods = historicMetric.getPeriodsNanos();
/*     */       
/*  75 */       msg.insert(Message.raw("\n" + pingType.name() + ":\n"));
/*  76 */       for (int i = 0; i < periods.length; i++) {
/*  77 */         String length = FormatUtil.timeUnitToString(periods[i], TimeUnit.NANOSECONDS, true);
/*     */         
/*  79 */         double average = historicMetric.getAverage(i);
/*  80 */         long max = historicMetric.calculateMax(i);
/*  81 */         long min = historicMetric.calculateMin(i);
/*     */         
/*  83 */         String value = FormatUtil.simpleTimeUnitFormat(min, average, max, PacketHandler.PingInfo.TIME_UNIT, TimeUnit.MILLISECONDS, 3);
/*  84 */         msg.insert(Message.raw("  (" + length + "): " + " ".repeat(Math.max(0, 24 - value.length())) + value + "\n"));
/*     */       } 
/*     */       
/*  87 */       msg.insert(Message.raw("  Queue: " + FormatUtil.simpleFormat(pingInfo.getPacketQueueMetric())));
/*     */     } 
/*  89 */     context.sendMessage(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendShortMessage(@Nonnull CommandContext context, @Nonnull PlayerRef playerRef) {
/*  99 */     String length = FormatUtil.timeUnitToString(1L, TimeUnit.SECONDS, true);
/* 100 */     Message msg = Message.join(new Message[] {
/* 101 */           Message.raw(playerRef.getUsername()), 
/* 102 */           Message.raw(" ping  (" + length + "):")
/*     */         });
/*     */     
/* 105 */     for (PongType pingType : PongType.values()) {
/* 106 */       HistoricMetric historicMetric = playerRef.getPacketHandler().getPingInfo(pingType).getPingMetricSet();
/*     */       
/* 108 */       double average = historicMetric.getAverage(0);
/* 109 */       long max = historicMetric.calculateMax(0);
/* 110 */       long min = historicMetric.calculateMin(0);
/*     */       
/* 112 */       String value = FormatUtil.simpleTimeUnitFormat(min, average, max, PacketHandler.PingInfo.TIME_UNIT, TimeUnit.MILLISECONDS, 3);
/* 113 */       msg.insert(Message.raw("\n" + pingType.name() + ":" + " ".repeat(Math.max(0, 24 - value.length())) + value));
/*     */     } 
/* 115 */     context.sendMessage(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Clear
/*     */     extends AbstractTargetPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 123 */     private static final Message MESSAGE_COMMANDS_PING_HISTORY_CLEARED = Message.translation("server.commands.ping.historyCleared");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Clear() {
/* 129 */       super("clear", "server.commands.ping.clear.desc");
/* 130 */       setPermissionGroup(GameMode.Adventure);
/* 131 */       addAliases(new String[] { "reset" });
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 136 */       for (PongType pingType : PongType.values()) {
/* 137 */         playerRef.getPacketHandler().getPingInfo(pingType).clear();
/*     */       }
/*     */       
/* 140 */       context.sendMessage(MESSAGE_COMMANDS_PING_HISTORY_CLEARED);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Graph
/*     */     extends AbstractTargetPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 153 */     private final DefaultArg<Integer> widthArg = withDefaultArg("width", "server.commands.ping.graph.width.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(100), "server.commands.ping.graph.width.default");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 159 */     private final DefaultArg<Integer> heightArg = withDefaultArg("height", "server.commands.ping.graph.height.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(10), "server.commands.ping.graph.height.default");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Graph() {
/* 165 */       super("graph", "server.commands.ping.graph.desc");
/* 166 */       setPermissionGroup(GameMode.Adventure);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 173 */       int width = ((Integer)this.widthArg.get(context)).intValue();
/* 174 */       int height = ((Integer)this.heightArg.get(context)).intValue();
/*     */       
/* 176 */       long startNanos = System.nanoTime();
/*     */       
/* 178 */       Message message = Message.empty();
/* 179 */       for (PongType pingType : PongType.values()) {
/* 180 */         message.insert(String.valueOf(pingType) + ":\n");
/* 181 */         PacketHandler.PingInfo pingInfo = playerRef.getPacketHandler().getPingInfo(pingType);
/*     */         
/* 183 */         HistoricMetric pingMetricSet = pingInfo.getPingMetricSet();
/* 184 */         long[] periods = pingMetricSet.getPeriodsNanos();
/* 185 */         for (int i = 0; i < periods.length; i++) {
/* 186 */           long period = periods[i];
/*     */           
/* 188 */           long max = pingMetricSet.calculateMax(i);
/* 189 */           long min = pingMetricSet.calculateMin(i);
/*     */           
/* 191 */           long[] historyTimestamps = pingMetricSet.getTimestamps(i);
/* 192 */           long[] historyValues = pingMetricSet.getValues(i);
/*     */           
/* 194 */           String historyLengthFormatted = FormatUtil.timeUnitToString(period, TimeUnit.NANOSECONDS, true);
/* 195 */           message.insert(Message.translation("server.commands.ping.graph.period")
/* 196 */               .param("time", historyLengthFormatted));
/*     */           
/* 198 */           StringBuilder sb = new StringBuilder();
/* 199 */           StringUtil.generateGraph(sb, width, height, startNanos - period, startNanos, min, max, value -> FormatUtil.timeUnitToString(MathUtil.fastCeil(value), PacketHandler.PingInfo.TIME_UNIT), historyTimestamps.length, ii -> historyTimestamps[ii], ii -> historyValues[ii]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 207 */           message.insert(sb.toString());
/*     */         } 
/*     */       } 
/* 210 */       context.sendMessage(message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\PingCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */