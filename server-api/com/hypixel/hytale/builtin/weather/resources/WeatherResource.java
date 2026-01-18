/*     */ package com.hypixel.hytale.builtin.weather.resources;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.weather.WeatherPlugin;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WeatherResource
/*     */   implements Resource<EntityStore>
/*     */ {
/*     */   public static final float WEATHER_UPDATE_RATE_S = 1.0F;
/*     */   private int forcedWeatherIndex;
/*     */   private int previousForcedWeatherIndex;
/*     */   
/*     */   public static ResourceType<EntityStore, WeatherResource> getResourceType() {
/*  27 */     return WeatherPlugin.get().getWeatherResourceType();
/*     */   }
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
/*     */   @Nonnull
/*  48 */   private final Int2IntMap environmentWeather = (Int2IntMap)new Int2IntOpenHashMap();
/*     */ 
/*     */   
/*  51 */   private int previousHour = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float playerUpdateDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WeatherResource() {
/*  62 */     this.environmentWeather.defaultReturnValue(-2147483648);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Int2IntMap getEnvironmentWeather() {
/*  70 */     return this.environmentWeather;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeatherIndexForEnvironment(int environmentId) {
/*  80 */     return this.environmentWeather.get(environmentId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getForcedWeatherIndex() {
/*  87 */     return this.forcedWeatherIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForcedWeather(@Nullable String forcedWeather) {
/*  96 */     this.previousForcedWeatherIndex = this.forcedWeatherIndex;
/*  97 */     this.forcedWeatherIndex = (forcedWeather == null) ? 0 : Weather.getAssetMap().getIndex(forcedWeather);
/*     */   }
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
/*     */   public boolean consumeForcedWeatherChange() {
/* 110 */     if (this.previousForcedWeatherIndex == this.forcedWeatherIndex) return false; 
/* 111 */     this.previousForcedWeatherIndex = this.forcedWeatherIndex;
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean compareAndSwapHour(int currentHour) {
/* 122 */     if (currentHour == this.previousHour) return false; 
/* 123 */     this.previousHour = currentHour;
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Resource<EntityStore> clone() {
/* 130 */     return new WeatherResource();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\weather\resources\WeatherResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */