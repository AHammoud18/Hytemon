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
/*     */ import com.hypixel.hytale.server.core.io.netty.LatencySimulationHandler;
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
/*     */ public class LatencySimulationCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public LatencySimulationCommand() {
/*  90 */     super("latencysimulation", "server.commands.latencySimulation.desc");
/*  91 */     addAliases(new String[] { "latsim" });
/*  92 */     addSubCommand((AbstractCommand)new Set());
/*  93 */     addSubCommand((AbstractCommand)new Reset());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Set
/*     */     extends AbstractTargetPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 105 */     private final RequiredArg<Integer> delayArg = withRequiredArg("delay", "server.commands.latencySimulation.set.delay.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Set() {
/* 111 */       super("set", "server.commands.latencySimulation.set.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 116 */       int delay = ((Integer)this.delayArg.get(context)).intValue();
/* 117 */       Channel channel = playerRef.getPacketHandler().getChannel();
/* 118 */       LatencySimulationHandler.setLatency(channel, delay, TimeUnit.MILLISECONDS);
/* 119 */       context.sendMessage(Message.translation("server.commands.latencySimulation.set.success")
/* 120 */           .param("millis", delay));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Reset
/*     */     extends AbstractTargetPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 129 */     private static final Message MESSAGE_COMMANDS_LATENCY_SIMULATION_RESET_SUCCESS = Message.translation("server.commands.latencySimulation.reset.success");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Reset() {
/* 135 */       super("reset", "server.commands.latencySimulation.reset.desc");
/* 136 */       addAliases(new String[] { "clear" });
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 141 */       Channel channel = playerRef.getPacketHandler().getChannel();
/* 142 */       LatencySimulationHandler.setLatency(channel, 0L, TimeUnit.MILLISECONDS);
/* 143 */       context.sendMessage(MESSAGE_COMMANDS_LATENCY_SIMULATION_RESET_SUCCESS);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\net\NetworkCommand$LatencySimulationCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */