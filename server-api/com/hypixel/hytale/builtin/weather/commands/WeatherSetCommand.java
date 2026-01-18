/*    */ package com.hypixel.hytale.builtin.weather.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class WeatherSetCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_WEATHER_SET_FORCED_WEATHER_SET = Message.translation("server.commands.weather.set.forcedWeatherSet");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 30 */   private final RequiredArg<Weather> weatherArg = withRequiredArg("weather", "server.commands.weather.set.weather.desc", (ArgumentType)ArgTypes.WEATHER_ASSET);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WeatherSetCommand() {
/* 36 */     super("set", "server.commands.weather.set.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 41 */     Weather weather = (Weather)this.weatherArg.get(context);
/* 42 */     String weatherName = weather.getId();
/* 43 */     setForcedWeather(world, weatherName, (ComponentAccessor<EntityStore>)store);
/*    */     
/* 45 */     context.sendMessage(MESSAGE_COMMANDS_WEATHER_SET_FORCED_WEATHER_SET
/* 46 */         .param("worldName", world.getName())
/* 47 */         .param("weather", weatherName));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static void setForcedWeather(@Nonnull World world, @Nullable String forcedWeather, ComponentAccessor<EntityStore> componentAccessor) {
/* 58 */     WeatherResource weatherResource = (WeatherResource)componentAccessor.getResource(WeatherResource.getResourceType());
/* 59 */     weatherResource.setForcedWeather(forcedWeather);
/*    */     
/* 61 */     WorldConfig config = world.getWorldConfig();
/* 62 */     config.setForcedWeather(forcedWeather);
/* 63 */     config.markChanged();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\weather\commands\WeatherSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */