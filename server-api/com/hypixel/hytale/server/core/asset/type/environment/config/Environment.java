/*     */ package com.hypixel.hytale.server.core.asset.type.environment.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Int2ObjectMapCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorFeatures;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.codec.validation.validator.MapKeyValidator;
/*     */ import com.hypixel.hytale.common.map.IWeightedElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.FluidParticle;
/*     */ import com.hypixel.hytale.protocol.WorldEnvironment;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluidfx.config.FluidFX;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluidfx.config.FluidParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.codec.WeightedMapCodec;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class Environment
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, Environment>>, NetworkSerializable<WorldEnvironment>
/*     */ {
/*  52 */   public static final int HOURS_PER_DAY = (int)ChronoUnit.DAYS.getDuration().toHours();
/*  53 */   public static final int MAX_KEY_HOUR = HOURS_PER_DAY - 1;
/*  54 */   public static final Integer[] HOURS = new Integer[HOURS_PER_DAY];
/*     */   @Nonnull
/*     */   private static final IWeightedMap<WeatherForecast> DEFAULT_WEATHER_FORECAST;
/*     */   public static final AssetBuilderCodec<String, Environment> CODEC;
/*     */   
/*     */   static {
/*  60 */     WeightedMap.Builder<WeatherForecast> mapBuilder = WeightedMap.builder((Object[])WeatherForecast.EMPTY_ARRAY);
/*  61 */     mapBuilder.put(new WeatherForecast(Weather.UNKNOWN.getId(), 1.0D), 1.0D);
/*  62 */     DEFAULT_WEATHER_FORECAST = mapBuilder.build();
/*     */     
/*  64 */     for (int i = 0; i < HOURS_PER_DAY; i++) {
/*  65 */       HOURS[i] = Integer.valueOf(i);
/*     */     }
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
/* 119 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(Environment.class, Environment::new, (Codec)Codec.STRING, (environment, s) -> environment.id = s, environment -> environment.id, (asset, data) -> asset.data = data, asset -> asset.data).metadata((Metadata)new UIEditorFeatures(new UIEditorFeatures.EditorFeature[] { UIEditorFeatures.EditorFeature.WEATHER_DAYTIME_BAR }))).appendInherited(new KeyedCodec("WaterTint", (Codec)ProtocolCodecs.COLOR), (environment, s) -> environment.waterTint = s, environment -> environment.waterTint, (environment, parent) -> environment.waterTint = parent.waterTint).add()).appendInherited(new KeyedCodec("FluidParticles", (Codec)new MapCodec((Codec)FluidParticle.CODEC, java.util.HashMap::new)), (environment, s) -> environment.fluidParticles = s, environment -> environment.fluidParticles, (environment, parent) -> environment.fluidParticles = parent.fluidParticles).addValidator((Validator)FluidFX.VALIDATOR_CACHE.getMapKeyValidator()).add()).appendInherited(new KeyedCodec("SpawnDensity", (Codec)Codec.DOUBLE), (environment, d) -> environment.spawnDensity = d.doubleValue(), environment -> Double.valueOf(environment.spawnDensity), (environment, parent) -> environment.spawnDensity = parent.spawnDensity).add()).appendInherited(new KeyedCodec("BlockModificationAllowed", (Codec)Codec.BOOLEAN), (environment, b) -> environment.blockModificationAllowed = b.booleanValue(), environment -> Boolean.valueOf(environment.blockModificationAllowed), (environment, parent) -> environment.blockModificationAllowed = parent.blockModificationAllowed).add()).appendInherited(new KeyedCodec("WeatherForecasts", (Codec)new Int2ObjectMapCodec((Codec)new WeightedMapCodec((Codec)WeatherForecast.CODEC, (IWeightedElement[])WeatherForecast.EMPTY_ARRAY), Int2ObjectOpenHashMap::new), true), (environment, l) -> environment.weatherForecasts = l, environment -> environment.weatherForecasts, (environment, parent) -> environment.weatherForecasts = parent.weatherForecasts).addValidator(Validators.nonNull()).addValidator((Validator)new MapKeyValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(MAX_KEY_HOUR)))).addValidator(Validators.requiredMapKeysValidator((Object[])HOURS)).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.WEIGHTED_TIMELINE)).metadata((Metadata)new UIEditorSectionStart("Weather")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).build();
/*     */   }
/* 121 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(Environment::getAssetStore));
/*     */   private static AssetStore<String, Environment, IndexedLookupTableAssetMap<String, Environment>> ASSET_STORE;
/*     */   public static final int UNKNOWN_ID = 0;
/*     */   
/*     */   public static AssetStore<String, Environment, IndexedLookupTableAssetMap<String, Environment>> getAssetStore() {
/* 126 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(Environment.class); 
/* 127 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, Environment> getAssetMap() {
/* 131 */     return (IndexedLookupTableAssetMap<String, Environment>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */   
/* 135 */   public static final Environment UNKNOWN = getUnknownFor("Unknown");
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   protected String id;
/*     */   protected Color waterTint;
/* 141 */   protected Map<String, FluidParticle> fluidParticles = Collections.emptyMap();
/*     */   
/*     */   protected Int2ObjectMap<IWeightedMap<WeatherForecast>> weatherForecasts;
/*     */   protected double spawnDensity;
/*     */   protected boolean blockModificationAllowed = true;
/*     */   private SoftReference<WorldEnvironment> cachedPacket;
/*     */   
/*     */   public Environment(String id, Color waterTint, Map<String, FluidParticle> fluidParticles, Int2ObjectMap<IWeightedMap<WeatherForecast>> weatherForecasts, double spawnDensity) {
/* 149 */     this.id = id;
/* 150 */     this.waterTint = waterTint;
/* 151 */     this.fluidParticles = fluidParticles;
/* 152 */     this.weatherForecasts = weatherForecasts;
/* 153 */     this.spawnDensity = spawnDensity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 161 */     return this.id;
/*     */   }
/*     */   
/*     */   public Color getWaterTint() {
/* 165 */     return this.waterTint;
/*     */   }
/*     */   
/*     */   public Map<String, FluidParticle> getFluidParticles() {
/* 169 */     return this.fluidParticles;
/*     */   }
/*     */   
/*     */   public Int2ObjectMap<IWeightedMap<WeatherForecast>> getWeatherForecasts() {
/* 173 */     return this.weatherForecasts;
/*     */   }
/*     */   
/*     */   public IWeightedMap<WeatherForecast> getWeatherForecast(int hour) {
/* 177 */     if (hour < 0 || hour > MAX_KEY_HOUR) {
/* 178 */       throw new IllegalArgumentException("hour must be in range of 0 to " + MAX_KEY_HOUR);
/*     */     }
/*     */     
/* 181 */     if (this.weatherForecasts == null) return DEFAULT_WEATHER_FORECAST; 
/* 182 */     return (IWeightedMap<WeatherForecast>)this.weatherForecasts.getOrDefault(hour, DEFAULT_WEATHER_FORECAST);
/*     */   }
/*     */   
/*     */   public double getSpawnDensity() {
/* 186 */     return this.spawnDensity;
/*     */   }
/*     */   
/*     */   public boolean isBlockModificationAllowed() {
/* 190 */     return this.blockModificationAllowed;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WorldEnvironment toPacket() {
/* 196 */     WorldEnvironment cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 197 */     if (cached != null) return cached; 
/* 198 */     WorldEnvironment packet = new WorldEnvironment();
/*     */     
/* 200 */     packet.id = this.id;
/*     */     
/* 202 */     if (this.waterTint != null) {
/* 203 */       packet.waterTint = this.waterTint;
/*     */     }
/*     */     
/* 206 */     if (!this.fluidParticles.isEmpty()) {
/* 207 */       Int2ObjectOpenHashMap<Integer, FluidParticle> int2ObjectOpenHashMap = new Int2ObjectOpenHashMap(this.fluidParticles.size());
/* 208 */       for (Map.Entry<String, FluidParticle> entry : this.fluidParticles.entrySet()) {
/* 209 */         int index = FluidFX.getAssetMap().getIndex(entry.getKey());
/* 210 */         if (index != Integer.MIN_VALUE) {
/* 211 */           int2ObjectOpenHashMap.put(Integer.valueOf(index), ((FluidParticle)entry.getValue()).toPacket());
/*     */         }
/*     */       } 
/* 214 */       packet.fluidParticles = (Map)int2ObjectOpenHashMap;
/*     */     } 
/*     */     
/* 217 */     if (this.data != null) {
/* 218 */       IntSet tags = this.data.getExpandedTagIndexes();
/* 219 */       packet.tagIndexes = tags.toIntArray();
/*     */     } 
/*     */     
/* 222 */     this.cachedPacket = new SoftReference<>(packet);
/* 223 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 228 */     if (this == o) return true; 
/* 229 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 231 */     Environment that = (Environment)o;
/*     */     
/* 233 */     return this.id.equals(that.id);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 238 */     return this.id.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 244 */     return "Environment{id='" + this.id + "', waterTint='" + String.valueOf(this.waterTint) + "', fluidParticles='" + String.valueOf(this.fluidParticles) + "', weatherForecasts=" + String.valueOf(this.weatherForecasts) + ", spawnDensity=" + this.spawnDensity + ", packet=" + String.valueOf(this.cachedPacket) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Environment getUnknownFor(final String unknownId) {
/* 256 */     return new Environment()
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIndexOrUnknown(String id, String message, Object... params) {
/* 265 */     int environmentIndex = getAssetMap().getIndex(id);
/*     */ 
/*     */     
/* 268 */     if (environmentIndex == Integer.MIN_VALUE) {
/* 269 */       HytaleLogger.getLogger().at(Level.WARNING).logVarargs(message, params);
/* 270 */       getAssetStore().loadAssets("Hytale:Hytale", Collections.singletonList(getUnknownFor(id)));
/* 271 */       int index = getAssetMap().getIndex(id);
/* 272 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + id); 
/* 273 */       environmentIndex = index;
/*     */     } 
/*     */     
/* 276 */     return environmentIndex;
/*     */   }
/*     */   
/*     */   protected Environment() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\environment\config\Environment.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */