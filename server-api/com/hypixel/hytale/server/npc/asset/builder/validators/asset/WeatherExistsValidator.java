/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators.asset;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeatherExistsValidator
/*    */   extends AssetValidator
/*    */ {
/* 14 */   private static final WeatherExistsValidator DEFAULT_INSTANCE = new WeatherExistsValidator();
/*    */ 
/*    */   
/*    */   private WeatherExistsValidator() {}
/*    */ 
/*    */   
/*    */   private WeatherExistsValidator(EnumSet<AssetValidator.Config> config) {
/* 21 */     super(config);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDomain() {
/* 27 */     return "Weather";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(String value) {
/* 32 */     return (Weather.getAssetMap().getAsset(value) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String value, String attribute) {
/* 38 */     return "The weather with the name \"" + value + "\" does not exist for attribute \"" + attribute + "\"";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getAssetName() {
/* 44 */     return Weather.class.getSimpleName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static WeatherExistsValidator required() {
/* 52 */     return DEFAULT_INSTANCE;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static WeatherExistsValidator withConfig(EnumSet<AssetValidator.Config> config) {
/* 57 */     return new WeatherExistsValidator(config);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\asset\WeatherExistsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */