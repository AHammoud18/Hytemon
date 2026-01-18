/*     */ package com.hypixel.hytale.server.core.asset.type.ambiencefx.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.AmbienceFXBlockSoundSet;
/*     */ import com.hypixel.hytale.protocol.AmbienceFXConditions;
/*     */ import com.hypixel.hytale.protocol.Range;
/*     */ import com.hypixel.hytale.protocol.Rangeb;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluidfx.config.FluidFX;
/*     */ import com.hypixel.hytale.server.core.asset.type.tagpattern.config.TagPattern;
/*     */ import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AmbienceFXConditions
/*     */   implements NetworkSerializable<AmbienceFXConditions>
/*     */ {
/*     */   public static final BuilderCodec<AmbienceFXConditions> CODEC;
/*     */   
/*     */   static {
/* 132 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AmbienceFXConditions.class, AmbienceFXConditions::new).appendInherited(new KeyedCodec("Never", (Codec)Codec.BOOLEAN), (ambienceFXConditions, l) -> ambienceFXConditions.never = l.booleanValue(), ambienceFXConditions -> Boolean.valueOf(ambienceFXConditions.never), (ambienceFXConditions, parent) -> ambienceFXConditions.never = parent.never).documentation("If true, this Ambience will never conditionally trigger (but can be set server-side, for example).").add()).appendInherited(new KeyedCodec("EnvironmentIds", (Codec)Codec.STRING_ARRAY), (ambienceFXConditions, l) -> ambienceFXConditions.environmentIds = l, ambienceFXConditions -> ambienceFXConditions.environmentIds, (ambienceFXConditions, parent) -> ambienceFXConditions.environmentIds = parent.environmentIds).addValidator((Validator)Environment.VALIDATOR_CACHE.getArrayValidator()).add()).appendInherited(new KeyedCodec("EnvironmentTagPattern", TagPattern.CHILD_ASSET_CODEC), (ambienceFxConditions, t) -> ambienceFxConditions.environmentTagPattern = t, ambienceFXConditions -> ambienceFXConditions.environmentTagPattern, (ambienceFXConditions, parent) -> ambienceFXConditions.environmentTagPattern = parent.environmentTagPattern).addValidator(TagPattern.VALIDATOR_CACHE.getValidator()).documentation("A tag pattern to use for matching environments.").add()).appendInherited(new KeyedCodec("WeatherTagPattern", TagPattern.CHILD_ASSET_CODEC), (ambienceFxConditions, t) -> ambienceFxConditions.weatherTagPattern = t, ambienceFXConditions -> ambienceFXConditions.weatherTagPattern, (ambienceFXConditions, parent) -> ambienceFXConditions.weatherTagPattern = parent.weatherTagPattern).addValidator(TagPattern.VALIDATOR_CACHE.getValidator()).documentation("A tag pattern to use for matching weathers.").add()).appendInherited(new KeyedCodec("WeatherIds", (Codec)Codec.STRING_ARRAY), (ambienceFXConditions, l) -> ambienceFXConditions.weatherIds = l, ambienceFXConditions -> ambienceFXConditions.weatherIds, (ambienceFXConditions, parent) -> ambienceFXConditions.weatherIds = parent.weatherIds).addValidator((Validator)Weather.VALIDATOR_CACHE.getArrayValidator()).add()).appendInherited(new KeyedCodec("FluidFXIds", (Codec)Codec.STRING_ARRAY), (ambienceFXConditions, l) -> ambienceFXConditions.fluidFXIds = l, ambienceFXConditions -> ambienceFXConditions.fluidFXIds, (ambienceFXConditions, parent) -> ambienceFXConditions.fluidFXIds = parent.fluidFXIds).addValidator((Validator)FluidFX.VALIDATOR_CACHE.getArrayValidator()).add()).appendInherited(new KeyedCodec("SurroundingBlockSoundSets", (Codec)new ArrayCodec((Codec)AmbienceFXBlockSoundSet.CODEC, x$0 -> new AmbienceFXBlockSoundSet[x$0])), (ambienceFXConditions, l) -> ambienceFXConditions.surroundingBlockSoundSets = l, ambienceFXConditions -> ambienceFXConditions.surroundingBlockSoundSets, (ambienceFXConditions, parent) -> ambienceFXConditions.surroundingBlockSoundSets = parent.surroundingBlockSoundSets).add()).appendInherited(new KeyedCodec("Altitude", (Codec)ProtocolCodecs.RANGE), (ambienceFXBlockEnvironment, o) -> ambienceFXBlockEnvironment.altitude = o, ambienceFXBlockEnvironment -> ambienceFXBlockEnvironment.altitude, (ambienceFXConditions, parent) -> ambienceFXConditions.altitude = parent.altitude).add()).appendInherited(new KeyedCodec("Walls", (Codec)ProtocolCodecs.RANGEB), (ambienceFXBlockEnvironment, o) -> ambienceFXBlockEnvironment.walls = o, ambienceFXBlockEnvironment -> ambienceFXBlockEnvironment.walls, (ambienceFXConditions, parent) -> ambienceFXConditions.walls = parent.walls).add()).appendInherited(new KeyedCodec("Roof", (Codec)Codec.BOOLEAN), (ambienceFXConditions, aBoolean) -> ambienceFXConditions.roof = aBoolean.booleanValue(), ambienceFXConditions -> Boolean.valueOf(ambienceFXConditions.roof), (ambienceFXConditions, parent) -> ambienceFXConditions.roof = parent.roof).add()).appendInherited(new KeyedCodec("RoofMaterialTagPattern", TagPattern.CHILD_ASSET_CODEC), (ambienceFxConditions, t) -> ambienceFxConditions.roofMaterialTagPattern = t, ambienceFXConditions -> ambienceFXConditions.roofMaterialTagPattern, (ambienceFXConditions, parent) -> ambienceFXConditions.roofMaterialTagPattern = parent.roofMaterialTagPattern).addValidator(TagPattern.VALIDATOR_CACHE.getValidator()).documentation("A tag pattern to use for matching roof material. If Roof is not required, will only be matched if a roof is present.").add()).appendInherited(new KeyedCodec("Floor", (Codec)Codec.BOOLEAN), (ambienceFXConditions, aBoolean) -> ambienceFXConditions.floor = aBoolean.booleanValue(), ambienceFXConditions -> Boolean.valueOf(ambienceFXConditions.floor), (ambienceFXConditions, parent) -> ambienceFXConditions.floor = parent.floor).add()).appendInherited(new KeyedCodec("SunLightLevel", (Codec)ProtocolCodecs.RANGEB), (ambienceFXBlockEnvironment, o) -> ambienceFXBlockEnvironment.sunLightLevel = o, ambienceFXBlockEnvironment -> ambienceFXBlockEnvironment.sunLightLevel, (ambienceFXConditions, parent) -> ambienceFXConditions.sunLightLevel = parent.sunLightLevel).add()).appendInherited(new KeyedCodec("TorchLightLevel", (Codec)ProtocolCodecs.RANGEB), (ambienceFXBlockEnvironment, o) -> ambienceFXBlockEnvironment.torchLightLevel = o, ambienceFXBlockEnvironment -> ambienceFXBlockEnvironment.torchLightLevel, (ambienceFXConditions, parent) -> ambienceFXConditions.torchLightLevel = parent.torchLightLevel).add()).appendInherited(new KeyedCodec("GlobalLightLevel", (Codec)ProtocolCodecs.RANGEB), (ambienceFXBlockEnvironment, o) -> ambienceFXBlockEnvironment.globalLightLevel = o, ambienceFXBlockEnvironment -> ambienceFXBlockEnvironment.globalLightLevel, (ambienceFXConditions, parent) -> ambienceFXConditions.globalLightLevel = parent.globalLightLevel).add()).appendInherited(new KeyedCodec("DayTime", (Codec)ProtocolCodecs.RANGEF), (ambienceFXBlockEnvironment, o) -> ambienceFXBlockEnvironment.dayTime = o, ambienceFXBlockEnvironment -> ambienceFXBlockEnvironment.dayTime, (ambienceFXConditions, parent) -> ambienceFXConditions.dayTime = parent.dayTime).add()).afterDecode(AmbienceFXConditions::processConfig)).build();
/*     */   }
/* 134 */   public static final Range DEFAULT_ALTITUDE = new Range(0, 512);
/* 135 */   public static final Rangeb DEFAULT_WALLS = new Rangeb((byte)0, (byte)4);
/* 136 */   public static final Rangeb DEFAULT_LIGHT_LEVEL = new Rangeb((byte)0, (byte)15);
/* 137 */   public static final Rangef DEFAULT_DAY_TIME = new Rangef(0.0F, 24.0F);
/*     */   
/*     */   protected boolean never;
/*     */   protected String[] environmentIds;
/*     */   protected transient int[] environmentIndices;
/*     */   protected String[] weatherIds;
/*     */   protected transient int[] weatherIndices;
/*     */   protected String environmentTagPattern;
/*     */   protected String weatherTagPattern;
/*     */   protected String[] fluidFXIds;
/*     */   protected transient int[] fluidFXIndices;
/*     */   protected AmbienceFXBlockSoundSet[] surroundingBlockSoundSets;
/* 149 */   protected Range altitude = DEFAULT_ALTITUDE;
/* 150 */   protected Rangeb walls = DEFAULT_WALLS;
/*     */   protected boolean roof;
/*     */   protected String roofMaterialTagPattern;
/*     */   protected boolean floor;
/* 154 */   protected Rangeb sunLightLevel = DEFAULT_LIGHT_LEVEL;
/* 155 */   protected Rangeb torchLightLevel = DEFAULT_LIGHT_LEVEL;
/* 156 */   protected Rangeb globalLightLevel = DEFAULT_LIGHT_LEVEL;
/* 157 */   protected Rangef dayTime = DEFAULT_DAY_TIME;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AmbienceFXConditions toPacket() {
/* 165 */     AmbienceFXConditions packet = new AmbienceFXConditions();
/* 166 */     packet.never = this.never;
/* 167 */     if (this.environmentIndices != null && this.environmentIndices.length > 0) {
/* 168 */       packet.environmentIndices = this.environmentIndices;
/*     */     }
/*     */     
/* 171 */     if (this.environmentTagPattern != null) {
/* 172 */       packet.environmentTagPatternIndex = TagPattern.getAssetMap().getIndex(this.environmentTagPattern);
/*     */     } else {
/*     */       
/* 175 */       packet.environmentTagPatternIndex = -1;
/*     */     } 
/*     */     
/* 178 */     if (this.weatherIndices != null && this.weatherIndices.length > 0) {
/* 179 */       packet.weatherIndices = this.weatherIndices;
/*     */     }
/*     */     
/* 182 */     if (this.weatherTagPattern != null) {
/* 183 */       packet.weatherTagPatternIndex = TagPattern.getAssetMap().getIndex(this.weatherTagPattern);
/*     */     } else {
/*     */       
/* 186 */       packet.weatherTagPatternIndex = -1;
/*     */     } 
/*     */ 
/*     */     
/* 190 */     if (this.fluidFXIndices != null) {
/* 191 */       packet.fluidFXIndices = this.fluidFXIndices;
/*     */     }
/* 193 */     if (this.surroundingBlockSoundSets != null && this.surroundingBlockSoundSets.length > 0) {
/* 194 */       packet.surroundingBlockSoundSets = (AmbienceFXBlockSoundSet[])ArrayUtil.copyAndMutate((Object[])this.surroundingBlockSoundSets, AmbienceFXBlockSoundSet::toPacket, x$0 -> new AmbienceFXBlockSoundSet[x$0]);
/*     */     }
/* 196 */     packet.altitude = this.altitude;
/* 197 */     packet.walls = this.walls;
/* 198 */     packet.roof = this.roof;
/*     */     
/* 200 */     if (this.roofMaterialTagPattern != null) {
/* 201 */       packet.roofMaterialTagPatternIndex = TagPattern.getAssetMap().getIndex(this.roofMaterialTagPattern);
/*     */     } else {
/*     */       
/* 204 */       packet.roofMaterialTagPatternIndex = -1;
/*     */     } 
/*     */     
/* 207 */     packet.floor = this.floor;
/* 208 */     packet.sunLightLevel = this.sunLightLevel;
/* 209 */     packet.torchLightLevel = this.torchLightLevel;
/* 210 */     packet.globalLightLevel = this.globalLightLevel;
/* 211 */     packet.dayTime = this.dayTime;
/* 212 */     return packet;
/*     */   }
/*     */   
/*     */   public boolean isNever() {
/* 216 */     return this.never;
/*     */   }
/*     */   
/*     */   public String[] getEnvironmentIds() {
/* 220 */     return this.environmentIds;
/*     */   }
/*     */   
/*     */   public int[] getEnvironmentIndices() {
/* 224 */     return this.environmentIndices;
/*     */   }
/*     */   
/*     */   public String[] getWeatherIds() {
/* 228 */     return this.weatherIds;
/*     */   }
/*     */   
/*     */   public int[] getWeatherIndices() {
/* 232 */     return this.weatherIndices;
/*     */   }
/*     */   
/*     */   public String[] getFluidFXIds() {
/* 236 */     return this.fluidFXIds;
/*     */   }
/*     */   
/*     */   public int[] getFluidFXIndices() {
/* 240 */     return this.fluidFXIndices;
/*     */   }
/*     */   
/*     */   public AmbienceFXBlockSoundSet[] getSurroundingBlockSoundSets() {
/* 244 */     return this.surroundingBlockSoundSets;
/*     */   }
/*     */   
/*     */   public Range getAltitude() {
/* 248 */     return this.altitude;
/*     */   }
/*     */   
/*     */   public Rangeb getWalls() {
/* 252 */     return this.walls;
/*     */   }
/*     */   
/*     */   public boolean getRoof() {
/* 256 */     return this.roof;
/*     */   }
/*     */   
/*     */   public boolean getFloor() {
/* 260 */     return this.floor;
/*     */   }
/*     */   
/*     */   public Rangeb getSunLightLevel() {
/* 264 */     return this.sunLightLevel;
/*     */   }
/*     */   
/*     */   public Rangeb getTorchLightLevel() {
/* 268 */     return this.torchLightLevel;
/*     */   }
/*     */   
/*     */   public Rangeb getGlobalLightLevel() {
/* 272 */     return this.globalLightLevel;
/*     */   }
/*     */   
/*     */   public Rangef getDayTime() {
/* 276 */     return this.dayTime;
/*     */   }
/*     */   
/*     */   public boolean isRoof() {
/* 280 */     return this.roof;
/*     */   }
/*     */   
/*     */   public boolean isFloor() {
/* 284 */     return this.floor;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processConfig() {
/* 289 */     if (this.environmentIds != null) {
/* 290 */       this.environmentIndices = new int[this.environmentIds.length];
/*     */       
/* 292 */       for (int i = 0; i < this.environmentIds.length; i++) {
/* 293 */         this.environmentIndices[i] = Environment.getAssetMap().getIndex(this.environmentIds[i]);
/*     */       }
/*     */     } 
/*     */     
/* 297 */     if (this.weatherIds != null) {
/* 298 */       this.weatherIndices = new int[this.weatherIds.length];
/*     */       
/* 300 */       for (int i = 0; i < this.weatherIds.length; i++) {
/* 301 */         this.weatherIndices[i] = Weather.getAssetMap().getIndex(this.weatherIds[i]);
/*     */       }
/*     */     } 
/*     */     
/* 305 */     if (this.fluidFXIds != null) {
/* 306 */       this.fluidFXIndices = new int[this.fluidFXIds.length];
/*     */       
/* 308 */       for (int i = 0; i < this.fluidFXIds.length; i++) {
/* 309 */         this.fluidFXIndices[i] = FluidFX.getAssetMap().getIndex(this.fluidFXIds[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 317 */     return "AmbienceFXConditions{,never=" + this.never + ",environmentIds=" + 
/*     */       
/* 319 */       Arrays.toString((Object[])this.environmentIds) + ", environmentIndices=" + 
/* 320 */       Arrays.toString(this.environmentIndices) + ", environmentTagPattern=" + this.environmentTagPattern + ", weatherIds=" + 
/*     */       
/* 322 */       Arrays.toString((Object[])this.weatherIds) + ", weatherIndices=" + 
/* 323 */       Arrays.toString(this.weatherIndices) + ", fluidFXIds=" + 
/* 324 */       Arrays.toString((Object[])this.fluidFXIds) + ", fluidFXIndices=" + 
/* 325 */       Arrays.toString(this.fluidFXIndices) + ", surroundingBlockSoundSets=" + 
/* 326 */       Arrays.toString((Object[])this.surroundingBlockSoundSets) + ", altitude=" + String.valueOf(this.altitude) + ", walls=" + String.valueOf(this.walls) + ", roof=" + this.roof + ", roofMaterialTagPattern=" + this.roofMaterialTagPattern + ", floor=" + this.floor + ", sunLightLevel=" + String.valueOf(this.sunLightLevel) + ", torchLightLevel=" + String.valueOf(this.torchLightLevel) + ", globalLightLevel=" + String.valueOf(this.globalLightLevel) + ", dayTime=" + String.valueOf(this.dayTime) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\ambiencefx\config\AmbienceFXConditions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */