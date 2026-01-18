/*     */ package com.hypixel.hytale.server.core.prefab.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.DirectDecodeCodec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.lookup.ACodecMapCodec;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.data.unknown.TempUnknownComponent;
/*     */ import com.hypixel.hytale.component.data.unknown.UnknownComponents;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockMigration;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.BsonPrefabBufferDeserializer;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockStateModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonArray;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonInt32;
/*     */ import org.bson.BsonString;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ public class SelectionPrefabSerializer
/*     */ {
/*     */   public static final int VERSION = 8;
/*     */   private static final Comparator<BsonDocument> COMPARE_BLOCK_POSITION;
/*     */   
/*     */   static {
/*  50 */     COMPARE_BLOCK_POSITION = Comparator.<BsonDocument>comparingInt(doc -> doc.getInt32("x").getValue()).thenComparingInt(doc -> doc.getInt32("z").getValue()).thenComparingInt(doc -> doc.getInt32("y").getValue());
/*     */   }
/*  52 */   private static final BsonInt32 DEFAULT_SUPPORT_VALUE = new BsonInt32(0);
/*  53 */   private static final BsonInt32 DEFAULT_FILLER_VALUE = new BsonInt32(0);
/*  54 */   private static final BsonInt32 DEFAULT_ROTATION_VALUE = new BsonInt32(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static BlockSelection deserialize(@Nonnull BsonDocument doc) {
/*  61 */     BsonValue versionValue = doc.get("version");
/*  62 */     int version = (versionValue != null) ? versionValue.asInt32().getValue() : -1;
/*  63 */     if (version <= 0) throw new IllegalArgumentException("Prefab version is too old: " + version); 
/*  64 */     if (version > 8) throw new IllegalArgumentException("Prefab version is too new: " + version + " by expected 8");
/*     */     
/*  66 */     int worldVersion = (version < 4) ? readWorldVersion(doc) : 0;
/*  67 */     BsonValue entityVersionValue = doc.get("entityVersion");
/*  68 */     int entityVersion = (entityVersionValue != null) ? entityVersionValue.asInt32().getValue() : 0;
/*     */     
/*  70 */     int anchorX = doc.getInt32("anchorX").getValue();
/*  71 */     int anchorY = doc.getInt32("anchorY").getValue();
/*  72 */     int anchorZ = doc.getInt32("anchorZ").getValue();
/*     */     
/*  74 */     BlockSelection selection = new BlockSelection();
/*  75 */     selection.setAnchor(anchorX, anchorY, anchorZ);
/*     */     
/*  77 */     int blockIdVersion = doc.getInt32("blockIdVersion", BsonPrefabBufferDeserializer.LEGACY_BLOCK_ID_VERSION).getValue();
/*     */     
/*  79 */     Function<String, String> blockMigration = null;
/*  80 */     Map<Integer, BlockMigration> blockMigrationMap = BlockMigration.getAssetMap().getAssetMap();
/*  81 */     int v = blockIdVersion;
/*  82 */     BlockMigration migration = blockMigrationMap.get(Integer.valueOf(v));
/*  83 */     while (migration != null) {
/*  84 */       if (blockMigration == null) { Objects.requireNonNull(migration); blockMigration = migration::getMigration; }
/*  85 */       else { Objects.requireNonNull(migration); blockMigration = blockMigration.andThen(migration::getMigration); }
/*  86 */        migration = blockMigrationMap.get(Integer.valueOf(++v));
/*     */     } 
/*     */     
/*  89 */     BsonValue blocksValue = doc.get("blocks");
/*  90 */     if (blocksValue != null) {
/*  91 */       BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*     */       
/*  93 */       BsonArray bsonArray = blocksValue.asArray();
/*  94 */       for (int i = 0; i < bsonArray.size(); i++) {
/*  95 */         BsonDocument innerObj = bsonArray.get(i).asDocument();
/*  96 */         int x = innerObj.getInt32("x").getValue();
/*  97 */         int y = innerObj.getInt32("y").getValue();
/*  98 */         int z = innerObj.getInt32("z").getValue();
/*  99 */         String blockTypeStr = innerObj.getString("name").getValue();
/* 100 */         boolean legacyStripName = false;
/*     */         
/* 102 */         if (version <= 4) {
/* 103 */           Fluid.ConversionResult result = Fluid.convertBlockToFluid(blockTypeStr);
/*     */           
/* 105 */           if (result != null) {
/* 106 */             legacyStripName = true;
/* 107 */             selection.addFluidAtLocalPos(x, y, z, result.fluidId, result.fluidLevel);
/* 108 */             if (result.blockTypeStr == null)
/*     */               continue; 
/*     */           } 
/*     */         } 
/* 112 */         int support = 0;
/* 113 */         if (version >= 6) {
/* 114 */           support = innerObj.getInt32("support", DEFAULT_SUPPORT_VALUE).getValue();
/*     */         }
/* 116 */         else if (blockTypeStr.contains("|Deco")) {
/* 117 */           legacyStripName = true;
/* 118 */           support = 15;
/* 119 */         } else if (blockTypeStr.contains("|Support=")) {
/* 120 */           legacyStripName = true;
/* 121 */           int start = blockTypeStr.indexOf("|Support=") + "|Support=".length();
/* 122 */           int end = blockTypeStr.indexOf('|', start);
/* 123 */           if (end == -1) end = blockTypeStr.length(); 
/* 124 */           support = Integer.parseInt(blockTypeStr, start, end, 10);
/*     */         } else {
/* 126 */           support = 0;
/*     */         } 
/*     */ 
/*     */         
/* 130 */         int filler = 0;
/* 131 */         if (version >= 7) {
/* 132 */           filler = innerObj.getInt32("filler", DEFAULT_FILLER_VALUE).getValue();
/*     */         }
/* 134 */         else if (blockTypeStr.contains("|Filler=")) {
/* 135 */           legacyStripName = true;
/* 136 */           int start = blockTypeStr.indexOf("|Filler=") + "|Filler=".length();
/* 137 */           int firstComma = blockTypeStr.indexOf(',', start);
/* 138 */           if (firstComma == -1) throw new IllegalArgumentException("Invalid filler metadata! Missing comma"); 
/* 139 */           int secondComma = blockTypeStr.indexOf(',', firstComma + 1);
/* 140 */           if (secondComma == -1) throw new IllegalArgumentException("Invalid filler metadata! Missing second comma");
/*     */           
/* 142 */           int end = blockTypeStr.indexOf('|', start);
/* 143 */           if (end == -1) end = blockTypeStr.length();
/*     */           
/* 145 */           int fillerX = Integer.parseInt(blockTypeStr, start, firstComma, 10);
/* 146 */           int fillerY = Integer.parseInt(blockTypeStr, firstComma + 1, secondComma, 10);
/* 147 */           int fillerZ = Integer.parseInt(blockTypeStr, secondComma + 1, end, 10);
/*     */           
/* 149 */           filler = FillerBlockUtil.pack(fillerX, fillerY, fillerZ);
/*     */         } else {
/* 151 */           filler = 0;
/*     */         } 
/*     */ 
/*     */         
/* 155 */         int rotation = 0;
/* 156 */         if (version >= 8) {
/* 157 */           rotation = innerObj.getInt32("rotation", DEFAULT_ROTATION_VALUE).getValue();
/*     */         } else {
/* 159 */           Rotation yaw = Rotation.None;
/* 160 */           Rotation pitch = Rotation.None;
/* 161 */           Rotation roll = Rotation.None;
/*     */           
/* 163 */           if (blockTypeStr.contains("|Yaw=")) {
/* 164 */             legacyStripName = true;
/* 165 */             int start = blockTypeStr.indexOf("|Yaw=") + "|Yaw=".length();
/* 166 */             int end = blockTypeStr.indexOf('|', start);
/* 167 */             if (end == -1) end = blockTypeStr.length(); 
/* 168 */             yaw = Rotation.ofDegrees(Integer.parseInt(blockTypeStr, start, end, 10));
/*     */           } 
/*     */           
/* 171 */           if (blockTypeStr.contains("|Pitch=")) {
/* 172 */             legacyStripName = true;
/* 173 */             int start = blockTypeStr.indexOf("|Pitch=") + "|Pitch=".length();
/* 174 */             int end = blockTypeStr.indexOf('|', start);
/* 175 */             if (end == -1) end = blockTypeStr.length(); 
/* 176 */             pitch = Rotation.ofDegrees(Integer.parseInt(blockTypeStr, start, end, 10));
/*     */           } 
/*     */           
/* 179 */           if (blockTypeStr.contains("|Roll=")) {
/* 180 */             legacyStripName = true;
/* 181 */             int start = blockTypeStr.indexOf("|Roll=") + "|Roll=".length();
/* 182 */             int end = blockTypeStr.indexOf('|', start);
/* 183 */             if (end == -1) end = blockTypeStr.length(); 
/* 184 */             pitch = Rotation.ofDegrees(Integer.parseInt(blockTypeStr, start, end, 10));
/*     */           } 
/*     */           
/* 187 */           rotation = RotationTuple.index(yaw, pitch, roll);
/*     */         } 
/*     */         
/* 190 */         if (legacyStripName) {
/* 191 */           int endOfName = blockTypeStr.indexOf('|');
/* 192 */           if (endOfName != -1) {
/* 193 */             blockTypeStr = blockTypeStr.substring(0, endOfName);
/*     */           }
/*     */         } 
/*     */         
/* 197 */         String blockTypeKey = blockTypeStr;
/* 198 */         if (blockMigration != null) blockTypeKey = blockMigration.apply(blockTypeKey);
/*     */         
/* 200 */         int blockId = BlockType.getBlockIdOrUnknown(assetMap, blockTypeKey, "Failed to find block '%s' in unknown legacy prefab!", new Object[] { blockTypeStr });
/*     */         
/* 202 */         Holder<ChunkStore> wrapper = null;
/* 203 */         if (version <= 2) {
/* 204 */           BsonValue stateValue = innerObj.get("state");
/* 205 */           if (stateValue != null) {
/* 206 */             wrapper = legacyStateDecode(stateValue.asDocument());
/*     */           }
/*     */         } else {
/* 209 */           BsonValue stateValue = innerObj.get("components");
/* 210 */           if (stateValue != null) {
/* 211 */             if (version < 4) {
/* 212 */               wrapper = ChunkStore.REGISTRY.deserialize(stateValue.asDocument(), worldVersion);
/*     */             } else {
/* 214 */               wrapper = ChunkStore.REGISTRY.deserialize(stateValue.asDocument());
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 219 */         selection.addBlockAtLocalPos(x, y, z, blockId, rotation, filler, support, wrapper);
/*     */         continue;
/*     */       } 
/*     */     } 
/* 223 */     BsonValue fluidsValue = doc.get("fluids");
/* 224 */     if (fluidsValue != null) {
/* 225 */       IndexedLookupTableAssetMap<String, Fluid> assetMap = Fluid.getAssetMap();
/*     */       
/* 227 */       BsonArray bsonArray = fluidsValue.asArray();
/* 228 */       for (int i = 0; i < bsonArray.size(); i++) {
/* 229 */         BsonDocument innerObj = bsonArray.get(i).asDocument();
/* 230 */         int x = innerObj.getInt32("x").getValue();
/* 231 */         int y = innerObj.getInt32("y").getValue();
/* 232 */         int z = innerObj.getInt32("z").getValue();
/* 233 */         String fluidName = innerObj.getString("name").getValue();
/*     */         
/* 235 */         int fluidId = Fluid.getFluidIdOrUnknown(assetMap, fluidName, "Failed to find fluid '%s' in unknown legacy prefab!", new Object[] { fluidName });
/* 236 */         byte fluidLevel = (byte)innerObj.getInt32("level").getValue();
/* 237 */         selection.addFluidAtLocalPos(x, y, z, fluidId, fluidLevel);
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     BsonValue entitiesValues = doc.get("entities");
/* 242 */     if (entitiesValues != null) {
/* 243 */       BsonArray entities = entitiesValues.asArray();
/* 244 */       for (int i = 0; i < entities.size(); i++) {
/* 245 */         BsonDocument bsonDocument = entities.get(i).asDocument();
/* 246 */         if (version <= 1) {
/*     */           try {
/* 248 */             selection.addEntityHolderRaw(legacyEntityDecode(bsonDocument, entityVersion));
/* 249 */           } catch (Throwable t) {
/* 250 */             ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.WARNING).withCause(t)).log("Exception when loading entity state %s", bsonDocument);
/*     */           } 
/*     */         } else {
/* 253 */           selection.addEntityHolderRaw(EntityStore.REGISTRY.deserialize(bsonDocument));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 258 */     return selection;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BsonDocument serialize(@Nonnull BlockSelection prefab) {
/* 263 */     Objects.requireNonNull(prefab, "null prefab");
/*     */     
/* 265 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/* 266 */     IndexedLookupTableAssetMap<String, Fluid> fluidMap = Fluid.getAssetMap();
/*     */     
/* 268 */     BsonDocument out = new BsonDocument();
/*     */     
/* 270 */     out.put("version", (BsonValue)new BsonInt32(8));
/* 271 */     out.put("blockIdVersion", (BsonValue)new BsonInt32(BlockMigration.getAssetMap().getAssetCount()));
/*     */     
/* 273 */     out.put("anchorX", (BsonValue)new BsonInt32(prefab.getAnchorX()));
/* 274 */     out.put("anchorY", (BsonValue)new BsonInt32(prefab.getAnchorY()));
/* 275 */     out.put("anchorZ", (BsonValue)new BsonInt32(prefab.getAnchorZ()));
/*     */     
/* 277 */     BsonArray contentOut = new BsonArray();
/* 278 */     prefab.forEachBlock((x, y, z, block) -> {
/*     */           BsonDocument innerObj = new BsonDocument(); innerObj.put("x", (BsonValue)new BsonInt32(x));
/*     */           innerObj.put("y", (BsonValue)new BsonInt32(y));
/*     */           innerObj.put("z", (BsonValue)new BsonInt32(z));
/*     */           innerObj.put("name", (BsonValue)new BsonString(((BlockType)assetMap.getAsset(block.blockId())).getId().toString()));
/*     */           if (block.holder() != null)
/*     */             innerObj.put("components", (BsonValue)ChunkStore.REGISTRY.serialize(block.holder())); 
/*     */           if (block.supportValue() != 0)
/*     */             innerObj.put("support", (BsonValue)new BsonInt32(block.supportValue())); 
/*     */           if (block.filler() != 0)
/*     */             innerObj.put("filler", (BsonValue)new BsonInt32(block.filler())); 
/*     */           if (block.rotation() != 0)
/*     */             innerObj.put("rotation", (BsonValue)new BsonInt32(block.rotation())); 
/*     */           contentOut.add((BsonValue)innerObj);
/*     */         });
/* 293 */     contentOut.sort((a, b) -> {
/*     */           BsonDocument aDoc = a.asDocument();
/*     */           BsonDocument bDoc = b.asDocument();
/*     */           return COMPARE_BLOCK_POSITION.compare(aDoc, bDoc);
/*     */         });
/* 298 */     out.put("blocks", (BsonValue)contentOut);
/*     */ 
/*     */     
/* 301 */     BsonArray fluidContentOut = new BsonArray();
/* 302 */     prefab.forEachFluid((x, y, z, fluid, level) -> {
/*     */           BsonDocument innerObj = new BsonDocument();
/*     */           
/*     */           innerObj.put("x", (BsonValue)new BsonInt32(x));
/*     */           innerObj.put("y", (BsonValue)new BsonInt32(y));
/*     */           innerObj.put("z", (BsonValue)new BsonInt32(z));
/*     */           innerObj.put("name", (BsonValue)new BsonString(((Fluid)fluidMap.getAsset(fluid)).getId()));
/*     */           innerObj.put("level", (BsonValue)new BsonInt32(level));
/*     */           fluidContentOut.add((BsonValue)innerObj);
/*     */         });
/* 312 */     fluidContentOut.sort((a, b) -> {
/*     */           BsonDocument aDoc = a.asDocument();
/*     */           BsonDocument bDoc = b.asDocument();
/*     */           return COMPARE_BLOCK_POSITION.compare(aDoc, bDoc);
/*     */         });
/* 317 */     if (!fluidContentOut.isEmpty()) {
/* 318 */       out.put("fluids", (BsonValue)fluidContentOut);
/*     */     }
/*     */     
/* 321 */     BsonArray entities = new BsonArray();
/* 322 */     prefab.forEachEntity(holder -> entities.add((BsonValue)EntityStore.REGISTRY.serialize(holder)));
/*     */ 
/*     */     
/* 325 */     if (!entities.isEmpty()) {
/* 326 */       out.put("entities", (BsonValue)entities);
/*     */     }
/*     */     
/* 329 */     return out;
/*     */   }
/*     */   
/*     */   public static int readWorldVersion(@Nonnull BsonDocument document) {
/*     */     int worldVersion;
/* 334 */     if (document.containsKey("worldVersion")) {
/* 335 */       worldVersion = document.getInt32("worldVersion").getValue();
/* 336 */     } else if (document.containsKey("worldver")) {
/* 337 */       worldVersion = document.getInt32("worldver").getValue();
/*     */     } else {
/* 339 */       worldVersion = 5;
/*     */     } 
/*     */     
/* 342 */     if (worldVersion == 18553)
/* 343 */       throw new IllegalArgumentException("WorldChunk version old format! Update!"); 
/* 344 */     if (worldVersion > 23) {
/* 345 */       throw new IllegalArgumentException("WorldChunk version is newer than we understand! Version: " + worldVersion + ", Latest Version: 23");
/*     */     }
/*     */ 
/*     */     
/* 349 */     return worldVersion;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Holder<EntityStore> legacyEntityDecode(@Nonnull BsonDocument document, int version) {
/* 354 */     String entityTypeStr = document.getString("EntityType").getValue();
/* 355 */     Class<? extends Entity> entityType = EntityModule.get().getClass(entityTypeStr);
/* 356 */     if (entityType == null) {
/* 357 */       UnknownComponents unknownComponents = new UnknownComponents();
/* 358 */       unknownComponents.addComponent(entityTypeStr, new TempUnknownComponent(document));
/* 359 */       return EntityStore.REGISTRY.newHolder(Archetype.of(EntityStore.REGISTRY.getUnknownComponentType()), new Component[] { (Component)unknownComponents });
/*     */     } 
/*     */     
/* 362 */     Function<World, ? extends Entity> constructor = EntityModule.get().getConstructor(entityType);
/* 363 */     if (constructor == null) return null;
/*     */     
/* 365 */     DirectDecodeCodec<? extends Entity> codec = EntityModule.get().getCodec(entityType);
/* 366 */     Objects.requireNonNull(codec, "Unable to create entity because there is no associated codec");
/*     */     
/* 368 */     Entity entity = constructor.apply(null);
/*     */     
/* 370 */     codec.decode((BsonValue)document, entity, new ExtraInfo(version));
/* 371 */     return entity.toHolder();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Holder<ChunkStore> legacyStateDecode(@Nonnull BsonDocument document) {
/* 376 */     ExtraInfo extraInto = ExtraInfo.THREAD_LOCAL.get();
/* 377 */     String type = (String)BlockState.TYPE_STRUCTURE.getNow(document, extraInto);
/* 378 */     Class<? extends BlockState> blockStateClass = BlockState.CODEC.getClassFor(type);
/* 379 */     if (blockStateClass != null) {
/*     */       try {
/* 381 */         BlockState t = (BlockState)BlockState.CODEC.decode((BsonValue)document, extraInto);
/*     */         
/* 383 */         Holder<ChunkStore> holder1 = ChunkStore.REGISTRY.newHolder();
/* 384 */         ComponentType<ChunkStore, ? extends BlockState> componentType = BlockStateModule.get().getComponentType(blockStateClass);
/* 385 */         if (componentType == null) throw new IllegalArgumentException("Unable to find component type for: " + String.valueOf(blockStateClass));
/*     */         
/* 387 */         holder1.addComponent(componentType, (Component)t);
/* 388 */         return holder1;
/* 389 */       } catch (com.hypixel.hytale.codec.lookup.ACodecMapCodec.UnknownIdException unknownIdException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 394 */     Holder<ChunkStore> holder = ChunkStore.REGISTRY.newHolder();
/* 395 */     UnknownComponents<ChunkStore> unknownComponents = new UnknownComponents();
/* 396 */     unknownComponents.addComponent(type, new TempUnknownComponent(document));
/* 397 */     holder.addComponent(ChunkStore.REGISTRY.getUnknownComponentType(), (Component)unknownComponents);
/* 398 */     return holder;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\config\SelectionPrefabSerializer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */