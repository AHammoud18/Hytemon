/*     */ package com.hypixel.hytale.server.worldgen.chunk;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockStateChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedEntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenTimingsCollector;
/*     */ import com.hypixel.hytale.server.worldgen.cache.CoreDataCacheEntry;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.populator.BlockPopulator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.populator.CavePopulator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.populator.PrefabPopulator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.populator.WaterPopulator;
/*     */ import com.hypixel.hytale.server.worldgen.util.BlockFluidEntry;
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
/*     */ public class ChunkGeneratorExecution
/*     */ {
/*     */   @Nonnull
/*     */   private final ChunkGenerator chunkGenerator;
/*     */   private final GeneratedBlockChunk blockChunk;
/*     */   private final GeneratedBlockStateChunk blockStateChunk;
/*     */   private final GeneratedEntityChunk entityChunk;
/*     */   private final Holder<ChunkStore>[] sections;
/*     */   @Nonnull
/*     */   private final BlockPriorityChunk priorityChunk;
/*     */   @Nonnull
/*     */   private final HeightThresholdInterpolator interpolator;
/*  47 */   private BlockPriorityModifier blockPriorityModifier = BlockPriorityModifier.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkGeneratorExecution(int seed, @Nonnull ChunkGenerator chunkGenerator, GeneratedBlockChunk blockChunk, GeneratedBlockStateChunk blockStateChunk, GeneratedEntityChunk entityChunk, Holder<ChunkStore>[] sections) {
/*  55 */     this.chunkGenerator = chunkGenerator;
/*  56 */     this.blockChunk = blockChunk;
/*  57 */     this.blockStateChunk = blockStateChunk;
/*  58 */     this.entityChunk = entityChunk;
/*  59 */     this.sections = sections;
/*  60 */     this.priorityChunk = (ChunkGenerator.getResource()).priorityChunk.reset();
/*     */     
/*  62 */     long start = -System.nanoTime();
/*  63 */     this.interpolator = (new HeightThresholdInterpolator(this)).populate(seed);
/*  64 */     chunkGenerator.getTimings().reportPrepare(start + System.nanoTime());
/*     */   }
/*     */   
/*     */   public void execute(int seed) {
/*  68 */     WorldGenTimingsCollector timings = this.chunkGenerator.getTimings();
/*     */ 
/*     */ 
/*     */     
/*  72 */     generateTintMapping(seed);
/*     */ 
/*     */     
/*  75 */     generateEnvironmentMapping(seed);
/*     */ 
/*     */     
/*  78 */     long start = -System.nanoTime();
/*  79 */     BlockPopulator.populate(seed, this);
/*  80 */     timings.reportBlocksGeneration(start + System.nanoTime());
/*     */ 
/*     */     
/*  83 */     start = -System.nanoTime();
/*  84 */     CavePopulator.populate(seed, this);
/*  85 */     timings.reportCaveGeneration(start + System.nanoTime());
/*     */ 
/*     */     
/*  88 */     start = -System.nanoTime();
/*  89 */     PrefabPopulator.populate(seed, this);
/*  90 */     timings.reportPrefabGeneration(start + System.nanoTime());
/*     */ 
/*     */     
/*  93 */     WaterPopulator.populate(seed, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ChunkGenerator getChunkGenerator() {
/* 104 */     return this.chunkGenerator;
/*     */   }
/*     */   
/*     */   public GeneratedBlockChunk getChunk() {
/* 108 */     return this.blockChunk;
/*     */   }
/*     */   
/*     */   public GeneratedBlockStateChunk getBlockStateChunk() {
/* 112 */     return this.blockStateChunk;
/*     */   }
/*     */   
/*     */   public GeneratedEntityChunk getEntityChunk() {
/* 116 */     return this.entityChunk;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockPriorityChunk getPriorityChunk() {
/* 121 */     return this.priorityChunk;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public HeightThresholdInterpolator getInterpolator() {
/* 126 */     return this.interpolator;
/*     */   }
/*     */   
/*     */   public Holder<ChunkStore> getSection(int y) {
/* 130 */     return this.sections[y];
/*     */   }
/*     */   
/*     */   public ZoneBiomeResult zoneBiomeResult(int cx, int cz) {
/* 134 */     return this.interpolator.zoneBiomeResult(cx, cz);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CoreDataCacheEntry[] getCoreDataEntries() {
/* 139 */     return this.interpolator.getEntries();
/*     */   }
/*     */   
/*     */   public long getIndex() {
/* 143 */     return this.blockChunk.getIndex();
/*     */   }
/*     */   
/*     */   public int getX() {
/* 147 */     return this.blockChunk.getX();
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 151 */     return this.blockChunk.getZ();
/*     */   }
/*     */   
/*     */   public void setPriorityModifier(BlockPriorityModifier blockPriorityModifier) {
/* 155 */     this.blockPriorityModifier = blockPriorityModifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateTintMapping(int seed) {
/* 165 */     int radius = 4;
/* 166 */     int[] rawTint = new int[(32 + radius * 2) * (32 + radius * 2)];
/*     */     
/* 168 */     int m = 32 + radius; int cx;
/* 169 */     for (cx = -radius; cx < m; cx++) {
/* 170 */       for (int cz = -radius; cz < m; cz++) {
/* 171 */         rawTint[tintIndexLocal(cx, cz)] = zoneBiomeResult(cx, cz).getBiome()
/* 172 */           .getTintContainer().getTintColorAt(seed, globalX(cx), globalZ(cz));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 178 */     int radius2 = radius * radius;
/* 179 */     for (cx = 0; cx < 32; cx++) {
/* 180 */       for (int cz = 0; cz < 32; cz++) {
/* 181 */         int r = 0, g = 0, b = 0, counter = 0;
/* 182 */         for (int ix = -radius; ix <= radius; ix++) {
/* 183 */           for (int iz = -radius; iz <= radius; iz++) {
/* 184 */             if (ix * ix + iz * iz <= radius2) {
/* 185 */               int c = rawTint[tintIndexLocal(cx + ix, cz + iz)];
/* 186 */               r += c >> 16 & 0xFF;
/* 187 */               g += c >> 8 & 0xFF;
/* 188 */               b += c & 0xFF;
/* 189 */               counter++;
/*     */             } 
/*     */           } 
/*     */         } 
/* 193 */         if (counter > 0) {
/* 194 */           r /= counter;
/* 195 */           g /= counter;
/* 196 */           b /= counter;
/* 197 */           this.blockChunk.setTint(cx, cz, 0xFF000000 | r << 16 | g << 8 | b);
/*     */         } else {
/*     */           
/* 200 */           this.blockChunk.setTint(cx, cz, -65536);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int tintIndexLocal(int x, int z) {
/* 207 */     return (x + 4) * 40 + z + 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateEnvironmentMapping(int seed) {
/* 217 */     for (int cx = 0; cx < 32; cx++) {
/* 218 */       for (int cz = 0; cz < 32; cz++) {
/*     */ 
/*     */ 
/*     */         
/* 222 */         int envId = zoneBiomeResult(cx, cz).getBiome().getEnvironmentContainer().getEnvironmentAt(seed, globalX(cx), globalZ(cz));
/* 223 */         this.blockChunk.setEnvironmentColumn(cx, cz, envId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlock(int x, int y, int z) {
/* 235 */     return this.blockChunk.getBlock(x, y, z);
/*     */   }
/*     */   
/*     */   public int getRotationIndex(int x, int y, int z) {
/* 239 */     return this.blockChunk.getRotationIndex(x, y, z);
/*     */   }
/*     */   
/*     */   public void setEnvironment(int x, int y, int z, int environment) {
/* 243 */     if (environment == Integer.MIN_VALUE)
/* 244 */       return;  this.blockChunk.setEnvironment(x, y, z, environment);
/*     */   }
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, byte type, int block) {
/* 248 */     return setBlock(x, y, z, type, block, (Holder<ChunkStore>)null);
/*     */   }
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, byte type, BlockFluidEntry entry) {
/* 252 */     return setBlock(x, y, z, type, entry.blockId(), null, -1, entry.rotation(), 0);
/*     */   }
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, byte type, int block, int environment) {
/* 256 */     if (setBlock(x, y, z, type, block, (Holder<ChunkStore>)null)) {
/* 257 */       setEnvironment(x, y, z, environment);
/* 258 */       return true;
/*     */     } 
/* 260 */     return false;
/*     */   }
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, byte type, BlockFluidEntry entry, int environment) {
/* 264 */     if (setBlock(x, y, z, type, entry.blockId(), null, -1, entry.rotation(), 0)) {
/* 265 */       setEnvironment(x, y, z, environment);
/* 266 */       return true;
/*     */     } 
/* 268 */     return false;
/*     */   }
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, byte type, int block, Holder<ChunkStore> holder) {
/* 272 */     return setBlock(x, y, z, type, block, holder, -1, 0, 0);
/*     */   }
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, byte type, int block, Holder<ChunkStore> holder, int supportValue, int rotation, int filler) {
/* 276 */     if (y < 0 || y >= 320) return false;
/*     */     
/* 278 */     byte newPriority = (byte)(type & 0x1F);
/* 279 */     byte newFlags = (byte)(type & 0xFFFFFFE0);
/*     */     
/* 281 */     byte oldPriority = this.priorityChunk.get(x, y, z);
/* 282 */     byte oldModified = this.blockPriorityModifier.modifyCurrent(oldPriority, newPriority);
/*     */     
/* 284 */     if (type == -1) {
/* 285 */       newPriority = oldModified;
/*     */     }
/*     */     
/* 288 */     if (newPriority >= oldModified) {
/* 289 */       newPriority = (byte)(this.blockPriorityModifier.modifyTarget(oldPriority, newPriority) | newFlags);
/* 290 */       this.priorityChunk.set(x, y, z, newPriority);
/* 291 */       this.blockChunk.setBlock(x, y, z, block, rotation, filler);
/* 292 */       this.blockStateChunk.setState(x, y, z, holder);
/*     */       
/* 294 */       Holder<ChunkStore> section = getSection(ChunkUtil.chunkCoordinate(y));
/*     */ 
/*     */       
/* 297 */       if (supportValue >= 0) {
/* 298 */         BlockPhysics.setSupportValue(section, x, y, z, supportValue);
/*     */       } else {
/* 300 */         BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(block);
/* 301 */         if (blockType == null || !blockType.hasSupport()) {
/* 302 */           BlockPhysics.clear(section, x, y, z);
/*     */         } else {
/* 304 */           BlockPhysics.reset(section, x, y, z);
/*     */         } 
/*     */       } 
/*     */       
/* 308 */       return true;
/*     */     } 
/*     */     
/* 311 */     return false;
/*     */   }
/*     */   
/*     */   public boolean setFluid(int x, int y, int z, byte type, int fluid, int environment) {
/* 315 */     if (setFluid(x, y, z, type, fluid)) {
/* 316 */       setEnvironment(x, y, z, environment);
/* 317 */       return true;
/*     */     } 
/* 319 */     return false;
/*     */   }
/*     */   
/*     */   public boolean setFluid(int x, int y, int z, byte type, int fluid) {
/* 323 */     return setFluid(x, y, z, type, fluid, (byte)((Fluid)Fluid.getAssetMap().getAsset(fluid)).getMaxFluidLevel());
/*     */   }
/*     */   
/*     */   public boolean setFluid(int x, int y, int z, byte type, int fluid, byte fluidLevel) {
/* 327 */     if (y < 0 || y >= 320) return false;
/*     */     
/* 329 */     byte newPriority = (byte)(type & 0x1F);
/* 330 */     byte newFlags = (byte)(type & 0xFFFFFFE0);
/*     */     
/* 332 */     byte oldPriority = this.priorityChunk.get(x, y, z);
/* 333 */     byte oldModified = this.blockPriorityModifier.modifyCurrent(oldPriority, newPriority);
/*     */     
/* 335 */     if (type == -1) {
/* 336 */       newPriority = oldModified;
/*     */     }
/*     */     
/* 339 */     if (newPriority >= oldModified) {
/* 340 */       newPriority = (byte)(this.blockPriorityModifier.modifyTarget(oldPriority, newPriority) | newFlags);
/* 341 */       this.priorityChunk.set(x, y, z, newPriority);
/*     */       
/* 343 */       Holder<ChunkStore> section = getSection(ChunkUtil.chunkCoordinate(y));
/* 344 */       FluidSection fluidSection = (FluidSection)section.getComponent(FluidSection.getComponentType());
/* 345 */       if (fluidSection == null) fluidSection = (FluidSection)section.ensureAndGetComponent(FluidSection.getComponentType()); 
/* 346 */       fluidSection.setFluid(x, y, z, fluid, fluidLevel);
/* 347 */       return true;
/*     */     } 
/*     */     
/* 350 */     return false;
/*     */   }
/*     */   
/*     */   public int getFluid(int x, int y, int z) {
/* 354 */     if (y < 0 || y >= 320) return Integer.MIN_VALUE;
/*     */     
/* 356 */     Holder<ChunkStore> section = getSection(ChunkUtil.chunkCoordinate(y));
/* 357 */     FluidSection fluidSection = (FluidSection)section.ensureAndGetComponent(FluidSection.getComponentType());
/* 358 */     return fluidSection.getFluidId(x, y, z);
/*     */   }
/*     */   
/*     */   public void overrideBlock(int x, int y, int z, byte type, int block) {
/* 362 */     overrideBlock(x, y, z, type, block, null);
/*     */   }
/*     */   
/*     */   public void overrideBlock(int x, int y, int z, byte type, int block, Holder<ChunkStore> holder) {
/* 366 */     overrideBlock(x, y, z, type, block, holder, 0, 0);
/*     */   }
/*     */   
/*     */   public void overrideBlock(int x, int y, int z, byte type, BlockFluidEntry entry) {
/* 370 */     overrideBlock(x, y, z, type, entry.blockId(), null, entry.rotation(), 0);
/*     */   }
/*     */   
/*     */   public void overrideBlock(int x, int y, int z, byte type, int block, Holder<ChunkStore> holder, int rotation, int filler) {
/* 374 */     this.priorityChunk.set(x, y, z, type);
/* 375 */     this.blockChunk.setBlock(x, y, z, block, rotation, filler);
/* 376 */     this.blockStateChunk.setState(x, y, z, holder);
/*     */     
/* 378 */     Holder<ChunkStore> section = getSection(ChunkUtil.chunkCoordinate(y));
/* 379 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(block);
/* 380 */     if (blockType == null || !blockType.hasSupport()) {
/* 381 */       BlockPhysics.clear(section, x, y, z);
/*     */     } else {
/* 383 */       BlockPhysics.reset(section, x, y, z);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void overrideFluid(int x, int y, int z, byte type, int fluid) {
/* 388 */     if (y < 0 || y >= 320)
/*     */       return; 
/* 390 */     this.priorityChunk.set(x, y, z, type);
/* 391 */     Holder<ChunkStore> section = getSection(ChunkUtil.chunkCoordinate(y));
/* 392 */     FluidSection fluidSection = (FluidSection)section.ensureAndGetComponent(FluidSection.getComponentType());
/* 393 */     fluidSection.setFluid(x, y, z, fluid, (byte)((Fluid)Fluid.getAssetMap().getAsset(fluid)).getMaxFluidLevel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int localX(int x) {
/* 403 */     return x - this.blockChunk.getX() * 32;
/*     */   }
/*     */   
/*     */   protected int localZ(int z) {
/* 407 */     return z - this.blockChunk.getZ() * 32;
/*     */   }
/*     */   
/*     */   public int globalX(int localX) {
/* 411 */     return ChunkUtil.minBlock(this.blockChunk.getX()) + localX;
/*     */   }
/*     */   
/*     */   public int globalZ(int localZ) {
/* 415 */     return ChunkUtil.minBlock(this.blockChunk.getZ()) + localZ;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\ChunkGeneratorExecution.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */