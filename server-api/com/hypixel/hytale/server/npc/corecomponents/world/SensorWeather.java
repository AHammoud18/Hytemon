/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorWeather;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SensorWeather extends SensorBase {
/*    */   @Nullable
/*    */   protected final String[] weathers;
/*    */   protected int prevWeatherIndex;
/*    */   protected boolean cachedResult;
/*    */   
/*    */   public SensorWeather(@Nonnull BuilderSensorWeather builder, @Nonnull BuilderSupport support) {
/* 25 */     super((BuilderSensorBase)builder);
/* 26 */     this.weathers = builder.getWeathers(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 31 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 33 */     int weatherIndex = role.getWorldSupport().getCurrentWeatherIndex((ComponentAccessor)store);
/* 34 */     if (weatherIndex == 0) return false;
/*    */     
/* 36 */     if (weatherIndex == this.prevWeatherIndex) return this.cachedResult;
/*    */     
/* 38 */     String weatherAssetId = ((Weather)Weather.getAssetMap().getAsset(weatherIndex)).getId();
/* 39 */     this.prevWeatherIndex = weatherIndex;
/* 40 */     this.cachedResult = matchesWeather(weatherAssetId);
/* 41 */     return this.cachedResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 46 */     return null;
/*    */   }
/*    */   
/*    */   protected boolean matchesWeather(@Nullable String weather) {
/* 50 */     if (weather == null) return false;
/*    */     
/* 52 */     for (String weatherMatcher : this.weathers) {
/* 53 */       if (StringUtil.isGlobMatching(weatherMatcher, weather)) return true;
/*    */     
/*    */     } 
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorWeather.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */