/*    */ package com.hypixel.hytale.server.core.asset.type.environment.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.common.map.IWeightedElement;
/*    */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeatherForecast
/*    */   implements IWeightedElement
/*    */ {
/*    */   public static final BuilderCodec<WeatherForecast> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WeatherForecast.class, WeatherForecast::new).append(new KeyedCodec("WeatherId", (Codec)Codec.STRING), (weatherForecast, s) -> weatherForecast.weatherId = s, weatherForecast -> weatherForecast.weatherId).addValidator(Validators.nonNull()).addValidator(Weather.VALIDATOR_CACHE.getValidator()).add()).addField(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (spawn, s) -> spawn.weight = s.doubleValue(), spawn -> Double.valueOf(spawn.weight))).afterDecode(WeatherForecast::processConfig)).build();
/*    */   }
/* 27 */   public static final WeatherForecast[] EMPTY_ARRAY = new WeatherForecast[0];
/*    */   
/*    */   protected String weatherId;
/*    */   protected transient int weatherIndex;
/*    */   protected double weight;
/*    */   
/*    */   public WeatherForecast(String weatherId, double weight) {
/* 34 */     this.weatherId = weatherId;
/* 35 */     this.weight = weight;
/*    */   }
/*    */ 
/*    */   
/*    */   protected WeatherForecast() {}
/*    */   
/*    */   public String getWeatherId() {
/* 42 */     return this.weatherId;
/*    */   }
/*    */   
/*    */   public int getWeatherIndex() {
/* 46 */     return this.weatherIndex;
/*    */   }
/*    */   
/*    */   protected void processConfig() {
/* 50 */     this.weatherIndex = Weather.getAssetMap().getIndex(this.weatherId);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 56 */     return "WeatherForecast{weatherId='" + this.weatherId + "', weatherIndex=" + this.weatherIndex + ", weight=" + this.weight + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getWeight() {
/* 65 */     return this.weight;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\environment\config\WeatherForecast.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */