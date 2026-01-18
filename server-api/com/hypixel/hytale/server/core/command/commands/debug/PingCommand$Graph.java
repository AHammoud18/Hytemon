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
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
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
/*     */ 
/*     */ 
/*     */ class Graph
/*     */   extends AbstractTargetPlayerCommand
/*     */ {
/*     */   @Nonnull
/* 153 */   private final DefaultArg<Integer> widthArg = withDefaultArg("width", "server.commands.ping.graph.width.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(100), "server.commands.ping.graph.width.default");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 159 */   private final DefaultArg<Integer> heightArg = withDefaultArg("height", "server.commands.ping.graph.height.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(10), "server.commands.ping.graph.height.default");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Graph() {
/* 165 */     super("graph", "server.commands.ping.graph.desc");
/* 166 */     setPermissionGroup(GameMode.Adventure);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 173 */     int width = ((Integer)this.widthArg.get(context)).intValue();
/* 174 */     int height = ((Integer)this.heightArg.get(context)).intValue();
/*     */     
/* 176 */     long startNanos = System.nanoTime();
/*     */     
/* 178 */     Message message = Message.empty();
/* 179 */     for (PongType pingType : PongType.values()) {
/* 180 */       message.insert(String.valueOf(pingType) + ":\n");
/* 181 */       PacketHandler.PingInfo pingInfo = playerRef.getPacketHandler().getPingInfo(pingType);
/*     */       
/* 183 */       HistoricMetric pingMetricSet = pingInfo.getPingMetricSet();
/* 184 */       long[] periods = pingMetricSet.getPeriodsNanos();
/* 185 */       for (int i = 0; i < periods.length; i++) {
/* 186 */         long period = periods[i];
/*     */         
/* 188 */         long max = pingMetricSet.calculateMax(i);
/* 189 */         long min = pingMetricSet.calculateMin(i);
/*     */         
/* 191 */         long[] historyTimestamps = pingMetricSet.getTimestamps(i);
/* 192 */         long[] historyValues = pingMetricSet.getValues(i);
/*     */         
/* 194 */         String historyLengthFormatted = FormatUtil.timeUnitToString(period, TimeUnit.NANOSECONDS, true);
/* 195 */         message.insert(Message.translation("server.commands.ping.graph.period")
/* 196 */             .param("time", historyLengthFormatted));
/*     */         
/* 198 */         StringBuilder sb = new StringBuilder();
/* 199 */         StringUtil.generateGraph(sb, width, height, startNanos - period, startNanos, min, max, value -> FormatUtil.timeUnitToString(MathUtil.fastCeil(value), PacketHandler.PingInfo.TIME_UNIT), historyTimestamps.length, ii -> historyTimestamps[ii], ii -> historyValues[ii]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 207 */         message.insert(sb.toString());
/*     */       } 
/*     */     } 
/* 210 */     context.sendMessage(message);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\PingCommand$Graph.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */