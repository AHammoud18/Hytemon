/*     */ package com.hypixel.hytale.server.core.asset.type.fluidfx.config;
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
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.FluidFX;
/*     */ import com.hypixel.hytale.protocol.FluidFXMovementSettings;
/*     */ import com.hypixel.hytale.protocol.FluidFog;
/*     */ import com.hypixel.hytale.protocol.NearFar;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FluidFX
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, FluidFX>>, NetworkSerializable<FluidFX>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, FluidFX> CODEC;
/*     */   
/*     */   static {
/* 146 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(FluidFX.class, FluidFX::new, (Codec)Codec.STRING, (fluidFX, s) -> fluidFX.id = s, fluidFX -> fluidFX.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Fog", (Codec)new EnumCodec(FluidFog.class)), (fluidFX, fluidFog) -> fluidFX.fog = fluidFog, fluidFX -> fluidFX.fog, (fluidFX, parent) -> fluidFX.fog = parent.fog).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("FogColor", (Codec)ProtocolCodecs.COLOR), (fluidFX, o) -> fluidFX.fogColor = o, fluidFX -> fluidFX.fogColor, (fluidFX, parent) -> fluidFX.fogColor = parent.fogColor).add()).appendInherited(new KeyedCodec("FogDistance", (Codec)Codec.DOUBLE_ARRAY), (weather, o) -> { weather.fogDistance = new float[2]; weather.fogDistance[0] = (float)o[0]; weather.fogDistance[1] = (float)o[1]; }weather -> new double[] { weather.fogDistance[0], weather.fogDistance[1] }, (weather, parent) -> weather.fogDistance = parent.fogDistance).addValidator(Validators.doubleArraySize(2)).add()).appendInherited(new KeyedCodec("FogDepthStart", (Codec)Codec.DOUBLE), (fluidFX, s) -> fluidFX.fogDepthStart = s.floatValue(), fluidFX -> Double.valueOf(fluidFX.fogDepthStart), (fluidFX, parent) -> fluidFX.fogDepthStart = parent.fogDepthStart).add()).appendInherited(new KeyedCodec("FogDepthFalloff", (Codec)Codec.DOUBLE), (fluidFX, s) -> fluidFX.fogDepthFalloff = s.floatValue(), fluidFX -> Double.valueOf(fluidFX.fogDepthFalloff), (fluidFX, parent) -> fluidFX.fogDepthFalloff = parent.fogDepthFalloff).add()).appendInherited(new KeyedCodec("ColorsSaturation", (Codec)Codec.DOUBLE), (fluidFX, s) -> fluidFX.colorsSaturation = s.floatValue(), fluidFX -> Double.valueOf(fluidFX.colorsSaturation), (fluidFX, parent) -> fluidFX.colorsSaturation = parent.colorsSaturation).add()).appendInherited(new KeyedCodec("ColorsFilter", (Codec)Codec.DOUBLE_ARRAY), (weather, o) -> { weather.colorsFilter = new float[3]; weather.colorsFilter[0] = (float)o[0]; weather.colorsFilter[1] = (float)o[1]; weather.colorsFilter[2] = (float)o[2]; }weather -> new double[] { weather.colorsFilter[0], weather.colorsFilter[1], weather.colorsFilter[2] }, (weather, parent) -> weather.colorsFilter = parent.colorsFilter).addValidator(Validators.doubleArraySize(3)).add()).appendInherited(new KeyedCodec("DistortionAmplitude", (Codec)Codec.DOUBLE), (fluidFX, s) -> fluidFX.distortionAmplitude = s.floatValue(), fluidFX -> Double.valueOf(fluidFX.distortionAmplitude), (fluidFX, parent) -> fluidFX.distortionAmplitude = parent.distortionAmplitude).add()).appendInherited(new KeyedCodec("DistortionFrequency", (Codec)Codec.DOUBLE), (fluidFX, s) -> fluidFX.distortionFrequency = s.floatValue(), fluidFX -> Double.valueOf(fluidFX.distortionFrequency), (fluidFX, parent) -> fluidFX.distortionFrequency = parent.distortionFrequency).add()).appendInherited(new KeyedCodec("Particle", (Codec)FluidParticle.CODEC), (fluidFX, s) -> fluidFX.particle = s, fluidFX -> fluidFX.particle, (fluidFX, parent) -> fluidFX.particle = parent.particle).add()).appendInherited(new KeyedCodec("MovementSettings", (Codec)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FluidFXMovementSettings.class, FluidFXMovementSettings::new).append(new KeyedCodec("SwimUpSpeed", (Codec)Codec.DOUBLE), (movementSettings, val) -> movementSettings.swimUpSpeed = val.floatValue(), movementSettings -> Double.valueOf(movementSettings.swimUpSpeed)).add()).append(new KeyedCodec("SwimDownSpeed", (Codec)Codec.DOUBLE), (movementSettings, val) -> movementSettings.swimDownSpeed = val.floatValue(), movementSettings -> Double.valueOf(movementSettings.swimDownSpeed)).add()).append(new KeyedCodec("SinkSpeed", (Codec)Codec.DOUBLE), (movementSettings, val) -> movementSettings.sinkSpeed = val.floatValue(), movementSettings -> Double.valueOf(movementSettings.sinkSpeed)).add()).append(new KeyedCodec("HorizontalSpeedMultiplier", (Codec)Codec.DOUBLE), (movementSettings, val) -> movementSettings.horizontalSpeedMultiplier = val.floatValue(), movementSettings -> Double.valueOf(movementSettings.horizontalSpeedMultiplier)).add()).append(new KeyedCodec("FieldOfViewMultiplier", (Codec)Codec.DOUBLE), (movementSettings, val) -> movementSettings.fieldOfViewMultiplier = val.floatValue(), movementSettings -> Double.valueOf(movementSettings.fieldOfViewMultiplier)).add()).append(new KeyedCodec("EntryVelocityMultiplier", (Codec)Codec.DOUBLE), (movementSettings, val) -> movementSettings.entryVelocityMultiplier = val.floatValue(), movementSettings -> Double.valueOf(movementSettings.entryVelocityMultiplier)).add()).build()), (fluidFX, movementSettings) -> fluidFX.movementSettings = movementSettings, fluidFX -> fluidFX.movementSettings, (fluidFX, parent) -> fluidFX.movementSettings = parent.movementSettings).add()).build();
/*     */   }
/* 148 */   public static final Color DEFAULT_FOG_COLOR = new Color((byte)-1, (byte)-1, (byte)-1);
/* 149 */   public static final float[] DEFAULT_FOG_DISTANCE = new float[] { 0.0F, 32.0F };
/* 150 */   public static final float[] DEFAULT_COLORS_FILTER = new float[] { 1.0F, 1.0F, 1.0F };
/*     */   
/*     */   public static final int EMPTY_ID = 0;
/*     */   
/*     */   public static final String EMPTY = "Empty";
/* 155 */   public static final FluidFX EMPTY_FLUID_FX = getUnknownFor("Empty");
/*     */   
/* 157 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(FluidFX::getAssetStore)); private static AssetStore<String, FluidFX, IndexedLookupTableAssetMap<String, FluidFX>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, FluidFX, IndexedLookupTableAssetMap<String, FluidFX>> getAssetStore() {
/* 162 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(FluidFX.class); 
/* 163 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, FluidFX> getAssetMap() {
/* 167 */     return (IndexedLookupTableAssetMap<String, FluidFX>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 173 */   protected FluidFog fog = FluidFog.Color;
/*     */   
/* 175 */   protected Color fogColor = DEFAULT_FOG_COLOR;
/* 176 */   protected float[] fogDistance = DEFAULT_FOG_DISTANCE;
/* 177 */   protected float fogDepthStart = 40.0F;
/* 178 */   protected float fogDepthFalloff = 10.0F;
/* 179 */   protected float colorsSaturation = 1.0F;
/* 180 */   protected float[] colorsFilter = DEFAULT_COLORS_FILTER;
/* 181 */   protected float distortionAmplitude = 8.0F;
/* 182 */   protected float distortionFrequency = 4.0F;
/*     */   
/*     */   protected FluidParticle particle;
/*     */   protected FluidFXMovementSettings movementSettings;
/*     */   private SoftReference<FluidFX> cachedPacket;
/*     */   
/*     */   public FluidFX(String id, FluidFog fog, Color fogColor, float[] fogDistance, float fogDepthStart, float fogDepthFalloff, float colorsSaturation, float[] colorsFilter, float distortionAmplitude, float distortionFrequency, FluidParticle particle, FluidFXMovementSettings movementSettings) {
/* 189 */     this.id = id;
/* 190 */     this.fog = fog;
/* 191 */     this.fogColor = fogColor;
/* 192 */     this.fogDistance = fogDistance;
/* 193 */     this.fogDepthStart = fogDepthStart;
/* 194 */     this.fogDepthFalloff = fogDepthFalloff;
/* 195 */     this.colorsSaturation = colorsSaturation;
/* 196 */     this.colorsFilter = colorsFilter;
/* 197 */     this.distortionAmplitude = distortionAmplitude;
/* 198 */     this.distortionFrequency = distortionFrequency;
/* 199 */     this.particle = particle;
/* 200 */     this.movementSettings = movementSettings;
/*     */   }
/*     */   
/*     */   public FluidFX(String id) {
/* 204 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public FluidFX toPacket() {
/* 213 */     FluidFX cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 214 */     if (cached != null) return cached;
/*     */     
/* 216 */     FluidFX packet = new FluidFX();
/* 217 */     packet.id = this.id;
/* 218 */     packet.fogMode = this.fog;
/* 219 */     packet.fogColor = this.fogColor;
/* 220 */     packet.fogDistance = new NearFar(this.fogDistance[0], this.fogDistance[1]);
/* 221 */     packet.fogDepthStart = this.fogDepthStart;
/* 222 */     packet.fogDepthFalloff = this.fogDepthFalloff;
/* 223 */     packet.colorFilter = new Color((byte)(int)(this.colorsFilter[0] * 255.0F), (byte)(int)(this.colorsFilter[1] * 255.0F), (byte)(int)(this.colorsFilter[2] * 255.0F));
/* 224 */     packet.colorSaturation = this.colorsSaturation;
/* 225 */     packet.distortionAmplitude = this.distortionAmplitude;
/* 226 */     packet.distortionFrequency = this.distortionFrequency;
/* 227 */     if (this.particle != null) packet.particle = this.particle.toPacket(); 
/* 228 */     if (this.movementSettings != null) packet.movementSettings = this.movementSettings;
/*     */     
/* 230 */     this.cachedPacket = new SoftReference<>(packet);
/* 231 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 236 */     return this.id;
/*     */   }
/*     */   
/*     */   public FluidFog getFog() {
/* 240 */     return this.fog;
/*     */   }
/*     */   
/*     */   public Color getFogColor() {
/* 244 */     return this.fogColor;
/*     */   }
/*     */   
/*     */   public float[] getFogDistance() {
/* 248 */     return this.fogDistance;
/*     */   }
/*     */   
/*     */   public float getColorsSaturation() {
/* 252 */     return this.colorsSaturation;
/*     */   }
/*     */   
/*     */   public float[] getColorsFilter() {
/* 256 */     return this.colorsFilter;
/*     */   }
/*     */   
/*     */   public float getDistortionAmplitude() {
/* 260 */     return this.distortionAmplitude;
/*     */   }
/*     */   
/*     */   public float getDistortionFrequency() {
/* 264 */     return this.distortionFrequency;
/*     */   }
/*     */   
/*     */   public float getFogDepthStart() {
/* 268 */     return this.fogDepthStart;
/*     */   }
/*     */   
/*     */   public float getFogDepthFalloff() {
/* 272 */     return this.fogDepthFalloff;
/*     */   }
/*     */   
/*     */   public FluidParticle getParticle() {
/* 276 */     return this.particle;
/*     */   }
/*     */   
/*     */   public FluidFXMovementSettings getMovementSettings() {
/* 280 */     return this.movementSettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 285 */     if (this == o) return true; 
/* 286 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 288 */     FluidFX fluidFX = (FluidFX)o;
/*     */     
/* 290 */     return (this.id != null) ? this.id.equals(fluidFX.id) : ((fluidFX.id == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 295 */     return (this.id != null) ? this.id.hashCode() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 301 */     return "FluidFX{id='" + this.id + "', fog=" + String.valueOf(this.fog) + ", fogColor='" + String.valueOf(this.fogColor) + "', fogDistance=" + 
/*     */ 
/*     */ 
/*     */       
/* 305 */       Arrays.toString(this.fogDistance) + ", fogDepthStart=" + this.fogDepthStart + ", fogDepthFalloff=" + this.fogDepthFalloff + ", colorsSaturation=" + this.colorsSaturation + ", colorsFilter=" + 
/*     */ 
/*     */ 
/*     */       
/* 309 */       Arrays.toString(this.colorsFilter) + ", distortionAmplitude=" + this.distortionAmplitude + ", distortionFrequency=" + this.distortionFrequency + ", particle=" + String.valueOf(this.particle) + ", movementSettings=" + String.valueOf(this.movementSettings) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static FluidFX getUnknownFor(String unknownId) {
/* 319 */     return new FluidFX(unknownId);
/*     */   }
/*     */   
/*     */   protected FluidFX() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\fluidfx\config\FluidFX.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */