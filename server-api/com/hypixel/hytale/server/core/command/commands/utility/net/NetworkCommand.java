/*     */ package com.hypixel.hytale.server.core.command.commands.utility.net;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.entity.knockback.KnockbackSystems;
/*     */ import com.hypixel.hytale.server.core.io.netty.LatencySimulationHandler;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.KnockbackPredictionSystems;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import io.netty.channel.Channel;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public NetworkCommand() {
/*  34 */     super("network", "server.commands.network.desc");
/*  35 */     addAliases(new String[] { "net" });
/*  36 */     addSubCommand((AbstractCommand)new LatencySimulationCommand());
/*  37 */     addSubCommand((AbstractCommand)new ServerKnockbackCommand());
/*  38 */     addSubCommand((AbstractCommand)new DebugKnockbackCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ServerKnockbackCommand
/*     */     extends CommandBase
/*     */   {
/*     */     ServerKnockbackCommand() {
/*  50 */       super("serverknockback", "server.commands.network.serverknockback.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/*  55 */       KnockbackSystems.ApplyPlayerKnockback.DO_SERVER_PREDICTION = !KnockbackSystems.ApplyPlayerKnockback.DO_SERVER_PREDICTION;
/*  56 */       context.sendMessage(Message.translation("server.commands.network.knockbackServerPredictionEnabled")
/*  57 */           .param("enabled", KnockbackSystems.ApplyPlayerKnockback.DO_SERVER_PREDICTION));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DebugKnockbackCommand
/*     */     extends CommandBase
/*     */   {
/*     */     DebugKnockbackCommand() {
/*  70 */       super("debugknockback", "server.commands.network.debugknockback.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/*  75 */       KnockbackPredictionSystems.DEBUG_KNOCKBACK_POSITION = !KnockbackPredictionSystems.DEBUG_KNOCKBACK_POSITION;
/*  76 */       context.sendMessage(Message.translation("server.commands.network.knockbackDebugEnabled")
/*  77 */           .param("enabled", KnockbackPredictionSystems.DEBUG_KNOCKBACK_POSITION));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LatencySimulationCommand
/*     */     extends AbstractCommandCollection
/*     */   {
/*     */     public LatencySimulationCommand() {
/*  90 */       super("latencysimulation", "server.commands.latencySimulation.desc");
/*  91 */       addAliases(new String[] { "latsim" });
/*  92 */       addSubCommand((AbstractCommand)new Set());
/*  93 */       addSubCommand((AbstractCommand)new Reset());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static class Set
/*     */       extends AbstractTargetPlayerCommand
/*     */     {
/*     */       @Nonnull
/* 105 */       private final RequiredArg<Integer> delayArg = withRequiredArg("delay", "server.commands.latencySimulation.set.delay.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       Set() {
/* 111 */         super("set", "server.commands.latencySimulation.set.desc");
/*     */       }
/*     */ 
/*     */       
/*     */       protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 116 */         int delay = ((Integer)this.delayArg.get(context)).intValue();
/* 117 */         Channel channel = playerRef.getPacketHandler().getChannel();
/* 118 */         LatencySimulationHandler.setLatency(channel, delay, TimeUnit.MILLISECONDS);
/* 119 */         context.sendMessage(Message.translation("server.commands.latencySimulation.set.success")
/* 120 */             .param("millis", delay));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     static class Reset
/*     */       extends AbstractTargetPlayerCommand
/*     */     {
/*     */       @Nonnull
/* 129 */       private static final Message MESSAGE_COMMANDS_LATENCY_SIMULATION_RESET_SUCCESS = Message.translation("server.commands.latencySimulation.reset.success");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       Reset() {
/* 135 */         super("reset", "server.commands.latencySimulation.reset.desc");
/* 136 */         addAliases(new String[] { "clear" });
/*     */       }
/*     */       
/*     */       protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store)
/*     */       {
/* 141 */         Channel channel = playerRef.getPacketHandler().getChannel();
/* 142 */         LatencySimulationHandler.setLatency(channel, 0L, TimeUnit.MILLISECONDS);
/* 143 */         context.sendMessage(MESSAGE_COMMANDS_LATENCY_SIMULATION_RESET_SUCCESS); } } } static class Set extends AbstractTargetPlayerCommand { @Nonnull private final RequiredArg<Integer> delayArg = withRequiredArg("delay", "server.commands.latencySimulation.set.delay.desc", (ArgumentType)ArgTypes.INTEGER); Set() { super("set", "server.commands.latencySimulation.set.desc"); } protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) { int delay = ((Integer)this.delayArg.get(context)).intValue(); Channel channel = playerRef.getPacketHandler().getChannel(); LatencySimulationHandler.setLatency(channel, delay, TimeUnit.MILLISECONDS); context.sendMessage(Message.translation("server.commands.latencySimulation.set.success").param("millis", delay)); } } static class Reset extends AbstractTargetPlayerCommand { protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) { Channel channel = playerRef.getPacketHandler().getChannel(); LatencySimulationHandler.setLatency(channel, 0L, TimeUnit.MILLISECONDS); context.sendMessage(MESSAGE_COMMANDS_LATENCY_SIMULATION_RESET_SUCCESS); }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private static final Message MESSAGE_COMMANDS_LATENCY_SIMULATION_RESET_SUCCESS = Message.translation("server.commands.latencySimulation.reset.success");
/*     */     
/*     */     Reset() {
/*     */       super("reset", "server.commands.latencySimulation.reset.desc");
/*     */       addAliases(new String[] { "clear" });
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\net\NetworkCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */