/*     */ package com.hypixel.hytale.server.core.asset.type.weather.config;
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
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorFeatures;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.Cloud;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.ColorAlpha;
/*     */ import com.hypixel.hytale.protocol.NearFar;
/*     */ import com.hypixel.hytale.protocol.Weather;
/*     */ import com.hypixel.hytale.protocol.WeatherParticle;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class Weather
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, Weather>>, NetworkSerializable<Weather>
/*     */ {
/*     */   public static final BuilderCodec<WeatherParticle> PARTICLE_CODEC;
/*     */   public static final AssetBuilderCodec<String, Weather> CODEC;
/*     */   
/*     */   static {
/*  76 */     PARTICLE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WeatherParticle.class, WeatherParticle::new).documentation("Particle System that can be spawned in relation to a weather.")).append(new KeyedCodec("SystemId", (Codec)Codec.STRING), (particle, s) -> particle.systemId = s, particle -> particle.systemId).addValidator(Validators.nonNull()).addValidator(ParticleSystem.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR), (particle, o) -> particle.color = o, particle -> particle.color).documentation("The colour used if none was specified in the particle settings.").add()).append(new KeyedCodec("Scale", (Codec)Codec.FLOAT), (particle, f) -> particle.scale = f.floatValue(), particle -> Float.valueOf(particle.scale)).documentation("The scale of the particle system.").add()).append(new KeyedCodec("OvergroundOnly", (Codec)Codec.BOOLEAN), (particle, s) -> particle.isOvergroundOnly = s.booleanValue(), particle -> Boolean.valueOf(particle.isOvergroundOnly)).documentation("Sets if the particles can only spawn above the columns highest blocks.").add()).append(new KeyedCodec("PositionOffsetMultiplier", (Codec)Codec.FLOAT), (particle, f) -> particle.positionOffsetMultiplier = f.floatValue(), particle -> Float.valueOf(particle.positionOffsetMultiplier)).documentation("The amount the system will move ahead of the camera.").add()).build();
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
/* 306 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(Weather.class, Weather::new, (Codec)Codec.STRING, (weather, s) -> weather.id = s, weather -> weather.id, (asset, data) -> asset.data = data, asset -> asset.data).metadata((Metadata)new UIEditorFeatures(new UIEditorFeatures.EditorFeature[] { UIEditorFeatures.EditorFeature.WEATHER_DAYTIME_BAR, UIEditorFeatures.EditorFeature.WEATHER_PREVIEW_LOCAL }))).appendInherited(new KeyedCodec("Stars", (Codec)Codec.STRING), (weather, o) -> weather.stars = o, weather -> weather.stars, (weather, parent) -> weather.stars = parent.stars).addValidator((Validator)CommonAssetValidator.TEXTURE_SKY).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("ScreenEffect", (Codec)Codec.STRING), (weather, o) -> weather.screenEffect = o, weather -> weather.screenEffect, (weather, parent) -> weather.screenEffect = parent.screenEffect).addValidator((Validator)CommonAssetValidator.UI_SCREEN_EFFECT).add()).appendInherited(new KeyedCodec("FogDistance", (Codec)Codec.DOUBLE_ARRAY), (weather, o) -> { weather.fogDistance = new float[2]; weather.fogDistance[0] = (float)o[0]; weather.fogDistance[1] = (float)o[1]; }weather -> new double[] { weather.fogDistance[0], weather.fogDistance[1] }, (weather, parent) -> weather.fogDistance = parent.fogDistance).addValidator(Validators.nonNull()).addValidator(Validators.doubleArraySize(2)).addValidator(Validators.monotonicSequentialDoubleArrayValidator()).documentation("Array of strictly two values. First is FogNear, which is expected to be negative. Second is FogFar. FogNear determines how foggy it is at the player's position, while FogFar determines the range at which FogDensities starts ramping up.").add()).appendInherited(new KeyedCodec("FogOptions", (Codec)FogOptions.CODEC), (weather, o) -> weather.fogOptions = o, weather -> weather.fogOptions, (weather, parent) -> weather.fogOptions = parent.fogOptions).documentation("Optional extra information about the fog for this Weather").add()).appendInherited(new KeyedCodec("Particle", (Codec)PARTICLE_CODEC), (weather, o) -> weather.particle = o, weather -> weather.particle, (weather, parent) -> weather.particle = parent.particle).add()).appendInherited(new KeyedCodec("ScreenEffectColors", (Codec)TimeColorAlpha.ARRAY_CODEC), (weather, o) -> weather.screenEffectColors = o, weather -> weather.screenEffectColors, (weather, parent) -> weather.screenEffectColors = parent.screenEffectColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("SunlightDampingMultipliers", (Codec)new ArrayCodec((Codec)TimeFloat.CODEC, x$0 -> new TimeFloat[x$0])), (weather, o) -> weather.sunlightDampingMultiplier = o, weather -> weather.sunlightDampingMultiplier, (weather, parent) -> weather.sunlightDampingMultiplier = parent.sunlightDampingMultiplier).metadata((Metadata)new UIEditorSectionStart("Colors")).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("SunlightColors", (Codec)TimeColor.ARRAY_CODEC), (weather, o) -> weather.sunlightColors = o, weather -> weather.sunlightColors, (weather, parent) -> weather.sunlightColors = parent.sunlightColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("SunColors", (Codec)TimeColor.ARRAY_CODEC), (weather, o) -> weather.sunColors = o, weather -> weather.sunColors, (weather, parent) -> weather.sunColors = parent.sunColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("MoonColors", (Codec)TimeColorAlpha.ARRAY_CODEC), (weather, o) -> weather.moonColors = o, weather -> weather.moonColors, (weather, parent) -> weather.moonColors = parent.moonColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("SunGlowColors", (Codec)TimeColorAlpha.ARRAY_CODEC), (weather, o) -> weather.sunGlowColors = o, weather -> weather.sunGlowColors, (weather, parent) -> weather.sunGlowColors = parent.sunGlowColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("MoonGlowColors", (Codec)TimeColorAlpha.ARRAY_CODEC), (weather, o) -> weather.moonGlowColors = o, weather -> weather.moonGlowColors, (weather, parent) -> weather.moonGlowColors = parent.moonGlowColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("SunScales", (Codec)new ArrayCodec((Codec)TimeFloat.CODEC, x$0 -> new TimeFloat[x$0])), (weather, o) -> weather.sunScales = o, weather -> weather.sunScales, (weather, parent) -> weather.sunScales = parent.sunScales).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("MoonScales", (Codec)new ArrayCodec((Codec)TimeFloat.CODEC, x$0 -> new TimeFloat[x$0])), (weather, o) -> weather.moonScales = o, weather -> weather.moonScales, (weather, parent) -> weather.moonScales = parent.moonScales).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("SkyTopColors", (Codec)TimeColorAlpha.ARRAY_CODEC), (weather, o) -> weather.skyTopColors = o, weather -> weather.skyTopColors, (weather, parent) -> weather.skyTopColors = parent.skyTopColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("SkyBottomColors", (Codec)TimeColorAlpha.ARRAY_CODEC), (weather, o) -> weather.skyBottomColors = o, weather -> weather.skyBottomColors, (weather, parent) -> weather.skyBottomColors = parent.skyBottomColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("SkySunsetColors", (Codec)TimeColorAlpha.ARRAY_CODEC), (weather, o) -> weather.skySunsetColors = o, weather -> weather.skySunsetColors, (weather, parent) -> weather.skySunsetColors = parent.skySunsetColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("FogColors", (Codec)TimeColor.ARRAY_CODEC), (weather, o) -> weather.fogColors = o, weather -> weather.fogColors, (weather, parent) -> weather.fogColors = parent.fogColors).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("FogHeightFalloffs", (Codec)new ArrayCodec((Codec)TimeFloat.CODEC, x$0 -> new TimeFloat[x$0])), (weather, o) -> weather.fogHeightFalloffs = o, weather -> weather.fogHeightFalloffs, (weather, parent) -> weather.fogHeightFalloffs = parent.fogHeightFalloffs).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("FogDensities", (Codec)new ArrayCodec((Codec)TimeFloat.CODEC, x$0 -> new TimeFloat[x$0])), (weather, o) -> weather.fogDensities = o, weather -> weather.fogDensities, (weather, parent) -> weather.fogDensities = parent.fogDensities).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("WaterTints", (Codec)TimeColor.ARRAY_CODEC), (weather, o) -> weather.waterTints = o, weather -> weather.waterTints, (weather, parent) -> weather.waterTints = parent.waterTints).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("ColorFilters", (Codec)TimeColor.ARRAY_CODEC), (weather, o) -> weather.colorFilters = o, weather -> weather.colorFilters, (weather, parent) -> weather.colorFilters = parent.colorFilters).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)UIEditor.TIMELINE)).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("Moons", (Codec)new ArrayCodec((Codec)DayTexture.CODEC, x$0 -> new DayTexture[x$0])), (weather, o) -> weather.moons = o, weather -> weather.moons, (weather, parent) -> weather.moons = parent.moons).metadata((Metadata)new UIEditorSectionStart("Moons")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("Clouds", (Codec)new ArrayCodec((Codec)Cloud.CODEC, x$0 -> new Cloud[x$0])), (weather, o) -> weather.clouds = o, weather -> weather.clouds, (weather, parent) -> weather.clouds = parent.clouds).metadata((Metadata)new UIEditorSectionStart("Clouds")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).build();
/*     */   }
/* 308 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(Weather::getAssetStore));
/*     */   
/*     */   private static AssetStore<String, Weather, IndexedLookupTableAssetMap<String, Weather>> ASSET_STORE;
/*     */   
/*     */   public static AssetStore<String, Weather, IndexedLookupTableAssetMap<String, Weather>> getAssetStore() {
/* 313 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(Weather.class); 
/* 314 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, Weather> getAssetMap() {
/* 318 */     return (IndexedLookupTableAssetMap<String, Weather>)getAssetStore().getAssetMap();
/*     */   }
/*     */   
/* 321 */   public static final float[] DEFAULT_FOG_DISTANCE = new float[] { -96.0F, 1024.0F };
/*     */   
/*     */   public static final int UNKNOWN_ID = 0;
/* 324 */   public static final Weather UNKNOWN = new Weather("Unknown");
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   protected String id;
/*     */   protected DayTexture[] moons;
/*     */   protected Cloud[] clouds;
/*     */   protected TimeFloat[] sunlightDampingMultiplier;
/*     */   protected TimeColor[] sunlightColors;
/*     */   protected TimeColor[] sunColors;
/*     */   protected TimeColorAlpha[] moonColors;
/*     */   protected TimeColorAlpha[] sunGlowColors;
/*     */   protected TimeColorAlpha[] moonGlowColors;
/*     */   protected TimeFloat[] sunScales;
/*     */   protected TimeFloat[] moonScales;
/*     */   protected TimeColorAlpha[] skyTopColors;
/*     */   protected TimeColorAlpha[] skyBottomColors;
/*     */   protected TimeColorAlpha[] skySunsetColors;
/*     */   protected TimeColor[] fogColors;
/*     */   protected TimeFloat[] fogHeightFalloffs;
/*     */   protected TimeFloat[] fogDensities;
/*     */   protected TimeColor[] waterTints;
/* 346 */   protected float[] fogDistance = DEFAULT_FOG_DISTANCE;
/*     */   
/*     */   protected FogOptions fogOptions;
/*     */   protected String screenEffect;
/*     */   protected TimeColorAlpha[] screenEffectColors;
/*     */   protected TimeColor[] colorFilters;
/*     */   protected String stars;
/*     */   protected WeatherParticle particle;
/*     */   private SoftReference<Weather> cachedPacket;
/*     */   
/*     */   public Weather(String id, DayTexture[] moons, Cloud[] clouds, TimeFloat[] sunlightDampingMultiplier, TimeColor[] sunlightColors, TimeColor[] sunColors, TimeColorAlpha[] moonColors, TimeColorAlpha[] sunGlowColors, TimeColorAlpha[] moonGlowColors, TimeFloat[] sunScales, TimeFloat[] moonScales, TimeColorAlpha[] skyTopColors, TimeColorAlpha[] skyBottomColors, TimeColorAlpha[] skySunsetColors, TimeColor[] fogColors, TimeFloat[] fogHeightFalloffs, TimeFloat[] fogDensities, TimeColor[] waterTints, float[] fogDistance, FogOptions fogOptions, String screenEffect, TimeColorAlpha[] screenEffectColors, TimeColor[] colorFilters, String stars, WeatherParticle particle) {
/* 357 */     this.id = id;
/* 358 */     this.moons = moons;
/* 359 */     this.clouds = clouds;
/* 360 */     this.sunlightDampingMultiplier = sunlightDampingMultiplier;
/* 361 */     this.sunlightColors = sunlightColors;
/* 362 */     this.sunColors = sunColors;
/* 363 */     this.moonColors = moonColors;
/* 364 */     this.sunGlowColors = sunGlowColors;
/* 365 */     this.moonGlowColors = moonGlowColors;
/* 366 */     this.sunScales = sunScales;
/* 367 */     this.moonScales = moonScales;
/* 368 */     this.skyTopColors = skyTopColors;
/* 369 */     this.skyBottomColors = skyBottomColors;
/* 370 */     this.skySunsetColors = skySunsetColors;
/* 371 */     this.fogColors = fogColors;
/* 372 */     this.fogHeightFalloffs = fogHeightFalloffs;
/* 373 */     this.fogDensities = fogDensities;
/* 374 */     this.waterTints = waterTints;
/* 375 */     this.fogDistance = fogDistance;
/* 376 */     this.fogOptions = fogOptions;
/* 377 */     this.screenEffect = screenEffect;
/* 378 */     this.screenEffectColors = screenEffectColors;
/* 379 */     this.colorFilters = colorFilters;
/* 380 */     this.stars = stars;
/* 381 */     this.particle = particle;
/*     */   }
/*     */   
/*     */   public Weather(String id) {
/* 385 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Weather toPacket() {
/* 394 */     Weather cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 395 */     if (cached != null) return cached;
/*     */     
/* 397 */     Weather packet = new Weather();
/* 398 */     packet.id = this.id;
/*     */     
/* 400 */     if (this.moons != null && this.moons.length > 0) packet.moons = toStringMap(this.moons); 
/* 401 */     if (this.clouds != null && this.clouds.length > 0) packet.clouds = (Cloud[])ArrayUtil.copyAndMutate((Object[])this.clouds, Cloud::toPacket, x$0 -> new Cloud[x$0]); 
/* 402 */     if (this.sunlightDampingMultiplier != null && this.sunlightDampingMultiplier.length > 0) packet.sunlightDampingMultiplier = toFloatMap(this.sunlightDampingMultiplier); 
/* 403 */     if (this.sunlightColors != null && this.sunlightColors.length > 0) packet.sunlightColors = toColorMap(this.sunlightColors); 
/* 404 */     if (this.sunColors != null && this.sunColors.length > 0) packet.sunColors = toColorMap(this.sunColors); 
/* 405 */     if (this.sunGlowColors != null && this.sunGlowColors.length > 0) packet.sunGlowColors = toColorAlphaMap(this.sunGlowColors); 
/* 406 */     if (this.sunScales != null && this.sunScales.length > 0) packet.sunScales = toFloatMap(this.sunScales); 
/* 407 */     if (this.moonColors != null && this.moonColors.length > 0) packet.moonColors = toColorAlphaMap(this.moonColors); 
/* 408 */     if (this.moonGlowColors != null && this.moonGlowColors.length > 0) packet.moonGlowColors = toColorAlphaMap(this.moonGlowColors); 
/* 409 */     if (this.moonScales != null && this.moonScales.length > 0) packet.moonScales = toFloatMap(this.moonScales); 
/* 410 */     if (this.skyTopColors != null && this.skyTopColors.length > 0) packet.skyTopColors = toColorAlphaMap(this.skyTopColors); 
/* 411 */     if (this.skyBottomColors != null && this.skyBottomColors.length > 0) packet.skyBottomColors = toColorAlphaMap(this.skyBottomColors); 
/* 412 */     if (this.skySunsetColors != null && this.skySunsetColors.length > 0) packet.skySunsetColors = toColorAlphaMap(this.skySunsetColors); 
/* 413 */     if (this.fogColors != null && this.fogColors.length > 0) packet.fogColors = toColorMap(this.fogColors); 
/* 414 */     if (this.fogHeightFalloffs != null && this.fogHeightFalloffs.length > 0) packet.fogHeightFalloffs = toFloatMap(this.fogHeightFalloffs); 
/* 415 */     if (this.fogDensities != null && this.fogDensities.length > 0) packet.fogDensities = toFloatMap(this.fogDensities); 
/* 416 */     packet.screenEffect = this.screenEffect;
/*     */     
/* 418 */     if (this.screenEffectColors != null && this.screenEffectColors.length > 0) packet.screenEffectColors = toColorAlphaMap(this.screenEffectColors); 
/* 419 */     if (this.colorFilters != null && this.colorFilters.length > 0) packet.colorFilters = toColorMap(this.colorFilters); 
/* 420 */     if (this.waterTints != null && this.waterTints.length > 0) packet.waterTints = toColorMap(this.waterTints); 
/* 421 */     if (this.fogOptions != null) packet.fogOptions = this.fogOptions.toPacket();
/*     */     
/* 423 */     packet.fog = new NearFar(this.fogDistance[0], this.fogDistance[1]);
/* 424 */     packet.stars = this.stars;
/*     */     
/* 426 */     if (this.particle != null) {
/* 427 */       packet.particle = this.particle;
/*     */     }
/*     */     
/* 430 */     if (this.data != null) {
/* 431 */       IntSet tags = this.data.getExpandedTagIndexes();
/* 432 */       packet.tagIndexes = tags.toIntArray();
/*     */     } 
/*     */     
/* 435 */     this.cachedPacket = new SoftReference<>(packet);
/* 436 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 441 */     return this.id;
/*     */   }
/*     */   
/*     */   public DayTexture[] getMoons() {
/* 445 */     return this.moons;
/*     */   }
/*     */   
/*     */   public Cloud[] getClouds() {
/* 449 */     return this.clouds;
/*     */   }
/*     */   
/*     */   public TimeFloat[] getSunlightDampingMultiplier() {
/* 453 */     return this.sunlightDampingMultiplier;
/*     */   }
/*     */   
/*     */   public TimeColor[] getSunlightColors() {
/* 457 */     return this.sunlightColors;
/*     */   }
/*     */   
/*     */   public TimeColor[] getSunColors() {
/* 461 */     return this.sunColors;
/*     */   }
/*     */   
/*     */   public TimeColorAlpha[] getMoonColors() {
/* 465 */     return this.moonColors;
/*     */   }
/*     */   
/*     */   public TimeColorAlpha[] getSunGlowColors() {
/* 469 */     return this.sunGlowColors;
/*     */   }
/*     */   
/*     */   public TimeColorAlpha[] getMoonGlowColors() {
/* 473 */     return this.moonGlowColors;
/*     */   }
/*     */   
/*     */   public TimeFloat[] getSunScales() {
/* 477 */     return this.sunScales;
/*     */   }
/*     */   
/*     */   public TimeFloat[] getMoonScales() {
/* 481 */     return this.moonScales;
/*     */   }
/*     */   
/*     */   public TimeColorAlpha[] getSkyTopColors() {
/* 485 */     return this.skyTopColors;
/*     */   }
/*     */   
/*     */   public TimeColorAlpha[] getSkyBottomColors() {
/* 489 */     return this.skyBottomColors;
/*     */   }
/*     */   
/*     */   public TimeColorAlpha[] getSkySunsetColors() {
/* 493 */     return this.skySunsetColors;
/*     */   }
/*     */   
/*     */   public TimeColor[] getFogColors() {
/* 497 */     return this.fogColors;
/*     */   }
/*     */   
/*     */   public TimeFloat[] getFogHeightFalloffs() {
/* 501 */     return this.fogHeightFalloffs;
/*     */   }
/*     */   
/*     */   public TimeFloat[] getFogDensities() {
/* 505 */     return this.fogDensities;
/*     */   }
/*     */   
/*     */   public TimeColor[] getWaterTints() {
/* 509 */     return this.waterTints;
/*     */   }
/*     */   
/*     */   public float[] getFogDistance() {
/* 513 */     return this.fogDistance;
/*     */   }
/*     */   
/*     */   public FogOptions getFogOptions() {
/* 517 */     return this.fogOptions;
/*     */   }
/*     */   
/*     */   public String getScreenEffect() {
/* 521 */     return this.screenEffect;
/*     */   }
/*     */   
/*     */   public TimeColorAlpha[] getScreenEffectColors() {
/* 525 */     return this.screenEffectColors;
/*     */   }
/*     */   
/*     */   public TimeColor[] getColorFilters() {
/* 529 */     return this.colorFilters;
/*     */   }
/*     */   
/*     */   public String getStars() {
/* 533 */     return this.stars;
/*     */   }
/*     */   
/*     */   public WeatherParticle getParticle() {
/* 537 */     return this.particle;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 543 */     return "Weather{id='" + this.id + "', moons=" + 
/*     */       
/* 545 */       Arrays.toString((Object[])this.moons) + ", clouds=" + 
/* 546 */       Arrays.toString((Object[])this.clouds) + ", sunlightDampingMultiplier=" + 
/* 547 */       Arrays.toString((Object[])this.sunlightDampingMultiplier) + ", sunlightColors=" + 
/* 548 */       Arrays.toString((Object[])this.sunlightColors) + ", sunColors=" + 
/* 549 */       Arrays.toString((Object[])this.sunColors) + ", sunGlowColors=" + 
/* 550 */       Arrays.toString((Object[])this.sunGlowColors) + ", sunScales=" + 
/* 551 */       Arrays.toString((Object[])this.sunScales) + ", moonColors=" + 
/* 552 */       Arrays.toString((Object[])this.moonColors) + ", moonGlowColors=" + 
/* 553 */       Arrays.toString((Object[])this.moonGlowColors) + ", moonScales=" + 
/* 554 */       Arrays.toString((Object[])this.moonScales) + ", skyTopColors=" + 
/* 555 */       Arrays.toString((Object[])this.skyTopColors) + ", skyBottomColors=" + 
/* 556 */       Arrays.toString((Object[])this.skyBottomColors) + ", skySunsetColors=" + 
/* 557 */       Arrays.toString((Object[])this.skySunsetColors) + ", fogColors=" + 
/* 558 */       Arrays.toString((Object[])this.fogColors) + ", fogHeightFalloffs=" + 
/* 559 */       Arrays.toString((Object[])this.fogHeightFalloffs) + ", fogDensities=" + 
/* 560 */       Arrays.toString((Object[])this.fogDensities) + ", fogDistance=" + 
/* 561 */       Arrays.toString(this.fogDistance) + ", fogOptions=" + String.valueOf(this.fogOptions) + ", screenEffect=" + this.screenEffect + ", screenEffectColors=" + 
/*     */ 
/*     */       
/* 564 */       Arrays.toString((Object[])this.screenEffectColors) + ", colorFilters=" + 
/* 565 */       Arrays.toString((Object[])this.colorFilters) + ", waterTints=" + 
/* 566 */       Arrays.toString((Object[])this.waterTints) + ", stars=" + this.stars + ", particle=" + String.valueOf(this.particle) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Map<Integer, String> toStringMap(@Nonnull DayTexture[] dayTexture) {
/* 575 */     return (Map<Integer, String>)Arrays.<DayTexture>stream(dayTexture).collect(Collectors.toMap(DayTexture::getDay, DayTexture::getTexture));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Map<Float, Float> toFloatMap(@Nonnull TimeFloat[] timeFloat) {
/* 580 */     return (Map<Float, Float>)Arrays.<TimeFloat>stream(timeFloat).collect(Collectors.toMap(TimeFloat::getHour, TimeFloat::getValue));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Map<Float, Color> toColorMap(@Nonnull TimeColor[] timeColor) {
/* 585 */     return (Map<Float, Color>)Arrays.<TimeColor>stream(timeColor).collect(Collectors.toMap(TimeColor::getHour, TimeColor::getColor));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Map<Float, ColorAlpha> toColorAlphaMap(@Nonnull TimeColorAlpha[] timeColorAlpha) {
/* 590 */     return (Map<Float, ColorAlpha>)Arrays.<TimeColorAlpha>stream(timeColorAlpha).collect(Collectors.toMap(TimeColorAlpha::getHour, TimeColorAlpha::getColor));
/*     */   }
/*     */   
/*     */   protected Weather() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\weather\config\Weather.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */