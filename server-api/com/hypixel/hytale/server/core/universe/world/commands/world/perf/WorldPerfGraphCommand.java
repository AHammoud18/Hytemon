/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world.perf;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.FormatUtil;
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.metrics.metric.HistoricMetric;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPerfGraphCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 28 */   private final DefaultArg<Integer> widthArg = withDefaultArg("width", "server.commands.world.perf.graph.width.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(100), "server.commands.world.perf.graph.width.default");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final DefaultArg<Integer> heightArg = withDefaultArg("height", "server.commands.world.perf.graph.height.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(10), "server.commands.world.perf.graph.height.default");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldPerfGraphCommand() {
/* 40 */     super("graph", "server.commands.world.perf.graph.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 45 */     Integer width = (Integer)this.widthArg.get(context);
/* 46 */     Integer height = (Integer)this.heightArg.get(context);
/* 47 */     long startNano = System.nanoTime();
/*    */     
/* 49 */     Message msg = Message.empty();
/* 50 */     HistoricMetric historicMetric = world.getBufferedTickLengthMetricSet();
/* 51 */     long[] periods = historicMetric.getPeriodsNanos();
/*    */     
/* 53 */     for (int i = 0; i < periods.length; i++) {
/* 54 */       long period = periods[i];
/* 55 */       long[] historyTimestamps = historicMetric.getTimestamps(i);
/* 56 */       long[] historyValues = historicMetric.getValues(i);
/* 57 */       String historyLengthFormatted = FormatUtil.timeUnitToString(period, TimeUnit.NANOSECONDS, true);
/*    */       
/* 59 */       msg.insert(Message.translation("server.commands.world.perf.graph")
/* 60 */           .param("time", historyLengthFormatted));
/*    */       
/* 62 */       StringBuilder sb = new StringBuilder();
/* 63 */       StringUtil.generateGraph(sb, width
/*    */           
/* 65 */           .intValue(), height
/* 66 */           .intValue(), startNano - period, startNano, 0.0D, world
/*    */ 
/*    */ 
/*    */           
/* 70 */           .getTps(), value -> String.valueOf(MathUtil.round(value, 2)), historyTimestamps.length, ii -> historyTimestamps[ii], ii -> WorldPerfCommand.tpsFromDelta(historyValues[ii], world.getTickStepNanos()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 76 */       msg.insert(sb.toString()).insert("\n");
/*    */     } 
/*    */     
/* 79 */     context.sendMessage(msg);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\perf\WorldPerfGraphCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */