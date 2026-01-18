/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world.tps;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class WorldTpsCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_WORLD_TPS_SET_SUCCESS = Message.translation("server.commands.world.tps.set.success");
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_WORLD_TPS_SET_INVALID = Message.translation("server.commands.world.tps.set.invalid");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 28 */   private final RequiredArg<Integer> tickRateArg = withRequiredArg("rate", "server.commands.world.tps.rate.desc", (ArgumentType)ArgTypes.TICK_RATE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldTpsCommand() {
/* 34 */     super("tps", "server.commands.world.tps.desc");
/* 35 */     addAliases(new String[] { "tickrate" });
/* 36 */     addSubCommand((AbstractCommand)new WorldTpsResetCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 41 */     int newTickRate = ((Integer)this.tickRateArg.get(context)).intValue();
/*    */     
/* 43 */     if (newTickRate <= 0 || newTickRate > 2048) {
/* 44 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_TPS_SET_INVALID
/* 45 */           .param("value", newTickRate));
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     world.setTps(newTickRate);
/* 50 */     double newMs = 1000.0D / newTickRate;
/* 51 */     context.sendMessage(MESSAGE_COMMANDS_WORLD_TPS_SET_SUCCESS
/* 52 */         .param("worldName", world.getName())
/* 53 */         .param("tps", newTickRate)
/* 54 */         .param("ms", String.format("%.2f", new Object[] { Double.valueOf(newMs) })));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\tps\WorldTpsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */