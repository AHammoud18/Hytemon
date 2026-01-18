/*    */ package com.hypixel.hytale.builtin.weather.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeatherGetCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 19 */   private static final Message MESSAGE_COMMANDS_WEATHER_GET_FORCED_WEATHER = Message.translation("server.commands.weather.get.getForcedWeather");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WeatherGetCommand() {
/* 25 */     super("get", "server.commands.weather.get.desc");
/*    */   }
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*    */     String weatherId;
/* 30 */     WeatherResource weatherResource = (WeatherResource)store.getResource(WeatherResource.getResourceType());
/* 31 */     int forcedWeatherIndex = weatherResource.getForcedWeatherIndex();
/*    */ 
/*    */     
/* 34 */     if (forcedWeatherIndex != 0) {
/* 35 */       Weather weatherAsset = (Weather)Weather.getAssetMap().getAsset(forcedWeatherIndex);
/* 36 */       weatherId = weatherAsset.getId();
/*    */     } else {
/* 38 */       weatherId = "not locked";
/*    */     } 
/*    */     
/* 41 */     context.sendMessage(MESSAGE_COMMANDS_WEATHER_GET_FORCED_WEATHER
/* 42 */         .param("worldName", world.getName())
/* 43 */         .param("weather", weatherId));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\weather\commands\WeatherGetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */