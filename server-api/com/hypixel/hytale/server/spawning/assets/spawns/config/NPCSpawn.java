/*     */ package com.hypixel.hytale.server.spawning.assets.spawns.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIPropertyTitle;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.LightType;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class NPCSpawn
/*     */ {
/*     */   public static final float HOURS_PER_DAY = 24.0F;
/*     */   public static final BuilderCodec<NPCSpawn> BASE_CODEC;
/*     */   
/*     */   static {
/* 148 */     BASE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)AssetBuilderCodec.abstractBuilder(NPCSpawn.class).documentation("A specification for spawning NPCs, including spawn and despawn parameters.")).appendInherited(new KeyedCodec("NPCs", (Codec)new ArrayCodec((Codec)RoleSpawnParameters.CODEC, x$0 -> new RoleSpawnParameters[x$0])), (spawn, o) -> spawn.npcs = o, spawn -> spawn.npcs, (spawn, parent) -> spawn.npcs = parent.npcs).documentation("A required list of **Role Spawn Parameters** defining each NPC that can be spawned and their relative weights.").metadata((Metadata)new UIPropertyTitle("NPCs")).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).add()).appendInherited(new KeyedCodec("Despawn", (Codec)DespawnParameters.CODEC), (spawn, o) -> spawn.despawnParameters = o, spawn -> spawn.despawnParameters, (spawn, parent) -> spawn.despawnParameters = parent.despawnParameters).documentation("Optional **Despawn Parameters** to control NPC despawning.").add()).appendInherited(new KeyedCodec("DayTimeRange", (Codec)Codec.DOUBLE_ARRAY), (spawn, o) -> { spawn.dayTimeRange = o; spawn.dayTimeRange[0] = spawn.dayTimeRange[0] / 24.0D; spawn.dayTimeRange[1] = spawn.dayTimeRange[1] / 24.0D; }spawn -> new double[] { spawn.dayTimeRange[0] * 24.0D, spawn.dayTimeRange[1] * 24.0D }, (spawn, parent) -> spawn.dayTimeRange = parent.dayTimeRange).documentation("An optional hour range within which the NPCs/beacon will spawn (between 0 and 24).").addValidator(Validators.doubleArraySize(2)).add()).appendInherited(new KeyedCodec("MoonPhaseRange", (Codec)Codec.INT_ARRAY), (spawn, o) -> spawn.moonPhaseRange = o, spawn -> new int[] { spawn.moonPhaseRange[0], spawn.moonPhaseRange[1] }, (spawn, parent) -> spawn.moonPhaseRange = parent.moonPhaseRange).documentation("An optional moon phase range during which the NPCs/beacon will spawn (must be greater than or equal to 0).").addValidator(Validators.intArraySize(2)).add()).appendInherited(new KeyedCodec("LightRanges", (Codec)(new EnumMapCodec(LightType.class, (Codec)Codec.DOUBLE_ARRAY)).documentKey((Enum)LightType.Light, "Total light level.").documentKey((Enum)LightType.SkyLight, "Light level based on how deep under cover the position is relative to the open sky (e.g. inside a cave will be low SkyLight).").documentKey((Enum)LightType.Sunlight, "Light level based on time of day (peaks at around noon and is 0 during most of the night).").documentKey((Enum)LightType.RedLight, "Red light level.").documentKey((Enum)LightType.GreenLight, "Green light level.").documentKey((Enum)LightType.BlueLight, "Blue light level.")), (spawn, o) -> spawn.lightTypeMap = o, spawn -> spawn.lightTypeMap, (spawn, parent) -> spawn.lightTypeMap = parent.lightTypeMap).documentation("Optional light ranges to spawn the NPCs/beacon in, defined between 0 and 100.").add()).appendInherited(new KeyedCodec("ScaleDayTimeRange", (Codec)Codec.BOOLEAN), (spawn, b) -> spawn.scaleDayTimeRange = b.booleanValue(), spawn -> Boolean.valueOf(spawn.scaleDayTimeRange), (spawn, parent) -> spawn.scaleDayTimeRange = parent.scaleDayTimeRange).documentation("If set to true, instead of using absolute hour values for DayTimeRange, it will be scaled based on the world's DaytimePortion.\n\n * 0 and 24 will represent the middle of the night portion.\n * 6 will represent the moment of sunrise.\n * 12 will represent the middle of the day portion.\n * 18 will represent the moment of sunset.").add()).afterDecode(spawn -> { if (spawn.environments == null || spawn.environments.length == 0) { spawn.environmentIds = (IntSet)IntSets.EMPTY_SET; } else { IntOpenHashSet environmentSet = new IntOpenHashSet(); IndexedLookupTableAssetMap<String, Environment> environments = Environment.getAssetMap(); for (String environment : spawn.environments) { int index = environments.getIndex(environment); if (index != Integer.MIN_VALUE) environmentSet.add(index);  }  environmentSet.trim(); spawn.environmentIds = IntSets.unmodifiable((IntSet)environmentSet); }  })).validator((asset, results) -> { double[] dayTimeRange = asset.getDayTimeRange(); if (dayTimeRange[0] < 0.0D || dayTimeRange[1] < 0.0D) results.fail("DayTimeRange values must be >=0");  int[] moonPhaseRange = asset.getMoonPhaseRange(); if (moonPhaseRange[0] < 0 || moonPhaseRange[1] < 0) results.fail("MoonPhaseRange values must be >=0");  for (LightType lightType : LightType.VALUES) validateLightRange(results, lightType.name(), asset.getLightRange(lightType));  DespawnParameters despawnParameters = asset.getDespawnParameters(); if (despawnParameters != null) { dayTimeRange = despawnParameters.getDayTimeRange(); if (dayTimeRange[0] < 0.0D || dayTimeRange[1] < 0.0D) results.fail("Despawn DayTimeRange values must be >=0");  moonPhaseRange = despawnParameters.getMoonPhaseRange(); if (moonPhaseRange[0] < 0 || moonPhaseRange[1] < 0) results.fail("Despawn MoonPhaseRange values must be >=0");  }  })).build();
/*     */   }
/*     */   private static void validateLightRange(@Nonnull ValidationResults results, String parameter, @Nonnull double[] lightRange) {
/* 151 */     for (int i = 0; i < lightRange.length; i++) {
/* 152 */       double value = lightRange[i];
/* 153 */       if (value < 0.0D || value > 100.0D) {
/* 154 */         results.fail(String.format("%s must be between 0 and 100 (inclusive)", new Object[] { parameter }));
/*     */       }
/* 156 */       if (i > 0 && value < lightRange[i - 1]) {
/* 157 */         results.fail(String.format("Values in %s must be weakly monotonic", new Object[] { parameter }));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/* 162 */   public static final double[] DEFAULT_DAY_TIME_RANGE = new double[] { 0.0D, Double.MAX_VALUE };
/* 163 */   public static final int[] DEFAULT_MOON_PHASE_RANGE = new int[] { 0, Integer.MAX_VALUE };
/* 164 */   public static final double[] FULL_LIGHT_RANGE = new double[] { 0.0D, 100.0D };
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   protected String id;
/*     */   
/*     */   protected RoleSpawnParameters[] npcs;
/*     */   
/*     */   protected DespawnParameters despawnParameters;
/*     */   protected String[] environments;
/* 174 */   protected IntSet environmentIds = (IntSet)IntSets.EMPTY_SET;
/*     */   
/* 176 */   protected double[] dayTimeRange = DEFAULT_DAY_TIME_RANGE;
/* 177 */   protected int[] moonPhaseRange = DEFAULT_MOON_PHASE_RANGE;
/*     */   
/*     */   protected Map<LightType, double[]> lightTypeMap;
/*     */   
/*     */   protected boolean scaleDayTimeRange = true;
/*     */ 
/*     */   
/*     */   public NPCSpawn(String id, RoleSpawnParameters[] npcs, DespawnParameters despawnParameters, String[] environments, IntSet environmentIds, double[] dayTimeRange, int[] moonPhaseRange, Map<LightType, double[]> lightTypeMap, boolean scaleDayTimeRange) {
/* 185 */     this.id = id;
/* 186 */     this.npcs = npcs;
/* 187 */     this.despawnParameters = despawnParameters;
/* 188 */     this.environments = environments;
/* 189 */     this.environmentIds = environmentIds;
/* 190 */     this.dayTimeRange = dayTimeRange;
/* 191 */     this.moonPhaseRange = moonPhaseRange;
/* 192 */     this.lightTypeMap = lightTypeMap;
/* 193 */     this.scaleDayTimeRange = scaleDayTimeRange;
/*     */   }
/*     */   
/*     */   protected NPCSpawn(String id) {
/* 197 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleSpawnParameters[] getNPCs() {
/* 206 */     return this.npcs;
/*     */   }
/*     */   
/*     */   public DespawnParameters getDespawnParameters() {
/* 210 */     return this.despawnParameters;
/*     */   }
/*     */   
/*     */   public String[] getEnvironments() {
/* 214 */     return this.environments;
/*     */   }
/*     */   
/*     */   public IntSet getEnvironmentIds() {
/* 218 */     return this.environmentIds;
/*     */   }
/*     */   
/*     */   public double[] getDayTimeRange() {
/* 222 */     return this.dayTimeRange;
/*     */   }
/*     */   
/*     */   public int[] getMoonPhaseRange() {
/* 226 */     return this.moonPhaseRange;
/*     */   }
/*     */   
/*     */   public double[] getLightRange(LightType lightType) {
/* 230 */     if (this.lightTypeMap != null && !this.lightTypeMap.isEmpty()) {
/* 231 */       double[] array = this.lightTypeMap.get(lightType);
/* 232 */       if (array != null) return array; 
/*     */     } 
/* 234 */     return FULL_LIGHT_RANGE;
/*     */   }
/*     */   
/*     */   public boolean isScaleDayTimeRange() {
/* 238 */     return this.scaleDayTimeRange;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 244 */     return "NPCSpawn{id='" + this.id + "', npcs=" + 
/*     */       
/* 246 */       Arrays.deepToString((Object[])this.npcs) + ", despawnParameters=" + (
/* 247 */       (this.despawnParameters != null) ? this.despawnParameters.toString() : "Null") + ", environments=" + 
/* 248 */       Arrays.toString((Object[])this.environments) + ", dayTimeRange=" + 
/* 249 */       Arrays.toString(this.dayTimeRange) + ", moonPhaseRange=" + 
/* 250 */       Arrays.toString(this.moonPhaseRange) + ", lightTypeMap=" + (
/* 251 */       (this.lightTypeMap != null) ? this.lightTypeMap.entrySet().stream().map(entry -> String.valueOf(entry.getKey()) + "=" + String.valueOf(entry.getKey())).collect(Collectors.joining(", ", "{", "}")) : "Null") + ", scaleDayTimeRange=" + this.scaleDayTimeRange + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NPCSpawn() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getId();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DespawnParameters
/*     */   {
/*     */     public static final BuilderCodec<DespawnParameters> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 279 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DespawnParameters.class, DespawnParameters::new).documentation("A set of parameters that determine if NPCs should despawn.")).append(new KeyedCodec("DayTimeRange", (Codec)Codec.DOUBLE_ARRAY), (parameters, o) -> { parameters.dayTimeRange = o; parameters.dayTimeRange[0] = parameters.dayTimeRange[0] / 24.0D; parameters.dayTimeRange[1] = parameters.dayTimeRange[1] / 24.0D; }parameters -> new double[] { parameters.dayTimeRange[0] * 24.0D, parameters.dayTimeRange[1] * 24.0D }).documentation("An optional hour range within which the NPCs will despawn (between 0 and 24). For Spawn Beacons, this refers to the beacon itself.").addValidator(Validators.doubleArraySize(2)).add()).append(new KeyedCodec("MoonPhaseRange", (Codec)Codec.INT_ARRAY), (parameters, o) -> parameters.moonPhaseRange = o, parameters -> new int[] { parameters.moonPhaseRange[0], parameters.moonPhaseRange[1] }).documentation("An optional moon phase range during which the NPCs will despawn (must be greater than or equal to 0). For Spawn Beacons, this refers to the beacon itself.").addValidator(Validators.intArraySize(2)).add()).build();
/*     */     }
/*     */     
/* 282 */     protected double[] dayTimeRange = NPCSpawn.DEFAULT_DAY_TIME_RANGE;
/* 283 */     protected int[] moonPhaseRange = NPCSpawn.DEFAULT_MOON_PHASE_RANGE;
/*     */     
/*     */     public DespawnParameters(double[] dayTimeRange, int[] moonPhaseRange) {
/* 286 */       this.dayTimeRange = dayTimeRange;
/* 287 */       this.moonPhaseRange = moonPhaseRange;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] getDayTimeRange() {
/* 294 */       return this.dayTimeRange;
/*     */     }
/*     */     
/*     */     public int[] getMoonPhaseRange() {
/* 298 */       return this.moonPhaseRange;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 305 */       return "DespawnParameters{dayTimeRange=" + Arrays.toString(this.dayTimeRange) + ", moonPhaseRange=" + 
/* 306 */         Arrays.toString(this.moonPhaseRange) + "}";
/*     */     }
/*     */     
/*     */     protected DespawnParameters() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\assets\spawns\config\NPCSpawn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */