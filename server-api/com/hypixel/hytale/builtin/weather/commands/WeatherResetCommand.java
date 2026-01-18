/*    */ package com.hypixel.hytale.builtin.weather.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class WeatherResetCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 17 */   private static final Message MESSAGE_COMMANDS_WEATHER_RESET_FORCED_WEATHER_RESET = Message.translation("server.commands.weather.reset.forcedWeatherReset");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WeatherResetCommand() {
/* 23 */     super("reset", "server.commands.weather.reset.desc");
/* 24 */     addAliases(new String[] { "clear" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 29 */     WeatherSetCommand.setForcedWeather(world, null, (ComponentAccessor<EntityStore>)store);
/* 30 */     context.sendMessage(MESSAGE_COMMANDS_WEATHER_RESET_FORCED_WEATHER_RESET
/* 31 */         .param("worldName", world.getName()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\weather\commands\WeatherResetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */