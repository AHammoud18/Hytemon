/*     */ package com.hypixel.hytale.server.spawning;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.modules.collision.BoxBlockIntersectionEvaluator;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionConfig;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*     */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*     */ import com.hypixel.hytale.server.core.modules.collision.WorldUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.environment.EnvironmentColumn;
/*     */ import com.hypixel.hytale.server.npc.util.NPCPhysicsMath;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.spawning.suppression.SuppressionSpanHelper;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawningContext
/*     */ {
/*  38 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  40 */   private static final BlockTypeAssetMap<String, BlockType> BLOCK_ASSET_MAP = BlockType.getAssetMap();
/*     */   
/*     */   @Nullable
/*     */   public World world;
/*     */   
/*     */   @Nullable
/*     */   public WorldChunk worldChunk;
/*     */   
/*     */   public int xBlock;
/*     */   
/*     */   public int zBlock;
/*     */   
/*     */   public double ySpawnHint;
/*     */   
/*     */   public int groundLevel;
/*     */   public int groundBlockId;
/*     */   public int groundRotation;
/*     */   @Nullable
/*     */   public BlockType groundBlockType;
/*     */   public int groundFluidId;
/*     */   @Nullable
/*     */   public Fluid groundFluid;
/*     */   public int ySpanMin;
/*     */   public int ySpanMax;
/*     */   public int yBlock;
/*     */   public int waterLevel;
/*     */   public int airHeight;
/*     */   public double ySpawnMin;
/*     */   public double xSpawn;
/*     */   public double zSpawn;
/*     */   public double ySpawn;
/*  71 */   private int environmentIndex = Integer.MIN_VALUE;
/*  72 */   private int minSpawnSpanHeight = Integer.MAX_VALUE;
/*     */   
/*     */   public double yaw;
/*     */   
/*     */   public double pitch;
/*     */   
/*     */   public double roll;
/*     */   
/*     */   @Nullable
/*     */   private ISpawnableWithModel spawnable;
/*     */   
/*     */   @Nullable
/*     */   private Model spawnModel;
/*     */   @Nullable
/*     */   private Scope modifierScope;
/*  87 */   private final CollisionResult collisionResult = new CollisionResult();
/*  88 */   private final Vector3d position = new Vector3d();
/*     */   
/*  90 */   private final ExecutionContext executionContext = new ExecutionContext();
/*     */   
/*     */   private SpawnSpan[] spawnSpans;
/*     */   private int spawnSpansUsed;
/*     */   private int currentSpawnSpanIndex;
/*     */   
/*     */   public SpawningContext() {
/*  97 */     this.spawnSpans = new SpawnSpan[4];
/*  98 */     for (int i = 0; i < this.spawnSpans.length; i++) {
/*  99 */       this.spawnSpans[i] = new SpawnSpan();
/*     */     }
/* 101 */     this.spawnSpansUsed = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int SOLID_BLOCK = -1;
/*     */   
/*     */   private static final int EMPTY_BLOCK = 0;
/*     */   private static final int FLUID_BLOCK = 1;
/*     */   
/*     */   public boolean setSpawnable(@Nonnull ISpawnableWithModel spawnable) {
/* 111 */     return setSpawnable(spawnable, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setSpawnable(@Nonnull ISpawnableWithModel spawnable, boolean maxScale) {
/*     */     String modelName;
/* 122 */     if (spawnable == this.spawnable) return true;
/*     */ 
/*     */     
/* 125 */     this.spawnable = spawnable;
/*     */     try {
/* 127 */       this.executionContext.setScope(spawnable.createExecutionScope());
/* 128 */       this.modifierScope = this.spawnable.createModifierScope(this.executionContext);
/* 129 */       modelName = spawnable.getSpawnModelName(this.executionContext, this.modifierScope);
/* 130 */     } catch (Throwable t) {
/* 131 */       LOGGER.at(Level.WARNING).log("Can't set role in spawning context %s: %s", spawnable.getIdentifier(), t.getMessage());
/* 132 */       spawnable.markNeedsReload();
/* 133 */       this.spawnable = null;
/* 134 */       return false;
/*     */     } 
/*     */     
/* 137 */     if (!setModel(modelName, maxScale)) {
/* 138 */       LOGGER.at(Level.WARNING).log("Can't set model in spawning context %s: %s", spawnable.getIdentifier(), modelName);
/* 139 */       spawnable.markNeedsReload();
/* 140 */       this.spawnable = null;
/* 141 */       return false;
/*     */     } 
/* 143 */     return true;
/*     */   }
/*     */   
/*     */   private boolean setModel(@Nullable String modelName, boolean maxScale) {
/* 147 */     if (modelName == null) {
/* 148 */       clearModel();
/* 149 */       return false;
/*     */     } 
/*     */     
/* 152 */     String currentModelName = (this.spawnModel != null) ? this.spawnModel.getModelAssetId() : null;
/* 153 */     if (modelName.equals(currentModelName)) return true;
/*     */     
/* 155 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(modelName);
/* 156 */     Model model = null;
/* 157 */     if (modelAsset != null) {
/* 158 */       model = maxScale ? Model.createScaledModel(modelAsset, modelAsset.getMaxScale()) : Model.createRandomScaleModel(modelAsset);
/*     */     }
/*     */     
/* 161 */     if (model == null || model.getBoundingBox() == null) {
/* 162 */       clearModel();
/* 163 */       return false;
/*     */     } 
/*     */     
/* 166 */     this.spawnModel = model;
/*     */     
/* 168 */     this.minSpawnSpanHeight = MathUtil.ceil(model.getBoundingBox().height() + 0.20000000298023224D);
/* 169 */     return true;
/*     */   }
/*     */   
/*     */   private void clearModel() {
/* 173 */     this.spawnModel = null;
/* 174 */     this.minSpawnSpanHeight = Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void newModel() {
/* 181 */     if (this.spawnModel != null) {
/*     */ 
/*     */       
/* 184 */       ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(this.spawnModel.getModelAssetId());
/* 185 */       this.spawnModel = Model.createRandomScaleModel(modelAsset);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Model getModel() {
/* 196 */     return this.spawnModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChunk(@Nonnull WorldChunk worldChunk, int environmentIndex) {
/* 206 */     this.worldChunk = worldChunk;
/* 207 */     this.world = worldChunk.getWorld();
/* 208 */     this.environmentIndex = environmentIndex;
/* 209 */     commonInit();
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
/*     */   public boolean setColumn(int x, int z, int yHint, @Nonnull int[] yRange) {
/* 222 */     this.xBlock = x;
/* 223 */     this.zBlock = z;
/* 224 */     this.ySpawnHint = -1.0D;
/*     */     
/* 226 */     this.spawnSpansUsed = 0;
/* 227 */     int min = Math.max(0, yHint + yRange[0]);
/* 228 */     int max = Math.min(319, yHint + yRange[1]);
/*     */     
/* 230 */     splitRangeToSpawnSpans(min, max);
/*     */     
/* 232 */     return (this.spawnSpansUsed > 0);
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
/*     */   public boolean setColumn(int x, int z, int yHint, @Nonnull int[] yRange, @Nonnull SuppressionSpanHelper suppressionHelper) {
/* 246 */     this.xBlock = x;
/* 247 */     this.zBlock = z;
/* 248 */     this.ySpawnHint = -1.0D;
/*     */     
/* 250 */     this.spawnSpansUsed = 0;
/* 251 */     int y = Math.max(0, yHint + yRange[0]);
/* 252 */     int hintMax = Math.min(319, yHint + yRange[1]);
/*     */     
/* 254 */     while (y <= hintMax) {
/* 255 */       int min = suppressionHelper.adjustSpawnRangeMin(y);
/* 256 */       if (min >= hintMax) {
/*     */         break;
/*     */       }
/*     */       
/* 260 */       int max = Math.min(suppressionHelper.adjustSpawnRangeMax(min, hintMax), hintMax);
/* 261 */       y = max + 1;
/* 262 */       splitRangeToSpawnSpans(min, max);
/*     */     } 
/*     */     
/* 265 */     return (this.spawnSpansUsed > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumn(int x, int z, @Nonnull SuppressionSpanHelper suppressionHelper) {
/* 276 */     this.xBlock = x;
/* 277 */     this.zBlock = z;
/* 278 */     this.ySpawnHint = -1.0D;
/*     */     
/* 280 */     this.spawnSpansUsed = 0;
/* 281 */     EnvironmentColumn column = this.worldChunk.getBlockChunk().getEnvironmentColumn(this.xBlock, this.zBlock);
/* 282 */     for (int i = column.indexOf(0); i < column.size(); i++) {
/* 283 */       int envId = column.getValue(i);
/* 284 */       if (envId == this.environmentIndex) {
/*     */ 
/*     */         
/* 287 */         int min = Math.max(0, column.getValueMin(i));
/* 288 */         if (min > 320) {
/*     */           break;
/*     */         }
/* 291 */         int adjustedMin = suppressionHelper.adjustSpawnRangeMin(min);
/*     */         
/* 293 */         int max = column.getValueMax(i);
/*     */         
/* 295 */         if (adjustedMin > max) {
/*     */ 
/*     */           
/* 298 */           i = column.indexOf(adjustedMin) - 1;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 303 */           int adjustedMax = suppressionHelper.adjustSpawnRangeMax(adjustedMin, max);
/* 304 */           splitRangeToSpawnSpans(adjustedMin, Math.min(adjustedMax, 319));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } @Nullable
/*     */   public Scope getModifierScope() {
/* 310 */     return this.modifierScope;
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
/*     */   
/*     */   public boolean set(@Nonnull World world, double x, double y, double z) {
/* 325 */     if (this.minSpawnSpanHeight >= Integer.MAX_VALUE) throw new IllegalStateException("minSpawnSpanHeight not set - forgot to set model or role?"); 
/* 326 */     this.xBlock = MathUtil.floor(x);
/* 327 */     this.zBlock = MathUtil.floor(z);
/* 328 */     this.ySpawnHint = y;
/*     */     
/* 330 */     this.worldChunk = world.getChunkIfLoaded(ChunkUtil.indexChunkFromBlock(this.xBlock, this.zBlock));
/* 331 */     if (this.worldChunk == null)
/*     */     {
/* 333 */       return false;
/*     */     }
/* 335 */     this.xBlock = ChunkUtil.localCoordinate(this.xBlock);
/* 336 */     this.zBlock = ChunkUtil.localCoordinate(this.zBlock);
/*     */     
/* 338 */     this.environmentIndex = Integer.MIN_VALUE;
/* 339 */     this.world = world;
/* 340 */     commonInit();
/*     */ 
/*     */     
/* 343 */     int yInt = MathUtil.floor(y);
/* 344 */     this.spawnSpansUsed = 0;
/*     */     
/* 346 */     EnvironmentColumn environmentColumn = this.worldChunk.getBlockChunk().getEnvironmentColumn(this.xBlock, this.zBlock);
/* 347 */     int rangeMin = Math.max(0, environmentColumn.getMin(yInt));
/* 348 */     int rangeMax = Math.min(environmentColumn.getMax(yInt), 319);
/* 349 */     splitRangeToSpawnSpans(rangeMin, rangeMax);
/*     */     
/* 351 */     if (this.spawnSpansUsed == 0) return false;
/*     */ 
/*     */ 
/*     */     
/* 355 */     int distance = Integer.MAX_VALUE;
/* 356 */     int chosenIndex = -1;
/* 357 */     for (int index = 0; index < this.spawnSpansUsed; index++) {
/*     */       int currentDistance;
/* 359 */       SpawnSpan spawnSpan = this.spawnSpans[index];
/* 360 */       if (spawnSpan.top < yInt) {
/* 361 */         currentDistance = yInt - spawnSpan.top;
/* 362 */       } else if (spawnSpan.bottom > yInt) {
/* 363 */         currentDistance = spawnSpan.bottom - yInt;
/*     */       } else {
/* 365 */         chosenIndex = index;
/*     */         break;
/*     */       } 
/* 368 */       if (currentDistance < distance) {
/* 369 */         chosenIndex = index;
/* 370 */         distance = currentDistance;
/*     */       } 
/*     */     } 
/*     */     
/* 374 */     return selectSpawnSpan(chosenIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteCurrentSpawnSpan() {
/* 381 */     if (--this.spawnSpansUsed > this.currentSpawnSpanIndex) {
/* 382 */       SpawnSpan temp = this.spawnSpans[this.currentSpawnSpanIndex];
/* 383 */       System.arraycopy(this.spawnSpans, this.currentSpawnSpanIndex + 1, this.spawnSpans, this.currentSpawnSpanIndex, this.spawnSpansUsed - this.currentSpawnSpanIndex);
/* 384 */       this.spawnSpans[this.spawnSpansUsed] = temp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean selectRandomSpawnSpan() {
/* 394 */     return (this.spawnSpansUsed > 0 && selectSpawnSpan(ThreadLocalRandom.current().nextInt(0, this.spawnSpansUsed)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean selectSpawnSpan(int index) {
/* 404 */     if (index < 0 || index >= this.spawnSpansUsed) return false; 
/* 405 */     this.currentSpawnSpanIndex = index;
/*     */     
/* 407 */     SpawnSpan spawnSpan = this.spawnSpans[this.currentSpawnSpanIndex];
/* 408 */     this.ySpanMin = spawnSpan.bottom;
/* 409 */     this.ySpanMax = spawnSpan.top;
/* 410 */     this.waterLevel = spawnSpan.waterLevel;
/* 411 */     this.groundLevel = spawnSpan.groundLevel;
/*     */     
/* 413 */     if (this.waterLevel != -1) {
/* 414 */       this.airHeight = -1;
/*     */       
/* 416 */       if (this.waterLevel < 319) {
/* 417 */         int blockId = this.worldChunk.getBlockChunk().getBlock(this.xBlock, this.waterLevel + 1, this.zBlock);
/* 418 */         int fluidId = this.worldChunk.getFluidId(this.xBlock, this.waterLevel + 1, this.zBlock);
/*     */         
/* 420 */         if ((blockId == 0 && fluidId == 0) || (((BlockType)BLOCK_ASSET_MAP
/* 421 */           .getAsset(blockId)).getMaterial() == BlockMaterial.Empty && fluidId == 0)) {
/* 422 */           this.airHeight = this.waterLevel + 1;
/*     */         }
/*     */       } 
/*     */     } else {
/* 426 */       this.airHeight = this.groundLevel + 1;
/*     */     } 
/*     */     
/* 429 */     this.yBlock = this.groundLevel;
/* 430 */     this.groundBlockId = this.worldChunk.getBlock(this.xBlock, this.groundLevel, this.zBlock);
/* 431 */     this.groundRotation = this.worldChunk.getRotationIndex(this.xBlock, this.groundLevel, this.zBlock);
/* 432 */     this.groundBlockType = (BlockType)BLOCK_ASSET_MAP.getAsset(this.groundBlockId);
/* 433 */     this.groundFluidId = this.worldChunk.getFluidId(this.xBlock, this.groundLevel, this.zBlock);
/* 434 */     this.groundFluid = (Fluid)Fluid.getAssetMap().getAsset(this.groundFluidId);
/* 435 */     this.ySpawnMin = this.yBlock + NPCPhysicsMath.blockHeight(this.groundBlockType, this.groundRotation);
/*     */ 
/*     */     
/* 438 */     this.xSpawn = (ChunkUtil.minBlock(this.worldChunk.getX()) + this.xBlock) + 0.5D;
/* 439 */     this.zSpawn = (ChunkUtil.minBlock(this.worldChunk.getZ()) + this.zBlock) + 0.5D;
/* 440 */     this.ySpawn = this.ySpawnMin - (this.spawnModel.getBoundingBox()).min.y;
/*     */     
/* 442 */     return true;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void splitRangeToSpawnSpans(int min, int max) {
/* 459 */     int span = 0;
/* 460 */     int waterLevel = -1;
/* 461 */     int groundLevel = -1;
/*     */     
/* 463 */     while (min <= max) {
/* 464 */       int kind = isSpawnSpanBlock(this.xBlock, min, this.zBlock);
/* 465 */       if (kind != -1) {
/* 466 */         if (kind == 1) waterLevel = min; 
/* 467 */         span++;
/*     */       } else {
/* 469 */         if (span > this.minSpawnSpanHeight) {
/* 470 */           addSpawnSpan(min, span, groundLevel, waterLevel);
/*     */         }
/* 472 */         span = 0;
/* 473 */         waterLevel = -1;
/* 474 */         groundLevel = min;
/*     */       } 
/* 476 */       min++;
/*     */     } 
/* 478 */     if (span > this.minSpawnSpanHeight) {
/* 479 */       addSpawnSpan(min, span, groundLevel, waterLevel);
/*     */     }
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
/*     */   private void addSpawnSpan(int top, int span, int groundLevel, int waterLevel) {
/* 492 */     if (groundLevel == -1) {
/* 493 */       groundLevel = top - span;
/* 494 */       int blockType = isSpawnSpanBlock(this.xBlock, groundLevel, this.zBlock);
/* 495 */       while (groundLevel >= 0 && blockType != -1) {
/* 496 */         if (waterLevel == -1 && blockType == 1) waterLevel = groundLevel; 
/* 497 */         blockType = isSpawnSpanBlock(this.xBlock, --groundLevel, this.zBlock);
/*     */       } 
/*     */     } 
/*     */     
/* 501 */     if (waterLevel == top - 1) {
/* 502 */       while (waterLevel < 319 && 
/* 503 */         isSpawnSpanBlock(this.xBlock, waterLevel + 1, this.zBlock) == 1) {
/* 504 */         waterLevel++;
/*     */       }
/*     */     }
/*     */     
/* 508 */     if (this.spawnSpans.length <= this.spawnSpansUsed) {
/*     */       
/* 510 */       SpawnSpan[] newSpans = new SpawnSpan[this.spawnSpansUsed + 4];
/* 511 */       System.arraycopy(this.spawnSpans, 0, newSpans, 0, this.spawnSpansUsed);
/* 512 */       for (int i = this.spawnSpansUsed; i < newSpans.length; i++) {
/* 513 */         newSpans[i] = new SpawnSpan();
/*     */       }
/* 515 */       this.spawnSpans = newSpans;
/*     */     } 
/*     */     
/* 518 */     SpawnSpan spawnSpan = this.spawnSpans[this.spawnSpansUsed++];
/* 519 */     spawnSpan.bottom = top - span;
/* 520 */     spawnSpan.top = top - 1;
/* 521 */     spawnSpan.waterLevel = waterLevel;
/* 522 */     spawnSpan.groundLevel = groundLevel;
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
/*     */   private int isSpawnSpanBlock(int x, int y, int z) {
/* 535 */     int block = this.worldChunk.getBlock(x, y, z);
/* 536 */     if (block == 0 || ((BlockType)BLOCK_ASSET_MAP.getAsset(block)).getMaterial() == BlockMaterial.Empty) {
/* 537 */       return (this.worldChunk.getFluidId(x, y, z) == 0) ? 0 : 1;
/*     */     }
/* 539 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void commonInit() {
/* 546 */     this.yaw = RandomExtra.randomRange(0.0F, 6.2831855F);
/* 547 */     this.pitch = 0.0D;
/* 548 */     this.roll = 0.0D;
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
/*     */   public SpawnTestResult canSpawn(boolean testOverlapBlocks, boolean testOverlapEntities) {
/* 560 */     SpawnTestResult spawnTestResult = SpawnTestResult.TEST_OK;
/* 561 */     if (testOverlapBlocks) {
/* 562 */       spawnTestResult = intersectsBlock();
/* 563 */       if (spawnTestResult != SpawnTestResult.TEST_OK) return spawnTestResult; 
/*     */     } 
/* 565 */     if (testOverlapEntities) {
/* 566 */       spawnTestResult = intersectsEntity();
/*     */     }
/* 568 */     return spawnTestResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SpawnTestResult canSpawn() {
/* 578 */     return canSpawn(true, true);
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
/*     */   @Nonnull
/*     */   private SpawnTestResult intersectsEntity() {
/* 591 */     return SpawnTestResult.TEST_OK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private SpawnTestResult intersectsBlock() {
/* 601 */     if (this.worldChunk == null || this.spawnModel == null || this.spawnable == null) throw new IllegalStateException("SpawningContext initialized"); 
/* 602 */     return this.spawnable.canSpawn(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWaterBlock(int fluidId) {
/* 612 */     return (fluidId != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWaterLevel() {
/* 621 */     return this.waterLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAirHeight() {
/* 630 */     return this.airHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInsideSpan(double y) {
/* 637 */     return (y >= this.ySpanMin && y <= this.ySpanMax);
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
/*     */   public boolean isInWater(float minDepth) {
/* 649 */     int depth = this.waterLevel - this.groundLevel - 1;
/* 650 */     if (depth < 0) return false;
/*     */     
/* 652 */     int roundedDepth = MathUtil.fastCeil(minDepth);
/* 653 */     if (depth < roundedDepth) return false;
/*     */     
/* 655 */     double ySpawn = (this.waterLevel - roundedDepth);
/* 656 */     if (!isInsideSpan(ySpawn)) return false;
/*     */     
/* 658 */     this.ySpawn = ySpawn - (this.spawnModel.getBoundingBox()).min.y;
/* 659 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnSolidGround() {
/* 670 */     if (isWaterBlock(this.groundFluidId)) return false;
/*     */     
/* 672 */     this.ySpawn = this.ySpawnMin - (this.spawnModel.getBoundingBox()).min.y;
/*     */     
/* 674 */     int ySpawnBlock = MathUtil.floor(this.ySpawnMin);
/*     */     
/* 676 */     if (ySpawnBlock == this.yBlock) return (this.ySpawn >= (this.ySpanMin - 1) && this.ySpawn <= this.ySpanMax);
/*     */     
/* 678 */     BlockType blockType = this.worldChunk.getBlockType(this.xBlock, ySpawnBlock, this.zBlock);
/* 679 */     int fluidId = this.worldChunk.getFluidId(this.xBlock, ySpawnBlock, this.zBlock);
/* 680 */     int rotation = this.worldChunk.getRotationIndex(this.xBlock, ySpawnBlock, this.zBlock);
/*     */     
/* 682 */     if (WorldUtil.isEmptyOnlyBlock(blockType, fluidId) || fluidId != 0) return isInsideSpan(this.ySpawn); 
/* 683 */     if (WorldUtil.isSolidOnlyBlock(blockType, fluidId)) {
/* 684 */       this.ySpawn = ySpawnBlock + NPCPhysicsMath.blockHeight(blockType, rotation) - (this.spawnModel.getBoundingBox()).min.y;
/* 685 */       return isInsideSpan(this.ySpawn);
/*     */     } 
/* 687 */     return false;
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
/*     */   public boolean isInAir(double height) {
/* 699 */     this.ySpawn = getAirHeight() + height - (this.spawnModel.getBoundingBox()).min.y;
/* 700 */     return (this.ySpawn < 320.0D && isInsideSpan(this.ySpawn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validatePosition(int invalidMaterials) {
/* 710 */     if (this.spawnModel == null) {
/* 711 */       return false;
/*     */     }
/*     */     
/* 714 */     this.position.assign(this.xSpawn, this.ySpawn, this.zSpawn);
/* 715 */     return (CollisionModule.get().validatePosition(this.world, this.spawnModel.getBoundingBox(), this.position, invalidMaterials, null, (_this, collisionCode, collision, collisionConfig) -> (collisionConfig.blockId != -1), this.collisionResult) != -1);
/*     */   }
/*     */   
/*     */   public boolean canBreathe(boolean breathesInAir, boolean breathesInWater) {
/* 719 */     if (this.spawnModel == null) return false; 
/* 720 */     if (!breathesInAir && this.ySpawn + this.spawnModel.getEyeHeight() >= this.airHeight) return false;
/*     */ 
/*     */     
/* 723 */     if (!breathesInWater && (this.waterLevel + 1) - this.ySpawn >= this.spawnModel.getEyeHeight()) return false; 
/* 724 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 731 */     this.groundBlockType = null;
/* 732 */     this.world = null;
/* 733 */     this.worldChunk = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseFull() {
/* 740 */     release();
/* 741 */     this.spawnable = null;
/* 742 */     this.modifierScope = null;
/* 743 */     this.spawnModel = null;
/* 744 */     this.executionContext.setScope(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ExecutionContext getExecutionContext() {
/* 754 */     return this.executionContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d newPosition() {
/* 764 */     return new Vector3d(this.xSpawn, this.ySpawn, this.zSpawn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f newRotation() {
/* 774 */     return new Vector3f((float)this.pitch, (float)this.yaw, (float)this.roll);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 780 */     return "SpawningContext{xBlock=" + this.xBlock + ", zBlock=" + this.zBlock + ", yBlock=" + this.yBlock + ", ySpawnMin=" + this.ySpawnMin + ", xSpawn=" + this.xSpawn + ", zSpawn=" + this.zSpawn + ", ySpawn=" + this.ySpawn + ", yaw=" + this.yaw + ", pitch=" + this.pitch + ", roll=" + this.roll + ", groundBlockId=" + this.groundBlockId + "}";
/*     */   }
/*     */   
/*     */   private static class SpawnSpan {
/*     */     int bottom;
/*     */     int top;
/*     */     int waterLevel;
/*     */     int groundLevel;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\SpawningContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */