/*     */ package com.hypixel.hytale.server.worldgen.loader.cave;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.map.WeightedMap;
/*     */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.HeightThresholdCoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*     */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*     */ import com.hypixel.hytale.procedurallib.json.DoubleRangeJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.HeightThresholdInterpreterJsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.JsonLoader;
/*     */ import com.hypixel.hytale.procedurallib.json.SeedString;
/*     */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.worldgen.SeedStringResource;
/*     */ import com.hypixel.hytale.server.worldgen.cave.CaveNodeType;
/*     */ import com.hypixel.hytale.server.worldgen.cave.prefab.CavePrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.cave.shape.CaveNodeShapeEnum;
/*     */ import com.hypixel.hytale.server.worldgen.loader.cave.shape.DistortedCaveNodeShapeGeneratorJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.cave.shape.PipeCaveNodeShapeGeneratorJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.loader.context.ZoneFileContext;
/*     */ import com.hypixel.hytale.server.worldgen.loader.util.ResolvedBlockArrayJsonLoader;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
/*     */ import com.hypixel.hytale.server.worldgen.util.ResolvedBlockArray;
/*     */ import java.nio.file.Path;
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
/*     */ public class CaveNodeTypeJsonLoader
/*     */   extends JsonLoader<SeedStringResource, CaveNodeType>
/*     */ {
/*     */   protected final String name;
/*     */   protected final CaveNodeTypeStorage storage;
/*     */   protected final ZoneFileContext zoneContext;
/*     */   
/*     */   public CaveNodeTypeJsonLoader(@Nonnull SeedString<SeedStringResource> seed, Path dataFolder, JsonElement json, String name, CaveNodeTypeStorage storage, ZoneFileContext zoneContext) {
/*  50 */     super(seed.append(".CaveNodeType-" + name), dataFolder, json);
/*  51 */     this.name = name;
/*  52 */     this.storage = storage;
/*  53 */     this.zoneContext = zoneContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CaveNodeType load() {
/*  68 */     CaveNodeType caveNodeType = new CaveNodeType(this.name, loadPrefabs(), loadFillings(), loadShapeGenerator(), loadHeightCondition(), loadChildCountBounds(), loadCovers(), loadPriority(), loadEnvironment());
/*     */     
/*  70 */     this.storage.add(this.name, caveNodeType);
/*  71 */     caveNodeType.setChildren(loadChildren());
/*  72 */     return caveNodeType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CaveNodeType.CaveNodeChildEntry[] loadChildren() {
/*  77 */     if (has("Children")) {
/*  78 */       JsonElement childrenElement = get("Children");
/*  79 */       if (childrenElement.isJsonArray()) {
/*  80 */         JsonArray childrenArray = childrenElement.getAsJsonArray();
/*  81 */         CaveNodeType.CaveNodeChildEntry[] children = new CaveNodeType.CaveNodeChildEntry[childrenArray.size()];
/*  82 */         for (int i = 0; i < childrenArray.size(); i++) {
/*  83 */           children[i] = (new CaveNodeChildEntryJsonLoader(this.seed
/*  84 */               .append(String.format(".Child-%s", new Object[] { Integer.valueOf(i) })), this.dataFolder, childrenArray.get(i), this.storage))
/*  85 */             .load();
/*     */         } 
/*  87 */         return children;
/*  88 */       }  if (childrenElement.isJsonObject()) {
/*  89 */         CaveNodeType.CaveNodeChildEntry[] children = new CaveNodeType.CaveNodeChildEntry[1];
/*  90 */         children[0] = (new CaveNodeChildEntryJsonLoader(this.seed
/*  91 */             .append(String.format(".Child-%s", new Object[] { Integer.valueOf(0) })), this.dataFolder, childrenElement, this.storage))
/*  92 */           .load();
/*  93 */         return children;
/*     */       } 
/*     */     } 
/*  96 */     return CaveNodeType.CaveNodeChildEntry.EMPTY_ARRAY;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected CavePrefabContainer loadPrefabs() {
/* 101 */     CavePrefabContainer container = null;
/* 102 */     if (has("Prefabs")) {
/*     */       
/* 104 */       ZoneFileContext context = this.zoneContext.matchContext(this.json, "Prefabs");
/*     */ 
/*     */       
/* 107 */       container = (new CavePrefabContainerJsonLoader(this.seed, this.dataFolder, get("Prefabs"), context)).load();
/*     */     } 
/* 109 */     return container;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected IWeightedMap<BlockFluidEntry> loadFillings() {
/* 114 */     WeightedMap.Builder<BlockFluidEntry> builder = WeightedMap.builder((Object[])BlockFluidEntry.EMPTY_ARRAY);
/* 115 */     JsonElement fillingElement = get("Filling");
/*     */     
/* 117 */     if (fillingElement == null || fillingElement.isJsonNull()) {
/* 118 */       builder.put(new BlockFluidEntry(0, 0, 0), 1.0D);
/* 119 */     } else if (fillingElement.isJsonObject()) {
/* 120 */       JsonObject fillingObject = fillingElement.getAsJsonObject();
/* 121 */       JsonArray blockArray = fillingObject.getAsJsonArray("Types");
/* 122 */       JsonArray weightArray = fillingObject.getAsJsonArray("Weight");
/*     */       
/* 124 */       ResolvedBlockArray blocks = (new ResolvedBlockArrayJsonLoader(this.seed, this.dataFolder, (JsonElement)blockArray)).load();
/*     */       
/* 126 */       for (int i = 0; i < blockArray.size(); i++) {
/* 127 */         builder.put(blocks.getEntries()[i], weightArray.get(i).getAsDouble());
/*     */       }
/* 129 */     } else if (fillingElement.isJsonArray()) {
/* 130 */       JsonArray blockArray = fillingElement.getAsJsonArray();
/*     */       
/* 132 */       ResolvedBlockArray blocks = (new ResolvedBlockArrayJsonLoader(this.seed, this.dataFolder, (JsonElement)blockArray)).load();
/* 133 */       for (int i = 0; i < blockArray.size(); i++) {
/* 134 */         builder.put(blocks.getEntries()[i], 1.0D);
/*     */       }
/*     */     } else {
/* 137 */       BlockPattern.BlockEntry key = BlockPattern.BlockEntry.decode(fillingElement.getAsString());
/* 138 */       int index = BlockType.getAssetMap().getIndex(key.blockTypeKey());
/* 139 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + String.valueOf(key)); 
/* 140 */       builder.put(new BlockFluidEntry(index, key.rotation(), 0), 1.0D);
/*     */     } 
/* 142 */     return builder.build();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CaveNodeShapeEnum.CaveNodeShapeGenerator loadShapeGenerator() {
/* 147 */     if (has("Type")) {
/* 148 */       JsonElement typeElement = get("Type");
/* 149 */       String typeString = typeElement.getAsString();
/*     */       try {
/* 151 */         switch (CaveNodeShapeEnum.valueOf(typeString.toUpperCase())) { default: throw new MatchException(null, null);case PIPE: case CYLINDER: case PREFAB: case ELLIPSOID: case EMPTY_LINE: case DISTORTED: break; }  return (
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 157 */           new DistortedCaveNodeShapeGeneratorJsonLoader(this.seed, this.dataFolder, this.json)).load();
/*     */       }
/* 159 */       catch (Throwable e) {
/* 160 */         throw new Error(String.format("Could not find Shape by the name %s in Json: %s", new Object[] { typeString, typeElement }), e);
/*     */       } 
/*     */     } 
/* 163 */     return (CaveNodeShapeEnum.CaveNodeShapeGenerator)(new PipeCaveNodeShapeGeneratorJsonLoader(this.seed, this.dataFolder, this.json))
/* 164 */       .load();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected ICoordinateCondition loadHeightCondition() {
/*     */     HeightThresholdCoordinateCondition heightThresholdCoordinateCondition;
/* 170 */     DefaultCoordinateCondition defaultCoordinateCondition = DefaultCoordinateCondition.DEFAULT_TRUE;
/* 171 */     if (has("HeightThreshold")) {
/*     */       
/* 173 */       IHeightThresholdInterpreter interpreter = (new HeightThresholdInterpreterJsonLoader(this.seed, this.dataFolder, get("HeightThreshold"), 320)).load();
/* 174 */       heightThresholdCoordinateCondition = new HeightThresholdCoordinateCondition(interpreter);
/*     */     } 
/* 176 */     return (ICoordinateCondition)heightThresholdCoordinateCondition;
/*     */   } public static interface Constants {
/*     */     public static final String KEY_CHILDREN = "Children"; public static final String KEY_PREFABS = "Prefabs"; public static final String KEY_FILLING = "Filling"; public static final String KEY_FILLING_TYPES = "Types"; public static final String KEY_FILLING_WEIGHT = "Weight"; public static final String KEY_TYPE = "Type"; public static final String KEY_HEIGHT_THRESHOLDS = "HeightThreshold"; public static final String KEY_CHILD_COUNT_BOUNDS = "ChildCountBounds"; public static final String KEY_COVER = "Cover"; public static final String KEY_PRIORITY = "Priority"; public static final String KEY_ENVIRONMENT = "Environment"; public static final String SEED_COVER_SUFFIX = "-cover#%s"; public static final String SEED_CHILD_ENTRY_SUFFIX = ".Child-%s"; public static final String ERROR_UNKNOWN_SHAPE_NAME = "Could not find Shape by the name %s in Json: %s"; public static final String ERROR_UNKOWN_CONSTRUCTOR_NODE_SHAPE = "Could not find Constructor for %s CaveNodeShape"; public static final String ERROR_LOADING_ENVIRONMENT = "Error while looking up environment \"%s\"!"; }
/*     */   @Nullable
/*     */   protected IDoubleRange loadChildCountBounds() {
/* 181 */     IDoubleRange bounds = null;
/* 182 */     if (has("ChildCountBounds"))
/*     */     {
/* 184 */       bounds = (new DoubleRangeJsonLoader(this.seed, this.dataFolder, get("ChildCountBounds"))).load();
/*     */     }
/* 186 */     return bounds;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CaveNodeType.CaveNodeCoverEntry[] loadCovers() {
/* 191 */     CaveNodeType.CaveNodeCoverEntry[] entries = CaveNodeType.CaveNodeCoverEntry.EMPTY_ARRAY;
/* 192 */     if (has("Cover")) {
/* 193 */       JsonElement coverElement = get("Cover");
/* 194 */       if (coverElement.isJsonArray()) {
/* 195 */         JsonArray coverArray = coverElement.getAsJsonArray();
/* 196 */         entries = new CaveNodeType.CaveNodeCoverEntry[coverArray.size()];
/* 197 */         for (int i = 0; i < entries.length; i++) {
/* 198 */           entries[i] = (new CaveNodeCoverEntryJsonLoader(this.seed
/* 199 */               .append(String.format("-cover#%s", new Object[] { Integer.valueOf(i) })), this.dataFolder, coverArray.get(i)))
/* 200 */             .load();
/*     */         } 
/*     */       } else {
/* 203 */         entries = new CaveNodeType.CaveNodeCoverEntry[1];
/* 204 */         entries[0] = (new CaveNodeCoverEntryJsonLoader(this.seed, this.dataFolder, coverElement))
/* 205 */           .load();
/*     */       } 
/*     */     } 
/* 208 */     return entries;
/*     */   }
/*     */   
/*     */   protected int loadPriority() {
/* 212 */     int priority = 0;
/* 213 */     if (has("Priority")) {
/* 214 */       priority = get("Priority").getAsInt();
/*     */     }
/* 216 */     return priority;
/*     */   }
/*     */   
/*     */   protected int loadEnvironment() {
/* 220 */     int environment = Integer.MIN_VALUE;
/* 221 */     if (has("Environment")) {
/* 222 */       String environmentId = get("Environment").getAsString();
/* 223 */       environment = Environment.getAssetMap().getIndex(environmentId);
/* 224 */       if (environment == Integer.MIN_VALUE) throw new Error(String.format("Error while looking up environment \"%s\"!", new Object[] { environmentId })); 
/*     */     } 
/* 226 */     return environment;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\cave\CaveNodeTypeJsonLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */