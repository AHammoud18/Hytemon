/*    */ package com.hypixel.hytale.builtin.weather.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.StoreSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldAddedSystem
/*    */   extends StoreSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 49 */   private final ResourceType<EntityStore, WeatherResource> weatherResourceType = WeatherResource.getResourceType();
/*    */ 
/*    */   
/*    */   public void onSystemAddedToStore(@Nonnull Store<EntityStore> store) {
/* 53 */     String forcedWeather = ((EntityStore)store.getExternalData()).getWorld().getWorldConfig().getForcedWeather();
/* 54 */     ((WeatherResource)store.getResource(this.weatherResourceType)).setForcedWeather(forcedWeather);
/*    */   }
/*    */   
/*    */   public void onSystemRemovedFromStore(@Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\weather\systems\WeatherSystem$WorldAddedSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */