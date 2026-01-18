/*     */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.FloatRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.supplier.DoubleRange;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IFloatRange;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveYawMode;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CaveNodeChildEntryJsonLoader
/*     */   extends JsonLoader<SeedStringResource, CaveNodeType.CaveNodeChildEntry>
/*     */ {
/*     */   protected final CaveNodeTypeStorage storage;
/*     */   
/*     */   public CaveNodeChildEntryJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, CaveNodeTypeStorage storage) {
/*  35 */     super(seed.append(".CaveNodeChildEntry"), dataFolder, json);
/*  36 */     this.storage = storage;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CaveNodeType.CaveNodeChildEntry load() {
/*  42 */     return new CaveNodeType.CaveNodeChildEntry(
/*  43 */         loadNodes(), 
/*  44 */         loadAnchor(), 
/*  45 */         loadOffset(), 
/*  46 */         loadRotations(), 
/*  47 */         loadChildrenLimit(), 
/*  48 */         loadRepeat(), 
/*  49 */         loadPitchModifier(), 
/*  50 */         loadYawModifier(), 
/*  51 */         loadChance(), 
/*  52 */         loadYawMode());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected IWeightedMap<CaveNodeType> loadNodes() {
/*  58 */     WeightedMap.Builder<CaveNodeType> builder = WeightedMap.builder((Object[])CaveNodeType.EMPTY_ARRAY);
/*  59 */     JsonElement nodeElement = get("Node");
/*  60 */     if (nodeElement.isJsonArray()) {
/*  61 */       JsonArray weightsArray, nodeArray = nodeElement.getAsJsonArray();
/*     */       
/*  63 */       if (has("Weights")) {
/*  64 */         JsonElement weightsElement = get("Weights");
/*  65 */         if (!weightsElement.isJsonArray()) throw new IllegalArgumentException("'Weights' must be an array if set"); 
/*  66 */         weightsArray = weightsElement.getAsJsonArray();
/*  67 */         if (weightsArray.size() != nodeArray.size()) throw new IllegalArgumentException("Weight array size is different from node name array."); 
/*     */       } else {
/*  69 */         weightsArray = null;
/*     */       } 
/*  71 */       for (int i = 0; i < nodeArray.size(); i++) {
/*  72 */         JsonElement nodeEntryElement = nodeArray.get(i);
/*  73 */         CaveNodeType caveNodeType = loadCaveNodeType(nodeEntryElement);
/*  74 */         double weight = (weightsArray != null) ? weightsArray.get(i).getAsDouble() : 1.0D;
/*  75 */         builder.put(caveNodeType, weight);
/*     */       } 
/*  77 */     } else if (nodeElement.isJsonPrimitive()) {
/*  78 */       CaveNodeType caveNodeType = loadCaveNodeType(nodeElement);
/*  79 */       builder.put(caveNodeType, 1.0D);
/*     */     } 
/*  81 */     if (builder.size() <= 0) throw new IllegalArgumentException("There are no valid nodes in this child entry!"); 
/*  82 */     return builder.build();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CaveNodeType loadCaveNodeType(@Nonnull JsonElement element) {
/*  87 */     String caveNodeTypeName = element.getAsString();
/*  88 */     return this.storage.getOrLoadCaveNodeType(caveNodeTypeName);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Vector3d loadAnchor() {
/*  93 */     Vector3d anchor = Vector3d.ZERO;
/*  94 */     if (has("Anchor")) {
/*  95 */       anchor = loadVector(anchor.clone(), get("Anchor"));
/*     */     }
/*  97 */     return anchor;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Vector3d loadOffset() {
/* 102 */     Vector3d offset = Vector3d.ZERO;
/* 103 */     if (has("Offset")) {
/* 104 */       offset = loadVector(offset.clone(), get("Offset"));
/*     */     }
/* 106 */     return offset;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected PrefabRotation[] loadRotations() {
/* 111 */     PrefabRotation[] rotations = { PrefabRotation.ROTATION_0 };
/* 112 */     if (has("Rotation")) {
/* 113 */       JsonElement rotationElement = get("Rotation");
/* 114 */       if (rotationElement.isJsonPrimitive()) {
/* 115 */         rotations = new PrefabRotation[] { PrefabRotation.valueOfExtended(rotationElement.getAsString()) };
/* 116 */       } else if (rotationElement.isJsonArray()) {
/* 117 */         JsonArray rotationArray = rotationElement.getAsJsonArray();
/* 118 */         rotations = new PrefabRotation[rotationArray.size()];
/* 119 */         for (int i = 0; i < rotations.length; i++) {
/* 120 */           rotations[i] = PrefabRotation.valueOfExtended(rotationArray.get(i).getAsString());
/*     */         }
/*     */       } 
/*     */     } 
/* 124 */     return rotations;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IDoubleRange loadChildrenLimit() {
/* 129 */     if (has("ChildrenLimit")) {
/* 130 */       return (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("ChildrenLimit"), 0.0D))
/* 131 */         .load();
/*     */     }
/* 133 */     return null;
/*     */   }
/*     */   @Nonnull
/*     */   protected IDoubleRange loadRepeat() {
/*     */     IDoubleRange iDoubleRange;
/* 138 */     DoubleRange.Constant constant = DoubleRange.ONE;
/* 139 */     if (has("Repeat"))
/*     */     {
/* 141 */       iDoubleRange = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("Repeat"), 0.0D)).load();
/*     */     }
/* 143 */     return iDoubleRange;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CaveNodeType.CaveNodeChildEntry.OrientationModifier loadYawModifier() {
/* 148 */     IFloatRange yawAdd = loadYawAdd();
/* 149 */     if (yawAdd != null) {
/* 150 */       return (current, random) -> current + yawAdd.getValue(random);
/*     */     }
/* 152 */     IFloatRange yawSet = loadYawSet();
/* 153 */     if (yawSet != null) {
/* 154 */       return (current, random) -> yawSet.getValue(random);
/*     */     }
/* 156 */     return (current, random) -> current;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CaveNodeType.CaveNodeChildEntry.OrientationModifier loadPitchModifier() {
/* 163 */     IFloatRange pitchAdd = loadPitchAdd();
/* 164 */     if (pitchAdd != null) {
/* 165 */       return (current, random) -> current + pitchAdd.getValue(random);
/*     */     }
/* 167 */     IFloatRange pitchSet = loadPitchSet();
/* 168 */     if (pitchSet != null) {
/* 169 */       return (current, random) -> pitchSet.getValue(random);
/*     */     }
/* 171 */     return (current, random) -> current;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected IFloatRange loadYawAdd() {
/* 178 */     IFloatRange yawAdd = null;
/* 179 */     if (has("YawAdd"))
/*     */     {
/* 181 */       yawAdd = (new FloatRangeJsonLoader(this.seed, this.dataFolder, get("YawAdd"), 0.0F, deg -> deg * 0.017453292F)).load();
/*     */     }
/* 183 */     return yawAdd;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IFloatRange loadPitchAdd() {
/* 188 */     IFloatRange pitchAdd = null;
/* 189 */     if (has("PitchAdd"))
/*     */     {
/* 191 */       pitchAdd = (new FloatRangeJsonLoader(this.seed, this.dataFolder, get("PitchAdd"), 0.0F, deg -> deg * 0.017453292F)).load();
/*     */     }
/* 193 */     return pitchAdd;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IFloatRange loadYawSet() {
/* 198 */     IFloatRange yawSet = null;
/* 199 */     if (has("YawSet")) {
/* 200 */       yawSet = (new FloatRangeJsonLoader(this.seed, this.dataFolder, get("YawSet"), 0.0F, deg -> deg * 0.017453292F)).load();
/*     */     }
/* 202 */     return yawSet;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected IFloatRange loadPitchSet() {
/* 207 */     IFloatRange pitchSet = null;
/* 208 */     if (has("PitchSet"))
/*     */     {
/* 210 */       pitchSet = (new FloatRangeJsonLoader(this.seed, this.dataFolder, get("PitchSet"), 0.0F, deg -> deg * 0.017453292F)).load();
/*     */     }
/* 212 */     return pitchSet;
/*     */   }
/*     */   
/*     */   protected double loadChance() {
/* 216 */     double chance = 1.0D;
/* 217 */     if (has("Chance")) {
/* 218 */       chance = get("Chance").getAsDouble();
/*     */     }
/* 220 */     return chance;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CaveYawMode loadYawMode() {
/* 225 */     CaveYawMode combiner = CaveYawMode.NODE;
/* 226 */     if (has("YawMode")) {
/* 227 */       combiner = CaveYawMode.valueOf(get("YawMode").getAsString());
/*     */     }
/* 229 */     return combiner;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Vector3d loadVector(@Nonnull Vector3d vector, @Nonnull JsonElement jsonElement) {
/* 234 */     JsonArray array = jsonElement.getAsJsonArray();
/* 235 */     vector.x = array.get(0).getAsDouble();
/* 236 */     vector.y = array.get(1).getAsDouble();
/* 237 */     vector.z = array.get(2).getAsDouble();
/* 238 */     return vector;
/*     */   }
/*     */   
/*     */   public static interface Constants {
/*     */     public static final String KEY_NODE = "Node";
/*     */     public static final String KEY_WEIGHTS = "Weights";
/*     */     public static final String KEY_ANCHOR = "Anchor";
/*     */     public static final String KEY_OFFSET = "Offset";
/*     */     public static final String KEY_ROTATION = "Rotation";
/*     */     public static final String KEY_CHILDREN_LIMIT = "ChildrenLimit";
/*     */     public static final String KEY_REPEAT = "Repeat";
/*     */     public static final String KEY_PITCH_ADD = "PitchAdd";
/*     */     public static final String KEY_PITCH_SET = "PitchSet";
/*     */     public static final String KEY_YAW_ADD = "YawAdd";
/*     */     public static final String KEY_YAW_SET = "YawSet";
/*     */     public static final String KEY_CHANCE = "Chance";
/*     */     public static final String KEY_YAW_MODE = "YawMode";
/*     */     public static final String ERROR_WEIGHTS_ARRAY = "'Weights' must be an array if set";
/*     */     public static final String ERROR_ENTRY_WEIGHT_SIZE = "Weight array size is different from node name array.";
/*     */     public static final String ERROR_NO_NODES = "There are no valid nodes in this child entry!";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CaveNodeChildEntryJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */