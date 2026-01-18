/*    */ package com.hypixel.hytale.builtin.weather;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.weather.commands.WeatherCommand;
/*    */ import com.hypixel.hytale.builtin.weather.components.WeatherTracker;
/*    */ import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
/*    */ import com.hypixel.hytale.builtin.weather.systems.WeatherSystem;
/*    */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandManager;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeatherPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   private static WeatherPlugin instance;
/*    */   private ComponentType<EntityStore, WeatherTracker> weatherTrackerComponentType;
/*    */   private ResourceType<EntityStore, WeatherResource> weatherResourceType;
/*    */   
/*    */   public static WeatherPlugin get() {
/* 31 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WeatherPlugin(@Nonnull JavaPluginInit init) {
/* 50 */     super(init);
/* 51 */     instance = this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 56 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/*    */     
/* 58 */     this.weatherResourceType = EntityStore.REGISTRY.registerResource(WeatherResource.class, WeatherResource::new);
/* 59 */     this.weatherTrackerComponentType = EntityStore.REGISTRY.registerComponent(WeatherTracker.class, WeatherTracker::new);
/*    */     
/* 61 */     entityStoreRegistry.registerSystem((ISystem)new WeatherSystem.WorldAddedSystem());
/* 62 */     entityStoreRegistry.registerSystem((ISystem)new WeatherSystem.PlayerAddedSystem());
/* 63 */     entityStoreRegistry.registerSystem((ISystem)new WeatherSystem.TickingSystem());
/* 64 */     entityStoreRegistry.registerSystem((ISystem)new WeatherSystem.InvalidateWeatherAfterTeleport());
/*    */     
/* 66 */     CommandManager.get().registerSystemCommand((AbstractCommand)new WeatherCommand());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ComponentType<EntityStore, WeatherTracker> getWeatherTrackerComponentType() {
/* 74 */     return this.weatherTrackerComponentType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ResourceType<EntityStore, WeatherResource> getWeatherResourceType() {
/* 82 */     return this.weatherResourceType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\weather\WeatherPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */