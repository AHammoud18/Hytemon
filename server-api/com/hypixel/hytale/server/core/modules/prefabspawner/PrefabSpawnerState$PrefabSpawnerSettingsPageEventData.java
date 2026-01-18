/*     */ package com.hypixel.hytale.server.core.modules.prefabspawner;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabSpawnerSettingsPageEventData
/*     */ {
/*     */   public static final String KEY_PREFAB_PATH = "@PrefabPath";
/*     */   public static final String KEY_FIT_HEIGHTMAP = "@FitHeightmap";
/*     */   public static final String KEY_INHERIT_SEED = "@InheritSeed";
/*     */   public static final String KEY_INHERIT_HEIGHT_CONDITION = "@InheritHeightCondition";
/*     */   public static final String KEY_DEFAULT_WEIGHT = "@DefaultWeight";
/*     */   public static final String KEY_PREFAB_WEIGHTS = "@PrefabWeights";
/*     */   public static final BuilderCodec<PrefabSpawnerSettingsPageEventData> CODEC;
/*     */   private String prefabPath;
/*     */   private boolean fitHeightmap;
/*     */   private boolean inheritSeed;
/*     */   private boolean inheritHeightCondition;
/*     */   private double defaultWeight;
/*     */   private String prefabWeights;
/*     */   
/*     */   static {
/* 201 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PrefabSpawnerSettingsPageEventData.class, PrefabSpawnerSettingsPageEventData::new).append(new KeyedCodec("@PrefabPath", (Codec)Codec.STRING), (entry, s) -> entry.prefabPath = s, entry -> entry.prefabPath).add()).append(new KeyedCodec("@FitHeightmap", (Codec)Codec.BOOLEAN), (entry, s) -> entry.fitHeightmap = s.booleanValue(), entry -> Boolean.valueOf(entry.fitHeightmap)).add()).append(new KeyedCodec("@InheritSeed", (Codec)Codec.BOOLEAN), (entry, s) -> entry.inheritSeed = s.booleanValue(), entry -> Boolean.valueOf(entry.inheritSeed)).add()).append(new KeyedCodec("@InheritHeightCondition", (Codec)Codec.BOOLEAN), (entry, s) -> entry.inheritHeightCondition = s.booleanValue(), entry -> Boolean.valueOf(entry.inheritHeightCondition)).add()).append(new KeyedCodec("@DefaultWeight", (Codec)Codec.DOUBLE), (entry, s) -> entry.defaultWeight = s.doubleValue(), entry -> Double.valueOf(entry.defaultWeight)).addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).append(new KeyedCodec("@PrefabWeights", (Codec)Codec.STRING), (entry, s) -> entry.prefabWeights = s, entry -> entry.prefabWeights).add()).build();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\prefabspawner\PrefabSpawnerState$PrefabSpawnerSettingsPageEventData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */